<template>
  <v-form ref="form1" v-model="valid1" lazy-validation>
    <v-row
        justify="space-between"
    >
      <v-col>
        <v-flex class="mt-4" >
          <h2>КОНСТРУКТОР ОТЧЕТОВ ПО РЕМОНТУ</h2>
        </v-flex>
      </v-col>
    </v-row>
    <v-flex class="mt-4 mb-2">
      <h3>По дате создания</h3>
    </v-flex>
    <v-row>
      <v-col
          cols="auto"
          md="4"
      >
        <v-menu
            ref="creationDatePicker"
            v-model="creationDatePickerFrom"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            max-width="290px"
            min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
                v-model="creationDateFrom"
                label="Дата приема (от)"
                persistent-hint
                prepend-icon="mdi-calendar"
                v-bind="attrs"
                v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
              v-model="creationDateFrom"
              no-title
              @input="creationDatePickerFrom = false"
          ></v-date-picker>
        </v-menu>
      </v-col>
      <v-col
          cols="auto"
          md="4"
      >
        <v-menu
            ref="issueDatePicker"
            v-model="creationDatePickerTo"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            max-width="290px"
            min-width="auto"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
                v-model="creationDateTo"
                label="Дата приема (до)"
                persistent-hint
                prepend-icon="mdi-calendar"
                v-bind="attrs"
                v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
              v-model="creationDateTo"
              no-title
              @input="creationDatePickerTo = false"
          ></v-date-picker>
        </v-menu>
      </v-col>
      <v-col
          cols="auto"
          md="4"
      >
        <v-combobox
            v-model="statuses1"
            :items="repairStatusList"
            item-text="name"
            return-object
            label="Статус ремонта"
            multiple
            prepend-icon="mdi-check"
            single-line
            chips
        >
          <template v-slot:selection="{ attrs, item, select, selected }">
            <v-chip
                v-bind="attrs"
                :input-value="selected"
                close
                @click="select"
                @click:close="remove1(item)"
            >
              <strong>{{ item.name }}</strong>
            </v-chip>
          </template>
        </v-combobox>
      </v-col>
    </v-row>
    <v-row
        class="justify-end"
    >
      <v-col
          md="3"
      >
        <v-btn
            block
            depressed
            large
            @click="clearCreationDateForm">
          Очистить поля
        </v-btn>
      </v-col>
      <v-col
          md="3"
      >
        <v-btn
            block
            depressed
            color="primary"
            @click="generateReportType1"
            large>
          Сформировать отчет
        </v-btn>
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
import {mapState, mapActions} from 'vuex'
export default {
  name: "OrderConstructor",
  data: () => ({
    valid1: false,
    notificationMessage: '',
    order: [],
    creationDatePickerFrom: false,
    creationDatePickerTo: false,
    creationDateFrom:[],
    creationDateTo:[],
    statuses1: [],

  }),
  computed: {
    ...mapState(['repairStatusList'])
  },
  beforeMount() {
    this.getRepairStatusListAction()
  },
  methods: {
    ...mapActions(['getRepairStatusListAction']),

    clearCreationDateForm() {
      this.creationDatePickerFrom = false
      this.creationDateFrom = []
      this.$refs.form1.reset()
    },
    remove1 (item) {
      this.statuses1.splice(this.statuses1.indexOf(item), 1)
      this.statuses1 = [...this.statuses1]
    },
    generateReportType1() {
      let st = [];
      for (let i = 0; i < this.statuses1.length; i++) {
        st[i] = this.statuses1.at(i).id
      }
      this.$router.push({path: '/report/repair', query: {md:'creation',  f: this.creationDateFrom, t: this.creationDateTo, st}})
    }
  }
}
</script>

<style>

</style>