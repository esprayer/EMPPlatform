package com.efounder.service.tree.view;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import com.efounder.service.tree.*;
import com.efounder.service.tree.node.*;

public class JTreeEx extends JTree implements TreeWillExpandListener,MouseListener{
  /**
   *
   */
  protected TreeNodeOpenEvent   openEvent = null;
  protected TreeNodeExecEvent   execEvent = null;
  protected TreeNodeActionEvent actionEvent = null;
  /**
   *
   * @return TreeNodeOpenExecute
   */
  public TreeNodeActionEvent getTreeNodeActionEvent() {
    return actionEvent;
  }
  /**
   *
   * @param openExec TreeNodeOpenExecute
   */
  public void setTreeNodeActionEvent(TreeNodeActionEvent actionEvent) {
    this.actionEvent = actionEvent;
  }
  /**
   *
   * @return TreeNodeOpenExecute
   */
  public TreeNodeExecEvent getTreeNodeExecEvent() {
    return execEvent;
  }
  /**
   *
   * @param openExec TreeNodeOpenExecute
   */
  public void setTreeNodeExecEvent(TreeNodeExecEvent execEvent) {
    this.execEvent = execEvent;
  }
  /**
   *
   * @return TreeNodeOpenExecute
   */
  public TreeNodeOpenEvent getTreeNodeOpenEvent() {
    return openEvent;
  }
  /**
   *
   * @param openExec TreeNodeOpenExecute
   */
  public void setTreeNodeOpenExecute(TreeNodeOpenEvent openEvent) {
    this.openEvent = openEvent;
  }
  /**
   *
   */
  protected TreeManager treeManager = null;
  /**
   *
   * @param treeManager TreeManager
   */
  public void setTreeManager(TreeManager treeManager) {
    this.treeManager = treeManager;
  }
  /**
   *
   * @return TreeManager
   */
  public TreeManager getTreeManager() {
    return treeManager;
  }
  /**
   *
   */
  public JTreeEx() {
    initTreeEvent();
  }
  protected void initTreeEvent() {
    this.addTreeWillExpandListener(this);
    this.addMouseListener(this);
  }
  /**
   *
   * @param event TreeExpansionEvent
   * @throws ExpandVetoException
   */
  public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

  }
  /**
   *
   * @param event TreeExpansionEvent
   * @throws ExpandVetoException
   */
  public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
    try {
      treeManager.expandTreeNode((ModelTreeNode)event.getPath().getLastPathComponent());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseClicked(MouseEvent e) {
    if ( ( e.getModifiers() & e.BUTTON3_MASK ) != 0 ) {
      processActionEvent();
    }
    if ( ( e.getModifiers() & e.BUTTON1_MASK ) != 0 && e.getClickCount() == 2 ) {
      processOpenExecEvent();
    }
  }
  /**
   *
   */
  protected void processActionEvent() {

  }
  /**
   *
   */
  protected void processOpenExecEvent() {
    TreePath tp = this.getSelectionPath();
    if ( tp == null ) return;
    ModelTreeNode treeNode = (ModelTreeNode)tp.getLastPathComponent();
    if ( openEvent != null ) openEvent.openTreeNode(this,treeNode);
    if ( execEvent != null ) execEvent.execTreeNode(this,treeNode);
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseEntered(MouseEvent e) {
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseExited(MouseEvent e) {
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mousePressed(MouseEvent e) {
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseReleased(MouseEvent e) {
  }
}
