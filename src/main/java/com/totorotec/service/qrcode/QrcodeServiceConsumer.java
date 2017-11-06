package com.totorotec.service.qrcode;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * QrcodeServiceConsumer
 */
public class QrcodeServiceConsumer extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(QrcodeServiceConsumer.class);

  @Override
  public void start() throws Exception {
    super.start();

    QrcodeService qrcodeServiceProxy = QrcodeService.createProxy(vertx, QrcodeService.SERVICE_ADDRESS);
    qrcodeServiceProxy.getQrcode("https://gm.totorotec.com", 600, "jpg", "file",
        "/Users/hezhiqiang/totoro/_vertx-projects/service_qrcode/_tmp/%s.%s", ar -> {
          if (ar.succeeded()) {
            logger.info(ar.result().encodePrettily());
          } else {
            logger.error(ar.cause());
          }
        });
  }
}
