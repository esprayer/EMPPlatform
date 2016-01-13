package jservice.jbof.classes.GenerQueryObject;

import java.awt.*;
import jfoundation.gui.window.classes.JFrameDialog;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import jframework.foundation.classes.*;
import com.pansoft.report.table.jxml.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import jfoundation.dataobject.classes.*;
import com.pansoft.pub.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQuerySelectItemDlg extends JFrameDialog
    implements ActionListener {
  String lmbh = null;
  JPanel main = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel topPanel = new JPanel();
  JPanel leftPanel = new JPanel();
  JPanel rightPanel = new JPanel();
  JPanel bottomPanel = new JPanel();
  JPanel centerPanel = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JButton okButton = new JButton();
  JButton cancelButton = new JButton();
  Border border1;
  TitledBorder titledBorder1;
  JScrollPane jScrollPane1 = new JScrollPane();
  JTable Table = new JTable();

  public JQuerySelectItemDlg(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
//    super.setMenuShow(false);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public JQuerySelectItemDlg() {
    this(JActiveDComDM.MainApplication.MainWindow, "栏目选择", false);
  }

  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(148, 145, 140));
    titledBorder1 = new TitledBorder(border1,"选择栏目");
    main.setLayout(borderLayout1);
    centerPanel.setLayout(borderLayout2);
    okButton.setMnemonic('O');
    okButton.setText("确认(O)");
    okButton.addActionListener(this);
    cancelButton.setMnemonic('C');
    cancelButton.setText("取消(C)");
    cancelButton.addActionListener(this);
    centerPanel.setBorder(titledBorder1);
    main.setPreferredSize(new Dimension(350, 300));
    getContentPane().add(main);
    main.add(topPanel, BorderLayout.NORTH);
    main.add(leftPanel, BorderLayout.WEST);
    main.add(rightPanel, BorderLayout.EAST);
    main.add(bottomPanel, BorderLayout.SOUTH);
    main.add(centerPanel, BorderLayout.CENTER);
    centerPanel.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(Table, null);
    bottomPanel.add(okButton, null);
    bottomPanel.add(cancelButton, null);
  }

  public boolean initTable(String QueryObject){
    TableManager tableManager = new TableManager(QueryObject);

    Object[] column = {"编 号","名 称"};
    DefaultTableModel TableModel = new DefaultTableModel(null,column){
      public boolean isCellEditable(int row,int column){
        return false;
      }
    };
    Table.setModel(TableModel);
    Table.setAutoResizeMode(Table.AUTO_RESIZE_OFF);

    int idLength=0,captionLength=0;
    boolean group = false;
    Vector groups = tableManager.getGroups();
    for (int i = 0; i < groups.size();i++){
      XmlGroup xmlGroup = (XmlGroup)groups.get(i);
      String id = xmlGroup.getId();
      if (!id.startsWith("gid")){
        continue;
      }
      id = id.replaceAll("gid","");
      String caption = xmlGroup.getCaption();
      //去掉标题中的换行符
      if(caption.indexOf(TableManager.NEW_LINE) > -1){
          caption = caption.substring(caption.indexOf(TableManager.NEW_LINE) + TableManager.NEW_LINE.length());
      }

      Object[] rowData = {id,caption};
      TableModel.addRow(rowData);
      group = true;
      if (idLength < id.length()){
        idLength = id.length();
      }
      if (captionLength < caption.length()){
        captionLength = caption.length();
      }
    }
    if (!group){
      Vector columns = tableManager.getTableView();
      for (int i = 0; i < columns.size(); i++) {
        XmlViewCol view =(XmlViewCol) columns.elementAt(i);
        XmlColumn xmlColumn = tableManager.getColumnById(view.getId());

        //XmlColumn xmlColumn = (XmlColumn) columns.get(i);
        String id = xmlColumn.getId();
        if (!id.startsWith("cid")) {
          continue;
        }
        id = id.replaceAll("cid_JE", "");
        id = id.replaceAll("cid_SL", "");
        id = id.replaceAll("cid", "");
        String caption = xmlColumn.getCaption();
        //去掉标题中的换行符
        if(caption.indexOf(TableManager.NEW_LINE) > -1){
          caption = caption.substring(caption.indexOf(TableManager.NEW_LINE) + TableManager.NEW_LINE.length());
        }

        Object[] rowData = {
            id, caption};
        TableModel.addRow(rowData);

        if (idLength < id.length()) {
          idLength = id.length();
        }
        if (captionLength < caption.length()) {
          captionLength = caption.length();
        }
      }
    }

    TableColumnModel tableCM = Table.getColumnModel();
    TableColumn idColumn = tableCM.getColumn(0);
    idColumn.setPreferredWidth(idLength*10);
    TableColumn captionColumn = tableCM.getColumn(1);
    captionColumn.setPreferredWidth(captionLength*15);

    this.repaint();
    return true;
  }

  /**
   * Invoked when an action occurs.
   *
   * @param e ActionEvent
   * @todo Implement this java.awt.event.ActionListener method
   */
  public void actionPerformed(ActionEvent e) {
    if (e.getSource()==this.okButton){
      okEvent();
    }else if(e.getSource()==this.cancelButton){
      cancelEvent();
    }
  }
  /**
   *
   */
  private void okEvent(){
    TableModel tableModel = Table.getModel();
    int curRow = Table.getSelectedRow();
    if (curRow < 0 || curRow > Table.getRowCount()){
      return;
    }
    lmbh = tableModel.getValueAt(curRow,0).toString();
    this.OnOk();
  }
  /**
   *
   */
  private void cancelEvent(){
    this.OnCancel();
  }

  public String getLmbh(){
    return lmbh;
  }
}
