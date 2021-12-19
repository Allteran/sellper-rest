import Vue from 'vue'

const posList = Vue.resource('/api/pos/{id}')

export default {
    get: () => posList.get(),
    add: pos => posList.save({}, pos),
    update: pos => posList.update({id: pos.id}, pos),
    remove: id => posList.remove({id: id})
}