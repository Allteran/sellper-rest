import Vue from 'vue'

const orderList = Vue.resource('/api/repair/order/{id}')
const acceptanceCertificate = Vue.resource('/api/repair/order/new/generate/acceptance_cert')
const repairCertificate = Vue.resource('/api/repair/order/generate/repair_cert')

export default {
    get: () => orderList.get(),
    add: order => orderList.save({}, order),
    update: order => orderList.update({id: order.id}, order),
    remove: id => orderList.remove({id: id}),
    getAcceptanceCertificate: order => acceptanceCertificate.save(order),
    generateRepairCertificate: order => repairCertificate.save(order)
}