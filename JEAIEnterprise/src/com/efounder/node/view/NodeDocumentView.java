package com.efounder.node.view;

import java.beans.*;
import java.net.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.Action;

import com.efounder.eai.ide.*;
import com.efounder.help.*;
import com.efounder.node.*;
import com.efounder.pfc.window.*;
import org.jdic.web.event.BrComponentEvent;
import org.jdic.web.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NodeDocumentView extends NodeViewerAdapter implements NodeViewer,Runnable,BrComponentListener {
  public NodeDocumentView() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    this.initSystemLayout();
  }
  private org.jdic.web.BrComponent browser = null;

  /**
   * createBrowser
   */
  public void createBrowser() {
    org.jdic.web.BrComponent.DESIGN_MODE = false;
    browser = new org.jdic.web.BrComponent();
    this.add(browser,BorderLayout.CENTER);
    browser.addBrComponentListener(this);
    browser.setURL("http://www.sohu.com");
  }

  /**
   *
   */
  protected JNodeStub nodeStub = null;
  /**
   *
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @param nodeWindow IWindow
   */
  public void initNodeViewer(Context context,Object p1,Object p2,IWindow nodeWindow) {
    nodeStub = context.getNode();
  }
  /**
   *
   * @param nw NodeWindow
   */
  public void nodeViewClose(NodeWindow nw) {
//    SwingUtilities.invokeLater(this);
  }

  /**
   *
   */
  public void releaseViewer() {

  }
  /**
   *
   */
  public void browserDeactivated() {
//    closeDocumentView();
  }
  /**
   *
   */
  public void browserActivated() {
    if ( this.browser != null ) return;
    SwingUtilities.invokeLater(this);
  }
  public void run() {
    createBrowser();
  }
  /**
   *
   * @return URL
   */
  protected URL getUrlPath() {
    String name = nodeStub.getDocName();
    URL url = HelpUtil.getDocUrl(name);
    if ( url == null ) {
      url   = HelpUtil.getDocUrl(JNodeStub.HelpNotFound);
    }
    return url;
  }
  public void viewerDeactivated() {
  }
  public void viewerDeactivating() {

  }
  public void viewerActivated(boolean boolean0) {
    if ( this.browser != null ) return;
    SwingUtilities.invokeLater(this);
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
    return "文档视图";
  }
  public String getViewerTitle() {
    return "文档";
  }
  /**
   * ��ȡ�����ĵ�Action
   * @return Action
   */
  public Action getContextAction() {
    return null;
  }
  /**
   * ��ȡ�����е�Action
   * @return Action
   */
  public Action getFrameAction() {
    return null;
  }
  /**
   * ��ȡ�Ҽ���Action
   * @return Action
   */
  public Action getPopupAction() {
    return null;
  }
  public Object getNodeDataObject(NodeDataStub nodeDataStub) {
    return null;
  }
  public void removeUserComponent() {
    if ( this.browser != null ) {
//      browser.setVisible(false);

//      this.remove(this.browser);
//      browser = null;
    }
  }
  /**
   *
   * @param isChildWindow boolean
   */
  public void onClosingWindowByScript(boolean isChildWindow) {
    if(!isChildWindow && JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
        this,
        "The webpage you are viewing is trying to close the window.\n" +
        "Do you want to close this window?",
        "Warning",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE)) {

    }
  }

  /**
   *
   * @param e BrComponentEvent
   * @return String
   */
  public String sync(BrComponentEvent e) {
    switch(e.getID()) {
      case BrComponentEvent.DISPID_WINDOWCLOSING:
        String stValue = e.getValue();
        if(null != stValue) {
          //OLE boolean: -1 - true, 0 - false; params:(bCancel, bIsChildWindow)
          Integer o = new Integer(0);
          final boolean isChildWindow = (!o.equals(Integer.valueOf(stValue.split(",")[1])));
          SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              onClosingWindowByScript(isChildWindow);
            }
          });
        }
        break;
    }
    return null;
  }
}
