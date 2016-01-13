package com.efounder.bz.dbform.component.dc.table;

import javax.swing.DefaultRowSorter;
import javax.swing.table.TableModel;

public class DictRowSorter extends DefaultRowSorter{
	
	TableModel dictModel = null;
	
	public DictRowSorter(TableModel dictModel) {
		this.dictModel = dictModel;
	}
	
	/**
     * {@inheritDoc}
     */
    public int getViewRowCount() {
        if (this.dictModel != null) {        	
        	return dictModel.getRowCount();
        }
        return 0;
    }
    
    /**
     * {@inheritDoc}
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public int convertRowIndexToModel(int index) {
//        if (viewToModel == null) {
//            if (index < 0 || index >= getModelWrapper().getRowCount()) {
//                throw new IndexOutOfBoundsException("Invalid index");
//            }
//            return index;
//        }
//        return viewToModel[index].modelIndex;
    	return 0;
    }
}
