<template>
  <BaseLayout>
    <div class="container py-5 text-white">
      <div v-if="loading">
        <p>Loading project details...</p>
      </div>

      <div v-else>
        <h2 class="mb-3">{{ project?.name }}</h2>
        <p>{{ project?.description }}</p>
        <p>🧑 Owner: {{ project?.owner?.fullName || 'Unknown' }}</p>

        <!-- OWNER -->
        <div v-if="isOwner">
          <h5 class="mt-4">📥 Join Requests</h5>
          <JoinRequestPanel :project-id="project.id" />
        </div>

        <!-- MEMBER -->
        <div v-else-if="isMember">
          <router-link
            :to="`/projects/${project.id}/issues`"
            class="btn btn-primary mt-4"
          >
            View Issues
          </router-link>
        </div>

        <!-- NON-MEMBER -->
        <div v-else>
          <div v-if="hasSentJoinRequest">
            <p class="text-success mt-3">✅ Join request already sent!</p>
          </div>
          <div v-else>
            <button class="btn btn-success mt-4" @click="handleJoinRequest">
              Request to Join
            </button>
          </div>
        </div>

        <p v-if="errorMessage" class="text-danger mt-3">{{ errorMessage }}</p>
      </div>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '../../stores/auth'

import { checkJoinRequestStatus, sendJoinRequest } from '../../services/devFlow/joinRequest'
import JoinRequestPanel from '../../components/JoinRequestPanel.vue'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { useProjectStore } from '../../stores/project'
import { useTeamStore } from '../../stores/teams'

const route = useRoute()
const projectId = route.params.projectId as string
const authStore = useAuthStore()
const projectStore = useProjectStore()
const teamStore = useTeamStore()
const project = ref<any>(null)
const isOwner = ref(false)
const isMember = ref(false)
const hasSentJoinRequest = ref(false)
const errorMessage = ref('')
const loading = ref(true)

onMounted(async () => {
  try {
    const user = await authStore.fetchUser()            
    
    
    const projectRes = await projectStore.fetchteamProject(projectId);
    console.log(projectRes);
    
    project.value = projectRes
    const currentUserId = authStore.user?.userId

   
   const members = await teamStore.fetchTeam(projectId);
    
    isOwner.value = project.value.owner?.userId === currentUserId
   
    isMember.value = members.some((m: any) => m.userId === currentUserId)
   

    if (!isOwner.value && !isMember.value) {
      hasSentJoinRequest.value = await checkJoinRequestStatus(projectId)
    }
    
  } catch (err: any) {
    errorMessage.value = '⚠️ Failed to load project details.'
  } finally {
    loading.value = false
  }
})

async function handleJoinRequest() {
  try {
    await sendJoinRequest(projectId)
    hasSentJoinRequest.value = true
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || 'Error sending join request.'
  }
}
</script>
