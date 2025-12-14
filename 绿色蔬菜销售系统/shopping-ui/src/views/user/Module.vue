<template>
    <div v-for="item in modules" :key="item.id" class="module-wrapper">
        <h2 class="module-title">{{item.name}}</h2>
        <el-row :gutter="20">
            <el-col :span="6" v-for="sku in item.skus" :key="sku.id">
                <el-card :body-style="{ padding: '0px' }" class="sku-card">
                    <div class="image-wrapper">
                        <img :src="sku.img" class="image"/>
                    </div>
                    <div class="card-content">
                      <router-link :to="{path:'/shopping/detail/' + sku.id}" class="sku-name" :title="sku.name">{{sku.name}}</router-link>
                      <div class="price-section">
                        <span class="price-label">￥</span>
                        <span class="price-value">{{sku.price}}</span>
                      </div>
                      <div class="bottom">
                          <el-button class="action-btn add-btn" @click="addCart(sku)">加入购物车</el-button>
                          <el-button class="action-btn buy-btn" @click="showPayDialog(sku)">购买</el-button>
                      </div>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>

    <el-dialog v-model="payDialogVisible" title="购买提示" width="30%" center>
        <span>您好！本次订单价格为 {{orderDetail.price}} ，购买请点击支付</span>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="payDialogVisible = false">取消支付</el-button>
            <el-button type="primary" @click="pay">支付</el-button>
        </span>
        </template>
    </el-dialog>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted, computed} from 'vue'
import { get,post,del,patch } from '@/api/http'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useStore } from "vuex";

export default({
  setup() {
    const store = useStore()
    let modules = ref([])
    let payDialogVisible = ref(false)
    let order = ref({
        phone: null,
        orderDetails: []
    })
    let orderDetail = reactive({
        skuId: null,
        number: null,
        price: null
    })
    
    function addCart(sku) {
      let shoppingCars = JSON.parse(localStorage.getItem('shoppingcar'));
      if(shoppingCars === null) {
        shoppingCars = []
      }
      let result = shoppingCars.findIndex(item => item.id == sku.id)
      if(result === -1) {
        sku.number = 1
        shoppingCars.push(sku)
        localStorage.setItem('shoppingcar', JSON.stringify(shoppingCars))
        store.commit('addCarNumber')
        ElMessage({
            message: '亲，成功添加购物车',
            type: 'success',
        })
      } else {
        ElMessage({
          message: '亲，我已经躺在购物车里了',
          type: 'info',
        })
      }
    }

    function showPayDialog(val) {
      let userCode = localStorage.getItem('userCode');
      if (userCode == null || userCode.length === 0) {
          ElMessage({
              message: '哎呀！你还没登录呢！',
              type: 'info',
          })
          return;
      }
      payDialogVisible.value =true
      orderDetail.number = 1
      orderDetail.skuId = val.id
      orderDetail.price = val.price
      orderDetail.storeId = val.storeId
    }

    function pay() {
        order.value.phone = localStorage.getItem('userCode');
        order.value.orderDetails.push(orderDetail);
        post('order', order.value).then(res => {
            ElMessage({
                message: '亲！恭喜您支付成功',
                type: 'success',
            })
            payDialogVisible.value = false  
        })
        order.value.phone = '';
        order.value.orderDetails = [];
    }

    onMounted(() => {
        get('module/moduleSku', '').then(res => {
        modules.value = res
      })
    })

    return {
      modules,
      payDialogVisible,
      orderDetail,
      pay,
      addCart,
      showPayDialog,
    }
  },
})
</script>

<style lang="scss" scoped>
$primary-green: #2ecc71;
$light-green: #58d68d;
$dark-green: #27ae60;
$text-dark: #2c3e50;
$shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

.module-wrapper {
  margin-bottom: 40px;
}

.module-title {
  color: $text-dark;
  font-size: 24px;
  font-weight: 600;
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 3px solid $primary-green;
  display: inline-block;
}

.el-row {
  background: transparent;
}

.sku-card {
  margin-top: 20px;
  border-radius: 8px;
  box-shadow: $shadow;
  transition: all 0.3s ease;
  overflow: hidden;

  &:hover {
    box-shadow: 0 8px 24px rgba(46, 204, 113, 0.15);
    transform: translateY(-4px);
  }

  ::v-deep(.el-card__body) {
    padding: 0 !important;
  }
}

.image-wrapper {
  width: 100%;
  overflow: hidden;
  background: #f5f7fa;
  display: flex;
  align-items: center;
  justify-content: center;

  .image {
    width: 100%;
    height: auto;
    display: block;
    object-fit: contain;
    transition: transform 0.3s ease;
    padding: 12px 0;

    &:hover {
      transform: scale(1.05);
    }
  }
}

.card-content {
  padding: 16px;
  background: #ffffff;
}

.sku-name {
  display: block;
  color: $text-dark;
  font-size: 14px;
  font-weight: 500;
  text-decoration: none;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  display: -webkit-box;
  min-height: 36px;
  line-height: 18px;
  margin-bottom: 10px;
  transition: color 0.3s ease;

  &:hover {
    color: $primary-green;
  }
}

.price-section {
  margin-bottom: 12px;
  display: flex;
  align-items: baseline;

  .price-label {
    color: $primary-green;
    font-size: 14px;
    font-weight: 600;
  }

  .price-value {
    color: $primary-green;
    font-size: 24px;
    font-weight: 700;
    margin-left: 4px;
  }
}

.bottom {
  margin-top: 12px;
  display: flex;
  gap: 8px;
  justify-content: space-between;
}

.action-btn {
  flex: 1;
  height: 36px;
  font-size: 13px;
  font-weight: 500;
  border-radius: 4px;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;

  &.add-btn {
    background-color: #f0f8f5;
    color: $primary-green;
    border: 1px solid $primary-green;

    &:hover {
      background-color: $primary-green;
      color: #ffffff;
    }
  }

  &.buy-btn {
    background-color: $primary-green;
    color: #ffffff;
    border: 1px solid $primary-green;

    &:hover {
      background-color: $light-green;
      border-color: $light-green;
    }

    &:active {
      background-color: $dark-green;
    }
  }
}

// 响应式设计
@media (max-width: 1024px) {
  .module-title {
    font-size: 20px;
    margin-bottom: 16px;
    padding-bottom: 10px;
  }

  .sku-card {
    margin-top: 16px;
  }
}

@media (max-width: 768px) {
  .module-title {
    font-size: 18px;
    margin-bottom: 12px;
  }

  .card-content {
    padding: 12px;
  }

  .action-btn {
    height: 32px;
    font-size: 12px;
  }
}
</style>
