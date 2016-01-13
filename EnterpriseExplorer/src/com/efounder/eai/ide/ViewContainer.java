package com.efounder.eai.ide;

import com.efounder.pfc.window.*;
import com.jidesoft.docking.*;
import com.jidesoft.document.*;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public interface ViewContainer {
  public IDocumentPane getDocumentPane();
  public DockableHolder getDockableHolder();
  public void setViewDevice(IViewDevice vd);
  public void setTopView(IView v);
  public void setExplorerView(IView v);
  public void setStructView(IView v);
  public void setMessageView(IView v);
  public void setContentView(IView v);
  public IView getContentView();
  public void setPropertyView(IView v);
}
