<template>
  <v-form ref="form" v-model="valid" lazy-validation>
    <v-container>
      <v-row>
        <v-col
            cols="12"
            md="6"
        >
          <v-text-field
              v-model="profile.firstName"
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
              v-model="profile.lastName"
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
              v-model="profile.phone"
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
              v-model="dialog"
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
                      <v-text-field
                          label="Текущий пароль"
                          type="password"
                          v-model="currentPassword"
                          :rules="passwordRules"
                          required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                          label="Новый пароль"
                          type="password"
                          v-model="newPassword"
                          :rules="passwordRules.concat(passwordConfirmationCheck)"
                          required
                      ></v-text-field>
                    </v-col>
                    <v-col cols="12">
                      <v-text-field
                          label="Подтверждение пароля"
                          type="password"
                          v-model="newPasswordConfirm"
                          :rules="passwordRules.concat(passwordConfirmationCheck)"
                          required
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
                <small>* - Поля обязательные к заполнению</small>
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
    </v-container>
    <v-btn
        title
        color="primary"
        @click="updateProfile"
    >
      Сохранить изменения
    </v-btn>
  </v-form>
</template>

<script>
import {mapState} from "vuex"
import {mapActions} from "vuex/dist/vuex.esm.browser";

export default {
  name: 'ProfileEdit',
  computed: {
    passwordConfirmationCheck() {
      return this.newPassword === this.newPasswordConfirm || 'Пароли не совпадают'
    },
    ...mapState(['profile']),
  },
  data: () => ({
    dialog: false,
    valid: false,
    errors: false,
    currentPassword:'',
    newPassword: '',
    newPasswordConfirm: '',
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
    roles:[],
  }),
  mounted() {
    console.log(this);
  },
  methods: {
    ...mapActions(['updateProfileAction']),

    updateProfile() {
      this.validate()
      console.log('updateProfile...')
      console.log('valid = ', this.valid)
      console.log('profile = ', this.profile)
      if(this.valid) {
        this.updateProfileAction(this.profile)
      }
    },

    saveChangePassword() {
      this.validate()
      if(this.valid) {
        this.dialog = false
      }
    },
    cancelChangePassword() {
      this.currentPassword = ''
      this.newPassword = ''
      this.newPasswordConfirm = ''
      this.dialog = false
    },
    validate() {
      this.valid = this.$refs.form.validate()
    }
  }
}

</script>

<style>

</style>