package jservice.jbof.classes.GenerQueryObject.ext.condition.help;

import java.lang.reflect.*;
import java.util.*;

import com.efounder.eai.data.JParamObject;

import jframework.foundation.classes.*;
import jservice.jbof.classes.GenerQueryObject.ext.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public class JCHelpObject {
    /***
     *
     */

    private static ResourceBundle mResourceBundle = null;
    static {
        mResourceBundle = ResourceBundle.getBundle("jreport.swing.classes.wizard.imp.HelpCOBJDlg.help.HelpMap");
    }

    JCHelpObjectImpl helpObjImpl = new JCHelpObjectImpl();
    /**
     *
     */
    public JCHelpObject() {
    }

    /**
     *
     * @param itemInfo
     * @return
     */
    public boolean isHelpable(QueryColumn col) {
        if (col != null) {
            String methodName = getMapHelpMethodName(col);
            if (methodName != null && methodName.length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param itemInfo
     */
    public Object showHelp(QueryColumn col) {
        try {
            String methodName = getMapHelpMethodName(col);
            if (methodName != null && methodName.length() > 0) {
                JParamObject PO = JParamObject.Create();
                //执行DOF中的帮助方法,如果返回值为空则执行Impl中的方法
                Object data = invokeDofHelpObject(methodName, PO);
                if (data != null) {
                    return data;
                }
                else {
                    return invokeCobjHelpObject(methodName, PO);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param methodName
     * @return
     */
    private Object invokeCobjHelpObject(String methodName, JParamObject PO) {
        try {
            Method method = JCHelpObjectImpl.class.getMethod(methodName, new Class[] {JParamObject.class});
            if (method != null) {
                return method.invoke(helpObjImpl, new Object[] {PO});
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param methodName
     * @return
     */
    private Object invokeDofHelpObject(String methodName, JParamObject PO) {
        String[] helpData = null;
		try {
			helpData = (String[]) JActiveDComDM.DataActiveFramework.InvokeObjectMethod("HelpObject", methodName, PO);
		} catch (Exception e) {
			e.printStackTrace();
		}
        String obj = null;
        if (helpData != null) {
            obj = helpData[0];
        }
        return obj;

    }

    /**
     *
     * @param resourceName
     * @return
     */
    private String getMapHelpMethodName(QueryColumn col) {
        try{
            if (col != null) {
                return mResourceBundle.getString(col.getColumnID());
            }
        }catch(Exception e){
            return null;
        }
        return null;
    }

}