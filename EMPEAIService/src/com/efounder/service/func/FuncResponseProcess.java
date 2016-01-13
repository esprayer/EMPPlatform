package com.efounder.service.func;

import com.efounder.model.biz.*;

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
public interface FuncResponseProcess {
  /**
   *
   * @param bizFunction BIZExecuteFunction
   * @param prepareObject Object
   * @param responseObject Object
   * @return Object
   * @throws Exception
   */
  public Object processResponse(BIZExecuteFunction bizFunctionExecute,BIZFunction bizFunction,
                                Object prepareObject,Object responseObject) throws Exception;
}
