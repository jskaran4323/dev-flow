import { defineStore } from 'pinia'
import { getComments, addComment } from '../services/devFlow/comments'

export interface Comment {
  id: string
  content: string
  createdAt: string
  updatedAt: string
}

export const useCommentStore = defineStore('comment', {
  state: () => ({
    comments: [] as Comment[],
    loading: false,
    error: ''
  }),
  actions: {
    async fetchComments(issueId: string) {
      this.loading = true
      try {
        this.comments = await getComments(issueId)
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Failed to load comments'
      } finally {
        this.loading = false
      }
    },

    async createComment(issueId: string, content: string) {
      try {
        const newComment = await addComment(issueId, { content })
        this.comments.push(newComment)
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Failed to create comment'
      }
    }
  }
})
