package com.efounder.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import com.efounder.node.*;
import com.efounder.pfc.window.*;
import com.efounder.tree.*;
import com.efounder.action.*;
import java.util.*;
import com.efounder.eai.ide.*;
import com.efounder.view.actions.*;
import java.awt.event.*;
import com.efounder.actions.*;
import com.efounder.node.view.*;
import org.openide.*;
import com.core.xml.*;
import com.efounder.eai.ui.*;
import com.efounder.eai.*;
import org.openide.util.RequestProcessor.*;
import org.openide.util.*;
import com.efounder.eai.data.JParamObject;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0 TreeWillExpandListener,TreeExpansionListener,
 */

public class JExplorerView extends JChildWindow implements IWindow,MouseListener,TreeSelectionListener,TreeWillExpandListener {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.view.Res");
  BorderLayout borderLayout1 = new BorderLayout();
  protected JPanel pnTOP = new JPanel();
  JScrollPane spCONTENT = new JScrollPane();
  JExplorerTree treeView = null;
  ExplorerViewToolBarPane ToolBar = null;
  protected JNodeStub RootNodeStub          = null;
//  protected DefaultMutableTreeNode RootNode = null;
  JExplorerTreeModel TreeModel;
//  JToolBar jToolBar1 = new JToolBar();
  BorderLayout borderLayout2 = new BorderLayout();
  ArrayList ToolbarGroupArray = new ArrayList();
  /**
   *
   * @return boolean
   */
  public boolean canDeleteAction(){ return false;}
  /**
   *
   * @param so StubObject
   */
  public void initStubObject(StubObject so) {
    return;
  }


