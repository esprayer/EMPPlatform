package jservice.jbof.classes.GenerQueryObject.ext.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface QueryColumn {
    public String getColumnName();

    public int getColumnIndex();

    public void setColumnName(String name);

    public void setColumnIndex(int colIndex);

    public String getColumnType();

    public void setColumnType(String type);

    public String getColumnID();

    public void setColumnID(String ID);
}