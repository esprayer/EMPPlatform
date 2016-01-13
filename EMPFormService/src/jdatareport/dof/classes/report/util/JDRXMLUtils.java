package jdatareport.dof.classes.report.util;

import org.jdom.*;

/**
 *
 * <p>Title: JDRXMLUtils</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */
public final class JDRXMLUtils {
    /**
     * 获得属性值
     * @param elmt       元素
     * @param name       名称
     * @param dftValue   缺省值
     * @return
     */
    public static String getAttributeValue(Element elmt, String name, String dftValue) {
        if (elmt != null && name != null) {
            String value = elmt.getAttributeValue(name);
            if (value != null) {
                return value;
            }
        }
        return dftValue;
    }

    /**
     * 获得属性值
     * @param elmt       元素
     * @param name       名称
     * @param dftValue   缺省值
     * @return
     */
    public static int getAttributeValue(Element elmt, String name, int dftValue) {
        if (elmt != null && name != null) {
            String value = elmt.getAttributeValue(name);
            if (value != null && value.length() > 0) {
                return Integer.parseInt(value);
            }
        }
        return dftValue;
    }
}