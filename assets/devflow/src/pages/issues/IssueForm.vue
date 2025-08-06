<template>
  <BaseLayout>
    <div class="container py-5 text-white">
      <h2 class="mb-4">📝 Create New Issue</h2>

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

        <!-- Assignee Dropdown -->
        <div class="mb-3">
  <label class="form-label">Assignee</label>
  <select v-model="issue.assigneeId" class="form-select">
    <option disabled value="">Select a user</option>
    <option v-for="user in assignableUsers" :key="user.userId" :value="user.userId">
  {{ user.fullName}}
</option>
  </select>
</div>

       
      <div class="mb-3">
          <label class="form-label">Labels</label>
          <select v-model="issue.labelIds" multiple class="form-select">
            <option v-for="label in labels" :key="label.id" :value="label.id">
              {{ label.name }}
            </option>
          </select>
        </div>

        <button type="submit" class="btn btn-success w-100">Create Issue</button>

        <p class="text-danger mt-2" v-if="errorMessage">{{ errorMessage }}</p>
      </form>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useIssueStore } from '../../stores/issue'
import BaseLayout from '../../layouts/BaseLayout.vue'

const issueStore = useIssueStore()
const route = useRoute()
const router = useRouter()
const projectId = route.params.projectId as string
import { useTeamStore } from '../../stores/teams'
import { IssueStatusType } from '../../enums/IssueStatusType'
const teamStore = useTeamStore()
const issue = reactive({
  title: '',
  description: '',
  status: IssueStatusType,
  assigneeId: '',
  labelIds: [] as string[]
})

const errorMessage = ref('')

const assignableUsers = computed(() =>
  teamStore.assignableUsers

  
)

onMounted(()=> {
  teamStore.fetchTeam(projectId)
  
})

const handleSubmit = async () => {
  try {
    await issueStore.createIssue(projectId, issue)
    router.push(`/projects/${projectId}/issues`)
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || 'Failed to create issue'
  }
}

</script>
