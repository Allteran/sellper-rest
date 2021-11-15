<template>
  <table class="table">
    <thead>
      <th scope="col">Наименование</th>
      <th scope="col">Цена</th>
    </thead>
    <tbody>
      <tr v-for="nom in nomList" :key="nom.id">
        <td>{{ nom.name }}</td>
        <td>{{ nom.price }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  name: 'nom-list',
  props: ['nomList'],
  created() {
    this.$resource('/nomenclature/{id}').get().then(result =>
        result.json().then(data =>
            data.forEach(nom => this.nomList.push(nom))
        )
    )
  },
  data() {
    return {
      nomList: null
    }
  }
}
</script>

<style>

</style>