<template>
    <BaseLayout>
      <div class="container py-5 text-white">
        <h2 class="mb-4">🌍 Public Projects</h2>
  
        <div v-if="publicProjectStore.loading">Loading public projects...</div>
        <div v-else-if="filteredProjects.length === 0">
          <p>No public projects available.</p>
        </div>
        <div v-else class="row g-4">
          <div
            v-for="project in filteredProjects"
            :key="project.id"
            class="col-md-6"
          >
            <div class="card bg-dark border-light shadow-sm h-100">
              <div class="card-body">
                <h5 class="card-title">{{ project.name }}</h5>
                <p class="card-text">{{ project.description }}</p>
                <router-link :to="`/projects/${project.id}`" class="btn btn-outline-light">
                  View Details
                </router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { computed, onMounted } from 'vue'
  import { usePublicProjectStore } from '../../stores/publicProject'
  import { useAuthStore } from '../../stores/auth'
  import BaseLayout from '../../layouts/BaseLayout.vue'
  
  const publicProjectStore = usePublicProjectStore()
  const authStore = useAuthStore()
  


  onMounted(() => {
    publicProjectStore.fetchProjects()
  })
  
  const filteredProjects = computed(() => {
  const currentUsername = authStore.user?.username
  return publicProjectStore.publicProjects.filter(
    (project) => project.owner.username !== currentUsername
  )
})


  
  </script>
  