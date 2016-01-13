package jenterprise.bof.classes.AppExplorerObject;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import jcomponent.custom.swing.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;
import java.awt.event.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skylin
//修改:
//--------------------------------------------------------------------------------------------------
public class JRequestPanel extends JPanel implements IRequestRefresh{
  static ResourceBundle res = ResourceBundle.getBundle("jenterprise.bof.classes.AppExplorerObject.Language");
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel10 = new JPanel();
  BorderLayout borderLayout12 = new BorderLayout();
  JButton bnDelRequest = new JButton();
  BorderLayout borderLayout10 = new BorderLayout();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout15 = new BorderLayout();
  JButton bnViewRequest = new JButton();
  JLabel lbRequest = new JLabel();
  JPanel jPanel11 = new JPanel();
  JScrollPane jScrollPane3 = new JScrollPane();
  BorderLayout borderLayout14 = new BorderLayout();
  JButton bnStopRequest = new JButton();
  JPanel jPanel7 = new JPanel();
  JButton bnStartRequest = new JButton();
  JList lstRequestList = new JList();
  JPanel jPanel1 = new JPanel();
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JRequestPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void jbInit() throws Exception {
    jPanel10.setLayout(borderLayout15);
    this.setLayout(borderLayout1);
    jPanel6.setLayout(borderLayout12);
    jPanel6.setPreferredSize(new Dimension(156, 24));
    lbRequest.setForeground(Color.black);
    lbRequest.setText(res.getString("String_369"));
    jPanel11.setLayout(borderLayout14);
    jPanel7.setLayout(borderLayout10);
    bnStopRequest.addActionListener(new JRequestPanel_bnStopRequest_actionAdapter(this));
    bnStartRequest.addActionListener(new JRequestPanel_bnStartRequest_actionAdapter(this));
    bnDelRequest.addActionListener(new JRequestPanel_bnDelRequest_actionAdapter(this));
    bnViewRequest.addActionListener(new JRequestPanel_bnViewRequest_actionAdapter(this));
    jPanel1.setPreferredSize(new Dimension(4, 4));
    jPanel11.add(bnStopRequest, BorderLayout.CENTER);
    jPanel11.add(bnStartRequest, BorderLayout.EAST);
    jPanel6.add(jPanel1, BorderLayout.WEST);
    jPanel7.add(jPanel10, BorderLayout.EAST);
    jPanel7.add(jPanel11, BorderLayout.CENTER);
    jPanel10.add(bnDelRequest, BorderLayout.CENTER);
    jPanel10.add(bnViewRequest, BorderLayout.EAST);
    jPanel6.add(lbRequest, BorderLayout.CENTER);
    this.add(jScrollPane3, BorderLayout.CENTER);
    jScrollPane3.getViewport().add(lstRequestList, null);
    jPanel6.add(jPanel7, BorderLayout.EAST);
    this.add(jPanel6,  BorderLayout.NORTH);

    ProcessButton();
    InitRequest();
    GUIPrepare();
  }
  public void GUIPrepare(){
      lbRequest.setIcon(TImages.getIcon("MESSAGE"));
      }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private void ProcessButton() {
    ImageIcon II;Insets is = new Insets(2,2,2,2);
      try {
        //
        II = JXMLResource.LoadImageIcon(this,"start.gif");
        bnStartRequest.setIcon(II);
        bnStartRequest.setToolTipText(res.getString("String_372"));
        this.bnStartRequest.setMargin(is);
        II = JXMLResource.LoadImageIcon(this,"stop.gif");
        this.bnStopRequest.setIcon(II);
        this.bnStopRequest.setMargin(is);
        bnStopRequest.setToolTipText(res.getString("String_374"));
        II = JXMLResource.LoadImageIcon(this,"remove.gif");
        this.bnDelRequest.setIcon(II);
        this.bnDelRequest.setMargin(is);
        bnDelRequest.setToolTipText(res.getString("String_376"));
        II = JXMLResource.LoadImageIcon(this,"viewreq.gif");
        this.bnViewRequest.setIcon(II);
        this.bnViewRequest.setMargin(is);
        bnViewRequest.setToolTipText(res.getString("String_378"));

        DefaultListModel ListModel = new DefaultListModel();
        this.lstRequestList.setModel(ListModel);

        II = JXMLResource.LoadImageIcon(this,"request.gif");
        JIConListCellRenderer ICR = new JIConListCellRenderer(II);
        lstRequestList.setCellRenderer(ICR);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void InitRequest() {
    JActiveDComDM.DataActiveFramework.InvokeObjectMethod("LoginfoObject","AddRequestEvent",this);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void LoadRequestList(Vector RequestList) {
    DefaultListModel ListModel;JRequestStub RS,LRS;int i;
    Vector DelList = new Vector();
    ListModel = (DefaultListModel)lstRequestList.getModel();
    // 清除掉ListModel中存在，RequestList中不存在的
    for(i=0;i<ListModel.size();i++) {
      RS = (JRequestStub)ListModel.elementAt(i);
      LRS = FindKeyInRqList(RequestList,RS);
      if ( LRS == null ) {
        DelList.add(RS);
      } else {
        RS.BeginTime = LRS.BeginTime;
        RS.EndTime   = LRS.EndTime;
      }
    }
    for(i=0;i<DelList.size();i++) {
      RS = (JRequestStub)DelList.get(i);
      ListModel.removeElement(RS);
    }
    // 增加RequestList中存在，ListModel中不存在的
    for(i=0;i<RequestList.size();i++) {
      RS = (JRequestStub)RequestList.get(i);
      LRS = FindKeyInListModel(ListModel,RS);
      if ( LRS == null ) {
        ListModel.addElement(RS);
      } else {
        LRS.key = RS.key;
        LRS.name = RS.name;
        LRS.count = RS.count;
        LRS.status = RS.status;
        LRS.point  = RS.point;
        LRS.ResultString = RS.ResultString;
      }
    }
    this.lstRequestList.repaint();
//    lstRequestList.setModel(ListModel);
//    lstRequestList.repaint();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  JRequestStub FindKeyInRqList(Vector RequestList,JRequestStub RS) {
    JRequestStub LRS;
    for(int i=0;i<RequestList.size();i++) {
      LRS = (JRequestStub)RequestList.get(i);
      if ( LRS.key.equals(RS.key) ) {
        return LRS;//Res = true;break;
      }
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  JRequestStub FindKeyInListModel(DefaultListModel ListModel,JRequestStub RS) {
    JRequestStub LRS;
    for(int i=0;i<ListModel.size();i++) {
      LRS = (JRequestStub)ListModel.elementAt(i);
      if ( LRS.key.equals(RS.key) ) {
        return LRS;
      }
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void RequestRefresh(java.util.Vector RequestList,Object EventObject) {
    LoadRequestList(RequestList);
  }
  public void UserListRefresh(java.util.Vector RequestList,Object EventObject) {

  }
  int getOpreateRQ(JParamObject PO) {
//    DefaultListModel ListModel = (DefaultListModel)lstRequestList.getModel();
    JRequestStub RS = null;
    Object RSArray[] = lstRequestList.getSelectedValues();
    if ( RSArray == null ) return 0;
    PO.SetIntByParamName("RequestSize",RSArray.length);
    for(int i=0;i<RSArray.length;i++) {
      RS = (JRequestStub)RSArray[i];
      PO.SetValueByParamName("RKEY"+String.valueOf(i),RS.key);
    }
    return RSArray.length;
  }
  void bnStopRequest_actionPerformed(ActionEvent e) {
    JParamObject PO = JParamObject.Create();
    if ( getOpreateRQ(PO) == 0 ) return;
    PO.SetValueByParamName("OpreateRequest","STOP");
    JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject","OpreateRequest",PO);
    JActiveDComDM.DataActiveFramework.InvokeObjectMethod("LoginfoObject","Refresh");
  }

  void bnStartRequest_actionPerformed(ActionEvent e) {
    JParamObject PO = JParamObject.Create();
    if ( getOpreateRQ(PO) == 0 ) return;
    PO.SetValueByParamName("OpreateRequest","START");
    JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject","OpreateRequest",PO);
    JActiveDComDM.DataActiveFramework.InvokeObjectMethod("LoginfoObject","Refresh");

  }

  void bnDelRequest_actionPerformed(ActionEvent e) {
    JParamObject PO = JParamObject.Create();
    if ( getOpreateRQ(PO) == 0 ) return;
    PO.SetValueByParamName("OpreateRequest","DEL");
    JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject","OpreateRequest",PO);
    JActiveDComDM.DataActiveFramework.InvokeObjectMethod("LoginfoObject","Refresh");
  }

  void bnViewRequest_actionPerformed(ActionEvent e) {
    JParamObject PO = JParamObject.Create();
    if ( getOpreateRQ(PO) == 0 ) return;
    PO.SetValueByParamName("OpreateRequest","VIEW");
    JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("LoginfoObject","OpreateRequest",PO);
  }
}
//------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//------------------------------------------------------------------------------------------------
class JRequestPanel_bnStopRequest_actionAdapter implements java.awt.event.ActionListener {
  JRequestPanel adaptee;

  JRequestPanel_bnStopRequest_actionAdapter(JRequestPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnStopRequest_actionPerformed(e);
  }
}
//------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//------------------------------------------------------------------------------------------------
class JRequestPanel_bnStartRequest_actionAdapter implements java.awt.event.ActionListener {
  JRequestPanel adaptee;

  JRequestPanel_bnStartRequest_actionAdapter(JRequestPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnStartRequest_actionPerformed(e);
  }
}
//------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//------------------------------------------------------------------------------------------------
class JRequestPanel_bnDelRequest_actionAdapter implements java.awt.event.ActionListener {
  JRequestPanel adaptee;

  JRequestPanel_bnDelRequest_actionAdapter(JRequestPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnDelRequest_actionPerformed(e);
  }
}
//------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//------------------------------------------------------------------------------------------------
class JRequestPanel_bnViewRequest_actionAdapter implements java.awt.event.ActionListener {
  JRequestPanel adaptee;

  JRequestPanel_bnViewRequest_actionAdapter(JRequestPanel adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.bnViewRequest_actionPerformed(e);
  }
}
