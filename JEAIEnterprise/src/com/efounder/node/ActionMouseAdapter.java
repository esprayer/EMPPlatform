package com.efounder.node;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import com.efounder.actions.ActionManager;
import com.efounder.action.*;
import com.efounder.view.*;

public class ActionMouseAdapter implements MouseListener {
  /**
   *
   */
  protected ActionMouseAdapter() {
  }
  protected Object frame = null;
  protected JComponent comp = null;
  protected NodeContextActionBuilder nab = null;
  /**
   *
   * @param frame Object
   * @param comp JComponent
   * @param nab NodeContextActionBuilder
   * @return ActionMouseAdapter
   */
  public static ActionMouseAdapter getInstance(Object frame,JComponent comp,NodeContextActionBuilder nab) {
    ActionMouseAdapter mouseAdapter = new ActionMouseAdapter();
    comp.addMouseListener(mouseAdapter);
//    comp.putClientProperty("mouseAdapter",mouseAdapter);
    mouseAdapter.comp  = comp;
    mouseAdapter.frame = frame;
    mouseAdapter.nab = nab;
    return mouseAdapter;
  }
  /**
   *
   * @return Action
   */
  protected ActionGroup getContextAction() {
    Object nodeKey     = nab.getNodeKey();
    Object nodeFrame   = nab.getFrame();
    Object[] nodeStubs = nab.getNodes();
    ActionGroup ag = null;
    Action[] as = ActionManager.getContextActions(nodeKey,nodeFrame,nodeStubs);
    for(int i=0;as!=null&&i<as.length;i++) {
      if ( ag == null ) ag = new ActionGroup();
      ag.add(as[i]);
    }
    return ag;
  }
  protected void openNode() {
    Object[] nodeStubs = nab.getNodes();
    if ( nodeStubs == null || nodeStubs.length == 0 ) return;
    JNodeStub nodeStub = (JNodeStub)nodeStubs[0];
    Object nodeFrame   = nab.getFrame();
    Context context = new Context((JExplorerView)nodeFrame,nodeStub);
//    if ( nodeStub.allowOpen() )
      NodeStubUtil.openNodeObject(nodeStub,null,context);
//    if ( nodeStub.allowExec() )
//      nodeStub.execObject(nodeStub,null,context);
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
   * @param event MouseEvent
   */
  public void mouseReleased(MouseEvent e) {
    if ( frame instanceof MouseListener ) {
      ((MouseListener)frame).mouseReleased(e);
    }
    if ( ( e.getModifiers() & e.BUTTON3_MASK ) != 0 ) {
      processAction(e);
    }
  }
  /**
   *
   * @param e MouseEvent
   */
  protected void processAction(MouseEvent e) {
    if ( nab == null ) return;
    ActionGroup ag = this.getContextAction();
    Component invoker = e.getComponent();
    showPopupmenu(e.getPoint(),invoker,ag);
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseClicked(MouseEvent e) {
    if ( frame instanceof MouseListener ) {
      ((MouseListener)frame).mouseClicked(e);
    }
    if ( ( e.getModifiers() & e.BUTTON1_MASK ) != 0 && e.getClickCount() == 2 ) {
      openNode();
      return;
    }
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseEntered(MouseEvent e) {
    if ( frame instanceof MouseListener ) {
      ((MouseListener)frame).mouseEntered(e);
    }
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mouseExited(MouseEvent e) {
    if ( frame instanceof MouseListener ) {
      ((MouseListener)frame).mouseExited(e);
    }
  }
  /**
   *
   * @param e MouseEvent
   */
  public void mousePressed(MouseEvent e) {
    if ( frame instanceof MouseListener ) {
      ((MouseListener)frame).mousePressed(e);
    }
  }
}
