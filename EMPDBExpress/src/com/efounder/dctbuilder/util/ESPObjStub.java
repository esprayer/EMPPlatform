package com.efounder.dctbuilder.util;

import com.core.xml.*;
import java.util.Hashtable;

/**
 * <p>Title: 数据字典对象（瘦身，只含SYS_DICTS中的信息） </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class ESPObjStub extends StubObject {

    public ESPObjStub() {
    }

    public ESPObjStub(String name, String caption) {
        setDctName(name);
        setDctCaption(caption);
    }

    public void putAllKey(java.util.Map keyval) {
        if (getStubTable() == null) setStubTable(new Hashtable());
        getStubTable().putAll(keyval);
    }

    public String getDctName() {
        return getString("OBJ_ID", "");
    }

    public void setDctName(String name) {
        setString("OBJ_ID", name);
    }

    public String getDctCaption() {
        return getString("OBJ_MC", "");
    }

    public void setDctCaption(String name) {
        setString("OBJ_MC", name);
    }

    public String toString() {
        return getDctName() + " " + getDctCaption();
    }

}
