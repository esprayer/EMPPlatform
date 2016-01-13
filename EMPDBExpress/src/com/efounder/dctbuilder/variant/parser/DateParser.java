package com.efounder.dctbuilder.variant.parser;

import com.efounder.date.DateFunction;
import com.efounder.dctbuilder.util.DictUtil;
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
public class DateParser implements IVariantAnalytic {

    private static final String          DATE = "DATE";
    private static final String          TIME = "TIME";
    private static final String      DATETIME = "DATETIME";
    private static final String  YEARFIRSTDAY = "YEARFIRSTDAY";
    private static final String   YEARLASTDAY = "YEARLASTDAY";
    private static final String MONTHFIRSTDAY = "MONTHFIRSTDAY";
    private static final String  MONTHLASTDAY = "MONTHLASTDAY";
    private static final String           NOW = "NOW";
    private static final String          KJQJ = "KJQJ";
    private static final String          NEXTYEAR = "NEXTYEAR";

    public DateParser() {
    }

    /**
     * 这些日期函数是基于服务器的日期时间
     *
     * @param variant VariantStub
     * @return Object
     * @todo Implement this com.efounder.dctbuilder.variant.IVariantAnalytic method
     */
    public Object getVariantValue(VariantStub variant) {
        String name = variant.getVariantName();
        if (name.startsWith("@")) name = name.substring(1);
        if (name.endsWith("@"))   name = name.substring(0, name.length() - 1);
        name = name.toUpperCase();
        //当前年
        String year = DictUtil.getCurrentYear();
        String date = DateFunction.getCurrentDate();
        String time = DateFunction.getCurrentTime();
        long nowInt = System.currentTimeMillis();
        if (DATE.equals(name)) {
            return date;
        } else if (TIME.equals(name)) {
            return time;
        } else if (DATETIME.equals(name)) {
            return date + time;
        } else if (YEARFIRSTDAY.equals(name)){
            return DictUtil.getMonthFirstDay(year + "01", 0, null);
        } else if (YEARLASTDAY.equals(name)){
            return DictUtil.getMonthLastDay(year + "12", 0, null);
        } else if (MONTHFIRSTDAY.equals(name)){
            return DictUtil.getMonthFirstDay(date, 0, null);
        } else if (MONTHLASTDAY.equals(name)){
            return DictUtil.getMonthLastDay(date, 0, null);
        } else if (NOW.equals(name)){
            return String.valueOf(nowInt);
        } else if (KJQJ.equals(name)) {
            return date.substring(0, 6);
        }else if (NEXTYEAR.equals(name)) {
            return String.valueOf(Integer.valueOf(year)+1);
        }
        return null;
    }
}
