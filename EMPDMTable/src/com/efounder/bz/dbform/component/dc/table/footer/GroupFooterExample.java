package com.efounder.bz.dbform.component.dc.table.footer;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.footer.*;
import com.jidesoft.swing.*;
import com.swing.table.GroupableTableHeader;
import com.swing.table.Table;


 /**
  * @version 1.0 11/09/98
  */

public class GroupFooterExample
    extends JFrame {

     TableGroupColumnFooter fixedTable;
     GroupFooterExample() {
         super( "Table" );

         DefaultTableModel dm = new DefaultTableModel();
         dm.setDataVector(new Object[20][],
                          new Object[]{"第一季度","第二季度","第三季度","10月份","11月份", "12月份"});
         Table table = new Table( dm ) {
             protected JTableHeader createDefaultTableHeader() {
                 return new GroupableTableHeader(columnModel);
             }
         };
         table.setRowHeight(20);
         JideScrollPane scroll = new JideScrollPane(table);

         //列脚表格模型
         GroupTableModel fixedModel = new GroupTableModel();
         fixedModel.setColCount(6);
         fixedModel.setRowCount(4);

         //列脚分组模型
         ColumnFooterGroupModel groups = new ColumnFooterGroupModel();
         //列脚分组1
         ColumnFooterGroup group1 = new ColumnFooterGroup(0, 0, 0, 3);
         group1.setName("合计：");
         groups.insertDataComponent(group1);
         //列脚分组2
         ColumnFooterGroup group2 = new ColumnFooterGroup(1, 3, 0, 3);
         group2.setName("合计：");
         groups.insertDataComponent(group2);
         //列脚分组3
         ColumnFooterGroup group3 = new ColumnFooterGroup(1, 3, 4, 5);
         groups.insertDataComponent(group3);

         fixedTable = new TableGroupColumnFooter();
         fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         fixedTable.setRowHeight(20);
         fixedTable.setRowMargin(1);
         fixedTable.setModel(fixedModel);
         fixedTable.setColumnModel(table.getColumnModel());
         fixedTable.setUI(new TableGroupColumnFooterUI());
         fixedTable.setGroupModel(groups);

         JViewport viewport = new JViewport();
         viewport.setView(fixedTable);
         viewport.setPreferredSize(fixedTable.getPreferredSize());
         scroll.setColumnFooterView(viewport);

         getContentPane().add(scroll, BorderLayout.CENTER);
         setSize( 510, 340 );
     }

     public static void main(String[] args) {
         GroupFooterExample frame = new GroupFooterExample();
         frame.addWindowListener( new WindowAdapter() {
             public void windowClosing( WindowEvent e ) {
                 System.exit(0);
             }
         });
         frame.setVisible(true);
     }

 }

