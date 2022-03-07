<template>

  <v-data-table
      :headers="headers"
      :items="posTypes"
      :items-per-page="15"
      class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar
          flat
      >
        <v-toolbar-title>Типы торговых точек</v-toolbar-title>
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
                        v-model="editedItem.name"
                        label="Наименование"
                        :rules="nameRules"
                    ></v-text-field>
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

</template>

<script>
import {mapActions, mapState} from 'vuex'

export default {
  name: 'POSTypeList',
  data: () => ({
    headers: [
      {text: 'ID', align: 'start', sortable: false, value: 'id'},
      {text: 'Название', value: 'name'},
      {text: 'Действия', value: 'actions', sortable: false},
    ],
    nameRules: [
      v => !!v || 'Поле не может быть пустым',
    ],
    dialog: false,
    editedIndex: -1,
    editedItem: {
      name: '',
      id: ''
    },
    defaultItem: {
      name: '',
      id: ''
    },
  }),
  computed: {
    ...mapState(['profile', 'posTypes']),
    formTitle() {
      return this.editedIndex === -1 ? 'Создание' : 'Редактирование'
    },
  },
  beforeMount() {
    if(this.profile.roles.indexOf('ADMIN') !== -1) {
      this.getPOSTypeListAction()
    } else {
      this.$router.push('/404')
    }
  },
  watch: {
    dialog(val) {
      val || this.close()
    },
  },
  methods: {
    ...mapActions(['getPOSTypeListAction', 'addPOSTypeAction', 'updatePOSTypeAction']),

    editItem(item) {
      this.editedIndex = this.posTypes.indexOf(item)
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
      if (this.editedItem.id) {
        this.updatePOSTypeAction(this.editedItem)
      } else {
        this.addPOSTypeAction(this.editedItem)
      }
      this.close()
    },

  }
}
</script>

<style>

</style>