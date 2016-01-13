package com.efounder.dbc;

import java.io.*;

import com.borland.dx.dataset.*;

/**
 * Wrapper class for DataSetData and Servlet parameters
 * for Database connection
 */

public class ClientDataSetData implements Serializable {
	private            Object             userObject;
	private           boolean            blobDataSet;
	protected QueryDescriptor               querySet = null;
	protected          String    extendServerDataSet = null;
	// 数据
	private     ClientDataSet            dataSetData = null;
	// 表名
	private            String              tableName = null;
	
	public ClientDataSetData(ClientDataSet cds) {
		this.dataSetData = cds;
		this.setTableName(cds.getTableName());
		this.setQuerySet(cds.getQuerySet());
		this.setExtendServerDataSet(cds.getExtendServerDataSet());
		this.setUserObject(cds.getUserObject());
	}
	
	public String getTableName() {
		return tableName;
	}

	public String getExtendServerDataSet() {
		return extendServerDataSet;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setExtendServerDataSet(String extendServerDataSet) {
		this.extendServerDataSet = extendServerDataSet;
	}

	public void setDataSetData(ClientDataSet dsd) {
		this.dataSetData = dsd;
	}
	public void setQuerySet(QueryDescriptor querySet) {
		this.querySet = querySet;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

    public void setHasBlobData(boolean b) {
        this.blobDataSet = b;
    }

    public ClientDataSet getDataSetData() {
    	return this.dataSetData;
    }

    public QueryDescriptor getQuerySet() {
    	return querySet;
    }
    public Object getUserObject() {
    	return userObject;
    }
    public boolean hasBlobData() {
    	return blobDataSet;
    }
}
