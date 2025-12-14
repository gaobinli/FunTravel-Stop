
<template>
    
    <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item label="蔬菜名称：">
            <el-input v-model="params.name" placeholder="请输入蔬菜名称"></el-input>
        </el-form-item>
        <el-form-item label="价格：">
            <el-input v-model="params.price" placeholder="请输入价格"></el-input>
        </el-form-item>
        <el-form-item label="库存量：">
            <el-input v-model="params.inventory" placeholder="请输入库存量"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="queryData()">查询</el-button>
        </el-form-item>
    </el-form>
    <div style="margin-bottom:10px">
        <el-button type="primary" icon="i-plus" plain @click="addTop">新增</el-button>
        <el-button type="primary" icon="i-edit" plain @click="updateTop">修改</el-button>
        <el-button type="primary" icon="i-delete" plain @click="mulDelete">删除</el-button>
    </div>
    
    <el-table :data="dataTable.list" height="550" style="width: 100%;" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />
        <el-table-column prop="id" label="主键" />

        <el-table-column prop="storeName" label="店铺" />

        <el-table-column prop="name" label="蔬菜名称" />

        <el-table-column prop="img" label="图片">
            <template #default="scope">
                <el-image
                    style="width: 100px; height: 100px"
                    :src="scope.row.img"
                    :preview-src-list="[scope.row.img]"
                    :initial-index="0"
                    fit="cover"
                    :append-to-body = "true"
                    :preview-teleported="true"
                    >
                </el-image>
            </template>    
        </el-table-column>

        <el-table-column prop="price" label="价格" />

        <el-table-column prop="inventory" label="库存量" />

        <el-table-column prop="modifyUser" label="更新人" />

        <el-table-column prop="modifyTime" label="更新时间" :formatter='handleTime' />

        <el-table-column label="操作">
            <template #default="scope">
                <el-button type="primary" icon="i-edit" @click="showDetail(scope.row.id)"></el-button>
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
            <el-form-item label="蔬菜名称：" prop="name">
                <el-input v-model="oneData.name" placeholder="请输入蔬菜名称"></el-input>
            </el-form-item>
            <el-form-item label="图片地址：" prop="img">
                <el-upload
                    :action="$imgUpload"
                    list-type="picture-card"
                    name="file"
                    multiple="false"
                    method="post"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                >
                    <img v-if="oneData.img" :src="oneData.img" class="avatar" style="widht:148px; height:148px" />
                    <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="价格：" prop="price">
                <el-input v-model="oneData.price" placeholder="请输入价格"></el-input>
            </el-form-item>
            <el-form-item label="库存量：" prop="inventory">
                <el-input-number v-model="oneData.inventory" :min="1" :max="9999999" @change="handleChange" />
            </el-form-item>
            <el-form-item label="所属模块：">
                <el-select v-model="oneData.moduleIds" multiple placeholder="选择模块，非必选">
                    <el-option
                    v-for="item in moduleList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                    >
                    </el-option>
                </el-select>
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
        width="60%"
        :before-close="addHandleClose"
    >
        <el-form :inline="false" :model="oneData" ref="dataFormRuleRef" :rules="addRules" class="demo-form-inline">
            <el-form-item label="蔬菜名称：" prop="name">
                <el-input v-model="oneData.name" placeholder="请输入蔬菜名称"></el-input>
            </el-form-item>
            <el-form-item label="图片地址：" prop="img">
                <el-upload
                    :action="$imgUpload"
                    list-type="picture-card"
                    name="file"
                    multiple="false"
                    method="post"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                >
                    <img v-if="oneData.img" :src="oneData.img" class="avatar" style="widht:148px; height:148px" />
                    <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="价格：" prop="price">
                <el-input-number v-model="oneData.price" :min="1" :max="9999999" @change="handleChange" />
            </el-form-item>
            <el-form-item label="库存量：" prop="inventory">
                <el-input-number v-model="oneData.inventory" :min="1" :max="127" @change="handleChange" />
            </el-form-item>
            <el-form-item label="所属模块：">
                <el-select v-model="oneData.moduleIds" multiple placeholder="选择模块，非必选">
                    <el-option
                    v-for="item in moduleList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                    >
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <div ref='editor'></div>
                    <button @click='syncHTML'>同步内容</button>
                <div :innerHTML='content.html'></div>
                

            </el-form-item>
            <el-form-item style="text-align: right">
                <el-button @click="addHandleClose">取消</el-button>
                <el-button type="primary" @click="add" >确定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>

    
