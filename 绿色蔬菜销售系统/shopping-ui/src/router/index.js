import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import Shopping from '../views/user/Shopping.vue'
import Login from '../views/admin/Login.vue'
import Layout from '../views/admin/layout/Index.vue'

const routes = [
  {
    path: '/shopping',
    component: Shopping,
    children: [
      {
        path: '',
        name: 'Main',
        component: () => import('@/views/user/Main.vue')
      },
      {
        path: 'message',
        name: 'Message',
        component: () => import('@/views/user/Message.vue')
      },
      {
        path: 'announcementDetail/:id',
        name: 'AnnouncementDetail',
        component: () => import('@/views/user/AnnouncementDetail.vue')
      },
      {
        path: 'skuList',
        name: 'SkuList',
        component: () => import('@/views/user/SkuList.vue')
      },
      {
        path: 'detail/:id',
        name: 'SkuDetail',
        component: () => import('@/views/user/SkuDetail.vue')
      },
      {
        path: 'shoppingCar',
        name: 'ShoppingCar',
        component: () => import('@/views/user/ShoppingCar.vue')
      },
      {
        path: 'order',
        name: 'UserOrder',
        component: () => import('@/views/user/UserOrder.vue')
      },
      {
        path: 'login',
        name: 'UserLogin',
        component: () => import('@/views/user/UserLogin.vue')
      },
      {
        path: 'register',
        name: 'Register',
        component: () => import('@/views/user/Register.vue')
      },
      {
        path: 'userInfo',
        name: 'UserInfo',
        component: () => import('@/views/user/UserInfo.vue')
      }
    ]
  },
  {
    path: '/admin',
    component: Login
  },
  {
    path: '/admin/login',
    component: Login
  },
  {
    path: '/admin/merchantRegister',
    name: 'MerchantRegister',
    component: () => import('@/views/admin/MerchantRegister.vue')
  },
  {
    path: '/admin',
    component: Layout,
    children: [
      {
        path: '/admin/home',
        name: 'Home',
        component: () => import('@/views/admin/Home.vue')
      },
      {
        path: '/admin/user',
        name: 'User',
        component: () => import('@/views/admin/sys/User.vue')
      },
      {
        path: '/admin/merchants',
        name: 'Merchants',
        component: () => import('@/views/admin/sys/Merchants.vue')
      },
      {
        path: '/admin/store',
        name: 'Store',
        component: () => import('@/views/admin/sys/Store.vue')
      },
      {
        path: '/admin/announcement',
        name: 'Announcement',
        component: () => import('@/views/admin/sys/Announcement.vue')
      },
      {
        path: '/admin/order',
        name: 'Order',
        component: () => import('@/views/admin/sys/Order.vue')
      },
      {
        path: '/admin/carousel',
        name: 'Carousel',
        component: () => import('@/views/admin/sys/Carousel.vue')
      },
      {
        path: '/admin/module',
        name: 'Module',
        component: () => import('@/views/admin/sys/Module.vue')
      },
      {
        path: '/admin/skuAppraise',
        name: 'SkuAppraise',
        component: () => import('@/views/admin/sys/SkuAppraise.vue')
      },
      {
        path: '/admin/sku',
        name: 'Sku',
        component: () => import('@/views/admin/sys/Sku.vue')
      },
      {
        path: '/admin/sku/add',
        name: 'SkuAdd',
        component: () => import('@/views/admin/sys/SkuAdd.vue')
      },
      {
        path: '/admin/sku/update/:id',
        name: 'SkuUpdate',
        component: () => import('@/views/admin/sys/SkuUpdate.vue')
      },
      {
        path: '/admin/sysUser',
        name: 'SysUser',
        component: () => import('@/views/admin/sys/SysUser.vue')
      },
      {
        path: '/admin/sysMenu',
        name: 'SysMenu',
        component: () => import('@/views/admin/sys/SysMenu.vue')
      },
      {
        path: '/admin/sysRole',
        name: 'SysRole',
        component: () => import('@/views/admin/sys/SysRole.vue')
      }
    ]
  }
]



const router = createRouter({
  mode: 'history', // 去掉url中的#
  history: createWebHistory(process.env.BASE_URL),
  // history: createWebHashHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  console.log('to', to)

  // 所有 /shopping 开头的路由都不需要 adminToken，直接放行
  if(to.path.startsWith('/shopping')) {
    next()
    return
  }

  // 如果已登录且访问的是登录页或注册页，则跳转到首页
  if(localStorage.getItem('adminToken') && (to.path=='/admin' || to.path == '/admin/login')) {
    next({path: '/admin/home'})
    return
  }

  // 如果访问的是注册页或登录页，不需要token，直接放行
  if(to.path == '/admin/merchantRegister' || to.path == '/admin/login' || to.path == '/admin') {
    next()
    return
  }

  // 访问其他需要登录的页面，检查token
  if(!localStorage.getItem('adminToken')) {
    next({path: '/admin/login'})
  } else {
    next()
  }
})

export default router
