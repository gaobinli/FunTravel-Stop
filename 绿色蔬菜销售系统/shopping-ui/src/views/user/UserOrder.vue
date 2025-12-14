
<template>
    <el-table :data="dataTable.list" height="650" style="width: 100%;margin-top:40px" >
        <el-table-column prop="orderNo" label="订单编号" />
        <el-table-column prop="status" label="订单状态" :formatter="handleStaus" />
        <el-table-column prop="orderPrice" label="订单价格" />
        <el-table-column prop="sendTime" label="发货时间" :formatter='handleTime' />
        <el-table-column prop="receiveTime" label="收货时间" :formatter='handleTime' />
        <el-table-column prop="createTime" label="下单时间" :formatter='handleTime' />
        <el-table-column label="操作" width="300px">
            <template #default="scope">
                <el-button type="text" @click="receipt(scope.row)">确认收货</el-button>
                <el-button type="text" @click="returnOrder(scope.row)">申请退货</el-button>
                <el-button type="text" @click="showOrderDetail(scope.row)">订单详情</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination
      :page-sizes= '[10, 15, 20, 50]'
      :page-size="dataTable.pageSize"
      :current-page="dataTable.currentPage"
      layout="total, sizes, prev, pager, next, jumper"
      :total="dataTable.totalCount"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    >
    </el-pagination>

    <el-dialog
        v-model="updateDialogVisible"
        title="修改弹窗"
        width="30%"
        :before-close="updateHandleClose"
    >
        <el-form :inline="false" :model="oneData" ref="dataFormRuleRef" :rules="addRules" class="demo-form-inline">
            <el-form-item label="用户编码：" prop="userId">
                <el-input v-model="oneData.userId" placeholder="请输入用户编码"></el-input>
            </el-form-item>
            <el-form-item label="订单编号：" prop="orderNo">
                <el-input v-model="oneData.orderNo" placeholder="请输入订单编号"></el-input>
            </el-form-item>
            <el-form-item label="发货时间：" prop="sendTime">
                <el-input v-model="oneData.sendTime" placeholder="请输入发货时间"></el-input>
            </el-form-item>
            <el-form-item label="收货时间：" prop="receiveTime">
                <el-input v-model="oneData.receiveTime" placeholder="请输入收货时间"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="updateHandleClose">取消</el-button>
            <el-button type="primary" @click="updated">确定</el-button>
        </span>
        </template>
    </el-dialog>

    <el-dialog
        v-model="addDialogVisible"
        title="新增弹窗"
        width="30%"
        :before-close="addHandleClose"
    >
        <el-form :inline="false" :model="oneData" ref="dataFormRuleRef" :rules="addRules" class="demo-form-inline">
            <el-form-item label="用户编码：" prop="userId">
                <el-input v-model="oneData.userId" placeholder="请输入用户编码"></el-input>
            </el-form-item>
            <el-form-item label="订单编号：" prop="orderNo">
                <el-input v-model="oneData.orderNo" placeholder="请输入订单编号"></el-input>
            </el-form-item>
            <el-form-item label="发货时间：" prop="sendTime">
                <el-input v-model="oneData.sendTime" placeholder="请输入发货时间"></el-input>
            </el-form-item>
            <el-form-item label="收货时间：" prop="receiveTime">
                <el-input v-model="oneData.receiveTime" placeholder="请输入收货时间"></el-input>
            </el-form-item>
            <el-form-item style="text-align: right">
                <el-button @click="addHandleClose">取消</el-button>
                <el-button type="primary" @click="add" >确定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>

    <el-dialog
        v-model="orderDetailDialogVisible"
        title="订单详情"
        width="50%"
        :before-close="orderDetailHandleClose"
    >
        <el-form :inline="false" :model="oneData" ref="dataFormRuleRef" :rules="addRules" class="demo-form-inline">
            <el-table :data="orderDetails" height="450" style="width: 100%;">
                <el-table-column prop="orderNo" label="订单编号" />
                
                <el-table-column prop="skuName" label="商品名称">
                    <template #default="scope">
                        <router-link :to="{path:'/shopping/detail/' + scope.row.skuId}" class="sku-name" :title="scope.row.skuName">{{ scope.row.skuName }}</router-link>
                    </template>
                </el-table-column>

                <el-table-column prop="img" label="图片">
                    <template #default="scope">
                        <img :src="scope.row.skuImg" style="widht:148px; height:148px" />
                    </template>    
                </el-table-column>
                <el-table-column prop="number" label="数量" />
                <el-table-column prop="price" label="单价"/>
                <el-table-column label="操作">
                    
                <template #default="scope">
                    <span v-if="!scope.row.appraiseFlag">
                        <el-button type="text" @click="appraiseDialog(scope.row)">评价</el-button>
                    </span>
                    <span v-if="scope.row.appraiseFlag">
                        <el-button disabled="true" type="text">已评价</el-button>
                    </span>
                </template>
                </el-table-column>
            </el-table>
            <el-form-item style="padding-left: 90%;margin-top:40px">
                <el-button type="primary" @click="orderDetailDialogVisible = false" >确定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>

    <el-dialog
        v-model="appraiseDialogVisible"
        title="评价"
        width="25%"
        :before-close="appraiseHandleClose"
    >
        <div class="login">
            <el-form :model="appraiseData" label-width="100px" class="login-form" :rules="appraiseRules" ref="appraiseDataFormRuleRef">
                <el-form-item label="物流评分：" prop="logisticeAppraise">
                    <el-rate
                        style="line-height:2px"
                        v-model="appraiseData.logisticeAppraise"
                        :texts="['非常差', '差', '一般', '好', '非常好']"
                        show-text
                    >
                    </el-rate>
                </el-form-item>
                <el-form-item label="商品评分：" prop="skuAppraise">
                    <el-rate
                        style="line-height:2px"
                        v-model="appraiseData.skuAppraise"
                        :texts="['非常差', '差', '一般', '好', '非常好']"
                        show-text
                    >
                    </el-rate>
                </el-form-item>
                <el-form-item label="评价内容：" prop="content">
                    <el-input v-model="appraiseData.content" placeholder="请输入评价内容"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="margin-right: 0px" type="primary" @click="appraiseSubmit">确定</el-button>
                </el-form-item>
            </el-form>
        </div>
    </el-dialog>
    
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted} from 'vue'
import { get,post,del,patch } from '@/api/http'
import { ElMessageBox, ElMessage } from 'element-plus'

