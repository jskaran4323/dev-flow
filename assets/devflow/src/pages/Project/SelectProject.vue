<template>
    <BaseLayout>
      <div class="container py-5 text-white">
        <h2>Select a Project to Continue</h2>
        <ul class="list-group">
          <li
            v-for="project in projectStore.projects"
            :key="project.id"
            class="list-group-item d-flex justify-content-between align-items-center"
          >
            {{ project.name }}
            <button class="btn btn-primary btn-sm" @click="selectProject(project.id)">
              Continue
            </button>
          </li>
        </ul>
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
  