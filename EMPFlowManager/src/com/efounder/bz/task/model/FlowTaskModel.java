package com.efounder.bz.task.model;

import java.util.Map;

import org.openide.ErrorManager;

import com.efounder.builder.base.data.DataSetEvent;
import com.efounder.builder.base.data.DataSetListener;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.meta.MetaDataManager;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.bz.dbform.datamodel.DMComponent;
import com.efounder.bz.dbform.datamodel.DataComponentAdapter;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import com.efounder.bz.dbform.datamodel.ServiceComponent;
import com.efounder.bz.dbform.datamodel.treetable.MultiDataSetTreeTableModel;
import com.efounder.bz.flow.FlowConstants;
import com.efounder.eai.EAIServer;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.service.script.ScriptManager;
import com.efounder.service.script.ScriptObject;
import com.efounder.service.script.Scriptable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FlowTaskModel extends DataComponentAdapter implements DataSetComponent, DataSetListener, Scriptable {
	public static final String            _FLOWDATASET_ = "FLOWDataSet";
	public static final String            _NODEDATASET_ = "NODEDataSet";
	public static final String     _TASKPENDINGDATASET_ = "TASKPendingDataSet";
	public static final String    _TASKPROCESSEDDATASET = "TASKProcessedDataSet";
	public static final String      _TASKWAITINGDATASET = "TASKWaitingDataSet";
	public static final String   _TASKPROCESSINGDATASET = "TASKProcessingDataSet";
	public static final String      _TASKCANCELDATASET_ = "TASKCancelDataSet";
	public static final String      _TASKFINISHDATASET_ = "TASKFinishDataSet";
	MultiDataSetTreeTableModel                treeModel = new MultiDataSetTreeTableModel();
	/**
	 *
	 */
	protected static String[] dataSetKeys = new String[] { _FLOWDATASET_,
														   _NODEDATASET_, _TASKPENDINGDATASET_, _TASKPROCESSEDDATASET,
														   _TASKWAITINGDATASET, _TASKPROCESSINGDATASET, _TASKCANCELDATASET_,
														   _TASKFINISHDATASET_ };

	/**
	 *
  	*/
	// add by fsz 要load的服务器列表，可以load不同服务器上的流程,以,号分隔
	protected String servers = "";

	public FlowTaskModel() {
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

	/**
	 * 
	 * @param dataSetKey  String
	 * @return EFDataSet
	 */
	public EFDataSet getDataSet(String dataSetKey) {
		// add by liushr for :根据dataSetKey返回不同的值
		for (int i = 0; i < dmComponentList.size(); i++) {
			DMComponent dmComponent = (DMComponent) dmComponentList.get(i);
			if (dataSetKey.equals(dmComponent.getDataSetID())) {
				return dmComponent.getDataSet();
			}
		}
		//
		return null;
	}

	/**
	 * 
	 * @return String[]
	 */
	public String[] getDataSetKey() {
		return dataSetKeys;
	}

	/**
	 * 
	 * @param obj_id  String
	 * @return DOMetaData[]
	 */
	public DOMetaData[] getDOMetaData(String obj_id) {
		try {
			return new DOMetaData[] { (DOMetaData) MetaDataManager
					.getDODataManager().getMetaData("FLOW_TASK_LIST") };
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 *
	 */
	protected java.util.List dmComponentList = null;

	/**
	 * 
	 * @param dmComponent  DMComponent
	 */
	public void insertDMComponent(DMComponent dmComponent) {
		if (dmComponentList == null)
			dmComponentList = new java.util.ArrayList();
		dmComponentList.add(dmComponent);
	}

	/**
	 * 
	 * @param dmComponent DMComponent
	 */
	public void removeDMComponent(DMComponent dmComponent) {
		if (dmComponentList == null) return;
		dmComponentList.remove(dmComponent);
	}

	public Object load(Object param) throws Exception {
		JParamObject PO = (JParamObject) param;
		EFDataSet eds = null;
		if (servers == null || servers.trim().length() == 0) {
			eds = loadTaskData(PO);
		} else {
			eds = EFDataSet.getInstance("FLOW_TASK_LIST");
			String[] sers = servers.split(",");
			boolean bhave = false;
			for (int i = 0; i < sers.length; i++) {
				if (EAIServer.getEAIServer(sers[i]) == null) continue;
				bhave = true;
			}
			if (!bhave) {// 找当前登陆的
				EFDataSet ds = loadTaskData(PO);
				if (ds != null) {
					if (eds.getRowCount() == 0) eds.setPrimeKey(ds.getPrimeKey());
					eds.addAll(ds);
				}
			}
			for (int i = 0; bhave && i < sers.length; i++) {
				if (EAIServer.getEAIServer(sers[i]) == null) continue;
				PO.setEAIServer(sers[i]);
				setLoadParamObject(PO, sers[i]);
				EFDataSet ds = loadTaskData(PO);
				if (ds == null) continue;
				if (eds.getRowCount() == 0) eds.setPrimeKey(ds.getPrimeKey());
				eds.addAll(ds);
			}
		}
		this.setActiveDataSet(eds);
		return eds;
	}

	public void refresh(EFDataSet dataset) {
		this.setActiveDataSet(dataset);
	}
	
	protected EFDataSet loadTaskData(JParamObject PO) throws Exception {
		JResponseObject RO = null;
		switch (loadType) {
		case FlowConstants._COMMON_LOAD: {
			RO = (JResponseObject) com.efounder.eai.EAI.DAL.IOM("TaskIOService", "loadFlowTaskInfo", PO);
		}
		break;
		case FlowConstants._SERVER_MTHOD_LOAD: {
			RO = (JResponseObject) com.efounder.eai.EAI.DAL.IOM(serverObj,
					serverMethod, PO);
		}
		break;
		case FlowConstants._SERVICE_KEY_LOAD:
			RO = (JResponseObject) ServiceComponent.syncRunEnterpriseService(serviceKey, PO, null, null, null);
		}
		EFDataSet eds = (EFDataSet) RO.getResponseObject();
//		Object o = runScript(this, "loadEnd", new String[] { "dataSet" }, new Object[] { eds });
//		if (o != null) eds = (EFDataSet) o;
		setIcon(eds);
		return eds;
	}

	protected void setIcon(EFDataSet eds) {
		for (int i = 0; eds != null && i < eds.getRowCount(); i++) {
			EFRowSet ers = eds.getRowSet(i);
			String icon = ers.getString("ICON", "");
			if (icon.trim().length() > 0)
				ers.setRowIcon(ExplorerIcons.getExplorerIcon(icon));
//			 String[]dstyps=ers.getDSTypes();
//			 for(int j=0;dstyps!=null&&j<dstyps.length;j++)
//			 setIcon(ers.getDataSet(dstyps[j]));
		}

	}

	public JParamObject getParamObject() {

		// 参数转换为空，则直接返回
		JParamObject po = JParamObject.Create();
		po.SetValueByParamName("FLOW_ID", flowids);
		po.SetValueByParamName("NODE_ID", nodeids);
		po.SetValueByParamName("OBJ_GUID", resids);
		po.SetValueByParamName("TASK_TYPE", taskType);
		// 取任务数据时，要用到的各个模型的组织机构字典
		// 虽然可以通过模型元数据取，但设置更直接
		// 后取取任务数据时，根据字典名从PO取值，来限制BIZ_TO_UNIT
		po.SetValueByParamName("MDL_UNIT_DCTIDS", this.mdlUnitDcts);
		po.SetIntByParamName(FlowConstants.CHILD_FLOW_DISP_TYPE, cflowDispType);
		return po;
	}

	public Object load() throws Exception {
		JParamObject po = getParamObject();
		return load(po);
	}

	public Object save() throws Exception {
		return null;
	}

	public Object first() throws Exception {
		return null;
	}

	public Object prior() throws Exception {
		return null;
	}

	public Object next() throws Exception {
		return null;
	}

	public Object last() throws Exception {
		return null;
	}

	public Object search(ESPRowSet rowSet) throws Exception {
		return null;
	}

	public Object create() throws Exception {
		return null;
	}

	public boolean canEdit() throws Exception {
		return false;
	}

	public Object startEdit() throws Exception {
		return null;
	}

	public Object cancelEdit() throws Exception {
		return null;
	}

	public Object stopEdit() throws Exception {
		return null;
	}

	public boolean isEditing() throws Exception {
		return false;
	}

	/**
   *
   */
	private String flowids = "";
	private String nodeids = "";
	private String resids = "";
	private String taskType = "pending";
	private int cflowDispType = 1;
	private String mdlUnitDcts;
	private int loadType;
	private String serviceKey;
	private String serverMethod;
	private String serverObj;

	public String getFlowids() {
		return flowids;
	}

	public String getNodeids() {
		return nodeids;
	}

	public String getResids() {
		return resids;
	}

	public void setFlowids(String flowids) {
		this.flowids = flowids;
	}

	public void setNodeids(String nodeids) {
		this.nodeids = nodeids;
	}

	public void setResids(String resids) {
		this.resids = resids;
	}

	public void dataSetChanged(DataSetEvent e) {
		// 处理游标改变事件
		if (e.getEventType() == DataSetEvent.CURSOR) {
			processDataSetCursorChangeEvent(e);
		}
	}

	/**
	 * 
	 * @param dataSetEvent  DataSetEvent
	 */
	protected void processDataSetCursorChangeEvent(DataSetEvent dataSetEvent) {
		EFDataSet dataSet = dataSetEvent.getDataSet();
		int oldRowIndex = dataSetEvent.getOldCursor();
		EFRowSet rowSet = dataSet.getRowSet(oldRowIndex);
		// ɾ��ɵ�rowSet��DataSet���¼�������
		if (rowSet != null) this.removeRowSetListener(rowSet);
		int newRowIndex = dataSetEvent.getNewCursor();
		rowSet = dataSet.getRowSet(newRowIndex);
		if (rowSet != null) this.addRowSetListener(rowSet);
	}

	/**
	 * 
	 * @param rowSet  EFRowSet
	 */
	private void removeRowSetListener(EFRowSet rowSet) {
		// String[] dsTypes = rowSet.getDSTypes();
		// if ( dsTypes == null || dsTypes.length == 0 ) return;
		// for(int i=0;i<dsTypes.length;i++) {
		// EFDataSet childDataSet = rowSet.getDataSet(dsTypes[i]);
		// if ( childDataSet != null ) removeDataSetListener(childDataSet);
		// }
	}

	/**
	 * 
	 * @param dataSet  EFDataSet
	 */
	protected void removeDataSetListener(EFDataSet dataSet) {
		dataSet.removeDataSetListener(this);
		EFRowSet rowSet = dataSet.getRowSet();
		if (rowSet == null)
			return;
		removeRowSetListener(rowSet);
	}

	/**
	 * 
	 * @param rowSet EFRowSet
	 */
	protected void addRowSetListener(EFRowSet rowSet) {
		// String[] dsTypes = rowSet.getDSTypes();
		// if ( dsTypes == null || dsTypes.length == 0 ) return;
		// for(int i=0;i<dsTypes.length;i++) {
		// EFDataSet childDataSet = rowSet.getDataSet(dsTypes[i]);
		// if ( childDataSet != null ) {
		// // �����¼�������
		// childDataSet.addDataSetListener(this);
		// // ����childDataSet
		// setActiveDataSet(childDataSet);
		// }
		// }
	}

	/**
	 * 
	 * @param dataSet  EFDataSet
	 */
	protected void setActiveDataSet(EFDataSet dataSet) {
		if (dataSet == null) return;
		if (dmComponentList == null || dmComponentList.size() == 0) return;
		DMComponent dmComponent = null;
		for (int i = 0; i < dmComponentList.size(); i++) {
			dmComponent = (DMComponent) dmComponentList.get(i);
			if (_FLOWDATASET_.equals(dmComponent.getDataSetID())) {
				dmComponent.setDataSet(dataSet);
			} else if (_NODEDATASET_.equals(dmComponent.getDataSetID())) {
				if (dataSet.getRowSet(0) != null) {
					dmComponent.setDataSet(dataSet.getRowSet(0).getDataSet(FlowTaskModel._NODEDATASET_));
				} else
					dmComponent.setDataSet(EFDataSet.getInstance(FlowTaskModel._NODEDATASET_));
			}
			// if ( dataSet.getTableName().equals(dmComponent.getDataSetID()) )
			// {
			// dmComponent.setDataSet(dataSet);
			// }
		}
		dataSet.activeDataSet();
	}

	public String getTaskType() {
		return taskType;
	}

	public int getCflowDispType() {

		return cflowDispType;
	}

	public String getMdlUnitDcts() {
		return mdlUnitDcts;
	}

	public int getLoadType() {
		return loadType;
	}

	public String getServiceKey() {

		return serviceKey;
	}

	public String getServerMethod() {
		return serverMethod;
	}

	public String getServerObj() {
		return serverObj;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public void setCflowDispType(int cflowDispType) {

		this.cflowDispType = cflowDispType;
	}

	public void setMdlUnitDcts(String mdlUnitDcts) {
		this.mdlUnitDcts = mdlUnitDcts;
	}

	public void setLoadType(int loadType) {
		this.loadType = loadType;
	}

	public void setServiceKey(String serviceKey) {

		this.serviceKey = serviceKey;
	}

	public void setServerMethod(String serverMethod) {
		this.serverMethod = serverMethod;
	}

	public void setServerObj(String serverObj) {
		this.serverObj = serverObj;
	}

	public void initScript(ScriptManager scriptManager) {
	}

	public void finishScript(ScriptManager scriptManager) {
	}

	public ScriptObject getScriptObject() {
		return null;
	}

	public Object getScriptKey() {
		return null;
	}

	public Object getScriptInstance() {
		return null;
	}

	public ScriptManager getScriptManager() {
		return null;
	}

	protected Object runScript(Scriptable script, String method, String[] mname, Object[] param) {
		try {
			return null;
		} catch (Exception ex) {
			ErrorManager.getDefault().notify(ex);
		}
		return null;
	}

	public boolean OpenTask(EFRowSet flowrowset, EFRowSet noderowset, EFRowSet taskrowset, Map nodePropery, Map formProperty) {
		Boolean o = (Boolean) runScript(this, "OpenTask", new String[] {"flowRowSet", "nodeRowSet", "taskRowSet", "nodeProp",
										"formProp" }, new Object[] { flowrowset, noderowset,
										taskrowset, nodePropery, formProperty });
		if (o != null) return o.booleanValue();
		return false;
	}

	public boolean OpenFlow(EFRowSet flowrowset, Map nodePropery, Map formProperty) {
		Boolean o = (Boolean) runScript(this, "OpenFlow", new String[] {"flowRowSet", "nodeProp", "formProp" }, new Object[] {flowrowset, nodePropery, formProperty });
		if (o != null) return o.booleanValue();
		return false;

	}

	public boolean OpenNode(EFRowSet flownode, EFRowSet noderowset, Map nodePropery, Map formProperty) {
		Boolean o = (Boolean) runScript(this, "OpenNode", new String[] { "flowRowSet", "nodeRowSet", "nodeProp", "formProp" }, new Object[] { flownode, noderowset, nodePropery, formProperty });
		if (o != null)
			return o.booleanValue();
		return false;

	}

	public void setLoadParamObject(JParamObject PO, String server) {
		runScript(this, "setLoadParamObject", new String[] { "PO", "eaiServer" }, new Object[] { PO, server });
	}
}
