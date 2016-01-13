package com.swing.table;


import com.jidesoft.swing.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;


 /**
  * @version 1.0 11/09/98
  */

public class Test extends JFrame {

     Object[][] data;
     Object[] column;
     MultiSpanCellTable fixedTable;
     Test() {
         super( "Table" );

         DefaultTableModel dm = new DefaultTableModel();
         dm.setDataVector(new Object[6][],
                          new Object[]{"第一季度","第二季度","第三季度","10月份","11、12月份"});
         Table table = new Table( dm ) {
             protected JTableHeader createDefaultTableHeader() {
                 return new GroupableTableHeader(columnModel);
             }
         };
         JideScrollPane scroll=new JideScrollPane(table);

         /**
          * RowHeader
          */
         data =  new Object[][]{
                 {"SNo."    ,"" },
                 {"Name"    ,"1"},
                 {""        ,"2"},
                 {"Language","1"},
                 {""        ,"2"},
                 {""        ,"3"}};
         column = new Object[]{"",""};

         AttributiveCellTableModel fixedModel = new AttributiveCellTableModel(data, column) {
             public boolean CellEditable(int row, int col) {
                 return false;
             }
         };

         CellSpan cellAtt =(CellSpan)fixedModel.getCellAttribute();
         cellAtt.combine(new int[] {0}    ,new int[] {0,1});    //RowHeader 分组
         cellAtt.combine(new int[] {1,2}  ,new int[] {0});
         cellAtt.combine(new int[] {3,4,5},new int[] {0});

         fixedTable = new MultiSpanCellTable( fixedModel );
         fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
         fixedTable.setGridColor(table.getTableHeader().getBackground());
//         fixedTable.setDefaultRenderer(Object.class, new RowHeaderRenderer(fixedTable));
         JViewport viewport = new JViewport();
         viewport.setView(fixedTable);
         viewport.setPreferredSize(fixedTable.getPreferredSize());
         scroll.setRowHeaderView(viewport);
         /**
          * end RowHeader
          */

         getContentPane().add(scroll, BorderLayout.CENTER);
         setSize( 510, 340 );
         try {
             jbInit();
         } catch (Exception ex) {
             ex.printStackTrace();
         }
     }
     public static void main(String[] args) {
         Test frame = new Test();
         frame.addWindowListener( new WindowAdapter() {
             public void windowClosing( WindowEvent e ) {
                 System.exit(0);
             }
         });
         frame.setVisible(true);
     }
     private void jbInit() throws Exception {
     }
 }
