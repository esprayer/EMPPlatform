package com.efounder.view.user;

import com.core.xml.StubObject;
import com.efounder.eai.EAI;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.eai.framework.IActiveFramework;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.eai.service.ParameterManager;
import com.efounder.pfc.application.JApplication;
import com.efounder.pfc.swing.JIConListCellRenderer;
import com.efounder.pfc.window.JChildWindow;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

public class JUserListView
  extends JChildWindow
  implements ActionListener, Runnable
{
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  JLabel jLabel1 = new JLabel();
  BorderLayout borderLayout2 = new BorderLayout();
  JList lstIndexTypeContent = new JList();
  protected DefaultListModel listContentModel = null;
  JScrollPane jScrollPane1 = new JScrollPane();
  FlowLayout flowLayout1 = new FlowLayout();
  JButton JBSend = new JButton();
  protected static JMessageDialog sendDlg = null;
  
  public JUserListView()
  {
    try
    {
      jbInit();
      initListModel();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  private void jbInit()
    throws Exception
  {
    this.JBSend.setText("发送信息");
    this.JBSend.addActionListener(this);
    this.JBSend.setMargin(new Insets(0, 0, 0, 0));
    this.JBSend.setIcon(ExplorerIcons.getExplorerIcon("UserProfileManager.gif"));
    
    this.jLabel1.setText("共个用户在线");
    this.jLabel1.setIcon(ExplorerIcons.getExplorerIcon("office/(10,15).gif"));
    this.flowLayout1.setAlignment(0);
    this.flowLayout1.setHgap(1);
    this.flowLayout1.setVgap(1);
    this.jPanel2.setLayout(this.flowLayout1);
    this.jPanel2.add(this.jLabel1);
    this.jPanel2.add(this.JBSend);
    setLayout(this.borderLayout1);
    this.jPanel1.setLayout(this.borderLayout2);
    add(this.jPanel2, "North");
    add(this.jPanel1, "Center");
    this.jScrollPane1.getViewport().add(this.lstIndexTypeContent, null);
    this.jPanel1.add(this.jScrollPane1, "Center");
    setPreferredSize(new Dimension(520, 100));
  }
  
  protected void initListModel()
  {
    this.listContentModel = new DefaultListModel();
    this.lstIndexTypeContent.setModel(this.listContentModel);
    
    JIConListCellRenderer df = new JIConListCellRenderer(ExplorerIcons.getExplorerIcon("office/(05,35).gif"));
    

    this.lstIndexTypeContent.setCellRenderer(df);
    String time = ParameterManager.getDefault().getSystemParam("USER_REFRESHTIME");
    if ((time == null) || ("".equals(time))) {
      time = "60000";
    }
    long peridtime = Long.parseLong(time);
    timeUserList(peridtime);
  }
  
  public void UserListRefresh()
  {
    JResponseObject RO = null;
    try
    {
      JParamObject PO = JParamObject.Create();
      

      RO = (JResponseObject)EAI.DAL.IOM("ThreadManageObject", "GetUserList", PO, null, null, null);
      


      Vector userlist = (Vector)RO.getResponseObject();
      if (userlist.size() == 0) {
        System.out.println("0");
      }
      for (int i = 0; i < userlist.size(); i++)
      {
        JSessionStubObject SSsession = (JSessionStubObject)userlist.get(i);
        if (findUser(SSsession) < 0) {
          this.listContentModel.addElement(SSsession);
        }
      }
      int iModelCount = this.listContentModel.size();
      List removeList = new ArrayList();
      for (int i = 0; i < iModelCount; i++)
      {
        JSessionStubObject addSession = (JSessionStubObject)this.listContentModel.elementAt(i);
        
        boolean ibExist = false;
        if (addSession != null)
        {
          for (int j = 0; j < userlist.size(); j++)
          {
            JSessionStubObject SListSsession = (JSessionStubObject)userlist.get(j);
            if (SListSsession.getKey().equals(addSession.getKey()))
            {
              ibExist = true;
              break;
            }
          }
          if (!ibExist) {
            removeList.add(addSession);
          }
        }
      }
      for (int i = 0; i < removeList.size(); i++) {
        this.listContentModel.removeElement(removeList.get(i));
      }
      this.jLabel1.setText("共有" + this.listContentModel.size() + "个用户在线");
      SwingUtilities.invokeLater(this);
      setMessage(userlist);
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }
  
  public void run()
  {
    this.lstIndexTypeContent.repaint();
  }
  
  private void setMessage(Vector vuserlist)
  {
    JParamObject PO = JParamObject.Create();
    boolean ibMessage = false;
    String sUseKey = "";
    String sUser = PO.GetValueByEnvName("UserName", "");
    String sUserName = PO.GetValueByEnvName("UserCaption", "");
    String sHostAddress = PO.GetValueByEnvName("HostAddress", "");
    sUseKey = sUser + "-" + sUserName + "-" + sHostAddress;
    Vector RemoveMessageList = new Vector();
    for (int i = 0; i < vuserlist.size(); i++)
    {
      JSessionStubObject addSession = (JSessionStubObject)vuserlist.elementAt(i);
      if ((addSession != null) && 
        (addSession.getKey().equals(sUseKey)))
      {
        JMessageStubObject MessageStub = (JMessageStubObject)addSession.getObject("MessageObject", null);
        if (MessageStub != null)
        {
          Vector MessageList = MessageStub.getMessageList();
          if (MessageList != null) {
            for (int im = 0; im < MessageList.size(); im++)
            {
              StubObject sMessStub = (StubObject)MessageList.get(im);
              String sSender = (String)sMessStub.getObject("Sender", "");
              String sMessage = (String)sMessStub.getObject("Message", "");
              String sMessKey = (String)sMessStub.getObject("Key", "");
              ibMessage = true;
              if (sendDlg == null) {
                sendDlg = new JMessageDialog(this, EAI.EA.getMainWindow(), "即时信息", false);
              }
              if (!sendDlg.isVisible())
              {
                sendDlg.CenterWindow();
                sendDlg.InitUserListPanel(this.listContentModel);
                sendDlg.ReceviceMessage(sSender, sMessage);
              }
              else
              {
                sendDlg.ReceviceMessage(sSender, sMessage);
              }
              RemoveMessageList.add(sMessKey);
            }
          }
        }
      }
    }
    if ((RemoveMessageList != null) && (RemoveMessageList.size() > 0)) {
      try
      {
        EAI.DAL.IOM("ThreadManageObject", "DeleteSendMessage", PO, sUseKey, RemoveMessageList, null);
      }
      catch (Exception ex) {}
    }
    if ((ibMessage) && 
      (!sendDlg.isVisible()))
    {
      sendDlg.setSize(500, 400);
      sendDlg.Show();
    }
  }
  
  private int findUser(JSessionStubObject SSsession)
  {
    int ifind = -1;
    for (int i = 0; i < this.listContentModel.size(); i++)
    {
      JSessionStubObject addSession = (JSessionStubObject)this.listContentModel.elementAt(i);
      if ((addSession != null) && 
        (SSsession.getKey().equals(addSession.getKey())))
      {
        ifind = i;
        break;
      }
    }
    return ifind;
  }
  
  class TT
    extends TimerTask
  {
    JUserListView view;
    long peridtime;
    
    public TT(JUserListView view, long l)
    {
      this.view = view;
      this.peridtime = l;
    }
    
    public void run()
    {
      this.view.UserListRefresh();
      String time = ParameterManager.getDefault().getSystemParam("USER_REFRESHTIME");
      if ((time == null) || ("".equals(time))) {
        time = "60000";
      }
      long aa = Long.parseLong(time);
      if (aa != this.peridtime)
      {
        cancel();
        this.view.timeUserList(aa);
      }
    }
  }
  
  protected void timeUserList(long peridtime)
  {
    TT tt = new TT(this, peridtime);
    
    Timer timer = new Timer(true);
    timer.schedule(tt, 0L, peridtime);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource().equals(this.JBSend))
    {
      if (sendDlg == null) {
        sendDlg = new JMessageDialog(this, EAI.EA.getMainWindow(), "即时信息", false);
      }
      sendDlg.CenterWindow();
      sendDlg.InitUserListPanel(this.listContentModel);
      sendDlg.setSize(500, 400);
      
      sendDlg.Show();
    }
  }
}
