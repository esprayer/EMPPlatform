package com.efounder.dbc.swing.render;

import java.util.Map;

import com.efounder.buffer.DictDataBuffer;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import java.util.HashMap;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.EFDataSet;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import com.efounder.dctbuilder.util.DictUtil;
import javax.swing.JTable;
import com.efounder.builder.meta.MetaDataManager;
import java.util.List;
import com.efounder.eai.data.JParamObject;

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

public class NameCellRenderer extends DefaultTableCellRenderer{

    /**
     *
     */
    private String fobj;
    private String userObject;

    Map codeNameMap = null;
    DCTMetaData dmd = null;

    private int row;

    /**
     *
     * @param key String
     */
    public NameCellRenderer(String key) {
        fobj = key;
        initMetaData();
    }


    /**
     *
     * @param tmp String
     */
    public void setUserObject(String tmp){
        userObject = tmp;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    /**
     *
     * @return String
     */
    public String getUserObject(){
        return this.userObject;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public JTable getTable() {
        return table;
    }

    private int column;
    private JTable table;



    protected void initMetaData(){
        try {
           if(fobj == null || "".equals(fobj.trim())){
              codeNameMap = new HashMap();
              return ;
           }
           dmd = (DCTMetaData) MetaDataManager.getDCTDataManager().getMetaData(fobj);

           EFDataSet efDataSet = (EFDataSet) DictDataBuffer.getDefault().getDataItem(DictDataBuffer.DCTDATASET, fobj);
           if(efDataSet == null)efDataSet = DictDataBuffer.getDefault().QueryData(JParamObject.Create(),fobj,null,null);
           codeNameMap  = makeMap(efDataSet,dmd);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    protected Map makeMap(EFDataSet efDataSet,DCTMetaData dmd){
        if(efDataSet == null) return new HashMap();
        List dataList = efDataSet.getRowSetList();
        if(dataList == null || dataList.size() == 0) return new HashMap();
        Map map = new HashMap();
        for(int i = 0; i < dataList.size(); i++){
            EFRowSet rowSet = (EFRowSet)dataList.get(i);
            map.put(rowSet.getString(dmd.getDCT_BMCOLID(),""),rowSet.getString(dmd.getDCT_MCCOLID(),""));
        }
        return map;
    }


    /**
     *
     * @param value Object
     */
    protected void setValue(Object value) {
        if (value == null) value = "";
        String bh = value.toString().trim();
        String mc = "";
        if(codeNameMap != null){
            mc = (String)codeNameMap.get(bh);
        }

        if(mc == null) mc = "";
        String text = mc;
        setText(text);
        setToolTipText(bh + " " + mc);
    }

    /**
     * 初始化字典元数据，主要获取编号、名称列
     */
    protected DCTMetaData getDCTMetaData() {
        try {
            if (fobj == null || fobj.trim().length() == 0) return null;
            return DictUtil.getDCTMetaData(fobj, "");
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     *
     * @param table JTable
     * @param value Object
     * @param isSelected boolean
     * @param hasFocus boolean
     * @param row int
     * @param column int
     * @return Component
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setRow(row);
        this.setColumn(column);
        this.setTable(table);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }

}
