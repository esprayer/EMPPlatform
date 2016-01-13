package com.efounder.dataobject.view;

import javax.swing.*;

import com.borland.dx.dataset.*;
import com.efounder.action.*;
import com.efounder.actions.*;
import com.efounder.dataobject.*;
import com.efounder.eai.ide.*;
import com.efounder.node.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTView extends OBJView {
  /**
   *
   */
  public DCTView() {
    super();
  }
  protected DCTObject dctObject = null;
  /**
   *
   */
  public DCTView(Icon icon,String sdctLable) {
    this(null,icon,sdctLable);
  }
  /**
   *
   * @param clientDataSet ClientDataSet
   * @param sdctLable String
   */
  public DCTView(DCTObject dctObject,String sdctLable) {
    this(dctObject,ExplorerIcons.ICON_FILEIMAGE,sdctLable);
  }
  /**
   * 完整的构造函数
   * @param dctObject DCTObject
   * @param icon Icon
   * @param sdctLable String
   */
  public DCTView(DCTObject dctObject,Icon icon,String sdctLable) {
    super(dctObject,icon,sdctLable);
  }
  /**
   *
   * @param dataRows DataRow[]
   * @return ActionGroup
   */
  protected ActionGroup getExplorerViewActionGroup(DataRow[] dataRows) {
    Context context = null;ActionGroup ag = null;
    if ( this.dctNodeDataRow != null ) {
      int[] Rows = this.getDctTable().getSelectedRows();
      context = dctNodeDataRow.getDataRowContext(DCT_TYPE,this.getDctTable().getDataSet(),dataRows,Rows,this);
      if ( context != null ) {
        Action[] as = ActionManager.getContextActions(context.getBrowser().getID(),context.getBrowser(),context.getNodeStubArray());
        if ( as != null && as.length != 0 ) {
          ag = new ActionGroup();
          for(int i=0;i<as.length;i++) {
            ag.add(as[i]);
          }
        }
      }
    }
    return ag;
  }
  protected void processDoubleClick() {
    DataRow[] nodeArray = getNodes();
    if (this.dctNodeDataRow != null) {
      int[] Rows = this.getDctTable().getSelectedRows();
      Context context = dctNodeDataRow.getDataRowContext(DCT_TYPE,getDctTable().getDataSet(), nodeArray, Rows,this);
      if ( context != null && context.getNodeStubArray().length > 0 ) {
        JNodeStub nodeStub = context.getNodeStubArray()[0];
        if ( nodeStub != null ) {
          Context ctx = new Context(nodeStub.getExplorerView(),nodeStub);
          NodeStubUtil.openNodeObject(nodeStub,null,ctx);
//          nodeStub.openObject(null,ctx);
        }
      }
    }
  }
}
