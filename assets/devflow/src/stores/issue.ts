import { defineStore } from "pinia";
import { getIssueById, getProjectIssues, updateIssue, deleteIssue, addIssue } from "../services/devFlow/issue";



export interface Label {
    id: string
    type: number
  }
export interface Issue {
    id: string
    title: string,
    description: string 
    status: 'OPEN' | 'IN_PROGRESS' | 'CLOSED'
    assignee: {
        userId: string
        username: string,
        fullName: string,
        userType: string
    },
    labels: Label[]
    project: {
        id: string
        name: string
    },
    createdAt: string
    updatedAt: string
}

export const useIssueStore = defineStore('issue', {
    state: () => ({
        issues: [] as Issue[],
        selectedIssue: null as Issue | null,
        loading: false,
        error: "" as string | null,
        suggestedLabels: [] as number[]
    }),
    getters: {
        getIssueById: (state) => (id: string) => {
            return state.issues.find(p => p.id === id) || null
        }
    },
    actions: {
        async fetchIssues(projectId: string) {
            this.loading = true
            this.error = null

            try {
                const data = await getProjectIssues(projectId)
                console.log("store", data);
                this.issues = data
            } catch (error: any) {
                this.error = error.response?.data?.message || 'Failed to load projects'
            } finally {
                this.loading = false
            }
        },

        async fetchIssue(id: string) {
            this.loading = true
            this.error = null
            try {
                const data = await getIssueById(id)
                this.selectedIssue = data
                const index = this.issues.findIndex(p => p.id === id)
                if (index === -1) {
                    this.issues.push(data)
                } else {
                    this.issues[index] = data
                }
                return data
            } catch (error: any) {
                this.error = error.response?.data?.message || 'Failed to load issue'
            } finally {
                this.loading = false
            }
        },

        async createIssue(projectId: string, data: {
            title: string
            description: string
            status: string
            assigneeId: string
            labels: number[]
        }) {
            this.error = null
            try {
                const newIssue = await addIssue(projectId, data)
                this.issues.unshift(newIssue)
                return newIssue
            } catch (error: any) {
                this.error = error.response?.data?.message || 'Failed to create issue'
            }
        },

        async updateIssue(id: string, data: {
            title: string,
            description: string,
            status: string,
            labels: number[]
        }) {
            this.error = null
            try {
                const updatedIssue = await updateIssue(id, data)
                const index = this.issues.findIndex(p => p.id === id)
                if (index !== -1) {
                    this.issues[index] = updatedIssue
                }
                return updatedIssue
            } catch (err: any) {
                this.error = err.response?.data?.message || 'Failed to update issue'
                throw err
            }
        },

        async deleteIssue(id: string) {
            this.error = null
            try {
                await deleteIssue(id)
                this.issues = this.issues.filter(p => p.id !== id);
            } catch (err: any) {
                this.error = err.response?.data?.message || 'Failed to delete project'
                throw err
            }
        },
    //     async fetchAISuggestions(title: string, description: string) {
    //         this.error = null
    //         try {
    //             const data = await getAISuggestions({ title, description })
    //             this.suggestedLabels = data.suggestedLabels // Expected [0, 2, 4]
    //         } catch (err: any) {
    //             this.error = err.response?.data?.message || 'AI label suggestion failed'
    //         }
    // }
}
})
