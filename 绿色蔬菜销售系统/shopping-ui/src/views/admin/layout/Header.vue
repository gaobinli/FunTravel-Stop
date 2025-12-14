<template>
    <header>
        <div class="l-content">
            <el-button plain size="mini" icon="i-menu" @click="handleMenu"></el-button>
            <el-breadcrumb separator="/">
                <!-- <el-breadcrumb-item :to="{ path: '/admin/home' }" style="!color: #7d8295">首页</el-breadcrumb-item> -->
                <el-breadcrumb-item v-if="current">{{current.name}}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="r-content">
            <el-dropdown trigger="click" size="mini">
                <span class="el-dropwodn-link">
                    <img :src="userImg" class="user">
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="showDetail">个人信息</el-dropdown-item>
                        <el-dropdown-item @click="logOut()">退出</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>

        <el-dialog
        v-model="updateDialogVisible"
        title="管理员个人信息"
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
                <el-input v-model="oneData.password" placeholder="如需修改密码则输入，不填不修改密码"></el-input>
            </el-form-item>
            <el-form-item label="手机号：" prop="phone">
                <el-input v-model="oneData.phone" placeholder="请输入手机号"></el-input>
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
    </header>
</template>

<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { computed, reactive, ref } from 'vue'
import { get,post,del,patch } from '@/api/request'
import { ElMessageBox, ElMessage } from 'element-plus'

export default({
    setup() {
        const userImg = require("@/assets/images/login-background.jpg")
        const store = useStore()
        const router = useRouter()
        let updateDialogVisible = ref()
        let oneData = reactive({
            id: null,
            code: '',
            email: '',
            enable: null,
            name: '',
            phone: '',
            password: ''
        })    
        function handleMenu() {
            store.commit('collapseMenu')
        }
    

        function logOut() {
            localStorage.removeItem("adminToken")
            localStorage.removeItem('adminCode')
            store.commit("clearTabList")
            router.push({path: '/admin'})
        }

        let current = computed(() => {
            return store.state.tab.currentMenu
        })

        function showDetail() {
            const code = localStorage.getItem('adminCode')
            get('sysUser/getInfo?code=' + code, {}).then(res => {
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
            patch('sysUser/' + oneData.id, oneData).then(res => {
                oneData.id = null
                oneData.code = ''
                oneData.email = ''
                oneData.enable = null
                oneData.name = ''
                oneData.phone = ''
                oneData.password = ''
                updateDialogVisible.value = false
                ElMessage({message: '亲，修改成功', type: 'success'})
            })
        }

        function updateHandleClose() {
            oneData.id = null
            oneData.code = ''
            oneData.email = ''
            oneData.enable = null
            oneData.name = ''
            oneData.phone = ''
            oneData.password = ''
            updateDialogVisible.value = false
        }

        return {
            userImg,
            current,
            oneData,
            updated,
            updateDialogVisible,
            updateHandleClose,
            logOut,
            handleMenu,
            showDetail
        }
    },
})
</script>

<style lang="scss" scoped>
header {
    display: flex;
    height: 100%;
    align-items: center;
    justify-content: space-between;
}

.l-content {
    display: flex;
    align-items: center;
    .el-button {
        margin-right: 20px;
    }
}

.r-content {
    .user {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        cursor: pointer;
        vertical-align: middle;
    }
}
</style>

<style lang="scss" scoped>
.el-breadcrumb__inner .is-link {
    color: #fff;
    font-weight: normal;
}
</style>
