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
//����:
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JReportDataModel {
    public JReportView ReportView = null;
    public Hashtable InfoList = new Hashtable();
	public boolean bHaveModifyQx = true;

    public int getReportType() {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
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
     * �����Ƿ�����
     * ֻ��ϵͳ��������
     * @return boolean
     */
    public boolean isLock() {
        return false;
    }

    /**
     * �����Ƿ����
     * @return boolean
     */
    public boolean getBBSH(){
        return false;
    }

    /**
     * �����Ƿ񸴺�
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
     * �Ƿ�����༭����
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
     * �Ƿ�����༭��ʽ
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
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public int CreateReport(JParamObject PO) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public boolean SaveReportObject() {
        return true;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public int OpenReportObject(Object roe, JParamObject PO) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void setModified() {

    }
	public void setModified(boolean isModified) {

	}

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public int getModified() {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�: ��ʾ����У�鹫ʽ
    //------------------------------------------------------------------------------------------------
    public void setDataStatus(int Status) {

    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�: ��ʾ����У�鹫ʽ
    //------------------------------------------------------------------------------------------------
    public int getDataStatus() {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�: ��ʾ����У�鹫ʽ
    //------------------------------------------------------------------------------------------------
    public String GetHzdOrde(String BH, String DATE, int Row) {
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�: ��ʾ����У�鹫ʽ
    //------------------------------------------------------------------------------------------------
    public String GetLzdOrde(String BH, String DATE, int Col) {
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public int GetHzdZb(String BH, String DATE, String HzdOrde) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�: ��ʾ����У�鹫ʽ
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
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void ChangeUnitData(int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {

    }

    public void ClearUnitData(int SRow, int SCol, int ERow, int ECol,int phySRow,int phySCol,int phyERow,int phyECol) {

    }
    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void ReportModelAttrib() {

    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    // ������ⲿ��ʽ���ӿ�,������ڲ���ʽ,�����ڲ���ʽ,������ǹ�ʽ,����ԭֵ
    public String setExternalF1(int Row, int Col, String F1String,int phyRow,int phyCol) throws Exception {
        return F1String;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    private void ChangeUI(int Row, int Col, int Op) {

    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public String setBookTextFieldText(int Row, int Col) {
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public String GetCaption() {
        return null;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
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
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public int CheckReport(JContextObject CO,JParamObject PO) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public int CheckSetReport(JContextObject CO) {
        return 0;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
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
