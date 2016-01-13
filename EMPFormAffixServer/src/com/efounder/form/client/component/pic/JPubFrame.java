package com.efounder.form.client.component.pic;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.efounder.eai.ide.*;
import com.efounder.form.client.component.infer.IDiglogPanel;

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

public class JPubFrame extends JFrame
    implements ActionListener  {
  private JButton bOk   = new JButton();
  private JButton bExit = new JButton();

  public JPubFrame(IDiglogPanel generalPanel,String title){
    try {
        this.centerPanel = generalPanel;
        this.setTitle(title);
        jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

//  public JPubFrame(JPanel generalPanel,String title){
//      try {
//        this.centerPanel = (IDiglogPanel)generalPanel;
//        this.setTitle(title);
//        jbInit();
//    }
//    catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

  JPanel topPanel = new JPanel();
  JPanel leftPanel = new JPanel();
  JPanel rightPanel = new JPanel();
  JPanel buttonPanel = new JPanel();
  protected IDiglogPanel centerPanel = null;

  private void jbInit() throws Exception {
      initMainPanel();
      addCmdButton();
      initBtn();

      this.addWindowListener(new JPubFrameWindowAdapter(this));
  }

  protected void initMainPanel(){
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(topPanel,BorderLayout.NORTH);
        this.getContentPane().add(leftPanel,BorderLayout.WEST);
        this.getContentPane().add(rightPanel,BorderLayout.EAST);
        this.getContentPane().add(buttonPanel,BorderLayout.SOUTH);

        this.getContentPane().add((JPanel)centerPanel,BorderLayout.CENTER);
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
    private JButton BOk;
    private JButton BExit;
    public void onOKAction(){
      this.centerPanel.destroy();
      this.centerPanel = null;//销毁
      this.setVisible(false);
      this.dispose();

//      if(((IDiglogPanel)centerPanel).onApply()){
//          retValue = ((IDiglogPanel) centerPanel).retValue();
//          this.OnOk();
//      }
  }

  public void onExitAction(){
      this.centerPanel.destroy();
      this.centerPanel = null;//销毁
      this.setVisible(false);
      this.dispose();
  }

    public Object getRetValue() {
        return retValue;
    }

    public JButton getBOk() {
        return BOk;
    }

    public JButton getBExit() {
        return BExit;
    }

    public void setOKVisiable(boolean isVis){
      bOk.setVisible(isVis);
    }

    public void setOKText(String title){
      bOk.setText(title);
  }

  public void setCancelText(String title){
      bExit.setText(title);
  }

  public void this_windowActivated(WindowEvent e) {
  }

  public void windowClosing(WindowEvent e) {
  }


  class JPubFrameWindowAdapter extends WindowAdapter {
    private JPubFrame adaptee;
    JPubFrameWindowAdapter(JPubFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void windowActivated(WindowEvent e) {
        adaptee.this_windowActivated(e);
    }
    public void windowClosing(WindowEvent e) {
        adaptee.windowClosing(e);
    }

}




}

