package com.efounder.dctbuilder.variant.parser;

import java.util.Calendar;
import java.util.Date;

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
public class KJQJParser implements IVariantAnalytic {

    public KJQJParser() {
    }

    public Object getVariantValue(VariantStub variant) {
    	long time=System.currentTimeMillis();
    	Date date=new Date(time);
    	int year=1900+date.getYear();
    	int month=date.getMonth()+1;
    	//
    	String ymd=String.valueOf(year);
    	if(month<10)
    	  ymd+="0";
    	ymd+=String.valueOf(month);
    	return ymd;
    }
}
