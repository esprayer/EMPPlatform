package jdatareport.dof.classes.report;

import java.util.*;

import jdatareport.dof.classes.report.event.*;

/**
 *
 * <p>Title: JDRCtrl</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */
public class JDRCtrl {
    /**
     *
     */
    private Vector mListeners = new Vector();
    /**
     * 添加事件监听器
     * @param listener
     */
    public void addListener(JDRListener listener) {
        if (listener != null) {
            this.mListeners.add(listener);
        }
    }

    /**
     * 删除事件监听器
     * @param listener
     */
    public void removeListener(JDRListener listener) {
        if (listener != null) {
            this.mListeners.remove(listener);
        }
    }

    /**
     * 事件通知
     * @param event
     */
    public void notifyListeners(JDREvent event) {
        if (event != null) {
            for (Enumeration e = mListeners.elements(); e.hasMoreElements(); ) {
                JDRListener listener = (JDRListener) e.nextElement();
                listener.action(event);
            }
        }
    }

}