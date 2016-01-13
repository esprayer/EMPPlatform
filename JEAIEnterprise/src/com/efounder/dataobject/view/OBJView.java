package com.efounder.dataobject.view;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.openide.*;
import com.borland.dbswing.*;
import com.borland.dx.dataset.*;
import com.efounder.action.*;
import com.efounder.actions.*;
import com.efounder.dataobject.*;
import com.efounder.dataobject.action.*;
import com.efounder.eai.ide.*;
import com.efounder.pfc.window.*;
import com.efounder.view.*;
import com.efounder.dbc.*;
import javax.swing.table.*;
import com.efounder.dataobject.node.*;
import com.efounder.node.*;
import java.awt.BorderLayout;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class OBJView extends JPanel implements MouseListener,StatusListener {
  BorderLayout borderLayout1 = new BorderLayout();
  TableScrollPane tableScrollPane = new DCTTableScrollPane();
  private JdbTable dctTable = new JdbTable();
  JPanel pnDock = new JPanel();
//  protected JToolBar dctToolBar = new JToolBar();
  BorderLayout borderLayout2 = new BorderLayout();

//  JdbNavToolBar dbToolbar = new JdbNavToolBar();

  ActionGroup GROUP_Context   = new ExplorerActionGroup("Context...",'C',"Context",ExplorerIcons.ICON_ARRAY,true);
  ActionGroup GROUP_FirstNode = new ExplorerActionGroup("FirstNode...",'F',"FirstNode",ExplorerIcons.ICON_PROJECT_GROUP,true);
  ActionGroup GROUP_ToolBar   = new ExplorerActionGroup("FileC",'C',"File toolbar");
  ActionGroup GROUP_POPUP     = new ExplorerActionGroup("PopupMenu",'P',"PopupMenu");
  DCTViewAction actionOpen   = new DCTViewCommandAction(this,"openDataRow",  "打开",'0',"打开数据节点",ExplorerIcons.ICON_OPEN);
  DCTViewAction actionAdd     = new DCTViewCommandAction(this,"insertDataRow","增加",'0',"新建数据节点",ExplorerIcons.ICON_PROJECTADD);
  DCTViewAction actionDel     = new DCTViewCommandAction(this,"deleteDataRow","删除",'0',"删除数据节点",ExplorerIcons.ICON_PROJECTREMOVE);
  DCTViewAction actionRefresh = new DCTViewCommandAction(this,"refresh","刷新",'F',"refresh",ExplorerIcons.ICON_REFRESH);

  /**
   *
   */
  protected ExplorerViewToolBarPane ToolBar = null;
  /**
   *
   */
  ArrayList ToolbarGroupArray = new ArrayList();
  /**
   *
   */
  protected String DCTVIEW_ID = null;
  /**
   *
   */
  JLabel dctLabel = new JLabel();
  /**
   *
   * @param tabObject TABObject
   */
  public void setDctObject(DCTObject dctObject) {
    this.dctObject = dctObject;
    if ( dctObject != null ) {
//      DataSet dataSet = dctObject.getTabObject().getDataSetStruct();
//      this.setClientDataSet(dataSet);
    }
  }
  /**
   *
   * @param dctNodeDataRow DCTNodeDataRow
   */
  public void setDctNodeDataRow(DCTNodeDataRow dctNodeDataRow) {
    this.dctNodeDataRow = dctNodeDataRow;
    if ( dctNodeDataRow == null ) return;
    this.setDctObject(dctNodeDataRow.getDCTObjectDataRow(DCT_TYPE));
    viewDataSet = dctNodeDataRow.getDataSet(DCT_TYPE);
    this.setClientDataSet(viewDataSet);
  }
  protected DataSet viewDataSet = null;
  public DataSet getViewDataSet() {
    return viewDataSet;
  }
  public void setDCT_TYPE(String DCT_TYPE) {
    this.DCT_TYPE = DCT_TYPE;
  }

  protected String DCT_TYPE = null;

  /**
   *
   */
  protected DCTNodeDataRow dctNodeDataRow = null;
  /**
   *
   * @return DCTNodeDataRow
   */
  public DCTNodeDataRow getDctNodeDataRow() {
    return dctNodeDataRow;
  }

  /**
   *
   * @return TABObject
   */
  public DCTObject getDctObject() {
    return dctObject;
  }

  public ExplorerViewToolBarPane getDctToolBar() {
    return this.ToolBar;
  }

  public JLabel getDctLabel() {
    return dctLabel;
  }

  public String getDCT_TYPE() {
    return DCT_TYPE;
  }

  /**
   *
   * @param clientDataSet DataSet
   */
  public void setClientDataSet(DataSet clientDataSet) {
    if ( clientDataSet != null ) {
      try {
        this.dctTable.setDataSet(clientDataSet);
      } catch ( Exception e ) {ErrorManager.getDefault().notify(0,e);};
    } else {
      this.dctTable.setDataSet(null);
    }
    TableColumn tabCol = dctTable.getColumnModel().getColumn(0);
    DCTLevelCellRenderer render = new DCTLevelCellRenderer(DCT_TYPE,dctTable.getDataSet(),getLevelColID(),dctLabel.getIcon(),dctNodeDataRow);
    tabCol.setCellRenderer(render);
//    dctTable.createDefaultColumnsFromModel();
  }
  /**
   * 获取当前数据字典的级数ColID
   * @return String
   */
  protected String getLevelColID() {
    if ( dctObject != null ) {
      return dctObject.getDCTField("DCT_JSCOLID").trim();
    }
    return null;
  }
  /**
   * 获取此DCTView的ID
   * @return String
   */
  public String getDCTVIEW_ID() {
    return DCTVIEW_ID;
  }
  /**
   *
   * @param ID String
   */
  public void setDCTVIEW_ID(String ID) {
    DCTVIEW_ID = ID;
  }
  /**
   *
   * @return DataRow[]
   */
  public DataRow[] getSelectNodes() {
    return (DataRow[])this.getNodes();
  }
  /**
   *
   */
  public OBJView() {
    this(null,null,null);
  }
  /**
   *
   */
  public OBJView(Icon icon,String sdctLable) {
    this(null,icon,sdctLable);
  }
  /**
   *
   * @param clientDataSet ClientDataSet
   * @param sdctLable String
   */
  public OBJView(DCTObject dctObject,String sdctLable) {
    this(dctObject,ExplorerIcons.ICON_ADD_NOTRACING,sdctLable);
  }
  /**
   *
   */
  public OBJView(DCTObject dctObject,Icon icon,String sdctLable) {
    try {
      jbInit();
      this.dctLabel.setText(sdctLable);
      this.dctLabel.setIcon(icon);
      setDctObject(dctObject);
    }
    catch(Exception ex) {
      ErrorManager.getDefault().notify(0,ex,this);
    }
  }
  protected DCTObject dctObject = null;
  JPanel pnCustomPanel = new JPanel();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  /**
   *
   * @throws Exception
   */
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    pnDock.setLayout(borderLayout2);
    jPanel3.setLayout(borderLayout3);
    initCtrl();
    pnCustomPanel.setLayout(borderLayout4);
    //
//    dctToolBar.add(dctLabel, null);
//    dctToolBar.add(ToolBar, null);
    ToolBar.add(dctLabel);
    tableScrollPane.getViewport().add(dctTable, null);
    pnDock.add(ToolBar, BorderLayout.CENTER);
//    pnDock.add(dctToolBar, BorderLayout.CENTER);
    jPanel3.add(pnDock, java.awt.BorderLayout.NORTH);
    jPanel3.add(tableScrollPane, java.awt.BorderLayout.CENTER);
    this.add(jPanel3, java.awt.BorderLayout.CENTER);
    this.add(pnCustomPanel, java.awt.BorderLayout.NORTH);
    initActionGroup();
    initEvent();
  }
  /**
   *
   */
  protected void initActionGroup() {
    GROUP_ToolBar.add(actionOpen);
    GROUP_ToolBar.add(actionAdd);
    GROUP_ToolBar.add(actionDel);
    GROUP_ToolBar.add(actionRefresh);
    GROUP_ToolBar.add(GROUP_Context);
    GROUP_ToolBar.add(GROUP_FirstNode);
    ToolBar.addGroup(GROUP_ToolBar);
  }
  /**
   *
   */
  private void initCtrl() {
    dctTable.setPopupMenuEnabled(false);
    dctTable.setEditable(false);
    ToolBar = new ExplorerViewToolBarPane(this);
//    dctToolBar.add(ToolBar, null);
//    dctToolBar.add(this.dbToolbar.getFirstButton());
//    dctToolBar.add(this.dbToolbar.getPriorButton());
//    dctToolBar.add(this.dbToolbar.getNextButton());
//    dctToolBar.add(this.dbToolbar.getLastButton());
//    ToolBar.setBackground(dctToolBar.getBackground());
    this.dctTable.setDragEnabled(true);
    dctTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
  }
  /**
   *
   */
  private void initEvent() {
    this.dctTable.addMouseListener(this);
  }
  /**
   *
   * @return ActionGroup[]
   */
  public ActionGroup[] getToolBarGroups() {
    ActionGroup aactiongroup[] = new ActionGroup[ToolbarGroupArray.size()];
    aactiongroup = (ActionGroup[])(ToolbarGroupArray).toArray(aactiongroup);
    return aactiongroup;
  }
  /**
   *
   * @param i1 int
   * @param actiongroup ActionGroup
   */
  public void addToolBarGroup(int i1, ActionGroup actiongroup) {
    ToolbarGroupArray.add(i1, actiongroup);
    if ( ToolBar != null )
      ToolBar.addGroup(i1,actiongroup);
  }
  /**
   *
   * @param actiongroup ActionGroup
   */
  public void addToolBarGroup(ActionGroup actiongroup) {
    ToolbarGroupArray.add(actiongroup);
    if ( ToolBar != null )
      ToolBar.addGroup(actiongroup);
  }
  /**
   *
   * @param e TreeSelectionEvent
   */
  public void statusMessage(StatusEvent event) {
//    setContextAction();
//    setStatus();
  }
