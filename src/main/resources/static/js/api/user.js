import Vue from 'vue'

const users = Vue.resource('/api/user/{id}')

export default {
    add: user => users.save({}, user),
    update: user => users.update({id: user.id}, user),
    remove: id => users.remove({id: id}),
}