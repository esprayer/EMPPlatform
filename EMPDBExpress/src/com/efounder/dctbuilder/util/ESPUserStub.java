package com.efounder.dctbuilder.util;

import com.core.xml.*;
import java.util.Hashtable;

/**
 *
 * <p>Title:用户对象 </p>
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
public class ESPUserStub
    extends StubObject {

    public ESPUserStub() {
    }

    public ESPUserStub(String name, String caption) {
        setUserName(name);
        setUserCaption(caption);
    }

    public String getUserName() {
        return getString("F_ZGBH", "");
    }

    public void setUserName(String name) {
        setString("F_ZGBH", name);
    }

    public String getUserCaption() {
        return getString("F_NAME", "");
    }

    public void setUserCaption(String name) {
        setString("F_NAME", name);
    }

    public void putAllKey(java.util.Map keyval) {
        if (getStubTable() == null) setStubTable(new Hashtable());
        getStubTable().putAll(keyval);
    }

    public String toString() {
        return getUserName() + " " + getUserCaption();
    }

}
