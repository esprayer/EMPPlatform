package com.efounder.eai.framework;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface IActiveObject {
	public IActiveFramework getActiveFramework();
	public void setActiveFramework(IActiveFramework ActiveFramework);
	public Object getActiveObjectStub();
	public void setActiveObjectStub(Object ActiveObjectStub);
	public Object InvokeObjectMethod(String ActiveObjectMethodName,Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception;
	public Object InitObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject) throws Exception;
	public Object DestroyObject(Object Param,Object Data,Object CustomObject,Object AdditiveObject);
}
