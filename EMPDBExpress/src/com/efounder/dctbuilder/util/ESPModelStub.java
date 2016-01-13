package com.efounder.dctbuilder.util;

import com.core.xml.*;
import java.util.Hashtable;

/**
 * <p>Title: 模型对象 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class ESPModelStub extends StubObject {

    public ESPModelStub() {
    }

    public ESPModelStub(String name, String caption) {
        setModelName(name);
        setModelCaption(caption);
    }

    public String getModelName() {
        return getString("MDL_ID", "");
    }

    public void setModelName(String name) {
        setString("MDL_ID", name);
    }

    public String getModelCaption() {
        return getString("MDL_MC", "");
    }

    public void setModelCaption(String name) {
        setString("MDL_MC", name);
    }

    public void putAllKey(java.util.Map keyval) {
        if (getStubTable() == null) setStubTable(new Hashtable());
        getStubTable().putAll(keyval);
    }


    public String toString() {
        return getModelName() + " " + getModelCaption();
    }

}
