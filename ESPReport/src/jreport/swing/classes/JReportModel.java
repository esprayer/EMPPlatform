package jreport.swing.classes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import java.awt.Frame;
import javax.swing.JOptionPane;

import org.jdom.Attribute;
import org.jdom.Element;

import com.client.fwkview.FMISContentWindow;
import com.efounder.builder.meta.MetaDataManager;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.eai.application.classes.JBOFApplication;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.data.JResponseObject;
import com.efounder.pub.util.NumberFunction;
import com.efounder.sql.JConnection;
import com.f1j.ss.CellFormat;
import com.f1j.swing.JBook;
import com.f1j.util.F1Exception;
import jbof.application.classes.JContextObject;
import jfoundation.gui.window.classes.JFrameDialog;
import jframework.foundation.classes.JActiveDComDM;
import jframework.log.JLogManager;
import jreport.foundation.classes.JReportDataModel;
import jreport.foundation.classes.JReportDataObject;
import jreport.foundation.classes.JReportObjectEntity;
import jreport.jbof.classes.BOFReportObject.JBOFReportPropertyObject;
import jreport.jbof.classes.BOFReportObject.ReportExplorer.JReportObjectStub;
import jreport.swing.classes.ReportBook.JAddUnitInfo;
import jreport.swing.classes.ReportBook.JBDHDataInfo;
import jreport.swing.classes.ReportBook.JBdhInfo;
import jreport.swing.classes.ReportBook.JBdhUtils;
import jreport.swing.classes.ReportBook.JBdlInfo;
import jreport.swing.classes.ReportBook.JColInfo;
import jreport.swing.classes.ReportBook.JCommentInfo;
import jreport.swing.classes.ReportBook.JJygsInfo;
import jreport.swing.classes.ReportBook.JRowInfo;
import jreport.swing.classes.ReportBook.JUnitInfo;
import jreport.swing.classes.util.ReportUtil;
import jreport.swing.classes.wizard.JFunctionWizardObject;
import jreportfunction.pub.JReportPubFunc;
import jreportwh.jdof.classes.common.util.StringUtil;

import java.io.FileOutputStream;
import jreport.swing.classes.ReportBook.JTzgsInfo;
import java.sql.Statement;
import jreport.swing.classes.ReportBook.JTzUnitInfo;
import jreport.swing.classes.ReportBook.JBdhSaveUtils;
import jreport.swing.classes.ReportBook.JBDUnitInfo;
import java.math.*;
import jbof.gui.window.classes.JBOFChildWindow;


