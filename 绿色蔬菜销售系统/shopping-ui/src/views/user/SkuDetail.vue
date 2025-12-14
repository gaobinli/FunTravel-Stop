<template>
    <div class="common-layout">
        <el-container>
            <el-aside width="400px" ><img :src="sku.img" class="image"/></el-aside>
            <el-main>
                <h4 class="title-class">{{sku.name}}</h4>
                <el-form label-width="100px" class="item-class" id="selectForm">
                    <el-form-item label="分类：">{{sku.moduleName}}</el-form-item>
                    <el-form-item label="价格：">￥{{sku.price}}</el-form-item>
                    <el-form-item label="剩余量：">{{sku.inventory}}</el-form-item>
                    <el-form-item class="button-group">
                        <el-button type="primary" size="large" @click="showPayDialog(sku)" class="buy-btn">
                            立即购买 ￥{{sku.price}}
                        </el-button>
                        <el-button size="large" @click="addCart(sku)" class="cart-btn">
                            加入购物车
                        </el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>

        <el-tabs
            v-model="activeName"
            style="margin-top:40px"
            type="card"
            class="demo-tabs"
            @tab-click="handleClick"
        >
            <el-tab-pane label="商品详情介绍">
                <span v-html="sku.detail"></span>
            </el-tab-pane>
            <el-tab-pane label="商品评价">
                <div class="appraise-container">
                    <div v-if="dataTable.list && dataTable.list.length > 0" class="appraise-list">
                        <div
                            v-for="appraise in dataTable.list"
                            :key="appraise.id"
                            class="appraise-item"
                        >
                            <!-- 用户信息区 -->
                            <div class="appraise-header">
                                <div class="user-info">
                                    <img :src="userImg" alt="用户头像" class="user-avatar" />
                                    <div class="user-details">
                                        <p class="user-name">{{ appraise.userName }}</p>
                                        <p class="appraise-time">{{ appraise.createTime.replace('T', ' ') }}</p>
                                    </div>
                                </div>
                            </div>

                            <!-- 评分区 -->
                            <div class="appraise-ratings">
                                <div class="rating-item">
                                    <span class="rating-label">商品评分：</span>
                                    <el-rate
                                        v-model="appraise.skuAppraise"
                                        disabled
                                        size="small"
                                    />
                                </div>
                                <div class="rating-item">
                                    <span class="rating-label">物流评分：</span>
                                    <el-rate
                                        v-model="appraise.logisticeAppraise"
                                        disabled
                                        size="small"
                                    />
                                </div>
                            </div>

                            <!-- 评价内容区 -->
                            <div class="appraise-content">
                                <p class="content-text">{{ appraise.content }}</p>
                            </div>
                        </div>
                    </div>

                    <!-- 空评价提示 -->
                    <div v-else class="empty-appraise">
                        <p class="empty-icon">📝</p>
                        <p class="empty-text">暂无评价</p>
                    </div>

                    <!-- 分页 -->
                    <div v-if="dataTable.list && dataTable.list.length > 0" class="appraise-pagination">
                        <el-pagination
                            :page-sizes="[10, 15, 20, 50]"
                            :page-size="dataTable.pageSize"
                            :current-page="dataTable.currentPage"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="dataTable.totalCount"
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                        />
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

    </div>

    <el-dialog v-model="payDialogVisible" title="购买提示" width="30%" center class="pay-dialog">
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
import { onMounted, computed} from 'vue'
import { useRouter } from "vue-router";
import { get,post,del,patch } from '@/api/http'
import { reactive, ref } from '@vue/reactivity'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useStore } from "vuex";

