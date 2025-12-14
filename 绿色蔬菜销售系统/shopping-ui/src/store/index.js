import { createStore } from 'vuex'
import tab from './tab'

export default createStore({
  // 需要存储的值都放在这里面
  state() {
    return {
      token: '',
      userInfo: ''
    }
  },
  mutations: {
    setToken(state, token) {
      state.token = token;
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
  },
  getters: {
    getToken(state) {
      if(token === '') state.token = localStorage.getItem('token')
      return state.token
    },
    getUserInfo(state) {
      return state.userInfo
    }
  },
  actions: {},
  modules: {
    tab
  },
})
