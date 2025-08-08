<template>
    <BaseLayout>
      <div class="container py-5 text-white">
        <h2 class="mb-4">👤 My Profile</h2>
        <div v-if="!authStore.user">Loading profile...</div>
        <div class="card bg-dark shadow-sm border-light p-4" style="max-width: 500px;">
            <div class="mb-3">
    <strong>Full Name:</strong>
    <p>{{ authStore.user?.fullName }}</p>
  </div>
  <div class="mb-3">
    <strong>Username:</strong>
    <p>{{ authStore.user?.username }}</p>
  </div>
  <div class="mb-3">
    <strong>User Type:</strong>
    <p>{{ authStore.user?.userType }}</p>
  </div>
  
          <router-link to="/" class="btn btn-outline-light mt-3">← Back to Dashboard</router-link>
        </div>
      </div>
    </BaseLayout>
  </template>
 <script setup lang="ts">
 import { onMounted, watch } from 'vue'
 import { useAuthStore } from '../../stores/auth'
 import BaseLayout from '../../layouts/BaseLayout.vue'
 
 const authStore = useAuthStore()
 
 onMounted(async () => {
   if (!authStore.user && authStore.token) {
     console.log('Calling fetchUser...')
     await authStore.fetchUser()
   }
 })
 
 watch(
   () => authStore.user,
   (user) => {
     if (user) {
       console.log('User fetched:', user)
     }
   },
   { immediate: true }
 )
 
 function formatDate(dateString: string) {
   return new Date(dateString).toLocaleDateString()
 }
 </script>
 

  