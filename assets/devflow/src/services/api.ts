// src/services/api.ts
import axios from 'axios'

const isLocalhost = import.meta.env.VITE_API_BASE_URL?.includes('localhost')

const api = axios.create({
  // Use proxy for localhost, direct URL for production
  baseURL: isLocalhost ? '/api' : `${import.meta.env.VITE_API_BASE_URL}/api`,
  withCredentials: true, 
  headers: {
    'Content-Type': 'application/json',
  },
})

export default api