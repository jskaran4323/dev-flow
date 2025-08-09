import { defineStore } from 'pinia'
import { getCurrentUser, loginUser, logoutUser } from '../services/authRequests'

export interface User {
  userId: string
  username: string
  fullName: string
  email: string
  userType: string
  createdAt: string | null
}

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as User | null
  }),

  getters: {
    isAuthenticated: (state): boolean => !!state.user,
    userId: (state): string | null => state.user?.userId || null
  },

  actions: {
    async login(credentials: { identifier: string; password: string }): Promise<User | null> {
     const user =  await loginUser(credentials)        
            await this.fetchUser()
            return user              
    },

    async logout(): Promise<void> {
      await logoutUser()                
      this.user = null
    },

    async fetchUser(): Promise<User | null> {
      try {
        const res = await getCurrentUser()
        this.user = res
        return res
      } catch (err) {
        console.error('Failed to fetch user', err)
        this.user = null
        return null
      }
    },

    async initializeAuth(): Promise<void> {
      await this.fetchUser()
    }
  }
})
