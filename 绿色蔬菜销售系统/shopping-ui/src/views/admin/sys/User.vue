
<template>
    <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item label="昵称：">
            <el-input v-model="params.nickName" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="手机号：">
            <el-input v-model="params.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="年龄：">
            <el-input v-model="params.age" placeholder="请输入年龄"></el-input>
        </el-form-item>
        <el-form-item label="地址：">
            <el-input v-model="params.address" placeholder="请输入地址"></el-input>
        </el-form-item>
        <el-form-item label="性别：">
            <el-select clearable v-model="params.sex" placeholder="选择性别" style="width: 214px;">
                    <el-option key="1" label="男" value="1"></el-option>
                    <el-option key="2" label="女" value="2"></el-option>
                </el-select>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="queryData()">查询</el-button>
        </el-form-item>
    </el-form>
    <div style="margin-bottom:10px">
        <el-button type="primary" icon="i-plus" plain @click="addDialogVisible = true">新增</el-button>
        <el-button type="primary" icon="i-edit" plain @click="updateTop">修改</el-button>
        <el-button type="primary" icon="i-delete" plain @click="mulDelete">删除</el-button>
    </div>
    
    <el-table :data="dataTable.list" height="550" style="width: 100%;" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />

        <el-table-column prop="phone" label="手机号" />

        <el-table-column prop="nickName" label="昵称" />

        <el-table-column prop="age" label="年龄" />

        <el-table-column prop="sex" label="性别" :formatter='handleSex' />

        <el-table-column prop="address" label="地址" />

        <el-table-column prop="" label="启用状态">
            <template #default="scope">
                <el-switch v-model="scope.row.enable" @change="updateEnable(scope.row)" />
            </template>
        </el-table-column>

        <el-table-column prop="createTime" label="创建时间" :formatter='handleTime' />

        <el-table-column prop="modifyUser" label="修改人" />

        <el-table-column prop="modifyTime" label="修改时间" :formatter='handleTime' />
        <el-table-column label="操作" width="200px">
            <template #default="scope">
                <el-button type="primary" icon="i-refresh" @click="refreshPassword(scope.row)"></el-button>
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
            <el-form-item label="昵称：" prop="nickName">
                <el-input v-model="oneData.nickName" placeholder="请输入昵称"></el-input>
            </el-form-item>
            <el-form-item label="手机号：" prop="phone">
                <el-input v-model="oneData.phone" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item label="年龄：" prop="age">
                <el-input v-model="oneData.age" placeholder="请输入年龄"></el-input>
            </el-form-item>
            <el-form-item label="地址：" prop="address">
                <el-input v-model="oneData.address" placeholder="请输入地址"></el-input>
            </el-form-item>
            <el-form-item label="性别：" prop="sex">
                <el-select clearable v-model="oneData.sex" placeholder="选择角色，非必选">
                        <el-option key="1" label="男" :value=1></el-option>
                        <el-option key="2" label="女" :value=2></el-option>
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
        width="30%"
        :before-close="addHandleClose"
    >
        <el-form :inline="false" :model="oneData" ref="dataFormRuleRef" :rules="addRules" class="demo-form-inline">
            <el-form-item label="昵称：" prop="nickName">
                <el-input v-model="oneData.nickName" placeholder="请输入昵称"></el-input>
            </el-form-item>
            <el-form-item label="手机号：" prop="phone">
                <el-input v-model="oneData.phone" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item label="年龄：" prop="age">
                <el-input v-model="oneData.age" placeholder="请输入年龄"></el-input>
            </el-form-item>
            <el-form-item label="地址：" prop="address">
                <el-input v-model="oneData.address" placeholder="请输入地址"></el-input>
            </el-form-item>
            <el-form-item label="性别：" prop="sex">
                <el-select clearable v-model="oneData.sex" placeholder="选择性别，非必选">
                        <el-option key="1" label="男" :value=1></el-option>
                        <el-option key="2" label="女" :value=2></el-option>
                </el-select>
            </el-form-item>
            
            <!-- <el-form-item style="text-align: right">
                <el-button @click="addHandleClose">取消</el-button>
                <el-button type="primary" @click="add" >确定</el-button>
            </el-form-item> -->
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="addHandleClose">取消</el-button>
            <el-button type="primary" @click="add">确定</el-button>
        </span>
        </template>
    </el-dialog>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted} from 'vue'
import { get,post,del,patch } from '@/api/request'
import { ElMessageBox, ElMessage } from 'element-plus'

export default({
    setup() {
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
            nickName: '',
            phone: '',
            age: '',
            address: '',
            sex: '',
        })
        let oneData = reactive({
            id: null,
            nickName: '',
            phone: '',
            age: '',
            address: '',
            sex: '',
            
        })

        let addRules = reactive({
            nickName: {required: true, message: '请输入昵称', trigger: 'blur'},
            phone: {required: true, message: '请输入手机号', trigger: 'blur'},
            age: {required: true, message: '请输入年龄', trigger: 'blur'},
            address: {required: true, message: '请输入地址', trigger: 'blur'},
            sex: {required: true, message: '请输入性别，1 男 2 女', trigger: 'blur'},
        })

        function queryData() {
            get('user?currentPage=' + dataTable.currentPage
            + '&pageSize=' + dataTable.pageSize
            + '&qp-nickName-like=' + params.nickName
            + '&qp-phone-like=' + params.phone
            + '&qp-age-like=' + params.age
            + '&qp-address-like=' + params.address
            + '&qp-sex-like=' + params.sex
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
                del('user/' + row.id, {}).then(res => {
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
            get("user/" + id, {}).then(res => {
                oneData.id = res.id
                oneData.nickName = res.nickName
                oneData.phone = res.phone
                oneData.age = res.age
                oneData.address = res.address
                oneData.sex = res.sex
                updateDialogVisible.value = true
            })
        }

        function updated() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    patch('user/' + oneData.id, oneData).then(res => {

                    oneData.nickName = ''
                    oneData.phone = ''
                    oneData.age = ''
                    oneData.address = ''
                    oneData.sex = ''
                    
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
            patch('user/' + row.id, {enable: row.enable}).then(res => {
                ElMessage({message: '亲，修改成功', type: 'success'})
            })
        }

        function add() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    post('user', oneData).then(res => {
                    oneData.nickName = ''
                    oneData.phone = ''
                    oneData.age = ''
                    oneData.address = ''
                    oneData.sex = ''
                    
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
                    del('user/' + idStr, {}).then(res => {
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

        function handleSex(row, column, cellValue, index) {
            if(cellValue === 1) {
                return '男'
            } else if (cellValue === 2){
                return '女'
            }
        }

        function updateHandleClose() {
            updateDialogVisible.value = false
            oneData.id = null
            oneData.nickName = ''
            oneData.phone = ''
            oneData.age = ''
            oneData.address = ''
            oneData.sex = ''
            
        }

        function addHandleClose() {
            addDialogVisible.value = false
            oneData.id = null
            oneData.nickName = ''
            oneData.phone = ''
            oneData.age = ''
            oneData.address = ''
            oneData.sex = ''
        }

        function refreshPassword(val) {
            patch('user/' + val.id, { password: '123456' }).then(res => {
                ElMessage({message: '亲，重置密码成功', type: 'success'})
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
            oneData,
            addRules,
            dataFormRuleRef,
            mulDelete,
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
            handleSex,
            handleSizeChange,
            refreshPassword,
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












