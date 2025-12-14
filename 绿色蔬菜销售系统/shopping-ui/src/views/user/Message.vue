<template>
  <div class="announcement-container">
    <div class="announcement-header">
      <h2 class="page-title">系统公告</h2>
    </div>

    <div class="announcement-list">
      <el-empty v-if="announcements.list.length === 0" description="暂无公告" />

      <el-row v-else :gutter="0">
        <el-col :span="24" v-for="item in announcements.list" :key="item.id">
          <div class="announcement-item" @click="goDetail(item.id)">
            <div class="item-left">
              <h3 class="item-title">{{ item.title }}</h3>
              <span class="item-time">{{ formatDate(item.createTime) }}</span>
            </div>
            <div class="item-right">
              <span class="arrow">→</span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <el-pagination
      v-if="announcements.totalCount > 0"
      :page-sizes="[10, 15, 20, 30]"
      :page-size="announcements.pageSize"
      :current-page="announcements.currentPage"
      layout="total, sizes, prev, pager, next, jumper"
      :total="announcements.totalCount"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      class="pagination"
    >
    </el-pagination>
  </div>
</template>

<script>
import { reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { get } from '@/api/http'

export default {
  setup() {
    const router = useRouter()

    let announcements = reactive({
      list: [],
      pageSize: 10,
      totalCount: 0,
      currentPage: 1,
    })

    function queryData() {
      let queryUrl = 'announcement?currentPage=' + announcements.currentPage
        + '&pageSize=' + announcements.pageSize
        + '&sorter=desc-createTime'

      get(queryUrl, '').then(res => {
        announcements.list = res.list
        announcements.totalCount = res.totalCount
        announcements.pageSize = res.pageSize
        announcements.currentPage = res.currentPage
      })
    }

    function handleSizeChange(val) {
      announcements.pageSize = val
      announcements.currentPage = 1
      queryData()
    }

    function handleCurrentChange(val) {
      announcements.currentPage = val
      queryData()
    }

    function goDetail(id) {
      router.push({path: '/shopping/announcementDetail/' + id})
    }

    function formatDate(dateStr) {
      if (!dateStr) return ''
      const date = new Date(dateStr)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      return `${year}-${month}-${day}`
    }

    onMounted(() => {
      queryData()
    })

    return {
      announcements,
      handleSizeChange,
      handleCurrentChange,
      goDetail,
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

.announcement-container {
  padding: 20px;
  background-color: transparent;
  min-height: 80vh;
}

.announcement-header {
  margin-bottom: 30px;

  .page-title {
    color: $text-dark;
    font-size: 28px;
    font-weight: 600;
    margin: 0;
    padding-bottom: 15px;
    border-bottom: 3px solid $primary-green;
    display: inline-block;
  }
}

.announcement-list {
  background: white;
  border-radius: 8px;
  box-shadow: $shadow;
  overflow: hidden;
  margin-bottom: 20px;

  .el-row {
    background: transparent;
  }
}

.announcement-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px 20px;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: all 0.3s ease;

  &:last-child {
    border-bottom: none;
  }

  &:hover {
    background-color: #f9fdf8;
    padding-left: 24px;

    .item-title {
      color: $primary-green;
    }

    .arrow {
      color: $primary-green;
      transform: translateX(4px);
    }
  }
}

.item-left {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.item-title {
  color: $text-dark;
  font-size: 16px;
  font-weight: 500;
  margin: 0;
  transition: color 0.3s ease;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.item-time {
  color: #999;
  font-size: 12px;
}

.item-right {
  flex-shrink: 0;
  margin-left: 20px;
}

.arrow {
  color: #ccc;
  font-size: 18px;
  font-weight: 600;
  transition: all 0.3s ease;
}

.pagination {
  float: right;
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;

  ::v-deep(.btn-prev),
  ::v-deep(.btn-next),
  ::v-deep(.pager li.active),
  ::v-deep(.el-pager li:hover) {
    color: $primary-green;
  }

  ::v-deep(.el-pagination button:hover) {
    color: $primary-green;
  }
}

// 响应式设计
@media (max-width: 768px) {
  .announcement-container {
    padding: 15px;
  }

  .announcement-header {
    margin-bottom: 20px;

    .page-title {
      font-size: 24px;
    }
  }

  .announcement-item {
    padding: 12px 15px;

    &:hover {
      padding-left: 15px;
    }
  }

  .item-title {
    font-size: 14px;
  }

  .item-time {
    font-size: 11px;
  }

  .item-right {
    margin-left: 10px;
  }

  .arrow {
    font-size: 16px;
  }
}
</style>
