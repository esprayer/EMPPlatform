package com.efounder.form.client.component.pic;

import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.ObjectInputStream;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JFileByteConvertFunction {
  public JFileByteConvertFunction() {
  }

  /**
   * ֵ
   * @param f File
   * @return byte[]
   */
  public static byte[] getBytesFromFile(File f) throws Exception{
    if (f == null) {
      return null;
    }
    try {
      FileInputStream stream = new FileInputStream(f);
      ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
      byte[] b = new byte[1000];
      int n;
      while ( (n = stream.read(b)) != -1) {
        out.write(b, 0, n);
      }
      stream.close();
      out.close();
      return out.toByteArray();
    }
    catch (Exception e) {
      throw e;
    }

  }

  public static File getFileFromBytes(byte[] b, String outputFile) throws Exception {
    BufferedOutputStream stream = null;
    File file = null;
    try {
      file = new File(outputFile);
      FileOutputStream fstream = new FileOutputStream(file);
      stream = new BufferedOutputStream(fstream);
      stream.write(b);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    finally {
      if (stream != null) {
        try {
          stream.close();
        }
        catch (Exception e1) {
          e1.printStackTrace();
        }

      }
      return file;
    }
  }

    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
      if (objBytes == null || objBytes.length == 0) {
        return null;
      }
      ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
      ObjectInputStream i = new ObjectInputStream(bi);

      return i.readObject();
    }

    /**
     *
     */
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {
      if (obj == null) {
        return null;
      }
      ByteArrayOutputStream bo = new ByteArrayOutputStream();
      ObjectOutputStream o = new ObjectOutputStream(bo);
      o.writeObject(obj);
      return bo.toByteArray();
    }

  }
