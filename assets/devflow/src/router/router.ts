// src/router/index.ts
import { createRouter, createWebHistory } from 'vue-router'
import Login from '../assets/pages/auth/Login.vue'
import Register from '../assets/pages/auth/Register.vue'
import Home from '../assets/pages/Home.vue'
const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
