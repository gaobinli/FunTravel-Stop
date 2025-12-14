
<template>
    <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item label="商品：">
            <el-select v-model="params.skuId" multiple clearable filterable placeholder="选择商品" style="width: 150px;">
                <el-option
                v-for="item in skuList"
                :key="item.id"
                :label="item.name"
                :value="item.id"
                >
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="用户：">
            <el-select v-model="params.userId" multiple filterable clearable placeholder="选择用户" style="width: 150px;">
                <el-option
                v-for="item in userList"
                :key="item.id"
                :label="item.nickName"
                :value="item.id"
                >
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="物流评分：">
            <el-select v-model="params.logisticeAppraise" multiple filterable clearable placeholder="选择物流评分" style="width: 150px;">
                <el-option
                v-for="item in numberList"
                :key="item.id"
                :label="item.desc"
                :value="item.score"
                >
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="商品评分：">
            <el-select v-model="params.skuAppraise" multiple filterable clearable placeholder="选择商品评分" style="width: 150px;">
                <el-option
                v-for="item in numberList"
                :key="item.id"
                :label="item.desc"
                :value="item.score"
                >
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="queryData()">查询</el-button>
        </el-form-item>
    </el-form>
    <div style="margin-bottom:10px">
        <el-button type="primary" icon="i-delete" plain @click="mulDelete">删除</el-button>
    </div>
    
    <el-table :data="dataTable.list" height="550" style="width: 100%;" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />
        
        <el-table-column prop="id" label="ID" />

        <el-table-column prop="skuName" label="商品名称" />

        <el-table-column prop="img" label="图片">
            <template #default="scope">
                <el-image
                    style="width: 100px; height: 100px"
                    :src="scope.row.skuImg"
                    :preview-src-list="[scope.row.skuImg]"
                    :initial-index="0"
                    fit="cover"
                    :append-to-body = "true"
                    :preview-teleported="true"
                    >
                </el-image>
            </template>    
        </el-table-column>

        <el-table-column prop="userName" label="评价人" />

        <el-table-column prop="content" label="评价内容" />

        <el-table-column label="物流评分">
            <template #default="scope">
                <el-rate
                    style="line-height:2px"
                    v-model="scope.row.logisticeAppraise"
                    disabled
                >
                </el-rate>
            </template>
        </el-table-column>

        <el-table-column label="商品评分">
            <template #default="scope">
                <el-rate
                    style="line-height:2px"
                    v-model="scope.row.skuAppraise"
                    disabled
                >
                </el-rate>
            </template>
        </el-table-column>
        

        <el-table-column prop="createTime" label="评价时间" :formatter='handleTime' />

        <el-table-column label="操作">
            <template #default="scope">
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
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted} from 'vue'
import { get,post,del,patch } from '@/api/request'
import { ElMessageBox, ElMessage } from 'element-plus'

export default({
    setup() {
        let adminCode = localStorage.getItem('adminCode')
        let updateDialogVisible = ref(false)
        let addDialogVisible = ref(false)
        let dataFormRuleRef = ref(null);
        let tableSelectIds = ref([])
        let skuList = ref([])
        let userList = ref([])
        let numberList = reactive(
            [
                {id: 1, score: 1, desc: '一星'},
                {id: 2, score: 2, desc: '二星'},
                {id: 3, score: 3, desc: '三星'},
                {id: 4, score: 4, desc: '四星'},
                {id: 5, score: 5, desc: '五星'}
            ]
        )
        let dataTable = reactive({
            list: [],
            pageSize: 10,
            totalCount: null,
            currentPage: 1,
        })
        let params = reactive({
            skuId: null,
            userId: '',
            content: '',
            logisticeAppraise: '',
            skuAppraise: '',
        })

        function queryData() {
            get('store/list?qp-merchantsCode-eq=' + (adminCode === 'admin' ? '' : adminCode), '').then(res => {
                let storeIds = '';
                if (res) {
                    storeIds = res.map(item => item.id).join(',');
                }
                if(storeIds && storeIds !== '') {
                    get('order?currentPage=1'
                    + '&pageSize=10000'
                    + '&qp-storeId-in=' + storeIds
                    ).then(res2 => {
                        let orderNos = ''
                        if (res2) {
                            orderNos = res2.list.map(item => item.orderNo).join(',');
                        }
                        if(orderNos && orderNos !== '') {
                            get('skuAppraise?currentPage=' + dataTable.currentPage
                            + '&pageSize=' + dataTable.pageSize
                            + '&qp-skuId-in=' + params.skuId
                            + '&qp-userId-in=' + params.userId
                            + '&qp-content-like=' + params.content
                            + '&qp-logisticeAppraise-in=' + params.logisticeAppraise
                            + '&qp-orderNo-in=' + orderNos
                            ).then(res => {
                                dataTable.list = res.list
                                dataTable.totalCount = res.totalCount
                                dataTable.pageSize = res.pageSize
                                dataTable.currentPage = res.currentPage
                            })
                        }
                    })
                }
                
            })

            
        }

        function handleTime(row, column, cellValue, index) {
            if(cellValue) {
                return cellValue.replace('T', ' ')
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
                del('skuAppraise/' + row.id, {}).then(res => {
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
                    del('skuAppraise/' + idStr, {}).then(res => {
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

       
        onMounted(() => {
            queryData();
            getSkuList();
            getUserList();
        })

        function getSkuList() {
            get('/sku/list', '').then(res => {
                skuList.value = res
            })
        }

        function getUserList() {
             get('/user/list', '').then(res => {
                userList.value = res
            })
        }

        return {
            dataTable,
            params,
            mulDelete,
            tableSelectIds,
            handleSelectionChange,
            deleted,
            queryData,
            handleTime,
            handleSizeChange,
            handleCurrentChange,
            skuList,
            userList,
            numberList
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