//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JReportModel
    extends JReportDataModel {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.Language");
	static BigDecimal bMaxn=new BigDecimal("100000000000000");

    public static JFunctionWizardObject WizardObject = null;
    public static final int STUB_TYPE_REPORT = 2;
    public static final int STUB_TYPE_LBZD = 4;
    public static final int STUB_TYPE_DWZD = 5;
    public static final int STUB_TYPE_BMZD = 6;
    public static final int STUB_TYPE_CBZD = 7;

    public static final int JY_JYGS=0x01;
    public static final int JY_FMDM=0x02;
    public static final int JY_SHGS=0x04;

    public String BBQX = null;
    public String ADDQX = null;

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JRowInfo HeadRowInfo = new JRowInfo();
    public int RowMaxOrde = 0;
    public JColInfo HeadColInfo = new JColInfo();
    public int ColMaxOrde = 0;
    public int BdhMaxOrde = 0;
    public int BdlMaxOrde = 0;
    public int JygsMaxOrde = 0;
    public int TzgsMaxOrde = 0;
    public int CommentMaxOrde = 0;

//  public JUnitInfo    HeadUnitInfo     = new JUnitInfo();
//  public JAddUnitInfo HeadAddUnitInfo  = new JAddUnitInfo();
    public Vector BdhInfoList = new Vector();
    public Vector BdlInfoList = new Vector();

    // 删除用链表
    public JRowInfo DelHeadRowInfo = new JRowInfo();
    public JColInfo DelHeadColInfo = new JColInfo();

    public Vector DelBdhInfoList = new Vector();
    public Vector DelBdlInfoList = new Vector();

    public ArrayList cellCountList = new ArrayList();

    public String TZZD_ORDE = "";
    public String TZZD_BH ="";//MF 调整字典编号
    public String TZZD_MC = "";//LK 调整字典名称
    public String TZZD_FH = "";//MF 调整字典符号
    public String TZZD_XS = "";//MF 调整字典系数
    public String TZZD_JD = "";//MF 调整字典精度
    public String TZZD_DW = "";//MF 调整字典单位
    

    public int BBZD_TITLE = 0;
    public int BBZD_HEAD = 0;
    public int BBZD_BT = 0;
    public int BBZD_HS = 0;
    public int BBZD_LS = 0;
    public int BBZD_FIXROW = 0;
    public int BBZD_FIXRN = 0;
    public int BBZD_FIXCOL = 0;
    public int BBZD_FIXCN = 0;
    public int BBZD_SPLIT = 0;
    public String BBZD_NTZK = "";
    public String BBZD_MODIFYDATE = "";
    public String BBZD_ZSHL = "";

    public String BBZD_BH = null;
    public String BBZD_DATE = null;
    public String BBZD_FCBZ = "0";
    public String BBZD_ALLFCBZ = "0";
    public String BBZD_DWWCBZ = "0";
    public String BBZD_LX = "1";
    public String BBZD_MC = null;
    public String BBZD_NB = "0";
    public String BBZD_SBBH = "";
    public String BBZD_SBXZ = "1";
    public String BBZD_XF = "0";
    public String BBZD_USE = "0"; //报表是否锁定
    public String BBZD_PTLB = "";//LQK
    /**
     * 报表复核或审核后不允许再修改
     * add by hufeng 2007.4.17
     */
    public String BBSH_SHBZ1 = "0"; // 报表复核
    public String BBSH_SHBZ2 = "0"; // 报表审核

    // 附加数据
    public String DWZD_CODE   = null;
    public String ADD_BH   = null;
    public String REAL_BH   = null;
    public String ADD_MC   = null;
    public int    ADD_TYPE = STUB_TYPE_REPORT;
    public String ADD_FCBZ = "0";
    public int    IS_YSDW  = 0;
    public int    IS_ZBTB  = 0;
    public String    IS_GFBB  = "0";
    // 报表属性修改标志
    public int ChangeLog = 0;
    public static final int MODEL_STATUS_DATA = 0;
    public static final int MODEL_STATUS_JSGS = 1;
    public static final int MODEL_STATUS_JYGS = 2;
    public static final int MODEL_STATUS_BBPZ = 3;
    public static final int MODEL_STATUS_TZGS = 4;//调整公式状态
    public static final int MODEL_STATUS_TZZT = 5;//使用调整金额打开报表
    public static final int MODEL_STATUS_ZSGS = 6;//调整公式状态
    public static final int MODEL_STATUS_ZBTB = 7;//指标填报状态
    public int DataStatus = MODEL_STATUS_DATA;



  //add BY lk
  public String CAL_BH    =  null; //计算类型编号
  public int    CAL_ISSAP = 0;     // 是否从SAP取数
  public String CAL_SOUCE = null;  // 取数源服务器
  public String CAL_DEST  = null;  // 数据存储服务器 估计用不到
    // 有关校验公式的标志设置
//  public String JYGS_FH = "=";
//  public int    JYGS_INDEX = 0;
//  public boolean JYGS_STAT = false;// false:修改,true:新加
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public ZipInputStream ZIS = null;
    public ZipOutputStream ZOS = null;
    public JReportObjectEntity ROE = new JReportObjectEntity(null, null);

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int getReportType() {
        return ADD_TYPE;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JReportModel() {
        super();
    }

    public String GetCaption() {
        String Text = "[" + BBZD_BH + "]" + BBZD_MC;
        /**
         * 显示标题加上日期
         * 应山东石油客户的要求
         * mofified by hufeng 2005.12.19
         */
        Text += "--" + BBZD_DATE;
        //
        if (ADD_BH != null) {
          Text += "--" + "[" + ADD_BH + "]" + ADD_MC + "";
//            Text += "<" + "[" + ADD_BH + "]" + ADD_MC + ">";
        }

        return Text;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int CreateReport(JParamObject PO) {
        GetReportAttribFromPO(PO);
        GetReportAdjustAttribFromPO(PO);
        ReportView.setPublicAttrib();
        setRowAttrib();
        setColAttrib();
        setReportFormat();
        ChangeLog = 1;
        /**
         * 设置最大行数和最大列数
         * add by hufeng 2005.9.19
         */
        try{
          ReportView.setMaxRow(this.BBZD_HS-1);
          ReportView.setMaxCol(this.BBZD_LS-1);
        }catch(Exception e){}
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void GetReportAttribFromPO(JParamObject PO) {
        TZZD_BH = PO.GetValueByParamName("TZZD_BH");
        TZZD_MC = PO.GetValueByParamName("TZZD_MC");
        TZZD_XS = PO.GetValueByParamName("TZZD_XS");
        TZZD_FH = PO.GetValueByParamName("TZZD_FH");
        TZZD_JD = PO.GetValueByParamName("TZZD_JD");

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: mf(2010.04.17)
    //实现: mf
    //修改:
    //------------------------------------------------------------------------------------------------
    void GetReportAdjustAttribFromPO(JParamObject PO) {
        BBZD_BH = PO.GetValueByParamName("BBZD_BH");
        BBZD_PTLB = PO.GetValueByParamName("BBZD_PTLB");//lqk
        BBZD_DATE = PO.GetValueByParamName("BBZD_DATE");
        BBZD_DATE = BBZD_DATE.substring(0, 6);
        BBZD_MC = PO.GetValueByParamName("BBZD_MC");
        BBZD_SBXZ = PO.GetValueByParamName("BBZD_SBXZ");
        BBZD_TITLE = PO.GetIntByParamName("BBZD_TITLE");
        BBZD_HEAD = PO.GetIntByParamName("BBZD_HEAD");
        BBZD_BT = PO.GetIntByParamName("BBZD_BT");
        BBZD_HS = BBZD_TITLE + BBZD_HEAD + BBZD_BT + PO.GetIntByParamName("BBZD_BW");
        BBZD_LS = PO.GetIntByParamName("BBZD_LS");
        BBZD_SBBH = PO.GetValueByParamName("BBZD_SBBH");
    }

    private void setReportFormat() {
        CellFormat CF;
        try {
            if (BBZD_TITLE != 0) {
                // 处理标题一
                this.ReportView.setSelection(0, 0, 0, BBZD_LS - 1);
                CF = ReportView.getCellFormat();
                ReportView.setText(0, 0, BBZD_MC);
                CF.setFontSize(24 * 20);
                CF.setHorizontalAlignment(CF.eHorizontalAlignmentCenterAcrossCells);
                ReportView.setCellFormat(CF);
                ReportView.setColWidthAuto(0, 0, 0, BBZD_LS - 1, true);
                // 处理标题二
                if (BBZD_TITLE > 1) {
                    JUnitInfo UI;
                    UI = JUnitInfo.GetUnitInfoByHL(BBZD_TITLE, 1, 1, this);
                    UI.setUIGSX("MACRO(@DWMC@)", 0, 0, this);
                    UI = JUnitInfo.GetUnitInfoByHL(BBZD_TITLE, BBZD_LS / 2, 1, this);
                    UI.setUIGSX("MACRO(@YYYY@@MM@)", 0, 0, this);
                    UI = JUnitInfo.GetUnitInfoByHL(BBZD_TITLE, BBZD_LS, 1, this);
                    UI.setUIGSX("MACRO(@JEDW@)", 0, 0, this);
                }
            }
            // 处理表头
            ReportView.setSelection(BBZD_TITLE, 0,
                                    BBZD_TITLE + BBZD_HEAD + BBZD_BT - 1, BBZD_LS - 1);
            int h = ReportView.getRowHeight(1);
            ReportView.setRowHeight(1, BBZD_TITLE + BBZD_HEAD + BBZD_BT - 1, (int) (h * 1.3), false, false);
            CF = ReportView.getCellFormat();
            CF.setTopBorder(CF.eBorderDouble);
            CF.setLeftBorder(CF.eBorderDouble);
            CF.setBottomBorder(CF.eBorderDouble);
            CF.setRightBorder(CF.eBorderDouble);
            CF.setHorizontalInsideBorder(CF.eBorderThin);
            CF.setVerticalInsideBorder(CF.eBorderThin);
            ReportView.setCellFormat(CF);
            ReportView.setRow(0);
            ReportView.setCol(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    protected void setRowAttrib() {
        for (int i = 1; i <= BBZD_HS; i++) {
            JRowInfo.GetRowInfoByZB(i, 1, this);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    protected void setColAttrib() {
        for (int i = 1; i <= BBZD_LS; i++) {
            JColInfo.GetColInfoByZB(i, 1, this);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int OpenReportObject(Object roe, JParamObject PO) {
        ADD_TYPE = PO.GetIntByParamName("ADD_TYPE", STUB_TYPE_REPORT);
        //add by mengfei 20091208

        TZZD_BH = PO.GetValueByParamName("TZZD_BH","");
        TZZD_MC = PO.GetValueByParamName("TZZD_MC","");
        TZZD_FH = PO.GetValueByParamName("TZZD_FH","");
        TZZD_XS = PO.GetValueByParamName("TZZD_XS","");
        TZZD_JD = PO.GetValueByParamName("TZZD_JD","");
        TZZD_DW = PO.GetValueByParamName("TZZD_DW","");

        IS_YSDW = PO.GetIntByParamName("IS_YSDW",0);
        IS_ZBTB = PO.GetIntByParamName("IS_ZBTB",0);
        IS_GFBB = PO.GetValueByParamName("IS_GFBB","0");
        BBZD_PTLB = PO.GetValueByParamName("BBZD_PTLB");//lqk
        
        REAL_BH = PO.GetValueByParamName("REAL_BH","");

        if (ADD_TYPE == -1) {
            ADD_TYPE = this.STUB_TYPE_REPORT;
        }
        ROE = (JReportObjectEntity) roe;
        OpenReportFormatObject();
        //add by fsz
        try {
            this.ReportView.setRowOutlineLevel(0, 65535, 0, false);
            this.ReportView.setColOutlineLevel(0, 1024, 0, false);
            ReportView.setTopRow(0);
            ReportView.setLeftCol(0);
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        //end
        OpenReportDataObject();
        // 刚打开的报表,肯定是没修改过的
        ReportView.setPublicAttrib();
        try {
        	int activeSheet = ReportView.getNumSheets() - 1;
            if(!ReportView.getSheetName(activeSheet).equals(BBZD_BH)){
                ReportView.setSheetName(activeSheet, BBZD_BH);
            }
//        	int activeSheet = 0;
////        	ReportView.deleteSheets(1, 1);
//        	ReportView.setNumSheets(1);
//        	ReportView.setSheetName(0, BBZD_BH);
//            ReportView.setSheet(activeSheet);
            /**
             * 设置最大行和最大列
             */
            ReportView.setMaxRow(getReportMaxRow() - 1);
            ReportView.setMaxCol(BBZD_LS - 1);

            /**
             * 如果设置了打印折页，自动刷新折页数据
             * modified by hufeng 2008.3.11
             */
            if(BBZD_SPLIT != 0){
                JSplitPage.SplitRefresh(ReportView);
                ReportView.setSheet(activeSheet);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //
        ClearChangeLogReport();
        // 权限
        BBQX = ROE.BBQX; // 报表权限

        ADDQX = ROE.ADDQX; // 附加权限
    //    ROE = null;
        return 0;
    }

    //报表：11111111        查询/打印/修改/计算/公式定义/汇总/单位修改/单位校验
    public boolean getBBQX(int pzw) {
        if (BBQX == null) {
            return true;
        }
        // add by fsz 7.25 旧版无此权限，直接返回TRUE
        if(pzw>6 && jreport.value.JREPORT.isOldDbVer())
          return true;
        //end
        String pz = BBQX.substring(pzw - 1, pzw);
        return pz.equals("1") ? true : false;
    }

    // 类别：11111100       查询/打印/修改/计算/汇总/公式定义/
    // 单位：11111000       查询/打印/修改/计算/公式修改/
    // 部门：11111000       查询/打印/修改/计算/公式修改/
    public boolean getADDQX(int pzw) {
        if (ADDQX == null) {
            return true;
        }
        String pz = ADDQX.substring(pzw - 1, pzw);
        return pz.equals("1") ? true : false;
    }

    /**
     * 当前报表是否锁定
     * @return boolean
     */
    public boolean isLock(){
        if(ADD_TYPE == STUB_TYPE_REPORT){
            try{
                int count = Integer.parseInt(BBZD_USE);
                int lock = count % 2;
                return lock == 1;
            }catch(Exception e){

            }
        }

        return false;
    }
    /**
     * 当前报表是否输入控制
     * @return boolean
     */

    public boolean getSRKZ() {
        String s;
        s = BBZD_XF;
        short mask = Integer.valueOf(s).shortValue();
        return mask >= 2 ? true : false;
    }
    /**
     * 当前报表是否下发报表(不能修改格式)
     * @return boolean
     */

    public boolean getBBXF() {
        String s;
        s = BBZD_XF;
        short mask = Integer.valueOf(s).shortValue();
        return (mask >= 1 && ADD_BH == null) ? true : false;
    }
    
    public String getTZBH() {
    	return TZZD_BH;
    }

    public boolean getBBFC() {
        String s;
        if(getALLBBFC())return true;
        if (ADD_TYPE == STUB_TYPE_LBZD || ADD_TYPE == STUB_TYPE_DWZD || ADD_TYPE == STUB_TYPE_BMZD || ADD_TYPE == STUB_TYPE_CBZD) {
            s = ADD_FCBZ;
        }
        else {
            s = BBZD_FCBZ;
        }
        short mask = Integer.valueOf(s).shortValue();
        return (mask & 0x01) > 0 ? true : false;
    }
/*
    public boolean getBBWC() {
        String s;
        if(getALLBBWC())return true;
        return false;
    }
*/

    public boolean getGSFC() {
        String s;
        if(getALLGSFC())return true;
        if (ADD_TYPE == STUB_TYPE_LBZD || ADD_TYPE == STUB_TYPE_DWZD || ADD_TYPE == STUB_TYPE_BMZD) {
            s = ADD_FCBZ;
        }
        else {
            s = BBZD_FCBZ;
        }
        short mask = Integer.valueOf(s).shortValue();
        if (getBBFC()) {
            return true;
        }
        return (mask & 0x02) > 0 ? true : false;
    }
    public boolean getALLBBFC(){
      short mask = Integer.valueOf(BBZD_ALLFCBZ).shortValue();
       return ( mask& 0x01) > 0 ? true : false;
    }
    public boolean getALLGSFC(){
      short mask = Integer.valueOf(BBZD_ALLFCBZ).shortValue();
      if(getALLBBFC())return true;
       return (mask & 0x02) > 0 ? true : false;
    }
    public boolean getDWWC(){
        short mask = Integer.valueOf(BBZD_DWWCBZ).shortValue();
         return ( mask& 0x01) > 0 && !BBZD_DATE.equals("201212") ? true : false;
      }


    /**
     * 报表是否复核
     * @return boolean
     */
    public boolean getBBFH(){
        if(BBSH_SHBZ1.equals("1")){
            return true;
        }
        return false;
    }

    /**
     * 报表是否审核
     * @return boolean
     */
    public boolean getBBSH(){
        if(BBSH_SHBZ2.equals("1")){
            return true;
        }
        return false;
    }

    /**
     * 是否允许编辑数据
     * @return boolean
     */
    public boolean isAllowEditData(){
        return (!(getBBFC() || getDWWC() || isLock() || getBBSH() || getBBFH() || getBBXF()) && bHaveModifyQx) || !TZZD_BH.equals("") ;
    }
    
    public boolean isYsdw(){
        return IS_YSDW==1;
    }

    public boolean isDwwc(){
    	return BBZD_DWWCBZ.equals("1") && !BBZD_DATE.equals("201212") ;
    }
    /**
     * 是否允许编辑公式
     * @return boolean
     */
    public boolean isAllowEditGS(){
       return ! (getGSFC()  || isLock() || getBBXF() );
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private int OpenReportFormatObject() {
        ByteArrayInputStream bis;
        try {
            bis = new ByteArrayInputStream(ROE.FormatObject);
            // 解压
            ZIS = new ZipInputStream(bis);
            ZipEntry zipEntry = ZIS.getNextEntry();
            try {
                if (ZIS != null) {
                    ReportView.read(ZIS);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                ZIS = null;
                zipEntry = null;
                // 清空以前的数据
                ROE.FormatObject = null;
            }
            try {
            	ReportView.setNumSheets(1);      //lk gf       	
                ReportView.setSheet(ReportView.getNumSheets() - 1);
//lk gf                ReportView.setSheet(ReportView.getNumSheets() - 1);
                ReportView.setShowZeroValues(false);
                //不允许设置为保护 modified hufeng 2007.10.30
                //ReportView.setEnableProtection(false);
				ReportView.setAllowInCellEditing(true);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private int OpenReportDataObject() {
        JReportDataObject RDO;
        try {
            RDO = new JReportDataObject(ROE.DataObject,ROE.getBdDataObject());
            GetReportDataModel(RDO);
            ROE.DataObject = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void GetReportDataModel(JReportDataObject RDO) {
        // 先清除再加载
        ReportView.ClearDataWithNoCheck();
        //
        GetReportAttrib(RDO);
        try {
            String sel = ReportView.getSelection();
//            ReportView.setSelection(BBZD_HEAD + BBZD_TITLE - 1, 0, ReportView.getMaxRow(), ReportView.getMaxCol());
//            ReportView.editClear(ReportView.eClearValues);
            ReportView.setSelection(sel);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        GetReportRowAttrib(RDO);
        GetReportColAttrib(RDO);
        //获取调整公式信息
        GetReportTzgsAttrib(RDO);
        JBdhInfo.GetReportBdhAttrib(this,RDO);
        GetReportBdlAttrib(RDO);

        JBdhInfo.viewBdhs(this);

        GetReportUnitAttrib(RDO);
        GetReportJygsAttrib(RDO);
        GetReportZsgsAttrib(RDO);


        /**
         * 获取报表附注信息
         * add by gengeng 2008-8-28
         */
         GetReportComment(RDO);

        GetReportAddUnitAttrib(RDO);
        if(TZZD_BH !=null && TZZD_BH.trim().length()>0){
         GetReportAddAdjustUnitAttrib(RDO);
          //GetReportTzUnitAttrib(RDO);
        }
        GetReportAddJsgsAtt(RDO);
        GetReportAddJygsAtt(RDO);
        GetReportAddZsgsAtt(RDO);
        this.ChangeLog = 0; // 强制不修改

        /**
         * 清除修改标志
         * modified by hufeng 2008.3.20
         */
        ClearChangeLog();
    }

    /**
      * 获取报表调整公式信息
      *
      * @param RDO JReportDataObject
      */
     private void GetReportTzgsAttrib(JReportDataObject RDO) {
         Element tzgsesElement, tzgsElement;
         Element node;
         List nodelist;
         int i = 0;
         String sTmp1, sTmp2,sTmp3;
         JUnitInfo UI = null;
         tzgsesElement = RDO.getTzgsesElement();
         if (tzgsesElement == null) {
             return;
         }

         nodelist = RDO.BeginEnumerate(tzgsesElement);
         while (nodelist != null) {
             node = RDO.Enumerate(nodelist, i++);
             if (node == null) {
                 break;
             }
             tzgsElement = (Element) node;
             sTmp1 = tzgsElement.getAttribute(tzgsesElement.getAttribute("HZD_ORDE").getValue()).getValue();
             sTmp2 = tzgsElement.getAttribute(tzgsesElement.getAttribute("LZD_ORDE").getValue()).getValue();
             sTmp3 = tzgsElement.getAttribute(tzgsesElement.getAttribute("TZGS_GSX").getValue()).getValue();
             UI = JUnitInfo.GetUnitInfoByOrde(sTmp1, sTmp2, 1, this);
             if (UI != null) {
                 UI.ChangeLog = 0;
                 //如果有调整公式信息
                 if (sTmp3 != null && sTmp3.trim().length() != 0) {
                     JTzgsInfo tzgsInfo = new JTzgsInfo();
                     tzgsInfo.TZGS_HZD = Integer.valueOf(UI.RowInfo.HZD_ORDE);
                     tzgsInfo.TZGS_LZD = Integer.valueOf(UI.ColInfo.LZD_ORDE);
                     tzgsInfo.TZGS_GSX = sTmp3.trim();
                     UI.DYZD_TZGS= sTmp3.trim();

                     //如果
                     if((TZZD_BH != null && TZZD_BH.trim().length()!=0)&& (UI.DYZD_TZGS !=null&&UI.DYZD_TZGS.trim().length()!=0)){
                       if((TZZD_XS!= null && TZZD_XS.trim().length()!=0)&&
                          (TZZD_FH!= null && TZZD_FH.trim().length()!=0) &&
                          (TZZD_JD!= null && TZZD_JD.trim().length()!=0)){
                         // if(UI.DYZD_DATA!=null){
                         String gs = "=ROUND("+UI.DYZD_DATA+TZZD_FH+TZZD_XS+","+TZZD_JD+")";
//                         double num = 0.00;
//                         if("*".equals(TZZD_FH)){
//                           num = UI.DYZD_DATA * Double.valueOf(TZZD_XS);
//                         }else if("/".equals(TZZD_FH)){
//                           num = UI.DYZD_DATA / Double.valueOf(TZZD_XS);
//                         }
                      //    JBdhInfo.setAdjustCellContent(this,tzgsInfo.TZGS_HZD-1,tzgsInfo.TZGS_LZD-1,gs,UI.DYZD_DATA);
                         //}
  }
}


                 }
             }
         }
         RDO.EndEnumerate();
    }

    /**
       * 调整状态下调整系统报表中有调整公式的数据
       *
       * @param RDO JReportDataObject
       */
      private void GetReportTzgsAtt() {
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
          UI = RowInfo.UnitRowHead;
          while (UI != null) {
             UI.ChangeLog = 0;
            //如果有调整公式信息
            if (UI.DYZD_TZGS != null && UI.DYZD_TZGS.trim().length() != 0) {
                JTzgsInfo tzgsInfo = new JTzgsInfo();
                tzgsInfo.TZGS_HZD = Integer.valueOf(UI.RowInfo.HZD_ZB);
                tzgsInfo.TZGS_LZD = Integer.valueOf(UI.ColInfo.LZD_ZB);
                tzgsInfo.TZGS_GSX = UI.DYZD_TZGS.trim();//try

                //如果
                if((TZZD_BH != null && TZZD_BH.trim().length()!=0)&& (UI.DYZD_TZGS !=null&&UI.DYZD_TZGS.trim().length()!=0)){
                  if((TZZD_XS!= null && TZZD_XS.trim().length()!=0)&&
                     (TZZD_FH!= null && TZZD_FH.trim().length()!=0) &&
                     (TZZD_JD!= null && TZZD_JD.trim().length()!=0)){
                    // if(UI.DYZD_DATA!=null){
                    String gs = "=ROUND("+UI.DYZD_DATA+TZZD_FH+TZZD_XS+","+TZZD_JD+")";
//                     BigDecimal b1 = new BigDecimal(Double.toString(UI.DYZD_DEC));
//                     BigDecimal b2 = new BigDecimal(TZZD_XS);
//                     double dd = b1.divide(b2,Integer.valueOf(TZZD_JD),BigDecimal.ROUND_HALF_EVEN).doubleValue();
                   // double dd = java.math.BigDecimal.divide(UI.DYZD_DEC,Integer.valueOf(TZZD_XS),Integer.valueOf(TZZD_JD));

                     JBdhInfo.setAdjustCell(this,tzgsInfo.TZGS_HZD-1,tzgsInfo.TZGS_LZD-1,gs,UI.DYZD_DATA,UI);
                    UI.setModified();
                    UI.setTZModified();
                    //}
                  }
                }

          }
            UI = UI.RowNextUnit;
          }
          RowInfo = RowInfo.NextRow;
        }
    }
    /**
          * 调整状态下调整报表附加信息中有调整公式的数据
          *
          * @param RDO JReportDataObject
          */
         private void GetReportTzgsAddAtt() {
           JRowInfo RowInfo = HeadRowInfo.NextRow;
           JAddUnitInfo UI;
           JUnitInfo SUI;
           while (RowInfo != null) {
             UI = RowInfo.AddUnitRowHead;
            // SUI = RowInfo.UnitRowHead;
             while (UI != null) {
               UI.ChangeLog = 0;

              int  hzb = UI.RowInfo.HZD_ZB;
              int  lzb = UI.ColInfo.LZD_ZB;
              SUI = JUnitInfo.GetUnitInfoByHL(hzb,lzb,1,this);
              if(SUI!=null){
                //如果有调整公式信息
                if (SUI.DYZD_TZGS != null && SUI.DYZD_TZGS.trim().length() != 0) {
                  JTzgsInfo tzgsInfo = new JTzgsInfo();
                  tzgsInfo.TZGS_HZD = Integer.valueOf(UI.RowInfo.HZD_ZB);
                  tzgsInfo.TZGS_LZD = Integer.valueOf(UI.ColInfo.LZD_ZB);
                  tzgsInfo.TZGS_GSX = SUI.DYZD_TZGS.trim();//try

                  //如果
                  if((TZZD_BH != null && TZZD_BH.trim().length()!=0)&& (SUI.DYZD_TZGS !=null&&SUI.DYZD_TZGS.trim().length()!=0)){
                    if((TZZD_XS!= null && TZZD_XS.trim().length()!=0)&&
                       (TZZD_FH!= null && TZZD_FH.trim().length()!=0) &&
                       (TZZD_JD!= null && TZZD_JD.trim().length()!=0)){
                      // if(UI.DYZD_DATA!=null){
                      String gs = "=ROUND("+UI.DYZD_DATA+TZZD_FH+TZZD_XS+","+TZZD_JD+")";
//                     BigDecimal b1 = new BigDecimal(Double.toString(UI.DYZD_DEC));
//                     BigDecimal b2 = new BigDecimal(TZZD_XS);
//                     double dd = b1.divide(b2,Integer.valueOf(TZZD_JD),BigDecimal.ROUND_HALF_EVEN).doubleValue();
       // double dd = java.math.BigDecimal.divide(UI.DYZD_DEC,Integer.valueOf(TZZD_XS),Integer.valueOf(TZZD_JD));

       JBdhInfo.setAdjustCell(this,tzgsInfo.TZGS_HZD-1,tzgsInfo.TZGS_LZD-1,gs,UI.DYZD_DATA,UI);
       UI.setModified();
       UI.setTZModified();
        //}
      }
    }

}

              }


               UI = (JAddUnitInfo)UI.RowNextUnit;
           //    SUI = SUI.RowNextUnit;
             }
             RowInfo = RowInfo.NextRow;
           }
    }

    /**
        * 调整状态下调整报表附加信息中有调整公式的数据
        *
        * @param RDO JReportDataObject
        */
       private void GetReportBdhTzgsAddAtt() {
         if (BdhInfoList == null)return;
         JBdhInfo BdhInfo = null;
       // 创建变动数据元素
       int index;
       int Count = BdhInfoList.size();
       for (index = 0; index < Count; index++) {
           BdhInfo = (JBdhInfo) BdhInfoList.get(index);
           // 如果是新版变动区，则进行处理
           if (BdhInfo.BDH_ISNEW == 1) {
              // saveData(RM, RDO, Rows, BdhInfo, "TZSJ");
               // 如果没有数据则直接返回
               if (BdhInfo.RowInfo.getBdhDataList() == null || BdhInfo.RowInfo.getBdhDataList().size() == 0)return;
               int idx, count = BdhInfo.RowInfo.getBdhDataList().size();
               JBDHDataInfo BDHDataInfo = null;
               for (idx = 0; idx < count; idx++) {
                 BDHDataInfo = (JBDHDataInfo) BdhInfo.RowInfo.getBdhDataList().get(idx);
               //  if (BDInfo.ChangeLog == 0 && BDInfo.BD_OFFSET == (idx + 1))continue; // 如果没有修改过数据并且OFFSET没有进行调整,说明这一行没有修改过
                 JBDUnitInfo UI = null;String Tmp;String TextName,NumName;
                 String Tmp1,Tmp2;
                 int hzb,lzb;
                 JUnitInfo SUI = null;
                 String DYZD_SJ;double DYZD_DATA;
                 for(int Col=1;Col<=BBZD_LS;Col++) {
                   UI = BDHDataInfo.getUnitInfo(Col-1);
                   hzb = BDHDataInfo.BdhInfo.RowInfo.HZD_ZB;
                   lzb = Col;
                   SUI = JUnitInfo.GetUnitInfoByHL(hzb,lzb,1,this);
                   if(SUI.DYZD_TZGS !=null&& SUI.DYZD_TZGS.trim().length()>0){
//                     if ( Col < 10 ) Tmp = "0"+String.valueOf(Col);else Tmp = String.valueOf(Col);
//                     TextName = "T"+Tmp;NumName = "N"+Tmp;
                     if ( UI == null ) {
                       DYZD_SJ = "";DYZD_DATA=0.00;
                     } else {
                       DYZD_SJ = UI.TZZD_SJ;
                       double dd = 0.00;
                       BigDecimal b1 = new BigDecimal(Double.toString(UI.DYZD_DATA));
                       BigDecimal b2 = new BigDecimal(TZZD_XS);
                       if(TZZD_FH.equals("*")){
                         dd = b1.multiply(b2).doubleValue();
                       }else if(TZZD_FH.equals("/")){
                         dd = b1.divide(b2,Integer.valueOf(TZZD_JD),BigDecimal.ROUND_HALF_EVEN).doubleValue();
                       }
                       UI.TZZD_SJ = Double.toString(dd);
                       UI.TZZD_DATA = dd;
                     //  JBdhInfo.setAdjustCell(this,hzb-1,lzb-1,"",UI.DYZD_DATA,UI);
                       /**
                        * 因为BDSJ表中的DYZD_DATA列是numeric(19,6)的，小数点最长为６位
                        * 所以在保存前要将小数点四舍五入到第６位
                        * modified by hufeng 2006.1.20
                        */
                      // DYZD_DATA=NumberFunction.round(UI.TZZD_DATA,6);
                     }
                     UI.setModified();
                     UI.setTZModified();
                   }
//                   else{
//                     if ( Col < 10 ) Tmp = "0"+String.valueOf(Col);else Tmp = String.valueOf(Col);
//                     TextName = "T"+Tmp;NumName = "N"+Tmp;
//                     DYZD_SJ = "";DYZD_DATA=0.00;
//
//                   }

                 }

               }
               JBdhInfo.viewNewData(this, BdhInfo);
             }
           }

  }


    /**
       * 调整状态下调整报表附加信息中有调整公式的数据
       *
       * @param RDO JReportDataObject
       */
      private void GetReportTzgsTzAtt() {
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JTzUnitInfo UI;
        JUnitInfo SUI;
        while (RowInfo != null) {
          UI = RowInfo.TzUnitRowHead;
          SUI = RowInfo.UnitRowHead;
          while (UI != null&& SUI!=null) {
             UI.ChangeLog = 0;
            //如果有调整公式信息
            if (SUI.DYZD_TZGS != null && SUI.DYZD_TZGS.trim().length() != 0) {
                JTzgsInfo tzgsInfo = new JTzgsInfo();
                tzgsInfo.TZGS_HZD = Integer.valueOf(UI.RowInfo.HZD_ZB);
                tzgsInfo.TZGS_LZD = Integer.valueOf(UI.ColInfo.LZD_ZB);
                tzgsInfo.TZGS_GSX = UI.DYZD_TZGS.trim();//try

                //如果
                if((TZZD_BH != null && TZZD_BH.trim().length()!=0)&& (SUI.DYZD_TZGS !=null&&SUI.DYZD_TZGS.trim().length()!=0)){
                  if((TZZD_XS!= null && TZZD_XS.trim().length()!=0)&&
                     (TZZD_FH!= null && TZZD_FH.trim().length()!=0) &&
                     (TZZD_JD!= null && TZZD_JD.trim().length()!=0)){
                    // if(UI.DYZD_DATA!=null){
                    String gs = "=ROUND("+UI.DYZD_DATA+TZZD_FH+TZZD_XS+","+TZZD_JD+")";


                     JBdhInfo.setAdjustCell(this,tzgsInfo.TZGS_HZD-1,tzgsInfo.TZGS_LZD-1,gs,UI.DYZD_DATA,UI);
                    UI.setModified();
                    UI.setTZModified();
                    //}
                  }
                }

          }
            UI = (JTzUnitInfo)UI.RowNextUnit;
            SUI = SUI.RowNextUnit;
          }
          RowInfo = RowInfo.NextRow;
        }
 }

    /**
     * 获取报表附注信息
     *
     * @param RDO JReportDataObject
     */
    private void GetReportComment(JReportDataObject RDO) {
        Element commentsElement, commentElement;
        Element node;
        List nodelist;
        int i = 0;
        String sTmp1, sTmp2, sTmp3, sTmp4;
        JUnitInfo UI = null;
        commentsElement = RDO.getCommentsElement();
        if (commentsElement == null) {
            return;
        }
        CommentMaxOrde = Integer.valueOf(commentsElement.getAttribute("MaxOrde").getValue()).intValue();
        nodelist = RDO.BeginEnumerate(commentsElement);
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            commentElement = (Element) node;
            sTmp1 = commentElement.getAttribute(commentsElement.getAttribute("HZD_ORDE").getValue()).getValue();
            sTmp2 = commentElement.getAttribute(commentsElement.getAttribute("LZD_ORDE").getValue()).getValue();

            UI = JUnitInfo.GetUnitInfoByOrde(sTmp1, sTmp2, 1, this);
            if (UI != null) {
                UI.ChangeLog = 0;
                sTmp1 = RDO.GetElementValue(commentElement, commentsElement.getAttribute("BBFZ_USER").getValue());
                sTmp2 = RDO.GetElementValue(commentElement, commentsElement.getAttribute("BBFZ_DATE").getValue());
                sTmp3 = RDO.GetElementValue(commentElement, commentsElement.getAttribute("BBFZ_MESS").getValue());
                //如果有附注信息
                if (sTmp3 != null && sTmp3.trim().length() != 0) {
                    if (UI.CommentList == null) {
                        UI.CommentList = new Vector();
                    }
                    JCommentInfo commentInfo = new JCommentInfo();
                    commentInfo.HZD_ORDE = UI.RowInfo.HZD_ORDE;
                    commentInfo.LZD_ORDE = UI.ColInfo.LZD_ORDE;
                    commentInfo.Comment_User = sTmp1.trim();
                    commentInfo.Comment_Date = sTmp2.trim();
                    commentInfo.Comment_Info = sTmp3.trim();
                    commentInfo.Comment_HOFFSET = Integer.valueOf(commentElement.getAttribute(commentsElement.getAttribute("DYZD_HOFFSET").getValue()).getValue()).intValue();
                    commentInfo.Comment_LOFFSET = Integer.valueOf(commentElement.getAttribute(commentsElement.getAttribute("DYZD_LOFFSET").getValue()).getValue()).intValue();
                    commentInfo.ADD_TYPE = Integer.valueOf(commentElement.getAttribute(commentsElement.getAttribute("ADD_TYPE").getValue()).getValue()).intValue();
                    commentInfo.ADD_BH = commentElement.getAttribute(commentsElement.getAttribute("ADD_BH").getValue()).getValue();
                    commentInfo.Comment_Orde = commentElement.getAttribute(commentsElement.getAttribute("BBFZ_ORDE").getValue()).getValue();

                    UI.CommentList.add(commentInfo);
                }
            }
        }
        RDO.EndEnumerate();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void GetReportAttrib(JReportDataObject RDO) {
        Element TE = RDO.getTableElement();
        if (TE == null) {
            return;
        }
        BBZD_BH = TE.getAttribute("BBZD_BH").getValue();
        BBZD_DATE = TE.getAttribute("BBZD_DATE").getValue();
        BBZD_FCBZ = TE.getAttribute("BBZD_FCBZ").getValue();
        BBZD_LX = TE.getAttribute("BBZD_LX").getValue();
        BBZD_MC = TE.getAttribute("BBZD_MC").getValue();
        BBZD_NB = TE.getAttribute("BBZD_NB").getValue();
        BBZD_SBBH = TE.getAttribute("BBZD_SBBH").getValue();
        BBZD_SBXZ = TE.getAttribute("BBZD_SBXZ").getValue();
        BBZD_XF = TE.getAttribute("BBZD_XF").getValue();
        ADD_FCBZ = TE.getAttribute("BBZD_ADDFCBZ").getValue();
        BBZD_ALLFCBZ= TE.getAttribute("BBZD_ALLFCBZ").getValue();
        BBZD_DWWCBZ= TE.getAttribute("BBZD_DWWCBZ").getValue();
        BBZD_USE = TE.getAttribute("BBZD_USE").getValue(); //取报表封存标志
        if(BBZD_USE == null || BBZD_USE.trim().equals("")){
            BBZD_USE = "0";
        }
        // 取报表复核审核标志
        BBSH_SHBZ1 = TE.getAttribute("BBSH_SHBZ1").getValue();
        BBSH_SHBZ2 = TE.getAttribute("BBSH_SHBZ2").getValue();
        try {
            Attribute at = TE.getAttribute("ADD_BH");
            if (at != null) {
                ADD_BH = at.getValue();
                if (ADD_BH != null && ADD_BH.trim().length() == 0) {
                    ADD_BH = null;
                }
                ADD_MC = TE.getAttribute("ADD_MC").getValue();
                ADD_TYPE = TE.getAttribute("ADD_TYPE").getIntValue();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        TZZD_ORDE = TE.getAttribute("TZZD_ORDE").getValue();
        TZZD_MC = TE.getAttribute("TZZD_MC").getValue();//LK 调整字典名称
        BBZD_TITLE = Integer.valueOf(TE.getAttribute("BBZD_TITLE").getValue()).
            intValue();
        BBZD_HEAD = Integer.valueOf(TE.getAttribute("BBZD_HEAD").getValue()).
            intValue();
        BBZD_BT = Integer.valueOf(TE.getAttribute("BBZD_BT").getValue()).intValue();
        BBZD_HS = Integer.valueOf(TE.getAttribute("BBZD_HS").getValue()).intValue();
        BBZD_LS = Integer.valueOf(TE.getAttribute("BBZD_LS").getValue()).intValue();
        BBZD_FIXROW = Integer.valueOf(TE.getAttribute("BBZD_FIXROW").getValue()).
            intValue();
        BBZD_FIXRN = Integer.valueOf(TE.getAttribute("BBZD_FIXRN").getValue()).
            intValue();
        BBZD_FIXCOL = Integer.valueOf(TE.getAttribute("BBZD_FIXCOL").getValue()).
            intValue();
        BBZD_FIXCN = Integer.valueOf(TE.getAttribute("BBZD_FIXCN").getValue()).
            intValue();
        BBZD_SPLIT = Integer.valueOf(TE.getAttribute("BBZD_SPLIT").getValue()).
            intValue();
        BBZD_NTZK = TE.getAttribute("BBZD_NTZK").getValue();
        BBZD_MODIFYDATE = TE.getAttribute("BBZD_MODIFYDATE").getValue();
        BBZD_ZSHL = TE.getAttribute("BBZD_ZSHL").getValue();
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void GetReportRowAttrib(JReportDataObject RDO) {
        Element RowElement;
        List nodelist;
        Element node;
        int i = 0;
        JRowInfo RowInfo = null, HRI = null;
        Element Row;
        int MaxID = 0, ID = 0;
        RowElement = RDO.getRowsElement();
        if (RowElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(RowElement);
        HRI = HeadRowInfo;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            Row = (Element) node;
            RowInfo = new JRowInfo();
            RowInfo.NextRow = HRI.NextRow;
            HRI.NextRow = RowInfo;
            RowInfo.PrioRow = HRI;
            HRI = RowInfo;

            RowInfo.HZD_ORDE = Row.getAttribute(RowElement.getAttribute("HZD_ORDE").
                                                getValue()).getValue();
            try{
              RowInfo.HZD_PRO = Row.getAttribute(RowElement.getAttribute("HZD_PRO").
                                                 getValue()).getIntValue();
            }catch(Exception e){}
            ID = Integer.valueOf(RowInfo.HZD_ORDE).intValue();
            if (ID > MaxID) {
                MaxID = ID;
            }
            RowInfo.HZD_ZB = Integer.valueOf(Row.getAttribute(RowElement.getAttribute(
                "HZD_ZB").getValue()).getValue()).intValue();
            RowInfo.BZBM_BM = Row.getAttribute(RowElement.getAttribute("BZBM_BM").
                                               getValue()).getValue();
            if (RowInfo.BZBM_BM != null && RowInfo.BZBM_BM.trim().length() == 0) {
                RowInfo.BZBM_BM = "";
            }
        }
        RowMaxOrde = ++MaxID;
        RDO.EndEnumerate();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void GetReportColAttrib(JReportDataObject RDO) {
        //<Cols BZBM_BM="c" LZD_HZ="f" LZD_MC="d" LZD_ORDE="a" LZD_SZ="e" LZD_TZ="g" LZD_XM="h" LZD_ZB="b">
        Element ColElement;
        List nodelist;
        Element node;
        int i = 0;
        JColInfo ColInfo = null, HCI = null;
        Element Col;
        String Tmp;
        int MaxID = 0, ID = 0;
        ColElement = RDO.getColsElement();
        if (ColElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(ColElement);
        HCI = HeadColInfo;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            Col = (Element) node;
            ColInfo = new JColInfo();
            ColInfo.RM = this;
            ColInfo.NextCol = HCI.NextCol;
            HCI.NextCol = ColInfo;
            ColInfo.PrioCol = HCI;
            HCI = ColInfo;

            ColInfo.LZD_ORDE = Col.getAttribute(ColElement.getAttribute("LZD_ORDE").
                                                getValue()).getValue();
            ColInfo.LZD_ZB = Integer.valueOf(Col.getAttribute(ColElement.getAttribute(
                "LZD_ZB").getValue()).getValue()).intValue();
            ColInfo.BZBM_BM = Col.getAttribute(ColElement.getAttribute("BZBM_BM").
                                               getValue()).getValue();
            if (ColInfo.BZBM_BM != null && ColInfo.BZBM_BM.trim().length() == 0) {
                ColInfo.BZBM_BM = "";
            }
            ColInfo.LZD_MC = Col.getAttribute(ColElement.getAttribute("LZD_MC").
                                              getValue()).getValue();
            if (ColInfo.LZD_MC != null && ColInfo.LZD_MC.trim().length() == 0) {
                ColInfo.LZD_MC = "";
            }
            Tmp = Col.getAttribute(ColElement.getAttribute("LZD_SZ").getValue()).
                getValue();
            if (Tmp.equals("0") == true) {
                ColInfo.LZD_SZ = false;
            }
            Tmp = Col.getAttribute(ColElement.getAttribute("LZD_HZ").getValue()).
                getValue();
            if (Tmp.equals("0") == true) {
                ColInfo.LZD_HZ = false;
            }
            Tmp = Col.getAttribute(ColElement.getAttribute("LZD_TZ").getValue()).
                getValue();
            if (Tmp.equals("0") == true) {
                ColInfo.LZD_TZ = false;
            }
            Tmp = Col.getAttribute(ColElement.getAttribute("LZD_XM").getValue()).
                getValue();
            if (Tmp.equals("1") == true) {
                ColInfo.LZD_XM = true;
            }
            Tmp = Col.getAttribute(ColElement.getAttribute("LZD_PX").getValue()).
                getValue();
            if (Tmp.equals("1") == true) {
                ColInfo.LZD_PX = true;
            }
            ID = Integer.valueOf(ColInfo.LZD_ORDE).intValue();
            if (ID > MaxID) {
                MaxID = ID;
            }
        }
        ColMaxOrde = ++MaxID;
        RDO.EndEnumerate();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void GetReportBdlAttrib(JReportDataObject RDO) {
        Element BdlsElement;
        List nodelist;
        Element node;
        int i = 0;
        JBdlInfo BdlInfo = null, HRI = null;
        Element Bdl;
        int MaxID = 0, ID = 0;
        BdlsElement = RDO.getBdlsElement();
        if (BdlsElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(BdlsElement);
//    HRI = HeadBdlInfo;
        String Name, BMLB;
        int Col;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            Bdl = (Element) node;
            Name = Bdl.getAttribute(BdlsElement.getAttribute("BDL_MC").getValue()).
                getValue();
            BMLB = Bdl.getAttribute(BdlsElement.getAttribute("BZBM_BM").getValue()).
                getValue();
            Col = JColInfo.GetColInfoByOrde(Bdl.getAttribute(BdlsElement.getAttribute(
                "BDL_KSORDE").getValue()).getValue(), this).LZD_ZB;
            int num = 0;

            try {
                num = Bdl.getAttribute(BdlsElement.getAttribute("BDL_NUM").getValue()).
                    getIntValue();
            }
            catch (Exception e) {}
            if (num > 1) { //C/S的变动格式
                int lzb;
                try {
                    lzb = Bdl.getAttribute(BdlsElement.getAttribute("BDL_MAX").getValue()).
                        getIntValue();
                }
                catch (Exception e) {
                    lzb = 1;
                }
                BdlInfo = this.setBDL(Name, Col , Col + num , lzb , BMLB);
            }
            else {
                BdlInfo = this.setBDL(Name, Col, Col + num, BMLB);

            }
            BdlInfo.ChangeLog = 0;
            BdlInfo.BDL_ORDE = Bdl.getAttribute(BdlsElement.getAttribute("BDL_ORDE").
                                                getValue()).getValue();
            BdlInfo.BDL_NUM = Integer.valueOf(Bdl.getAttribute(BdlsElement.
                getAttribute("BDL_NUM").getValue()).getValue()).intValue();
            ID = Integer.valueOf(BdlInfo.BDL_ORDE).intValue();
            if (ID > MaxID) {
                MaxID = ID;
            }
        }
        BdlMaxOrde = ++MaxID;
        RDO.EndEnumerate();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void GetReportUnitAttrib(JReportDataObject RDO) {
        JRowInfo RI;
        JColInfo CI;
        Element UnitsElement, UnitElement;
        Element node;
        List nodelist;
        int i = 0;
        String sTmp;
        Object obj;
        JUnitInfo UI = null; //,LUI=null;
        int MaxID = 0, ID = 0;
        UnitsElement = RDO.getUnitsElement();
        if (UnitsElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(UnitsElement);
//    LUI = HeadUnitInfo;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            UnitElement = (Element) node;
            sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("HZD_ORDE").
                                            getValue()).getValue();
            RI = JRowInfo.GetRowInfoByOrde(sTmp.trim(), this);
            sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("LZD_ORDE").
                                            getValue()).getValue();
            CI = JColInfo.GetColInfoByOrde(sTmp.trim(), this);
            if (RI != null && CI != null) {
                UI = JUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
                UI.ChangeLog = 0;
//          UI = new JUnitInfo();
//          if ( UI.NextUnit != null ) UI.NextUnit.PrioUnit = UI;
//          UI.NextUnit = LUI.NextUnit;LUI.NextUnit = UI;UI.PrioUnit = LUI;
//          LUI = UI;
                // 处理行单元
                /*
                          UI.RowNextUnit = RI.UnitRowHead;
                 if ( RI.UnitRowHead != null ) RI.UnitRowHead.RowPrioUnit = UI;
                          RI.UnitRowHead = UI;
                          // 处理列单元
                          UI.ColNextUnit = CI.UnitColHead;
                 if ( CI.UnitColHead != null ) CI.UnitColHead.ColPrioUnit = UI;
                          CI.UnitColHead = UI;
                          //
                          UI.RowInfo = RI;UI.ColInfo = CI;
                 */

//         if ( ID > MaxID ) MaxID = ID;

                UI.DYZD_COMBO = RDO.GetElementValue(UnitElement,
                                                 UnitsElement.getAttribute("DYZD_COMBO").
                                                 getValue());
                if (UI.DYZD_COMBO != null && UI.DYZD_COMBO.trim().length() == 0) {
                    UI.DYZD_COMBO = null;
                }
                if ( UI.DYZD_COMBO != null ) {
                    int RowStatus = JBdhUtils.checkPhyRowStatus(this,RI.HZD_ZB);
                    if ( RowStatus == 0 ) {
                        UI.DYZD_MJZD = true;
                    }
                }


                UI.DYZD_SJ = RDO.GetElementValue(UnitElement,
                                                 UnitsElement.getAttribute("DYZD_SJ").
                                                 getValue());
                if (UI.DYZD_SJ != null && UI.DYZD_SJ.trim().length() == 0) {
                    UI.DYZD_SJ = null;
                }
                sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("DYZD_DATA").
                                                getValue()).getValue();
                if (UI.DYZD_SJ == null) {
                    UI.DYZD_SJ = sTmp;
                }
                if (sTmp != null && sTmp.trim().length() != 0) {
                    UI.DYZD_DATA = Double.
                        valueOf(sTmp).doubleValue();
                }
                JBdhInfo.setLogCellContent(this,RI.HZD_ZB-1,CI.LZD_ZB-1,UI.DYZD_SJ,UI.DYZD_DATA);
//                try {
//                    try {
//                        Double.valueOf(UI.DYZD_SJ);
//                        ReportView.setNumber(RI.HZD_ZB - 1, CI.LZD_ZB - 1, UI.DYZD_DATA);
//                    }
//                    catch (Exception e) {
//                        this.ReportView.setText(RI.HZD_ZB - 1, CI.LZD_ZB - 1, UI.DYZD_SJ);
//                    }
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
                UI.DYZD_GSX = RDO.GetElementValue(UnitElement,
                                                  UnitsElement.getAttribute("DYZD_GSX").
                                                  getValue());
                sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("DYZD_LX").
                                                getValue()).getValue();
                if (sTmp.trim().equals("C")) {
                    UI.DYZD_LX = 1;
                }
                sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("DYZD_DEC").
                                                getValue()).getValue();
                if (sTmp != null && sTmp.trim().length() != 0) {
                    UI.DYZD_DEC = Integer.
                        valueOf(sTmp.trim()).intValue();

                }
                UI.DYZD_HOFFSET = Integer.valueOf(UnitElement.getAttribute(UnitsElement.
                    getAttribute("DYZD_HOFFSET").getValue()).getValue()).intValue();
                UI.DYZD_LOFFSET = Integer.valueOf(UnitElement.getAttribute(UnitsElement.
                    getAttribute("DYZD_LOFFSET").getValue()).getValue()).intValue();

                UI.DYZD_GSBZ = UnitElement.getAttribute(UnitsElement.getAttribute(
                    "DYZD_GSBZ").getValue()).getValue();
                if (UI.DYZD_GSBZ != null && UI.DYZD_GSBZ.trim().length() == 0) {
                    UI.
                        DYZD_GSBZ = null;

                }
                sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("DYZD_GSJB").
                                                getValue()).getValue();
                if (sTmp != null && sTmp.trim().length() != 0) {
                    UI.DYZD_GSJB = Integer.
                        valueOf(sTmp.trim()).intValue();

                }
                RI.BZBM_BM = UnitElement.getAttribute(UnitsElement.getAttribute(
                    "H_BZBM").getValue()).getValue();
                CI.BZBM_BM = UnitElement.getAttribute(UnitsElement.getAttribute(
                    "L_BZBM").getValue()).getValue();
                sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("DYZD_SFBH").
                                                getValue()).getValue();
                if (sTmp != null && sTmp.trim().equals("1")) {
                    UI.DYZD_SFBH = true;
                }
                sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("DYZD_SFTZ").
                                                getValue()).getValue();
                if (sTmp != null && sTmp.trim().equals("0")) {
                    UI.DYZD_SFTZ = false;

                }
                obj = UnitsElement.getAttribute("DYZD_ZBXX");
                if ( obj!=null ) {
                	sTmp = RDO.GetElementValue(UnitElement,UnitsElement.getAttribute("DYZD_ZBXX").getValue());
                	if ( sTmp!=null) {
                		UI.DYZD_ZBXX = sTmp;	
                	} else {
                		UI.DYZD_ZBXX = "";	
                	}
                }
                if ( UI.DYZD_ZBXX != null ) {
                	if ( !UI.DYZD_ZBXX.trim().equals("")) {
                		try {
                			ReportView.setColor(RI.HZD_ZB-1, CI.LZD_ZB-1, RI.HZD_ZB-1, CI.LZD_ZB-1, java.awt.Color.green);
                		}catch (Exception ex){}
                	}
                }
                //如果
                if((TZZD_BH != null && TZZD_BH.trim().length()!=0)&& (UI.DYZD_TZGS !=null&&UI.DYZD_TZGS.trim().length()!=0)){
                  if(TZZD_XS!= null && TZZD_JD.trim().length()!=0){

                  }
                }


            }
        }
//    UI.MaxOrde = MaxID;
        RDO.EndEnumerate();
    }

    private void GetReportAddJsgsAtt(JReportDataObject RDO) {
        JRowInfo RI;
        JColInfo CI;
        Element UnitsElement, UnitElement;
        Element node;
        List nodelist;
        int i = 0;
        String sTmp;
        JUnitInfo UI = null; //,LUI=null;
        int MaxID = 0, ID = 0;
        UnitsElement = RDO.getAddjsgsesElement();
        if (UnitsElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(UnitsElement);
//    LUI = HeadUnitInfo;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            UnitElement = (Element) node;
            sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("HZD_ORDE").
                                            getValue()).getValue();
            RI = JRowInfo.GetRowInfoByOrde(sTmp.trim(), this);
            sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("LZD_ORDE").
                                            getValue()).getValue();
            CI = JColInfo.GetColInfoByOrde(sTmp.trim(), this);
            if (RI != null && CI != null) {
              /**
               * 改为先检查后创建
               * modified by hufeng 2005.11.24
               */
              UI = JUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
              UI.ChangeLog = 0;
//                UI = new JUnitInfo();
//                // 处理行单元
//                UI.RowNextUnit = RI.UnitRowHead;
//                if (RI.UnitRowHead != null) {
//                    RI.UnitRowHead.RowPrioUnit = UI;
//                }
//                RI.UnitRowHead = UI;
//                // 处理列单元
//                UI.ColNextUnit = CI.UnitColHead;
//                if (CI.UnitColHead != null) {
//                    CI.UnitColHead.ColPrioUnit = UI;
//                }
//                CI.UnitColHead = UI;
//                //
//                UI.RowInfo = RI;
//                UI.ColInfo = CI;

                UI.DYZD_GSX = RDO.GetElementValue(UnitElement,
                                                  UnitsElement.getAttribute("DYZD_GSX").
                                                  getValue());
                UI.setJSGS_TYPE(this);
                UI.DYZD_HOFFSET = Integer.valueOf(UnitElement.getAttribute(UnitsElement.
                    getAttribute("DYZD_HOFFSET").getValue()).getValue()).intValue();
                UI.DYZD_LOFFSET = Integer.valueOf(UnitElement.getAttribute(UnitsElement.
                    getAttribute("DYZD_LOFFSET").getValue()).getValue()).intValue();

                sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("DYZD_GSJB").
                                                getValue()).getValue();
                if (sTmp != null && sTmp.trim().length() != 0) {
                    UI.DYZD_GSJB = Integer.
                        valueOf(sTmp.trim()).intValue();
                }

            }
        }
//    UI.MaxOrde = MaxID;
        RDO.EndEnumerate();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void GetReportZsgsAttrib(JReportDataObject RDO) {
        JRowInfo RI;
        JColInfo CI;
        Element UnitsElement, UnitElement;
        Element node;
        List nodelist;
        int i = 0;
        String sTmp;
        JUnitInfo UI = null; //,LUI=null;
        int MaxID = 0, ID = 0;
        UnitsElement = RDO.getZsgsesElement();
        if (UnitsElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(UnitsElement);
//    LUI = HeadUnitInfo;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            UnitElement = (Element) node;
            sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("HZD_ORDE").
                                            getValue()).getValue();
            RI = JRowInfo.GetRowInfoByOrde(sTmp.trim(), this);
            sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("LZD_ORDE").
                                            getValue()).getValue();
            CI = JColInfo.GetColInfoByOrde(sTmp.trim(), this);
            if (RI != null && CI != null) {
                UI = JUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
                UI.ChangeLog = 0;
                UI.DYZD_ZSGS = RDO.GetElementValue(UnitElement,
                                                  UnitsElement.getAttribute("DYZD_ZSGS").
                                                  getValue());
                UI.DYZD_ZSHOFFSET = Integer.valueOf(UnitElement.getAttribute(UnitsElement.
                    getAttribute("DYZD_ZSHOFFSET").getValue()).getValue()).intValue();
                UI.DYZD_ZSLOFFSET = Integer.valueOf(UnitElement.getAttribute(UnitsElement.
                    getAttribute("DYZD_ZSLOFFSET").getValue()).getValue()).intValue();
            }
        }
        RDO.EndEnumerate();
    }
    
    private void GetReportAddZsgsAtt(JReportDataObject RDO) {
        JRowInfo RI;
        JColInfo CI;
        Element UnitsElement, UnitElement;
        Element node;
        List nodelist;
        int i = 0;
        String sTmp;
        JUnitInfo UI = null; //,LUI=null;
        int MaxID = 0, ID = 0;
        UnitsElement = RDO.getAddzsgsesElement();
        if (UnitsElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(UnitsElement);
//    LUI = HeadUnitInfo;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            UnitElement = (Element) node;
            sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("HZD_ORDE").
                                            getValue()).getValue();
            RI = JRowInfo.GetRowInfoByOrde(sTmp.trim(), this);
            sTmp = UnitElement.getAttribute(UnitsElement.getAttribute("LZD_ORDE").
                                            getValue()).getValue();
            CI = JColInfo.GetColInfoByOrde(sTmp.trim(), this);
            if (RI != null && CI != null) {
              UI = JUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
              UI.ChangeLog = 0;

              UI.DYZD_ZSGS = RDO.GetElementValue(UnitElement,
                                                  UnitsElement.getAttribute("DYZD_ZSGS").
                                                  getValue());
              UI.setJSGS_TYPE(this);
              UI.DYZD_ZSHOFFSET = Integer.valueOf(UnitElement.getAttribute(UnitsElement.
                    getAttribute("DYZD_ZSHOFFSET").getValue()).getValue()).intValue();
              UI.DYZD_ZSLOFFSET = Integer.valueOf(UnitElement.getAttribute(UnitsElement.
                    getAttribute("DYZD_ZSLOFFSET").getValue()).getValue()).intValue();

            }
        }
//    UI.MaxOrde = MaxID;
        RDO.EndEnumerate();
    }
    
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //-----------------------------------------------------------------------------------------------
    private void GetReportJygsAttrib(JReportDataObject RDO) {
        JRowInfo RI;
        JColInfo CI;
        Element JygsesElement, JygseElement;
        Element node;
        List nodelist;
        int i = 0;
        String sTmp1, sTmp2;
        JUnitInfo UI = null;
        JJygsInfo JygsInfo;
        int MaxID = 0, ID = 0;
        JygsesElement = RDO.getJygsesElement();
        if (JygsesElement == null) {
            return;
        }
        JygsMaxOrde = Integer.valueOf(JygsesElement.getAttribute("MaxOrde").
                                      getValue()).intValue();
        nodelist = RDO.BeginEnumerate(JygsesElement);
//    LUI = HeadUnitInfo;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            JygseElement = (Element) node;
            sTmp1 = JygseElement.getAttribute(JygsesElement.getAttribute("HZD_ORDE").
                                              getValue()).getValue();
//        RI   = JRowInfo.GetRowInfoByOrde(sTmp.trim(),this);
            sTmp2 = JygseElement.getAttribute(JygsesElement.getAttribute("LZD_ORDE").
                                              getValue()).getValue();
//        CI   = JColInfo.GetColInfoByOrde(sTmp.trim(),this);
            UI = JUnitInfo.GetUnitInfoByOrde(sTmp1, sTmp2, 1, this);
            /**
             * 测试输出
             */
//            if(UI == null){
//              System.out.println("HZD_ORDE:" + sTmp1);
//              System.out.println("LZD_ORDE:" + sTmp2);
//            }

            /**
             * 将修改标志改为零
             * modified by hufeng 2005.11.9
             */
            if (UI != null) {
                UI.ChangeLog = 0;
                sTmp1 = RDO.GetElementValue(JygseElement,
                                            JygsesElement.getAttribute("JYGS_EXP").
                                            getValue());
                if (sTmp1 != null && sTmp1.trim().length() != 0) {
                    if (UI.JygsList == null) {
                        UI.JygsList = new Vector();
                    }
                    JygsInfo = new JJygsInfo();
                    JygsInfo.JYGS_FROM  = JygsInfo.FROM_XTBB; //系统报表公式
                    JygsInfo.JYGS_GSX = sTmp1.trim();
                    sTmp1 = RDO.GetElementValue(JygseElement,JygsesElement.getAttribute("JYGS_SHOW").getValue());
                    JygsInfo.JYGS_SHOW=sTmp1;
                    JygsInfo.JYGS_ORDE = JygseElement.getAttribute(JygsesElement.
                        getAttribute("JYGS_ORDE").getValue()).getValue();
                    JygsInfo.JYGS_BJ = RDO.GetElementValue(JygseElement,
                        JygsesElement.
                        getAttribute("JYGS_BJ").
                        getValue());
                   sTmp1=RDO.GetElementValue(JygseElement,JygsesElement.getAttribute("JYGS_TYPE").getValue());
                   JygsInfo.JYGS_TYPE = Integer.parseInt(sTmp1);
                   sTmp1= RDO.GetElementValue(JygseElement,JygsesElement.getAttribute("JYGS_STYLE").getValue());
                   JygsInfo.JYGS_STYLE =Integer.parseInt(sTmp1);
                   JygsInfo.JYGS_SYFW = JygseElement.getAttribute(JygsesElement.
                           getAttribute("JYGS_SYFW").getValue()).getValue();
                    JygsInfo.JYGS_HOFFSET = Integer.valueOf(JygseElement.getAttribute(
                        JygsesElement.getAttribute("DYZD_HOFFSET").getValue()).getValue()).
                        intValue();
                    JygsInfo.JYGS_LOFFSET = Integer.valueOf(JygseElement.getAttribute(
                        JygsesElement.getAttribute("DYZD_LOFFSET").getValue()).getValue()).
                        intValue();
                    UI.JygsList.add(JygsInfo);
//            ID = Integer.valueOf(JygsInfo.JYGS_ORDE).intValue();
//            if ( ID > MaxID ) MaxID = ID;
                }
            }
        }
//    JygsMaxOrde = MaxID;
        RDO.EndEnumerate();
    }

    private void GetReportAddJygsAtt(JReportDataObject RDO) {
        JRowInfo RI;
        JColInfo CI;
        Element JygsesElement, JygseElement;
        Element node;
        List nodelist;
        int i = 0;
        String sTmp1, sTmp2;
        JUnitInfo UI = null;
        JJygsInfo JygsInfo;
        int MaxID = 0, ID = 0;
        JygsesElement = RDO.getAddjygsesElement();
        if (JygsesElement == null) {
            return;
        }
        int MaxOrde = Integer.valueOf(JygsesElement.getAttribute("MaxOrde").
                                      getValue()).intValue();
        if (JygsMaxOrde < MaxOrde) {
            JygsMaxOrde = MaxOrde;
        }
        nodelist = RDO.BeginEnumerate(JygsesElement);
//    LUI = HeadUnitInfo;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            JygseElement = (Element) node;
            sTmp1 = JygseElement.getAttribute(JygsesElement.getAttribute("HZD_ORDE").
                                              getValue()).getValue();
            sTmp2 = JygseElement.getAttribute(JygsesElement.getAttribute("LZD_ORDE").
                                              getValue()).getValue();
            UI = JUnitInfo.GetUnitInfoByOrde(sTmp1, sTmp2, 1, this);
            if (UI != null) {
                sTmp1 = RDO.GetElementValue(JygseElement,
                                            JygsesElement.getAttribute("JYGS_EXP").
                                            getValue());
                if (sTmp1 != null && sTmp1.trim().length() != 0) {
                    if (UI.JygsList == null) {
                        UI.JygsList = new Vector();
                    }
                    JygsInfo = new JJygsInfo();
                    JygsInfo.JYGS_FROM  = JygsInfo.FROM_DWBB; //单位报表公式
                    JygsInfo.JYGS_GSX = sTmp1.trim();
                    sTmp1 = RDO.GetElementValue(JygseElement,
                                            JygsesElement.getAttribute("JYGS_SHOW").
                                            getValue());
                    JygsInfo.JYGS_SHOW=sTmp1;
                    JygsInfo.JYGS_ORDE = JygseElement.getAttribute(JygsesElement.
                        getAttribute("JYGS_ORDE").getValue()).getValue();
                    JygsInfo.JYGS_BJ = RDO.GetElementValue(JygseElement,JygsesElement.getAttribute("JYGS_BJ").getValue());
                   // JygsInfo.JYGS_MESS = RDO.GetElementValue(JygseElement,JygsesElement.getAttribute("JYGS_BJ").getValue());
                   // JygsInfo.JYGS_SHJG = RDO.GetElementValue(JygseElement,JygsesElement.getAttribute("JYGS_BJ").getValue());
                    sTmp1=RDO.GetElementValue(JygseElement,JygsesElement.getAttribute("JYGS_TYPE").getValue());
                    JygsInfo.JYGS_TYPE = Integer.parseInt(sTmp1);
                    sTmp1= RDO.GetElementValue(JygseElement,JygsesElement.getAttribute("JYGS_STYLE").getValue());
                    JygsInfo.JYGS_STYLE =Integer.parseInt(sTmp1);
                    JygsInfo.JYGS_SYFW = JygseElement.getAttribute(JygsesElement.
                            getAttribute("JYGS_SYFW").getValue()).getValue();
                    JygsInfo.JYGS_HOFFSET = Integer.valueOf(JygseElement.getAttribute(
                        JygsesElement.getAttribute("DYZD_HOFFSET").getValue()).getValue()).
                        intValue();
                    JygsInfo.JYGS_LOFFSET = Integer.valueOf(JygseElement.getAttribute(
                        JygsesElement.getAttribute("DYZD_LOFFSET").getValue()).getValue()).
                        intValue();
                    UI.JygsList.add(JygsInfo);
                    UI.setJYGS_TYPE(this);
//            ID = Integer.valueOf(JygsInfo.JYGS_ORDE).intValue();
//            if ( ID > MaxID ) MaxID = ID;
                }
            }
        }
//    JygsMaxOrde = MaxID;
        RDO.EndEnumerate();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void GetReportAddUnitAttrib(JReportDataObject RDO) {
        JRowInfo RI;
        JColInfo CI;
        Element AddUnitsElement, AddUnitElement;
        Element node;
        List nodelist;
        int i = 0;
        String sTmp;
        Object obj;
        JAddUnitInfo UI = null; //,LUI=null;
        int MaxID = 0, ID = 0;
        AddUnitsElement = RDO.getAddUnitsElement();
        if (AddUnitsElement == null) {
            return;
        }
        nodelist = RDO.BeginEnumerate(AddUnitsElement);
//    LUI = HeadUnitInfo;
        while (nodelist != null) {
            node = RDO.Enumerate(nodelist, i++);
            if (node == null) {
                break;
            }
            AddUnitElement = (Element) node;
            sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                "HZD_ORDE").getValue()).getValue();
            RI = JRowInfo.GetRowInfoByOrde(sTmp.trim(), this);
            sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                "LZD_ORDE").getValue()).getValue();
            CI = JColInfo.GetColInfoByOrde(sTmp.trim(), this);
            if (RI != null && CI != null) {
              /**
               * 改为先检查后创建
               * modified by hufeng 2005.11.24
               */
              UI = (JAddUnitInfo)JAddUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
              UI.ChangeLog = 0;
//                UI = new JAddUnitInfo();
//                // 处理行单元
//                UI.RowNextUnit = RI.AddUnitRowHead;
//                if (RI.AddUnitRowHead != null) {
//                    RI.AddUnitRowHead.RowPrioUnit = UI;
//                }
//                RI.AddUnitRowHead = UI;
//                // 处理列单元
//                UI.ColNextUnit = CI.AddUnitColHead;
//                if (CI.AddUnitColHead != null) {
//                    CI.AddUnitColHead.ColPrioUnit = UI;
//                }
//                CI.AddUnitColHead = UI;
//                // 处理行\列
//                UI.RowInfo = RI;
//                UI.ColInfo = CI;
                UI.DYZD_SJ = RDO.GetElementValue(AddUnitElement,
                                                 AddUnitsElement.getAttribute("DYZD_SJ").
                                                 getValue());
                if (UI.DYZD_SJ != null && UI.DYZD_SJ.trim().length() == 0) {
                    UI.DYZD_SJ = null;
                }
                sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                    "DYZD_DATA").getValue()).getValue();
                if (UI.DYZD_SJ == null) {
                    UI.DYZD_SJ = sTmp;
                }
                if (sTmp != null && sTmp.trim().length() != 0) {
                    UI.DYZD_DATA = Double.valueOf(sTmp).doubleValue();
                }
                obj = AddUnitsElement.getAttribute("DYZD_ZBXX");
                if ( obj!=null ) {
                	sTmp = RDO.GetElementValue(AddUnitElement,AddUnitsElement.getAttribute("DYZD_ZBXX").getValue());
                	if ( sTmp!=null) {
                		UI.DYZD_ZBXX = sTmp;	
                	} else {
                		UI.DYZD_ZBXX = "";	
                	}
                }
                
                JBdhInfo.setLogCellContent(this,RI.HZD_ZB-1,CI.LZD_ZB-1,UI.DYZD_SJ,UI.DYZD_DATA);
                //如果调整编号不为空，检索调整附加字典（单位，部门，类别）将数据赋值
                if(TZZD_BH !=null&& TZZD_BH.trim().length()>0){
                //           GetReportAddAdjustUnitAttrib(RDO,UI,RI,CI);
                }

                RI.BZBM_BM = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                    "H_BZBM").getValue()).getValue();
                CI.BZBM_BM = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                    "L_BZBM").getValue()).getValue();

            }
        }
//    UI.MaxOrde = MaxID;
        RDO.EndEnumerate();
    }

    //------------------------------------------------------------------------------------------------
       //描述:
       //设计: Skyline(2001.12.29)
       //实现: Skyline
       //修改:
       //------------------------------------------------------------------------------------------------
       private void GetReportTzUnitAttrib(JReportDataObject RDO) {
           JRowInfo RI;
           JColInfo CI;
           Element AddUnitsElement, AddUnitElement;
           Element node;
           List nodelist;
           int i = 0;
           String sTmp;
           JTzUnitInfo UI = null; //,LUI=null;
           int MaxID = 0, ID = 0;
           AddUnitsElement = RDO.getAddAdjustUnitsElement();
           if (AddUnitsElement == null) {
               return;
           }
           nodelist = RDO.BeginEnumerate(AddUnitsElement);
//    LUI = HeadUnitInfo;
           while (nodelist != null) {
               node = RDO.Enumerate(nodelist, i++);
               if (node == null) {
                   break;
               }
               AddUnitElement = (Element) node;
               sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                   "HZD_ORDE").getValue()).getValue();
               RI = JRowInfo.GetRowInfoByOrde(sTmp.trim(), this);
               sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                   "LZD_ORDE").getValue()).getValue();
               CI = JColInfo.GetColInfoByOrde(sTmp.trim(), this);
               if (RI != null && CI != null) {
                 /**
                  * 改为先检查后创建
                  * modified by hufeng 2005.11.24
                  */
                 UI = (JTzUnitInfo)JTzUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
                 UI.ChangeLog = 0;

                   UI.DYZD_SJ = RDO.GetElementValue(AddUnitElement,
                                                    AddUnitsElement.getAttribute("DYZD_SJ").
                                                    getValue());
                   if (UI.DYZD_SJ != null && UI.DYZD_SJ.trim().length() == 0) {
                       UI.DYZD_SJ = null;
                   }
                   sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                       "DYZD_DATA").getValue()).getValue();
                   if (UI.DYZD_SJ == null) {
                       UI.DYZD_SJ = sTmp;
                   }
                   if (sTmp != null && sTmp.trim().length() != 0) {
                       UI.DYZD_DATA = Double.valueOf(sTmp).doubleValue();
                   }
                   JBdhInfo.setLogCellContent(this,RI.HZD_ZB-1,CI.LZD_ZB-1,UI.DYZD_SJ,UI.DYZD_DATA);
                   //如果调整编号不为空，检索调整附加字典（单位，部门，类别）将数据赋值

                   RI.BZBM_BM = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                       "H_BZBM").getValue()).getValue();
                   CI.BZBM_BM = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                       "L_BZBM").getValue()).getValue();

               }
           }
