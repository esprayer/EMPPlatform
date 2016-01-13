package com.efounder.dctbuilder.view;

import java.awt.*;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import com.borland.dx.dataset.*;
import com.core.xml.*;
import com.efounder.bz.dbform.component.dc.table.footer.TableGroupColumnFooter;
import com.efounder.dbc.*;
import com.efounder.dctbuilder.rule.*;
import com.efounder.dctbuilder.util.*;
import com.efounder.dctbuilder.variant.*;
import com.efounder.plugin.*;

/**
 * <p>Title: 单个字典表格维护视图</p>
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
public class DictTableView extends DictView {
    public DictTableView() {
        super();
        actionKey = "";
    }

    public DictTableView(boolean showcard) {
        actionKey = "";
    }

    public void refrensh() {
        try {
            getDictModel().refrensh();
        } catch (Exception ex1) {
            ex1.printStackTrace();
        } finally {
        }
    }

    protected void jbInit() throws Exception {
    	jsp = new JScrollPane();
    	jsp.setViewportView(table);
    	this.setLayout(borderLayout1);
    	this.add(jsp, BorderLayout.CENTER);
    }

    public void addRow() {
        this.addRow(true);
    }

    public void addRow(boolean post) {
        try {
            // 停止编辑
            table.stopEditing();

            //beforeAdd
//            Object o1 = getScriptManager().runFunction(this, "beforeAdd");
//            if (o1 != null && o1 instanceof Boolean) {
//                if (!((Boolean) o1).booleanValue()) return;
//            }

            // 记录上一行(默认不复制)
            ClientDataSet cds = getDictModel().getDataSet();

			//插入一行
            cds.insertRow(false);

            //将上一行值设置到新加行
            String bmcol = getDictModel().getMetaData().getDctBmCol();
            if (bmcol.trim().length() > 0 && cds.hasColumn(bmcol) != null)
                cds.getRowSet(cds.getRowCount() - 1).putString(bmcol, "");
//            if (!isAddCopyPrevious()) dr = null;
//            super.setNewRowValue(dr, null);
            //设置默认值
            VariantUtil.setDefaultValue(getDictModel());
            // 编码规则
            String codeRuleType = getDictModel().getCdsParam().getString("CodeRuleType", "");
            java.util.List tmlist = PluginManager.loadPlugins("CodeRule", true);
            ICodeRule rule = null;
            String bhValue = "";
            for (int i = 0, n = tmlist.size(); i < n; i++) {
                StubObject so = (StubObject) tmlist.get(i);
                if (so.getPluginObject() != null && so.getPluginObject() instanceof ICodeRule) {
                    rule = (ICodeRule) so.getPluginObject();
                    try {
                        rule.setDictView(this);
                        rule.setNodeViewerAdapter(this);
                        rule.setCodeRuleType(codeRuleType);
                        bhValue = (String) rule.doByRule();
                        if (bhValue != null && bhValue.trim().length() > 0)
                            cds.getRowSet().putString(bmcol, bhValue);
                    } finally {
                        rule.setDictView(null);
                        rule.setNodeViewerAdapter(null);
                        rule.setCodeRuleType(null);
                    }
                }
            }
            // 设置SYS_ID，如果有的话
//            if (getViewer() != null
//                && getViewer() instanceof JDictEditorView) {
//                String sysid = ((JDictEditorView) getViewer()).getSystemID();
//                if (cds.hasColumn("SYS_ID") != null) {
//                    cds.setString("SYS_ID", sysid);
//                }
//            }
            // 值为空的主键列，约定设一个空格
            processDCTKeyValue(cds);
            
            this.getTable().setRowSelectionInterval(cds.getCurrentRowIndex(), cds.getCurrentRowIndex());
            this.repaint();
            this.getTable().updateUI();
            Rectangle rect = this.getTable().getCellRect(cds.getCurrentRowIndex(), 0, true);
            this.getTable().scrollRectToVisible(rect);
//            //使新加值生效
//            if (post)
//                cds.post();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param cds ClientDataSet
     */
    protected void processDCTKeyValue(ClientDataSet cds) {
//        java.util.List keys = getDictModel().getMetaData().getKeyColumnName();
//        for (int i = 0; keys != null && i < keys.size(); i++) {
//            String keyc = keys.get(i).toString();
//            String vals = DictUtil.getVariantValue(cds, keyc);
//            if (vals == null || vals.length() == 0) DictUtil.setVariantValue(cds, keyc, " ");
//        }
    }

    /**
     *
     */
    public void delRow() {
        try {
            // 停止编辑
            table.stopEditing();

//            Object o1 = getScriptManager().runFunction(this, "beforeDelete");
//            if (o1 != null && o1 instanceof Boolean) {
//                if (!((Boolean) o1).booleanValue()) return;
//            }

            //是否物理删除，默认是物理删除，否则置F_SYZT="0"
            String  phyDelete = getDictModel().getCdsParam().getString("PhysicalDelete", "1");
            if (phyDelete.trim().length() == 0) phyDelete = "1";
            ClientDataSet cds = getDictModel().getDataSet();
//            DataRow dr = new DataRow(cds);
//            cds.getDataRow(dr);
//            getDictModel().setLastDataRow(dr);
            if ("1".equals(phyDelete)) {
                if(cds.getRowCount() > 0) {
                	cds.deleteRow();
                }
            } else {
              if(cds.getRowSet().getDataStatus() == RowStatus.INSERTED){
                if(cds.getRowCount() > 0) cds.deleteRow();
              }else if (cds.hasColumn("F_SYZT") != null &&
                    cds.hasColumn("F_SYZT").getColType().equals("C")) {
                    cds.getRowSet().putString("F_SYZT", "0");
                    cds.setMidified(true);
                }
            }

//            Object o2 = getScriptManager().runFunction(this, "afterDelete");
            if(cds.getRowCount() > 0) {
            	this.getTable().setRowSelectionInterval(cds.getCurrentRowIndex(), cds.getCurrentRowIndex());
            }
            this.getTable().setRowSelectionInterval(cds.getCurrentRowIndex(), cds.getCurrentRowIndex());
            this.repaint();
            this.getTable().updateUI();
            Rectangle rect = this.getTable().getCellRect(cds.getCurrentRowIndex(), 0, true);
            this.getTable().scrollRectToVisible(rect);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delAllRow() {
        int count = getDictModel().getDataSet().getRowCount();
        for (int i = 0; i < count; i++)
            delRow();
    }
}
