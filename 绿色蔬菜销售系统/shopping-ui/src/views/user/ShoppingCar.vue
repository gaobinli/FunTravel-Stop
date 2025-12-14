<template>
    <div class="shopping-car-wrapper">
        <!-- 购物车表头 -->
        <div class="cart-header">
            <h2>我的购物车</h2>
            <span class="cart-count" v-if="tableData && tableData.length > 0">
                共 {{ tableData.length }} 件商品
            </span>
        </div>

        <!-- 购物车内容 -->
        <div class="cart-content" v-if="tableData && tableData.length > 0">
            <el-table
                :data="tableData"
                style="width: 100%"
                @selection-change="handleSelectionChange"
                class="cart-table"
            >
                <el-table-column type="selection" width="50" />
                <el-table-column prop="img" label="商品图片">
                    <template #default="scope">
                        <div class="product-image">
                            <img :src="scope.row.img" alt="商品图片" />
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="商品名称">
                    <template #default="scope">
                        <span class="product-name">{{ scope.row.name }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="单价">
                    <template #default="scope">
                        <span class="product-price">￥{{ scope.row.price }}</span>
                    </template>
                </el-table-column>
                <el-table-column prop="number" label="数量">
                    <template #default="scope">
                        <div class="quantity-control">
                            <el-input-number
                                v-model="scope.row.number"
                                :min="1"
                                :max="9999"
                                @change="handleChange"
                                size="small"
                            />
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="total" label="小计">
                    <template #default="scope">
                        <span class="product-total">￥{{ (scope.row.price * scope.row.number).toFixed(2) }}</span>
                    </template>
                </el-table-column>
                <el-table-column width="100" label="操作">
                    <template #default="scope">
                        <el-button
                            type="danger"
                            size="small"
                            @click="del(scope.row.id)"
                            class="delete-btn"
                        >
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- 购物车为空 -->
        <div class="empty-cart" v-else>
            <div class="empty-icon">🛒</div>
            <p class="empty-text">购物车为空</p>
            <router-link :to="{ path: '/shopping' }" class="continue-shopping">
                继续购物
            </router-link>
        </div>

        <!-- 结算区域 -->
        <div class="settlement-section" v-if="tableData && tableData.length > 0">
            <div class="settlement-summary">
                <div class="summary-item">
                    <span class="label">商品总数：</span>
                    <span class="value">{{ getTotalQuantity() }} 件</span>
                </div>
                <div class="summary-item">
                    <span class="label">合计金额：</span>
                    <span class="total-price">￥{{ countPrize.toFixed(2) }}</span>
                </div>
            </div>
            <div class="settlement-actions">
                <el-button @click="$router.push('/shopping')" class="continue-btn">
                    继续购物
                </el-button>
                <el-button type="primary" @click="settle" class="settle-btn">
                    去结算
                </el-button>
            </div>
        </div>
    </div>

    <!-- 支付确认对话框 -->
    <el-dialog v-model="payDialogVisible" title="购买提示" width="30%" center class="pay-dialog">
        <span>您好！本次订单价格为 {{countPrize.toFixed(2)}} ，购买请点击支付</span>
        <template #footer>
            <span class="dialog-footer">
                <el-button @click="payDialogVisible = false">取消支付</el-button>
                <el-button type="primary" @click="pay">支付</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script>
import { onMounted } from "vue"
import { reactive, ref } from '@vue/reactivity'
import { ElMessage, ElMessageBox } from 'element-plus'
import { get,post,del,patch } from '@/api/http'
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default ({
    setup() {
        const router = useRouter()
        const store = useStore()
        let tableData = ref([])
        let countPrize = ref(0)
        let skus = ref([])
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

        onMounted(() => {
            tableData.value = JSON.parse(localStorage.getItem('shoppingcar'))
        })

        function handleChange() {
            localStorage.setItem('shoppingcar', JSON.stringify(tableData.value))
            let val = []
            countPrize.value = 0
            tableData.value.forEach((item) => {
                skus.value.forEach((item2)=> {
                    if (item.id === item2.id) {
                        countPrize.value = countPrize.value + item.price * item.number
                        val.push(item)
                    }
                })
            })
            skus.value = val
        }

        function handleSelectionChange(val) {
            countPrize.value = 0
            val.forEach((item) => {
                countPrize.value = countPrize.value + item.price * item.number
            })
            skus.value = val
        }

        function del(val) {
            let storage = JSON.parse(localStorage.getItem('shoppingcar'))
            let result = storage.findIndex(item => item.id == val)
            storage.splice(result, 1)
            localStorage.setItem('shoppingcar', JSON.stringify(storage))
            tableData.value = storage
            store.commit('decrCarNumber')
        }

        function getTotalQuantity() {
            let total = 0
            tableData.value.forEach((item) => {
                total += item.number || 0
            })
            return total
        }

        function settle() {
            let userCode = localStorage.getItem('userCode');
            if (userCode == null || userCode.length === 0) {
                ElMessage({
                    message: '哎呀！你还没登录呢！',
                    type: 'info',
                })
                return;
            }


            if(skus.value.length > 0) {
                payDialogVisible.value = true
            } else if(skus.value.length == 0 && tableData.value.length > 0) {
                ElMessage({
                    message: '哎呀！你还没选择要结算的商品呢！',
                    type: 'info',
                })
            } else {
                ElMessage({
                    message: '亲，购物车空空如也呢！',
                    type: 'info',
                })
            }
        }

        function pay() {
            order.value.phone = localStorage.getItem('userCode');
            let tempArr = [];
            skus.value.forEach((item) => {
                orderDetail = {}
                orderDetail.number = item.number
                orderDetail.skuId = item.id
                orderDetail.price = item.price
                orderDetail.storeId = item.storeId
                tempArr.push(orderDetail)
            })
            order.value.orderDetails = tempArr;
            console.log('order===', order.value)
            post('order', order.value).then(res => {
                for (let i = tableData.value.length - 1; i >= 0 ; i--){
                    let result = skus.value.findIndex(item => item.id == tableData.value[i].id)
                    if(result != -1) {
                        tableData.value.splice(i, 1)
                    }
                }
                localStorage.setItem('shoppingcar', JSON.stringify(tableData.value))
                ElMessage({
                    message: '亲！恭喜您支付成功',
                    type: 'success',
                })
                payDialogVisible.value = false
                order.value.phone = '';
                order.value.orderDetails = [];
            })
        }

        return{
            tableData,
            countPrize,
            pay,
            settle,
            payDialogVisible,
            handleChange,
            del,
            handleSelectionChange,
            skus,
            getTotalQuantity
        }
    },
})
</script>

<style lang="scss" scoped>
// 绿色蔬菜系统主色调
$primary-green: #2ecc71;
$light-green: #58d68d;
$dark-green: #27ae60;
$text-dark: #2c3e50;
$text-light: #ffffff;
$shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
$border-color: #ecf0f1;

.shopping-car-wrapper {
  padding: 40px 80px;
  background-color: #f8fafb;
  min-height: 100vh;
}

.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 3px solid $primary-green;

  h2 {
    color: $text-dark;
    font-size: 28px;
    font-weight: 700;
    margin: 0;
  }

  .cart-count {
    color: $primary-green;
    font-size: 16px;
    font-weight: 500;
  }
}

