package com.efounder.dataobject.view;

import java.awt.*;
import javax.swing.*;

import com.efounder.node.view.*;
import com.efounder.eai.ide.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class GenerTABNodeView
    extends NodeViewerAdapter {
  /**
   *
   */
  protected OBJView objView = new OBJView();
  /**
   *
   */
  BorderLayout borderLayout1 = new BorderLayout();
  /**
   *
   */
  JPanel jPanel1 = new JPanel();
  /**
   * 设置DCTView
   * @param dctView DCTView
   */
  public void setObjView(OBJView objView) {
    this.objView = objView;
  }
  /**
   * 获取DCTView
   * @return DCTView
   */
  public OBJView getObjView() {
    return objView;
  }
  /**
   *
   */
  public GenerTABNodeView() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(objView, BorderLayout.CENTER);
  }
  /**
   *
   * @return Icon
   */
  public Icon getViewerIcon() {
    return EnterpriseIcons.ICON_WEBAPP_SERVLET_NEW;
  }
  /**
   *
   * @return String
   */
  public String getViewerDescription() {
    return "统用数据表视图";
  }
  /**
   *
   * @return String
   */
  public String getViewerTitle() {
    return "数据表视图";
  }

}
