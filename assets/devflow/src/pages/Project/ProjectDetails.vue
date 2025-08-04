<template>
  <BaseLayout>
    <div class="container py-5 text-white">
      <div v-if="project">
        <h2 class="mb-3">{{ project.name }}</h2>
        <p>{{ project.description }}</p>
        <p>🧑 Owner: {{ project.owner?.fullName || 'Unknown' }}</p>

        <div v-if="isOwner">
          <h5 class="mt-4">📥 Join Requests</h5>
          <JoinRequestPanel :project-id="project.id" />
        </div>
        <div v-else>
          <button class="btn btn-success mt-3" @click="handleJoinRequest">
            Request to Join
          </button>
          <p class="text-success mt-2" v-if="successMessage">{{ successMessage }}</p>
          <p class="text-danger mt-2" v-if="errorMessage">{{ errorMessage }}</p>
        </div>
      </div>

      <div v-else>
        <p>Loading project details...</p>
      </div>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { usePublicProjectStore } from '../../stores/publicProject'
import { sendJoinRequest } from '../../services/devFlow/joinRequest'
import { useAuthStore } from '../../stores/auth'
import JoinRequestPanel from '../../components/JoinRequestPanel.vue'

const route = useRoute()
const projectId = route.params.projectId as string
const projectStore = usePublicProjectStore()
const authStore = useAuthStore()

const successMessage = ref('')
const errorMessage = ref('')

// Get project from store
const project = computed(() => projectStore.getPublicProjectById(projectId))

// Determine if current user is owner
const isOwner = computed(() => {
  const currentUserId = authStore.user?.id
  console.log("username",authStore.user);
  
  return project.value?.owner?.id === currentUserId
})

onMounted(async () => {
  if (!project.value) {
    try {
      await projectStore.fetchProjects()
      await authStore.fetchUser()
    } catch (err) {
      errorMessage.value = 'Failed to load project'
    }
  }
})

async function handleJoinRequest() {
  try {
    await sendJoinRequest(projectId)
    successMessage.value = 'Join request sent!'
    errorMessage.value = ''
  } catch (err: any) {
    successMessage.value = ''
    errorMessage.value = err.response?.data?.message || 'Error sending request'
  }
}
</script>
