package com.efounder.dbc;

import java.net.*;

import com.borland.dx.dataset.*;
import com.borland.dx.sql.dataset.*;
import com.borland.jb.util.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class AgentDatabase implements java.io.Serializable, Designable {
	protected ExtendClientAgent extendClientAgent = null;// 扩展的客户端接口
	protected String extendServerAgent = null;// 这里设置的是一个后台的类名
    protected String agentServer  = null;//"127.0.0.1";// 应用服务器地址
    protected String agentPort    = "8088";// 应用服务器端口
    protected String agentSafetyPort    = "443";//应用服务器安全端口
    protected String agentAppName = "EnterpriseServer";// 应用名称
    protected String agentService = "DataService";// Servlet名称
    protected boolean agentSafety = false;// 是否使用安全
    protected ConnectionDescriptor connectSet = null;
    /**
     *
     */
    private static AgentDatabase agentDatabase = null;

    /**
     *
     * @param dbServer String
     * @return AgentDatabase
     */
    public static synchronized AgentDatabase getDefault(String dbServer) {
    	if ( dbServer == null || dbServer.trim().length() == 0 ) return getDefault();
    	AgentDatabase db = new AgentDatabase();
    	db.setAgentServer(dbServer);
    	return db;
    }
    /**
     *
     * @return AgentDatabase
     */
    public static synchronized AgentDatabase getDefault() {
    	if ( agentDatabase == null ) {
    		agentDatabase = new AgentDatabase();
    	}
    	return agentDatabase;
    }
    /**
     *
     * @return ConnectionDescriptor
     */
    public ConnectionDescriptor getConnectSet() {
    	return connectSet;
    }
    public void setConnectSet(ConnectionDescriptor c) {
    	connectSet = c;
    }
    public String getExtendServerAgent() {
    	return extendServerAgent;
    }
    public void setExtendServerAgent(String v) {
    	extendServerAgent = v;
    }
    public void setExtendClientAgent(ExtendClientAgent eca) {
    	extendClientAgent = eca;
    }
    public ExtendClientAgent getExtendClientAgent() {
	  return extendClientAgent;
    }
    public void setExtendAgentObject(JParamObject extendAgentObject) {
      this.extendAgentObject = extendAgentObject;
    }
    protected JParamObject extendAgentObject = null;
    public Object getExtendAgentObject() {
    	if ( extendAgentObject == null )
    		extendAgentObject = JParamObject.Create();
//      setAgentSafety(extendAgentObject.isSecurity());
//      if ( EAI.Server != null && !"".equals(EAI.Server) ) {
//          if (!extendAgentObject.isSecurity()) {
//              setAgentServer(extendAgentObject.getEAIServer(EAI.Server));
//              setAgentPort(extendAgentObject.getEAIPort(EAI.Port));
//          } else {
//              setAgentServer(extendAgentObject.getSSLEAIServer(EAI.SSLServer));
//              setAgentSafetyPort(extendAgentObject.getSSLEAIPort(EAI.SSLPort));
//          }
//          setAgentAppName(extendAgentObject.getEnterpriseServer(EAI.Path));
//          return extendAgentObject;
//      }
    	return extendAgentObject;
    }
    public void setAgentSafetyPort(String v) {
    	agentSafetyPort = v;
    }
    public String getAgentSafetyPort() {
    	return agentSafetyPort;
    }
    public boolean isAgentSafety() {
    	return agentSafety;
    }
    public void setAgentSafety(boolean v) {
    	agentSafety = v;
    }
    public String getAgentServer() {
    	return agentServer;
    }
    public void setAgentServer(String v) {
    	agentServer = v;
    }
    public String getAgentPort() {
    	return agentPort;
    }
    public void setAgentPort(String v) {
    	agentPort = v;
    }
    public String getAgentAppName() {
    	return agentAppName;
    }
    public void setAgentAppName(String v) {
    	agentAppName = v;
    }
    public String getAgentService() {
    	return agentService;
    }
    public void setAgentService(String v) {
    	agentService = v;
    }
    public URL getAgentURL() throws Exception {
    	URL agentURL = null;String uri;
    // 从外部扩展的接口中获取相应的属性
//    getExtendAgentObject();
    	if ( agentSafety ) {
    		uri = "https://"+this.agentServer+":"+this.agentSafetyPort+"/";
    	} else {
    		uri = "http://"+ this.agentServer+":"+this.agentPort+"/";
    	}
    	uri += this.agentAppName+"/"+this.agentService;
    	agentURL = new URL(uri);
    	return agentURL;
    }
    public AgentDatabase() {
    }
	/**
	 * 装入多个DataSet的值
	 * @param datasets JAgentDataSet[]
	 */
  	public synchronized void loadDataSets(ClientDataSet[] datasets) throws Exception {
  		ClientDataSetData cdss[] = new ClientDataSetData[datasets.length];

  		for(int i=0;i<cdss.length;i++) {
  			cdss[i] = new ClientDataSetData(datasets[i]);// 获取每一个DataSet的SQL
  			datasets[i].BeforeDataLoad(cdss[i]);
  		}
  		ClientDataSetData newcdss[] = dbAgent.getDataSetDatas(this,cdss);
  		for (int i = 0; i < newcdss.length; i++) {
//  			newcdss[i].getDataSetData().loadDataSet(datasets[i]); // 对每一个DataSet进行数据的装入
  			datasets[i].EndDataLoad(newcdss[i]);
  		}
  	}
  	/**
  	 * 装入单个DataSet的值
  	 * @param datasets JAgentDataSet
  	 */
  	public void loadDataSet(ClientDataSet datasets) throws Exception {
  		ClientDataSet dss[] = {datasets};
  		loadDataSets(dss);
  	}
  	
  	/**
  	 * 同时保存多个DataSet的值
  	 * @param datasets JAgentDataSet[]
  	 */
  	public synchronized Object saveDataSets(ClientDataSet[] datasets) throws Exception {
//  		ClientDataSet[] dataSets = startResolution(datasets,true);
//  		ClientDataSet[] resOrder = findResolutionOrder(dataSets);
  		ResponseDataSetData RO = dbAgent.saveDataSets(this,datasets);
  		if ( RO.getErrorCode() == -1 ) {
  			resetPendingStatus(datasets,false);
  			endResolution(datasets,datasets.length);
  			throw RO.getAgentException();
  		} else {
  			resetPendingStatus(datasets,true);
  			endResolution(datasets,datasets.length);
  			//add by fsz
  			Object[]o=(Object[])RO.getAgentObject();
  			for(int i=0;i<datasets.length;i++)
  				datasets[i].EndDataSave(o[i]);
  			return RO.getAgentObject();
  		}
  	}
  	/**
  	 * 保存一个DataSet的值
  	 * @param datasets JAgentDataSet
  	 */
  	public Object saveDataSet(ClientDataSet datasets) throws Exception{
  		ClientDataSet dss[] = {datasets};
  		Object[] o=(Object[])saveDataSets(dss);
  		if(o!=null)
  			return o[0];
  		return null;
  	}
  	
  	public static String getTableNameFromSQL(String SQL,String def) {
  		String TableName = def;String tSQL,FROM=" FROM ",SPACE=" ",COMMA=",";
  		tSQL = SQL.toUpperCase();
  		int Index = tSQL.indexOf(FROM);
  		if ( Index > 0 ) {
  			tSQL = SQL.substring(Index+FROM.length());
  			tSQL = tSQL.trim();
  			Index = tSQL.indexOf(SPACE);
  			if ( Index > 0 ) {
  				TableName = tSQL.substring(0,Index);
  			} else {
  				Index = tSQL.indexOf(COMMA);
  				if ( Index > 0 ) {
  					TableName = tSQL.substring(0,Index);
  				} else {
  					TableName = tSQL;
  				}
  			}
  		}
  		return TableName;
  	}




  	private static void endResolution(ClientDataSet[] dataSets,int resolutionCalled) {
//  		StorageDataSet  dataSet;
//  		for (int index = 0; index < resolutionCalled; ++index) {
//  			if (dataSets[index] != null) {
//  				ProviderHelp.endResolution(dataSets[index].getStorageDataSet());
//  			}
//  		}
  	}
  	public static void resetPendingStatus(ClientDataSet[] dataSets, boolean markResolved) /*-throws DataSetException-*/ {
//  		for (int index = 0; index < dataSets.length; ++index)
//  			dataSets[index].resetPendingStatus(markResolved);
  	}
  	private static ClientDataSet[] startResolution(ClientDataSet[] dataSets, boolean postEdits) /*-throws DataSetException-*/ {
//  		StorageDataSet dataSet;
//  		for (int index = 0; index < dataSets.length; ++index) {
//  			dataSets[index].open();
//  			dataSet = dataSets[index].getStorageDataSet();
//  			ProviderHelp.startResolution(dataSets[index].getStorageDataSet(), postEdits);
//  		}
//  		return dataSets;
  		return null;
  	}
  	private static ClientDataSet[] findResolutionOrder(ClientDataSet[] dataSets) /*-throws DataSetException-*/ {
//  		ClientDataSet[] order = new ClientDataSet[dataSets.length];
//  		System.arraycopy(dataSets,0,order,0,dataSets.length);
//
//  		for (int index=0; index < dataSets.length; index++) {
//  			MasterLinkDescriptor link = order[index].getMasterLink();
//  			if (link == null) continue;
//  			ClientDataSet masterDataSet = link.getMasterDataSet();
//  			Diagnostic.check(masterDataSet != null);
//  			int mIndex;
//  			for (mIndex=0; mIndex < order.length && order[mIndex] != masterDataSet; mIndex++);
//  			if (mIndex != order.length && mIndex > index) {  // Swap if neccessary
//  				ClientDataSet temp  = order[index];
//  				order[index]  = order[mIndex];
//  				order[mIndex] = temp;
//  				index--;  // remember to check the master's master
//  			}
//  		}
//  		return order;
  		return null;
	}

}
