package com.efounder.form.client.component.pic;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import com.efounder.form.client.component.infer.*;
import com.efounder.pfc.dialog.*;
import com.efounder.eai.ide.ExplorerIcons;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class JPicDialog extends JPDialog
    implements ActionListener  {
  private JButton bOk   = new JButton();
  private JButton bExit = new JButton();
  //是否标准0是1否
  int standard = 0;

  JPanel topPanel = new JPanel();
  JPanel leftPanel = new JPanel();
  JPanel rightPanel = new JPanel();
  JPanel buttonPanel = new JPanel();
  JPanel centerPanel = null;

  public JPicDialog(Frame frame, String title, boolean modal, JPanel generalPanel, String imageName){
    super(frame, title, modal);
    try {
    	jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
      initMainPanel();
      addCmdButton();
      initBtn();
  }

  protected void initMainPanel(){
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(topPanel,BorderLayout.NORTH);
        this.getContentPane().add(leftPanel,BorderLayout.WEST);
        this.getContentPane().add(rightPanel,BorderLayout.EAST);
        this.getContentPane().add(buttonPanel,BorderLayout.SOUTH);
  }

  public void addPanelToDialog(JPanel panel){
      this.centerPanel = panel;
//      panel.setBorder(new TitledBorder(""));
      this.getContentPane().add(panel,BorderLayout.CENTER);
  }

  protected void addCmdButton(){
        FlowLayout fl = new FlowLayout();
        fl.setAlignment(FlowLayout.RIGHT);
        buttonPanel.setLayout(fl);

        buttonPanel.add(bOk);
        buttonPanel.add(bExit);
  }


  protected void initBtn(){
      bOk.setText("确定");
      bOk.setToolTipText("确定");
      bOk.setMargin(new Insets(0, 0, 0, 0));
      bOk.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/checkbox_16.png"));
      bOk.addActionListener(this);

      bExit.setText("取消");
      bExit.setToolTipText("取消");
      bExit.setMargin(new Insets(0, 0, 0, 0));
      bExit.setIcon(ExplorerIcons.getExplorerIcon("jprofiler/delete.png"));
      bExit.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) {
      if (e.getSource() == bOk) {
          onOKAction();
      }
      else if(e.getSource() == bExit){
          onExitAction();
      }
  }


  Object retValue = null;
  public void onOKAction(){
      if(((IDiglogPanel)centerPanel).onApply()){
          retValue = ((IDiglogPanel) centerPanel).retValue();
          this.OnOk();
      }
  }

  public void onExitAction(){
      if(centerPanel != null){
          ((IDiglogPanel)centerPanel).destroy();
      }

      this.centerPanel = null;//销毁
      this.setVisible(false);
      this.dispose();
  }

  public Object getRetValue() {
       return retValue;
  }

  public void setOKText(String title){
      bOk.setText(title);
  }

  public void setCancelText(String title){
      bExit.setText(title);
  }

  public void hide() {
      if(this.centerPanel != null){
          ((IDiglogPanel) centerPanel).destroy();
      }
      super.hide();
  }

  public void setOKVisiable(boolean isVis){
      this.bOk.setVisible(isVis);
  }

  public void setExitVisiable(boolean isVis){
      this.bExit.setVisible(isVis);
  }






}
