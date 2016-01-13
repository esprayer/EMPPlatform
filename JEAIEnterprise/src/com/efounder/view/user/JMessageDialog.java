package com.efounder.view.user;

import com.borland.jbcl.layout.BoxLayout2;
import com.borland.jbcl.layout.VerticalFlowLayout;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.framework.IActiveFramework;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.pfc.application.JApplication;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import jfoundation.gui.window.classes.JFrameDialog;

public class JMessageDialog
  extends JFrameDialog
  implements ActionListener
{
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JPanel jPanel3 = new JPanel();
  JLabel jLabel1 = new JLabel();
  FlowLayout flowLayout1 = new FlowLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel5 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JTextField tfSendMessage = new JTextField();
  JButton bnSend = new JButton();
  JPanel jPanel6 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JScrollPane epScrollPane = new JScrollPane();
  JEditorPane epMessagePanel = new JEditorPane();
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel prefixList = new JPanel();
  JScrollPane scrollPane = new JScrollPane();
  JButton bnClear = new JButton();
  DefaultListModel userListModel = null;
  private JUserListView ListVeiw = null;
  JLabel jLabel2 = new JLabel();
  BorderLayout borderLayout7 = new BorderLayout();
  BorderLayout borderLayout8 = new BorderLayout();
  VerticalFlowLayout verticalFlowLayout1 = new VerticalFlowLayout();
  JPanel pnCheckBox = new JPanel();
  BoxLayout2 boxLayout21 = new BoxLayout2();
  JSplitPane jSplitPane1 = new JSplitPane();
  
  public void InitUserListPanel(DefaultListModel listContentModel)
  {
    this.userListModel = listContentModel;
    this.pnCheckBox.removeAll();
    try
    {
      this.pnCheckBox.repaint();
      this.pnCheckBox.doLayout();
    }
    catch (Exception e) {}
    for (int i = 0; i < listContentModel.size(); i++)
    {
    	JSessionStubObject addSession = (JSessionStubObject)listContentModel.elementAt(i);
      if (addSession != null)
      {
        addSession.CheckBox = new JCheckBox(addSession.toString());
        this.pnCheckBox.add(addSession.CheckBox);
        addSession.CheckBox.setActionCommand(addSession.getHostAddress());
        addSession.CheckBox.addActionListener(this);
        try
        {
          this.pnCheckBox.repaint();
          this.pnCheckBox.doLayout();
        }
        catch (Exception e) {}
      }
    }
  }
  
  public JMessageDialog(JUserListView listView, Frame frame, String title, boolean modal)
  {
    super(frame, title, modal);
    this.ListVeiw = listView;
    try
    {
      jbInit();
      pack();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  private void jbInit()
    throws Exception
  {
    this.flowLayout1.setAlignment(0);
    this.panel1.setLayout(this.borderLayout1);
    this.jPanel1.setLayout(this.flowLayout1);
    this.jPanel2.setLayout(this.borderLayout2);
    this.jPanel3.setLayout(this.borderLayout3);
    this.jPanel4.setLayout(this.borderLayout4);
    this.jPanel5.setLayout(this.borderLayout7);
    this.jPanel6.setLayout(this.borderLayout5);
    this.jPanel7.setLayout(this.borderLayout8);
    this.prefixList.setLayout(this.verticalFlowLayout1);
    
    this.jLabel1.setText("即时信息");
    this.bnSend.setMnemonic('S');
    this.bnSend.setText("发送(S)");
    this.bnSend.setIcon(ExplorerIcons.getExplorerIcon("javaMailSession.gif"));
    
    this.bnSend.addActionListener(this);
    
    this.epMessagePanel.setFont(new Font("Dialog", 0, 12));
    this.epMessagePanel.setPreferredSize(new Dimension(273, 24));
    this.epMessagePanel.setText("");
    this.epScrollPane.setPreferredSize(new Dimension(270, 27));
    
    this.bnClear.setMnemonic('L');
    this.bnClear.setText("清除(L)");
    this.bnClear.addActionListener(this);
    this.jLabel2.setText("输入信息:");
    this.prefixList.setBorder(BorderFactory.createEtchedBorder());
    this.prefixList.setMaximumSize(new Dimension(100, 200));
    this.prefixList.setMinimumSize(new Dimension(80, 120));
    this.prefixList.setPreferredSize(new Dimension(80, 200));
    this.pnCheckBox.setLayout(this.boxLayout21);
    this.boxLayout21.setAxis(1);
    this.jPanel7.setMaximumSize(new Dimension(100, 200));
    this.jPanel7.setPreferredSize(new Dimension(80, 248));
    this.scrollPane.setMaximumSize(new Dimension(100, 200));
    this.scrollPane.setMinimumSize(new Dimension(80, 120));
    this.scrollPane.setPreferredSize(new Dimension(376, 280));
    

    getContentPane().add(this.panel1);
    this.jPanel1.add(this.jLabel1, null);
    this.panel1.add(this.jPanel2, "Center");
    this.panel1.add(this.jPanel3, "South");
    this.jPanel3.add(this.jPanel4, "Center");
    this.jPanel4.add(this.tfSendMessage, "Center");
    this.jPanel3.add(this.jPanel5, "East");
    this.jPanel4.add(this.jLabel2, "West");
    this.jPanel5.add(this.bnSend, "Center");
    this.jPanel7.add(this.prefixList, "Center");
    this.prefixList.add(this.scrollPane, null);
    this.jPanel7.add(this.bnClear, "South");
    this.scrollPane.getViewport().add(this.pnCheckBox);
    this.jPanel6.add(this.epScrollPane, "Center");
    this.jSplitPane1.add(this.jPanel7, "right");
    this.epScrollPane.getViewport().add(this.epMessagePanel, null);
    this.jSplitPane1.add(this.jPanel6, "left");
    this.jPanel2.add(this.jSplitPane1, "Center");
    this.panel1.add(this.jPanel1, "North");
    this.tfSendMessage.grabFocus();
  }
  
  public void SendMessage(String Message)
  {
    String Text = this.epMessagePanel.getText();
    Text = Text + "\n我自己:  " + Message;
    this.epMessagePanel.setText(Text);
    
    JParamObject PO = JParamObject.Create();
    
    String sSender = PO.GetValueByEnvName("UserName", "") + "-" + PO.GetValueByEnvName("UserCaption", "") + "(" + PO.GetValueByEnvName("HostAddress", "") + ")";
    

    Vector ReceiverList = new Vector();
    for (int i = 0; i < this.userListModel.size(); i++)
    {
    	JSessionStubObject addSession = (JSessionStubObject)this.userListModel.elementAt(i);
      if (addSession != null)
      {
        if (addSession.CheckBox == null)
        {
          addSession.CheckBox = new JCheckBox(addSession.toString());
          addSession.CheckBox.setActionCommand(addSession.getHostAddress());
          addSession.CheckBox.addActionListener(this);
        }
        if (addSession.CheckBox.isSelected()) {
          ReceiverList.add(addSession.getKey());
        }
      }
    }
    try
    {
      EAI.DAL.IOM("ThreadManageObject", "SendMessage", PO, sSender, ReceiverList, Message);
    }
    catch (Exception ex) {}
    this.ListVeiw.UserListRefresh();
  }
  
  public void ReceviceMessage(String Sender, String Message)
  {
    if (Message.startsWith("shutdown"))
    {
      shutDown(Message);
      return;
    }
    String Text = this.epMessagePanel.getText();
    Text = Text + "\n" + Sender + ":  " + Message;
    this.epMessagePanel.setText(Text);
    this.epScrollPane.getVerticalScrollBar().setValue(this.epScrollPane.getVerticalScrollBar().getMaximum());
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == this.bnSend)
    {
      SendMessage(this.tfSendMessage.getText());
      this.tfSendMessage.setText("");
    }
    if (e.getSource() == this.bnClear) {
      this.epMessagePanel.setText("");
    }
  }
  
  public void shutDown(String Message)
  {
    String[] str = Message.split(" ");
    String mess;
    if (str.length == 2) {
      mess = "因系统升级，程序需要在" + str[1] + "分钟后退出，请及时保存数据！";
    } else {
      mess = str[2];
    }
    int time = (int)(Double.parseDouble(str[1]) * 60.0D);
    JShutDownStatus sd = (JShutDownStatus)System.getProperties().get("WindowShutDownStatus");
    if (sd.shutDown(time)) {
      JOptionPane.showMessageDialog(EAI.EA.getMainWindow(), mess, "系统警告", 2);
    }
  }
}
