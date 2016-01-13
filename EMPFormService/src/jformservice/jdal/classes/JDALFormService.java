package jformservice.jdal.classes;


import com.eai.frame.fcl.interfaces.message.*;
import jdal.foundation.classes.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jfoundation.sql.classes.*;
import jframework.foundation.classes.*;
import jformservice.jdal.classes.DALFormService.*;
import java.io.InputStream;
import jframework.MtoDB.BofJfc.DBO.CommonDBObject;
import com.eai.toolkit.xml.XmlEngine;
import com.eai.tools.translate.*;
import java.net.*;
import com.eai.frame.fcl.classes.message.Message;
/**
 * <p>JDALFormService</p>
 * <p>服务端的数据窗服务对象。</p>
 * <p>Copyright (c) 2003</p>
 * <p>Pansoft.</p>
 * @author Fndlvr
 * @version 1.0
 */
public class JDALFormService extends JAbstractDataActiveObject {
  private boolean mReqTranslate = false;

  public JDALFormService() {
//    URL pUrl = getClass().getResource("/_FormServiceCfg.xml");
//    if ( pUrl!=null){
//      try{
//        XmlEngine pXml = new XmlEngine(pUrl.openStream());
//        pXml.OpenRootSection("FormService");
//        String strReq = pXml.getKeyValue("ReqTranslate","0");
//        mReqTranslate = strReq.equals("1");
//      }
//      catch(Exception E){
//        mReqTranslate = false;
//      }
//
//    }

    TCoding.REQ_TRANSLATE = mReqTranslate;

  }
  /**
    * 执行一个SQL。
    * @param SqlStr String
    * @return int
    */
   public      Object FormQuery(Object Param,Object Data,Object CustomObject,Object AdditiveObject){
     //Param:参数,环境变量串的XML
     //Data :对象，包含命令的参数。IExchangeMessage
     IExchangeMessage  IE = null;
     IMessage         msgInput = null;
     if ( Data != null ){
       IE = (IExchangeMessage)Data;
       msgInput = (IMessage)IE.getInputMessage().asObjectValue("msgInput");
     }
     else
       return null;

     JConnection conn = (JConnection) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("DBManagerObject", "GetDBConnection", Param);
     JResponseObject RO = null;
     try {
       DBOFormService.SQLQuery(conn,msgInput.asStringValue("SqlText",""),IE.getResultMessage());
       RO = new JResponseObject(IE);
     }
     catch (Exception E) {

     }
     finally {
       conn.close();
     }
     return RO;

   }

 /**
 * 找开一个服务FORM,该FORM非实例型
 * 返回以下几个数据值
 * SQLRESULT : 首个数据集IDataExchange
 * DSDEFINE  : 数据集的定义格式XML文本
 * FORMDEFINE: 视的定义格式文本
 * 设计: Findlover(2003.8.13)
 * 实现: Findlover
 * 修改: Findlover
 **/
  public Object OpenVirtual(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    //Param:参数,环境变量串的XML
    //Data :对象，包含命令的参数。IExchangeMessage
    IExchangeMessage  IE = null;
    if ( Data != null ){
      IE = (IExchangeMessage)Data;
    }
    else
      return null;


    JConnection conn = (JConnection) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("DBManagerObject", "GetDBConnection", Param);
    JResponseObject RO = null;
    try {
      DBOFormService.OpenVirtual(conn, (JParamObject) Param,IE.getInputMessage().asStringValue("Name",""),(IMessage)(IE.getInputMessage().asObjectValue("msgInput")), IE.getResultMessage(),null);
      RO = new JResponseObject(IE);
    }
    catch (Exception E) {
      E.printStackTrace();
    }
    finally {
      conn.close();
    }
    return RO;
  }

  /**
  * 找开一个服务FORM,该FORM非实例型
  * 返回以下几个数据值
  * SQLRESULT : 首个数据集IDataExchange
  * DSDEFINE  : 数据集的定义格式XML文本
  * FORMDEFINE: 视的定义格式文本
  * 设计: Findlover(2003.8.13)
  * 实现: Findlover
  * 修改: Findlover
  **/
   public Object OpenVirtualEx(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
     //Param:参数,环境变量串的XML
     //Data :对象，包含命令的参数。IExchangeMessage
     IExchangeMessage  IE = null;
     if ( Data != null ){
       IE = (IExchangeMessage)Data;
     }
     else
       return null;


     JConnection conn = (JConnection) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("DBManagerObject", "GetDBConnection", Param);
     JResponseObject RO = null;
     try {
       DBOFormService.OpenVirtualEx(conn, (JParamObject) Param,IE.getInputMessage().asStringValue("Name",""),(IMessage)(IE.getInputMessage().asObjectValue("msgInput")), IE.getResultMessage(),null);
       RO = new JResponseObject(IE);
     }
     catch (Exception E) {

     }
     finally {
       conn.close();
     }
     return RO;
   }

  /**
  * 提交修改的数据
  * 设计: Findlover(2003.8.13)
  * 实现: Findlover
  * 修改: Findlover
  **/
   public Object UpdateEdit(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
     //Param:参数,环境变量串的XML
     //Data :对象，包含命令的参数。IExchangeMessage
     IExchangeMessage  IE = null;
     if ( Data != null ){
       IE = (IExchangeMessage)Data;
     }
     else
       return null;


     JConnection     conn = null;
     JResponseObject RO   = null;
     try {
       //申请数据库连接
       conn = (JConnection) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("DBManagerObject","GetDBConnection", Param);

       //交给实体类处理
       DBOFormService.UpdateEdit(conn, (IMessage)(IE.getInputMessage().asObjectValue("msgInput")), IE.getResultMessage());

       RO = new JResponseObject(IE);

     }
     catch (Exception E) {
     }
     finally {
       conn.close();
     }
     return RO;
   }

   public Object OpenReport(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
       String path="/JDWReport/"+Data.toString()+".xml";
       try {
         InputStream IStream = CommonDBObject.createResourceFileInputStream(path, (JParamObject) Param);
         XmlEngine XML = new XmlEngine(IStream);
         if (XML.isEmpty()) {
           System.out.print("Load form define empty.");
           return new JResponseObject("");
         }else {
           return new JResponseObject(XML.GetXmlString());
         }
       }catch (Exception E) {
         System.out.print("Load form define error.");
         E.printStackTrace();
         return new JResponseObject("");
       }
   }

   /**
    * 模拟FI调用服务。
    * @param Param Object
    * @param Data Object
    * @param CustomObject Object
    * @param AdditiveObject Object
    * @return Object
    */
   public Object FICall(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
     //Param:参数,环境变量串的XML
     //Data :对象，包含命令的参数。IExchangeMessage
     IMessage  IE = null;
     if ( AdditiveObject != null ){
       IE = (IMessage)AdditiveObject;
     }
     else{
       return null;
     }

     JResponseObject RO = null;

     try {
       RO = new JResponseObject("10001",0);
     }
     catch (Exception E) {
       E.printStackTrace();
       RO = new JResponseObject(E.getMessage(),-1);
     }
     finally {

     }
     return RO;
   }

}
