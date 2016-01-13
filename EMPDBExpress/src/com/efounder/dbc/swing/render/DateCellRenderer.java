package com.efounder.dbc.swing.render;

import java.text.*;

import javax.swing.*;
import javax.swing.table.*;

import com.efounder.dctbuilder.util.DictUtil;

import java.awt.Component;
import java.awt.Color;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DateCellRenderer extends DefaultTableCellRenderer {

    /**
     *
     */
    DateFormat df = null;

    String datePattern = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[1345 6789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d) (0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";

    /**
     *
     * @param pattern String
     */
    public DateCellRenderer(String pattern) {
        super();
        df = new SimpleDateFormat(pattern);
        df.setTimeZone(java.util.TimeZone.getDefault());
        setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     *
     * @param value Object
     */
    protected void setValue(Object value) {
        String showText = "";
        if (value == null) value = "";
        java.util.Date date = null;
        if (value instanceof java.util.Date) {
            date = (java.util.Date) value;
            showText = df.format(date);
        }
        if (value instanceof Number) {
            date = new java.util.Date(((Number) value).longValue());
            showText = df.format(date);
        }
        /**
         * 可能有多种可能
         */
        if (value instanceof String) {
            //是否是一个有效的日期
//        	try {
//        		java.util.Date date1 = new java.util.Date(Long.parseLong(value.toString()));
//        		showText = df.format(date1);
//        	} catch(Exception e) {
//        		showText = value.toString();
//        	}
            if (value != null && value.toString().length() >= 12) {
                //可能是一个13位的时间戳
                java.util.Date date1 = new java.util.Date(Long.parseLong(value.toString()));
                showText = df.format(date1);
            } else if (value != null && value.toString().length() >= 4) {
                showText = value.toString();
                java.util.Date date1 = DictUtil.getUtilDate(showText);
                showText = df.format(date1);
            }else if (value != null){
            	showText = value.toString();
            }
        }
        setText(showText);
    }

    /**
     *
     * @param color Color
     */
    public void setBackground(Color color) {
        super.setBackground(color);
    }

}
