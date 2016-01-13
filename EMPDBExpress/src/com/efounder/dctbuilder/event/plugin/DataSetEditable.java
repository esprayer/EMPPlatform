package com.efounder.dctbuilder.event.plugin;

import java.io.Serializable;

import com.borland.dx.dataset.RowStatus;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.dbc.swing.table.ICellEditable;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.pub.util.ESPKeyValueUtil;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class DataSetEditable implements ICellEditable, Serializable {

    public DataSetEditable() {
    }

    public boolean isCellEditable(Object o, int row, int col) {
    	
    	if (col < 0 || row < 0) return false;
    	
    	DictModel 		dm = (DictModel) o;
    	Column		column = null;
    	String 		 bmcol = null;
        ColumnMetaData cmd = null;        
       

        //如果菜单上定义了不执行该插件
        String DataSetEditable = "1";
        if (dm.getNodeStub() != null)
            DataSetEditable = dm.getNodeStub().getString("DataSetEditable", "1");
        if("0".equals(DataSetEditable)){
        	return true;
        }
        column = dm.getColModel().getColumnByModelIndex(col);
        cmd = (ColumnMetaData) column.getColumnMeataData();   
//        BaseDBTableModel model = (BaseDBTableModel) ( (DictView) dm.getView()).getTable().getModel();
//        if (model.getColumn(col) == null) return false;
//        String column = model.getColumn(col).getColumnName();
        column = dm.getColModel().getColumnByModelIndex(col);
        String colid = column.getColumnMeataData().getColid();
//        if ("MDPUBLISH_SELECT".equals(column)) return true;
        if (dm.getCdsParam().isReadOnly()) {
            return false;
        }
        
        //元数据设置了不可编辑
        if(!cmd.isEditable()) {
        	return false;
        }
        //创建日期、修改日期不能编辑
        if ("F_CRDATE".equals(colid) || "F_CHDATE".equals(colid)){
            return false;
        }
        
        bmcol = dm.getMetaData().getDctBmCol();
        
        // 新增的数据，视其定义情况而定是否可以修改
        if (isEditAtJTable() && dm.getDataSet().getRowSet(row).getDataStatus() == RowStatus.INSERTED) {
            if (cmd == null) return true;
            return isEditable(cmd, dm.getDataSet().getRowSet(row).getDataStatus());
        } else if (bmcol.equals(column) || dm.getMetaData().getKeyColumnName().contains(column)) {
            return false;
        }
//        int bmcolindex = ((DictView) dm.getView()).getTable().get.getBmCol();
//        if (bmcolindex == -1) {
//            if (cmd == null) return true;
////            return cmd.isEditable();
//            return isEditable(cmd, dm.getDataSet().getRowSet(row).getDataStatus());
//        }
//        String bm = (String) model.getValueAt(row, bmcolindex);
        //是否有写权限
//        if (!dm.writeAble(bm)) return false;
        if (cmd == null) return true;
//        return cmd.isEditable();
        return isEditable(cmd, dm.getDataSet().getRowSet(row).getDataStatus());
    }

    protected boolean isEditable(ColumnMetaData cmd, int status) {
    	String readonly = "";
        if(cmd.isEditable()){
        	readonly = "0";
        }else{
        	readonly = "1";
        }
        String srccont = cmd.getString("F_SRCCONT", "");
        if(srccont != null && srccont.length()>0){
        	srccont = srccont.toUpperCase();
        }
        java.util.Map<String, String> keyValueMap = ESPKeyValueUtil.getKeyValue(srccont);
        if (keyValueMap.get("ARD") != null){
        	readonly = (String)keyValueMap.get("ARD");
        }
        else if (keyValueMap.get("RD") != null){
        	if("1".equals(keyValueMap.get("RD"))){
	        	if(status == RowStatus.INSERTED){
	        		readonly = "0";
	        	}else{
	        		readonly = "1";
	        	}
        	}
        }
    	return !"1".equals(readonly);
    }

    /**
     *
     * @return boolean
     */
    protected boolean isEditAtJTable() {
        StackTraceElement[] es = Thread.currentThread().getStackTrace();
        for (int i = 0; es != null && i < es.length; i++) {
            if (es[i].getMethodName().indexOf("editCell") >= 0
                || es[i].getMethodName().indexOf("onPaste") >= 0)
                return true;
        }
        return false;
    }

}
