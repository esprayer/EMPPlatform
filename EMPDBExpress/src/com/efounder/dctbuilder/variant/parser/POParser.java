package com.efounder.dctbuilder.variant.parser;

import com.efounder.dctbuilder.variant.*;
import com.efounder.eai.data.JParamObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author gengeng
 * @version 1.0
 */
public class POParser implements IVariantAnalytic {

    public POParser() {
    }

    /**
     * getVariantValue
     *
     * @param variant VariantStub
     * @return Object
     * @todo Implement this com.efounder.dctbuilder.variant.IVariantAnalytic method
     */
    public Object getVariantValue(VariantStub variant) {
        //PO.UNIT_ID
        String       name = variant.getVariantName();
        String[]    array = null;
        String       pkey = "";
        JParamObject   PO = null;
        
        if (name == null || name.indexOf("PO.") < 0) return "";
        array = name.split("\\.");
        pkey = array[2];
        PO = variant.getPO();
        
        if(array[1].equals("PARAM")) {
        	return PO.GetValueByParamName(pkey, "");
        } else if(array[1].equals("ENV")) {
        	return PO.GetValueByEnvName(pkey, "");
        } 
        return "";
    }
}
