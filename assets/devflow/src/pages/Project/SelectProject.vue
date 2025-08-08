<template>
  <BaseLayout>
    <div class="container py-5 text-white">
      <h2 class="mb-4 text-center">📁 Select a Project to Continue</h2>

      <div class="card bg-dark border border-secondary shadow-sm p-4">
        <ul class="list-group list-group-flush">
          <li
            v-for="project in projectStore.projects"
            :key="project.id"
            class="list-group-item bg-dark text-white d-flex justify-content-between align-items-center border-secondary"
          >
            <div>
              <h5 class="mb-0">{{ project.name }}</h5>
              <small class="text-muted">ID: {{ project.id }}</small>
            </div>
            <button class="btn btn-outline-info btn-sm" @click="selectProject(project.id)">
              Continue →
            </button>
          </li>
        </ul>

        <div v-if="projectStore.projects.length === 0" class="text-center text-muted py-3">
          <p>No projects available.</p>
        </div>
      </div>
    </div>
  </BaseLayout>
</template>
  
  <script setup lang="ts">
  import { onMounted } from 'vue'
  import { useRouter, useRoute } from 'vue-router'
 import { useProjectStore } from '../../stores/project'
import BaseLayout from '../../layouts/BaseLayout.vue'
  
  const router = useRouter()
  const route = useRoute()
  const projectStore = useProjectStore()
  
  onMounted(async () => {
    if (projectStore.projects.length === 0) {
      await projectStore.fetchProjects()
    }
  })
  
  const selectProject = (projectId: string) => {
    router.push({
    name: 'ProjectIssues',
    params: { projectId }
  })
  }
  </script>
  