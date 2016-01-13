package com.efounder.node.view;

import com.core.xml.*;
import com.efounder.node.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface FrameTrayFactory {
  // 提供 Context 和 nodeView
  public void createFrameTray(Context context);
  // 检查是否可以进行此类视和创建
  public boolean canCreateFrameTray(Context node,Object p1,Object p2);
  public StubObject getStubObject();
  public void setStubObject(StubObject SO);
  public void   setSecurityKey(String key);
  public String getSecurityKey();
}
