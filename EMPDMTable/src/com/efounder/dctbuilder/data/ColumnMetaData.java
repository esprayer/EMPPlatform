package com.efounder.dctbuilder.data;

import com.core.xml.*;
import java.util.*;
import com.efounder.service.meta.db.*;
import com.efounder.builder.base.data.*;
import com.pub.util.ESPKeyValueUtil;
import com.pub.util.StringFunction;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ColumnMetaData extends StubObject{
	//外键编辑方式
    public static final int EL_NOVISIBLE    = 0;
    public static final int EL_STRING = 1;
    public static final int EL_INT = 2;
    public static final int EL_FLOAT = 3;
    public static final int EL_BOOL = 4;
    public static final int EL_DATE = 5;
    public static final int EL_POPUP = 6;  //下拉box
    public static final int EL_WINDOW = 7; //弹出窗口(实际为密码框，借用了)
    public static final int EL_TT = 8;     //TreeTable
    //弹出窗口，结合COL_OPT中的PopupClass=
    //如果DialogClass未指定，则视为普通的字符编辑
    //提供获取值的接口
    public static final int EL_DIALOG = 9;
    public static final int EL_DATETIME = 10;
    public static final int EL_YEAR = 11;
    public static final int EL_MONTH = 12;
    public static final int EL_DAY = 13;
    public static final int EL_TIME = 14;
    public static final int EL_SELFENUM = 15; //自列表
    public static final int EL_PRO_BAR = 16; //进度条
    public static final int EL_TEXT = 17; //长文本
    
    //外键名称显示方式
    public static final String   FKEY_SHOWMODE_BH = "1"; //显示编号
    public static final String   FKEY_SHOWMODE_MC = "2"; //显示名称
    public static final String FKEY_SHOWMODE_BHMC = "3"; //显示编号名称

    /**
     *
     * @return ColMetadata
     */
	public static ColumnMetaData getInstance() {
     return new ColumnMetaData(new StubObject());
	}
   
    public ColumnMetaData(StubObject cmd) {
        if(cmd!=null)
            assignStubObject(cmd);
    }

    public boolean isMLang() {
        return "1".equals(getString("COL_LANG", "0"));
    }

    public String getColType(){
        return getString("COL_TYPE","");
    }
    public String getColid(){
        return getString("COL_ID","").trim();
    }
    public String getColMc(){
        return getString("COL_MC","");
    }
    public int getColDataLen(){
        return getInt("COL_LEN",0);
    }
    public int getColDispLen(){
        return getInt("COL_PREC",10);
    }
    public int getColScale(){
        return getInt("COL_SCALE",0);
    }
    public boolean isNull(){
    	int nvl = getInt("COL_ISNULL",0);
        return (nvl==1);
    }

    public boolean isUnique() {
        return getString("COL_CHECK", "0").equals("1");
    }

    public String getRegexID() {
        return getString("COL_REGEXREF", "");
    }

    public int getColEditType(){
      Object oo=getObject("COL_EDIT","0");
      if(oo instanceof Integer)
        return ((Integer)oo).intValue();
      String o=getString("COL_EDIT","0").trim();
      if (o.length() == 0) o = "1";
        return Integer.parseInt(o);
    }
    public boolean isFKEY(){
        return getInt("COL_ISFKEY",0)==1;
    }
    public String getFOBJ(){
        return getString("COL_FOBJ","");
    }

    public String getColView(){
        return getString("COL_VIEW","");
    }

    public boolean isEditable() {
        String editable = getString("COL_EDITABLE", "1");
        return "1".equals(editable);
    }

    public boolean isVisible(){
        String visible = getString("COL_VISIBLE", "1");
        return "1".equals(visible);
    }

    public int getColDisp(){
        return getInt("COL_DISP",0);
    }

    public String getEditPattern(){
        switch (getColEditType()) {
	        case EL_FLOAT: {
	            int scale = getColScale();
	            String strFmt = ".00;-.00";
	            if (scale > 0) {
	                String strScaleDots = StringFunction.
	                                      fillString("", '0', scale);
	                strFmt = "." + strScaleDots + ";-." + strScaleDots;
	            }
	            return strFmt;
	        }
        }
        return null;
    }
    
    public String getHelpClass(){
       String opt= getString("COL_OPT","");
       if(opt!=null&&opt.length()>12&&opt.toUpperCase().indexOf("HELPCLASS=")!=-1){
           String cls=opt.substring(opt.toUpperCase().indexOf("HELPCLASS=")+10);
           if (cls.indexOf(";") > 0) cls=cls.substring(0,cls.indexOf(";"));
           return cls;
       }
       return "";
    }

    public String getHelpFObj(){
        String opt = getString("COL_OPT", "");
        if (opt.toUpperCase().indexOf("HELPFOBJ=") != -1) {
            opt = opt.substring(opt.toUpperCase().indexOf("HELPFOBJ=") + 9);
            if (opt.indexOf(";") >= 0) {
                opt = opt.substring(0, opt.indexOf(";"));
            }
            return opt;
        }
        return "";
    }

    public String getHelpFColumn(){
        String opt = getString("COL_OPT", "");
        if (opt.toUpperCase().indexOf("HELPCOLUMN=") != -1) {
            opt = opt.substring(opt.toUpperCase().indexOf("HELPCOLUMN=") + 11);
            if (opt.indexOf(";") >= 0) {
                opt = opt.substring(0, opt.indexOf(";"));
            }
            return opt;
        }
        return "";
    }

    /**
     * SQL语法的过滤条件
     * @return String
     */
    public String getHelpFWhere(){
    	//列扩展属性上定义的过滤条件
        String opt = getString("COL_OPT", "");
        if (opt.toUpperCase().indexOf("HELPFWHERE=") != -1) {
            opt = opt.substring(opt.toUpperCase().indexOf("HELPFWHERE=") + 11);
            if (opt.indexOf(";") >= 0) {
                opt = opt.substring(0, opt.indexOf(";"));
            }
            return opt;
        }
        return "";
    }

    /**
     *
     * @param newWhere
     */
    public void setHelpFWhwere(String newWhere) {
        if (newWhere == null || newWhere.trim().length() == 0) return;
        String oldWhere = getHelpFWhere();
        String  COL_OPT = getString("COL_OPT", "");
        COL_OPT = COL_OPT.replaceAll(oldWhere, newWhere);
        setString("COL_OPT", COL_OPT);
    }

    /**
     * JAVA语法的过滤条件
     * @return String
     */
    public String getHelpFliterExp() {
        String opt = getString("COL_OPT", "");
        if (opt.toUpperCase().indexOf("HELPFILTEREXP=") != -1) {
            opt = opt.substring(opt.toUpperCase().indexOf("HELPFILTEREXP=") + 14);
            if (opt.indexOf(";") >= 0) {
                opt = opt.substring(0, opt.indexOf(";"));
            }
            return opt;
        }
        return "";
    }

	/**
	 *
	 * @param newFliterExp
	 */
	public void setHelpFliterExp(String newFliterExp) {
		if (newFliterExp == null || newFliterExp.trim().length() == 0)
			return;
		String oldFliterExp = getHelpFliterExp();
		String COL_OPT = getString("COL_OPT", "");
		COL_OPT = COL_OPT.replaceAll(oldFliterExp, newFliterExp);
		setString("COL_OPT", COL_OPT);
	}

    public String getFKeyShowMode(){
        String opt = getString("COL_OPT", "").toUpperCase();
        if (opt.indexOf("FKEYSHOWMODE=") != -1) {
            opt = opt.substring(opt.indexOf("FKEYSHOWMODE=") + 13);
            if (opt.indexOf(";") >= 0) {
                opt = opt.substring(0, opt.indexOf(";"));
            }
            return opt;
        }
        return FKEY_SHOWMODE_MC;
    }

    /**
     * 获取弹出窗口的类名
     *
     * @return String
     */
    public String getPopupClass() {
        String opt = getString("COL_OPT", "");
        if (opt.toUpperCase().indexOf("POPUPCLASS=") != -1) {
            opt = opt.substring(opt.toUpperCase().indexOf("POPUPCLASS=") + 11);
            if (opt.indexOf(";") >= 0) {
                opt = opt.substring(0, opt.indexOf(";"));
            }
            return opt;
        }
        return "";
    }

    public String getDispPattern(){
        switch(getColEditType()){
	        case EL_FLOAT: {
	            int scale = getColScale();
	            String strFmt = "#,##0.00;-#,##0.00";
	            if (scale > 0) {
	                String strScaleDots = StringFunction.fillString("", '0', scale);
	                strFmt = "#,##0." + strScaleDots + ";-#,##0." + strScaleDots;
	            }
	            return strFmt;
	        }
	        case EL_DATE:{
	        	String strFmt = "yyyy-MM-dd";
	            return strFmt;
	        }
        }
        return null;
    }
    public DictMetadata getFKMetaData(){
        return (DictMetadata)getObject(DctConstants.METADATA,null);
    }
    public void setFKMetaData(Object o){
        setObject(DctConstants.METADATA,o);
    }
    public EFDataSet getFKDataMap(){
        return (EFDataSet)getObject("DATAMAP",null);
    }
    public void setFKDataSet(Object map){
        setObject("DATAMAP",map);
    }
    public void setEditor(Object o){
        setObject("EDITOR",o);
    }
    public Object getEditor(){
        return getObject("EDITOR",null);
    }

    public void setRender(Object o) {
        setObject("RENDER", o);
    }

    public Object getRender() {
        return getObject("RENDER",null);
    }

    public String toString() {
        return getColMc();
    }

    public boolean isKey(){
        return "1".equals(getString("COL_ISKEY", ""));
    }

    //是否是计算列
    public boolean isCaculateColumn() {
        return "1".equals(getString("COL_ISCALC", ""));
    }

    //获取计算公式
    public String getCaculateFunction() {
        return getString("COL_COLGS", "").toUpperCase();
    }

    /**
     * 从指定扩展属性列中获取某个key的值
     * @param col String
     * @param connector String
     * @param delimeter String
     * @param key String
     * @return String
     */
    public String getExtAttriValue(String col, String connector, String delimeter, String key) {
        String value = getString(col, "");
        if(value == null){
        	value = "";
        }
        java.util.Map map = ESPKeyValueUtil.getKeyValue(connector, delimeter, value);
        if (map != null && !map.isEmpty()) {
        	Iterator keys = map.keySet().iterator();
        	String key1  = "";
        	while(keys.hasNext()){
        		key1 = keys.next().toString();
        		if(key1.toUpperCase().equals(key.toUpperCase())){
        			return (String) map.get(key1);
        		}
        	}
        	
        }
        return null;
    }

	/**
     *
     */
	public DictMetadata refDictmetadata = null;
    /**
     *
     * @return DictMetadata
     */
	public DictMetadata getRefDictmetadata() {
		return refDictmetadata;
	}
}
