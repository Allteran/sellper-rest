<template>
  <v-app>
    <v-app-bar>
      <v-toolbar-title>Sellper</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon href="/logout">
        <v-icon>mdi-exit-to-app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container>
        <nom-list :nomList="nomList"/>
      </v-container>
    </v-main>

  </v-app>
</template>

<script>
import Vue from 'vue'
import NomList from 'components/nomenclature/NomList.vue'

/*
  Global import component
  Next line you should use to connect actual NomList.vue to object NomList and to template 'nom-list'
  You also should import Vue library from 'vue'
 */
Vue.component('nom-list', NomList)

export default {
  comments: {
    NomList
  },
  data() {
    return {
      nomList: frontendData.nomList,
      profile: frontendData.profile
    }
  },
  created() {

  }
}
</script>

<style>

</style>