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
    cancelPage: path.resolve(__dirname, 'src', 'pages', 'cancelPage.js'),
    cancel_confirmationPage: path.resolve(__dirname, 'src', 'pages', 'cancel_confirmationPage.js'),
    checkoutPage: path.resolve(__dirname, 'src', 'pages', 'checkoutPage.js'),
//    create_accountPage: path.resolve(__dirname, 'src', 'pages', 'create_accountPage.js'),
//    customer_dashboardPage: path.resolve(__dirname, 'src', 'pages', 'customer_dashboardPage.js'),
//    customer_signinPage: path.resolve(__dirname, 'src', 'pages', 'customer_signinPage.js'),
    flightsPage: path.resolve(__dirname, 'src', 'pages', 'flightsPage.js'),
    indexPage: path.resolve(__dirname, 'src', 'pages', 'indexPage.js'),
    payment_confPage: path.resolve(__dirname, 'src', 'pages', 'payment_confPage.js'),
    payscreenPage: path.resolve(__dirname, 'src', 'pages', 'payscreenPage.js'),
//    searchPage: path.resolve(__dirname, 'src', 'pages', 'searchPage.js'),
    updatePage: path.resolve(__dirname, 'src', 'pages', 'updatePage.js'),
    update_changesPage: path.resolve(__dirname, 'src', 'pages', 'update_changesPage.js'),
    update_confPage: path.resolve(__dirname, 'src', 'pages', 'update_confPage.js'),
    flightAdmin: path.resolve(__dirname, 'src', 'pages', 'flightAdmin.js'),
    ticketPurchase: path.resolve(__dirname, 'src', 'pages', 'ticketPurchase.js')

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
    overlay: true,
    proxy:[
                  {
                    context: [
                      '/'
                    ],
                    target: 'http://localhost:5001/'
                  }
                ]

  },
  plugins: [

    new HtmlWebpackPlugin({
      template: './src/index.html',
      filename: 'index.html',
      inject: false
    }),
    new HtmlWebpackPlugin({
          template: './src/cancel.html',
          filename: 'cancel.html',
          inject: false
        }),
    new HtmlWebpackPlugin({
              template: './src/cancel_conf.html',
              filename: 'cancel_conf.html',
              inject: false
            }),
    new HtmlWebpackPlugin({
              template: './src/checkout.html',
              filename: 'checkout.html',
              inject: false
            }),
//    new HtmlWebpackPlugin({
//              template: './src/create_account.html',
//              filename: 'create_account.html',
//              inject: false
//            }),
    new HtmlWebpackPlugin({
              template: './src/customer_dashboard.html',
              filename: 'customer_dashboard.html',
              inject: false
            }),
    new HtmlWebpackPlugin({
              template: './src/customer_signin.html',
              filename: 'customer_signin.html',
              inject: false
            }),
    new HtmlWebpackPlugin({
          template: './src/payscreen.html',
          filename: 'payscreen.html',
          inject: false
        }),
    new HtmlWebpackPlugin({
              template: './src/payment_conf.html',
              filename: 'payment_conf.html',
              inject: false
            }),
    new HtmlWebpackPlugin({
              template: './src/update.html',
              filename: 'update.html',
              inject: false
            }),
    new HtmlWebpackPlugin({
                  template: './src/update_conf.html',
                  filename: 'update_conf.html',
                  inject: false
                }),
    new HtmlWebpackPlugin({
              template: './src/flightAdmin.html',
              filename: 'flightAdmin.html',
              inject: false
            }),
    new HtmlWebpackPlugin({
              template: './src/index2.html',
              filename: 'index2.html',
              inject: false
            }),
    new CopyPlugin({
      patterns: [
        {
          from: path.resolve('src/css'),
          to: path.resolve("dist/css")
        }
      ]
    }),
    new CleanWebpackPlugin()
  ]

}
