package jenterprise.bof.classes.AppExplorerObject.MessageService;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public interface IMessageServiceInterface {
  public void ReceviceMessage(String HostAddress,String Message);
  public void RemoveSession(JMessageSessionStub MSS);
  public void CreateSession(JMessageSessionStub MSS,boolean isShow);
}