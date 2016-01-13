package com.pub.comp;

import java.text.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.*;

import com.incors.plaf.alloy.*;
import com.sun.java.swing.plaf.motif.*;
import com.sun.java.swing.plaf.windows.*;

/**
 * <p>Title:日期的选择框</p>
 * <p>Description: 按月为单位,显示整个月份的每天信息</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JDateComboBox
    extends JComboBox {

    static ResourceBundle res = ResourceBundle.getBundle("com.pansoft.pub.comp.Language");
    protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 初始化
     */
    public JDateComboBox() {
        this.setEditable(true);
        JTextField textField = ( (JTextField)this.getEditor().getEditorComponent());
        textField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    textFieldTransferFocus();
                }
            }
        });
        setSelectedItem(dateFormat.format(new java.util.Date()));
    }

    public void textFieldTransferFocus() {
        ( (JTextField)this.getEditor().getEditorComponent()).transferFocus();
    }

    /**
     * 设置日期格式
     * @param dateFormat 日期格式
     */
    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * 设计选择哪一天
     * @param item 一天的字符串.如2006-01-01
     */
    public void setSelectedItem(Object item) {
        // Could put extra logic here or in renderer when item is instanceof Date, Calendar, or String
        // Dont keep a list ... just the currently selected item
        removeAllItems(); // hides the popup if visible
        addItem(item);
        super.setSelectedItem(item);
    }

    public void updateUI() {
        ComboBoxUI cui = (ComboBoxUI) UIManager.getUI(this);
        if (cui instanceof AlloyComboBoxUI) {
            cui = new AlloyDateComboBoxUI();
        }
        if (cui instanceof MetalComboBoxUI) {
            cui = new MetalDateComboBoxUI();
        } else if (cui instanceof MotifComboBoxUI) {
            cui = new MotifDateComboBoxUI();
        } else if (cui instanceof WindowsComboBoxUI) {
            cui = new WindowsDateComboBoxUI();
        }
        setUI(cui);
    }

    // UI Inner classes -- one for each supported Look and Feel
    class AlloyDateComboBoxUI
        extends AlloyComboBoxUI {
        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    // UI Inner classes -- one for each supported Look and Feel
    class MetalDateComboBoxUI
        extends MetalComboBoxUI {
        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    class WindowsDateComboBoxUI
        extends WindowsComboBoxUI {
        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    class MotifDateComboBoxUI
        extends MotifComboBoxUI {
        protected ComboPopup createPopup() {
            return new DatePopup(comboBox);
        }
    }

    // DatePopup inner class
    class DatePopup
        implements ComboPopup, MouseMotionListener,
        MouseListener, KeyListener, PopupMenuListener {

        protected JComboBox comboBox;
        protected Calendar calendar;
        protected JPopupMenu popup;
        protected JLabel monthLabel;
        protected JPanel days = null;
        protected JLabel today = null;
        protected SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

        protected Color selectedBackground;
        protected Color selectedForeground;
        protected Color background;
        protected Color foreground;

        public DatePopup(JComboBox comboBox) {
            this.comboBox = comboBox;
            calendar = Calendar.getInstance();
            // check Look and Feel
            background = UIManager.getColor("ComboBox.background");
            foreground = UIManager.getColor("ComboBox.foreground");
            selectedBackground = UIManager.getColor(
                "ComboBox.selectionBackground");
            selectedForeground = UIManager.getColor(
                "ComboBox.selectionForeground");

            initializePopup();
        }

        //========================================
        // begin ComboPopup method implementations
        //
        public void show() {
            try {
                // if setSelectedItem() was called with a valid date, adjust the calendar
                calendar.setTime(dateFormat.parse(comboBox.getSelectedItem().
                                                  toString()));
            } catch (Exception e) {}
            updatePopup();
            popup.show(comboBox, 0, comboBox.getHeight());
        }

        public void hide() {
            popup.setVisible(false);
        }

        protected JList list = new JList();
        public JList getList() {
            return list;
        }

        public MouseListener getMouseListener() {
            return this;
        }

        public MouseMotionListener getMouseMotionListener() {
            return this;
        }

        public KeyListener getKeyListener() {
            return this;
        }

        public boolean isVisible() {
            return popup.isVisible();
        }

        public void uninstallingUI() {
            popup.removePopupMenuListener(this);
        }

        // end ComboPopup method implementations

        // begin Event Listeners
        // MouseListener

        public void mousePressed(MouseEvent e) {}

        public void mouseReleased(MouseEvent e) {}

        // something else registered for MousePressed
        public void mouseClicked(MouseEvent e) {
            if (!SwingUtilities.isLeftMouseButton(e))
                return;
            if (!comboBox.isEnabled())
                return;
            if (comboBox.isEditable()) {
                comboBox.getEditor().getEditorComponent().requestFocus();
            } else {
              //mod xft 2008.12.27 解决选择日期第一次选不上的问题
              comboBox.getEditor().getEditorComponent().requestFocus();
//                comboBox.requestFocus();
            }
            togglePopup();
        }

        protected boolean mouseInside = false;
        public void mouseEntered(MouseEvent e) {
            mouseInside = true;
        }

        public void mouseExited(MouseEvent e) {
            mouseInside = false;
        }

        // MouseMotionListener
        public void mouseDragged(MouseEvent e) {}

        public void mouseMoved(MouseEvent e) {}

        // KeyListener
        public void keyPressed(KeyEvent e) {}

        public void keyTyped(KeyEvent e) {}

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE ||
                e.getKeyCode() == KeyEvent.VK_ENTER) {
                togglePopup();
            }
        }

        /**
         * Variables hideNext and mouseInside are used to
         * hide the popupMenu by clicking the mouse in the JComboBox
         */
        public void popupMenuCanceled(PopupMenuEvent e) {}

        protected boolean hideNext = false;
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            hideNext = mouseInside;
        }

        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {}

        //
        // end Event Listeners
        //=================================================================

        //===================================================================
        // begin Utility methods
        //

        protected void togglePopup() {
            if (isVisible() || hideNext) {
                hide();
            } else {
                show();
            }
            hideNext = false;
        }

        //
        // end Utility methods
        //=================================================================

        // Note *** did not use JButton because Popup closes when pressed
        protected JLabel createUpdateButton(final int field, final int amount) {
            final JLabel label = new JLabel();
            final Border selectedBorder = new EtchedBorder();
            final Border unselectedBorder = new EmptyBorder(selectedBorder.
                getBorderInsets(new JLabel()));
            label.setBorder(unselectedBorder);
            label.setForeground(foreground);
            label.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    calendar.add(field, amount);
                    updatePopup();
                }

                public void mouseEntered(MouseEvent e) {
                    label.setBorder(selectedBorder);
                }

                public void mouseExited(MouseEvent e) {
                    label.setBorder(unselectedBorder);
                }
            });
            return label;
        }

        protected void initializePopup() {
            JPanel header = new JPanel(); // used Box, but it wasn't Opaque
            header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
            header.setBackground(background);
            header.setOpaque(true);

            JLabel label;
            label = createUpdateButton(Calendar.YEAR, -1);
            label.setText("<<");
            label.setToolTipText("PreviousYear");

            header.add(Box.createHorizontalStrut(12));
            header.add(label);
            header.add(Box.createHorizontalStrut(12));

            label = createUpdateButton(Calendar.MONTH, -1);
            label.setText("<");
            label.setToolTipText("PreviousMonth");
            header.add(label);

            monthLabel = new JLabel("", JLabel.CENTER);
            monthLabel.setForeground(foreground);
            header.add(Box.createHorizontalGlue());
            header.add(monthLabel);
            header.add(Box.createHorizontalGlue());

            label = createUpdateButton(Calendar.MONTH, 1);
            label.setText(">");
            label.setToolTipText("NextMonth");
            header.add(label);

            label = createUpdateButton(Calendar.YEAR, 1);
            label.setText(">>");
            label.setToolTipText("NextYear");

            header.add(Box.createHorizontalStrut(12));
            header.add(label);
            header.add(Box.createHorizontalStrut(12));

            popup = new JPopupMenu();
            popup.setBorder(BorderFactory.createLineBorder(Color.black));
            popup.setLayout(new BorderLayout());
            popup.setBackground(background);
            popup.addPopupMenuListener(this);
            popup.add(BorderLayout.NORTH, header);
        }

        // update the Popup when either the month or the year of the calendar has been changed
        protected void updatePopup() {
            monthLabel.setText(monthFormat.format(calendar.getTime()));
            if (days != null) {
                popup.remove(days);
            }
            days = new JPanel(new GridLayout(0, 7));
            days.setBorder(BorderFactory.createEtchedBorder(0));
            days.setBackground(background);
            days.setOpaque(true);

            Calendar setupCalendar = (Calendar) calendar.clone();
            setupCalendar.set(Calendar.DAY_OF_WEEK, setupCalendar.getFirstDayOfWeek());
//          for (int i = 0; i < 7; i++)
//          {
//              int dayInt = setupCalendar.get(Calendar.DAY_OF_WEEK);
            {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setForeground(foreground);
                //              if (dayInt == Calendar.SUNDAY)
                //              {
                label.setText(res.getString("String_12"));
                days.add(label);
                //              }
                //              else if (dayInt == Calendar.MONDAY)
                //              {
                JLabel l2 = new JLabel();
                l2.setHorizontalAlignment(JLabel.CENTER);
                l2.setForeground(foreground);
                l2.setText(res.getString("String_13"));
                days.add(l2);
                //              }
                //              else if (dayInt == Calendar.TUESDAY)
                //              {
                JLabel l3 = new JLabel();
                l3.setHorizontalAlignment(JLabel.CENTER);
                l3.setForeground(foreground);

                l3.setText(res.getString("String_14"));
                days.add(l3);
                //              }
                //              else if (dayInt == Calendar.WEDNESDAY)
                //              {
                JLabel l4 = new JLabel();
                l4.setHorizontalAlignment(JLabel.CENTER);
                l4.setForeground(foreground);

                l4.setText(res.getString("String_15"));
                days.add(l4);
                //              }
                //              else if (dayInt == Calendar.THURSDAY)
                //              {
                JLabel l5 = new JLabel();
                l5.setHorizontalAlignment(JLabel.CENTER);
                l5.setForeground(foreground);

                l5.setText(res.getString("String_16"));
                days.add(l5);
                //              }
                //              else if (dayInt == Calendar.FRIDAY)
                //              {
                JLabel l6 = new JLabel();
                l6.setHorizontalAlignment(JLabel.CENTER);
                l6.setForeground(foreground);

                l6.setText(res.getString("String_17"));
                days.add(l6);
                //              }
                //              else if (dayInt == Calendar.SATURDAY)
                //              {
                JLabel l7 = new JLabel();
                l7.setHorizontalAlignment(JLabel.CENTER);
                l7.setForeground(foreground);

                l7.setText(res.getString("String_18"));
                //              }
                days.add(l7);
                setupCalendar.roll(Calendar.DAY_OF_WEEK, true);
                //          }
            }
            setupCalendar = (Calendar) calendar.clone();
            setupCalendar.set(Calendar.DAY_OF_MONTH, 1);
            int first = setupCalendar.get(Calendar.DAY_OF_WEEK);
            for (int i = 0; i < (first - 1); i++) {
                days.add(new JLabel(""));
            }
            for (int i = 1;
                 i <= setupCalendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                final int day = i;
                final JLabel label = new JLabel(String.valueOf(day));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setForeground(foreground);
                label.addMouseListener(new MouseListener() {
                    public void mousePressed(MouseEvent e) {}

                    public void mouseClicked(MouseEvent e) {}

                    public void mouseReleased(MouseEvent e) {
                        label.setOpaque(false);
                        label.setBackground(background);
                        label.setForeground(foreground);
                        comboBox.requestFocus();
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        comboBox.setSelectedItem(dateFormat.format(calendar.
                            getTime()));
                        hide();
                        // hide is called with setSelectedItem() ... removeAll()
                        comboBox.requestFocus();
                    }

                    public void mouseEntered(MouseEvent e) {
                        label.setOpaque(true);
                        label.setBackground(selectedBackground);
                        label.setForeground(selectedForeground);
                    }

                    public void mouseExited(MouseEvent e) {
                        label.setOpaque(false);
                        label.setBackground(background);
                        label.setForeground(foreground);
                    }
                });

                days.add(label);
            }

            if (today != null) {
                popup.remove(today);
            }
            today = new JLabel();
            today.setHorizontalAlignment(JLabel.CENTER);
            today.setForeground(foreground);
            today.setText(res.getString("String_20"));
            today.addMouseListener(new MouseListener() {
                public void mousePressed(MouseEvent e) {}

                public void mouseClicked(MouseEvent e) {}

                public void mouseReleased(MouseEvent e) {
                    today.setOpaque(false);
                    today.setBackground(background);
                    today.setForeground(foreground);
                    calendar = Calendar.getInstance();
                    comboBox.requestFocus();
                    comboBox.setSelectedItem(dateFormat.format(calendar.
                        getTime()));
                    hide();
                    // hide is called with setSelectedItem() ... removeAll()
                    comboBox.requestFocus();
                }

                public void mouseEntered(MouseEvent e) {
                    today.setOpaque(true);
                    today.setBackground(selectedBackground);
                    today.setForeground(selectedForeground);
                }

                public void mouseExited(MouseEvent e) {
                    today.setOpaque(false);
                    today.setBackground(background);
                    today.setForeground(foreground);
                }
            });
            popup.add(BorderLayout.SOUTH, today);

            popup.add(BorderLayout.CENTER, days);
            popup.pack();
        }
    }

    //////////////////////////////////////////////////////////////
    // This is only included to provide a sample GUI
    //////////////////////////////////////////////////////////////
    public static void main(String args[]) {
        JFrame f = new JFrame();
        Container c = f.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(new JLabel("Date 1:"));
        c.add(new JDateComboBox());
        c.add(new JLabel("Date 2:"));
        JDateComboBox dcb = new JDateComboBox();
        dcb.setEditable(true);
        c.add(dcb);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setSize(500, 200);
        f.show();
    }

}
