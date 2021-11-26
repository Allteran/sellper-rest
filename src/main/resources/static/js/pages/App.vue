<template>
  <v-app>
    <v-app-bar>
      <v-toolbar-title>Sellper</v-toolbar-title>

      <v-spacer></v-spacer>
      <span v-if="profile">{{ profile.firstName }} {{ profile.lastName }}</span>
      <v-btn v-if="profile" icon href="/logout">
        <v-icon>mdi-exit-to-app</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container>
        <nom-list/>
      </v-container>
    </v-main>

  </v-app>
</template>

<script>
import Vue from 'vue'
import NomList from 'components/nomenclature/NomList.vue'
import {mapState} from 'vuex'
// import {addHandler} from "../util/ws";

/*
  Global import component
  Next line you should use to connect actual NomList.vue to object NomList and to template 'nom-list'
  You also should import Vue library from 'vue'
 */
Vue.component('nom-list', NomList)

export default {
  components: {
    NomList
  },
  computed: mapState(['profile'])
  // created() {
  //   addHandler(data => {
  //     if (data.objectType === 'NOMENCLATURE') {
  //       const index = this.nomList.findIndex(item => item.id === data.body.id)
  //
  //       switch (data.eventType) {
  //         case 'CREATE':
  //         case 'UPDATE':
  //           if (index > -1) {
  //             this.nomList.splice(index, 1, data.body)
  //           } else {
  //             this.nomList.push(data.body)
  //           }
  //           break;
  //         case 'REMOVE':
  //           this.nomList.splice(index, 1)
  //           break;
  //         default:
  //           console.error(`Looks like the event type if unknown "${data.eventType}"`)
  //       }
  //     } else {
  //       console.error(`Looks like the object type if unknown "${data.objectType}"`)
  //     }
  //   })
  // }
}
</script>

<style>

</style>