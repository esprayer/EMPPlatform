package com.efounder.eai.ui.action.searchaction;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public interface SearchActionInterface {
  public boolean canSearch();
  public boolean canReplace();
  public void searchAgainNode();
  public void searchNode();
  public void replaceNode();
}
