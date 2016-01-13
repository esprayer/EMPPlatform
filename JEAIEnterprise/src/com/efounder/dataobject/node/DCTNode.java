package com.efounder.dataobject.node;

import java.util.*;

import javax.swing.*;

import org.openide.*;
import com.borland.dx.dataset.*;
import com.core.xml.*;
import com.efounder.dataobject.*;
import com.efounder.dbc.*;
import com.efounder.eai.*;
import com.efounder.eai.ide.*;
import com.efounder.node.*;
import com.efounder.model.biz.BIZContext;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DCTNode extends JNodeStub implements BIZContext {
  protected long internalRow = 0;
  protected DCTManagerNode dctManagerNode = null;
  /**
   *
   * @return ReadRow
   */
  public DataRow getDataRow() {
    return getDctNodeDataStub().getDataRow();
  }
  /**
   *
   * @param dataRow DataRow
   */
  public void setDataRow(DataRow dataRow) {
    getDctNodeDataStub().setDataRow(dataRow);
  }
  /**
   *
   * @return DCTNodeDataStub
   */
  public DCTNodeDataStub getDctNodeDataStub() {
    return (DCTNodeDataStub)this.getNodeDataStub();
  }
  /**
   * 获取节点数据管理器
   * @return DCTNodeDataManager
   */
  public DCTNodeDataManager getDctNodeDataManager() {
    return getDctManagerNode().getDctNodeDataManager();
  }
  /**
   *
   * @return DCTManagerNode
   */
  public DCTManagerNode getDctManagerNode() {
    return dctManagerNode;
  }

  public long getInternalRow() {
    return internalRow;
  }

  /**
   *
   * @return String
   */
  public String getDCT_ID() {
    return getDctNodeDataStub().getDctObject().getDCT_ID();
  }
  /**
   *
   * @param key String
   * @param context Context
   * @return Object
   */
  public Object openObject(String key,Context context) {
    return super.openObject(key,context);
  }
  /**
   *
   * @return String
   */
  public String getDCTMC() {
    return this.getDctNodeDataStub().getDCTMC();
  }
  /**
   *
   * @return String
   */
  public String getDCTBH() {
    return this.getDctNodeDataStub().getDCTBH();
  }
  /**
   *
   * @return int
   */
  public int getDCTJS() {
    return this.getDctNodeDataStub().getDCTJS();
  }
  /**
   *
   * @return String
   */
  public String getDCTMX() {
    return this.getDctNodeDataStub().getDCTMX();
  }
  /**
   *
   * @return DCTObject
   */
  public DCTObject getDCTObject() {
    return getDctNodeDataStub().getDctObject();
  }

  /**
   *
   * @param dctObject DCTObject
   */
//  public void setDCTObject(DCTObject dctObject) {
//    this.dctObject = dctObject;
//    try {
//
//      DCT_DATA = dctObject.getTabObject().getTAB_DATA();
//    } catch ( Exception e ) {
//      ErrorManager.getDefault().notify(0,e);
//    }
//  }

  public void setDctManagerNode(DCTManagerNode dctManagerNode) {
    this.dctManagerNode = dctManagerNode;
  }

  public void setInternalRow(long internalRow) {
    this.internalRow = internalRow;
  }

  /**
   *
   * @param DCT_DATA ClientDataSet
   * @param readRow ReadRow
   */
  public DCTNode() {
    loaded = false;
  }
  public NodeDataManager getNodeDataManager() {
    return null;
  }
  /**
   *
   * @return DCTNode
   */
  public DCTNode getDCTNode() {
    DCTNode dctNode = null;
    try {
      dctNode = (DCTNode)this.getClass().forName(this.getClass().getName()).newInstance();
    } catch ( Exception e ) {
      ErrorManager.getDefault().notify(0,e);
    }
    return dctNode;
  }
  /**
   *
   * @throws Exception
   */
  public void doLoad() throws Exception {

  }
  /**
   *
   * @return Icon
   */
  public Icon          getNodeIcon() {
    Icon icon = getIcon();
    if ( icon == null )
     icon = ExplorerIcons.ICON_CVS_FILE_CHECKIN;
    return icon;
  }
  /**
   *
   * @return Icon
   */
  private Icon getIcon() {
    Vector DCTList = PackageStub.getContentVector(this.getDCT_ID());
    if ( DCTList == null ) {
      return null;//ExplorerIcons.ICON_CLASS;
    }
    StubObject SO = findDCTData(DCTList,getDCTBH());
    if ( SO == null ) return null;
    Icon icon = ExplorerIcons.getImageIcon(this, "/" + EAI.Product + "/Resource",SO.getString("icon",null), null);//ExplorerIcons.getExplorerIcon(Tmp);
    return icon;
  }
  /**
   *
   * @param DCTList Vector
   * @param BM String
   * @return StubObject
   */
  private StubObject findDCTData(Vector DCTList,String BM) {
    StubObject SO = null;BM = "BM"+BM;
    for(int i=0;i<DCTList.size();i++) {
      SO = (StubObject)DCTList.get(i);
      if ( BM.equals(SO.getStubID()) ) {
        return SO;
      }
    }
    return null;
  }
  /**
   *
   * @return String
   */
  public String toString() {
    if ( this.getParentNode() instanceof DCTNode )
      return this.getDctNodeDataStub().getCurrentBH()+"-"+getDCTMC();
    else
      return this.getDCTBH()+"-"+getDCTMC();
  }
  /**
   *
   * @return Icon
   */
  public Icon getOpenIcon() {
    return getNodeIcon();
  }
  /**
   *
   * @return Icon
   */
  public Icon getClosedIcon() {
    return getNodeIcon();
  }
  /**
   *
   */
  public void listHasnotDataManagerNode() {

  }

  public String getBIZUnit() {
    return null;
  }

  public String getBIZType() {
    return null;
  }

  public String getDateType() {
    return null;
  }

  public String getBIZSDate() {
    return null;
  }

  public String getBIZEDate() {
    return null;
  }

  public Object getBIZValue(Object key, Object def) {
    if ( "BIZContextNode".equals(key) ) {
      return this;
    }
    return null;
  }

  public void setBIZValue(Object key, Object value) {
  }

  public void callBack(Object callObject, Object context) {
  }
  public void   enumBIZKey(java.util.List keyList){}
}
