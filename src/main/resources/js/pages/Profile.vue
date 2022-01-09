<template>
  <v-container>
    <v-row>
      <v-col>
        <div class="title">Профиль пользователя</div>
      </v-col>
      <v-col
          class="d-flex justify-end"
      >
        <v-btn
            tile
            @click="profileEditPage"
        >
          <v-icon left>
            mdi-pencil
          </v-icon>
          Редактировать профиль
        </v-btn>
      </v-col>
    </v-row>

    <v-container class="grey lighten-5">
      <v-row no-gutters>
        <v-col
            md="3"
        >
          <v-card
              class="pa-2"
              outlined
              tile
          >
            Имя
          </v-card>
        </v-col>
        <v-col

        >
          <v-card
              class="pa-2"
              outlined
              tile
          >
            {{ profile.firstName }}
          </v-card>
        </v-col>
      </v-row>
      <v-row no-gutters>
        <v-col
            md="3"
        >
          <v-card
              class="pa-2"
              outlined
              tile
          >
            Фамилия
          </v-card>
        </v-col>
        <v-col

        >
          <v-card
              class="pa-2"
              outlined
              tile
          >
            {{ profile.lastName }}
          </v-card>
        </v-col>
      </v-row>
      <v-row no-gutters>
        <v-col
            md="3"
        >
          <v-card
              class="pa-2"
              outlined
              tile
          >
            Номер телефона
          </v-card>
        </v-col>
        <v-col

        >
          <v-card
              class="pa-2"
              outlined
              tile
          >
            {{ profile.phone }}
          </v-card>
        </v-col>
      </v-row>
      <v-row no-gutters>
        <v-col
            md="3"
        >
          <v-card
              class="pa-2"
              outlined
              tile
          >
            Дилер
          </v-card>
        </v-col>
        <v-col

        >
          <v-card
              class="pa-2"
              outlined
              tile
          >
            {{ profile.dealerId }}
          </v-card>
        </v-col>
      </v-row>
      <v-row no-gutters>
        <v-col
            md="3"
        >
          <v-card
              class="pa-2"
              outlined
              tile
          >
            Права
          </v-card>
        </v-col>
        <v-col
        >
            <v-card
                class="pa-2"
                outlined
                tile
            >
              <span
                  v-for="role in roles"
                  :key="role"
              >{{ role }} / </span>
            </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import {mapState} from "vuex"

export default {
  name: 'Profile',
  computed: mapState(['profile']),
  data() {
    return {
      roles: []
    }
  },
  beforeMount() {
    for(let i = 0; i<this.profile.roles.length; i++) {
      if (this.profile.roles[i] === 'ADMIN') {
        this.roles.push('Администратор')
      } else if (this.profile.roles[i] === 'MANAGER') {
        this.roles.push('Менеджер')
      } else if (this.profile.roles[i] === 'USER') {
        this.roles.push('Продавец')
      }
    }
  },
  methods: {
    profileEditPage() {
      this.$router.push('/profileEdit')
    }
  }
}
</script>

<style>

</style>