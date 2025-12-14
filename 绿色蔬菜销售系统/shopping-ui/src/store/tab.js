export default {
    state: {
        isCollapse: false,
        currentMenu: null,
        tabList: [
            {
                path: '/admin/home',
                code: 'Home',
                name: '首页',
                icon: 'home'
            }
        ],
        carNumber: JSON.parse(localStorage.getItem('shoppingcar')) == null ? 0 : JSON.parse(localStorage.getItem('shoppingcar')).length
    },
    mutations: {
        collapseMenu(state) {
            state.isCollapse = !state.isCollapse
        },
        selectMenu(state, val) {
            if(val.name == 'home') {
                state.currentMenu = null
            } else {
                state.currentMenu = val
                let result = state.tabList.findIndex(item => item.name == val.name)
                result === -1 ? state.tabList.push(val) : ''
            }
        },
        clearTabList(state) {
            state.tabList = [
                {
                    path: '/admin/home',
                    name: 'Home',
                    label: '首页',
                    icon: 'home'
                }
            ]
        },
        closeTag (state, val) {
            let result = state.tabList.findIndex(item => item.name === val.name)
            state.tabList.splice(result, 1)
        },
        addCarNumber(state) {
            state.carNumber++
        },
        decrCarNumber(state) {
            state.carNumber--
        }
    }
}