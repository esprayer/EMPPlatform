package com.efounder.dbc.data;

;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DataItem
    extends Object implements java.io.Serializable {
    public static int SHOW_BH = 1;
    public static int SHOW_MC = 2;
    public static int SHOW_BH_MC = 3;
    public int showType = 3;
    protected String bh;
    protected transient String name;
    private transient Object data;

    public DataItem(String i, String s) {
        bh = i;
        if(bh==null)bh="";
        name = s;
    }

    public DataItem(String i, String s, int type) {
        bh = i;
        name = s;
        showType = type;
    }

    public DataItem(int i, String s) {
        bh = String.valueOf(i);
        name = s;
    }

    public DataItem(int i, String s, int type) {
        bh = String.valueOf(i);
        name = s;
        showType = type;
    }

    public DataItem(String i, String s, Object o) {
        bh = i;
        name = s;
        data = o;
    }

    public DataItem(String i, String s, Object o, int type) {
        bh = i;
        name = s;
        data = o;
        showType = type;
    }

    public String toString() {
        if(showType == SHOW_BH){
            return bh;
        }else if(showType == SHOW_MC){
            return name;
        }
        return bh + " " + name;
    }

    public boolean equals(Object obj) {
        if (obj instanceof String) {
            if(obj==null)obj="";
            if (bh.equals(obj))
                return true;
        }
        if (obj instanceof DataItem) {
            DataItem us = (DataItem) obj;
            String kk=us.bh;
            if(kk==null)kk="";
            if (bh.equals(kk))
                return true;
        }
        return false;
    }

    public int getShowType() {
        return showType;
    }

    public String getBh() {
        return bh;
    }

    public String getName() {
        return name;
    }

    public Object getData() {
        return data;
    }

    public void setShowType(int type) {
        this.showType = type;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
