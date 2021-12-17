import Vue from 'vue'

const posTypeList = Vue.resource('/api/pos-type/{id}')

export default {
    get: () => posTypeList.get(),
}