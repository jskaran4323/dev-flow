import Vue from 'vue';
import Router from 'vue-router';

import UserDashBoard from '@/components/UserDashBoard.vue';
import UserForm from '@/components/UserForm.vue';
import LoginForm from '@/components/LoginForm.vue';
import HomePage from '@/components/HomePage.vue';


Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [ 
    { path: '/', name: 'Home', component: HomePage },
    { path: '/register', name: 'Register', component: UserForm },
    {path: '/dashboard', name:'DashBoard', component: UserDashBoard},
    {path: '/login', name:'Login', component: LoginForm}
  ]
});