export default({
    setup() {
        let updateDialogVisible = ref(false)
        let addDialogVisible = ref(false)
        let orderDetailDialogVisible = ref(false)
        let appraiseDialogVisible = ref(false)
        let dataFormRuleRef = ref(null)
        let appraiseDataFormRuleRef = ref(null)
        let tableSelectIds = ref([])
        
        let dataTable = reactive({
            list: [],
            pageSize: 10,
            totalCount: null,
            currentPage: 1,
        })
        let params = reactive({
            userId: '',
            orderNo: '',
            sendTime: '',
            receiveTime: '',
        })
        let oneData = reactive({
            id: null,
            userId: null,
            orderNo: '',
            sendTime: '',
            receiveTime: '',
        })
        let appraiseData = reactive({
            skuId: null,
            userId: null,
            content: '',
            logisticeAppraise: null,
            skuAppraise: null,
            orderNo: null
        })
        let orderDetails = ref([])
        let addRules = reactive({
            userId: {required: true, message: '请输入用户编码', trigger: 'blur'},
            orderNo: {required: true, message: '请输入订单编号', trigger: 'blur'},
            sendTime: {required: true, message: '请输入发货时间', trigger: 'blur'},
            receiveTime: {required: true, message: '请输入收货时间', trigger: 'blur'},
        })

        let appraiseRules = reactive({
            content: {required: true, message: '请填写评价内容', trigger: 'blur'},
            logisticeAppraise: {required: true, message: '请做出物流评价', trigger: 'blur'},
            skuAppraise: {required: true, message: '请做出商品评价', trigger: 'blur'},
        })

        function queryData() {
            let phone = localStorage.getItem('userCode');
            get('order?currentPage=' + dataTable.currentPage
            + '&pageSize=' + dataTable.pageSize
            + '&qp-phone-eq=' + phone
            ).then(res => {
                dataTable.list = res.list
                dataTable.totalCount = res.totalCount
                dataTable.pageSize = res.pageSize
                dataTable.currentPage = res.currentPage
            })
        }

        function handleTime(row, column, cellValue, index) {
            if(cellValue) {
                return cellValue.replace('T', ' ')
            }
        }

        function handleStaus(row, column, cellValue, index) {
            if(cellValue === 0) {
                return '待发货'
            } else if (cellValue === 1) {
                return '已发货'
            } else if (cellValue === 2) {
                return '已收货'
            } else if (cellValue === 3) {
                return '申请退货'
            } else if (cellValue === 4) {
                return '已退货'
            }
        }

        function handleSizeChange(val) {
            dataTable.pageSize = val
            queryData()
        }

        function handleCurrentChange(val) {
            dataTable.currentPage = val
            queryData()
        }

        function deleted(row) {
            ElMessageBox.confirm('请确认是否删除?','提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                del('order/' + row.id, {}).then(res => {
                    queryData()
                    ElMessage({
                        message: '亲，删除成功',
                        type: 'success',
                    })
                })
            })
            .catch(() => {
                ElMessage({
                    message: '亲，你取消了删除操作',
                    type: 'info',
                })
            });
        }

        function showDetail(id) {
            get("order/" + id, {}).then(res => {
                oneData.id = res.id
                oneData.userId = res.userId
                oneData.orderNo = res.orderNo
                oneData.sendTime = res.sendTime
                oneData.receiveTime = res.receiveTime
                updateDialogVisible.value = true
            })
        }

        function updated() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    patch('order/' + oneData.id, oneData).then(res => {
                    oneData.userId = null
                    oneData.orderNo = ''
                    oneData.sendTime = ''
                    oneData.receiveTime = ''
                    queryData()
                    updateDialogVisible.value = false
                    ElMessage({message: '亲，修改成功', type: 'success'})
            })
                } else {
                    return false
                }
            })
        }

        function updateEnable(row) {
            patch('order/' + row.id, {enable: row.enable}).then(res => {
                ElMessage({message: '亲，修改成功', type: 'success'})
            })
        }

        function add() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    post('order', oneData).then(res => {
                    oneData.userId = null
                    oneData.orderNo = ''
                    oneData.sendTime = ''
                    oneData.receiveTime = ''
                    queryData()
                    addDialogVisible.value = false
                    ElMessage({message: '亲，新增成功', type: 'success'})
            })
                } else {
                    return false
                }
            })
        }

        function handleSelectionChange(val) {
            tableSelectIds.value = val;
        }

        function mulDelete() {
            if(tableSelectIds.value.length !== 0) {
                let idStr = tableSelectIds.value.map(item => item.id).toString()
                ElMessageBox.confirm('请确认是否删除?','提示',{
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    del('order/' + idStr, {}).then(res => {
                        queryData()
                        ElMessage({
                            message: '亲，删除成功',
                            type: 'success',
                        })
                    })

                })
                .catch(() => {
                    ElMessage({
                        message: '亲，你取消了删除操作',
                        type: 'info',
                    })
                });
            } else {
                ElMessage({message: '亲，请选择要删除的行', type: 'error'})
            }
        }

        function updateTop() {
            if(tableSelectIds.value.length === 1) {
                showDetail(tableSelectIds.value[0].id)
            } else if(tableSelectIds.value.length > 1) {
                ElMessage({message: '亲，只能选择一行进行修改哦', type: 'error'})
            } else {
                ElMessage({message: '亲，请至少选择一行进行修改哦', type: 'error'})
            }
        }

        function returnOrder(val) {
            let status = val.status
            if (status == 0 || status == 1 || status == 3 || status == 4) {
                ElMessage({message: '亲，只有已收货的订单才能同意退货呢', type: 'error'})
            } else {
                ElMessageBox.confirm('请确认是否申请退货?','提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                }).then(() => {
                    post('order/return?ids=' + val.id + '&status=3', {}).then(res => {
                        queryData()
                        ElMessage({
                            message: '亲，申请退货成功',
                            type: 'success',
                        })
                    })

                })
                .catch(() => {
                    ElMessage({
                        message: '亲，你取消了申请退货操作',
                        type: 'info',
                    })
                });
            }
        }

        function updateHandleClose() {
            updateDialogVisible.value = false
            oneData.id = null
            oneData.userId = null
            oneData.orderNo = ''
            oneData.sendTime = ''
            oneData.receiveTime = ''
        }

        function appraiseHandleClose() {
            appraiseDialogVisible.value = false
            orderDetailDialogVisible.value = true
            appraiseData.skuId = null
            appraiseData.userId = null
            appraiseData.content = ''
            appraiseData.logisticeAppraise = null
            appraiseData.skuAppraise = null
            appraiseData.orderNo = null
        }

        function addHandleClose() {
            addDialogVisible.value = false
            oneData.userId = null
            oneData.orderNo = ''
            oneData.sendTime = ''
            oneData.receiveTime = ''
        }

        function showOrderDetail(val) {
            const userIdLocal = localStorage.getItem('userId');
            get('orderDetail/list?orderNo=' + val.orderNo + "&userId=" + userIdLocal, '').then(res => {
                orderDetails.value = res
            })
            orderDetailDialogVisible.value = true
        }

        function receipt(val){
            if(val.status === 1) {
                post('order/receipt?id='+val.id, '').then(res => {
                    queryData()
                    ElMessage({
                        message: '亲，收货成功',
                        type: 'success',
                    })
                })
            } else {
                ElMessage({
                    message: '亲，发货了才可以收货哦',
                    type: 'info',
                })
            }
        
        }

        function appraiseDialog(val) {
            appraiseData.userId = localStorage.getItem('userId');
            appraiseData.skuId = val.skuId
            appraiseData.orderNo = val.orderNo
            appraiseDialogVisible.value = true
            orderDetailDialogVisible.value = false
        }

        function appraiseSubmit() {
            appraiseDataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    const orderNoTemp = appraiseData.orderNo
                    post('skuAppraise', appraiseData).then(res => {
                    appraiseData.skuId = null
                    appraiseData.userId = null
                    appraiseData.content = ''
                    appraiseData.logisticeAppraise = null
                    appraiseData.skuAppraise = null
                    appraiseData.orderNo = null
                    ElMessage({message: '亲，评价成功', type: 'success'})
                    appraiseDialogVisible.value = false

                    const userIdLocal = localStorage.getItem('userId');
                    get('orderDetail/list?orderNo=' + orderNoTemp + "&userId=" + userIdLocal, '').then(res => {
                        orderDetails.value = res
                    })
                    orderDetailDialogVisible.value = true
            })
                } else {
                    return false
                }
            })
        }

        onMounted(() => {
            queryData()
        })

        return {
            dataTable,
            params,
            updateDialogVisible,
            addDialogVisible,
            orderDetailDialogVisible,
            showOrderDetail,
            oneData,
            orderDetails,
            addRules,
            handleStaus,
            dataFormRuleRef,
            mulDelete,
            tableSelectIds,
            handleSelectionChange,
            add,
            receipt,
            updateTop,
            returnOrder,
            addHandleClose,
            updateHandleClose,
            showDetail,
            updateEnable,
            deleted,
            updated,
            queryData,
            handleTime,
            handleSizeChange,
            handleCurrentChange,
            appraiseDialog,
            appraiseHandleClose,
            appraiseData,
            appraiseDialogVisible,
            appraiseRules,
            appraiseSubmit,
            appraiseDataFormRuleRef,
            
        }
    }
})
</script>

<style lang="scss" scoped>
// 绿色蔬菜系统主色调
$primary-green: #2ecc71;
$light-green: #58d68d;
$dark-green: #27ae60;
$text-dark: #2c3e50;

.el-pagination {
  margin-top: 20px;
  float: right;
}

.login {
  display: flex;
  justify-content: center;
  align-items: center;
  background-size: cover;
}

.login-form {
  border-radius: 6px;
  width: 100%;

  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
}

// 商品名称链接样式
.sku-name {
  color: $primary-green;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.3s ease;
  display: inline-block;
  border-bottom: 1px solid transparent;

  &:hover {
    color: $dark-green;
    border-bottom-color: $primary-green;
    transform: translateY(-1px);
  }

  &:active {
    color: $dark-green;
  }
}
</style>












