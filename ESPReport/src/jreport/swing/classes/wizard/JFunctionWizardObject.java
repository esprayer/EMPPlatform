package jreport.swing.classes.wizard;

import java.net.*;
import java.util.*;

import java.awt.*;
import javax.swing.*;

import org.jdom.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.foundation.classes.*;
import jframework.resource.classes.*;
import jreport.foundation.classes.*;
import jreport.model.classes.calculate.*;
import jreport.swing.classes.*;
import jreport.swing.classes.ReportBook.*;
import jreportfunction.extend.*;
import jtoolkit.xml.classes.*;
import com.pansoft.esp.fmis.client.fwkview.FMISContentWindow;
import jbof.gui.window.classes.JBOFChildWindow;

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
public class JFunctionWizardObject {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");
    // �ⲿ�����б�
    private static ArrayList mExtFuncList = null;
    // ϵͳ�б�
    private static ArrayList mXtbhList    = null;

    //
    public JReportBook ReportBook = null;
    //
    public IWizardInterface WizardInterface = null;
    //
    public JReportView ReportView = null;
    //
    public JReportModel ReportModel = null;
    //
    public JBookTextField BookTextField = null;
    public JBookComboBox BookComboBox = null;
    //
    public JButton bnCancelSum = null;
    public JButton bnDHOK = null;
    public JButton bnFuncWzd = null;

    public Frame MainFrame;

    public JFunctionWizardDialog WizardDialog = null;
    // ���ƶ����б�
    public Hashtable LimitObjectList = new Hashtable();
    // ���ݶ����б�
    public Hashtable DataObjectList = new Hashtable();
    // ���������б�
    public Hashtable ConditionObjectList = new Hashtable();
    // ���������б�
    public Vector CObjList = new Vector();
    // ���������б�
    public Vector FunctionTypeList = new Vector();
    // �����б�
    public Hashtable FunctionList = new Hashtable();
    // ���ú����б�
    public Vector FavFuncList = new Vector();
    public JWizardHelpObject HelpObject = new JWizardHelpObject();
    public JXMLBaseObject XMLObject = null;

    /**
     * �Ƿ񱨱��ڵ���ģʽ
     * ������Ǳ�����ģʽ������Ҫ���湫ʽ������
     */
    private boolean mIsReportWinzard = true;

    /**
     * Ϊ��֧���Զ��幫ʽ������ʾʲô�������ӣ�����ϵͳ��
     * add by gengeng 2008-8-4
     */
    public boolean isCustom;
    public HashMap mFuncMap;

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public JFunctionWizardObject() {
    }

    /**
     * ��ʼ�������б�
     * @param funcList ArrayList
     */
    public void InitWizardObject(){
        URL url;
        String uri;
        url = JXMLResource.LoadXML(this, "wizard.xml");
        uri = url.toString();
        XMLObject = new JXMLBaseObject();
        XMLObject.InitXMLURI(uri);

        /**
         * ȡ����ǰϵͳ
         * �жϺ�������ϵͳ�Ƿ��Ѱ�װ
         * ���δ��װ�����ټ��ش��ຯ��
         * modified by hufeng 2008.5.26
         */
 //lk       InitSystemList();

        // 1.��ʼ����������
        InitLimitObjectList(XMLObject);
        // 2.��ʼ�����ݶ���
        InitDataObjectList(XMLObject);
        // 3.��ʼ����������
        InitConditionObjectList(XMLObject);
        // 4.��ʼ�����������б�
        InitFunctionTypeList(XMLObject);
        // 5.��ʼ�����ú����б�
        InitFavFuncList(XMLObject);
        // 6.��ʼ���ⲿ�����б�
        InitExtendFunction();

        HelpObject.WizardObject = this;
    }

