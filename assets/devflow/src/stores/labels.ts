import { defineStore } from 'pinia'
import { getProjectLabels,createLabel } from '../services/devFlow/labels'

export interface Label {
  id: string
  name: string
  type: string
}

export const useLabelStore = defineStore('label', {
  state: () => ({
    labels: [] as Label[],
    loading: false,
    error: ''
  }),
  actions: {
    async fetchLabels(projectId: string) {
      this.loading = true
      try {
        this.labels = await getProjectLabels(projectId)
        console.log(this.labels);
        
      } catch (err: any) {
        this.error = err.response?.data?.message || 'Failed to load labels'
      } finally {
        this.loading = false
      }
    },
    async addLabel(projectId: string, label: { name: string; type: string }) {
        const newLabel = await createLabel(projectId, label)
        this.labels.push(newLabel)
      }
  }
})
