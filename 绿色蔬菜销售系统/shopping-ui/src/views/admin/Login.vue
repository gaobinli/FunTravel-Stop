  <template>
  <div class="login">
    <el-form :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">绿色蔬菜销售系统后台管理</h3>
      
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" placeholder="账号" type="text">
        <template #prefix>
          <svg-icon icon-class="user" class="el-input__icon input-icon" />
        </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" placeholder="密码" show-password class="custom_input">
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
      <div class="register-link">
        <router-link :to="{path:'/admin/merchantRegister'}">注册成为商家</router-link>
      </div>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 绿色蔬菜销售系统后台管理</span>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted} from 'vue'
import { get,post } from '../../api/request'
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter()
    let loading = ref(false)
    let codeUrl = ref('')
    let loginForm = reactive({
      username: 'admin',
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
      post('admin/login', loginForm).then(res => {
        localStorage.setItem('adminCode', loginForm.username)
        localStorage.setItem('adminToken', res)
        router.push({ path: '/admin/home' })
      })
    }

    return {
      loading,
      codeUrl,
      loginForm,
      loginRules,
      getCode,
      onSubmit,
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login {
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

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;
  
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

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  padding-left: 10px;
  img {
    cursor: pointer;
    vertical-align: middle;
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
.login-code-img {
  height: 38px;
}
.el-button{
  width: 100%;
}
.register-link {
  text-align: right;
  margin-top: 10px;
  padding-right: 5px;
}
.register-link a {
  color: #409eff;
  text-decoration: none;
  font-size: 12px;
  transition: color 0.3s;
}
.register-link a:hover {
  color: #66b1ff;
  text-decoration: underline;
}
</style>