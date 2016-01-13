package com.efounder.dbc.swing.editor;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import com.efounder.dbc.swing.help.DictHelpTextField;
import com.efounder.dctbuilder.data.ColumnMetaData;
import java.awt.Component;
import java.util.Hashtable;
import javax.swing.JComboBox;
import javax.swing.ListCellRenderer;
import com.efounder.dbc.swing.exttreetable.JTreeTableComboBox;
import com.efounder.dbc.swing.DictDataSet;
import com.efounder.dbc.swing.exttreetable.DictTreeTableExt;
import javax.swing.tree.TreePath;
import com.efounder.dbc.swing.exttreetable.TreeTableNodeExt;
import com.efounder.dbc.swing.tree.DataSetTreeNode;

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
public class TreeTableEnumCellEditor extends DefaultCellEditor {
    ColumnMetaData cmd;
    protected JTreeTableComboBox comboBox;
    public TreeTableEnumCellEditor(ColumnMetaData cmd){
        super(new JTreeTableComboBox());
        comboBox = (JTreeTableComboBox)editorComponent;
        this.cmd=cmd;
        this.setClickCountToStart(1);
        initTreeTable();
    }

    protected void initTreeTable(){
        String dictId = cmd.getFOBJ();
//        DictDataSet dictDataSet = new RLDictDataSet();
//         dictDataSet.setDictName(dictId);
//         dictDataSet.setActiveAgent(true);
         DictTreeTableExt dictTreeTable = new DictTreeTableExt();
//         dictTreeTable.setDataSet(dictDataSet);
         comboBox.setTree(dictTreeTable);
    }
    public Object getCellEditorValue() {
        Object objVal = super.getCellEditorValue();
        TreePath treePath = (TreePath)objVal;
        if(treePath == null) return null;
        DataSetTreeNode treeNode = (DataSetTreeNode)treePath.getLastPathComponent();

        return treeNode.getDctBH();
     }
     public Component getComponent() {
         return comboBox;
     }

     public Component getTableCellEditorComponent(JTable table, Object value,
                                                  boolean isSelected,
                                                  int row, int column) {
         super.getTableCellEditorComponent(table,value,isSelected,row,column);
         return comboBox;
     }

}
