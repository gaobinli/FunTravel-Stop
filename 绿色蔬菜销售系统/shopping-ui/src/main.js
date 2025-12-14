import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import elementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'normalize.css'
import "@/assets/icons/index"
import svgIcon from "@/components/SvgIcon/index.vue";
import zhCn from 'element-plus/es/locale/lang/zh-cn'

import * as ElIconModules from '@element-plus/icons'

import VueWangeditor from './components/npm/index.umd.min'


const app = createApp(App)
app.config.globalProperties.$imgUpload = 'http://127.0.0.1:9000/upload';

app.use(elementPlus, {locale: zhCn, size: 'default' });
app.component('svg-icon', svgIcon)
// 统一注册el-icon图标
for(let iconName in ElIconModules){
    app.component(transElIconName(iconName),ElIconModules[iconName])
}
app.use(store)
app.use(router)
app.use(VueWangeditor)
app.mount('#app')

export function transElIconName(iconName){
    return 'i'+iconName.replace(/[A-Z]/g,(match)=>'-'+match.toLowerCase())
}