<template>
  <v-container>
    <v-row>
      <v-col
          :cols="6"
          sm="4"
      >
        <v-card
            class="mx-auto"
            @click="showUserManagePage"
        >
          <v-card-text>
            <p class="text-h5 text--primary">
              Пользователи системы
            </p>
            <div class="text--primary">
              Список всех пользователей дилера
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
            @click="showPosManagePage"
          >
          <v-card-text>
            <p class="text-h5 text--primary">
              Торговые точки
            </p>
            <div class="text--primary">
              Список доступных торговых точек дилера, как активных так и закрытых
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
            @click="showPosTypeManagePage"
        >
          <v-card-text>
            <p class="text-h5 text--primary">
              Типы торговых точек
            </p>
            <div class="text--primary">
              Список типов торговых точек - франшизы (Мегафон, Т2, МТС и т.д.)
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>

import {mapState} from "vuex";

export default {
  name: 'AdminPanel',
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
    showUserManagePage() {
      if(this.isAdmin) {
        this.$router.push('/adm/users')
      }else {
        this.$router.push('/404')
      }
    },

    showPosManagePage() {
      if(this.isAdmin) {
        this.$router.push('/adm/pos')
      }else {
        this.$router.push('/404')
      }
    },

    showPosTypeManagePage() {
      if(this.isAdmin) {
        this.$router.push('/adm/pos-types')
      }else {
        this.$router.push('/404')
      }
    }
  }
}


</script>

<style>

</style>