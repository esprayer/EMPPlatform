package jframework.MtoDB.BofJfc.DBO;

import java.io.*;

import com.efounder.eai.data.JParamObject;
import com.efounder.pub.util.Debug;
import com.efounder.sql.JConnection;
import com.help.xml.HelpMetaManager;
import com.report.table.jxml.TableManager;

import jframework.foundation.classes.*;
import jframework.resource.classes.*;
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Xyz(2001.12.29)
//实现: Xyz
//修改:
//--------------------------------------------------------------------------------------------------
public class CommonDBObject {

  protected JConnection con = null;              //数据库连接
  protected JParamObject paramObject = null; //数据参数
  //
  protected String userId   = "";        //查询的用户编号
  protected String centerId   = "";      //查询的责任中心编号
  protected String centerName = "";      //查询的责任中心名称
  //Xyz用于生成XML数据
  protected String[] resultXmlDatas = null;
/**
 * CommonDBObject 构造子注解。
 */
public CommonDBObject() {
	super();
}
  public CommonDBObject(JConnection c, JParamObject po, String p1, Integer p2, Object p3) {
    setConnection(c);
    setParamObject(po);
    //
    setUserId(po.GetValueByEnvName("UserName"));//UserNameCaption姓名//UserID标识
    setCenterId(po.GetValueByEnvName("CW_ZWZRZX"));
    setCenterName(po.GetValueByEnvName("CW_ZWZRZXNAME"));
  }
/**
 * 此处插入方法描述。
 * 创建日期：(2002-03-06 09:17:22)
 * @return com.pansoft.help.xml.HelpMetaManager
 * @param fileName java.lang.String
 */
public static HelpMetaManager createHelpTableManagerTemplate(String fileName,JParamObject mypo) throws Exception{
    HelpMetaManager helpTableManager = null;
    try{

//      fileName=JActiveDComDM.CodeBase+"Resource/"+JActiveDComDM.International+"/XML/"+fileName;
      InputStream inputStream = CommonDBObject.createResourceFileInputStream(fileName,mypo);
      helpTableManager = new HelpMetaManager(inputStream);

//      Debug.PrintlnMessageToSystem("Get TableManager OK!"+helpTableManager.printDOMTree());
    }catch(Exception e){
      Debug.PrintlnMessageToSystem(e);
	  e.printStackTrace();
	  throw new Exception("读取数据显示格式文件时发生错误："+e.getMessage());
    }
    return helpTableManager;
}
/**
 * 此处插入方法描述。
 * 创建日期：(2002-03-06 09:17:22)
 * @return inputStream java.io.InputStream
 * @param  fileName    java.lang.String
 */
public static InputStream createResourceFileInputStream(String fileName,JParamObject mypo)  throws Exception{
//      Debug.PrintlnMessageToSystem(fileName);
//      //
//      fileName = fileName.trim();
//      //if(fileName.startsWith("/"))
//        //fileName = fileName.substring(1);
//      if(!fileName.startsWith("/"))
//        fileName = "/"+fileName;
//      if(!fileName.endsWith(".xml"))
//        fileName = fileName+".xml";
//      //
//      fileName=JActiveDComDM.CodeBase+"Resource/"+mypo.GetValueByEnvName("International")+"/XML/"+fileName;
//      fileName=JActiveDComDM.CodeBase+"Resource/"+mypo.GetValueByEnvName("International")+"/XML/"+fileName;
      fileName = "Resource/"+mypo.GetValueByEnvName("International")+"/XML"+fileName;
      try{
        java.net.URL url = JXMLResource.LoadSXML("", fileName, "");
        fileName = url.toString();
  //      Debug.PrintlnMessageToSystem(fileName);
  //      Debug.PrintlnMessageToSystem(CommonDBObject.class.getResource(fileName));
  //      InputStream inputStream = new BufferedInputStream(CommonDBObject.class.getResourceAsStream(fileName));
        java.net.URL myurl = new java.net.URL(fileName);
        InputStream inputStream = myurl.openStream();
  //      Debug.PrintlnMessageToSystem("Get InputStream OK!");
        //
        return inputStream;
      }
      catch(Exception E){
        System.out.println("Load file error:"+fileName);
        throw E;
      }
}
/**
 * 此处插入方法描述。
 * 创建日期：(2002-03-06 09:17:22)
 * @return com.pansoft.report.table.xml.TableManager
 * @param fileName java.lang.String
 */
public static TableManager createTableManagerTemplate(String fileName,JParamObject mypo) throws Exception{
    TableManager tableManager = null;
    try{
//      fileName=JActiveDComDM.CodeBase+"Resource/"+JActiveDComDM.International+"/XML/"+fileName;
      InputStream inputStream = CommonDBObject.createResourceFileInputStream(fileName,mypo);
      tableManager = new TableManager(inputStream);

//      Debug.PrintlnMessageToSystem("Get TableManager OK!"+tableManager.printDOMTree());
    }catch(Exception e){
	  e.printStackTrace();
	  throw new Exception("读取数据显示格式文件时发生错误："+e.getMessage());
    }
    return tableManager;
}
  public String getCenterId(){
    return this.centerId;
  }
  public String getCenterName(){
    return this.centerName;
  }
  public JConnection getConnection(){
    return this.con;
  }
  public JParamObject getParamObject(){
    return this.paramObject;
  }
  public String[] getResultXmlDatas(){
    return this.resultXmlDatas;
  }
  public String getUserId(){
    return this.userId;
  }
  public void setCenterId(String id){
    if(id != null)
      this.centerId = id.trim();
  }
  public void setCenterName(String name){
    if(name != null)
      this.centerName = name.trim();
  }
  public void setConnection(JConnection c){
    this.con = c;
  }
  public void setParamObject(JParamObject po){
    this.paramObject = po;
  }
  public void setUserId(String id){
    if(id != null)
      this.userId = id.trim();
  }
}
