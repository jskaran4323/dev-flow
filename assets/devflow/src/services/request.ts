import api from './api'

const request = {
  get: async (url: string, params?: any) => {
    const res = await api.get(url, { params })
    return res.data
  },

  post: async (url: string, data?: any) => {
    const res = await api.post(url, data)
    console.log(res.data);

    return res.data
  },

  put: async (url: string, data?: any) => {
    const res = await api.put(url, data)
    return res.data
  },

  delete: async (url: string) => {
    const res = await api.delete(url)
    return res.data
  },
}

export default request
