package com.efounder.eai.ui;

import com.efounder.action.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.ui.action.editaction.*;
import com.efounder.eai.ui.action.searchaction.SearchExplorerAction;
import com.efounder.eai.ui.action.searchaction.SearchAgainExplorerAction;
import com.efounder.eai.ui.action.searchaction.ReplaceExplorerAction;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class EditMenu {
  static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
  public static final ActionGroup GROUP_Edit;
  public static final ActionGroup GROUP_EditBar;
  public static final ActionGroup GROUP_EditUndoStack = new ExplorerActionGroup();
  public static final ActionGroup GROUP_EditClipboard = new ExplorerActionGroup();
  public static final ActionGroup GROUP_EditFormat    = new ExplorerActionGroup();
  public static final ActionGroup GROUP_EditSelect    = new ExplorerActionGroup();
  static {
    GROUP_EditBar = new ExplorerActionGroup();
    //
    GROUP_EditBar.add(UndoExplorerAction.undoEditAction);
    GROUP_EditBar.add(RedoExplorerAction.redoEditAction);
    //
    GROUP_EditBar.add(CopyExplorerAction.copyEditAction);
    GROUP_EditBar.add(PasteExplorerAction.pasteEditAction);
    GROUP_EditBar.add(CutExplorerAction.cutEditAction);
    GROUP_EditBar.add(SearchExplorerAction.findSearchAction);
  }
  static {
    GROUP_Edit    = new ExplorerActionGroup(res.getString("KEY46")+"[E]", 'E', true);
    GROUP_EditUndoStack.add(UndoExplorerAction.undoEditAction);
    GROUP_EditUndoStack.add(RedoExplorerAction.redoEditAction);
    GROUP_Edit.add(GROUP_EditUndoStack);
    GROUP_EditClipboard.add(CopyExplorerAction.copyEditAction);
    GROUP_EditClipboard.add(PasteExplorerAction.pasteEditAction);
    GROUP_EditClipboard.add(CutExplorerAction.cutEditAction);
    GROUP_EditClipboard.add(DeleteExplorerAction.deleteEditAction);
    GROUP_Edit.add(GROUP_EditClipboard);
    ActionGroup ag = new ActionGroup();
    ag.add(SearchExplorerAction.findSearchAction);
    ag.add(SearchAgainExplorerAction.findAgainSearchAction);
    ag.add(ReplaceExplorerAction.replaceSearchAction);
    GROUP_Edit.add(ag);
//    GROUP_Edit.add(GROUP_EditFormat);
//    GROUP_Edit.add(GROUP_EditSelect);
  }

  public EditMenu() {
  }

}
