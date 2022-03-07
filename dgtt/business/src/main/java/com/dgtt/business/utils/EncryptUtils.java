package com.dgtt.business.utils;

import java.util.Base64;

public class EncryptUtils {

  private EncryptUtils() {}

  public static String encryptBase64(String strToEncrypt) {
    return Base64.getEncoder().encodeToString(strToEncrypt.getBytes());
  }

  public static String decryptBase64(String strToDecrypt) {
    return new String(Base64.getDecoder().decode(strToDecrypt));
  }
}
