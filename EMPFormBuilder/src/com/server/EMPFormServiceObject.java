package com.server;

import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.common.util.EMPSQLUtil;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.framework.JActiveObject;
import com.efounder.sql.JConnection;
import com.pub.util.FileUtil;

public class EMPFormServiceObject extends JActiveObject {
	private static final String GUID = "00000000-0003-0001-0000000000000009";

	/**
	 * 加载服务
	 * @param Param
	 * @param Data
	 * @param CustomObject
	 * @param AdditiveObject
	 * @return
	 */
    public JResponseObject loadService(Object Param, Object Data, Object CustomObject, Object AdditiveObject){
		JParamObject        PO = null;
		JConnection       conn = null;
		Statement         stmt = null;
		ResultSet           rs = null;
		File           xmlFile = null;
		byte[]        formByte = null;
		JResponseObject     RO = null;
		Object      fileObject = null;
		String              sp = System.getProperty("file.separator");
		String     servletPath = EAI.LocalUserHome+"WEB-INF" + sp + "ServiceSpace"+sp;
		String      ServiceKey = "";
		String          strSql = "";
		
		try {
			if (Param == null) {
				PO = JParamObject.Create();
			} else {
				PO = (JParamObject) Param;
			}

			ServiceKey = PO.GetValueByEnvName("ServiceKey", "");
			servletPath += ServiceKey + ".xml";
			xmlFile = FileUtil.getFile(servletPath); 
			
			if(xmlFile != null && xmlFile.exists()) {
				formByte = FileUtil.getBytesFromFile(servletPath);
			} else {
				conn = this.getConnection(PO);
				stmt = conn.createStatement();
				
				if(formByte == null){
					strSql = "select * from SYS_DBFORMGS where BBZD_BH = '" + ServiceKey + "'";
					rs = stmt.executeQuery(strSql);
		            if( rs != null && rs.next() ){
		            	formByte = ( byte[] )EMPSQLUtil.getBlogData( rs, "BBZD_GS" );
		            }
				}
			}
			
			XMLDecoder xmlEncoder = new XMLDecoder(new ByteArrayInputStream(formByte), null, null, Thread.currentThread().getContextClassLoader());
			fileObject = xmlEncoder.readObject();
			xmlEncoder.close();
			
			RO = new JResponseObject(fileObject, 1);
		} catch (Exception ce) {
			RO = new JResponseObject("", -1);
			ce.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					stmt = null;
				}
			}
			if (conn != null) {
				conn.close();
			}
		}
		return RO;
	}
	/**
     * 根据PO获取数据库连接
     *
     * @param PO JParamObject
     * @return   JConnection
     * @throws    Exception
     */
    public static JConnection getConnection(JParamObject PO) throws Exception {

        JConnection con = (JConnection) EAI.DAL.IOM("DBManagerObject","GetDBConnection",PO,null);
        return con;
    }
}