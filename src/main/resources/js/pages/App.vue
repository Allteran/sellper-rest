<template>
  <v-app>
    <v-app-bar
        elevation="9"
        outlined
        app
    >
      <v-toolbar-title>Sellper</v-toolbar-title>
      <v-btn text
      >
        Личные продажи
      </v-btn>
      <v-btn text
      >
        Выполнение KPI
      </v-btn>
      <v-btn text
             @click="showRepairOrderPage"
      >
        Ремонт
      </v-btn>
      <v-btn text
             @click="showReportPanel"
             v-if="isManager"
      >
        Отчеты
      </v-btn>

      <v-btn text
             href="/"
             v-if="isManager"
      >
        Товары
      </v-btn>
      <v-btn text
             @click="showAdminPanel"
             v-if="isAdmin"
      >
        Управление
      </v-btn>

      <v-spacer></v-spacer>
      <v-btn
          text
          v-if="profile"
          @click="showProfilePage"
      >
        {{ profile.firstName }} {{ profile.lastName }}
      </v-btn>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>mdi-exit-to-app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container>
        <router-view></router-view>
      </v-container>
    </v-main>

  </v-app>
</template>

<script>
import {mapState} from 'vuex'

export default {
  computed: mapState(['profile']),
  data: () => ({
    isAdmin: false,
    isManager: false,
  }),
  beforeMount() {
    this.isAdmin = this.profile.roles.indexOf('ADMIN') !== -1
    this.isManager = this.profile.roles.indexOf('MANAGER') !== -1
  },
  methods: {
    showProfilePage() {
      this.$router.push('/profile')
    },

    showAdminPanel(){
      if(this.isAdmin) {
        this.$router.push('/adm')
      }else {
        this.$router.push('/404')
      }
    },
    showRepairOrderPage() {
      this.$router.push('/repair/order')
    },
    showReportPanel() {
      if(this.isManager) {
        this.$router.push('/report/panel')
      } else {
        this.$router.push('/404')
      }
    }
  }
}
</script>

<style>

</style>