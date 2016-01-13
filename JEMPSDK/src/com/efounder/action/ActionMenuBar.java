package com.efounder.action;

import java.util.*;
import javax.swing.*;
import com.jidesoft.action.CommandMenuBar;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ActionMenuBar extends CommandMenuBar { //JMenuBar {

  protected Object source;
  protected HashMap menuHash;
  protected ArrayList groups;

  public void removeGroup(ActionGroup actiongroup)
  {
      groups.remove(actiongroup);
      ActionMenu actionmenu = (ActionMenu)menuHash.get(actiongroup);
      if(actionmenu != null)
          remove(actionmenu);
      menuHash.remove(actiongroup);
  }

  public void addGroup(int i, ActionGroup actiongroup)
  {
      if(groups.contains(actiongroup))
          return;
      ActionMenu actionmenu = new ActionMenu(source, actiongroup);
      groups.add(i, actiongroup);
      menuHash.put(actiongroup, actionmenu);
      removeAll();
      for(int j = 0; j < i; j++)
          add((ActionMenu)menuHash.get(groups.get(j)));

      add(actionmenu);
      for(int k = i + 1; k < groups.size(); k++)
          add((ActionMenu)menuHash.get(groups.get(k)));

  }

  public void addGroup(ActionGroup actiongroup)
  {
      if(groups.contains(actiongroup))
      {
          return;
      } else
      {
          ActionMenu actionmenu = new ActionMenu(source, actiongroup);
          groups.add(actiongroup);
          menuHash.put(actiongroup, actionmenu);
          add(actionmenu);
          return;
      }
  }

  public ActionGroup[] getGroups()
  {
      ActionGroup aactiongroup[] = new ActionGroup[groups.size()];
      aactiongroup = (ActionGroup[])groups.toArray(aactiongroup);
      return aactiongroup;
  }

  public ActionGroup getGroup(int i)
  {
      return (ActionGroup)groups.get(i);
  }

  public int getGroupCount()
  {
      return groups.size();
  }

  public Object getSource()
  {
      return source;
  }

  public ActionMenuBar(Object obj,String id)
  {
    super(id);
      groups = new ArrayList();
      menuHash = new HashMap();
      source = obj;

  }

}
