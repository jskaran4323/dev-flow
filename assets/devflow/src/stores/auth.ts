import { defineStore } from 'pinia'
import { getCurrentUser } from '../services/authRequests'

export interface User {
  userId: string
  username: string
  fullName: string
  email: string
  userType: string
  createdAt: string | null
}

export const useAuthStore = defineStore('auth', {
  state: () => {
    let initialToken: string | null = null
    if (typeof window !== 'undefined' && window.localStorage) {
      initialToken = localStorage.getItem('token')
    }

    return {
      token: initialToken,
      user: null as User | null
    }
  },

  getters: {
    isAuthenticated: (state): boolean => !!state.token,
    userId: (state): string | null => state.user?.userId || null
  },

  actions: {
    login(token: string): void {
      this.token = token
      localStorage.setItem('token', token)
    },

    logout(): void {
      this.token = null
      this.user = null
      localStorage.removeItem('token')
    },

    async fetchUser(): Promise<void> {
      if (!this.token) return
      try {
        const res = await getCurrentUser()
        
        this.user = res
      } catch (err) {
        console.error('Failed to fetch user', err)
        this.logout()
      }
    },

    initializeAuth(): void {
      const storedToken = localStorage.getItem('token')
      if (storedToken) {
        this.token = storedToken
      }
    }
  }
})
