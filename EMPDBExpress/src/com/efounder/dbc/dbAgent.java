package com.efounder.dbc;

import java.net.*;
import java.sql.*;

import com.borland.dx.dataset.*;
import com.borland.dx.sql.dataset.*;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.dbc.blob.*;
import com.efounder.eai.data.*;
import com.efounder.db.*;
import com.efounder.eai.*;
import com.efounder.eai.service.ParameterManager;
import java.util.List;
import com.efounder.dctbuilder.data.DctContext;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class dbAgent {
	public dbAgent() {
	}
	/**
	 *
	 * @param agentDatabase AgentDatabase
	 * @param request RequestDataSetData
	 * @throws Exception
	 * @return ResponseDataSetData
	 */
	public static ResponseDataSetData invokeAgentService(AgentDatabase agentDatabase,RequestDataSetData request) throws Exception {
		ResponseDataSetData RDS = null;URLConnection urlc = null;
		String agentServer = agentDatabase.getAgentServer();
		String method = request.agentCommand;
		String object = "DataSetService";
		if ( agentServer != null ) object = agentServer+"."+object;
		// 通过统一接口调用
		RDS = (ResponseDataSetData) EAI.DAL.IOM(object,method,agentDatabase.getExtendAgentObject(),request);
		return RDS;
	}
	/**
	 * 指定SQL数组，返回数据,数据格式是DataSetData
	 * @param sqls String[]
	 * @param tabs String[]
	 * @return AgentDataSetData[]
	 */
	public static ClientDataSetData[] getDataSetDatas(AgentDatabase agentDatabase,ClientDataSetData[] cdss) throws Exception {
		ClientDataSetData[] clientDataSetData = null;
		RequestDataSetData PO;
		ResponseDataSetData RO = null;
		PO = new RequestDataSetData(agentDatabase,"provider",cdss);
		RO = invokeAgentService(agentDatabase,PO);
		if ( RO != null && RO.getErrorCode() >= 0 ) {
			Object o=RO.getAgentObject();
			if(o instanceof ClientDataSetData[])
				clientDataSetData = (ClientDataSetData[])RO.getAgentObject();
			else{
				ClientDataSetData dsd[]=(ClientDataSetData[])o;
				clientDataSetData=cdss;
				for(int i=0;i<clientDataSetData.length;i++){
					clientDataSetData[i].setDataSetData(dsd[i].getDataSetData());
				}
			}
		} else {
			throw RO.getAgentException();
  	  }
		return clientDataSetData;
	}
	
	/**
	 * 指定单个SQL，返回数据
	 * @param sql String
	 * @param tab String
	 * @return AgentDataSetData
	 */
	public static ClientDataSetData getDataSetData(AgentDatabase agentDatabase,ClientDataSetData cds) throws Exception {
		ClientDataSetData clientDataSetData = null;
		ClientDataSetData[] cdss={cds};
		ClientDataSetData[] res1 = getDataSetDatas(agentDatabase,cdss);;
		if ( res1 != null && res1.length >0) clientDataSetData = res1[0];
		return clientDataSetData;
	}
	/**
	 * 保存多个DataSet
	 * @param resOrder JAgentDataSet[]
	 * @return JResponseObject
	 */
	public static ResponseDataSetData saveDataSets(AgentDatabase agentDatabase,ClientDataSet[] resOrder) throws Exception {
		ClientDataSetData adss[] = new ClientDataSetData[resOrder.length];
//		TableDataSet dataSetLoaded = new TableDataSet();
		for(int i=0;i<adss.length;i++) {
			adss[i] = new ClientDataSetData(resOrder[i]);
			((ClientDataSet)resOrder[i]).BeforDataSave(adss[i]);
//			dsd = DataSetData.extractDataSetChanges(resOrder[i]);
//			dataSetLoaded.empty();
//			dsd.loadDataSet(dataSetLoaded);
//			adss[i].setDataSetData(dsd);
		}
		RequestDataSetData  PO = new RequestDataSetData(agentDatabase,"resolver",adss);
		ResponseDataSetData RO = invokeAgentService(agentDatabase,PO);
		return RO;
	}
	/**
	 * 保存单个DataSet
	 * @param ds DataSet
	 * @return JResponseObject
	 */
	public static ResponseDataSetData saveDataSet(AgentDatabase agentDatabase,ClientDataSet ds) throws Exception {
		ClientDataSet dss[]={ds};
		return saveDataSets(agentDatabase,dss);
	}
	/**
	 *
	 * @param DB Database
	 * @param Sqls String[]
	 * @throws Exception
	 * @return DataSetData[]
	 */
	public static void changeSchema(Database db,JParamObject PO){
//		String schema = DBTools.getDBOSchemaUser(PO);
//		String strCmd = "alter session set current_schema =" + schema;
//		db.executeStatement(strCmd);
	}
	public static ClientDataSetData[] QueryDataObject(Database DB,ClientDataSetData[] cdss,RequestDataSetData request) throws Exception {
		ExtendServerDataSet    esds = null;
		QueryDataSet            QDS = new QueryDataSet();
		QueryDescriptor          qd = null;
		String                  sql = "";
		BlobServerDataSet       bsd = new BlobServerDataSet();
		JParamObject             PO = (JParamObject)request.getExtentObject();
		JResponseObject          RO = null;
		EFDataSet      queryDataset = null;
		
		try{
			changeSchema(DB,PO);
			for (int i = 0; i < cdss.length; i++) {
				if(cdss[i].hasBlobData())
					bsd.dataSetStartLoad(DB, request, cdss[i]);
				esds = getCDSExtend(cdss[i]);
				if (esds != null)
					esds.dataSetStartLoad(DB, request, cdss[i]);
				sql = cdss[i].getQuerySet().getQueryString();
				java.util.List list=(List) PO.getValue("$$SQL_VIEWLIST",null);
				if(list!=null)list.add(sql);
				String value=ParameterManager.getDefault().getSystemParam("SQL_MONITOR");
				if (value == null || value.trim().length() == 0)
					value = "0";
				int iv = Integer.parseInt(value);
				if ((iv & 1) != 0)
					System.out.println(sql);

				qd = new QueryDescriptor(sql);
				
				try {
					RO = (JResponseObject) EAI.DAL.IOM("ServiceObject", "queryDataSet", PO, qd);
					if(RO != null && RO.getErrorCode() != -1) {
						queryDataset = (EFDataSet) RO.getAddinObject();
						cdss[i].getDataSetData().setRowSetList(queryDataset.getRowSetList());
					}
				} catch(Exception ce) {
					ce.printStackTrace();
				}
//				QDS.setQuery(qd);
//				QDS.open();
				if (esds != null)
					esds.dataSetEndLoad(DB, request, cdss[i], QDS);
				if (cdss[i].hasBlobData())
					bsd.dataSetEndLoad(DB, request, cdss[i],QDS);
//				DSD = DataSetData.extractDataSet(QDS);

//				cdss[i].setDataSetData(DSD);
				//
//				DSDS[i] = DSD;
				// 不传输context中的元数据，客户端使用缓存
//				Object userObject = cdss[i].getUserObject();
//				if (userObject instanceof DctContext) {
//					DctContext dctContext = (DctContext) userObject;
//					dctContext.setMetaData(null);
//				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception(sql+"\r\n"+e.getLocalizedMessage(),e);
		}
		return cdss;
	}
	private static ExtendServerDataSet getCDSExtend(ClientDataSetData cdsd) {
		ExtendServerDataSet esds = null;String clazz;
		clazz = cdsd.getExtendServerDataSet();
		try {
			if ( clazz != null && !"".equals(clazz) )
				esds = (ExtendServerDataSet)dbAgent.class.forName(clazz).newInstance();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return esds;
	}
	/**
	 *
	 * @param DB Database
	 * @param Adsds AgentDataSetData[]
	 * @throws Exception
	 * @return int
	 */
	public static String getNoBlogColumns(Database DB,String SQL) throws Exception {
		Statement stmt=DB.createStatement();
		ResultSet rs=stmt.executeQuery(SQL);
		ResultSetMetaData rsmd=rs.getMetaData();
		int count=rsmd.getColumnCount();
		String columns="";
		int type;
		for(int i=1;i<=count;i++){
			type=rsmd.getColumnType(i);
			if(type!= Types.BLOB&& type!=Types.CLOB&& type!=Types.JAVA_OBJECT)
				columns+=rsmd.getColumnName(i)+",";

		}
		if(columns.length()>0)columns=columns.substring(0,columns.length()-1);
		return columns;
	}
	public static int SaveDataObject(Database DB,ClientDataSetData[] Adsds,RequestDataSetData request) throws Exception {
		ExtendServerDataSet      esds = null;
		BlobServerDataSet         bsd = new BlobServerDataSet();
		ClientDataSetData        Adsd = null;
		JParamObject               PO = (JParamObject)request.getExtentObject();
		JResponseObject            RO = null;
		int                 errorCode = -1;
		
		DB.setAutoCommit(false);
		DB.setUseTransactions(false);//ADD BY FSZ重要，　否则DataSet自动提交了
		try {
			changeSchema(DB,(JParamObject)request.getExtentObject());
			for(int i=0;i<Adsds.length;i++) {
				Adsd = Adsds[i];// 取出第一个AgenetDataSetData
				esds = getCDSExtend(Adsd);
				if ( esds != null ) {
			          esds.dataSetStartSave(DB,request,Adsd, null);
				}
				RO = (JResponseObject) EAI.DAL.IOM("ServiceObject", "saveDataSet", PO, DB, Adsd);
				if(RO != null && RO.getErrorCode() == -1) {
					errorCode = RO.getErrorCode();
					if(errorCode == -1) {
						throw new Exception(RO.getResponseObject().toString());
					}
				}
			    if ( esds != null ) {
			    	esds.dataSetEndSave(DB,request,Adsd,null);
			    }
			    if(Adsd.hasBlobData()) {
			    	bsd.dataSetEndSave(DB,request,Adsd,null);
			    }
//				TableName = Adsd.getTableName();// 获取表名
//				SQL = "SELECT * FROM "+TableName+Where;//标准的SQL
//				if(Adsd.hasBlobData()){
//					String col=getNoBlogColumns(DB,SQL);
//					if(!col.equals(""))
//						SQL = "SELECT "+col+" FROM "+TableName+Where;
//				}
//				QueryResolver qr = new QueryResolver();
//				qr.setDatabase(DB);
//				qr.setUpdateMode(UpdateMode.KEY_COLUMNS);
//				QDS.setResolver(qr);
//				QDS.setQuery(new QueryDescriptor(SQL));// 一行数据都装不进来，2会小于1吗？
//				QDS.open(); // 打开
//				Adsd.getDataSetData().loadDataSet(QDS); // 将前台修改的数据填进dataSet中，TMD
//				if(Adsd.hasBlobData())
//					bsd.dataSetStartSave(DB,request,Adsd,QDS);
//				if ( esds != null )
//					esds.dataSetStartSave(DB,request,Adsd,QDS);
//				QDS.saveChanges();
//				if ( esds != null )
//					esds.dataSetEndSave(DB,request,Adsd,QDS);
//				if(Adsd.hasBlobData())
//					bsd.dataSetEndSave(DB,request,Adsd,QDS);
//				QDS.close();// 关闭
			}
			DB.commit();
		} catch ( Exception e ) {
			DB.rollback();
			e.printStackTrace();
			throw e;
		}
		return 0;
	}
}
