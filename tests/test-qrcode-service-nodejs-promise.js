/**
 * async/await 封装
 *
 * 可以使用 babel-node 调用
 *
 * 1. 安装 babel-cli
 *    yarn add --dev babel-cli
 * 2. 运行
 *    ./node_modules/.bin/babel-node tests/test-qrcode-service.js
 */
function getQrcode(service, text, imageSize, imageType, outputType, filePatten) {
  return new Promise((resolve, reject) => {
    service.getQrcode(text, imageSize, imageType, outputType, filePatten, function (error, data) {
      if(error == null) {
        resolve(data)
      } else {
        reject(error)
      }
    })
  })
}


var EventBus = require("vertx3-eventbus-client")

var options = {
  vertxbus_reconnect_attempts_max: Infinity,  // Max reconnect attempts
  vertxbus_reconnect_delay_min: 1000,         // Initial delay (in ms) before first reconnect attempt
  vertxbus_reconnect_delay_max: 5000,         // Max delay (in ms) between reconnect attempts
  vertxbus_reconnect_exponent: 2,             // Exponential backoff factor
  vertxbus_randomization_factor: 0.5          // Randomization factor between 0 and 1
};

var eb = new EventBus("http://localhost:8080/eventbus", options);
eb.enableReconnect(true)


eb.onopen = async function () {
  // 导入代理模块
  var QrcodeService = require("../target/classes/qrcode-service-js/qrcode_service-proxy")
  // 实例化服务对象
  var service = new QrcodeService(eb, "com.totorotec.servicefactory.qrcode-service")

  // 调用服务: 异步回调
  // service.getQrcode("https://www.qq.com", 380, "png", "dataurl", "/tmp/%s.%s", function (data, error) {
  //   if(error == null) {
  //     console.log(data)
  //   } else {
  //     console.log(error)
  //   }
  // })

  // 调用服务: async/await 语法
  var qrcode = await getQrcode(service, "https://www.qq.com", 380, "png", "dataurl", "/tmp/%s.%s")
  console.log(qrcode)
  // Close event bus when result returned.
  eb.close()
}


eb.onerror = function() {
  logger.error("Eventbus: error ocurred.")
}
eb.onclose = function() {
  logger.error("Eventbus: closed.")
}
eb.onreconnect = function() {
  logger.info("Eventbus: reconnecting...")
}
