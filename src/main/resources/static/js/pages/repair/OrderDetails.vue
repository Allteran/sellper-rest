<template>
  <v-container>
    <v-alert v-if="generated" type="success">
      {{ notificationMessage }}
    </v-alert>
    <v-row
        justify="space-between"
    >
      <v-col>
        <v-flex class="mt-4" >
          <h2>ЗАЯВКА НА РЕМОНТ №{{ order.id }}</h2>
        </v-flex>
      </v-col>
      <v-col
          md="4"
      >
        <v-flex class="mt-4" >
          <h2>Цена: {{ order.totalPrice }} р.</h2>
        </v-flex>
      </v-col>
    </v-row>
    <v-flex class="mt-4 mb-2">
      <h3>Основная информация</h3>
    </v-flex>
    <v-row>
      <v-col
          cols="auto"
          md="4"
      >
        <v-text-field
            v-model="creationDate"
            label="Дата приема"
            prepend-icon="mdi-calendar"
            readonly
        ></v-text-field>
      </v-col>
      <v-col
          cols="auto"
          md="4"
      >
        <v-text-field
            v-model="order.status.name"
            label="Статус ремонта"
            prepend-icon="mdi-check"
            readonly
        ></v-text-field>
      </v-col>
      <v-col
          cols="auto"
          md="4"
      >
        <v-text-field
            v-model="issueDate"
            label="Дата выдачи"
            prepend-icon="mdi-calendar"
            :color="issueDateColor"
            readonly
        ></v-text-field>
      </v-col>
    </v-row>
    <v-flex class="mt-4 mb-2">
      <h3>Заказчик</h3>
    </v-flex>
    <v-row>
      <v-col
          cols="6"
          md="6"
      >
        <v-text-field
            v-model="order.customerName"
            label="Заказчик"
            prepend-icon="mdi-account"
            readonly
        ></v-text-field>
      </v-col>
      <v-col
          cols="6"
          md="6"
      >
        <v-text-field
            v-model="order.customerPhone"
            label="Контактный номер телефона"
            prepend-icon="mdi-phone"
            readonly
        ></v-text-field>
      </v-col>
    </v-row>
    <v-flex class="mt-4 mb-2">
      <h3>Устройство</h3>
    </v-flex>
    <v-row>
      <v-col
          cols="8"
          md="4"
      >
        <v-text-field
            v-model="order.deviceType.name"
            label="Тип устройства"
            prepend-icon="mdi-cellphone"
            readonly
        ></v-text-field>
      </v-col>
      <v-col
          cols="8"
          md="4"
      >
        <v-text-field
            v-model="order.deviceName"
            label="Модель устройства"
            prepend-icon="mdi-cellphone-text"
            readonly
        ></v-text-field>
      </v-col>
      <v-col
          cols="8"
          md="4"
      >
        <v-text-field
            v-model="order.serialNumber"
            label="Серийный номер (IMEI)"
            prepend-icon="mdi-numeric"
            readonly
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col
          cols="12"
      >
        <v-text-field
            v-model="order.defect"
            label="Описание неисправности"
            prepend-icon="mdi-comment-text-outline"
            readonly
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col
          cols="6"
          md="6"
      >
        <v-text-field
            v-model="order.appearance"
            label="Состояние устройства"
            prepend-icon="mdi-cellphone-information"
            readonly
        ></v-text-field>
      </v-col>
      <v-col
          cols="6"
          md="6"
      >
        <v-text-field
            v-model="order.warranty"
            label="Гарантийный срок"
            prepend-icon="mdi-alert-circle"
            readonly
        ></v-text-field>
      </v-col>
    </v-row>
    <v-flex class="mt-4 mb-2">
      <h3>Выполненные работы и цены</h3>
    </v-flex>
    <v-row>
      <v-col
          cols="auto"
          md="6"
      >
        <v-text-field
            v-model="order.performedActions"
            label="Проведенные работы"
            :color="performedActionsColor"
            prepend-icon="mdi-cog-outline"
            readonly
        ></v-text-field>
      </v-col>
      <v-col
          cols="auto"
          md="6"
      >
        <v-text-field
            v-model="order.preliminaryPrice"
            label="Предварительная цена"
            prepend-icon="mdi-cash"
            readonly
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-col
          cols="auto"
          md="4"
      >
        <v-text-field
            v-model="order.componentPrice"
            label="Стоимость запчастей"
            prepend-icon="mdi-cash"
            readonly
        ></v-text-field>
      </v-col>
      <v-col
          cols="auto"
          md="4"
      >
        <v-text-field
            v-model="order.marginPrice"
            label="Маржа"
            prepend-icon="mdi-cash"
            readonly
        ></v-text-field>
      </v-col>
      <v-col
          cols="auto"
          md="4"
      >
        <v-text-field
            v-model="order.totalPrice"
            label="Итоговая цена"
            prepend-icon="mdi-cash"
            readonly
        ></v-text-field>
      </v-col>
    </v-row>
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
            mdi-pencil-box-multiple
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
          @click="showEditPage"
      >
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
    </v-speed-dial>
  </v-container>
</template>

<script>
import {mapState, mapActions} from 'vuex'
import moment from 'moment'

export default {
  name: 'OrderDetails',
  data: () => ({
    order: [],
    creationDate: [],
    issueDate: [],
    issueDateColor: 'black',
    performedActionsColor: 'black',
    fab: false,
    notificationMessage: '',
    generated: false,
  }),
  computed: {
    ...mapState(['repairOrderList']),
  },
  beforeMount() {
    this.order = this.repairOrderList.find(o => o.id === this.$route.params.id)
    this.creationDate = this.dateFormat(this.order.creationDate)
    if(this.order.issueDate === '2000-01-01T01:01:00') {
      this.issueDate = 'Не выдан'
      this.issueDateColor = 'red'
    } else {
      this.issueDate = this.dateFormat(this.order.issueDate)
    }

    if(this.order.performedActions === '') {
      this.performedActionsColor = 'red'
    }
  },
  methods: {
    ...mapActions(['generateAcceptanceCertificate']),
    dateFormat(date) {
      return moment(date).format('DD.MM.YYYY')
    },
    printAcceptanceCertificate() {
      this.generateAcceptanceCertificate(this.order)
      this.generated = true
      this.notificationMessage = 'Сейчас начнется повторная загрузка акта устройства приема на ремонт'
    },
    showEditPage() {
      this.$router.push({name: 'order-edit', params: {id: this.order.id}})
    }
  }
}

</script>

<style>

</style>