
<template>
    <el-form :inline="true" :model="params" class="demo-form-inline">
        <el-form-item label="标题：">
            <el-input v-model="params.title" clearable placeholder="请输入标题"></el-input>
        </el-form-item>
        <el-form-item label="内容：">
            <el-input v-model="params.content" clearable placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="queryData()">查询</el-button>
        </el-form-item>
    </el-form>
    <div style="margin-bottom:10px">
        <el-button type="primary" icon="i-plus" plain @click="showDialog(null)">新增</el-button>
        <el-button type="primary" icon="i-edit" plain @click="updateTop">修改</el-button>
        <el-button type="primary" icon="i-delete" plain @click="mulDelete">删除</el-button>
    </div>
    <el-table :data="dataTable.list" height="70vh" style="width: 100%;margin-top:10px;" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />
        <el-table-column prop="id" label="主键" />
        <el-table-column prop="title" label="标题" />
        <el-table-column label="内容">
            <template #default="scope">
                <el-button type="primary" link @click="viewContent(scope.row)">查看</el-button>
            </template>
        </el-table-column>
        <el-table-column prop="modifyUser" label="更新人" />
        <el-table-column prop="modifyTime" label="更新时间" :formatter='handleTime' />
        <el-table-column label="操作" width="130px">
            <template #default="scope">
                <el-button type="primary" icon="i-edit" @click="showDialog(scope.row.id)"></el-button>
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
        v-model="dialogVisible"
        :title="oneData.id ? '修改弹窗' : '新增弹窗'"
        width="30%"
        :before-close="dialogHandleClose"
        center
    >
        <el-form :inline="false" :model="oneData" ref="dataFormRuleRef" :rules="addRules" class="demo-form-inline" label-width="auto">
            <el-form-item label="标题：" prop="title">
                <el-input v-model="oneData.title" clearable placeholder="请输入标题"></el-input>
            </el-form-item>
            <el-form-item label="内容：" prop="content">
                <v-wangeditor
                    :content="oneData.content"
                    :options="options"
                    @change="change"
                    style="width: 100%; margin: 0 0"
                />
            </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogHandleClose">取消</el-button>
            <el-button type="primary" @click="addOrUpdated">确定</el-button>
        </span>
        </template>
    </el-dialog>

    <el-dialog
        v-model="viewDialogVisible"
        :title="viewData.title"
        width="50%"
        @close="viewDialogHandleClose"
        center
    >
        <div class="content-view" v-html="viewData.content"></div>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="viewDialogHandleClose">关闭</el-button>
        </span>
        </template>
    </el-dialog>
</template>

