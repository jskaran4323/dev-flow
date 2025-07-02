<template>
    <BaseLayout>
      <div class="container py-5 text-white">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h2>📦 My Projects</h2>
          <router-link to="/projects/new" class="btn btn-success">+ New Project</router-link>
        </div>
  
        <div v-if="projects.length === 0" class="alert alert-secondary text-muted">
          You don’t have any projects yet. Click “New Project” to create one.
        </div>
  
        <div class="row g-4">
          <div
            class="col-md-6 col-lg-4"
            v-for="project in projects"
            :key="project.id"
          >
            <div class="card bg-secondary text-white shadow-sm h-100 border-light">
              <div class="card-body">
                <h5 class="card-title fw-bold">{{ project.title }}</h5>
                <p class="card-text small text-light-emphasis">{{ project.description }}</p>
                <span class="badge bg-light text-dark">{{ project.status }}</span>
              </div>
              <div class="card-footer bg-transparent d-flex justify-content-between border-top border-light">
                <router-link :to="`/projects/${project.id}/edit`" class="btn btn-sm btn-outline-light">Edit</router-link>
                <button class="btn btn-sm btn-outline-danger" @click="deleteProject(project.id)">Delete</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  import BaseLayout from '../../layouts/BaseLayout.vue'
  
  // Dummy data (replace with API later)
  const projects = ref([
    { id: 1, title: 'Backend API', description: 'Spring Boot + JWT + PostgreSQL', status: 'Active' },
    { id: 2, title: 'Frontend UI', description: 'Vue 3 + Pinia + Bootstrap layout', status: 'In Progress' },
  ])
  
  const deleteProject = (id: number) => {
    projects.value = projects.value.filter(p => p.id !== id)
  }
  </script>
  