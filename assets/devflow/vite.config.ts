import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        cookieDomainRewrite: '', // ✅ rewrite cookies for frontend domain
        configure: (proxy, options) => {
          proxy.on('error', (err, req, res) => {
            console.log('API Proxy error:', err)
          })
          proxy.on('proxyReq', (proxyReq, req, res) => {
            console.log('API Proxying request:', req.method, req.url)
          })
        }
      },
      '/auth': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        secure: false,
        cookieDomainRewrite: '', // ✅ same here
        configure: (proxy, options) => {
          proxy.on('error', (err, req, res) => {
            console.log('Auth Proxy error:', err)
          })
          proxy.on('proxyReq', (proxyReq, req, res) => {
            console.log('Auth Proxying request:', req.method, req.url)
          })
        }
      }
    }
    
    
  }
})