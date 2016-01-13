package com.efounder.dataobject.view;

import com.efounder.node.view.*;
import javax.swing.*;
import com.efounder.eai.ide.*;
import java.awt.*;
import com.efounder.node.*;
import com.efounder.pfc.window.*;
import com.efounder.dataobject.node.*;
import com.efounder.dataobject.*;
import com.borland.dx.dataset.*;
import com.core.xml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class GenerDCTNodeView extends NodeViewerAdapter {

  /**
   *
   * @param dctView DCTView
   */
  public void setDctView(DCTView dctView) {
    this.dctView = dctView;
  }
  /**
   *
   * @param DCT_TYPE String
   */
  public void setDCT_TYPE(String DCT_TYPE) {
    this.DCT_TYPE = DCT_TYPE;
    if ( dctView != null )
      dctView.setDCT_TYPE(DCT_TYPE);
  }
  /**
   *
   * @return DCTView
   */
  public DCTView getDctView() {
    return dctView;
  }
  /**
   *
   * @return String
   */
  public String getDCT_TYPE() {
    return DCT_TYPE;
  }

  /**
   *
   */
  public GenerDCTNodeView() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }
  /**
   *
   */
  protected DCTView dctView = new DCTView();
  /**
   *
   */
  BorderLayout borderLayout1 = new BorderLayout();
  /**
   *
   * @return Icon
   */
  public Icon getViewerIcon() {
    if ( dctNodeDataRow != null )
      return dctNodeDataRow.getIcon(DCT_TYPE);
    return EnterpriseIcons.ICON_BROWSESYMBOL;
  }
  /**
   *
   * @return String
   */
  public String getViewerDescription() {
    if ( dctNodeDataRow != null )
      return dctNodeDataRow.getText(DCT_TYPE);
    return "统用数据字典视图";
  }
  /**
   *
   * @return String
   */
  public String getViewerTitle() {
    if ( dctNodeDataRow != null )
      return dctNodeDataRow.getText(DCT_TYPE);
    return "数据字典视图";
  }
  /**
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(dctView, BorderLayout.CENTER);
  }
  DCTNodeDataRow dctNodeDataRow = null;
  /**
   *
   * @param context Context
   */
  public void initDataStruct(Context context) {
    dctNodeDataRow = (DCTNodeDataRow)context.getNode();
    this.dctView.setDctObject(dctNodeDataRow.getDCTObjectDataRow(DCT_TYPE));
  }
  protected String DCT_TYPE = null;
  protected Component viewDockComp = null;
  protected Component topViewDockComp = null;
  /**
   *
   */
  protected void initViewDockBar(ViewDockbar viewDockbar) {
    viewDockComp = viewDockbar.getDockComp(DCT_TYPE);
    if ( viewDockComp != null ) {
      this.dctView.getDctToolBar().add(viewDockComp);
    }
    topViewDockComp = viewDockbar.getTopDockComp(DCT_TYPE);
    if ( topViewDockComp != null ) {
      this.dctView.pnCustomPanel.add(topViewDockComp,BorderLayout.CENTER);
    }
  }
  protected void setViewDocBar() {
    if ( viewDockComp != null )
      JBOFClass.CallObjectMethod(viewDockComp,"setDCTNodeView",this);
  }
  /**
   * 初始化NodeViewer
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @param nodeWindow IWindow
   */
  public void initNodeViewer(Context context,Object p1,Object p2,IWindow nodeWindow) {
    dctNodeDataRow = (DCTNodeDataRow)context.getNode();
    JNodeStub nodeStub = context.getNode();

//    DCTObject dctObject = dctNodeDataRow.getDCTObject();
    this.dctView.setDctNodeDataRow(dctNodeDataRow);
    if ( nodeStub instanceof ViewDockbar ) {
      initViewDockBar((ViewDockbar)nodeStub);
      setViewDocBar();
    }

//    DataSet   dataSet   = dctNodeDataRow.getDataSet();
//    dctView.setClientDataSet(dataSet);
  }
  /**
   * 重新初始化NodeViewer
   * @param context Context
   * @param p1 Object
   * @param p2 Object
   * @param nodeWindow IWindow
   * @throws Exception
   */
  public void reInitNodeViewer(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception {
    DCTNodeDataRow dctNodeDataRow = (DCTNodeDataRow)context.getNode();
//    DCTObject dctObject = dctNodeDataRow.getDCTObject();
    this.dctView.setDctNodeDataRow(dctNodeDataRow);
    setViewDocBar();
  }
}
