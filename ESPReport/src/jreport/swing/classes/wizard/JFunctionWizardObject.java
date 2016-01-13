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
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JFunctionWizardObject {

    static ResourceBundle res = ResourceBundle.getBundle("jreport.swing.classes.wizard.Language");
    // 外部函数列表
    private static ArrayList mExtFuncList = null;
    // 系统列表
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
    // 限制对象列表
    public Hashtable LimitObjectList = new Hashtable();
    // 数据对象列表
    public Hashtable DataObjectList = new Hashtable();
    // 条件对象列表
    public Hashtable ConditionObjectList = new Hashtable();
    // 条件对象列表
    public Vector CObjList = new Vector();
    // 函数类型列表
    public Vector FunctionTypeList = new Vector();
    // 函数列表
    public Hashtable FunctionList = new Hashtable();
    // 常用函数列表
    public Vector FavFuncList = new Vector();
    public JWizardHelpObject HelpObject = new JWizardHelpObject();
    public JXMLBaseObject XMLObject = null;

    /**
     * 是否报表内的向导模式
     * 如果不是表内向导模式，则不需要保存公式到报表
     */
    private boolean mIsReportWinzard = true;

    /**
     * 为了支持自定义公式向导中显示什么函数而加，报告系统用
     * add by gengeng 2008-8-4
     */
    public boolean isCustom;
    public HashMap mFuncMap;

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public JFunctionWizardObject() {
    }

    /**
     * 初始化函数列表
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
         * 取出当前系统
         * 判断函数类别的系统是否已安装
         * 如果未安装不用再加载此类函数
         * modified by hufeng 2008.5.26
         */
 //lk       InitSystemList();

        // 1.初始化参数对象
        InitLimitObjectList(XMLObject);
        // 2.初始化数据对象
        InitDataObjectList(XMLObject);
        // 3.初始化条件对象
        InitConditionObjectList(XMLObject);
        // 4.初始化函数类型列表
        InitFunctionTypeList(XMLObject);
        // 5.初始化常用函数列表
        InitFavFuncList(XMLObject);
        // 6.初始化外部函数列表
        InitExtendFunction();

        HelpObject.WizardObject = this;
    }

    /**
     * 初始化系统列表
     */
    private void InitSystemList() {
        if(mXtbhList != null){
            return;
        }
        JParamObject PO = JParamObject.Create();
        /**
         * 后台调用时，此值为空
         * 以此为判断是前台判断还是后台判断
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
     * 是否包含指定的系统
     * @param xtbh String
     * @return boolean
     */
    private boolean isHaveSystem(String xtbh){
        // 默认为系统函数，需要加载
        if(xtbh == null || xtbh.trim().equals("")){
            return true;
        }
        if(mXtbhList == null){
            return true;
        }

        return mXtbhList.contains(xtbh);
    }

    /**
     * 初始化外部函数
     * 在后台初始化时，需要用外部函数列表来初始化
     */
    private void InitExtendFunction() {
        ArrayList extFuncList = JFunctionManager.getExtendFunctionList();
        if(extFuncList == null || extFuncList.size() == 0){
            return;
        }
        mExtFuncList = extFuncList;

        // 加入外部函数类别
        JFunctionTypeStub FTS = new JFunctionTypeStub();
        FTS.setId("EXTEND");
        FTS.setText("外部函数");
        FunctionTypeList.add(FTS);
        // 将函数列表加入函数类型中
        JFunctionStub funcStub;
        JExtFuncStub  extStub;
        int count = mExtFuncList.size();
        for(int i=0; i<count; i++){
            funcStub = new JFunctionStub();
            extStub = (JExtFuncStub)mExtFuncList.get(i);
            //
            funcStub.setId(extStub.getFunc());
            funcStub.setPs(extStub.getParamCount() + 4); // 默认有四个参数（年，月，取数对象，条件对象）
            funcStub.setText(extStub.getName());
            funcStub.setDes1(extStub.getFmDesc());
            funcStub.setDes2(extStub.getFnDesc());
            // 初始化外部函数参数对象
            initExtFuncPobj(funcStub,extStub);
            // 初始化外部函数条件对象
            initExtFuncCobj(funcStub,extStub);
            // 初始化外部函数取数对象
            initExtFuncDobj(funcStub,extStub);
            //
            FunctionList.put(funcStub.getId(),funcStub);
            FTS.FunctionList.add(funcStub);
        }

    }

    /**
     * 初始化外部函数参数对象
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
            LOS.setDefvalue(""); // 默认值
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
     * 初始化外部函数取数对象
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
            // 如果取数对象列表中不存在，则需要加入
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
     * 初始化外部函数条件对象
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
            // 如果条件列表中不存在，则需要加入
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
     * 从数据库取外部函数列表
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
     * 初始化参数对象
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
            //id="YEAR" type="N" help="HelpYear" text="会计年度" des1="" des2=""
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
     * 初始化取数对象
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
            //id="YEAR" type="N" help="HelpYear" text="会计年度" des1="" des2=""
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
     * 初始化条件对象
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
            //id="YEAR" type="N" help="HelpYear" text="会计年度" des1="" des2=""
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
     * 初始化函数类型列表
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
            //id="YEAR" type="N" help="HelpYear" text="会计年度" des1="" des2=""
            xtbh   = XMLObject.GetElementValue(e, "xtbh","");
            /**
             * 判断当前系统是否
             * 以此来决定是否加载此类函数
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
     * 初始化函数列表
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
            //<F id="BB" ps="4" p0="YEAR" p1="MONTH" p2="BBBH" p3="BBRF" text="报表函数" des1="" des2=""/>
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
            // 处理数据对象
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
            // 处理条件对象
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
     * 初始化常用函数列表
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
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:Object
    //------------------------------------------------------------------------------------------------
    public void setReportModel(JReportDataModel RM, Object RB) {
        ReportModel = (JReportModel) RM;
        //
        ReportBook = (JReportBook) RB;

        WizardInterface = ReportBook;
        ReportView = RM.ReportView;
        // 初始化控件一
        BookTextField = ReportModel.ReportView.BookTextField;
        BookComboBox = ReportModel.ReportView.BookComboBox;
        // 初始化控件二
        bnCancelSum = ReportModel.ReportView.bnCancelSum;
        bnDHOK = ReportModel.ReportView.bnDHOK;
        bnFuncWzd = ReportModel.ReportView.bnFuncWzd;
    }

    //------------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:HideWizard
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
     * 外部使用向悻
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
     * 报告生成用
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
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //------------------------------------------------------------------------------------------------
    public void HideWizard() {
        if (WizardDialog == null)return;
        WizardDialog.Hide();
        if (WizardInterface != null)
            WizardInterface.WizardDialogClose();
    }

    /**
     * 是否是表内公式向导
     * @return boolean
     */
    public boolean isReportWinzard() {
        return mIsReportWinzard;
    }

    public String getFuncString() {
        return WizardDialog.getFuncString();
    }

    /**
     * 取外部函数列表
     * @return ArrayList
     */
    public ArrayList getExtFuncList(){
        return mExtFuncList;
    }
}
