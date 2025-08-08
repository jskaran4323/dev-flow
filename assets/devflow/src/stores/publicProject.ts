import { defineStore } from 'pinia'
import { getPublicProjects } from '../services/devFlow/project'

// 1. ✅ Define the interface
export interface PublicProject {
  id: string
  name: string
  description: string
  status: 'ACTIVE' | 'IN_PROGRESS' | 'COMPLETED'
  createdAt: string
  updatedAt: string
  owner: {
    id: string
    username: string
    fullName: string
  }
}

export const usePublicProjectStore = defineStore('publicProject', {
  state: () => ({
    publicProjects: [] as PublicProject[],
    loading: false,
    error: null as string | null
  }),

  
  getters: {
    getAllPublicProjects: (state): PublicProject[] => state.publicProjects,

    getPublicProjectById: (state) => (id: string): PublicProject | undefined =>
      state.publicProjects.find(project => project.id === id)
  },

  actions: {
    async fetchProjects() {
      this.loading = true
      this.error = null
      

      try {
        const res = await getPublicProjects()
        console.log(res);
        
        this.publicProjects = res 
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Failed to fetch projects'
      } finally {
        this.loading = false
      }
    }
  }
})
