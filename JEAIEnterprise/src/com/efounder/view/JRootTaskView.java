package com.efounder.view;

import javax.swing.*;

import com.core.xml.*;
import com.efounder.node.*;
import com.efounder.view.actions.*;
import com.l2fprod.common.swing.*;
//import com.l2fprod.common.swing.plaf.*;
//import com.l2fprod.common.swing.plaf.windows.*;

public class JRootTaskView extends JExplorerView {
  /**
   *
   */
  public JRootTaskView() {
    super(null);
  }
  /**
   *
   * @param rootSO StubObject
   */
  public void initStubObject(StubObject rootSO) {
    JNodeStub.initStubObject(rootSO);
    JNodeStub root = JNodeStub.createNodeStub(rootSO);
    root.setExplorerView(this);
    this.registryRoot(root);
    try {
      root.doRefresh();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    initTaskPane(RootNodeStub);
  }
//  protected JTaskPane taskPane = null;
  /**
   *
   */
  protected void initCustomPane() {

  }
  /**
   *
   * @param nodeStub JNodeStub
   */
  protected void initTaskPane(JNodeStub nodeStub) {
    LookAndFeel lookAndFeel = null;
    try {
      lookAndFeel = UIManager.getLookAndFeel();
//      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//      UIManager.put("win.xpstyle.name", "homestead");
//      LookAndFeelAddons.setAddon(WindowsLookAndFeelAddons.class);
//      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//      LookAndFeelAddons.setAddon(MetalLookAndFeelAddons.class);
    } catch ( Exception e ) {e.printStackTrace();}
//    taskPane = new JTaskPane();
//    spCONTENT.getViewport().remove(treeView);
//    spCONTENT.getViewport().add(taskPane, null);

    int count = nodeStub.getChildCount();JNodeStub childStub = null;
    JTaskPaneGroup childGroup = null;
    for(int i=0;i<count;i++) {
      childStub  = (JNodeStub)nodeStub.getChildAt(i);
      childGroup = new JTaskPaneGroup();
      childGroup.setTitle(childStub.toString());
      childGroup.setToolTipText(childStub.toString());
      childGroup.setSpecial(true);
      childGroup.setIcon(childStub.getNodeIcon());
      createChildTaskAction(childGroup,childStub);
//      taskPane.add(childGroup);
    }
    try {
//      UIManager.setLookAndFeel(lookAndFeel);
//      javax.swing.UIManager.getLookAndFeelDefaults().put("ClassLoader", lookAndFeel.getClass().getClassLoader());
    } catch ( Exception e ) {

    }
  }
  /**
   *
   * @param childGroup JTaskPaneGroup
   * @param nodeStub JNodeStub
   */
  protected void createChildTaskAction(JTaskPaneGroup childGroup,JNodeStub nodeStub) {
    int count = nodeStub.getChildCount();
    Action action = null;
    JNodeStub childStub = null;
    for(int i=0;i<count;i++) {
      childStub  = (JNodeStub)nodeStub.getChildAt(i);
      JNodeStub[] nss = {childStub};
      action = new OpenNodeAction(this,nss,null,childStub.toString(),'0',childStub.toString(),childStub.getNodeIcon());
      action.putValue(Action.NAME,childStub.toString());
      childGroup.add(action);
    }
  }
  /**
   *
   * @param o Object
   */
  public void initObject(Object o) {
    if ( o == null ) return;
    StubObject rootSO = getRootStubObject(o.toString());
    initStubObject(rootSO);
//    JNodeStub.initStubObject(rootSO);
//    JNodeStub root = JNodeStub.createNodeStub(rootSO);
//    root.setExplorerView(this);
////    initRootChildNode(root);
//    this.registryRoot(root);
////    TreePath tp = new TreePath(root);
//    try {
//      root.doRefresh();
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
  }
  protected void initRootChildNode(JNodeStub root) {
    java.util.List list = root.getChildNodeStubList();
    if ( list == null ) return;
    StubObject SO = null;
    JNodeStub childNode = null;
    for(int i=0;i<list.size();i++) {
      SO = (StubObject)list.get(i);
      childNode = root.createChildNode(root,SO.getString("id",null));
      root.add(childNode);
    }
  }
  /**
   *
   * @param rootName String
   * @return StubObject
   */
  public static StubObject getRootStubObject(String rootName) {
    StubObject SO = null;
    java.util.List childNodeList = PackageStub.getContentVector("RootNode");
    for(int i=0;i<childNodeList.size();i++) {
      SO = (StubObject)childNodeList.get(i);
      if ( rootName.equals(SO.getString("id",null)) ) {
        return SO;
      }
    }
    return SO;
  }
}
