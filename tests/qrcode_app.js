
var service = require("qrcode-service-js/qrcode_service");

console.log("Creating service proxy...");
var proxy = service.createProxy(vertx, "com.totorotec.servicefactory.qrcode-service");

// proxy.getQrcode("https://gm.totorotec.com", 380, "png", "dataurl",
//   "/Users/hezhiqiang/totoro/_vertx-projects/service_qrcode/_tmp/%s.%s", function (data, error) {
//     console.log(data);
//     console.log(error);
//   });

proxy.getQrcode("https://gm.totorotec.com", 380, "png", "dataurl", "/tmp/%s.%s", function (error, data) {
  if(error == null) {
    console.log(data);
  }
  else {
    console.log(error);
  }
});
