<template>
  <BaseLayout>
    <div class="container py-5 text-white">
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>📦 My Projects</h2>
        <router-link to="/projects/new" class="btn btn-success">+ New Project</router-link>
      </div>

      <!-- Show alert if no projects -->
      <div v-if="projects.length === 0" class="alert alert-secondary text-muted">
        You don’t have any projects yet. Click “New Project” to create one.
      </div>

      <!-- Project Cards -->
      <div class="row g-4">
        <div
          class="col-md-6 col-lg-4"
          v-for="project in projects"
          :key="project.id"
        >
          <div
            class="card bg-secondary text-white shadow-sm h-100 border-light project-card"
            @click="goToProjectDetails(project.id)"
          >
            <div class="card-body">
              <h5 class="card-title fw-bold">{{ project.name }}</h5>
              <p class="card-text small text-light-emphasis">{{ project.description }}</p>
              <span class="badge bg-light text-dark">{{ project.status }}</span>
            </div>
            <div
              class="card-footer bg-transparent d-flex justify-content-between border-top border-light"
              @click.stop
            >
              <router-link :to="`/projects/${project.id}/edit`" class="btn btn-sm btn-outline-light">Edit</router-link>
              <button class="btn btn-sm btn-outline-danger" @click="deleteProject(project.id)">Delete</button>
            </div>
            <router-link
              :to="`/projects/${project.id}/kanban`"
              class="btn btn-outline-light"
              @click.stop
            >
              📊 View Kanban Board
            </router-link>
          </div>
        </div>
      </div>
    </div>
   
  </BaseLayout>
</template>
<script setup lang="ts">
import { onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { storeToRefs } from 'pinia'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { useProjectStore } from '../../stores/project'

const router = useRouter()
const projectStore = useProjectStore()
const { projects } = storeToRefs(projectStore)

onMounted(async () => {
  await projectStore.fetchAllProjects()
})

const goToProjectDetails = (projectId: string) => {
  router.push(`/projects/${projectId}`)
}

const deleteProject = async (id: string) => {
  try {
    await projectStore.deleteProject(id)
    await projectStore.fetchProjects()
  } catch (err) {
    console.error('Failed to delete project', err)
  }
}
</script>
