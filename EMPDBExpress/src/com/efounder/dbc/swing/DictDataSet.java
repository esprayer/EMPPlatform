package com.efounder.dbc.swing;

import java.util.*;

import com.borland.dx.dataset.*;
import com.borland.dx.sql.dataset.*;
import com.efounder.db.*;
import com.efounder.dbc.*;
import com.borland.dx.text.*;
import com.core.xml.*;
import com.efounder.dctbuilder.data.DctContext;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DictDataSet extends ClientDataSet {
    protected String dictName;
    protected ClientDataSet dictInfo = new ClientDataSet();
    private ClientDataSet dictColInfo = new ClientDataSet();
    private ClientDataSet tableInfo = new ClientDataSet();
    protected Hashtable colInfo = new Hashtable();
    String server="";
    public DictDataSet() {
    }

    public ClientDataSet getdictInfo() {
        return dictInfo;
    }

    public ClientDataSet dictColInfo() {
        return dictInfo;
    }

    public ClientDataSet tableInfo() {
        return dictInfo;
    }
    public void setServerName(String dictName) {
    	server=dictName;
    	getAgentDataBase().setAgentServer(server);
    }
    public void setDictName(String dictName) {
        this.dictName = dictName;
        setTableName(dictName);
        setMetaDataUpdate(MetaDataUpdate.NONE);
    }

    public String getDictName() {
        return dictName;
    }

    public ClientDataSet getDictColInfo() {
        return dictColInfo;
    }

    public StubObject getDictUseColInfo(String colname) {
        return (StubObject) colInfo.get(colname);
    }
    public void putDictUseColInfo(String colname,StubObject so) {
        colInfo.put(colname,so);
    }
    public Hashtable getDictUseColInfo() {
        return colInfo;
    }

    protected void createDictInfo() {
        String sql;
        dictInfo.setAgentDataBase(getAgentDataBase());
        sql = "select * from SYS_DICTS where DCT_ID='" + dictName + "'";
        dictInfo.setQuerySet(new QueryDescriptor(null, sql, null, true,
                                                 Load.ALL));
    }

    protected void createObjInfo() {
        String sql;
        tableInfo.setAgentDataBase(getAgentDataBase());
        sql = "select * from SYS_OBJECTS where OBJ_ID='" + dictName + "'";
        tableInfo.setQuerySet(new QueryDescriptor(null, sql, null, true,
                                                  Load.ALL));
    }

    protected void createColInfo() {
      dictColInfo.setAgentDataBase(getAgentDataBase());
          String sql = "SELECT SYS_OBJCOLS.* FROM  SYS_OBJCOLS,SYS_DICTS WHERE DCT_ID='"
                     + dictName +
                     "' and SYS_OBJCOLS.OBJ_ID=SYS_DICTS.OBJ_ID  order by COL_DISP";
        dictColInfo.setQuerySet(new QueryDescriptor(null, sql, null, true,
                Load.ALL));

    }

    protected void createDictDataInfo() {
       String sql = "select * from "+dictName;
       Object o=getUserObject();
       if(o instanceof DctContext){
           String w=((DctContext)o).getWhere();
           if(w!=null&&!w.equals(""))
               sql+=" WHERE "+w;
       }
       setQuerySet(new QueryDescriptor(null, sql, null, true,
                                        Load.ALL));

    }
    protected void createOtherInfo(){

    }
    public ClientDataSet[] getDataSetArray(){
      return new ClientDataSet[] {dictInfo,
                                        dictColInfo, tableInfo, this};
    }
    protected void loadDataSet() throws Exception {
        getAgentDataBase().loadDataSets(getDataSetArray());
    }
    protected void setColumnRenderInfo(StubObject so){

    }
    protected void setColEditorInfo(StubObject so){

    }

    protected void setupDataInfo() {

    }
    protected void setupColumnInfo() {
        String[] Keys = null;
        if (getJsCol() == null || getJsCol().trim().length() == 0) //不分级，没有级数
            Keys = new String[] {getBhCol()};
        else
            Keys = new String[] {getJsCol(), getBhCol()};
        SortDescriptor sd = new SortDescriptor(Keys);
        setSort(sd);
        String name;
        if (dictColInfo.getRowCount() > 0) {
            dictColInfo.first();
            while (true) {
                name = dictColInfo.getString("COL_ID");
                if ("1".equals(dictColInfo.getString("COL_USE"))) {
                    getColumn(name).setCaption(dictColInfo.getString("COL_MC"));
                    getColumn(name).setWidth(dictColInfo.getBigDecimal("COL_LEN").intValue());
                    if (dictColInfo.getString("COL_TYPE").equals("D"))
                        getColumn(name).setFormatter(new DateFormatter());
                    StubObject so=new StubObject();
                    so.setString("COL_ID",name);
                    so.setString("COL_TYPE",dictColInfo.getString("COL_TYPE"));
                    so.setString("COL_EDIT",dictColInfo.getString("COL_EDIT"));
                    //ADD BY LCHONG
                    so.setString("COL_ISFKEY",dictColInfo.getString("COL_ISFKEY"));
                    so.setString("COL_FOBJ",dictColInfo.getString("COL_FOBJ"));
                    //ADD END
                    colInfo.put(name, so);
                } else
                    getColumn(name).setVisible(0);
                if (!dictColInfo.next())
                    break;
            }
        }

    }

    public void loadData() throws Exception {
        if (getAgentDataBase() == null)
            return;
        createDictInfo();
        createObjInfo();
        createColInfo();
        createDictDataInfo();
        createOtherInfo();
        loadDataSet();
        setupDataInfo();
        setupColumnInfo();
    }

    public String getBmStru() {
        return dictInfo.getString("DCT_BMSTRU");
    }

    public String getBhCol() {
        return dictInfo.getString("DCT_BMCOLID");
    }

    public String getMcCol() {
        return dictInfo.getString("DCT_MCCOLID");
    }

    public String getJsCol() {
        String js = dictInfo.getString("DCT_JSCOLID");
        if (js == null)
            js = null;
        return js.trim();
    }

    public String getMxCol() {
        return dictInfo.getString("DCT_MXCOLID");
    }

    public String getParentCol() {
        return dictInfo.getString("DCT_PTCOLID");
    }

    public String getDictMc() {
        return dictInfo.getString("DCT_MC");
    }
}