  protected boolean onlySelection = false;
  /**
   *
   * @return boolean
   */
  public boolean isOnlySelection() {
    return onlySelection;
  }
  /**
   *
   * @return JNodeStub
   */
  public JNodeStub getRootNodeStub() {
    return RootNodeStub;
  }
  /**
   *
   * @param s boolean
   */
  public void setOnlySelection(boolean s) {
    onlySelection = s;
  }
  /**
   *
   * @param RootNodeStub JNodeStub
   */
  public void setRootNodeStub(JNodeStub RootNodeStub) {
    this.RootNodeStub = RootNodeStub;
  }
  /**
   *
   */
  ActionGroup GROUP_Context   = new ExplorerActionGroup("Context...",'C',"Context",ExplorerIcons.ICON_ARRAY,true);
  ActionGroup GROUP_FirstNode = new ExplorerActionGroup(res.getString("KEY"),'F',res.getString("KEY1"),ExplorerIcons.ICON_PROJECT_GROUP,true);
  ActionGroup GROUP_ToolBar   = new ExplorerActionGroup("FileC",'C',"File toolbar");
  ActionGroup GROUP_POPUP     = new ExplorerActionGroup("PopupMenu",'P',"PopupMenu");
  ExplorerViewAction actionClose  = new ExplorerViewCommandAction(this,"close",res.getString("KEY2"),'F',"close",ExplorerIcons.ICON_CLOSE_PROJECT);
  ExplorerViewAction actionAdd  = new ExplorerViewCommandAction(this,"add",res.getString("KEY3"),'F',"add",ExplorerIcons.ICON_PROJECTADD);
  ExplorerViewAction actionDel  = new ExplorerViewCommandAction(this,"delete",res.getString("KEY4"),'F',"delete",ExplorerIcons.ICON_PROJECTREMOVE);
  ExplorerViewAction actionRefresh  = new ExplorerViewCommandAction(this,"refresh",res.getString("KEY5"),'F',"refresh",ExplorerIcons.ICON_REFRESH);
  /**
   *
   * @param ID String
   */
  public JExplorerView(String ID) {
    try {
      setID(ID);
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   */
  protected void initExplorerTreeView() {
    treeView = new JExplorerTree();
  }
  /**
   *
   */
  protected void initActionGroup() {
    GROUP_ToolBar.add(actionClose);
    GROUP_ToolBar.add(actionAdd);
    GROUP_ToolBar.add(actionDel);
    GROUP_ToolBar.add(actionRefresh);
    GROUP_ToolBar.add(GROUP_Context);
    GROUP_ToolBar.add(GROUP_FirstNode);
    ToolBar.addGroup(GROUP_ToolBar);
  }
  protected void initToolBar() {
    ToolBar = new ExplorerViewToolBarPane(this);
  }
  protected void initExplorerView() {
  }
 protected void jbInit() throws Exception {
    initToolBar();
//    jToolBar1.setBorder(null);
    initExplorerTreeView();
    this.setLayout(borderLayout1);
    pnTOP.setLayout(borderLayout2);
    this.add(pnTOP, BorderLayout.NORTH);
    pnTOP.add(ToolBar, BorderLayout.CENTER);
//    jToolBar1.add(ToolBar, null);
    this.add(spCONTENT,  BorderLayout.CENTER);
    spCONTENT.getViewport().add(treeView, null);
    initActionGroup();
    initEvent();
    initCtrl();
    initCustomPane();
  }
  protected void initCustomPane() {

  }
  protected void initCtrl() {
    this.getTreeView().setToggleClickCount(0);
  }
  /**
   * 初始化MouseEvent
   */
  protected void initEvent() {
    this.getTreeView().addMouseListener(this);
    this.getTreeView().addTreeSelectionListener(this);
    this.getTreeView().addTreeWillExpandListener(this);
  }
  public JExplorerTree getTreeView() {
    return getTreeView();
  }
  /**
   * Invoked whenever a node in the tree is about to be expanded.
   */
  public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
    TreePath tp = event.getPath();
    if ( !(tp.getLastPathComponent() instanceof JNodeStub) ) {
      callBackTreeWillExpand(event);
      return;
    }
    JNodeStub nodeStub = (JNodeStub)tp.getLastPathComponent();
    if ( nodeStub != null ) {
      try {
        WaitingManager.getDefault().beginWait(EAI.EA.getMainWindow());
        this.getTreeView().removeTreeWillExpandListener(this);
        nodeStub.doRefresh();// 装入
      } catch ( Exception e ) {
        ErrorManager.getDefault().notify(0,e,this);
      } finally {
        this.getTreeView().addTreeWillExpandListener(this);
        WaitingManager.getDefault().endWait(EAI.EA.getMainWindow());
      }
    }
  }
//  private void dyinsertNode(TreePath tp) {
//    this.TreeModel.dyinsertNode(tp);
//  }
//  /**
//   * Invoked whenever a node in the tree is about to be collapsed.
//   */
  public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

  }
//  /**
//    * Called whenever an item in the tree has been expanded.
//    */
//  public void treeExpanded(TreeExpansionEvent event) {
//    TreePath tp = event.getPath();
//    DefaultMutableTreeNode node = (DefaultMutableTreeNode)tp.getLastPathComponent();
//    dyinsertNode(tp);
//
//  }
//
//  /**
//    * Called whenever an item in the tree has been collapsed.
//    */
//  public void treeCollapsed(TreeExpansionEvent event) {
//
//  }
  public ActionGroup[] getToolBarGroups() {
    ActionGroup aactiongroup[] = new ActionGroup[ToolbarGroupArray.size()];
    aactiongroup = (ActionGroup[])(ToolbarGroupArray).toArray(aactiongroup);
    return aactiongroup;

  }
  public void showToolbar(boolean isshow) {
    if ( ToolBar != null )
      ToolBar.setVisible(isshow);
  }
  public void addToolBarGroup(int i1, ActionGroup actiongroup) {
    ToolbarGroupArray.add(i1, actiongroup);
    if ( ToolBar != null )
      ToolBar.addGroup(i1,actiongroup);
  }
  public void addToolBarGroup(ActionGroup actiongroup) {
    ToolbarGroupArray.add(actiongroup);
    if ( ToolBar != null )
      ToolBar.addGroup(actiongroup);
  }
  /**
   *
   * @param cap ContextActionProvider
   */
  public void registerContextActionProvider(ContextActionProvider cap) {
    super.registerContextActionProvider(this.getID(),cap);
  }
  protected boolean showWillExpand = true;
  protected boolean showExpanded   = true;
  public void setShowWillExpand(boolean v) {
    showWillExpand = v;
  }
  public void setShowExpanded(boolean v) {
    showExpanded = v;
  }
  public boolean isShowWillExpand() {
    return showWillExpand;
  }
  public boolean isShowExpanded() {
    return showExpanded;
  }
  /**
   *
   * @param nodestub JNodeStub
   * @return Object
   */
  public Object registryRoot(JNodeStub nodestub) {
    StubObject stubObject = nodestub.getNodeStubObject();
    if ( stubObject != null ) {
      String s = stubObject.getString("showWillExpand","1");
      showWillExpand = "1".equals(s);
      s = stubObject.getString("showExpanded","1");
      showExpanded = "1".equals(s);
    }
    this.getTreeView().initExplorerTree(nodestub);
    // 注册
//    regNodeActionProvider(nodestub);
    buildGROUP_FIRST_NODE();
    this.setRootNodeStub(nodestub);
    return nodestub;
  }
  public void setSelectionMode(int sm){
    this.getTreeView().getSelectionModel().setSelectionMode(sm);
  }
  public JNodeStub getSelectNode() {
    TreePath tp = this.getTreeView().getSelectionPath();
    if ( tp != null )
      return (JNodeStub)tp.getLastPathComponent();
    return null;
  }
  /**
   *
   * @param nodes JNodeStub[]
   */
//  private void regNodesActionProvider(JNodeStub[] nodes) {
//    JNodeStub node = null;
//    for(int i=0;i<nodes.length;i++) {
//      node = nodes[i];
//      regNodeActionProvider(node);
//    }
//  }
  /**
   *
   * @param node JNodeStub
   */
//  private void regNodeActionProvider(JNodeStub node) {
//    ContextActionProvider cap = node.getContextActionProvider();
//    if ( cap != null )
//      registerContextActionProvider(cap);
//    JNodeStub childnode = null;
//    for(int i=0;i<node.getChildCount();i++) {
//      childnode = (JNodeStub)node.getChildAt(i);
//      regNodeActionProvider(childnode);
//    }
//  }
  /**
   *
   * @param nodestub JNodeStub
   * @return Object
   */
  public Object registryChildNode(JNodeStub nodestub) {
    JNodeStub[] nsa = {nodestub};
    return registryChildNode(nsa)[0];
  }
  /**
   *
   * @param nodestubs JNodeStub[]
   * @return Object[]
   */
  public Object[] registryChildNode(JNodeStub[] nodestubs) {
    TreePath tp = getTreeView().findTreePath(getTreeView().getRootNode());// 以根节点生成路径
    Object[] res = registryChildNode(tp,nodestubs);
    buildGROUP_FIRST_NODE();
    return res;
  }
  ActionGroup fileNewGroup = null;
  /**
   *
   * @param nodestubs JNodeStub[]
   */
  protected void buildGROUP_FIRST_NODE() {
    GROUP_FirstNode.removeAll();
    if ( this.isOnlySelection() ) return;
    JNodeStub node;NodeAction na=null;
    if ( fileNewGroup != null )
      FileMenu.GROUP_FileNew.remove(fileNewGroup);
    else
      fileNewGroup = new ActionGroup();
    fileNewGroup.removeAll();
    Action ag = null;
    for(int i=0;i<getTreeView().getRootNode().getChildCount();i++) {
      node = (JNodeStub)getTreeView().getRootNode().getChildAt(i);
      na   = new NodeAction(this,node);
      GROUP_FirstNode.add(na);
      ag = getCreateActionGroup(this,node);
      if ( ag != null )
        fileNewGroup.add(ag);
      listFirstNodeHasnotDataManagerNode(node);
    }
    if ( fileNewGroup.getActionCount() > 0 )
      FileMenu.GROUP_FileNew.add(fileNewGroup);
  }
  public static Action getCreateActionGroup(JExplorerView ev,JNodeStub ns) {
    if ( ns.getNodeDataManager() == null ) return null;
    java.util.List list = ns.getChildNodeStubList();
    if ( list.size() == 0 ) return null;
    ActionGroup GROUP_Create   = new ExplorerActionGroup();;
    StubObject SO = null;Action createAction = null;
    for(int i=0;i<list.size();i++) {
      SO = (StubObject)list.get(i);String ID = SO.getString("id",null);String Name = SO.toString();
      createAction = new CreateNodeAction(ev,ns,ID,Name,'0',Name,(Icon)SO.getObject("icon",null),SO);
      if ( SO.getObject("nodeDataClass",null) == null )
        createAction.setEnabled(false);
      GROUP_Create.add(createAction);
    }
    return GROUP_Create;
  }

