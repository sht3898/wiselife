import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import TeamPage from './views/TeamPage.vue'
import ErrorPage from './views/ErrorPage.vue'
import SignUp from './views/SignUp.vue'

Vue.use(Router)

const requireAuth = () => (to, from, next) => {
	console.log(store.state.accessToken);
	console.log(sessionStorage.getItem('accessToken'));
	if (sessionStorage.getItem('accessToken') == null) {	
		alert("접근하실 수 없습니다. 로그인 해주세요!")	
		return next('/');
	}else{
		return next();
	}			
  };

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
		{
			path: '/',
			name: 'home',
			component: Home
		},
		{
			path: '/team',
			name: 'team',
			component: TeamPage
		},	
		{
			path : '*',
			name : 'errorPage',
			component: ErrorPage
        },
        {
			path : '/signup',
			name : 'signUp',
			component: SignUp
		},
  ]
})