<script>
import { reactive, ref, onMounted, onBeforeUnmount,getCurrentInstance } from 'vue'
import { get,post,del,patch } from '@/api/request'
import { ElMessageBox, ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { Plus } from '@element-plus/icons'

export default({
    components: { Plus },
    setup() {
        const userId = localStorage.getItem('userId')
        const router = useRouter()
        const { proxy } = getCurrentInstance()
        const options = reactive({
            height: 300,
            pasteIgnoreImg: false,
            uploadImgServer: proxy.$imgUpload,
            uploadFileName: 'file',
            uploadImgHooks: {
                customInsert: function (insertLinkImg, result, editor) {
                console.log('customInsert')
                console.log('result：', result)
                // 图片上传并返回结果，自定义插入图片的事件，而不是编辑器自动插入图片
                insertLinkImg(result.data)
                }
            },
            uploadVideoServer: proxy.$imgUpload,
            uploadVideoName: 'file',
            uploadVideoHooks: {
                customInsert: function(insertVideoFn, result) {
                    // result 即服务端返回的接口
                    console.log('customInsert', result)
                    // insertVideoFn 可把视频插入到编辑器，传入视频 src ，执行函数即可
                    insertVideoFn(result.data)
                }
            },
        })
        function change({ html }) {
            console.log(html)
            oneData.content = html
        }
        let dialogVisible = ref(false)
        let viewDialogVisible = ref(false)
        let dataFormRuleRef = ref(null)
        let tableSelectIds = ref([])
        let dataTable = reactive({
            list: [],
            pageSize: 10,
            totalCount: null,
            currentPage: 1,
        })
        let params = reactive({
            title: '',
            content: '',
        })
        let oneData = reactive({
            id: null,
            title: '',
            content: '',
        })
        let viewData = reactive({
            title: '',
            content: '',
        })

        let addRules = reactive({
            title: {required: true, message: '请输入标题', trigger: 'blur'},
            content: {required: true, message: '请输入内容', trigger: 'blur'},
        })

        function queryData() {
            get('announcement?currentPage=' + dataTable.currentPage
            + '&pageSize=' + dataTable.pageSize
            + '&qp-title-like=' + params.title
            + '&qp-content-like=' + params.content
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
                del('announcement/' + row.id, {}).then(res => {
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

        function showDialog(id) {
            if(!id) {
                dialogVisible.value = true
                oneData.content = ' '
                return
            }
            get("announcement/" + id, {}).then(res => {
                oneData.id = res.id
                oneData.title = res.title
                oneData.content = res.content
                dialogVisible.value = true
            })
        }

        function addOrUpdated() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    if (!oneData.id) {
                        post('announcement', oneData).then(res => {
                            oneData.title = ''
                            oneData.content = ''
                            queryData()
                            ElMessage({message: '亲，新增成功', type: 'success'})
                        })
                    } else {
                        patch('announcement/' + oneData.id, oneData).then(res => {
                            oneData.id = ''
                            oneData.title = ''
                            oneData.content = ''
                            queryData()
                            ElMessage({message: '亲，修改成功', type: 'success'})
                        })
                    }
                    dialogVisible.value = false
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
                    del('announcement/' + idStr, {}).then(res => {
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
                showDialog(tableSelectIds.value[0].id)
            } else if(tableSelectIds.value.length > 1) {
                ElMessage({message: '亲，只能选择一行进行修改哦', type: 'error'})
            } else {
                ElMessage({message: '亲，请至少选择一行进行修改哦', type: 'error'})
            }
        }

        function dialogHandleClose() {
            dialogVisible.value = false
            oneData.id = null
            oneData.title = ''
            oneData.content = ''
        }

        function viewContent(row) {
            viewData.title = row.title
            viewData.content = row.content
            viewDialogVisible.value = true
        }

        function viewDialogHandleClose() {
            viewDialogVisible.value = false
            viewData.title = ''
            viewData.content = ''
        }


        function handleAvatarSuccess(res, file) {
            oneData.url = res.data
        }


        onMounted(() => {
            queryData();
        })



        return {
            options,
            change,
            dataTable,
            params,
            dialogVisible,
            viewDialogVisible,
            oneData,
            viewData,
            addRules,
            dataFormRuleRef,
            mulDelete,
            tableSelectIds,
            handleSelectionChange,
            handleAvatarSuccess,
            updateTop,
            dialogHandleClose,
            viewDialogHandleClose,
            showDialog,
            viewContent,
            Plus,
            deleted,
            addOrUpdated,
            queryData,
            handleTime,
            handleSizeChange,
            handleCurrentChange,
        }
    }
})
</script>

<style lang="scss" scoped>
.el-pagination {
    margin-top: 20px;
    float: right;
}

.content-view {
    padding: 20px;
    line-height: 1.6;
    word-break: break-word;

    img {
        max-width: 100%;
        height: auto;
    }

    p {
        margin: 10px 0;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin: 10px 0;

        td, th {
            border: 1px solid #ddd;
            padding: 8px;
        }
    }

    pre {
        background-color: #f5f5f5;
        padding: 10px;
        border-radius: 4px;
        overflow-x: auto;
    }
}
</style>












