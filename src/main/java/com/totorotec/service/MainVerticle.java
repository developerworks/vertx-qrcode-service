package com.totorotec.service;

import com.totorotec.service.qrcode.QrcodeServiceConsumer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {

    // // Only one instance
    DeploymentOptions options = new DeploymentOptions().setInstances(1);
    // String classpath = System.getProperty("java.class.path");
    // System.out.println(classpath);

    vertx.deployVerticle("service:com.totorotec.service.qrcode-service", options);

    vertx.setTimer(3000, ar -> {
      vertx.deployVerticle(QrcodeServiceConsumer.class.getName(), options);
    });
  }

}
