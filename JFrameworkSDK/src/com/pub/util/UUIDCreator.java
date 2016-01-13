package com.pub.util;

import java.net.*;
import java.security.SecureRandom;
import java.util.ResourceBundle;

/**
 * 产生唯一的标记号UUID。
 */
public class UUIDCreator {

  static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.pub.util.Language");
//  private static SecureRandom seedRandom = new SecureRandom();
//  private static String midValue = null;
//  static {
//    try {
//      //IPAddress
//      byte addr[] = InetAddress.getLocalHost().getAddress();
//      StringBuffer buffer = new StringBuffer(8);
//      buffer.append(toHex(toInt(addr), 8));
//      midValue = buffer.toString();
//      //init seedRandom
//      //seedRandom = new SecureRandom();
//      seedRandom.nextInt();
//    }
//    catch (Exception ex) {
//      ex.printStackTrace();
//    }
//  }

  public UUIDCreator() {
  }

  /**
   * 产生一个32位的UUID.
   * @return
   */
  public static String generateUUID() {
    try{
      StringBuffer uid = new StringBuffer(32);
      //1 get the system time
      long currentTimeMillis = System.currentTimeMillis();
      uid.append(toHex( (int) (currentTimeMillis & -1L), 8));

      //2 get the internet address
//      byte addr[] = InetAddress.getLocalHost().getAddress();
//      StringBuffer buffer = new StringBuffer(8);
//      buffer.append(toHex(toInt(addr), 8));
//      uid.append(buffer.toString());
      uid.append(toHex(getRandom(), 8));

      //3 get the object hash value
      //uid.append(toHex(System.identityHashCode(obj), 8));
      uid.append(toHex(getRandom(), 8));

      //4 get the random number
      uid.append(toHex(getRandom(), 8));

      return uid.toString();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
    return null;
  }

  private static String toHex(int value, int length) {
    char hexDigits[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F'
    };
    StringBuffer buffer = new StringBuffer(length);
    int shift = length - 1 << 2;
    for (int i = -1; ++i < length; ) {
      buffer.append(hexDigits[value >> shift & 0xf]);
      value <<= 4;
    }
    return buffer.toString();
  }

  private static int toInt(byte bytes[]) {
    int value = 0;
    for (int i = -1; ++i < bytes.length; ) {
      value <<= 8;
      value |= bytes[i];
    }
    return value;
  }

  private static synchronized int getRandom() {
    SecureRandom seedRandom = new SecureRandom();
    return seedRandom.nextInt();
  }

  public static void main(String[] args) {
    try{
      System.out.println(generateUUID());
      System.out.println(generateUUID());
      System.out.println(generateUUID());
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
  }
}
