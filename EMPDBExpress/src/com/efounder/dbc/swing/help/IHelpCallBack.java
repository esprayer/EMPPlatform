package com.efounder.dbc.swing.help;

import com.efounder.dctbuilder.data.ColumnMetaData;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface IHelpCallBack {

    public Object help(Object o);

    /**
     *
     * @param cmd ColumnMetaData
     */
    public void setColumnMetaData(ColumnMetaData cmd);

    /**
     * 对于自定义帮助类的地方，实现此方法返回外键字典，以方便Renderer的显示
     *
     * @return String
     */
    public String getHelpFKey();

}
