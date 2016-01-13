package com.efounder.service.eai;

import com.core.xml.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
           RequestObject.ActiveObjectName,
          RequestObject.ActiveObjectMethodName,
          RequestObject.ParamObject,
          RequestObject.DataObject,
          RequestObject.CustomObject,
          RequestObject.AdditiveObject);

 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface EAIService {
  public void   initEAIService(Object stubObject);
  public Object startEAIService(StubObject context,String ON,String OMN,Object PO,Object DO,Object CO,Object AO) throws Exception;
  public Object endEAIService  (StubObject context,String ON,String OMN,Object PO,Object DO,Object CO,Object AO) throws Exception;
  public Object errorEAIService(StubObject context,String ON,String OMN,Object PO,Object DO,Object CO,Object AO) throws Exception;
}
