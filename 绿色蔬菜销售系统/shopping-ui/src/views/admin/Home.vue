<template>
    <h2 style="text-align: center">成交单量统计</h2>
    <div id="myEcharts2" :style="{ width: '100%', height: '300px' }"></div>

    <h2 style="text-align: center">销售额统计</h2>
    <div id="myEcharts" :style="{ width: '100%', height: '300px' }"></div>
</template>

<script>
import * as echarts from 'echarts';
import { onMounted, onUnmounted, computed, ref, reactive } from 'vue'
import { get,post,del,patch } from '@/api/request'

export default({
    setup() {
        const userImg = require("@/assets/images/login-background.jpg")

        let chartData = reactive({
            orderNumber: []
        })

        /// 声明定义一下echart
        let echart = echarts;

        onMounted(() => {
            

            initChart();
            initChart2();

            
        });

        onUnmounted(() => {
            echart.dispose;
        });
        
        // 基础配置一下Echarts
        function initChart() {
            get('/report/orderSale', '').then(res => {
                let chart = echart.init(document.getElementById("myEcharts"));
                // 把配置和数据放这里
                chart.setOption({
                    xAxis: {
                    type: "category",
                    data: [
                        "一月",
                        "二月",
                        "三月",
                        "四月",
                        "五月",
                        "六月",
                        "七月",
                        "八月",
                        "九月",
                        "十月",
                        "十一月",
                        "十二月"
                    ]
                    },
                    tooltip: {
                    trigger: "axis"
                    },
                    yAxis: {
                    type: "value"
                    },
                    series: [
                    {
                        data: res,
                        type: "line",
                    }
                    ]
                });
                window.onresize = function() {
                    //自适应大小
                    chart.resize();
                };
            })

            
        }

        // 基础配置一下Echarts
        function initChart2() {
            
            get('/report/orderNumber', '').then(res => {
                let chart = echart.init(document.getElementById("myEcharts2"));
                // 把配置和数据放这里
                chart.setOption({
                    xAxis: {
                        type: "category",
                        data: [
                            "一月",
                            "二月",
                            "三月",
                            "四月",
                            "五月",
                            "六月",
                            "七月",
                            "八月",
                            "九月",
                            "十月",
                            "十一月",
                            "十二月"
                        ]
                    },
                    tooltip: {
                        trigger: "axis"
                    },
                    yAxis: {
                        type: "value"
                    },
                    series: [
                    {
                        data: res,
                        type: "bar",
                    }
                    ]
                });
                window.onresize = function() {
                    //自适应大小
                    chart.resize();
                };
            })
        }

        return {
            userImg,
            initChart,
            initChart2
        }
    },
})
</script>