    /**
     * ��ʼ��ϵͳ�б�
     */
    private void InitSystemList() {
        if(mXtbhList != null){
            return;
        }
        JParamObject PO = JParamObject.Create();
        /**
         * ��̨����ʱ����ֵΪ��
         * �Դ�Ϊ�ж���ǰ̨�жϻ��Ǻ�̨�ж�
         */
        String value = PO.GetValueByEnvName("UserID","");
        if(value.equals("")){
            return;
        }
        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getSystemList", PO);
        if (RO != null && RO.ErrorCode == 0) {
            mXtbhList = (ArrayList)RO.ResponseObject;
        }
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

    /**
     * �Ƿ����ָ����ϵͳ
     * @param xtbh String
     * @return boolean
     */
    private boolean isHaveSystem(String xtbh){
        // Ĭ��Ϊϵͳ��������Ҫ����
        if(xtbh == null || xtbh.trim().equals("")){
            return true;
        }
        if(mXtbhList == null){
            return true;
        }

        return mXtbhList.contains(xtbh);
    }

    /**
     * ��ʼ���ⲿ����
     * �ں�̨��ʼ��ʱ����Ҫ���ⲿ�����б�����ʼ��
     */
    private void InitExtendFunction() {
        ArrayList extFuncList = JFunctionManager.getExtendFunctionList();
        if(extFuncList == null || extFuncList.size() == 0){
            return;
        }
        mExtFuncList = extFuncList;

        // �����ⲿ�������
        JFunctionTypeStub FTS = new JFunctionTypeStub();
        FTS.setId("EXTEND");
        FTS.setText("�ⲿ����");
        FunctionTypeList.add(FTS);
        // �������б���뺯��������
        JFunctionStub funcStub;
        JExtFuncStub  extStub;
        int count = mExtFuncList.size();
        for(int i=0; i<count; i++){
            funcStub = new JFunctionStub();
            extStub = (JExtFuncStub)mExtFuncList.get(i);
            //
            funcStub.setId(extStub.getFunc());
            funcStub.setPs(extStub.getParamCount() + 4); // Ĭ�����ĸ��������꣬�£�ȡ��������������
            funcStub.setText(extStub.getName());
            funcStub.setDes1(extStub.getFmDesc());
            funcStub.setDes2(extStub.getFnDesc());
            // ��ʼ���ⲿ������������
            initExtFuncPobj(funcStub,extStub);
            // ��ʼ���ⲿ������������
            initExtFuncCobj(funcStub,extStub);
            // ��ʼ���ⲿ����ȡ������
            initExtFuncDobj(funcStub,extStub);
            //
            FunctionList.put(funcStub.getId(),funcStub);
            FTS.FunctionList.add(funcStub);
        }

    }

    /**
     * ��ʼ���ⲿ������������
     * @param funcStub JFunctionStub
     * @param extStub JExtFuncStub
     */
    private void initExtFuncPobj(JFunctionStub funcStub,JExtFuncStub extStub){
        JExtObjStub objStub;
        JLimitObjectStub LOS;
        String value = "YEAR,MONTH";
        for (int j = 0; j < extStub.getParamCount(); j++) {
            objStub = (JExtObjStub) extStub.getPobjList().get(j);
            value += "," + objStub.getObjID();
            //
            LOS = new JLimitObjectStub();
            LOS.setId(objStub.getObjID());
            LOS.setType(objStub.getDataType());
            LOS.setText(objStub.getObjName());
            LOS.setDes1(objStub.getObjDesc());
            LOS.setDes2(objStub.getObjDesc());
            LOS.setDefvalue(""); // Ĭ��ֵ
            LOS.setTbName(objStub.getTable());
            LOS.setBhCol(objStub.getBhCol());
            LOS.setMcCol(objStub.getMcCol());
            LOS.setStruCol(objStub.getBmStru());
            LOS.setHelp(JExtFuncStub.FUNC_PARAM_HELP);
            LOS.HelpObject = HelpObject;
            this.LimitObjectList.put(LOS.getId(), LOS);
        }
        value += ",DOBJ,COBJ";
        funcStub.setParam(value);
    }

    /**
     * ��ʼ���ⲿ����ȡ������
     * @param funcStub JFunctionStub
     * @param extStub JExtFuncStub
     */
    private void initExtFuncDobj(JFunctionStub funcStub,JExtFuncStub extStub){
        String id,value = "";
        JExtObjStub objStub;
        ArrayList objList = extStub.getDobjList();
        for(int j=0; j<objList.size(); j++){
            objStub = (JExtObjStub)objList.get(j);
            id = objStub.getObjID();
            value += "," + id;
            // ���ȡ�������б��в����ڣ�����Ҫ����
            if(DataObjectList.get(id) == null){
                JDataObjectStub DOS = new JDataObjectStub();
                DOS.setId(id);
                DOS.setText(objStub.getObjName());
                DOS.setType(objStub.getDataType());
                DOS.setDes(objStub.getObjDesc());
                DataObjectList.put(DOS.id, DOS);
            }
        }
        if (value.length() > 1) {
            value = value.substring(1);
            funcStub.setDobj(value);
        }
    }

    /**
     * ��ʼ���ⲿ������������
     * @param funcStub JFunctionStub
     * @param extStub JExtFuncStub
     */
    private void initExtFuncCobj(JFunctionStub funcStub,JExtFuncStub extStub){
        String id,value = "";
        JExtObjStub objStub;
        ArrayList objList = extStub.getCobjList();
        for(int j=0; j<objList.size(); j++){
            objStub = (JExtObjStub)objList.get(j);
            id = objStub.getObjID();
            value += "," + id;
            // ��������б��в����ڣ�����Ҫ����
            if(ConditionObjectList.get(id) == null){
                JConditionObjectStub condStub = new JConditionObjectStub();
                condStub.setId(id);
                condStub.setText(objStub.getObjName());
                condStub.setType(objStub.getDataType());
                ConditionObjectList.put(id,condStub);
            }
        }
        if(value.length() > 1){
            value = value.substring(1);
            funcStub.setCobj(value);
        }
    }


    /**
     * �����ݿ�ȡ�ⲿ�����б�
     * @return ArrayList
     */
    private ArrayList getExtFuncListByDB() {
        JParamObject PO = JParamObject.Create();
        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod(GetRealRptSvr()+"ReportObject", "getExtFuncList", PO);
        if(RO == null){
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,res.getString("String_311"));
            return null;
        }
        if(RO.ErrorCode == -1){
            JOptionPane.showMessageDialog(JActiveDComDM.MainApplication.MainWindow,res.getString("String_312") + RO.ErrorString);
            return null;
        }

        return (ArrayList)RO.ResponseObject;
    }

