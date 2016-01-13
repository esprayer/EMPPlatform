package jservice.jbof.classes.GenerQueryObject.action;

import java.lang.reflect.*;

import jservice.jbof.classes.GenerQueryObject.action.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JBookActionManager {
    /**
     *
     */
    private JBookActionImpl mActionImpl = null;
    /**
     * 使用默认实现
     * @param book
     */
    public JBookActionManager() {
        mActionImpl = new JBookActionDefaultImpl();
    }

    /**
     *
     * @param actionImpl
     */
    public JBookActionManager(JBookActionImpl actionImpl) {
        if (actionImpl != null) {
            mActionImpl = actionImpl;
        }
    }

    /**
     *
     * @param actionImpl
     */
    public void setActionImpl(JBookActionImpl actionImpl) {
        if (actionImpl != null) {
            mActionImpl = actionImpl;
        }
    }

    /**
     *
     * @return
     */
    public JBookActionImpl getActionImpl() {
        return mActionImpl;
    }

    /**
     * 执行
     * @param action
     */
    public void execute(JBookAction action) {
        if (action == null) {
            return;
        }

        String actionName = action.getAction();
        Method method = JBookActionUtils.lookupActionImplMethod(actionName);
        try {
            if (method != null) {
                method.invoke(mActionImpl, new Object[] {action});
            }
            else {
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}