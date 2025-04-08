<template>
  <v-card class="pa-5">
    <v-card-title>Welcome</v-card-title>
    <v-card-text>
      <p>You are now logged in.</p>

      <v-divider class="my-3"></v-divider>

     
    </v-card-text>
    <v-card-actions>
      <v-btn color="error" @click="logout">Logout</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>


export default {
  name: 'UserDashboard',
  components: {
   
  },
  methods: {
    logout() {
      this.$emit('logout');
    }
  }
}
</script>
