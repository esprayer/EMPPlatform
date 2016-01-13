package com.efounder.service.eai;

import com.core.xml.*;

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
public class EAIServiceAdapter implements EAIService {
  /**
   *
   */
  public EAIServiceAdapter() {
  }

  /**
   * endEAIService
   *
   * @param context StubObject
   * @param ON String
   * @param OMN String
   * @param PO Object
   * @param DO Object
   * @param CO Object
   * @param AO Object
   * @return Object
   * @throws Exception
   * @todo Implement this com.efounder.service.eai.EAIService method
   */
  public Object endEAIService(StubObject context, String ON, String OMN,
                              Object PO, Object DO, Object CO, Object AO) throws
      Exception {
    return null;
  }
  protected Object rootStubObject = null;
  /**
   * initEAIService
   *
   * @param stubObject Object
   * @todo Implement this com.efounder.service.eai.EAIService method
   */
  public void initEAIService(Object stubObject) {
    rootStubObject = stubObject;
  }

  /**
   * startEAIService
   *
   * @param context StubObject
   * @param ON String
   * @param OMN String
   * @param PO Object
   * @param DO Object
   * @param CO Object
   * @param AO Object
   * @return Object
   * @throws Exception
   * @todo Implement this com.efounder.service.eai.EAIService method
   */
  public Object startEAIService(StubObject context, String ON, String OMN,
                                Object PO, Object DO, Object CO, Object AO) throws
      Exception {
    return null;
  }
  /**
   *
   * @param context StubObject
   * @param ON String
   * @param OMN String
   * @param PO Object
   * @param DO Object
   * @param CO Object
   * @param AO Object
   * @return Object
   * @throws Exception
   */
  public Object errorEAIService(StubObject context, String ON, String OMN,
                                Object PO, Object DO, Object CO, Object AO) throws
      Exception {
    return null;
  }
}
