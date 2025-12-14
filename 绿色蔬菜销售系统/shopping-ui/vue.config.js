// vue.config.js 配置说明
//官方vue.config.js 参考文档 https://cli.vuejs.org/zh/config/#css-loaderoptions
// 这里只列一部分，具体配置参考文档
module.exports = {
  publicPath: "/", // 公共路径(必须有的)
  outputDir: "dist", // 输出文件目录
  assetsDir: "static", //静态资源文件名称
  lintOnSave: false,
  productionSourceMap: false, //去除打包后js的map文件
  chainWebpack: config => {
    const svgRule = config.module.rule("svg");
    svgRule.uses.clear();
    svgRule
      .use("svg-sprite-loader")
      .loader("svg-sprite-loader")
      .options({
        symbolId: "icon-[name]"
      });
  }
}