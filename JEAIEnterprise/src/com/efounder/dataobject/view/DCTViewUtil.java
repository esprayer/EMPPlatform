package com.efounder.dataobject.view;

import java.awt.*;
import com.efounder.node.*;
import com.borland.dx.dataset.*;
import com.efounder.dataobject.*;
import com.borland.dbswing.*;
import com.core.xml.StubObject;
import com.efounder.view.JRootExplorerView;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTViewUtil {
  public DCTViewUtil() {
  }
  /**
   *
   * @param obj Object
   * @return DCTView
   */
  public static DCTView findDctView(Object obj) {
      if(obj instanceof DCTView)
          return (DCTView)obj;
      if(obj instanceof Component)
          return findDctView(((Component)obj).getParent());
      else
          return null;
  }
  /**
   *
   * @param obj Object
   * @return GenerDCTNodeView
   */
  public static GenerDCTNodeView findDctNodeView(Object obj) {
      if(obj instanceof GenerDCTNodeView)
          return (GenerDCTNodeView)obj;
      if(obj instanceof Component)
          return findDctNodeView(((Component)obj).getParent());
      else
          return null;
  }
  /**
   *
   * @param dctNodeView GenerDCTNodeView
   */
  public static void reOpenDCTNode(GenerDCTNodeView dctNodeView) {
    JNodeStub nodeStub = dctNodeView.getNodeStub();
    if ( nodeStub != null ) {
      NodeStubUtil.openNodeObject(nodeStub,null,Context.getContext(nodeStub));
//      nodeStub.openObject(null,Context.getContext(nodeStub));
    }
  }
  /**
   *
   * @param findValue String
   * @param dctView DCTView
   */
  public static void setFindDCTNode(DCTView dctView,JdbNavField nvFind,String FieldName) {
    DataSet   dataSet   = dctView.getViewDataSet();
    nvFind.setDataSet(dataSet);
    nvFind.setColumnName(FieldName);
  }
  /**
   *
   * @param caption String
   * @param DCT_ID String
   * @param ViewID String
   * @param NodeID String
   * @return JRootExplorerView
   */
  public static JRootExplorerView getDCTExplorerViwe(String caption,String DCT_ID,String ViewID,String NodeID) {
    return getDCTExplorerViwe(caption,DCT_ID,null,ViewID,NodeID);
  }
  /**
   *
   * @param caption String
   * @param DCT_ID String
   * @param ViewID String
   * @param NodeID String
   * @return JRootExplorerView
   */
  public static JRootExplorerView getDCTExplorerViwe(String caption,String DCT_ID,String DCT_BM,String ViewID,String NodeID) {
    JRootExplorerView dctExplorerView = new JRootExplorerView();
    dctExplorerView.setID(ViewID);//DCT_ID+"_ExplorerView");
    StubObject stubObject = JRootExplorerView.getRootStubObject(NodeID);//"GenerDCTManagerNode");
    stubObject.setObject("caption",caption);
    stubObject.setObject("DCT_ID",DCT_ID);
    stubObject.setObject("DCT_BM",DCT_BM);
    dctExplorerView.initStubObject(stubObject);
    return dctExplorerView;
  }
  /**
   *
   * @param caption String
   * @param DCT_ID String
   * @return JRootExplorerView
   */
  public static JRootExplorerView getDCTExplorerViwe(String caption,String DCT_ID) {
    return getDCTExplorerViwe(caption,DCT_ID,null);
  }
  /**
   *
   * @param caption String
   * @param DCT_ID String
   * @return JRootExplorerView
   */
  public static JRootExplorerView getDCTExplorerViwe(String caption,String DCT_ID,String DCT_BM) {
    String ViewID = DCT_ID+"_ExplorerView";
    String NodeID = "GenerDCTManagerNode";
    return getDCTExplorerViwe(caption,DCT_ID,DCT_BM,ViewID,NodeID);
  }
}
