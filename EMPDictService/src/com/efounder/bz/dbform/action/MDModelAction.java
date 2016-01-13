package com.efounder.bz.dbform.action;

import java.awt.event.*;
import javax.swing.*;

import org.openide.*;
import com.efounder.action.*;
import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.*;
import com.efounder.builder.meta.bizmodel.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.bz.dbform.bizmodel.*;
import com.efounder.bz.dbform.container.*;
import com.efounder.eai.ide.*;

/**
 *
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MDModelAction extends ActionAdapter{
  /**
   *
   */
  public MDModelAction() {
  }
  /**
   *
   */
  protected MDModel mdModel = null;
  /**
   *
   * @return MDModel
   */
  public MDModel getMDModel() {
    return mdModel;
  }
  /**
   *
   * @param MDModel MDModel
   */
  public void setMDModel(MDModel mdModel) {
    this.mdModel = mdModel;
  }
//  /**
//   *
//   */
//  protected FormCanvas createFormCanvas = null;
//  /**
//   *
//   * @return FormCanvas
//   */
//  public FormCanvas getCreateFormCanvas() {
//    return createFormCanvas;
//  }
//  /**
//   *
//   * @param createFormCanvas FormCanvas
//   */
//  public void setCreateFormCanvas(FormCanvas createFormCanvas) {
//    this.createFormCanvas = createFormCanvas;
//  }
  /**
   *
   * @param frame Object
   * @param nodeArray Object[]
   * @return Action
   */
  public Action getAction(Object frame, Object[] nodeArray) {
    return getActionGroup(frame,nodeArray);
  }
  /**
   *
   * @param dctMetaData DCTMetaData
   * @return EFDataSet
   */
//  protected static EFDataSet getMDL_LXDCT(DCTMetaData dctMetaData) {
//    EFDataSet dataSet = (EFDataSet)ESPHelpUtil.getHelpData(dctMetaData);
//    return dataSet;
//    return null;
//  }
  /**
   *
   * @return ActionGroup
   */
  protected ActionGroup getActionGroup(Object frame, Object[] nodeArray) {
    ActionGroup ag = new ActionGroup();
    Object[] params = {this};
    // 导航Action
    ActionGroup agg = getNavAction(frame, nodeArray);
    ag.add(agg);
    // 编缉Action
    return ag;
  }
  /**
   *
   * @param frame Object
   * @param nodeArray Object[]
   * @return ActionGroup
   */
  protected ActionGroup getNavAction(Object frame, Object[] nodeArray) {
    ActionGroup ag = new ActionGroup();
    Object[] params = {this};
    Action aoa = new ActiveObjectAction(this,params,"gotoFirst","第一张单据",'0',"第一张单据",ExplorerIcons.getExplorerIcon("beeload/moveleftall.gif"));
    ag.add(aoa);
    aoa = new ActiveObjectAction(this,params,"gotoPrior","上一张单据",'0',"上一张单据",ExplorerIcons.getExplorerIcon("beeload/moveleft.gif"));
    ag.add(aoa);
    aoa = new ActiveObjectAction(this,params,"gotoNext","下一张单据",'0',"下一张单据",ExplorerIcons.getExplorerIcon("beeload/moveright.gif"));
    ag.add(aoa);
    aoa = new ActiveObjectAction(this,params,"gotoLast","最末张单据",'0',"最末张单据",ExplorerIcons.getExplorerIcon("beeload/moverightall.gif"));
    ag.add(aoa);
    aoa = new ActiveObjectAction(this,params,"gotoSearch","查找单据",'0',"查找单据",ExplorerIcons.getExplorerIcon("beeload/check_16.gif"));
    ag.add(aoa);
    return ag;
  }
  /**
   *
   * @param actionObject Object
   * @param nodeArray Object[]
   * @param action Action
   * @param actionevent ActionEvent
   */
  public void gotoFirst(Object actionObject, Object[] nodeArray, Action action,ActionEvent actionevent) {
    MDModelAction formAction = (MDModelAction)actionObject;
    if ( formAction.mdModel == null ) return;
    try {
      // 创建一张新凭证
      formAction.mdModel.load();
    }
    catch (Exception ex) {
      ErrorManager.getDefault().notify(ex);
    }
  }
}
