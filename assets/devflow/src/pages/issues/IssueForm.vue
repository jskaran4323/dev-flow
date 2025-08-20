<template>
  <BaseLayout>
    <!-- Page title -->
    <section class="py-6 text-center">
      <h2 class="text-2xl font-semibold tracking-tight">📜 Create New Issue</h2>
    </section>

    <!-- Form card -->
    <form @submit.prevent="handleSubmit" class="card mx-auto max-w-2xl">
      <!-- Title -->
      <div class="mb-4">
        <label class="block text-sm font-medium mb-1">Title</label>
        <Input v-model="issue.title" type="text" required />
      </div>

      <!-- Description -->
      <div class="mb-4">
        <label class="block text-sm font-medium mb-1">Description</label>
        <Textarea v-model="issue.description" :rows="4" />
      </div>

      <!-- Status -->
      <div class="mb-4">
        <label class="block text-sm font-medium mb-1">Status</label>
        <Select v-model="issue.status">
          <option :value="IssueStatusType.OPEN">OPEN</option>
          <option :value="IssueStatusType.IN_PROGRESS">IN PROGRESS</option>
          <option :value="IssueStatusType.CLOSED">CLOSED</option>
        </Select>
      </div>

      <!-- Assignee -->
      <div class="mb-4">
        <label class="block text-sm font-medium mb-1">Assignee</label>
        <Select v-model="issue.assigneeId">
          <option disabled value="">Select a user</option>
          <option v-for="user in assignableUsers" :key="user.userId" :value="user.userId">
            {{ user.fullName }}
          </option>
        </Select>
      </div>

      <!-- Labels -->
      <!-- <div class="mb-4">
        <label class="block text-sm font-medium">Labels</label>
        <div class="mt-2 rounded-lg border border-input bg-background p-3 max-h-52 overflow-y-auto">
          <Checkbox
            v-for="(labelName, index) in labelTypeMap"
            :key="index"
            v-model="issue.labels"
            :value="index"
            :id="`label-${index}`"
            :label="labelName"
            class="mr-4 mb-2"
          />
        </div>
        <p class="mt-1 text-xs text-muted-foreground">Select one or more labels for this issue</p>
      </div> -->
      <div class="mb-4">
  <label class="block text-sm font-medium">Labels</label>
  <div class="mt-2">
    <select 
      v-model="issue.labels" 
      multiple
      class="w-full rounded-lg border border-input bg-background p-3 max-h-52 overflow-y-auto focus:ring-2 focus:ring-blue-500 focus:border-blue-500"
    >
      <option 
        v-for="(labelName, index) in labelTypeMap" 
        :key="index" 
        :value="index"
      >
        {{ labelName }}
      </option>
    </select>
  </div>
  <p class="mt-1 text-xs text-muted-foreground">Select one or more labels for this issue</p>
</div>

      <!-- AI Loading -->
      <div v-if="aiLoading" class="mb-4 rounded-lg border border-border bg-muted px-3 py-2 text-sm">
        🤖 Getting AI label suggestions...
      </div>

      <!-- Suggested Labels -->
      <div v-if="suggestedLabels.length > 0" class="mb-4 rounded-lg border border-border bg-muted px-3 py-2 text-sm">
  <span class="mr-2">🤖 AI Suggested:</span>
  <span
    v-for="labelNum in suggestedLabels"
    :key="labelNum"
    class="inline-flex items-center rounded-full bg-primary px-2.5 py-1 text-xs font-medium text-primary-foreground mr-2 cursor-pointer"
  
  >
    {{ labelTypeMap[labelNum] }}
    <span class="ml-1 text-xs opacity-75">
      ({{ Math.round(issueStore.suggestedLabels.find(s => s.label === labelNum)?.confidence * 100) }}%)
    </span>
  </span>
</div>

      <!-- Submit -->
      <Button type="submit" variant="primary" size="lg" class="w-full">
        Create Issue
      </Button>

      <!-- Error -->
      <p v-if="errorMessage" class="mt-3 mb-0 text-center text-sm text-destructive">
        {{ errorMessage }}
      </p>
    </form>
  </BaseLayout>
</template>

<script setup lang="ts">
import { reactive, ref, onMounted, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useIssueStore } from '../../stores/issue'
import { useTeamStore } from '../../stores/teams'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { IssueStatusType } from '../../enums/IssueStatusType'

// UI primitives
import Input from '../../components/ui/Input.vue'
import Textarea from '../../components/ui/Textarea.vue'
import Select from '../../components/ui/Select.vue'
import Checkbox from '../../components/ui/Checkbox.vue'
import Button from '../../components/ui/Button.vue'

const route = useRoute()
const router = useRouter()
const projectId = route.params.projectId as string
const aiLoading = ref(false)
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
const suggestedLabels = computed(() => {
  return issueStore.suggestedLabels?.map(suggestion => suggestion.label) || []
})
console.log( suggestedLabels);

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

let aiSuggestionTimer: ReturnType<typeof setTimeout> | null = null

watch(
  () => [issue.title, issue.description],
  async ([title, description]) => {
    if (aiSuggestionTimer) clearTimeout(aiSuggestionTimer)
    if (title.length > 15 || description.length > 25) {
      aiSuggestionTimer = setTimeout(async () => {
        await issueStore.fetchAISuggestions(title, description)
        issue.labels = [...issueStore.suggestedLabels]
      }, 10000)
    }
  },
  { immediate: false }
)

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
    router.push({name : "Issues"})
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || 'Failed to create issue'
  }
}
</script>
