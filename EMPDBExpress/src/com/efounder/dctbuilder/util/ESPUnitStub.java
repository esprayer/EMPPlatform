package com.efounder.dctbuilder.util;

import com.core.xml.*;
import java.util.Hashtable;

/**
 * <p>Title: 单位对象 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class ESPUnitStub
    extends StubObject {

    public ESPUnitStub() {
    }

    public ESPUnitStub(String name, String caption) {
        setUnitName(name);
        setUnitCaption(caption);
    }

    public void putAllKey(java.util.Map keyval) {
        if (getStubTable() == null) setStubTable(new Hashtable());
        getStubTable().putAll(keyval);
    }

    public String getUnitName() {
        return getString("UNIT_ID", "");
    }

    public void setUnitName(String name) {
        setString("UNIT_ID", name);
    }

    public String getUnitCaption() {
        return getString("UNIT_MC", "");
    }

    public void setUnitCaption(String name) {
        setString("UNIT_MC", name);
    }

    public String toString() {
        return getUnitName() + " " + getUnitCaption();
    }

}
