package jformservice.jdal.classes.DALFormService;

import java.io.File;
import com.eai.toolkit.xml.XmlEngine;
import jframework.MtoDB.BofJfc.DBO.CommonDBObject;
import java.io.InputStream;
import jfoundation.dataobject.classes.JParamObject;
/**
 * <p>DBOFormStorage</p>
 * <p>Form存储管理器</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DBOFormStorage {
  static public String mFilePath = "";
  static public String mFileSeprator = "/";

  static public String mDefAppName = "JDataCenter";
  static public String mFormDir = "XML/FORMS";
  static public String mDSDir = "XML/DS";

  public DBOFormStorage() {
  }

  public static String LoadFormDefine(String FFormName, String Type,JParamObject ENV,String strAppName) {
    if ( strAppName == null || strAppName.equals("")){
      strAppName = mDefAppName;
    }

    StringBuffer FName = new StringBuffer(mFileSeprator);  //"/"
    FName.append(strAppName);                              //"JdataCenter"
    FName.append(mFileSeprator);                           //"/"
    FName.append(mFormDir);                                //"XML/FORMS"
    FName.append(mFileSeprator);                           //"/"
    FName.append(Type);                                    //"virtual"
    FName.append(mFileSeprator);                           //"/"
    FName.append(FFormName);                               //"zgzd"
    FName.append(".xml");                                  //".xml"

    InputStream IStream = null;
    try {
      /**
       * IStream原来没有关的地方。
       */
      IStream = CommonDBObject.createResourceFileInputStream(FName.toString(), ENV);
      XmlEngine XML = new XmlEngine(IStream);

      if (XML.isEmpty()) {
        System.out.print("EAI:Load form define empty.");
        return "";
      }
      else {
        return XML.GetXmlString();
      }

    }
    catch (Exception E) {
      System.out.print("EAI:Load form define error.");
      E.printStackTrace();
      return "";
    }
    finally{
      if ( IStream != null ){
        try{
          IStream.close();
        }catch(Exception E){
          E.printStackTrace();
        }
      }
    }


  }

  public static String LoadDSDefine(String FFormName, String Type,JParamObject ENV,String strAppName) {
    if ( strAppName == null || strAppName.equals("")){
      strAppName = mDefAppName;
    }

    StringBuffer FName = new StringBuffer(mFileSeprator);  //"/"
    FName.append(strAppName);                              //"JdataCenter"
    FName.append(mFileSeprator);                           //"/"
    FName.append(mDSDir);                                  //"XML/FORMS"
    FName.append(mFileSeprator);                           //"/"
    FName.append(Type);                                    //"virtual"
    FName.append(mFileSeprator);                           //"/"
    FName.append(FFormName);                               //"zgzd"
    FName.append(".xml");                                  //".xml"

    InputStream IStream = null;
    try {
      /**
       * IStream原来没有关的地方。
       */
      IStream = CommonDBObject.createResourceFileInputStream(FName.toString(), ENV);
      XmlEngine XML = new XmlEngine(IStream);

      if (XML.isEmpty()) {
        System.out.print("EAI:Load ds define empty.");

        return "";
      }
      else {
        return XML.GetXmlString();
      }
    }
    catch (Exception E) {
      E.printStackTrace();
      System.out.print("EAI:Load ds define error.");
      return "";
    }
    finally{
      if ( IStream == null ){
        try{
          IStream.close();
        }catch(Exception E){
          E.printStackTrace();
        }
      }
    }

  }



}
