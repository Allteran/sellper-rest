import Vue from 'vue'

const nomList = Vue.resource('/nomenclature/{id}')

export default {
    add: nomenclature => nomList.save({}, nomenclature),
    update: nomenclature => nomList.update({id: nomenclature.id}, nomenclature),
    remove: id => nomList.remove({id: id})
}