package com.efounder.dctbuilder.event;

import com.borland.dx.dataset.*;
import com.borland.jb.util.*;
import com.core.xml.*;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dctbuilder.mdl.*;
import org.openide.*;
import com.efounder.dbc.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DataSetRowEventClass extends DataSetEventClass{
	public DataSetRowEventClass(DictModel m) {
        super(m);
        type = "row";
    }
	public void deleteError(EFDataSet dataSet) {
	}
	
	public void updateError(EFDataSet dataSet) {
	}
	public void addError(EFDataSet dataSet) {
	}
	
	public void editError(EFDataSet dataSet, Column column) {
	}

//	public void inserted(DataSet dataSet) {
//	}
//
//	public void inserting(DataSet dataSet) throws Exception {
////  System.out.println("inserting"+dataSet.getString("ZD_BH"));
//	}
//
//	public void modifying(DataSet dataSet) throws Exception {
////    System.out.println("modifying"+dataSet.getObject("ZD_BH").toString());
//	}
//
//	public void deleted(DataSet dataSet) {
//		try {
//			ClientEventProcess("deleted", null);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	public void deleting(DataSet dataSet) throws Exception {
//		ClientEventProcess( "deleting", null);
//	}
//
//	public void added(DataSet dataSet) {
//		try {
//			ClientEventProcess( "added", null);
//		} catch (Exception ex) {
//        ex.printStackTrace();
//		}
//	}
//
//	public void adding(DataSet dataSet, ReadWriteRow newRow) throws Exception {
//		//System.out.println("adding"+dataSet.getString("ZD_BH"));
//	}
//
//	public void updated(DataSet dataSet) {
//		//   System.out.println("updated"+dataSet.getString("ZD_BH"));
//	}
//
//	public void updating(DataSet dataSet, ReadWriteRow newRow, ReadRow oldRow) throws Exception {
//      // System.out.println("updating"+dataSet.getString("ZD_BH"));
//	}
//
//	public void canceling(DataSet dataSet) throws Exception {
// //  System.out.println("canceling");
//	}
//
//	public void postRow(DataChangeEvent event) throws Exception {
//   //System.out.println("postRow"+((DataSet)event.getSource()).getString("ZD_BH"));
//	}
//
//	public void dataChanged(DataChangeEvent event) {
//		StubObject so=new StubObject();
//		if(event.getID()==DataChangeEvent.ROW_DELETED){
//			DataSet ds=(DataSet)event.getSource();
//		}
//	}
}
