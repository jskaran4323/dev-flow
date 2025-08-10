<template>
  <BaseLayout>
    <!-- Guard -->
    <section v-if="issue" class="space-y-6">
      <!-- Header -->
      <header>
        <h2 class="text-2xl font-semibold tracking-tight">🐛 {{ issue.title }}</h2>
        <p class="mt-2 text-sm text-muted-foreground">
          {{ issue.description }}
        </p>

        <div class="mt-4 flex flex-wrap items-center gap-3 text-sm">
          <div>
            <span class="text-muted-foreground">Status:</span>
            <span class="inline-flex items-center rounded-full bg-accent px-2.5 py-1 text-xs font-medium text-accent-foreground">
              {{ issue.status }}
            </span>
          </div>

          <div>
            <span class="text-muted-foreground">Assignee:</span>
            <span class="font-medium text-foreground">
              {{ issue.assignee?.fullName }}
            </span>
            <span class="text-muted-foreground">({{ issue.assignee?.username }})</span>
          </div>
        </div>
      </header>

      <!-- Labels -->
      <section class="card">
        <h3 class="text-lg font-medium">🏷️ Labels</h3>
        <div class="mt-3 flex flex-wrap gap-2">
          <span
            v-for="label in issue.labels"
            :key="label.id"
            class="inline-flex items-center rounded-full bg-accent px-2.5 py-1 text-xs font-medium text-accent-foreground"
          >
            {{ labelTypeMap[label.type] || 'Unknown' }}
          </span>
        </div>
      </section>

      <!-- Comments -->
      <section class="card">
        <h3 class="text-lg font-medium">💬 Comments</h3>

        <div v-if="comments.length === 0" class="mt-2 text-sm text-muted-foreground">
          No comments yet.
        </div>

        <ul v-else class="mt-4 space-y-3">
          <li
            v-for="comment in comments"
            :key="comment.id"
            class="rounded-lg border border-border bg-card/60 p-3"
          >
            <p class="text-sm">{{ comment.content }}</p>
            <small class="mt-1 block text-xs text-muted-foreground">
              {{ formatDate(comment.createdAt) }}
            </small>
          </li>
        </ul>

        <form @submit.prevent="submitComment" class="mt-4">
          <label class="block text-sm font-medium text-foreground mb-1">Add a comment</label>
          <textarea
            v-model="newComment"
            rows="3"
            placeholder="Write your comment…"
            class="w-full rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none focus-visible:ring-2 focus-visible:ring-ring"
          ></textarea>
          <button
            type="submit"
            class="mt-2 inline-flex h-9 items-center rounded-lg bg-primary px-4 text-sm font-medium text-primary-foreground hover:opacity-90 transition-opacity"
          >
            Post Comment
          </button>
        </form>
      </section>

      <!-- Team Members -->
      <section class="card">
        <h3 class="text-lg font-medium">👥 Team Members</h3>

        <div class="mt-3 overflow-x-auto">
          <table class="min-w-full text-sm">
            <thead>
              <tr class="text-left text-muted-foreground">
                <th class="py-2 pr-4">Name</th>
                <th class="py-2 pr-4">Username</th>
                <th class="py-2">Role</th>
              </tr>
            </thead>
            <tbody class="divide-y divide-border">
              <tr v-for="member in team" :key="member.userId">
                <td class="py-2 pr-4">{{ member.fullName }}</td>
                <td class="py-2 pr-4 text-muted-foreground">{{ member.username }}</td>
                <td class="py-2">
                  <span class="inline-flex items-center rounded-full bg-secondary px-2.5 py-1 text-xs font-medium text-foreground">
                    {{ member.userType }}
                  </span>
                </td>
              </tr>
              <tr v-if="team.length === 0">
                <td colspan="3" class="py-4 text-center text-muted-foreground">
                  No team members found.
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </section>
  </BaseLayout>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import BaseLayout from '../../layouts/BaseLayout.vue'
import { useIssueStore } from '../../stores/issue'
import { useCommentStore } from '../../stores/comments'
import { useTeamStore } from '../../stores/teams'

const route = useRoute()
const issueId = route.params.id as string

const issueStore = useIssueStore()
const commentStore = useCommentStore()
const teamStore = useTeamStore()

const issue = computed(() => issueStore.getIssueById(issueId))
const comments = computed(() => commentStore.comments)
const team = computed(() => teamStore.members || [])
const newComment = ref('')

const labelTypeMap: Record<number, string> = {
  0: 'Bug',
  1: 'Feature',
  2: 'Enhancement',
  3: 'Documentation',
  4: 'Urgent',
  5: 'Design',
  6: 'Test',
  7: 'Performance',
  8: 'Security',
  9: 'Backend',
  10: 'Frontend'
}

onMounted(async () => {
  await issueStore.fetchIssue(issueId)
  await commentStore.fetchComments(issueId)
  const projectId = issue.value?.project?.id
  if (projectId) {
    await teamStore.fetchTeam(projectId)
    console.log('Team data:', teamStore.members)
  }
})

const submitComment = async () => {
  if (!newComment.value.trim()) return
  await commentStore.createComment(issueId, newComment.value)
  newComment.value = ''
}

const formatDate = (date: string) => {
  return new Date(date).toLocaleString()
}

export interface TeamMember {
  userId: string
  fullName: string
  username: string
  userType: string
}
</script>
