// 一、配置axios
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

// import store from '@/store/index' 如果使用vuex，那么token，userinfo都可以在登录以后存储到store中，不需要使用storage
// 获取浏览器的接口地址。
// let baseUrl = window.location.origin
// axios配置
axios.defaults.baseURL = 'http://127.0.0.1:9000'
// 设置请求最大时长
axios.defaults.timeout = 50000
axios.defaults.withCredentials = true

// 请求拦截器
axios.interceptors.request.use(config => {

  return config
}, error => {
  // 可以安装elementui等ui组件，将错误信息输出到界面。
  console.log(error)
  return Promise.reject(error)
})

// 2、封装请求方式
// @param url 接口地址
// @param data 携带参数
// @param file 上传文件对象
// @param auth 是否携带token
// get请求
export function get (url, data) {
  return axios.get(url, data)
}
// post请求
export function post (url, data) {
  return axios.post(url, data)
}
// put请求
export function put (url, data) {
  return axios.put(url, data)
}
// delete 请求
export function del (url, data) {
  return axios.delete(url, data)
}
// delete 请求
export function patch (url, data) {
  return axios.patch(url, data)
}
// upload 请求
export function uploader (url, file) {
  let params = new FormData()
  params.append('file', file)
  return axios.post(url, params)
}