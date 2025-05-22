<template>
  <v-container>
    <NavBar/>
    <v-row class="text-center">
      
      <v-col class="mb-4">
        <h1 class="display-2 font-weight-bold mb-3">
          Access Control 
        </h1>
      <h2 class="display-2 font-weight-bold mb-3">
        A Gemini based ChatBot
      </h2>
        <p class="subheading font-weight-regular">
         Link to project
          <a
            href="https://github.com/jskaran4323/smart-access-control-system.git"
            target="_blank"
          >Github Link</a>
        </p>
      </v-col>
        </v-row>
        <v-row>
          <v-col>
            <v-btn @click="goToLogin">Login</v-btn>
          </v-col>
        </v-row>
        <v-btn @click="goToRegister">Register</v-btn>
         
  </v-container>
</template>

<script>

import NavBar from './NavBar.vue';
  export default {
    name: 'HelloPage',
   components:{
   NavBar
    },


    data: () => ({
      
    }),
    methods:{
      goToRegister(){
       
    this.$router.push({ name: 'Register' });
  },
  goToLogin(){
       
       this.$router.push({ name: 'Login' });
       
       
     }
      
    }
  }
</script>
