package com.efounder.actions;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ContextActionProvider {
  // Methods
  public Action getContextAction(Object frame, Object[] nodeArray);
}
