<template>
    <v-app-bar app color="primary" dark>
      <v-toolbar-title href="">Sample App</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text @click="$router.push({ name: 'Home' })">Home</v-btn>
      <v-btn text @click="$router.push({ name: 'Login' })">Login</v-btn>
      
    </v-app-bar>
  </template>
  
  <script>
  export default {
    name: 'NavBar'
  };
  </script>
  