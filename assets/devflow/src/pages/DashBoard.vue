
<template>
    <BaseLayout>
      <div class="container py-5 text-white">
        <h2 class="mb-4">📊 Dashboard</h2>
  
        <div class="row g-4">
          <div class="col-md-4">
            <div class="card bg-secondary shadow-sm">
              <div class="card-body">
                <h5 class="card-title">📝 Total Issues</h5>
                <p class="card-text display-6">{{ totalIssues }}</p>
              </div>
            </div>
          </div>
  
          <div class="col-md-4">
            <div class="card bg-secondary shadow-sm">
              <div class="card-body">
                <h5 class="card-title">📦 Projects</h5>
                <p class="card-text display-6">{{ totalProjects }}</p>
              </div>
            </div>
          </div>
  
          <div class="col-md-4">
            <div class="card bg-secondary shadow-sm">
              <div class="card-body">
                <h5 class="card-title">💬 Comments</h5>
                <p class="card-text display-6">{{ totalComments }}</p>
              </div>
            </div>
          </div>
        </div>
  
        <div class="mt-5">
          <h4 class="mb-3">Quick Actions</h4>
          <router-link to="/projects" class="btn btn-outline-light me-2">View Projects</router-link>
          <router-link to="/issues" class="btn btn-outline-light me-2">View Issues</router-link>
          <router-link to="/create" class="btn btn-success">+ Create Issue</router-link>
        </div>
      </div>
    </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { computed, onMounted, ref } from 'vue'
  import BaseLayout from '../layouts/BaseLayout.vue'
  import { useProjectStore } from '../stores/project'
  // You’ll replace these with real API values later
  const projectStore = useProjectStore()
  onMounted(async ()=>{
    await projectStore.fetchProjects();
  })
  const totalProjects = computed(() => projectStore.projects.length);
  
  const totalIssues = ref(12)
  
  
  const totalComments = ref(27)
  </script>
  