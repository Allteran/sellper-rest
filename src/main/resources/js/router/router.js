import Vue from 'vue'
import VueRouter from 'vue-router'
import NomList from 'nomenclature/NomList.vue'
import Profile from 'pages/Profile.vue'
import ProfileEdit from 'pages/ProfileEdit.vue'
import NotFound from 'pages/NotFound.vue'
import UserList from 'pages/admin/UserList.vue'
import UserEdit from 'pages/admin/UserEdit.vue'
import AdminPanel from 'pages/admin/AdminPanel.vue'
import POSTypeList from 'pages/admin/POSTypeList.vue'
import POSList from 'pages/admin/POSList.vue'
import DeviceType from 'pages/admin/DeviceType.vue'
import RepairStatus from 'pages/admin/RepairStatus.vue'
import OrderList from 'pages/repair/OrderList.vue'
import CreateOrder from 'pages/repair/CreateOrder.vue'
import OrderDetails from 'pages/repair/OrderDetails.vue'
import OrderEdit from 'pages/repair/OrderEdit.vue'
import StartPage from 'pages/StartPage.vue'
import ReportPanel from 'pages/report/ReportPanel.vue'

Vue.use(VueRouter)

const routes = [
    {path: '/', component: StartPage},
    {path: '/profile', component: Profile},
    {path: '/profileEdit', component: ProfileEdit},
    {path: '/adm', component: AdminPanel},
    {path: '/adm/users', component: UserList},
    {path: '/adm/user/:id', name: 'user-edit', component: UserEdit},
    {path: '/adm/pos-types', component: POSTypeList},
    {path: '/adm/pos', component: POSList},
    {path: '/adm/repair/device-type', component: DeviceType},
    {path: '/adm/repair/status', component: RepairStatus},
    {path: '/repair/order', component: OrderList},
    {path: '/repair/order/new', component: CreateOrder},
    {path: '/repair/order/view/:id', name: 'order-details', component: OrderDetails},
    {path: '/repair/order/edit/:id', name: 'order-edit', component: OrderEdit},
    {path: '/report/panel', component: ReportPanel},
    {path: '/404', component: NotFound},
    {path: '*', redirect: '/404'}
]

export default new VueRouter({
    mode: 'history',
    routes: routes,
})