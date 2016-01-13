package com.efounder.dbc.swing.help;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import com.borland.dbswing.*;
import com.core.xml.*;
import com.efounder.dataobject.view.DCTTableScrollPane;
import com.efounder.pfc.dialog.*;

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
public class CommonHelpWindow extends JPDialog implements ActionListener,KeyListener{
    BorderLayout borderLayout1 = new BorderLayout();
    BorderLayout bl1 = new BorderLayout();
    FlowLayout fl1=new FlowLayout();
    FlowLayout flTop=new FlowLayout();
    TableScrollPane jsp = new DCTTableScrollPane();
    JdbTable table=new JdbTable();
    JTextField findField = new JTextField(15);
    JButton btnFind = new JButton();
    JPanel findPanel = new JPanel();
    JPanel bottom=new JPanel();
    JPanel oppnel=new JPanel();
    JButton btOk=new JButton("选择");
    JButton btCancel=new JButton("退出");
    TitledBorder titledBorder1 = new TitledBorder("");
    String selBh;
    Vector colName = null;
    Vector rowData = null;
    String bhcolName = "";
    final static String _COLNAME_ = "COLNAME";
    final static String _ROWDATA_ = "ROWDATA";
    final static String _BH_ = "BH";

    int findIndex = 0;

//    ClientDataSet dataSet = new ClientDataSet();

    public CommonHelpWindow(Frame frame,String title,StubObject paramSO) {//Vector cols,Vector rowData,String bhColName
        super(frame, title, true);
        try {
            this.colName = (Vector)paramSO.getObject(_COLNAME_,null);
            this.rowData = (Vector)paramSO.getObject(_ROWDATA_,null);
            this.bhcolName = (String)paramSO.getString(_BH_,null);

            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getContentPane().setLayout(borderLayout1);
        btOk.setPreferredSize(new Dimension(70, 23));
        btCancel.setPreferredSize(new Dimension(70, 23));
        getContentPane().add(findPanel,BorderLayout.NORTH);
        getContentPane().add(jsp,BorderLayout.CENTER);
        getContentPane().add(bottom,BorderLayout.SOUTH);
        bottom.setLayout(bl1);
        bottom.add(oppnel,BorderLayout.EAST);
        oppnel.setLayout(fl1);
        oppnel.add(btOk);
        oppnel.add(btCancel);

        initFindPanel();
        DefaultTableModel m_tableModel = new DefaultTableModel(this.rowData,this.colName);
        table.setModel(m_tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
//        table.setSelectionModel(ListSelectionModel.SINGLE_SELECTION);
        jsp.getViewport().add(table);
        jsp.setPreferredSize(new Dimension(400,300));
        btOk.addActionListener(this);
        btCancel.addActionListener(this);
    }

    protected void initFindPanel(){
      flTop.setAlignment(FlowLayout.LEFT);
      findPanel.setLayout(flTop);
      btnFind.setText("查找");
      btnFind.addActionListener(this);
      findPanel.add(new JLabel("编号/名称:"));
      btnFind.setMargin(new Insets(0,0,0,0));
      btnFind.setPreferredSize(new Dimension(60,24));
      findField.addKeyListener(this);
      findPanel.add(findField);
      findPanel.add(btnFind);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btCancel){
          super.OnCancel();
        }
        else if(e.getSource()==btOk){
            onConfirm();
            super.OnOk();
        }
        else if(e.getSource() == btnFind){
          onFind();
        }
    }
    public Object getResultObject() {
        return selBh;
    }

    protected void onConfirm(){
      int selRowIndex = table.getSelectedRow();
      if(selRowIndex == -1) return;
      TableColumn tc =  table.getColumn(bhcolName);
      int modelIndex = tc.getModelIndex();
      DefaultTableModel m_Model = (DefaultTableModel)table.getModel();
      String bh = (String)m_Model.getValueAt(selRowIndex,modelIndex);
      selBh = bh;
    }

    public void onFind(){
        String findVal = findField.getText();
        DefaultTableModel m_Model = (DefaultTableModel)table.getModel();
        int rowCount = m_Model.getRowCount();
        if(rowCount <= 0) return ;

        if(findIndex == rowCount) findIndex = 0;
        boolean isFind = false;
       for(int i = findIndex; i < rowCount; i++){
           String valBh = (String) m_Model.getValueAt(i, 0);
           String valMc = (String) m_Model.getValueAt(i, 1);
           if(valBh.indexOf(findVal) > -1){
               findIndex = i;
               isFind = true;
               break;
           }
           else{
               if(valMc.indexOf(findVal) > -1){
                   findIndex = i;
                   isFind = true;
                   break;
               }
               else{
                 findIndex = i;
               }
           }
       }
       if(isFind){//找到
         Rectangle rect = table.getCellRect(findIndex, 0, true);
         table.scrollRectToVisible(rect);
         table.setRowSelectionInterval(findIndex,findIndex);
         findIndex ++;
       }
       else{
         findIndex = 0;
       }
    }

    public void keyTyped(KeyEvent e) {
        if (e.getSource() == findField) {
          //按回车键查找
          if (e.getKeyChar() == '\n') {
            onFind();
          }
          else{
            findIndex =0;//换字符重新查找
          }
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
