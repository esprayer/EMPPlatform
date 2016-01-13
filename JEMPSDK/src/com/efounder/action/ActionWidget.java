package com.efounder.action;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ActionWidget {
  public abstract Action getAction();
  public abstract Object getSource();
  public abstract Component getActionComponent();

}
