
<template>
    
    <el-button type="primary" icon="i-plus" plain @click="showAddDialog">新增</el-button>
    <el-button type="primary" icon="i-edit" plain @click="updateTop">修改</el-button>
    <el-button type="primary" icon="i-delete" plain @click="mulDelete">删除</el-button>
    <el-table
      :data="menus"
      style="width: 100%"
      row-key="id"
      lazy
      :load="load"
      default-expand-all
      :tree-props="{ children: 'childrens', hasChildren: 'hasChildren' }"
       @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" />
      <el-table-column prop="id" label="ID" width="180" />
      <el-table-column prop="code" label="编码" width="180" />
      <el-table-column prop="name" label="菜单名称" width="180" />
      <el-table-column prop="path" label="路径" width="180" />
      <el-table-column prop="icon" label="图标" width="180" />
      <el-table-column prop="sort" label="排序" width="180" />
      <el-table-column label="操作">
            <template #default="scope">
                <el-button type="primary" icon="i-edit" @click="showDetail(scope.row.id)"></el-button>
                <el-button type="danger" icon="i-delete" @click="deleted(scope.row)"></el-button>
            </template>
        </el-table-column>
    </el-table>

    <el-dialog
        v-model="updateDialogVisible"
        title="修改弹窗"
        width="30%"
        :before-close="updateHandleClose"
    >
        <el-form :inline="false" :model="oneData" ref="dataFormRuleRef" :rules="addRules" class="demo-form-inline">
            <el-form-item label="菜单编码：" prop="code">
                <el-input v-model="oneData.code" disabled placeholder="请输入菜单编码"></el-input>
            </el-form-item>
            <el-form-item label="菜单名称：" prop="name">
                <el-input v-model="oneData.name" placeholder="请输入菜单名称"></el-input>
            </el-form-item>
            <el-form-item label="上级菜单：">
                <el-select v-model="selectValue" placeholder="选择上级菜单，可不选">
                    <el-option v-for="item in options" :key="item.code" :label="item.name" :value="item.code">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="菜单路径：">
                <el-input v-model="oneData.path" placeholder="请输入菜单路径"></el-input>
            </el-form-item>
            <el-form-item label="菜单图标：">
                <el-input v-model="oneData.icon" placeholder="请输入菜单图标"></el-input>
            </el-form-item>
            <el-form-item label="排序：" prop="name">
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
            <el-form-item label="菜单编码：" prop="code">
                <el-input v-model="oneData.code" placeholder="请输入菜单编码"></el-input>
            </el-form-item>
            <el-form-item label="菜单名称：" prop="name">
                <el-input v-model="oneData.name" placeholder="请输入菜单名称"></el-input>
            </el-form-item>
            <el-form-item label="上级菜单：">
                <el-select v-model="selectValue" placeholder="选择上级菜单，可不选">
                    <el-option v-for="item in options" :key="item.code" :label="item.name" :value="item.code">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="菜单路径：">
                <el-input v-model="oneData.path" placeholder="请输入菜单路径"></el-input>
            </el-form-item>
            <el-form-item label="菜单图标：">
                <el-input v-model="oneData.icon" placeholder="请输入菜单图标"></el-input>
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
        let menus = ref([])
        let options = ref([])
        let selectValue = ref('')
        let dataTable = reactive({
            list: [],
            pageSize: 10,
            totalCount: null,
            currentPage: 1,
        })
        let params = reactive({
            code: '',
            name: '',
            parentCode: '',
            path: ''
        })
        let oneData = reactive({
            id: null,
            code: '',
            name: '',
            parentCode: '',
            path: '',
            sort: null,
            icon: ''
        })

        let addRules = reactive({
            code: {required: true, message: '请输入菜单编码', trigger: 'blur'},
            name: {required: true, message: '请输入菜单名称', trigger: 'blur'},
            sort: {required: true, message: '请输入排序', trigger: 'blur'},
        })

        function queryData() {
            get('sysMenu/menuTree', '').then(res => {
                menus.value = res
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
                del('sysMenu/' + row.id, {}).then(res => {
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

        function showAddDialog() {
            addDialogVisible.value = true
            get('sysMenu/getParentList', {}).then(res => {
                options.value = res
            })
        }

        function showDetail(id) {
            get('sysMenu/getParentList', {}).then(res => {
                options.value = res
            })
            get("sysMenu/" + id, {}).then(res => {
                oneData.id = res.id
                oneData.code = res.code
                oneData.name = res.name
                oneData.parentCode = res.parentCode
                oneData.path = res.path
                oneData.icon = res.icon
                oneData.sort = res.sort
                selectValue.value = res.parentCode
                updateDialogVisible.value = true
            })
            
        }

        function updated() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    patch('sysMenu/' + oneData.id, oneData).then(res => {
                    oneData.code = ''
                    oneData.name = ''
                    oneData.parentCode = ''
                    oneData.path = ''
                    oneData.sort = null
                    oneData.icon = ''
                    selectValue.value = ''
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
            patch('sysMenu/' + row.id, {enable: row.enable}).then(res => {
                ElMessage({message: '亲，修改成功', type: 'success'})
            })
        }

        function add() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    oneData.parentCode = selectValue
                    post('sysMenu', oneData).then(res => {
                    oneData.code = ''
                    oneData.name = ''
                    oneData.parentCode = ''
                    oneData.path = ''
                    oneData.sort = null
                    oneData.icon = ''
                    selectValue.value = ''
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
                    del('sysMenu/' + idStr, {}).then(res => {
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
            oneData.code = ''
            oneData.name = ''
            oneData.parentCode = ''
            oneData.path = ''
            oneData.sort = null
            oneData.icon = ''
            selectValue.value = ''
        }

        function addHandleClose() {
            addDialogVisible.value = false
            oneData.id = null
            oneData.code = ''
            oneData.name = ''
            oneData.parentCode = ''
            oneData.path = ''
            oneData.sort = null
            oneData.icon = ''
            selectValue.value = ''
        }

        onMounted(() => {
            queryData()
        })

        return {
            dataTable,
            params,
            menus,
            selectValue,
            updateDialogVisible,
            addDialogVisible,
            oneData,
            addRules,
            dataFormRuleRef,
            mulDelete,
            tableSelectIds,
            handleSelectionChange,
            add,
            options,
            updateTop,
            showAddDialog,
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












