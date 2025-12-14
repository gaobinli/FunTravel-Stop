<template>
  <div class="footer-content">
    <div class="footer-text">
      <span class="icon">🥬</span>
      <span>Copyright © 绿色蔬菜销售系统</span>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.footer-content {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;

  .footer-text {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 14px;
    letter-spacing: 0.5px;

    .icon {
      font-size: 18px;
    }
  }
}
</style>