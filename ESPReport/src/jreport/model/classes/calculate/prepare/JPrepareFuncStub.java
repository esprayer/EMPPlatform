package jreport.model.classes.calculate.prepare;

import java.util.*;
import jreportfunction.pub.JReportPubFunc;
import com.pub.util.StringFunction;
import java.util.ResourceBundle;
import jtoolkit.string.classes.TString;

/**
 * <p>Title: Ԥ���?ʽ����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: pansoft</p>
 * @author not attributable
 * @version 1.0
 */
public class JPrepareFuncStub {
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.calculate.prepare.Language");
    private String    ID           = null; // ����ID
    private String    funcXh       = null; // �������
    private String    funcString   = null; // ��������
    private String    hzdx         = null; // ���ܶ���
    private ArrayList hzdxList     = null; // ���ܶ���
    private String    dobj         = null; // ȡ�����
    private ArrayList dobjList     = null; // ȡ�����
    private HashMap   resultMap    = null; // ������


    public String getFuncString() {
        return funcString;
    }

    public String getFuncXh() {
        return funcXh;
    }

    public ArrayList getHzdxList() {
        return hzdxList;
    }

    public HashMap getResultMap() {
        return resultMap;
    }

    public void setFuncString(String funcString) {
        this.funcString = funcString;
    }

    public void setFuncXh(String funcXh) {
        this.funcXh = funcXh;
    }

    public void setHzdxList(ArrayList hzdxList) {
        this.hzdxList = hzdxList;
    }

    /**
     * ���û��ܶ���
     * Ҫ���ַ��͵Ķ���ָ����ַ��б�
     * @param hzdx String
     */
    public void setHzdx(String hzdx) {
        String dxList[] = hzdx.split(":");
        hzdxList = new ArrayList();
        for(int i=0; i<dxList.length;i++){
            hzdxList.add(dxList[i]);
        }
        this.hzdx  = hzdx;
    }


    public String getHzdx(){
        return hzdx;
    }

    /**
     * ����ȡ�����
     * Ҫ���ַ��͵Ķ���ָ����ַ��б�
     * @param hzdx String
     */
    public void setDobj(String obj) {
        dobjList = new ArrayList();
        String dxList[] = obj.split(":");
        for(int i=0; i<dxList.length;i++){
            dobjList.add(dxList[i]);
        }
        this.dobj  = obj;
    }


    public String getDobj(){
        return dobj;
    }

    public ArrayList getDobjList() {
        return dobjList;
    }

    public String getID() {
        return ID;
    }

    public void setResultMap(HashMap resultMap) {
        this.resultMap = resultMap;
    }

    public void setDobjList(ArrayList dobjList) {
        this.dobjList = dobjList;
    }

    public void setID(String ID) {
        this.ID = ID;
    }


    /**
     * ����ڲ�������ȡ������
     * @param dobj String
     * @param cobj String
     * @return Object
     */
    public Object getResult(String qsdx,String cond){
        if(resultMap == null){
            return null;
        }
        if(dobj.indexOf(qsdx) < 0){
            System.out.println(TString.F(res.getString("String_3") ,funcXh, qsdx));
            return null;
        }
        String key = getKey(cond);
        key += "_" + qsdx;

        Object obj = resultMap.get(key);
        // �����nullֵ�Ĵ���
        if(obj == null){
            return "0";
        }
        return obj;
    }


    /**
     * ����ڲ�������ȡ������
     * @param qsdx String
     * @param cond String
     * @return Object
     */
    private Object getResultByLike(String qsdx,String cond){
        return null;
    }


    /**
     * ����ڲ���ʽ�������γɶ�Ӧ�ļ�ֵ
     * @param cond String
     * @return String
     */
    private String getLikeKey(String cond) {
        String key = "";
        String sTemp,value;
        for(int i=0; i<hzdxList.size(); i++){
            value = "";
            sTemp = JReportPubFunc.asRightValue(cond,hzdxList.get(i).toString()," 2 > 1 ");
            if(!sTemp.trim().equals("")) {
                sTemp = sTemp.substring(sTemp.indexOf("$@$@$@") + 6);
                value = sTemp.substring(0, sTemp.indexOf("$@$@$@")).trim();
                cond = sTemp.substring(sTemp.indexOf("$@$@$@") + 6);
            }
            if (i != 0){
                key += "_";
            }
            value = StringFunction.replaceString(value,"'","");
            value = StringFunction.replaceString(value,"\"","");
            key += value;
        }

        return key;
    }

    /**
     * ����ڲ���ʽ�������γɶ�Ӧ�ļ�ֵ
     * @param cond String
     * @return String
     */
    private String getKey(String cond) {
        String key = "";
        String sTemp,value;
        for(int i=0; i<hzdxList.size(); i++){
            value = "";
            sTemp = JReportPubFunc.asRightValue(cond,hzdxList.get(i).toString()," 2 > 1 ");
            if(!sTemp.trim().equals("")) {
                sTemp = sTemp.substring(sTemp.indexOf("$@$@$@") + 6);
                value = sTemp.substring(0, sTemp.indexOf("$@$@$@")).trim();
                cond = sTemp.substring(sTemp.indexOf("$@$@$@") + 6);
            }
            if (i != 0){
                key += "_";
            }
            value = StringFunction.replaceString(value,"'","");
            value = StringFunction.replaceString(value,"\"","");
            key += value;
        }

        return key;
    }



}
