import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig(({ mode }) => {
  const env = loadEnv(mode, process.cwd(), '')
  const isDev = env.VITE_API_BASE_URL?.includes('localhost')
  
  return {
    plugins: [vue()],
    server: {
      port: 5173,
      // Only use proxy for localhost
      ...(isDev && {
        proxy: {
          '/api': {
            target: env.VITE_API_BASE_URL,
            changeOrigin: true,
            secure: false,
            cookieDomainRewrite: '',
            configure: (proxy, _options) => {
              proxy.on('error', (err, _req, _res) => {
                console.log('API Proxy error:', err)
              })
              proxy.on('proxyReq', (_proxyReq, req, _res) => {
                console.log('API Proxying request:', req.method, req.url)
              })
            }
          },
          '/auth': {
            target: env.VITE_API_BASE_URL,
            changeOrigin: true,
            secure: false,
            cookieDomainRewrite: '',
            configure: (proxy, _options) => {
              proxy.on('error', (err, _req, _res) => {
                console.log('Auth Proxy error:', err)
              })
              proxy.on('proxyReq', (_proxyReq, req, _res) => {
                console.log('Auth Proxying request:', req.method, req.url)
              })
            }
          }
        }
      })
    }
  }
})