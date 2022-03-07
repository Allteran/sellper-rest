<template>
  <v-form ref="form" v-model="valid" lazy-validation>
    <v-container>
      <v-row>
        <v-col
            cols="12"
            md="6"
        >
          <v-text-field
              v-model="user.firstName"
              :rules="nameRules"
              :counter="15"
              label="Имя"
              required
          ></v-text-field>
        </v-col>

        <v-col
            cols="12"
            md="6"
        >
          <v-text-field
              v-model="user.lastName"
              :rules="nameRules"
              :counter="15"
              label="Фамилия"
              required
          ></v-text-field>
        </v-col>

      </v-row>

      <v-row>
        <v-col
            cols="12"
            md="6"
        >
          <v-text-field
              v-model="user.phone"
              :rules="phoneRules"
              label="Номер телефона"
              placeholder="79XXXXXXXXX"
              :counter="11"
              required
          ></v-text-field>
        </v-col>
        <v-col
            cols="12"
            md="6"
        >
          <v-dialog
              v-model="newPasswordDialog"
              persistent
              max-width="600px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="secondary"
                  dark
                  v-bind="attrs"
                  v-on="on"
              >
                Сменить пароль
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="text-h5">Смена пароля</span>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12">
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                          label="Новый пароль"
                          type="password"
                          v-model="newPassword"
                          :rules="passwordRules"
                          required
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
                    @click="cancelChangePassword"
                >
                  Отмена
                </v-btn>
                <v-btn
                    color="blue darken-1"
                    text
                    @click="saveChangePassword"
                >
                  Сохранить
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>

        </v-col>
      </v-row>
      <v-row>
        <v-col
            cols="12"
            md="6"
        >
          Роли пользователя
          <v-layout row wrap>
            <v-flex
                v-for="role in roles"
                :key="role"
            >
              <v-checkbox
                  type="checkbox"
                  v-model="user.roles"
                  :label="role"
                  :value="role"
              />
            </v-flex>
          </v-layout>
        </v-col>
        <v-col
            cols="12"
            md="6"
        >
          Признак активности
          <v-layout row wrap>
            <v-radio-group
                v-model="user.active"
                row
            >
              <v-radio
                  label="Активен"
                  :value="true"
              ></v-radio>
              <v-radio
                  label="Не активен"
                  :value="false"
              ></v-radio>
            </v-radio-group>
          </v-layout>
        </v-col>
      </v-row>
    </v-container>
    <v-btn
        title
        color="primary"
        @click="updateUser"
    >
      Сохранить изменения
    </v-btn>

    <v-row justify="center">
      <v-dialog
          v-model="notificationDialog"
          max-width="600"
      >
        <v-card>
          <v-card-title class="text-h5">
            {{ notificationTitle }}
          </v-card-title>

          <v-card-text>
            {{ notificationMessage }}
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>


            <v-btn
                color="green darken-1"
                text
                @click="closeNotificationDialog"
            >
              Ok
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>

  </v-form>

</template>

<script>
import {mapState} from "vuex"
import {mapActions} from "vuex"

export default {
  name: 'UserEdit',
  data: () => ({
    newPassword:'',
    newPasswordDialog: false,
    notificationDialog: false,
    notificationTitle:'',
    notificationMessage: '',
    valid: false,
    user: [],
    nameRules: [
      v => !!v || 'Поле не может быть пустым',
      v => v.length <= 15 || 'Некорректно введенное имя',
    ],
    phoneRules: [
      v => !!v || 'Номер телефона не может быть пустым',
      v => /^[\d]{11}$/.test(v) || '7XXXXXXXXXX',
    ],
    passwordRules: [
      v => !!v || 'Поле не может быть пустым',
      v => v.length >=8 || 'Пароль слишком короткий',
    ],
  }),
  computed: {
    ...mapState(['users', 'roles']),
  },
  beforeMount() {
    if(this.profile.roles.indexOf('ADMIN') !== -1) {
      this.user = this.users.find(u => u.id === this.$route.params.id)
    } else {
      this.$router.push('/404')
    }
  },
  methods: {
    ...mapActions(['updateUserAction']),
    validate() {
      this.valid = this.$refs.form.validate()
    },
    updateUser() {
      this.validate()
      if(this.valid) {
        this.updateUserAction(this.user)
        this.showUserListPage()
      }
    },
    cancelChangePassword() {
      this.newPassword = ''
      this.newPasswordDialog = false
    },
    saveChangePassword() {
      this.validate()
      if (this.valid) {
        this.user.newPassword = this.newPassword
        this.newPasswordDialog = false
        this.updateUserAction(this.user)

        this.notificationTitle = 'Пароль пользователя был изменен'
        this.notificationMessage = 'Для вступления изменений в силу пользователю необходимо перезайти в систему'
        this.notificationDialog = true

      }
    },
    closeNotificationDialog() {
      this.notificationDialog = false
      this.notificationMessage = ''
      this.notificationTitle=''
    },

    showUserListPage() {
      this.$router.push('/adm/users')
    }
  }

}
</script>

<style>

</style>