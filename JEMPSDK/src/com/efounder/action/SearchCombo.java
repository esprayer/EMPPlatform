package com.efounder.action;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.awt.*;

// Referenced classes of package com.borland.primetime.actions:
//            ActionCombo, b, ActionWidget, a,
//            ComboDelegateAction, Res, DelegateAction

public class SearchCombo extends ActionCombo
    implements ActionWidget
{

  public SearchCombo(Object obj, ComboDelegateAction combodelegateaction, Dimension dimension)
  {
    super(obj, combodelegateaction, dimension);
  }
  public SearchCombo(Object obj, ComboDelegateAction combodelegateaction)
  {
      this(obj, combodelegateaction, null);
  }
  public Component getActionComponent() {
    return this;
  }
}
