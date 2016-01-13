package com.efounder.dbc.swing.exttreetable;

import com.efounder.dbc.swing.context.TreeInfoContext;
import com.core.xml.StubObject;
import com.efounder.dbc.swing.DictDataSet;
import java.util.Hashtable;
import com.efounder.dbc.swing.tree.DataSetTreeNode;
import java.util.List;
import com.borland.dx.dataset.DataSet;
import javax.swing.tree.*;
import com.efounder.dbc.swing.tree.DatasetTreeModel;
import com.efounder.comp.treetable.TreeTableModel;
import javax.swing.event.EventListenerList;
import java.util.Map;
import java.util.HashMap;

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
public class DictTreeTableModelExt extends DatasetTreeModel implements TreeTableModel  {
    protected String colIndex[];//列，对应的列名
    private Map ColumnClass=new HashMap();
    protected EventListenerList listenerList = new EventListenerList();

    public DictTreeTableModelExt(TreeNode cotext) {
        super(cotext);
    }


    public void setColumnClass(String name,Object obj){
         ColumnClass.put(name,obj);
 }
    /**
     * 外键列,要显示中文
     * @param colId String
     * @param dr StubObject
     * @param ds ClientDataSet
     */
    protected void setColHash(StubObject dr, List colList) {
        String isFKey = dr.getString("COL_ISFKEY", "0");
        if (isFKey.equals("1")) { //是外键
            String fKeyObj = dr.getString("COL_FOBJ", "");
            if (fKeyObj == null || "".equals(fKeyObj)) {
                return;
            }
            Hashtable hash = getDictHash(fKeyObj);
            dr.setObject(fKeyObj + "_HASH", hash);
            colList.add(dr.getString("COL_ID", ""));
        }
    }

    protected void addColList(DictDataSet dds, List colList) {
        //关键列
        DataSet dictInfo = dds.getdictInfo();
        String bhColId = (String) dictInfo.getString("DCT_BMCOLID");
        colList.add(bhColId);
        String mcColId = (String) dictInfo.getString("DCT_MCCOLID");
        colList.add(mcColId);
        //js mx
//        String jsColId = (String) dictInfo.getString("DCT_JSCOLID");
//        colList.add(jsColId);
//        String mxColId = (String) dictInfo.getString("DCT_MXCOLID");
//        colList.add(mxColId);

        //外键列: 显示编码,名称
//        String name ;
//        Hashtable colinfo=dds.getDictUseColInfo();
//        for(int i=0;i<dds.getColumnCount();i++){
//          name=dds.getColumn(i).getColumnName();
//          if(colinfo.get(name)!=null){
//            StubObject dr=(StubObject)colinfo.get(name);
//            setColHash(dr,colList);
//          }
//        }
    }

    protected DictDataSet getDictData(String fkeyId) {
        try {
            DictDataSet fkeyObjDataSet = new DictDataSet();
            fkeyObjDataSet.setDictName(fkeyId);
            fkeyObjDataSet.loadData();
            return fkeyObjDataSet;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    protected Hashtable getDictHash(String fkeyId) {
        Hashtable hash = new Hashtable();
        DictDataSet dataSet = getDictData(fkeyId);
        if (dataSet != null && dataSet.getRowCount() > 0) {
            dataSet.first();
            do {
                String key = dataSet.getString(dataSet.getBhCol());
                String val = dataSet.getString(dataSet.getMcCol());
                hash.put(key, val);
            } while (dataSet.next());
        }
        return hash;
    }

    public Object getValueAt(Object node, int col) {
        DataSetTreeNode tn = (DataSetTreeNode) node;
        if (dataSet.getString(0).indexOf("ROOT") != -1) {
        }
        long row = tn.getInternalRow();
        if (dataSet.getInternalRow() != row) {
            dataSet.goToInternalRow(row);
        }
        String column = colIndex[col];
        StubObject cdr = (StubObject) ((DictDataSet) dataSet).getDictUseColInfo(
                column);
        String isFkey = cdr.getString("COL_ISFKEY", "0");
        if (isFkey.equals("1")) {
            String fKeyObj = cdr.getString("COL_FOBJ", "");
            Hashtable hash = (Hashtable) cdr.getObject(fKeyObj + "_HASH", null);
            String bh = dataSet.getString(column);
            String val = (String) hash.get(bh);
            if (val == null) {
                val = "";
            }
            return new DataItemExt(bh, val);
        } else {
            if (cdr.getString("COL_TYPE", "").equals("C")) {
                return dataSet.getString(column);
            }
            if (cdr.getString("COL_TYPE", "").equals("D")) {
                return dataSet.getDate(column);
            }
            if (cdr.getString("COL_TYPE", "").equals("N")) {
                return dataSet.getBigDecimal(column);
            }
            if (cdr.getString("COL_TYPE", "").equals("I")) {
                return dataSet.getBigDecimal(column);
            }
        }
        return "";
    }


    public boolean isCellEditable(Object o, int col) {
        return false;
    }

    public String getNodeClass() {
        return "com.efounder.dbc.swing.exttreetable.TreeTableNodeExt";
    }

    public int getColumnCount() {
        return 0;
    }

    public String getColumnName(int column) {
        return "";
    }

    public Class getColumnClass(int column) {
        return null;
    }

    public void setValueAt(Object aValue, Object node, int column) {
    }

}
