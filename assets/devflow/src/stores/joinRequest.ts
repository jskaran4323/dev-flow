// src/stores/joinRequest.ts
import { defineStore } from 'pinia'
import { fetchJoinRequests, approveJoinRequest } from '../services/devFlow/joinRequest'

export interface JoinRequest {
  userId: string
  username: string
  fullName: string
}

export const useJoinRequestStore = defineStore('joinRequest', {
  state: () => ({
    requests: [] as JoinRequest[],
    loading: false,
    error: null as string | null,
    loadingUserId: null as string | null
  }),

  actions: {
    async fetchRequests(projectId: string) {
      this.loading = true
      this.error = null
      try {
        const res = await fetchJoinRequests(projectId)
        console.log(res);
        
        this.requests = res.data
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Failed to load join requests'
      } finally {
        this.loading = false
      }
    },

    async approveRequest(projectId: string, userId: string) {
      this.loadingUserId = userId
      try {
        await approveJoinRequest(projectId, userId)
        this.requests = this.requests.filter(r => r.userId !== userId)
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Approval failed'
      } finally {
        this.loadingUserId = null
      }
    }
  }
})
