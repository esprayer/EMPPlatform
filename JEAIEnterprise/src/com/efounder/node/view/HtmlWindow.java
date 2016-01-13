package com.efounder.node.view;

import com.efounder.pfc.window.*;
import javax.swing.JComponent;
import java.awt.BorderLayout;
import java.beans.PropertyChangeListener;
import com.efounder.eai.ide.EnterpriseIcons;
import com.efounder.node.JNodeStub;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.Action;
import com.efounder.node.Context;
import com.efounder.node.NodeDataStub;
import com.efounder.help.HelpUtil;
import com.efounder.eai.EAI;

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
public class HtmlWindow extends JChildWindow {
  BorderLayout borderLayout1 = new BorderLayout();
  HtmlBrowser htmlBrowser = null;
  public HtmlWindow() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  protected int getBrowserType() {
    return HtmlBrowser.NATIVE_HTML_BROWSER;
  }
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    htmlBrowser = new HtmlBrowser(getBrowserType());
//    htmlBrowser = new HtmlBrowser(HtmlBrowser.JAVA_HTML_BROWSER);
    this.add(htmlBrowser,BorderLayout.CENTER);
  }
  /**
   *
   */
  public void releaseViewer() {

  }
  public void browserDeactivated() {
//    closeDocumentView();
  }
  public void browserActivated() {
    activeDocumentView();
  }
  protected boolean activeDocument = false;
  protected URL getUrlPath() {
    return null;
  }
  protected void closeDocumentView() {
    htmlBrowser.closeURL();
  }
  /**
   *
   */
  protected void activeDocumentView() {
//    if ( activeDocument ) return;
    if ( htmlBrowser != null && htmlBrowser.getURL() != null ) return;
    URL url = getUrlPath();
    if ( url != null ) {
      htmlBrowser.setURL(url);
//      activeDocument = true;
    }
  }
  public void viewerDeactivated() {
//    closeDocumentView();
  }
  public void viewerDeactivating() {

  }
  public void viewerActivated(boolean boolean0) {
    activeDocumentView();
  }
  public void viewerNodeChanged() {

  }
  public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

  }
  public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

  }
  public IWindow[] getStructureComponent() {
    return null;
  }
  public JComponent getViewerComponent() {
    return this;
  }
  public Icon getViewerIcon() {
    return EnterpriseIcons.ICON_JBPROJECT;
  }
  public String getViewerDescription() {
    return "文档信息";
  }
  public String getViewerTitle() {
    return "文档";
  }
  /**
   * 获取上下文的Action
   * @return Action
   */
  public Action getContextAction() {
    return null;
  }
  /**
   * 获取主框架中的Action
   * @return Action
   */
  public Action getFrameAction() {
    return null;
  }
  /**
   * 获取右键弹出的Action
   * @return Action
   */
  public Action getPopupAction() {
    return null;
  }
  public Object getNodeDataObject(NodeDataStub nodeDataStub) {
    return null;
  }
  public static HtmlWindow getHtmlWindow() {
    return getHtmlWindow(null);
  }
  public static HtmlWindow getHtmlWindow(String uri) {
    if ( uri == null || uri.trim().length() == 0 ) return null;
    HtmlWindow hw = new HtmlWindow();
    hw.initObject(uri);
    return hw;
  }
  public boolean canOpen() {
    return (url!=null);
  }
  URL url = null;
  public void initObject(Object o) {
    if ( htmlBrowser == null ) return;
    String rootNode = null;
    if ( o == null )
      rootNode = (String)EAI.getEnv("welcomeURL",null);
    else
      rootNode = o.toString();
    try {
      if ( rootNode == null || rootNode.toCharArray().length == 0 ) return;
      url = new URL(rootNode);
      if (url != null)
        htmlBrowser.setURL(url);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}
