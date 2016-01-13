package com.swing.history;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * 带有记忆功能的文本框。
 *
 * @version 1.0 {初始改进版本}${2007-6-21}
 */
public class HistoryTextField
        extends JTextField implements FocusListener, DocumentListener {

    /**
     * Creates a new history text field.
     *
     * @param name   The history model name
     */
    public HistoryTextField() {
        controller = new HistoryText(this, null) {
            public void fireActionPerformed() {
                HistoryTextField.this.fireActionPerformed();
            }
        };

        /**
         * 鼠标事件。
         */
        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

        addFocusListener(this);
        getDocument().addDocumentListener(this);
    }

    /**
     * Sets if selecting a value from the popup should immediately fire
     * an ActionEvent.
     */
    public void setInstantPopups(boolean instantPopups) {
        controller.setInstantPopups(instantPopups);
    }

    /**
     * Returns if selecting a value from the popup should immediately fire
     * an ActionEvent.
     */
    public boolean getInstantPopups() {
        return controller.getInstantPopups();
    }

    /**
     * Returns the underlying history model.
     */
    public HistoryModel getModel() {
        return controller.getModel();
    }

    /**
     * Sets the history list model.
     *
     * @param name The model name
     */
    public void setModel(String name) {
        controller.setModel(name);
        repaint();
    }

    /**
     * Adds the currently entered item to the history.
     */
    public void addCurrentToHistory() {
        controller.addCurrentToHistory();
    }

    /**
     * Sets the displayed text.
     */
    public void setText(String text) {
        super.setText(text);
        controller.setIndex( -1);
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.
     */
    public void fireActionPerformed() {
        super.fireActionPerformed();
    }

    /**
     * 键盘事件.
     * @param evt KeyEvent
     */
    protected void processKeyEvent(KeyEvent evt) {
        if (!isEnabled())
            return;

        if (evt.getID() == KeyEvent.KEY_RELEASED) {
            switch (evt.getKeyCode()) {
                case KeyEvent.VK_0:
                case KeyEvent.VK_1:
                case KeyEvent.VK_2:
                case KeyEvent.VK_3:
                case KeyEvent.VK_4:
                case KeyEvent.VK_5:
                case KeyEvent.VK_6:
                case KeyEvent.VK_7:
                case KeyEvent.VK_8:
                case KeyEvent.VK_9:
                case KeyEvent.VK_A:
                case KeyEvent.VK_B:
                case KeyEvent.VK_C:
                case KeyEvent.VK_D:
                case KeyEvent.VK_E:
                case KeyEvent.VK_F:
                case KeyEvent.VK_G:
                case KeyEvent.VK_H:
                case KeyEvent.VK_I:
                case KeyEvent.VK_J:
                case KeyEvent.VK_K:
                case KeyEvent.VK_L:
                case KeyEvent.VK_M:
                case KeyEvent.VK_N:
                case KeyEvent.VK_O:
                case KeyEvent.VK_P:
                case KeyEvent.VK_Q:
                case KeyEvent.VK_R:
                case KeyEvent.VK_S:
                case KeyEvent.VK_T:
                case KeyEvent.VK_U:
                case KeyEvent.VK_V:
                case KeyEvent.VK_W:
                case KeyEvent.VK_X:
                case KeyEvent.VK_Y:
                case KeyEvent.VK_Z:
                case KeyEvent.VK_BACK_SPACE:
                case KeyEvent.VK_SPACE:
                    processShowEvent();
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_DOWN:
                    processUpDownEvent();
                    break;
                case KeyEvent.VK_ENTER:
                    processEnterEvent();
                    break;
                case KeyEvent.VK_DELETE:
                    processDeleteEvent();
                    break;
            }
        }

        if (!evt.isConsumed())
            super.processKeyEvent(evt);
    }

    /**
     * 处理事件.
     */
    private void processShowEvent() {
        isSaveable = false;
        controller.processShowEvent();
        requestFocus();
    }

    /**
     * 处理UpDown事件.
     */
    private void processUpDownEvent() {
        JPopupMenu menu = controller.getPopupMenu();
        if (menu == null || !menu.isVisible()) {
            controller.showPopupMenu(true, null);
        }
        requestFocus();
    }

    /**
     * 处理回车事件.
     * @throws NumberFormatException
     */
    private void processEnterEvent() {
        controller.processEnterEvent();
        requestFocus();
    }

    /**
     * 处理删除事件.
     * @throws NumberFormatException
     */
    private void processDeleteEvent() {
        controller.processDeleteEvent();
        requestFocus();
    }

    /**
     * 鼠标事件.
     * @param evt MouseEvent
     */
    protected void processMouseEvent(MouseEvent evt) {
        if (!isEnabled())
            return;

        switch (evt.getID()) {
            case MouseEvent.MOUSE_PRESSED:
                Border border = getBorder();
                Insets insets = border.getBorderInsets(HistoryTextField.this);
                if (evt.getX() >= getWidth() - insets.right
                    || evt.getClickCount() == 2){
                    controller.showPopupMenu(evt.isShiftDown(), null);
                    requestFocus();
                }
                else
                    super.processMouseEvent(evt);
                break;
            case MouseEvent.MOUSE_EXITED:
                setCursor(Cursor.getDefaultCursor());
                super.processMouseEvent(evt);
                break;
            default:
                super.processMouseEvent(evt);
                break;
        }
    }

    /**
     * Invoked when a component gains the keyboard focus.
     *
     * @param e FocusEvent
     * @todo Implement this java.awt.event.FocusListener method
     */
    public void focusGained(FocusEvent e) {
        ArrayList coorList = ascendParentCoordinations();
        int parentCount = coorList.size();
        String groupPrefix = "";
        String objectsName = "";
        if (parentCount > 0) {
            String templeStr = null;
            for (int i = parentCount - 1; i >= 0; i--) {
                //第一个为组前缀
                if (i == parentCount - 1) {
                    groupPrefix = coorList.get(i).toString();
                }
                templeStr = coorList.get(i).toString();
                objectsName += "." + templeStr;
                templeStr = null;
            }
            if (objectsName.length() > 0) {
                objectsName = objectsName.substring(1);
            }
        }
        HistoryModel.loadHistory(groupPrefix);
        setModel(objectsName);
    }

    /**
     * 追溯父级的坐标以串联为唯一的名称标示。
     *
     * @return ArrayList
     */
    private ArrayList ascendParentCoordinations() {
        ArrayList coorList = new ArrayList();

        java.awt.Component component  = null;
        java.awt.Component rootPanel  = getRootPane();
        java.awt.Component rootParent = null;
        if (rootPanel != null) {
            rootParent = rootPanel.getParent();
        }
        if (rootParent != null && this != null) {
            component = this;
            int coorX = 0;
            int coorY = 0;
            String coordinate, rootParentName;
            while (component != null && !component.equals(rootParent)) {
                coorX = component.getX();
                coorY = component.getY();
                coordinate = "[" + Integer.toString(coorX) + "," + Integer.toString(coorY) + "]";
                coorList.add(coordinate);
                //avoid VM leak
                coordinate = null;
                component = component.getParent();
            }
            //计算rootParent的名称信息
            rootParentName = rootParent.getClass().toString();
            String rootParentStr = rootParent.toString();
            String[] values = rootParentStr.split(",");
            String tmpStr;
            if (values != null && values.length > 0) {
                for (int i = 0, n = values.length; i < n; i++) {
                    tmpStr = values[i];
                    if (tmpStr.indexOf("title=") >= 0) {
                        rootParentStr = tmpStr.substring(tmpStr.indexOf("=") + 1);
                    }
                }
            } else {
                rootParentStr = "";
            }
            rootParentName += "." + rootParentStr;
            coordinate = "[" + rootParentName + "]";
            coorList.add(coordinate);
            coordinate = null;
        }
        return coorList;
    }

    /**
     * 当该文本框失去焦点而且弹出菜单没有显示的时候添加到历史记录.
     * 其中一种特殊情况是：menu的菜单项个数为0。
     * @param e FocusEvent
     * @todo Implement this java.awt.event.FocusListener method
     */
    public void focusLost(FocusEvent e) {
        String enterString = getText();
        JPopupMenu menu = controller.getPopupMenu();
        if (menu != null && enterString.trim().length() != 0) {
            if (!menu.isVisible()) {
                addCurrentToHistory();
                HistoryModel.saveHistory();
            }
        }
    }

    private HistoryText controller;
    private boolean isSaveable;


    class MouseHandler
            extends MouseInputAdapter {

        public void mousePressed(MouseEvent evt) {
        }

        public void mouseReleased(MouseEvent evt) {
        }

        public void mouseMoved(MouseEvent evt) {
            Border border = getBorder();
            Insets insets = border.getBorderInsets(HistoryTextField.this);

            if (evt.getX() >= getWidth() - insets.right)
                setCursor(Cursor.getDefaultCursor());
            else
                setCursor(Cursor.getPredefinedCursor(
                        Cursor.TEXT_CURSOR));
        }

        public void mouseDragged(MouseEvent evt) {
        }
    }

    /**
     * 如果当前焦点的拥有者不是该文本框，则执行保存动作。
     *
     * @param e the document event
     * @todo Implement this javax.swing.event.DocumentListener method
     */
    public void insertUpdate(DocumentEvent e) {
        if (!isFocusOwner()) {
            addCurrentToHistory();
            HistoryModel.saveHistory();
        }
    }

    /**
     * Gives notification that a portion of the document has been removed.
     *
     * @param e the document event
     * @todo Implement this javax.swing.event.DocumentListener method
     */
    public void removeUpdate(DocumentEvent e) {
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e the document event
     * @todo Implement this javax.swing.event.DocumentListener method
     */
    public void changedUpdate(DocumentEvent e) {
    }

}
