package com.efounder.node.view;

import java.beans.*;
import javax.swing.*;
import com.efounder.pfc.window.*;
import com.efounder.node.*;
import com.efounder.model.biz.BIZContext;
import com.efounder.action.ActionStub;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface NodeViewer extends BIZContext {
  // Fields
  public static final String PROP_STRUCTURE_COMPONENT = "StructureComponent";
  public static final String PROP_VIEWER_COMPONENT = "ViewerComponent";
  public static final String PROP_ICON = "Icon";
  public static final String PROP_DESCRIPTION = "Description";
  public static final String PROP_TITLE = "Title";
  public static final NodeViewer[] EMPTY_ARRAY = null;;

  // Methods
  public void setNodeViewer(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception;
  public void prepareViewer(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception;
  public void initNodeViewer(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception;
  public void initNodeViewer2(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception;
  public void reInitNodeViewer(Context context,Object p1,Object p2,IWindow nodeWindow) throws Exception;
  public void releaseViewer();
  public void browserDeactivated();
  public void browserActivated();
  public void viewerDeactivated();
  public void viewerDeactivating();
  public void viewerActivated(boolean boolean0);
  public void viewerNodeChanged();
  public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener);
  public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener);
  public IWindow[] getStructureComponent();
  public IWindow[] getPropertyComponent();
  public JComponent getViewerComponent();
  public Icon getViewerIcon();
  public String getViewerDescription();
  public String getViewerTitle();
  public void setViewerIcon(Icon icon);
  public void setViewerDescription(String v);
  public void setViewerTitle(String v);
  public Action getTopFrameAction();
  public Action getContextAction();
  public Action getFrameAction();
  public Action getPopupAction();
  public Action getEntityAction();
  public ActionStub[] getFloatAction();
  public Map getFloatBarMap();
  public Object getNodeDataObject(NodeDataStub nodeDataStub);
  public String getNodeViewID();
  public void setNodeViewID(String id);
  public void setViewFactory(NodeViewerFactory nvf);
  public NodeViewerFactory getViewFactory();
  public void setNodeWindow(NodeWindow ncw);
  public NodeWindow getNodeWindow();

  public void setNodeExpWindow(NodeExplorerWindow ncw);
  public NodeExplorerWindow getNodeExpWindow();
  public JNodeStub getNodeStub();

  public NodeViewer getExpNodeViewer();
  public void setExpNodeViewer(NodeViewer nv);

  public boolean isAutoTitle();

  public String getBIZContextUnit();
  public String getBIZContextType();
  public String getBIZContextDateType();
  public String getBIZContextSDate();
  public String getBIZContextEDate();
  public Object getBIZContextValue(Object object, Object object1);
  public void setBIZContextValue(Object object, Object object1);
  public void bizContextCallBack(Object object, Object object1);
  public void bizContextEnumKey(java.util.List list);
  public void initCompBIZContext();
  public void setModified(boolean v);
  public boolean isModified();
  public void nodeViewClose(NodeWindow nw);
  /**
   *
   * @param attribView Object
   * @param def boolean
   * @return boolean
   */
  public boolean isAttribViewVisible(Object attribView,boolean def);
  /**
   *
   * @param attribView Object
   * @param value boolean
   */
  public void setAttribViewVisible(Object attribView,boolean value);
}
