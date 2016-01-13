package com.efounder.view;

import java.awt.*;
import javax.swing.*;

import com.core.xml.*;
import com.efounder.eai.*;
import com.efounder.eai.ide.*;
import com.efounder.node.*;
import com.efounder.pub.util.*;
import com.l2fprod.common.swing.*;
import com.l2fprod.common.swing.plaf.windows.*;
import com.l2fprod.common.swing.plaf.*;
import com.efounder.view.actions.*;
import java.util.*;

import com.efounder.service.tree.TreeLoader;
import com.efounder.service.tree.TreeStubObject;
import com.efounder.tree.JExplorerTree;

import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import com.efounder.action.*;
import com.jidesoft.pane.FloorTabbedPane;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JRootExplorerView
    extends JExplorerView implements ChangeListener {
  /**
   *
   */
  public JRootExplorerView() {
    super(null);
  }

  protected void initBackImage(StubObject rootSO) {
    try {
      String value = rootSO.getString("backImage", null);
      Icon icon = ExplorerIcons.getExplorerIcon(value);
      this.treeView.setIconImage( (ImageIcon) icon);
      value = rootSO.getString("nodeSelectFrontColor", null);
      if (value != null) {
        Color color = StringFunction.getColorFromString(value);
        if (color != null) {
          treeView.setNodeSelectFrontColor(color);
        }
      }
      value = rootSO.getString("nodeFrontColor", null);
      if (value != null) {
        Color color = StringFunction.getColorFromString(value);
        if (color != null) {
          treeView.setNodeFrontColor(color);
        }
      }
      value = rootSO.getString("nodeSelectBackColor", null);
      if (value != null) {
        Color color = StringFunction.getColorFromString(value);
        if (color != null) {
          treeView.setNodeSelectBackColor(color);
        }
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
//    private Color nodeSelectFrontColor = Color.white;
//    private Color nodeFrontColor = Color.black;
//    private Color nodeSelectBackColor = Color.BLUE.darker();
  }

  public void initStubObject(StubObject rootSO) {
    initBackImage(rootSO);
    JNodeStub.initStubObject(rootSO);
    JNodeStub root = JNodeStub.createNodeStub(rootSO);
    root.setExplorerView(this);
    this.registryRoot(root);
    try {
      root.doRefresh();
      // ����CallBack���ƣ�����������ڵ�
      callBackExplorerView(rootSO);
      // ��ʼ��startView
      initStartContextView(root);
      // ��rootNodeView
      openRootNode(root);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void initStartContextView(JNodeStub root) {
    try {
      StubObject rootSO = root.getNodeStubObject();
      if ("1".equals(rootSO.getString("openStart", "0"))) {
        EAI.DOF.IOM(EAI.Product, "openStartWindow",
                    EnterpriseExplorer.getExplorer());
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   *
   * @param root JNodeStub
   */
  protected void openRootNode(JNodeStub root) {
    StubObject rootSO = root.getNodeStubObject();
    if ("1".equals(rootSO.getString("initOpen", "0"))) {
      NodeStubUtil.openNodeObject(root, null);
    }
  }

  public void initObject(Object o) {
    if (o == null) {
      initDBNode();
    }
    else {
      String root = (String) o;
      if (root.indexOf(",") != -1) {
        initObject(root.split(","));
      }
      else {
        Vector v = PackageStub.getContentVector(root);
        if (v == null || v.size() == 0) {
          StubObject rootSO = getRootStubObject(o.toString());
          if (rootSO == null)
            initDBNode();
          else
            initStubObject(rootSO);
        }
        else {
          StubObject rootSO = getRootStubObject(o.toString());
          initStubObject(rootSO);
          // ����TaskViewģʽ��ExplorerView
          //      processTaskView(rootSO, RootNodeStub);
        }
      }
    }
  }

  //����϶���taskView
  public void initObject(String[] roots) {

    processTaskView(roots);
  }

  protected void initDBNode() {
    String rootNode = (String) EAI.getEnv("rootNode", null);
    if (rootNode == null) {
      return;
    }
    // ��ȡ��װ����
    TreeLoader treeLoader = TreeLoader.getDefault();
    try {
      // װ����
      TreeStubObject treeStubObject = treeLoader.loadTree(rootNode);
      StubObject rootSO = treeStubObject.getRootNodeStubObject();
      initStubObject(rootSO);

//      processTaskView(rootSO, RootNodeStub);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean isTaskView(StubObject rootStub) {
    String isTaskView = rootStub.getString("taskView", "0");
    // �����ڵ���û�ж���ΪtaskViewģʽ��ExpolorerView��ʽ���򲻴���
    if ("0".equals(isTaskView)) {
      return false;
    }
    return true;
  }

  protected void processTaskView(String[] roots) {

    initTaskPane(roots);

  }

  // TaskView ��Pane JTaskPane
//  protected JOutlookBar taskPane = null;
  // FloorTabbedPane
  protected FloorTabbedPane taskPane = null;
  /**
   *
   */
  public FloorTabbedPane getTaskPane() {
    if (taskPane == null) {
//      taskPane = new JOutlookBar();
      taskPane = new FloorTabbedPane();
      taskPane.setBorder(null);
//      taskPane.setContentBorderInsets(new Insets(0,0,0,0));
    }
    return taskPane;
  }

  /**
   *
   */
  java.util.List treeList = new ArrayList();
  /**
   *
   */
  java.util.List treeNode = new ArrayList();
  /**
   *
   * @return JTree
   */
  public JExplorerTree getTreeView() {
    if ( taskPane != null ) {
      int index = taskPane.getSelectedIndex();
      if ( index >= 0 && index < treeList.size() ) {
        return (JExplorerTree)treeList.get(index);
      }
    }
    return treeView;
  }

  protected void initTaskPane(String[] roots) {
    getTaskPane();

    // ������Ӧ��λ��
    spCONTENT.getViewport().remove(treeView);
    spCONTENT.getViewport().add(taskPane, null);

    TreeLoader treeLoader = TreeLoader.getDefault();
    JExplorerTree bktree = treeView;
    JNodeStub nsbk = RootNodeStub;
    StubObject rootSO = null;
    try {
      for (int i = 0; i < roots.length; i++) {
        String menuString = roots[i];
        if ("".equals(menuString)) {
          continue;
        }

        if ("".equals(menuString)) {
          continue;
        }
        treeView = new JExplorerTree();
        initEvent();
        rootSO = getRootStubObject(menuString);
        try {
            if (rootSO == null) {
                TreeStubObject treeStubObject = treeLoader.loadTree(menuString);
                if ( treeStubObject == null ) continue;
                rootSO = treeStubObject.getRootNodeStubObject();
            }
            initStubObject(rootSO);
            treeList.add(treeView);
            treeNode.add(RootNodeStub);
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
      }

      for (int i = 0; i < treeList.size(); i++) {
        RootNodeStub = (JNodeStub) treeNode.get(i);
        treeView = (JExplorerTree) treeList.get(i);
        treeView.setRootVisible(false);
        if (i != treeList.size() - 1) {
          treeView.putClientProperty("OBJECT", treeList.get(treeList.size() - 1));
        }

        JScrollPane sb = new JScrollPane(treeView);//taskPane.makeScrollPane(treeView);
//        sb.getVerticalScrollBar().setPreferredSize(new Dimension(16, 0));
        taskPane.addTab(RootNodeStub.toString(), sb);
        taskPane.setIconAt(i, RootNodeStub.getNodeIcon());
      }
//      taskPane.seta
//      taskPane.setAnimated(true);
      buildGROUP_FIRST_NODE2();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      treeView = bktree;
      RootNodeStub = nsbk;
    }
//    taskPane.setSelectedIndex(taskPane.getTabCount()-1);
    taskPane.addChangeListener(this);
//    initTaskView(RootNodeStub);
//   UIManager.put("Button.background", cr);
//     backLookAndFeel(lookandFeel);
  }

  protected void backLookAndFeel(LookAndFeel lookandFeel) {
    try {
      UIManager.setLookAndFeel(lookandFeel);
    }
    catch (Exception e) {
    }
  }

  /**
   *
   * @param taskPane JTaskPane
   * @param rootStub StubObject
   */
  protected LookAndFeel initTaskPaneLookAndFell() {
    LookAndFeel lookAndFeel = null;
    try {
      lookAndFeel = UIManager.getLookAndFeel();
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      LookAndFeelAddons.setAddon(WindowsLookAndFeelAddons.class);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return lookAndFeel;
  }

  /**
   *
   * @param nodeStub JNodeStub
   */
  protected void initTaskView(JNodeStub nodeStub) {
    int count = nodeStub.getChildCount();
    JNodeStub childStub = null;
    JTaskPaneGroup childGroup = null;
    for (int i = 0; i < count; i++) {
      childStub = (JNodeStub) nodeStub.getChildAt(i);
      childGroup = new JTaskPaneGroup();
      childGroup.setTitle(childStub.toString());
      childGroup.setToolTipText(childStub.toString());
      childGroup.setSpecial(true);
      childGroup.setIcon(childStub.getNodeIcon());
      createChildTaskAction(childGroup, childStub);
      taskPane.add(childGroup);
    }
  }

  /**
   *
   * @param childGroup JTaskPaneGroup
   * @param nodeStub JNodeStub
   */
  protected void createChildTaskAction(JTaskPaneGroup childGroup,
                                       JNodeStub nodeStub) {
    int count = nodeStub.getChildCount();
    Action action = null;
    JNodeStub childStub = null;
    for (int i = 0; i < count; i++) {
      childStub = (JNodeStub) nodeStub.getChildAt(i);
      JNodeStub[] nss = {
          childStub};
      action = new OpenNodeAction(this, nss, null, childStub.toString(), '0',
                                  childStub.toString(), childStub.getNodeIcon());
      action.putValue(Action.NAME, childStub.toString());
      childGroup.add(action);
    }
  }

  protected void initRootChildNode(JNodeStub root) {
    java.util.List list = root.getChildNodeStubList();
    if (list == null) {
      return;
    }
    StubObject SO = null;
    JNodeStub childNode = null;
    for (int i = 0; i < list.size(); i++) {
      SO = (StubObject) list.get(i);
      childNode = root.createChildNode(root, SO.getString("id", null));
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
    for (int i = 0; i < childNodeList.size(); i++) {
      SO = (StubObject) childNodeList.get(i);
      if (rootName.equals(SO.getString("id", null))) {
        return SO;
      }
      SO=null;
    }
    return SO;
  }

  public void stateChanged(ChangeEvent e) {
    JScrollPane sb = (JScrollPane) taskPane.getSelectedComponent();
    if (treeView != sb.getViewport().getView()) {
      treeView = (JExplorerTree) sb.getViewport().getView();
      RootNodeStub = treeView.getRootNode();
      System.out.println("change treeview!");
    }
  }
  protected void buildGROUP_FIRST_NODE2() {
   GROUP_FirstNode.removeAll();
   JNodeStub node;
   NodeAction  na;
   ExplorerActionGroup nagroup;

//   Action ag = getOpenActionGroup((JExplorerView) frame, nodeArray);
//     ActionGroup group1   = new ExplorerActionGroup();
//     group1.add(ag);
//      GROUP_Gener.add(group1);

   int count=taskPane.getTabCount();
   for(int tab=0;tab<count;tab++){
     JScrollPane sb = (JScrollPane) taskPane.getComponentAt(tab);
     JExplorerTree tv = (JExplorerTree) sb.getViewport().getView();
      JNodeStub rn = tv.getRootNode();
      ActionGroup agg   = new ExplorerActionGroup(rn.toString(),' ',rn.toString(), rn.getNodeIcon(),true);;
      nagroup   = new ExplorerActionGroup();
     for (int i = 0; i <rn.getChildCount(); i++) {
       node = (JNodeStub)rn.getChildAt(i);
       na = new NodeAction(this, node);
       agg.add(na);
     }
     nagroup.add(agg);
     GROUP_FirstNode.add(nagroup);
   }
 }

}
