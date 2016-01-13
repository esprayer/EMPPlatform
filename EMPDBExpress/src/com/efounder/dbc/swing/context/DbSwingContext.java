package com.efounder.dbc.swing.context;

import com.core.xml.*;
import com.efounder.dbc.ClientDataSet;
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
public class DbSwingContext extends StubObject {
    DataDicDefine dictInfo = new DataDicDefine();
    protected int strulen[] = new int[] {
                              4, 8, 12, 16, 20, 24, 28};
    public DbSwingContext() {
    }

    public DataDicDefine getDictInfo() {
        return dictInfo;
    }

    public void setDictInfo(DataDicDefine in) {
        dictInfo = in;
        setStru(dictInfo.getDicBHStruct());
    }

    public void setDataObject(Object o) {
        setObject("DBO", o);
    }

    public Object getDataObject() {
        return getObject("DBO", null);
    }

    public void setDataSet(ClientDataSet cds) {
        setObject("DATASET", cds);
    }

    public ClientDataSet getDataSet() {
        return (ClientDataSet) getObject("DATASET", null);
    }

    public String getDCTID() {
        return dictInfo.getDicID();
    }

    public void setDCTID(String s) {
        dictInfo.setDicID(s);
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

    private void setStru(String stru) {
        if (stru == null || stru.length() <= 0) {
            return;
        }
        int Max = stru.trim().length();
        strulen = new int[Max];
        for (int i = 0; i < Max; i++) {
            String temp = stru.substring(i, i + 1);
            if (i != 0) {
                /**
                 * 改进以支持对字母的解析
                 * modified by gengeng 2009-9-4
                 */
                strulen[i] = strulen[i - 1] + Integer.parseInt(temp, 36);
            } else {
                strulen[i] = Integer.parseInt(temp, 36);
            }
        }
    }

    public int getJslen(int js) {
        if (js < strulen.length)
            return strulen[js];
        return 0;
    }

    public void setBMStru(String stru) {
        dictInfo.setDicBHStruct(stru);
        setStru(stru);
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

    public String getBMStru() {
        return dictInfo.getDicBHStruct();
    }

    /**
     * 下级控制列
     * add by gengeng 2009-10-13
     */
    public String getKZColumn(){
        return dictInfo.getDicFieldControl();
    }

    public void setKZColumn(String kzcol) {
        dictInfo.setString(DataDicDefine.T_DIC_FIELD_KZ, kzcol);
    }

    //add by luody
    public void setParentColumn(String parent) {
        setString("BHPARENT", parent);
    }

    public String getParentColumn() {
        return getString("BHPARENT", "");
    }

}
