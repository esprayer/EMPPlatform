package jservice.jbof.classes.GenerQueryObject;

import java.net.*;

import javax.swing.*;

import com.efounder.eai.data.JResponseObject;

import jfoundation.bridge.classes.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JQueryStubObject
    implements java.io.Serializable {
    /*
       <?xml version="1.0" encoding="gb2312"?>
       <!-- edited with XML Spy v4.2 U (http://www.xmlspy.com) by xskyline (Pansoft) -->
       <Query>
                 <!-- 前缀 -->
               <PrefixQuery  class="" method="" xml="" />
                <!--组织-->
               <OrganizeQuery class="" xml="" reclaimer=""/>
                 <!-- 实体 -->
               <EntityQuery  class="" xml="" entityicon=""/>
                 <!-- 后缀 -->
               <PostfixQuery class="" xml="" />
                 <!-- 定制 -->
               <CustomQuery  class="" xml="" />
       </Query>
     */
    public transient JResponseObject RO = null;
    public transient Hashtable PrefixQuery = new Hashtable();
    public transient Hashtable OrganizeQuery = new Hashtable();
    public transient Hashtable EntityQuery = new Hashtable();
    public transient Hashtable PostfixQuery = new Hashtable();
    public transient Hashtable QueryParamObject = new Hashtable();
    //菜单中获取到的参数
    public Object Param = null;
    public Object Data = null;
    public String Path = null;
    public String ServerName=null;
    //查询版本
    public String QueryVersion = null;
    //查询描述
    public String CaptionID = null;
    public String CaptionText = null;
    public String CaptionType = null;
    public boolean isECC6 = false;
    //前缀
    public String PrefixQueryClass = null;
    public String PrefixQueryXML = null;
    public String PrefixMethod = null;
    public String PrefixParam = null;
    //后台数据组织
    public String OrganizeQueryClass = null;
    public String OrganizeQueryXML = null;
    public String OrganizeMethod = null;
    public String ReclaimerMethod = null;
    public String QueryBmStruct = null;
    public transient URL OrganizeQueryXMLURL = null;
    //显示实体
    public String EntityQueryClass = null;
    public transient URL EntityQueryXMLURL = null;
    public String ViewCustomClass  = null;
    public transient Object ViewCustomObject = null;
    public String EntityQueryXML = null;
    public transient Icon EntityIconURL = null;
    public String EntityIcon = null;
    //后缀
    public String PostfixQueryClass = null;
    public String PostfixQueryXML = null;
    //定制
    public String CustomQueryClass = null;
    public String CustomQueryXML = null;
    //
    public Object CustomObject = null;
    //外部数据查询
    public String ExteriorQueryClass=null;
    public String ExteriorMethod=null;
    public List ExteriorList = null;
    //数据深层加工
    public String DeepQueryClass = null;
    public String DeepMethod = null;
    //绘制器
    public String PainterQueryClass = null;

    public JQueryStubObject() {
    }

}
