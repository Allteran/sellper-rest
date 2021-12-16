<template>
  <v-data-table
      :headers="headers"
      :items="users"
      :items-per-page="15"
      @click:row="clickRow"
      class="elevation-1"
  ></v-data-table>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  data(){
    return {
      headers: [
        {text: 'Телефон', align: 'start', sortable: false, value: 'phone'},
        {text: 'Имя', value: 'firstName'},
        {text: 'Фамилия', value: 'lastName'},
        {text: 'Активен', value: 'active'},
        {text: 'Права', value: 'roles'},
        {text: 'Дата создания', value: 'creationDate'},
      ]
    }
  },
  computed: {
    ...mapState(['profile', 'users']),
  },
  beforeMount() {
    for(let i = 0; i<this.profile.roles.length; i++) {
      if (this.profile.roles[i] === 'ADMIN') {
        this.getAllUsersAction()
        return
      }
    }
    this.$router.push('/404')
  },
  methods: {
    ...mapActions(['getAllUsersAction']),
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