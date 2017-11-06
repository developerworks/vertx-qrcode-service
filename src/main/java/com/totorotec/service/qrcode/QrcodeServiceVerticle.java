package com.totorotec.service.qrcode;

import com.totorotec.service.qrcode.impl.QrcodeServiceImpl;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.serviceproxy.ServiceBinder;

/**
 * DatabaseServiceVerticle
 */
public class QrcodeServiceVerticle extends AbstractVerticle {
  /**
   * 定义服务所在的事件总线地址
   */
  private static final Logger logger = LoggerFactory.getLogger(QrcodeServiceVerticle.class);

  @Override
  public void start() throws Exception {
    super.start();
    JsonObject config = config();
    QrcodeService qrcodeService = new QrcodeServiceImpl(vertx, config);
    new ServiceBinder(vertx).setAddress(QrcodeService.SERVICE_ADDRESS).register(QrcodeService.class, qrcodeService);
    logger.info("Register service: " + QrcodeService.SERVICE_ADDRESS + ".");
  }
}
