import Vue from 'vue'
import 'es6-promise/auto' //to support old browsers
import Vuex from 'vuex'
import nomenclatureApi from "../api/nomList"
import userApi from "../api/user";
import posTypeApi from "../api/posType"
import posApi from '../api/pointOfSales'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        nomList: frontendData.nomList,
        profile: frontendData.profile,
        roles: frontendData.roles,
        users: [],
        posTypes:[],
        posList:[],
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
            state.profile = null
            state.profile = user
        },

        getAllUsersMutation(state, list) {
            state.users = list
        },

        updateUserMutation(state, user) {
            const index = state.users.findIndex(item => item.id === user.id)
            if(index >-1) {
                state.users = [
                    ...state.users.slice(0, index),
                    user,
                    ...state.users.slice(index +1)
                ]
            }
        },

        getAllPOSTypesMutation(state, list) {
            state.posTypes = list
        },

        addPOSTypeMutation(state, type) {
            state.posTypes = [
                ...state.posTypes,
                type
            ]
        },

        updatePOSTypeMutation(state, type) {
            const index = state.posTypes.findIndex(item => item.id === type.id)
            if(index>-1) {
                state.posTypes = [
                    ...state.posTypes.slice(0, index),
                    type,
                    ...state.posTypes.slice(index+1)
                ]
            }
        },

        removePOSTypeMutation(state, type) {
            const index = state.posTypes.findIndex(item => item.id === type.id)
            if(index > -1) {
                state.posTypes = [
                    ...state.posTypes.slice(0, index),
                    ...state.posTypes.slice(index + 1)
                ]
            }
        },

        getAllPOSMutation(state, list) {
            state.posList = list
        },

        addPOSMutation(state, pos) {
            state.posList = [
                ...state.posList,
                pos
            ]
        },

        updatePOSMutation(state, pos) {
            const index = state.posList.findIndex(item => item.id === pos.id)
            if(index>-1) {
                state.posList = [
                    ...state.posList.slice(0, index),
                    pos,
                    ...state.posList.slice(index+1)
                ]
            }
        },

        removePOSMutation(state, pos) {
            const index = state.posList.findIndex(item => item.id === pos.id)
            if(index > -1) {
                state.posList = [
                    ...state.posList.slice(0, index),
                    ...state.posList.slice(index + 1)
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
        },
        async updateProfileAction({commit}, user) {
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
        },
        async updateUserAction({commit}, user) {
            const result = await userApi.update(user)
            const data = await result.json()
            commit('updateUserMutation', data)
        },
        async getAllPOSTypesActions({commit}) {
            const result = await posTypeApi.get()
            const data = await result.json()
            commit('getAllPOSTypesMutation',data)
        },
        async addPOSTypeAction({commit}, type) {
            const result = await posTypeApi.add(type)
            const data = result.json()
            const index = this.state.posTypes.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updatePOSTypeMutation', data)
            } else {
                commit('addPOSTypeMutation', data)
            }
        },
        async updatePOSTypeAction({commit}, type) {
            const result = await posTypeApi.update(type)
            const data = await result.json()
            commit('updatePOSTypeMutation', data)
        },
        async removePOSTypeAction({commit}, type) {
            const result = await posTypeApi.remove(type.id)
            const data = await result.json()
            if(result.ok) {
                commit('removePOSTypeMutation', data)
            }
        },

        async getAllPOSAction({commit}) {
            const result = await posApi.get()
            const data = await result.json()
            commit('getAllPOSMutation',data)
        },
        async addPOSAction({commit}, pos) {
            const result = await posApi.add(pos)
            const data = result.json()
            const index = this.state.posList.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updatePOSMutation', data)
            } else {
                commit('addPOSMutation', data)
            }
        },
        async updatePOSAction({commit}, pos) {
            const result = await posApi.update(pos)
            const data = await result.json()
            commit('updatePOSMutation', data)
        },
        async removePOSAction({commit}, pos) {
            const result = await posApi.remove(pos.id)
            const data = await result.json()
            if(result.ok) {
                commit('removePOSMutation', data)
            }
        }
    }
})
