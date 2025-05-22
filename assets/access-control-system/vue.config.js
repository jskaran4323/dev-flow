const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: [
    'vuetify'
  ],
  devServer: {
    proxy: {
      '/': {
        target: 'http://backend:8081',
        changeOrigin: true,
      }
    }
  }
})
