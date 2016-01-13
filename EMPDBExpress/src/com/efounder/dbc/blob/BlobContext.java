package com.efounder.dbc.blob;

import java.io.*;
import java.util.*;

import com.borland.dx.dataset.*;

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
public class BlobContext implements Serializable{
    private boolean bLoadOpen=false;
    String[] keyColumn;
    String[] blobColumn;
    java.util.Map blobMap;
    String where;
    public BlobContext() {
    }

    public void setLoadOpen(boolean b) {
        this.bLoadOpen = b;
    }
    public boolean isLoadOpen() {
        return bLoadOpen;
    }

    public String[] getKeyColumn() {
        return keyColumn;
    }

    public String[] getBlobColumn() {
        return blobColumn;
    }

    public String getWhere() {
        return where;
    }

    public Map getBlobMap() {
        return blobMap;
    }

    public void setBlobColumn(String[] blobColumn) {
        this.blobColumn = blobColumn;
    }

    public void setKeyColumn(String[] keyColumn) {
        this.keyColumn = keyColumn;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public void setBlobMap(Map blobMap) {
        this.blobMap = blobMap;
    }

    public String getKeys(DataSet ds) {
        String key = "";
        int type;
        for (int i = 0; i < keyColumn.length; i++) {
            type = ds.getColumn(keyColumn[i]).getDataType();
            if (type == Variant.STRING)
                key += keyColumn[i] + "='" + ds.getString(keyColumn[i]) +
                        "';";
            if (type == Variant.BIGDECIMAL || type == Variant.INT ||
                type == Variant.LONG) {
                Variant v = new Variant();
                ds.getVariant(keyColumn[i], v);
                key += keyColumn[i] + "=" + v.toString() +
                        ";";
            }
        }
        return key;
    }
}
