package com.efounder.dbc.swing.treetable;

import java.util.*;

import javax.swing.event.*;
import javax.swing.tree.*;

import org.jdesktop.swingx.treetable.TreeTableNode;

import com.efounder.comp.treetable.*;
import com.efounder.dbc.swing.context.*;
import com.efounder.dbc.swing.tree.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class BaseTreeTableModel extends DefaultTreeModel implements TreeTableModel {
  protected String colIndex[];//列，对应的列名
  protected String colCaption[];
  private Map ColumnClass=new HashMap();
  protected EventListenerList listenerList = new EventListenerList();
  String nodeClass = "com.efounder.dbc.swing.treetable.TreeTableNode";
  protected TreeInfoContext context;
  protected Hashtable hash = new Hashtable();
  protected BaseTreeTableModel(TreeInfoContext ct,String[]ccol,String[]cn) {
      super(null, false);
      context=ct;
      setColIndex(ccol);
      setColCaption(cn);
      DataSetTreeNode dstn=TreeUtils.createRootNode(ct.getCaption());
      setRoot(dstn);
      CreateData();
  }
  public TreeInfoContext getTreeInfoContext(){
    return context;
  }
  public String getString(String key){
      return context.getString(key,"");
  }
  public void setString(String key,String value){
      context.setString(key,value);
  }
  protected abstract void CreateData();
  public  void setValueAt(Object aValue, Object node, int column){
  }
  public  Object getValueAt(Object node, int col){
      return "";
  }
 public void setColumnClass(String name,Object obj){
     ColumnClass.put(name,obj);
 }
 public boolean isLeaf(Object o){
     if(o instanceof TreeTableNode){
         TreeTableNode node=(TreeTableNode)o;
         return node.isLeaf();
     }

     return getChildCount(o) == 0;
 }
   public Object getChild(Object parent, int index){
     Object o= super.getChild(parent,index);
     return o;
   }
   public void valueForPathChanged(TreePath path, Object newValue) {}

   // This is not called in the JTree's default mode: use a naive implementation.
   public int getIndexOfChild(Object parent, Object child) {
       for (int i = 0; i < getChildCount(parent); i++) {
           if (getChild(parent, i).equals(child)) {
               return i;
           }
       }
       return -1;
   }

   public void addTreeModelListener(TreeModelListener l) {
       listenerList.add(TreeModelListener.class, l);
   }

   public void removeTreeModelListener(TreeModelListener l) {
       listenerList.remove(TreeModelListener.class, l);
   }

   /*
    * Notify all listeners that have registered interest for
    * notification on this event type.  The event instance
    * is lazily created using the parameters passed into
    * the fire method.
    * @see EventListenerList
    */
   protected void fireTreeNodesChanged(Object source, Object[] path,
                                       int[] childIndices,
                                       Object[] children) {
       // Guaranteed to return a non-null array
       Object[] listeners = listenerList.getListenerList();
       TreeModelEvent e = null;
       // Process the listeners last to first, notifying
       // those that are interested in this event
       for (int i = listeners.length-2; i>=0; i-=2) {
           if (listeners[i]==TreeModelListener.class) {
               // Lazily create the event:
               if (e == null)
                   e = new TreeModelEvent(source, path,
                                          childIndices, children);
               ((TreeModelListener)listeners[i+1]).treeNodesChanged(e);
           }
       }
   }

   /*
    * Notify all listeners that have registered interest for
    * notification on this event type.  The event instance
    * is lazily created using the parameters passed into
    * the fire method.
    * @see EventListenerList
    */
   protected void fireTreeNodesInserted(Object source, Object[] path,
                                       int[] childIndices,
                                       Object[] children) {
       // Guaranteed to return a non-null array
       Object[] listeners = listenerList.getListenerList();
       TreeModelEvent e = null;
       // Process the listeners last to first, notifying
       // those that are interested in this event
       for (int i = listeners.length-2; i>=0; i-=2) {
           if (listeners[i]==TreeModelListener.class) {
               // Lazily create the event:
               if (e == null)
                   e = new TreeModelEvent(source, path,
                                          childIndices, children);
               ((TreeModelListener)listeners[i+1]).treeNodesInserted(e);
           }
       }
   }

   /*
    * Notify all listeners that have registered interest for
    * notification on this event type.  The event instance
    * is lazily created using the parameters passed into
    * the fire method.
    * @see EventListenerList
    */
   protected void fireTreeNodesRemoved(Object source, Object[] path,
                                       int[] childIndices,
                                       Object[] children) {
       // Guaranteed to return a non-null array
       Object[] listeners = listenerList.getListenerList();
       TreeModelEvent e = null;
       // Process the listeners last to first, notifying
       // those that are interested in this event
       for (int i = listeners.length-2; i>=0; i-=2) {
           if (listeners[i]==TreeModelListener.class) {
               // Lazily create the event:
               if (e == null)
                   e = new TreeModelEvent(source, path,
                                          childIndices, children);
               ((TreeModelListener)listeners[i+1]).treeNodesRemoved(e);
           }
       }
   }

   /*
    * Notify all listeners that have registered interest for
    * notification on this event type.  The event instance
    * is lazily created using the parameters passed into
    * the fire method.
    * @see EventListenerList
    */
   protected void fireTreeStructureChanged(Object source, Object[] path,
                                       int[] childIndices,
                                       Object[] children) {
       // Guaranteed to return a non-null array
       Object[] listeners = listenerList.getListenerList();
       TreeModelEvent e = null;
       // Process the listeners last to first, notifying
       // those that are interested in this event
       for (int i = listeners.length-2; i>=0; i-=2) {
           if (listeners[i]==TreeModelListener.class) {
               // Lazily create the event:
               if (e == null)
                   e = new TreeModelEvent(source, path,
                                          childIndices, children);
               ((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
           }
       }
   }

   //
   // Default impelmentations for methods in the TreeTableModel interface.
   //

   public Class getColumnClass(int column) {
     if(column==0)return TreeTableModel.class;
     Object obj=ColumnClass.get(colIndex[column]);
     if(obj!=null)
         return (Class)obj;
     return Object.class;
   }
   public boolean isCellEditable(Object o, int col) {
       return false;
   }
  public int getColumnCount() {
    return colIndex.length;
  }

  public String getColumnName(int col) {
   return colCaption[col];
  }
  public void setColIndex(String[] ColIndex) {
    this.colIndex = ColIndex;
  }

  public String[] getColIndex() {
    return colIndex;
  }
  public void setColCaption(String[] cap) {
    this.colCaption = cap;
  }

  public String[] getColCaption() {
    return colCaption;
  }
  public String getNodeClass() {
    return nodeClass;
  }


}
