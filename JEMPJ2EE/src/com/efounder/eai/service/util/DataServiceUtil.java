package com.efounder.eai.service.util;

import java.sql.*;
import com.borland.dx.dataset.*;
import com.borland.dx.sql.dataset.*;
import com.efounder.db.*;
import com.efounder.dbc.*;
import com.efounder.eai.data.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DataServiceUtil {
  /**
   *
   */
  protected DataServiceUtil() {
  }
//  /**
//   *
//   * @param request HttpServletRequest
//   * @return RequestDataSetData
//   */
//  public static RequestDataSetData getRequestDataSetData(HttpServletRequest request) throws Exception {
//    RequestDataSetData RO=null;ServletInputStream sis=null;
//    try {
//      sis = request.getInputStream();
//      if (sis != null) {
//        RO = (RequestDataSetData) CommManager.getDefault().requestObject(sis);
//      }
//      return RO;
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return null;
//  }
//  /**
//   *
//   * @param response HttpServletResponse
//   * @param RO ResponseDataSetData
//   * @throws Exception
//   */
//  public static void putResponseDataSetData(HttpServletResponse response,ResponseDataSetData RO)throws Exception{
//    // 通过response对象返回数据
//    ServletOutputStream os = response.getOutputStream();
//    // CommManger用来通过HTTP/S进行通讯
//    CommManager.getDefault().responseObject(os,RO);
//    // 关闭流
//    os.close();
//  }
//  /**
//   *
//   * @param httpServlet HttpServlet
//   * @param request HttpServletRequest
//   * @param response HttpServletResponse
//   * @param requestObject RequestDataSetData
//   */
//  protected static void initParamObject(HttpServlet httpServlet,HttpServletRequest request, HttpServletResponse response,RequestDataSetData requestObject) {
//    if ( !(requestObject.getExtentObject() instanceof JParamObject) ) return;
//    JParamObject po = (JParamObject)requestObject.getExtentObject();
//    po.setHttpServlet(httpServlet);
//    po.setHttpServletRequest(request);
//    po.setHttpServletResponse(response);
//    SessionUtil.loginStub2Param(request,po);
//  }
//  /**
//   *
//   * @param requestObject RequestDataSetData
//   * @return ResponseDataSetData
//   */
//  public static ResponseDataSetData invokeAgentService(HttpServlet httpServlet,
//      HttpServletRequest request, HttpServletResponse response,
//      RequestDataSetData requestObject) throws Exception {
//    // 初始化JParamObject操作
//    initParamObject(httpServlet,request,response,requestObject);
//    //
//    return invokeDBAgentService(requestObject);
//    }
    /**
     *
     * @param requestObject RequestDataSetData
     * @return ResponseDataSetData
     */
    public static ResponseDataSetData invokeDBAgentService(RequestDataSetData requestObject) {
      ResponseDataSetData RSD = null;
      String agentCommand = requestObject.getAgentCommand();
      Database agentDatabase = null;
      // 获取Database
      try {
    	  agentDatabase = getDatabase(requestObject);
        // 装入数据
        if ("provider".equals(agentCommand)) {
          ClientDataSetData[] cdss = (ClientDataSetData[]) requestObject.getAgentObject();
          ClientDataSetData[] dsd = dbAgent.QueryDataObject(agentDatabase, cdss, requestObject);
          RSD = new ResponseDataSetData(dsd, null, 0);
        }
        // 保存
        if ("resolver".equals(agentCommand)) {
          ClientDataSetData[] Adsds = (ClientDataSetData[]) requestObject.getAgentObject();
          dbAgent.SaveDataObject(agentDatabase, Adsds, requestObject);
          //add by fsz
          Object o[] = new Object[Adsds.length];
          for (int i = 0; i < o.length; i++)
            o[i] = Adsds[i].getUserObject();
          RSD = new ResponseDataSetData(o, null, 0);
        }
      }
      catch (Exception e) {
        e.printStackTrace();
        RSD = new ResponseDataSetData(null, new Exception(e), -1);
      }
      finally {
        agentDatabase.closeConnection();
      }
      return RSD;
    }
    /**
     *
     * @param requestObject RequestDataSetData
     * @return Database
     */
    private static Database getDatabase(RequestDataSetData requestObject) throws Exception {
      Database serverAgentDatabase = null;
      serverAgentDatabase = new Database();
      Object extentObject = requestObject.getExtentObject();
      if ( extentObject == null ) {
        ConnectionDescriptor cd = requestObject.getConnectSet();
        serverAgentDatabase.setConnection(cd);
      } else {
        Connection conn = getConnection(requestObject);
        serverAgentDatabase.setJdbcConnection(conn);
      }
      return serverAgentDatabase;
    }
    /**
     *
     * @param requestObject RequestDataSetData
     * @return Connection
     */
    private static Connection getConnection(RequestDataSetData requestObject) throws Exception {
      Connection conn = null;
      conn = DBServerAgent.getDefault().getExtendServerAgent(requestObject);
      return conn;
    }
}
