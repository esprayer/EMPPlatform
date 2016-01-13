package jreportwh.jdof.classes.common.util;

import jtoolkit.xml.classes.*;
import java.util.*;
import com.pub.util.*;
import java.sql.*;
import org.jdom.*;
import com.f1j.swing.JBook;

/**
 * <p>Title: XMLUtil</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public final class XMLUtil extends Object
{
    static ResourceBundle res = ResourceBundle.getBundle("jreportwh.jdof.classes.common.util.Language");
  public static final String PREFIX="XML_";
    public XMLUtil()
    {
    }
    /**
     * 在XMLObject中名称为NODE的节点下构造字典树
     * @param RS          字典的查询结果集
     * @param XMLObject   JXMLOObject对象
     * @param BMJG        编码结构
     * @param NODE        树的根结点
     */
    public static void createTree(ResultSet rs, JXMLObject XMLObject, String BMJG, String rootName)
    {
        Element model = XMLObject.GetElementByName(rootName);
        Element node = null;
        Element pnode = null;
        int Length;
        String Parent_CODE;
        int OJS = 0;
        try {
            String BH = rs.getString("BH").trim();
            String MC = rs.getString("MC").trim();
            int JS = rs.getInt("JS");
            int MX = rs.getInt("MX");
            if (OJS == 0) {
                OJS = JS;
            }
            MC = StringFunction.Uni2GB(MC);
            if (JS == OJS) { //构造同级节点
                node = XMLObject.AddChildElement(model, "T_" + BH);
            } else if (JS > OJS) { //构造下级节点
                Length = StringFunction.GetStructLength(BMJG, JS - 1);
                Parent_CODE = BH.substring(0, Length);
                pnode = XMLObject.GetElementByName("T_" + Parent_CODE);
                if (pnode != null) {
                    node = XMLObject.AddChildElement(pnode, "T_" + BH);
                }
            }
            if (node != null) {
                XMLObject.SetElementValue(node, "BH", BH);
                XMLObject.SetElementValue(node, "MC", MC);
                XMLObject.SetElementValue(node, "JS", String.valueOf(JS));
                XMLObject.SetElementValue(node, "MX", String.valueOf(MX));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //子节点中叶子的个数
    public static int getLeafCount(Element element,int leafCount){
        for(Iterator iterator=element.getChildren().iterator();
            iterator.hasNext();){
            Element crtElement=(Element)iterator.next();
            if(crtElement.hasChildren()){
                getLeafCount(crtElement,leafCount);
            }else{
                leafCount++;
            }
        }
        return leafCount;
    }
    //深度
    public static int getDepth(Element element,int depth){
        for(Iterator iterator=element.getChildren().iterator();
            iterator.hasNext();){
            Element crtElement=(Element)iterator.next();
            if(crtElement.hasChildren()){
                depth++;
                getDepth(crtElement,depth);
            }
        }
        return depth;
    }
    /**
     *
     * @param XMLObject
     * @param parentElement
     * @param name
     * @return
     */
    public static Element getElementByName(JXMLObject XMLObject,Element parentElement,String name){
        Element element=XMLObject.GetElementByName(name);
        if(element==null){
            element = XMLObject.AddChildElement(parentElement,name);
        }
        return element;
    }
}
