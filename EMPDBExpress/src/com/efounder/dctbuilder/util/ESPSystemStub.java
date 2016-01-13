package com.efounder.dctbuilder.util;

import java.io.Serializable;
import com.core.xml.StubObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class ESPSystemStub implements Serializable {

    protected StubObject systemStub;

    public ESPSystemStub(StubObject stub) {
        this.systemStub = stub;
    }

    public void putAllKey(java.util.Map keyVal) {
        systemStub.getStubTable().putAll(keyVal);
    }

    /**
     * 获取当前应用的默认语言
     *
     * @return String
     */
    public String getDefaultLang() {
        return getString("LANG_ID", "");
    }

    /**
     * 是否分页读取
     *
     * @return boolean
     */
    public boolean isPagingRead() {
        String mlang = getString("PagingRead", "0");
        return "1".equals(mlang) || "TRUE".equals(mlang.toUpperCase());
    }

    /**
     * 是否启用多语言维护
     *
     * @return boolean
     */
    public boolean isUseMLang() {
        String mlang = getString("UseMLang", "0");
        return "1".equals(mlang) || "TRUE".equals(mlang.toUpperCase());
    }

    public String getSysid() {
        return systemStub.getString("SYS_ID", "");
    }

    public void setSysid(String sysid) {
        systemStub.setString("SYS_ID", sysid);
    }

    public String getSysmc() {
      return systemStub.getString("SYS_MC", "");
    }

    public void setSysmc(String sysid) {
      systemStub.setString("SYS_MC", sysid);
    }

    /**
     * Delegate method java.lang.String toString() to systemStub : StubObject
     *
     * @return String
     */
    public String toString() {
        return systemStub.getString("SYS_ID", "") + " " + systemStub.getString("SYS_MC", "");
    }

    /**
     * Delegate method java.lang.String getString(Object Key, String def) to systemStub : StubObject
     *
     * @param Key Object
     * @param def String
     * @return String
     */
    public String getString(Object Key, String def) {
        return systemStub.getString(Key, def);
    }

    /**
     * Delegate method void setString(Object key, String v) to systemStub : StubObject
     *
     * @param key Object
     * @param v String
     */
    public void setString(Object key, String v) {
        systemStub.setString(key, v);
    }
}
