package com.efounder.dctbuilder.variant.parser;

import java.util.Calendar;

import com.efounder.dctbuilder.variant.IVariantAnalytic;
import com.efounder.dctbuilder.variant.VariantStub;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class KJNDParser implements IVariantAnalytic {

    public KJNDParser() {
    }

    public Object getVariantValue(VariantStub variant) {
    	Calendar calr = Calendar.getInstance();
    	return String.valueOf(calr.get(Calendar.YEAR));
    }
}
