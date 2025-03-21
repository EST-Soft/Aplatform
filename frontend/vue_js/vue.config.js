const { defineConfig } = require('@vue/cli-service');

module.exports = defineConfig({
  transpileDependencies: true,
  configureWebpack: {
    devtool: 'source-map', // 디버깅을 위한 소스맵 설정
  },
  devServer: {
    hot: true, // 핫 리로딩 활성화
    liveReload: true, // 변경 사항 자동 새로고침
  },
});
