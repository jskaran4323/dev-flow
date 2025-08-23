<template>
  <BaseLayout>
    <!-- Header -->
    <section class="py-6">
      <h2 class="text-2xl font-semibold tracking-tight">📊 Project Issues Board</h2>
    </section>

    <!-- Columns -->
    <section class="grid grid-cols-1 md:grid-cols-3 gap-6">
      <Card
        v-for="status in statuses"
        :key="status.key"
        class="h-[70vh] flex flex-col"
      >
        <!-- Column header -->
        <div class="flex items-center justify-between">
          <h3 class="text-lg font-semibold">{{ status.label }}</h3>
          <Badge variant="info" rounded>{{ filteredIssues(status.key).length }}</Badge>
        </div>

        <!-- Column body -->
        <div class="mt-4 flex-1 overflow-y-auto space-y-3">
          <!-- Issue cards -->
          <article
            v-for="issue in filteredIssues(status.key)"
            :key="issue.id"
            class="rounded-lg border border-border bg-card/60 p-3 cursor-pointer hover:bg-muted transition-colors"
            @click="navigateToIssue(issue.id, projectId)"
          >
            <h4 class="text-sm font-semibold">{{ issue.title }}</h4>
            <p class="mt-1 text-xs text-muted-foreground line-clamp-3">{{ issue.description }}</p>

            <!-- Labels -->
            <div v-if="issue.labels && issue.labels.length" class="mt-2 flex flex-wrap gap-1.5">
              <Badge
                v-for="(label, index) in issue.labels"
                :key="typeof label === 'object' ? label.id : `${issue.id}-${index}`"
                :variant="getLabelVariant(getLabelType(label))"
                rounded
              >
                {{ getLabelName(getLabelType(label)) }}
              </Badge>
            </div>

            <!-- Assignee + date -->
            <div class="mt-3 flex items-center justify-between text-[11px] text-muted-foreground">
              <div>👤 {{ issue.assignee?.fullName || 'Unassigned' }}</div>
              <div>{{ formatDate(issue.createdAt) }}</div>
            </div>
          </article>

          <!-- Empty state -->
          <div
            v-if="filteredIssues(status.key).length === 0"
            class="text-center text-sm text-muted-foreground py-8"
          >
            📭 No issues in {{ status.label.toLowerCase() }}
          </div>
        </div>
      </Card>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { onMounted, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useIssueStore } from '../stores/issue'
import BaseLayout from '../layouts/BaseLayout.vue'
import Card from '../components/ui/Card.vue'
import Badge from '../components/ui/Badge.vue'

const route = useRoute()
const router = useRouter()
const projectId = route.params.projectId as string
const issueStore = useIssueStore()

const issues = computed(() => issueStore.issues)

onMounted(() => {
  issueStore.fetchIssues(projectId)
})

const statuses = [
  { key: 'OPEN',        label: '🟢 Open' },
  { key: 'IN_PROGRESS', label: '🟡 In Progress' },
  { key: 'CLOSED',      label: '🔴 Closed' }
]

const filteredIssues = (status: string) => {
  return issues.value.filter((i) => {
    if (typeof i.status === 'number') {
      return getStatusString(i.status) === status
    }
    return i.status === status
  })
}

const getStatusString = (statusCode: number) => {
  const statusMap: Record<number, string> = {
    0: 'OPEN',
    1: 'IN_PROGRESS',
    2: 'CLOSED'
  }
  return statusMap[statusCode] || 'UNKNOWN'
}

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

const getLabelType = (label: any) => {
  return typeof label === 'object' && label.type !== undefined ? label.type : label
}

const getLabelName = (labelType: number) => {
  return labelTypeMap[labelType] || `Unknown (${labelType})`
}

/** Map label type -> Badge variant colors */
const getLabelVariant = (labelType: number):
  'default' | 'secondary' | 'success' | 'warning' | 'danger' | 'info' => {
  switch (labelType) {
    case 0: return 'danger'     // BUG
    case 1: return 'success'    // FEATURE
    case 2: return 'info'       // ENHANCEMENT
    case 3: return 'secondary'  // DOCUMENTATION
    case 4: return 'warning'    // URGENT
    case 5: return 'info'       // DESIGN
    case 6: return 'secondary'  // TEST
    case 7: return 'warning'    // PERFORMANCE
    case 8: return 'danger'     // SECURITY
    case 9: return 'success'    // BACKEND
    case 10: return 'info'      // FRONTEND
    default: return 'secondary'
  }
}

const formatDate = (dateString: string) => {
  if (!dateString) return ''
  return new Date(dateString).toLocaleDateString()
}

const navigateToIssue = (issueId: string, projectId?: string) => {
  router.push({
    name: 'IssueDetails',
    params: { id: issueId },
    query: projectId ? { projectId } : {}
  })
}
</script>