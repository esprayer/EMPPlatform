package com.efounder.eai.ui;

import java.awt.*;
import com.efounder.pfc.window.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class JViewDevicePanel extends JPanel implements IViewDevice {
  final int  LEFT_TOP = 0;
  final int  RIGHT_BOTTOM = 1;
  final int  DIV_SIZE = 4;
  BorderLayout borderLayout1 = new BorderLayout();
  JSplitPane spTop = new JSplitPane();
  JSplitPane spBottom = new JSplitPane();
  JSplitPane spContainer = new JSplitPane();
  JSplitPane spLeft = new JSplitPane();
  JSplitPane spRight = new JSplitPane();
  protected IView      TopView = null;
  protected IView      ExplorerView = null;
  protected IView      PropertyView = null;
  protected IView      StructView = null;
  protected IView      MessageView = null;
  protected IView      ContentView = null;
  IView      MaxView     = null;
  Action GROUP_View = null;

  int SIZE_SPTOP    = 0;
  int SIZE_SPBOTTOM = 0;
  int SIZE_SPLEFTTOP= 0;
  int SIZE_SPLEFT   = 0;
  int SIZE_SPRIGHT  = 0;
  /**
   *
   * @return IView
   */
  public IView getTopView() {
    return TopView;
  }
  /**
   *
   * @return IView
   */
  public IView getContentView() {
    return ContentView;
  }
  /**
   *
   * @return IView
   */
  public IView getStructView() {
    return StructView;
  }
  /**
   *
   * @return IView
   */
  public IView getPropertyView() {
    return PropertyView;
  }
  /**
   *
   * @return IView
   */
  public IView getMessageView() {
    return MessageView;
  }
  /**
   *
   * @return IView
   */
  public IView getExplorerView() {
    return ExplorerView;
  }

  public JViewDevicePanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    spTop.setOrientation(JSplitPane.VERTICAL_SPLIT);
    spBottom.setOrientation(JSplitPane.VERTICAL_SPLIT);
    this.setBorder(BorderFactory.createEtchedBorder());
    spLeft.setOrientation(JSplitPane.VERTICAL_SPLIT);
    this.add(spTop, BorderLayout.CENTER);
    spTop.add(spBottom, JSplitPane.BOTTOM);
    spBottom.add(spContainer, JSplitPane.TOP);
    spContainer.add(spRight, JSplitPane.RIGHT);
    spContainer.add(spLeft, JSplitPane.LEFT);
  }
  protected void initWorkSpace() {
    spTop.setDividerLocation(0.2);
//    spBottom.setDividerLocation(0.8);
    spContainer.setDividerLocation(0.25);
    spRight.setDividerLocation(0.75);
  }
  public JComponent getDeviceComponent() {
    return this;
  }
  public void openView(IView comp,int layer) {
    openView(comp,layer,IView.VIEW_TITLE|IView.VIEW_CLOSE);
  }
  public void openView(IView comp,int layer,int Style) {
//    openView(comp,layer,Style);
  }
  public void openView(IView comp,String layer) {
    openView(comp,layer,IView.VIEW_TITLE|IView.VIEW_CLOSE);
  }
  public double getLayerLocation(String layer,double def) {
    double res = def;
    // TOP
    if ( BorderLayout.NORTH.equals(layer) ) {
      // 不变
    }
    // BOTTOM
    if ( BorderLayout.SOUTH.equals(layer) ) {
      res = 1.00 - def;// 取反
    }
    // LEFT
    if ( BorderLayout.WEST.equals(layer) ) {
      // 不变
    }
    // RIGHT
    if ( BorderLayout.EAST.equals(layer) ) {
      res = 1.00 - def;// 取反
    }
    // CENTER
    if ( BorderLayout.CENTER.equals(layer) ) {
      // 不变
    }
    return res;
  }

  public void openView(IView comp,String layer,int Style) {
    comp.setStyle(Style);
    comp.setLayer(layer);
    comp.setViewDevice(this);
    // TOP
    if ( BorderLayout.NORTH.equals(layer) ) {
      TopView = comp; // 设置TopView，将ViewComponent设置到TopComponent
//      spTop.setTopComponent(TopView.getViewComponent());
    }
    // BOTTOM
    if ( BorderLayout.SOUTH.equals(layer) ) {
      MessageView = comp;
//      spBottom.setBottomComponent(MessageView.getViewComponent());
    }
    // LEFT
    if ( BorderLayout.WEST.equals(layer) ) {
      ExplorerView = comp;
//      spLeft.setLeftComponent(ExplorerView.getViewComponent());
    }
    // LEFT
    if ( LEFT_BOTTOM.equals(layer) ) {
      StructView = comp;
//      spLeft.setLeftComponent(ExplorerView.getViewComponent());
    }
    // RIGHT
    if ( BorderLayout.EAST.equals(layer) ) {
      PropertyView = comp;
//      spRight.setRightComponent(StructView.getViewComponent());
    }
    // CENTER
    if ( BorderLayout.CENTER.equals(layer) ) {
      ContentView = comp;
//      spRight.setLeftComponent(ContentView.getViewComponent());
    }
    // 注册一个视
    WindowManager.registryView(comp.getID(),comp);
  }
  private JSplitPane getSplitPane(IView comp) {
    JSplitPane sp = (JSplitPane)comp.getViewComponent().getParent();
    return sp;
  }
  public void setLocation(IView comp,int pos) {
    JSplitPane spPane = this.getSplitPane(comp);
    spPane.setDividerLocation(pos);
  }
  public void setLocation(IView comp,double pos) {
    JSplitPane spPane = this.getSplitPane(comp);
    if ( spPane == null ) return;
    int w = (int)(spPane.getWidth() * pos);
    spPane.setDividerLocation(pos);
  }
  public boolean isVisible(IView comp) {
    return comp.getViewComponent().getParent()==null?false:true;
  }
  protected boolean interVisible(IView comp,boolean v) {
    String layer = comp.getLayer();
    Component Comp = null;
    if ( v ) Comp = comp.getViewComponent();
    //设置Top的组件，
    if ( BorderLayout.NORTH.equals(layer) ) {
      if ( spTop.getTopComponent() == null && Comp != null ) spTop.setTopComponent(Comp);
      if ( spTop.getTopComponent() != null && Comp == null ) spTop.setTopComponent(Comp);
    }
    // 如果是左下
    if ( LEFT_BOTTOM.equals(layer) ) {
      if ( spLeft.getBottomComponent() == null && Comp != null ) spLeft.setBottomComponent(Comp);
      if ( spLeft.getBottomComponent() != null && Comp == null ) spLeft.setBottomComponent(Comp);
    }

    // 如果是Left
    if ( BorderLayout.WEST.equals(layer) ) {
      if ( spLeft.getTopComponent() == null && Comp != null ) spLeft.setTopComponent(Comp);
      if ( spLeft.getTopComponent() != null && Comp == null ) spLeft.setTopComponent(Comp);
    }
    // CENTER
    if ( BorderLayout.CENTER.equals(layer) ) {
      if ( spRight.getLeftComponent() == null && Comp != null ) spRight.setLeftComponent(Comp);
      if ( spRight.getLeftComponent() != null && Comp == null ) spRight.setLeftComponent(Comp);
    }
    // RIGHT
    if ( BorderLayout.EAST.equals(layer) ) {
      if ( spRight.getRightComponent() == null && Comp != null ) spRight.setRightComponent(Comp);
      if ( spRight.getRightComponent() != null && Comp == null ) spRight.setRightComponent(Comp);
    }
    // BOTTOM
    if ( BorderLayout.SOUTH.equals(layer) ) {
      if ( spBottom.getBottomComponent() == null && Comp != null ) spBottom.setBottomComponent(Comp);
      if ( spBottom.getBottomComponent() != null && Comp == null ) spBottom.setBottomComponent(Comp);
    }
    // 先处理 spRight
    if ( spRight.getLeftComponent() == null && spRight.getRightComponent() == null ) {
      // 如果左右都为空,则从spLeft中删除
      spContainer.setRightComponent(null);
    }
    if ( spRight.getLeftComponent() != null || spRight.getRightComponent() != null ) {
      // 如果左右有一个不为空或是都不为空，并且spLeft的右为空，则需设置
      if ( spContainer.getRightComponent() == null ) spContainer.setRightComponent(spRight);
    }
    if ( spRight.getLeftComponent() != null && spRight.getRightComponent() != null ) {
      // 如果左右都不为空，则需设置DIV_SIZE,否则设置为0
      spRight.setDividerSize(DIV_SIZE);
    } else {spRight.setDividerSize(0);}

    // 次处理spLeft

    if ( spLeft.getTopComponent() == null && spLeft.getBottomComponent() == null ) {
      // 如果左右都为空,则从spLeft中删除
      spContainer.setLeftComponent(null);
    }
    if ( spLeft.getTopComponent() != null || spLeft.getBottomComponent() != null ) {
      // 如果左右有一个不为空或是都不为空，并且spLeft的右为空，则需设置
      if ( spContainer.getLeftComponent() == null ) spContainer.setLeftComponent(spLeft);
    }
    if ( spLeft.getTopComponent() != null && spLeft.getBottomComponent() != null ) {
      // 如果左右都不为空，则需设置DIV_SIZE,否则设置为0
      spLeft.setDividerSize(DIV_SIZE);
    } else {spLeft.setDividerSize(0);}

    // 处理
    if ( spContainer.getLeftComponent() == null && spContainer.getRightComponent() == null ) {
      // 如果左右都为空,则从spBottom中删除
      spBottom.setTopComponent(null);
    }
    if ( spContainer.getLeftComponent() != null || spContainer.getRightComponent() != null ) {
      // 如果左右有一个不为空或是都不为空，并且spBottom的上为空，则需设置
      if ( spBottom.getTopComponent() == null ) spBottom.setTopComponent(spContainer);
    }
    if ( spContainer.getLeftComponent() != null && spContainer.getRightComponent() != null ) {
      // 如果左右都不为空，则需设置DIV_SIZE,否则设置为0
      spContainer.setDividerSize(DIV_SIZE);
    } else {spContainer.setDividerSize(0);}

    // 再处理spBottom
    if ( spBottom.getTopComponent() == null && spBottom.getBottomComponent() == null ) {
      // 如果上下都为空，则从spTop中删除
      spTop.setBottomComponent(null);
    }
    if ( spBottom.getTopComponent() != null || spBottom.getBottomComponent() != null ) {
      // 如果上下有一个不为空或是都不为空，并且spTop的下为空，则需设置
      if ( spTop.getBottomComponent() == null ) spTop.setBottomComponent(spBottom);
    }
    if ( spBottom.getTopComponent() != null && spBottom.getBottomComponent() != null ) {
      // 如果上下都不为空，则需设置DIV_SIZE,否则设置为0
      spBottom.setDividerSize(DIV_SIZE);
    } else {spBottom.setDividerSize(0);}

    // 最后处理spTop
    if ( spTop.getTopComponent() != null && spTop.getBottomComponent() != null ) {
      // 如果上下都不为空，则需设置DIV_SIZE,否则设置为0
      spTop.setDividerSize(DIV_SIZE);
    } else {spTop.setDividerSize(0);}
    clearMax_RstViewList();
    return true;
  }
  public void setVisible(IView comp,boolean v) {
    if ( interVisible(comp,v) ) return;
  }
  public Action getAction() {
    return GROUP_View;
  }
  public void setAction(Action a) {
    GROUP_View = a;
  }
  //add by fsz
  public void rstViewSize(int []sizes) {
     if ( sizes[0] != 0 ) {
       spTop.setDividerLocation(sizes[0]);
     }
     if ( sizes[1] != 0 ) {
       spBottom.setDividerLocation(sizes[1]);
     }
     if ( sizes[2] != 0 ) {
       spContainer.setDividerLocation(sizes[2]);
     }
     if ( sizes[3] != 0 ) {
       spRight.setDividerLocation(sizes[3]);
     }
     if ( sizes[4] != 0 ) {
       spLeft.setDividerLocation(sizes[4]);
     }
     if(TopView!=null && sizes[5]==1)
       TopView.setVisible(true);
     else
       TopView.setVisible(false);
     if(ExplorerView!=null&&sizes[6]==1)
       ExplorerView.setVisible(true);
     else
       ExplorerView.setVisible(false);

     if (PropertyView != null && sizes[7] == 1)
       PropertyView.setVisible(true);
     else
       PropertyView.setVisible(false);
     if (StructView != null && sizes[8] == 1)
       StructView.setVisible(true);
     else
       StructView.setVisible(false);
     if (MessageView != null && sizes[9] == 1)
       MessageView.setVisible(true);
     else
       MessageView.setVisible(false);
     if (ContentView != null && sizes[10] == 1)
       ContentView.setVisible(true);
     else
       ContentView.setVisible(false);


  }

  public Object getViewSize() {
    int sizes[] = new int[11];
    sizes[0] = 0;
    if (spTop.getTopComponent() != null && spTop.getBottomComponent() != null) {
      sizes[0] = spTop.getDividerLocation();
    }
    sizes[1] = 0;
    if (spBottom.getTopComponent() != null && spBottom.getBottomComponent() != null) {
      sizes[1] = spBottom.getDividerLocation();
    }
    sizes[2] = 0;
    if (spContainer.getLeftComponent() != null && spContainer.getRightComponent() != null) {
      sizes[2] = spContainer.getDividerLocation();
    }

    sizes[3] = 0;
    if (spRight.getLeftComponent() != null && spRight.getRightComponent() != null) {
      sizes[3] = spRight.getDividerLocation();
    }
    sizes[4] = 0;
    if (spLeft.getTopComponent() != null && spLeft.getBottomComponent() != null) {
      sizes[4] = spLeft.getDividerLocation();
    }
    if(TopView!=null&&TopView.isVisible())
      sizes[5]=1;
    if(ExplorerView!=null&&ExplorerView.isVisible())
      sizes[6]=1;
    if(PropertyView!=null&&PropertyView.isVisible())
      sizes[7]=1;
    if(StructView!=null&&StructView.isVisible())
      sizes[8]=1;
    if(MessageView!=null&&MessageView.isVisible())
      sizes[9]=1;
    if(ContentView!=null&&ContentView.isVisible())
      sizes[10]=1;


    return sizes;
  }
    //end
  private void saveMaxSize() {
    SIZE_SPTOP = 0;
    if ( spTop.getTopComponent() != null && spTop.getBottomComponent() != null ) {
      SIZE_SPTOP = spTop.getDividerLocation();
    }
    SIZE_SPBOTTOM = 0;
    if ( spBottom.getTopComponent() != null && spBottom.getBottomComponent() != null ) {
      SIZE_SPBOTTOM = spBottom.getDividerLocation();
    }
    SIZE_SPLEFT = 0;
    if ( spContainer.getLeftComponent() != null && spContainer.getRightComponent() != null ) {
      SIZE_SPLEFT = spContainer.getDividerLocation();
      //add b fsz EXPLOREVIEW太长，窗口最大化再恢复时看不到了
      if(SIZE_SPLEFT==spContainer.getWidth())
        SIZE_SPLEFT=(int)(spContainer.getWidth()*0.25);
    }else
      SIZE_SPLEFT=SIZE_SPLEFT=(int)(spContainer.getWidth()*0.25);
    SIZE_SPRIGHT = 0;
    if ( spRight.getLeftComponent() != null && spRight.getRightComponent() != null ) {
      SIZE_SPRIGHT = spRight.getDividerLocation();
    }
    SIZE_SPLEFTTOP = 0;
    if ( spLeft.getTopComponent() != null && spLeft.getBottomComponent() != null ) {
      SIZE_SPLEFTTOP = spLeft.getDividerLocation();
    }
  }
  private void rstMaxSize() {
    if ( SIZE_SPTOP != 0 ) {
      spTop.setDividerLocation(SIZE_SPTOP);SIZE_SPTOP = 0;
    }
    if ( SIZE_SPBOTTOM != 0 ) {
      spBottom.setDividerLocation(SIZE_SPBOTTOM);SIZE_SPBOTTOM = 0;
    }
    if ( SIZE_SPRIGHT != 0 ) {
      spRight.setDividerLocation(SIZE_SPRIGHT);SIZE_SPRIGHT = 0;
    }
    if ( SIZE_SPLEFTTOP != 0 ) {
      spLeft.setDividerLocation(SIZE_SPLEFTTOP);SIZE_SPLEFTTOP = 0;
    }
    if ( SIZE_SPLEFT != 0 ) {
      spContainer.setDividerLocation(SIZE_SPLEFT);SIZE_SPLEFT = 0;
    }
  }
  private void maxView(IView comp) {
    // 记录当前可视的View
    Vector list = getVisibleViewList(comp);
    saveMaxSize();
    // 不显示除comp外的其他View
    IView view;
    if ( list != null ) {
      for(int i=0;i<list.size();i++) {
        view = (IView)list.get(i);
        view.setVisible(false);
      }
    }
    MaxView = comp;
    VisibleViewList = list;
  }
  private void rstView(IView comp) {
    IView view;
    rstMaxSize();
    if ( VisibleViewList != null ) {
      Vector list = VisibleViewList;
      for(int i=0;i<list.size();i++) {
        view = (IView)list.get(i);
        view.setVisible(true);
      }
    }
    clearMax_RstViewList();

  }
  /**
   *
   * @param comp IView
   * @param stat int
   */
  public void setViewState(IView comp,int stat) {
    if ( (stat & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH ) {
      maxView(comp);
    }
    if ( (stat & Frame.NORMAL) == Frame.NORMAL ) {
      rstView(comp);
    }
    comp.setViewStatus(stat);
  }
  /**
   *
   * @param comp IView
   * @param stat int
   */
  public void setExtendedState(IView comp,int stat) {
    if ( (stat & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH ) {
      if ( MaxView != comp )
        maxView(comp);
      else
        rstView(comp);
    }
    comp.setViewStatus(stat);
//    if ( (stat & Frame.NORMAL) == Frame.NORMAL ) {
//      rstView(comp);
//    }
//    if ( (stat & Frame.NORMAL) == Frame.NORMAL ) {
//      rstView(comp);
//    }
  }
  private Vector VisibleViewList = null;
  private void clearMax_RstViewList() {
    VisibleViewList = null;
    MaxView = null;
  }
  private Vector getVisibleViewList(IView view) {
    Vector listVisibleViewList = new Vector();
    if ( TopView != null && TopView.isVisible() && view != TopView ) {
      listVisibleViewList.add(TopView);
    }
    if ( StructView != null && StructView.isVisible() && view != StructView ) {
      listVisibleViewList.add(StructView);
    }

    if ( ExplorerView != null && ExplorerView.isVisible() && view != ExplorerView ) {
      listVisibleViewList.add(ExplorerView);
    }
    if ( PropertyView != null && PropertyView.isVisible() && view != PropertyView ) {
      listVisibleViewList.add(PropertyView);
    }
    if ( MessageView != null && MessageView.isVisible() && view != MessageView ) {
      listVisibleViewList.add(MessageView);
    }
    if ( ContentView != null && ContentView.isVisible() && view != ContentView ) {
      listVisibleViewList.add(ContentView);
    }
    return listVisibleViewList;
  }
}
