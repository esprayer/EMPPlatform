package com.efounder.dctbuilder.data;

import java.io.Serializable;
import java.util.*;

import com.core.xml.*;
import com.efounder.dctbuilder.util.MetaDataUtil;
import com.efounder.service.meta.db.*;

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
public class DictMetaDataEx implements Serializable{
    List usedColList = null;
    DictMetadata dmd;
    protected int strulen[] = new int[] {
      4, 8, 12, 16, 20, 24, 28};
//    protected int curlen[] = new int[] {
//      4, 4, 4, 4, 4, 4, 4};
    public DictMetaDataEx(DictMetadata metadata) {
        dmd = metadata;
        setStru(getDctBMStru());
    }

//    public int getCurJSLen(int js) {
//        if (js > curlen.length || js < 1)
//            return 0;
//        else
//            return curlen[js - 1];
//    }

    public int getJSLen(int js) {
        if (js > strulen.length || js < 1)
            return strulen[strulen.length - 1];
        else
            return strulen[js - 1];
    }

    /**
     *
     * @return int
     */
    public int getMaxJS() {
        return getDctBMStru().length();
    }

    public boolean isGradeDict(){
        String stru = this.getDctBMStru();
        if (stru == null || stru.length() <= 0) {
            return false;
        }
        if(this.getDctJsCol()==null||"".equals(this.getDctJsCol()))
            return false;
        return true;
    }
    public Object getObject(String key,String value){
        return dmd.getObject(key,value);
    }

    /**
     * 取数据字典的扩展属性
     * @param unitid String
     * @param key String
     * @return String
     */
    public String getExtAttribute(String unitid, String key) {
        Object object = getObject("SYS_DCT_CST", null);
        unitid = unitid == null ? "" : unitid.trim();
        if (object != null) {
            java.util.List list = (List) object;
            for (int i = 0, n = list.size(); i < n; i++) {
                StubObject so = (StubObject) list.get(i);
                if (so.getString("DCT_KEY", "").toUpperCase().equals(key.toUpperCase())
                    && unitid.equals(so.getString("UNIT_ID", "").trim())) {
                    return so.getString("DCT_VALUE", "");
                }
            }
        }
        return "";
    }

    /**
     *
     * @return DictMetadata
     */
    public DictMetadata getOrdinalMetadata() {
        return dmd;
    }

    /**
     *
     * @return List
     */
    public List getUsedColList(){
        if(usedColList==null)
            usedColList=MetaDataUtil.getUsedColumnStub(dmd);
        return usedColList;
    }
    
    public void setUsedColList(java.util.List list){
        usedColList = list;
    }
    
    /**
     *
     * @return List
     */
    public java.util.List getVisibleColList() {
        java.util.List list2 = new java.util.ArrayList();
        java.util.List  list = getUsedColList();
        for (int i = 0; list != null && i < list.size(); i++) {
            ColumnMetaData cmd = (ColumnMetaData) list.get(i);
            if (cmd.isVisible()) list2.add(cmd);
        }
        return list2;
    }

    /**
     * 判断是否有该列
     *
     * @param colName String
     * @return boolean
     */
    public boolean hasColumn(String colName){
        if (usedColList == null)
            return false;
        Object o = getUsedColInfo(colName);
        return o != null;
    }

    /**
     * 增加一个列
     *
     * @param meta ColumnMetaData
     */
    public void addUsedCol(ColumnMetaData meta){
        if (meta != null) {
            getUsedColList().add(meta);
        }
    }

    /**
     * add by gengeng 2009-10-12
     *
     * @param   key Object
     * @param value String
     */
    public void setString(Object key, String value) {
        dmd.setString(key, value);
    }

    /**
     * 增加编码的时候判断是否控制一级
     *
     * @return boolean
     */
    public boolean isControlOne() {
        String ctlOne = dmd.getString("DCT_CTLONE", "0");
        return "1".equals(ctlOne.trim());
    }

    public String getString(Object key, String def) {
        return dmd.getString(key, def);
    }

