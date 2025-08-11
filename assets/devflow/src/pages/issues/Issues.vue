<template>
  <BaseLayout>
    <!-- Header -->
    <section class="py-6 flex flex-col gap-3 md:flex-row md:items-center md:justify-between">
      <div>
        <h2 class="text-2xl font-semibold tracking-tight">Issues</h2>
        <p class="text-sm text-muted-foreground mt-1">All issues in this project</p>
      </div>

      <Button variant="primary" size="md" @click="goToCreateIssue">
        + New Issue
      </Button>
    </section>

    <!-- Empty State -->
    <section v-if="issues.length === 0" class="py-10">
      <div class="card text-center">
        <h3 class="text-lg font-medium">No issues found</h3>
        <p class="text-sm text-muted-foreground mt-1">
          Start by creating a new one.
        </p>
        <div class="mt-4">
          <Button variant="primary" size="md" @click="goToCreateIssue">
            + Create Issue
          </Button>
        </div>
      </div>
    </section>

    <!-- Issues Grid -->
    <section v-else class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <article
        v-for="issue in issues"
        :key="issue.id"
        class="card h-full flex flex-col"
      >
        <div class="flex-1">
          <!-- Title -->
          <h3 class="text-lg font-semibold">
            <router-link :to="`/issues/${issue.id}`" class="hover:underline">
              {{ issue.title }}
            </router-link>
          </h3>

          <!-- Description -->
          <p class="mt-2 text-sm text-muted-foreground line-clamp-3">
            {{ issue.description.length > 100 ? issue.description.slice(0, 100) + '...' : issue.description }}
          </p>

          <!-- Assignee -->
          <p class="mt-3 text-sm">
            <span class="text-muted-foreground">👤 Assignee:</span>
            <span class="font-medium text-foreground">
              {{ issue.assignee.fullName }}
            </span>
            <span class="text-muted-foreground">({{ issue.assignee.username }})</span>
          </p>

          <!-- Labels -->
          <div class="mt-3">
            <span class="text-sm text-muted-foreground">🏷️ Labels:</span>
            <div class="mt-2 flex flex-wrap gap-2">
              <Badge
                v-for="label in issue.labels"
                :key="label.id"
                variant="default"
                rounded
              >
                {{ label.type }}
              </Badge>
            </div>
          </div>

          <!-- Timestamps -->
          <div class="mt-4 text-xs text-muted-foreground space-y-1">
            <div>🕒 Created: {{ formatDate(issue.createdAt) }}</div>
            <div>🔄 Updated: {{ formatDate(issue.updatedAt) }}</div>
          </div>
        </div>

        <!-- Footer Actions -->
        <div class="mt-4 pt-4 border-t border-border flex items-center justify-between">
          <router-link
            :to="`/issues/${issue.id}/edit`"
            class="inline-flex h-8 items-center rounded-lg border border-border bg-background px-3 text-sm font-medium hover:bg-muted transition-colors"
          >
            ✏️ Edit
          </router-link>

          <Button
            variant="destructive"
            size="sm"
            @click="deleteIssue(issue.id)"
          >
            🗑️ Delete
          </Button>
        </div>
      </article>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { useIssueStore } from '../../stores/issue'
import Button from '../../components/ui/Button.vue'
import Badge from '../../components/ui/Badge.vue'

const route = useRoute()
const router = useRouter()
const projectId = route.params.projectId as string
const issueStore = useIssueStore()

const issues = computed(() => issueStore.issues)

onMounted(async () => {
  await issueStore.fetchIssues(projectId)
})

function goToCreateIssue() {
  router.push(`/projects/${projectId}/issues/new`)
}

const deleteIssue = async (id: string) => {
  await issueStore.deleteIssue(id)
}

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleString()
}
</script>
