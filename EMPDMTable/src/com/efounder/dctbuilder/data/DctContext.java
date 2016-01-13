package com.efounder.dctbuilder.data;

import java.util.*;

import com.efounder.eai.data.*;
import com.efounder.service.meta.db.DictMetadata;
import com.efounder.builder.base.data.*;

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
public class DctContext implements KeyValue,java.io.Serializable{

    protected Map map=new HashMap();
    protected transient Map losMap=new HashMap();
    public DctContext() {
    }

    public Object getLosableValue(Object key,Object def){
        if(losMap==null)return def;
      Object value = losMap.get(key);
      if (value == null)
        value = def;
      return value;
    }
    public void setLosableValue(Object key, Object value) {
      if(losMap==null)losMap=new HashMap();
      if (value == null)
        losMap.remove(key);
      else
        losMap.put(key, value);
    }
    public String getString(String key,String def){
        String value=(String)map.get(key);
        if(value==null)value=def;
        return value;
    }
    public void setString(String key,String value){
        if(value==null)
            map.remove(key);
        else
            map.put(key,value);
    }
    public Object getValue(Object key,Object def){
      Object value=map.get(key);
      if(value==null){
          value=this.getLosableValue(key,def);
      }
      return value;
  }
  public void setValue(Object key,Object value){
      if(value==null){
          map.remove(key);
          setLosableValue(key,null);
      }
      else
          map.put(key,value);
  }
    public String getDctID() {
        return getString("DCTID","");
    }

    public String getWhere() {
        return getString(DctConstants.WHERE,"");
    }
    public String getOrder() {
            return getString(DctConstants.ORDER,"");
    }
    public void setDctID(String dctID) {
       setString("DCTID",dctID);
    }

    public void setWhere(String Where) {
        setString(DctConstants.WHERE,Where);
    }
    public void setOrder(String order) {
           setString(DctConstants.ORDER,order);
    }
    public Map getAttriMap() {
        return map;
    }

    public String getPlugInKey() {
        return getString(DctConstants.PLUGIN_KEY,"");
    }

    public void setPlugInKey(String plugInKey) {
        setString(DctConstants.PLUGIN_KEY,plugInKey);
    }
    
    public DictMetadata getMetaData() {
        return (DictMetadata)getValue(DctConstants.METADATA,null);
    }
    
    //权限编号
    public void setQXBH(String QXBH) {
        setString("QXBH",QXBH);
    }
    
    public String getQXBH() {
        return getString("QXBH","");
    }
    
    //权限标志位
    public void setQXBZW(String BZW) {
        setString("QXBZW", BZW);
    }
    
    public String getQXBZW() {
        return getString("QXBZW","");
    }
    
    //查询标志位
    public void setQueryQXBZW(String BZW) {
        setString("QQXBZW", BZW);
    }
    
    public String getQueryQXBZW() {
        return getString("QQXBZW","");
    }
    
    //修改标志位
    public void setWriteQXBZW(String BZW) {
        setString("WQXBZW", BZW);
    }
    
    public String getWriteQXBZW() {
        return getString("WQXBZW","");
    }
    
    public void setReadOnly(boolean b){
        this.setString("READONLY",b?"1":"0");
    }
    public boolean isReadOnly(){
       return getString("READONLY","").equals("1");
    }
    public void setFirstLoad(boolean b){
        this.setString("FIRSTLOAD",b?"1":"0");
    }
    public boolean isFirstLoad(){
       return getString("FIRSTLOAD","1").equals("1");
    }
    public void  setCurBM(String bm){
        setString(DctConstants.CURBM,bm);
    }
    public String getCurBM(){
        return getString(DctConstants.CURBM,"");
    }
    public void  setCurJS(String bm){
        setString(DctConstants.CURJS,bm);
    }
    public String getCurJS(){
        return getString(DctConstants.CURJS,"1");
    }

    public void setServerName(String sn) {
        setString("ServerName", sn);
    }

    public String getServerName() {
        return getString("ServerName", null);
    }
    public void setCurrentLang(String lang) {
        setString("CurrentLang", lang);
    }