.cart-content {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: $shadow;
  padding: 20px;
  margin-bottom: 30px;
  overflow-x: auto;
}

.cart-table {
  ::v-deep(.el-table__header th) {
    background-color: #f5f7fa;
    color: $text-dark;
    font-weight: 600;

    .el-table__cell {
      padding: 16px 8px;
    }
  }

  ::v-deep(.el-table__body tr) {
    &:hover > td {
      background-color: #fafbfc !important;
    }
  }

  ::v-deep(.el-table__body td) {
    padding: 16px 8px;
    border-bottom: 1px solid $border-color;
  }
}

.product-image {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 100px;
  background-color: #f5f7fa;
  border-radius: 6px;
  overflow: hidden;

  img {
    max-width: 100%;
    max-height: 100%;
    object-fit: contain;
  }
}

.product-name {
  color: $text-dark;
  font-size: 14px;
  font-weight: 500;
  display: block;
  overflow: hidden;
  text-overflow: ellipsis;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  display: -webkit-box;
}

.product-price,
.product-total {
  color: $primary-green;
  font-size: 16px;
  font-weight: 700;
}

.quantity-control {
  display: flex;
  align-items: center;

  ::v-deep(.el-input-number) {
    .el-input-number__increase,
    .el-input-number__decrease {
      background-color: transparent;
      border-color: $border-color;

      &:hover {
        color: $primary-green;
        border-color: $primary-green;
      }
    }

    .el-input__inner {
      border-color: $border-color;

      &:focus {
        border-color: $primary-green;
      }
    }
  }
}

.delete-btn {
  ::v-deep(.el-button--danger) {
    background-color: #f5f7fa;
    border-color: $border-color;
    color: #e74c3c;

    &:hover {
      background-color: #e74c3c;
      border-color: #e74c3c;
      color: $text-light;
    }
  }
}

