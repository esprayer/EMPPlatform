package com.efounder.eai.ui.action.moveaction;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public interface MoveActionInterface {
  public boolean canMoveFirst();
  public boolean canMoveNext();
  public boolean canMovePrior();
  public boolean canMoveLast();
  public void moveFirst();
  public void moveNext();
  public void movePrior();
  public void moveLast();
}
