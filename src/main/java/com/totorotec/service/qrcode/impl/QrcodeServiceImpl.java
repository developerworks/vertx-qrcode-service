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
  public void getQrcode(String text, int imageSize, String imageType, String outputType, String filePatten, Handler<AsyncResult<JsonObject>> resultHandler) {
    logger.info("qrcode text: " + text);
    logger.info("qrcode image size: " + imageSize);
    logger.info("qrcode image type: " + imageType);
    logger.info("qrcode output type: " + outputType);
    logger.info("qrcode file pattern: " + filePatten);

    boolean deleteTempFile = config.getBoolean("deleteTempFile");

    Qrcode qrcode = new Qrcode(text, imageSize, imageType, outputType, deleteTempFile, filePatten);

    try {
      String resp = qrcode.getQrcode();
      JsonObject result = new JsonObject().put("data", resp);
      logger.debug("Generate qrcode for text: " + text);
      resultHandler.handle(Future.succeededFuture(result));
    } catch (Exception e) {
      logger.error(e);
    }

  }

}
