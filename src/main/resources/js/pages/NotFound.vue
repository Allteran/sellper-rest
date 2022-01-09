<template>
  <v-container>
    <v-layout row wrap>
      <v-flex xs12>
        <v-card height="400" color="transparent" flat>
          <div class="display-3 mt-5">Страница недоступна.</div>
          <div/>
          <div class="grey--text lighten-5">
            Упс...Похоже, что страница, которую Вы ищете недоступна. Что делать? Попробуйте вернуться <a @click="showMainPage">на главную страницу</a>. Вполне возможно это Вам поможет
          </div>
          <div class="paragraph-text mt-2">
            <br/>
            <br/>Если, всё же, это не заимеет эффекта - обратитесь к системному администратору. Мир!
          </div>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'NotFound',
  methods: {
    showMainPage() {
      this.$router.push("/")
    }
  }
}

</script>

<style>

</style>