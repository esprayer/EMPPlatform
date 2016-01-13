package com.efounder.service.tree;

import com.core.xml.*;
import java.util.Map;
import java.util.HashMap;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TreeDataStub extends StubObject implements TreeNodeDataObject{
  public TreeDataStub() {
  }
  public Object getValue(Object key,Object value){
    return getObject(key,value);
  }

}
