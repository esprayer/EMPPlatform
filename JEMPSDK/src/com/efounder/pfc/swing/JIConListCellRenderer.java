package com.efounder.pfc.swing;

import javax.swing.*;

import java.awt.Component;

import com.core.xml.*;
import com.efounder.pub.comp.CompoundIcon;
import com.efounder.pub.util.EMPStringUtils;
import com.efounder.ui.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed JLabel
 * @version 1.0
 */
//------------------------------------------------------------------------------------------------
// 描述:
// 设计: Skyline(2001.12.29)
// 实现: Skyline
// 修改:
// ------------------------------------------------------------------------------------------------
public class JIConListCellRenderer extends DefaultListCellRenderer implements ListCellRenderer {
	private Icon CellIcon = null;
	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public JIConListCellRenderer(Icon icon) {
		CellIcon = icon;
	}
	// ------------------------------------------------------------------------------------------------
	// 描述:
	// 设计: Skyline(2001.12.29)
	// 实现: Skyline
	// 修改:
	// ------------------------------------------------------------------------------------------------
	public Component getListCellRendererComponent(JList list,
												  Object value,            // value to display
												  int index,               // cell index
												  boolean isSelected,      // is the cell selected
												  boolean cellHasFocus)    // the list and the cell have the focus
	{

		DefaultListCellRenderer comp = (DefaultListCellRenderer)super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);

		if ( value == null ) {
			setIcon(null);setText(null);
			return comp;
		}
		String s = getValueCaption(value);
		setText(s);
		Icon icon = getIcon1(value);//(Icon)JBOFClass.CallObjectMethod(value,"getIcon");
		if ( icon == null )
			setIcon(CellIcon);
		else
			setIcon(icon);
//      if (isSelected) {
//        setBackground(list.getSelectionBackground());
//        setForeground(list.getSelectionForeground());
//      }
//      else {
//        setBackground(list.getBackground());
//        setForeground(list.getForeground());
//      }
//  setEnabled(list.isEnabled());
//  setFont(list.getFont());
//      setOpaque(true);
		return comp;
	}
	protected String getValueCaption(Object value) {
		return value.toString();
	}
	private Icon getIcon1(Object o) {
		try {
			if(o instanceof StubObject) {
				StubObject stub = (StubObject) o;
				Icon imageIcon = EMPStringUtils.createImageIcon(stub.getString("icon", ""));
//				Icon icon = (Icon)com.core.xml.JBOFClass.CallObjectMethod(o,"getIcon");
				int level = 1;
				String sLevel = stub.getString("Level", "1");
				Integer Level = Integer.valueOf(sLevel);
				if ( Level != null ) level = Level.intValue();
//				Integer Level = ((Integer)com.core.xml.JBOFClass.CallObjectMethod(o,"getLevel"));
//				if ( Level != null ) level = Level.intValue();
				if ( level > 1 ) {
					Icon blankIcon = Icons.getBlankIcon(8*(level-1),16);
					imageIcon = new CompoundIcon(blankIcon,imageIcon);
				}
				return imageIcon;
			}			
		} catch(Exception ce) {
			ce.printStackTrace();
		}
		return null;
	}
}
