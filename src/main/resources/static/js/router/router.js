import Vue from 'vue'
import VueRouter from 'vue-router'
import NomList from 'components/nomenclature/NomList.vue'
import Profile from 'pages/Profile.vue'
import ProfileEdit from 'pages/ProfileEdit.vue'
import NotFound from 'pages/NotFound.vue'
import UserList from 'pages/admin/UserList.vue'
import UserEdit from 'pages/admin/UserEdit.vue'

Vue.use(VueRouter)

const routes = [
    {path: '/', component: NomList},
    {path: '/profile', component: Profile},
    {path:'/profileEdit', component: ProfileEdit},
    {path:'/adm/users', component: UserList},
    {name: 'user-edit', path:'/adm/user/:id', component: UserEdit},
    {path: '/404', component: NotFound},
    {path: '*', redirect: '/404'}
]

export default new VueRouter({
    mode: 'history',
    routes: routes,
})