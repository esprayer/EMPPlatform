package jenterprise.bof.classes.AppExplorerObject;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class JFavMenuItemObjectStub {
  JMenuItemObjectStub MOS;
  public JFavMenuItemObjectStub(JMenuItemObjectStub mos) {
    MOS = mos;
  }
  public JMenuItemObjectStub getMenuItem(){
    return MOS;
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String toString() {
      return MOS.Captions;
  }
}
