package com.efounder.eai.ui.action.fileaction;

import com.efounder.eai.ide.*;
import javax.swing.*;
import com.efounder.eai.*;
import com.efounder.service.security.ServiceSecurityManager;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class ExitExplorerAction extends ExplorerAction {
static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.action.fileaction.Res");
    public static ExitExplorerAction exitFileAction = new ExitExplorerAction(res.getString("_X_"), 'X', res.getString("KEY15"), null);
    public ExitExplorerAction(String s, char c, String s1, Icon icon) {
        super(s, c, s1, icon);
        this.waitInvoke = false;
    }

    public void actionPerformed(EnterpriseExplorer browser) {
        if (SystemExitAdapter.closeAllWindow()) {
            boolean isExit = SystemExitAdapter.execute(EAI.EA);
            if (isExit) {
                EAI.putEnv("CLOSEALL", "1");
                System.exit(0);
            }
        }
    }
}
