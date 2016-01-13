package com.efounder.dbc.swing.tree;

import javax.swing.*;
import javax.swing.tree.*;

import com.efounder.dbc.swing.render.*;
import com.efounder.eai.ide.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DictTree
    extends JTree {
  private Icon openIcon;
  private Icon closeIcon;
  private Icon leafIcon;
  public DictTree() {
      setRootVisible(true);
      try {
          DictTreeCellRender dtcr = new DictTreeCellRender();
          setCellRenderer(dtcr);
          setOpenIcon(ExplorerIcons.ICON_REMOVE_ALL_CUSTOM_VIEW);
          setCloseIcon(ExplorerIcons.ICON_ADD_CUSTOM_VIEW);
          setLeafIcon(ExplorerIcons.ICON_CUSTOM_VIEW);
      } catch (Exception e) {}
  }
  public void setOpenIcon(Icon openIcon) {
    this.openIcon = openIcon;
    ( (DefaultTreeCellRenderer) getCellRenderer()).setOpenIcon(openIcon);
  }

  public void setCloseIcon(Icon closeIcon) {
    this.closeIcon = closeIcon;
    ( (DefaultTreeCellRenderer) getCellRenderer()).setClosedIcon(closeIcon);
  }

  public void setLeafIcon(Icon leafIcon) {
    this.leafIcon = leafIcon;
    ( (DefaultTreeCellRenderer) getCellRenderer()).setLeafIcon(leafIcon);
  }

    public Icon getOpenIcon() {
    return openIcon;
  }

  public Icon getCloseIcon() {
    return closeIcon;
  }

  public Icon getLeafIcon() {
    return leafIcon;
  }

}
