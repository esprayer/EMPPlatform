package com.efounder.service.func;

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
public interface BIZExecuteFunction {
  public Object prepare() throws Exception;
  public Object execute(Object prepareObject) throws Exception;
  public Object finish() throws Exception;
  public boolean isThread();
}
