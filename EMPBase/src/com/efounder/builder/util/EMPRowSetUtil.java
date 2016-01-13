package com.efounder.builder.util;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.util.EMPReflectUtil;

public class EMPRowSetUtil {
	public static EFRowSet createRowSet(Object object) throws IllegalArgumentException, IllegalAccessException{	
		Class    classObj = object.getClass();
		Field[]    fields = classObj.getDeclaredFields();
		Field       field;
		EFRowSet   rowset = EFRowSet.getInstance();

		for(int i = 0; i < fields.length; i++) {
			field = fields[i];
			field.setAccessible(true);
			EMPReflectUtil.setDeclareFieldValueToRowSet(rowset, field, field.get(object));
		}
		return rowset;
	}
}
