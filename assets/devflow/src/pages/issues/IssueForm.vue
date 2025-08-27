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

      <!-- Labels with Checkboxes -->
      <div class="mb-4">
        <label class="block text-sm font-medium mb-2">Labels</label>
        
        <!-- Multi-select with checkboxes -->
        <div class="border border-input rounded-lg p-3 bg-background max-h-52 overflow-y-auto space-y-2">
          <label 
            v-for="(labelName, index) in LabelStatusType" 
            :key="index"
            class="flex items-center space-x-3 cursor-pointer hover:bg-muted/50 p-2 rounded-md transition-colors"
          >
            <input 
              type="checkbox" 
              :value="index"
              v-model="issue.labels"
              class="h-4 w-4 text-primary focus:ring-2 focus:ring-primary focus:ring-offset-2 border-input rounded"
            >
            <span class="text-sm select-none">{{ labelName }}</span>
          </label>
        </div>

        <!-- Selected Labels Display -->
        <div v-if="issue.labels.length > 0" class="mt-3">
          <div class="flex flex-wrap gap-2">
            <span 
              v-for="labelIndex in issue.labels" 
              :key="labelIndex"
              class="inline-flex items-center gap-1 bg-primary/10 text-primary text-xs px-2.5 py-1 rounded-full"
            >
              {{ LabelStatusType[labelIndex] }}
              <button 
                type="button"
                @click="removeLabel(labelIndex)"
                class="text-primary/70 hover:text-primary hover:bg-primary/20 rounded-full w-4 h-4 flex items-center justify-center text-xs font-medium"
                :title="`Remove ${LabelStatusType[labelIndex]} label`"
              >
                ×
              </button>
            </span>
          </div>
        </div>
        
        <p class="mt-1 text-xs text-muted-foreground">Select one or more labels for this issue</p>
      </div>

      <!-- AI Loading -->
      <div v-if="aiLoading" class="mb-4 rounded-lg border border-border bg-muted px-3 py-2 text-sm">
        🤖 Getting AI label suggestions...
      </div>

      <!-- Suggested Labels -->
      <div v-if="suggestedLabels.length > 0" class="mb-4 rounded-lg border border-border bg-muted px-3 py-2 text-sm">
        <div class="flex items-center flex-wrap gap-2">
          <span class="text-muted-foreground">🤖 AI Suggested:</span>
          <button
            type="button"
            v-for="labelNum in suggestedLabels"
            :key="labelNum"
            @click="toggleSuggestedLabel(labelNum)"
            :class="[
              'inline-flex items-center rounded-full px-2.5 py-1 text-xs font-medium transition-colors cursor-pointer',
              issue.labels.includes(labelNum) 
                ? 'bg-primary text-primary-foreground' 
                : 'bg-primary/20 text-primary hover:bg-primary/30'
            ]"
          >
            {{ LabelStatusType[labelNum] }}
            <span class="ml-1 text-xs opacity-75">
              ({{ Math.round((issueStore.suggestedLabels.find(s => s.label === labelNum)?.confidence || 0) * 100) }}%)
            </span>
          </button>
        </div>
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
import Button from '../../components/ui/Button.vue'
import { LabelStatusType } from '../../enums/LabelStatusType'

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

const assignableUsers = computed(() => teamStore.assignableUsers)

onMounted(() => {
  teamStore.fetchTeam(projectId)
})


let aiSuggestionTimer: ReturnType<typeof setTimeout> | null = null

const removeLabel = (labelIndex: number) => {
  const index = issue.labels.indexOf(labelIndex);
  if (index > -1) {
    issue.labels.splice(index, 1);
  }
}
const toggleSuggestedLabel = (labelNum: number) => {
  const index = issue.labels.indexOf(labelNum);
  if (index > -1) {
    issue.labels.splice(index, 1);
  } else {
    issue.labels.push(labelNum);
  }
}

watch(
  () => [issue.title, issue.description],
  async ([title, description]) => {
    if (aiSuggestionTimer) clearTimeout(aiSuggestionTimer)
    if (title.length > 15 || description.length > 25) {
      aiSuggestionTimer = setTimeout(async () => {
        await issueStore.fetchAISuggestions(title, description)
        // Extract only the label numbers from suggested labels
        issue.labels = issueStore.suggestedLabels.map(suggestion => suggestion.label)
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
    router.push({ name: "Issues" })
  } catch (err: any) {
    errorMessage.value = err.response?.data?.message || 'Failed to create issue'
  }
}
</script>