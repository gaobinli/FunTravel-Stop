<template>
  <div class="announcement-detail-container">
    <div class="detail-header">
      <router-link :to="{path:'/shopping/message'}" class="back-btn">
        <span>← 返回列表</span>
      </router-link>
    </div>

    <div v-if="announcement" class="detail-content">
      <div class="detail-card">
        <h1 class="detail-title">{{ announcement.title }}</h1>
        <div class="detail-meta">
          <span class="meta-item">
            <span class="meta-label">发布时间：</span>
            <span class="meta-value">{{ formatDate(announcement.createTime) }}</span>
          </span>
        </div>

        <div class="detail-body">
          <div v-html="announcement.content" class="rich-text"></div>
        </div>
      </div>
    </div>

    <div v-else class="loading">
      <el-empty description="加载中..." />
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { get } from '@/api/http'

export default {
  setup() {
    const route = useRoute()
    let announcement = ref(null)

    function getDetail() {
      const id = route.params.id
      get('announcement/' + id, '').then(res => {
        announcement.value = res
      })
    }

    function formatDate(dateStr) {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      return `${year}-${month}-${day} ${hours}:${minutes}`
    }

    onMounted(() => {
      getDetail()
    })

    return {
      announcement,
      formatDate,
    }
  },
}
</script>

<style lang="scss" scoped>
$primary-green: #2ecc71;
$light-green: #58d68d;
$text-dark: #2c3e50;
$shadow: 0 2px 12px rgba(0, 0, 0, 0.1);

.announcement-detail-container {
  padding: 20px;
  background-color: transparent;
  min-height: 80vh;
}

.detail-header {
  margin-bottom: 20px;

  .back-btn {
    color: $primary-green;
    text-decoration: none;
    font-size: 14px;
    font-weight: 500;
    transition: all 0.3s ease;
    display: inline-block;

    &:hover {
      color: $light-green;
      transform: translateX(-4px);
    }

    span {
      display: inline-block;
    }
  }
}

.detail-content {
  .detail-card {
    background: white;
    border-radius: 8px;
    box-shadow: $shadow;
    padding: 40px;
  }
}

.detail-title {
  color: $text-dark;
  font-size: 32px;
  font-weight: 600;
  margin: 0 0 20px 0;
  line-height: 1.4;
}

.detail-meta {
  display: flex;
  align-items: center;
  gap: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid #f0f0f0;
  margin-bottom: 30px;

  .meta-item {
    display: flex;
    align-items: center;
    gap: 8px;
    font-size: 14px;

    .meta-label {
      color: #999;
      font-weight: 500;
    }

    .meta-value {
      color: $text-dark;
    }
  }
}

.detail-body {
  padding: 20px 0;
}

.rich-text {
  color: $text-dark;
  font-size: 16px;
  line-height: 1.8;
  word-break: break-word;

  // 富文本样式
  ::v-deep {
    h1, h2, h3, h4, h5, h6 {
      color: $text-dark;
      margin: 20px 0 10px 0;
      font-weight: 600;
    }

    h1 {
      font-size: 28px;
      border-bottom: 2px solid $primary-green;
      padding-bottom: 10px;
    }

    h2 {
      font-size: 24px;
    }

    h3 {
      font-size: 20px;
    }

    p {
      margin: 12px 0;
    }

    ul, ol {
      margin: 12px 0;
      padding-left: 30px;
    }

    li {
      margin: 6px 0;
    }

    blockquote {
      border-left: 4px solid $primary-green;
      padding: 12px 16px;
      background-color: #f9fdf8;
      margin: 12px 0;
    }

    code {
      background-color: #f5f5f5;
      padding: 2px 6px;
      border-radius: 4px;
      font-family: monospace;
      color: #d63200;
    }

    pre {
      background-color: #f5f5f5;
      padding: 12px 16px;
      border-radius: 4px;
      overflow-x: auto;
      margin: 12px 0;

      code {
        background-color: transparent;
        padding: 0;
        color: inherit;
      }
    }

    img {
      max-width: 100%;
      height: auto;
      border-radius: 4px;
      margin: 12px 0;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin: 12px 0;

      th, td {
        border: 1px solid #ddd;
        padding: 8px 12px;
        text-align: left;
      }

      th {
        background-color: #f5f5f5;
        color: $text-dark;
        font-weight: 600;
      }

      tr:nth-child(even) {
        background-color: #f9f9f9;
      }
    }

    a {
      color: $primary-green;
      text-decoration: none;
      transition: color 0.3s ease;

      &:hover {
        color: $light-green;
        text-decoration: underline;
      }
    }

    strong {
      color: $text-dark;
      font-weight: 600;
    }

    em {
      color: $primary-green;
    }
  }
}

.loading {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

// 响应式设计
@media (max-width: 768px) {
  .announcement-detail-container {
    padding: 15px;
  }

  .detail-card {
    padding: 20px !important;
  }

  .detail-title {
    font-size: 24px;
    margin-bottom: 15px;
  }

  .detail-meta {
    flex-direction: column;
    gap: 10px;
    align-items: flex-start;
    padding-bottom: 15px;
    margin-bottom: 20px;
  }

  .rich-text {
    font-size: 14px;
    line-height: 1.6;

    ::v-deep {
      h1 {
        font-size: 22px;
      }

      h2 {
        font-size: 18px;
      }

      h3 {
        font-size: 16px;
      }
    }
  }
}
</style>
