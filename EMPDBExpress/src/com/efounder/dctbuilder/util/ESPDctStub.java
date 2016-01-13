package com.efounder.dctbuilder.util;

import com.core.xml.*;
import java.util.Hashtable;

/**
 *
 * <p>Title: 数据字典</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: pansoft</p>
 *
 * @author zhangft
 * @version 1.0
 */
public class ESPDctStub extends StubObject {

    public ESPDctStub() {
    }

    public ESPDctStub(String id, String mc) {
        setDctId(id);
        setDctMc(mc);
    }

    public void putAllKey(java.util.Map keyval) {
        if (getStubTable() == null)
            setStubTable(new Hashtable());
        getStubTable().putAll(keyval);
    }

    public String getDctId() {
        return getString("DCT_ID", "");
    }

    public void setDctId(String name) {
        setString("DCT_ID", name);
    }

    public String getDctMc() {
        return getString("DCT_MC", "");
    }

    public void setDctMc(String name) {
        setString("DCT_MC", name);
    }

    public String getObjId() {
        return getString("OBJ_ID", "");
    }

    public void setObjId(String name) {
        setString("OBJ_ID", name);
    }

    public String getDctDes() {
        return getString("DCT_DES", "");
    }

    public void setDctDes(String name) {
        setString("DCT_DES", name);
    }

    public String getDctType() {
        return getString("DCT_TYPE", "");
    }

    public void setDctType(String name) {
        setString("DCT_TYPE", name);
    }

    public String getDctBmCol() {
        return getString("DCT_BMCOLID", "");
    }

    public void setDctBmCol(String name) {
        setString("DCT_BMCOLID", name);
    }

    public String getDctMcCol() {
        return getString("DCT_MCCOLID", "");
    }

    public void setDctMcCol(String name) {
        setString("DCT_MCCOLID", name);
    }

    public String getDctJsCol() {
        return getString("DCT_JSCOLID", "");
    }

    public void setDctJsCol(String name) {
        setString("DCT_JSCOLID", name);
    }

    public String getDctMxCol() {
        return getString("DCT_MXCOLID", "");
    }

    public void setDctMxCol(String name) {
        setString("DCT_MXCOLID", name);
    }

    public String getBmStru() {
        return getString("DCT_BMSTRU", "");
    }

    public void setBmStru(String name) {
        setString("DCT_BMSTRU", name);
    }

    public String getDctQxobjid() {
        return getString("DCT_QXOBJID", "");
    }

    public void setDctQxobjid(String name) {
        setString("DCT_QXOBJID", name);
    }

    public String getDctQxstat() {
        return getString("DCT_QXSTAT", "");
    }

    public void setDctQxstat(String name) {
        setString("DCT_QXSTAT", name);
    }

    public String getDctUnit() {
        return getString("DCT_UNIT", "");
    }

    public void setDctUnit(String name) {
        setString("DCT_UNIT", name);
    }

    public String getSysid() {
        return getString("SYS_ID", "");
    }

    public void setSysid(String name) {
        setString("SYS_ID", name);
    }


    public String getStau() {
        return getString("F_STAU", "");
    }

    public void setStau(String name) {
        setString("F_STAU", name);
    }

    public String toString() {
        return getDctId() + " " + getDctMc();
    }
}
