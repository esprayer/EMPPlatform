package jservice.jbof.classes.GenerQueryObject.action.util;

import java.util.*;

import jbof.gui.window.classes.style.mdi.imp.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JBookActionMapper {
    /**
     *
     */
    private static ResourceBundle mResourceBundle = null;

    /**
     *
     */
    public JBookActionMapper(String path) {
        if (path != null) {
            this.setMappingFilePath(path);
        }
    }

    /**
     *
     * @return
     */
    public String getAction(JMenuItemStub mis) {
        String key = mis.Name;
        String value = getString(key);
        try {
            if (value != null && value.length() > 0) {
                return value;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     */
    public void setMappingFilePath(String path) {
        mResourceBundle = ResourceBundle.getBundle(path);
    }

    /**
     *
     * @param resourceName
     * @return
     */
    private String getString(String resourceName) {
        return mResourceBundle.getString(resourceName);
    }

}