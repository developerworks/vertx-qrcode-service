package com.totorotec.service.qrcode.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

class Qrcode {
  private static final Logger logger = LoggerFactory.getLogger(Qrcode.class);
  private String text;
  private String outputType = "file";
  private String imageType = "png";
  private int size = 600;
  private String filePath = "/tmp/%s.%s";
  private File imageFile = null;
  private boolean deleteTempFile = true;

  public Qrcode(String text, int size, String imageType, String output_type, boolean deleteTempFile) {
    this.text = text;
    this.size = size;
    this.imageType = imageType;
    this.filePath = String.format(this.filePath, UUID.randomUUID().toString(), imageType);
    this.outputType = output_type;
    this.deleteTempFile = deleteTempFile;
  }

  public Qrcode(String text, int size, String imageType, String output_type, boolean deleteTempFile, String filePath) {
    this.text = text;
    this.size = size;
    this.imageType = imageType;
    this.filePath = String.format(filePath, UUID.randomUUID().toString(), imageType);
    this.outputType = output_type;
    this.imageFile = new File(this.filePath);
    this.deleteTempFile = deleteTempFile;
  }

  public String getQrcode() throws Exception {

    Map<EncodeHintType, Object> hintMap = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
    hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    hintMap.put(EncodeHintType.MARGIN, 1);
    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

    QRCodeWriter qrCodeWriter = new QRCodeWriter();

    BitMatrix byteMatrix = qrCodeWriter.encode(this.text, BarcodeFormat.QR_CODE, this.size, this.size, hintMap);
    int matrixWidth = byteMatrix.getWidth();
    BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
    image.createGraphics();
    Graphics2D graphics = (Graphics2D) image.getGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, matrixWidth, matrixWidth);
    graphics.setColor(Color.BLACK);
    for (int i = 0; i < matrixWidth; i++) {
      for (int j = 0; j < matrixWidth; j++) {
        if (byteMatrix.get(i, j)) {
          graphics.fillRect(i, j, 1, 1);
        }
      }
    }
    if (this.outputType.equals("dataurl")) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(image, imageType, baos);
      String base64 = DatatypeConverter.printBase64Binary(baos.toByteArray());
      String dataUrl = String.format("data:image/%s;base64,%s", imageType, base64);
      String msg = String.format("Qrcode image file is encoded to base 64 data url, image type: %s", imageType);
      logger.info(msg);
      return dataUrl;
    } else if (this.outputType.equals("file")) {
      try {
        ImageIO.write(image, imageType, imageFile);
        logger.info(String.format("Qrcode image file is written to %s", filePath));
        if (deleteTempFile) {
          imageFile.delete();
          logger.info(String.format("Image file %s has been deleted.", imageFile.getPath()));
        }
      } catch (SecurityException e) {
        String msg = String.format("Can not delete file: %s", imageFile.getPath());
        logger.error(msg, e);
      }
      return imageFile.getPath();
    } else {
      logger.warn("Output type is not supported.");
      return "";
    }
  }

  /**
   * @return the outputType
   */
  public String getOutputType() {
    return outputType;
  }

  /**
   * @param outputType the outputType to set
   */
  public void setOutputType(String outputType) {
    this.outputType = outputType;
  }

  /**
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * @param text the text to set
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * @return the imageType
   */
  public String getImageType() {
    return imageType;
  }

  /**
   * @param imageType the imageType to set
   */
  public void setImageType(String imageType) {
    this.imageType = imageType;
  }

  /**
   * @return the imageFile
   */
  public File getImageFile() {
    return imageFile;
  }

  /**
   * @param imageFile the imageFile to set
   */
  public void setImageFile(File imageFile) {
    this.imageFile = imageFile;
  }

}