  private void listFirstNodeHasnotDataManagerNode(JNodeStub node) {
    getTreeView().removeTreeWillExpandListener(this);
    try {
      node.listHasnotDataManagerNode(); // 只将一级节点中的没有节点数据管理器的节点的下级节点
      JNodeStub childNode = null;Action ag = null;
      for(int i=0;i<node.getChildCount();i++) {
        childNode = (JNodeStub)node.getChildAt(i);
        ag = getCreateActionGroup(this,childNode);
        if ( ag != null )
          fileNewGroup.add(ag);
//          FileMenu.GROUP_FileNew.add(ag);
      }
    } catch ( Exception e ) {e.printStackTrace();} finally {
      getTreeView().addTreeWillExpandListener(this);
    }
  }
  /**
   *
   * @param parentNode JNodeStub
   * @param childNode JNodeStub
   * @return Object
   */
  public Object registryChildNode(JNodeStub parentNode,JNodeStub childNode) {
    TreePath tp = getTreeView().findTreePath(parentNode);
    JNodeStub[] nsa = {childNode};
    boolean isAutoExp = parentNode.isAutoExpend();
    Object[] os = registryChildNode(tp,nsa,isAutoExp);
    if ( os == null ) return null;
    return os[0];
  }
  public Object changeChildNode(JNodeStub parentNode,JNodeStub childNode) {
    TreePath tp = getTreeView().findTreePath(parentNode);
//    JNodeStub[] nsa = {childNode};
    return changeChildNode(tp,childNode)[0];
  }
  public Object removeChildNode(JNodeStub parentNode,JNodeStub childNode) {
    TreePath tp = getTreeView().findTreePath(parentNode);
//    JNodeStub[] nsa = {childNode};
    return removeChildNode(tp,childNode)[0];
  }


