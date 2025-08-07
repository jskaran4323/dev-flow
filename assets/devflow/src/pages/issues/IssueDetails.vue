<template>
    <BaseLayout>
      <div class="container py-5 text-white" v-if="issue">
        <h2 class="mb-4">🐛 {{ issue.title }}</h2>
  
        <p class="mb-2"><strong>Description:</strong> {{ issue.description }}</p>
        <p class="mb-2">
          <strong>Status:</strong>
          <span class="badge bg-warning text-dark">{{ issue.status }}</span>
        </p>
  
        <p class="mb-2">
          <strong>Assignee:</strong>
          {{ issue.assignee?.fullName }} ({{ issue.assignee?.username }})
        </p>
  
        <div class="mb-3">
          <strong>Labels:</strong>
          <span
            v-for="label in issue.labels"
            :key="label.id"
            class="badge bg-info text-dark me-1"
          >
            {{ label.name }} - {{ labelTypeMap[label.type] || 'Unknown' }}
          </span>
        </div>
  
        <hr class="border-light" />
  
        <!-- Comments Section -->
        <div class="mt-4">
          <h4 class="text-white">💬 Comments</h4>
  
          <div v-if="comments.length === 0" class="text-muted">No comments yet.</div>
  
          <div v-for="comment in comments" :key="comment.id" class="bg-light text-dark p-2 mb-2 rounded">
            <p class="mb-1">{{ comment.content }}</p>
            <small class="text-muted">{{ formatDate(comment.createdAt) }}</small>
          </div>
  
          <form @submit.prevent="submitComment" class="mt-3">
            <textarea v-model="newComment" class="form-control" rows="3" placeholder="Add a comment..."></textarea>
            <button type="submit" class="btn btn-primary mt-2">Post Comment</button>
          </form>
        </div>
      </div>
    </BaseLayout>
  </template>
  
  <script setup lang="ts">
  import { ref, computed, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import BaseLayout from '../../layouts/BaseLayout.vue'
  import { useIssueStore } from '../../stores/issue'
  import { useCommentStore } from '../../stores/comments'
  
  const route = useRoute()
  const issueId = route.params.id as string
  
  const issueStore = useIssueStore()
  const commentStore = useCommentStore()
  
  const issue = computed(() => issueStore.getIssueById(issueId))
  const comments = computed(() => commentStore.comments)
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
    await issueStore.fetchIssue(issueId) // loads full issue details
    await commentStore.fetchComments(issueId) // loads all comments
  })
  
  const submitComment = async () => {
    if (!newComment.value.trim()) return
    await commentStore.createComment(issueId, newComment.value)
    newComment.value = ''
  }
  
  const formatDate = (date: string) => {
    return new Date(date).toLocaleString()
  }
  </script>
  