</template>

<script>
import { reactive, ref, onMounted, onBeforeUnmount } from 'vue'
import { get,post,del,patch } from '@/api/request'
import { ElMessageBox, ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons'
import { useRouter } from 'vue-router'
import { componentToSlot } from 'element-plus/es/components/table-v2/src/utils.mjs'

export default({
    components: { Plus },
    setup() {
        const router = useRouter()
        let adminCode = localStorage.getItem('adminCode')
        let updateDialogVisible = ref(false)
        let addDialogVisible = ref(false)
        let dataFormRuleRef = ref(null);
        let tableSelectIds = ref([])
        let dataTable = reactive({
            list: [],
            pageSize: 10,
            totalCount: null,
            currentPage: 1,
        })
        let params = reactive({
            name: '',
            img: '',
            price: '',
            inventory: '',
        })
        let oneData = reactive({
            id: null,
            name: '',
            img: '',
            price: '',
            inventory: '',
            moduleIds: null,
            detail: ''
        })
        let moduleList = ref([])
        let addRules = reactive({
            name: {required: true, message: '请输入蔬菜名称', trigger: 'blur'},
            img: {required: true, message: '请输入图片地址', trigger: 'blur'},
            price: {required: true, message: '请输入价格', trigger: 'blur'},
            inventory: {required: true, message: '请输入库存量', trigger: 'blur'},
        })


        function queryData() {
            get('store/list?qp-merchantsCode-eq=' + (adminCode === 'admin' ? '' : adminCode), '').then(res => {
                let storeIds = '';
                if (res) {
                    storeIds = res.map(item => item.id).join(',');
                    
                }
                if(storeIds && storeIds !== '') {
                    get('sku?currentPage=' + dataTable.currentPage
                    + '&pageSize=' + dataTable.pageSize
                    + '&qp-name-like=' + params.name
                    + '&qp-img-like=' + params.img
                    + '&qp-price-like=' + params.price
                    + '&qp-inventory-like=' + params.inventory
                    + '&qp-storeId-in=' + storeIds
                    ).then(res => {
                        dataTable.list = res.list
                        dataTable.totalCount = res.totalCount
                        dataTable.pageSize = res.pageSize
                        dataTable.currentPage = res.currentPage
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

        function handleAvatarSuccess(res, file) {
            oneData.img = res.data
        }

        function deleted(row) {
            ElMessageBox.confirm('请确认是否删除?','提示',{
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
            }).then(() => {
                del('sku/' + row.id, {}).then(res => {
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
            router.push({path:'/admin/sku/update/' + id});
        }

        function addTop() {
            router.push({path:'/admin/sku/add'});
        }

        function updated() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    patch('sku/' + oneData.id, oneData).then(res => {
                    oneData.name = ''
                    oneData.img = ''
                    oneData.price = ''
                    oneData.inventory = null
                    oneData.moduleIds = null
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
            patch('sku/' + row.id, {enable: row.enable}).then(res => {
                ElMessage({message: '亲，修改成功', type: 'success'})
            })
        }

        function add() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    post('sku', oneData).then(res => {
                    oneData.name = ''
                    oneData.img = ''
                    oneData.price = ''
                    oneData.inventory = null
                    oneData.moduleIds = null
                    oneData.detail = ''
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
                    del('sku/' + idStr, {}).then(res => {
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

        function updateHandleClose() {
            updateDialogVisible.value = false
            oneData.id = null
            oneData.name = ''
            oneData.img = ''
            oneData.price = ''
            oneData.inventory = null
            oneData.moduleIds = null
        }

        function addHandleClose() {
            addDialogVisible.value = false
            oneData.id = null
            oneData.name = ''
            oneData.img = ''
            oneData.price = ''
            oneData.inventory = null
            oneData.moduleIds = null
        }

        let storeList = ref([])
        onMounted(() => {
            queryData()
            get('module/list', '').then(res => {
                moduleList.value = res
            })
            
        })

        function toAdd() {
            setTimeout("location.href = '/admin/sku/add'","1");
        }

        return {
            dataTable,
            params,
            updateDialogVisible,
            addDialogVisible,
            oneData,
            addRules,
            moduleList,
            dataFormRuleRef,
            mulDelete,
            handleAvatarSuccess,
            tableSelectIds,
            handleSelectionChange,
            add,
            updateTop,
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
            toAdd,
            addTop,
            adminCode
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












