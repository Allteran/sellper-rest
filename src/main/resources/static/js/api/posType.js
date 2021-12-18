import Vue from 'vue'

const posTypeList = Vue.resource('/api/pos-type/{id}')

export default {
    get: () => posTypeList.get(),
    add: type => posTypeList.save({}, type),
    update: type => posTypeList.update({id: type.id}, type),
    remove: id => posTypeList.remove({id: id})
}