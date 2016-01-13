package jreportwh.jdof.classes.common.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

/**
 * <p>Title: ResourceHandler</p>
 * <p>Description: 用于多个ResourceBunlde的统一管理</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ResourceHandler
{
//    public static final ResourceBundle BUNDLE_LABEL;
//    static
//    {
//        LABEL_BUNDLE = ResourceBundle.getBundle("jreportwh.jdof.common.util.Labels", Locale.CHINESE);
//    }
    private static ResourceHandler mResourceHanlder=new ResourceHandler();
    private ResourceHandler()
    {
    }
    public static ResourceHandler getInstance(){
        return mResourceHanlder;
    }
    public String getString(ResourceBundle resourcebundle,String resourceName)throws Exception
    {
        return resourcebundle.getString(resourceName);
    }
}