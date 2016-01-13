package com.pub.util;

import com.efounder.builder.base.data.EFDataSet;

public class EMPLimitConvertUtil {
	
	public static String getSqlLimitString(String sql, int currentPage,int numPerPage) {
		currentPage--;
        if (currentPage > 0) {   
        	sql = sql + " limit " + currentPage * numPerPage + "," + numPerPage; 
        } else {   
        	sql = sql + " limit " + (numPerPage);
        }
        System.out.println("convert Sql|||" + sql);
        return sql;
	} 
	
	public static EFDataSet getDataSetLimit(EFDataSet dataset, int currentPage,int numPerPage) {
		EFDataSet newDataset = EFDataSet.getInstance();
		int           maxRow = 0;
		int         startRow = 0;
		currentPage--;
		if (currentPage > 0) {   
        	if(currentPage * numPerPage > dataset.getRowCount())  return newDataset;
        	startRow = currentPage * numPerPage;
        	for(int i = startRow, j = 0; i < dataset.getRowCount() && i < ((currentPage + 1) * numPerPage) && j < numPerPage; i++, j++) {
        		newDataset.insertRowSet(dataset.getRowSet(i));
        	}
        } else {   
        	maxRow = (dataset.getRowCount() > numPerPage ? numPerPage : dataset.getRowCount());
        	for(int i = 0; i < maxRow; i++) {
        		newDataset.insertRowSet(dataset.getRowSet(i));
        	}
        }
		return newDataset;
	}
}
