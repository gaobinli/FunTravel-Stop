<template>
  <div>
    这是hello呀
  </div>
</template>

<script>
  import { onMounted} from 'vue'
  import {get} from '../api/request'

  export default {
    
    setup() {
        onMounted(() => {
          get('captchaImage', '').then(res => {
            console.log(res)
          })
        })
    },
  }
</script>

<style scoped>

</style>
