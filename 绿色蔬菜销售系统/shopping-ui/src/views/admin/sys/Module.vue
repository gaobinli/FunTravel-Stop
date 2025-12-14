
<template>
    <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item label="模块名：">
            <el-input v-model="params.name" placeholder="请输入模块名"></el-input>
        </el-form-item>
        <el-form-item label="排序：">
            <el-input v-model="params.sort" placeholder="请输入排序"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="queryData()">查询</el-button>
        </el-form-item>
    </el-form>
    <el-button type="primary" icon="i-plus" plain @click="addDialogVisible = true">新增</el-button>
    <el-button type="primary" icon="i-edit" plain @click="updateTop">修改</el-button>
    <el-button type="primary" icon="i-delete" plain @click="mulDelete">删除</el-button>
    <el-table :data="dataTable.list" height="550" style="width: 100%;" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />
        <el-table-column prop="id" label="ID" />

        <el-table-column prop="name" label="模块名" />

        <el-table-column prop="sort" label="排序" />

        <el-table-column prop="createUser" label="创建人" />

        <el-table-column prop="createTime" label="创建时间" :formatter='handleTime' />

        <el-table-column prop="modifyUser" label="修改人" />

        <el-table-column prop="modifyTime" label="修改时间" :formatter='handleTime' />

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
            <el-form-item label="模块名：" prop="name">
                <el-input v-model="oneData.name" placeholder="请输入模块名"></el-input>
            </el-form-item>
            <el-form-item label="排序：" prop="sort">
                <el-input-number v-model="oneData.sort" :min="1" :max="127" @change="handleChange" />
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
            <el-form-item label="模块名：" prop="name">
                <el-input v-model="oneData.name" placeholder="请输入模块名"></el-input>
            </el-form-item>
            <el-form-item label="排序：" prop="sort">
                <el-input-number v-model="oneData.sort" :min="1" :max="127" @change="handleChange" />
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
            name: '',
            sort: '',
        })
        let oneData = reactive({
            id: null,
            name: '',
            sort: '',
        })

        let addRules = reactive({
            name: {required: true, message: '请输入模块名', trigger: 'blur'},
            sort: {required: true, message: '请输入排序', trigger: 'blur'},
        })

        function queryData() {
            get('module?currentPage=' + dataTable.currentPage
            + '&pageSize=' + dataTable.pageSize
            + '&qp-name-like=' + params.name
            + '&qp-sort-like=' + params.sort
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
                del('module/' + row.id, {}).then(res => {
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
            get("module/" + id, {}).then(res => {
                oneData.id = res.id
                oneData.name = res.name
                oneData.sort = res.sort
                updateDialogVisible.value = true
            })
        }

        function updated() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    patch('module/' + oneData.id, oneData).then(res => {

                    oneData.name = ''
                    oneData.sort = ''
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
            patch('module/' + row.id, {enable: row.enable}).then(res => {
                ElMessage({message: '亲，修改成功', type: 'success'})
            })
        }

        function add() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    post('module', oneData).then(res => {
                    oneData.name = ''
                    oneData.sort = ''
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
                    del('module/' + idStr, {}).then(res => {
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
            oneData.sort = ''
        }

        function addHandleClose() {
            addDialogVisible.value = false
            oneData.id = null
            oneData.name = ''
            oneData.sort = ''
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












