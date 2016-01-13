package com.efounder.dbc.swing.render;

import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import java.math.BigDecimal;
import java.text.NumberFormat;
import com.efounder.dctbuilder.data.ColumnMetaData;

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

public class NumberCellRenderer extends DefaultTableCellRenderer {

    /**
     *
     */
    public NumberCellRenderer() {
        super();
        this.setHorizontalAlignment(JLabel.RIGHT);
    }

    /**
     *
     */
    private ColumnMetaData meta;

    /**
     *
     * @param meta ColumnMetaData
     */
    public void setColumnMetaData(ColumnMetaData meta) {
        this.meta = meta;
    }

    /**
     *
     * @param value Object
     */
    protected void setValue(Object value) {
        String pattern = "";
        if (meta != null) pattern = meta.getColView();
//        if (pattern == null || pattern.trim().length() == 0) pattern = "#,###.######";
        if (pattern == null || pattern.trim().length() == 0) pattern = "#,##0.00";
        NumberFormat nf = new java.text.DecimalFormat(pattern);

        if (value == null) value = "";
        String text = "";
        if (value instanceof BigDecimal) {
            java.math.BigDecimal bigDeciValue = (BigDecimal) value;
            text = bigDeciValue.toString();
        } else {
            text = value.toString();
        }
        try {
            java.math.BigDecimal b = new BigDecimal(text);
            text = nf.format(b.doubleValue());
            if ("0".equals(meta.getExtAttriValue("COL_OPT", "=", ";", "showZero"))
                && b.doubleValue() == 0)
                text = "";
        } catch (NumberFormatException ex) {
        }

        setText(text);
    }

}
