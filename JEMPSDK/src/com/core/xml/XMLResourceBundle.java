package com.core.xml;

import java.util.*;
import org.jdom.*;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class XMLResourceBundle extends ListResourceBundle {
  private String[][] contents = null;
  /**
   *
   * @param XML JDOMXMLBaseObject
   */
  public XMLResourceBundle(java.util.List XMLList) {
    JDOMXMLBaseObject XML = null;
    java.util.Map valueList = new Hashtable();
    for(int i=0;i<XMLList.size();i++) {
      XML = (JDOMXMLBaseObject)XMLList.get(i);
      initResourceBundle(valueList, XML);
    }
    initContents(valueList);
  }
  /**
   *
   * @param XML JDOMXMLBaseObject
   */
  protected java.util.Map initResourceBundle(java.util.Map valueList,JDOMXMLBaseObject XML) {
    java.util.List nodeList = XML.BeginEnumerate(XML.Root);
    Element node = null;int index = 0;
    String name,caption;
    while ( nodeList != null ) {
      node = XML.Enumerate(nodeList,index++);
      if ( node == null ) break;
      name = node.getName();caption = XML.GetElementValue(node,"text",null);
      if ( name != null && caption != null ) {
        valueList.put(name,caption);
      }
    }
    XML.EndEnumerate();
    return valueList;
  }
  /**
   *
   * @param valueList Map
   */
  protected void initContents(java.util.Map valueList) {
    Object[] keys = valueList.keySet().toArray();
    contents = new String[keys.length][2];
    for(int i=0;i<keys.length;i++) {
      contents[i][0] = keys[i].toString();
      contents[i][1] = valueList.get(keys[i]).toString();
    }
  }
  /**
   *
   * @return Object[][]
   */
  public Object[][] getContents() {
    return contents;
  }

}
