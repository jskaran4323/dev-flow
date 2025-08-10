<template>
  <BaseLayout>
    <!-- Loading -->
    <section v-if="loading" class="py-10">
      <p class="text-sm text-muted-foreground">Loading project details...</p>
    </section>

    <!-- Content -->
    <section v-else class="space-y-6">
      <!-- Header -->
      <header>
        <h2 class="text-2xl font-semibold tracking-tight">
          {{ project?.name }}
        </h2>
        <p class="text-sm text-muted-foreground mt-1">
          {{ project?.description }}
        </p>
        <p class="mt-3 text-sm">
          🧑 <span class="text-muted-foreground">Owner:</span>
          <span class="font-medium text-foreground">{{ project?.owner?.fullName || 'Unknown' }}</span>
        </p>
      </header>

      <!-- Owner tools -->
      <section v-if="isOwner" class="card">
        <h3 class="text-lg font-medium mb-4">📥 Join Requests</h3>
        <JoinRequestPanel :project-id="project.id" />
      </section>

      <!-- Member tools -->
      <section v-else-if="isMember" class="card">
        <div class="flex items-center justify-between">
          <div>
            <h3 class="text-lg font-medium">Project Workspace</h3>
            <p class="text-sm text-muted-foreground">Access issues and collaborate with your team.</p>
          </div>
          <router-link
            :to="`/projects/${project.id}/issues`"
            class="inline-flex h-9 items-center rounded-lg bg-primary px-4 text-sm font-medium text-primary-foreground hover:opacity-90 transition-opacity"
          >
            View Issues
          </router-link>
        </div>
      </section>

      <!-- Non-member actions -->
      <section v-else class="card">
        <div v-if="hasSentJoinRequest" class="text-sm">
          <span class="inline-flex items-center rounded-md bg-muted px-3 py-2">
            ✅ Join request already sent!
          </span>
        </div>
        <div v-else class="flex items-center justify-between">
          <div>
            <h3 class="text-lg font-medium">Interested in this project?</h3>
            <p class="text-sm text-muted-foreground">Send a join request to the owner.</p>
          </div>
          <button
            class="inline-flex h-9 items-center rounded-lg bg-primary px-4 text-sm font-medium text-primary-foreground hover:opacity-90 transition-opacity"
            @click="handleJoinRequest"
          >
            Request to Join
          </button>
        </div>
      </section>

      <!-- Error -->
      <p v-if="errorMessage" class="text-sm text-destructive">
        {{ errorMessage }}
      </p>
    </section>
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
