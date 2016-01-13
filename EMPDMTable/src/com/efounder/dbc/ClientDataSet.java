package com.efounder.dbc;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.bz.dbform.datamodel.ColumnModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.data.DctContext;
import com.efounder.eai.data.JParamObject;
import com.efounder.service.meta.db.TableMetadata;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ClientDataSet extends EFDataSet {
	private           String               querySql = "";
	private           Object             userObject = null;
	private          boolean               midified;
	transient      Exception              exception = null;
	private     JParamObject                     PO = null;
	private      ColumnModel            columnModel = null;
	private  QueryDescriptor               querySet = null;
	private    AgentDatabase          agentDataBase = new AgentDatabase();
	protected         String    extendServerDataSet = null;
	protected      EFDataSet          deleteDataSet = EFDataSet.getInstance();
	
	public ClientDataSet() {
		super();
		this.PO = JParamObject.Create();
	}
	
	public ClientDataSet(JParamObject PO) {
		super();
		this.PO = PO;
	}
	
	public JParamObject getParamObject() {
		return this.PO;
	}
	
	public void setParamObject(JParamObject PO) {
		this.PO = PO;
	}
	
	public String getExtendServerDataSet() {
		return extendServerDataSet;
	}
	
	public void setExtendServerDataSet(String extendServerDataSet) {
		this.extendServerDataSet = extendServerDataSet;
	}
	
	public void setAgentDataBase(AgentDatabase agentDataBase) {
		this.agentDataBase = agentDataBase;
	}

	public AgentDatabase getAgentDataBase() {
		return agentDataBase;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public void setMidified(boolean midified) {
		this.midified = midified;
	}

	public Object getUserObject() {
		return userObject;
	}

	public boolean isMidified() {
		return midified;
	}

	public Exception getException() {
        return exception;
    }
	
	public void setQuerySql(String sql) {
		this.querySql = sql;
	}
	
	public String getQuerySql() {
		return this.querySql;
	}
	
	public void insertRow(boolean b) {
		EFRowSet rowset = EFRowSet.getInstance();
		this.addRowSet(rowset);
		this.goToRow(this.getRowCount() - 1);
	}
	
	public void deleteRow() {
		EFRowSet deleteRowSet = this.removeRowSet(this.currentRowIndex);
		
		if(deleteRowSet.getDataStatus() != EFRowSet._DATA_STATUS_INSERT_) {
			this.deleteDataSet.addRowSet(deleteRowSet);
		}
		
		if(this.getCurrentRowIndex() >= this.getRowCount()) {
			this.goToRow(this.getRowCount() - 1);
		}
	}
	
	public ColumnMetaData hasColumn(String colID) {
//		DctContext                  context = (DctContext) this.getUserObject();
//		TableMetadata         tableMetaData = context.getMetaData().getTableMetadata();
//		List                     columnList = tableMetaData.getColList();
//		ColumnMetaData       columnMetaData = null;
//		
//		for(int i = 0; i < columnList.size(); i++) {
//			columnMetaData = (ColumnMetaData) columnList.get(i);
//			if(columnMetaData.getColid().equals(colID)) return columnMetaData;
//		}
		Enumeration enumeration = this.getColumnModel().getColumns();
		Column aColumn;
	    while (enumeration.hasMoreElements()) {
	      aColumn = (Column)enumeration.nextElement();
	      if ( !(aColumn instanceof Column) ) return null;
	      if ( aColumn.getColumnMeataData().getString("COL_ID", "").equals(colID)) return aColumn.getColumnMeataData();
	      
	    }
	    return null;
	}
	
	public Column getColumn(String colID) {
		Enumeration enumeration = this.getColumnModel().getColumns();
		Column aColumn;
	    while (enumeration.hasMoreElements()) {
	      aColumn = (Column)enumeration.nextElement();
	      if ( !(aColumn instanceof Column) ) return null;
	      if ( aColumn.getColumnMeataData().getString("COL_ID", "").equals(colID)) return aColumn;
	      
	    }
	    return null;
	}
	
	public void loadData() throws Exception {
//		PO.SetValueByParamName("QuerySql", this.querySql);
//		EAI.DAL.IOM("ServiceObject", "queryDataSet", this.PO);
		if ( agentDataBase == null) return;
	    try{
//	    	this.setColumnModel(null);
	    	agentDataBase.loadDataSet(this);
	    }catch(Exception ce){
	      ce.printStackTrace();
	    }
	}

	public ColumnModel getColumnModel() {
		return columnModel;
	}

	public void setColumnModel(ColumnModel columnModel) {
		this.columnModel = columnModel;
	}
	
	public Column getColumn(int colIndex) {
		return this.columnModel.getColumnByModelIndex(colIndex);
	}
	
	public int getColumnCount() {
		return this.columnModel.getColumnCount();
	}
	
	public void BeforeDataLoad(ClientDataSetData cdss){

	}
	public void EndDataLoad(ClientDataSetData cdss) {
		setUserObject(cdss.getUserObject());
		setTableName(cdss.getTableName());
		this.setRowSetList(cdss.getDataSetData().getRowSetList());
		setMidified(false);
	}
	
	public Object saveData() throws Exception {
		if ( agentDataBase == null) return null;
		try{
			ClientDataSet dataset = clone(this);
			Object o = agentDataBase.saveDataSet(dataset);
			return o;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return null;
	}
	 
	public void BeforDataSave(ClientDataSetData cdss) {

	}
	public void EndDataSave(Object o) {
	      setMidified(false);
	      for(int i = 0; i < this.getRowCount(); i++) {
	    	  this.getRowSet(i).setDataStatus(EFRowSet._DATA_STATUS_NORMAL_);
	      }
	}
	
	public QueryDescriptor getQuerySet() {
		return querySet;
	}

	public void setQuerySet(QueryDescriptor qs) {
		querySet = qs;
	}

	public EFDataSet getDeleteDataSet() {
		return deleteDataSet;
	}

	public void setDeleteDataSet(EFDataSet deleteDataSet) {
		this.deleteDataSet = deleteDataSet;
	}
	
	public ClientDataSet clone(ClientDataSet sourceDataset) {
		ClientDataSet          desDataSet = new ClientDataSet();
		EFRowSet                desRowset = null;
		EFDataSet        desDeleteDataSet = EFDataSet.getInstance();
		EFRowSet             sourceRowset = null;
		EFDataSet     sourceDeleteDataSet = sourceDataset.deleteDataSet;
		
		desDataSet.setExtendServerDataSet(sourceDataset.getExtendServerDataSet());
		
		for(int i = 0; i < sourceDataset.getRowCount(); i++) {
			sourceRowset = sourceDataset.getRowSet(i);
			desRowset = EFRowSet.getInstance();
			Iterator it = sourceRowset.getDataMap().entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry m = (Entry) it.next();
				desRowset.put(m.getKey(), m.getValue());
			}
			desDataSet.addRowSet(desRowset);
			desRowset.setDataStatus(sourceRowset.getDataStatus());
		}
		
		for(int i = 0; i < sourceDeleteDataSet.getRowCount(); i++) {
			sourceRowset = sourceDeleteDataSet.getRowSet(i);
			desRowset = EFRowSet.getInstance();
			Iterator it = sourceRowset.getDataMap().entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry m = (Entry) it.next();
				desRowset.put(m.getKey(), m.getValue());
			}
			desRowset = (EFRowSet) sourceRowset.clone();
			desDeleteDataSet.addRowSet(desRowset);
			desRowset.setDataStatus(sourceRowset.getDataStatus());
		}
		desDataSet.setTableName(sourceDataset.getTableName());
		desDeleteDataSet.setTableName(sourceDeleteDataSet.getTableName());
		
		desDataSet.setDeleteDataSet(desDeleteDataSet);
		desDataSet.setUserObject(sourceDataset.getUserObject());
		desDataSet.setMidified(sourceDataset.midified);
		desDataSet.exception = sourceDataset.exception;
		desDataSet.PO = sourceDataset.PO;
//		desDataSet.setColumnModel(sourceDataset.getColumnModel());
		desDataSet.setQuerySet(sourceDataset.getQuerySet());
		desDataSet.setAgentDataBase(sourceDataset.getAgentDataBase());
		
		
		ColumnModel      colModel = new ColumnModel();
  		Column        tableColumn = new Column();
  		  		
  		for(int i = 0; sourceDataset.getColumnModel() != null && i < sourceDataset.getColumnModel().getColumnCount(); i++) {
  			tableColumn = new Column();
  			tableColumn.setColumnMeataData(sourceDataset.getColumnModel().getColumnByModelIndex(i).getColumnMeataData());
  			colModel.addColumn(tableColumn);
  		}
  		desDataSet.setColumnModel(colModel);
		
		return desDataSet;
	}
}
