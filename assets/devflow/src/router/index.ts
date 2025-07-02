import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'
import DashBoard from '../pages/DashBoard.vue'
import Home from '../pages/Home.vue'
// ... your other routes

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/dashboard', component: DashBoard },
  // ... your other routes
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const auth = useAuthStore()


  if (!auth.token && typeof window !== 'undefined') {
    const storedToken = localStorage.getItem('token')
    if (storedToken) {
      auth.initializeAuth()
    }
  }

  
  if (auth.isAuthenticated && (to.path === '/login' || to.path === '/register')) {
    return next('/dashboard')
  }

  next()
})

export default router
