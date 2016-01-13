package com.efounder.bz.dbform.component.dc.table.header;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.*;
import com.efounder.bz.dbform.datamodel.column.*;

/**
 *
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

public class TableGroupColumnHeaderUI extends BasicTableHeaderUI {
  /**
   *
   * @param g Graphics
   * @param c JComponent
   */
  public void paint(Graphics g, JComponent c) {
    if (header.getColumnModel() == null) return;
    Rectangle clipBounds = g.getClipBounds();
    int column = 0;
    Dimension size = header.getSize();
    Rectangle cellRect = new Rectangle(0, 0, size.width, size.height);
    Hashtable h = new Hashtable();
    Enumeration enumeration = header.getColumnModel().getColumns();
    while (enumeration.hasMoreElements()) {
      cellRect.height = size.height;
      cellRect.y = 0;
      TableColumn aColumn = (TableColumn) enumeration.nextElement();
//      if ( aColumn.getHeaderValue() == null ) aColumn.setHeaderValue("T");
      Enumeration cGroups = ( (TableGroupColumnHeader) header).getColumnGroups(aColumn);
      if (cGroups != null) {
        int groupHeight = 0;
        while (cGroups.hasMoreElements()) {
          ColumnGroup cGroup = (ColumnGroup) cGroups.nextElement();
          Rectangle groupRect = (Rectangle) h.get(cGroup);
          if (groupRect == null) {
            groupRect = new Rectangle(cellRect);
            Dimension d = cGroup.getSize(header.getTable());
            groupRect.width = d.width;
            groupRect.height = d.height;
            h.put(cGroup, groupRect);
          }
          paintCell(g, groupRect, cGroup);
          groupHeight += groupRect.height;
          cellRect.height = size.height - groupHeight;
          cellRect.y = groupHeight;
        }
      }
      cellRect.width = aColumn.getWidth(); //+ columnMargin;
      if (cellRect.intersects(clipBounds)) {
        paintCell(g, cellRect, column);
      }
      cellRect.x += cellRect.width;
      column++;
    }
  }
  /**
   *
   * @param g Graphics
   * @param cellRect Rectangle
   * @param columnIndex int
   */
  private void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
    TableColumn aColumn = header.getColumnModel().getColumn(columnIndex);
    TableCellRenderer renderer = aColumn.getHeaderRenderer();
    if (renderer == null) {
      renderer = header.getDefaultRenderer();
    }
    Component component = renderer.getTableCellRendererComponent(
        header.getTable(), aColumn.getHeaderValue(), false, false,
        -1,
        columnIndex);
    rendererPane.add(component);
    rendererPane.paintComponent(g, component, header, cellRect.x,
                                cellRect.y,
                                cellRect.width, cellRect.height, true);
  }
  /**
   *
   * @param g Graphics
   * @param cellRect Rectangle
   * @param cGroup ColumnGroup
   */
  private void paintCell(Graphics g, Rectangle cellRect,
                         ColumnGroup cGroup) {
    TableCellRenderer renderer = cGroup.getHeaderRenderer();
    Component component = renderer.getTableCellRendererComponent(
        header.getTable(), cGroup.getHeaderValue(), false, false,
        -1,
        -1);
    rendererPane.add(component);
    rendererPane.paintComponent(g, component, header, cellRect.x,
                                cellRect.y,
                                cellRect.width, cellRect.height, true);
  }
  /**
   *
   * @return int
   */
  private int getHeaderHeight() {
    int height = 0;
    TableColumnModel columnModel = header.getColumnModel();
    for (int column = 0; column < columnModel.getColumnCount(); column++) {
      TableColumn aColumn = columnModel.getColumn(column);
      TableCellRenderer renderer = aColumn.getHeaderRenderer();
      if (renderer == null) {
        renderer = header.getDefaultRenderer();
    }
      Component comp = renderer.getTableCellRendererComponent(
          header.getTable(), aColumn.getHeaderValue(), false, false,
          -1, column);
      int cHeight = comp.getPreferredSize().height;
      Enumeration enume = ( (TableGroupColumnHeader) header).
          getColumnGroups(
              aColumn);
      if (enume != null) {
        while (enume.hasMoreElements()) {
          ColumnGroup cGroup = (ColumnGroup) enume.nextElement();
          cHeight += cGroup.getSize(header.getTable()).height;
        }
      }
      height = Math.max(height, cHeight);
    }
    return height;
  }
  /**
   *
   * @param width long
   * @return Dimension
   */
  private Dimension createHeaderSize(long width) {
    TableColumnModel columnModel = header.getColumnModel();
    width += columnModel.getColumnMargin() * columnModel.getColumnCount();
    if (width >= Integer.MAX_VALUE) {
      width = Integer.MAX_VALUE;
    }
    return new Dimension( (int) width, getHeaderHeight());
  }
  /**
   *
   * @param c JComponent
   * @return Dimension
   */
  public Dimension getPreferredSize(JComponent c) {
    long width = 0;
    Enumeration enumeration = header.getColumnModel().getColumns();
    while (enumeration.hasMoreElements()) {
      TableColumn aColumn = (TableColumn) enumeration.nextElement();
      width = width + aColumn.getPreferredWidth();
    }
    return createHeaderSize(width);
  }
}
