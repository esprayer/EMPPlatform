package com.help.data;

import javax.swing.event.*;
import javax.swing.table.*;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import java.util.*;
import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.domodel.SYS_OBJCOLS;
import com.efounder.builder.meta.dctmodel.SYS_DICTS;
import com.efounder.eai.data.*;

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
public class HelpTableModel implements TableModel {
    protected DCTMetaData dmd;
    protected List colList; 
    protected EFDataSet dispDataSet,allDataSet;
    
    protected HelpTableModel() {
    }
    
    public static HelpTableModel getInstance(DCTMetaData dmd){
        return getInstance(dmd, null);
    }
    
    public static HelpTableModel getInstance(DCTMetaData dmd, String[] helpColumns){
        HelpTableModel htm=new HelpTableModel();
        htm.setMetaData(dmd, helpColumns);
        return htm;
    }
    
    protected void setMetaData(DCTMetaData dmd){
    	setMetaData(dmd, null);
    }
    
    protected void setMetaData(DCTMetaData dmd, String[] helpColumns){
        this.dmd=dmd;
        EFDataSet eds=dmd.getDoMetaData().getDOColumns();

        
        //------ 获取帮助列  modify by zhangft start 20130607 --------------
        List colNameList = new ArrayList();
        //编码列名称列一定显示
        colNameList.add(dmd.getDCT_BMCOLID());
        colNameList.add(dmd.getDCT_MCCOLID());
        //加上外键字典扩展属性中定义的帮助列
        String unitid = "";
    	String helpcol = dmd.getSYS_DCT_CST(unitid,"HELPCOL","");
    	String[] helpcols=null;
        if(helpcol != null && helpcol.length() > 0){
        	helpcols = helpcol.split(",");
        }
        if(helpcols!= null && helpcols.length > 0){
			for (int i = 0; i < helpcols.length; i++) {
				if (helpcols[i] == null || helpcols[i].trim().length() == 0
						|| colNameList.contains(helpcols[i])) {
					continue;
				}
				colNameList.add(helpcols[i]);
			}
    	}
		// 如果有自指定列，则加上自指定列
		if (helpColumns != null && helpColumns.length > 0) {
			for (int i = 0; i < helpColumns.length; i++) {
				if (helpColumns[i] == null || helpColumns[i].trim().length() == 0
						|| colNameList.contains(helpColumns[i])) {
					continue;
				}
				colNameList.add(helpColumns[i]);
			}
		}
		
        //组织列的元数据信息
        colList=new ArrayList();
        for(int i=0;i<colNameList.size();i++){
            EFRowSet ers=(EFRowSet) eds.getRowSet(colNameList.get(i).toString().trim());
            if(ers==null) continue;
            colList.add(ers);
        }
        //------ 获取帮助列  modify by zhangft end 20130607 --------------
    }
    
    public String getCodeColumn(){
        return dmd.getDCT_BMCOLID();
    }
    public String getNameColumn(){
        return dmd.getDCT_MCCOLID();
    }
    public EFDataSet getDispDataSet(){
        return dispDataSet;
    }
    
    public EFDataSet getAllDataSet(){
      return allDataSet;
    }
    
    public void setAllDataSet(EFDataSet data){
         allDataSet=data;
    }
    
    public void setDispDataSet(EFDataSet data){
        dispDataSet=data;
    }
    public void addTableModelListener(TableModelListener l) {
    }
    public Class getColumnClass(int columnIndex) {
        return Object.class;
    }
    public java.util.List  getColumnList(){
        return colList;
    }
    public int getColumnCount() {
        return colList.size();
    }
    public String getColumnName(int col) {
      if(colList==null||col>=colList.size())return "";
        EFRowSet ers=(EFRowSet) colList.get(col);
        return ers==null?"":ers.getString(SYS_OBJCOLS._COL_MC_,"");
    }
    public int getRowCount() {
        return dispDataSet==null?0:dispDataSet.getRowCount();
    }
    public Object getRowData(int row){
        if(dispDataSet==null)return null;
        if(row<dispDataSet.getRowCount())return dispDataSet.getRowSet(row);
        return null;
    }
    public Object getValueAt(int row, int col) {
        EFRowSet crs=(EFRowSet) colList.get(col);
        EFRowSet data=(EFRowSet) dispDataSet.getRowSet(row);
        return data.getObject(crs.getString(SYS_OBJCOLS._COL_ID_,""),null);
    }
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void removeTableModelListener(TableModelListener l) {
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }
}
