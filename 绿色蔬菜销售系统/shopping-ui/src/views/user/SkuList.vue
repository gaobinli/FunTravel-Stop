<template>
    <div class="shopping-list-container">
        <el-form :inline="true" :model="params" class="search-form">
            <el-form-item label="商品名称：">
                <el-input v-model="params.name" clearable placeholder="请输入商品名称" @keyup.enter="queryData"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="queryData">查询</el-button>
            </el-form-item>
        </el-form>

        <div class="products-grid">
            <el-empty v-if="dataTable.list.length === 0" description="暂无商品" />
            <el-row :gutter="20" v-else>
                <el-col :span="6" v-for="sku in dataTable.list" :key="sku.id">
                    <el-card :body-style="{ padding: '0px' }" class="sku-card">
                        <div class="image-wrapper">
                            <img :src="sku.img" class="image" @click="goDetail(sku.id)"/>
                        </div>
                        <div class="card-content">
                            <router-link :to="{path:'/shopping/detail/' + sku.id}" class="sku-name" :title="sku.name">{{sku.name}}</router-link>
                            <div class="price-section">
                                <span class="price-label">￥</span>
                                <span class="price-value">{{sku.price}}</span>
                            </div>
                            <div class="inventory-info">库存：{{sku.inventory}}</div>
                            <div class="bottom">
                                <el-button class="action-btn add-btn" @click.stop="addCart(sku)">加入购物车</el-button>
                                <el-button class="action-btn buy-btn" @click.stop="showPayDialog(sku)">购买</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </div>

        <el-pagination
            v-if="dataTable.totalCount > 0"
            :page-sizes="[10, 15, 20, 50]"
            :page-size="dataTable.pageSize"
            :current-page="dataTable.currentPage"
            layout="total, sizes, prev, pager, next, jumper"
            :total="dataTable.totalCount"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            class="pagination"
        >
        </el-pagination>

        <el-dialog v-model="payDialogVisible" title="购买提示" width="30%" center>
            <span>您好！本次订单价格为 {{orderDetail.price}} ，购买请点击支付</span>
            <template #footer>
            <span class="dialog-footer">
                <el-button @click="payDialogVisible = false">取消支付</el-button>
                <el-button type="primary" @click="pay">支付</el-button>
            </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import { reactive, ref, onMounted } from 'vue'
import { useRouter } from "vue-router";
import { get, post } from '@/api/http'
import { ElMessage } from 'element-plus'
import { useStore } from "vuex";

export default {
    setup() {
        const router = useRouter()
        const store = useStore()
        let payDialogVisible = ref(false)
        let order = ref({
            phone: null,
            orderDetails: []
        })
        let dataTable = reactive({
            list: [],
            pageSize: 10,
            totalCount: 0,
            currentPage: 1,
        })
        let params = reactive({
            name: '',
        })
        let orderDetail = reactive({
            skuId: null,
            number: null,
            price: null,
            storeId: null
        })

        function queryData() {
            let queryUrl = 'sku?currentPage=' + dataTable.currentPage
                + '&pageSize=' + dataTable.pageSize
            if (params.name) {
                queryUrl += '&qp-name-like=' + params.name
            }
            get(queryUrl, '').then(res => {
                dataTable.list = res.list
                dataTable.totalCount = res.totalCount
                dataTable.pageSize = res.pageSize
                dataTable.currentPage = res.currentPage
            })
        }

        function handleSizeChange(val) {
            dataTable.pageSize = val
            queryData()
        }

        function handleCurrentChange(val) {
            dataTable.currentPage = val
            queryData()
        }

        function goDetail(skuId) {
            router.push({path: '/shopping/detail/' + skuId})
        }

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
            payDialogVisible.value = true
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
                queryData()
            })
            order.value.phone = '';
            order.value.orderDetails = [];
        }

        onMounted(() => {
            queryData();
        })

        return {
            dataTable,
            params,
            payDialogVisible,
            orderDetail,
            queryData,
            handleSizeChange,
            handleCurrentChange,
            goDetail,
            addCart,
            showPayDialog,
            pay,
        }
    },
}
</script>

<style lang="scss" scoped>
$primary-green: #2ecc71;
$light-green: #58d68d;
$dark-green: #27ae60;
$text-dark: #2c3e50;
$shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

.shopping-list-container {
    padding: 20px;
    background-color: transparent;
    min-height: 80vh;
}

.search-form {
    margin-bottom: 20px;
    background-color: white;
    padding: 15px;
    border-radius: 8px;
    box-shadow: $shadow;

    ::v-deep(.el-button--primary) {
        background-color: $primary-green;
        border-color: $primary-green;

        &:hover {
            background-color: $light-green;
            border-color: $light-green;
        }
    }
}

.products-grid {
    margin-bottom: 20px;
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
    cursor: pointer;
    height: 100%;

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
        cursor: pointer;

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
    margin-bottom: 8px;
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

.inventory-info {
    font-size: 12px;
    color: #999;
    margin-bottom: 10px;
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

.pagination {
    float: right;
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;

    ::v-deep(.btn-prev),
    ::v-deep(.btn-next),
    ::v-deep(.pager li.active),
    ::v-deep(.el-pager li:hover) {
        color: $primary-green;
    }

    ::v-deep(.el-pagination button:hover) {
        color: $primary-green;
    }
}

// 响应式设计
@media (max-width: 1024px) {
    .search-form {
        padding: 12px;
    }
}

@media (max-width: 768px) {
    .shopping-list-container {
        padding: 15px;
    }

    .image-wrapper {
        .image {
            padding: 8px 0;
        }
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
