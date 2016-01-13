package com.efounder.eai.ui.action.editaction;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public interface EditActionInterface {
  public boolean canCopy();
  public boolean canCut();
  public boolean canDelete();
  public boolean canPaste();
  public boolean canUndo();
  public void editCopy();
  public void editCut();
  public void editDelete();
  public void editPaste();
  public void editUndo();
  public void editRedo();
}
