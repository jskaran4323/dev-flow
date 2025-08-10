import { defineStore } from "pinia";

import { getTeams } from "../services/devFlow/teams";


export interface TeamMember{
  userId: string
  fullName: string 
  username: string
  userType: string

}
export const useTeamStore = defineStore('team', {
    state: () => ({
      members: [] as TeamMember[],
      loading: false,
      error: ''
    }),
  
    actions: {
      async fetchTeam(projectId: string) {
        this.loading = true
        this.error = ''
        try {
          const response = await getTeams(projectId)
          this.members = response
          return response
        } catch (err: any) {
          this.error = err.message || 'Failed to fetch team'
        } finally {
          this.loading = false
        }
      }
    },
  
    getters: {
      assignableUsers(state) {
        return state.members.filter(m => ['DEVELOPER', 'TESTER', 'VIEWER'].includes(m.userType))
      }
    }
  })