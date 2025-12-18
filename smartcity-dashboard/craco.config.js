const webpack = require('webpack');

module.exports = {
    resolve: {
        fallback: {
            "stream": require.resolve("stream-browserify"),
            "buffer": require.resolve("buffer/"),
            "timers": require.resolve("timers-browserify")
        }
    },
    webpack: {
        configure: (webpackConfig) => {
            webpackConfig.resolve.fallback = {
                buffer: require.resolve('buffer/'),
                timers: require.resolve('timers-browserify')
            };
            webpackConfig.plugins.push(
                new webpack.ProvidePlugin({
                    Buffer: ['buffer', 'Buffer']
                })
            );
            return webpackConfig;
        }
    }
};
