package com.efounder.service.tree.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import com.borland.jbcl.layout.*;
import com.efounder.pfc.window.*;
import com.efounder.service.tree.*;
import com.efounder.service.tree.node.*;
import com.borland.dx.dataset.DataSetData;
import java.util.List;
import com.efounder.eai.ide.EnterpriseExplorer;
import com.efounder.eai.EAI;

public class JTreeView extends JChildWindow {
  /**
   *
   */


  public JTreeView() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    pnTop.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    pnLft.setLayout(verticalFlowLayout1);
    verticalFlowLayout1.setHgap(0);
    verticalFlowLayout1.setVgap(0);
    pnRht.setLayout(verticalFlowLayout2);
    verticalFlowLayout2.setHgap(0);
    verticalFlowLayout2.setVgap(0);
    pnBom.setLayout(flowLayout2);
    flowLayout2.setHgap(0);
    flowLayout2.setVgap(0);
    this.add(pnTop, java.awt.BorderLayout.NORTH);
    this.add(pnLft, java.awt.BorderLayout.WEST);
    this.add(pnRht, java.awt.BorderLayout.EAST);
    this.add(pnBom, java.awt.BorderLayout.SOUTH);
    this.add(spCnt, java.awt.BorderLayout.CENTER);
    treeList.addTreeWillExpandListener(new TreeWillExpandAdapter());
    treeList.addMouseListener(new TreeMouseAdpater());
    spCnt.getViewport().add(treeList);
    treeList.setToggleClickCount(0);

  }

  BorderLayout borderLayout1 = new BorderLayout();
  JPanel pnTop = new JPanel();
  JPanel pnLft = new JPanel();
  JPanel pnRht = new JPanel();
  JPanel pnBom = new JPanel();
  FlowLayout flowLayout1 = new FlowLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  VerticalFlowLayout verticalFlowLayout2 = new VerticalFlowLayout();
  FlowLayout flowLayout2 = new FlowLayout();
  JScrollPane spCnt = new JScrollPane();
  JTree treeList = new JTreeEx();
  /**
   *
   * @return JTree
   */
  public JTree getTree() {
    return treeList;
  }
  protected TreeManager treeManager = null;
  /**
   *
   * @return TreeManager
   */
  public TreeManager getTreeManager() {
    return treeManager;
  }
  /**
   *
   * @param treeManager TreeManager
   */
  public void setTreeManager(TreeManager treeManager) {
    this.treeManager = treeManager;
  }
  // add by luody


  class TreeWillExpandAdapter implements TreeWillExpandListener{
    public void treeWillCollapse(TreeExpansionEvent event) throws
        ExpandVetoException {

    }
    public void treeWillExpand(TreeExpansionEvent event) throws
        ExpandVetoException {
      try {
       treeManager.expandTreeNode( (ModelTreeNode) event.getPath().
                                  getLastPathComponent());

     }
     catch (Exception ex) {
     }

    }

  }
  class TreeMouseAdpater implements MouseListener{
    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
      int selRow = treeList.getRowForLocation(e.getX(),e.getY());
      TreePath selectionPath = treeList.getSelectionPath();
      if (selRow != -1) {
        /*if (e.getClickCount() == 2) {
           ModelTreeNode selectedNode = (ModelTreeNode)selectionPath.getLastPathComponent();
          try {
            Object data = treeManager.expandNodeData(selectedNode,0);
            if(data != null){
               Object[] datas = (Object[])data;
               List fields = null;
               if(datas[0] != null){
                  fields = (List)datas[0];
               }
               DataSetData dsd = null;
               if(datas[1] != null){
                  dsd = (DataSetData)datas[1];
               }
               TreeNodeDataDialog tndd = new TreeNodeDataDialog(EAI.EA.getMainWindow(), "树节点数据显示", true);

               tndd.initTable(dsd,fields);
               tndd.CenterWindow();
               tndd.pack();
               tndd.setVisible(true);
              // EnterpriseExplorer.ContentView.openChildWindow(tndd);

            }
          }
          catch (Exception ex) {
          }
        }*/
      }

    }

    public void mouseReleased(MouseEvent e) {
    }

  }
  //end add
}
