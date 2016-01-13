package jservice.jbof.classes.monitor;

import java.text.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.eai.iwe.gui.classes.action.*;
import com.eai.iwe.gui.interfaces.*;
import com.eai.iwe.wui.spaces.classes.*;
import com.eai.resources.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.foundation.classes.*;
import jservice.jbof.classes.monitor.chart.*;
import jservice.jbof.classes.monitor.grid.*;

/**
 * 监控器面板。
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TKernelMonitorSpace extends TWiSpaceFunction implements IActionProvider, Runnable {
  private        static String T_MONITOR_TIME    = "BISMonitorTimer";
  private        static String T_UPDATE_TIME     = "BISTimeUpdateTimer";
  private     SimpleDateFormat TimeFormat        = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );


  private      TGridMonitorModel  mGridModel     = null;
  private      JTable             mGridTable     = null;
  private      TChartMonitor      mChartMonitor  = new TChartMonitor();
  private         int             mTimer         = 0;
  private      boolean            mIsStarted     = false;
  private      boolean            mIsPrepared    = false;
  private        Thread           mThread =     null;

  IAction mActFrush  = null;
  IAction mActStop   = null;
  IAction mActStart  = null;
  IAction mActViewChart  = null;
  IAction mActTime   = null;

  IAction[]  mActList    = null;

  JPanel PLMain = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane PLAll = new JTabbedPane();
  JPanel PLGrid = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel PLTime = new JPanel();
  JLabel CETime = new JLabel();
  BorderLayout borderLayout3 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
    public TKernelMonitorSpace() {
    try {
      jbInit();

    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }


  public String getCaption() {
    return "BIS监控";
  }

  public String getIconId() {
    return "SYS_MONITOR";
  }

  public String getSpaceID(){
    return "BIS_MONITOR";
  }


  public boolean onPrepare() {
    if ( mIsPrepared ){return true;}

    super.onPrepare();
    super.getToolBar().removeAll();

    mActFrush = TAction.Create("FRUSH","刷新",'P',this,"onReLoad","BIS_FRUSH");
//    mActFrush.setAsynchronous(true);      //此为异步。

    mActStop  = TAction.Create("STOP", "停止",'S',this,"onStop","BIS_STOP");
    mActStart = TAction.Create("START","开始",'O',this,"onStart","BIS_START");
    mActViewChart = TAction.Create("CHART","监控图",'O',this,"onChart","BIS_CHART");
    mActTime      = TAction.Create("TIME","当前时间",'T',this,"onTime","BIS_CHART");
    IActionable pCtrol = new TActionable(CETime/*,140,16*/);
    mActTime.setActionable(pCtrol);

    mActList      = new IAction[]{mActStart,mActStop,mActFrush,mActViewChart};

    mActStart.setEnabled(true);
    mActStop.setEnabled(false);

    super.getToolBar().addAction(mActStart);
    super.getToolBar().addAction(mActStop);
    super.getToolBar().addAction(mActFrush);
    super.getToolBar().addAction(mActViewChart);
    super.getToolBar().addAction(TAction.CreateEmpty());

    super.getToolBar().addAction(mActTime);

    super.getToolBar().Initialize();

    /**
     * 监控表.
     */
    ShowMonitorGrid();

    /**
     * 注册一个时钟。
     */
    RegTimer();


    try{
      //mEngineServiceRef = (IBISEngineEntry)TEAIEnv.QueryServerService("BISApplication","BISEngineEntry","com.bis.pub.services.interfaces.IBISEngineEntry");
    }
    catch(Exception E){
      E.printStackTrace();
    }

    /**
     * 我自己的准备过程。
     */
    onMyPrepare();

    mIsPrepared = true;


    return true;
  }

  //注册。
  private void RegTimer(){
    mThread = new Thread(this);
    mThread.start();
  }

  private void ShowMonitorGrid(){
    try{
      mGridModel = new TGridMonitorModel();
      mGridTable = new JTable(mGridModel);

      mGridTable.addMouseListener(this);

      //整个背景设成标题的。
      //mGridTable.setBackground(mGridTable.getTableHeader().getBackground());
      /*
      Font pFont = mGridTable.getFont();
      mGridTable.getTableHeader().setFont(new Font("宋体",Font.PLAIN,pFont.getSize()+2));
      */
    }
    catch(Exception E){
      E.printStackTrace();
    }

    PLGrid.add(new JScrollPane(mGridTable),BorderLayout.CENTER);
  }

  /**
   * 开始.
   * @param pAct IAction
   * @param pEvt ActionEvent
   * @return boolean
   */
  public boolean onStart(IAction pAct,ActionEvent pEvt){
    mIsStarted = true;
    pAct.setEnabled(false);
    mActStop.setEnabled(true);
    return true;
  }

  public boolean onStop(IAction pAct,ActionEvent pEvt){
    try{
      mIsStarted = false;
      pAct.setEnabled(false);
      mActStart.setEnabled(true);

    }
    catch(Exception E){

    }
    return true;
  }


  public boolean onReLoad(IAction pAct,ActionEvent pEvt){
    try{
      onLoadMonitorValues();
    }
    catch(Exception E){

    }
    return true;
  }

  public boolean onChart(IAction pAct,ActionEvent pEvt){
    try{
      /**
       * 初始化一下。
       */
      mChartMonitor.Prepare(new Vector(),getMonitorChannels(),getMonitorChannelNames());
      PLAll.setSelectedIndex(1);
    }
    catch(Exception E){

    }
    return true;
  }

  private boolean onMyPrepare() {
    /**
     * 监控数据表
     */
    PLAll.removeAll();

    ImageIcon pIcon = TImages.getIcon("BIS_MONITOR_GRID");
    PLAll.addTab("监控表",pIcon,PLGrid);

    /**
     * 监控图
     */
    pIcon = TImages.getIcon("BIS_CHART");
    PLAll.addTab("监控图",pIcon,mChartMonitor);

    /*
    IMenuBar pMenus = super.getRootForm().getMainMenuBar();
    IToolBar pTools = super.getRootForm().getMainToolBar();

    TActionGroup pGroup = new TActionGroup();

    pGroup.addAction(mActStart);
    pGroup.addAction(mActStop);
    pGroup.addAction(mActFrush);
    pGroup.addAction(mActViewChart);

    IAction pActionMp3 = TAction.Create("Monnitor", "BIS监控器", 'N', this, "");
    pActionMp3.setGroup(pGroup);

    pMenus.CreateMenuFromAction(pActionMp3);

    pTools.addAction(mActStart);
    pTools.addAction(mActStop);
    pTools.addAction(mActFrush);
    pTools.addAction(mActViewChart);
    pTools.Initialize();
    */



    return true;
  }
  private void jbInit() throws Exception {
        PLMain.setLayout(borderLayout1);
    PLAll.setTabPlacement(JTabbedPane.BOTTOM);
    PLGrid.setLayout(borderLayout2);
    PLGrid.setMaximumSize(new Dimension(32767, 32767));
        PLTime.setLayout(borderLayout3);
    CETime.setFont(new java.awt.Font("Dialog", 0, 14));
    //CETime.setBorder(BorderFactory.createLoweredBevelBorder());
    CETime.setText("时：分：秒");
    PLTime.setMaximumSize(new Dimension(32767, 32767));
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 15));
    jLabel1.setToolTipText("");
    jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
    jLabel1.setText("BIS监控");
    this.add(PLMain, BorderLayout.CENTER);
        PLMain.add(PLAll,  BorderLayout.CENTER);
    PLTime.add(CETime, BorderLayout.EAST);
    PLTime.add(jLabel1, BorderLayout.CENTER);
    }


  public boolean isEnabled(String pId) {
    return true;
  }

  /**
   * 重装入监控数据.
   */
  private void onLoadMonitorValues(){
    JParamObject PO = JParamObject.Create();

    String[] pChannels = null;
    Vector   pValues   = null;

    try{
      JResponseObject RO = (JResponseObject) JActiveDComDM.
          AbstractDataActiveFramework.InvokeObjectMethod("KernelMonitorObject",
          "QueryValues", PO);
      if (RO != null && RO.GetErrorCode() == 0) {
        Object[] pOBJ = (Object[]) RO.ResponseObject;
        pChannels = (String[]) pOBJ[0];
        pValues = (Vector) pOBJ[1];
      }
    }
    catch(Exception E){
      E.printStackTrace();
    }

    mGridModel.setupChannels(pChannels);
    mGridModel.setupValues(pValues);

    mGridTable.setVisible(false);
    mGridTable.setVisible(true);

    /**
     * 更新图表。
     */
    mChartMonitor.appendValues(pValues);

  }

  private int[] getMonitorChannels(){
    int[] pRow = mGridTable.getSelectedRows();
    int[] pChanID = mGridModel.getMonitorChannels(pRow);
    return pChanID;
  }

  private String[] getMonitorChannelNames(){
    int[] pRow = mGridTable.getSelectedRows();
    String[] pNames = new String[pRow.length];

    for ( int i = 0; i < pRow.length; i ++){
      pNames[i]  = (String)(mGridModel.getValueAt(pRow[i],0));
    }

    return pNames;
  }

  /**
   * 时钟。
   * @param pTime Calendar
   * @return boolean
   */
  public boolean onTimer(String pId,Calendar pTime) {
    if ( pId.equals(T_UPDATE_TIME)){

      CETime.setText(TimeFormat.format(pTime.getTime()));
    }

    if ( pId.equals(T_MONITOR_TIME)){
      if ( mIsStarted){
        onLoadMonitorValues();
      }
    }

    return true;
  }

  public boolean isValid(String pID, IAction pAction) {
    return false;
  }

  public Object onCallBack(String pID, IAction pAction, ActionEvent pEvnt) {
    return "";
  }

  public IAction[] onQueryActions() {
    return mActList;
  }

  public IActionProvider getActionProvider() {
    return this;
  }

  /**
   * When an object implementing interface <code>Runnable</code> is used to
   * create a thread, starting the thread causes the object's <code>run</code>
   * method to be called in that separately executing thread.
   *
   * @todo Implement this java.lang.Runnable method
   */
  public void run() {
    while ( true ){
      if ( mIsStarted ){
        onLoadMonitorValues();
      }
      try{
        Thread.currentThread().sleep(5000);
      }catch(Exception E){}
    }
  }

}
