package com.efounder.eai.ui;

import java.awt.*;
import javax.swing.*;
import java.net.*;
import com.efounder.view.*;
import com.efounder.action.*;
import com.efounder.eai.ide.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class HtmlPanel extends JPanel {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JEditorPane htmlView = new JEditorPane();
  ExplorerViewToolBarPane ToolBar = null;
  JToolBar jToolBar1 = new JToolBar();
  FlowLayout flowLayout1 = new FlowLayout();
  /**
   *
   */
  public HtmlPanel() {
    try {
      // 初始工具栏
      initToolBar();
      // 初始化
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   */
  private Action actionRefresh  = new ActiveObjectAction(this,htmlView,res.getString("refreshHtml"),res.getString("KEY8"),'0',"",ExplorerIcons.ICON_REFRESH);


  /**
   *
   */
  protected void initToolBar() {
    ToolBar = new ExplorerViewToolBarPane(this);
  }

  /**
   *
   * @throws Exception
   */
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    htmlView.setSelectionStart(8);
    jPanel1.setLayout(flowLayout1);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    flowLayout1.setHgap(0);
    flowLayout1.setVgap(0);
    this.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jToolBar1, null);
    this.add(jScrollPane1, BorderLayout.CENTER);
    jToolBar1.add(ToolBar, null);
    jScrollPane1.getViewport().add(htmlView, null);
  }
  /**
   * 设置新的URL地址
   * @param htmlUrl URL
   */
  public void setURL(URL htmlUrl) {
    try {
      htmlView.setPage(htmlUrl);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}
