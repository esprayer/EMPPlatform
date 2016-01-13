package com.help;

import com.core.xml.*;
import com.efounder.dctbuilder.data.*;

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
public class HelpInfoContext
    extends StubObject {
    public static String HELPTREEMODEL = "HELPTREEMODEL";
    DataDicDefine dictInfo = new DataDicDefine();
    transient Object obj=null;
    public HelpInfoContext() {
    }
    public Object getFilterObject(){
        return obj;
    }

    public void setFilterObject(Object o) {
        obj = o;
    }
    public Object getDataObject(){
       return this.getObject("DATA",null);
   }

   public void setDataObject(Object o) {
       this.setObject("DATA",o);
   }

    public boolean isMulSel() {
        return this.getBoolean("MULSEL", false);
    }

    public void setMulSel(boolean b) {
        this.setBoolean("MULSEL", b);
    }

    public DataDicDefine getDictInfo() {
        return dictInfo;
    }

    public String getModelID() {
        return  getString("MDL_ID", null);
    }

    public void setDictInfo(DataDicDefine in) {
        dictInfo = in;
    }

    public void setModelID(String modelID) {
         setString("MDL_ID", modelID);
    }

    public void setZZJG_CODE(String code) {
        setString("ZZJG_CODE", code);
    }

    public String getZZJG_CODE() {
        return getString("ZZJG_CODE", "");
    }
    
    //设置使用自定义的核算主体否
    public void setUseSelfZZJG_CODE(boolean use) {
    	setBoolean("UseSelfZZJG_CODE", use);
    }
    
    //是否使用自定义的核算主体
    public boolean isUseSelfZZJG_CODE() {
        return getBoolean("UseSelfZZJG_CODE", false);
    }
    
    public void setDCTID(String k) {
        dictInfo.setDicID(k);
    }

    public String getDCTID() {
        return dictInfo.getDicID();
    }

    public void setSXCol(String k) {
        setString("SXKEY", k);
    }

    public String getSXCol() {
        return getString("SXKEY", null);
    }

    public void setSXBM(String k) {
        setString("SXBM", k);
    }

    public String getSXBM() {
        return getString("SXBM", null);
    }

    public void setFKeyInfo(HelpInfoContext tc) {
        setObject("FKEYINFO", tc);
    }

    public HelpInfoContext getFKeyInfo() {
        return (HelpInfoContext) getObject("FKEYINFO", null);
    }

    public void setCurBM(String bm) {
        setString("CURBM", bm);
    }

    public String getCurBM() {
        return getString("CURBM", null);
    }

    public void setCurJS(String js) {
        setString("CURJS", js);
    }

    public String getCurJS() {
        return getString("CURJS", "1");
    }

    public void setDctMC(String dctMC) {
        dictInfo.setDicMC(dctMC);
    }

    public String getDctMC() {
        return dictInfo.getDicMC();
    }

    public void setWhere(whereItem where) {
        setObject("WHERE", where);
    }

    public whereItem getWhere() {
        return (whereItem) getObject("WHERE", null);
    }

    public void setWhere(String where) {
        setString("WHERECLAUSE", where);
    }

    public String getWhereClause() {
        return getString("WHERECLAUSE", "");
    }

    public void setBHColumn(String col) {
        dictInfo.setDicFieldBH(col);
    }

    public void setMCColumn(String col) {
        dictInfo.setDicFieldName(col);
    }

    public void setJSColumn(String col) {
        dictInfo.setDicFieldJS(col);
    }

    public void setMXColumn(String col) {
        dictInfo.setDicFieldMX(col);
    }

    public void setBMStru(String stru) {
        dictInfo.setDicBHStruct(stru);
    }
    public String getBMStru(){
        return dictInfo.getDicBHStruct();
    }
    public String getBHColumn() {
        return dictInfo.getDicFieldBH();
    }

    public String getMCColumn() {
        return dictInfo.getDicFieldName();
    }

    public String getJSColumn() {
        return dictInfo.getDicFieldJS();
    }

    public String getMXColumn() {
        return dictInfo.getDicFieldMX();
    }

    public String getPTColumn() {
        return dictInfo.getDicFieldParent();
    }

    public void setPTColumn(String s) {
        dictInfo.setDicFieldParent(s);
    }

    public String getQXBH() {
        return getString("QXBH", null);
    }

    public void setQXBH(String s) {
        setString("QXBH", s);
    }

    public void setZrzx(String zrzx) {
        setString("F_CODE", zrzx);
    }

    public String getZrzx() {
        return getString("F_CODE", null);
    }
    
    public void setQXBZW(String s) {
        setString("QXBZW", s);
    }

    public String getQXBZW() {
        return getString("QXBZW", DctConstants.DCTREADBIT);
    }

    public String[] getHelpColumn() {
        return (String[]) getObject("HLPCOL", new String[] {this.getBHColumn(),
                                    this.getMCColumn()});
    }

    public void setHelpColumn(String[] s) {
        setObject("HLPCOL", s);
    }

    public String[] getHelpCapiton() {
        return (String[]) getObject("HLPCAP", new String[] {"编号", "名称"});
    }

    public void setHelpCapiton(String[] s) {
        setObject("HLPCAP", s);
    }

    public boolean isBufData() {
        return this.getBoolean("BUFFER", true);
    }

    public void setBufdata(boolean b) {
        setBoolean("BUFFER", b);
    }

    public boolean isGradeQuery() {
        return this.getBoolean("GRADEQUERY", true);
    }

    public void setGradeQuery(boolean b) {
        setBoolean("GRADEQUERY", b);
    }

    public void setSelValue(String s) {
        setString("SELVALUE", s);
    }

    public String getSelValue() {
        return getString("SELVALUE", null);
    }

    public void setCheckCenterSet(boolean ischeck) {
        setString("CheckCenterSet", ischeck ? "1" : "0");
    }

    /**
     * 帮助时候是否检查责任中心设置表，默认是不检查
     */
    public boolean isCheckCenterSet() {
        return "1".equals(getString("CheckCenterSet", "0"));
    }

    //可以跨数据库帮助
    public void setServerName(String servername) {
        setString("ServerName", servername);
    }

    public String getServerName() {
        return getString("ServerName", "");
    }
    public void setFilterExp(String exp) {
          setString("FILTEREXP", exp);
      }

      public String getFilterExp() {
          return getString("FILTEREXP", "");
    }

    /**
     *
     * @param UNIT_ID String
     */
    public void setUNIT_ID(String UNIT_ID) {
        setString("UNIT_ID", UNIT_ID);
    }

    /**
     *
     * @return String
     */
    public String getUNIT_ID(){
        return getString("UNIT_ID", "");
    }

    public void setFilterNullCols(String[] FilterNullCols) {
    	setObject("FilterNullCols", FilterNullCols);
    }

    /**
     *
     * @return String
     */
    public String[] getFilterNullCols(){
        return (String[])getObject("FilterNullCols", new String[0]);
    }

    /**
     *
     * @param b boolean
     */
    public void setFJCX(boolean b){
        setString("FJCX", b?"1":"0");
    }

    /**
     *
     * @return boolean
     */
    public boolean getFJCX(){
        return getString("FJCX","0").equals("1")?true:false;
    }

}
