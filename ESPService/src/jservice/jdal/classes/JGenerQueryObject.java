package jservice.jdal.classes;

import jfoundation.bridge.classes.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;
import jservice.jbof.classes.GenerQueryObject.*;
import jservice.jdal.classes.query.server.QueryCacheManager;

import java.lang.reflect.*;
import jtoolkit.xml.classes.*;
import org.jdom.*;
import java.util.*;

import com.core.xml.JBOFClass;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.service.MemCachedManager;
import com.efounder.sql.JConnection;
import com.efounder.buffer.DataBufferManager;
import com.efounder.buffer.DataBuffer;
import java.io.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JGenerQueryObject extends JActiveObject {
  static ResourceBundle res = ResourceBundle.getBundle("jservice.jdal.classes.Language");
  private static final String GUID = "00000000-1113-0001-0000000000000017";
  public JGenerQueryObject() {
    setObjectGUID(GUID);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object QueryObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception{
    JParamObject     PO  = (JParamObject)Param;
    JQueryStubObject QO  = (JQueryStubObject)Data;
    JResponseObject  RO  = null;JConnection conn = null;
    try {
      // 获取数据库连接
      conn = (JConnection) JActiveDComDM.
          AbstractDataActiveFramework.InvokeObjectMethod("DBManagerObject",
          "GetDBConnection", Param, Data, CustomObject, AdditiveObject);
      // 返回组织数据
      RO = processOrganizeQuery(PO, QO, conn);
    } catch(Exception ce) {
    	ce.printStackTrace();
    } finally {
      conn.close();
    }
    return RO;
  }
  public JResponseObject processOrganizeQuery(JParamObject PO,JQueryStubObject QO,JConnection conn) {
    Object OrganizeObject = null;JResponseObject RO = null;String Lang;
    try {
      
      Lang = PO.GetValueByEnvName("International","zh");
      QO.OrganizeQueryXMLURL = JXMLResource.LoadSXML(QO.Path+"OrganizeQuery/",QO.OrganizeQueryXML,Lang);
      Object OArray[] = {PO,QO,conn};
      OrganizeObject = Class.forName(QO.OrganizeQueryClass).newInstance();

      //进行参数回收
      if (QO.ReclaimerMethod!=null && !"".equals(QO.ReclaimerMethod)){
        if (QO.QueryParamObject==null) QO.QueryParamObject = new Hashtable();
        JBOFClass.CallObjectMethod(OrganizeObject,QO.ReclaimerMethod,QO,PO);
      }
      //正常查询过程
      RO = (JResponseObject)JBOFClass.CallObjectMethod(OrganizeObject,"QueryObject",OArray);
      //外部数据查询
      if(QO.ExteriorQueryClass!=null && !"".equals(QO.ExteriorQueryClass) && QO.isECC6){
        if (QO.ExteriorMethod == null || "".equals(QO.ExteriorMethod))
          QO.ExteriorMethod = "ExteriorQuery";
        Object ExteriorClass = Class.forName(QO.ExteriorQueryClass).newInstance();
        JResponseObject ro = (JResponseObject)JBOFClass.CallObjectMethod(ExteriorClass,QO.ExteriorMethod,OArray);
        //将两个结果同时返回
        Object[] Res = {RO,ro};
        RO = new JResponseObject(Res,0);
      }
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return RO;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object AnalyzeObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) {
    return null;
  }
  /**
   *
   * @param Param Object
   * @param Data Object
   * @param CustomObject Object
   * @param AdditiveObject Object
   * @return Object
   * @throws Exception
   */
  public Object GetQueryObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception{
    JParamObject     PO  = (JParamObject)Param;
    JResponseObject  RO  = null;
    try {
      // 返回组织数据
      String key = PO.GetValueByParamName("QueryFile","");
      Object file = MemCachedManager.getDefault().getMemCached().get(key);
      if (file==null){
        System.out.println("--QueryDataGetFromMemCachedFalse");
        java.io.InputStream ins = new FileInputStream(new File(QueryCacheManager.Local_UserHome + key));
        byte[] filebyte = new byte[ins.available()];
        ins.read(filebyte);
        file = filebyte;
      }else if (file instanceof String){

        System.out.println("--QueryDataGetFromMemCachedSucceed");
      }
      RO = new JResponseObject(file,1,null);
    }catch(Exception e){
      e.printStackTrace();
      RO = new JResponseObject(null,-1,null);
    }
    return RO;
  }}
