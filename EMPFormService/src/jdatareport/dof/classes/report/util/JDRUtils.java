package jdatareport.dof.classes.report.util;

import java.util.*;

import org.jdom.*;
import com.eai.frame.fcl.classes.message.*;
import com.f1j.swing.*;
import com.pansoft.pub.util.*;
import jdatareport.dof.classes.report.*;
import jformservice.jdof.classes.*;
import jfoundation.bridge.classes.*;
import jfoundation.dataobject.classes.*;
import jframework.foundation.classes.*;
import jtoolkit.xml.classes.*;

/**
 *
 * <p>Title: JDRUtils</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */
public final class JDRUtils {
    /**
     * 合并参数列表
     * @param p0
     * @param p1
     * @return
     */
    public static Hashtable join(Hashtable p0, Hashtable p1) {
        if (p0 != null && p1 != null) {

            Hashtable p = new Hashtable();
            for (Enumeration e = p0.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                String value = (String) p0.get(key);
                p.put(key, value);
            }

            for (Enumeration e = p1.keys(); e.hasMoreElements(); ) {
                String key = (String) e.nextElement();
                if (!p0.containsKey(key)) {
                    p.put(key, p1.get(key));
                }
            }

            return p;
        }
        return p0;
    }

    /**
     * 解析参数
     * @param params
     * @return
     */
    public static Hashtable parseParams(String params) {
        try {
            if (params != null && params.length() > 0) {
                String[] keys = params.split(";");
                Hashtable map = new Hashtable();
                for (int i = 0; i < keys.length; i++) {
                    String crtStr = keys[i];
                    int index = crtStr.lastIndexOf("@") + 1;
                    String key = crtStr.substring(1, index - 1); //去除@
                    String value = crtStr.substring(index);
                    map.put(key, value);
                }
                return map;
            }
        }
        catch (Exception e) {
            System.out.println("解析参数出现错误:" + params);
            e.printStackTrace();
        }
        return new Hashtable();
    }

    /**
     * 解析参数
     * 形式:BBLX:TZ01,BBBH:0110,CS:@CQSM@;@YYYY@2004
     * @param params
     * @return
     */
    public static Hashtable parseQueryParams(String params) {
        try {
            if (params != null && params.length() > 0) {
                String[] keys = params.split(",");
                Hashtable map = new Hashtable();
                for (int i = 0; i < keys.length; i++) {
                    String crtStr = keys[i];
                    int index = crtStr.indexOf(":");
                    String key = crtStr.substring(0, index); //去除:
                    String value = crtStr.substring(index + 1);
                    map.put(key, value);
                }
                return map;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断相等
     * @param params0
     * @param params1
     * @return
     */
    public static boolean equals(Hashtable params0, Hashtable params1) {
        //相等,包括均为空
        if (params0 == null && params1 == null) {
            return true;
        }
        //非空
        if (params0 == null || params1 == null) {
            return false;
        }

        if (params0.size() != params1.size()) {
            return false;
        }
        //键和值相等
        for (Enumeration e = params0.keys(); e.hasMoreElements(); ) {
            String key0 = (String) e.nextElement();
            String val0 = (String) params0.get(key0);

            if (params1.containsKey(key0)) {
                String val1 = (String) params1.get(key0);
                if (!val0.equals(val1)) {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        return true;
    }

    /**
     * 加载数据模型
     * @param rptName
     * @return
     */
    public static JDRModel loadModel(String rptName) {
        String XMLStr = loadResource(rptName);
        if (XMLStr == null || XMLStr.length() == 0) {
            Debug.PrintlnMessageToSystem("报表定义文件加载无法正常加载");
            return null;
        }
        JDRModel model = new JDRModel(new JXMLBaseObject(XMLStr));
        return model;
    }

    /**
     * 加载资源
     * @param reportName
     * @return
     */
    public static String loadResource(String reportName) {
        String XMLStr = "";
        JParamObject PO = JParamObject.Create();
        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("DataReport", "OpenReport", PO, reportName);
        if (RO != null && RO.ResponseObject != null) {
            XMLStr = (String) RO.ResponseObject;
        }
        return XMLStr;
    }

    /**
     * 打开数据窗口
     * @param formName
     * @param params
     * @return
     */
    public static JDataWindow openForm(String formName, Hashtable params) {
        if (formName != null) {
            //打开数据窗口
            JDataWindow form = new JDataWindow();
            form.Prepare();
            ExchangeMessage Extmsg = new ExchangeMessage("", "");
            if (params != null && params.size() > 0) {
                Message SqlValues = new Message();
                for (Iterator i = params.keySet().iterator(); i.hasNext(); ) {
                    String key = (String) i.next();
                    String value = (String) params.get(key);
                    SqlValues.CreateItem(key, value);
                }
                Extmsg.getInputMessage().CreateItem("SqlValues", SqlValues);
            }
            form.Open(formName, Extmsg);
            form.getDataForm().PrepareUI();
            return form;
        }
        return null;
    }

    /**
     * 打开查询结果
     */
    public static String[] openQuery(Hashtable params) {

        JParamObject PO = new JParamObject();

        for (Enumeration e = params.keys(); e.hasMoreElements(); ) {
            String key = (String) e.nextElement();
            String val = (String) params.get(key);
            PO.SetValueByParamName(key, val);
        }
        String[] queryResult = new String[2];
        JResponseObject RO = (JResponseObject) JActiveDComDM.AbstractDataActiveFramework.InvokeObjectMethod("DataReport", "OpenQuery", PO, "");
        String XMLStr = (String) RO.ResponseObject;
        if (RO != null && RO.ResponseObject != null) {
            XMLStr = (String) RO.ResponseObject;
            JXMLBaseObject XMLObj = new JXMLBaseObject(XMLStr);
            Element queryElmt = XMLObj.GetElementByName("Query");
            //格式
            queryResult[0] = queryElmt.getAttributeValue("QueryFormat");
            //数据
            queryResult[0] = queryElmt.getAttributeValue("QueryData");
        }
        return queryResult;
    }

    /**
     *
     * @param book
     * @param startRow
     * @param startCol
     * @param endRow
     * @param endCol
     * @return
     */
    public static String coor2Formula(JBook book, int startRow, int startCol, int endRow, int endCol) {
        String T1 = null, T2 = null, Text = null;
        try {
            T1 = book.formatRCNr(startRow, startCol, false);
            if (startRow == endRow &&
                startCol == endCol) {
                Text = T1;
            }
            else {
                T2 = book.formatRCNr(endRow, endCol, false);
                Text = T1 + ":" + T2;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return Text;
    }
}