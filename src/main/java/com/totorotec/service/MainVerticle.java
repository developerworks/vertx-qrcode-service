package com.totorotec.service;

import com.totorotec.service.qrcode.QrcodeServiceBridge;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start() {

    // // Only one instance
    DeploymentOptions options = new DeploymentOptions().setInstances(1);
    // String classpath = System.getProperty("java.class.path");
    // System.out.println(classpath);
    String service_qrcode = "service:com.totorotec.service.qrcode-service";
    vertx.deployVerticle(service_qrcode, options, ar -> {
      logger.info("Service verticle deployed: " + service_qrcode);
    });

    // vertx.setTimer(3000, ar -> {
    //   vertx.deployVerticle(QrcodeServiceConsumer.class.getName(), options);
    // });

    // 部署服务桥
    vertx.deployVerticle(QrcodeServiceBridge.class.getName(), ar -> {
      if (ar.succeeded()) {
        logger.info(QrcodeServiceBridge.class.getName() + " deployed.");
      } else {
        logger.error(QrcodeServiceBridge.class.getName() + " failed deployed.");
      }
    });
  }

}