    /**
     * ��ʼ����������
     * @param XMLObject JXMLBaseObject
     */
    public void InitLimitObjectList(JXMLBaseObject XMLObject) {
        String SecName = "BaseLimitObjects";
        JLimitObjectStub LOS = null;
        java.util.List nodelist;
        Element node;
        Element e;
        int Index = 0;
        e = XMLObject.GetElementByName(SecName);
        if (e == null)return;
        nodelist = XMLObject.BeginEnumerate(e);
        while (nodelist != null) {
            node = XMLObject.Enumerate(nodelist, Index++);
            if (node == null)break;
//      if ( node.getNodeType() == node.ELEMENT_NODE ) {
            e = (Element) node;
            LOS = new JLimitObjectStub();
            //id="YEAR" type="N" help="HelpYear" text="������" des1="" des2=""
            LOS.id = XMLObject.GetElementValue(e, "id");
            LOS.type = XMLObject.GetElementValue(e, "type");
            LOS.help = XMLObject.GetElementValue(e, "help");
            LOS.text = XMLObject.GetElementValue(e, "text");
            LOS.des1 = XMLObject.GetElementValue(e, "des1");
            LOS.des2 = XMLObject.GetElementValue(e, "des2");
            LOS.defvalue = XMLObject.GetElementValue(e, "defvalue");
            LOS.tbName = XMLObject.GetElementValue(e, "tbName");
            LOS.bhCol = XMLObject.GetElementValue(e, "bhCol");
            LOS.mcCol = XMLObject.GetElementValue(e, "mcCol");
            LOS.struCol = XMLObject.GetElementValue(e, "struCol");
            LOS.HelpObject = HelpObject;
            this.LimitObjectList.put(LOS.id, LOS);
//      }
        }
        XMLObject.EndEnumerate();
    }

