
<template>
        <el-form :inline="false" :model="oneData" ref="dataFormRuleRef" :rules="addRules" class="demo-form-inline">
            <el-form-item label="蔬菜名称：" prop="name">
                <el-input v-model="oneData.name" placeholder="请输入蔬菜名称"></el-input>
            </el-form-item>

            <el-form-item label="店铺：" prop="storeId">
                <el-select v-model="oneData.storeId" placeholder="选择店铺，必选">
                    <el-option
                    v-for="item in storeList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                    >
                    </el-option>
                </el-select>
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
            <el-form-item label="图片上传：" prop="img">
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
                <el-input-number v-model="oneData.inventory" :min="1" :max="9999999" @change="handleChange" />
            </el-form-item>
            
            <el-form-item label="蔬菜详情">
                <v-wangeditor
                    :content="oneData.detail"
                    :options="options"
                    @change="change"
                    style="width: 80%; margin: 0 0"
                />
            </el-form-item>
            <el-form-item style="padding-left:45%">
                <el-button @click="toSku">返回</el-button>
                <el-button type="primary" @click="add">确定</el-button>
            </el-form-item>
        </el-form>

    
</template>

<script>
import { reactive, ref, onMounted, onBeforeUnmount,getCurrentInstance } from 'vue'
import { get,post,del,patch } from '@/api/request'
import { ElMessageBox, ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons'
import { useRouter } from 'vue-router'

export default({
    components: { Plus },
    setup() {
        let adminCode = localStorage.getItem('adminCode')
        const router = useRouter()
        const { proxy } = getCurrentInstance()
        const options = reactive({
            height: 600,
            pasteIgnoreImg: false,
            uploadImgServer: proxy.$imgUpload,
            uploadFileName: 'file',
            uploadImgHooks: {
                customInsert: function (insertLinkImg, result, editor) {
                console.log('customInsert')
                console.log('result：', result)
                // 图片上传并返回结果，自定义插入图片的事件，而不是编辑器自动插入图片
                insertLinkImg(result.data)
            },
            }
        })

        function change({ html }) {
            console.log(html)
            oneData.detail = html
        }

        let dataFormRuleRef = ref(null);
       
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
            storeId: {required: true, message: '请选择店铺', trigger: 'blur'},
        })

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
                        ElMessage({message: '亲，新增成功', type: 'success'})
                        router.push({path:'/admin/sku'})
                    })
                } else {
                    return false
                }
            })
        }

        function handleAvatarSuccess(res, file) {
            oneData.img = res.data
        }

        function toSku() {
            router.push({path:'/admin/sku'})
        }
       let merchantsList = ref([])
       let storeList = ref([])
       onMounted(() => {
            get('module/list', '').then(res => {
                moduleList.value = res
            })
            get('store/list?qp-merchantsCode-eq=' + (adminCode === 'admin' ? '' : adminCode), '').then(res => {
                let storeIds = '';
                if (res) {
                    storeIds = res.map(item => item.id).join(',');
                    storeList.value = res
                }
            })
        })

        return {
            oneData,
            dataFormRuleRef,
            addRules,
            moduleList,
            merchantsList,
            add,
            handleAvatarSuccess,
            toSku,
            change,
            options,
            adminCode,
            storeList
        }
    }
})
</script>

<style lang="scss" scoped>
.el-pagination {
    margin-top: 20px;
    text-align: right;
}
</style>