//  public void changeSelection(int rowIndex,int columnIndex,boolean toggle,boolean extend) {
//
//  }
  /**
   *
   */
  public void setStatus() {
    IWindowStatus Status = getStatus();
    if( Status != null ) {
      TableColumn tc = this.dctTable.getColumnModel().getColumn(0);
      TableColumn tc1 = this.dctTable.getColumnModel().getColumn(1);
      if ( tc != null && tc.getCellRenderer() instanceof JLabel ) {
        JLabel l = (JLabel)tc.getCellRenderer();
        Status.setIcon(l.getIcon());Status.setText(l.getText());
      }
    }
  }
  /**
   *
   * @return IWindowStatus
   */
  private IWindowStatus getStatus() {
    return (IWindowStatus)System.getProperties().get("WindowStatus");
  }
  /**
   *
   * @param popupMenu ActionGroup
   */
  protected void setContextAction(ActionGroup popupMenu) {
    GROUP_Context.removeAll();
//    ActionGroup popupMenu = getPopupMenuActionGroup();
    if ( popupMenu != null )
      GROUP_Context.add(popupMenu);
  }

  /**
   * mouseClicked
   *
   * @param e MouseEvent
   */
  public void mouseClicked(MouseEvent e) {
    ActionGroup popupMenu = getPopupMenuActionGroup();
    setContextAction(popupMenu);
    if ( (e.getModifiers() & e.BUTTON3_MASK) != 0) {
      Point p = e.getPoint();
      showPopupmenu(p, (Component) e.getSource(), popupMenu);
    }
    // 如果是右键双击，
    if ( ( (e.getModifiers() & e.BUTTON1_MASK) != 0) && e.getClickCount() == 2) {
      processDoubleClick();
    }
    setStatus();
  }
  /**
   *
   */
  protected void processDoubleClick() {

  }
  /**
   *
   * @param X int
   * @param Y int
   */
  protected void showPopupmenu(Point p,Component invoker,ActionGroup popupMenu) {
    if ( popupMenu != null ) {
      ActionPopupMenu actionPopupMenu = new ActionPopupMenu(invoker, popupMenu, true);
      actionPopupMenu.show(invoker, p.x,p.y);
    }
  }
  /**
   *
   * @return Object[]
   */
  DataRow[] getNodes() {
    int[] Rows = this.dctTable.getSelectedRows();
    if ( Rows.length == 0 ) return null;
    DataRow   dataRow  = null;
    DataRow[] dataRows = new DataRow[Rows.length];
    ListSelectionModel listModel = dctTable.getSelectionModel();

    DataSet dataSet = dctTable.getDataSet();
    try {
//      dataSet.enableDataSetEvents(false);
      for (int i = 0; i < dataRows.length; i++) {
        dataRow     = new DataRow(dataSet);
        dataSet.goToRow(Rows[i]);
        dataSet.getDataRow(dataRow);
        dataRows[i] = dataRow;
      }
    } catch ( Exception e ) {
      ErrorManager.getDefault().notify(0,e,this);
    } finally {
//      dataSet.enableDataSetEvents(true);
      for(int i=0;i<Rows.length;i++) {
        listModel.addSelectionInterval(Rows[i],Rows[i]);
      }
//      dctTable.setSelectionModel(listModel);
    }

    return dataRows;
  }
  protected ActionGroup getExplorerViewActionGroup(DataRow[] dataRows) {
    return null;
  }
  /**
   *
   * @return ActionGroup
   */
  protected ActionGroup getPopupMenuActionGroup() {
    // 获取ID，以关键字作有
    String ID = this.getDCTVIEW_ID();
    // 将GROUP_POPUP清空
    GROUP_POPUP.removeAll();
    // 获取当前选择的子节点
    DataRow[] nodeArray = getNodes();
    Action explorerAction = getExplorerViewActionGroup(nodeArray);
    if ( explorerAction != null ) GROUP_POPUP.add(explorerAction);
    // 获取上下文的Action
    Action[] actions   = ActionManager.getContextActions("DCT_VIEW",this,nodeArray);
    if ( actions != null ) {
      // 将Action增加到GROUP_POPUP
      for(int i=0;i<actions.length;i++) {
        GROUP_POPUP.add(actions[i]);
      }
    }
//    this.GROUP_Context.add(GROUP_FirstNode);
    if ( GROUP_POPUP.getActionCount() > 0 )
      return GROUP_POPUP;//GROUP_FirstNode;//GROUP_POPUP;
    else
      return null;
  }

  /**
   * mouseEntered
   *
   * @param e MouseEvent
   */
  public void mouseEntered(MouseEvent e) {
  }

  /**
   * mouseExited
   *
   * @param e MouseEvent
   */
  public void mouseExited(MouseEvent e) {
  }

  /**
   * mousePressed
   *
   * @param e MouseEvent
   */
  public void mousePressed(MouseEvent e) {
  }

  /**
   * mouseReleased
   *
   * @param e MouseEvent
   */
  public void mouseReleased(MouseEvent e) {
  }
  /**
   *
   * @return JdbTable
   */
  public JdbTable getDctTable() {
    return this.dctTable;
  }
  public void setToolbarVisible(boolean v) {
    pnDock.setVisible(v);
  }
  public boolean isToolbarVisible() {
    return pnDock.isVisible();
  }
}