    /**
     * ��ʼ��ȡ������
     * @param XMLObject JXMLBaseObject
     */
    public void InitDataObjectList(JXMLBaseObject XMLObject) {
        String SecName = "BaseDataObjects";
        JDataObjectStub DOS = null;
        java.util.List nodelist;
        Element node;
        Element e;
        int Index = 0;
        e = XMLObject.GetElementByName(SecName);
        if (e == null)return;
        nodelist = XMLObject.BeginEnumerate(e);
        while (nodelist != null) {
            node = XMLObject.Enumerate(nodelist, Index++);
            if (node == null)break;
//      if ( node.getNodeType() == node.ELEMENT_NODE ) {
            e = (Element) node;
            DOS = new JDataObjectStub();
            //id="YEAR" type="N" help="HelpYear" text="������" des1="" des2=""
            DOS.id = XMLObject.GetElementValue(e, "id");
            DOS.type = XMLObject.GetElementValue(e, "type");
            DOS.text = XMLObject.GetElementValue(e, "text");
            DOS.des = XMLObject.GetElementValue(e, "des1");
            this.DataObjectList.put(DOS.id, DOS);
//      }
        }
        XMLObject.EndEnumerate();
    }

    /**
     * ��ʼ����������
     * @param XMLObject JXMLBaseObject
     */
    public void InitConditionObjectList(JXMLBaseObject XMLObject) {
        String SecName = "BaseConditionObjects";
        JConditionObjectStub COS = null;
        java.util.List nodelist;
        Element node;
        Element e;
        int Index = 0;
        e = XMLObject.GetElementByName(SecName);
        if (e == null)return;
        nodelist = XMLObject.BeginEnumerate(e);
        while (nodelist != null) {
            node = XMLObject.Enumerate(nodelist, Index++);
            if (node == null)break;
//      if ( node.getNodeType() == node.ELEMENT_NODE ) {
            e = (Element) node;
            COS = new JConditionObjectStub();
            //id="YEAR" type="N" help="HelpYear" text="������" des1="" des2=""
            COS.id = XMLObject.GetElementValue(e, "id");
            COS.type = XMLObject.GetElementValue(e, "type");
            COS.text = XMLObject.GetElementValue(e, "text");
            this.ConditionObjectList.put(COS.id, COS);
            CObjList.add(COS);
//      }
        }
        XMLObject.EndEnumerate();
    }

    /**
     * ��ʼ�����������б�
     * @param XMLObject JXMLBaseObject
     */
    public void InitFunctionTypeList(JXMLBaseObject XMLObject) {
        int Index = 0;
        Element e,node;
        String xtbh = null;
        String SecName = "FunctionTypes";
        JFunctionTypeStub FTS = null;
        java.util.List nodelist;
        e = XMLObject.GetElementByName(SecName);
        if (e == null)return;
        nodelist = XMLObject.BeginEnumerate(e);
        while (nodelist != null) {
            node = XMLObject.Enumerate(nodelist, Index++);
            if (node == null)break;
//      if ( node.getNodeType() == node.ELEMENT_NODE ) {
            e = (Element) node;
            FTS = new JFunctionTypeStub();
            //id="YEAR" type="N" help="HelpYear" text="������" des1="" des2=""
            xtbh   = XMLObject.GetElementValue(e, "xtbh","");
            /**
             * �жϵ�ǰϵͳ�Ƿ�
             * �Դ��������Ƿ���ش��ຯ��
             * modified by hufeng 2008.5.26
             */
            if(!isHaveSystem(xtbh)){
                continue;
            }
            FTS.id = XMLObject.GetElementValue(e, "id");
            FTS.text = XMLObject.GetElementValue(e, "text");
            FTS.dobj = XMLObject.GetElementValue(e, "dobj");
            if (FTS.dobj != null && FTS.dobj.trim().length() == 0) FTS.dobj = null;
            FTS.cobj = XMLObject.GetElementValue(e, "cobj");
            if (FTS.cobj != null && FTS.cobj.trim().length() == 0) FTS.cobj = null;
            this.FunctionTypeList.add(FTS);
            InitFunctionList(XMLObject, FTS);
//      }
        }
        XMLObject.EndEnumerate();
    }

