<template>
  <div class="header-wrapper">
    <div class="header-inner">
      <!-- 左侧：系统名称 -->
      <router-link :to="{path:'/shopping'}" class="logo-link">
        <span class="logo-icon">🥬</span>
        <span class="logo-text">绿色蔬菜销售系统</span>
      </router-link>

      <!-- 右侧：导航和用户操作 -->
      <div class="header-right">
       

        <!-- 未登录状态 -->
        <template v-if="!username">
          <router-link :to="{path:'/shopping/login'}" class="nav-link login-link">
            登录
          </router-link>
        </template>

        <!-- 已登录状态 -->
        <template v-else>
          <router-link :to="{path:'/shopping/userInfo'}" class="nav-link username-link" :title="username">
            欢迎您！{{ username }}
          </router-link>
        </template>

        <!-- 主导航 -->
        <router-link :to="{path:'/shopping/skuList'}" class="nav-link">
          商品中心
        </router-link>

        <!-- 公告导航 -->
        <router-link :to="{path:'/shopping/message'}" class="nav-link">
          系统公告
        </router-link>

        <!-- 购物车 -->
        <router-link :to="{path:'/shopping/shoppingCar'}" class="nav-link cart-link">
          <span class="icon-text">🛒</span>
          <span>购物车</span>
          <el-badge :value="carNumber" class="cart-badge" v-if="carNumber > 0" />
        </router-link>

        <!-- 我的订单 -->
        <router-link :to="{path:'/shopping/order'}" class="nav-link">
          <span class="icon-text">📋</span>
          <span>我的订单</span>
        </router-link>

        <!-- 退出登录 -->
        <a
          href="javascript:void(0);"
          v-if="username"
          @click="clearUserInfo"
          class="nav-link logout-link"
        >
          退出
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, computed} from 'vue'
import { useStore } from "vuex"
import { get,post,del,patch } from '@/api/http'
import { reactive, ref } from '@vue/reactivity'
import { useRouter } from "vue-router";

export default({
  setup() {
    const store = useStore()
    const router = useRouter()
    let phone = localStorage.getItem('userCode');
    let username = ref('')
    onMounted(() => {
      if (phone != null) {
        get('user/one?phone=' + phone, '').then(res => {
          username.value = res.nickName
          localStorage.setItem('userId', res.id)
        })
      }
      
    })

    let carNumber = computed(() => {
      return store.state.tab.carNumber
    })

    function clearUserInfo() {
      localStorage.removeItem('userCode');
      localStorage.removeItem('userId');
      router.push({path:'/shopping'});
      setTimeout(function () {
        window.location.reload();
      }, 10);
    }

    return{
      carNumber,
      username,
      clearUserInfo
    }
  },
})
</script>


<style lang="scss" scoped>
// 绿色蔬菜系统主色调
$primary-green: #2ecc71;
$light-green: #58d68d;
$dark-green: #27ae60;
$text-light: #ffffff;
$shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
$content-width: 1400px;

.header-wrapper {
  background: linear-gradient(135deg, $primary-green 0%, $light-green 100%);
  box-shadow: $shadow;
  position: relative;
  z-index: 500;
  height: 70px;
  display: flex;
  align-items: center;
}

.header-inner {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  max-width: $content-width;
  margin: 0 auto;
  padding: 0 40px;
  height: 100%;
}

// Logo 和系统名称
.logo-link {
  display: flex;
  align-items: center;
  gap: 12px;
  text-decoration: none;
  color: $text-light;
  font-weight: 600;
  font-size: 18px;
  transition: all 0.3s ease;
  flex-shrink: 0;

  &:hover {
    transform: scale(1.05);
    text-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  }

  .logo-icon {
    font-size: 28px;
    display: flex;
    align-items: center;
  }

  .logo-text {
    letter-spacing: 0.5px;
  }
}

// 右侧：导航和用户操作
.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
  flex-shrink: 0;
  margin-left: auto;

  .nav-link {
    color: $text-light;
    font-size: 14px;
    text-decoration: none;
    padding: 8px 12px;
    border-radius: 4px;
    transition: all 0.3s ease;
    position: relative;
    display: flex;
    align-items: center;
    gap: 6px;

    &:hover {
      background-color: rgba(255, 255, 255, 0.2);
      transform: translateY(-2px);

      &::after {
        width: 100%;
      }
    }

    &::after {
      content: '';
      position: absolute;
      bottom: -4px;
      left: 50%;
      transform: translateX(-50%);
      width: 0;
      height: 2px;
      background-color: $text-light;
      transition: width 0.3s ease;
    }

    .icon-text {
      font-size: 16px;
    }

    &.login-link {
      padding: 8px 16px;
      background-color: rgba(255, 255, 255, 0.25);
      border: 1px solid rgba(255, 255, 255, 0.3);

      &:hover {
        background-color: rgba(255, 255, 255, 0.35);
        border-color: rgba(255, 255, 255, 0.5);
      }
    }

    &.username-link {
      max-width: 150px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    &.logout-link {
      cursor: pointer;

      &:active {
        transform: translateY(0);
      }
    }

    .cart-badge {
      ::v-deep(.el-badge__content) {
        background-color: #e74c3c;
        color: $text-light;
        font-size: 10px;
        padding: 0 4px;
      }
    }
  }
}

// 响应式设计
@media (max-width: 1024px) {
  .header-inner {
    padding: 0 20px;
  }

  .header-right {
    gap: 12px;
  }
}

@media (max-width: 768px) {
  .header-wrapper {
    height: 60px;
  }

  .header-inner {
    padding: 0 15px;
    flex-wrap: wrap;
  }

  .logo-link {
    font-size: 16px;
    gap: 8px;

    .logo-icon {
      font-size: 24px;
    }
  }

  .header-right {
    gap: 8px;
    font-size: 12px;

    .nav-link {
      padding: 6px 8px;
      font-size: 12px;

      .icon-text {
        font-size: 14px;
      }
    }
  }
}
</style>