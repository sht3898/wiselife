import Vue from 'vue'
import Router from 'vue-router'
import room from './components/room.vue'
import roomdetail from './components/roomdetail.vue'


// const originalPush = Router.prototype.push;
// Router.prototype.push = function push(location) {
//   return originalPush.call(this, location).catch(err => err)
// };

Vue.use(Router)


export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
		{
			path: '/',
			name: 'room',
			component: room
    },
    
    {
			path: '/roomdetail/:roomId',
			name: 'roomdetail',
			component: roomdetail,
			props:true
		},
		
  ]
})
