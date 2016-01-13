package com.efounder.bz.dbform.component.dc.table.render;

import java.awt.Rectangle;
import org.jdesktop.swingx.tree.DefaultXTreeCellRenderer;
import javax.swing.border.Border;
import org.jdesktop.swingx.renderer.StringValue;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import org.jdesktop.swingx.renderer.StringValues;
import org.jdesktop.swingx.JXTreeTable;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.JComponent;
import com.efounder.eai.ide.ExplorerIcons;
import javax.swing.Icon;
import com.efounder.pub.comp.CompoundIcon;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.bz.dbform.datamodel.treetable.DataSetTreeTableNode;
import com.efounder.service.script.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class IconTreeCellRender extends DefaultXTreeCellRenderer implements StringValue {
	/**
	 *
	 * @param treeTable JXTreeTable
	 */
	public IconTreeCellRender(JXTreeTable treeTable) {
		super();
		this.treeTable = treeTable;
	}

	@SuppressWarnings("unused")
	private boolean inpainting;
	
	/**
	 *
	 */
	private String shortText;
	
	/**
	 *
	 * @param g Graphics
	 */
	public void paint(Graphics g) {
		String fullText = super.getText();
		shortText = SwingUtilities.layoutCompoundLabel(
        this, g.getFontMetrics(), fullText, getIcon(),
        getVerticalAlignment(), getHorizontalAlignment(),
        getVerticalTextPosition(), getHorizontalTextPosition(),
        getItemRect(itemRect), iconRect, textRect,
        getIconTextGap());
		/** TODO: setText is more heavyweight than we want in this
		 * situation. Make JLabel.text protected instead of private.
		 */
		try {
			inpainting = true;
			// TODO JW: don't - override getText to return the short version
			// during painting
			setText(shortText); // temporarily truncate text
			super.paint(g);
		} finally {
			inpainting = false;
			setText(fullText); // restore full text
		}
	}

	/**
	 *
	 * @param itemRect Rectangle
	 * @return Rectangle
	 */
	private Rectangle getItemRect(Rectangle itemRect) {
		getBounds(itemRect);
		hierarchicalColumnWidth = treeTable.getColumn(0).getWidth();
//                LOG.info("rect" + itemRect);
		itemRect.width = hierarchicalColumnWidth - itemRect.x;
		return itemRect;
	}
	
	/**
	 *
	 * @param ers EFRowSet
	 * @param js int
	 * @return Icon
	 */
	public Icon getLeafTreeIcon(EFRowSet ers, int js) {

		if ( this.titleIcon != null ) {
			Icon ti = titleIcon.getLeafIcon(ers,js);
			if ( ti != null ) return ti;
		}

		try {
			return ExplorerIcons.getExplorerIcon("netbean/" + js + ".png");
		} catch(Exception ce) {
			
		}
//		try {
//			Object sIcon = ScriptUtil.runComponentFunction(scriptable, "getIconByRow", new String[] {"rowSet", "js"}, new Object[] {ers, String.valueOf(js)});
//			if (sIcon == null) {
//				return null;
//			}
//			String s = sIcon.toString();
//			String[] strs = s.split(";");
//			Icon icon = null;
//			for (int i = 0; i < strs.length; i++) {
//				if (icon != null) {
//					icon = new CompoundIcon(icon, icon = ExplorerIcons.getExplorerIcon(strs[i]));
//				} else {
//					icon = ExplorerIcons.getExplorerIcon(strs[i]);
//				}
//			}
//			return icon;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		return null;
	}
	
	/**
	 *
	 * @param tree JTree
	 * @param value Object
	 * @param sel boolean
	 * @param expanded boolean
	 * @param leaf boolean
	 * @param row int
	 * @param hasFocus boolean
 	 * @return Component
 	 */
	public Icon getOpenTreeIcon(EFRowSet ers, int js) {

		if ( this.titleIcon != null ) {
			Icon ti = titleIcon.getOpenIcon(ers,js);
			if ( ti != null ) return ti;
		}
		try {
			return ExplorerIcons.getExplorerIcon("netbean/" + js + ".png");
		} catch(Exception ce) {
			
		}
		
//		try {
//			Object sIcon = ScriptUtil.runComponentFunction(scriptable, "getIconByRow", new String[] {"rowSet", "js"}, new Object[] {ers, String.valueOf(js)});
//			if (sIcon == null) {
//				return null;
//			}
//			String s = sIcon.toString();
//			String[] strs = s.split(";");
//			Icon icon = null;
//			for (int i = 0; i < strs.length; i++) {
//				if (icon != null) {
//					icon = new CompoundIcon(icon, icon = ExplorerIcons.getExplorerIcon(strs[i]));
//				} else {
//					icon = ExplorerIcons.getExplorerIcon(strs[i]);
//				}
//			}
//			return icon;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		return null;
	}
	
	public Icon getCloseTreeIcon(EFRowSet ers, int js) {

		if ( this.titleIcon != null ) {
			Icon ti = titleIcon.getOpenIcon(ers,js);
			if ( ti != null ) return ti;
		}

		try {
			return ExplorerIcons.getExplorerIcon("netbean/" + js + ".png");
		} catch(Exception ce) {
			
		}
//		try {
//			Object sIcon = ScriptUtil.runComponentFunction(scriptable, "getCloseIconByRow", new String[] {"rowSet", "js"}, new Object[] {ers, String.valueOf(js)});
//			if (sIcon == null) {
//				return null;
//			}
//			String s = sIcon.toString();
//			String[] strs = s.split(";");
//			Icon icon = null;
//			for (int i = 0; i < strs.length; i++) {
//				if (icon != null) {
//					icon = new CompoundIcon(icon, icon = ExplorerIcons.getExplorerIcon(strs[i]));
//				} else {
//					icon = ExplorerIcons.getExplorerIcon(strs[i]);
//				}
//			}
//			return icon;
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
		return null;
	}
	
	/**
	 *
	 * @param a Scriptable
	 */
	public void setScriptable(Scriptable a) {
		scriptable = a;
	}
	
	/**
	 *
	 * @param ti TitleIcon
	 */
	public void setTitleIcon(TitleIcon ti) {
		this.titleIcon = ti;
	}
	
	/**
	 *
	 */
	protected TitleIcon titleIcon = null;
	
	/**
	 *
	 */
	protected Scriptable scriptable = null;
	protected int rowSetJS = 1;
	
	/**
	 *
	 * @param tree JTree
	 * @param value Object
	 * @param sel boolean
	 * @param expanded boolean
	 * @param leaf boolean
	 * @param row int
	 * @param hasFocus boolean
	 * @return Component
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
		DataSetTreeTableNode node = (DataSetTreeTableNode) value;
		EFRowSet rowSet =  (EFRowSet) node.getRowSet();

		Icon rowIcon = null;

		if (rowSet != null) {
			rowIcon = rowSet.getRowIcon();
			if ( rowIcon == null ) rowIcon = ExplorerIcons.ICON_RUN;
			String sjs = rowSet.getString("JS", "0");
			rowSetJS = Integer.parseInt(sjs) + 1;
		}

		if (node != null && rowSet != null) {
			if ( leaf ) {
				Icon leafIcon = getLeafTreeIcon(rowSet,rowSetJS);
				if ( leafIcon == null ) {
					leafIcon = rowIcon; 
				} else {
					leafIcon = new CompoundIcon(leafIcon,rowIcon);
				}
				setLeafIcon(leafIcon);
			}
			Icon openIcon = getOpenTreeIcon( rowSet, rowSetJS);
			if ( openIcon == null ) {
				openIcon = rowIcon;
			} else {
				openIcon = new CompoundIcon(openIcon,rowIcon);
			}
			setOpenIcon(openIcon);
			Icon closeIcon = getCloseTreeIcon( rowSet, rowSetJS);
			if ( closeIcon == null ) {
				closeIcon = rowIcon;
			} else {
				closeIcon = new CompoundIcon(closeIcon,rowIcon);
			}
			setClosedIcon(closeIcon);
		}
		JComponent comp = (JComponent)super.getTreeCellRendererComponent(tree, getHierarchicalTableValue(value,rowSetJS), sel, expanded, leaf, row, hasFocus);
		if ( this.titleIcon != null ) {
			// 配置
			this.titleIcon.confing((JComponent)comp,rowSet,rowSetJS);
		}
		return comp;
	}


	/**
	 *
	 */
	protected int hierarchicalColumnWidth = 0;
	
	/**
	 *
	 */
	protected JXTreeTable treeTable = null;
	
	/**
	 *
	 * @return JXTreeTable
	 */
	public JXTreeTable getTreeTable() {
		return treeTable;
	}

	/**
	 *
	 * @param treeTable JXTreeTable
	 */
	public void setTreeTable(JXTreeTable treeTable) {
		this.treeTable = treeTable;
	}

	/**
	 *
	 * @param node the node in the treeModel as passed into the TreeCellRenderer
	 * @return the corresponding value of the hierarchical cell in the TreeTableModel
	 */
	private Object getHierarchicalTableValue(Object node,int js) {
		//
		if ( node instanceof DataSetTreeTableNode ) {
			DataSetTreeTableNode dataSetNode = (DataSetTreeTableNode) node;
			EFRowSet rowSet =  (EFRowSet) dataSetNode.getRowSet();
			if ( rowSet != null && titleIcon != null ) {
				String title = this.titleIcon.getTitle(rowSet,js);
				if ( title != null && title.trim().length() > 0 ) return title;
			}
		}

		Object val = node;



		if (treeTable != null) {

			int treeColumn = treeTable.getTreeTableModel().getHierarchicalColumn();
			Object o = null;
			if (treeColumn >= 0) {
//				following is unreliable during a paint cycle
//				somehow interferes with BasicTreeUIs painting cache
//                        o = treeTable.getValueAt(row, treeColumn);
//				ask the model - that's always okay
//				might blow if the TreeTableModel is strict in
//				checking the containment of the value and
//				this renderer is called for sizing with a prototype
				o = treeTable.getTreeTableModel().getValueAt(node, treeColumn);
			}
			val = o;
		}
		return val;
	}

	/**
	 * {@inheritDoc} <p>
	 */
	public String getString(Object node) {
		return StringValues.TO_STRING.getString(getHierarchicalTableValue(node,rowSetJS));
	}

	// Rectangles filled in by SwingUtilities.layoutCompoundLabel();
	private final Rectangle iconRect = new Rectangle();
	
	/**
	 *
	 */
	private final Rectangle textRect = new Rectangle();
	
	// Rectangle filled in by this.getItemRect();
	/**
	 *
	 */
	private final Rectangle itemRect = new Rectangle();
}
