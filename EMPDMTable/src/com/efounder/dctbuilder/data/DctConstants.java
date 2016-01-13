package com.efounder.dctbuilder.data;

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
public class DctConstants {
    public final static String RIGHT_CTL="right_ctl";//是否控制权限
    public final static String INSERTITEM="insertitems";//插入的项
    public final static String DELETEITEM="deleteitems";//删除的项
    public final static String UPDATEITEM="updateitems";//更新的项
    public final static String METADATA="metadata";
    public final static String PLUGIN_KEY="pluginkey";
    public final static String WHERE="where";
    public final static String ORDER="order";
    public final static String CURBM="curbm";//当前编码
    public final static String CURJS="curjs";//当前极数
    public final static int MINFKEYCOUNT=500;//外键字典小于这个数时，直接取
    public final static String DCTREADBIT="1";
    public final static String DCTWRITEBIT="2";

    public final static String WRITEABLEDATA="writeabledata";
    public final static String ALLDATA="IFALLDATA";//数据是不是全的

    public final static boolean REMOTE=true;//是否远程调用
}
