package com.totorotec.service.qrcode;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;

/**
 * Vert.x-Web 包含内置的SockJS套接字处理器, 被称为事件总线桥, 它有效地把服务器端Vert.x事件总线
 * 扩展到了客户端Javascript
 */
public class QrcodeServiceBridge extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(QrcodeServiceBridge.class);

  @Override
  public void start() throws Exception {
    super.start();

    // 初始化路由器
    Router router = Router.router(vertx);
    // 套接字处理
    SockJSHandler sockJSHandler = SockJSHandler.create(vertx);

    // 桥选项
    //
    BridgeOptions options = new BridgeOptions();
    options.addInboundPermitted(new PermittedOptions().setAddress(QrcodeService.SERVICE_ADDRESS));
    options.addOutboundPermitted(new PermittedOptions().setAddress(QrcodeService.SERVICE_ADDRESS));

    // 桥接 它可以链接js和java
    sockJSHandler.bridge(options);

    // 设置路由
    router.route("/eventbus/*").handler(sockJSHandler);

    // 监听
    vertx.createHttpServer().connectionHandler(conn -> {
      // Track conn.remoteAddress()
      logger.info("Accept connect from " + conn.remoteAddress().toString());
      conn.closeHandler(v -> {
        // Cleanup track of conn.remoteAddress()
        logger.info("Disconnect from " + conn.remoteAddress().toString());
      });
    }).requestHandler(router::accept).listen(8080, "127.0.0.1");
  }
}
