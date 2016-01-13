package com.efounder.dctbuilder.view;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.component.dc.table.DictTable;
import com.efounder.bz.dbform.component.dc.table.footer.TableGroupColumnFooter;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dbc.swing.table.ICellEditable;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.efounder.dctbuilder.data.DictMetaDataEx;
import com.efounder.dctbuilder.util.*;
import org.openide.*;

/**
 *
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
public class DictView extends JPanel implements MouseListener, ChangeListener, ICellEditable {

    protected BorderLayout          borderLayout1 = new BorderLayout();
    protected DictModel                 dictModel = null;
    protected String                    actionKey = "";
    protected JScrollPane                     jsp = new JScrollPane();
    protected DictTable                     table = null;
    protected Object                 actionObject;
    protected String                    filtercol = null, filterbjf, filtervalue;
    protected Map                   userObjectMap = new HashMap();
    // 列脚
    DictFooterModel                    fixedModel = null;
    TableGroupColumnFooter             fixedTable = null;
    JViewport                            viewport = null;
    protected ESPLangStub         currentLangStub;
    // 编辑字典时，最大命中数量，如果是0则命中所有数据
    protected int                  manageMaxCount;
    // 编辑字典时的筛选条件
    protected String                  filterWhere;


    /**
     *
     */
    public DictView() {
        try {
        	dictModel = new DictModel(this);
        	table = new DictTable(dictModel);
            jbInit();
            setDictModel(dictModel);
            dictModel.setView(this);
            getTable().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @throws Exception
     */
    protected void jbInit() throws Exception {
//    	this.setLayout(new BorderLayout());
//    	this.add(jsp, BorderLayout.CENTER);
//    	jsp.setViewportView(table);
    }

    /**
     *
     * @param where String
     */
    public void setFilterWhere(String where) {
        this.filterWhere = where;
    }

    public String getFilterWhere() {
        return filterWhere;
    }

    public DictTable getTable() {
        return table;
    }

    public String getActionKey() {
        return actionKey;
    }

    public Object getActionObject() {
        return actionObject;
    }

    public DictModel getDictModel() {
        return dictModel;
    }

    public void paintDict() {
//        try {
//            if (tablePainter != null) {
//                tablePainter.paintDict();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     *
     * @param actionKey String
     */
    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }

    /**
     *
     * @param actionObject Object
     */
    public void setActionObject(Object actionObject) {
        this.actionObject = actionObject;
    }

    /**
     *
     * @param userPluginKey String
     */
    public void setUserPluginKey(String userPluginKey) {
        this.getDictModel().setUserPluginKey(userPluginKey);
    }

    /**
     *
     * @param dctModel DictModel
     */
    public void setDictModel(DictModel dctModel) {
        this.dictModel = dctModel;
        dctModel.setView(this);
//        dctModel.addChangeListener(this); // model里改变东东时，需要在这里statechanged处理
    }

    /**
     *
     */
    public void processMetaDataChanged(DictMetaDataEx dmd) {
        setColumnSortor(dmd);
    }

    /**
     *
     */
    protected void setColumnSortor(DictMetaDataEx dmd) {
        if (dmd == null) return;
        String sort = dmd.getExtAttribute("", "ColumnSort");
//        this.table.setColumnSortEnabled("1".equals(sort));
    }

    /**
     *
     * @return Action[]
     */
    public Action[] getContextAction() {
//        JNodeStub node = dictModel.getNodeStub();
//        if (node != null && "1".equals(node.getNodeStubObject().getString("hideContextAction", "0")))
//            return null;
//        return ActionManager.getContextActions(actionKey, actionObject, dictModel);
    	return null;
    }

    /**
     *
     * @param e MouseEvent
     */
    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
//        ActionGroup popupMenu = getPopupActionGroup();
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            Point p = e.getPoint();
//            showPopupmenu(p, (java.awt.Component) e.getSource(), popupMenu);
        }
        if (e.getButton() == e.BUTTON1) {
            currentRow = table.getSelectedRow();
        }
    }

    /**
     *
     */
    protected int currentRow = 0;

    /**
     *
     * @return int
     */
    public int getCurrentRow() {
        return currentRow;
    }

    /**
     *
     * @param p
     *            Point
     * @param invoker
     *            Component
     * @param popupMenu
     *            ActionGroup
     */
