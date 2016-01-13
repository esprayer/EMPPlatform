package jservice.jbof.classes.GenerQueryObject.action;

import com.f1j.swing.*;
import java.util.ResourceBundle;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JBookAction
    implements JBookActionConstants {
    /**
     *
     */
    static ResourceBundle res = ResourceBundle.getBundle("jservice.jbof.classes.GenerQueryObject.action.Language");
  private JBook mBook = null;
    private String mAction = ACTION_NULL;
    private Object mParam = null;
    /**
     *
     */
    public JBookAction() {
    }

    /**
     *
     * @param source
     * @param actionType
     */
    public JBookAction(JBook source, String actionType) {
        this(source, actionType, null);
    }

    /**
     *
     * @param source
     * @param actionType
     * @param param
     */
    public JBookAction(JBook source, String action, Object param) {
        this.mBook = source;
        this.mAction = action;
        this.mParam = param;
    }

    /**
     *
     * @return
     */
    public String getAction() {
        return mAction;
    }

    /**
     *
     * @param action
     */
    public void setAction(String action) {
        if (action != null) {
            this.mAction = action;
        }
    }

    /**
     *
     * @return
     */
    public JBook getBook() {
        return mBook;
    }

    /**
     *
     * @param book
     */
    public void setBook(JBook book) {
        if (book != null) {
            this.mBook = book;
        }
    }

    /**
     *
     * @return
     */
    public Object getParam() {
        return mParam;
    }

    /**
     *
     * @param param
     */
    public void setParam(Object param) {
        if (param != null) {
            this.mParam = param;
        }
    }

    /**
     *
     * @return
     */
    public String toString() {
        if (mAction != null) {
            return mAction;
        }
        return "";
    }
}