  /**
   *
   * @param parentNode JNodeStub
   * @param childNodes JNodeStub[]
   * @return Object[]
   */
  public Object[] registryChildNode(JNodeStub parentNode,JNodeStub[] childNodes) {
    TreePath tp = getTreeView().findTreePath(parentNode);
    boolean isAutoExp = parentNode.isAutoExpend();
    return registryChildNode(tp,childNodes,isAutoExp);
  }
  public Object[] registryChildNode(TreePath treepath,JNodeStub[] nodeStubArray) {
    return registryChildNode(treepath,nodeStubArray,true);
  }
  /**
   *
   * @param treepath TreePath
   * @param nodestub JNodeStub
   * @return Object
   */
  public Object[] registryChildNode(TreePath treepath,JNodeStub[] nodeStubArray,boolean isAutoExp) {
    Object[] res = getTreeView().getExplorerTreeModel().registryChildNode(treepath,nodeStubArray);
    if ( this.isShowExpanded() && isAutoExp )
      this.getTreeView().expandPath(treepath);
    JNodeStub ns = (JNodeStub)treepath.getLastPathComponent();
    if ( ns.getParent() == null ) {
      buildGROUP_FIRST_NODE();
    }
    // 注册
//    regNodesActionProvider(nodeStubArray);
    return res;
  }
  public Object[] changeChildNode(TreePath treepath,JNodeStub nodeStubArray) {
    Object[] res = getTreeView().getExplorerTreeModel().changeChildNode(treepath,nodeStubArray);
    this.getTreeView().expandPath(treepath);
    return res;
  }
  public Object[] removeChildNode(TreePath treepath,JNodeStub nodeStubArray) {
    Object[] res = getTreeView().getExplorerTreeModel().removeChildNode(treepath,nodeStubArray);
    this.getTreeView().expandPath(treepath);
    return res;
  }

