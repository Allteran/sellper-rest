import Vue from 'vue'

const typeList = Vue.resource('/api/repair/device-type/{id}')

export default {
    get: () => typeList.get(),
    add: type => typeList.save({}, type),
    update: type => typeList.update({id: type.id}, type),
    remove: id => typeList.remove({id: id})
}