//    protected void showPopupmenu(Point p, java.awt.Component invoker, ActionGroup popupMenu) {
//        if (popupMenu != null) {
//            ActionPopupMenu actionPopupMenu = new ActionPopupMenu(invoker,
//                    popupMenu, true);
//            actionPopupMenu.show(invoker, p.x, p.y);
//        }
//    }

    public void mouseExited(MouseEvent e) {
    }

    // 要将过滤去掉，否则gotointerrow对于有些未在过滤之列的行不起作用
    public Object save() throws Exception {
        // 停止编辑
        table.stopEditing();
        Object saveObject = null;
//        RowFilterListener rf = dictModel.getDataSet().getRowFilterListener();
        try {
//            if (rf != null) {
//                dictModel.getDataSet().removeRowFilterListener(rf);
//                dictModel.getDataSet().dropIndex();
//            }
            saveObject = dictModel.saveData();
            this.updateUI();
            return saveObject;
        } catch (Exception e) {
            throw e;
        } finally {
//            if (rf != null) {
//                dictModel.getDataSet().addRowFilterListener(rf);
//                dictModel.getDataSet().refilter();
//            }
        }
    }

    public void refrensh() {
    }

    public void delRow() {
    }

    public void delAllRow() {
    }

    public void addRow() {
    }

    public void addRow(boolean post) {

    }

    /**
     *
     * @return boolean
     */
    protected boolean isAddCopyPrevious() {
        if (getDictModel() == null)
            return true;
        if (getDictModel().getMetaData() == null)
            return true;
        String copy = getDictModel().getMetaData().getExtAttribute("",
                "AddCopyPrevious");
        return "1".equals(copy);
    }

