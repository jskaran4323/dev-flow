import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  headers: {
    'Content-Type': 'application/json',
  },
})
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token')
  
  if (token && token !== 'undefined' && token !== 'null') {
    config.headers.Authorization = `Bearer ${token}`
  } else {
    console.warn('[Axios] No valid token found — not setting Authorization header.')
  }

  return config
})

export default api
