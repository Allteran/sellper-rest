const { merge } = require('webpack-merge');
const common = require('./webpack.common.js');
const { CleanWebpackPlugin } = require('clean-webpack-plugin')
const path = require('path');


module.exports = merge(common, {
    mode: 'production',
    plugins: [
        new CleanWebpackPlugin(),
    ],
    output: {
        filename: 'main.js',
        path: path.resolve(__dirname, 'src', 'main', 'resources', 'static', 'js')
    }
});