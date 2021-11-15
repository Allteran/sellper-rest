<template>
  <div>
    <nom-list :nomList="nomList"/>
  </div>
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
    }
  }
}
</script>

<style>

</style>