    /**
     * ��ʼ�������б�
     * @param XMLObject JXMLBaseObject
     * @param FTS JFunctionTypeStub
     */
    public void InitFunctionList(JXMLBaseObject XMLObject, JFunctionTypeStub FTS) {
        String SecName = FTS.id;
        JFunctionStub FS = null;
        java.util.List nodelist;
        Element node;
        Element e;
        int Index = 0;
        e = XMLObject.GetElementByName(SecName);
        if (e == null)return;
        nodelist = XMLObject.BeginEnumerate(e);
        while (nodelist != null) {
            node = XMLObject.Enumerate(nodelist, Index++);
            if (node == null)break;
//      if ( node.getNodeType() == node.ELEMENT_NODE ) {
            e = (Element) node;
            FS = new JFunctionStub();
            //<F id="BB" ps="4" p0="YEAR" p1="MONTH" p2="BBBH" p3="BBRF" text="������" des1="" des2=""/>
            FS.id = XMLObject.GetElementValue(e, "id");
            FS.ps = Integer.valueOf(XMLObject.GetElementValue(e, "ps")).intValue();
            FS.param = "";
            for (int i = 0; i < FS.ps; i++) {
                FS.param += XMLObject.GetElementValue(e, "p" + String.valueOf(i)) + ",";
            }
            FS.param = FS.param.substring(0, FS.param.length() - 1);
            FS.text = XMLObject.GetElementValue(e, "text");
            FS.des1 = XMLObject.GetElementValue(e, "des1");
            FS.des2 = XMLObject.GetElementValue(e, "des2");

            FS.dobj = XMLObject.GetElementValue(e, "dobj");
            if (FS.dobj != null && FS.dobj.trim().length() == 0) FS.dobj = null;
            FS.cobj = XMLObject.GetElementValue(e, "cobj");
            if (FS.cobj != null && FS.cobj.trim().length() == 0) FS.cobj = null;
            // �������ݶ���
            String Type = XMLObject.GetElementValue(e, "dobj_t");
            if (Type == null || Type.trim().length() == 0) Type = "0";
            if (Type.equals("1")) {
                if (FS.dobj != null) {
                    if (FTS.dobj != null) FS.dobj += "," + FTS.dobj;
                }
                else {
                    FS.dobj = FTS.dobj;
                }
            }
            // ������������
            Type = XMLObject.GetElementValue(e, "cobj_t");
            if (Type == null || Type.trim().length() == 0) Type = "0";
            if (Type.equals("1")) {
                if (FS.cobj != null) {
                    if (FTS.cobj != null) FS.cobj += "," + FTS.cobj;
                }
                else {
                    FS.cobj = FTS.cobj;
                }
            }

            FunctionList.put(FS.id, FS);
            FTS.FunctionList.add(FS);
//      }
        }
        XMLObject.EndEnumerate();
    }

