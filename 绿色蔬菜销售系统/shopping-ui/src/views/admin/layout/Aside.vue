<template>
  <el-menu
    class="el-menu-vertical-demo"
    :collapse="isCollapse"  
    @open="handleOpen"
    @close="handleClose"
    background-color="#545c64"
    text-color="#fff"
  >
    <h3 v-show="!isCollapse">绿色蔬菜销售系统后台</h3>
    <h3 v-show="isCollapse">蔬菜</h3>

    <template v-for="item in menus" :index="item.path" :key="item.path">
      <template v-if="!item.hasChildren">
        <el-menu-item :index="item.path" @click="clickMenu(item)">
          <el-icon :size="20"><component :is="item.icon" /></el-icon>
          <template #title>{{item.name}}</template>
        </el-menu-item>
      </template>
      <template v-if="item.hasChildren">
        <el-sub-menu :index="item.name">
          <template #title>
            <el-icon :size="20"><component :is="item.icon" /></el-icon>
            <span >{{item.name}}</span>
          </template>
          <template v-for="subItem in item.childrens" :key="subItem.path">
            <el-menu-item-group>
              <el-menu-item :index="subItem.path"  @click="clickMenu(subItem)">
                <el-icon :size="20"><component :is="subItem.icon" /></el-icon>
                <template #title>{{subItem.name}}</template>
              </el-menu-item>
            </el-menu-item-group>
          </template>
        </el-sub-menu>
      </template>
      
    </template>
  </el-menu>
</template>
<script>
import { computed, reactive, ref } from 'vue'
import {
  Setting,
  HomeFilled,
  Menu,
  UserFilled,
  Place,
  View,
  Tickets,
  MagicStick,
  ShoppingBag,
  User,
  DocumentChecked
} from '@element-plus/icons'
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { onMounted} from 'vue'
import { get } from '@/api/request';
export default({
  components: {
    Setting,
    HomeFilled,
    Menu,
    UserFilled,
    Place,
    View,
    Tickets,
    MagicStick,
    ShoppingBag,
    User,
    DocumentChecked
  },
  setup() {
    const router = useRouter()
    // const isCollapse = ref(false)
    const store = useStore()
    let menus = ref([])

    onMounted(() => {
      get('sysMenu/permisson/menuTree', '').then(res => {
        menus.value = res
      })
    })

    function clickMenu(item) {
      router.push({ path: item.path })
      store.commit('selectMenu', item)
    }

    const handleOpen = (key, keyPath) => {
      console.log(key, keyPath)
    }
    const handleClose = (key, keyPath) => {
      console.log(key, keyPath)
    }
    let isCollapse = computed(() => {
      return store.state.tab.isCollapse
    })
    function showMenus() {
      console.log('showMenus；；；；', menus)
    }
    return {
      isCollapse,
      menus,
      handleOpen,
      handleClose,
      clickMenu,
      showMenus,
    }
  },
})
</script>

<style lang="scss" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
  // height: 100%;
}

.el-menu {
  height: 100%;
  border: none;
  h3 {
    margin: 0;
    color: #ffffff;
    text-align: center;
    line-height: 48px;
  }
}

/* .h3-title {
  margin-top: 0px;
  text-align: center;
} */
</style>
