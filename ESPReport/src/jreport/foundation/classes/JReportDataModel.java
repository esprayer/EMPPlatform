package jreport.foundation.classes;

import java.util.*;

import java.awt.*;

import com.efounder.eai.application.classes.JBOFApplication;
import com.efounder.eai.data.JParamObject;

import jbof.application.classes.*;
import jreport.swing.classes.*;
import jreport.swing.classes.ReportBook.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JReportDataModel {
    public JReportView ReportView = null;
    public Hashtable InfoList = new Hashtable();
	public boolean bHaveModifyQx = true;

    public int getReportType() {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JReportDataModel() {
    }

    public boolean getBBFC() {
        return true;
    }
    public boolean getBBXF() {
        return true;
    }
    public boolean getSRKZ() {
        return true;
    }

    /**
     * 报表是否锁定
     * 只对系统报表有用
     * @return boolean
     */
    public boolean isLock() {
        return false;
    }

    /**
     * 报表是否审核
     * @return boolean
     */
    public boolean getBBSH(){
        return false;
    }

    /**
     * 报表是否复核
     * @return boolean
     */
    public boolean getBBFH(){
        return false;
    }

    public boolean getGSFC() {
        return true;
    }

    public void processBbfc() {

    }

    public boolean getBBQX(int pzw) {
        return true;
    }

    public boolean getADDQX(int pzw) {
        return true;
    }

    /**
     * 是否允许编辑数据
     * @return boolean
     */
    public boolean isAllowEditData(){
        return false;
    }

    public boolean isYsdw(){
        return false;
    }
    public boolean isDwwc(){
        return false;
    }
    
    /**s
     * 是否允许编辑公式
     * @return boolean
     */
    public boolean isAllowEditGS(){
        return false;
    }

    public void setModelInfo(String Name, Object Info) {
        InfoList.put(Name, Info);
    }

    public Object getModelInfo(String Name, Object def) {
        Object o = InfoList.get(Name);
        if (o == null) {
            return def;
        }
        return o;
    }

    public void setBDQ() {

    }

    public void getBDQ() {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int CreateReport(JParamObject PO) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public boolean SaveReportObject() {
        return true;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int OpenReportObject(Object roe, JParamObject PO) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void setModified() {

    }
	public void setModified(boolean isModified) {

	}

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int getModified() {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public void setDataStatus(int Status) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public int getDataStatus() {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public String GetHzdOrde(String BH, String DATE, int Row) {
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public String GetLzdOrde(String BH, String DATE, int Col) {
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int GetHzdZb(String BH, String DATE, String HzdOrde) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改: 显示报表校验公式
    //------------------------------------------------------------------------------------------------
    public int GetLzdZb(String BH, String DATE, String LzdOrde) {
        return 0;
    }

    public void InsertRow(int SRow, int ERow) throws Exception {

    }

    public void InsertCol(int SCol, int ECol) {

    }

    public void DeleteRow(int SRow, int ERow) throws Exception {

    }

    public void DeleteCol(int SCol, int ECol) {

    }

    public void DeleteForumlaOne(int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {
    }

    public void DeleteComment(int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void ChangeUnitData(int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {

    }

    public void ClearUnitData(int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {

    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void ReportModelAttrib() {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    // 如果是外部公式返加空,如果是内部公式,返加内部公式,如果不是公式,返回原值
    public String setExternalF1(int Row, int Col, String F1String,int phyRow,int phyCol) throws Exception {
        return F1String;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    private void ChangeUI(int Row, int Col, int Op) {

    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public String setBookTextFieldText(int Row, int Col) {
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public String GetCaption() {
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int CountReport(JContextObject CO) {
        return 0;
    }
    public int CalcReport(JContextObject CO) {
        return 0;
    }
	public int DealReport(JContextObject CO) {
		return 0;
	}

    public int AdjustReport(JContextObject CO) {
        return 0;
    }
    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int CheckReport(JContextObject CO,JParamObject PO) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int CheckSetReport(JContextObject CO) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public int RefreshData(JContextObject CO) {
        return 0;
    }

    public void ShowWizard(int x, int y, Frame frame, JReportDataModel RM, Object RB) {

    }

    public void HideWizard() {

    }

    public void setWizardObject(Object ReportBook) {

    }

    public boolean selectionChanged(Object SelectChangeObject) {
        return false;
    }

    public JBdhInfo getBDH(int Row) {
        return null;
    }

    public JBdlInfo getBDL(int Col) {
        return null;
    }

    public JBdhInfo setBDH(String Name, int Row, int RowNum, int DR, int DyCol, String BMLB) {
        return null;
    }

    public JBdhInfo setBDH(String Name, int Row, int RowNum, int DyCol, String BMLB) {
        return null;
    }

    public JBdlInfo setBDL(String Name, int Col, String BMLB) {
        return null;
    }

    public JBdhInfo delBDH(int Row) {
        return null;
    }

    public JBdlInfo delBDL(int Col) {
        return null;
    }

    public java.util.List getBDHList() {
        return null;
    }

    public java.util.List getBDLList() {
        return null;
    }

    public void EditBDQData() {

    }

    public void ProcessImportFromExcel(JBOFApplication app) {

    }
    public JBdhInfo processInsertRowInBdh(int SRow,int ERow){
      return null;
    }
    public JBdlInfo processInsertColInBdl(int SCol,int ECol){
      return null;
    }
    public String getYSDW(){
        return null;
      }
    public String getTZBH(){
    	return null;
    }

	public int CheckSetReport1(JContextObject cO) {
		return 0;
	}

}
