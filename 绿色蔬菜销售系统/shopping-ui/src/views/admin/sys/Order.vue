
<template>
    <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item label="用户手机号：">
            <el-input v-model="params.userId" placeholder="请输入用户编码"></el-input>
        </el-form-item>
        <el-form-item label="订单编号：">
            <el-input v-model="params.orderNo" placeholder="请输入订单编号"></el-input>
        </el-form-item>
        <el-form-item label="发货时间：">
            <el-date-picker
                v-model="params.sendTime"
                type="daterange"
                range-separator="至"
                start-placeholder="开始"
                end-placeholder="结束"
                value-format="YYYY-MM-DD"
            >
            </el-date-picker>
        </el-form-item>
        <el-form-item label="收货时间：">
            <el-date-picker
                v-model="params.receiveTime"
                type="daterange"
                range-separator="至"
                start-placeholder="开始"
                end-placeholder="结束"
                value-format="YYYY-MM-DD"
            >
            </el-date-picker>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="queryData()">查询</el-button>
        </el-form-item>
    </el-form>
    <div style="margin-bottom:10px">
        <el-button type="primary" icon="i-delete" plain @click="mulDelete">删除</el-button>
        <el-button type="primary" icon="i-edit" plain @click="returnOrder">同意退货</el-button>
    </div>
    
    <el-table :data="dataTable.list" height="550" style="width: 100%;" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />
        <el-table-column prop="storeName" label="店铺" />
        <el-table-column prop="orderNo" label="订单编号" />
        <el-table-column prop="status" label="订单状态" :formatter="handleStaus" />
        <el-table-column prop="phone" label="用户手机号" />
        <el-table-column prop="orderPrice" label="订单价格" />
        <el-table-column prop="sendTime" label="发货时间" :formatter='handleTime' />
        <el-table-column prop="receiveTime" label="收货时间" />
        <el-table-column prop="createTime" label="创建时间" :formatter='handleTime' />
        <el-table-column label="操作" width="300px">
            <template #default="scope">
                <el-button type="primary" icon="i-select" @click="send(scope.row)"></el-button>
                <el-button type="primary" icon="i-orange" @click="showOrderDetail(scope.row)"></el-button>
                <el-button type="danger" icon="i-delete" @click="deleted(scope.row)"></el-button>
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
                <el-table-column prop="storeName" label="店铺" />
                <el-table-column prop="orderNo" label="订单编号" />
                <el-table-column prop="skuName" label="商品名称" />

                <el-table-column label="图片">
                    <template #default="scope">
                        <el-image
                            style="width: 100px; height: 100px"
                            :src="scope.row.skuImg">
                        </el-image>
                    </template>    
                </el-table-column>

                <el-table-column prop="number" label="数量" />
                <el-table-column prop="price" label="单价"/>
            </el-table>
            <el-form-item style="padding-left: 90%;margin-top:20px">
                <el-button type="primary" @click="orderDetailDialogVisible = false" >确定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted, toRefs} from 'vue'
import { get,post,del,patch } from '@/api/request'
import { ElMessageBox, ElMessage } from 'element-plus'

export default({
    setup() {
        let adminCode = localStorage.getItem('adminCode')
        let updateDialogVisible = ref(false)
        let addDialogVisible = ref(false)
        let orderDetailDialogVisible = ref(false)
        let dataFormRuleRef = ref(null);
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
            receiveTime: [],
        })
        let oneData = reactive({
            id: null,
            userId: null,
            orderNo: '',
            sendTime: '',
            receiveTime: '',
        })
        let orderDetails = ref([])
        let addRules = reactive({
            userId: {required: true, message: '请输入用户编码', trigger: 'blur'},
            orderNo: {required: true, message: '请输入订单编号', trigger: 'blur'},
            sendTime: {required: true, message: '请输入发货时间', trigger: 'blur'},
            receiveTime: {required: true, message: '请输入收货时间', trigger: 'blur'},
        })

        function queryData() {
            let receiveStartTime= ''
            let receiveEndTime = ''
            if(params.receiveTime != null && params.receiveTime.length != 0) {
                receiveStartTime = params.receiveTime[0] + ' 00:00:00'
                receiveEndTime = params.receiveTime[1] + ' 23:59:59'
            }
            let sendTimeStartTime= ''
            let sendTimeEndTime = ''
            if(params.sendTime != null && params.sendTime.length != 0) {
                sendTimeStartTime = params.sendTime[0] + ' 00:00:00'
                sendTimeEndTime = params.sendTime[1] + ' 23:59:59'
            }
            get('store/list?qp-merchantsCode-eq=' + (adminCode === 'admin' ? '' : adminCode), '').then(res => {
                let storeIds = '';
                if (res) {
                    storeIds = res.map(item => item.id).join(',');
                }
                get('order?currentPage=' + dataTable.currentPage
                + '&pageSize=' + dataTable.pageSize
                + '&qp-userId-like=' + params.userId
                + '&qp-orderNo-like=' + params.orderNo
                + '&qp-sendTime-ge=' + sendTimeStartTime
                + '&qp-sendTime-le=' + sendTimeEndTime
                + '&qp-receiveTime-ge=' + receiveStartTime
                + '&qp-receiveTime-le=' + receiveEndTime
                + '&qp-storeId-in=' + storeIds
                ).then(res => {
                    dataTable.list = res.list
                    dataTable.totalCount = res.totalCount
                    dataTable.pageSize = res.pageSize
                    dataTable.currentPage = res.currentPage
                })
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

        function returnOrder() {
            if(tableSelectIds.value.length !== 0) {
                let result = tableSelectIds.value.findIndex(item => item.status == 0 || item.status == 1 || item.status == 2 || item.status == 4)
                if (result === -1) {
                    ElMessageBox.confirm('请确认是否同意退货?','提示',{
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                    }).then(() => {
                        let idStr = tableSelectIds.value.map(item => item.id).toString()
                        post('order/return?ids=' + idStr + '&status=4', {}).then(res => {
                            queryData()
                            ElMessage({
                                message: '亲，同意退货成功',
                                type: 'success',
                            })
                        })

                    })
                    .catch(() => {
                        ElMessage({
                            message: '亲，你取消了退货操作',
                            type: 'info',
                        })
                    });
                } else {
                    ElMessage({message: '亲，只有申请退货的订单才能同意退货呢', type: 'error'})
                }
                
            } else {
                ElMessage({message: '亲，请选择要同意退货的订单', type: 'error'})
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

        function addHandleClose() {
            addDialogVisible.value = false
            oneData.id = null
            oneData.userId = null
            oneData.orderNo = ''
            oneData.sendTime = ''
            oneData.receiveTime = ''
        }

        function showOrderDetail(val) {
            get('orderDetail/list?orderNo=' + val.orderNo, '').then(res => {
                orderDetails.value = res
            })
            orderDetailDialogVisible.value = true
        }

        function send(val){
            let status = val.status
            if(status === 0) {
                post('order/send?id='+val.id, '').then(res => {
                    queryData()
                    ElMessage({
                        message: '亲，发货成功',
                        type: 'success',
                    })
                })
            } else {
                ElMessage({
                    message: '亲，待发货的订单才可以发货',
                    type: 'info',
                })
            }
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
            send,
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
            handleCurrentChange
        }
    }
})
</script>

<style lang="scss" scoped>
.el-pagination {
    margin-top: 20px;
    float: right;
}
</style>












