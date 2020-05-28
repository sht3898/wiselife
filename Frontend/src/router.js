import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import TeamPage from './views/TeamPage.vue'
import ErrorPage from './views/ErrorPage.vue'
import ResultPage from './views/ResultPage.vue'
import LoginPage from './views/LoginPage.vue'
import ContentWritePage from './views/ContentWritePage.vue'
import ContentDetailPage from './views/ContentDetailPage.vue'
import MyPage from './views/MyPage.vue'
import SurveyResultPage from './views/SurveyResultPage.vue'

const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

Vue.use(Router)

// const requireAuth = () => (to, from, next) => {
// 	if (sessionStorage.getItem('accessToken') == null) {	
// 		alert("접근하실 수 없습니다. 로그인 해주세요!")	
// 		return next('/');
// 	}else{
// 		return next();
// 	}			
//   };

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
			path: '/login',
			name: 'loginPage',
			component: LoginPage
		},
		{
			path: '/team',
			name: 'teamPage',
			component: TeamPage
		},	
		{
			path : '*',
			name : 'errorPage',
			component: ErrorPage
        },
		{
			path : '/result/:category/:keyword',
			name : 'resultPage',
			component: ResultPage,
			props : true
		},
		{
			path : '/contentwrite',
			name : 'contentWritePage',
			component: ContentWritePage	
		},
		{
			path : '/contentdetail/:seq',
			name : 'contentDetailPage',
			component: ContentDetailPage,
			props : true	
		},
		{
			path : '/mypage',
			name : 'myPage',
			component: MyPage
		},
		{
			path : '/surveyresult',
			name : 'surveyResultPage',
			component : SurveyResultPage
		}
  ]
})
