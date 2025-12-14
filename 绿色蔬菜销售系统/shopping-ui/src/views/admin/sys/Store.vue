
<template>
    <el-form :inline="true" :model="params" class="demo-form-inline">
        <!-- <el-form-item label="商家：">
            <el-input v-model="params.merchantsCode" clearable placeholder="请输入商家"></el-input>
        </el-form-item> -->
        <el-form-item v-if="adminCode === 'admin'" label="商家：" >
                <el-select v-model="params.merchantsCode" placeholder="选择商家，非必选" style="width: 170px;">
                    <el-option
                    v-for="item in merchantsList"
                    :key="item.code"
                    :label="item.name"
                    :value="item.code"
                    >
                    </el-option>
                </el-select>
            </el-form-item>
        <el-form-item label="店铺名称：">
            <el-input v-model="params.name" clearable placeholder="请输入店铺名称"></el-input>
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
        <el-table-column prop="merchantsName" label="商家" />
        <el-table-column prop="name" label="店铺名称" />
        <el-table-column prop="url" label="店铺封面">
            <template #default="scope">
                <el-image
                    style="width: 100px; height: 100px"
                    :src="scope.row.url"
                    :preview-src-list="[scope.row.url]"
                    :initial-index="0"
                    fit="cover"
                    :append-to-body = "true"
                    :preview-teleported="true"
                    >
                </el-image>
            </template>
        </el-table-column>
        <el-table-column prop="simpleDesc" label="店铺简介" />
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
            <!-- <el-form-item label="商家：" prop="merchantsCode">
                <el-input v-model="oneData.merchantsCode" clearable placeholder="请输入商家"></el-input>
            </el-form-item> -->
            <el-form-item v-if="adminCode === 'admin'" label="商家：">
                <el-select v-model="oneData.merchantsCode" placeholder="选择商家，非必选">
                    <el-option
                    v-for="item in merchantsList"
                    :key="item.code"
                    :label="item.name"
                    :value="item.code"
                    >
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="店铺名称：" prop="name">
                <el-input v-model="oneData.name" clearable placeholder="请输入店铺名称"></el-input>
            </el-form-item>
            <el-form-item label="店铺封面：" prop="url">
                <el-upload
                    :action="$imgUpload"
                    list-type="picture-card"
                    name="file"
                    multiple="false"
                    method="post"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                >
                    <img v-if="oneData.url" :src="oneData.url" class="avatar" style="widht:148px; height:148px" />
                    <el-icon v-else class="avatar-uploader-icon"><plus /></el-icon>
                </el-upload>
            </el-form-item>
            <el-form-item label="店铺简介：" prop="simpleDesc">
                <el-input v-model="oneData.simpleDesc" clearable placeholder="请输入店铺简介"></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogHandleClose">取消</el-button>
            <el-button type="primary" @click="addOrUpdated">确定</el-button>
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
        let adminCode = localStorage.getItem('adminCode')
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
        let dataFormRuleRef = ref(null)
        let tableSelectIds = ref([])
        let dataTable = reactive({
            list: [],
            pageSize: 10,
            totalCount: null,
            currentPage: 1,
        })
        let params = reactive({
            merchantsCode: '',
            name: '',
            url: '',
            simpleDesc: '',
            detail: '',
        })
        let oneData = reactive({
            id: null,
            merchantsCode: '',
            name: '',
            url: '',
            simpleDesc: '',
            detail: '',
        })

        let addRules = reactive({
            merchantsCode: {required: true, message: '请输入商家', trigger: 'blur'},
            name: {required: true, message: '请输入店铺名称', trigger: 'blur'},
            url: {required: true, message: '请输入店铺封面', trigger: 'blur'},
            simpleDesc: {required: true, message: '请输入店铺简介', trigger: 'blur'},
            detail: {required: true, message: '请输入详情', trigger: 'blur'},
        })

        function queryData() {
            get('store?currentPage=' + dataTable.currentPage
            + '&pageSize=' + dataTable.pageSize
            + '&qp-merchantsCode-eq=' + (params.merchantsCode && adminCode =='admin' ? params.merchantsCode : (adminCode =='admin'? '' : adminCode))
            + '&qp-name-like=' + params.name
            + '&qp-url-like=' + params.url
            + '&qp-simpleDesc-like=' + params.simpleDesc
            + '&qp-detail-like=' + params.detail
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
                del('store/' + row.id, {}).then(res => {
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
            get("store/" + id, {}).then(res => {
                oneData.id = res.id
                oneData.merchantsCode = res.merchantsCode
                oneData.name = res.name
                oneData.url = res.url
                oneData.simpleDesc = res.simpleDesc
                oneData.detail = res.detail
                dialogVisible.value = true
            })
        }

        function addOrUpdated() {
            dataFormRuleRef.value.validate((valid) => {
                if (valid) {
                    if (!oneData.merchantsCode) {
                        oneData.merchantsCode = adminCode
                    }
                    if (!oneData.id) {
                        post('store', oneData).then(res => {
                            oneData.merchantsCode = ''
                            oneData.name = ''
                            oneData.url = ''
                            oneData.simpleDesc = ''
                            oneData.detail = ''
                            queryData()
                            ElMessage({message: '亲，新增成功', type: 'success'})
                        })
                    } else {
                        patch('store/' + oneData.id, oneData).then(res => {
                            oneData.merchantsCode = ''
                            oneData.name = ''
                            oneData.url = ''
                            oneData.simpleDesc = ''
                            oneData.detail = ''
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
                    del('store/' + idStr, {}).then(res => {
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
            oneData.merchantsCode = ''
            oneData.name = ''
            oneData.url = ''
            oneData.simpleDesc = ''
            oneData.detail = ''
        }


        function handleAvatarSuccess(res, file) {
            oneData.url = res.data
        }

        let merchantsList = ref([])

        onMounted(() => {
            queryData();
            get('sysUser/list', '').then(res => {
                merchantsList.value = res
            })
        })



        return {
            options,
            change,
            dataTable,
            params,
            dialogVisible,
            oneData,
            addRules,
            dataFormRuleRef,
            mulDelete,
            tableSelectIds,
            handleSelectionChange,
            handleAvatarSuccess,
            updateTop,
            dialogHandleClose,
            showDialog,
            Plus,
            deleted,
            addOrUpdated,
            queryData,
            handleTime,
            handleSizeChange,
            handleCurrentChange,
            adminCode,
            merchantsList
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












