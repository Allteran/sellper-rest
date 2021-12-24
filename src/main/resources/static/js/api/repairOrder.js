import Vue from 'vue'

const orderList = Vue.resource('/api/repair/order{id}')

export default {
    get: () => orderList.get(),
    add: order => orderList.save({}, order),
    update: order => orderList.update({id: order.id}, order),
    remove: id => orderList.remove({id: id})
}