<template>
  <v-data-table
      :headers="headers"
      :items="sortedNomList"
      class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar
          flat
      >
        <v-toolbar-title>Товары</v-toolbar-title>
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
              Создать товар
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
                      sm="6"
                      md="6"
                  >
                    <v-text-field
                        v-model="editedItem.name"
                        label="Наименование"
                    ></v-text-field>
                  </v-col>
                  <v-col
                      cols="12"
                      sm="6"
                      md="6"
                  >
                    <v-text-field
                        v-model="editedItem.price"
                        label="Цена"
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
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5">Вы уверены, что хотите удалить товар?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete">Отмена</v-btn>
              <v-btn color="blue darken-1" text @click="deleteItemConfirm">Удалить</v-btn>
              <v-spacer></v-spacer>
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
      <v-icon
          small
          @click="deleteItem(item)"
      >
        mdi-delete
      </v-icon>
    </template>
  </v-data-table>
</template>

<script>
import nomenclatureApi from 'api/nomList'
import {mapGetters, mapState, mapActions} from 'vuex'

export default {
  data: () => ({
    dialog: false,
    dialogDelete: false,
    headers: [
      {
        text: 'Наименование',
        align: 'start',
        sortable: false,
        value: 'name',
      },
      {text: 'Цена', value: 'price'},
      {text: 'Действия', value: 'actions', sortable: false},
    ],
    editedIndex: -1,
    editedItem: {
      name: '',
      price: 0,
    },
    defaultItem: {
      name: '',
      price: 0,
    },
  }),
  computed: {
    ...mapGetters(['sortedNomList']),
    ...mapState(['nomList']),
    formTitle() {
      return this.editedIndex === -1 ? 'Создание' : 'Редактирование'
    }
  },
  watch: {
    dialog(val) {
      val || this.close()
    },
    dialogDelete(val) {
      val || this.closeDelete()
    },
  },
  methods: {
    ...mapActions(['addNomenclatureAction', 'updateNomenclatureAction', 'removeNomenclatureAction']),
    editItem(item) {
      this.editedIndex = this.nomList.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    deleteItem(item) {
      this.editedIndex = this.nomList.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogDelete = true
    },

    deleteItemConfirm() {
      //AFTER REMOVE ACTION FOLLOWS ERROR
      // Resolved [org.springframework.web.method.annotation.MethodArgumentTypeMismatchException: Failed to convert value of type 'java.lang.String' to required type 'com.allteran.sellper.domain.Nomenclature'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [java.lang.Long] for value 'id,54,name,123,group,[object Object],price,123'; nested exception is java.lang.NumberFormatException: For input string: "id,54,name,123,group,[objectObject],price,123"]
      this.removeNomenclatureAction(this.editedItem)
      this.closeDelete()
    },

    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    closeDelete() {
      this.dialogDelete = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save() {
      if (this.editedItem.id) {
        nomenclatureApi.update(this.editedItem).then(result =>
            result.json().then(data => {
              const index = this.nomList.findIndex(item => item.id === data.id)
              this.nomList.splice(index, 1, data)
            })
        )
      } else {
        nomenclatureApi.add(this.editedItem).then(result =>
            result.json().then(data => {
              const index = this.nomList.findIndex(item => item.id === data.id)
              if (index > -1) {
                this.nomList.splice(index, 1, data)
              } else {
                this.nomList.push(data)
              }
            })
        )
      }
      this.close()
    },
  },
}
</script>

<style>

</style>