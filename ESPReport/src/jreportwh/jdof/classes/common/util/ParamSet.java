package jreportwh.jdof.classes.common.util;

import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Pansoft Copyright (c) 2003</p>
 * <p>Company: Pansoft</p>
 * @author Yamaksy
 * @version 1.0
 */

public class ParamSet
{
    private HashMap mParamSet;
    public ParamSet()
    {
        mParamSet = new HashMap();
    }

    public void add(String paramName, Object paramValue)
    {
        mParamSet.put(paramName, paramValue);
    }

    public void add(String paramName, int paramValue)
    {
        mParamSet.put(paramName, new Integer(paramValue));
    }

    public void add(String paramName, boolean paramValue)
    {
        mParamSet.put(paramName, new Boolean(paramValue));
    }

    public String getString(String paramName)
    {
        if (mParamSet.containsKey(paramName)) {
            return mParamSet.get(paramName).toString();
        }
        return null;
    }

    public int getInt(String paramName)
    {
        if (mParamSet.containsKey(paramName)) {
            return ( (Integer) mParamSet.get(paramName)).intValue();
        }
        return 0;
    }

    public boolean getBoolean(String paramName)
    {
        if (mParamSet.containsKey(paramName)) {
            return ( (Boolean) mParamSet.get(paramName)).booleanValue();
        }
        return false;
    }

    public Object getObject(String paramName)
    {
        if (mParamSet.containsKey(paramName)) {
            return mParamSet.get(paramName);
        }
        return null;
    }
}