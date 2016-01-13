package com.efounder.dbc.swing.render.plugin;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.domodel.*;
import com.efounder.dbc.swing.render.*;

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
public class SYS_OBJCOLSCaption implements IFKeyCaption {

    public SYS_OBJCOLSCaption() {
    }

    /**
     *
     * @return String
     */
    public String getCaption(Object value, FkeyCellRenderer fkeyCellRenderer) {
        try {
            String fdct = fkeyCellRenderer.getUserObject();
            if (fdct == null || fdct.trim().length() == 0) return "";
            DOMetaData doMeta = (DOMetaData) DOMetaDataManager.getDODataManager().getMetaData(fdct);
            if (doMeta != null) {
                ESPRowSet rs = doMeta.getColumnDefineRow(value.toString());
                if (rs != null)
                    return rs.getString("COL_MC", "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
