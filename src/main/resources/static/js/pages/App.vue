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
      >
        Ремонт
      </v-btn>
      <v-btn text
      >
        Отчеты
      </v-btn>

      <v-btn text
             href="/"
      >
        Товары
      </v-btn>
      <v-btn text
             @click="showUserListPage"
             v-if="isAdmin"
      >
        Пользователи
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
  }),
  beforeMount() {
    for(let i = 0; i<this.profile.roles.length; i++) {
      if (this.profile.roles[i] === 'ADMIN') {
        this.isAdmin = true
        return
      }
    }
    this.isAdmin = false
  },
  methods: {
    showProfilePage() {
      this.$router.push('/profile')
    },
    showUserListPage() {
      if(this.isAdmin) {
        this.$router.push('/adm/users')
      }else {
        this.$router.push('/404')
      }
    }
  }
}
</script>

<style>

</style>