<template>
    <div class="login">
    <el-form :model="loginForm" :rules="loginRules" ref="loginFormRule" class="login-form">
      <h3 class="title">账号登录</h3>
      
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" placeholder="账号" type="text">
        <template #prefix>
          <svg-icon icon-class="user" class="el-input__icon input-icon" />
        </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" placeholder="密码" show-password>
          <template #prefix>
          <svg-icon icon-class="password" class="el-input__icon input-icon" />
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input v-model="loginForm.code" placeholder="验证码" style="width: 63%">
          <template #prefix>
            <svg-icon icon-class="validCode" class="el-input__icon input-icon" />
          </template>
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img"/>
        </div>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button v-if="!loading" type="primary" @click="onSubmit">登录</el-button>
        <el-button v-else type="primary">登录中...</el-button>
      </el-form-item>
      <div class="register-class">
          <router-link :to="{path:'/shopping/register'}">注册</router-link>
      </div>
    </el-form>
  </div>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted} from 'vue'
import { get,post } from '../../api/http'
import { useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  setup() {
    const router = useRouter()
    let loading = ref(false)
    let codeUrl = ref('')
    let loginFormRule = ref(null)
    let loginForm = reactive({
      username: '13301867905',
      password: '123456',
      uuid: '',
      code: '1234'
    })

    let loginRules = reactive({
      username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
      ],
      password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
      ],
      code: [{ required: true, trigger: "change", message: "请输入验证码" }]
    })

    onMounted(() => {
        getCode()
    })

    function getCode() {
      get('captchaImage', '').then(res => {
        codeUrl.value = "data:image/gif;base64," + res.img
        loginForm.uuid = res.uuid
      })
    }

    function onSubmit() {
      loginFormRule.value.validate((valid) => {
        if(valid) {
          post('user/login', loginForm).then(res => {
            localStorage.setItem('userCode', loginForm.username)
            ElMessage({ message: '登录成功，跳转首页中', type: 'success' })
            router.push({path:'/shopping'});
            setTimeout(function () {
              window.location.reload();
            }, 10);
          })
        } else {
            return false
        }
      })
      
    }

    return {
      loading,
      codeUrl,
      loginForm,
      loginRules,
      loginFormRule,
      getCode,
      onSubmit,
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// 绿色蔬菜系统主色调
$primary-green: #2ecc71;
$light-green: #58d68d;
$dark-green: #27ae60;
$text-dark: #2c3e50;
$text-light: #ffffff;
$shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

.login {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-image: url("../../assets/images/user_login.jpg");
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
  background-repeat: no-repeat;
  overflow: hidden;

  // 半透明遮罩层
  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
    z-index: 1;
  }
}

.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: $text-dark;
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 1px;
}

.login-form {
  position: relative;
  z-index: 2;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  width: 100%;
  max-width: 420px;
  padding: 40px 35px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.2);
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.25);
    transform: translateY(-2px);
  }

  .el-form-item {
    margin-bottom: 20px;

    &:last-of-type {
      margin-bottom: 10px;
    }
  }

  .el-input {
    height: 44px;

    input {
      height: 44px;
      font-size: 15px;
      border-color: #e0e0e0;
      border-radius: 6px;
      transition: all 0.3s ease;

      &:focus {
        border-color: $primary-green;
        box-shadow: 0 0 0 2px rgba(46, 204, 113, 0.1);
      }

      &::placeholder {
        color: #bdbdbd;
      }
    }

    .el-input__prefix {
      display: flex;
      align-items: center;
    }
  }

  .input-icon {
    height: 18px;
    width: 18px;
    margin-left: 4px;
    color: #999;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 44px;
  float: right;
  display: flex;
  align-items: center;

  img {
    cursor: pointer;
    vertical-align: middle;
    border-radius: 6px;
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: all 0.3s ease;

    &:hover {
      transform: scale(1.02);
      box-shadow: 0 2px 8px rgba(46, 204, 113, 0.2);
    }
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
  background: rgba(0, 0, 0, 0.3);
  z-index: 10;
}

.login-code-img {
  height: 44px;
}

::v-deep(.el-button) {
  width: 100%;
  height: 44px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 6px;
  border: none;
  transition: all 0.3s ease;
  margin-top: 10px;

  &.el-button--primary {
    background-color: $primary-green;
    border-color: $primary-green;
    color: $text-light;

    &:hover {
      background-color: $light-green;
      border-color: $light-green;
      box-shadow: 0 4px 16px rgba(46, 204, 113, 0.4);
      transform: translateY(-2px);
    }

    &:active {
      background-color: $dark-green;
      transform: translateY(0);
    }
  }
}

::v-deep(.el-form-item__error) {
  font-size: 12px;
  color: #f56c6c;
}

.register-class {
  text-align: right;
  padding-top: 10px;
  margin-top: 10px;

  a {
    color: $primary-green;
    text-decoration: none;
    font-weight: 500;
    transition: all 0.3s ease;
    position: relative;

    &::after {
      content: '';
      position: absolute;
      bottom: -2px;
      left: 0;
      width: 0;
      height: 2px;
      background-color: $primary-green;
      transition: width 0.3s ease;
    }

    &:hover {
      color: $dark-green;

      &::after {
        width: 100%;
      }
    }
  }
}

// 响应式设计
@media (max-width: 768px) {
  .login {
    background-position: center center;
    background-attachment: scroll;
  }

  .login-form {
    max-width: 90%;
    padding: 30px 25px;
  }

  .title {
    font-size: 24px;
    margin-bottom: 25px;
  }

  .el-input {
    height: 40px !important;

    input {
      height: 40px !important;
      font-size: 14px;
    }
  }

  .login-code {
    height: 40px;
  }

  .login-code-img {
    height: 40px;
  }

  ::v-deep(.el-button) {
    height: 40px !important;
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .login-form {
    max-width: 95%;
    padding: 25px 20px;
  }

  .title {
    font-size: 20px;
    margin-bottom: 20px;
  }

  .el-form-item {
    margin-bottom: 16px !important;
  }

  .el-input {
    height: 38px !important;

    input {
      height: 38px !important;
      font-size: 13px;
    }
  }

  .login-code {
    height: 38px;
  }

  .login-code-img {
    height: 38px;
  }

  ::v-deep(.el-button) {
    height: 38px !important;
    font-size: 13px;
  }
}
</style>