    public Object getUsedColInfo(String colid){
        List list=getUsedColList();
        for(int i=0;i<list.size();i++){
            if(colid.equals(((ColumnMetaData)list.get(i)).getColid()))
                return list.get(i);
        }
        return null;
    }
    public int[] getStrulen() {
        String stru = getDctBMStru();
        int Max = stru.trim().length();
        strulen = new int[Max];
        for (int i = 0; i < Max; i++) {
            String temp = stru.substring(i, i + 1);
            if (i != 0) {
                strulen[i] = strulen[i - 1] + Integer.parseInt(temp, 36);
            } else {
                strulen[i] = Integer.parseInt(temp, 36);
            }
        }
        return strulen;
    }

    public int getTotalLength(){
        int[] len = getStrulen();
        if (len != null && len.length > 0){
            return len[len.length - 1];
        }
        return 0;
    }
    public String getDctid() {
        return dmd.getString("DCT_ID", "");
    }

    public String getDctMc() {
        return dmd.getString("DCT_MC", "");
    }

    public String getDctBmCol() {
        return dmd.getString("DCT_BMCOLID", "").trim();
    }

    public String getDctMcCol() {
        return dmd.getString("DCT_MCCOLID", "").trim();
    }

    public String getDctJsCol() {
        return dmd.getString("DCT_JSCOLID", "").trim();
    }

    public String getDctMxCol() {
        return dmd.getString("DCT_MXCOLID", "").trim();
    }

    public String getDctParentCol() {
        return dmd.getString("DCT_PTCOLID", "").trim();
    }

    public String getDctControlCol() {
        return dmd.getString("DCT_KZCOLID", "").trim();
    }

    public String getDctBMStru() {
        return dmd.getString("DCT_BMSTRU", "").trim();
    }
    public void setDctBMStru(String bm) {
            dmd.setString("DCT_BMSTRU", bm);
    }

    public int getDctBMStep(){
        return dmd.getInt("DCT_STEP", 1);
    }

    private void setStru(String stru) {
        if (stru == null || stru.length() <= 0) {
            return;
        }
        int Max = stru.trim().length();
        strulen = new int[Max];
        for (int i = 0; i < Max; i++) {
            String temp = stru.substring(i, i + 1);
            if (i != 0) {
                strulen[i] = strulen[i - 1] + Integer.parseInt(temp, 36);
            } else {
                strulen[i] = Integer.parseInt(temp, 36);
            }
        }
    }

  public boolean isMLang() {
      TableMetadata tm = getOrdinalMetadata().getTableMetadata();
      if (tm != null) {
          return "1".equals(tm.getString("OBJ_LANG", ""));
      }
      return false;
  }

  public java.util.List getKeyColumn(){
      java.util.List allcol = getUsedColList();
      java.util.List keycol = new ArrayList();
      for (int i = 0, n = allcol.size(); i < n; i++) {
          ColumnMetaData meta = (ColumnMetaData)allcol.get(i);
          if (meta.isKey()) {
              keycol.add(meta);
          }
      }
      return keycol;
  }

  public java.util.List getKeyColumnName() {
      java.util.List allcol = getKeyColumn();
      java.util.List keycol = new ArrayList();
      for (int i = 0, n = allcol.size(); i < n; i++) {
          ColumnMetaData meta = (ColumnMetaData) allcol.get(i);
          keycol.add(meta.getColid());
      }
      return keycol;
  }

  public java.util.List getExtendProperties(String key){
      java.util.List list = new ArrayList();
      java.util.List cst = (List) dmd.getObject("SYS_DCT_CST", null);
      if (cst == null) {
          return list;
      }
      for (int i = 0, n = cst.size(); i < n; i++) {
          StubObject so = (StubObject) cst.get(i);
          if (so.getString("DCT_KEY", "").toUpperCase().equals(key.toUpperCase())) {
              list.add(so);
          }
      }
      return list;
  }

  public String[] getColumnByFObj(String fobj) {
      java.util.List columns = new ArrayList();
      java.util.List list = this.getUsedColList();
      for (int i = 0; list != null && i < list.size(); i++) {
          ColumnMetaData meta = (ColumnMetaData) list.get(i);
          if (meta.isFKEY() && fobj.equals(meta.getFOBJ()))
              columns.add(meta.getColid());
      }
      String[] cols = new String[columns.size()];
      System.arraycopy(columns.toArray(), 0, cols, 0, cols.length);
      return cols;
  }

}
