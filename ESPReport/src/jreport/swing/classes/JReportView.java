package jreport.swing.classes;

import java.awt.event.*;
import javax.swing.*;

import com.client.fwkview.FMISContentWindow;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.f1j.swing.*;
import com.f1j.util.*;
import jbof.application.classes.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;
import jreport.foundation.classes.*;
import jreport.jbof.classes.BOFReportObject.ReportExplorer.JReportObjectStub;
import jreport.model.classes.calculate.*;
import jreport.swing.classes.ReportBook.*;
import jreport.swing.classes.util.FormulaSelection;
import jreport.swing.classes.util.FormulaUtil;

import java.util.ResourceBundle;
import java.awt.Color;
import com.f1j.ss.CellFormat;
import jbof.gui.window.classes.JBOFChildWindow;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改: ChartViewListener
//--------------------------------------------------------------------------------------------------
public class JReportView extends JBook implements SelectionChangedListener,CancelEditListener,
                                                  EndEditListener,EndRecalcListener,ModifiedListener,ObjectListener,
                                                  StartEditListener,StartRecalcListener,UpdateListener,ValidationFailedListener,
                                                  ViewChangedListener,MouseListener {
static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.Language");
  private JReportDataModel ReportModel = null;
  //
  public  JBookComboBox  BookComboBox  = null;
  public  JBookTextField BookTextField = null;
  //
  public JButton bnCancelSum = null;
  public JButton bnDHOK      = null;
  public JButton bnFuncWzd   = null;
  public JComboBox cbFH = null;
  public JCheckBox cbJYGS = null;
  // 计算管理器
  public static JCalculateManager CalculateManager = null;
  // 函数管理器
  public static JFunctionManager  FunctionManager  = null;
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object CreateReport(JParamObject PO) {
    JReportDataModel RM;
    try {
      String ModelClassName;
      ModelClassName = PO.GetValueByParamName("ModelClassName");
      RM  = (JReportDataModel)Class.forName(ModelClassName).newInstance();
      RM.ReportView = this;
      RM.CreateReport(PO);
      InitEventListener();
      setModel(RM);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public Object OpenReport(Object ROE,JParamObject PO) {
    JReportDataModel RM;
	JResponseObject RO=null;
    try {
      String ModelClassName;
      String sYSDW = "";
      String ADD_BH="";
      int    ADD_TYPE = -1; 
      ADD_BH = PO.GetValueByParamName("ADD_BH","");
      ADD_TYPE = PO.GetIntByParamName("ADD_TYPE",JReportObjectStub.STUB_TYPE_REPORT);
      if ( ADD_TYPE == JReportObjectStub.STUB_TYPE_DWZD ) {
    	  RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "GetYSDW", PO);
    	  if (RO != null && RO.ErrorCode == 0) {
    		  sYSDW = (String) RO.ResponseObject;
    	  }
    	  if ( !sYSDW.equals("")) {
    		  PO.SetValueByParamName("ADD_BH", sYSDW);
    		  PO.SetIntByParamName("IS_YSDW", 1);
    		  PO.SetValueByParamName("REAL_BH", ADD_BH);
    	  }
      }
      
      
      ModelClassName = PO.GetValueByParamName("ModelClassName");
      RM  = (JReportDataModel)Class.forName(ModelClassName).newInstance();
      RM.ReportView = this;
      setModel(RM);
      RM.OpenReportObject(ROE,PO);
      InitEventListener();
      setFixedRow(0);
      setFixedCol(0);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return null;
  }
  public int  OpenReport(int ADD_TYPE,String ADD_BH,String BBZD_BH,String BBZD_DATE,boolean bShowRowHeading,boolean bShowColHeading) {
	  JReportDataModel RM=null;
	  JResponseObject RO=null;
	  JParamObject PO=null;
	  Object ROE = null ;
	  try {
		String ModelClassName;
		ModelClassName = "jreport.swing.classes.JReportModel";
		RM  = (JReportDataModel)Class.forName(ModelClassName).newInstance();
		RM.ReportView = this;
		setModel(RM);
		PO = JParamObject.Create();
		PO.SetIntByParamName("ADD_TYPE",ADD_TYPE);
		PO.SetValueByParamName("ADD_BH",ADD_BH);
		PO.SetValueByParamName("BBZD_BH",BBZD_BH);
		PO.SetValueByParamName("BBZD_DATE",BBZD_DATE);

		RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "OpenReportObject", PO);
		ADD_BH = PO.GetValueByParamName("ADD_BH");
		if (ADD_BH != null && ADD_BH.trim().length() == 0) {
			ADD_BH = null;
		}

		if (RO != null && RO.ErrorCode == 0) {
			ROE = (JReportObjectEntity) RO.ResponseObject;
		}
		RM.OpenReportObject(ROE,PO);

        this.setShowRowHeading(bShowRowHeading);
        this.setShowColHeading(bShowColHeading);

//		InitEventListener();
		setFixedRow(0);
		setFixedCol(0);
	  } catch ( Exception e ) {
		e.printStackTrace();
	  }
	  return 0;
  }
  public String GetRealRptSvr() {
	  String sSvrName = "";
	  Object childwindow=com.efounder.eai.ide.EnterpriseExplorer.ContentView.getActiveWindow();
	  if(childwindow instanceof FMISContentWindow) {
		  childwindow = ( (FMISContentWindow) childwindow).getFMISComp();
		  if ( childwindow instanceof JBOFChildWindow ) {
			  sSvrName = ((JBOFChildWindow)childwindow).getPageBaseUrl();
		  }
	  }
	  if ( !sSvrName.equals("") && sSvrName != null ) {
		  return sSvrName + ".";
	  } else {
		  sSvrName = "";
	  }
	  return sSvrName;
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public boolean SaveReport() {
      this.removeModifiedListener(this); //因为变动表格式保存后会重新读取变动行，让系统以为报表又修改了 liukun2010.10.27
      boolean bRes = this.ReportModel.SaveReportObject();
      if ( bRes ) {
          this.setModified(false);
      }
	  this.addModifiedListener(this);   //保存结束，恢复监听。
      return bRes;
  }
  //add by fsz 2004.5.11
  public boolean SaveReportAs(String bh,String mc,String date){
    return ((JReportModel)ReportModel).SaveReportObjectAs(bh,mc,date);
  }
  //end
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JReportView() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void setPublicAttrib() {
    try {
      setAllowMoveRange(false);
      setAllowAutoFill(false);
      setAllowObjectSelections(true);
      setAllowDesigner(true);
      setAllowLockedCellsErrMsg(false);
      setShowOutlines(true);
      setAllowFillRange(false);
      setAllowObjectSelections(true);
      setShowEditBar(false);
      setShowEditBarCellRef(false);
      setShowSelections(eShowOn);
      // 设置默认字体
      setDefaultFontName(JXMLResource.FontName);
      // 设置最大行与列
      /**
       * 默认设置为报表的行数与列数
       */
      // setMaxCol(1024);
//      setMaxRow(65535);
      setShowGridLines(false); //不显示表格线
      // 为变动表处理
      setRowSummaryBeforeDetail(true);
      setColSummaryBeforeDetail(true);

      //
//      for(int i=0;i<ReportView.getComponentCount();i++) {
//        ReportView.getComponent(i).setFont(Font.getFont("Dialog"));
//      }
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
  void InitEventListener() {
    // add by xSkyline
    JReportViewEditor.hideCSGRObject(this);
    this.addMouseListener(this);
    this.addSelectionChangedListener(this);
    this.addCancelEditListener(this);
//    this.addChartViewListener(this);
    this.addEndEditListener(this);
    this.addEndRecalcListener(this);
    this.addModifiedListener(this);
    this.addObjectListener(this);
    this.addStartEditListener(this);
    this.addStartRecalcListener(this);
    this.addUpdateListener(this);
    this.addValidationFailedListener(this);
    this.addViewChangedListener(this);

    addKeyListener(new KeyAdapter(){
        public void keyReleased(KeyEvent e) {
            /**
             * 不允许编辑时不能删除数据或公式
             * modified by hufeng 2008.2.22
             */
            if (!ReportModel.isAllowEditData()) {
                return;
            }
            int status = getModel().getDataStatus();
            if (!ReportModel.isAllowEditGS() && status != JReportModel.MODEL_STATUS_DATA) {
                return;
            }

            // 删除数据或公式
            if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                if (status == JReportModel.MODEL_STATUS_DATA) {
                    DeleteData();
                } else if (status == JReportModel.MODEL_STATUS_JSGS) {
                    DeleteForumlaOne();
                } else if (status == JReportModel.MODEL_STATUS_JYGS) {
                    DeleteForumlaOne();
                } else if (status == JReportModel.MODEL_STATUS_BBPZ) {
                    DeleteComment();
                } else if (status == JReportModel.MODEL_STATUS_ZSGS) {
                    DeleteForumlaOne();
                }
            }
        }
        });
    if(BookComboBox!=null&&BookComboBox.getEditor()!=null&&BookComboBox.getEditor().getEditorComponent()!=null){
        BookComboBox.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_DELETE) {
                    processBookCombobox();
                }
            }
        });
    }//End of if
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public int setModel(JReportDataModel rm) {
    ReportModel = rm;
    return 0;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JReportDataModel getModel() {
    return ReportModel;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void selectionChanged(SelectionChangedEvent selectionChangedEvent) {
    if ( !ReportModel.selectionChanged(selectionChangedEvent) ) {
      setBookTextFieldText();
      setBookComboBoxText();
    }
    // 处理CellEditor
    JReportViewEditor.selectionChanged(this,selectionChangedEvent);
  }
  protected ICellEditor CellEditor = null;
  public void setCellEditor(ICellEditor ce) {
    CellEditor = ce;
  }
  public ICellEditor getCellEditor() {
    return CellEditor;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void setBookComboBoxText() {
    try {
      String T1,T2,Text;
      T1 = this.formatRCNr(this.getSelStartRow(),this.getSelStartCol(),false);
      if ( this.getSelStartRow() == this.getSelEndRow() &&
           this.getSelStartCol() == this.getSelEndCol() ) {
        Text = T1;
      } else {
        T2 = this.formatRCNr(this.getSelEndRow(),this.getSelEndCol(),false);
        Text = T1 + ":" + T2;
      }
      this.BookComboBox.setSelectedItem(Text);

    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  //对公式作用范围的修改
  private void processBookCombobox(){
      String text=((String)BookComboBox.getSelectedItem()).trim();
      BookTextField.isCellRange=text;
      BookTextField.getEditor().getEditorComponent().requestFocus();
     /* if(!Character.isLetter(text.charAt(0))) return;//起始坐标字符非法
      String[] coords=text.split(":");
      if(coords.length>1){//区域
          int[] coordsStart=ReportUtil.getCoordinateFromText(coords[0]);
          int[] coordsEnd=ReportUtil.getCoordinateFromText(coords[1]);
          int startCol=coordsStart[0];
          int startRow=coordsStart[1];
          int endCol=coordsEnd[0];
          int endRow=coordsEnd[1];
          int temp = 0;
          if (startRow > endRow || startCol > endCol) {//起点坐标>终点
              return ;
          }else{
              int offseth = endRow - startRow;
              int offsetl = endCol - startCol;
              //计算公式
              if(ReportModel.getDataStatus()==((JReportModel)ReportModel).MODEL_STATUS_DATA||
                 ReportModel.getDataStatus()==((JReportModel)ReportModel).MODEL_STATUS_JSGS){
                  JUnitInfo UI=JUnitInfo.GetUnitInfoByHL(getActiveRow()+1,getActiveCol()+1,(JReportModel)ReportModel);
                  if(UI==null) return;
                  UI.setOffset(offseth, offsetl);
              }//校验公式
              else if(ReportModel.getDataStatus()==((JReportModel)ReportModel).MODEL_STATUS_JYGS){
                  JUnitInfo UI=JUnitInfo.GetUnitInfoByHL(getActiveRow()+1,getActiveCol()+1,(JReportModel)ReportModel);
                  if(UI==null) return;
                  int index=((Integer)ReportModel.getModelInfo("JYGS_INDEX",new Integer(0))).intValue();
                  UI.setOffset(offseth, offsetl,index);
              }
          }
      }
      else{//单元格
          //计算公式
          if(ReportModel.getDataStatus()==((JReportModel)ReportModel).MODEL_STATUS_DATA||
             ReportModel.getDataStatus()==((JReportModel)ReportModel).MODEL_STATUS_JSGS){
              JUnitInfo UI=JUnitInfo.GetUnitInfoByHL(getActiveRow()+1,getActiveCol()+1,(JReportModel)ReportModel);
              if(UI==null) return;
              UI.setOffset(0, 0);
          }//校验公式
          else if(ReportModel.getDataStatus()==((JReportModel)ReportModel).MODEL_STATUS_JYGS){
              JUnitInfo UI=JUnitInfo.GetUnitInfoByHL(getActiveRow()+1,getActiveCol()+1,(JReportModel)ReportModel);
              if(UI==null) return;
              int index=((Integer)ReportModel.getModelInfo("JYGS_INDEX",new Integer(0))).intValue();
              UI.setOffset(0, 0,index);
          }
//          JUnitInfo UI=JUnitInfo.GetUnitInfoByHL(getActiveRow()+1,getActiveCol()+1,(JReportModel)ReportModel);
//          int coords2=ReportUtil.getCoordinateFromText(coords[0])[0];
//          UI.setOffset(0,0);
      }*/
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void setBookTextFieldText() {
    String Text=null;
    try {
      Text = this.getEntry();
      BookTextField.setText(Text,false);
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
  public Object setCellF1(String F1String) throws Exception{
//    try {
    int phyRow = getPhySelStartRow();
      int phyCol = getSelStartCol();
      String Text = ReportModel.setExternalF1(getRow()+1,getCol()+1,F1String,phyRow,phyCol);
      if ( Text != null ) {
        this.setEntry(Text);
      }
//    } catch ( Exception e ) {
//      JOptionPane.showMessageDialog(null,e.getMessage(),"系统提示",JOptionPane.ERROR_MESSAGE);
//      e.printStackTrace();
//    }
//    JUnitInfo UI;
//    try {
//      if ( F1String.startsWith("=") ) F1String = F1String.substring(1,F1String.length());
//      UI = JUnitInfo.GetUnitInfoByHL(getRow()+1,getCol()+1,1,ReportModel);
//      if ( UI != null ) {
//        UI.setUIGSX(F1String,getSelEndRow()-getSelStartRow(),getSelEndCol()-getSelStartCol());
//      }
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
    return null;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void cancelEdit(CancelEditEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void endEdit(EndEditEvent e) {
    ProcessEndEdit(e);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ProcessEndEdit(EndEditEvent e) {
    String Text;
    Text = e.getEditString();
    try {
      int phyRow = getPhySelStartRow();
      int phyCol = getSelStartCol();
      e.setEditString(ReportModel.setExternalF1(getRow()+1,getCol()+1,Text,phyRow,phyCol));
    } catch ( Exception ee ) {
      e.setCanceled(true);
      JOptionPane.showMessageDialog(null,ee.getMessage(),res.getString("String_81"),JOptionPane.ERROR_MESSAGE);
      ee.printStackTrace();
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void endRecalc(EndRecalcEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void modified(ModifiedEvent e) {
      this.setModified();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void objectClicked(ObjectEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void objectDblClicked(ObjectEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void objectGotFocus(ObjectEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void objectLostFocus(ObjectEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void objectValueChanged(ObjectEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
//  public void selectionChanged(ChartViewEvent e) {
//
//  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void startEdit(StartEditEvent e) {
//    String Text = e.getEditString();
      String Text = getF1String(e);
      JReportModel RM = (JReportModel) ReportModel;

      /**
       * 如果是批注状态，双击弹出批注定义窗口
       */
//      if (RM.getDataStatus() == RM.MODEL_STATUS_BBPZ) {
//          JCommentDefineDialog dlg = new JCommentDefineDialog(JActiveDComDM.MainApplication.MainWindow, "附注定义窗口", false, RM);
//          dlg.CenterWindow();
//          dlg.setVisible(true);
//          return;
//      }

      if (Text == null) {
          Text = e.getEditString();
      } else {
          if (RM.getDataStatus() == RM.MODEL_STATUS_BBPZ) {
          } else {
              Text = "=" + Text;
          }
      }
      if (Text != null && Text.trim().length() == 0) Text = "";
      e.setEditString(Text);
  }

  /**
   * 设置要显示的内容
   */
  private String getF1String(StartEditEvent e) {
      JReportModel RM = (JReportModel) ReportModel;
      if(RM.getDataStatus() == RM.MODEL_STATUS_DATA){
          return null;
      }

      try {
          JUnitInfo UI = null;
          int phyRow = getPhySelStartRow();
          int phyCol = getSelStartCol();
          int Row = getRow() + 1;
          int Col = getCol() + 1;
          int RowStatus = JBdhUtils.checkPhyRowStatus(RM, phyRow);
          if (RowStatus == 3) { // 新变动行
              UI = JBdhInfo.getBDUnitInfo(RM, Row - 1, Col - 1, phyRow, phyCol);
          }
          else {
              UI = JUnitInfo.GetUnitInfoByHL(Row, Col, 1, RM);
          }

          // 返回定义的公式信息
          if (RM.getDataStatus() == RM.MODEL_STATUS_JSGS) {
              return UI.getUIGSX(RM);
          } else if (RM.getDataStatus() == RM.MODEL_STATUS_JYGS) {
              return UI.getUIJYGS(RM, 0);
          } else if (RM.getDataStatus() == RM.MODEL_STATUS_BBPZ) {
              return UI.getUIComment(RM, 0);
          } else if (RM.getDataStatus() == RM.MODEL_STATUS_ZSGS) {
              return UI.getUIZSGSX(RM);
          } 

          return null;
      }
      catch (Exception ee) {
          ee.printStackTrace();
          return null;
      }
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void startRecalc(StartRecalcEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void update(UpdateEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void validationFailed(ValidationFailedEvent e) {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void viewChanged(ViewChangedEvent e) {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void viewChanged(ChartViewEvent e) {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private void jbInit() throws Exception {
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void BOFCopy() {

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void BOFDelete() {

  }
  public void setModified() {
    ReportModel.setModified();
  }
  public void setModified(boolean isModified) {
	ReportModel.setModified(isModified);
  }
  public int getModified() {
    return ReportModel.getModified();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void DeleteForumlaOne() {
    int SRow, SCol, ERow, ECol;
    try {
      int phySRow,phySCol,phyERow,phyECol;
      phySRow = this.getPhySelStartRow();
      phySCol = this.getSelStartCol();
      phyERow = this.getPhySelEndRow();
      phyECol = this.getSelEndCol();
      if ( !checkArea(phySRow,phyERow) ) return;

      SRow = getSelStartRow()+1;SCol = getSelStartCol()+1;
      ERow = getSelEndRow()+1;  ECol = getSelEndCol()+1;
      ReportModel.DeleteForumlaOne(SRow,SCol,ERow,ECol,phySRow,phySCol,phyERow,phyECol);
      this.BookTextField.setText("",false);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  public void DeleteComment() {
      int SRow, SCol, ERow, ECol;
      try {
          int phySRow, phySCol, phyERow, phyECol;
          phySRow = this.getPhySelStartRow();
          phySCol = this.getSelStartCol();
          phyERow = this.getPhySelEndRow();
          phyECol = this.getSelEndCol();
          if (!checkArea(phySRow, phyERow))return;

          SRow = getSelStartRow() + 1;
          SCol = getSelStartCol() + 1;
          ERow = getSelEndRow() + 1;
          ECol = getSelEndCol() + 1;
          ReportModel.DeleteComment(SRow, SCol, ERow, ECol, phySRow, phySCol, phyERow, phyECol);
          BookTextField.setText("", false);
      } catch (Exception e) {
          e.printStackTrace();
      }
  }

  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void InsertRow() {
    int SRow,ERow;
    try {
      SRow = this.getPhySelStartRow();
      ERow = this.getPhySelEndRow();
      int maxRow = this.getMaxRow();
      if((ERow - SRow) >= maxRow){
          JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,res.getString("String_85"));
          return;
      }
      ReportModel.InsertRow(SRow,ERow);
    } catch ( Exception e ) {
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, e.getMessage(), res.getString("String_86"), JOptionPane.ERROR_MESSAGE);
//      e.printStackTrace();
    }
//    int SRow,ERow;
//    try {
//      SRow = this.getSelStartRow()+1;ERow = this.getSelEndRow()+1;
//      //add by fsz 2004.10.22
//      JBdhInfo bdh=ReportModel.processInsertRowInBdh(SRow,ERow);//end
//      this.editInsert(eShiftRows);
//      ReportModel.InsertRow(SRow,ERow);
//      //add by fsz
//      if(bdh !=null)
//        this.setRowOutlineLevel(bdh.RowInfo.HZD_ZB-1,
//                                bdh.RowInfo.HZD_ZB+bdh.BDH_NUM-1,1,true);
//      //end
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void InsertCol() {
    int SCol,ECol,colCount;
    try {
      SCol = this.getSelStartCol()+1;ECol = this.getSelEndCol()+1;
      colCount = this.getMaxCol();
      if((ECol - SCol) >= colCount){
          JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,res.getString("String_87"));
          return;
      }
      //add by fsz 2004.10.22
      JBdlInfo bdl=ReportModel.processInsertColInBdl(SCol,ECol);//end
      this.editInsert(eShiftColumns);
      ReportModel.InsertCol(SCol,ECol);
      //add by fsz
     if(bdl !=null)
       this.setColOutlineLevel(bdl.ColInfo.LZD_ZB-1,
                               bdl.ColInfo.LZD_ZB+bdl.BDL_NUM-1,1,true);
     //end

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
  public void DeleteRow() {
    int SRow,ERow;
    try {
      SRow = this.getPhySelStartRow();
      ERow = this.getPhySelEndRow();
      ReportModel.DeleteRow(SRow,ERow);

//      SRow = this.getSelStartRow()+1;ERow = this.getSelEndRow()+1;
//      this.editDelete(eShiftRows);
//      ReportModel.DeleteRow(SRow,ERow);
    } catch ( Exception e ) {
      JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, e.getMessage(), res.getString("String_88"), JOptionPane.ERROR_MESSAGE);
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void DeleteCol() {
    int SCol,ECol;
    try {
      SCol = this.getSelStartCol()+1;ECol = this.getSelEndCol()+1;
      this.editDelete(eShiftColumns);
      ReportModel.DeleteCol(SCol,ECol);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
  public boolean checkArea() {
    try {
      int phySRow = this.getPhySelStartRow();
      int phyERow = this.getPhySelEndRow();
      return checkArea(phySRow,phyERow);
    } catch ( Exception e ) {
      e.printStackTrace();
    }
    return false;
  }
  public boolean checkArea(int phySRow,int phyERow) {
    if ( ReportModel instanceof JReportModel ) {
      int TwoRowStatus = JBdhUtils.checkPhyTwoRowStatus((JReportModel)ReportModel,phySRow,phyERow);
      if ( TwoRowStatus == 4 ) {
        JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,res.getString("String_89"),res.getString("String_90"),JOptionPane.ERROR_MESSAGE);
        return false;
      }
    }
    return true;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void DeleteData() {
    try {
      int phySRow,phySCol,phyERow,phyECol;
      phySRow = this.getPhySelStartRow();
      phySCol = this.getSelStartCol();
      phyERow = this.getPhySelEndRow();
      phyECol = this.getSelEndCol();
      if ( !checkArea(phySRow,phyERow) ) return;
      editClear(eClearValues);
      ReportModel.ChangeUnitData(getSelStartRow()+1,getSelStartCol()+1,
                                     getSelEndRow()+1,getSelEndCol()+1,phySRow,phySCol,phyERow,phyECol);
      this.setModified();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  /**
   * 在固定行上设置枚举字典
   * 应集团决算需求改进
   * modified by liukun 2008.11.15
   */
  public void SetMJZD() {
    try {
      JReportModel RM = (JReportModel) ReportModel;
      if ( RM.ADD_TYPE != JReportModel.STUB_TYPE_REPORT ) {
          return;
      }
      int RowStatus = 0;
      int phySRow,phySCol,phyERow,phyECol;
      phySRow = this.getPhySelStartRow();
      phySCol = this.getSelStartCol();
      phyERow = this.getPhySelEndRow();
      phyECol = this.getSelEndCol();
      if ( !checkArea(phySRow,phyERow) ) return;
      JUnitInfo UI = null;
      for ( int Row=phySRow+1;Row<=phyERow+1;Row++ ) {
          RowStatus = JBdhUtils.checkPhyRowStatus(RM,Row);
          if ( RowStatus == 2 || RowStatus == 3)
              continue;
          for (int Col=phySCol+1;Col<=phyECol+1;Col++ ) {
              UI = JUnitInfo.GetUnitInfoByHL(Row, Col, 1,RM);
              UI.DYZD_MJZD = true;
          }
      }

    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  public void ClearData() {
    try {
      int phySRow,phySCol,phyERow,phyECol;
      phySRow = 0;
      phySCol = 0;
      phyERow = super.getMaxRow();
      phyECol = super.getMaxCol();
//      super.setSelEndCol(super.getMaxCol());
//      super.setSelEndRow(super.getMaxRow());
      if ( !checkArea(phySRow,phyERow) ) return;
//      editClear(eClearValues);
      ReportModel.ClearUnitData(1,1,1,1,phySRow,phySCol,phyERow,phyECol);
      this.setModified();
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  /**
   * 清除表内所有数据，不检查是否跨区域
   * 因为在重新汇总计算时，要先清除原表的内容，再刷新新的内容
   * 而在清除的过程中，如果此表涉及到变动行，则在检查跨区域时则过不去
   * add by hufeng 2005.10.19
   */
  public void ClearDataWithNoCheck(){
    try {
      int phySRow,phySCol,phyERow,phyECol;
      phySRow = 0;
      phySCol = 0;
      phyERow = super.getMaxRow();
      phyECol = super.getMaxCol();
      ReportModel.ClearUnitData(1,1,1,1,phySRow,phySCol,phyERow,phyECol);
      this.setModified();
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
  public void CopyData() {
//      LookAndFeel lookfeel = com.efounder.eai.service.dof.JDOFResourceObject.
//          getCurrentLookAndFeel();
//      LookAndFeel oldfeel = com.efounder.eai.service.dof.JDOFResourceObject.
//          getAlloyLookandFeel();
    try {
//        try{
//          javax.swing.UIManager.setLookAndFeel(oldfeel);
//        }catch(Exception ex){
//          javax.swing.UIManager.setLookAndFeel(lookfeel);
//        }
    	editCopy();
    	FormulaUtil.setClipBoardContents("");
    	
    	String asText="";
    	String asTmp = "";
    	int iStartRow = this.getPhySelStartRow();
    	int iEndRow   = this.getPhySelEndRow();
    	int iStartCol = this.getSelStartCol();
    	int iEndCol   = this.getSelEndCol();
    	if ( iStartRow<0 || iStartCol<0) {
    		return;
    	}
    	if ( iEndRow >  this.getMaxRow() ) {
    		iEndRow = this.getMaxRow();
    	}
    	if ( iEndCol >  this.getMaxCol() ) {
    		iEndCol = this.getMaxCol();
    	}
    	for ( int i=iStartRow;i<=iEndRow;i++){
        	for ( int j=iStartCol;j<=iEndCol;j++){
        		asTmp = getPhyText(i,j);
        		if ( asTmp!=null && !asTmp.equals("0"))
        			asText += asTmp;
        		if (j!=iEndCol)
        			asText += "\t";
        	}
        //	if ( i!= iEndRow )
        	asText += "\n";
    	}
    	FormulaUtil.setClipBoardContents(asText);
    	
//        editCopy();
//        javax.swing.UIManager.setLookAndFeel(lookfeel);
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
  public void PasteData() {
    try {
      int phySRow,phySCol,phyERow,phyECol;
      phySRow = this.getPhySelStartRow();
      phySCol = this.getSelStartCol();
      phyERow = this.getPhySelEndRow();
      phyECol = this.getSelEndCol();
      if ( !checkArea(phySRow,phyERow) ) return;
      String contents = FormulaUtil.getClipBoardContents();
      if (contents != null && contents.length() > 0 &&
          !contents.startsWith(FormulaSelection.FORMULA_PREFIX)) {
          editPaste();
          ReportModel.ChangeUnitData(getSelStartRow()+1,getSelStartCol()+1,
                                     getSelEndRow()+1,getSelEndCol()+1,phySRow,phySCol,phyERow,phyECol);
          this.setModified();
      }
//      boolean bOk = this.isCanEditPaste();
//      bOk = isCanEditPasteSpecial();
//      editPasteSpecial(this.eCopyValues);
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
  public void CutData() {
    try {
      int phySRow,phySCol,phyERow,phyECol;
      phySRow = this.getPhySelStartRow();
      phySCol = this.getSelStartCol();
      phyERow = this.getPhySelEndRow();
      phyECol = this.getSelEndCol();
      if ( !checkArea(phySRow,phyERow) ) return;
      //editCopy();
      editCut();
      //editClear(eClearValues);
      ReportModel.ChangeUnitData(getSelStartRow()+1,getSelStartCol()+1,
                                     getSelEndRow()+1,getSelEndCol()+1,phySRow,phySCol,phyERow,phyECol);
      this.setModified();
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
  public int CalcReport(JContextObject CO) {
    return ReportModel.CalcReport(CO);
  }
  public int DealReport(JContextObject CO) {
	return ReportModel.DealReport(CO);
  }
  public int AdjustReport(JContextObject CO) {
  return ReportModel.AdjustReport(CO);
}

  public int CountReport(JContextObject CO) {
    return ReportModel.CountReport(CO);
  }


  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public int CheckReport(JContextObject CO,JParamObject PO) {
    return ReportModel.CheckReport(CO,PO);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public int CheckSetReport(JContextObject CO) {
    return ReportModel.CheckSetReport(CO);
  }
  //add by lqk  审核报告
  public int CheckSetReport1(JContextObject CO) {
	  return ReportModel.CheckSetReport1(CO);
	  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public int RefreshData(JContextObject CO) {
    return ReportModel.RefreshData(CO);
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void ReportModelAttrib() {
    ReportModel.ReportModelAttrib();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void PrintView() {
    try {
		JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject","PrintView",this);
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void PageSetup() {
   /* try {
      com.f1j.swing.ss.PageSetupDlg Dlg = new com.f1j.swing.ss.PageSetupDlg(this);
      Dlg.setModal(true);
      Dlg.addWindowListener(new JMulLangF1Object());
      Dlg.show();
      Dlg.dispose();
    } catch ( Exception e ){
      e.printStackTrace();
    }*/
/*
   SwingUtilities.invokeLater(new InnerThread(this) {
      public void run() {
        try {
          JBook Book = QueryWindow;
          com.f1j.swing.ss.PageSetupDlg PageDlg = new com.f1j.swing.ss.PageSetupDlg(Book);
          PageDlg.addWindowListener(new JMulLangF1Object());
          PageDlg.setVisible(true);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    return ;
*/
	SwingUtilities.invokeLater(new InnerThread(this) {
	  //F1 LoopAndFeel与SAP相冲突，在打开页面设置之前处理之
	  LookAndFeel lookfeel = com.efounder.eai.service.dof.JDOFResourceObject.
		  getCurrentLookAndFeel();
	  LookAndFeel oldfeel = com.efounder.eai.service.dof.JDOFResourceObject.
		  getAlloyLookandFeel();
	  public void run() {
		try {
		  try{
			javax.swing.UIManager.setLookAndFeel(oldfeel);
		  }catch(Exception ex){
			javax.swing.UIManager.setLookAndFeel(lookfeel);
		  }
		  JBook Book = QueryWindow;
		  com.f1j.swing.ss.PageSetupDlg PageDlg = new com.f1j.swing.ss.
			  PageSetupDlg(Book);
		  PageDlg.show();
		  javax.swing.UIManager.setLookAndFeel(lookfeel);
		}
		catch (Exception e) {
		  e.printStackTrace();
		}
	  }
	});
	return;
  }

  class InnerThread
      extends Thread {
      JBook QueryWindow = null;
      InnerThread(JBook qw) {
          QueryWindow = qw;
      }

  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void PrintReport() {
    try {
		JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject","PrintReport",this);
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public void FilePrint(boolean ShowDialog,Object PJob,JParamObject PO) {
      try {
        SavePrintParamObject(PO);
        ApplyPrintParamObject(PO);
        filePrint(ShowDialog, PJob);
        LoadPrintParamObject(PO);
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
  public void FilePrint(boolean ShowDialog,JParamObject PO) {
      try {
        SavePrintParamObject(PO);
        ApplyPrintParamObject(PO);
        this.filePrint(ShowDialog);
        LoadPrintParamObject(PO);
      } catch ( Exception e ) {
        e.printStackTrace();
      }
  }
  void ApplyPrintParamObject(JParamObject PO) {
    try {
      // 如果需要设置
      int cbReportDefaultPage = PO.GetIntByParamName("cbReportDefaultPage",1);
      if ( cbReportDefaultPage == 0 ) {
        setPrintPaperSize( (short) PO.GetIntByParamName("PrintPaperSize"));
        setPrintLandscape(PO.GetIntByParamName("isPrintLandscape")==1?true:false);
      }
      // 如果需要设置
      int cbPrintDefaultSize = PO.GetIntByParamName("cbPrintDefaultSize",1);
      if ( cbPrintDefaultSize == 0 ) {
        if(!(PO.GetIntByParamName("isPrintScaleFitToPage") == 1)){
          setPrintScale((short)PO.GetIntByParamName("PrintScale"));
        }else{
          setPrintScaleFitToPage(PO.GetIntByParamName("isPrintScaleFitToPage") == 1 ? true : false);
          setPrintScaleFitHPages( (short) PO.GetIntByParamName("PrintScaleFitHPages"));
          setPrintScaleFitVPages( (short) PO.GetIntByParamName("PrintScaleFitVPages"));
        }
      }

      setPrintNumberOfCopies((short)PO.GetIntByParamName("PrintNumberOfCopies"));
      setPrintAutoPageNumbering(PO.GetIntByParamName("isPrintAutoPageNumbering")==1?true:false);
      setPrintStartPageNumber(PO.GetIntByParamName("getPrintStartPageNumber"));

    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }

  void SavePrintParamObject(JParamObject PO) {
    PO.SetIntByParamName("Save_PrintPaperSize",getPrintPaperSize());
    PO.SetIntByParamName("Save_PrintScale",getPrintScale());
    PO.SetIntByParamName("Save_PrintScaleFitHPages",getPrintScaleFitHPages());
    PO.SetIntByParamName("Save_PrintScaleFitVPages",getPrintScaleFitVPages());
    PO.SetIntByParamName("Save_PrintNumberOfCopies",getPrintNumberOfCopies());
    PO.SetIntByParamName("Save_isPrintScaleFitToPage",isPrintScaleFitToPage()?1:0);
    PO.SetIntByParamName("Save_isPrintLandscape",isPrintLandscape()?1:0);
    PO.SetIntByParamName("Save_isPrintAutoPageNumbering",isPrintAutoPageNumbering()?1:0);
    PO.SetIntByParamName("Save_PrintStartPageNumber",getPrintStartPageNumber());
  }
  void LoadPrintParamObject(JParamObject PO) {
    try {
      setPrintPaperSize( (short) PO.GetIntByParamName("Save_PrintPaperSize"));
      setPrintScale((short)PO.GetIntByParamName("Save_PrintScale"));
      setPrintScaleFitHPages((short)PO.GetIntByParamName("Save_PrintScaleFitHPages"));
      setPrintScaleFitVPages((short)PO.GetIntByParamName("Save_PrintScaleFitVPages"));
      setPrintNumberOfCopies((short)PO.GetIntByParamName("Save_PrintNumberOfCopies"));
      setPrintScaleFitToPage(PO.GetIntByParamName("Save_isPrintScaleFitToPage")==1?true:false);
      setPrintLandscape(PO.GetIntByParamName("Save_isPrintLandscape")==1?true:false);
      setPrintAutoPageNumbering(PO.GetIntByParamName("Save_isPrintAutoPageNumbering")==1?true:false);
      setPrintStartPageNumber(PO.GetIntByParamName("Save_PrintStartPageNumber"));

    } catch ( Exception e ) {
      e.printStackTrace();
    }

  }
  public int getSelStartRow() throws F1Exception {
    int ORow = super.getSelStartRow();
    int NRow = getHeadRow(ORow);
    return NRow;
  }
  public int getPhySelStartRow() throws F1Exception {
    return super.getSelStartRow();
  }

  public int getSelEndRow() throws F1Exception {
    int ORow = super.getSelEndRow();
    int NRow = getHeadRow(ORow);
    return NRow;
  }
  public int getPhySelEndRow() throws F1Exception {
    return super.getSelEndRow();
  }

  public int getRow() throws F1Exception {
    int ORow = super.getRow();
    int NRow = getHeadRow(ORow);
    return NRow;
  }
  public int getPhyRow() throws F1Exception {
    return super.getRow();
  }
  public void setRow(int Row) throws F1Exception {
    // 根据逻辑行获取物理行
    int phyRow = JBdhUtils.getPhyRow(Row,this.ReportModel);
    super.setRow(phyRow);
    return;
  }

  /**
   * 只有有变动行时，才进行行转换
   * 在操作大表时，可以大大提高速度
   * @param row int
   * @param col int
   * @param text String
   * @throws F1Exception
   */
  public void setText(int logRow,int col,java.lang.String text) throws F1Exception {
      int phyRow = logRow;
      if(ReportModel != null &&
         ReportModel.getBDHList() != null &&
         ReportModel.getBDHList().size() > 0) {
          phyRow = JBdhUtils.getPhyRow(logRow, ReportModel);
      }
      super.setText(phyRow,col,text);
  }

  /**
   * 只有有变动行时，才进行行转换
   * 在操作大表时，可以大大提高速度
   * @param logRow int
   * @param col int
   * @return String
   * @throws F1Exception
   */
  public String getText(int logRow,int col) throws F1Exception {
      int phyRow = logRow;
      if(ReportModel != null &&
         ReportModel.getBDHList() != null &&
         ReportModel.getBDHList().size() > 0) {
          phyRow = JBdhUtils.getPhyRow(logRow, ReportModel);
      }
      return super.getText(phyRow,col);
  }

  public String getPhyText(int logRow,int col) throws F1Exception {
    return super.getText(logRow,col);
  }
  public void setPhyText(int row,int col,java.lang.String text) throws F1Exception {
    super.setText(row,col,text);
  }

  public void setPhyColor(int row, int col, Color color) throws F1Exception {
	  super.setExtraColor(color);
  }

  public void setColor(int SRow,int SCol,int ERow,int ECol,Color c) throws F1Exception {
	  CellFormat CF = null;
	  this.setSelection(SRow, SCol, ERow, ECol);
	  if (CF == null)
		  CF = getCellFormat();
      CF.setPattern(CellFormat.ePatternSolid);
	  CF.setPatternFG(c.getRGB());
	  setCellFormat(CF);
  }

  /**
   * 只有有变动行时，才进行行转换
   * 在操作大表时，可以大大提高速度
   * @param logRow int
   * @param col int
   * @param number double
   * @throws F1Exception
   */
  public void setNumber(int logRow,int col,double number) throws F1Exception {
    int phyRow = logRow;
    if(ReportModel != null &&
       ReportModel.getBDHList() != null &&
       ReportModel.getBDHList().size() > 0) {
        phyRow = JBdhUtils.getPhyRow(logRow, ReportModel);
    }
    super.setNumber(phyRow,col,number);
  }

  /**
   * 只有有变动行时，才进行行转换
   * 在操作大表时，可以大大提高速度
   * @param logRow int
   * @param col int
   * @return double
   * @throws F1Exception
   */
  public double getNumber(int logRow,int col) throws F1Exception {
      int phyRow = logRow;
      if(ReportModel != null &&
         ReportModel.getBDHList() != null &&
         ReportModel.getBDHList().size() > 0) {
          phyRow = JBdhUtils.getPhyRow(logRow, ReportModel);
      }
      return super.getNumber(phyRow,col);
  }
  public double getPhyNumber(int logRow,int col) throws F1Exception {
    return super.getNumber(logRow,col);
  }
  public void setPhyNumber(int row,int col,double number) throws F1Exception {
    super.setNumber(row,col,number);
  }
  public void setEntry(int logRow,int col,java.lang.String entry) throws F1Exception {
    int phyRow = JBdhUtils.getPhyRow(logRow,ReportModel);
    super.setEntry(phyRow,col,entry);
  }
  public String getEntry(int logRow,int col) throws F1Exception {
    int phyRow = JBdhUtils.getPhyRow(logRow,ReportModel);
    return super.getEntry(phyRow,col);
  }
  public String getPhyEntry(int logRow,int col) throws F1Exception {
    return super.getEntry(logRow,col);
  }
  public void setPhyEntry(int logRow,int col,java.lang.String entry) throws F1Exception {
    super.setEntry(logRow,col,entry);
  }

  public void setPhyRow(int Row) throws F1Exception {
    super.setRow(Row);
    return;
  }
  public int getActiveRow() {
    int row = super.getActiveRow();
    return getHeadRow(row);
  }
  public void setSelection(int row1,int col1,int row2,int col2) throws F1Exception {
    int phyRow1 = JBdhUtils.getPhyRow(row1,ReportModel);
    int phyRow2 = JBdhUtils.getPhyRow(row2,ReportModel);
    super.setSelection(phyRow1,col1,phyRow2,col2);
  }
  public void setPhySelection(int row1,int col1,int row2,int col2) throws F1Exception {
    super.setSelection(row1,col1,row2,col2);
  }
  public void setRowOutlineLevel(int row1,int row2,int outlineLevel,boolean additive) throws F1Exception {
    int phyRow1 = JBdhUtils.getPhyRow(row1,ReportModel);
    int phyRow2 = JBdhUtils.getPhyRow(row2,ReportModel);
    super.setRowOutlineLevel(phyRow1,phyRow2,outlineLevel,additive);
  }
  public void setPhyRowOutlineLevel(int row1,int row2,int outlineLevel,boolean additive) throws F1Exception {
    super.setRowOutlineLevel(row1,row2,outlineLevel,additive);
  }

  private int getHeadRow(int ORow) {
    return JBdhUtils.getHeadRow(ReportModel,ORow);
//    int NRow = ORow;String Text;
//    try {
//      Text = this.getRowText(ORow);
//      if ( Text != null ) {
//        if (Text.startsWith("+") || Text.startsWith("-")) {
//          Text = Text.substring(1, Text.length());
//        }
//        NRow = Integer.parseInt(Text) - 1;
//      } else {
//        NRow = ORow;
//      }
//    } catch ( Exception e ) {
//      e.printStackTrace();
//    }
//    return NRow;
  }
  /**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   */
  public void mouseClicked(MouseEvent e){
      if (e.getClickCount() == 2) {
      }
  }


  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  public void mousePressed(MouseEvent e) {
    if ( e.getClickCount() == 2 ) {

    }

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
