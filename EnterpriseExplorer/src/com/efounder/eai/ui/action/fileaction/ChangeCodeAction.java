package com.efounder.eai.ui.action.fileaction;

import javax.swing.*;

import com.efounder.eai.ide.*;
import com.efounder.eai.ui.*;
import com.efounder.eai.EAI;
import com.efounder.eai.data.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */
public class ChangeCodeAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
  public static ChangeCodeAction chagePasswordAction = new ChangeCodeAction(res.getString("KEY13"),'0',res.getString("KEY14"),null);
  /**
   *
   */
  public ChangeCodeAction(String s, char c, String s1, Icon icon)
  {
      super(s, c, s1, icon);
  }
  public void doUpdate(EnterpriseExplorer explorer)
  {
    try{
      JParamObject paramObject = JParamObject.Create();
      String Product = paramObject.GetValueByEnvName("Product");
      boolean enable = false;
      Object val = EAI.DOF.IOM(Product, "changeCode",String.valueOf(true));
      if (val!=null)
        enable=((Boolean)val).booleanValue();
      setEnabled(enable);
    }catch(Exception e){

    }
  }
  /**
   * actionPerformed
   *
   * @param browser EnterpriseExplorer
   * @todo Implement this com.efounder.eai.ide.ExplorerAction method
   */
  public void actionPerformed(EnterpriseExplorer browser) {
    try {
      JParamObject paramObject = JParamObject.Create();
      String Product = paramObject.GetValueByEnvName("Product");
      EAI.DOF.IOM(Product, "changeCode");
    } catch ( Exception e ) {
      e.printStackTrace();
    }
  }
}
