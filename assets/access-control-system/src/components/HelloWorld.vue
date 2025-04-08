<template>
  <v-container>
    <v-row class="text-center">
      
      <v-col class="mb-4">
        <h1 class="display-2 font-weight-bold mb-3">
          Welcome to Sample App
        </h1>

        <p class="subheading font-weight-regular">
         this will something related to any real world project
          <a
            href="https://github.com/jskaran4323/smart-access-control-system.git"
            target="_blank"
          >Github Link</a>
        </p>
      </v-col>
        </v-row>
        <v-btn >Login</v-btn>
        <v-btn @click="goToRegister">Register</v-btn>

  </v-container>
</template>

<script>
  export default {
    name: 'HelloWorld',

    data: () => ({
      
    }),
    methods:{
      goToRegister(){
       
    this.$router.push({ name: 'Register' });
  }
      
    }
  }
</script>
