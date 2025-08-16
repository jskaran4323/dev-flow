<template>
  <BaseLayout>
    <!-- Page header -->
    <section class="py-4">
      <h2 class="text-xl font-semibold tracking-tight">Dashboard</h2>
      <p class="text-sm text-muted-foreground mt-1">Recent activity in your workspace</p>
    </section>

    <section class="grid grid-cols-1 md:grid-cols-12 gap-6">
      <!-- LEFT: Quick actions -->
      <div class="md:col-span-3 space-y-4">
        <Card>
          <h3 class="text-sm font-semibold">Quick actions</h3>
          <div class="mt-3 flex flex-col gap-2">
            <Button as="router-link" :to="'/projects'" variant="secondary" size="md">
              View Projects
            </Button>
            <Button
              as="router-link"
              :to="'/select-project?next=create-issue'"
              variant="primary"
              size="md"
            >
              + Create Issue
            </Button>
            <Button
              as="router-link"
              :to="'/public-projects'"
              variant="secondary"
              size="md"
            >
              View Public Projects
            </Button>
          </div>
        </Card>
        <Card>
  <h4 class="text-sm font-medium text-muted-foreground">Your projects</h4>
  <p class="mt-2 text-2xl font-semibold">{{ totalProjects }}</p>
</Card>

<Card>
  <h4 class="text-sm font-medium text-muted-foreground">Your issues</h4>
  <p class="mt-2 text-2xl font-semibold">{{ totalIssues }}</p>
</Card>

      </div>

      <!-- CENTER: Activity feed -->
      <div class="md:col-span-9 space-y-4">
        <Card>
          <template #header>
            <h3 class="text-sm font-semibold">Recent activity</h3>
          </template>

          <div class="space-y-3">
            <div v-if="loading" class="text-sm text-muted-foreground">Loading activity…</div>
            <div v-else-if="activity.length === 0" class="text-sm text-muted-foreground">
              No recent activity yet.
            </div>

            <article
              v-for="item in activity"
              :key="item.id"
              class="rounded-lg border border-border bg-card/60 p-3"
            >
              <div class="flex items-start justify-between gap-3">
                <div class="min-w-0">
                  <h4 class="text-sm font-semibold truncate">
                    <router-link
                      v-if="item.kind === 'project'"
                      :to="`/projects/${item.projectId}`"
                      class="hover:underline"
                    >
                      {{ item.title }}
                    </router-link>
                    <router-link
                      v-else-if="item.kind === 'issue' && item.issueId"
                      :to="`/issues/${item.issueId}`"
                      class="hover:underline"
                    >
                      {{ item.title }}
                    </router-link>
                    <span v-else>{{ item.title }}</span>
                  </h4>
                  <p class="mt-1 text-xs text-muted-foreground">
                    {{ item.subtitle }}
                  </p>
                </div>

                <Badge :variant="item.action === 'created' ? 'success' : 'info'" rounded>
                  {{ item.action }}
                </Badge>
              </div>

              <div class="mt-2 text-[11px] text-muted-foreground">
                {{ formatWhen(item.whenISO) }}
              </div>
            </article>
          </div>
        </Card>
      </div>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">

import BaseLayout from '../layouts/BaseLayout.vue'
import Card from '../components/ui/Card.vue'
import Button from '../components/ui/Button.vue'
import Badge from '../components/ui/Badge.vue'
import { ref, computed, onMounted } from 'vue'
import { useProjectStore } from '../stores/project'
import { getProjectIssues } from '../services/devFlow/issue'

const projectStore = useProjectStore()

const totalProjects = computed(() => projectStore.projects.length)
const totalIssues = ref(0)

onMounted(async () => {
  // Ensure projects are loaded (skip if you already fetch elsewhere)
  if (!projectStore.projects.length) {
    await projectStore.fetchAllProjects()
  }

  // Sum issues across all of the user's projects
  const ids = projectStore.projects.map(p => p.id)
  const counts = await Promise.all(
    ids.map(id =>
      getProjectIssues(id)
        .then((arr: any[]) => arr?.length ?? 0)
        .catch(() => 0)
    )
  )
  totalIssues.value = counts.reduce((a, b) => a + b, 0)
})

type ActivityItem = {
  id: string
  kind: 'project' | 'issue'
  action: 'created' | 'updated'
  title: string
  subtitle: string
  whenISO: string
  projectId: string
  issueId?: string
}

const loading = ref(true)
const rawActivity = ref<ActivityItem[]>([])

/** pick a usable timestamp even if API fields are missing */
function pickWhen(...candidates: (string | null | undefined)[]) {
  const first = candidates.find(Boolean)
  return first ?? new Date().toISOString()
}

onMounted(async () => {
  loading.value = true
  try {
    await projectStore.fetchAllProjects()

    // Project activity (always include; fall back to now if dates missing)
    const fromProjects: ActivityItem[] = projectStore.projects.flatMap((p) => {
      const created: ActivityItem = {
        id: `${p.id}-project-created`,
        kind: 'project',
        action: 'created',
        title: `Created project: ${p.name}`,
        subtitle: p.description || 'No description',
        whenISO: pickWhen(p.createdAt, p.updatedAt),
        projectId: p.id
      }

      const updated: ActivityItem[] =
        p.updatedAt && p.updatedAt !== p.createdAt
          ? [{
              id: `${p.id}-project-updated`,
              kind: 'project',
              action: 'updated',
              title: `Updated project: ${p.name}`,
              subtitle: p.description || 'No description',
              whenISO: pickWhen(p.updatedAt, p.createdAt),
              projectId: p.id
            }]
          : []

      return [created, ...updated]
    })

    // Issue activity per project (safe fetch; don’t mutate store)
    const fromIssuesArrays = await Promise.all(
      projectStore.projects.map(async (p) => {
        try {
          const issues = await getProjectIssues(p.id)
          return (issues || []).flatMap((i: any) => {
            const base = {
              kind: 'issue' as const,
              projectId: p.id,
              issueId: i.id,
              title: `Issue: ${i.title}`,
              subtitle: i.description || 'No description'
            }
            const created: ActivityItem = {
              id: `${i.id}-issue-created`,
              action: 'created',
              whenISO: pickWhen(i.createdAt, i.updatedAt),
              ...base
            }
            const updates: ActivityItem[] =
              i.updatedAt && i.updatedAt !== i.createdAt
                ? [{
                    id: `${i.id}-issue-updated`,
                    action: 'updated',
                    whenISO: pickWhen(i.updatedAt, i.createdAt),
                    ...base
                  }]
                : []
            return [created, ...updates]
          })
        } catch {
          return []
        }
      })
    )

    const fromIssues = fromIssuesArrays.flat()

    rawActivity.value = [...fromProjects, ...fromIssues]
      .sort((a, b) => new Date(b.whenISO).getTime() - new Date(a.whenISO).getTime())
      .slice(0, 50)
  } finally {
    loading.value = false
  }
})

const activity = computed(() => rawActivity.value)

function formatWhen(iso: string) {
  try {
    return new Date(iso).toLocaleString()
  } catch {
    return iso
  }
}
</script>
