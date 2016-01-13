package com.efounder.eai.ui;

import com.efounder.pfc.window.*;
import javax.swing.*;
import com.efounder.eai.ui.action.*;
import com.efounder.action.*;
import javax.swing.event.*;
import com.efounder.eai.ide.*;
import com.efounder.comp.ToolBarDialog;
import com.efounder.eai.*;
import com.core.xml.*;
import com.efounder.node.view.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class JWindowPanel extends JViewPanel {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  public JWindowPanel() {
    super();
    this.setOpaque(false);
  }
  protected void addAction(IWindow cw) {
    // 注册一个WindowAction
    Action ac = new JWindowAction(this,cw,cw.getTitle(),'@',cw.getTips(),cw.getIcon());
    ActionGroup ag = (ActionGroup)this.getAction();
    if ( ag != null ) {
      ag.add(ac);
    }
  }
  protected void delActioin(IWindow cw) {
    ActionGroup ag = (ActionGroup)this.getAction();
    JWindowAction winAction=null;
    if ( ag != null ) {
      for(int i=0;i<ag.getActionCount();i++) {
        winAction = (JWindowAction)ag.getAction(i);
        if ( winAction.getWindow().equals(cw) ) {
          ag.remove(winAction);
          break;
        }
      }
    }
  }
  protected void unregistryWindow(IWindow cw) {
    // 将此窗口取消注册
    WindowManager.unregistryWindow(cw);
  }
  /**
   *
   */
  public void removeWindowAction() {
    // 将用户可定制的组清除掉
    EnterpriseExplorer.removeAllToolBarGroup(1);
    // 将用户可定制的组清除掉
    EnterpriseExplorer.removeAllToolBarGroup(2);
    EnterpriseExplorer.removeTopFrameActionGroup();

  }
  /**
   *
   * @param win IWindow
   */
  public void insertWindowAction(IWindow win) {
    // 2.将此窗口的工具条显示出来
    ActionGroup ag = (ActionGroup)win.getFrameAction();
    if ( ag != null && ag.getActionCount() > 0 ) {
      addWindowAction(ag);

    }
    ag = (ActionGroup)win.getTopFrameAction();
    if ( ag != null ) {
      EnterpriseExplorer.insertTopFrameActionGroup(ag);
    }

  }
  protected ToolBarDialog toolBarDialog = null;
  /**
   *
   * @param ag ActionGroup
   */
  protected void addWindowAction(ActionGroup ag) {
    ActionGroup ag1 = null;
    // 获取Toolbar1的ActionGroup
    if ( ag.getActionCount() > 0 ) {
        boolean isAdd = false;
      ag1 = (ActionGroup) ag.getAction(0);
      if (ag1 != null )
        for(int i=0;i<ag1.getActionCount();i++) {
            if ( !(ag1.getAction(i) instanceof ActionGroup )) continue;
            ActionGroup aggg = (ActionGroup)ag1.getAction(i);
            if ( aggg.isPopup() || aggg.getActionCount() > 0 ) {
                EnterpriseExplorer.addToolBarGroup(aggg, 1);
                isAdd = true;
            }
        }
      if ( isAdd )
        EnterpriseExplorer.getExplorer().showToolbar(1,true);
    }
    // 获取Toolbar2的ActionGroup
    if ( ag.getActionCount() > 1 ) {
      ag1 = (ActionGroup) ag.getAction(1);
      if (ag1 != null)
        for(int i=0;i<ag1.getActionCount();i++) {
          EnterpriseExplorer.addToolBarGroup((ActionGroup)ag1.getAction(i), 2);
        }
      if ( ag1.getActionCount() > 0 )
        EnterpriseExplorer.getExplorer().showToolbar(2,true);
    }
  }
  /**
   *2
   * @param e ChangeEvent
   */
  protected void activeEvent(ChangeEvent e) {
    // 如果上次的激活窗口存在则需要进行处理
    if ( LAST_ACTIVE_WINDOW != null ) {
      // 如果当前是最大化
      if ( LAST_ACTIVE_WINDOW.isMaxWindow() ) {
        // 先恢得到不是最大化状态
        byte[] _screenLayout = LAST_ACTIVE_WINDOW.getMaxLayoutData();
        if ( _screenLayout != null )
          dockableHolder.getDockingManager().setLayoutRawData(_screenLayout);
      }

      // １、调用丢失焦点事件
      //add by fsz 窗口切换时 把各个视图的状态保留
      Object o=JBOFClass.CallObjectMethod(EnterpriseExplorer.ViewDevice, "getViewSize");
      if(o!=null)LAST_ACTIVE_WINDOW.putObject(res.getString("BKSIZE"),o);


      //end
      LAST_ACTIVE_WINDOW.deactiveWindowEvent(this);
      // ２、清除框架工具条
      removeWindowAction();

    }
    // 获取当前激活的窗口
    IWindow NOW_ACTIVE_WINDOW = (IWindow)this.tbContent.getSelectedComponent();
    if ( NOW_ACTIVE_WINDOW != null ) {
      // 1.如果不为空,则调用其激活事件
      NOW_ACTIVE_WINDOW.activeWindowEvent(this);
      //add by fsz 窗口恢复时，恢复自己的视图信息
      Object o=NOW_ACTIVE_WINDOW.getObject(res.getString("BKSIZE1"),null);
      if(o!=null){
        JBOFClass.CallObjectMethod(EnterpriseExplorer.ViewDevice,
          "rstViewSize",o);
      }
      //end
      // 2.将此窗口的工具条显示出来
      insertWindowAction(NOW_ACTIVE_WINDOW);
//      // 2.将此窗口的工具条显示出来
//      ActionGroup ag = (ActionGroup)NOW_ACTIVE_WINDOW.getFrameAction();
//      if ( ag != null ) {
//        addWindowAction(ag);
//      }
    }
    LAST_ACTIVE_WINDOW = NOW_ACTIVE_WINDOW;
    //
    IWindow win = (IWindow)this.tbContent.getSelectedComponent();
    if ( win != null && !(win instanceof NodeWindow) ) { // 如果不为空，且不是NodeWindow类型，则需要处理最大化的问题
      if ( win.isMaxWindow() ) {
        // 先保存当前状态
        byte[] _screenLayout = EnterpriseExplorer.getExplorer().getDockableHolder().getDockingManager().getLayoutRawData();
        win.setMaxLayoutData(_screenLayout);
        // 再设为最大化
        dockableHolder.getDockingManager().autohideAll();
    }
    }

  }

}
