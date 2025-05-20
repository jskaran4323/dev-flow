import Vue from 'vue';
import Router from 'vue-router';
import HelloWorld from '@/components/HelloWorld.vue';
import UserDashBoard from '@/components/UserDashBoard.vue';
import UserForm from '@/components/UserForm.vue';
import LoginForm from '@/components/LoginForm.vue';


Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [ 
    { path: '/', name: 'Home', component: HelloWorld },
    { path: '/register', name: 'Register', component: UserForm },
    {path: '/dashboard', name:'DashBoard', component: UserDashBoard},
    {path: '/login', name:'Login', component: LoginForm}
  ]
});
