<template>
    <el-container>
      <el-header>
          <Header/>
      </el-header>
      <el-main>
          <router-view/>
      </el-main>
      <el-footer>
          <Footer/>
      </el-footer>
    </el-container>
</template>

<script>
import Header from './Header.vue'
import Footer from './Footer.vue'

export default ({
    setup() {
        
    },
    components: { Header,Footer }
})
</script>

<style scoped>
.el-main {
    margin: 0px 200px;
    margin-top: 70px;
    z-index: 100;
    margin-bottom: 60px;
    height: 100%;
}
.el-footer {
  background: linear-gradient(90deg, #2ecc71 0%, #27ae60 100%);
  color: #ffffff;
  text-align: center;
  line-height: 60px;
  bottom: 0;
  position: fixed;
  width: 100%;
  z-index: 300;
  box-shadow: 0 -2px 12px rgba(0, 0, 0, 0.1);
  font-weight: 500;
}
.el-header {
    padding: 0;
    background: transparent;
    position: fixed;
    top: 0;
    width: 100%;
    z-index: 500;
}
</style>