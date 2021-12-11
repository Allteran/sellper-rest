<template>
  <v-main>
    <div>Here is gonna be a list of users</div>
    <div v-for="user in users">
      <i>{{ user.phone }}</i>
      <i>{{ user.firstName }}</i>
      <i>{{ user.lastName }}</i>
    </div>
  </v-main>
</template>

<script>
import {mapActions, mapState} from 'vuex'
import userApi from 'api/user'

export default {
  // data:() =>({
  //   users: null
  // }),
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
    ...mapActions(['getAllUsersAction'])
  }
}
</script>

<style>

</style>