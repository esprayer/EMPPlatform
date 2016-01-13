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

public interface IWindowStatus {
  public void setText(int index,String text);
  public String getText(int index);
  public void setText(String Text);
  public void setText(String compName,String text);
  public void setToolTipText(String compName,String text);
  public void setIcon(String compName,Icon icon);
  public void setIcon(Icon icon);
  public JComponent getStatusComp();
  public void startProgress(int max,String text);
  public void incProgress(int step,String text);
  public void endProgress(String text);
  public void clearProgress();
  public void beginWait(long time);
  public void endWait();

  public Component registryComponent(Component comp);
  public Component registryComponent(Component comp,Object Id);
  public Component unregistryComponent(Component comp);
  public Container registryContainer(Container comp);
  public Container registryContainer(Container comp,Object Id);
  public Container unregistryContainer(Container comp);
  public Component getContainer(Object ID);
  public Component getComponent(Object ID);
}
