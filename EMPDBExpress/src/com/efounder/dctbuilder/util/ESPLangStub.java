package com.efounder.dctbuilder.util;

import com.core.xml.*;
import java.util.Hashtable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class ESPLangStub extends StubObject {

    public ESPLangStub() {
    }

    public ESPLangStub(String name, String caption) {
        setLangName(name);
        setLangCaption(caption);
    }

    public String getLangName() {
        return getString("LangName", "");
    }

    public void setLangName(String name) {
        setString("LangName", name);
    }

    public String getLangCaption() {
        return getString("LangCaption", "");
    }

    public void setLangCaption(String name) {
        setString("LangCaption", name);
    }

    public void putAllKey(java.util.Map keyval) {
        if (getStubTable() == null) setStubTable(new Hashtable());
        getStubTable().putAll(keyval);
    }


    public String toString() {
        return getLangName() + " " + getLangCaption();
    }

}