//    UI.MaxOrde = MaxID;
           RDO.EndEnumerate();
       }

    //------------------------------------------------------------------------------------------------
      //描述:
      //设计: Skyline(2001.12.29)
      //实现: Skyline
      //修改:
      //------------------------------------------------------------------------------------------------
      private void GetReportAddAdjustUnitAttrib(JReportDataObject RDO) {
        JRowInfo RI;
        JColInfo CI;
        Element AddUnitsElement, AddUnitElement;
        Element node;
        List nodelist;
        List aUIList = new ArrayList();
        int i = 0;
        String sTmp;
        JAddUnitInfo UI = null,AUI=null;
          JUnitInfo SUI = null;
          int MaxID = 0, ID = 0;

          AddUnitsElement = RDO.getAddAdjustUnitsElement();
          if (AddUnitsElement == null) {
              return;
          }
          nodelist = RDO.BeginEnumerate(AddUnitsElement);
          while (nodelist != null) {
              node = RDO.Enumerate(nodelist, i++);
              if (node == null) {
                  break;
              }
              AddUnitElement = (Element) node;
              sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                  "HZD_ORDE").getValue()).getValue();
              RI = JRowInfo.GetRowInfoByOrde(sTmp.trim(), this);
              sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                  "LZD_ORDE").getValue()).getValue();
              CI = JColInfo.GetColInfoByOrde(sTmp.trim(), this);
              if (RI != null && CI != null) {
                /**
                 * 改为先检查后创建
                 * modified by hufeng 2005.11.24
                 */
                UI = (JAddUnitInfo)JAddUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
                SUI = JUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
//                JRowInfo RowInfo = HeadRowInfo.NextRow;
//                while (RowInfo != null) {
//                  AUI = RowInfo.AddUnitRowHead;
//                  while (AUI != null) {
//                    if(AUI.RowInfo.HZD_ORDE.equals(UI.RowInfo.HZD_ORDE)&&AUI.ColInfo.LZD_ORDE.equals(UI.ColInfo.LZD_ORDE)){
//                      UI= AUI;
//                      break;
//                    }
//                    AUI = (JAddUnitInfo)AUI.RowNextUnit;
//                  }
//                  RowInfo = RowInfo.NextRow;
//                }

//                if(aUIList.size()>0){
//                  for(int i=0;i<aUIList.size();i++){
//                  }
//                }
                UI.ChangeLog = 0;
                if(SUI!=null){
                  if(SUI.DYZD_TZGS!=null&&SUI.DYZD_TZGS.trim().length()>0){
                    UI.TZZD_SJ = RDO.GetElementValue(AddUnitElement,
                        AddUnitsElement.getAttribute("DYZD_SJ").
                        getValue());
                    if (UI.TZZD_SJ != null && UI.TZZD_SJ.trim().length() == 0) {
                      UI.TZZD_SJ = null;
                    }
                    sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                        "DYZD_DATA").getValue()).getValue();
                    if (UI.TZZD_SJ == null) {
                      UI.TZZD_SJ = sTmp;
                    }
                    if (sTmp != null && sTmp.trim().length() != 0) {
                      UI.TZZD_DATA = Double.valueOf(sTmp).doubleValue();
                    }
                    UI.DYZD_TZGS= SUI.DYZD_TZGS;
                    JBdhInfo.setLogCellContent(this,RI.HZD_ZB-1,CI.LZD_ZB-1,UI.TZZD_SJ,UI.TZZD_DATA);
                    RI.BZBM_BM = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                        "H_BZBM").getValue()).getValue();
                    CI.BZBM_BM = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                        "L_BZBM").getValue()).getValue();

                  }

                }



              }
          }
//    UI.MaxOrde = MaxID;
          RDO.EndEnumerate();
    }


    //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private void GetReportAddAdjustUnitAttrib(JReportDataObject RDO,JAddUnitInfo UI,JRowInfo RI, JColInfo CI) {

      Element AddUnitsElement, AddUnitElement;
      Element node;
      List nodelist;
      int i = 0;
      String sTmp,sTmp1,sTmp2;
    //  JUnitInfo SUI = null;
      AddUnitsElement = RDO.getAddAdjustUnitsElement();
      if (AddUnitsElement == null) {
          return;
      }
      nodelist = RDO.BeginEnumerate(AddUnitsElement);
//    LUI = HeadUnitInfo;
      while (nodelist != null) {
          node = RDO.Enumerate(nodelist, i++);
          if (node == null) {
              break;
          }
          AddUnitElement = (Element) node;
          sTmp1 = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
              "HZD_ORDE").getValue()).getValue();
//          RI = JRowInfo.GetRowInfoByOrde(sTmp.trim(), this);
          sTmp2 = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
              "LZD_ORDE").getValue()).getValue();
