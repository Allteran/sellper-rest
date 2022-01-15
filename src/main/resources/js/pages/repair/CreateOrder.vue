<template>
  <v-form ref="form" v-model="valid" lazy-validation>
    <v-container>
      <v-alert v-if="generated" type="success">
        {{ notificationMessage }}
      </v-alert>
      <v-flex class="mt-4 mb-4">
        <h2>ЗАЯВКА НА РЕМОНТ</h2>
      </v-flex>
      <v-row>
        <v-col
            cols="8"
            md="4"
        >
          <v-select
              v-model="selectedType"
              :items="deviceTypeList"
              item-text="name"
              item-value="id"
              label="Тип устройства"
              persistent-hint
              return-object
              single-line
          ></v-select>
        </v-col>
        <v-col
            cols="8"
            md="4"
        >
          <v-text-field
              v-model="order.deviceName"
              :rules="inputFieldRules"
              :counter="15"
              label="Модель устройства"
              required
          ></v-text-field>
        </v-col>
        <v-col
            cols="8"
            md="4"
        >
          <v-text-field
              v-model="order.serialNumber"
              :rules="inputFieldRules"
              :counter="15"
              label="Серийный номер"
              required
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col
            cols="6"
            md="6"
        >
          <v-text-field
              v-model="order.customerName"
              :rules="inputFieldRules"
              :counter="45"
              label="Заказчик"
              required
          ></v-text-field>
        </v-col>
        <v-col
            cols="6"
            md="6"
        >
          <v-text-field
              v-model="order.customerPhone"
              :rules="phoneRules"
              label="79XXXXXXXXX"
              :counter="11"
              required
          ></v-text-field>
        </v-col>

      </v-row>
      <v-row>
        <v-col
            cols="8"
            md="4"
        >
          <v-text-field
              v-model="order.defect"
              :rules="inputFieldRules"
              label="Заявленная неисправность"
              :counter="45"
              required
          ></v-text-field>
        </v-col>
        <v-col
            cols="8"
            md="4"
        >
          <v-text-field
              v-model="order.equipSet"
              :rules="inputFieldRules"
              label="Комплектация устройства"
              :counter="45"
              required
          ></v-text-field>
        </v-col>
        <v-col
            cols="8"
            md="4"
        >
          <v-text-field
              v-model="order.appearance"
              :rules="inputFieldRules"
              label="Состояние устройства"
              :counter="45"
              required
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col
            cols="6"
            md="6"
        >
          <v-select
              v-model="selectedPOS"
              :items="posList"
              item-text="street"
              item-value="id"
              label="Точка продаж"
              persistent-hint
              return-object
              single-line
          ></v-select>
        </v-col>
        <v-col
            cols="6"
            md="6"
        >
          <v-text-field
              v-model="order.preliminaryPrice"
              :rules="inputFieldRules"
              label="Предварительная цена"
              :counter="5"
              type="number"
              required
          ></v-text-field>
        </v-col>
      </v-row>

    </v-container>
    <v-speed-dial
        v-model="fab"
        class="mr-10 mb-10"
        transition="slide-y-reverse-transition"
        right
        bottom
        fixed
    >
      <template v-slot:activator>
        <v-btn
            v-model="fab"
            color="blue darken-2"
            dark
            fab
        >
          <v-icon v-if="fab">
            mdi-close
          </v-icon>
          <v-icon v-else>
            mdi-pencil
          </v-icon>
        </v-btn>
      </template>
      <v-btn
          fab
          dark
          color="indigo"
          @click="printAcceptanceCertificate"
      >
        <v-icon>mdi-printer-outline</v-icon>
      </v-btn>
      <v-btn
          fab
          dark
          color="red"
          @click="saveOrder"
      >
        <v-icon>mdi-content-save</v-icon>
      </v-btn>
    </v-speed-dial>
  </v-form>
</template>

<script>
import {mapActions, mapState} from 'vuex'
import axios from 'axios'

export default {
  name: 'CreateOrder',
  data: () =>({
    inputFieldRules: [
      v => !!v || 'Поле не может быть пустым',
    ],
    phoneRules: [
      v => !!v || 'Номер телефона не может быть пустым',
      v => /^[\d]{11}$/.test(v) || '7XXXXXXXXXX',
    ],
    order: {
      id: '',
      deviceName: '',
      serialNumber:'',
      customerName: '',
      customerPhone: '',
      preliminaryPrice: '',
      defect: '',
      equipSet: '',
      appearance: '',
      pos: {},
      status: {},
      deviceType: {},
      warranty: '',
      performedActions: '',
      creationDate: '',
      issueDate: '',
      author: {},
      servicePrice: '',
      componentPrice: '',
      marginPrice: '',
      totalPrice: '',
      directorProfit: '',
      repManProfit: '',
      managerProfit: '',
    },
    valid: false,
    generated: false,
    notificationMessage: '',
    selectedType: {
      id: '',
      name: '',
    },
    selectedPOS: {
      id: '',
      city: '',
      street: '',
      building: '',
      type: {
        id: '',
        name: ''
      }
    },
    direction: 'top',
    fab: false,

  }),
  computed: {
    ...mapState(['deviceTypeList', 'posList', 'profile'])
  },
  beforeMount() {
    this.getDeviceTypeListAction()
    this.getPOSListAction()
  },
  methods: {
    ...mapActions(['getDeviceTypeListAction', 'getPOSListAction', 'addRepairOrderAction', 'generateAcceptanceCertificate']),
    validate() {
      this.valid = this.$refs.form.validate()
    },

    prepareOrder() {
      this.order.pos = this.selectedPOS
      this.order.deviceType = this.selectedType
      this.order.author = this.profile
      this.order.issueDate = '2000-01-01T01:01:00'
    },

    printAcceptanceCertificate() {
      this.validate()
      if(this.valid) {
        this.prepareOrder()
        this.generateAcceptanceCertificate(this.order)
        this.notificationMessage = 'Готово! Сейчас начнется загрузка документа'
        this.generated = true
      }
    },
    saveOrder() {
      this.validate()
      if(this.valid) {
        this.prepareOrder()

        this.addRepairOrderAction(this.order)
        this.$router.push('/repair/order')
      }
    },

  }
}

</script>

<style>

</style>