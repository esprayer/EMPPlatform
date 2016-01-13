package com.efounder.bz.task.model;

import com.efounder.builder.base.data.*;

import com.efounder.builder.meta.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.eai.data.*;

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
public class FlowEndTaskModel extends FlowTaskModel/* DataComponentAdapter implements DataSetComponent,DBComponent,DataSetListener */{

	public FlowEndTaskModel() {
	}

	/**
	 * 
	 * @param obj_id   String
	 * @return DOMetaData[]
	 */
	public DOMetaData[] getDOMetaData(String obj_id) {
		try {
			return new DOMetaData[] { (DOMetaData) MetaDataManager.getDODataManager().getMetaData("FLOW_TASK_END_LIST") };
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Object load(Object param) throws Exception {

		JParamObject PO = (JParamObject) param;
		JResponseObject RO = (JResponseObject) com.efounder.eai.EAI.DAL.IOM("TaskIOService", "loadEndFlowTaskInfo", PO);
		EFDataSet eds = (EFDataSet) RO.getResponseObject();
		// setIcon(eds);
		this.setActiveDataSet(eds);
		return null;
	}

	public JParamObject getParamObject() {
		JParamObject po = JParamObject.Create();
		po.SetValueByParamName("FLOW_ID", flowids);
		return po;
	}

	public Object load() throws Exception {
		JParamObject po = getParamObject();
		return load(po);
	}

	/**
   *
   */
	private String flowids = "";

	public String getFlowids() {
		return flowids;
	}

	public void setFlowids(String flowids) {
		this.flowids = flowids;
	}

	/**
	 * 
	 * @param rowSet
	 *            EFRowSet
	 */
	private void removeRowSetListener(EFRowSet rowSet) {
//		String[] dsTypes = rowSet.getDSTypes();
//		if (dsTypes == null || dsTypes.length == 0) return;
//		for (int i = 0; i < dsTypes.length; i++) {
//			EFDataSet childDataSet = rowSet.getDataSet(dsTypes[i]);
//			if (childDataSet != null)
//				removeDataSetListener(childDataSet);
//		}
	}
}
