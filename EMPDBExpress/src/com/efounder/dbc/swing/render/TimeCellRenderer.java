package com.efounder.dbc.swing.render;

import javax.swing.table.*;
import javax.swing.JLabel;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

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
public class TimeCellRenderer
    extends DefaultTableCellRenderer {

    /**
     *
     */
    String tf = null;

    String datePattern = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[1345 6789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d) (0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$";

    /**
     *
     * @param pattern String
     */
    public TimeCellRenderer(String pattern) {
        super();
        tf = pattern;
        setHorizontalAlignment(JLabel.CENTER);
    }

    /**
     *
     * @param value Object
     */
    protected void setValue(Object value) {
        if (value == null){
            if(value instanceof java.util.Date){
                value = new java.util.Date();
            }else {
                value = "20100101000000";
            }
        }
        String showText = value.toString();
        showText = showText.replace("/", "");
        showText = showText.replace("-", "");
        showText = showText.replace(" ", "");
        showText = showText.replace(":", "");
        int length = showText.length();
        for (int i = 0; i < (6 - length); i++) {
            showText += "0";
        }
        showText = showText.substring(0, 4) + "-" + showText.substring(4, 6) + "-" +
            showText.substring(6, 8) + " " + showText.substring(8, 10) + ":" +
            showText.substring(10, 12) + ":" + showText.substring(12, 14);
        setText(showText);
    }
}