    public String getCurrentLang() {
        return getString("CurrentLang", "");
    }

    public Map getLosMap() {
        return losMap;
    }

    //是不是分级取读数据
    public void setGradeQuery(boolean b) {
        this.setString("GradeQuery", b ? "1" : "0");
    }
    public boolean isGradeQuery() {
        return getString("GradeQuery", "").equals("1");
    }
    //当对象没有源数据时，在这个列表里设置字典List用来取字典的信息，做帮助
    public void addOtherFKey(String dctid) {
        List list=getOtherFKeyList();
        if(list==null){
            list=new ArrayList();
            this.setValue("OTHERFKEYLIST", list);
        }
        if(!list.contains(dctid))list.add(dctid);
    }
    public List getOtherFKeyList() {
        return (List) getValue("OTHERFKEYLIST", null);
    }

    /**
     * 设置外键的帮助过滤条件
     * add by gengeng 2009-9-10
     *
     * @param dctid String
     * @param where String
     */
    public void addFKEYWhere(String dctid, String where) {
        Map list = getFKEYWhereMap();
        if (list == null) {
            list = new HashMap();
            setValue("OTHERFKEYWHEREMAP", list);
        }
        //modified by lchong
        //注释里面判断有问题:如果list里面有SYS_FACTS  SYS_ID = 'SINOPEC-FMIS',这是选择应用改变时key为SYS_FACTS,SYS_ID = 'SNPC-FRSS' 时就不会更新条件
//        if (!list.containsKey(dctid)) {
//            list.remove(dctid);
//            list.put(dctid, where);
//        }
        if (list.containsKey(dctid)) {
            list.remove(dctid);
        }
        list.put(dctid, where);
       //modified end
    }

    /**
     * 返回所有外键的过滤条件的集合
     * add by gengeng 2009-9-10
     *
     * @return Map
     */
    public Map getFKEYWhereMap() {
        return (Map) getValue("OTHERFKEYWHEREMAP", null);
    }

    public void setFKEYMetaData(String objid,Object md){
        this.setValue(objid+"_MD",md);
    }

    public void setFKEYDataSet(String objid, Object md) {
        this.setValue(objid + "_DATASET", md);
    }

    public EFDataSet getFKEYDataSet(String objid) {
        return (EFDataSet) getValue(objid + "_DATASET", null);
    }

    public void setUseMLangView(boolean use) {
        setValue("UseMLangeView", use ? "1" : "0");
    }

    /**
     * 读取数据时是否从多语言视图中读取
     * @return boolean
     */
    public boolean isUseMLangeView() {
        return "1".equals(getValue("UseMLangeView", ""));
    }

    public void setMLangeDeleteInsert(boolean use) {
        setValue("MLangeUpdate", use ? "1" : "0");
    }

    /**
     * 保存数据时是否同时处理多语言数据添加删除
     */
    public boolean isMLangeDeleteInsert() {
        return "1".equals(getValue("MLangeUpdate", ""));
    }


    public void setMLangViewName(String name) {
        setValue("MLangeViewName", name);
    }

    public String getMLangViewName() {
        return (String) getValue("MLangeViewName", "");
    }

    /**
     * 是否分页读取数据
     * @return boolean
     */
    public boolean isPagingRead() {
        return "1".equals(getValue("PagingRead", "0"));
    }

    public void setPagingRead(boolean pagingread) {
        setValue("PagingRead", pagingread ? "1" : "0");
    }

    /**
     * 分页读取的数据行数
     * @return int
     */
    public int getPagingReadCount() {
        Integer i = (Integer) getValue("PagingReadCount", new Integer(0));
        return i.intValue();
    }

    public void setPagingReadCount(int count){
        Integer i = new Integer(count);
        setValue("PagingReadCount", i);
    }
    
    public void setMetaData(DictMetadata metaData) {
        setValue(DctConstants.METADATA,metaData);
    }
    public DictMetadata getFKEYMetaData(String objid){
        return (DictMetadata)getValue(objid+"_MD",null);
    }
}
