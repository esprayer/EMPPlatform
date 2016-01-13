package com.efounder.bz.dbform.component.dc.table.footer;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.efounder.bz.dbform.component.dc.table.*;
import com.efounder.bz.dbform.component.dc.table.header.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.bz.dbform.datamodel.column.*;
import com.jidesoft.swing.*;


 /**
  * @version 1.0 11/09/98
  */

public class GroupFooterExample2
    extends JFrame {

    GroupFooterExample2(){
        super( "Table" );
    }

    /**
     *
     */
    void testTable2() {
        //新建一个表格面板
        JideScrollPane scroll = new JideScrollPane();

        //新建一个表格模型
        DCTableModel tableModel = new DCTableModel();
        tableModel.setRowCount(20);
        //新建一个数据表格
        DMTable table = new DMTable(tableModel);

        //将表格添加到面板
        scroll.setViewportView(table);

        //新建一个表列模型
        ColumnModel columnModel = new ColumnModel();
        for (int i = 1; i <= 12; i++) {
            Column tableColumn = new Column();
            tableColumn.setHeaderValue(String.valueOf(i) + "月份");
            columnModel.addColumn(tableColumn);
        }
        table.setSortable(false);
        table.setColModel(columnModel);

        //列头分组模型
        ColumnHeaderGroupModel hgroup = new ColumnHeaderGroupModel();
        hgroup.setColumnModel(table.getColModel());
        table.setGroupModel(hgroup);

        //列头组0
        ColumnGroup cgroup0 = new ColumnGroup("2010年");
        hgroup.insertDataComponent(cgroup0);

        //列头组1
        ColumnGroup cgroup1 = new ColumnGroup("第一季度");
        cgroup0.add(cgroup1);
        cgroup1.add( (Column) columnModel.getColumn(0));
        cgroup1.add( (Column) columnModel.getColumn(1));
        cgroup1.add( (Column) columnModel.getColumn(2));
        //列头组2
        ColumnGroup cgroup2 = new ColumnGroup("第二季度");
        cgroup0.add(cgroup2);
        cgroup2.add( (Column) columnModel.getColumn(3));
        cgroup2.add( (Column) columnModel.getColumn(4));
        cgroup2.add( (Column) columnModel.getColumn(5));
        //列头组3
        ColumnGroup cgroup3 = new ColumnGroup("第三季度");
        cgroup0.add(cgroup3);
        cgroup3.add( (Column) columnModel.getColumn(6));
        cgroup3.add( (Column) columnModel.getColumn(7));
        cgroup3.add( (Column) columnModel.getColumn(8));
        //列头组4
        ColumnGroup cgroup4 = new ColumnGroup("第四季度");
        cgroup0.add(cgroup4);
        cgroup4.add( (Column) columnModel.getColumn(9));
        cgroup4.add( (Column) columnModel.getColumn(10));
        cgroup4.add( (Column) columnModel.getColumn(11));

        //行头
        TableRowHeader header = new TableRowHeader(table);
        scroll.setRowHeaderView(header);

        //列脚表格模型
//        GroupTableModel fixedModel = new GroupTableModel();
//        fixedModel.setColumnModel(table.getColModel());
//        fixedModel.setRowCount(3);

        //列脚分组模型
        ColumnFooterGroupModel groups = new ColumnFooterGroupModel();
//        table.setGroupFooterModel(groups);
        table.setColumnFooterVisible(true);

//        ColumnFooterGroup group1 = new ColumnFooterGroup(0, 0, 0, 2);
//        group1.setName("合计：");
//        groups.insertDataComponent(group1);
//        ColumnFooterGroup group2 = new ColumnFooterGroup(1, 1, 0, 2);
//        group2.setName("合计：");
//        groups.insertDataComponent(group2);
//        ColumnFooterGroup group3 = new ColumnFooterGroup(1, 1, 3, 5);
//        group3.setName("合计：");
//        groups.insertDataComponent(group3);
//        ColumnFooterGroup group4 = new ColumnFooterGroup(2, 3, 0, 2);
//        group4.setName("合计：");
//        groups.insertDataComponent(group4);

        getContentPane().add(scroll, BorderLayout.CENTER);
        setSize(510, 340);
     }

     /**
      *
      * @param args String[]
      */
     public static void main(String[] args) {
         GroupFooterExample2 frame = new GroupFooterExample2();
         frame.testTable2();
         frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                 System.exit(0);
             }
         });
         frame.setVisible(true);
     }

 }

