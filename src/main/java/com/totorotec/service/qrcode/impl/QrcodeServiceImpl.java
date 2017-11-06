package com.totorotec.service.qrcode.impl;

import com.totorotec.service.qrcode.QrcodeService;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class QrcodeServiceImpl implements QrcodeService {

  private static final Logger logger = LoggerFactory.getLogger(QrcodeServiceImpl.class);
  private JsonObject config;

  public QrcodeServiceImpl(Vertx vertx, JsonObject config) {
    this.config = config;
  }

  @Override
  public void getQrcode(String text, Handler<AsyncResult<JsonObject>> resultHandler) {

    String imageType = config.getString("imageType");
    Integer imageSize = config.getInteger("imageSize");

    Qrcode qrcode = new Qrcode(text, imageSize, imageType);

    try {
      String resp = qrcode.getQrcode();
      JsonObject result = new JsonObject().put("dataurl", resp);
      logger.debug("Generate qrcode for text: " + text);
      resultHandler.handle(Future.succeededFuture(result));
    } catch (Exception e) {
      logger.error(e);
    }


  }

}
