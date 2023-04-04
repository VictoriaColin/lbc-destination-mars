const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CopyPlugin = require("copy-webpack-plugin");
const { CleanWebpackPlugin } = require('clean-webpack-plugin');

module.exports = {
  optimization: {
    usedExports: true
  },

  entry: {
    examplePage: path.resolve(__dirname, 'src', 'pages', 'examplePage.js'),
    concertAdmin: path.resolve(__dirname, 'src', 'pages', 'flightAdmin.js'),
    ticketPurchase: path.resolve(__dirname, 'src', 'pages', 'ticketPurchase.js'),
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    filename: '[name].js',
  },
  devServer: {
    https: false,
    port: 8080,
    open: true,
    openPage: 'http://localhost:8080',
    // diableHostChecks, otherwise we get an error about headers and the page won't render
    disableHostCheck: true,
    contentBase: 'packaging_additional_published_artifacts',
    // overlay shows a full-screen overlay in the browser when there are compiler errors or warnings
    overlay: true
    proxy: [
          {
            context: [
              '/index',
              '/admin'
            ],
            target: 'http://localhost:5001'
          }
        ]
  },
  plugins: [
      new HtmlWebpackPlugin({
        template: './src/admin.html',
        filename: 'admin.html',
        inject: false
      }),

}