export default ({
    setup() {
        const router = useRouter()
        const store = useStore()
        let payDialogVisible = ref(false)
        let skuId = router.currentRoute.value.params.id
        let sku = ref({})
        const userImg = require("@/assets/images/login-background.jpg")
        let order = ref({
            phone: null,
            orderDetails: []
        })
        let orderDetail = reactive({
            skuId: null,
            number: null,
            price: null
        })
        let dataTable = reactive({
            list: [],
            pageSize: null,
            totalCount: null,
            currentPage: null,
        })
        let params = reactive({
            skuId: router.currentRoute.value.params.id,
            userId: null,
            content: '',
            logisticeAppraise: '',
            skuAppraise: '',
        })

        onMounted(() => {
            get('sku/' + skuId, '').then(res => {
                sku.value = res
            })
            queryData()
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
            order.value.phone = ''
            order.value.orderDetails = []
            router.push({path:'/shopping'})
        }

        function handleSizeChange(val) {
            dataTable.pageSize = val
            queryData()
        }

        function handleCurrentChange(val) {
            dataTable.currentPage = val
            queryData()
        }

        function queryData() {
            get('skuAppraise?currentPage=' + dataTable.currentPage
            + '&pageSize=' + dataTable.pageSize
            + '&qp-skuId-like=' + params.skuId
            + '&qp-userId-like=' + params.userId
            + '&qp-content-like=' + params.content
            + '&qp-logisticeAppraise-like=' + params.logisticeAppraise
            + '&qp-skuAppraise-like=' + params.skuAppraise
            ).then(res => {
                dataTable.list = res.list
                dataTable.totalCount = res.totalCount
                dataTable.pageSize = res.pageSize
                dataTable.currentPage = res.currentPage
            })
        }

        return {
            skuId,
            sku,
            addCart,
            showPayDialog,
            payDialogVisible,
            pay,
            order,
            orderDetail,
            dataTable,
            handleSizeChange,
            handleCurrentChange,
            userImg,
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

.common-layout {
  padding: 40px 80px;
  background-color: #f8fafb;
  min-height: 100vh;
}

.el-container {
  background-color: transparent;
  margin-bottom: 40px;
}

// 产品展示区域
.el-aside {
  padding-right: 30px;

  .image {
    height: 400px;
    width: 100%;
    object-fit: contain;
    border-radius: 8px;
    box-shadow: $shadow;
    background-color: #ffffff;
    padding: 20px;
    box-sizing: border-box;
  }
}

.el-main {
  background-color: transparent;
  color: $text-dark;
  text-align: left;
  font-size: 16px;
  padding: 0 20px;
}

// 产品标题
.title-class {
  color: $text-dark;
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 20px;
  line-height: 1.4;
}

// 产品表单项
.item-class {
  text-align: left;
  color: $text-dark;

  ::v-deep(.el-form-item__label) {
    font-size: 16px;
    font-weight: 600;
    color: $text-dark;
  }

  ::v-deep(.el-form-item__content) {
    font-size: 18px;
    color: $primary-green;
    font-weight: 500;
    margin-bottom: 18px;
  }
}

#selectForm {
  ::v-deep(.el-form-item) {
    margin-bottom: 24px;
  }

  ::v-deep(.el-form-item__label) {
    font-size: 16px;
    font-weight: 600;
    color: $text-dark;
  }

  ::v-deep(.el-form-item__content) {
    font-size: 18px;
    color: $primary-green;
    font-weight: 500;
  }

  .button-group {
    display: flex;
    gap: 16px;
    align-items: center;

    ::v-deep(.el-form-item__content) {
      flex: 1;
      display: flex;
      gap: 16px;
    }
  }

  ::v-deep(.el-button) {
    height: 44px;
    font-size: 16px;
    font-weight: 600;
    border-radius: 4px;
    transition: all 0.3s ease;
    flex: 1;

    &.buy-btn {
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

    &.cart-btn {
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

    &.el-button--primary:not(.buy-btn) {
      background-color: $primary-green;
      border-color: $primary-green;

      &:hover {
        background-color: $light-green;
        border-color: $light-green;
        box-shadow: 0 4px 16px rgba(46, 204, 113, 0.3);
      }

      &:active {
        background-color: $dark-green;
      }
    }

    &:not(.el-button--primary):not(.buy-btn):not(.cart-btn) {
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
  }
}

// Tabs 样式
::v-deep(.demo-tabs) {
  margin-top: 40px;

  .el-tabs__header {
    border-bottom: 2px solid $border-color;
  }

  .el-tabs__nav-wrap::after {
    background-color: transparent;
  }

  .el-tabs__item {
    color: $text-dark;
    font-size: 16px;
    font-weight: 500;
    transition: all 0.3s ease;

    &:hover {
      color: $primary-green;
    }

    &.is-active {
      color: $primary-green;
      font-weight: 600;

      .el-tabs__active-bar {
        background-color: $primary-green;
      }
    }
  }

  .el-tabs__active-bar {
    background-color: $primary-green;
    height: 3px;
  }

  .el-tabs__content {
    padding: 20px 0;
  }
}

// 评价容器
.appraise-container {
  padding: 20px 0;
}

// 评价列表
.appraise-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

// 单个评价项
.appraise-item {
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: $shadow;
  padding: 20px;
  transition: all 0.3s ease;
  border-left: 4px solid $primary-green;

  &:hover {
    box-shadow: 0 8px 24px rgba(46, 204, 113, 0.15);
    transform: translateY(-2px);
  }
}

// 评价头部（用户信息）
.appraise-header {
  margin-bottom: 16px;
  padding-bottom: 16px;
  border-bottom: 1px solid $border-color;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  border: 2px solid $primary-green;
  object-fit: cover;
  transition: transform 0.3s ease;

  &:hover {
    transform: scale(1.1);
  }
}

.user-details {
  flex: 1;
  margin: 0;

  .user-name {
    margin: 0;
    font-size: 15px;
    font-weight: 600;
    color: $text-dark;
  }

  .appraise-time {
    margin: 4px 0 0 0;
    font-size: 12px;
    color: #95a5a6;
  }
}

// 评分区域
.appraise-ratings {
  margin-bottom: 16px;
  display: flex;
  gap: 24px;
  flex-wrap: wrap;
}

.rating-item {
  display: flex;
  align-items: center;
  gap: 8px;

  .rating-label {
    font-size: 13px;
    font-weight: 500;
    color: $text-dark;
    white-space: nowrap;
  }
}

// 评价内容区域
.appraise-content {
  margin-top: 12px;

  .content-text {
    margin: 0;
    font-size: 14px;
    color: $text-dark;
    line-height: 1.6;
    word-break: break-word;
  }
}

// 空评价提示
.empty-appraise {
  text-align: center;
  padding: 60px 40px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: $shadow;

  .empty-icon {
    font-size: 60px;
    margin: 0 0 16px 0;
  }

  .empty-text {
    font-size: 16px;
    color: #95a5a6;
    margin: 0;
  }
}

// 分页容器
.appraise-pagination {
  margin-top: 24px;
  display: flex;
  justify-content: center;
}

// 评价内容样式
::v-deep(.el-rate) {
  color: $primary-green;

  .el-rate__item:hover .el-icon {
    color: $light-green;
  }
}

// 分页样式
::v-deep(.el-pagination) {
  margin-top: 30px;
  text-align: right;

  .btn-prev,
  .btn-next,
  .el-pager li {
    color: $text-dark;
    transition: all 0.3s ease;

    &:hover {
      color: $primary-green;
    }

    &.active {
      color: $primary-green;
    }
  }
}

// 对话框样式
::v-deep(.pay-dialog) {
  .el-dialog__header {
    border-bottom: 3px solid $primary-green;
    padding: 20px 24px;
  }

  .el-dialog__title {
    color: $text-dark;
    font-weight: 600;
    font-size: 18px;
  }

  .el-dialog__body {
    padding: 24px;
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
      font-size: 14px;
      font-weight: 500;
      border-radius: 4px;
      transition: all 0.3s ease;
      min-width: 90px;

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
    }
  }
}

// 响应式设计
@media (max-width: 1024px) {
  .common-layout {
    padding: 30px 40px;
  }

  .el-aside {
    padding-right: 20px;

    .image {
      height: 300px;
    }
  }

  .title-class {
    font-size: 28px;
  }

  #selectForm {
    ::v-deep(.el-form-item__label) {
      font-size: 15px;
    }

    ::v-deep(.el-form-item__content) {
      font-size: 16px;
    }
  }
}

@media (max-width: 768px) {
  .common-layout {
    padding: 20px;
  }

  .el-aside {
    width: 100% !important;
    padding-right: 0;
    margin-bottom: 30px;

    .image {
      height: 280px;
    }
  }

  .el-main {
    padding: 0;
  }

  .title-class {
    font-size: 24px;
  }

  #selectForm {
    ::v-deep(.el-form-item__label) {
      font-size: 14px;
    }

    ::v-deep(.el-form-item__content) {
      font-size: 15px;
    }

    ::v-deep(.el-button) {
      height: 38px;
      font-size: 14px;
    }
  }

  ::v-deep(.demo-tabs) {
    .el-tabs__item {
      font-size: 14px;
    }
  }

  .appraise-item {
    padding: 15px;
  }

  .user-avatar {
    width: 40px;
    height: 40px;
  }

  .user-details {
    .user-name {
      font-size: 14px;
    }

    .appraise-time {
      font-size: 11px;
    }
  }

  .appraise-ratings {
    gap: 16px;
  }

  .rating-item {
    .rating-label {
      font-size: 12px;
    }
  }

  .appraise-content {
    .content-text {
      font-size: 13px;
    }
  }

  .appraise-pagination {
    ::v-deep(.el-pagination) {
      .el-pager li,
      .btn-prev,
      .btn-next {
        font-size: 12px;
      }
    }
  }
}
</style>