  /**
   *
   * @return JNodeStub[]
   */
  public JNodeStub[] getSelectNodes() {
    JNodeStub[] res = null;
    TreePath[] tps = getTreeView().getSelectionPaths();
    if ( tps != null ) {
      res = new JNodeStub[tps.length];
      for(int i=0;i<tps.length;i++) {
        res[i] = (JNodeStub)tps[i].getLastPathComponent();
      }
    }
    return res;
  }
  /**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   */
  public void mouseClicked(MouseEvent e) {
    //add by fsz
    if(e.getSource()instanceof JExplorerTree && getTreeView()!=e.getSource()){
      treeView = (JExplorerTree) e.getSource();
       RootNodeStub= treeView.getRootNode();
      System.out.println("change treeview!");
    }
    TreePath tp = this.getTreeView().getSelectionPath();
    if ( tp == null ) return;
    if ( !(tp.getLastPathComponent() instanceof JNodeStub) ) {
      callBackMouseClicked(e);
      return;
    }
    if ( isOnlySelection() ) return;
    if ( ( e.getModifiers() & e.BUTTON3_MASK ) != 0 ) {
//      if ( getTreeView().getSelectionPath() == null ) return;
      Point p = e.getPoint();
//      SwingUtilities.convertPointToScreen(p, (Component)e.getSource());
      showPopupmenu(p,(Component)e.getSource());
    }
    if ( ( e.getModifiers() & e.BUTTON1_MASK ) != 0 && e.getClickCount() == 2 ) {
      if ( tp != null && tp.getLastPathComponent() instanceof JNodeStub) {
        JNodeStub ns = (JNodeStub)tp.getLastPathComponent();
        processExpandNode(tp,ns);
        Context context = new Context(this,ns);
        if (canOpenNodeStub(ns, context)) {
        	try {
        		 NodeStubUtil.openNodeObject(ns,null,context);
        	} catch(Exception ce) { 
        		ce.printStackTrace();
        	}
         
        } else {
          String errorMessage = context.getString("ErrorMessage", null);
          JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), errorMessage,
                                        "提示信息", JOptionPane.WARNING_MESSAGE, null);
        }
//        ns.openObject(null,context);
      }
    }
  }

  /**
   *
   * @param nodeStub JNodeStub
   * @param context Context
   * @return boolean
   */
  protected boolean canOpenNodeStub(JNodeStub nodeStub, Context context) {
    if (context.getNode() == null || context.getNode().getNodeStubObject() == null) return true;

    // 检查某些应用可以不做这样的检查

    java.util.List list = PackageStub.getContentVector("ProductUserManaLimit");
    if ( list != null ) {

      for(int i=0;i<list.size();i++) {
        StubObject so = (StubObject)list.get(i);
        if ( EAI.Product.equals(so.getID()) ) return true;
      }

    }

    String mana = nodeStub.getNodeStubObject().getString("MANA", "0");
    JParamObject PO = JParamObject.Create();
    String userMana = PO.GetValueByEnvName("UserMANA", null);
    if (userMana == null) userMana = PO.GetValueByEnvName("UserF_MANA", null);
    if (userMana == null) return true;
    if (mana.indexOf(userMana) == -1) {
      context.setString("ErrorMessage", "您无权使用此功能！");
      return false;
    }
    return true;
  }

  protected void processExpandNode(TreePath tp,JNodeStub nodeStub) {
    StubObject stub = nodeStub.getNodeStubObject();
    if ( stub == null ) {
      this.getTreeView().expandPath(tp);
      return;
    }
    Object nodeClass= stub.getObject("nodeClass",null) ;
    Object nodeDataClass=stub.getObject("nodeDataClass",null) ;
    Object nodeDataManagerClass=stub.getObject("nodeDataManagerClass",null) ;

    if ( nodeClass.equals(JNodeStub.class) && nodeDataClass==null && nodeDataManagerClass == null ) {
      this.getTreeView().expandPath(tp);
    }
  }

  private IWindowStatus getStatus() {
    return (IWindowStatus)System.getProperties().get("WindowStatus");
  }

  /**
   *
   * @param X int
   * @param Y int
   */
  protected void showPopupmenu(Point p,Component invoker) {
    ActionGroup popupMenu = getPopupMenuActionGroup();
    if ( popupMenu != null ) {
      ActionPopupMenu actionPopupMenu = new ActionPopupMenu(invoker, popupMenu, true);
      actionPopupMenu.show(invoker, p.x,p.y);
    }
  }
  /**
   * 导出的通用方法
   * @param nodeArray Object[]
   * @return ActionGroup
   */
