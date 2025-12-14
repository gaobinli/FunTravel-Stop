<template>
    <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item label="账号：">
            <el-input v-model="params.code" placeholder="请输入账号"></el-input>
        </el-form-item>
        <el-form-item label="姓名：">
            <el-input v-model="params.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="手机号：">
            <el-input v-model="params.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="queryData()">查询</el-button>
        </el-form-item>
    </el-form>
    <el-button type="primary" icon="i-plus" plain @click="showAddDialog">新增</el-button>
    <el-button type="primary" icon="i-edit" plain @click="updateTop">修改</el-button>
    <el-button type="primary" icon="i-delete" plain @click="mulDelete">删除</el-button>
    <el-table :data="dataTable.list" height="550" style="width: 100%;" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />
        <el-table-column prop="id" label="ID" />
        <el-table-column prop="code" label="商家账号" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="name" label="商家姓名" />
        <el-table-column prop="roleNames" label="角色" />
        <el-table-column prop="modifyUser" label="更新人" />
        <el-table-column prop="modifyTime" label="更新时间" :formatter='handleTime' />
        <el-table-column prop="" label="启用状态">
            <template #default="scope">
                <el-switch v-model="scope.row.enable" @change="updateEnable(scope.row)" />
            </template>
        </el-table-column>
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
            <el-form-item label="账号：">
                <el-input v-model="oneData.code" disabled placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item label="姓名：" prop="name">
                <el-input v-model="oneData.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="密码：">
                <el-input v-model="oneData.password" placeholder="请输入密码，不填默认123456"></el-input>
            </el-form-item>
            <el-form-item label="手机号：" prop="phone">
                <el-input v-model="oneData.phone" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item label="角色：">
                <el-select v-model="selectValue" multiple placeholder="选择角色，非必选">
                    <el-option
                    v-for="item in options"
                    :key="item.code"
                    :label="item.name"
                    :value="item.code"
                    >
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="邮箱：">
                <el-input v-model="oneData.email" placeholder="请输入邮箱"></el-input>
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
            <el-form-item label="账号：" prop="code">
                <el-input v-model="oneData.code" placeholder="请输入账号"></el-input>
            </el-form-item>
            <el-form-item label="姓名：" prop="name">
                <el-input v-model="oneData.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="密码：">
                <el-input v-model="oneData.password" placeholder="请输入密码，不填默认123456"></el-input>
            </el-form-item>
            <el-form-item label="角色：">
                <el-select v-model="selectValue" multiple placeholder="选择角色，非必选">
                    <el-option
                    v-for="item in options"
                    :key="item.code"
                    :label="item.name"
                    :value="item.code"
                    >
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="手机号：" prop="phone">
                <el-input v-model="oneData.phone" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item label="邮箱：">
                <el-input v-model="oneData.email" placeholder="请输入邮箱"></el-input>
            </el-form-item>
            <el-form-item label="启用状态：">
                <el-switch v-model="oneData.enable" />
            </el-form-item>

            <el-form-item style="text-align: right">
                <el-button @click="addHandleClose">取消</el-button>
                <el-button type="primary" @click="add" >确定</el-button>
            </el-form-item>
        </el-form>
    </el-dialog>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted} from 'vue'
import { get,post,del,patch } from '@/api/request'
import { ElMessageBox, ElMessage } from 'element-plus'
// v-model:currentPage="currentPage"
// :current-page="currentPage"
export default({
    setup() {
        let updateDialogVisible = ref(false)
        let addDialogVisible = ref(false)
        let dataFormRuleRef = ref(null);
        let tableSelectIds = ref([])
        let options = ref([])
        let selectValue = ref([])
        let dataTable = reactive({
            list: [],
            pageSize: 10,
            totalCount: null,
            currentPage: 1,
        })
        let params = reactive({
            name: '',
            code: '',
            phone: ''
        })
        let oneData = reactive({
            id: null,
            code: '',
            email: '',
            enable: null,
            name: '',
            phone: '',
            password: '',
            roleCodes: []
        })

        let addRules = reactive({
            code: {required: true, message: '请输入账号', trigger: 'blur'},
            name: {required: true, message: '请输入姓名', trigger: 'blur'},
            phone: {required: true, message: '请输入手机号', trigger: 'blur'}
        })

        function queryData() {
            get('sysUser?currentPage=' + dataTable.currentPage 
            + '&pageSize=' + dataTable.pageSize
            + '&qp-code-like=' + params.code
            + '&qp-name-like=' + params.name
            + '&qp-phone-like=' + params.phone
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
                del('sysUser/' + row.id, {}).then(res => {
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
            get("/sysRole/getAllRoleList", {}).then(res => {
                options.value = res
            })

            get("/sysRole/getUserRoleList?userId=" + id, {}).then(res => {
                res.forEach((v) => {
                    selectValue.value.push(v.code)
                })
            })

            get("/sysUser/" + id, {}).then(res => {
                oneData.id = res.id
                oneData.code = res.code
                oneData.email = res.email
                oneData.enable = res.enable
                oneData.name = res.name
                oneData.phone = res.phone
                updateDialogVisible.value = true
            })
        }
        
        function updated() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    oneData.roleCodes = selectValue.value
                    patch('sysUser/' + oneData.id, oneData).then(res => {
                    oneData.id = null
                    oneData.code = ''
                    oneData.email = ''
                    oneData.enable = null
                    oneData.name = ''
                    oneData.phone = ''
                    oneData.password = ''
                    options.value = []
                    selectValue.value = []
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
            patch('sysUser/' + row.id, {enable: row.enable}).then(res => {
                ElMessage({message: '亲，修改成功', type: 'success'})
            })
        }

        function showAddDialog() {
            get("/sysRole/getAllRoleList", {}).then(res => {
                options.value = res
            })
            addDialogVisible.value = true
        }

        function add() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    oneData.roleCodes = selectValue.value
                    post('sysUser', oneData).then(res => {
                    queryData()
                    addDialogVisible.value = false
                    ElMessage({message: '亲，新增成功', type: 'success'})
                    oneData.id = null
                    oneData.code = ''
                    oneData.email = ''
                    oneData.enable = null
                    oneData.name = ''
                    oneData.phone = ''
                    oneData.password = ''
                    options.value = []
                    selectValue.value = []
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
                    del('sysUser/' + idStr, {}).then(res => {
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
            oneData.id = null
            oneData.code = ''
            oneData.email = ''
            oneData.enable = null
            oneData.name = ''
            oneData.phone = ''
            oneData.password = ''
            options.value = []
            selectValue.value = []
            updateDialogVisible.value = false
        }

        function addHandleClose() {
            oneData.id = null
            oneData.code = ''
            oneData.email = ''
            oneData.enable = null
            oneData.name = ''
            oneData.phone = ''
            oneData.password = ''
            options.value = []
            selectValue.value = []
            addDialogVisible.value = false
        }

        onMounted(() => {
            queryData()
        })

        return {
            dataTable,
            params,
            updateDialogVisible,
            addDialogVisible,
            oneData,
            addRules,
            options,
            dataFormRuleRef,
            mulDelete,
            tableSelectIds,
            handleSelectionChange,
            add,
            showAddDialog,
            updateTop,
            addHandleClose,
            updateHandleClose,
            showDetail,
            selectValue,
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