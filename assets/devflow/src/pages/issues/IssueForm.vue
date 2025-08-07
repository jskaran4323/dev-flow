<template>
  <BaseLayout>
    <div class="container py-5 text-white">
      <h2 class="mb-4">📜 Create New Issue</h2>

      <form @submit.prevent="handleSubmit" class="card bg-secondary text-white p-4 shadow-sm" style="max-width: 600px;">
        <div class="mb-3">
          <label class="form-label">Title</label>
          <input v-model="issue.title" type="text" class="form-control" required />
        </div>

        <div class="mb-3">
          <label class="form-label">Description</label>
          <textarea v-model="issue.description" class="form-control" rows="4"></textarea>
        </div>

        <div class="mb-3">
          <label class="form-label">Status</label>
          <select v-model="issue.status" class="form-select">
            <option :value="IssueStatusType.OPEN">OPEN</option>
            <option :value="IssueStatusType.IN_PROGRESS">IN PROGRESS</option>
            <option :value="IssueStatusType.CLOSED">CLOSED</option>
          </select>
        </div>

        <!-- Assignee -->
        <div class="mb-3">
          <label class="form-label">Assignee</label>
          <select v-model="issue.assigneeId" class="form-select">
            <option disabled value="">Select a user</option>
            <option v-for="user in assignableUsers" :key="user.userId" :value="user.userId">
              {{ user.fullName }}
            </option>
          </select>
        </div>

        <!-- Labels -->
        <div class="mb-3">
          <label class="form-label">Labels</label>
          <div class="border rounded p-3" style="max-height: 200px; overflow-y: auto;">
            <div v-for="(labelName, index) in labelTypeMap" :key="index" class="form-check">
              <input
                class="form-check-input"
                type="checkbox"
                :id="`label-${index}`"
                :value="index"
                v-model="issue.labels"
              >
              <label class="form-check-label" :for="`label-${index}`">
                {{ labelName }}
              </label>
            </div>
          </div>
          <small class="form-text text-muted">Select one or more labels for this issue</small>
        </div>

        <!-- Suggested Labels Display -->
        <div v-if="suggestedLabels.length > 0" class="alert alert-info">
          Suggested:
          <span v-for="type in suggestedLabels" :key="type" class="badge bg-primary mx-1">
            {{ labelTypeMap[type] }}
          </span>
        </div>

        <button type="submit" class="btn btn-success w-100">Create Issue</button>
        <p class="text-danger mt-2" v-if="errorMessage">{{ errorMessage }}</p>
      </form>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useIssueStore } from '../../stores/issue'
import { useTeamStore } from '../../stores/teams'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { IssueStatusType } from '../../enums/IssueStatusType'

const route = useRoute()
const router = useRouter()
const projectId = route.params.projectId as string

const issueStore = useIssueStore()
const teamStore = useTeamStore()

const issue = reactive({
  title: '',
  description: '',
  status: '',
  assigneeId: '',
  labels: [] as number[]
})

const errorMessage = ref('')
const suggestedLabels = computed(() => issueStore.suggestedLabels)
const assignableUsers = computed(() => teamStore.assignableUsers)

onMounted(() => {
  teamStore.fetchTeam(projectId)
})

const labelTypeMap: Record<number, string> = {
  0: 'BUG',
  1: 'FEATURE',
  2: 'ENHANCEMENT',
  3: 'DOCUMENTATION',
  4: 'URGENT',
  5: 'DESIGN',
  6: 'TEST',
  7: 'PERFORMANCE',
  8: 'SECURITY',
  9: 'BACKEND',
  10: 'FRONTEND'
}

// watch(
//   () => [issue.title, issue.description],
//   async ([title, description]) => {
//     if (title.length > 5 || description.length > 10) {
//       await issueStore.fetchAISuggestions(title, description)
//       issue.labels = [...issueStore.suggestedLabels]
//     }
//   },
//   { immediate: false }
// )

const handleSubmit = async () => {
  try {
    const payload = {
      title: issue.title,
      description: issue.description,
      status: issue.status,
      assigneeId: issue.assigneeId,
      labels: issue.labels
    }
    await issueStore.createIssue(projectId, payload)
    router.push(`/projects/${projectId}/issues`)
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || 'Failed to create issue'
  }
}
</script>
