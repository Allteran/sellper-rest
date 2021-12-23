import Vue from 'vue'
import 'es6-promise/auto' //to support old browsers
import Vuex from 'vuex'
import nomenclatureApi from "../api/nomList"
import userApi from "../api/user";
import posTypeApi from "../api/posType"
import posApi from '../api/pointOfSales'
import deviceTypeApi from '../api/repairDeviceType'
import repairStatusApi from '../api/repairStatus'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        nomList: frontendData.nomList,
        profile: frontendData.profile,
        roles: frontendData.roles,
        users: [],
        posTypes:[],
        posList:[],
        deviceTypeList:[],
        repairStatusList:[],
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

        /**
         * Module for User (for current user via Profile page)
         */
        updateProfileMutation(state, user) {
            state.profile = null
            state.profile = user
        },

        /**
         * Module for User (via admin page)
         */
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

        /**
         * Module for POSType entity
         */
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

        /**
         * Module for PointOfSales entity
         */
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
        },

        /**
         * Module for RepairDeviceType entity
         */
        getAllDeviceTypeMutation(state, list) {
            state.deviceTypeList = list
        },
        addDeviceTypeMutation(state, type) {
            state.deviceTypeList = [
                ...state.deviceTypeList,
                type
            ]
        },
        updateDeviceTypeMutation(state, type) {
            const index = state.deviceTypeList.findIndex(item => item.id === type.id)
            if(index>-1) {
                state.deviceTypeList = [
                    ...state.deviceTypeList.slice(0, index),
                    type,
                    ...state.deviceTypeList.slice(index+1)
                ]
            }
        },
        removeDeviceTypeMutation(state, type) {
            const index = state.deviceTypeList.findIndex(item => item.id === type.id)
            if(index > -1) {
                state.deviceTypeList = [
                    ...state.deviceTypeList.slice(0, index),
                    ...state.deviceTypeList.slice(index + 1)
                ]
            }
        },

        /**
         * Module for RepairStatus entity
         */
        getAllRepairStatusMutation(state, list) {
            state.repairStatusList = list
        },
        addRepairStatusMutation(state, status) {
            state.repairStatusList = [
                ...state.repairStatusList,
                status
            ]
        },
        updateRepairStatusMutation(state, status) {
            const index = state.repairStatusList.findIndex(item => item.id === status.id)
            if(index>-1) {
                state.repairStatusList = [
                    ...state.repairStatusList.slice(0, index),
                    status,
                    ...state.repairStatusList.slice(index+1)
                ]
            }
        },
        removeRepairStatusMutation(state, status) {
            const index = state.repairStatusList.findIndex(item => item.id === status.id)
            if(index > -1) {
                state.repairStatusList = [
                    ...state.repairStatusList.slice(0, index),
                    ...state.repairStatusList.slice(index + 1)
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

        /**
         * Module for User (for current user via Profile page)
         */
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

        /**
         * Module for User (via admin page)
         */
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

        /**
         * Module for POSType entity
         */
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
                commit('updatePOSTypeMutation', type)
            } else {
                commit('addPOSTypeMutation', type)
            }
        },
        async updatePOSTypeAction({commit}, type) {
            const result = await posTypeApi.update(type)
            const data = await result.json()
            commit('updatePOSTypeMutation', data)
        },
        async removePOSTypeAction({commit}, type) {
            const result = await posTypeApi.remove(type.id)
            if(result.ok) {
                commit('removePOSTypeMutation', type)
            }
        },

        /**
         * Module for PointOfSales entity
         */
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
                commit('updatePOSMutation', pos)
            } else {
                commit('addPOSMutation', pos)
            }
        },
        async updatePOSAction({commit}, pos) {
            const result = await posApi.update(pos)
            const data = await result.json()
            commit('updatePOSMutation', data)
        },
        async removePOSAction({commit}, pos) {
            const result = await posApi.remove(pos.id)
            if(result.ok) {
                commit('removePOSMutation', pos)
            }
        },

        /**
         * Module for RepairDeviceType entity
         */
        async getAllDeviceTypeAction({commit}) {
            const result = await deviceTypeApi.get()
            const data = await result.json()
            commit('getAllDeviceTypeMutation',data)
        },
        async addDeviceTypeAction({commit}, type) {
            const result = await deviceTypeApi.add(type)
            const data = result.json()
            const index = this.state.deviceTypeList.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updateDeviceTypeMutation', type)
            } else {
                commit('addDeviceTypeMutation', type)
            }
        },
        async updateDeviceTypeAction({commit}, type) {
            const result = await deviceTypeApi.update(type)
            const data = await result.json()
            commit('updateDeviceTypeMutation', data)
        },
        async removeDeviceTypeAction({commit}, type) {
            const result = await deviceTypeApi.remove(type.id)
            const data = await result.json()
            if(result.ok) {
                commit('removeDeviceTypeMutation', type)
            }
        },

        /**
         * Module for RepairStatus entity
         */
        async getAllRepairStatusAction({commit}) {
            const result = await repairStatusApi.get()
            const data = await result.json()
            commit('getAllRepairStatusMutation',data)
        },
        async addRepairStatusAction({commit}, status) {
            const result = await repairStatusApi.add(status)
            const data = result.json()
            const index = this.state.repairStatusList.findIndex(item => item.id === data.id)
            if (index > -1) {
                commit('updateRepairStatusMutation', status)
            } else {
                commit('addRepairStatusMutation', status)
            }
        },
        async updateRepairStatusAction({commit}, status) {
            const result = await repairStatusApi.update(status)
            const data = await result.json()
            commit('updateRepairStatusMutation', data)
        },
        async removeRepairStatusAction({commit}, status) {
            const result = await repairStatusApi.remove(status.id)
            if(result.ok) {
                commit('removeDeviceTypeMutation', status)
            }
        },
    }
})
