<template>
  <div class="carousel-wrapper">
    <el-carousel :interval="4000" arrow="always" height="400px" :indicator-position="null">
      <el-carousel-item v-for="item in carousels" :key="item.sort" class="carousel-item">
        <img :src="item.img" class="carousel-image" />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
import { reactive, ref } from '@vue/reactivity'
import { onMounted} from 'vue'
import { get,post,del,patch } from '@/api/http'
import { ElMessageBox, ElMessage } from 'element-plus'

export default({
    setup() {
        let carousels = ref([])

        function getCarousel() {
            get('carousel/list', '').then(res => {
                carousels.value = res
            })
        }
        onMounted(() => {
            getCarousel()
        })

        return {
            carousels
        }
    },
})
</script>


<style lang="scss" scoped>
$primary-green: #2ecc71;
$light-green: #58d68d;
$shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

.carousel-wrapper {
  width: 100%;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: $shadow;
  overflow: hidden;

  ::v-deep(.el-carousel) {
    border-radius: 8px;
  }

  ::v-deep(.el-carousel__container) {
    border-radius: 8px;
    overflow: hidden;
  }

  ::v-deep(.el-carousel__item) {
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 0;
    margin: 0;
  }

  ::v-deep(.el-carousel__arrow) {
    width: 48px;
    height: 48px;
    background-color: rgba(46, 204, 113, 0.85);
    opacity: 0.85;
    transition: all 0.3s ease;
    border-radius: 4px;

    &:hover {
      background-color: $primary-green;
      opacity: 1;
    }

    i {
      font-size: 24px;
    }
  }

}

.carousel-item {
  display: flex !important;
  align-items: center;
  justify-content: center;
  background: #f5f7fa;
  padding: 0 !important;
  width: 100% !important;
  height: 100% !important;

  .carousel-image {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 8px;
    display: block;
  }
}

// 响应式设计
@media (max-width: 768px) {
  .carousel-wrapper {
    ::v-deep(.el-carousel) {
      height: 250px !important;
    }

    ::v-deep(.el-carousel__arrow) {
      width: 40px;
      height: 40px;

      i {
        font-size: 20px;
      }
    }
  }
}
</style>