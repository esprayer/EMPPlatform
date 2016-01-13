package jenterprise.bof.classes.AppExplorerObject.messlist;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import jenterprise.bof.classes.AppExplorerObject.JMessageShowDetailWindow;
import jframework.foundation.classes.JActiveDComDM;

/**
 *
 * @version 1.0
 */
public class JIconListMouseListener
    extends MouseAdapter implements MouseMotionListener {

    private Object component;

    public JIconListMouseListener() {
    }

    public JIconListMouseListener(Object component) {
        this.component = component;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed and released)
     * on a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mouseClicked(MouseEvent e) {
        JList list = (JList) e.getSource();
        int index = list.locationToIndex(e.getPoint());
        if (index > -1) {
            MessageItem item = (MessageItem) list.getModel().getElementAt(index);
            if (item.isHelpable()) {
                try {
                    JActiveDComDM.MainApplication.BeginWaitCursor();
                    JMessageShowDetailWindow win;
                    win = new JMessageShowDetailWindow(item.F_MESSCAP,item.F_MESSOBJ, item.F_ID, item.F_MESSLINK);
                    JActiveDComDM.MainApplication.OpenObjectWindow("消息主体", null, "消息主体", win);
                    item.setReaded(true);
                } finally {
                    JActiveDComDM.MainApplication.EndWaitCursor();
                }
            }
        }
    }

    /**
     * Invoked when the mouse enters a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mouseEntered(MouseEvent e) {
        beautifyItem(e);
    }

    /**
     * 根据具体条件设置鼠标状态。
     * @param e MouseEvent
     */
    private void beautifyItem(MouseEvent e) {
        JList list = (JList) e.getSource();
        int index = list.locationToIndex(e.getPoint());
        if (index > -1) {
            MessageItem item = (MessageItem) list.getModel().getElementAt(index);
            //check if the item is handable
            if (item.isHelpable()) {
                list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else {
                list.setCursor(Cursor.getDefaultCursor());
            }
        }
    }

    /**
     * Invoked when the mouse exits a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mouseExited(MouseEvent e) {
        JList list = (JList) e.getSource();
        int index = list.locationToIndex(e.getPoint());
        if (index > -1) {
        }
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mousePressed(MouseEvent e) {
        JList list = (JList) e.getSource();
        int index = list.locationToIndex(e.getPoint());
        if (index > -1) {
            MessageItem item = (MessageItem) list.getModel().getElementAt(index);
            Rectangle rect = list.getCellBounds(index, index);
            list.repaint(rect);
        }
    }

    /**
     * Invoked when a mouse button has been released on a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mouseReleased(MouseEvent e) {
        JList list = (JList) e.getSource();
        int index = list.locationToIndex(e.getPoint());
        if (index > -1) {

        }
    }

    /**
     * Invoked when a mouse button is pressed on a component and then dragged.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseMotionListener method
     */
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Invoked when the mouse cursor has been moved onto a component but no
     * buttons have been pushed.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseMotionListener method
     */
    public void mouseMoved(MouseEvent e) {
        beautifyItem(e);
    }

}
