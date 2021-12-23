<template>

  <v-data-table
      :headers="headers"
      :items="repairStatusList"
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
  name: 'RepairStatus',
  data: () => ({
    headers: [
      {text: 'ID', align: 'start', sortable: false, value: 'id'},
      {text: 'Название', value: 'name'},
      {text: 'Действия', value: 'actions', sortable: false},
    ],
    dialog: false,
    valid: true, //TODO: implement validation of field 'name'
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
    ...mapState(['profile', 'repairStatusList']),
    formTitle() {
      return this.editedIndex === -1 ? 'Создание' : 'Редактирование'
    },
  },
  beforeMount() {
    for(let i = 0; i<this.profile.roles.length; i++) {
      if (this.profile.roles[i] === 'ADMIN') {
        this.getAllRepairStatusAction()
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
    ...mapActions(['getAllRepairStatusAction', 'addRepairStatusAction', 'updateRepairStatusAction']),

    editItem(item) {
      this.editedIndex = this.repairStatusList.indexOf(item)
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
        this.updateRepairStatusAction(this.editedItem)
      } else {
        this.addRepairStatusAction(this.editedItem)
      }
      this.close()
    },

  }
}
</script>

<style>

</style>