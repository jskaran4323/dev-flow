// src/services/api.ts
import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  withCredentials: true, // ✅ Send cookies automatically!
  headers: {
    'Content-Type': 'application/json',
  },
})


export default api
