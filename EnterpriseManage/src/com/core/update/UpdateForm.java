package com.core.update;

import com.borland.jbcl.layout.VerticalFlowLayout;
import com.core.loader.mainConfig;
import com.core.xml.JBOFClass;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class UpdateForm extends JDialog
  implements ActionListener
{
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JButton bnCancel = new JButton();
  JButton bnFinish = new JButton();
  JButton bnNext = new JButton();
  JButton bnBack = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  JPanel jPanel2 = new JPanel();
  JLabel lbStep = new JLabel();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JLabel lbStep1 = new JLabel();
  JLabel lbStep2 = new JLabel();
  JLabel lbStep3 = new JLabel();
  JLabel lbStep4 = new JLabel();
  JPanel pnCard = new JPanel();

  Hashtable hashList = null;
  JPanel[] cardForm = new JPanel[4];
  int cardIndex = 0;
  CardLayout cardLayout1 = new CardLayout();
  Border border1;
  JCheckBox cbAutoUpdate = new JCheckBox();

  public void setCustomObject(Object o)
  {
  }

  public UpdateForm(Frame owner, Hashtable p) {
    super(owner);
    try {
      this.hashList = p;
      jbInit();
      pack();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
    getContentPane().setLayout(this.borderLayout1);
    this.bnCancel.setText("取消");
    this.bnFinish.setText("完成");
    this.bnNext.setText("下一步");
    this.bnBack.setText("上一步");
    this.jPanel1.setLayout(this.flowLayout1);
    this.flowLayout1.setAlignment(2);
    this.lbStep.setText("下载升级步骤：");
    this.jPanel2.setLayout(this.verticalFlowLayout1);
    this.jPanel2.setBackground(SystemColor.inactiveCaption);
    this.jPanel2.setBorder(this.border1);
    this.lbStep1.setText("1、选择下载升级中心");
    this.lbStep2.setText("2、选择下载升级模块");
    this.lbStep3.setText("3、下载模块检查签名");
    this.lbStep4.setText("4、检查验证安装模块");
    this.pnCard.setLayout(this.cardLayout1);
    this.cbAutoUpdate.setForeground(Color.red);
    this.cbAutoUpdate.setText("每次启动自动检查更新");
    getContentPane().add(this.jPanel1, "South");
    this.jPanel1.add(this.cbAutoUpdate, null);
    this.jPanel1.add(this.bnBack, null);
    this.jPanel1.add(this.bnNext, null);
    this.jPanel1.add(this.bnFinish, null);
    this.jPanel1.add(this.bnCancel, null);
    getContentPane().add(this.jPanel2, "West");
    this.jPanel2.add(this.lbStep, null);
    this.jPanel2.add(this.lbStep1, null);
    this.jPanel2.add(this.lbStep2, null);
    this.jPanel2.add(this.lbStep3, null);
    this.jPanel2.add(this.lbStep4, null);
    getContentPane().add(this.pnCard, "Center");
    initCardForm();

    _$5524();
    _$5528();
    _$5978();
    this.cbAutoUpdate.setVisible(false); }

  private void _$5978() {
    willInto(this.cardForm[0], null, null);
    _$5979(0); }

  private Object _$5980() {
    return _$5980(this.cardIndex);
  }

  private Object _$5980(int index) {
    return this.cardForm[index]; }

  private void _$5528() {
    this.bnBack.addActionListener(this);
    this.bnNext.addActionListener(this);
    this.bnFinish.addActionListener(this);
    this.bnCancel.addActionListener(this);
    this.cbAutoUpdate.addActionListener(this); }

  private void _$5524() {
    setDefaultCloseOperation(0);
    this.bnBack.setEnabled(this.cardIndex != 0);
    this.bnNext.setEnabled(this.cardIndex != 3);
    this.bnFinish.setEnabled(this.cardIndex == 3);
    String acu = (String)mainConfig.get("autocheckupdate", "0");
    this.cbAutoUpdate.setSelected("1".equals(acu)); }

  protected void initCardForm() {
    setTitle("下载升级中心");
    JPanel panel = new Setp1Form();
    this.cardForm[0] = panel;
    this.pnCard.add(this.cardForm[0], "panel0");
    String Application = (String)this.hashList.get("application");
    String Product = (String)this.hashList.get("product");
    panel = new Setp2Form(Application, Product);
    this.cardForm[1] = panel;
    this.pnCard.add(this.cardForm[1], "panel1");
    panel = new Setp3Form(Application, Product);
    this.cardForm[2] = panel;
    this.pnCard.add(this.cardForm[2], "panel2");
    panel = new Setp4Form();
    this.cardForm[3] = panel;
    this.pnCard.add(this.cardForm[3], "panel3"); }

  private void _$5979(int index) {
    this.cardLayout1.show(this.pnCard, "panel" + index); }

  public void CenterWindow() {
    Dimension dlgSize = getSize();
    Dimension ScnSize = Toolkit.getDefaultToolkit().getScreenSize();
    setLocation((ScnSize.width - dlgSize.width) / 2, (ScnSize.height - dlgSize.height) / 2); }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.bnBack) {
      processBack();
    }
    if (e.getSource() == this.bnNext) {
      processNext();
    }
    if (e.getSource() == this.bnFinish) {
      processFinish();
    }
    if (e.getSource() == this.bnCancel) {
      processCancel();
    }
    if (e.getSource() == this.cbAutoUpdate) {
      processAutoUpdate();
    }
    _$5524(); }

  protected void processAutoUpdate() {
    mainConfig.put("autocheckupdate", (this.cbAutoUpdate.isSelected()) ? "1" : "0"); }

  public void processBack() {
    if (this.cardIndex > 0)
      _$5979(--this.cardIndex);
  }

  public void processNext() {
    if (this.cardIndex >= 3)
      return;
    Object cardObject = _$5980();
    Object res = willNext(cardObject, null, null);
    if (res != null) {
      cardObject = _$5980(this.cardIndex + 1);
      res = willInto(cardObject, res, null);
      if (res != null)
        _$5979(++this.cardIndex);
    }
  }

  protected void processFinish()
  {
    if (UpdateObject.BOOT_OR_EXEC_UPDATE_TYPE == 1) {
      JOptionPane.showMessageDialog(this, "下载升级完毕，应用系统需要重新启动！", "系统提示", 2);
    }
    hide();
    dispose(); }

  protected void processCancel() {
    hide();
    dispose(); }

  public Object willIntoObject(int index) {
    Object res = null;

    return res; }

  public Object willNext(Object cardObject, Object p1, Object p2) {
    Object res = null; Object[] oarray = { p1, p2, this.hashList, this };
    try {
      setCursor(Cursor.getPredefinedCursor(3));
      res = JBOFClass.CallObjectMethod(cardObject, "willNext", oarray);
    } catch (Exception e) {
    }
    finally {
      setCursor(Cursor.getPredefinedCursor(0));
    }
    return res; }

  public Object willBack(Object cardObject, Object p1, Object p2) {
    Object res = null; Object[] oarray = { p1, p2, this.hashList, this };
    try {
      setCursor(Cursor.getPredefinedCursor(3));
      res = JBOFClass.CallObjectMethod(cardObject, "willBack", oarray);
    } catch (Exception e) {
    }
    finally {
      setCursor(Cursor.getPredefinedCursor(0));
    }
    return res; }

  public Object willInto(Object cardObject, Object p1, Object p2) {
    Object res = null; Object[] oarray = { p1, p2, this.hashList, this };
    try {
      setCursor(Cursor.getPredefinedCursor(3));
      res = JBOFClass.CallObjectMethod(cardObject, "willInto", oarray);
    } catch (Exception e) {
    }
    finally {
      setCursor(Cursor.getPredefinedCursor(0));
    }

    return res; }

  public void setEnable(int index, boolean enable) {
    if (index == 0) {
      this.bnBack.setEnabled(enable);
    }
    if (index == 1) {
      this.bnNext.setEnabled(enable);
    }
    if (index == 2) {
      this.bnFinish.setEnabled(enable);
    }
    if (index == 3)
      this.bnCancel.setEnabled(enable);
  }
}