//  public ActionGroup getContextActions(Object[] nodeArray) {
//    // 将GROUP_POPUP清空
//    ActionGroup ag = null;
//    // 获取上下文的Action
//    Action[] actions   = ActionManager.getContextActions(getID(),this,nodeArray);
//    if ( actions != null ) {
//      ag = new ActionGroup();
//      // 将Action增加到GROUP_POPUP
//      for(int i=0;i<actions.length;i++) {
//        ag.add(actions[i]);
//      }
//    }
//    return ag;
//  }
  /**
   *
   * @return ActionGroup
   */
  protected ActionGroup getPopupMenuActionGroup() {
    // 获取ID，以关键字作有
    String ID = getID();
    // 获取当前选择的子节点
    Object[] nodeArray = getNodes();
    GROUP_POPUP = NodeActionUtil.getContextActions(ID,this,nodeArray);//getContextActions(nodeArray);
    return GROUP_POPUP;
    /**
    // 将GROUP_POPUP清空
    GROUP_POPUP.removeAll();
    // 获取上下文的Action
    Action[] actions   = ActionManager.getContextActions(ID,this,nodeArray);
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
      return null;*/
  }
  /**
   *
   * @return Object[]
   */
  private Object[] getNodes() {
    TreePath[] Paths = getTreeView().getSelectionPaths();
    Object[]   Nodes = null;
    if ( Paths != null && Paths.length > 0 ) {
      Nodes = new Object[Paths.length];
      for(int i=0;i<Paths.length;i++) {
        Nodes[i] = Paths[i].getLastPathComponent();
      }
    }
    return Nodes;
  }
  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  public void mousePressed(MouseEvent e) {
    if ( ( e.getModifiers() & e.BUTTON3_MASK ) != 0 ) {
      getPathsForLocation(e.getX(), e.getY());
    }
  }

  /**
   * Invoked when a mouse button has been released on a component.
   */
  public void mouseReleased(MouseEvent e) {

  }
  /**
   *
   * @param x int
   * @param y int
   * @return TreePath[]
   */
  protected TreePath[] getPathsForLocation(int x,int y) {
    TreePath[] Paths = getTreeView().getSelectionPaths();
    TreePath   Path  = getTreeView().getPathForLocation(x,y);
    TreePath   tp    = null;int i=0;
    if ( Paths != null ) {
      for(i=0;i<Paths.length;i++) {
        tp = Paths[i];
        if ( tp.equals(Path) ) {
          break;
        }
      }
      if ( i == Paths.length ) {
        getTreeView().setSelectionPath(Path);
      }
    } else {
      getTreeView().setSelectionPath(Path);
    }
    return null;
  }
  /**
   * Invoked when the mouse enters a component.
   */
  public void mouseEntered(MouseEvent e) {

  }
  /**
   * Invoked when the mouse exits a component.
   */
  public void mouseExited(MouseEvent e) {

  }
  /**
   *
   * @param e TreeSelectionEvent
   */
  public void valueChanged(TreeSelectionEvent e) {
    TreePath[] tps = e.getPaths();
    if ( tps == null || tps.length == 0 ) return;
    if ( !(tps[0].getLastPathComponent() instanceof JNodeStub) ) {
      callBackValueChanged(e);
      return;
    }
    setContextAction(e.getPaths());
    setStatus();
  }
  /**
   *
   */
  public void setStatus() {
    TreePath tp = this.getTreeView().getSelectionPath();
    if ( tp != null ) {
      JNodeStub ns = (JNodeStub) tp.getLastPathComponent();
      IWindowStatus Status = getStatus();
      if( Status != null ) {
        Status.setText(ns.getLongText());
        Status.setIcon(ns.getNodeIcon());
      }
    }
  }
  protected void setContextAction(TreePath[] tps) {
    GROUP_Context.removeAll();
    ActionGroup popupMenu = getPopupMenuActionGroup();
    if ( popupMenu != null )
      GROUP_Context.add(popupMenu);
  }
  /**
   * 获取当前的节点
   * @return JNodeStub
   */
  public JNodeStub getActiveNode() {
    IWindow activeWindow = EnterpriseExplorer.ContentView.getActiveWindow();
    NodeChildWindow nodeWindow = null;JNodeStub nodeStub = null;
    if ( activeWindow instanceof NodeChildWindow ) {
      nodeWindow = (NodeChildWindow)activeWindow;
      nodeStub = nodeWindow.getNodeStub();
    }
    return nodeStub;
  }
  /**
   *
   * @return JExplorerView
   */
  public static JExplorerView getActiveExplorerView() {
    JExplorerView ev = null;IWindow activeWindow = null;
    activeWindow = EnterpriseExplorer.ExplorerView.getActiveWindow();
    if ( activeWindow instanceof JExplorerView ) {
      ev = (JExplorerView)activeWindow;
    }
    return ev;
  }
  /**
   *
   */
  protected Class callBackClazz = null;
  /**
   *
   * @param rootSO StubObject
   * @throws Exception
   */
  protected void callBackExplorerView(StubObject rootSO) throws Exception {
    String callBackClass = rootSO.getString("callBackClass",null);
    if ( callBackClass == null || "".equals(callBackClass.trim()) ) return;
    callBackClazz = this.getClass().forName(callBackClass);
    Object[] OArray = {this,getTreeView(),getTreeView().getExplorerTreeModel(),RootNodeStub};
    JBOFClass.CallClassMethod(callBackClazz,"callBackExplorerView",OArray);
  }
  /**
   *
   * @param event TreeExpansionEvent
   */
  protected void callBackTreeWillExpand(TreeExpansionEvent event) {
    if ( callBackClazz == null ) return;
    Object[] OArray = {this,getTreeView(),getTreeView().getExplorerTreeModel(),RootNodeStub,event};
    JBOFClass.CallClassMethod(callBackClazz,"callBackTreeWillExpand",OArray);
  }
  /**
   *
   * @param e MouseEvent
   */
  protected void callBackMouseClicked(MouseEvent e) {
    if ( callBackClazz == null ) return;
    Object[] OArray = {this,getTreeView(),getTreeView().getExplorerTreeModel(),RootNodeStub,e};
    JBOFClass.CallClassMethod(callBackClazz,"callBackMouseClicked",OArray);
  }
  /**
   *
   * @param event TreeSelectionEvent
   */
  protected void callBackValueChanged(TreeSelectionEvent event) {
    if ( callBackClazz == null ) return;
    Object[] OArray = {this,getTreeView(),getTreeView().getExplorerTreeModel(),RootNodeStub,event};
    JBOFClass.CallClassMethod(callBackClazz,"callBackValueChanged",OArray);
  }
  protected boolean isCanClose = true;
  /**
   *
   * @return boolean
   */
  public boolean canClose() {
    return isCanClose;
  }
  public void setCanClose(boolean v) {
    isCanClose = v;
  }
}
