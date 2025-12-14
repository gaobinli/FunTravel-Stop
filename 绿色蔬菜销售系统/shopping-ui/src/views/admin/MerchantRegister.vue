<template>
  <div class="register">
    <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef" class="register-form">
      <h3 class="title">商家注册</h3>

      <el-form-item prop="code">
        <el-input v-model="registerForm.code" placeholder="账号" type="text">
        <template #prefix>
          <svg-icon icon-class="user" class="el-input__icon input-icon" />
        </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="name">
        <el-input v-model="registerForm.name" placeholder="姓名" type="text">
        <template #prefix>
          <svg-icon icon-class="user" class="el-input__icon input-icon" />
        </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="password">
        <el-input v-model="registerForm.password" placeholder="密码" show-password class="custom_input">
          <template #prefix>
          <svg-icon icon-class="password" class="el-input__icon input-icon" />
          </template>
        </el-input>
      </el-form-item>

      <el-form-item prop="phone">
        <el-input v-model="registerForm.phone" placeholder="手机号" type="text">
        <template #prefix>
          <svg-icon icon-class="phone" class="el-input__icon input-icon" />
        </template>
        </el-input>
      </el-form-item>

      <div class="button-group">
        <el-button type="primary" @click="onRegister">注册</el-button>
        <router-link :to="{path:'/admin/login'}">
          <el-button>返回登录</el-button>
        </router-link>
      </div>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 绿色蔬菜销售系统后台管理</span>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from 'vue'
import { post } from '@/api/request'
import { useRouter } from "vue-router";
import { ElMessage } from 'element-plus'
import SvgIcon from '@/components/SvgIcon/index.vue'

export default {
  components: { SvgIcon },
  setup() {
    const router = useRouter()
    let registerFormRef = ref(null)
    let registerForm = reactive({
      code: '',
      name: '',
      password: '',
      phone: '',
      email: '',
      enable: false,
      roleCodes: []
    })

    let registerRules = reactive({
      code: [
          { required: true, trigger: "blur", message: "请输入账号" }
      ],
      name: [
          { required: true, trigger: "blur", message: "请输入姓名" }
      ],
      password: [
          { required: true, trigger: "blur", message: "请输入密码" }
      ],
      phone: [
          { required: true, trigger: "blur", message: "请输入手机号" }
      ],
      email: [
          { required: true, trigger: "blur", message: "请输入邮箱" }
      ]
    })

    function onRegister() {
      registerFormRef.value.validate((valid) => {
        if (valid) {
          post('sysUser', registerForm).then(res => {
            ElMessage({
              message: '亲，注册成功，请使用账号密码登录',
              type: 'success',
            })
            router.push({ path: '/admin/login' })
          })
        } else {
          return false
        }
      })
    }

    return {
      registerFormRef,
      registerForm,
      registerRules,
      onRegister,
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.register {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../../assets/images/login-background.jpg");
  background-size: cover;
}
.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.register-form {
  border-radius: 6px;
  background: #ffffff;
  width: 450px;
  padding: 25px 25px 5px 25px;
  max-height: 85vh;
  overflow-y: auto;

  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.button-group {
  display: flex;
  gap: 10px;
  margin-top: 20px;

  .el-button {
    flex: 1;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

a {
  text-decoration: none;
  width: 50%;

  .el-button {
    width: 100%;
  }
}
</style>
