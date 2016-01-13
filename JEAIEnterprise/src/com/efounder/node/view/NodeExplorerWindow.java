package com.efounder.node.view;

import com.efounder.pfc.window.*;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import com.borland.jbcl.layout.BoxLayout2;
import com.efounder.node.*;
import com.efounder.model.biz.BIZContext;
import java.util.List;
import com.efounder.eai.ide.*;
import javax.swing.*;
import com.core.xml.StubObject;
import java.util.Hashtable;
import java.awt.*;
import java.awt.event.*;
import com.borland.jbcl.layout.VerticalFlowLayout;
import com.efounder.eai.data.JParamObject;
import com.efounder.pfc.swing.JIConListCellRenderer;
import com.core.xml.JBOFClass;

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
public class NodeExplorerWindow extends JChildWindow implements BIZContext,ActionListener {
  /**
   *
   */
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnTop = new JPanel();
  JPanel pnContent = new JPanel();
  CardLayout cardLayout = new CardLayout();
//  JLabel lbTitle = new JLabel();
  JComboBox cbNodeViewList = new JComboBox();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  JPanel pnLft = new JPanel();
  JPanel pnRht = new JPanel();
  JPanel pnBom = new JPanel();
  /**
   *
   */
  protected JNodeStub nodeStub = null;
  protected NodeWindow nodeWindow = null;
  protected NodeViewer nodeView = null;
  /**
   *
   * @param nodeStub JNodeStub
   * @param nodeWindow NodeWindow
   * @return NodeExplorerWindow
   */
  public static NodeExplorerWindow getNodeExplorerWindow(JNodeStub nodeStub,
      NodeViewer nodeView) {
    NodeExplorerWindow win = new NodeExplorerWindow();
    win.nodeStub = nodeStub;
    win.nodeView = nodeView;
    win.nodeWindow = nodeView.getNodeWindow();
    nodeView.setNodeExpWindow(win);
    return win;
  }
  /**
   *
   */
  protected NodeExplorerWindow() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    pnContent.setLayout(cardLayout);
//    lbTitle.setText("testTitle");
    Icon icon = ExplorerIcons.getExplorerIcon("office/(01,06).gif");
//    lbTitle.setIcon(icon);
    pnTop.setLayout(boxLayout21);
    pnLft.setLayout(verticalFlowLayout1);
    verticalFlowLayout1.setHgap(0);
    verticalFlowLayout1.setVgap(0);
    pnRht.setLayout(verticalFlowLayout2);
    verticalFlowLayout2.setHgap(0);
    verticalFlowLayout2.setVgap(0);
    pnBom.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    this.add(pnTop, java.awt.BorderLayout.NORTH);
//    pnTop.add(lbTitle);
    pnTop.add(cbNodeViewList);
    this.add(pnContent, java.awt.BorderLayout.CENTER);
    this.add(pnLft, java.awt.BorderLayout.WEST);
    this.add(pnRht, java.awt.BorderLayout.EAST);
    this.add(pnBom, java.awt.BorderLayout.SOUTH);
    JIConListCellRenderer icr = new JIConListCellRenderer(null);
    this.cbNodeViewList.setRenderer(icr);
  }
  protected Context   nodeContext = null;
  protected NodeViewerFactory[] nodeViewerFactoryArray = null;
  protected NodeViewer[]        nodeViewerArray        = null;
  java.util.Map NodeViewList = new Hashtable();
  /**
   *
   * @param context Context
   * @param Key String
   * @param nvfs NodeViewerFactory[]
   */
  public void initNodeChildWindow(Context context,NodeViewerFactory[] nvfs,NodeViewer nodeView)  throws Exception {
    nodeViewerFactoryArray = nvfs;
    nodeContext = context;
    int index = 0;NodeViewer[] nvs = null;
    if ( nodeViewerFactoryArray == null ) return;
    nodeViewerArray = new NodeViewer[nvfs.length];
    nodeStub = context.getNode();// 设置当前Node
    for(int i=0;i<nodeViewerFactoryArray.length;i++) {
      NodeViewer nv = null;
      nvs = nodeViewerFactoryArray[i].createNodeViewers(context,context.getBrowser(),nodeView);
      for(int k=0;nvs!=null&&k<nvs.length;k++) {
        nv = nvs[k];
        if ( nv == null ) continue;
        nv.setNodeWindow(nodeWindow);
        nv.setNodeExpWindow(this);
        nv.setViewFactory(nodeViewerFactoryArray[i]); // 设置工厂
        // 设置初始化
        nv.setNodeViewer(context,context.getNode(),nodeViewerFactoryArray[i],this);//
        // 准备Viewer
        nv.prepareViewer(context,context.getNode(),nodeViewerFactoryArray[i],this);//
        // 增加到可视
        insertNodeView(nv,index++);
        nv.initNodeViewer(context,context.getNode(),nodeViewerFactoryArray[i],this);// 初始化
      }
    }
    LAST_ACTIVE_WINDOW = getSelectedNodeView();//(NodeViewer)tpNodeView.getSelectedComponent();
    initEvent();
  }
  /**
   *
   */
  void initEvent() {
    this.cbNodeViewList.addActionListener(this);
  }
  /**
   *
   * @param nv NodeViewer
   */
  protected void selectNodeView(NodeViewer nv) {
    for(int i=0;i<cbNodeViewList.getItemCount();i++) {
      NodeViewStub winStub = (NodeViewStub)cbNodeViewList.getItemAt(i);
      if ( winStub.getNodeView().equals(nv) ) {
        cbNodeViewList.setSelectedIndex(i);
        break;
      }
    }
  }
  /**
   *
   * @return NodeViewer
   */
  protected NodeViewer getSelectedNodeView() {
    NodeViewStub winStub = (NodeViewStub)this.cbNodeViewList.getSelectedItem();
    return (NodeViewer)winStub.getNodeView();
  }
  /**
   *
   * @param e ActionEvent
   */
  public void actionPerformed(ActionEvent e) {
    if ( e.getSource().equals(getStateChanged()) ) {
      actionCbxWindowList();
    }
  }
  /**
   *
   * @return Object
   */
  protected Object getStateChanged() {
    return cbNodeViewList;
  }
  /**
   *
   */
  private void actionCbxWindowList() {
    NodeViewStub winStub = (NodeViewStub)this.cbNodeViewList.getSelectedItem();
    if ( winStub != null ) {
      this.cardLayout.show(this.pnContent,winStub.getNodeView().getNodeViewID());
      activeNodeView();
    }
  }
  protected void activeNodeView() {
    activeEvent();
  }
  /**
   *
   * @param e ChangeEvent
   */
  NodeViewer LAST_ACTIVE_WINDOW = null;
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  protected void activeEvent() {
    if ( LAST_ACTIVE_WINDOW != null ) {
      LAST_ACTIVE_WINDOW.viewerDeactivated();
    }
    NodeViewer NOW_ACTIVE_WINDOW = (NodeViewer)this.getSelectedNodeView();
    if ( NOW_ACTIVE_WINDOW != null ) {
      NOW_ACTIVE_WINDOW.viewerActivated(true);
      LAST_ACTIVE_WINDOW = NOW_ACTIVE_WINDOW;
    }
    if ( this.getView() != null ) {
      this.getView().removeWindowAction();
      this.getView().insertWindowAction(this);
    }
  }
  /**
   *
   * @param comp JComponent
   * @return JComponent
   */
  private JComponent getChild(JComponent comp) {
    Component[] comps = pnContent.getComponents();
    for(int i=0;i<comps.length;i++) {
      if ( comps[i].equals(comp) ) return comp;
    }
    return null;
  }
  /**
   *
   */
  public void initView2() {
    java.util.List viewList = getViewList();
    if ( viewList == null || viewList.size() == 0 ) return;
    for(int i=0;i<viewList.size();i++) {
      NodeViewer nv = (NodeViewer)viewList.get(i);
      JBOFClass.CallObjectMethod(nv,"initView2");
    }
  }
  /**
   *
   * @param nv NodeViewer
   * @param index int
   */
  protected void insertNodeView(NodeViewer nv,int index) {
    JComponent comp = getChild(nv.getViewerComponent());
    if ( comp == null ) {
      pnContent.add(nv.getViewerComponent(), nv.getNodeViewID());
      NodeViewStub winStub = new NodeViewStub(nv);
      this.cbNodeViewList.insertItemAt(winStub,index);
      cbNodeViewList.setSelectedIndex(index);
    } else {
      for(int i=0;i<cbNodeViewList.getItemCount();i++) {
        NodeViewStub winStub = (NodeViewStub)cbNodeViewList.getItemAt(i);
        if ( winStub.getNodeView().equals(nv) ) {
          cbNodeViewList.setSelectedIndex(i);
          break;
        }
      }
    }
    cardLayout.show(this.pnContent,nv.getNodeViewID());
  }
  protected java.util.List getViewList() {
    java.util.List viewList = new java.util.ArrayList();
    for(int i=0;i<cbNodeViewList.getItemCount();i++) {
      NodeViewStub winStub = (NodeViewStub)cbNodeViewList.getItemAt(i);
      viewList.add(winStub.getNodeView());
    }
    return viewList;
  }
  /**
   *
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
  public class NodeViewStub {
    NodeViewer nodeView;String title;String tips;Icon icon;
    protected NodeViewStub(NodeViewer nodeView) {
      this.nodeView = nodeView;
      this.title = nodeView.getViewerTitle();
      this.tips = nodeView.getViewerDescription();
      this.icon = nodeView.getViewerIcon();
    }
    public String toString() {
      return title;
    }
    public Icon getIcon() {
      return icon;
    }
    public NodeViewer getNodeView() {
      return nodeView;
    }
  }
  public String getBIZUnit() {
    NodeViewer nv = this.getSelectedNodeView();
    if ( nv != null ) nv.getBIZContextUnit();
    return null;
  }

  public String getBIZType() {
    NodeViewer nv = this.getSelectedNodeView();
    if ( nv != null ) nv.getBIZContextType();
    return null;
  }

  public String getDateType() {
    NodeViewer nv = this.getSelectedNodeView();
    if ( nv != null ) nv.getBIZContextDateType();
    return null;
  }

  public String getBIZSDate() {
    NodeViewer nv = this.getSelectedNodeView();
    if ( nv != null ) nv.getBIZContextSDate();
    return null;
  }

  public String getBIZEDate() {
    NodeViewer nv = this.getSelectedNodeView();
    if ( nv != null ) nv.getBIZContextEDate();
    return null;
  }

  public Object getBIZValue(Object object, Object object1) {
    NodeViewer nv = this.getSelectedNodeView();
    if ( nv != null ) nv.getBIZContextValue(object,object1);
    return null;
  }

  public void setBIZValue(Object object, Object object1) {
    NodeViewer nv = this.getSelectedNodeView();
    if ( nv != null ) nv.setBIZContextValue(object,object1);
  }

  public void callBack(Object object, Object object1) {
    NodeViewer nv = this.getSelectedNodeView();
    if ( nv != null ) nv.bizContextCallBack(object,object1);
  }

  public void enumBIZKey(List list) {
    NodeViewer nv = this.getSelectedNodeView();
    if ( nv != null ) nv.bizContextEnumKey(list);
  }
  /**
   *
   * @param contextObject Object
   * @param addinObject Object
   */
  public final void   initBIZContext(Object sourceObject,Object contextObject,Object addinObject){
    java.util.List viewList = this.getViewList();
    NodeViewer nv = null;
    // NodeViewer 初始化过程
    for(int i=0;i<viewList.size();i++) {
      nv = (NodeViewer)viewList.get(i);
      nv.initBIZContext(sourceObject, contextObject, addinObject);
    }
  }
  public void   changeEvent(int eventType,Object sourceObject,Object contextObject,Object addinObject){
    java.util.List viewList = this.getViewList();
    NodeViewer nv = null;
    // NodeViewer 初始化过程
    for(int i=0;i<viewList.size();i++) {
      nv = (NodeViewer)viewList.get(i);
      nv.changeEvent(eventType,sourceObject,contextObject,addinObject);
    }
//    NodeViewer nv = this.getSelectedNodeView();
//    if ( nv != null )
//      nv.changeEvent(eventType,sourceObject,contextObject,addinObject);
  }
  public JParamObject convertParamObject(Object userObject,JParamObject po){return po;}
  public void setCallBackValue(Object key,Object value){}
  public java.util.Map getCallBackMap(){return null;}
  public void addBIZContext(BIZContext bizContext) {}

  public String getPrepareAttString() {
    return null;
  }
}
