package com.efounder.pfc.window;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface IViewDevice {
  public static final String LEFT_BOTTOM = "LEFT_BOTTOM";
  public JComponent getDeviceComponent();
  public void openView(IView comp,int layer);
  public void openView(IView comp,int layer,int Style);
  public void setVisible(IView comp,boolean v);
  public boolean isVisible(IView comp);
  public void openView(IView comp,String layer);
  public void openView(IView comp,String layer,int Style);
  public void setLocation(IView comp,int l);
  public void setLocation(IView comp,double w);
  public Action getAction();
  public void setAction(Action a);
  public void setExtendedState(IView comp,int stat);

  public IView getTopView();
  public IView getContentView();
  public IView getStructView();
  public IView getPropertyView();
  public IView getMessageView();
  public IView getExplorerView();
  public void setViewState(IView comp,int stat);
}
