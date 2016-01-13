package com.efounder.service.start;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import com.borland.jbcl.layout.*;
import com.core.xml.*;
import com.efounder.comp.treetable.*;
import com.efounder.eai.ide.*;
import com.efounder.node.*;
import com.efounder.node.view.*;
import com.efounder.pfc.window.*;
import com.l2fprod.common.swing.*;
import com.efounder.comp.JImagePanel;
import com.efounder.service.security.ServiceSecurityManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EAIMenuView
    extends NodeViewerAdapter implements ActionListener,NodeContextActionBuilder{
	static ResourceBundle res = ResourceBundle.getBundle("com.efounder.service.start.Res");
  public EAIMenuView() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    vfl.setHgap(0);
    vfl.setVgap(0);
    panel.setLayout(vfl);
    jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
    this.add(jSplitPane1, java.awt.BorderLayout.CENTER);
    jSplitPane1.add(topjsp,JSplitPane.TOP);
    jSplitPane1.add(jsp,JSplitPane.BOTTOM);
    topjsp.getViewport().add(panel);
//     Paint paint = new GradientPaint(0.0F, 0.0F, Color.blue, 0.0F, 0.0F, new Color(0, 0, 64));

    panel.setBackground(Color.white);
  }

  BorderLayout borderLayout1 = new BorderLayout();
  JSplitPane jSplitPane1 = new JSplitPane();
  JImagePanel panel=new JImagePanel();
  ButtonGroup group = new ButtonGroup();
  JScrollPane jsp=new JScrollPane();
  JScrollPane topjsp=new JScrollPane();
  VerticalFlowLayout vfl=new VerticalFlowLayout();
  protected void setBackImage(JNodeStub menuNode) {
    StubObject stub = menuNode.getNodeStubObject();
    Icon image = null;String images = null;
    images = stub.getString("lefttop",null);
    if ( images != null ) {
      image = ExplorerIcons.getExplorerIcon(images);
      this.panel.setLeftTopImage(image);
    }
    images = stub.getString("righttop",null);
    if ( images != null ) {
      image = ExplorerIcons.getExplorerIcon(images);
      this.panel.setRightTopImage(image);
    }
    images = stub.getString("rightbottom",null);
    if ( images != null ) {
      image = ExplorerIcons.getExplorerIcon(images);
      this.panel.setRightBottomImage(image);
    }
    images = stub.getString("leftbottom",null);
    if ( images != null ) {
      image = ExplorerIcons.getExplorerIcon(images);
      this.panel.setLeftBottomImage(image);
    }
    panel.repaint();
  }
  public void initNodeViewer(Context context, Object p1, Object p2, IWindow nodeWindow) throws Exception {
    JNodeStub curnode = context.getNode();
    // 设置背景
    setBackImage(curnode);
    int count=curnode.getChildCount();
    int gp=count/5;
    int pos=0;
    JButtonBar toolbar=null;
    for(int i=0;i<gp+1;i++){
      for (int m = 0; m < 5&&pos<count; m++) {
        if(m==0){
          toolbar = new JButtonBar(JButtonBar.HORIZONTAL);
//          toolbar.setBackground(Color.yellow.brighter());
          toolbar.setOpaque(false);
          toolbar.setBorder(null);
//          toolbar.setVisible(false);
          panel.add(toolbar);
        }
        JNodeStub node = (JNodeStub) curnode.getChildAt(pos++);
        StubObject so = node.getNodeStubObject();
//        Action action = new AbstractAction() {
//          public void actionPerformed(ActionEvent e) {
//            //show(component);
//          }
//        };
        Icon icon=(Icon) so.getObject("icon", null);
        String bi=so.getString("bigIcon",null);
        if (bi != null && !"".equals(bi))
          icon=ExplorerIcons.getExplorerIcon(bi);
        JToggleButton button = new JToggleButton(so.getCaption(),icon);
        button.putClientProperty("NODE",node);
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(150,35));
        toolbar.add(button);
        group.add(button);

      }
    }
  }
  protected void getStubList(Vector vector,JNodeStub node,int level){
    StubObject so=node.getNodeStubObject();
    so.setLevel(level);
    so.setLostValue("NODEOBJECT",node);
    so.setIcon((Icon) so.getObject("icon", null));
    vector.add(so);
    if(node.getChildCount()>0){
      for (int i = 0; i < node.getChildCount(); i++) {
        JNodeStub node1 = (JNodeStub) node.getChildAt(i);
        String GNBH = node1.getSecurityKey();
        if (GNBH == null || "".equals(GNBH.trim()) ||
            ServiceSecurityManager.getDefault().checkPermission(GNBH)) {
          getStubList(vector, node1, level + 1);
        }
      }
    }else{
      java.util.List list=node.getChildNodeStubList();
      for(int i=0;i<list.size();i++){
        so=(StubObject)list.get(i);
        JNodeStub node1=JNodeStub.createNodeStub(so);
        String GNBH = node1.getSecurityKey();
        if (GNBH == null || "".equals(GNBH.trim()) ||
            ServiceSecurityManager.getDefault().checkPermission(GNBH)) {

          node1.setParent(node);
          node1.setExplorerView(node.getExplorerView());
          getStubList(vector, node1, level + 1);
        }
      }
    }
  }
  Map tabMap=new HashMap();
  public void actionPerformed(ActionEvent e) {
    JToggleButton bt=(JToggleButton)e.getSource();
    JNodeStub node=(JNodeStub)bt.getClientProperty("NODE");

    JTable table=(JTable)tabMap.get(node.getNodeID());
    if(table==null){
      table = new JTable();
      tabMap.put(node.getNodeID(),table);
      Vector list = new Vector();
      getStubList(list, node, 0);
      JStubTableModel aa = new JStubTableModel(list);
      aa.setNames(new String[] {res.getString("KEY")});
      aa.setTypes(new Class[] {StubObject.class});
      aa.setColumns(new String[] {"id"});
      table.setRowHeight(table.getRowHeight() + 5);
      table.setModel(aa);
      TableColumn tabCol = table.getColumnModel().getColumn(0);
      Icon icon = ExplorerIcons.ICON_CLASS;

      TableCellRenderer render = MenuCellRenderer.getInstance(icon);
      tabCol.setCellRenderer(render);

      // 处理鼠标事件Action
      ActionMouseAdapter.getInstance(this, table, this);
    }
    jsp.getViewport().removeAll();
    jsp.getViewport().add(table);
    table.setShowGrid(false);
  }

  public Object[] getNodes() {
    JTable  table=(JTable)KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
    StubObject so=(StubObject)table.getValueAt(table.getSelectedRow(),0);
    JNodeStub ns=(JNodeStub) so.getLostValue("NODEOBJECT",null);
    if(ns!=null&&ns.getChildNodeStubList()!=null&&ns.getChildNodeStubList().size()>0)
      return null;
    if(ns!=null)
      return new Object[]{ns};
    return null;
  }

  public Object getFrame() {
    Object explorerView = NodeViewerManager.findExporerView(this);
    return explorerView;
  }

  public Object getNodeKey() {
    return null;
  }

}