    /**
     * ��ʼ�����ú����б�
     * @param XMLObject JXMLBaseObject
     */
    void InitFavFuncList(JXMLBaseObject XMLObject) {
        String SecName = "FavFuncList";
        String id;
        JFunctionStub FS = null;
        java.util.List nodelist;
        Element node;
        Element e;
        int Index = 0;
        e = XMLObject.GetElementByName(SecName);
        if (e == null)return;
        nodelist = XMLObject.BeginEnumerate(e);
        while (nodelist != null) {
            node = XMLObject.Enumerate(nodelist, Index++);
            if (node == null)break;
//      if ( node.getNodeType() == node.ELEMENT_NODE ) {
            e = (Element) node;
            id = XMLObject.GetElementValue(e, "id");
            FS = (JFunctionStub) FunctionList.get( (Object) id);
            FavFuncList.add(FS);
//      }
        }
        XMLObject.EndEnumerate();
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:Object
    //------------------------------------------------------------------------------------------------
    public void setReportModel(JReportDataModel RM, Object RB) {
        ReportModel = (JReportModel) RM;
        //
        ReportBook = (JReportBook) RB;

        WizardInterface = ReportBook;
        ReportView = RM.ReportView;
        // ��ʼ���ؼ�һ
        BookTextField = ReportModel.ReportView.BookTextField;
        BookComboBox = ReportModel.ReportView.BookComboBox;
        // ��ʼ���ؼ���
        bnCancelSum = ReportModel.ReportView.bnCancelSum;
        bnDHOK = ReportModel.ReportView.bnDHOK;
        bnFuncWzd = ReportModel.ReportView.bnFuncWzd;
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:HideWizard
    //------------------------------------------------------------------------------------------------
    public void ShowWizard(int x, int y, Frame frame, JReportDataModel RM, Object RB) {
        if (WizardDialog == null) {
            WizardDialog = new JFunctionWizardDialog(frame, res.getString("String_358"), false);
            WizardDialog.WizardObject = this;
            MainFrame = frame;
        }
        setReportModel(RM, RB);
        WizardDialog.setLocation(x, y);
        WizardDialog.Show();
    }

    /**
     * �ⲿʹ�����
     * @param x int
     * @param y int
     * @param frame Frame
     * @param RM JReportDataModel
     * @param RB Object
     */
    public void ShowExtendWizard(int x, int y, Frame frame, JReportDataModel RM, Object RB) {
        mIsReportWinzard = false;
        if (WizardDialog == null) {
            WizardDialog = new JFunctionWizardDialog(frame, res.getString("String_359"), true);
            WizardDialog.WizardObject = this;
            MainFrame = frame;
        }
        setReportModel(RM, RB);
        WizardDialog.setLocation(x, y);
        WizardDialog.Show();
    }

    /**
     * ����������
     *
     * @param map HashMap
     */
    public void setCustomFunctionMap(HashMap map) {
        this.mFuncMap = null;
        this.mFuncMap = map;
    }


    public void ShowCustomWizard(int x, int y, Frame frame, JReportDataModel RM) {
        mIsReportWinzard = false;
        if (WizardDialog == null) {
            WizardDialog = new JFunctionWizardDialog(frame, res.getString("String_359"), true);
            WizardDialog.WizardObject = this;
            MainFrame = frame;
        }
        WizardDialog.isCustom = true;
        WizardDialog.setCustomFunctionMap(mFuncMap);
        setReportModel(RM, null);
        WizardDialog.setLocation(x, y);
        WizardDialog.Show();
    }

    //------------------------------------------------------------------------------------------------
    //����:
    //���: Skyline(2001.12.29)
    //ʵ��: Skyline
    //�޸�:
    //------------------------------------------------------------------------------------------------
    public void HideWizard() {
        if (WizardDialog == null)return;
        WizardDialog.Hide();
        if (WizardInterface != null)
            WizardInterface.WizardDialogClose();
    }

    /**
     * �Ƿ��Ǳ��ڹ�ʽ��
     * @return boolean
     */
    public boolean isReportWinzard() {
        return mIsReportWinzard;
    }

    public String getFuncString() {
        return WizardDialog.getFuncString();
    }

    /**
     * ȡ�ⲿ�����б�
     * @return ArrayList
     */
    public ArrayList getExtFuncList(){
        return mExtFuncList;
    }
}