//    /**
//     *
//     * @param dr DataRow
//     * @param parent DataSetTreeNode
//     */
//    public void setNewRowValue(DataRow dr, DataSetTreeNode parent) {
//        ClientDataSet cds = getDictModel().getDataSet();
//        String bmcol = getDictModel().getMetaData().getDctBmCol();
//        String jscol = getDictModel().getMetaData().getDctJsCol();
//        String mxcol = getDictModel().getMetaData().getDctMxCol();
//        String ptcol = getDictModel().getMetaData().getDctParentCol();
//        String pt = MetaDataConstants.DCT_ROOT;
//        int js = 1;
//        if (parent != null) {
//            pt = parent.getDctBH();
//            js = parent.getIntJs() + 1;
//        }
//        // 说明不是复制的需要设置一些属性
//        if (dr == null) {
//            if (!"".equals(ptcol)) {
//                cds.setString(ptcol, pt);
//            }
//            // 改进以支持字符型的级数
//            // modified by gengeng 2009-10-14
//            if (!"".equals(jscol)) {
//                DictUtil.setVariantValue(cds, jscol, String.valueOf(js));
//            }
//        } else {
//            int count = cds.getColumnCount();
//            java.util.List keyColumn = getDictModel().getMetaData()
//                                       .getKeyColumnName();
//            for (int col = 0; col < count; col++) {
//                if (cds.getColumn(col).getColumnName().equals(bmcol))
//                    continue;
//                if (cds.getColumn(col).getColumnName().equals("ZD_BH_OLD"))
//                    continue;
//                if (keyColumn != null
//                    && keyColumn.contains(cds.getColumn(col)
//                                          .getColumnName()))
//                    continue;
//                // 复制上一行的值，有些值还是不要复制的好
//                Variant variant = new Variant();
//                dr.getVariant(col, variant);
//                cds.setVariant(col, variant);
//            }
//        }
//        if (!"".equals(mxcol)) {
//            // 改进以支持数值型的明细
//            // modified by gengeng 2009-10-14
//            DictUtil.setVariantValue(cds, mxcol, String.valueOf("1"));
//        }
//        if (cds.hasColumn("F_STAU") != null) {
//            cds.setBigDecimal("F_STAU", new BigDecimal(0));
//        }
//        try{
//	        if (cds.hasColumn("F_CRDATE") != null) {
//	            cds.setTimestamp("F_CRDATE", System.currentTimeMillis());
//	        }
//	        if (cds.hasColumn("F_CHDATE") != null) {
//	            cds.setTimestamp("F_CHDATE", System.currentTimeMillis());
//	        }
//        }catch(Exception ee){
//        	
//        }
//
//        // 这里不要POST，过滤的时候可能就POST没了
//        // cds.post();
//    }

    /**
     *
     * @param o Object
     * @param row int
     * @param col int
     * @return boolean
     */
    public boolean isCellEditable(Object o, int row, int col) {
        return dictModel.isCellEditable( row, col);
    }

    /**
     *
     * @return boolean
     */
    public boolean isModified() {
        return getDictModel().getDataSet().isMidified();
    }

    /**
     *
     */
    public void stopEditing() {
        if (this.table != null)
            table.stopEditing();
    }

    /**
     *
     * @return Object
     */
    public Object getScriptKey() {
        return "BIZDict";
    }

    public void setUserObject(Object key, Object value) {
    	this.userObjectMap = userObjectMap;
    	if(userObjectMap==null)userObjectMap = new HashMap();
        if (value == null)
        	userObjectMap.remove(key);
        else
        	userObjectMap.put(key, value);
    }

	public Object getUserObject(Object key, Object def) {
		if (userObjectMap == null)
			return def;
		Object value = userObjectMap.get(key);
		if (value == null)
			value = def;
		return value;
	}

    // 设置列脚
    public void setColumnFooter() {
        // 设置是否显示列脚
//        String showFooter = getDictModel().getMetaData().getExtAttribute("", "showFooter");
//        if (!"1".equals(showFooter)) {
//            tabletsp.setColumnFooterView(null);
//            return;
//        }
//        fixedModel.setColCount(table.getColumnCount());
////		fixedModel.setRowCount(1);
//        fixedTable.setDefaultRenderer(Object.class, new FooterNumberRender(fixedTable, null)); //设置渲染
//        fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        fixedTable.setModel(fixedModel);
//        fixedTable.setColumnModel(table.getColumnModel());
////		fixedTable.setUI(new TableGroupColumnFooterUI());
//        viewport.setPreferredSize(fixedTable.getPreferredSize());
//        if (fixedModel.getRowCount() > 0 && fixedModel.getColumnCount() > 0)
//            fixedModel.setValueAt("合计：", 0, 0);
//        heji();
    }

    //合计
    public void heji() {
        java.util.List list = getDictModel().getMetaData().getVisibleColList();
        ColumnMetaData meta = null;
        long t1 = System.currentTimeMillis();
        for (int i = 0; list != null && i < list.size(); i++) {
            meta = (ColumnMetaData) list.get(i);
            hejiONE(meta, i);
        }
        long t2 = System.currentTimeMillis();
        System.out.println("count use:" + (t2-t1));
//        //载入全部的列
//        for (int i = 1; i < table.getColumnCount(); i++) {
//            hejiONE(i);
//        }
    }

    /**
     *
     * @param columnindex int
     */
    public void hejiONE(ColumnMetaData cmd, int index) {
        // 只对数据列进行合计
//        if (!"N".equals(cmd.getColType()) && !"I".equals(cmd.getColType()))
//            return;
//        // 是否合计，根据定义
//        if (!"1".equals(cmd.getExtAttriValue("COL_OPT", "=", ";", "count")))
//            return;
//
//        double m = 0.00;
//        int rows = getDictModel().getDataSet().getRowCount();
//        Variant v= new Variant();
//        for (int j = 0; j < rows; j++) {
//            double value = 0.0;
//            try {
//                getDictModel().getDataSet().getVariant(cmd.getColid(), j, v);
//                value = Double.parseDouble(v.toString());
//            } catch (NumberFormatException ex) {
//                System.out.println("count error:" + cmd.getColid() + "=" + v.toString());
//            }
//            m = JFNumber.round(m, 2) + JFNumber.round(value, 2);
//        }
//        String type = cmd.getColType();
//        DecimalFormat df = null;
//        if("I".equals(type)){
//        	df = new DecimalFormat("#,##0");
//        }else{
//        	df = new DecimalFormat("#,##0.00");
//        }
//        try {
//            fixedModel.setValueAt(df.format(m), 0, index);
//        } catch (Exception ex1) {
//        }
    }

    /**
     *
     * @return JComponent
     */
    public JComponent getJComponent() {
        return this;
    }

    /**
     *
     */
    public void loadDictModel(String where) {
        try {
            DictUtil.setDefaultDctViewProperty(getDictModel());
            getDictModel().setDCT_ID(getDictModel().getDCT_ID());
            getDictModel().setUserPluginKey(getDictModel().getUserPluginKey());
            getDictModel().getCdsParam().setReadOnly(getDictModel().isReadOnly());
            if (where != null) getDictModel().getCdsParam().setWhere(where);
            getDictModel().loadData();
            getDictModel().getCdsParam().setMetaData(null);
        } catch (Exception e) {
            ErrorManager.getDefault().notify(e);
        }
    }

    /**
     *
     */
    public void refreshDictModel(String where) {
        try {
            if (where != null) getDictModel().getCdsParam().setWhere(where);
            getDictModel().refrensh();
//            getDictModel().getCdsParam().setMetaData(null);
        } catch (Exception e) {
            ErrorManager.getDefault().notify(e);
        }
    }

    /**
     *
     * @return EFDataSet
     */
    public EFDataSet getSelectedRowSets() {
        EFDataSet ds = EFDataSet.getInstance(getDictModel().getDCT_ID());
        int[] rows = getTable().getSelectedRows();
        if (rows == null || rows.length == 0) {
            rows = new int[1];
//            rows[0] = getDictModel().getDataSet().getRow();
        }
        EFRowSet rs = null;
        for (int i = 0; rows != null && i < rows.length; i++) {
            getDictModel().getDataSet().goToRow(rows[i]);
//            rs = DictUtil.getEFRowSet(getDictModel().getDataSet());
            // 外键列设置ID2NAME
//            DictUtil.setID2NAME(rs, getDictModel().getMetaData());
//            ds.addRowSet(rs);
        }
        return ds;
    }

    /**
     *
     * @param dictView DictView
     * @param errorInfo EFDataSet
     */
    public void auto2ErrorRow(EFDataSet errorInfo) {
//        if (errorInfo == null || errorInfo.getRowCount() == 0) return;
//        DictMetaDataEx dmd = getDictModel().getMetaData();
//        EFRowSet rowSet = errorInfo.getRowSet(0);
//        ClientDataSet cds = getDictModel().getDataSet();
//        java.util.List list2 = dmd.getKeyColumnName();
//        String[] c = new String[list2.size()];
//        System.arraycopy(list2.toArray(), 0, c, 0, c.length);
//        DataRow dr = new DataRow(cds, c);
//        for(int i = 0; c != null && i < c.length; i++){
//            dr.setString(c[i], rowSet.getString(c[i], ""));
//        }
//        if (!cds.locate(dr, Locate.FIRST))
//            return;
//        java.util.List columns = (java.util.List)rowSet.getObject("ERROR_COLUMN", null);
//        if (columns == null || columns.size() == 0) return;
//        // 定位到的列
//        String colid = columns.get(0).toString();
//        // 定位的组件
//        Object viewObject = getSelectedComponent();
//        if (viewObject == null) return;
//        // 表格方式
//        if (viewObject instanceof JTable) {
//            int row = cds.getRow();
//            int col = getVisibleColumnIndex(dmd.getVisibleColList(), colid);
//            Rectangle rr = getTable().getCellRect(row, col, true);
//            getTable().scrollRectToVisible(rr);
//            getTable().editCellAt(row, col);
//        } else if (viewObject instanceof CardTableView) {
//            // 卡片方式
//            CardTableView cardTableView = (CardTableView) getCardView();
//            cardTableView.scroll2CardComponent(colid);
//        }
    }

    /**
     *
     * @param list List
     * @param colid String
     * @return int
     */
    protected int getVisibleColumnIndex(java.util.List list, String colid) {
        for (int i = 0; list != null && i < list.size(); i++) {
            ColumnMetaData c = (ColumnMetaData) list.get(i);
            if (colid.equals(c.getColid())) return i;
        }
        return 0;
    }

	@Override
	public void stateChanged(ChangeEvent e) {
//		if (e.getChangeType() == DctChangeListener.DATA_LOADED) {
//            paintDict();
//        }
	}
}