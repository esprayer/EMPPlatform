package com.efounder.form.client.component.frame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Action;
import javax.swing.table.*;

import com.efounder.action.*;
import com.efounder.actions.*;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.bizmodel.*;
import com.efounder.form.client.component.pic.*;
import com.efounder.dbc.swing.render.BoolCellRender;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JAffixStandardExamplePanel extends JPanel {
    public JAffixStandardExamplePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    FormModel formModel = null;
    BorderLayout borderLayout = new BorderLayout();
    JTable  listTable  = new ReadonlyTable();
    DefaultTableModel tableModel = new DefaultTableModel();
    JScrollPane scrollPanel = new JScrollPane();

    private void jbInit() throws Exception {
        initTable();
        initEvent();

        this.setLayout(borderLayout);
        scrollPanel.getViewport().add(listTable,null);
        this.add(scrollPanel,BorderLayout.CENTER);
    }

    protected void initTable(){
        Vector column = new Vector();
        column.add("附件标准编号");//附件标准
        column.add("名称");
        column.add("文件类型");
        column.add("必须上传");
        column.add("高度");//4
        column.add("宽度");//5
        column.add("尺寸");//6
        column.add("颜色");//7
        column.add("使用状态");//8

        listTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableModel.setColumnIdentifiers(column);
        listTable.setModel(tableModel);
        listTable.setSelectionMode(0);

        listTable.getColumn("附件标准编号").setPreferredWidth(90);
        listTable.getColumn("名称").setPreferredWidth(180);

        listTable.removeColumn(listTable.getColumn(listTable.getColumnName(8)));
        listTable.removeColumn(listTable.getColumn(listTable.getColumnName(7)));
        listTable.removeColumn(listTable.getColumn(listTable.getColumnName(6)));
        listTable.removeColumn(listTable.getColumn(listTable.getColumnName(5)));
        listTable.removeColumn(listTable.getColumn(listTable.getColumnName(4)));
        listTable.removeColumn(listTable.getColumn(listTable.getColumnName(3)));
        listTable.removeColumn(listTable.getColumn(listTable.getColumnName(2)));

        listTable.setRowHeight(25);

//        TableColumn colorColumn = listTable.getColumn("文件类型");
//        colorColumn.setCellRenderer(new PicResWJLXCellRender());
//
//       colorColumn = listTable.getColumn("必须上传");
//       colorColumn.setCellRenderer(new BoolCellRender());

//       JCheckBox checkBox = new JCheckBox();
//       colorColumn.setCellEditor(new DefaultCellEditor(checkBox));
//       colorColumn.setCellRenderer(new DefaultCellRenderer(checkBox));

//       DefaultTableCellRenderer colorRenderer = new DefaultTableCellRenderer() {
//            public void setValue(Object value) {
//                if (value instanceof NamedColor) {
//                    NamedColor c = (NamedColor) value;
//                    setBackground(c);
//                    setForeground(c.getTextColor());
//                    setText(c.toString());
//                } else {
//                    super.setValue(value);
//                }
//            }
//         };

//       colorRenderer.setHorizontalAlignment(JLabel.CENTER);
    }

    protected void initEvent(){
//        listTable.addMouseListener(new StandardExampleMouseAdapter(this));
    }


    public void fillData(EFDataSet dataSet){
        if(dataSet == null || dataSet.getRowCount() == 0) return;

        //先把内容清空
        int size = tableModel.getRowCount();
        for(int i = size -1 ; i >= 0; i--){
            tableModel.removeRow(i);
        }

        //填充新的数据
        for(int i = 0; i < dataSet.getRowCount(); i++){
            EFRowSet rowSet = (EFRowSet)dataSet.getRowSet(i);
            Vector vItem = new Vector();
            //T.F_FJLXBH,T.F_FJLXMC,T.F_TYPE,GX.F_YXBC,T.F_HIGH,T.F_WIDTH,T.F_SIZE,T.F_COLOR,T.F_SYZT
            vItem.add(rowSet.getObject("F_FJLXBH",""));
            vItem.add(rowSet.getObject("F_FJLXMC",""));
            vItem.add(rowSet.getObject("F_TYPE",""));
            vItem.add(rowSet.getObject("F_YXBC","0"));
            vItem.add(rowSet.getObject("F_HIGH",new Integer(0)));
            vItem.add(rowSet.getObject("F_WIDTH",new Integer(0)));
            vItem.add(rowSet.getObject("F_SIZE",new Integer(0)));
            vItem.add(rowSet.getObject("F_COLOR",new Integer(0)));
            vItem.add(rowSet.getObject("F_SYZT",""));

            tableModel.addRow(vItem);
        }
    }


    public void mouseClicked(MouseEvent e) {
       if (( e.getModifiers() & e.BUTTON3_MASK ) != 0  ) {//e.getModifiers() == e.BUTTON3_MASK 右键弹出菜单
           ActionGroup popupMenu = null;
           Point p = e.getPoint();
           if(e.getSource()== listTable ){
               popupMenu = getMenuAction();
           }
           if (popupMenu != null) {
               showPopupmenu(p, (java.awt.Component) e.getSource(), popupMenu);
           }
        }
    }

    protected ActionGroup getMenuAction() {
        ActionGroup ag = null;
        Object[] nodeArray = {this, listTable};
        Action[] actions = ActionManager.getContextActions(this.getClass().getName(), this, nodeArray);
        if (actions != null) {
            ag = new ActionGroup();
            for (int i = 0; i < actions.length; i++) {
                ag.add(actions[i]);
            }
        }
        return ag;
    }

    protected void showPopupmenu(Point p, java.awt.Component invoker,
                         ActionGroup popupMenu) {
        if (popupMenu != null) {
            ActionPopupMenu actionPopupMenu = new ActionPopupMenu(invoker, popupMenu, true);
            actionPopupMenu.show(invoker, p.x, p.y);
        }
    }


    class ReadonlyTable extends JTable {
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }


//    public FormModel getFormModel() {
//        return formModel;
//    }
//
//    public void setFormModel(FormModel formModel) {
//        this.formModel = formModel;
//    }


}

class StandardExampleMouseAdapter
    extends MouseAdapter {
  private JAffixStandardExamplePanel adaptee;
  StandardExampleMouseAdapter(JAffixStandardExamplePanel adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.mouseClicked(e);
  }
}