.empty-cart {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: $shadow;
  padding: 60px 40px;
  text-align: center;
  margin-bottom: 30px;

  .empty-icon {
    font-size: 80px;
    margin-bottom: 20px;
  }

  .empty-text {
    color: $text-dark;
    font-size: 18px;
    margin-bottom: 30px;
  }

  .continue-shopping {
    display: inline-block;
    padding: 12px 32px;
    background-color: $primary-green;
    color: $text-light;
    text-decoration: none;
    border-radius: 4px;
    font-weight: 600;
    transition: all 0.3s ease;

    &:hover {
      background-color: $light-green;
      box-shadow: 0 4px 16px rgba(46, 204, 113, 0.3);
    }

    &:active {
      background-color: $dark-green;
    }
  }
}

.settlement-section {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: $shadow;
  padding: 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.settlement-summary {
  display: flex;
  gap: 60px;
  flex: 1;

  .summary-item {
    display: flex;
    align-items: baseline;
    gap: 12px;

    .label {
      color: $text-dark;
      font-size: 14px;
      font-weight: 500;
    }

    .value {
      color: $text-dark;
      font-size: 16px;
      font-weight: 600;
    }

    .total-price {
      color: $primary-green;
      font-size: 28px;
      font-weight: 700;
    }
  }
}

.settlement-actions {
  display: flex;
  gap: 12px;
  margin-left: 40px;

  .continue-btn {
    color: $primary-green;
    border-color: $primary-green;
    background-color: #f0f8f5;

    &:hover {
      background-color: $primary-green;
      color: $text-light;
      box-shadow: 0 4px 16px rgba(46, 204, 113, 0.3);
    }

    &:active {
      background-color: $dark-green;
      color: $text-light;
    }
  }

  .settle-btn {
    background-color: $primary-green;
    border-color: $primary-green;
    color: $text-light;
    min-width: 120px;

    &:hover {
      background-color: $light-green;
      border-color: $light-green;
      box-shadow: 0 4px 16px rgba(46, 204, 113, 0.3);
    }

    &:active {
      background-color: $dark-green;
    }
  }
}

// 支付对话框样式
::v-deep(.pay-dialog) {
  .el-dialog__header {
    border-bottom: 2px solid $border-color;
    padding: 20px 24px;
  }

  .el-dialog__title {
    color: $text-dark;
    font-weight: 600;
    font-size: 18px;
  }

  .el-dialog__body {
    padding: 28px 24px;
    font-size: 16px;
    color: $text-dark;
    line-height: 1.6;
  }

  .dialog-footer {
    padding: 16px 24px 20px;
    display: flex;
    justify-content: flex-end;
    gap: 12px;

    .el-button {
      height: 40px;
      font-size: 16px;
      font-weight: 600;
      border-radius: 4px;
      transition: all 0.3s ease;
      min-width: 100px;

      &.el-button--primary {
        background-color: $primary-green;
        border-color: $primary-green;
        color: $text-light;

        &:hover {
          background-color: $light-green;
          border-color: $light-green;
          box-shadow: 0 4px 16px rgba(46, 204, 113, 0.3);
        }

        &:active {
          background-color: $dark-green;
        }
      }

      &:not(.el-button--primary) {
        color: $text-dark;
        border-color: $border-color;
        background-color: #ffffff;

        &:hover {
          color: $primary-green;
          border-color: $primary-green;
          background-color: #f0f8f5;
        }

        &:active {
          color: $dark-green;
        }
      }
    }
  }
}

// 响应式设计
@media (max-width: 1024px) {
  .shopping-car-wrapper {
    padding: 30px 40px;
  }

  .cart-header {
    h2 {
      font-size: 24px;
    }
  }

  .settlement-section {
    flex-direction: column;
    gap: 20px;

    .settlement-summary {
      width: 100%;
      flex-direction: column;
      gap: 16px;
    }

    .settlement-actions {
      width: 100%;
      margin-left: 0;
    }
  }
}

@media (max-width: 768px) {
  .shopping-car-wrapper {
    padding: 20px;
  }

  .cart-header {
    flex-direction: column;
    gap: 12px;
    align-items: flex-start;

    h2 {
      font-size: 20px;
    }
  }

  .cart-table {
    ::v-deep(.el-table__header th) {
      font-size: 12px;

      .el-table__cell {
        padding: 12px 6px;
      }
    }

    ::v-deep(.el-table__body td) {
      padding: 12px 6px;
      font-size: 12px;
    }
  }

  .product-image {
    width: 80px;
    height: 80px;
  }

  .settlement-section {
    flex-direction: column;
    gap: 16px;
  }

  .settlement-summary {
    flex-direction: column;
    gap: 12px;
    width: 100%;

    .summary-item {
      width: 100%;
      justify-content: space-between;
    }
  }

  .settlement-actions {
    width: 100%;
    margin-left: 0;

    .settle-btn {
      min-width: 100px;
    }
  }
}
</style>
