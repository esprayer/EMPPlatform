package com.efounder.form.client.component;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.efounder.action.*;
import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.component.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.eai.ide.*;
import com.efounder.form.client.component.pic.*;

/**
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

public class FormPicLabel extends JLabel implements DataSetListener, FormComponent,ActionListener,DMComponent{
  JButton button = new JButton();
  JLabel titleLabel = new JLabel();
  protected JLabel numberLabel = new JLabel();
  int opType = 0;//默认是0:可以维护附件, 1:查看附件
  int viewType = 0; //查看类型-0：直接查看、1查看列表
  int standard = 0;//是否加载标准格式-0： 是、1：否
  boolean extendScreen = true;// 扩展到分屏上
  protected String MDL_ID=null;//需要根据MDL_ID找配制变量
  public FormPicLabel() {
      jInit();
  }
  public void setMDL_ID(String mdlid){
	  MDL_ID=mdlid;
  }
  public String getMDL_ID(){
	  return MDL_ID;
  }
  protected  void jInit(){
      initLabel();
//    initButton();
      initActionButton();
      addComp();
  }

  protected void addComp(){
    this.setLayout(new BorderLayout());
    this.add(titleLabel,BorderLayout.WEST);
    this.add(numberLabel,BorderLayout.CENTER);
    this.add(button,BorderLayout.EAST);
    this.setBorder(new TitledBorder(""));
  }

  protected void initLabel(){
    titleLabel.setText("影像：");//上报
    numberLabel.setText("0  个");
    numberLabel.setHorizontalAlignment(JLabel.CENTER);
  }

  protected void initActionButton(){
      Action ac = new  ActiveObjectAction(this,this,"popupAffixView","",'0',"",ExplorerIcons.getExplorerIcon("office/(01,49).gif"));
      button = ActionButton.getActionButton(this,ac);
  }

  public void popupAffixView(Object actionObject, Object nodeArray, Action action,
                         ActionEvent actionevent) {
      openPicAttachManagerWindow(this);
  }

  protected void initButton(){
    button.setText(getButtonText());
//    button.setMargin(new Insets(0,0,0,0));
    button.setSize(new Dimension(250,30));
    button.addActionListener(this);
//    button.setIcon(ExplorerIcons.getExplorerIcon("FrssIcon/upload.gif"));//uparrow.gif
    if(opType == 1){//查看
        button.setIcon(ExplorerIcons.getExplorerIcon("expand_tree.gif"));
    }else{
        button.setIcon(ExplorerIcons.getExplorerIcon("uparrow.gif"));
    }
  }
  protected String getButtonText(){
      if(opType == 1){
          return "查看";
      }
      return "上传";
  }

  protected void setButtonText(){
      button.setText(getButtonText());
  }

  public void setNumber(int number){
    numberLabel.setText(number+"  个");
  }

  public FormContainer getFormContainer() {
    return null;
  }

  public JComponent getJComponent() {
    return this;
  }

  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == button){
        openPicAttachManagerWindow(this);
    }
  }
  //附件窗口，打开附件的时候要判断，如果该窗口已打开则不用打开。
  Component affixWindow = null;


  public void openPicAttachManagerWindow(FormPicLabel picLabel){
      try{
          //如果已经打开则不用再打开
          if(affixWindow != null){
              affixWindow.requestFocus();
              return;
          }
          if (opType == 1) {
              //查看附件
              if (viewType == 1) {
//                  JPicAttachFunction.openNewAffixWorkspace(picLabel);
                  return;
              } else {
//                  JPicAttachFunction.viewAffixWorkspace(picLabel,extendScreen);
                  return;
              }
          }
          //维护附件
//          JPicAttachFunction.openNewAffixWorkspace(picLabel);
      }catch(Exception e){
          e.printStackTrace();
      }
  }

  /**
   *
   */
  protected DataSetComponent dataSetComponent = null;
  /**
   *
   * @return DataSetComponent
   */
  public DataSetComponent getDataSetComponent() {
    return dataSetComponent;
  }
  /**
   *
   * @param dsc DataSetComponent
   */
  public void setDataSetComponent(DataSetComponent dsc) {
    if ( dataSetComponent != dsc ) {
      if ( dataSetComponent != null ) dataSetComponent.removeDMComponent(this);
      dataSetComponent =  dsc;
      if ( dataSetComponent != null ) dataSetComponent.insertDMComponent(this);
    }
  }


  /**
   *
   */
  protected String dataSetID = null;
  /**
   *
   * @param dataSetID String
   */
  public void setDataSetID(String dataSetID) {
    this.dataSetID = dataSetID;
    if ( dataSetComponent != null )
      setDataSet(dataSetComponent.getDataSet(dataSetID));
  }

  /**
   *
   * @return String
   */
  public String getDataSetID() {
    return dataSetID;
  }

  /**
   * 分录编号列
   */
//  String itemBHCol = null;

  public EFDataSet dataSet = null;
  /**
   *
   * @return EFDataSet
   */
  public EFDataSet getDataSet() {
    return dataSet;
  }

    public JPubFrame getPubFrame() {
        return pubFrame;
    }

    public int getOpType() {
        return opType;
    }

    public int getViewType(){
        return viewType;
    }

    public int getStandard(){
        return standard;
    }

    public Component getAffixWindow() {
        return affixWindow;
    }

    public boolean isExtendScreen() {
        return extendScreen;
    }

    //    public String getItemBHCol() {
//        return itemBHCol;
//    }

    /**
   *
   * @param dataSet EFDataSet
   */
  public void setDataSet(EFDataSet ds) {
    if ( dataSet != ds ) {
      if ( dataSet != null ) dataSet.removeDataSetListener(this);
      dataSet = ds;
      if ( dataSet != null ) dataSet.addDataSetListener(this);
    }
  }

    public void setPubFrame(JPubFrame pubFrame) {
        this.pubFrame = pubFrame;
    }

    public void setOpType(int opType) {
        this.opType = opType;
        setButtonText();
    }

    public void setViewType(int viewType){
        this.viewType = viewType;
    }

    public void setStandard(int standard){
        this.standard = standard;
    }

    public void setAffixWindow(Component affixWindow) {
        this.affixWindow = affixWindow;
    }

    public void setExtendScreen(boolean extendScreen) {
        this.extendScreen = extendScreen;
    }

    //    public void setItemBHCol(String itemBHCol) {
//        this.itemBHCol = itemBHCol;
//    }


    public ESPRowSet getMainRowSet() {
    return mainRowSet;
  }

  public void dataSetComponentListener(DataSetComponentEvent
                                       dataSetComponentEvent) {
  }

  protected EFRowSet mainRowSet = null;
  public void dataSetChanged(DataSetEvent e) {
      EFDataSet dataSet = e.getDataSet();
      if ( dataSet == null ) return;
      mainRowSet = dataSet.getRowSet();
//      int count = JPicAttachFunction.countPicAttachNumber(this);
      int count = 0;
      setNumber(count);
  }

  JPubFrame pubFrame = null;//打开普通的对话框


}
