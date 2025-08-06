<template>
    <div class="card bg-light p-4 shadow">
      <h2 class="text-lg font-bold mb-3">👥 Project Team</h2>
  
      <div v-if="loading">Loading team...</div>
      <div v-else-if="error" class="text-red-500">{{ error }}</div>
      <div v-else-if="members.length===0" class="text-red-500"> No Team members </div>
      
      <ul v-else>
        <li v-for="member in members" :key="member.id">
          {{ member.fullName }} ({{ member.fullName }}) - <span class="italic">{{ member.userType }}</span>
        </li>
      </ul>
    </div>
  </template>
  
  <script setup lang="ts">
  import { useRoute } from 'vue-router'
  import { storeToRefs } from 'pinia'
  import { useTeamStore } from '../stores/teams'
  
  const route = useRoute()
  const projectId = route.params.projectId as string
  
  const store = useTeamStore()
  store.fetchTeam(projectId)
  
  const { members, loading, error } = storeToRefs(store)
  </script>
  