package com.efounder.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import java.io.*;

public class IOToolkit {

  public IOToolkit() {
  }

  /**
   * 从一个文件中读出串
   * @param FileName
   * @return
   */
  public String ReadFileToString(String FileName){
    try{
      FileInputStream FS = new FileInputStream(FileName);
      int   size = FS.available();

      byte[] pp = new byte[size];

      FS.read( pp,0,size);

      String ss = new String(pp);

      FS.close();

      return ss;
    }
    catch (Exception e){
      e.printStackTrace();
      return "";
    }

  }

  /**
   * 把串写入文件
   * @param DataToWrite
   * @param FileName
   * @return
   */
  public boolean WriteStringToFile(String DataToWrite,String FileName){
     try{
       FileOutputStream FS = new FileOutputStream(FileName);
       byte[] pp = DataToWrite.getBytes();

       FS.write( pp );

       FS.close();

       return true;
     }
     catch (Exception e){
       e.printStackTrace();
       return false;
     }

  }

  /**
   * 清掉串中的所有空格
   * @param Data
   * @return
   */
  public String ClearSpace(String Data){
    String Re = "";
    String[] Words = Data.split(" ");

    for ( int i = 0; i < Words.length; i ++){
      Re += Words[i];
    }
    return Re;
  }
  public static void main(String[] args) {
    IOToolkit IOToolkit1 = new IOToolkit();
  }
}
