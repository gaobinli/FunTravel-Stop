<template>
    <el-container style="height: 100%">
        <el-aside width="auto">
            <Aside />
        </el-aside>
        <el-container>
            <el-header>
                <Header />
            </el-header>
                <Tag />
            <el-main>
                <router-view />
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
import Aside from './Aside'
import Header from './Header'
import Tag from './Tag'


export default ({
    setup() {
        
    },
    components: { Aside, Header, Tag }
})
</script>


<style lang="scss" scoped>
.el-header {
    background: #333;
}

.el-main {
    padding-top: 10;
}
</style>
