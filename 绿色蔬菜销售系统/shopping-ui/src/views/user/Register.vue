<template>
    <div class="login">
      <el-form :model="registerForm" label-width="120px" class="login-form" :rules="arules" ref="registerFormRule">
        <h3 class="title">注册</h3>
        <el-form-item label="手机号：" prop="phone">
          <el-input v-model="registerForm.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="昵称：" prop="nickName">
          <el-input v-model="registerForm.nickName" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="password">
          <el-input v-model="registerForm.password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码：" prop="repassword">
          <el-input v-model="registerForm.repassword" placeholder="请输入确认密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="年龄：" prop="age">
          <el-input-number v-model="registerForm.age" :min="1" :max="127" />
        </el-form-item>
        <el-form-item label="性别：" prop="sex">
          <el-radio v-model="registerForm.sex" label="1">男</el-radio>
          <el-radio v-model="registerForm.sex" label="2">女</el-radio>
        </el-form-item>
        <el-form-item label="邮寄地址：" prop="address">
          <el-input v-model="registerForm.address" placeholder="请输入邮寄地址"></el-input>
        </el-form-item>
        <el-form-item style="width:90%;">
          <el-button v-if="!loading" type="primary" @click="onSubmit">注册</el-button>
          <el-button v-else type="primary">注册中...</el-button>
        </el-form-item>
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
    let registerFormRule = ref(null)
    let registerForm = reactive({
      password: '',
      repassword: '',
      age: 1,
      nickName: '',
      phone: '',
      address: '',
      sex: '1'
    })

    let arules = reactive({
      phone: [
        { required: true, trigger: ["blur", "change"], message: "请输入正确的手机号"},
        { pattern: /^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\d{8}$/, message: '请输入正确的手机号码' }
      ],
      nickName: [{ required: true, trigger: ["blur", "change"], message: "请输入您的昵称" }],
      password: [{ required: true, trigger: ["blur", "change"], message: "请输入密码" }],
      repassword: [{ required: true, trigger: ["blur", "change"], message: "请输入确认密码" }],
      age: [{ required: true, trigger: ["blur", "change"], message: "请选择年龄" }],
      address: [{ required: true, trigger: ["blur", "change"], message: "请输入邮寄地址" }],
      sex: [{ required: true, trigger: ["blur", "change"], message: "请输入性别" }],
    })

    function onSubmit() {
      registerFormRule.value.validate((valid) => {
        if(valid) {
            if(registerForm.password != registerForm.repassword) {
              ElMessage({ message: '两次密码输入不一致',type: 'error'})
              return false
            }
            post('user', registerForm).then(res => {
            ElMessage({
              message: '注册成功',
              type: 'success',
            })
            router.push({path:'/shopping/login'});
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
      arules,
      registerForm,
      registerFormRule,
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
  background-image: url("../../assets/images/register-bg.jpg");
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
  margin: 0 auto 25px auto;
  text-align: center;
  color: $text-dark;
  font-size: 28px;
  font-weight: 700;
  letter-spacing: 1px;
  padding-bottom: 0;
}

.login-form {
  position: relative;
  z-index: 2;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  width: 100%;
  max-width: 480px;
  padding: 35px 40px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.2);
  transition: all 0.3s ease;
  max-height: 90vh;
  overflow-y: auto;

  &:hover {
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.25);
    transform: translateY(-2px);
  }

  .el-form-item {
    margin-bottom: 18px;

    &:last-of-type {
      margin-bottom: 10px;
    }

    ::v-deep(.el-form-item__label) {
      color: $text-dark;
      font-weight: 500;
      font-size: 14px;
    }
  }

  .el-input {
    height: 40px;

    input {
      height: 40px;
      font-size: 14px;
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
  }

  .input-icon {
    height: 18px;
    width: 18px;
    margin-left: 4px;
    color: #999;
  }

  // 数字输入框
  ::v-deep(.el-input-number) {
    width: 100%;

    .el-input-number__increase,
    .el-input-number__decrease {
      background-color: transparent;
      border-color: #e0e0e0;

      &:hover {
        color: $primary-green;
        border-color: $primary-green;
      }
    }

    .el-input__inner {
      height: 40px;
      border-color: #e0e0e0;
      border-radius: 6px;

      &:focus {
        border-color: $primary-green;
        box-shadow: 0 0 0 2px rgba(46, 204, 113, 0.1);
      }
    }
  }

  // 单选框
  ::v-deep(.el-radio) {
    color: $text-dark;
    margin-right: 20px;

    .el-radio__input.is-checked .el-radio__inner {
      background-color: $primary-green;
      border-color: $primary-green;
    }

    &:hover {
      color: $primary-green;
    }
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 40px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
    border-radius: 6px;
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
  height: 40px;
}

::v-deep(.el-button) {
  width: 100%;
  height: 42px;
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
  margin-top: 4px;
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
    margin-bottom: 20px;
  }

  .el-input {
    height: 38px !important;

    input {
      height: 38px !important;
      font-size: 13px;
    }
  }

  ::v-deep(.el-input-number) {
    .el-input__inner {
      height: 38px !important;
    }
  }

  ::v-deep(.el-button) {
    height: 38px !important;
    font-size: 14px;
  }

  ::v-deep(.el-form-item__label) {
    font-size: 12px !important;
    width: 100px !important;
  }
}

@media (max-width: 480px) {
  .login-form {
    max-width: 95%;
    padding: 25px 20px;

    ::v-deep(.el-form-item__label) {
      display: block;
      width: 100% !important;
      text-align: left;
      margin-bottom: 6px;
      padding-left: 0 !important;
    }

    ::v-deep(.el-form-item__content) {
      margin-left: 0 !important;
    }
  }

  .title {
    font-size: 20px;
    margin-bottom: 18px;
  }

  .el-form-item {
    margin-bottom: 14px !important;
  }

  .el-input {
    height: 36px !important;

    input {
      height: 36px !important;
      font-size: 13px;
    }
  }

  ::v-deep(.el-input-number) {
    .el-input__inner {
      height: 36px !important;
    }
  }

  ::v-deep(.el-radio) {
    margin-right: 12px !important;
  }

  ::v-deep(.el-button) {
    height: 36px !important;
    font-size: 13px;
  }
}
</style>