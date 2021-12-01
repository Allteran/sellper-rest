<template>
  <v-container>
    <v-layout justify-space-around
    >
      <v-flex>
        <div class="title mb-3">Профиль пользователя</div>
        <v-layout row justify-space-between>
          <v-flex class="px-1">
            <v-layout column>
              <v-flex>Имя</v-flex>
              <v-flex>Фамилия</v-flex>
              <v-flex>Номер телефона</v-flex>
              <v-flex>Дилер</v-flex>
            </v-layout>
          </v-flex>
          <v-flex class="px-1">
            <v-layout column>
              <v-flex>{{ profile.firstName }}</v-flex>
              <v-flex>{{ profile.lastName }}</v-flex>
              <v-flex>{{ profile.phone }}</v-flex>
              <v-flex>{{ profile.dealerId }}</v-flex>
            </v-layout>
          </v-flex>
        </v-layout>
      </v-flex>
      <v-btn
          elevation="7"
      >
        Редактировать профиль
      </v-btn>
    </v-layout>
  </v-container>
</template>

<script>
import {mapState} from "vuex"

export default {
  name: 'Profile',
  computed: mapState(['profile'])
}
</script>

<style>

</style>