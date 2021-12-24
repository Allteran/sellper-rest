<template>

    <v-data-table
        :headers="headers"
        :items="repairOrderList"
        :items-per-page="15"
        @click:row="clickRow"
        class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar
            flat
        >
          <v-toolbar-title>Реестр ремонта</v-toolbar-title>
          <v-divider
              class="mx-4"
              inset
              vertical
          ></v-divider>
        </v-toolbar>
      </template>
    </v-data-table>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'RepairOrderList',
  data: () => ({
    headers: [
      {text: 'ID', align: 'start', sortable: false, value: 'id'},
      {text: 'Устройство', value: 'deviceName'},
      {text: 'Дефект', value: 'defect'},
      {text: 'Дата приема', value: 'creationDate'},
      {text: 'Дата выдачи', value: 'issueDate'},
      {text: 'Статус', value: 'status.name'},
      {text: 'Стоимость', value: 'totalPrice'},
      {text: 'Точка продаж', value: 'pos.street'},
      {text: 'Сотрудник', value: 'author.lastName'}
    ],

  }),
  computed: {
    ...mapState(['profile', 'repairOrderList']),
  },
  methods: {
    ...mapActions(['getPOSListAction', 'addPOSAction', 'updatePOSAction', 'getPOSTypeListAction']),

    clickRow(item) {
      // this.$router.push({name: 'user-edit', params: {id: item.id}})
    },

  }
}
</script>

<style>

</style>