<template>
    <div class="tabs">
        <el-tag 
            v-for="(tag, index) in tags" 
            :key="tag.name" 
            :closable="tag.name !== 'Home'"
            :effect="router.currentRoute.value.name === tag.code ? 'dark' : 'plain'"    
            @click="changeMenu(tag)"
            @close="handleClose(tag, index)"
        >
            {{tag.name}}
        </el-tag>
    </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default({
    setup() {
        const store = useStore()
        const router = useRouter()

        let tags = computed(() => {
            return store.state.tab.tabList
        })

        function changeMenu(item) {
            router.push({ path: item.path })
            store.commit('selectMenu', item)
        }

        function handleClose(tag, index) {
            let length = tags.value.length - 1;

            store.commit('closeTag', tag)

            if(tag.name !== router.currentRoute.value.name) {
                return;
            }
            
            if(index === length) {
                // 往左边跳转
                router.push({name: tags.value[index - 1].name})
            } else {
                // 往右边跳转
                router.push({name: tags.value[index].name})
            }
        }

        return {
            tags,
            router,
            changeMenu,
            handleClose
        }
    },
})
</script>

<style lang="scss" scoped>
.tabs {
    padding: 10px;
    .el-tag {
        margin-left: 15px;
        cursor: pointer;
    }
}
</style>