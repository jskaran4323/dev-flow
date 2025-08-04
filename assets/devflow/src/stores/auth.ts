import { defineStore } from 'pinia'
import { getCurrentUser } from '../services/authRequests' // assumes you have an endpoint to get user from token

export interface User {
  id: string
  username: string
  fullName: string
  email: string
  // add other fields if needed
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
    userId: (state): string | null => state.user?.id || null
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
