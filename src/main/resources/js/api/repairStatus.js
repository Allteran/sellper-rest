import Vue from 'vue'

const statusList = Vue.resource('/api/repair/status/{id}')

export default {
    get: () => statusList.get(),
    add: status => statusList.save({}, status),
    update: status => statusList.update({id: status.id}, status),
    remove: id => statusList.remove({id: id})
}