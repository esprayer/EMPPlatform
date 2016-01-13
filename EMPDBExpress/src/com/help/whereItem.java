package com.help;

import java.io.*;
import java.util.List;

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
public class whereItem implements Serializable {
    public whereItem(String link,String key,String oper,String value) {
        this.link=link;
        this.key=key;
        this.oper=oper;
        this.value=value;
    }
    public whereItem(String link,List list){
        this.link=link;
        this.list=list;
    }
    List list = null;
    String link; //DBSQL
    String key;
    String oper;
    String value;
    public String toString() {
        String str = "";
        if (list == null) {
            str = " "+link +" "+ key + oper + value;
        } else {
            str = link + "(";
            for (int i = 0; i < list.size(); i++) {
                whereItem wi = (whereItem) list.get(i);
                str += wi.toString();
            }
            str += ")";
        }
        return str;
    }
}
