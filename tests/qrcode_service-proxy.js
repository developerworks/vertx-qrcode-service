/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

/** @module qrcode-service-js/qrcode_service */
!function (factory) {
  if (typeof require === 'function' && typeof module !== 'undefined') {
    factory();
  } else if (typeof define === 'function' && define.amd) {
    // AMD loader
    define('qrcode-service-js/qrcode_service-proxy', [], factory);
  } else {
    // plain old include
    QrcodeService = factory();
  }
}(function () {

  /**
 @class
  */
  var QrcodeService = function(eb, address) {

    var j_eb = eb;
    var j_address = address;
    var closed = false;
    var that = this;
    var convCharCollection = function(coll) {
      var ret = [];
      for (var i = 0;i < coll.length;i++) {
        ret.push(String.fromCharCode(coll[i]));
      }
      return ret;
    };

    /**

     @public
     @param text {string} 
     @param imageSize {number} 
     @param imageType {string} 
     @param outputType {string} 
     @param filePatten {string} 
     @param resultHandler {function} 
     */
    this.getQrcode = function(text, imageSize, imageType, outputType, filePatten, resultHandler) {
      var __args = arguments;
      if (__args.length === 6 && typeof __args[0] === 'string' && typeof __args[1] ==='number' && typeof __args[2] === 'string' && typeof __args[3] === 'string' && typeof __args[4] === 'string' && typeof __args[5] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"text":__args[0], "imageSize":__args[1], "imageType":__args[2], "outputType":__args[3], "filePatten":__args[4]}, {"action":"getQrcode"}, function(err, result) { __args[5](err, result &&result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

  };

  /**

   @memberof module:qrcode-service-js/qrcode_service
   @param vertx {Vertx} 
   @param config {Object} 
   @return {QrcodeService}
   */
  QrcodeService.create = function(vertx, config) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && (typeof __args[1] === 'object' && __args[1] != null)) {
      if (closed) {
        throw new Error('Proxy is closed');
      }
      j_eb.send(j_address, {"vertx":__args[0], "config":__args[1]}, {"action":"create"});
      return;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  /**

   @memberof module:qrcode-service-js/qrcode_service
   @param vertx {Vertx} 
   @param address {string} 
   @return {QrcodeService}
   */
  QrcodeService.createProxy = function(vertx, address) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
      if (closed) {
        throw new Error('Proxy is closed');
      }
      j_eb.send(j_address, {"vertx":__args[0], "address":__args[1]}, {"action":"createProxy"});
      return;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  if (typeof exports !== 'undefined') {
    if (typeof module !== 'undefined' && module.exports) {
      exports = module.exports = QrcodeService;
    } else {
      exports.QrcodeService = QrcodeService;
    }
  } else {
    return QrcodeService;
  }
});