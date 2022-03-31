<template>
  <v-container>
    <v-data-table
        :headers="headers"
        :items="repairList"
        :loading="loading"
        :search="search"
        :loading-text="'Загружаем данные'"
        :no-data-text="'Ничено не найдено :('"
        :no-results-text="'Ничено не найдено :('"
        class="elevation-1"
        :footer-props="{
          'items-per-page-options': [10, 20, 30, 40, 50, 100],
          'items-per-page-text':'Отображаемое количество',
        }"
    >
      <template v-slot:top>
        <v-toolbar
            class="mx-1"
            flat
        >
          <v-toolbar-title>ОТЧЕТ ПО РЕМОНТУ - ПО ДАТЕ ПРИЕМА</v-toolbar-title>
          <v-divider
              class="mx-4"
              inset
              vertical
          ></v-divider>
          <v-text-field
              class="mx-1"
              v-model="search"
              append-icon="mdi-magnify"
              label="Поиск заявки"
              single-line
              hide-details
          ></v-text-field>
          <v-btn
              class="mx-1">
            Экспорт в XLS
          </v-btn>
        </v-toolbar>
      </template>
      <template v-slot:item.servicePrice="price">
        <td v-text="priceFormat(price)"></td>
      </template>

      <template v-slot:item.componentPrice="price">
        <td v-text="priceFormat(price)"></td>
      </template>
      <template v-slot:item.marginPrice="price">
        <td v-text="priceFormat(price)"></td>
      </template>
      <template v-slot:item.totalPrice="price">
        <td v-text="priceFormat(price)"></td>
      </template>
      <template v-slot:item.repManProfit="price">
        <td v-text="priceFormat(price)"></td>
      </template>
      <template v-slot:item.managerProfit="price">
        <td v-text="priceFormat(price)"></td>
      </template>
      <template v-slot:item.managerProfit="price">
        <td v-text="priceFormat(price)"></td>
      </template>
      <template v-slot:item.directorProfit="price">
        <td v-text="priceFormat(price)"></td>
      </template>

      <template v-slot:item.pos="{ item }">
        <td>{{item.pos.street}} {{item.pos.building}}</td>
      </template>
      <template v-slot:item.creationDate="{ item }">
        <td v-text="dateFormat(item.creationDate)"></td>
      </template>
      <template v-slot:item.issueDate="{ item }">
        <td v-text="dateFormat(item.issueDate)"></td>
      </template>
      <template v-slot:item.status="{ item }">
        <td>{{ item.status.name }} </td>
      </template>

      <template v-slot:body.append="{headers}">
        <tr>
          <td v-for="(header,i) in headers" :key="i">
            <div v-if="header.value == 'status'">
              <b>Итого</b>
            </div>
            <div v-if="header.value == 'servicePrice'">
              <b>{{ priceSumFormat(serviceSum) }}</b>
            </div>
            <div v-if="header.value == 'componentPrice'">
              <b>{{ priceSumFormat(componentSum) }}</b>
            </div>
            <div v-if="header.value == 'marginPrice'">
              <b>{{ priceSumFormat(marginSum) }}</b>
            </div>
            <div v-if="header.value == 'totalPrice'">
              <b>{{ priceSumFormat(totalSum) }}</b>
            </div>
            <div v-if="header.value == 'repManProfit'">
              <b>{{ priceSumFormat(repManSum) }}</b>
            </div>
            <div v-if="header.value == 'managerProfit'">
              <b>{{ priceSumFormat(managerSum) }}</b>
            </div>
            <div v-if="header.value == 'directorProfit'">
              <b>{{ priceSumFormat(directorSum) }}</b>
            </div>
            <div v-else>
              <!-- empty table cells for columns that don't need a sum -->
            </div>
          </td>
        </tr>
      </template>

    </v-data-table>
  </v-container>
</template>

<script>
import axios from 'axios'
import moment from 'moment'

export default {
  name: "ReportRepairOrder",
  data: () => ({
    statuses: [],
    from: [],
    to: [],
    reportType: [],
    repairList: [],
    serviceSum: 0,
    componentSum: 0,
    marginSum: 0,
    totalSum: 0,
    repManSum: 0,
    managerSum: 0,
    directorSum: 0,
    loading: true,
    search: '',
    headers: [
      {text: 'Устройство', value: 'deviceName'},
      {text: 'Дата приема', value: 'creationDate'},
      {text: 'Дата выдачи', value: 'issueDate'},
      {text: 'Статус', value: 'status'},
      {text: 'Стоимость работ', value: 'servicePrice'},
      {text: 'Запчасти', value: 'componentPrice'},
      {text: 'Маржа', value: 'marginPrice'},
      {text: 'Итог', value: 'totalPrice'},
      {text: 'Мастер', value: 'repManProfit'},
      {text: 'Менеджер', value: 'managerProfit'},
      {text: 'Чистая прибыль', value: 'directorProfit'},
      {text: 'Торговая точка', value: 'pos'},
    ]

  }),
  beforeMount() {
    if (!this.$route.query.md) {
      //to avoid unreachable query requests
      this.$router.push('/404')
      return
    }
    if (this.$route.query.md === 'creation') {
      if(!this.$route.query.f || !this.$route.query.t || !this.$route.query.st) {
        this.$router.push('/404')
        return
      }
      this.getReportRepairListCreationDate(this.$route.query.f, this.$route.query.t, this.$route.query.st).then(res => {
        this.repairList = res.data
        this.initPriceSum()
        this.loading = false
      })
    }
    if (this.$route.query.md === 'issue') {
      console.log('Report #2: range in issue date and filtered with statuses')

    }
  },
  methods: {
    async getReportRepairListCreationDate(f, t, st) {
      return axios({
        url: location.origin + '/api/report/repair/creation-date/from=' + f + '&to=' + t + '&st=' + st,
        method: 'GET',
        responseType: 'json'
      })
    },
    async getReportRepairListIssueDate(f,t,st) {
      return axios({
        url: location.origin + '/api/report/repair/creation-date/from=' + f + '&to=' + t + '&st=' + st,
        method: 'GET',
        responseType: 'json'
      })
    },

    dateFormat(date) {
      if(date === '2000-01-01T01:01:00') {
        return 'Не выдан'
      } else {
        return moment(date).format('DD.MM.YYYY')
      }
    },

    initPriceSum() {
      for (let i = 0; i < this.repairList.length; i++) {
        this.serviceSum += this.repairList[i].servicePrice
        this.componentSum += this.repairList[i].componentPrice
        this.marginSum += this.repairList[i].marginPrice
        this.totalSum += this.repairList[i].totalPrice
        this.repManSum += this.repairList[i].repManProfit
        this.managerSum += this.repairList[i].managerProfit
        this.directorSum += this.repairList[i].directorProfit
      }
    },

    priceFormat(price) {
      let formatter = Intl.NumberFormat('ru-RU', {
        style: 'currency',
        currency: 'RUB',
        maximumFractionDigits: 0
      })
      return formatter.format(price.value)
    },

    priceSumFormat(price) {
      let formatter = Intl.NumberFormat('ru-RU', {
        style: 'currency',
        currency: 'RUB',
        maximumFractionDigits: 0
      })
      return formatter.format(price)
    }
  }
}
</script>

<style>

</style>