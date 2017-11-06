package com.totorotec.service.qrcode;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import com.totorotec.service.qrcode.impl.QrcodeServiceImpl;

@ProxyGen
@VertxGen
public interface QrcodeService {
  public static final String SERVICE_ADDRESS = "com.totorotec.servicefactory.qrcode-service";

  static QrcodeService create(Vertx vertx, JsonObject config) {
    return new QrcodeServiceImpl(vertx, config);
  }

  static QrcodeService createProxy(Vertx vertx, String address) {
    return new QrcodeServiceVertxEBProxy(vertx, address);
  }

  void getQrcode(String text, int imageSize, String imageType, String outputType, String filePatten, Handler<AsyncResult<JsonObject>> resultHandler);
}
