import Vue from 'vue'
import 'es6-promise/auto' //to support old browsers
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        nomList: frontendData.nomList,
        profile: frontendData.profile
    },
    getters: {
        sortedNomList: state => {
            return state.nomList.sort((a, b) => -(a.id - b.id))
        }
    },
    mutations: {
        increment(state) {
            state.count++
        }
    }
})
