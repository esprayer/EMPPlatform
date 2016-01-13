package com.efounder.dbc.swing.render;

import javax.swing.table.*;
import com.efounder.dctbuilder.data.DictMetaDataEx;

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
public class GradeBmCellRenderer extends  DefaultTableCellRenderer{//DefaultTableCellRenderer
  DictMetaDataEx dme;
    public GradeBmCellRenderer(DictMetaDataEx md) {
        dme=md;
    }
    protected void setValue(Object value) {
        if (value == null) {
            super.setValue(value);
            return;
        }
        String bh = value.toString().trim();
        String newbh = bh;
        int len = bh.length();
        if (dme.isGradeDict()) {
            int[] stru = dme.getStrulen();
            for (int i = 0; i < stru.length; i++) {
                if (len <= stru[i]) {
                    break;
                } else
                    newbh = bh.substring(stru[i]);
            }
        }
        setText(newbh);
    }

}
