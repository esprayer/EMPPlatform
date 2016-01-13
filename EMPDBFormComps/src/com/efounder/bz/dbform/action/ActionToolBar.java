package com.efounder.bz.dbform.action;

import com.efounder.action.*;
import com.efounder.bz.dbform.component.*;
import javax.swing.JComponent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ActionToolBar extends ActionToolBarPane implements FormComponent,PropertyChangeListener
,HierarchyListener,Runnable{
  /**
   *
   */
  public ActionToolBar() {
    super(null);
    this.addPropertyChangeListener(this);
    this.addHierarchyListener(this);
  }
  /**
   *
   * @return Object
   */
  public Object getSource() {
    return this.getParent();
  }
  /**
   *
   * @return FormContainer
   */
  public FormContainer getFormContainer() {
    return null;
  }
  /**
   *
   * @return JComponent
   */
  public JComponent getJComponent() {
    return this;
  }
  public void run() {
    setActionList();
  }
  /**
   *
   * @param key Object
   * @param oldValue Object
   * @param newValue Object
   */
  java.util.List contextActionList =null;

  /**
   *
   * @return List
   */
  public java.util.List getContextActionList() {
    return this.contextActionList;
  }

  /**
   *
   * @param key Object
   * @param oldValue Object
   * @param newValue Object
   */
  protected void contextActionListChanged(Object key, Object oldValue,
                                          Object newValue) {
    if ( !"contextActionList".equals(key) ) return;
    if ( oldValue != null )
      this.removeAllGroup();
    contextActionList = (java.util.List)newValue;
    //  SwingUtilities.invokeLater(this);
    setActionList();
  }

  /**
   *
   */
  protected void setActionList(){
      if ( contextActionList == null || contextActionList.size() == 0 ) return;
    ActionAdapter actionProvider = null;
    Object[] nodeArray = {this};
    for(int i=0;i<contextActionList.size();i++) {
      actionProvider = (ActionAdapter)contextActionList.get(i);
      if ( actionProvider != null ) {
        ActionGroup actionGroup = (ActionGroup)actionProvider.getAction(this,nodeArray);
        if ( actionGroup != null ) {
          for(int j=0;j<actionGroup.getActionCount();j++) {
            this.addGroup((ActionGroup)actionGroup.getAction(j));
          }
        }
      }
    }
  }

  /**
   *
   * @param evt PropertyChangeEvent
   */
  public void propertyChange(PropertyChangeEvent evt) {
    String key = evt.getPropertyName();
    Object oldValue = evt.getOldValue();
    Object newValue = evt.getNewValue();
    contextActionListChanged(key,oldValue,newValue);
  }

  /**
   *
   * @param e HierarchyEvent
   */
  public void hierarchyChanged(HierarchyEvent e) {
//    if(e.getID()==HierarchyEvent.HIERARCHY_CHANGED&&e.getChangeFlags()==HierarchyEvent.PARENT_CHANGED)
//        if(source==null){
//            source = e.getChangedParent();
//            setActionList();
//        }
  }
}
