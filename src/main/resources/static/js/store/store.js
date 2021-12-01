import Vue from 'vue'
import 'es6-promise/auto' //to support old browsers
import Vuex from 'vuex'
import nomenclatureApi from "../api/nomList";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        nomList: frontendData.nomList,
        profile: frontendData.profile
    },
    getters: {
        sortedNomList: state => {
            return (state.nomList || []).sort((a, b) => -(a.id - b.id))
        }
    },
    mutations: {
        addNomenclatureMutation(state, nomenclature) {
            state.nomList = [
                ...state.nomList,
                nomenclature
            ]
        },
        updateNomenclatureMutation(state, nomenclature) {
            const index = state.nomList.findIndex(item => item.id === nomenclature.id)
            state.nomList = [
                ...state.nomList.slice(0, index),
                nomenclature,
                ...state.nomList.slice(index + 1)
            ]
        },
        removeNomenclatureMutation(state, nomenclature) {
            const index = state.nomList.findIndex(item => item.id === nomenclature.id)
            if (index > -1) {
                state.nomList = [
                    ...state.nomList.slice(0, index),
                    ...state.nomList.slice(index + 1)
                ]
            }
        }
    },
    actions: {
        async addNomenclatureAction({commit}, nomenclature) {
            const result = await nomenclatureApi.add(nomenclature)
            const data = await result.json()
            const index = this.state.nomList.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updateNomenclatureMutation', nomenclature)
            } else {
                commit('addNomenclatureMutation', nomenclature)
            }
        },
        async updateNomenclatureAction({commit}, nomenclature) {
            const result = await nomenclatureApi.update(nomenclature)
            const data = await result.json()
            commit('updateNomenclatureMutation', data)
        },
        async removeNomenclatureAction({commit}, nomenclature) {
            const result = await nomenclatureApi.remove(nomenclature.id)
            if (result.ok) {
                commit('removeNomenclatureMutation', nomenclature)
            }
        }
    }
})