//          CI = JColInfo.GetColInfoByOrde(sTmp.trim(), this);
          if (RI != null && CI != null) {
            if(RI.HZD_ORDE.equals(sTmp1)&& CI.LZD_ORDE.equals(sTmp2)){
              /**
               * 改为先检查后创建
               * modified by hufeng 2005.11.24
               */
              // UI = (JAddUnitInfo)JAddUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
              //SUI = JUnitInfo.GetUnitInfoByHL(RI.HZD_ZB, CI.LZD_ZB, 1, this);
              // UI =HeadRowInfo.AddUnitRowHead;
              //SUI = HeadRowInfo.UnitRowHead;
              UI.ChangeLog = 0;
            //  if(SUI.DYZD_TZGS!=null&&SUI.DYZD_TZGS.trim().length()>0){
                UI.TZZD_SJ = RDO.GetElementValue(AddUnitElement,
                                   AddUnitsElement.getAttribute("DYZD_SJ").
                                   getValue());
                if (UI.TZZD_SJ != null && UI.TZZD_SJ.trim().length() == 0) {
                  UI.TZZD_SJ = null;
                }
                sTmp = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                    "DYZD_DATA").getValue()).getValue();
                if (UI.TZZD_SJ == null) {
                  UI.TZZD_SJ = sTmp;
                }
                if (sTmp != null && sTmp.trim().length() != 0) {
                  UI.TZZD_DATA = Double.valueOf(sTmp).doubleValue();
                }
               // UI.DYZD_TZGS= SUI.DYZD_TZGS;
                JBdhInfo.setLogCellContent(this,RI.HZD_ZB-1,CI.LZD_ZB-1,UI.TZZD_SJ,UI.TZZD_DATA);
                RI.BZBM_BM = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                    "H_BZBM").getValue()).getValue();
                CI.BZBM_BM = AddUnitElement.getAttribute(AddUnitsElement.getAttribute(
                    "L_BZBM").getValue()).getValue();

             // }

            }
          }
      }
      RDO.EndEnumerate();
}



    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public boolean SaveReportObject() {
		boolean res = false;
		try {
			ReportView.setSheet(ReportView.getNumSheets() - 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setDataStatus(this.MODEL_STATUS_DATA);
		if (ADD_BH != null && ADD_BH.trim().length() == 0) {
			ADD_BH = null;

		}
		if (TZZD_BH != null && TZZD_BH.trim().length() == 0) {
			TZZD_BH = null;
		}

		/**
		 * 重新修改状态标志
		 */
		ROE.resetModifiedStatus();

		if (TZZD_BH == null && IS_ZBTB == 0 ) {
			// 首先保存数据
			SaveReportDataObject();
			// 是否要进行格式保存
			if (ADD_BH == null && !IS_GFBB.equals("1") ) {
				if (ChangeLog != 0) {
					SaveReportFormatObject();
				}
			} else {
				ROE.FormatObject = null;
			}
			/**
			 * 保存修改日志 addd by hufeng 2006.8.17
			 */
			// writeLog();

			// 保存成功
			if (SaveToDB() == 0) {
				res = true;
			}
			try {
				// 如果格式进行了保存，需要再次显示变动行 add xSkyline 2004.11.27
				if (ChangeLog != 0 && ADD_BH == null && !IS_GFBB.equals("1")) {
					JBdhInfo.viewBdhs(this);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (TZZD_BH != null ) {
			// 只保存调整数据
			SaveReportAdjustDataObject();
			/**
			 * 保存修改日志 addd by hufeng 2006.8.17
			 */
//			writeLog();
			// 保存成功
				if (SaveToDB() == 0) {
					res = true;
				}
			}
			if ( IS_ZBTB == 1) {
				SaveZbtbxx();
			}
		}
		if (res) {
			// 清除修改标志
			ClearChangeLog();
		}
		return res;
      }

      /**
       * 保存修改日志
       */
      private void writeLog(){
        String mess = "";
        String sjbh = "";
      String gnbh = "";

      switch (this.ADD_TYPE) {
        case JReportObjectStub.STUB_TYPE_REPORT:
          mess = res.getString("String_182");
          sjbh = ";;;";
          break;
        case JReportObjectStub.STUB_TYPE_DWZD:
          mess = res.getString("String_184");
          sjbh = ADD_BH + ";;;";
          break;
        case JReportObjectStub.STUB_TYPE_LBZD:
          mess = res.getString("String_186");
          sjbh = ";" + ADD_BH + ";;";
          break;
        case JReportObjectStub.STUB_TYPE_BMZD:
          mess = res.getString("String_201");
          sjbh = ";;" + ADD_BH + ";";
          break;
        case JReportObjectStub.STUB_TYPE_CBZD:
          mess = res.getString("String_192");
          sjbh = ";;;" + ADD_BH ;
          break;
        default:
          return;
      }
      sjbh += ";" + BBZD_BH + ";" + BBZD_DATE;
      //格式计算公式
      if(ROE.mIsModifiedJsgs){
        JLogManager.writeDataLog(res.getString("String_208"),sjbh);
      }
      //格式校验公式
      if(ROE.mIsModifiedJygs){
        JLogManager.writeDataLog(res.getString("String_209"),sjbh);
      }
      //格式调整公式
      if(ROE.mIsModifiedTzgs){
        JLogManager.writeDataLog(res.getString("String_207"),sjbh);
	  }

      //单位计算公式
      if(ROE.mIsModifiedAddJsgs){
        gnbh = res.getString("String_210") + mess + res.getString("String_211");
        JLogManager.writeDataLog(gnbh,sjbh);
      }
      //格式校验公式
      if(ROE.mIsModifiedAddJygs){
        gnbh = res.getString("String_212") + mess + res.getString("String_213");
        JLogManager.writeDataLog(gnbh,sjbh);
      }
      //格式调整公式
      if(ROE.mIsModifiedAddTzgs){
        gnbh = res.getString("String_217") + mess + res.getString("String_218");
        JLogManager.writeDataLog(gnbh,sjbh);
      }

      if(ADD_BH == null){
        JLogManager.writeDataLog(res.getString("String_214"),sjbh);
      }
      else{
        gnbh = res.getString("String_215") + mess + res.getString("String_216");
        JLogManager.writeDataLog(gnbh,sjbh);
      }
    }

//add by fsz 2005.5.11
    public boolean SaveReportObjectAs(String bh, String mc, String date) {
        BBZD_BH = bh;
        BBZD_MC = mc;
        BBZD_DATE = date;
        BBZD_FCBZ = "0";
        ADD_BH = null;
        setChangLog(1);
        return SaveReportObject();
    }

    //end
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private int SaveToDB() {
        // 调用对象
        JResponseObject RO = null;
        JParamObject PO = JParamObject.Create();
        PO.SetValueByParamName("BBZD_BH", BBZD_BH);
        PO.SetValueByParamName("BBZD_PTLB", BBZD_PTLB);//LQK
        PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
        PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
        PO.SetValueByParamName("ADD_BH", ADD_BH);
        PO.SetValueByParamName("TZZD_BH", TZZD_BH);
        PO.SetValueByParamName("IS_GFBB", IS_GFBB);//lK
        if(TZZD_BH !=null && TZZD_BH.trim().length()>0){
          try {
			RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "SaveReportAdjustObject", PO, ROE);
		} catch (Exception e) {
			e.printStackTrace();
		}
        }else{
          try {
			RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "SaveReportObject", PO, ROE);
		} catch (Exception e) {
			e.printStackTrace();
		}
        }
        if (RO.GetErrorCode() != 0) {
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, RO.ErrorString, res.getString("String_224"), JOptionPane.ERROR_MESSAGE);
        }
        else {
            String Res = (String) RO.ResponseObject;

        }
        return RO.GetErrorCode();
    }
    //end
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
    private int SaveReportFormatObject() {
        ZipEntry ze;
        ZipOutputStream zos;
        ByteArrayOutputStream os;
        JParamObject PO;
        // add by xSkyline 保存格式之前，首先将新版变动行中的行删掉
        try {
          JBdhInfo.deleBdhs(this);
        } catch ( Exception e ) {
          e.printStackTrace();
        }
        try {
          int rowCount = getReportMaxRow();
          ReportView.setMaxRow(rowCount-1);
          ReportView.setMaxCol(BBZD_LS-1);
          // $_LCBBGS_$.VTS

          ze = new ZipEntry("$_LCBBGS_$.VTS");
          os = new ByteArrayOutputStream();
    //      ze  = new ZipEntry("DataObject");os  = new ByteArrayOutputStream();
          zos = new ZipOutputStream(os);
          zos.putNextEntry(ze);
          ReportView.saveViewInfo();
//          ReportView.write(zos, new com.f1j.ss.WriteParams(ReportView.eFileFormulaOne6));
          ReportView.write(zos, new com.f1j.ss.WriteParams(ReportView.eFileExcel97));
          zos.flush();
          zos.finish();
          ROE.FormatObject = os.toByteArray();

          /**
           * 写入文件
           */
   /*
          java.io.File file = new java.io.File("c:\\aa.txt");
          FileOutputStream FOS = new FileOutputStream(file);
          ReportView.saveViewInfo();
          ReportView.write(FOS,new com.f1j.ss.WriteParams(ReportView.eFileExcel97));
          FOS.close();
    */
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 取报表的最大行数，包含变动行
     * @return int
     */
    public int getReportMaxRow(){
      int i,bdCount = 0,count = 0;
      JBdhInfo bdInfo;
      ArrayList list;
      for(i=0; i<BdhInfoList.size(); i++){
        bdInfo = (JBdhInfo)BdhInfoList.get(i);
        list = bdInfo.RowInfo.getBdhDataList();
        if(list != null){
          bdCount += list.size();
        }
      }
      count = this.BBZD_HS + bdCount;
      return count;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private int SaveReportDataObject() {
        JReportDataObject RDO;
        Element ReportElement;
        RDO = new JReportDataObject();
        ReportElement = RDO.AddChildElement(null, "Report");

        // 如果是系统报表,格式需要保存
        if (ADD_BH == null) {
        	if ( !IS_GFBB.equals("1")) { //如果是定义取股份报表公式，就只存公式，别的不让动，动了也不存
	            SaveReportAttrib(RDO, ReportElement);
	            SaveReportDelHZDAttrib(RDO, ReportElement);
	            SaveReportHZDAttrib(RDO, ReportElement);
	            SaveReportDelLZDAttrib(RDO, ReportElement);
	            SaveReportLZDAttrib(RDO, ReportElement);
	            SaveReportDelBDHAttrib(RDO, ReportElement);
	            SaveReportBDHAttrib(RDO, ReportElement);
	            SaveReportDelBDLAttrib(RDO, ReportElement);
	            SaveReportBDLAttrib(RDO, ReportElement);
	            SaveReportDYZDAttrib(RDO, ReportElement);
	            SaveReportJYGSAttrib(RDO, ReportElement);
	            SaveReportTZGSAttrib(RDO, ReportElement);
	            SaveReportZSGSAttrib(RDO, ReportElement);
	            // 处理新版变动区数据
	            JBdhUtils.SaveReportDelBDSJ(this,RDO, ReportElement);
	            JBdhUtils.SaveReportBDSJ(this,RDO, ReportElement);
        	} else {
	            SaveReportZHGSAttrib(RDO, ReportElement);
	            JBdhUtils.saveBDJS(this,RDO, ReportElement);
        	}
        }
        else {
            // 保存附加数据(可能是汇总数据或是单位数据和责任中心数据)
        		SaveReportAddDYZDAttrib(RDO, ReportElement);
        		SaveReportAddJSGSAttrib(RDO, ReportElement);
        		SaveReportAddJYGSAttrib(RDO, ReportElement);
        		SaveReportAddZSGSAttrib(RDO, ReportElement);
            // 处理新版变动区数据
        		JBdhUtils.SaveReportDelBDSJ(this,RDO, ReportElement);
        		JBdhUtils.SaveReportBDSJ(this,RDO, ReportElement);

        }
        if ( !IS_GFBB.equals("1")) {
        // 无论什么类型的表都需要保存附注信息
        	SaveReportCommentInfo(RDO, ReportElement);
        }

//        RDO.SaveToFile("D:\\savereport.xml");
        ROE.DataObject = RDO.GetRootXMLString();
//    System.out.println(ROE.DataObject);
        return 0;
    }


    //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  private int SaveReportAdjustDataObject() {
      JReportDataObject RDO;
      Element ReportElement;
      RDO = new JReportDataObject();
      ReportElement = RDO.AddChildElement(null, "Report");

          // 保存附加调整数据(可能是汇总数据或是单位数据和责任中心数据)
          SaveReportAddTZZDAttrib(RDO, ReportElement);
          //SaveReportAddJSGSAttrib(RDO, ReportElement);
          //SaveReportAddJYGSAttrib(RDO, ReportElement);
          // 处理新版变动区数据
          JBdhUtils.SaveReportDelBDSJ(this,RDO, ReportElement);
          JBdhUtils.SaveReportBDTZSJ(this,RDO, ReportElement);


      // 无论什么类型的表都需要保存附注信息
    //  SaveReportCommentInfo(RDO, ReportElement);

//        RDO.SaveToFile("D:\\savereport.xml");
      ROE.DataObject = RDO.GetRootXMLString();
//    System.out.println(ROE.DataObject);
      return 0;
  }


    /**
     * 保存报表附注信息
     * add by gengeng 2008-9-4
     *
     * @param RDO JReportDataObject
     * @param ReportElement Element
     */
    private void SaveReportCommentInfo(JReportDataObject RDO, Element ReportElement) {
        JParamObject PO = JParamObject.Create();
        Element CommentsElement = RDO.AddChildElement(ReportElement, "Comments");
        Element commentElement;
        CommentsElement.setAttribute("BBFZ_ORDE",    "a");
        CommentsElement.setAttribute("DYZD_HOFFSET", "b");
        CommentsElement.setAttribute("DYZD_LOFFSET", "c");
        CommentsElement.setAttribute("BBFZ_USER",    "d");
        CommentsElement.setAttribute("BBFZ_DATE",    "e");
        CommentsElement.setAttribute("BBFZ_INFO",    "f");
        CommentsElement.setAttribute("ADD_TYPE",     "g");
        CommentsElement.setAttribute("ADD_BH",       "h");
        CommentsElement.setAttribute("HZD_ORDE",     "i");
        CommentsElement.setAttribute("LZD_ORDE",     "j");
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                if (UI.ChangeLog != 0) {
                    JCommentInfo commentInfo;
                    if (UI.CommentList != null) {
                        /**
                         * 如果剩余附注的个数是0，也保存一下
                         */
                        if (UI.CommentList.size() == 0) {
                            commentElement = RDO.AddChildElement(CommentsElement, "C");
                            commentElement.setAttribute("g", String.valueOf(ADD_TYPE));
                            commentElement.setAttribute("h", ADD_BH == null ? "" : ADD_BH.trim());
                            commentElement.setAttribute("i", UI.RowInfo.HZD_ORDE);
                            commentElement.setAttribute("j", UI.ColInfo.LZD_ORDE);
                        }
                        for (int i = 0; i < UI.CommentList.size(); i++) {
                            commentInfo = (JCommentInfo) UI.CommentList.get(i);
                            if (commentInfo.Comment_Info != null && commentInfo.Comment_Info.trim().length() != 0) {
                                commentElement = RDO.AddChildElement(CommentsElement, "C");
                                commentElement.setAttribute("a", commentInfo.Comment_Orde);
                                commentElement.setAttribute("b", String.valueOf(commentInfo.Comment_HOFFSET));
                                commentElement.setAttribute("c", String.valueOf(commentInfo.Comment_LOFFSET));
                                commentElement.setAttribute("d", PO.GetValueByEnvName("UserCaption", ""));
                                commentElement.setAttribute("e", commentInfo.Comment_Date);
                                commentElement.setAttribute("f", commentInfo.Comment_Info);
                                commentElement.setAttribute("g", String.valueOf(ADD_TYPE));
                                commentElement.setAttribute("h", ADD_BH == null?"":ADD_BH);
                                commentElement.setAttribute("i", UI.RowInfo.HZD_ORDE);
                                commentElement.setAttribute("j", UI.ColInfo.LZD_ORDE);
                            }
                        }
                    }
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }

    //add by fsz 2004.5.11
    protected void setChangLog(int Log) {
        SetChangeLogReport(Log);
        SetChangeLogHZD(Log);
        SetChangeLogLZD(Log);
        SetChangeLogBDH(Log);
        SetChangeLogBDL(Log);
        SetChangeLogDYZD(Log);
        SetChangeLogJYGS(Log);
    }

    private void SetChangeLogReport(int Log) {
        ChangeLog = Log;
    }

    private void SetChangeLogHZD(int Log) {
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        while (RowInfo != null) {
            // 如果等于0说明没有修改过,不需要保存
            RowInfo.ChangeLog = Log;
            RowInfo = RowInfo.NextRow;
        }
    }

    private void SetChangeLogLZD(int Log) {
        JColInfo ColInfo = HeadColInfo.NextCol;
        while (ColInfo != null) {
            ColInfo.ChangeLog = Log;
            ColInfo = ColInfo.NextCol;
        }
    }

    private void SetChangeLogBDH(int Log) {
        JBdhInfo BdhInfo = null;
        for (int i = 0; i < BdhInfoList.size(); i++) {
            BdhInfo = (JBdhInfo) BdhInfoList.get(i);
            // 如果等于0说明没有修改过,不需要保存
            BdhInfo.ChangeLog = Log;
        }
    }

    private void SetChangeLogBDL(int Log) {
        JBdlInfo BdlInfo = null;
        for (int i = 0; i < BdlInfoList.size(); i++) {
            BdlInfo = (JBdlInfo) BdlInfoList.get(i);
            BdlInfo.ChangeLog = Log;

        }

    }

    private void SetChangeLogDYZD(int Log) {
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                if (UI.ChangeLog != Log) {
                    UI.ChangeLog = Log;
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }

    private void SetChangeLogJYGS(int Log) {

    }

    //end
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    protected void ClearChangeLog() {
        if (ADD_BH == null) {
            ClearChangeLogReport();
            ClearChangeLogHZD();
            ClearChangeLogLZD();
            ClearChangeLogBDH();
            ClearChangeLogBDL();
            ClearChangeLogDYZD();
            ClearChangeLogJYGS();
            ClearChangeLogZSGS();
            // 清除删除列表
            ClearDelList();
        }
        else {
            // 保存附加数据(可能是汇总数据或是单位数据和责任中心数据)
            ClearChangeLogReport();
            ClearChangeLogDYZD();
            ClearChangeLogAddDYZD();
            ClearChangeLogAddJSGS();
            ClearChangeLogAddJYGS();
            ClearChangeLogAddZSGS();
        }
        // 清除变动区域中的修改状态
        JBdhUtils.ClearChangeLog(this);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void setModified() {
        // 2是修改,1是新建
        if (ChangeLog == 0) {
            ChangeLog = 2;
        }
    }

	public void setModified(boolean isModified) {
		ChangeLog = 0;
	}
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int getModified() {
        // 2是修改,1是新建d
        return ChangeLog;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    protected void ClearChangeLogReport() {
        ChangeLog = 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogHZD() {
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        while (RowInfo != null) {
            // 如果等于0说明没有修改过,不需要保存
            if (RowInfo.ChangeLog != 0) {
                RowInfo.ChangeLog = 0;
            }
            RowInfo = RowInfo.NextRow;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogLZD() {
        JColInfo ColInfo = HeadColInfo.NextCol;
        while (ColInfo != null) {
            if (ColInfo.ChangeLog != 0) {
                ColInfo.ChangeLog = 0;
            }
            ColInfo = ColInfo.NextCol;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogBDH() {
        JBdhInfo BdhInfo = null;
        for (int i = 0; i < BdhInfoList.size(); i++) {
            BdhInfo = (JBdhInfo) BdhInfoList.get(i);
            // 如果等于0说明没有修改过,不需要保存
            if (BdhInfo.ChangeLog != 0) {
                BdhInfo.ChangeLog = 0;
            }
        }
//    JBdhInfo BdhInfo = HeadBdhInfo.Next;
//    while ( BdhInfo != null ) {
//      // 如果等于0说明没有修改过,不需要保存
//      if ( BdhInfo.ChangeLog != 0 ) {
//        BdhInfo.ChangeLog = 0;
//      }
//      BdhInfo = BdhInfo.Next;
//    }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogBDL() {
        JBdlInfo BdlInfo = null;
        for (int i = 0; i < BdlInfoList.size(); i++) {
            BdlInfo = (JBdlInfo) BdlInfoList.get(i);
            // 如果等于0说明没有修改过,不需要保存
            if (BdlInfo.ChangeLog != 0) {
                BdlInfo.ChangeLog = 0;
            }
        }
//    JBdlInfo BdlInfo = HeadBdlInfo.Next;
//    while ( BdlInfo != null ) {
//      // 如果等于0说明没有修改过,不需要保存
//      if ( BdlInfo.ChangeLog != 0 ) {
//        BdlInfo.ChangeLog = 0;
//      }
//      BdlInfo = BdlInfo.Next;
//    }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogDYZD() {
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                if (UI.ChangeLog != 0) {
                    UI.ChangeLog = 0;
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogZSGS() {
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                if (UI.ChangeLog != 0) {
                    UI.ChangeLog = 0;
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogJYGS() {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogAddDYZD() {
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.AddUnitRowHead;
            while (UI != null) {
                if (UI.ChangeLog != 0) {
                    UI.ChangeLog = 0;
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogAddJSGS() {
      JRowInfo RowInfo = HeadRowInfo.NextRow;
      JUnitInfo UI;
      while (RowInfo != null) {
          UI = RowInfo.UnitRowHead;
          while (UI != null) {
              if (UI.ChangeLogJs != 0) {
                  UI.ChangeLogJs = 0;
              }
              UI = UI.RowNextUnit;
          }
          RowInfo = RowInfo.NextRow;
      }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogAddZSGS() {
      JRowInfo RowInfo = HeadRowInfo.NextRow;
      JUnitInfo UI;
      while (RowInfo != null) {
          UI = RowInfo.UnitRowHead;
          while (UI != null) {
              if (UI.ChangeLogZs != 0) {
                  UI.ChangeLogZs = 0;
              }
              UI = UI.RowNextUnit;
          }
          RowInfo = RowInfo.NextRow;
      }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearChangeLogAddJYGS() {
      JRowInfo RowInfo = HeadRowInfo.NextRow;
      JUnitInfo UI;
      while (RowInfo != null) {
          UI = RowInfo.UnitRowHead;
          while (UI != null) {
              if (UI.ChangeLogJy != 0) {
                  UI.ChangeLogJy = 0;
              }
              UI = UI.RowNextUnit;
          }
          RowInfo = RowInfo.NextRow;
      }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ClearDelList() {
        DelHeadRowInfo = new JRowInfo();
        DelHeadColInfo = new JColInfo();

        DelBdhInfoList = new Vector();
        DelBdlInfoList = new Vector();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportAttrib(JReportDataObject DataObject, Element ReportElement) {
        // 如果没有修改过
        if (ChangeLog == 0) {
            return;
        }
        Element TE = DataObject.AddChildElement(ReportElement, "Table");
        TE.setAttribute("BBZD_BH", BBZD_BH);
        TE.setAttribute("BBZD_DATE", BBZD_DATE);
        TE.setAttribute("BBZD_FCBZ", BBZD_FCBZ);
        TE.setAttribute("BBZD_LX", BBZD_LX);
        TE.setAttribute("BBZD_MC", BBZD_MC);
        TE.setAttribute("BBZD_NB", BBZD_NB);
        TE.setAttribute("BBZD_SBBH", BBZD_SBBH);
        TE.setAttribute("BBZD_SBXZ", BBZD_SBXZ);
        TE.setAttribute("BBZD_XF", BBZD_XF);
        TE.setAttribute("ADD_TYPE", String.valueOf(ADD_TYPE));
        if (ADD_BH != null) {
            TE.setAttribute("ADD_BH", ADD_BH);

        }

        TE.setAttribute("TZZD_ORDE", TZZD_ORDE);
        TE.setAttribute("TZZD_MC", TZZD_MC);//LK 调整字典名称
        TE.setAttribute("BBZD_TITLE", String.valueOf(BBZD_TITLE));
        TE.setAttribute("BBZD_HEAD", String.valueOf(BBZD_HEAD));
        TE.setAttribute("BBZD_BT", String.valueOf(BBZD_BT));
        TE.setAttribute("BBZD_HS", String.valueOf(BBZD_HS));
        TE.setAttribute("BBZD_LS", String.valueOf(BBZD_LS));
        TE.setAttribute("BBZD_FIXROW", String.valueOf(BBZD_FIXROW));
        TE.setAttribute("BBZD_FIXRN", String.valueOf(BBZD_FIXRN));
        TE.setAttribute("BBZD_FIXCOL", String.valueOf(BBZD_FIXCOL));
        TE.setAttribute("BBZD_FIXCN", String.valueOf(BBZD_FIXCN));
        TE.setAttribute("BBZD_SPLIT", String.valueOf(BBZD_SPLIT));
        TE.setAttribute("BBZD_NTZK", BBZD_NTZK);
        TE.setAttribute("BBZD_MODIFYDATE", BBZD_MODIFYDATE);
        TE.setAttribute("BBZD_ZSHL", BBZD_ZSHL);
        TE.setAttribute("ChangeLog", String.valueOf(ChangeLog));
//        ChangeLog = 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportDelHZDAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element RowsElement = DataObject.AddChildElement(ReportElement, "DelRows");
        Element RowElement;
        RowsElement.setAttribute("HZD_ORDE", "a");
        JRowInfo RowInfo = DelHeadRowInfo.NextRow;
        while (RowInfo != null) {
            RowElement = DataObject.AddChildElement(RowsElement, "R");
            RowElement.setAttribute("a", RowInfo.HZD_ORDE);
            RowInfo = RowInfo.NextRow;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportHZDAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element RowsElement = DataObject.AddChildElement(ReportElement, "Rows");
        Element RowElement;
        RowsElement.setAttribute("HZD_ORDE", "a");
        RowsElement.setAttribute("HZD_ZB", "b");
        RowsElement.setAttribute("BZBM_BM", "c");
        RowsElement.setAttribute("HZD_PRO", "d");
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        while (RowInfo != null) {
            // 如果等于0说明没有修改过,不需要保存
            if (RowInfo.ChangeLog != 0) {
                RowElement = DataObject.AddChildElement(RowsElement, "R");
                RowElement.setAttribute("a", RowInfo.HZD_ORDE);
                RowElement.setAttribute("b", String.valueOf(RowInfo.HZD_ZB));
                RowElement.setAttribute("c", RowInfo.BZBM_BM);
                RowElement.setAttribute("d", String.valueOf(RowInfo.HZD_PRO));
                RowElement.setAttribute("z", String.valueOf(RowInfo.ChangeLog));
//        RowInfo.ChangeLog = 0;
            }
            RowInfo = RowInfo.NextRow;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportDelLZDAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element ColsElement = DataObject.AddChildElement(ReportElement, "DelCols");
        Element ColElement;
        ColsElement.setAttribute("LZD_ORDE", "a");
        JColInfo ColInfo = DelHeadColInfo.NextCol;
        while (ColInfo != null) {
            ColElement = DataObject.AddChildElement(ColsElement, "C");
            ColElement.setAttribute("a", ColInfo.LZD_ORDE);
            ColInfo = ColInfo.NextCol;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportLZDAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element ColsElement = DataObject.AddChildElement(ReportElement, "Cols");
        Element ColElement;
        ColsElement.setAttribute("LZD_ORDE", "a");
        ColsElement.setAttribute("LZD_ZB", "b");
        ColsElement.setAttribute("BZBM_BM", "c");
        ColsElement.setAttribute("LZD_MC", "d");
        ColsElement.setAttribute("LZD_SZ", "e");
        ColsElement.setAttribute("LZD_HZ", "f");
        ColsElement.setAttribute("LZD_TZ", "g");
        ColsElement.setAttribute("LZD_XM", "h");
        ColsElement.setAttribute("LZD_PX", "i");
        JColInfo ColInfo = HeadColInfo.NextCol;
        while (ColInfo != null) {

            if (ColInfo.ChangeLog != 0) {
                ColElement = DataObject.AddChildElement(ColsElement, "C");
                // 设置属性
                ColElement.setAttribute("a", ColInfo.LZD_ORDE);
                ColElement.setAttribute("b", String.valueOf(ColInfo.LZD_ZB));
                ColElement.setAttribute("c", ColInfo.BZBM_BM);
                ColElement.setAttribute("d", ColInfo.LZD_MC);
                ColElement.setAttribute("e", ColInfo.LZD_SZ ? "1" : "0");
                ColElement.setAttribute("f", ColInfo.LZD_HZ ? "1" : "0");
                ColElement.setAttribute("g", ColInfo.LZD_TZ ? "1" : "0");
                ColElement.setAttribute("h", ColInfo.LZD_XM ? "1" : "0");
                ColElement.setAttribute("i", ColInfo.LZD_PX ? "1" : "0");
                ColElement.setAttribute("z", String.valueOf(ColInfo.ChangeLog));
//        ColInfo.ChangeLog = 0;
            }
            ColInfo = ColInfo.NextCol;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportDelBDHAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element BdhsElement = DataObject.AddChildElement(ReportElement, "DelBdhs");
        Element BdhElement;
        BdhsElement.setAttribute("BDH_ORDE", "a");
        BdhsElement.setAttribute("BDH_KSORDE", "b");
        JBdhInfo BdhInfo = null;
        for (int i = 0; i < DelBdhInfoList.size(); i++) {
            BdhInfo = (JBdhInfo) DelBdhInfoList.get(i);
            BdhElement = DataObject.AddChildElement(BdhsElement, "H");
            BdhElement.setAttribute("a", BdhInfo.BDH_ORDE);
            BdhElement.setAttribute("b", BdhInfo.RowInfo.HZD_ORDE);
        }
//    JBdhInfo BdhInfo = DelHeadBdhInfo.Next;
//    while ( BdhInfo != null ) {
//      BdhElement = DataObject.AddChildElement(BdhsElement,"H");
//      BdhElement.setAttribute("a",BdhInfo.BDH_ORDE);
//      BdhElement.setAttribute("b",BdhInfo.RowInfo.HZD_ORDE);
//      BdhInfo = BdhInfo.Next;
//    }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportBDHAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element BdhsElement = DataObject.AddChildElement(ReportElement, "Bdhs");
        Element BdhElement;
        BdhsElement.setAttribute("BDH_ORDE", "a");
        BdhsElement.setAttribute("BZBM_BM", "b");
        BdhsElement.setAttribute("BDH_MC", "c");
        BdhsElement.setAttribute("BDH_KSORDE", "d");
        BdhsElement.setAttribute("BDH_NUM", "e");
        BdhsElement.setAttribute("BDH_DYL", "f");
        BdhsElement.setAttribute("BDH_ISNEW", "g");
        JBdhInfo BdhInfo = null;
        for (int i = 0; i < BdhInfoList.size(); i++) {
            BdhInfo = (JBdhInfo) BdhInfoList.get(i);
            if (BdhInfo.ChangeLog != 0) {
                BdhElement = DataObject.AddChildElement(BdhsElement, "H");
                BdhElement.setAttribute("a", BdhInfo.BDH_ORDE);
                BdhElement.setAttribute("b", BdhInfo.getBZBM_BM());
                BdhElement.setAttribute("c", BdhInfo.BDH_MC);
                BdhElement.setAttribute("d", BdhInfo.RowInfo.HZD_ORDE);
                BdhElement.setAttribute("e", String.valueOf(BdhInfo.BDH_NUM));
                BdhElement.setAttribute("f", BdhInfo.DyColInfo.LZD_ORDE);
                BdhElement.setAttribute("g", String.valueOf(BdhInfo.BDH_ISNEW));
                BdhElement.setAttribute("z", String.valueOf(BdhInfo.ChangeLog));
//        BdhInfo.ChangeLog = 0;
            }
        }
//    JBdhInfo BdhInfo = HeadBdhInfo.Next;
//    while ( BdhInfo != null ) {
//      // 如果等于0说明没有修改过,不需要保存
//      if ( BdhInfo.ChangeLog != 0 ) {
//        BdhElement = DataObject.AddChildElement(BdhsElement,"H");
//        BdhElement.setAttribute("a",BdhInfo.BDH_ORDE);
//        BdhElement.setAttribute("b",BdhInfo.BZBM_BM);
//        BdhElement.setAttribute("c",BdhInfo.BDH_MC);
//        BdhElement.setAttribute("d",BdhInfo.RowInfo.HZD_ORDE);
//        BdhElement.setAttribute("e",String.valueOf(BdhInfo.BDH_NUM));
//        BdhElement.setAttribute("f",BdhInfo.DyColInfo.LZD_ORDE);
//        BdhElement.setAttribute("z",String.valueOf(BdhInfo.ChangeLog));
//        BdhInfo.ChangeLog = 0;
//      }
//      BdhInfo = BdhInfo.Next;
//    }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportDelBDLAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element BdlsElement = DataObject.AddChildElement(ReportElement, "DelBdls");
        Element BdlElement;
        BdlsElement.setAttribute("BDL_ORDE", "a");
        BdlsElement.setAttribute("BDL_KSORDE", "b");
        JBdlInfo BdlInfo = null;
        for (int i = 0; i < DelBdlInfoList.size(); i++) {
            BdlInfo = (JBdlInfo) DelBdlInfoList.get(i);
            BdlElement = DataObject.AddChildElement(BdlsElement, "L");
            BdlElement.setAttribute("a", BdlInfo.BDL_ORDE);
            BdlElement.setAttribute("b", BdlInfo.ColInfo.LZD_ORDE);
        }
//    JBdlInfo BdlInfo = HeadBdlInfo.Next;
//    while ( BdlInfo != null ) {
//      BdlElement = DataObject.AddChildElement(BdlsElement,"L");
//      BdlElement.setAttribute("a",BdlInfo.BDL_ORDE);
//      BdlElement.setAttribute("b",BdlInfo.ColInfo.LZD_ORDE);
//      BdlInfo = BdlInfo.Next;
//    }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportBDLAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element BdlsElement = DataObject.AddChildElement(ReportElement, "Bdls");
        Element BdlElement;
        BdlsElement.setAttribute("BDL_ORDE", "a");
        BdlsElement.setAttribute("BZBM_BM", "b");
        BdlsElement.setAttribute("BDL_MC", "c");
        BdlsElement.setAttribute("BDL_KSORDE", "d");
        BdlsElement.setAttribute("BDL_NUM", "e");
        JBdlInfo BdlInfo = null;
        for (int i = 0; i < BdlInfoList.size(); i++) {
            BdlInfo = (JBdlInfo) BdlInfoList.get(i);
            if (BdlInfo.ChangeLog != 0) {
                BdlElement = DataObject.AddChildElement(BdlsElement, "L");
                BdlElement.setAttribute("a", BdlInfo.BDL_ORDE);
                BdlElement.setAttribute("b", BdlInfo.BZBM_BM);
                BdlElement.setAttribute("c", BdlInfo.BDL_MC);
                BdlElement.setAttribute("d", BdlInfo.ColInfo.LZD_ORDE);
                BdlElement.setAttribute("e", String.valueOf(BdlInfo.BDL_NUM));
                BdlElement.setAttribute("z", String.valueOf(BdlInfo.ChangeLog));
//        BdlInfo.ChangeLog = 0;
            }
        }
//    JBdlInfo BdlInfo = HeadBdlInfo.Next;
//    while ( BdlInfo != null ) {
//      // 如果等于0说明没有修改过,不需要保存
//      if ( BdlInfo.ChangeLog != 0 ) {
//        BdlElement = DataObject.AddChildElement(BdlsElement,"L");
//        BdlElement.setAttribute("a",BdlInfo.BDL_ORDE);
//        BdlElement.setAttribute("b",BdlInfo.BZBM_BM);
//        BdlElement.setAttribute("c",BdlInfo.BDL_MC);
//        BdlElement.setAttribute("d",BdlInfo.ColInfo.LZD_ORDE);
//        BdlElement.setAttribute("e",String.valueOf(BdlInfo.BDL_NUM));
//        BdlElement.setAttribute("z",String.valueOf(BdlInfo.ChangeLog));
//        BdlInfo.ChangeLog = 0;
//      }
//      BdlInfo = BdlInfo.Next;
//    }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportDYZDAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element UnitsElement = DataObject.AddChildElement(ReportElement, "Units");
        Element UnitElement, JygsElement, commentElement;
        UnitsElement.setAttribute("HZD_ORDE", "a");
        UnitsElement.setAttribute("LZD_ORDE", "b");
        UnitsElement.setAttribute("H_BZBM", "c");
        UnitsElement.setAttribute("L_BZBM", "d");
        UnitsElement.setAttribute("DYZD_LX", "e");
        UnitsElement.setAttribute("DYZD_DEC", "f");
        UnitsElement.setAttribute("DYZD_SJ", "g");
        UnitsElement.setAttribute("DYZD_DATA", "h");
        UnitsElement.setAttribute("DYZD_SFTZ", "i");
        UnitsElement.setAttribute("DYZD_SFBH", "j");
        UnitsElement.setAttribute("DYZD_HOFFSET", "k");
        UnitsElement.setAttribute("DYZD_LOFFSET", "l");
        UnitsElement.setAttribute("DYZD_GSJB", "m");
        UnitsElement.setAttribute("DYZD_GSX", "n");
        UnitsElement.setAttribute("DYZD_COMBO", "o");
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                if (UI.ChangeLog != 0) {
                    UnitElement = DataObject.AddChildElement(UnitsElement, "U");
                    // 设置属性
                    UnitElement.setAttribute("a", UI.RowInfo.HZD_ORDE);
                    UnitElement.setAttribute("b", UI.ColInfo.LZD_ORDE);
                    UnitElement.setAttribute("c", UI.RowInfo.BZBM_BM);
                    UnitElement.setAttribute("d", UI.ColInfo.BZBM_BM);
                    UnitElement.setAttribute("e", UI.DYZD_LX == 0 ? "N" : "C");
                    UnitElement.setAttribute("f", String.valueOf(UI.DYZD_DEC));
                    try {
                        /* DataObject.SetElementValue(UnitElement,"g",ReportView.getText(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1));//UI.DYZD_SJ);
                         UnitElement.setAttribute("h","0.0");
                         UnitElement.setAttribute("h",String.valueOf(ReportView.getNumber(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1)));*/
                        //modi by fsz 2004.5.20
                        if (UI.DYZD_SJ == null) {
                            UI.DYZD_SJ = "";
                        }
                        UnitElement.setAttribute("g", UI.DYZD_SJ);
						BigDecimal bData = new BigDecimal(String.valueOf(UI.DYZD_DATA));
						   if ( bData.compareTo(bMaxn) >0 ) { // Modify by lk
							UnitElement.setAttribute("h","0");
						} else {
							UnitElement.setAttribute("h",String.valueOf(UI.DYZD_DATA));
						}
//                        UnitElement.setAttribute("h", String.valueOf(UI.DYZD_DATA));
                        //end
                    }
                    catch (Exception e) { /*e.printStackTrace();*/}
                    UnitElement.setAttribute("i", UI.DYZD_SFTZ ? "Y" : "N");
                    UnitElement.setAttribute("j", UI.DYZD_SFBH ? "Y" : "N");
                    UnitElement.setAttribute("k", String.valueOf(UI.DYZD_HOFFSET));
                    UnitElement.setAttribute("l", String.valueOf(UI.DYZD_LOFFSET));
                    UnitElement.setAttribute("m", String.valueOf(UI.DYZD_GSJB));
                    // 公式项先暂不处理其他公式项目
                    String T = UI.DYZD_GSX;
                    if (T == null) {
                        T = "";
                    }
                    /**
                     * 修改计算公式
                     * modified by hufeng 2006.8.17
                     */
                    if(!T.equals("")){
                      ROE.mIsModifiedJsgs = true;
                    }
                    DataObject.SetElementValue(UnitElement, "n", T);
                    DataObject.SetElementValue(UnitElement, "o", UI.DYZD_COMBO);
                    UnitElement.setAttribute("z", String.valueOf(UI.ChangeLog));
                    JJygsInfo JygsInfo;
                    if (UI.JygsList != null) {
                        for (int i = 0; i < UI.JygsList.size(); i++) {
                            JygsInfo = (JJygsInfo) UI.JygsList.get(i);
                            if (JygsInfo.JYGS_GSX != null && JygsInfo.JYGS_GSX.trim().length() != 0) {
                                JygsElement = DataObject.AddChildElement(UnitElement, "J");
                                DataObject.SetElementValue(JygsElement, "a", JygsInfo.JYGS_BJ);
                                DataObject.SetElementValue(JygsElement, "b", JygsInfo.JYGS_GSX);
                                JygsElement.setAttribute("c", String.valueOf(JygsInfo.JYGS_HOFFSET));
                                JygsElement.setAttribute("d", String.valueOf(JygsInfo.JYGS_LOFFSET));
                                JygsElement.setAttribute("e", JygsInfo.JYGS_ORDE);
                                JygsElement.setAttribute("f", JygsInfo.JYGS_SHOW);
                                JygsElement.setAttribute("g", String.valueOf(JygsInfo.JYGS_TYPE));
                                JygsElement.setAttribute("h", String.valueOf(JygsInfo.JYGS_STYLE));
                                JygsElement.setAttribute("i", String.valueOf(JygsInfo.JYGS_SYFW));
                                /**
                                 * 修改校验公式
                                 * modified by hufeng 2006.8.17
                                 */
                                ROE.mIsModifiedJygs = true;
                            }
                        }
                    }
//          UI.ChangeLog = 0;
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportZHGSAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element UnitsElement = DataObject.AddChildElement(ReportElement, "Units");
        Element UnitElement, JygsElement, commentElement;
        UnitsElement.setAttribute("HZD_ORDE", "a");
        UnitsElement.setAttribute("LZD_ORDE", "b");
        UnitsElement.setAttribute("H_BZBM", "c");
        UnitsElement.setAttribute("L_BZBM", "d");
        UnitsElement.setAttribute("DYZD_LX", "e");
        UnitsElement.setAttribute("DYZD_DEC", "f");
        UnitsElement.setAttribute("DYZD_SJ", "g");
        UnitsElement.setAttribute("DYZD_DATA", "h");
        UnitsElement.setAttribute("DYZD_SFTZ", "i");
        UnitsElement.setAttribute("DYZD_SFBH", "j");
        UnitsElement.setAttribute("DYZD_HOFFSET", "k");
        UnitsElement.setAttribute("DYZD_LOFFSET", "l");
        UnitsElement.setAttribute("DYZD_GSJB", "m");
        UnitsElement.setAttribute("DYZD_GSX", "n");
        UnitsElement.setAttribute("DYZD_COMBO", "o");
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                if (UI.ChangeLog != 0) {
                    UnitElement = DataObject.AddChildElement(UnitsElement, "U");
                    // 设置属性
                    UnitElement.setAttribute("a", UI.RowInfo.HZD_ORDE);
                    UnitElement.setAttribute("b", UI.ColInfo.LZD_ORDE);
                    UnitElement.setAttribute("c", UI.RowInfo.BZBM_BM);
                    UnitElement.setAttribute("d", UI.ColInfo.BZBM_BM);
                    UnitElement.setAttribute("e", UI.DYZD_LX == 0 ? "N" : "C");
                    UnitElement.setAttribute("f", String.valueOf(UI.DYZD_DEC));
                    try {
                        if (UI.DYZD_SJ == null) {
                            UI.DYZD_SJ = "";
                        }
                        UnitElement.setAttribute("g", UI.DYZD_SJ);
						BigDecimal bData = new BigDecimal(String.valueOf(UI.DYZD_DATA));
						   if ( bData.compareTo(bMaxn) >0 ) { // Modify by lk
							UnitElement.setAttribute("h","0");
						} else {
							UnitElement.setAttribute("h",String.valueOf(UI.DYZD_DATA));
						}
                    }
                    catch (Exception e) { /*e.printStackTrace();*/}
                    UnitElement.setAttribute("i", UI.DYZD_SFTZ ? "Y" : "N");
                    UnitElement.setAttribute("j", UI.DYZD_SFBH ? "Y" : "N");
                    UnitElement.setAttribute("k", String.valueOf(UI.DYZD_HOFFSET));
                    UnitElement.setAttribute("l", String.valueOf(UI.DYZD_LOFFSET));
                    UnitElement.setAttribute("m", String.valueOf(UI.DYZD_GSJB));
                    // 公式项先暂不处理其他公式项目
                    String T = UI.DYZD_GSX;
                    if (T == null) {
                        T = "";
                    }
                    /**
                     * 修改计算公式
                     * modified by hufeng 2006.8.17
                     */
                    if(!T.equals("")){
                      ROE.mIsModifiedJsgs = true;
                    }
                    DataObject.SetElementValue(UnitElement, "n", T);
                    DataObject.SetElementValue(UnitElement, "o", UI.DYZD_COMBO);
                    UnitElement.setAttribute("z", String.valueOf(UI.ChangeLog));
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportJYGSAttrib(JReportDataObject DataObject, Element ReportElement) {

    }

    //------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//------------------------------------------------------------------------------------------------
    private void SaveReportTZGSAttrib(JReportDataObject RDO, Element ReportElement) {
       JParamObject PO = JParamObject.Create();
       Element TzgsesElement = RDO.AddChildElement(ReportElement, "Tzgses");
       Element tzgsElement;
       TzgsesElement.setAttribute("BBZD_BH",    "a");

       TzgsesElement.setAttribute("BBZD_DATE", "b");
       TzgsesElement.setAttribute("HZD_ORDE",  "c");
       TzgsesElement.setAttribute("LZD_ORDE",  "d");
       TzgsesElement.setAttribute("TZGS_GSX", "e");
       JRowInfo RowInfo = HeadRowInfo.NextRow;
       JUnitInfo UI;
       while (RowInfo != null) {
           UI = RowInfo.UnitRowHead;
           while (UI != null) {
              // if (UI.ChangeLog != 0) {
                   JTzgsInfo tzgsInfo;
                   if (UI.DYZD_TZGS != null&& UI.DYZD_TZGS.toString().trim().length()>0) {

                           tzgsElement = RDO.AddChildElement(TzgsesElement, "T");

                           tzgsElement.setAttribute("a", ADD_BH == null ? "" : ADD_BH.trim());
						   BigDecimal bData = new BigDecimal(String.valueOf(UI.DYZD_DATA));
						   if ( bData.compareTo(bMaxn) >0 ) { // Modify by lk
							   tzgsElement.setAttribute("b","0");
						   } else {
							   tzgsElement.setAttribute("b",String.valueOf(UI.DYZD_DATA));
						   }
//                           tzgsElement.setAttribute("b", String.valueOf(UI.DYZD_DATA));
                           tzgsElement.setAttribute("c", UI.RowInfo.HZD_ORDE);
                           tzgsElement.setAttribute("d", UI.ColInfo.LZD_ORDE);
                           tzgsElement.setAttribute("e", UI.DYZD_TZGS);

                   }
             //  }
               UI = UI.RowNextUnit;
           }
           RowInfo = RowInfo.NextRow;
       }
   }

    //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
      private void SaveReportZSGSAttrib(JReportDataObject RDO, Element ReportElement) {

    	  JParamObject PO = JParamObject.Create();
         Element ZsgsesElement = RDO.AddChildElement(ReportElement, "Zsgses");
         Element zsgsElement;
         ZsgsesElement.setAttribute("HZD_ORDE",  "a");
         ZsgsesElement.setAttribute("LZD_ORDE",  "b");
         ZsgsesElement.setAttribute("DYZD_ZSHOFFSET","c");
         ZsgsesElement.setAttribute("DYZD_ZSLOFFSET","d");
         ZsgsesElement.setAttribute("DYZD_ZSGS", "e");
         JRowInfo RowInfo = HeadRowInfo.NextRow;
         JUnitInfo UI;
         while (RowInfo != null) {
             UI = RowInfo.UnitRowHead;
             while (UI != null) {
                 if (UI.ChangeLogZs != 0) {
//                     if (UI.DYZD_ZSGS != null && UI.DYZD_ZSGS.toString().trim().length()>0) {
                    	zsgsElement = RDO.AddChildElement(ZsgsesElement, "Z");
                    	zsgsElement.setAttribute("a", UI.RowInfo.HZD_ORDE);
  						zsgsElement.setAttribute("b", UI.ColInfo.LZD_ORDE);
  						zsgsElement.setAttribute("c", String.valueOf(UI.DYZD_ZSHOFFSET));
  						zsgsElement.setAttribute("d", String.valueOf(UI.DYZD_ZSLOFFSET));
  						if ( UI.DYZD_ZSGS != null ) {
  							zsgsElement.setAttribute("e", UI.DYZD_ZSGS);
  						} else {
  							zsgsElement.setAttribute("e", "");
  						}
//                     }
                 }
                 UI = UI.RowNextUnit;
             }
             RowInfo = RowInfo.NextRow;
         }
     }



    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportAddJSGSAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element UnitsElement = DataObject.AddChildElement(ReportElement, "Addjsgses");
        Element UnitElement, JygsElement;
        UnitsElement.setAttribute("HZD_ORDE", "a");
        UnitsElement.setAttribute("LZD_ORDE", "b");
        UnitsElement.setAttribute("DYZD_HOFFSET", "c");
        UnitsElement.setAttribute("DYZD_LOFFSET", "d");
        UnitsElement.setAttribute("DYZD_GSJB", "e");
        UnitsElement.setAttribute("DYZD_GSX", "f");
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                // 如果修改过,并且是类别或是单位
//                if (UI.ChangeLog != 0 && (UI.JSGS_TYPE == UI.JSGS_TYPE_LBZD || UI.JSGS_TYPE == UI.JSGS_TYPE_DWZD || UI.JSGS_TYPE == UI.JSGS_TYPE_BMZD)) {
                  if (UI.ChangeLogJs != 0 ) {
                    UnitElement = DataObject.AddChildElement(UnitsElement, "U");
                    // 设置属性
                    UnitElement.setAttribute("a", UI.RowInfo.HZD_ORDE);
                    UnitElement.setAttribute("b", UI.ColInfo.LZD_ORDE);
                    UnitElement.setAttribute("c", String.valueOf(UI.DYZD_HOFFSET));
                    UnitElement.setAttribute("d", String.valueOf(UI.DYZD_LOFFSET));
                    UnitElement.setAttribute("e", String.valueOf(UI.DYZD_GSJB));
                    // 公式项先暂不处理其他公式项目
                    DataObject.SetElementValue(UnitElement, "f", UI.DYZD_GSX);
                    UnitElement.setAttribute("z", String.valueOf(UI.ChangeLog));
                    /**
                     * 修改了单位计算公式
                     * modified by hufeng 2006.8.17
                     */
                    if(!(UI.DYZD_GSX == null || UI.DYZD_GSX.equals(""))){
                      ROE.mIsModifiedAddJsgs = true;
                    }
                    //          UI.ChangeLog = 0;
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportAddJYGSAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element JygssElement = DataObject.AddChildElement(ReportElement, "Addjygses");
        Element UnitElement, JygsElement;
        JygssElement.setAttribute("JYGS_ORDE", "a");
        JygssElement.setAttribute("HZD_ORDE", "b");
        JygssElement.setAttribute("LZD_ORDE", "c");
        JygssElement.setAttribute("DYZD_HOFFSET", "d");
        JygssElement.setAttribute("DYZD_LOFFSET", "e");
        JygssElement.setAttribute("JYGS_BJ", "f");
        JygssElement.setAttribute("JYGS_MESS","g");
        JygssElement.setAttribute("JYGS_SHJG","h");
        JygssElement.setAttribute("JYGS_EXP", "i");
        JygssElement.setAttribute("JYGS_SHOW", "j");
        JygssElement.setAttribute("JYGS_TYPE", "k");
        JygssElement.setAttribute("JYGS_STYLE", "l");
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        Vector JygsList;
        JJygsInfo JygsInfo;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                // 如果修改过,并且是类别或是单位
//                if (UI.ChangeLog != 0 && (UI.JYGS_TYPE == UI.JYGS_TYPE_LBZD || UI.JYGS_TYPE == UI.JYGS_TYPE_DWZD || UI.JYGS_TYPE==UI.JYGS_TYPE_BMZD)) {
                if (UI.ChangeLogJy != 0 ) {
                    //加入单元格
                    UnitElement = DataObject.AddChildElement(JygssElement, "U");
                    // 设置属性
                    UnitElement.setAttribute("b", UI.RowInfo.HZD_ORDE);
                    UnitElement.setAttribute("c", UI.ColInfo.LZD_ORDE);
                    //
                    JygsList = UI.JygsList;
                    if (JygsList != null) {
                        for (int i = 0; i < JygsList.size(); i++) {
                            JygsInfo = (JJygsInfo) JygsList.get(i);
                            /**
                             * 不保存报表格式中的公式，现在不管在单位表的同一单元格上是否有校验公式，都要显示格式中的校验公式
                             * modified by hufeng 2005.11.23
                             */
                            if(JygsInfo.JYGS_FROM.equals(JJygsInfo.FROM_XTBB)){
                              continue;
                            }
                            if (JygsInfo.JYGS_GSX != null ) {//&& JygsInfo.JYGS_GSX.trim().length() != 0) {
                                JygsElement = DataObject.AddChildElement(UnitElement, "J");
                                JygsElement.setAttribute("a", JygsInfo.JYGS_ORDE);
                                JygsElement.setAttribute("b", UI.RowInfo.HZD_ORDE);
                                JygsElement.setAttribute("c", UI.ColInfo.LZD_ORDE);
                                JygsElement.setAttribute("d", String.valueOf(JygsInfo.JYGS_HOFFSET));
                                JygsElement.setAttribute("e", String.valueOf(JygsInfo.JYGS_LOFFSET));
                                DataObject.SetElementValue(JygsElement, "f", JygsInfo.JYGS_BJ);
                                JygsElement.setAttribute("g","");
                                JygsElement.setAttribute("h","");
                                DataObject.SetElementValue(JygsElement, "i", JygsInfo.JYGS_GSX);
                                DataObject.SetElementValue(JygsElement, "j", JygsInfo.JYGS_SHOW);
                                DataObject.SetElementValue(JygsElement, "k", String.valueOf(JygsInfo.JYGS_TYPE));
                                DataObject.SetElementValue(JygsElement, "l", String.valueOf(JygsInfo.JYGS_STYLE));
                                DataObject.SetElementValue(JygsElement, "o", String.valueOf(JygsInfo.JYGS_SYFW));
                                /**
                                 * 修改了单位校验公式
                                 * modified by hufeng 2006.8.17
                                 */
                                if(!(JygsInfo.JYGS_GSX == null || JygsInfo.JYGS_GSX.equals(""))){
                                  ROE.mIsModifiedAddJygs = true;
                                }

                            }
                        }
                    }
//          UI.ChangeLog = 0;
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportAddZSGSAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element UnitsElement = DataObject.AddChildElement(ReportElement, "Addzsgses");
        Element UnitElement;
        UnitsElement.setAttribute("HZD_ORDE", "a");
        UnitsElement.setAttribute("LZD_ORDE", "b");
        UnitsElement.setAttribute("DYZD_ZSHOFFSET", "c");
        UnitsElement.setAttribute("DYZD_ZSLOFFSET", "d");
        UnitsElement.setAttribute("DYZD_ZSGS", "e");
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                // 如果修改过,并且是类别或是单位
                  if (UI.ChangeLogZs != 0 ) {
                    UnitElement = DataObject.AddChildElement(UnitsElement, "U");
                    // 设置属性
                    UnitElement.setAttribute("a", UI.RowInfo.HZD_ORDE);
                    UnitElement.setAttribute("b", UI.ColInfo.LZD_ORDE);
                    UnitElement.setAttribute("c", String.valueOf(UI.DYZD_ZSHOFFSET));
                    UnitElement.setAttribute("d", String.valueOf(UI.DYZD_ZSLOFFSET));
                    if ( UI.DYZD_ZSGS != null ) {
                    	UnitElement.setAttribute("e", UI.DYZD_ZSGS);
                    } else {
                    	UnitElement.setAttribute("e", "");
                    }
                    // 公式项先暂不处理其他公式项目
//                    DataObject.SetElementValue(UnitElement, "e", UI.DYZD_ZSGS);
                    UnitElement.setAttribute("z", String.valueOf(UI.ChangeLog));
                    /**
                     * 修改了单位计算公式
                     * modified by hufeng 2006.8.17
                     */
                    if(!(UI.DYZD_ZSGS == null || UI.DYZD_ZSGS.equals(""))){
                      ROE.mIsModifiedAddZsgs = true;
                    }
                    //          UI.ChangeLog = 0;
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void SaveReportAddDYZDAttrib(JReportDataObject DataObject, Element ReportElement) {
        Element AddUnitsElement = DataObject.AddChildElement(ReportElement, "AddUnits");
        Element AddUnitElement;
        AddUnitsElement.setAttribute("HZD_ORDE", "a");
        AddUnitsElement.setAttribute("LZD_ORDE", "b");
        AddUnitsElement.setAttribute("H_BZBM", "c");
        AddUnitsElement.setAttribute("L_BZBM", "d");
        AddUnitsElement.setAttribute("DYZD_SJ", "e");
        AddUnitsElement.setAttribute("DYZD_DATA", "f");
        JRowInfo RowInfo = HeadRowInfo.NextRow;
        JUnitInfo UI;
        while (RowInfo != null) {
            UI = RowInfo.AddUnitRowHead;
            while (UI != null) {
                if (UI.ChangeLog != 0) {
                    AddUnitElement = DataObject.AddChildElement(AddUnitsElement, "U");
                    // 设置属性
                    AddUnitElement.setAttribute("a", UI.RowInfo.HZD_ORDE);
                    AddUnitElement.setAttribute("b", UI.ColInfo.LZD_ORDE);
                    AddUnitElement.setAttribute("c", UI.RowInfo.BZBM_BM);
                    AddUnitElement.setAttribute("d", UI.ColInfo.BZBM_BM);
                    try {
                        if (UI.DYZD_SJ == null) {
                            UI.DYZD_SJ = "";
                        }
                        AddUnitElement.setAttribute("e", UI.DYZD_SJ);
						BigDecimal bData = new BigDecimal(String.valueOf(UI.DYZD_DATA));
						if ( bData.compareTo(bMaxn) >0 ) { // Modify by lk
							AddUnitElement.setAttribute("f","0");
						} else {
							AddUnitElement.setAttribute("f",String.valueOf(UI.DYZD_DATA));
						}
                        /*   DataObject.SetElementValue(AddUnitElement,"e",ReportView.getText(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1));//UI.DYZD_SJ);
                           AddUnitElement.setAttribute("f","0.0");
                           AddUnitElement.setAttribute("f",String.valueOf(ReportView.getNumber(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1)));*/
                    }
                    catch (Exception e) { /*e.printStackTrace();*/}
//          UI.ChangeLog = 0;
                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }

    //------------------------------------------------------------------------------------------------
     //描述:
     //设计: mengfei(2010.04.20)
     //实现: mengfei
     //修改:
     //------------------------------------------------------------------------------------------------
     private void SaveReportAddTZZDAttrib(JReportDataObject DataObject, Element ReportElement) {
         Element AddUnitsElement = DataObject.AddChildElement(ReportElement, "AddAdjustUnits");
         Element AddUnitElement;
         AddUnitsElement.setAttribute("HZD_ORDE", "a");
         AddUnitsElement.setAttribute("LZD_ORDE", "b");
         AddUnitsElement.setAttribute("H_BZBM", "c");
         AddUnitsElement.setAttribute("L_BZBM", "d");
         AddUnitsElement.setAttribute("DYZD_SJ", "e");
         AddUnitsElement.setAttribute("DYZD_DATA", "f");
         JRowInfo RowInfo = HeadRowInfo.NextRow;
         JRowInfo RI = HeadRowInfo.NextRow;
         JUnitInfo UI;
         JAddUnitInfo AUI;
         while (RowInfo != null) {
             //UI = RowInfo.AddUnitRowHead;
             UI = RowInfo.UnitRowHead;
             while (UI != null) {
                 if (UI.ChangeLog != 0) {
                   //如果有调整公式信息
                   if (UI.DYZD_TZGS != null && UI.DYZD_TZGS.trim().length() != 0) {
                     AddUnitElement = DataObject.AddChildElement(AddUnitsElement, "U");
                     // 设置属性
                     AddUnitElement.setAttribute("a", UI.RowInfo.HZD_ORDE);
                     AddUnitElement.setAttribute("b", UI.ColInfo.LZD_ORDE);
                     AddUnitElement.setAttribute("c", UI.RowInfo.BZBM_BM);
                     AddUnitElement.setAttribute("d", UI.ColInfo.BZBM_BM);
                     try {
                       if (UI.TZZD_SJ == null) {
                         UI.TZZD_SJ = "";
                       }
                       AddUnitElement.setAttribute("e", UI.TZZD_SJ);
                       AddUnitElement.setAttribute("f", String.valueOf(UI.TZZD_DATA));

                       /*   DataObject.SetElementValue(AddUnitElement,"e",ReportView.getText(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1));//UI.DYZD_SJ);
                                      AddUnitElement.setAttribute("f","0.0");
                                      AddUnitElement.setAttribute("f",String.valueOf(ReportView.getNumber(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1)));*/
                     }
                     catch (Exception e) { /*e.printStackTrace();*/}

                   }
                 }
                 UI = UI.RowNextUnit;
             }
             RowInfo = RowInfo.NextRow;

           }
           while (RI != null) {
               AUI = RI.AddUnitRowHead;

               while (AUI != null) {
                   if (AUI.ChangeLog != 0) {
                     String stmp1,stmp2;
                     //JUnitInfo SUI;
                     stmp1 = AUI.RowInfo.HZD_ORDE;
                     stmp2 = AUI.ColInfo.LZD_ORDE;
                     UI = JUnitInfo.GetUnitInfoByOrde(stmp1, stmp2, 1, this);

                     //如果有调整公式信息
                     if (UI.DYZD_TZGS != null && UI.DYZD_TZGS.trim().length() != 0) {
                       AddUnitElement = DataObject.AddChildElement(AddUnitsElement, "U");
                       // 设置属性
                       AddUnitElement.setAttribute("a", UI.RowInfo.HZD_ORDE);
                       AddUnitElement.setAttribute("b", UI.ColInfo.LZD_ORDE);
                       AddUnitElement.setAttribute("c", UI.RowInfo.BZBM_BM);
                       AddUnitElement.setAttribute("d", UI.ColInfo.BZBM_BM);
                       try {
                         if (AUI.TZZD_SJ == null) {
                           AUI.TZZD_SJ = "";
                         }
                         AddUnitElement.setAttribute("e", AUI.TZZD_SJ);
                         AddUnitElement.setAttribute("f", String.valueOf(AUI.TZZD_DATA));

                         /*   DataObject.SetElementValue(AddUnitElement,"e",ReportView.getText(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1));//UI.DYZD_SJ);
                                        AddUnitElement.setAttribute("f","0.0");
                                        AddUnitElement.setAttribute("f",String.valueOf(ReportView.getNumber(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1)));*/
                       }
                       catch (Exception e) { /*e.printStackTrace();*/}

                     }
                   }
                   AUI =(JAddUnitInfo) AUI.RowNextUnit;
               }
               RI = RI.NextRow;

           }

    }
    //------------------------------------------------------------------------------------------------
        //描述:
        //设计: mengfei(2010.04.20)
        //实现: mengfei
        //修改:
        //------------------------------------------------------------------------------------------------
        private void SaveReportTZZDAttrib(JReportDataObject DataObject, Element ReportElement) {
            Element AddUnitsElement = DataObject.AddChildElement(ReportElement, "AddAdjustUnits");
            Element AddUnitElement;
            AddUnitsElement.setAttribute("HZD_ORDE", "a");
            AddUnitsElement.setAttribute("LZD_ORDE", "b");
            AddUnitsElement.setAttribute("H_BZBM", "c");
            AddUnitsElement.setAttribute("L_BZBM", "d");
            AddUnitsElement.setAttribute("DYZD_SJ", "e");
            AddUnitsElement.setAttribute("DYZD_DATA", "f");
            JRowInfo RowInfo = HeadRowInfo.NextRow;
            //JAddUnitInfo UI;
            JUnitInfo UI;
            while (RowInfo != null) {
                UI = RowInfo.UnitRowHead;
               // UI = RowInfo.AddUnitRowHead;
                while (UI != null) {
                    if (UI.ChangeLog != 0) {
                      //如果有调整公式信息
                      if (UI.DYZD_TZGS != null && UI.DYZD_TZGS.trim().length() != 0) {
                        AddUnitElement = DataObject.AddChildElement(AddUnitsElement, "U");
                        // 设置属性
                        AddUnitElement.setAttribute("a", UI.RowInfo.HZD_ORDE);
                        AddUnitElement.setAttribute("b", UI.ColInfo.LZD_ORDE);
                        AddUnitElement.setAttribute("c", UI.RowInfo.BZBM_BM);
                        AddUnitElement.setAttribute("d", UI.ColInfo.BZBM_BM);
                        try {
                          if (UI.TZZD_SJ == null) {
                            UI.TZZD_SJ = "";
                          }
                          AddUnitElement.setAttribute("e", UI.TZZD_SJ);
                          AddUnitElement.setAttribute("f", String.valueOf(UI.TZZD_DATA));

                          /*   DataObject.SetElementValue(AddUnitElement,"e",ReportView.getText(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1));//UI.DYZD_SJ);
                                         AddUnitElement.setAttribute("f","0.0");
                                         AddUnitElement.setAttribute("f",String.valueOf(ReportView.getNumber(UI.RowInfo.HZD_ZB-1,UI.ColInfo.LZD_ZB-1)));*/
                        }
                        catch (Exception e) { /*e.printStackTrace();*/}

                      }
                    }
                    UI = UI.RowNextUnit;
                }
                RowInfo = RowInfo.NextRow;
            }
    }

	private void SaveZbtbxx() {
            JRowInfo RowInfo = HeadRowInfo.NextRow;
            JUnitInfo UI;
            ArrayList valueList = new ArrayList();
            while (RowInfo != null) {
                UI = RowInfo.AddUnitRowHead;
                while (UI != null) {
                    if (UI.ChangeLog != 0  && UI.DYZD_ZBXX != null  ) {
                		if ( !UI.DYZD_ZBXX.trim().equals("") )  {
                			try {
                    			String[] zbValue = new String[2];
                    			BigDecimal bData = new BigDecimal(String.valueOf(UI.DYZD_DATA));
                    			if ( bData.compareTo(bMaxn) >0 ) { // Modify by lk
                    				zbValue[0] = UI.DYZD_ZBXX;
                    				zbValue[1] = "0";
                    				valueList.add(zbValue);
                    			} else {
                    				zbValue[0] = UI.DYZD_ZBXX;
                    				zbValue[1] = String.valueOf(UI.DYZD_DATA);
                    				valueList.add(zbValue);
                    			}
                    		} catch (Exception e) { /*e.printStackTrace();*/}
                    	}
                	}
                    UI = UI.RowNextUnit;
                }
                RowInfo = RowInfo.NextRow;
            }
            if ( valueList.size()>0 ) {
                JResponseObject RO;
                JParamObject PO = JParamObject.Create();
                PO.SetValueByParamName("BBZD_BH", BBZD_BH);
                PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
                PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
                PO.SetValueByParamName("ADD_BH", ADD_BH);
        		DWZD_CODE = JBOFReportPropertyObject.getDwZrzx(PO,null,null,null );
                PO.SetValueByParamName("DWZD_CODE", DWZD_CODE);
                RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "SaveZbtbxx", PO, valueList);
                if (RO.GetErrorCode() != 0) {
                    JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, RO.ErrorString, res.getString("String_224"), JOptionPane.ERROR_MESSAGE);
                }
                else {
                    String Res = (String) RO.ResponseObject;
                }
            }
            
            
        }

	// ------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表数据
    //------------------------------------------------------------------------------------------------
    private void ShowReportData() {
        int i,j,rowCount,colCount,hzb,lzb;
        JUnitInfo UI,unit;
        JRowInfo RowInfo;
        JColInfo ColInfo;
        try {
            ReportView.cbFH.setVisible(false);
            ReportView.cbJYGS.setVisible(false);
            RowInfo = HeadRowInfo.NextRow;
            while (RowInfo != null) {

                UI = RowInfo.UnitRowHead;
                while (UI != null) {
                  /**
                   * 如果当前单元格没有公式项，则跳过
                   * add by hufeng 2005.11.8
                   */
                  if((UI.DYZD_GSX == null || UI.DYZD_GSX.trim().length() == 0) &&
                     (UI.JygsList == null || UI.JygsList.size() == 0) &&
                     (UI.CommentList == null || UI.CommentList.size() == 0)&&
                     (UI.DYZD_TZGS == null || UI.DYZD_TZGS.trim().length() == 0) && 
                     (UI.DYZD_ZSGS == null || UI.DYZD_ZSGS.trim().length() == 0)){
                      UI = UI.RowNextUnit;
                      continue;
                    }
                    ColInfo = UI.ColInfo;
                    try {
                      /**
                       * 更新整个块公式的范围，因为在块公式中，有的地方设置成了同左，同上
                       */
                      rowCount = UI.DYZD_HOFFSET;
                      colCount = UI.DYZD_LOFFSET;
                      if (UI.DYZD_ZSHOFFSET > rowCount) {
                          rowCount = UI.DYZD_ZSHOFFSET;
                      }
                      if (UI.DYZD_ZSLOFFSET > colCount) {
                          colCount = UI.DYZD_ZSLOFFSET;
                      }
                      
                      
                      if (UI.JygsList != null && UI.JygsList.size() > 0) {
                          JJygsInfo jygsInfo = (JJygsInfo) UI.JygsList.get(0);
                          if (jygsInfo.JYGS_HOFFSET > rowCount) {
                              rowCount = jygsInfo.JYGS_HOFFSET;
                          }
                          if (jygsInfo.JYGS_LOFFSET > colCount) {
                              colCount = jygsInfo.JYGS_LOFFSET;
                          }
                      }

                      if (UI.CommentList != null && UI.CommentList.size() > 0) {
                          JCommentInfo commentInfo = (JCommentInfo) UI.CommentList.get(0);
                          if (commentInfo.Comment_HOFFSET > rowCount) {
                              rowCount = commentInfo.Comment_HOFFSET;
                          }
                          if (commentInfo.Comment_LOFFSET > colCount) {
                              colCount = commentInfo.Comment_LOFFSET;
                          }
                      }

                      for (i = 0; i <= rowCount; i++) {
                          for (j = 0; j <= colCount; j++) {
                              if (i == 0 && j == 0) {
                                  JBdhInfo.setLogCellContent(this, RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, UI.DYZD_SJ, UI.DYZD_DATA);
                              } else {
                                  hzb = RowInfo.HZD_ZB + i;
                                  lzb = ColInfo.LZD_ZB + j;
                                  unit = JUnitInfo.GetUnitInfoByHL(hzb, lzb, this);
                                  if (unit != null) {
                                      JBdhInfo.setLogCellContent(this, hzb - 1, lzb - 1, unit.DYZD_SJ, unit.DYZD_DATA);
                                  } else {
                                      ReportView.setText(hzb - 1, lzb - 1, "");
                                  }
                              }
                          }
                      }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    UI = UI.RowNextUnit;
                }
                RowInfo = RowInfo.NextRow;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ShowReportAddData();
        if(TZZD_BH != null && TZZD_BH.trim().length()>0){
            ShowReportAddAdjustData();
          //  ShowReportAddTzData();
        }

        JBdhUtils.ShowReportData(this);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表附加数据
    //------------------------------------------------------------------------------------------------
    private void ShowReportAddData() {
        JUnitInfo UI;
        JRowInfo RowInfo;
        JColInfo ColInfo;
        try {
            if (ADD_BH == null) {
                return;
            }
            RowInfo = HeadRowInfo.NextRow;
            while (RowInfo != null) {
                UI = RowInfo.AddUnitRowHead;
                while (UI != null) {
                    ColInfo = UI.ColInfo;
                    JBdhInfo.setLogCellContent(this,RowInfo.HZD_ZB - 1,ColInfo.LZD_ZB - 1,UI.DYZD_SJ,UI.DYZD_DATA);

//                    try {
//                        try {
//                            Double.valueOf(UI.DYZD_SJ);
//                            ReportView.setNumber(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, UI.DYZD_DATA);
//                        }
//                        catch (Exception e) {
//                            ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, UI.DYZD_SJ);
//                        }
//                    }
//                    catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    UI = UI.RowNextUnit;
                }
                RowInfo = RowInfo.NextRow;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
      //描述:
      //设计: Skyline(2001.12.29)
      //实现: Skyline
      //修改: 显示报表附加数据
      //------------------------------------------------------------------------------------------------
      private void ShowReportAddAdjustData() {
          JAddUnitInfo UI;
          JUnitInfo SUI;
          JRowInfo RowInfo;
          JColInfo ColInfo;
          try {
              if (ADD_BH == null) {
                  return;
              }
              RowInfo = HeadRowInfo.NextRow;
              while (RowInfo != null) {
                  UI = RowInfo.AddUnitRowHead;
                 // SUI = RowInfo.UnitRowHead;
                  while (UI != null) {
                      ColInfo = UI.ColInfo;
                     // if(SUI !=null){
                        //如果
                        if(TZZD_BH != null && TZZD_BH.trim().length()>0){
                          SUI = JUnitInfo.GetUnitInfoByHL(RowInfo.HZD_ZB,ColInfo.LZD_ZB,1,this);
                          if(SUI!=null){
                            if(SUI.DYZD_TZGS!=null&&SUI.DYZD_TZGS.trim().length()>0){
                              JBdhInfo.setLogCellContent(this,RowInfo.HZD_ZB - 1,ColInfo.LZD_ZB - 1,UI.TZZD_SJ,UI.TZZD_DATA);
                            }else{
                              JBdhInfo.setLogCellContent(this,RowInfo.HZD_ZB - 1,ColInfo.LZD_ZB - 1,UI.DYZD_SJ,UI.DYZD_DATA);
                            }

                          }else{
                             JBdhInfo.setLogCellContent(this,RowInfo.HZD_ZB - 1,ColInfo.LZD_ZB - 1,UI.DYZD_SJ,UI.DYZD_DATA);
                          }

                      }else{
                        JBdhInfo.setLogCellContent(this,RowInfo.HZD_ZB - 1,ColInfo.LZD_ZB - 1,UI.DYZD_SJ,UI.DYZD_DATA);
                      }
                     // }



                      UI =(JAddUnitInfo) UI.RowNextUnit;
                   //   SUI = SUI.RowNextUnit;
                  }
                  RowInfo = RowInfo.NextRow;
              }
          }
          catch (Exception e) {
              e.printStackTrace();
          }
      }
      //------------------------------------------------------------------------------------------------
            //描述:
            //设计: Skyline(2001.12.29)
            //实现: Skyline
            //修改: 显示报表附加数据
            //------------------------------------------------------------------------------------------------
            private void ShowReportAddTzData() {
                JTzUnitInfo UI;
                JUnitInfo SUI;
                JRowInfo RowInfo;
                JColInfo ColInfo;
                try {
                    if (ADD_BH == null) {
                        return;
                    }
                    RowInfo = HeadRowInfo.NextRow;
                    while (RowInfo != null) {
                        UI = RowInfo.TzUnitRowHead;
                        SUI = RowInfo.UnitRowHead;
                        while (UI != null) {
                            ColInfo = UI.ColInfo;
                            if(SUI !=null){
                              //如果
                              if((TZZD_BH != null && TZZD_BH.trim().length()>0)&& (SUI.DYZD_TZGS !=null&&SUI.DYZD_TZGS.trim().length()>0)){
                                 // JBdhInfo.setAdjustCell(this, tzgsInfo.TZGS_HZD - 1,tzgsInfo.TZGS_LZD - 1, "",UI.DYZD_DATA, UI);
                                  JBdhInfo.setLogCellContent(this,RowInfo.HZD_ZB - 1,ColInfo.LZD_ZB - 1,UI.DYZD_SJ,UI.DYZD_DATA);
                            }
                            }



                            UI =(JTzUnitInfo) UI.RowNextUnit;
                            SUI = SUI.RowNextUnit;
                        }
                        RowInfo = RowInfo.NextRow;
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
      }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表计算公式
    //------------------------------------------------------------------------------------------------
    private void ShowReportJsgs() {
        int i,j;
        JUnitInfo UI;
        JRowInfo RowInfo;
        JColInfo ColInfo;
        try {
            ReportView.cbFH.setVisible(false);
            ReportView.cbJYGS.setVisible(false);
            RowInfo = HeadRowInfo.NextRow;
            while (RowInfo != null) {
                UI = RowInfo.UnitRowHead;
                while (UI != null) {
                    if (UI.DYZD_GSX != null && UI.DYZD_GSX.trim().length() != 0) {
                        ColInfo = UI.ColInfo;
                        ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, res.getString("String_529"));
                        /**
                         * 定义块公式时，加入同左同上的标志
                         * add by hufeng 2005.11.8
                         */
                        for(i=1; i<=UI.DYZD_HOFFSET; i++){
                          ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1, res.getString("String_530"));
                        }
                        for(i=1; i<=UI.DYZD_LOFFSET; i++){
                          ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1 + i, res.getString("String_531"));
                        }
                        for(i=1; i<=UI.DYZD_HOFFSET; i++){
                          for(j=1; j<=UI.DYZD_LOFFSET; j++){
                            ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1 + j, res.getString("String_532"));
                          }
                        }

                    }
                    UI = UI.RowNextUnit;
                }
                RowInfo = RowInfo.NextRow;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        JBdhUtils.ShowReportJsgs(this);
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表计算公式
    //------------------------------------------------------------------------------------------------
    private void ShowReportZsgs() {
        int i,j;
        JUnitInfo UI;
        JRowInfo RowInfo;
        JColInfo ColInfo;
        try {
            ReportView.cbFH.setVisible(false);
            ReportView.cbJYGS.setVisible(false);
            RowInfo = HeadRowInfo.NextRow;
            while (RowInfo != null) {
                UI = RowInfo.UnitRowHead;
                while (UI != null) {
                    if (UI.DYZD_ZSGS != null && UI.DYZD_ZSGS.trim().length() != 0) {
                        ColInfo = UI.ColInfo;
                        ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, "<折算>");
                        /**
                         * 定义块公式时，加入同左同上的标志
                         * add by hufeng 2005.11.8
                         */
                        for(i=1; i<=UI.DYZD_ZSHOFFSET; i++){
                          ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1, res.getString("String_530"));
                        }
                        for(i=1; i<=UI.DYZD_ZSLOFFSET; i++){
                          ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1 + i, res.getString("String_531"));
                        }
                        for(i=1; i<=UI.DYZD_ZSHOFFSET; i++){
                          for(j=1; j<=UI.DYZD_ZSLOFFSET; j++){
                            ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1 + j, res.getString("String_532"));
                          }
                        }

                    }
                    UI = UI.RowNextUnit;
                }
                RowInfo = RowInfo.NextRow;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        JBdhUtils.ShowReportZsgs(this);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    private void ShowReportJygs() {
        int i,j;
        JUnitInfo UI;
        JRowInfo RowInfo;
        JColInfo ColInfo;
        try {
            ReportView.cbFH.setVisible(true);
            ReportView.cbJYGS.setVisible(true);
            RowInfo = HeadRowInfo.NextRow;
            while (RowInfo != null) {
                UI = RowInfo.UnitRowHead;
                while (UI != null) {
                    if (UI.JygsList != null && UI.JygsList.size() != 0) {
                      JJygsInfo JygsInfo = (JJygsInfo)UI.JygsList.get(0);
                      if (null != JygsInfo.JYGS_GSX && !JygsInfo.JYGS_GSX.equals("")) {
                        ColInfo = UI.ColInfo;
                        ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, res.getString("String_533"));
                        /**
                         * 定义块公式时，加入同左同上的标志
                         * 注意：校验公式的行偏移与列偏移在校验公式信息中
                         * add by hufeng 2005.11.8
                         */
                        for(i=1; i<=JygsInfo.JYGS_HOFFSET; i++){
                          ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1, res.getString("String_534"));
                        }
                        for(i=1; i<=JygsInfo.JYGS_LOFFSET; i++){
                          ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1 + i, res.getString("String_535"));
                        }
                        for(i=1; i<=JygsInfo.JYGS_HOFFSET; i++){
                          for(j=1; j<=JygsInfo.JYGS_LOFFSET; j++){
                            ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1 + j, res.getString("String_536"));
                          }
                        }
                      }

// 2004.11.25 repleace by fsz
//                        ColInfo = UI.ColInfo;
//                        ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, "<校验>");
                    }
                    UI = UI.RowNextUnit;
                }
                RowInfo = RowInfo.NextRow;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        JBdhUtils.ShowReportJygs(this);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: liu,meng(2009.11.12)
    //实现: mengfei
    //修改: 显示报表调整公式
    //------------------------------------------------------------------------------------------------
    private void ShowReportTzgs() {
        int i,j;
        JUnitInfo UI;
        JRowInfo RowInfo;
        JColInfo ColInfo;
        try {
            ReportView.cbFH.setVisible(false);
            ReportView.cbJYGS.setVisible(false);
            RowInfo = HeadRowInfo.NextRow;
            while (RowInfo != null) {
                UI = RowInfo.UnitRowHead;
                while (UI != null) {
                    if (UI.DYZD_TZGS != null&&UI.DYZD_TZGS.trim().length() != 0 ) {
                        ColInfo = UI.ColInfo;
                        ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, res.getString("String_579"));
                    }
                    UI = UI.RowNextUnit;
                }
                RowInfo = RowInfo.NextRow;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
//调整公式不在变动行定义，变动行的调整逻辑和它的主行一样
//        JBdhUtils.ShowReportTzgs(this);
    }


    //------------------------------------------------------------------------------------------------
//描述:
//设计: liu,meng(2009.11.12)
//实现: mengfei
//修改: 通过选择调整金额以调整状态打开表表
//------------------------------------------------------------------------------------------------
private void ShowReportTztz() {
    int i,j;
    JUnitInfo UI;
    JRowInfo RowInfo;
    JColInfo ColInfo;
    try {
        ReportView.cbFH.setVisible(false);
        ReportView.cbJYGS.setVisible(false);
        RowInfo = HeadRowInfo.NextRow;
        while (RowInfo != null) {
            UI = RowInfo.UnitRowHead;
            while (UI != null) {
                if (UI.DYZD_TZGS != null&&UI.DYZD_TZGS.trim().length() != 0 ) {
                    ColInfo = UI.ColInfo;


                }
                UI = UI.RowNextUnit;
            }
            RowInfo = RowInfo.NextRow;
        }
    }
    catch (Exception e) {
        e.printStackTrace();
    }
//调整公式不在变动行定义，变动行的调整逻辑和它的主行一样
//        JBdhUtils.ShowReportTzgs(this);
}


    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public void setDataStatus(int Status) {
        if (DataStatus == this.MODEL_STATUS_DATA) {
            // 如果当前是数据状态
            if (Status == this.MODEL_STATUS_JSGS) {
                this.ShowReportJsgs();
            }
            if (Status == this.MODEL_STATUS_JYGS) {
                this.ShowReportJygs();
            }

            /**
             * 报表调整公式状态
             * add by mengfei 2009-11-12
             */
            if(Status == this.MODEL_STATUS_TZGS){
              this.ShowReportTzgs();
            }
            /**
             * 报表批注状态
             * add by gengeng 2008-8-27
             */
            if (Status == this.MODEL_STATUS_BBPZ) {
                ShowReportComment();
            }
            if (Status == this.MODEL_STATUS_ZSGS) {
                this.ShowReportZsgs();
            }
            
        }
        else {
            this.ShowReportData();
            if (Status == this.MODEL_STATUS_JSGS) {
                this.ShowReportJsgs();
            }
            if (Status == this.MODEL_STATUS_JYGS) {
                this.ShowReportJygs();
            }

            /**
             * 报表调整公式状态
             * add by mengfei 2009-11-12
             */
            if(Status == this.MODEL_STATUS_TZGS){
              this.ShowReportTzgs();
            }
            /**
             * 报表调整状态展示
             * add by mengfei 2009-11-12
             */
            if(Status == this.MODEL_STATUS_TZZT){
              this.ShowReportTztz();
            }


            /**
             * 报表批注状态
             * add by gengeng 2008-8-27
             */
            if (Status == this.MODEL_STATUS_BBPZ) {
                ShowReportComment();
            }
            if (Status == this.MODEL_STATUS_ZSGS) {
                this.ShowReportZsgs();
            }
            
        }
        DataStatus = Status;
    }

    /**
     * 报表批注状态，显示报表批注内容，类似于校验公式
     */
    private void ShowReportComment() {
        JUnitInfo UI;
        JRowInfo RowInfo;
        JColInfo ColInfo;
        try {
            ReportView.cbFH.setVisible(false);
            ReportView.cbJYGS.setVisible(true);
            RowInfo = HeadRowInfo.NextRow;
            while (RowInfo != null) {
                UI = RowInfo.UnitRowHead;
                while (UI != null) {
                    if (UI.CommentList != null && UI.CommentList.size() != 0) {
                        JCommentInfo commentInfo = (JCommentInfo) UI.CommentList.get(0);
                        if (commentInfo != null && commentInfo.Comment_Info.trim().length() > 0) {
                            ColInfo = UI.ColInfo;
                            ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1, "<附注>");

                            for (int i = 1; i <= commentInfo.Comment_HOFFSET; i++) {
                                ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1, res.getString("String_534"));
                            }
                            for (int i = 1; i <= commentInfo.Comment_LOFFSET; i++) {
                                ReportView.setText(RowInfo.HZD_ZB - 1, ColInfo.LZD_ZB - 1 + i, res.getString("String_535"));
                            }
                            for (int i = 1; i <= commentInfo.Comment_HOFFSET; i++) {
                                for (int j = 1; j <= commentInfo.Comment_LOFFSET; j++) {
                                    ReportView.setText(RowInfo.HZD_ZB - 1 + i, ColInfo.LZD_ZB - 1 + j, res.getString("String_536"));
                                }
                            }
                        }
                    }
                    UI = UI.RowNextUnit;
                }
                RowInfo = RowInfo.NextRow;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JBdhUtils.ShowReportJygs(this);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public int getDataStatus() {
        return DataStatus;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public String GetHzdOrde(String BH, String DATE, int Row) {
        JRowInfo RI = null;
        String HzdOrde = null;
        /**
         * 为使校验公式可以使用公式转换（用户－存储），特加了条件this.ReportView != null
         * 使校验公式中，BB类函数强制从后台转换坐标和ORDER
         * 下面这个公式雷同
         * modified by hufeng 2005.9.15
         */
        if (this.BBZD_BH.equals(BH) && this.BBZD_DATE.equals(DATE) && this.ReportView != null) {
            RI = JRowInfo.GetRowInfoByZB(Row, 1, this);
            HzdOrde = RI.HZD_ORDE;
        }
        else {
            JResponseObject RO;
            JParamObject PO = JParamObject.Create();
            PO.SetValueByParamName("BBZD_BH", BH);
            PO.SetValueByParamName("BBZD_DATE", DATE);
            PO.SetValueByParamName("HZD_ZB", String.valueOf(Row));
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "GetHzdOrde", PO);
            if (RO.GetErrorCode() == 0) {
                HzdOrde = (String) RO.ResponseObject;
            }
        }
        return HzdOrde;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public String GetLzdOrde(String BH, String DATE, int Col) {
        JColInfo CI = null;
        String LzdOrde = null;
        if (this.BBZD_BH.equals(BH) && this.BBZD_DATE.equals(DATE) && this.ReportView != null) {
            CI = JColInfo.GetColInfoByZB(Col, 1, this);
            LzdOrde = CI.LZD_ORDE;
        }
        else {
            JResponseObject RO;
            JParamObject PO = JParamObject.Create();
            PO.SetValueByParamName("BBZD_BH", BH);
            PO.SetValueByParamName("BBZD_DATE", DATE);
            PO.SetValueByParamName("LZD_ZB", String.valueOf(Col));
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "GetLzdOrde", PO);
            if (RO.GetErrorCode() == 0) {
                LzdOrde = (String) RO.ResponseObject;
            }
        }
        return LzdOrde;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public int GetHzdZb(String BH, String DATE, String HzdOrde) {
        JRowInfo RI = null;
        int Row = 0;
        if (BBZD_BH.equals(BH) && BBZD_DATE.equals(DATE) && this.ReportView != null) {
            RI = JRowInfo.GetRowInfoByOrde(HzdOrde, this);
			if ( RI != null )
				Row = RI.HZD_ZB;
        }
        else {
            JResponseObject RO;
            JParamObject PO = JParamObject.Create();
            PO.SetValueByParamName("BBZD_BH", BH);
            PO.SetValueByParamName("BBZD_DATE", DATE);
            PO.SetValueByParamName("HZD_ORDE", HzdOrde);
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "GetHzdZb", PO);
            if (RO.GetErrorCode() == 0) {
                Row = Integer.valueOf( (String) RO.ResponseObject).intValue();
            }
            if ( Row == 0 ) { //其它期间没找到，按本表算
                if ( BBZD_BH.equals(BH) ) {
                    RI = JRowInfo.GetRowInfoByOrde(HzdOrde, this);
                    if ( RI != null ) {
                        Row = RI.HZD_ZB;
                    } else {
                        Row = Integer.valueOf(HzdOrde);
                    }
                } else {
                    PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
                    RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "GetHzdZb", PO);
                    if (RO.GetErrorCode() == 0) {
                        Row = Integer.valueOf( (String) RO.ResponseObject).intValue();
                    }
                    if ( Row == 0 ) {
                        Row = Integer.valueOf(HzdOrde);
                    }
                }
            }
        }
        return Row;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public int GetLzdZb(String BH, String DATE, String LzdOrde) {
        JColInfo CI = null;
        int Col = 0;
        if (BBZD_BH.equals(BH) && BBZD_DATE.equals(DATE) && this.ReportView != null) {
            CI = JColInfo.GetColInfoByOrde(LzdOrde, this);
			if ( CI != null)
				Col = CI.LZD_ZB;
			
        }
        else {
            JResponseObject RO;
            JParamObject PO = JParamObject.Create();
            PO.SetValueByParamName("BBZD_BH", BH);
            PO.SetValueByParamName("BBZD_DATE", DATE);
            PO.SetValueByParamName("LZD_ORDE", LzdOrde);
            RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "GetLzdZb", PO);
            if (RO.GetErrorCode() == 0) {
                Col = Integer.valueOf( (String) RO.ResponseObject).intValue();
            }
            if ( Col == 0 ) {
                if ( BBZD_BH.equals(BH) ) {
                    CI = JColInfo.GetColInfoByOrde(LzdOrde, this);
                    if ( CI != null ) {
                    	Col = CI.LZD_ZB;
                    } else {
                        Col = Integer.valueOf(LzdOrde);
                    }
                } else {
                    PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
                    RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "GetLzdZb", PO);
                    if (RO.GetErrorCode() == 0) {
                        Col = Integer.valueOf( (String) RO.ResponseObject).intValue();
                    }
                    if ( Col == 0 ) {
                        Col = Integer.valueOf(LzdOrde);
                    }
                }
            }
        }
        return Col;
    }

    public void InsertRow(int SRow, int ERow) throws Exception {

//      int SPhyRow = JBdhUtils.getPhyRow(SRow-1,this);
//      int EPhyRow = JBdhUtils.getPhyRow(ERow-1,this);
      int AddCount = ERow - SRow + 1; // 获取增加的行数
      int Row = SRow;
      int TwoRowStatus = JBdhUtils.checkPhyTwoRowStatus(this,SRow,ERow);
      if ( TwoRowStatus == 4 ) {
        throw new Exception("不允许跨越不同的区域进行插入操作！");
      }
      // 检查上一行状态
      int UPRowStatuc = JBdhUtils.checkPhyUpRowStatus(this,Row);
      //int 0:正常行 1:旧版变动行 2:新版变动主行 3:新版变动行
      switch ( UPRowStatuc ) {
      case 0:// 0:正常行
        if(ReportView.getModel().getReportType() == JReportModel.STUB_TYPE_REPORT){
          insertRowStyle0(SRow,ERow,AddCount);
          ReportView.editInsert(ReportView.eShiftRows);
          setInsertRowBorder(SRow,ERow); //设置表格线
          break;
        }else{
          JOptionPane.showMessageDialog(ReportView, res.getString("String_134"), res.getString("String_135"), JOptionPane.WARNING_MESSAGE);
          return;
        }

//        insertRowStyle0(SRow,ERow,AddCount);
//        ReportView.editInsert(ReportView.eShiftRows);
////        JBdhInfo.setRowHeadTitle(this,true);
//        setInsertRowBorder(SRow,ERow); //设置表格线
//        break;
      case 1:// 1:旧版变动行
        if(ReportView.getModel().getReportType() == JReportModel.STUB_TYPE_REPORT){
          insertRowStyle1(SRow,ERow,AddCount);
          ReportView.editInsert(ReportView.eShiftRows);
          break;
        }else{
          JOptionPane.showMessageDialog(ReportView, res.getString("String_134"), res.getString("String_135"), JOptionPane.WARNING_MESSAGE);
          return;
        }
      case 2://2:新版变动主行
        insertRowStyle2(SRow,ERow,AddCount);
        AddCount = 0;
        break;
      case 3://3:新版变动行
        insertRowStyle3(SRow,ERow,AddCount);
        AddCount = 0;
        break;
      }
      BBZD_BT += AddCount;
      BBZD_HS += AddCount;
      JBdhInfo.setRowHeadTitle(this,true);
      this.setModified();
      /**
       * 设置最大行数
       * add by hufeng 2005.9.19
       */
      ReportView.setMaxRow(this.getReportMaxRow() -1);
    }

    /**
     * 设置新插入行的表格线
     * @throws Exception
     */
    private void setInsertRowBorder(int SRow, int ERow) throws Exception{
      ReportView.setPhySelection(SRow,0,ERow,this.BBZD_LS -1);
      CellFormat CF = ReportView.getCellFormat();
      CF = ReportView.getCellFormat();
      CF.setTopBorder(CF.eBorderThin);
      CF.setBottomBorder(CF.eBorderThin);
      if(SRow == this.BBZD_TITLE + BBZD_HEAD + BBZD_BT){
        CF.setLeftBorder(CF.eBorderDouble);
        CF.setRightBorder(CF.eBorderDouble);
      }
      CF.setHorizontalInsideBorder(CF.eBorderThin);
      CF.setVerticalInsideBorder(CF.eBorderThin);
      ReportView.setCellFormat(CF);
    }

    /**
     * 设置新插入列的表格线
     * @throws Exception
     */
    private void setInsertColBorder(int SCol, int ECol) throws Exception{
      ReportView.setPhySelection(this.BBZD_TITLE + this.BBZD_HEAD -1,SCol-1,this.BBZD_TITLE + this.BBZD_HEAD + this.BBZD_BT -1,ECol-1);
      CellFormat CF = ReportView.getCellFormat();
      CF = ReportView.getCellFormat();
      if(SCol == 1){
        CF.setTopBorder(CF.eBorderDouble);
      }
      CF.setLeftBorder(CF.eBorderThin);
      CF.setBottomBorder(CF.eBorderDouble);
      CF.setRightBorder(CF.eBorderThin);
      CF.setHorizontalInsideBorder(CF.eBorderThin);
      CF.setVerticalInsideBorder(CF.eBorderThin);
      ReportView.setCellFormat(CF);
    }

    private void insertRowStyle0(int SRow,int ERow,int Count) {
      SRow = JBdhUtils.getLogRow(this,SRow);
      ERow = JBdhUtils.getLogRow(this,ERow);
      JRowInfo.InsertRow(SRow, ERow, this);
    }
    private void insertRowStyle1(int SRow,int ERow,int Count) {
      SRow = JBdhUtils.getLogRow(this,SRow);
      ERow = JBdhUtils.getLogRow(this,ERow);
      JBdhInfo BdhInfo = this.getBDH(SRow-1);
      BdhInfo.incBdhNum(Count);
      JRowInfo.InsertRow(SRow, ERow, this);
    }
    // 处理新版变动行增加的问题，已经存在相应的变动区了
    private void insertRowStyle2(int SRow,int ERow,int Count) throws Exception {
      int Row = SRow;
      // 上一行为新变动区，获取其主行坐标,为物理行
      int PriRow = JBdhUtils.getPhyNewBdhPrioRow(this,Row-1);
      int logPriRow = JBdhUtils.getLogRow(this,PriRow);
      JBdhInfo BdhInfo = this.getBDH(logPriRow);
      ReportView.editInsert(ReportView.eShiftRows);
      BdhInfo.incNewBdh(0,Count);
      JBdhInfo.setNewBdhTitle(this,BdhInfo);
    }

    // 处理新版变动行增加的问题，已经存在相应的变动区了
    private void insertRowStyle3(int SRow,int ERow,int Count) throws Exception {
      int Row = SRow;int Index;
      Index = JBdhUtils.getLogRow(this,Row-1);
      // 上一行为新变动区，获取其主行坐标,为物理行
      int PriRow = JBdhUtils.getPhyNewBdhPrioRow(this,Row-1);
      int logPriRow = JBdhUtils.getLogRow(this,PriRow);
      JBdhInfo BdhInfo = this.getBDH(logPriRow);
      ReportView.editInsert(ReportView.eShiftRows);
      BdhInfo.incNewBdh(Index,Count);
      JBdhInfo.setNewBdhTitle(this,BdhInfo);
    }


    public void InsertCol(int SCol, int ECol){
        JColInfo.InsertCol(SCol, ECol, this);
        BBZD_LS += (ECol - SCol + 1);
        this.setModified();
        /**
         * 设置最大列数
         * add by hufeng 2005.9.19
         */
        try{
          setInsertColBorder(SCol,ECol); //设置表格线
          ReportView.setMaxCol(BBZD_LS-1);
        }catch(Exception e){}
    }

    public void DeleteRow(int SRow, int ERow) throws Exception {
      int DelCount = ERow - SRow + 1; // 获取增加的行数
      int Row = SRow;
      int TwoRowStatus = JBdhUtils.checkPhyTwoRowStatus(this,SRow,ERow);
      if ( TwoRowStatus == 4 ) {
        throw new Exception("不允许跨越不同的区域进行删除操作！");
      }
      //int 0:正常行 1:旧版变动行 2:新版变动主行 3:新版变动行
      int RowStatus = JBdhUtils.checkPhyRowStatus(this,Row);
      switch ( RowStatus ) {
      case 0:
          if(ReportView.getModel().getReportType() == JReportModel.STUB_TYPE_REPORT){
              deleteRowStyle0(SRow,ERow,DelCount);
              ReportView.editDelete(ReportView.eShiftRows);
              JBdhInfo.setRowHeadTitle(this,true);
            break;
          }else{
            JOptionPane.showMessageDialog(ReportView, res.getString("String_134"), res.getString("String_135"), JOptionPane.WARNING_MESSAGE);
            return;
          }
      case 1:
        deleteRowStyle1(SRow,ERow,DelCount);
        ReportView.editDelete(ReportView.eShiftRows);
        break;
      case 2:
        throw new Exception("变动区的主行不允许删除！");
      case 3:
        deleteRowStyle3(SRow,ERow,DelCount);
        DelCount = 0;
        break;
      }
      BBZD_BT -= DelCount;
      BBZD_HS -= DelCount;
      JBdhInfo.setRowHeadTitle(this);
      this.setModified();
//        JRowInfo.DeleteRow(SRow, ERow, this);
//        BBZD_BT -= (ERow - SRow + 1);
//        //add by fdz 2004.10.28
//        BBZD_HS -=(ERow - SRow + 1);
//        //
//        this.setModified();
//        JBdhInfo.setRowHeadTitle(this);
      /**
       * 设置最大行数
       * add by hufeng 2005.9.19
       */
      ReportView.setMaxRow(this.getReportMaxRow() -1);
    }
    private void deleteRowStyle3(int SRow, int ERow,int Count) throws Exception {
      int Row = SRow;int Index;
      Index = JBdhUtils.getLogRow(this,Row) - 1;
      // 上一行为新变动区，获取其主行坐标,为物理行
      int PriRow = JBdhUtils.getPhyNewBdhPrioRow(this,Row);
      int logPriRow = JBdhUtils.getLogRow(this,PriRow);
      JBdhInfo BdhInfo = this.getBDH(logPriRow);
      ReportView.editDelete(ReportView.eShiftRows);
      BdhInfo.decNewBdh(Index,Count);
      JBdhInfo.setNewBdhTitle(this,BdhInfo);

    }
    private void deleteRowStyle1(int SRow, int ERow,int Count) throws Exception {
      SRow = JBdhUtils.getLogRow(this,SRow); // 转换成逻辑行
      ERow = JBdhUtils.getLogRow(this,ERow);
      JBdhInfo BdhInfo = this.getBDH(SRow);
      if ( BdhInfo != null && BdhInfo.RowInfo.HZD_ZB == SRow )
        throw new Exception("变动区的主行不允许删除！");
      JRowInfo.DeleteRow(SRow, ERow, this);
      if ( BdhInfo != null )
        BdhInfo.decBdhNum(Count);
    }
    private void deleteRowStyle0(int SRow, int ERow,int Count) {
      SRow = JBdhUtils.getLogRow(this,SRow); // 转换成逻辑行
      ERow = JBdhUtils.getLogRow(this,ERow);
      JRowInfo.DeleteRow(SRow, ERow, this);
    }
    public void DeleteCol(int SCol, int ECol) {
        JColInfo.DeleteCol(SCol, ECol, this);
        BBZD_LS -= (ECol - SCol + 1);
        this.setModified();
        /**
         * 设置最大列数
         * add by hufeng 2005.9.19
         */
        try{
          ReportView.setMaxCol(BBZD_LS-1);
        }catch(Exception e){}
    }

    public void DeleteForumlaOne(int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {
        JUnitInfo UI;
        JAddUnitInfo AddUI;
//        if(ERow > this.BBZD_HS ){
//            ERow = this.BBZD_HS;
//        }
        /**
         * 加入对变动行的考虑
         * 解决有些变动行公式不能正常删除的问题
         */
        int maxRow = this.getReportMaxRow();
        if(ERow > maxRow){
            ERow = maxRow;
        }
        if(ECol >this.BBZD_LS){
            ECol = this.BBZD_LS;
        }
        int phyRow = phySRow;int phyCol = phySCol;int RowStatus = 0;
        RowStatus = JBdhUtils.checkPhyRowStatus(this,phyRow);
        for (int Row = SRow; Row <= ERow; Row++) {
          phyCol = phySCol;
          for (int Col = SCol; Col <= ECol; Col++) {
            if ( RowStatus == 3 ) {// 新变动行
              UI = JBdhInfo.getUnitInfo(this, Row - 1, Col - 1, phyRow, phyCol);
            } else {
              UI = JUnitInfo.GetUnitInfoByHL(Row, Col, this);
            }

            if (UI != null) {
              UI.deleteGS(this);
            }
            phyCol++;
          }
          phyRow++;
        }
    }

    public void DeleteComment(int SRow, int SCol, int ERow, int ECol, int phySRow, int phySCol, int phyERow, int phyECol) {
        JUnitInfo UI;
        JAddUnitInfo AddUI;
        /**
         * 加入对变动行的考虑
         * 解决有些变动行公式不能正常删除的问题
         */
        int maxRow = getReportMaxRow();
        if (ERow > maxRow) {
            ERow = maxRow;
        }
        if (ECol > BBZD_LS) {
            ECol = BBZD_LS;
        }
        int phyRow = phySRow;
        int phyCol = phySCol;
        int RowStatus = 0;
        RowStatus = JBdhUtils.checkPhyRowStatus(this, phyRow);
        for (int Row = SRow; Row <= ERow; Row++) {
            phyCol = phySCol;
            for (int Col = SCol; Col <= ECol; Col++) {
                if (RowStatus == 3) { // 新变动行
                    UI = JBdhInfo.getUnitInfo(this, Row - 1, Col - 1, phyRow, phyCol);
                } else {
                    UI = JUnitInfo.GetUnitInfoByHL(Row, Col, this);
                }
                if (UI != null) {
                    UI.deleteComment(this);
                }
                phyCol++;
            }
            phyRow++;
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void ChangeUnitData(int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {
        JUnitInfo UI=null;
        JUnitInfo AddUI=null;

        /**
         * 对于选中的区域大于报表的区域的优化处理
         * Stephen Zhao
         * 2004-07-20
         */
      //  if(ERow > this.BBZD_HS ){
      //      ERow = this.BBZD_HS;
      //  }
        if(ECol >this.BBZD_LS){
            ECol = this.BBZD_LS;
        }
        int phyRow = phySRow;int phyCol = phySCol;int RowStatus = 0;
        RowStatus = JBdhUtils.checkPhyRowStatus(this,phyRow);
        for (int Row = SRow; Row <= ERow; Row++) {
          phyCol = phySCol;
          for (int Col = SCol; Col <= ECol; Col++) {
//            RowStatus = JBdhUtils.checkPhyRowStatus(this,phyRow); // 只检查一次
            if ( RowStatus == 3 ) {// 新变动行
              UI = JBdhInfo.getBDUnitInfo(this,Row-1,Col-1,phyRow,phyCol);
              if ( UI == null ) continue;
              try {
                if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                  JBdhInfo.getPhyTzCellContent(this,phyRow,phyCol,UI);
                }else{
                  JBdhInfo.getPhyCellContent(this,phyRow,phyCol,UI);
                }

//                UI.DYZD_SJ = ReportView.getPhyText(phyRow,phyCol);
//                UI.DYZD_DATA = 0;
//                UI.DYZD_DATA = ReportView.getPhyNumber(phyRow,phyCol);
              }
              catch (Exception e) {}
              UI.setModified();
              if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
              	UI.setTZModified();
              }
            } else {// 处理正常行
              if (ADD_BH == null) {
                UI = JUnitInfo.GetUnitInfoByHL(Row, Col, 1,this);
                if ( UI == null ) continue;
                try {
                  if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                    JBdhInfo.getLogTzCellContent(this,UI.RowInfo.HZD_ZB - 1,UI.ColInfo.LZD_ZB - 1,UI);
                  }else{
                    JBdhInfo.getLogCellContent(this,UI.RowInfo.HZD_ZB - 1,UI.ColInfo.LZD_ZB - 1,UI);
                  }

//                  UI.DYZD_SJ = ReportView.getText(UI.RowInfo.HZD_ZB - 1,UI.ColInfo.LZD_ZB - 1);
//                  UI.DYZD_DATA = 0;
//                  UI.DYZD_DATA = ReportView.getNumber(UI.RowInfo.HZD_ZB - 1,UI.ColInfo.LZD_ZB - 1);
                }
                catch (Exception e) {}
                UI.setModified();
                if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                	UI.setTZModified();
                }
                
              } else {// 和理附加数据
                AddUI = JAddUnitInfo.GetUnitInfoByHL(Row, Col, 1,this);
                if ( AddUI == null ) continue;
                try {
                  if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                   JBdhInfo.getLogTzCellContent(this,AddUI.RowInfo.HZD_ZB - 1,AddUI.ColInfo.LZD_ZB - 1,AddUI);
                  }else{
                    JBdhInfo.getLogCellContent(this,AddUI.RowInfo.HZD_ZB - 1,AddUI.ColInfo.LZD_ZB - 1,AddUI);
                  }


//                  AddUI.DYZD_SJ = ReportView.getText(AddUI.RowInfo.HZD_ZB - 1,AddUI.ColInfo.LZD_ZB - 1);
//                  AddUI.DYZD_DATA = 0;
//                  AddUI.DYZD_DATA = ReportView.getNumber(AddUI.RowInfo.HZD_ZB - 1,AddUI.ColInfo.LZD_ZB - 1);
                }
                catch (Exception e) {}
                AddUI.setModified();
                if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                	AddUI.setTZModified();
                }
                }
              }
              phyCol++;
            }
            phyRow++;
        }
    }

    public void ClearUnitData(int SRow, int SCol, int ERow, int ECol, int phySRow, int phySCol, int phyERow, int phyECol) {
        JUnitInfo UI = null;
        JUnitInfo AddUI = null;
        JColInfo colInfo = null;

        /**
         * 对于选中的区域大于报表的区域的优化处理
         * Stephen Zhao
         * 2004-07-20
         */
        ERow = this.BBZD_HS;
        ECol = this.BBZD_LS;
        int phyRow = phySRow;
        int phyCol = phySCol;
        int RowStatus = 0;
        RowStatus = JBdhUtils.checkPhyRowStatus(this, phyRow);
        for (int Row = SRow; Row <= ERow; Row++) {
            phyCol = phySCol;
            for (int Col = SCol; Col <= ECol; Col++) {
                //            RowStatus = JBdhUtils.checkPhyRowStatus(this,phyRow); // 只检查一次
                if (RowStatus == 3) { // 新变动行
                    UI = JBdhInfo.getBDUnitInfo(this, Row - 1, Col - 1, phyRow, phyCol);
                    if (UI == null)continue;
                    if (Row <= this.BBZD_HEAD + this.BBZD_TITLE) {
                        continue;
                    }
                    if (!UI.ColInfo.LZD_XM) {
                        try {
                            ReportView.clearRange(Row - 1, Col - 1, Row - 1, Col - 1, ReportView.eClearValues);
                            if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                              JBdhInfo.getPhyTzCellContent(this, phyRow, phyCol, UI);
                            }else{
                               JBdhInfo.getPhyCellContent(this, phyRow, phyCol, UI);
                            }

                            //                UI.DYZD_SJ = ReportView.getPhyText(phyRow,phyCol);
                            //                UI.DYZD_DATA = 0;
                            //                UI.DYZD_DATA = ReportView.getPhyNumber(phyRow,phyCol);
                        }
                        catch (Exception e) {}
                        UI.setModified();
                        if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                        	UI.setTZModified();
                        }
                    }
                }
                else { // 处理正常行
                    if (ADD_BH == null) {
                        UI = JUnitInfo.GetUnitInfoByHL(Row, Col, 1, this);
                        if (UI == null)continue;
                        if (Row <= this.BBZD_HEAD + this.BBZD_TITLE) {
                            continue;
                        }
                        if (!UI.ColInfo.LZD_XM) {
                            try {
                                ReportView.clearRange(Row - 1, Col - 1, Row - 1, Col - 1, ReportView.eClearValues);
                                if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                                  JBdhInfo.getLogTzCellContent(this, UI.RowInfo.HZD_ZB - 1,
                                    UI.ColInfo.LZD_ZB - 1, UI);
                                }else{
                                  JBdhInfo.getLogCellContent(this, UI.RowInfo.HZD_ZB - 1,
                                    UI.ColInfo.LZD_ZB - 1, UI);
                                }


                                //                  UI.DYZD_SJ = ReportView.getText(UI.RowInfo.HZD_ZB - 1,UI.ColInfo.LZD_ZB - 1);
                                //                  UI.DYZD_DATA = 0;
                                //                  UI.DYZD_DATA = ReportView.getNumber(UI.RowInfo.HZD_ZB - 1,UI.ColInfo.LZD_ZB - 1);
                            }
                            catch (Exception e) {}
                            UI.setModified();
                            if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                            	UI.setTZModified();
                            }
                        }
                    }
                    else {
                        /**
                         * 处理附加数据
                         * 只清除数据区域的数据
                         * 表头和项目列除外
                         * modified by hufeng 2008.1.1
                         */
                        if (Row <= this.BBZD_HEAD + this.BBZD_TITLE) {
                            continue;
                        }
                        colInfo = JColInfo.GetColInfoByZB(Col, this);
                        if (colInfo == null || colInfo.LZD_XM) {
                            continue;
                        }
                        AddUI = JAddUnitInfo.GetUnitInfoByHL(Row, Col, 1, this);
                        if (AddUI == null){
                            continue;
                        }
                        if (!AddUI.ColInfo.LZD_XM) {
                            try {
                                ReportView.clearRange(Row - 1, Col - 1, Row - 1, Col - 1, ReportView.eClearValues);
                                if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                                  JBdhInfo.getLogTzCellContent(this,AddUI.RowInfo.HZD_ZB - 1,AddUI.ColInfo.LZD_ZB - 1,AddUI);
                                }else{
                                  JBdhInfo.getLogCellContent(this,AddUI.RowInfo.HZD_ZB - 1,AddUI.ColInfo.LZD_ZB - 1,AddUI);
                                }

                                //                  AddUI.DYZD_SJ = ReportView.getText(AddUI.RowInfo.HZD_ZB - 1,AddUI.ColInfo.LZD_ZB - 1);
                                //                  AddUI.DYZD_DATA = 0;
                                //                  AddUI.DYZD_DATA = ReportView.getNumber(AddUI.RowInfo.HZD_ZB - 1,AddUI.ColInfo.LZD_ZB - 1);
                            }
                            catch (Exception e) {}
                            AddUI.setModified();
                            if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                            	AddUI.setTZModified();
                            }
                        }
                    }
                }
                phyCol++;
            }
            phyRow++;
        }
    }

   /**
    * 预处理公式只能在系统报表中定义
    * 并且要有报表的公式定义权限
    * @return boolean
    */
   public boolean checkPreFuncLimit() {
       if ( getGSFC() || getBBFC() || ADD_BH != null) {
           return false;
       }
       if (getBBQX(5) == false) {
           return false;
       }
       if ( getBBXF() ) {
           return false;
       }
       return true;
   }


    private boolean checkQX() {
      if ( (getGSFC() || getBBFC()) ){//&& ADD_BH == null) {
        JOptionPane.showMessageDialog(ReportView, res.getString("String_557"), res.getString("String_558"),
                                      JOptionPane.WARNING_MESSAGE);
        return false;
      }
      if ( getBBXF() ){
        JOptionPane.showMessageDialog(ReportView, res.getString("String_556"), res.getString("String_558"),
                                      JOptionPane.WARNING_MESSAGE);
        return false;
      }
      if (ADD_TYPE == JReportModel.STUB_TYPE_BMZD ||
          ADD_TYPE == JReportModel.STUB_TYPE_CBZD ||
          ADD_TYPE == JReportModel.STUB_TYPE_LBZD ||
          ADD_TYPE == JReportModel.STUB_TYPE_DWZD) {
        if (getBBQX(7) == false) {
          JOptionPane.showMessageDialog(ReportView, res.getString("String_559"), res.getString("String_560"),
                                        JOptionPane.WARNING_MESSAGE);
          return false;
        }
        if (ADD_TYPE == STUB_TYPE_LBZD &&
            getADDQX(6) == false) {
          JOptionPane.showMessageDialog(ReportView, res.getString("String_561"), res.getString("String_562"),
                                        JOptionPane.WARNING_MESSAGE);
          return false;
        }
        if (ADD_TYPE == STUB_TYPE_DWZD &&
            getADDQX(5) == false) {
          JOptionPane.showMessageDialog(ReportView, res.getString("String_563"), res.getString("String_564"),
                                        JOptionPane.WARNING_MESSAGE);
          return false;
        }
        if (ADD_TYPE == STUB_TYPE_BMZD &&
            getADDQX(5) == false) {
          JOptionPane.showMessageDialog(ReportView, res.getString("String_565"), res.getString("String_566"),
                                        JOptionPane.WARNING_MESSAGE);
          return false;
        }
        if (ADD_TYPE == STUB_TYPE_CBZD &&
            getADDQX(5) == false) {
          JOptionPane.showMessageDialog(ReportView, res.getString("String_567"), res.getString("String_568"),
                                        JOptionPane.WARNING_MESSAGE);
          return false;
        }
      }
      else {
        if (getBBQX(5) == false) {
          JOptionPane.showMessageDialog(ReportView, res.getString("String_569"), res.getString("String_570"),
                                        JOptionPane.WARNING_MESSAGE);
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
    // 如果是外部公式返加空,如果是内部公式,返加内部公式,如果不是公式,返回原值
    public String setExternalF1(int Row, int Col, String F1String,int phyRow,int phyCol) throws Exception {
        if (ReportView.getSheet() != ReportView.getNumSheets() - 1) {
            setModified();
            return F1String;
        }
//        if(getGSFC())   return null;

        String Text;

        JUnitInfo UI = null;
        if (F1String.startsWith("=") || StringUtil.isStartWith(F1String, ReportUtil.SYMBOL_NAMES))  {
          if (!checkQX()) return null; // 检查权限
          if (getGSFC())  return null; // 检查是否公式封存
        }
        int RowStatus = JBdhUtils.checkPhyRowStatus(this,phyRow);
        if ( RowStatus == 3 ) {// 新变动行
          UI = JBdhInfo.getBDUnitInfo(this,Row-1,Col-1,phyRow,phyCol);
        } else {
          UI = JUnitInfo.GetUnitInfoByHL(Row, Col, 1, this);
        }
        if (getDataStatus() == this.MODEL_STATUS_JSGS || getDataStatus() == this.MODEL_STATUS_DATA) { //计算公式
            if (F1String.startsWith("=")) {
              Text = F1String.substring(1, F1String.length());

              Text = Text.toUpperCase();
//	      //条件值不进行大写转换
//	      //add by gengeng 2007.2.10
//	      Text = JReportPubFunc.formulaConditionNotUpper(Text);

                // 如果是外部公式
                if (ReportView.FunctionManager.isExternalF1(Text) == true) {
//                    UI = JUnitInfo.GetUnitInfoByHL(Row, Col, 1, this);
                    //add by fsz2004。5。18
                    //原先是根据选择的范围决定公式作用范围
                    //现在改为根据左上角的范围决定
                    //在公式向导的情况下还是用原来的方式
                    //if(!ReportView.BookTextField.isWizard){

                    //String cell = (String) ReportView.BookComboBox.getSelectedItem();
                    String cell = ReportView.BookTextField.isCellRange;
                    cell = jreportfunction.pub.JReportPubFunc.cellRangeToRowCol(cell);
                    if (cell.equals("")) {
                        throw new Exception("单元格范围错误！");
                    }
                    String elem[];
                    elem = cell.split("#");
                    int row = Integer.valueOf(elem[2]).intValue() -
                        Integer.valueOf(elem[0]).intValue();
                    int col = Integer.valueOf(elem[3]).intValue() -
                        Integer.valueOf(elem[1]).intValue();
                    UI.setUIGSX(Text, row, col, this);
                    //}else
                    //UI.setUIGSX(Text,ReportView.getSelEndRow() - ReportView.getSelStartRow(),ReportView.getSelEndCol() -ReportView.getSelStartCol(), this);
                    //end

                    //return null
                    //modi by fsz
                    if (getDataStatus() == this.MODEL_STATUS_JSGS) {
                        return res.getString("String_575");
                    }
                    else {
                        return null;
                    }
                    //end
                }
                else { // 如果是内部公式,返回原值
                    ChangeUI(Row, Col, 1,UI,RowStatus);
                    return F1String;
                }
            }
            else {
                ChangeUIandData(Row, Col, F1String,UI,RowStatus); //add by fsz 2004.5.20
            }
        }
        else if (getDataStatus() == MODEL_STATUS_JYGS) { //校验公式
            Text = F1String;
            if (StringUtil.isStartWith(Text, ReportUtil.SYMBOL_NAMES)) {
                if (ReportView.FunctionManager.isExternalF1(Text) == true) {
//                    UI = JUnitInfo.GetUnitInfoByHL(Row, Col, 1, this);
                    //add by fsz2004。5。18
                    //原先是根据选择的范围决定公式作用范围
                    //现在改为根据左上角的范围决定
                    //String cell=(String)ReportView.BookComboBox.getSelectedItem();
                    String cell = ReportView.BookTextField.isCellRange;
                    cell = jreportfunction.pub.JReportPubFunc.cellRangeToRowCol(cell);
                    if (cell.equals("")) {
                        throw new Exception("单元格范围错误！");
                    }
                    String elem[];
                    elem = cell.split("#");
                    int row = Integer.valueOf(elem[2]).intValue() - Integer.valueOf(elem[0]).intValue();
                    int col = Integer.valueOf(elem[3]).intValue() - Integer.valueOf(elem[1]).intValue();
                    UI.setUIGSX(Text, row, col, this);

                    // UI.setUIGSX(Text,ReportView.getSelEndRow() - ReportView.getSelStartRow(),ReportView.getSelEndCol() -ReportView.getSelStartCol(), this);
                    //return null; modi by fsz 2004.5.09
                    return res.getString("String_578");
                    //end
                }
                else { // 如果是内部公式,返回原值
                    ChangeUI(Row, Col, 1,UI,RowStatus);
                    return F1String;
                }
            }
            else {
                ChangeUIandData(Row, Col, F1String,UI,RowStatus); //add by fsz 2004.5.20
                return F1String;
            }
        } else if (getDataStatus() == MODEL_STATUS_BBPZ) { //报表附注
            Text = F1String;
            if (Text.trim().equals("<同上>") || Text.trim().equals("<同左>")) {
                return Text;
            }
            String cell = ReportView.BookTextField.isCellRange;
            cell = JReportPubFunc.cellRangeToRowCol(cell);
            if (cell.equals("")) {
                throw new Exception("单元格范围错误！");
            }
            String[] elem = cell.split("#");
            int row = Integer.valueOf(elem[2]).intValue() - Integer.valueOf(elem[0]).intValue();
            int col = Integer.valueOf(elem[3]).intValue() - Integer.valueOf(elem[1]).intValue();
            UI.setUIGSX(Text, row, col, this);
            return "<附注>";
        } else if (getDataStatus() == MODEL_STATUS_TZGS) { //调整公式
          if (F1String.startsWith("=")) {
              Text = F1String.substring(1, F1String.length());
              Text = Text.toUpperCase();
              // 调整公式手工输入的应该只有一种,即excel公式，应在这里进行判断，不符合规则的给出提示
              if (ReportView.FunctionManager.isExternalF1(Text) == true) {
                  String cell = ReportView.BookTextField.isCellRange;
                  cell = jreportfunction.pub.JReportPubFunc.cellRangeToRowCol(cell);
                  if (cell.equals("")) {
                      throw new Exception("单元格范围错误！");
                  }
                  String elem[];
                  elem = cell.split("#");
                  int row = Integer.valueOf(elem[2]).intValue() -
                      Integer.valueOf(elem[0]).intValue();
                  int col = Integer.valueOf(elem[3]).intValue() -
                      Integer.valueOf(elem[1]).intValue();
                  UI.setUIGSX(Text, row, col, this);
                  return res.getString("String_579");
              } else { // 如果是内部公式,返回原值
                  ChangeUI(Row, Col, 1,UI,RowStatus);
                  return F1String;
              }
          }
        }
        else if (getDataStatus() == this.MODEL_STATUS_ZSGS ) { //折算公式
            if (F1String.startsWith("=")) {
              Text = F1String.substring(1, F1String.length());

              Text = Text.toUpperCase();
//	      //条件值不进行大写转换
//	      //add by gengeng 2007.2.10
//	      Text = JReportPubFunc.formulaConditionNotUpper(Text);

                // 如果是外部公式
                if (ReportView.FunctionManager.isExternalF1(Text) == true) {
//                    UI = JUnitInfo.GetUnitInfoByHL(Row, Col, 1, this);
                    //add by fsz2004。5。18
                    //原先是根据选择的范围决定公式作用范围
                    //现在改为根据左上角的范围决定
                    //在公式向导的情况下还是用原来的方式
                    //if(!ReportView.BookTextField.isWizard){

                    //String cell = (String) ReportView.BookComboBox.getSelectedItem();
                    String cell = ReportView.BookTextField.isCellRange;
                    cell = jreportfunction.pub.JReportPubFunc.cellRangeToRowCol(cell);
                    if (cell.equals("")) {
                        throw new Exception("单元格范围错误！");
                    }
                    String elem[];
                    elem = cell.split("#");
                    int row = Integer.valueOf(elem[2]).intValue() -
                        Integer.valueOf(elem[0]).intValue();
                    int col = Integer.valueOf(elem[3]).intValue() -
                        Integer.valueOf(elem[1]).intValue();
                    UI.setUIZSGSX(Text, row, col, this);
                    //}else
                    //UI.setUIGSX(Text,ReportView.getSelEndRow() - ReportView.getSelStartRow(),ReportView.getSelEndCol() -ReportView.getSelStartCol(), this);
                    //end

                    //return null
                    //modi by fsz
                    if (getDataStatus() == this.MODEL_STATUS_ZSGS) {
                        return "<折算>";
                    }
                    else {
                        return null;
                    }
                    //end
                }
                else { // 如果是内部公式,返回原值
                    ChangeUI(Row, Col, 1,UI,RowStatus);
                    return F1String;
                }
            }
//            else {
//                ChangeUIandData(Row, Col, F1String,UI,RowStatus); //add by fsz 2004.5.20
//            }
        }
        ChangeUIandData(Row, Col, F1String,UI,RowStatus); //modi by fsz 2004.5.20
        return F1String;
//未修改之前的代码
//    if ( F1String.startsWith("=") ) {
//      Text = F1String.substring(1,F1String.length()).toUpperCase();
//        // 如果是外部公式
//        if ( ReportView.FunctionManager.isExternalF1(Text) == true ) {
//          UI = JUnitInfo.GetUnitInfoByHL(Row,Col,1,this);
//          UI.setUIGSX(Text,ReportView.getSelEndRow()-ReportView.getSelStartRow(),ReportView.getSelEndCol()-ReportView.getSelStartCol(),this);
//          return null;
//        } else { // 如果是内部公式,返回原值
//          ChangeUI(Row,Col,1);
//          return F1String;
//        }
//    } else {
//      ChangeUI(Row,Col,1);
//      return F1String;
//    }
//    return F1String;
    }

    /**
     * modified by hufeng 2008.3.25
     * @param Row int
     * @param Col int
     * @param Op int
     * @param UI JUnitInfo
     * @param RowStatus int
     */
    private void ChangeUI(int Row, int Col, int Op,JUnitInfo UI,int RowStatus) {
        JUnitInfo AddUI;

        try {
            if (ADD_BH != null && RowStatus != 3) {
                AddUI = JAddUnitInfo.GetUnitInfoByHL(Row, Col, 1, this);
                AddUI.setModified();
            }
            else {
                UI.setModified();
            }
            this.setModified();
        }
        catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    /**
     * 不但改变UI的状态，而且直接将UI里面的DYZD_DATA和DYZD_SJ更新
     * @param Row int
     * @param Col int
     * @param F1String String
     * @param UI JUnitInfo
     * @param RowStatus int
     */
    private void ChangeUIandData(int Row, int Col, String F1String,JUnitInfo UI,int RowStatus) {
        JUnitInfo AddUI;
        if (F1String == null) {
            F1String = "";
        }
        if (F1String.startsWith("=")) {
            return;
        }
        double dysj = 0;
        try {
            dysj = Double.valueOf(F1String).doubleValue();
        }
        catch (Exception e) {
            dysj = 0;
        }
        if (ADD_BH != null && RowStatus != 3 ) { //不在新变动区里
        	AddUI = JAddUnitInfo.GetUnitInfoByHL(Row, Col, 1, this);
            if(TZZD_BH!=null && TZZD_BH.trim().length()>0){
                AddUI.TZZD_DATA = dysj;
                AddUI.TZZD_SJ = F1String;
                AddUI.setTZModified();
            }else {
                AddUI.DYZD_DATA = dysj;
                AddUI.DYZD_SJ = F1String;
            }
            AddUI.setModified();
        }
        else {
//            UI = JUnitInfo.GetUnitInfoByHL(Row, Col, 1, this);
            if(UI != null){
              if(TZZD_BH != null && TZZD_BH.trim().length()>0){
                UI.TZZD_DATA = dysj;
                UI.TZZD_SJ = F1String;
                UI.setTZModified();
              }else{
                UI.DYZD_DATA = dysj;
                UI.DYZD_SJ = F1String;
              }
              UI.setModified();
            }
        }
        this.setModified();
    }

//end

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public String setBookTextFieldText(int Row, int Col) {
        JUnitInfo UI;
        String Text = null;
        String T1;
        try {
            UI = JUnitInfo.GetUnitInfoByHL(Row, Col, this);
            if (UI != null) {
                Text = UI.getUIGSX(this);
                if (Text != null) {
                    Text = "=" + Text;
                    T1 = ReportView.formatRCNr(UI.RowInfo.HZD_ZB - 1, UI.ColInfo.LZD_ZB - 1, false);
                    if (UI.DYZD_HOFFSET != 0 || UI.DYZD_LOFFSET != 0) {
                        T1 = T1 + " X " + ReportView.formatRCNr(UI.RowInfo.HZD_ZB - 1 + UI.DYZD_HOFFSET, UI.ColInfo.LZD_ZB - 1 + UI.DYZD_LOFFSET, false);
                    }
                    ReportView.setToolTipText(T1);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Text;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
//    public void ReportModelAttrib() {
//        JFrameDialog Dlg;
//        try {
//            Dlg = new JReportAttribDlg(JActiveDComDM.MainApplication.MainWindow,"",true);
////            Dlg = (JFrameDialog) Class.forName("jreport.jdof.classes.DOFReportObject.ReportWindow.Dlg.JReportAttribDlg").newInstance();
//            Dlg.setCustomObject(ReportView);
//            Dlg.pack();
//            Dlg.CenterWindow();
//            Dlg.setModal(true);
////            Dlg.setResizable(false);
//            Dlg.Show();
//            if (Dlg.Result == Dlg.RESULT_OK) {
//                if (Dlg.PO != null) {
//                    ReportAttribFromPO(Dlg.PO);
//                    this.setModified();
//                }
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ReportAttribFromPO(JParamObject PO) {
//    BBZD_BH    = PO.GetValueByParamName("BBZD_BH");
        BBZD_MC = PO.GetValueByParamName("BBZD_MC");
        BBZD_SBXZ = PO.GetValueByParamName("BBZD_SBXZ");
        BBZD_TITLE = PO.GetIntByParamName("BBZD_TITLE");
        BBZD_HEAD = PO.GetIntByParamName("BBZD_HEAD");
        BBZD_BT = PO.GetIntByParamName("BBZD_BT");
        BBZD_HS = BBZD_TITLE + BBZD_HEAD + BBZD_BT + PO.GetIntByParamName("BBZD_BW");
        BBZD_LS = PO.GetIntByParamName("BBZD_LS");
        BBZD_SBBH = PO.GetValueByParamName("BBZD_SBBH");
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int CalcReport(JContextObject CO) {
        JParamObject PO = JParamObject.Create();
        PO.SetIntByParamName("CALC_REPORT_COUNT", 1);
        if (ADD_BH != null) {
            PO.SetValueByParamName("ADD_BH", ADD_BH);
            PO.SetValueByParamName("ADD_MC", ADD_MC);
        }
        PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
        PO.SetValueByParamName("BBZD_BH" + String.valueOf(0), BBZD_BH);
        PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
        PO.SetValueByParamName("TZZD_BH", TZZD_BH);
        PO.SetValueByParamName("TZZD_MC", TZZD_DW);//LK 调整字典名称
        /**
         * 单个单元格计算时不检查公式是否已经审核
         * 因为要考虑公式定义时的测试
         * 整表计算时要检查是否有未审批的公式
         */
        if (CO.DataObject != null) { //计算所选择的报表
            int[] para = (int[]) CO.DataObject;
            PO.SetIntByParamName("CALC_SEL", 1);
            PO.SetIntByParamName("R1", para[0]);
            PO.SetIntByParamName("R2", para[1]);
            PO.SetIntByParamName("C1", para[2]);
            PO.SetIntByParamName("C2", para[3]);
            PO.SetIntByParamName("BDH_HZB", para[4]);
        }
        else {
            if(!isCanCalc(BBZD_BH,PO)){
                return 0;
            }
            PO.SetIntByParamName("CALC_SEL", 0);
        }
		//获取报表计算过滤条件，传到后台
//		String DWZD_CODE = JBOFReportPropertyObject.getDwZrzx(PO,null,null,null );
        try {
            BIZMetaData bmd = (BIZMetaData) MetaDataManager.getBIZMetaDataManager().getMetaData("ACRPTModel");
            String codeWhere = bmd.getSYS_MDL_VAL(" ",ADD_BH,"");
            
            if( codeWhere.trim().length()>0 ) {
                PO.SetValueByParamName("ZRZX"+ADD_BH, codeWhere);
            }
 //           JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,ADD_BH+": "+codeWhere);
            
        } catch ( Exception ex){
        	ex.printStackTrace();
        }
        
        //end
        JResponseObject RO = (JResponseObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "CalcReportNoThread", PO, null, null, CO.Application);
        if (RO != null) {
            jreportfunction.pub.JReportPubFunc.copytoClipboard(RO.ErrorString);
        }
        return 0;
    }

	//------------------------------------------------------------------------------------------------
	//描述:
	//设计: Skyline(2001.12.29)
	//实现: Skyline
	//修改:
	//------------------------------------------------------------------------------------------------
	public int DealReport(JContextObject CO) {
		JParamObject PO = JParamObject.Create();
		PO.SetIntByParamName("CALC_REPORT_COUNT", 1);
		if (ADD_BH != null) {
			PO.SetValueByParamName("ADD_BH", ADD_BH);
			PO.SetValueByParamName("ADD_MC", ADD_MC);
		}
		PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
		PO.SetValueByParamName("BBZD_BH" + String.valueOf(0), BBZD_BH);
		PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
		PO.SetValueByParamName("TZZD_ORDE", TZZD_ORDE);
		PO.SetValueByParamName("TZZD_MC", TZZD_MC);//LK 调整字典名称

		if(!isCanCalc(BBZD_BH,PO)){
			return 0;
		}
		PO.SetIntByParamName("CALC_SEL", 0);
		//end
		//获取报表计算过滤条件，传到后台
//		String DWZD_CODE = JBOFReportPropertyObject.getDwZrzx(PO,null,null,null );
        try {
            BIZMetaData bmd = (BIZMetaData) MetaDataManager.getBIZMetaDataManager().getMetaData("ACRPTModel");
            String codeWhere = bmd.getSYS_MDL_VAL(" ",ADD_BH,"");
            if( codeWhere.trim().length()>0 ) {
                PO.SetValueByParamName("ZRZX"+ADD_BH, codeWhere);
            }
        } catch ( Exception ex){
        	ex.printStackTrace();
        }
		
		JResponseObject RO = (JResponseObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "DealReportNoThread", PO, null, null, CO.Application);
		if (RO != null) {
			jreportfunction.pub.JReportPubFunc.copytoClipboard(RO.ErrorString);
		}
		return 0;
	}


    //------------------------------------------------------------------------------------------------
      //描述:
      //设计: Skyline(2001.12.29)
      //实现: Skyline
      //修改:
      //------------------------------------------------------------------------------------------------
      public int AdjustReport(JContextObject CO) {
        JParamObject PO = JParamObject.Create();
          PO.SetIntByParamName("CALC_REPORT_COUNT", 1);
          if (ADD_BH != null) {
              PO.SetValueByParamName("ADD_BH", ADD_BH);
              PO.SetValueByParamName("ADD_MC", ADD_MC);
          }
          PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
          //PO.SetValueByParamName("BBZD_BH", BBZD_BH);
          PO.SetValueByParamName("BBZD_BH" + String.valueOf(0), BBZD_BH);
          PO.SetIntByParamName("BBZD_LS",BBZD_LS);
          PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
          PO.SetValueByParamName("TZZD_ORDE", TZZD_ORDE);
          PO.SetValueByParamName("TZZD_BH", TZZD_BH);//MF 调整字典编号
          PO.SetValueByParamName("TZZD_MC", TZZD_MC);//LK 调整字典名称
          PO.SetValueByParamName("TZZD_FH", TZZD_FH);//MF 调整字典符号
          PO.SetValueByParamName("TZZD_XS", TZZD_XS);//MF 调整字典系数
          PO.SetValueByParamName("TZZD_JD", TZZD_JD);//MF 调整字典精度
          PO.SetValueByParamName("TZZD_DW", TZZD_DW);//MF 调整字典单位

//            // 先关闭以前的变动区
//            try {
//              JBdhInfo.deleBdhs(this);
//            } catch ( Exception e ) {
//              e.printStackTrace();
//            }

            try {
              GetReportTzgsAtt();
              GetReportTzgsAddAtt();
              GetReportBdhTzgsAddAtt();
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          return 0;
    }

    /**
     * 报表计算时需要检查
     * 报表是否封存
     * 报表是否复核
     * 是否有未审核的计算公式
     */
    private boolean isCanCalc(String BBZD_BH,JParamObject PO){
        Vector bbList = new Vector();
        bbList.add(BBZD_BH);
        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "isCanCalc", PO, bbList);
        if(RO == null){
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, "Remote Invoke Error！");
            return false;
        }
        if (RO.ErrorCode == -1) {
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, res.getString("String_30") + RO.ErrorString);
            return false;
        }
        // 加入错误信息
        String mess = RO.ErrorString;
        if(!mess.equals("")){
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,mess);
            return false;
        }
        return true;
    }
    /**
     * 报表计算时需要检查
     * 报表是否封存
     * 报表是否复核
     * 是否有未审核的计算公式
     */
    private boolean isCanCount(String BBZD_BH,JParamObject PO){
        Vector bbList = new Vector();
        bbList.add(BBZD_BH);
        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "isCanCount", PO, bbList);
        if(RO == null){
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, "Remote Invoke Error！");
            return false;
        }
        if (RO.ErrorCode == -1) {
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow, res.getString("String_30") + RO.ErrorString);
            return false;
        }
        // 加入错误信息
        String mess = RO.ErrorString;
        if(!mess.equals("")){
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,mess);
            return false;
        }
        return true;
    }    
//     private void OpenReportAdjustObject(JConnection conn, JParamObject PO) {
//          Statement stmt = null, st = null;
//          JReportDataObject DataObject = null;
//          Element ReportElement;
//          int EXPORT = 0;
//          try {
//              // 新建一个XML的DataObject对象
//              EXPORT = PO.GetIntByParamName("EXPORT");
//              DataObject = new JReportDataObject();
//              ReportElement = DataObject.AddChildElement(null, "Report");
//              // 创建一个Statement语句
//              stmt = conn.createStatement();
//              st = conn.createStatement();
//              if (conn == null || stmt == null || st == null) {
//                  String xml = PO.GetRootXMLString();
//              }
//              // 调用获取报表属性的过程
////              OpenReportSheetAtt(stmt, DataObject, PO, ReportElement);
//              // 获取行字典
//  //            OpenReportHZDAtt(stmt, DataObject, PO, ReportElement);
//              // 获取列字典
//    //          OpenReportLZDAtt(stmt, DataObject, PO, ReportElement);
//              // 不是转出，所以不需要代坐标
//              if (EXPORT == 0 || EXPORT == -1) {
//                  // 获取变动行
//            //      OpenReportBDHAtt(stmt, DataObject, PO, ReportElement);
//                  // 获取变动列
//              //    OpenReportBDLAtt(stmt, DataObject, PO, ReportElement);
//                  // 获取单元字典
//               //   OpenReportDYZDAtt(stmt, DataObject, PO, ReportElement);
//                  // 获取校验公式
//                //  OpenReportJYGSAtt(stmt, DataObject, PO, ReportElement);
//                  // 获取调整公式
//                //  OpenReportTZGSAtt(stmt, DataObject, PO, ReportElement);
//                  /**
//                   * 获取附注信息
//                   * add by gengeng 2008-8-28
//                   */
//                //  OpenReportComment(stmt, DataObject, PO, ReportElement);
//
//                  // 获取附加单元信息(可能是单位数或是汇总数据和责任中心数据)
//                 // OpenReportAddInfoAtt(stmt, DataObject, PO, ReportElement);
//                  if(TZZD_MC!=null && TZZD_MC.trim().length()>0){
//                    // 获取附加单元调整信息(可能是单位数或是汇总数据和责任中心调整数据)
//                  // OpenReportAddInfoAttTZ(stmt, DataObject, PO, ReportElement);
//                  }
//
//                  // 获取单位的私有计算公式
//                //  OpenReportAddJsgsAtt(stmt, DataObject, PO, ReportElement);
//                  // 获取单位的私有校验公式
//              //    OpenReportAddJygsAtt(stmt, DataObject, PO, ReportElement);
//              } else {
//                  // 获取变动行
//              //    OpenReportBDHAttZB(conn, stmt, st, DataObject, PO, ReportElement);
//                  // 获取变动列
//               //   OpenReportBDLAttZB(stmt, DataObject, PO, ReportElement);
//                  // 获取单元字典
//               //   OpenReportDYZDAttZB(stmt, DataObject, PO, ReportElement, conn);
//                  // 获取校验公式
//              //    OpenReportJYGSAttZB(stmt, DataObject, PO, ReportElement, conn);
//                  // 获取附加单元信息(可能是单位数或是汇总数据和责任中心数据)
//                //  OpenReportAddInfoAttZB(stmt, DataObject, PO, ReportElement);
//                  if(TZZD_MC!=null && TZZD_MC.trim().length()>0){
//                    // 获取附加单元调整信息(可能是单位数或是汇总数据和责任中心调整数据)
//                //    OpenReportAddInfoAttTZZB(stmt, DataObject, PO, ReportElement);
//                  }
//
//                  // 获取单位的私有计算公式
//               //   OpenReportAddJsgsAttZB(stmt, DataObject, PO, ReportElement, conn);
//                  // 获取单位的私有校验公式
//               //   OpenReportAddJygsAttZB(stmt, DataObject, PO, ReportElement, conn);
//               //   OpenReportFmshgs(conn, stmt, DataObject, PO, ReportElement);
//               //   OpenReportShgs(conn, stmt, DataObject, PO, ReportElement);
//                  // 获取枚举字典
//                 // OpenReportMjzd(conn, stmt, DataObject, PO, ReportElement);
//              }
//          } catch (Exception e) {
//              DataObject = null;
//              e.printStackTrace();
//          } finally {
//              conn.BackStatement(stmt, null);
//              conn.BackStatement(st, null);
//          }
//    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int CheckReport(JContextObject CO,JParamObject PO) {
     //   JParamObject PO = JParamObject.Create();
        PO.SetIntByParamName("CALC_REPORT_COUNT", 1);
        if (ADD_BH != null) {
            PO.SetValueByParamName("ADD_BH", ADD_BH);
            PO.SetValueByParamName("ADD_MC", ADD_MC);
        }
        if(TZZD_BH !=null){
          PO.SetValueByParamName("TZZD_BH", TZZD_BH);
          PO.SetValueByParamName("TZZD_MC", TZZD_MC);
          PO.SetValueByParamName("TZZD_FH", TZZD_FH);
          PO.SetValueByParamName("TZZD_XS", TZZD_XS);
          PO.SetValueByParamName("TZZD_JD", TZZD_JD);
          PO.SetValueByParamName("TZZD_DW", TZZD_DW);
        }
        PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
        PO.SetValueByParamName("BBZD_BH" + String.valueOf(0), BBZD_BH);
        PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
        JResponseObject RO = (JResponseObject) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "CheckReportNoThread", PO, null, null, CO.Application);
        if (RO != null) {
            return RO.ErrorCode;
        }
        else {
            return -1;
        }
    }
//    //add by fsz 2004.10.29
//    public int CountReport(JContextObject CO) {
//        JParamObject PO = JParamObject.Create();
//        int iAddType = this.ADD_TYPE ;
//        //if (ADD_TYPE!=this.STUB_TYPE_LBZD)return 0;
//        PO.SetIntByParamName("ADD_COUNT", 1);
//        PO.SetValueByParamName("ADD_BH0", ADD_BH);
//        PO.SetValueByParamName("ADD_BH", ADD_BH);
//        PO.SetValueByParamName("ADD_MC0", ADD_MC);
//        PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
//        PO.SetIntByParamName("BBZD_COUNT", 1);
//        PO.SetValueByParamName("BBZD_BH0" , BBZD_BH);
//        PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
//        if(!isCanCount(BBZD_BH,PO)){
//            return 0;
//        }
//
//        
//        /**
//         * 加入汇总选项
//         * modified by hufeng 2006.1.19
//         */
//        JReportCountOptionDlg pDlg = new JReportCountOptionDlg(JActiveDComDM.MainApplication.MainWindow, res.getString("String_623"), true);
//        pDlg.setAddType(iAddType);
//        pDlg.CenterWindow();
//        pDlg.setVisible(true);
//        if(pDlg.Result != pDlg.RESULT_OK){
//          return -1;
//        }
//        String hzlb = pDlg.getHzlb();
//        //ADDBY MF
//        String auto = pDlg.getAutoCalc();
//        PO.SetValueByParamName("hzlb", hzlb);
//        PO.SetValueByParamName("autoCalc", auto);
//        //
//        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "CountReportNoThread", PO);
//        if (RO != null) {
//            return RO.ErrorCode;
//        }
//        else {
//            return -1;
//        }
//    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int CheckSetReport(JContextObject CO) {
        JParamObject PO = JParamObject.Create();
        if (ADD_BH != null) {
            PO.SetValueByParamName("ADD_BH" + String.valueOf(0), ADD_BH);
            PO.SetValueByParamName("ADD_MC" + String.valueOf(0), ADD_MC);
            PO.SetValueByParamName("ADD_COUNT", String.valueOf(1));
        }
        PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
        PO.SetValueByParamName("BBZD_BH" + String.valueOf(0), BBZD_BH);
        PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
        PO.SetValueByParamName("BBZD_COUNT", String.valueOf(1));
        PO.SetValueByParamName("TZZD_BH",TZZD_BH);
        JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "ResultReportForDate", PO, null, null, CO.Application);
        return 0;
    }
    //add by lqk 审核报告
    public int CheckSetReport1(JContextObject CO) {
        JParamObject PO = JParamObject.Create();
        if (ADD_BH != null) {
            PO.SetValueByParamName("ADD_BH" + String.valueOf(0), ADD_BH);
            PO.SetValueByParamName("ADD_MC" + String.valueOf(0), ADD_MC);
            PO.SetValueByParamName("ADD_COUNT", String.valueOf(1));
        }
        PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
        PO.SetValueByParamName("BBZD_BH" + String.valueOf(0), BBZD_BH);
        PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
        PO.SetValueByParamName("BBZD_COUNT", String.valueOf(1));
        PO.SetValueByParamName("TZZD_BH",TZZD_BH);
        PO.SetValueByParamName("ISsh","1");
        JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "ResultReportForDate", PO, null, null, CO.Application);
        return 0;
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int RefreshData(JContextObject CO) {
        JReportObjectEntity ROE;
        JParamObject PO = JParamObject.Create();
        if (ADD_BH != null) {
            PO.SetValueByParamName("ADD_BH", ADD_BH);
            PO.SetValueByParamName("ADD_MC", ADD_MC);
        }
        PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
        PO.SetIntByParamName("BBZD_LS",BBZD_LS);
        PO.SetValueByParamName("BBZD_BH", BBZD_BH);
        PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
        ROE = (JReportObjectEntity) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "OpenReportData", PO, null, null, CO.Application);
        if (ROE != null) {
          // 先关闭以前的变动区
          try {
            JBdhInfo.deleBdhs(this);
          } catch ( Exception e ) {
            e.printStackTrace();
          }

            JReportDataObject RDO;
            try {
                RDO = new JReportDataObject(ROE);
                GetReportDataModel(RDO);
                ROE.DataObject = null;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void ShowWizard(int x, int y, Frame frame, JReportDataModel RM, Object RB) {
        if (WizardObject == null) {
            InitWizardObject();
        }
        WizardObject.ShowWizard(x, y, frame, RM, RB);
    }

    public void HideWizard() {
        if (WizardObject == null) {
            return;
        }
        WizardObject.HideWizard();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void InitWizardObject() {
        WizardObject = new JFunctionWizardObject();
        // 初始化
        WizardObject.InitWizardObject();
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void setWizardObject(Object ReportBook) {
        if (WizardObject != null) {
            WizardObject.setReportModel(this, ReportBook);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public boolean selectionChanged(Object SelectChangeObject) {
        int SRow, SCol, ERow, ECol;int phySRow,phySCol,phyERow,phyECol;
        try {
            SRow = ReportView.getSelStartRow() + 1;
            SCol = ReportView.getSelStartCol() + 1;
            ERow = ReportView.getSelEndRow() + 1;
            ECol = ReportView.getSelEndCol() + 1;
            //
            phySRow = ReportView.getPhySelStartRow();
            phySCol = ReportView.getSelStartCol();
            phyERow = ReportView.getPhySelEndRow();
            phyECol = ReportView.getSelEndCol();
            if (!ReportView.BookTextField.isWizard) {
                PorcessSelectionChanged(SRow, SCol, ERow, ECol,phySRow,phySCol,phyERow,phyECol);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void PorcessSelectionChanged(int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {
        JUnitInfo UI = null;int RowStatus=0;
        // 如果是最后一个Sheet才进行处理
        if (ReportView.getSheet() == ReportView.getNumSheets() - 1) {
          // 获取当前行的状态
          RowStatus = JBdhUtils.checkPhyRowStatus(this,phySRow);
          if ( RowStatus == 3 ) {// 新变动行
            UI = JBdhInfo.getUnitInfo(this,SRow-1,SCol-1,phySRow,phySCol);
          } else {// 正常行，旧版变动行，新版变动行主行
            UI = JUnitInfo.GetUnitInfoByHL(SRow, SCol, 0, this);
          }
        }
        if (UI == null) { // 如果不存
            setBookComboBoxText(SRow, SCol, ERow, ECol);
            setBookTextFieldNoUI(SRow, SCol,phySRow,phySCol);
        }
        else {
            //modi by fsz 2004.5.18 原来如果存在公式，不管选择怎么改变
            //始终显示公式的作用范围，改为如果只选择单位格
            //显示此公式的作用范围，否则显示实际选择的范围
            ProcessSelectionChangedUI(UI, SRow, SCol, ERow, ECol,phySRow,phySCol,phyERow,phyECol);
            if (SRow == ERow && SCol == ECol) {
                ; //
            }
            else {
                setBookComboBoxText(SRow, SCol, ERow, ECol);
            }
            //end
        }
        //在数据状态下显示所选区域内的数据的平均值和和
        //add by gengeng 2008-2-22
        ProcessSelectionChangedData(UI, SRow, SCol, ERow, ECol,phySRow,phySCol,phyERow,phyECol);
    }

    void ProcessSelectionChangedData(JUnitInfo UI, int SRow, int SCol, int ERow, int ECol, int phySRow, int phySCol, int phyERow, int phyECol) {
        int Status = getDataStatus();
        if (Status == MODEL_STATUS_DATA)
            ProcessSCUIDATA(UI, SRow, SCol, ERow, ECol, phySRow, phySCol, phyERow, phyECol);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessSelectionChangedUI(JUnitInfo UI, int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {
        int Status = this.getDataStatus();
        switch (Status) {
            case MODEL_STATUS_DATA:
            case MODEL_STATUS_JSGS:
                ProcessSCUIJSGS(UI, SRow, SCol, ERow, ECol,phySRow,phySCol,phyERow,phyECol);
                break;
            case MODEL_STATUS_JYGS:
                ProcessSCUIJYGS(UI, SRow, SCol, ERow, ECol,phySRow,phySCol,phyERow,phyECol);
                break;
            case MODEL_STATUS_BBPZ:
                ProcessSCUIComment(UI, SRow, SCol, ERow, ECol,phySRow,phySCol,phyERow,phyECol);
                break;
            case MODEL_STATUS_ZSGS:
            	ProcessSCUIZSGS(UI, SRow, SCol, ERow, ECol,phySRow,phySCol,phyERow,phyECol);
                break;
        }
    }

    /**
     * 数据状态下对所选区域进行求和、求平均等计算
     */
    void ProcessSCUIDATA(JUnitInfo UI, int SRow, int SCol, int ERow, int ECol, int phySRow, int phySCol, int phyERow, int phyECol) {
        /**
         * 加入兼容处理
         * 解决行列大于物理行列的问题：使用最大行列数，应对折页的情况
         * modified by hufeng 2008.3.4
         */
        int maxRow = ReportView.getMaxRow() + 1;
        int maxCol = ReportView.getMaxCol() + 1;
        if (ERow > maxRow || ECol > maxCol) {
            return;
        }

        ArrayList areaList = getSelectedAreas();
        cellCountList.clear();
        /**
         * 对所有选择的单元格做一下处理，判断该单元格的数据是不是数字，如果是则参与计算
         */
        String cellCoor, cellStrs;
        String row, col;
        ArrayList dataList = new ArrayList();
        for (Iterator it = areaList.iterator(); it.hasNext(); ) {
            cellCoor = (String) it.next();
            cellStrs = JReportPubFunc.cellToRowCol(cellCoor);
            //如果在本地行动中已经取过值
            if (cellCountList.contains(cellStrs))
                continue;
            row = cellStrs.substring(0, cellStrs.indexOf("#"));
            col = cellStrs.substring(cellStrs.indexOf("#") + 1);
            try {
                int r = Integer.parseInt(row);
                int c = Integer.parseInt(col);

                String t = ReportView.getPhyEntry(r - 1, c - 1);
                double d = ReportView.getPhyNumber(r - 1, c - 1);
                if (JReportPubFunc.isNumString(t)) {
                    try {
                        d = Double.parseDouble(t);
                    } catch (NumberFormatException ex1) {
                        d = ReportView.getPhyNumber(r - 1, c - 1);
                    }
                } else {
                    if (t != null && t.indexOf("-") >= 0) {

                    } else {
                        d = 0.0;
                    }
                }

                if (NumberFunction.CmpDouble(0.0, d, 10) != 0) {
                    dataList.add(new BigDecimal(d));
                }
            } catch (F1Exception ex) {
                continue;
            }
            cellCountList.add(cellStrs);
        }
        BigDecimal     sum = new BigDecimal(0.0);
        double     average = 0.0;
        for (Iterator it = dataList.iterator(); it.hasNext(); ) {
            BigDecimal bd = (BigDecimal) it.next();
            sum = sum.add(bd);
        }
        int count = dataList.size();
        if (count == 0 || count == 1) {
            return;
        }
        average = sum.doubleValue()/count;

        DecimalFormat df = new DecimalFormat("###,###.######");

        String result = "和：" + df.format(sum.doubleValue()) + "    平均：" + df.format(average);
        ReportView.BookTextField.removeAllItems();
        ReportView.BookTextField.setText(result, true);
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessSCUIJSGS(JUnitInfo UI, int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {
        String GSX = null;
        try {
            GSX = UI.getUIGSX(this); // 获取公式
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // 如果公式项等空
        if (GSX == null) {
            setBookComboBoxText(SRow, SCol, ERow, ECol);
            setBookTextFieldNoUI(SRow, SCol,phySRow,phySCol);
        }
        else {
            ReportView.BookTextField.removeAllItems();
            setBookComboBoxText(UI.getHZD_ZB(), UI.getLZD_ZB(), UI.getHZD_ZB() + UI.DYZD_HOFFSET, UI.getLZD_ZB() + UI.DYZD_LOFFSET);
            ReportView.BookTextField.setText("=" + GSX, true);
        }
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessSCUIZSGS(JUnitInfo UI, int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {
        String GSX = null;
        try {
            GSX = UI.getUIZSGSX(this); // 获取公式
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // 如果公式项等空
        if (GSX == null) {
            setBookComboBoxText(SRow, SCol, ERow, ECol);
            setBookTextFieldNoUI(SRow, SCol,phySRow,phySCol);
        }
        else {
            ReportView.BookTextField.removeAllItems();
            setBookComboBoxText(UI.getHZD_ZB(), UI.getLZD_ZB(), UI.getHZD_ZB() + UI.DYZD_ZSHOFFSET, UI.getLZD_ZB() + UI.DYZD_ZSLOFFSET);
            ReportView.BookTextField.setText("=" + GSX, true);
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void ProcessSCUIJYGS(JUnitInfo UI, int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {
        if (UI.JygsList == null || UI.JygsList.size() == 0) {
            setBookComboBoxText(SRow, SCol, ERow, ECol);
            setBookTextFieldNoUI(SRow, SCol,phySRow,phySCol);
        }
        else {
            JJygsInfo JygsInfo;
            ReportView.BookTextField.setSelectedItem(res.getString("String_645"));
            ReportView.BookTextField.removeAllItems();
            for (int i = 0; i < UI.JygsList.size(); i++) {
                try {
                    if(UI.getUIJYGS(this, i)==null)return; // repleace by fsz 2004.11.25 UI.getUIJYGS(this, i);
                    JygsInfo = (JJygsInfo) UI.JygsList.get(i);
                    ReportView.BookTextField.insertItemAt(JygsInfo, i);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
//      setBookComboBoxText(SRow,SCol,ERow,ECol);
//      ReportView.BookTextField.setSelectedIndex(0);
            ReportView.BookTextField.setJYGS(UI, 0); //默认
        }
    }

    /**
     *
     * @param      UI JUnitInfo
     * @param    SRow int
     * @param    SCol int
     * @param    ERow int
     * @param    ECol int
     * @param phySRow int
     * @param phySCol int
     * @param phyERow int
     * @param phyECol int
     */
    void ProcessSCUIComment(JUnitInfo UI, int SRow, int SCol, int ERow, int ECol, int phySRow, int phySCol, int phyERow, int phyECol) {
        if (UI.CommentList == null || UI.CommentList.size() == 0) {
            setBookComboBoxText(SRow, SCol, ERow, ECol);
            setBookTextFieldNoUI(SRow, SCol, phySRow, phySCol);
        } else {
            JCommentInfo commentInfo;
            ReportView.BookTextField.setSelectedItem(res.getString("String_645"));
            ReportView.BookTextField.removeAllItems();
            for (int i = 0; i < UI.CommentList.size(); i++) {
                try {
                    commentInfo = (JCommentInfo) UI.CommentList.get(i);
                    ReportView.BookTextField.insertItemAt(commentInfo, i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ReportView.BookTextField.setComment(UI, 0); //默认
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void setBookTextFieldNoUI(int SRow, int SCol,int phySRow,int phySCol) {
        String Entry;
        try {
            ReportView.BookTextField.removeAllItems();
//            Entry = ReportView.getEntry(SRow - 1, SCol - 1);
            Entry = ReportView.getPhyEntry(phySRow, phySCol);
            ReportView.BookTextField.setText(Entry, false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    void setBookComboBoxText(int SRow, int SCol, int ERow, int ECol) {
        try {
            String T1, T2, Text;
            T1 = ReportView.formatRCNr(SRow - 1, SCol - 1, false);
            if (SRow == ERow && SCol == ECol) {
                Text = T1;
            }
            else {
                T2 = ReportView.formatRCNr(ERow - 1, ECol - 1, false);
                Text = T1 + ":" + T2;
            }
            ReportView.BookComboBox.setSelectedItem(Text);
            ReportView.BookTextField.isCellRange = Text;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    //------------------------------------------------------------------------------------------------
//    //描述:
//    //设计: Skyline(2001.12.29)
//    //实现: Skyline
//    //修改:
//    //------------------------------------------------------------------------------------------------
//    public void setBDQ() {
//        int SRow, SCol, ERow, ECol;
//        try {
//            SRow = ReportView.getSelStartRow();
//            SCol = ReportView.getSelStartCol();
//            ERow = ReportView.getSelEndRow();
//            ECol = ReportView.getSelEndCol();
//            if ( (ERow - SRow) > (ECol - SCol)) {
//                if (BdhInfoList.size() > 0) {
//                    JOptionPane.showMessageDialog(ReportView, res.getString("String_647"), res.getString("String_648"),
//                                                  JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//            }
//            else {
//                if (BdlInfoList.size() > 0) {
//                    JOptionPane.showMessageDialog(ReportView, res.getString("String_649"), res.getString("String_650"),
//                                                  JOptionPane.WARNING_MESSAGE);
//                    return;
//                }
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        JBDQSetupDlg Dlg;
//        try {
//            Dlg = new JBDQSetupDlg(this, JActiveDComDM.MainApplication.MainWindow, res.getString("String_651"), true);
////      Dlg.setReportModel(this);
//            if (Dlg.bok) {
//                Dlg.CenterWindow();
//                Dlg.setVisible(true);
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //------------------------------------------------------------------------------------------------
//    //描述:
//    //设计: Skyline(2001.12.29)
//    //实现: Skyline
//    //修改:
//    //------------------------------------------------------------------------------------------------
//    public void getBDQ() {
//        JBDQSetup2Dlg Dlg;
//        try {
//            Dlg = new JBDQSetup2Dlg(this, JActiveDComDM.MainApplication.MainWindow, res.getString("String_652"), true);
////      Dlg.setReportModel(this);
//            Dlg.CenterWindow();
//            Dlg.setVisible(true);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JBdhInfo getBDH(int Row) {
        JBdhInfo BdhInfo = null;int BDH_NUM=0;
        for (int i = 0; i < BdhInfoList.size(); i++) {
            BdhInfo = (JBdhInfo) BdhInfoList.get(i);
            BDH_NUM = BdhInfo.BDH_NUM;
            if ( BdhInfo.BDH_ISNEW != 0 ) BDH_NUM =0;
            if (Row >= (BdhInfo.RowInfo.HZD_ZB )
                && Row <= (BdhInfo.RowInfo.HZD_ZB + BDH_NUM)) {
                return BdhInfo;
            }
        }
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JBdlInfo getBDL(int Col) {
        JBdlInfo BdlInfo = null;
        for (int i = 0; i < BdlInfoList.size(); i++) {
            BdlInfo = (JBdlInfo) BdlInfoList.get(i);
            if (Col >= (BdlInfo.ColInfo.LZD_ZB )
                && Col <= (BdlInfo.ColInfo.LZD_ZB + BdlInfo.BDL_NUM )) {
                return BdlInfo;
            }
        }
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JBdhInfo setBDH(String Name, int Row, int RowNum, int DR, int DyCol, String BMLB) {
        JBdhInfo BdhInfo = getBDH(Row);
        try {
            if (BdhInfo == null) {
                BdhInfo = new JBdhInfo(this);
                BdhInfoList.add(BdhInfo);
                BdhInfo.BDH_ORDE = String.valueOf(BdhMaxOrde++);
                BdhInfo.ChangeLog = 1;
            }
            else {
                BdhInfo.ChangeLog = 2;
            }
            BdhInfo.DyColInfo = JColInfo.GetColInfoByZB(DyCol, 1, this);
            BdhInfo.RowInfo = JRowInfo.GetRowInfoByZB(Row, 1, this);
            BdhInfo.BDH_NUM = RowNum - Row;
            BdhInfo.BZBM_BM = BMLB;
            BdhInfo.BDH_MC = Name;
            int v = this.ReportView.getRowOutlineLevel(Row);
            //在F1里面行是从0开始,所以设显示级数时要减1
            RowNum=RowNum-1;
            ReportView.setRowOutlineLevel(Row-1, RowNum, 0, false);
            ReportView.setRowOutlineLevel(Row-1, RowNum, 1, true);
            if (DR >= Row && DR <= RowNum) { //最大的有数据的行
                ReportView.setRowOutlineLevel(Row-1, DR-1, 0, false);
                ReportView.setRowOutlineLevel(Row-1, RowNum, 2, true);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return BdhInfo;
    }
    public JBdhInfo setBDH(String Name, int Row, int RowNum, int DyCol, String BMLB) {
      return setBDH(Name, Row, RowNum, DyCol,  BMLB,0);
    }
    public JBdhInfo setBDH(String Name, int Row, int RowNum, int DyCol, String BMLB,int BDH_ISNEW) {
        JBdhInfo BdhInfo = getBDH(Row);
        try {
            if (BdhInfo == null) {
                BdhInfo = new JBdhInfo(this);
                BdhInfoList.add(BdhInfo);
                BdhInfo.BDH_ORDE = String.valueOf(BdhMaxOrde++);
                BdhInfo.ChangeLog = 1;
                BdhInfo.RowInfo = JRowInfo.GetRowInfoByZB(Row, 1, this);
                if ( BDH_ISNEW == 1 ) {
                  BdhInfo.RowInfo.setNewBDH();
                }
            }
            else {
                BdhInfo.ChangeLog = 2;
            }
            BdhInfo.BDH_ISNEW = BDH_ISNEW;
            BdhInfo.DyColInfo = JColInfo.GetColInfoByZB(DyCol, 1, this);
            if ( BDH_ISNEW == 1 ) {
              BdhInfo.BDH_NUM = 0;
            } else {
              BdhInfo.BDH_NUM = RowNum - Row;
            }
            BdhInfo.BZBM_BM = BMLB;
            BdhInfo.BDH_MC = Name;
            // 显示变动区
            JBdhInfo.deleNewBdh(this,BdhInfo);
            JBdhInfo.viewBdh(this,BdhInfo,false);
            JBdhInfo.viewNewData(this,BdhInfo);
            JBdhInfo.setRowHeadTitle(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return BdhInfo;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JBdhInfo delBDH(int Row) {
        JBdhInfo BdhInfo = getBDH(Row);
        try {
            if (BdhInfo != null) {
                BdhInfoList.remove(BdhInfo);
                DelBdhInfoList.add(BdhInfo);
                JBdhInfo.deleBdh(this,BdhInfo);
                if ( BdhInfo.BDH_ISNEW == 1 )
                  BdhInfo.RowInfo.delNewBDH();
                JBdhInfo.setRowHeadTitle(this,true);
                this.setModified();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return BdhInfo;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JBdlInfo delBDL(int Col) {
        JBdlInfo BdlInfo = getBDL(Col);
        try {
            if (BdlInfo != null) {
                BdlInfoList.remove(BdlInfo);
                DelBdlInfoList.add(BdlInfo);
                // int v = this.ReportView.getColOutlineLevel(Col);
               // if (BdlInfo.BDL_NUM == 1) {
                 //   ReportView.setColOutlineLevel(BdlInfo.ColInfo.LZD_ZB - 1, BdlInfo.ColInfo.LZD_ZB + 1, 0, false);
                //}
                ReportView.setColOutlineLevel(BdlInfo.ColInfo.LZD_ZB - 1,
                                              BdlInfo.ColInfo.LZD_ZB +BdlInfo.BDL_NUM- 1, 0, false);
                ReportView.setColHidden(BdlInfo.ColInfo.LZD_ZB - 1,
                            BdlInfo.ColInfo.LZD_ZB +BdlInfo.BDL_NUM+1,false);

              }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return BdlInfo;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JBdlInfo setBDL(String Name, int Col, int ColNum, String BMLB) {
        JBdlInfo BdlInfo = getBDL(Col);
        try {
            if (BdlInfo == null) {
                BdlInfo = new JBdlInfo();
                BdlInfoList.add(BdlInfo);
                BdlInfo.BDL_ORDE = String.valueOf(BdlMaxOrde++);
                BdlInfo.ChangeLog = 1;
            }
            else {
                BdlInfo.ChangeLog = 2;
            }
            //if (BdlInfo.ChangeLog == 1 && (ColNum - Col != 1)) {
             //   BdlInfo.ColInfo = JColInfo.GetColInfoByZB(Col + 1, 1, this);
           // }
            //else {
                BdlInfo.ColInfo = JColInfo.GetColInfoByZB(Col, 1, this);
            //}
            BdlInfo.BZBM_BM = BMLB;
            BdlInfo.BDL_NUM = ColNum - Col;
            BdlInfo.BDL_MC = Name;
            //int v = this.ReportView.getColOutlineLevel(Col);
            //if ( v == 0 ) {
            ReportView.setColOutlineLevel(Col-1, ColNum-1, 0, false);
            ReportView.setColOutlineLevel(Col-1, ColNum-1, 1, true);
            //}
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return BdlInfo;
    }

    public boolean SaveReportsFormat(String bh,String date,JBook ReportView){
    	boolean res = false;    	
    	BBZD_BH = bh;
//        BBZD_MC = mc;
        BBZD_DATE = date;
//        BBZD_FCBZ = "0";
//        ADD_BH = null;
        
		if (BBZD_BH != null) {			
			//读取excel文件
			ZipEntry ze;
	        ZipOutputStream zos;
	        ByteArrayOutputStream os;
	        JParamObject PO;
	        
	        try {
	         /**
		      * 写入文件
		      */
	         
	          ze = new ZipEntry("$_LCBBGS_$.VTS");
	          os = new ByteArrayOutputStream();
	    
	          zos = new ZipOutputStream(os);
	          zos.putNextEntry(ze);	          
          
	          ReportView.write(zos, new com.f1j.ss.WriteParams(ReportView.eFileExcel97));
	          zos.flush();
	          zos.finish();
	          ROE.FormatObject = os.toByteArray(); 
	   
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        
			
		} else {
			ROE.FormatObject = null;
		}
		
			// 保存成功
		if (SaveFormatToDB()) {
			res = true;			
		}    	
    	return res;
    }
  //end
  //------------------------------------------------------------------------------------------------
    //描述:批量导入报表格式使用
    //设计:sunhao (2012.12.6)
    //实现: sunhao
    //修改:
    //------------------------------------------------------------------------------------------------
    private Boolean SaveFormatToDB() {
    	Boolean res = false;
        JParamObject PO = JParamObject.Create();     
        
        if(BBZD_BH !=null&&BBZD_DATE!=null&&ROE.FormatObject.length>0){
        	
        	PO.SetValueByParamName("BBZD_BH", BBZD_BH);
        	PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
//        	PO.SetValueByParamName("SVRNAME","ReportServer");
        	res = (Boolean) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "SaveReportsFormatObject", PO, ROE);
        }else{
        	res = false;
        }
        
    	return res;
    }

    
    public JBdlInfo setBDL(String Name, int Col, int ColNum, int DR, String BMLB) {
        JBdlInfo BdlInfo = getBDL(Col);
        try {
            if (BdlInfo == null) {
                BdlInfo = new JBdlInfo();
                BdlInfoList.add(BdlInfo);
                BdlInfo.BDL_ORDE = String.valueOf(BdlMaxOrde++);
                BdlInfo.ChangeLog = 1;
            }
            else {
                BdlInfo.ChangeLog = 2;
            }
            BdlInfo.ColInfo = JColInfo.GetColInfoByZB(Col, 1, this);
            BdlInfo.BZBM_BM = BMLB;
            BdlInfo.BDL_NUM = ColNum - Col;
            BdlInfo.BDL_MC = Name;

            int v = this.ReportView.getColOutlineLevel(Col);
//      if ( v == 0 ) {
//      ReportView.setRowOutlineLevel(Row,RowNum,1,true);
            //  }else{
            ColNum-=1;
            ReportView.setColOutlineLevel(Col-1, ColNum, 0, false);
            ReportView.setColOutlineLevel(Col-1, ColNum, 1, true);
            //}
            if (DR >= Col && DR <= ColNum) { //最大的有数据的行
                ReportView.setColOutlineLevel(Col-1, DR-1, 0, false);
                ReportView.setColOutlineLevel(Col-1, ColNum, 2, true);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return BdlInfo;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public java.util.List getBDHList() {
        return BdhInfoList;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public java.util.List getBDLList() {
        return BdlInfoList;
    }
//
//    //------------------------------------------------------------------------------------------------
//    //描述:
//    //设计: Skyline(2001.12.29)
//    //实现: Skyline
//    //修改:
//    //------------------------------------------------------------------------------------------------
//    public void EditBDQData() {
//        try {
//            if (BdlInfoList!=null&&BdlInfoList.size()>0) {
//                EditBDLData();
//            }
//            else {
//                EditBDHData();
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //------------------------------------------------------------------------------------------------
//    //描述:
//    //设计: Skyline(2001.12.29)
//    //实现: Skyline
//    //修改:
//    //------------------------------------------------------------------------------------------------
//    void EditBDHData() {
//        int RowStatus = 0;
//        JBdhInfo BdhInfo = null;
//        JParamObject PO = JParamObject.Create();
//        try {
//            int Row = ReportView.getSelStartRow() + 1;
//            int phySRow = ReportView.getPhySelStartRow();
//            RowStatus = JBdhUtils.checkPhyRowStatus(this,phySRow);
//            int proRow = JBdhUtils.getPhyNewBdhPrioRow(this,phySRow);
//            int logRow = JBdhUtils.getLogRow(this,proRow);
//
//
//            //add by fsz 2004.5.11
//            if ( RowStatus == 3 ) {
//                BdhInfo = getBDH(logRow);
//            } else {
//                BdhInfo = getBDH(Row);
//            }
//            if (BdhInfo == null)
//            return;
//            //为兼容C/S，处理标准编码
////            if (BdhInfo.BDH_NUM > 1) {
//            JBzbmListDlg Dlg;
//            try {
//                if (BdhInfo.BZBM_BM != null &&
//                    BdhInfo.BZBM_BM.trim().length() != 0) {
//                    Dlg = new JBzbmListDlg(BdhInfo.BZBM_BM,
//                                           JActiveDComDM.MainApplication.MainWindow,
//                                           res.getString("String_653"), true, getBzbm(BdhInfo));
//                    Dlg.CenterWindow();
//                    Dlg.setVisible(true);
//                    if (Dlg.Result == Dlg.RESULT_OK) {
//                        Vector list = Dlg.getBZBMList();
//                        // 新旧变动区域标准编码的处理，就在于以下这个方法了
//                        FillBzbm(list, BdhInfo);
//                    }
//                }
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//                return;
////            }
//            //end
//            /*  这段搞不清楚干啥，也不能正常运行，新版变动的标准编码处理还是另外写吧
//            JRowInfo RI = JRowInfo.GetRowInfoByZB(Row, 1, this);
//            PO.SetValueByParamName("BBZD_BH", BBZD_BH);
//            PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
//            PO.SetValueByParamName("BBZD_MC", BBZD_MC);
//            if (ADD_TYPE != JReportObjectStub.STUB_TYPE_REPORT) {
//                PO.SetValueByParamName("ADD_BH", ADD_BH);
//                PO.SetValueByParamName("ADD_MC", ADD_MC);
//                PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
//            }
//            if (TZZD_ORDE != null) {
//                PO.SetValueByParamName("TZZD_ORDE", TZZD_ORDE);
//            }
//            PO.SetValueByParamName("HZD_ORDE", BdhInfo.RowInfo.HZD_ORDE);
//            JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "ProcessBDHData", PO, this);
//         */
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    //------------------------------------------------------------------------------------------------
//    //描述:
//    //设计: Skyline(2001.12.29)
//    //实现: Skyline
//    //修改:
//    //------------------------------------------------------------------------------------------------
//    void EditBDLData() {
//        JParamObject PO = JParamObject.Create();
//        try {
//            int Col = ReportView.getSelStartCol() + 1;
//            JBdlInfo BdlInfo = getBDL(Col);
//            if (BdlInfo == null) {
//                return;
//            }
//            if (BdlInfo.BDL_NUM > 1) {
//                JBzbmListDlg Dlg;
//                try {
//                    if (BdlInfo.BZBM_BM != null &&
//                        BdlInfo.BZBM_BM.trim().length() != 0) {
//                        Dlg = new JBzbmListDlg(BdlInfo.BZBM_BM,
//                                               JActiveDComDM.MainApplication.MainWindow,
//                                               res.getString("String_654"), true, getBzbm(BdlInfo));
//                        Dlg.CenterWindow();
//                        Dlg.setVisible(true);
//                        if (Dlg.Result == Dlg.RESULT_OK) {
//                            Vector list = Dlg.getBZBMList();
//                            FillBzbm(list, BdlInfo);
//                        }
//                    }
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return;
//            }
//            //end
//            JColInfo CI = JColInfo.GetColInfoByZB(Col, 1, this);
//            PO.SetValueByParamName("BBZD_BH", BBZD_BH);
//            PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
//            PO.SetValueByParamName("BBZD_MC", BBZD_MC);
//            if (ADD_TYPE != JReportObjectStub.STUB_TYPE_REPORT) {
//                PO.SetValueByParamName("ADD_BH", ADD_BH);
//                PO.SetValueByParamName("ADD_MC", ADD_MC);
//                PO.SetIntByParamName("ADD_TYPE", ADD_TYPE);
//            }
//            if (TZZD_ORDE != null) {
//                PO.SetValueByParamName("TZZD_ORDE", TZZD_ORDE);
//            }
//            PO.SetValueByParamName("LZD_ORDE", BdlInfo.ColInfo.LZD_ORDE);
//            JActiveDComDM.DataActiveFramework.InvokeObjectMethod("ReportObject", "ProcessBDLData", PO, this);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    void FillBzbm(Vector list, JBdhInfo BdhInfo) {
        int index;
        int BDH_ISNEW = BdhInfo.BDH_ISNEW; //
        try {
            int Col = BdhInfo.DyColInfo.LZD_ZB;
            int SRow = ReportView.getSelStartRow() + 1;
            int ERow = ReportView.getSelEndRow() + 1;
            int phySRow,phyERow;
            phySRow = ReportView.getPhySelStartRow();
            phyERow = ReportView.getPhySelEndRow();

            String[] iso;
            if ( BDH_ISNEW == 1) {
                JBDHDataInfo BDHDataInfo = null;
                for ( int Row = SRow,phyRow = phySRow ; Row <= ERow; Row++,phyRow++) {
                    if ( Row <= BdhInfo.RowInfo.getBdhDataList().size()){
                        index = Row -SRow;
                        BDHDataInfo = (JBDHDataInfo)BdhInfo.RowInfo.getBdhDataList().get(Row-1);
                        if ( index < list.size() ) {
                            iso = (String[]) list.get(Row - SRow);
                            BDHDataInfo.BDSJ_HBM = iso[0];
                            ReportView.setPhyText(phyRow, Col - 1, iso[0] + "-" + iso[1]);
                        } else {
                            BDHDataInfo.BDSJ_HBM = "";
                            ReportView.setPhyText(phyRow, Col - 1, "");
                        }
                        this.ChangeUnitData(Row, 1, Row, BBZD_LS, phyRow, 0, phyERow, BBZD_LS - 1);
                    }
                }
            } else {
                JRowInfo RowInfo = null;
                for (int Row = SRow; Row <= ERow; Row++) {
                    if (Row >= (BdhInfo.RowInfo.HZD_ZB)
                        && Row <= (BdhInfo.RowInfo.HZD_ZB + BdhInfo.BDH_NUM)) {
                        index = Row - SRow;
                        RowInfo = JRowInfo.GetRowInfoByZB(Row, 1, this);
                        if (index < list.size()) {
                            iso = (String[]) list.get(Row - SRow);
                            RowInfo.BZBM_BM = iso[0];
                            ReportView.setText(Row - 1, Col - 1, iso[0] + "-" + iso[1]);
                        }
                        else {
                            RowInfo.BZBM_BM = "";
                            ReportView.setText(Row - 1, Col - 1, "");
                        }
                        this.ChangeUnitData(Row, 1, Row, BBZD_LS, phySRow, 0, phyERow, BBZD_LS - 1);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    void FillBzbm(Vector list, JBdlInfo BdlInfo) {
        int index;
        try {
            int SCol = ReportView.getSelStartCol() + 1;
            int ECol = ReportView.getSelEndCol() + 1;

            String[] iso;
            JColInfo ColInfo = null;
            for (int Col = SCol; Col <= ECol; Col++) {
                if (Col >= (BdlInfo.ColInfo.LZD_ZB )
                    && Col <= (BdlInfo.ColInfo.LZD_ZB + BdlInfo.BDL_NUM )) {
                    index = Col - SCol;
                    ColInfo = JColInfo.GetColInfoByZB(Col, 1, this);
                    if (index < list.size()) {
                        iso = (String[]) list.get(Col - SCol);
                        ColInfo.BZBM_BM = iso[0];
                        ReportView.setText(BBZD_HEAD + BBZD_TITLE - 1, Col - 1, iso[0] + "-" + iso[1]);
                    }
                    else {
                        ColInfo.BZBM_BM = "";
                        ReportView.setText(BBZD_HEAD + BBZD_TITLE - 1, Col - 1, "");
                    }
                    this.ChangeUnitData(BBZD_TITLE + BBZD_HEAD, Col, BBZD_HS, Col,-1,-1,-1,-1);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
//
//    protected Vector getBzbm(JBdhInfo BdhInfo) {
//        Vector v = new Vector();
//        try {
//            int Col = BdhInfo.DyColInfo.LZD_ZB;
//            int SRow = ReportView.getSelStartRow() + 1;
//            int ERow = ReportView.getSelEndRow() + 1;
//            IStubObject iso;
//            if ( BdhInfo.BDH_ISNEW == 1 ) {
//                JBDHDataInfo BDHDataInfo = null;
//                for ( int Row = SRow; Row <= ERow; Row++) {
//                    if ( Row <= BdhInfo.RowInfo.getBdhDataList().size()){
//                        BDHDataInfo = (JBDHDataInfo)BdhInfo.RowInfo.getBdhDataList().get(Row-1);
//                        v.add(BDHDataInfo.BDSJ_HBM);
//                    }
//                }
//
//            } else {
//                JRowInfo RowInfo = null;
//                for (int Row = SRow; Row <= ERow; Row++) {
//                    if (Row >= (BdhInfo.RowInfo.HZD_ZB)
//                        && Row <= (BdhInfo.RowInfo.HZD_ZB + BdhInfo.BDH_NUM)) {
//                        RowInfo = JRowInfo.GetRowInfoByZB(Row, 1, this);
//                        v.add(RowInfo.BZBM_BM);
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return v;
//    }
//
//    protected Vector getBzbm(JBdlInfo BdlInfo) {
//        Vector v = new Vector();
//        try {
//            int Row = BBZD_TITLE + BBZD_HEAD;
//            int SCol = ReportView.getSelStartCol() + 1;
//            int ECol = ReportView.getSelEndCol() + 1;
//            IStubObject iso;
//            JColInfo ColInfo = null;
//            for (int Col = SCol; Col <= ECol; Col++) {
//                if (Col >= (BdlInfo.ColInfo.LZD_ZB )
//                    && Col <= (BdlInfo.ColInfo.LZD_ZB + BdlInfo.BDL_NUM )) {
//                    ColInfo = JColInfo.GetColInfoByZB(Col, 1, this);
//                    v.add(ColInfo.BZBM_BM);
//                }
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return v;
//    }

    public void ProcessImportFromExcel(JBOFApplication app) {
        JParamObject PO = null;
        PO = JParamObject.Create();
        PO.SetValueByParamName("BBZD_BH", BBZD_BH);
        PO.SetValueByParamName("BBZD_DATE", BBZD_DATE);
//        JActiveDComDM.DataActiveFramework.MInvokeObjectMethod("ReportObject",
//            "DeleteReport", PO, null, null, app);
        SetChangeLogHZD(1);
        SetChangeLogLZD(1);
        ChangeUnitData(1, 1, BBZD_HS, BBZD_LS,-1,-1,-1,-1);

    }

    /**
     * 处理报表封存或报表锁定
     * 报表复核或审核后都不能再进行修改
     * 报表封存或锁定后应该允许修改行列宽度及打印格式
     * 现在改为只锁定单元格编辑
     * modified by hufeng 2008.2.22
     */
    public void processBbfc() {
        if (!isAllowEditData() ) {
            ReportView.setAllowInCellEditing(false);
            ReportView.setAllowDelete(false);
            ReportView.BookTextField.setEnabled(false);
//            ReportView.setEnableProtection(true);
//            ReportView.setAllowResize(true);
        }else{
            ReportView.setAllowInCellEditing(true);
            ReportView.setAllowDelete(true);
        }

        // 格式是否允许修改
        if (!isAllowEditGS()) {
            ReportView.bnFuncWzd.setEnabled(false);
            ReportView.bnCancelSum.setEnabled(false);
            ReportView.bnDHOK.setEnabled(false);
            ReportView.cbFH.setEnabled(false);
            ReportView.cbJYGS.setEnabled(false);
            ReportView.BookTextField.setEnabled(false);
        }
    }
    public JBdhInfo processInsertRowInBdh(int SRow,int ERow){
      //add by fsz 2004.10.22 如果在变动区内自动更新变动区的NUM
      JBdhInfo bdh = this.getBDH(SRow);
      try {
        if (bdh != null) {
          ReportView.setRowOutlineLevel(bdh.RowInfo.HZD_ZB - 1,
                                        bdh.RowInfo.HZD_ZB + bdh.BDH_NUM - 1, 0, false);
          int end = ERow > bdh.RowInfo.HZD_ZB + bdh.BDH_NUM ?
              bdh.RowInfo.HZD_ZB + bdh.BDH_NUM : ERow;
          bdh.BDH_NUM += end - SRow + 1;
          bdh.ChangeLog = 2;
          //  ReportView.setRowOutlineLevel(bdh.RowInfo.HZD_ZB-1,
          //                    bdh.RowInfo.HZD_ZB+bdh.BDH_NUM-1,1,true);
        }
      }
      catch (Exception e) {}
      //end
      return bdh;
    }
    public JBdlInfo processInsertColInBdl(int SCol,int ECol){
      //add by fsz 2004.10.22 如果在变动区内自动更新变动区的NUM
      JBdlInfo bdl = this.getBDL(SCol);
      try {
        if (bdl != null) {
          ReportView.setColOutlineLevel(bdl.ColInfo.LZD_ZB - 1,
                                        bdl.ColInfo.LZD_ZB + bdl.BDL_NUM - 1, 0, false);
          int end = ECol > bdl.ColInfo.LZD_ZB + bdl.BDL_NUM ?
              bdl.ColInfo.LZD_ZB + bdl.BDL_NUM : ECol;
          bdl.BDL_NUM += end - SCol + 1;
          bdl.ChangeLog = 2;
          //  ReportView.setRowOutlineLevel(bdh.RowInfo.HZD_ZB-1,
          //                    bdh.RowInfo.HZD_ZB+bdh.BDH_NUM-1,1,true);
        }
      }
      catch (Exception e) {}
      //end
      return bdl;
    }

    /**
     * 取调整区域
     * @return String
     */
    public String getNotAdjustArea(){
        return this.BBZD_NTZK;
    }

    /**
     * 设置调整区域
     * @param area String
     */
    public void setNotAdjustArea(String area){
        BBZD_NTZK = area;
    }

    /**
     * 取选择的区域
     *
     * @return String[]
     */
    private ArrayList getSelectedAreas() {
        ArrayList cellList = new ArrayList();
        if (ReportView != null) {
            String areaString = ReportView.getSelection();
            if (areaString != null && areaString.trim().length() > 0) {
                String[] array = areaString.split(",");
                //分割出来的每一项可能是区域，也可能是单个单元格
                if (array != null) {
                    String cellStr;
                    for (int i = 0, n = array.length; i < n; i++) {
                        cellStr = array[i];
                        if (cellStr.indexOf(":") >= 0) {
                            String c1 = cellStr.substring(0, cellStr.indexOf(":"));
                            String c2 = cellStr.substring(cellStr.indexOf(":") + 1);
                            String c3 = JReportPubFunc.cellToRowCol(c1); //开始行列
                            String c4 = JReportPubFunc.cellToRowCol(c2); //结束行列
                            if (c3 != null && c4 != null && (c3.trim().length() == 0 || c4.trim().length() == 0))
                                continue;
                            String sr = c3.substring(0, c3.indexOf("#"));
                            String sc = c3.substring(c3.indexOf("#") + 1);
                            String er = c4.substring(0, c4.indexOf("#"));
                            String ec = c4.substring(c4.indexOf("#") + 1);
                            int i1 = Integer.parseInt(sr); //开始行
                            int i2 = Integer.parseInt(sc); //开始列
                            int i3 = Integer.parseInt(er); //结束行
                            int i4 = Integer.parseInt(ec); //结束列
                            for (int r = i1; r <= i3; r++) {
                                for (int c = i2; c <= i4; c++) {
                                    String cell = JReportPubFunc.rowColToCell(r, c);
                                    cellList.add(cell);
                                }
                            }
                        } else {
                            cellList.add(cellStr);
                        }
                    }
                }
            }
        }
        return cellList;
    }

}
