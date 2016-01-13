package com.efounder.comp.table;

import javax.swing.*;
import java.awt.BorderLayout;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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
public class StatusCellRenderer extends JPanel implements TableCellRenderer {
  /**
   *
   * @return StatusCellRenderer
   */
  public static TableCellRenderer getInstance() {
    StatusCellRenderer r = new StatusCellRenderer();
    return r;
  }

  /**
   *
   */
  protected StatusCellRenderer() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  /**
   *
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(lbStatus, java.awt.BorderLayout.EAST);
    lbStatus.setOpaque(false);
    this.add(lbText, java.awt.BorderLayout.CENTER);
    lbText.setOpaque(false);
  }
  /**
   *
   * @param icon Icon
   */
  public void setIcon(Icon icon) {
    this.lbText.setIcon(icon);
//    this.lbStatus.setIcon(icon);
  }
  /**
   *
   * @param icon Icon
   */
  public void setStatusIcon(Icon icon) {
    this.lbStatus.setIcon(icon);
  }
  BorderLayout borderLayout1 = new BorderLayout();
  protected JLabel lbText = new JLabel();
  protected JLabel lbStatus = new JLabel();
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
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus, int row,
                                                 int column) {
    if (isSelected) {
      Color fc;

        fc=table.getSelectionForeground();
       super.setForeground(fc);
      String c1=com.efounder.eai.service.ParameterManager.getDefault().getSystemParam("BCOLOR");
      if(c1!=null&&c1.trim().length()>0){
        String[]aa=c1.split(",");
         fc=new Color(Integer.parseInt(aa[0]),Integer.parseInt(aa[1]),Integer.parseInt(aa[2]));
      }else
        fc=new Color(100,100,255);

       super.setBackground(fc);
    }
    else {
        super.setForeground((unselectedForeground != null) ? unselectedForeground
                                                           : table.getForeground());
        super.setBackground((unselectedBackground != null) ? unselectedBackground
                                                           : table.getBackground());
    }

    lbText.setFont(table.getFont());

    if (hasFocus) {
        setBorder( UIManager.getBorder("Table.focusCellHighlightBorder") );
        if (table.isCellEditable(row, column)) {
            super.setForeground( UIManager.getColor("Table.focusCellForeground") );
            super.setBackground( UIManager.getColor("Table.focusCellBackground") );
        }
    } else {
        setBorder(getNoFocusBorder());
    }

    setValue(value);

	return this;
  }
  protected void setValue(Object value) {
    lbText.setText((value == null) ? "" : value.toString());
  }
  private Color unselectedForeground;
  private Color unselectedBackground;
  /**
   * Overrides <code>JComponent.setForeground</code> to assign
   * the unselected-foreground color to the specified color.
   *
   * @param c set the foreground color to this value
   */
  public void setForeground(Color c) {
      super.setForeground(c);
      unselectedForeground = c;
  }

  /**
   * Overrides <code>JComponent.setBackground</code> to assign
   * the unselected-background color to the specified color.
   *
   * @param c set the background color to this value
   */
  public void setBackground(Color c) {
      super.setBackground(c);
      unselectedBackground = c;
  }
  private static Border getNoFocusBorder() {
      if (System.getSecurityManager() != null) {
          return SAFE_NO_FOCUS_BORDER;
      } else {
          return noFocusBorder;
      }
  }
  protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
}
