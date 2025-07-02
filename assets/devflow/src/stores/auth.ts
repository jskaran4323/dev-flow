import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
  state: () => {
    // Initialize token from localStorage safely
    let initialToken: string | null = null
    if (typeof window !== 'undefined' && window.localStorage) {
      initialToken = localStorage.getItem('token')
    }
    
    return {
      token: initialToken,
    }
  },
  getters: {
    isAuthenticated: (state): boolean => !!state.token,
  },
  actions: {
    login(token: string): void {
      this.token = token
      if (typeof window !== 'undefined' && window.localStorage) {
        localStorage.setItem('token', token)
      }
    },
    logout(): void {
      this.token = null
      if (typeof window !== 'undefined' && window.localStorage) {
        localStorage.removeItem('token')
      }
    },
    // Keep this method for manual initialization if needed
    initializeAuth(): void {
      if (typeof window !== 'undefined' && window.localStorage) {
        const storedToken = localStorage.getItem('token')
        if (storedToken) {
          this.token = storedToken
        }
      }
    }
  },
})