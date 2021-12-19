<template>
  <v-form ref="form" v-model="valid" lazy-validation>

    <v-data-table
        :headers="headers"
        :items="posList"
        :items-per-page="15"
        @click:row="clickRow"
        class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar
            flat
        >
          <v-toolbar-title>Торговые точки</v-toolbar-title>
          <v-divider
              class="mx-4"
              inset
              vertical
          ></v-divider>
          <v-spacer></v-spacer>
          <v-dialog
              v-model="dialog"
              max-width="500px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="primary"
                  dark
                  class="mb-2"
                  v-bind="attrs"
                  v-on="on"
              >
                Создать
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col
                        cols="12"
                        sm="12"
                        md="12"
                    >
                      <v-text-field
                          v-model="editedItem.id"
                          label="ID"
                          :rules="idRules"
                          type="number"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col
                        cols="12"
                        sm="6"
                        md="6"
                    >
                      <v-text-field
                          v-model="editedItem.city"
                          label="Город"
                          :rules="inputFieldRules"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="6"
                    >
                      <v-text-field
                          v-model="editedItem.street"
                          label="Улица"
                          :rules="inputFieldRules"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col
                        cols="12"
                        sm="6"
                        md="6"
                    >
                      <v-text-field
                          v-model="editedItem.building"
                          label="Дом"
                          :rules="inputFieldRules"
                      ></v-text-field>
                    </v-col>
                    <v-col
                        cols="12"
                        sm="6"
                        md="6"
                    >
                      <v-select
                          v-model="selectedType"
                          :items="posTypes"
                          item-text="name"
                          item-value="id"
                          label="Тип торговой точки"
                          persistent-hint
                          return-object
                          single-line
                      ></v-select>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                    color="blue darken-1"
                    text
                    @click="close"
                >
                  Отмена
                </v-btn>
                <v-btn
                    color="blue darken-1"
                    text
                    @click="save"
                >
                  Сохранить
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon
            small
            class="mr-2"
            @click="editItem(item)"
        >
          mdi-pencil
        </v-icon>
      </template>
    </v-data-table>
  </v-form>
</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'POSList',
  data: () => ({
    headers: [
      {text: 'ID', align: 'start', sortable: false, value: 'id'},
      {text: 'Город', value: 'city'},
      {text: 'Улица', value: 'street'},
      {text: 'Дом', value: 'building'},
      {text: 'Ритейл', value: 'type'},
      {text: 'Действия', value: 'actions', sortable: false},
    ],
    dialog: false,
    valid: false,
    editedIndex: -1,
    editedItem: {
      id: '',
      city:'',
      street:'',
      building:'',
      type: ''
    },
    defaultItem: {
      id: '',
      city:'',
      street:'',
      building:'',
      type: ''
    },
    selectedType: {
      name: 'Мегафон',
      id: '4'
    },
    idRules: [
      v => !!v || 'Поле не может быть пустым',
      v => /\d/.test(v) || 'ID должен содержать только цифры',
    ],
    inputFieldRules: [
      v => !!v || 'Поле не может быть пустым',
    ],
  }),
  computed: {
    ...mapState(['profile', 'posList', 'posTypes']),
    formTitle() {
      return this.editedIndex === -1 ? 'Создание' : 'Редактирование'
    },
  },
  beforeMount() {
    for(let i = 0; i<this.profile.roles.length; i++) {
      if (this.profile.roles[i] === 'ADMIN') {
        this.getAllPOSAction()
        this.getAllPOSTypesActions()
        return
      }
    }
    this.$router.push('/404')
  },
  watch: {
    dialog(val) {
      val || this.close()
    },
  },
  methods: {
    ...mapActions(['getAllPOSAction', 'addPOSAction', 'updatePOSAction', 'getAllPOSTypesActions']),

    clickRow(item) {
      // this.$router.push({name: 'user-edit', params: {id: item.id}})
    },
    validate() {
      this.valid = this.$refs.form.validate()
    },
    editItem(item) {
      this.editedIndex = this.posList.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save() {
      this.validate()
      if(this.valid) {
        if (this.editedItem.id) {
          this.updatePOSAction(this.editedItem)
        } else {
          this.addPOSAction(this.editedItem)
        }
        this.close()
      }
    },

  }
}
</script>

<style>

</style>