package com.efounder.pub.util;

import java.io.*;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FileUtil {
  /**
   *
   */
  protected FileUtil() {
  }
  /**
   *
   * @param file File
   * @return byte[]
   * @throws Exception
   */
  public static byte[] readFile(File file) throws Exception {
    FileInputStream fis = new FileInputStream(file);
    byte[] datas = new byte[fis.available()];
    fis.read(datas);
    fis.close();
    return datas;
  }
  /**
   *
   * @param file File
   * @param datas byte[]
   * @throws Exception
   */
  public static void writeFile(File file,byte[] datas) throws Exception {
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(datas);
    fos.close();
  }
  /**
   *
   * @param txtFile File
   * @return String
   * @throws Exception
   */
  public static String readTextFromFile(File txtFile,String encoding) throws Exception {
    FileInputStream fis = new FileInputStream(txtFile);
    int leng = fis.available();
    byte[] datas = new byte[leng];
    fis.read(datas);
    String s = new String(datas,encoding);
    return s;
//    BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
//    StringBuffer buffer = new StringBuffer();
//    String line = reader.readLine();       // ��ȡ��һ��
//    while (line != null) {          // ��� line Ϊ��˵�������
//      buffer.append(line);        // ���u���������ӵ� buffer ��
//      buffer.append("\n");        // ��ӻ��з�
//      line = reader.readLine();   // ��ȡ��һ��
//    }
//    fis.close();
//    return buffer.toString();
  }
  public static String readTextFromFile(File txtFile) throws Exception {
    return readTextFromFile(txtFile,"utf8");
  }
  /**
   *
   * @param objFile File
   * @return Object
   * @throws Exception
   */
  public static Object readObjFromFile(File objFile) throws Exception {
    FileInputStream   fis = new FileInputStream(objFile);
    ObjectInputStream ois = new ObjectInputStream(fis);
    return ois.readObject();
  }
  /**
   *
   * @param stubObjects Object
   * @param objFile File
   * @throws Exception
   */
  public static void writeObj2File(Object stubObjects,File objFile) throws Exception {
    FileOutputStream   fos = new FileOutputStream(objFile);
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(stubObjects);
    oos.flush();fos.flush();fos.close();
  }
  /**
   *
   * @param obj Object
   * @param xmlFile File
   * @throws Exception
   */
  public static void writeObj2XMLFile(Object obj,File xmlFile) throws Exception {
    FileOutputStream fileOutputStream = new FileOutputStream(xmlFile);
    XMLEncoder xmlEncoder = new XMLEncoder(fileOutputStream);
    xmlEncoder.writeObject(obj);
    xmlEncoder.close();
    fileOutputStream.close();
  }
  /**
   *
   * @param xmlFile File
   * @return Object
   * @throws Exception
   */
  public static Object readObjFromXMLFile(File xmlFile) throws Exception {
    return readObjFromXMLFile(xmlFile,FileUtil.class.getClassLoader());
  }
  /**
   *
   * @param xmlFile File
   * @param classLoader ClassLoader
   * @return Object
   * @throws Exception
   */
  public static Object readObjFromXMLFile(File xmlFile,ClassLoader classLoader) throws Exception {
    FileInputStream fileInputStream = new FileInputStream(xmlFile);
    XMLDecoder encoder = new XMLDecoder(fileInputStream, null, null,classLoader);
    return encoder.readObject();
  }
}
