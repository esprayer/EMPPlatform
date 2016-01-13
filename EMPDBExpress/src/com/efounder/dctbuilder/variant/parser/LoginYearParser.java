package com.efounder.dctbuilder.variant.parser;

import com.efounder.dctbuilder.variant.*;
import com.efounder.eai.data.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class LoginYearParser implements IVariantAnalytic {

    public LoginYearParser() {
    }

    /**
     * getVariantValue
     *
     * @param variant VariantStub
     * @return Object
     * @todo Implement this com.efounder.dctbuilder.variant.IVariantAnalytic method
     */
    public Object getVariantValue(VariantStub variant) {
        JParamObject PO = JParamObject.Create();
        String date = PO.GetValueByEnvName("LoginDate");
        if (date.length() >= 4) return date.substring(0, 4);
        return "";
    }
}
