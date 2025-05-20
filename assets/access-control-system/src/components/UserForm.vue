<template>
  <v-container>
    <v-row>
      <v-col>
        <h2>Register</h2>
      </v-col>
    </v-row>

    <v-form @submit.prevent="registerUser">
      <v-row>
        <v-col>
          <v-text-field
            v-model="username"
            label="Username"
            :rules="[rules.required]"
            outlined
            required
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="password"
            label="Password"
            type="password"
            :rules="[rules.required]"
            outlined
            required
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="fullName"
            label="Full Name"
            :rules="[rules.required]"
            outlined
            required
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
            v-model="role"
            label="Role"
            :rules="[rules.required]"
            outlined
            required
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-btn color="primary" type="submit">
            Register
          </v-btn>
        </v-col>
      </v-row>

      <v-alert type="error" v-if="error" dense outlined class="mt-3">
        {{ error }}
      </v-alert>
    </v-form>
  </v-container>
</template>

<script>
export default {
  name: 'UserForm',
  data() {
    return {
      username: '',
      password: '',
      fullName: '',
      role: '',
      error: '',
      rules: {
        required: v => !!v || 'Required',
      }
    };
  },
  methods: {
    async registerUser() {
      this.error = '';

      try {
        const res = await fetch('/auth/register', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            username: this.username,
            password: this.password,
            fullName: this.fullName,
            roles: [this.role]  
          })
        });

        if (!res.ok) {
          this.error = 'Registration failed';
          return;
        }

        const data = await res.json();
        console.log('User registered:', data);
        this.$router.push({name: 'Login'})
      } catch (err) {
        this.error = 'Something went wrong';
        console.error(err);
      }
    }
  }
};
</script>
