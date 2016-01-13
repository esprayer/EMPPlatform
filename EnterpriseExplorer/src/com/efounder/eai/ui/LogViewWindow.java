package com.efounder.eai.ui;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

import org.openide.*;
import org.openide.util.*;
import com.efounder.action.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.ui.action.*;
import com.efounder.pfc.window.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class LogViewWindow extends JChildWindow implements Logview,Runnable,MouseListener{
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  private static final Object FLUSH_LOCK = new String("FLUSH_LOCK"); // NOI18N
  private static final int FLUSH_DELAY = Integer.getInteger("efounder.logger.flush.delay", 0).intValue(); // NOI18N
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextPane epLogView = new JTextPane();//JEditorPane();
  JToolBar jToolBar1 = new JToolBar();
  BorderLayout borderLayout2 = new BorderLayout();
  private static String CLEAR_COMMAND = "ClearLog";
  private static String COPY_COMMAND  = "CopyLog";
  private static String SAVE_COMMAND  = "SaveLog";
  private static String WATCH_COMMAND = "WatchLog";
  private static String WRITE_COMMAND = "WriteLog";
  java.util.List textList = new ArrayList();
  Action logClear = null;
  Action logCopy  = null;
  Action logSave  = null;
  Action logStop  = null;
  Action logWatch = null;// 查看Log
  Action logWrite = null;// 记录Log
  ActionGroup actionGroup = new ActionGroup();
  boolean LOG_WATCH = true;
  boolean LOG_WRITE = true;

  public Boolean getStateWatchLog(Object actionObject,Object obj,ObjectStateAction action) {
    return new Boolean(LOG_WATCH);
  }
  public void setStateWatchLog(Object actionObject,Object obj,ObjectStateAction action,Boolean b) {
    LOG_WATCH = b.booleanValue();
  }
  public Boolean getStateWriteLog(Object actionObject,Object obj,ObjectStateAction action) {
    return new Boolean(LOG_WRITE);
  }
  public void setStateWriteLog(Object actionObject,Object obj,ObjectStateAction action,Boolean b) {
    LOG_WRITE = b.booleanValue();
  }

  /**
   *
   * @param explorer EnterpriseExplorer
   * @param actionObject Object
   * @param action Action
   */
  public void ClearLog(EnterpriseExplorer explorer,Object actionObject,Action action) {

  }
  /**
   *
   * @param explorer EnterpriseExplorer
   * @param actionObject Object
   * @param action Action
   */
  public void CopyLog(EnterpriseExplorer explorer,Object actionObject,Action action) {

  }
  /**
   *
   * @param explorer EnterpriseExplorer
   * @param actionObject Object
   * @param action Action
   */
  public void SaveLog(EnterpriseExplorer explorer,Object actionObject,Action action) {

  }
  /**
   *
   * @param explorer EnterpriseExplorer
   * @param actionObject Object
   * @param action Action
   */
  public void UpdateClearLog(EnterpriseExplorer explorer,Object actionObject,Action action) {

  }
  /**
   *
   * @param explorer EnterpriseExplorer
   * @param actionObject Object
   * @param action Action
   */
  public void UpdateCopyLog(EnterpriseExplorer explorer,Object actionObject,Action action) {

  }
  /**
   *
   * @param explorer EnterpriseExplorer
   * @param actionObject Object
   * @param action Action
   */
  public void UpdateSaveLog(EnterpriseExplorer explorer,Object actionObject,Action action) {

  }

  public LogViewWindow() {
    try {
      jbInit();
      initAction();
      TopLogging.setLogView(this);

    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void initAction() {
    logCopy  = new ObjectAction(this,COPY_COMMAND,res.getString("KEY34"),'0',res.getString("KEY35"),ExplorerIcons.ICON_COPY);
    logClear = new ObjectAction(this,CLEAR_COMMAND,res.getString("KEY36"),'0',res.getString("KEY37"),ExplorerIcons.ICON_CLEAN_BUILD);
    logSave  = new ObjectAction(this,SAVE_COMMAND,res.getString("KEY38"),'0',res.getString("KEY39"),ExplorerIcons.ICON_SAVE);
    logWatch = new ObjectStateAction(this,WATCH_COMMAND,res.getString("KEY40"),'0',res.getString("KEY41"),ExplorerIcons.ICON_INSPECT);
    logWrite = new ObjectStateAction(this,WRITE_COMMAND,res.getString("KEY42"),'0',res.getString("KEY43"),ExplorerIcons.ICON_ADDWATCH);

    actionGroup.add(logCopy);
    actionGroup.add(logClear);
    actionGroup.add(logSave);
    actionGroup.add(logWatch);
    actionGroup.add(logWrite);
    ActionGroup[] ags = {actionGroup};
    WindowActionToolBarPane watp = new WindowActionToolBarPane(this,ags);
    this.jToolBar1.add(watp);
  }
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    jPanel1.setLayout(borderLayout2);
    this.add(jPanel1,  BorderLayout.SOUTH);
    jPanel1.add(jToolBar1, BorderLayout.CENTER);
    this.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(epLogView, null);
    epLogView.setEditable(false);
    this.epLogView.addMouseListener(this);
  }
  public void write(int b,int style) {
    if ( !this.isVisible() ) return;
    String Text = style + String.valueOf(b);
    synchronized ( textList ) {
      textList.add(Text);
    }
    flushLog();
//    epLogView.setText(epLogView.getText()+Text);
  }
  public void write(byte b[],int style) {
    if ( !this.isVisible() ) return;
    write(b,0,b.length,style);
  }
  public void write(byte b[],int off,int len,int style) {
    if ( !this.isVisible() ) return;
    try {
      String Text = style + new String(b, off, len, res.getString("GBK"));
      synchronized ( textList ) {
        textList.add(Text);
      }

//      epLogView.setText(epLogView.getText() + Text);
    } catch ( Exception e ) {}
    flushLog();
  }
  //
  private RequestProcessor.Task logFlushTask;
  /** processor in which to flush them */
  private static final RequestProcessor RP = new RequestProcessor("Flush View ide.log"); // NOI18N
  private void flushLog() {
      synchronized (FLUSH_LOCK) {
          if (logFlushTask == null) {
              logFlushTask = RP.create(this);
              logFlushTask.schedule(FLUSH_DELAY);
          }
      }
  }
  public void run() {
    Object[] textArray = null;
      synchronized (FLUSH_LOCK) {
          try {
            synchronized ( textList ) {
              if ( textList.size() == 0 ) return;
              textArray = textList.toArray();
              textList = new ArrayList();
            }
            String text = "";int Style = 0;
            for(int i=0;i<textArray.length;i++) {
              text = (String)textArray[i];
              Style = Integer.parseInt(text.substring(0,1));
              text = text.substring(1,text.length());
              insertString(text,Style);
            }
            epLogView.setCaretPosition(epLogView.getDocument().getLength());

//            this.jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
          } catch (Exception e) {
          }
          logFlushTask = null;
      }
  }
  private void insertString(String text,int Style) {
    Document doc = this.epLogView.getDocument();
    SimpleAttributeSet attrSet = new SimpleAttributeSet();
    if ( Style == 0 ) {
      StyleConstants.setForeground(attrSet, Color.BLACK);
    } else {
      StyleConstants.setForeground(attrSet, Color.RED);
    }
    try {
      doc.insertString(doc.getLength(), text, attrSet);
    } catch ( Exception e ) {

    }
  }
  protected void showPopupmenu(Point p,Component invoker) {
    if ( actionGroup != null && actionGroup.getActionCount() > 0 ) {
      ActionPopupMenu actionPopupMenu = new ActionPopupMenu(this, actionGroup, true);
      actionPopupMenu.show(invoker, p.x,p.y);
    }
  }

  /**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   */
  public void mouseClicked(MouseEvent e) {
    if ( ( e.getModifiers() & e.BUTTON3_MASK ) != 0 ) {
      Point p = e.getPoint();
//      SwingUtilities.convertPointToScreen(p, (Component)e.getSource());
      showPopupmenu(p,(Component)e.getSource());

    }
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  public void mousePressed(MouseEvent e) {

  }

  /**
   * Invoked when a mouse button has been released on a component.
   */
  public void mouseReleased(MouseEvent e) {

  }

  /**
   * Invoked when the mouse enters a component.
   */
  public void mouseEntered(MouseEvent e) {

  }

  /**
   * Invoked when the mouse exits a component.
   */
  public void mouseExited(MouseEvent e) {

  }

}
