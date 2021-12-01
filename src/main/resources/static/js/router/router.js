import Vue from 'vue'
import VueRouter from 'vue-router'
import NomList from 'components/nomenclature/NomList.vue'
import Profile from 'pages/Profile.vue'

Vue.use(VueRouter)

const routes = [
    {path: '/', component: NomList},
    {path: '/profile', component: Profile}
]

export default new VueRouter({
    mode: 'history',
    routes: routes
})