package com.swing.history;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Text with a history.
 * @version 1.0
 */
public class HistoryText {

    public HistoryText(JTextComponent text, String name) {
        this.text = text;
        setModel(name);
        index = -1;
    }

    public void fireActionPerformed() {

    }

    /**
     * Returns the active item's index
     * @return int The active index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set the active index
     * @param index The active index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Returns the underlying history controller.
     */
    public HistoryModel getModel() {
        return historyModel;
    }

    /**
     * Sets the history list controller.
     * @param name The model name
     */
    public void setModel(String name) {
        if (name == null)
            historyModel = null;
        else
            historyModel = HistoryModel.getModel(name);
        index = -1;
    }

    /**
     * Sets if selecting a value from the popup should immediately fire
     * an ActionEvent.
     */
    public void setInstantPopups(boolean instantPopups) {
        this.instantPopups = instantPopups;
    }

    /**
     * Returns if selecting a value from the popup should immediately fire
     * an ActionEvent.
     */
    public boolean getInstantPopups() {
        return instantPopups;
    }

    /**
     * Adds the currently entered item to the history.
     */
    public void addCurrentToHistory() {
        if (historyModel != null){
            String text = getText();
            if (text != null || text.trim().length() != 0) {
                historyModel.addItem(text);
            }
        }
        index = 0;
    }

    /**
     * Return the document of the text
     */
    public Document getDocument() {
        return text.getDocument();
    }

    /**
     * Subclasses can override this to provide funky history behavior,
     * for JTextPanes and such.
     */
    public String getText() {
        return text.getText();
    }

    /**
     * Subclasses can override this to provide funky history behavior,
     * for JTextPanes and such.
     */
    public void setText(String text) {
        this.index = -1;
        this.text.setText(text);
    }

    /**
     * Subclasses can override this to provide funky history behavior,
     * for JTextPanes and such.
     */
    public int getInputStart() {
        return 0;
    }

    /**
     *
     * @param t String
     * @param x int
     * @param y int
     */
    public void showPopupMenu(String t, int x, int y) {
        if (historyModel == null)
            return;
        KeyHandler key = new KeyHandler();
        popup = null;
        popup = new JPopupMenu();
        //add all the items stored in the model
        String item;
        JMenuItem menuItem;
        for (int i = 0; i < historyModel.getSize(); i++) {
            item = historyModel.getItem(i);
            if (item.startsWith(t) && !item.equals(t)) {
                menuItem = new JMenuItem(item);
                menuItem.setActionCommand(String.valueOf(i));
                menuItem.addActionListener(new ActionHandler());
                menuItem.addMenuKeyListener(key);
                popup.add(menuItem);
                menuItem = null;
            }
        }
        showPopupMenu(popup, text, x, y, true);
    }

    public void showPopupMenu(JPopupMenu popup, Component comp,
                              int x, int y, boolean point) {
        int offsetX = 0;
        int offsetY = 0;

        int extraOffset = (point ? 1 : 0);

        Component win = comp;
        while (! (win instanceof Window || win == null)) {
            offsetX += win.getX();
            offsetY += win.getY();
            win = win.getParent();
        }

        if (win != null) {
            int height = popup.getPreferredSize().height;
            if (height < 20)
                height = 0;
            popup.setPopupSize(text.getWidth(), height);
            Dimension size = popup.getPreferredSize();
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            if (x + offsetX + size.width + win.getX() > screenSize.width
                && x + offsetX + win.getX() >= size.width) {
                if (point)
                    x -= (size.width + extraOffset);
                else
                    x = (win.getWidth() - size.width - offsetX + extraOffset);
            } else {
                x += extraOffset;
            }
            if (y + offsetY + size.height + win.getY() > screenSize.height
                && y + offsetY + win.getY() >= size.height) {
                if (point)
                    y = (win.getHeight() - size.height - offsetY + extraOffset);
                else
                    y = -size.height - 1;
            } else {
                y += extraOffset;
            }
            popup.show(comp, x, y);
        } else{
            popup.show(comp, x + extraOffset, y + extraOffset);
        }
        text.requestFocus();
    }

    /**
     * Show popupmenu
     * @param search boolean
     */
    public void showPopupMenu(boolean search, String value) {
        String t;
        if (search && value != null)
            t = value;
        else
            t = "";
        showPopupMenu(t, 0, text.getHeight());
    }

    public JPopupMenu getPopupMenu() {
        return popup;
    }

    /**
     * 处理事件.
     */
    public void processShowEvent() {
        String value = getText();
        if (value == null || value.trim().length() == 0) {
            JPopupMenu menu = getPopupMenu();
            if (menu != null || menu.isVisible()) {
                menu.setVisible(false);
            }
            return;
        }
        showPopupMenu(true, value);
    }

    /**
     * 处理回车事件。
     * @throws NumberFormatException
     */
    public void processEnterEvent() throws NumberFormatException {
        JPopupMenu menu1 = getPopupMenu();
        if (menu1 != null) {
            if (menu1.isVisible()) {
                MenuElement[] elements = menu1.getSubElements();
                JMenuItem item;
                for (int i = 0, n = elements.length; i < n; i++) {
                    item = (JMenuItem) elements[i];
                    if (item.isArmed()) {
                        int ind = Integer.parseInt(item.getActionCommand());
                        setText(historyModel.getItem(ind));
                    }
                }
                menu1.setVisible(false);
            }else{
                processShowEvent();
            }
        }
    }

    /**
     * 处理删除事件。
     * @throws NumberFormatException
     */
    public void processDeleteEvent() throws NumberFormatException {
        JPopupMenu menu = getPopupMenu();
        if (menu != null) {
            MenuElement[] elements = menu.getSubElements();
            JMenuItem item;
            for (int i = 0, n = elements.length; i < n; i++) {
                item = (JMenuItem) elements[i];
                if (item.isArmed()) {
                    historyModel.removeElement(item.getText());
                    showPopupMenu(true, getText().trim());
                }
            }
        }
    }

    private JTextComponent text;
    private HistoryModel historyModel;
    private int index;
    private String current;
    private JPopupMenu popup;
    private boolean instantPopups;

    class KeyHandler
            implements MenuKeyListener {

        public void menuKeyPressed(MenuKeyEvent e) {
        }

        public void menuKeyReleased(MenuKeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DELETE:
                    HistoryText.this.processDeleteEvent();
                    break;
            }
        }

        public void menuKeyTyped(MenuKeyEvent e) {
        }

    }

    class ActionHandler
            implements ActionListener {

        public void actionPerformed(ActionEvent evt) {
            int ind = Integer.parseInt(evt.getActionCommand());
            if (ind == -1) {
                if (index != -1)
                    setText(current);
            } else {
                setText(historyModel.getItem(ind));
                index = ind;
            }
            if (instantPopups) {
                addCurrentToHistory();
                fireActionPerformed();
            }
        }
    }
}
