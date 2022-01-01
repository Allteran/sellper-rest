import Vue from 'vue'

const orderList = Vue.resource('/api/repair/order{id}')
const acceptanceCertificate = Vue.resource('/api/repair/order/new/generate/acceptance_cert')

export default {
    get: () => orderList.get(),
    add: order => orderList.save({}, order),
    update: order => orderList.update({id: order.id}, order),
    remove: id => orderList.remove({id: id}),
    getAcceptanceCertificate: order => acceptanceCertificate.save(order),
}