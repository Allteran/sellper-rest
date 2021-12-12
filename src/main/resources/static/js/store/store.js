import Vue from 'vue'
import 'es6-promise/auto' //to support old browsers
import Vuex from 'vuex'
import nomenclatureApi from "../api/nomList";
import userApi from "../api/user";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        nomList: frontendData.nomList,
        profile: frontendData.profile,
        users: []
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
        },
        updateProfileMutation(state, user) {
            console.log('updateProfileMutation...')
            state.profile = null
            state.profile = user
            console.log(state.profile)
        },
        getAllUsersMutation(state, list) {
            state.users = list
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
        },
        async updateProfileAction ({commit}, user) {
            let result
            try{
                result = await userApi.update(user)
            } catch (e) {
                throw new Error('Entered current password is incorrect')
            }

            const data = await result.json()
            commit('updateProfileMutation', data)
        },
        async getAllUsersAction({commit}) {
            const result = await userApi.get()
            const data = await result.json()
            commit('getAllUsersMutation',data)
        }
    }
})
