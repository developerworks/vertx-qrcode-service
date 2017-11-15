var EventBus = require("vertx3-eventbus-client");

var eb = new EventBus("http://localhost:8080/eventbus");

eb.onopen = function () {
  // 导入代理模块
  var QrcodeService = require("../target/classes/qrcode-service-js/qrcode_service-proxy");
  // 实例化服务对象
  var service = new QrcodeService(eb, "com.totorotec.servicefactory.qrcode-service");
  // 调用服务
  service.getQrcode("https://www.qq.com", 380, "png", "dataurl", "/tmp/%s.%s", function (data, error) {
    if(error == null) {
      console.log(data);
    } else {
      console.log(error);
    }
  });
}


