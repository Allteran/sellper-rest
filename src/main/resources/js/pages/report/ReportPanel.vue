<template>
  <v-container>
    <v-flex class="mt-4 mb-4">
      <h3>Создание отчетов по необходимым категориям</h3>
    </v-flex>
    <v-row>
      <v-col
          :cols="6"
          sm="4"
      >
        <v-card
            class="mx-auto"
        >
          <v-card-text>
            <p class="text-h5 text--primary">
              Ремонт
            </p>
            <div class="text--primary">
              Отчет по ремонту за определенный период
            </div>
          </v-card-text>
        </v-card>
      </v-col>

      <v-col
          :cols="6"
          sm="4"
      >
        <v-card
            class="mx-auto"
        >
          <v-card-text>
            <p class="text-h5 text--primary">
              KPI сотрудников
            </p>
            <div class="text--primary">
              Подсчет выполнения KPI каждого сотрудника
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>

import {mapState} from 'vuex'

export default {
  name: 'ReportPanel',
  computed: mapState(['profile']),
  data: () => ({
    isAllowed: false,
  }),
  beforeMount() {
    this.isAllowed = (this.profile.roles.indexOf('ADMIN') !== -1 || this.profile.roles.indexOf('MANAGER') !== -1)
    if(this.isAllowed === false) {
      this.$router.push('/404')
    }
  },
  methods: {

  }
}
</script>

<style>

</style>