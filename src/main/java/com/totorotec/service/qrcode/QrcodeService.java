package com.totorotec.service.qrcode;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import com.totorotec.service.qrcode.impl.QrcodeServiceImpl;

/**
 * @ProxyGen
 *
 * 有了这个接口，Vert.x会生成所有需要的用于在Event Bus上访问你的服务的模板代
 * 码，同时也会生成对应的 调用端代理类（client side proxy），这样你的服务调用
 * 端就可以使用一个相当符合习惯的API（译者注：即相同的服务接口）进行服务调用，
 * 而不是去手动地向Event Bus发送消息。不管你的服务实际在哪个Event Bus上
 * （可能是在不同的机器上），调用端代理类都能正常工作。
 *
 * @VertxGen
 * 可以将多语言API生成功能（ @VertxGen  注解）与  @ProxyGen  注解相结
 * 合，用于生成其它Vert.x支持的JVM语言对应的服务代理 —— 这意味着你可以只用
 * Java编写你的服务一次，就可以在其他语言中以一种习惯的API风格进行服务调
 * 用，而不必管服务是在本地还是在Event Bus的别处。想要利用多语言代码生成功
 * 能，不要忘记添加对应支持语言的依赖
 */
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
