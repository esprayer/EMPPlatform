package com.efounder.bz.task.bof.render;

import java.util.Map;

import com.efounder.buffer.DictDataBuffer;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import java.util.HashMap;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.EFDataSet;
import java.awt.Component;
import javax.swing.table.DefaultTableCellRenderer;
import com.efounder.dctbuilder.util.DictUtil;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import com.efounder.builder.meta.MetaDataManager;
import java.util.List;
import com.efounder.eai.data.JParamObject;
import com.efounder.eai.ide.ExplorerIcons;

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

public class ImageCellRenderer extends DefaultTableCellRenderer{

    /**
     *
     */
    private String fobj;
    private String userObject;

    Map iconMap = null;

    /**
     *
     * @param key String
     */
    public ImageCellRenderer(Map iconMap) {
    	this.iconMap = iconMap;
    }

    /**
     *
     * @param table JTable
     * @param value Object
     * @param isSelected boolean
     * @param hasFocus boolean
     * @param row int
     * @param column int
     * @return Component
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    	
    	if(iconMap == null) {
    		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	}
    	
    	if(value == null) {
    		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	}
    	
    	if(iconMap.get(value) == null) {
    		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    	}
    	
        Icon       cellIcon = ExplorerIcons.getExplorerIcon(iconMap.get(value).toString());
        JLabel    iconLabel = new JLabel();
        
        iconLabel.setIcon(cellIcon);
        return iconLabel;
    }

}
