package com.efounder.dctbuilder.util;

import com.core.xml.*;
import java.util.Hashtable;

/**
 *
 * <p>Title: 数据权限</p>
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
public class ESPSjqxStub
    extends StubObject {

    public ESPSjqxStub() {
    }

    public ESPSjqxStub(String id, String mc) {
        setQxbh(id);
        setQxmc(mc);
    }

    public void putAllKey(java.util.Map keyval) {
        if (getStubTable() == null)
            setStubTable(new Hashtable());
        getStubTable().putAll(keyval);
    }

    public String getQxbh() {
        return getString("F_QXBH", "");
    }

    public void setQxbh(String bh) {
        setString("F_QXBH", bh);
    }

    public String getQxmc() {
        return getString("F_QXMC", "");
    }

    public void setQxmc(String name) {
        setString("F_QXMC", name);
    }

    public String toString() {
        return getQxbh() + " " + getQxmc();
    }
}
