<template>
  <v-row
      align="center"
      justify="space-around"
  >
    <v-btn depressed>
      Normal
    </v-btn>
    <v-btn
        depressed
        color="primary"
    >
      Primary
    </v-btn>
    <v-btn
        depressed
        color="error"
    >
      Error
    </v-btn>
    <v-btn
        depressed
        disabled
    >
      Disabled
    </v-btn>
  </v-row>
<!--    <v-data-table-->
<!--        :headers="headers"-->
<!--        :items="posTypes"-->
<!--        :items-per-page="15"-->
<!--        @click:row="clickRow"-->
<!--        class="elevation-1"-->
<!--    ></v-data-table>-->
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'POSTypeList',
  data(){
    return {
      headers: [
        {text: 'ID', align: 'start', sortable: false, value: 'id'},
        {text: 'Название', value: 'name'},
      ]
    }
  },
  computed: {
    ...mapState(['profile', 'posTypes']),
  },
  beforeMount() {
    for(let i = 0; i<this.profile.roles.length; i++) {
      if (this.profile.roles[i] === 'ADMIN') {
        this.getAllPOSTypesActions()
        return
      }
    }
    this.$router.push('/404')
  },
  methods: {
    ...mapActions(['getAllPOSTypesActions']),
    clickRow(item) {
      console.log('clicked on row with user')
      console.log('user.id = ', item.id)
      this.$router.push({name: 'user-edit', params: {id: item.id}})
    },

  }
}
</script>

<style>

</style>