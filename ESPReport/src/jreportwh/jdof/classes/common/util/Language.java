package jreportwh.jdof.classes.common.util;

import java.util.*;

public class Language extends java.util.ListResourceBundle {
  private static final Object[][] contents = new String[][]{
	{ "String_18", "增加同级(A)" },
	{ "String_20", "增加下级(X)" },
	{ "String_22", "删除(D)" },
	{ "String_24", "保存(S)" },
	{ "String_26", "选择包含(C)" }};
  public Object[][] getContents() {
    return contents;
  }
}
