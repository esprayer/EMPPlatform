package com.efounder.view.user;

import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.pfc.window.ComponentFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.openide.util.RequestProcessor;
import org.openide.util.RequestProcessor.Task;

public class JShutDownStatus
  extends JPanel
  implements ComponentFactory, Runnable, ActionListener
{
  JLabel st = new JLabel("");
  JCheckBox cancel = new JCheckBox("");
  BorderLayout bl = new BorderLayout();
  boolean bstart = false;
  int time;
  RequestProcessor.Task timeStatusTask = null;
  
  public JShutDownStatus()
  {
    try
    {
      jbInit();
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
  
  private void jbInit()
    throws Exception
  {
    this.st.setForeground(Color.red);
    System.getProperties().put("WindowShutDownStatus", this);
    setLayout(this.bl);
    add(this.st, "Center");
    this.cancel.addActionListener(this);
    Icon icon1 = ExplorerIcons.getExplorerIcon("ApplicationServer_stopped");
    this.cancel.setIcon(icon1);
  }
  
  public JComponent[] createComponent(JComponent parent)
  {
    JComponent[] rs = { this };
    return rs;
  }
  
  public void setText(String text)
  {
    this.st.setText(text);
  }
  
  public boolean shutDown(int time)
  {
    if (this.bstart) {
      return false;
    }
    this.bstart = true;
    this.time = time;
    add(this.cancel, "East");
    this.timeStatusTask = RequestProcessor.getDefault().create(this);
    this.timeStatusTask.schedule(1000);
    return true;
  }
  
  public void run()
  {
    this.time -= 1;
    setText("离程序退出时间还有：" + this.time / 60 + "分:" + this.time % 60 + "秒,请注意保存数据！");
    this.timeStatusTask.schedule(1000);
    if (this.time == 0) {
      System.exit(0);
    }
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if ((e.getSource() == this.cancel) && 
      (this.timeStatusTask != null))
    {
      this.timeStatusTask.cancel();
      setText("");
      remove(this.cancel);
      this.bstart = false;
    }
  }
}
