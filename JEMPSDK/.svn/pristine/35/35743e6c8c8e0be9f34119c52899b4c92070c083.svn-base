package org.openide;

import org.openide.util.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public abstract class WaitingManager {

  /** We keep a reference to our proxy ErrorManager here. */
  private static DelegatingWaitingManager current;

  /** Getter for the default version of error manager.
   * @return the error manager installed in the system
   * @since 2.1
   */
  public static WaitingManager getDefault () {
      synchronized (WaitingManager.class) {
          if (current != null) {
              return current;
          }
      }
      return getDefaultDelegate();
  }

  private static DelegatingWaitingManager getDefaultDelegate() {
      DelegatingWaitingManager c = new DelegatingWaitingManager(""); // NOI18N
      try {
          c.initialize();
          synchronized (ErrorManager.class) {
              if (current == null) {
                  current = c;
                  // r is not null after c.initialize();
                  current.r.addLookupListener(current);
              }
          }
      } catch (RuntimeException e) {
          // #20467
          e.printStackTrace();
          current = c;
      } catch (LinkageError e) {
          // #20467
          e.printStackTrace();
          current = c;
      }
      return current;
  }
  /**
   *
   * @param waitComp Component
   */
  public final void showWait(Component waitComp) {
    if ( waitComp != null ) {
      if ( !(waitComp instanceof Window ) ) waitComp = SwingUtilities.getWindowAncestor(waitComp);
      waitComp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }
  }
  /**
   *
   * @param waitComp Component
   */
  public final void endShowWait(Component waitComp) {
    if ( waitComp != null ) {
      if ( !(waitComp instanceof Window ) ) waitComp = SwingUtilities.getWindowAncestor(waitComp);
      waitComp.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
  }
  /**
   *
   * @param name String
   * @return WaitingManager
   */
  public abstract WaitingManager getInstance(String name);
  public abstract void beginWait(Component waitCompObject,String message);
  /**
   *
   * @param waitCompObject Component
   */
  public final void beginWait(Component waitCompObject) {
    beginWait(waitCompObject,null);
  }
  public abstract void endWait(Component waitCompObject);
  /**
   *
   * @param waitObject Component
   * @param message String
   * @param icon Icon
   */
  public final    void beginWaitingFor(Component waitObject,String title,String message,Icon icon) {
    beginWaitingFor(waitObject,title,message,icon);
  }
  public final    void waitInvoke(String title,String message,Icon icon,Runnable run) {
    waitInvoke(null,title,message,icon,run,null);
  }
  public final    void waitInvoke(String title,String message,Icon icon,Runnable run,Component waitComp) {
    waitInvoke(null,title,message,icon,run,waitComp);
  }
  public final void waitInvoke(Component waitObject,String title,String message,Icon icon,Runnable run ){
    waitInvoke(waitObject,title,message,icon,run,null);
  }
  public abstract void waitInvoke(Component waitObject,String title,String message,Icon icon,Runnable run,Component waitComp);
  public abstract void beginWaitingFor(Component waitObject,String title,String message,Icon icon,Runnable run,Component waitComp);
  public abstract void setWaitMessage(String title,String content,Icon contentIcon);
  public abstract void endWaitngFor(Component waitObject);
  public abstract void waitingFor(String title,String content,Icon contentIcon,Object waitObject);
  public abstract void waitingFor(String title,Icon contentIcon,SpinnerModel spmodel,Object waitObject);
  /**
   *
   * @param waitObject Component
   * @param title String
   * @param message String
   * @param icon Icon
   * @param run Runnable
   */
  public void beginWaitingFor(Component waitObject,String title,String message,Icon icon,Runnable run) {
    beginWaitingFor(waitObject,title,message,icon,run,null);
  }
  /**
   *
   * @param title String
   * @param content String
   */
  public void waitingFor(String title,String content) {
    waitingFor(title,content,null,null);
  }
  /**
   *
   * @param title String
   * @param content String
   * @param titleIcon Icon
   */
  public void waitingFor(String title,String content,Icon titleIcon) {
    waitingFor(title,content,titleIcon,null);
  }
  /**
   *
   */
  public WaitingManager() {
  }
  /**
   * 系统默认的等待服务管理
   *
   */
  private static class DelegatingWaitingManager extends WaitingManager implements LookupListener {

      private String name = null;
      private int waitCount = 0;

      /**
       * The set of instances we delegate to. Elements type is ErrorManager.
       */
      private Set delegates = new HashSet();

      /**
       * A set that has to be updated when the list of delegates
       * changes. All instances created by getInstance are held here.
       * It is a set of DelagatingErrorManager.
       */
      private WeakSet createdByMe = new WeakSet();

      /** If we are the "central" delagate this is not null and
       * we listen on the result. On newly created delegates this
       * is null.
       */
      Lookup.Result r;

      public DelegatingWaitingManager(String name) {
          this.name = name;
      }

      /** If the name is not empty creates new instance of
       * DelegatingErrorManager. Adds it to createdByMe.
       */
      public WaitingManager getInstance(String name) {
          if ((name == null) || ("".equals(name))) { // NOI18N
              return this;
          }
          DelegatingWaitingManager dwm = new DelegatingWaitingManager(name);
          synchronized (this) {
              attachNewDelegates(dwm, name);
              createdByMe.add(dwm);
          }
          return dwm;
      }
      public void beginWait(Component waitCompObject,String message) {
        if ( waitCount > 0 ) {waitCount++;return;}waitCount++;
        if ( waitCompObject != null ) {
          waitCompObject.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
        for (Iterator i = delegates.iterator(); i.hasNext(); ) {
          WaitingManager em = (WaitingManager)i.next();
          em.beginWait(waitCompObject,message);
        }
      }
      public void beginWaitingFor(Component waitCompObject,String title,String message,Icon icon,Runnable run,Component waitComp) {
        if ( waitCount > 0 ) {waitCount++;return;}waitCount++;
        if ( waitCompObject != null ) {
          waitCompObject.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
        for (Iterator i = delegates.iterator(); i.hasNext(); ) {
          WaitingManager em = (WaitingManager)i.next();
          em.beginWaitingFor(waitCompObject,title,message,icon,run,waitComp);
        }
      }
      public void setWaitMessage(String title,String content,Icon contentIcon) {
        for (Iterator i = delegates.iterator(); i.hasNext(); ) {
          WaitingManager em = (WaitingManager)i.next();
          em.setWaitMessage(title,content,contentIcon);
        }
      }
      public void endWaitngFor(Component waitCompObject) {
        waitCount--;if ( waitCount > 0 ) return;
        for (Iterator i = delegates.iterator(); i.hasNext(); ) {
          WaitingManager em = (WaitingManager)i.next();
          em.endWaitngFor(waitCompObject);
        }
        if ( waitCompObject != null ) {
          waitCompObject.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
      }
      /**
       *
       * @param title String
       * @param content String
       * @param contentIcon Icon
       * @param waitObject Object
       */
      public void waitingFor(String title,String content,Icon contentIcon,Object waitObject) {
        if ( waitCount > 0 ) {waitCount++;return;}waitCount++;
        for (Iterator i = delegates.iterator(); i.hasNext(); ) {
          WaitingManager em = (WaitingManager)i.next();
          em.waitingFor(title,content,contentIcon,waitObject);
        }
      }
      /**
       *
       * @param title String
       * @param contentIcon Icon
       * @param spmodel SpinnerModel
       * @param waitObject Object
       */
      public void waitingFor(String title,Icon contentIcon,SpinnerModel spmodel,Object waitObject) {
        if ( waitCount > 0 ) {waitCount++;return;}waitCount++;
        for (Iterator i = delegates.iterator(); i.hasNext(); ) {
          WaitingManager em = (WaitingManager)i.next();
          em.waitingFor(title,contentIcon,spmodel,waitObject);
        }
      }
      /**
       *
       * @param waitObject Object
       */
      public void endWait(Component waitCompObject) {
        waitCount--;if ( waitCount > 0 ) return;
        for (Iterator i = delegates.iterator(); i.hasNext(); ) {
          WaitingManager em = (WaitingManager)i.next();
          em.endWait(waitCompObject);
        }
        if ( waitCompObject != null ) {
          waitCompObject.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
      }
      /**
       * Updates the list of delegates. Also updates all instances created
       * by ourselves.
       */
      public synchronized void setDelegates(Collection newDelegates) {
          java.util.LinkedHashSet d = new java.util.LinkedHashSet(newDelegates);
          delegates = d;
          for (Iterator i = createdByMe.iterator(); i.hasNext(); ) {
              DelegatingWaitingManager dwm = (DelegatingWaitingManager)i.next();
              attachNewDelegates(dwm, dwm.getName());
          }
      }

      private String getName() {
          return name;
      }

      /**
       * Takes all our delegates, asks them for an instance identified by
       * name and adds those results as new delegates for dem.
       * @param String name
       * @param DelagatingErrorManager d the instance to which we will attach
       */
      private void attachNewDelegates(DelegatingWaitingManager dwm, String name) {
          Set newDelegatesForDem = new HashSet();
          for (Iterator j = delegates.iterator(); j.hasNext(); ) {
              ErrorManager e = (ErrorManager)j.next();
              newDelegatesForDem.add(e.getInstance(name));
          }
          dwm.setDelegates(newDelegatesForDem);
      }

      /** Blocks on lookup and after the lookup returns updates
       * delegates and adds a listener.
       */
      public void initialize() {
          r = Lookup.getDefault().lookup(
              new Lookup.Template(WaitingManager.class));
          Collection instances = r.allInstances();
          setDelegates(instances);
      }

      /** Updates the delegates.*/
      public void resultChanged (LookupEvent ev) {
          if (r != null) {
              Collection instances = r.allInstances();
              setDelegates(instances);
          }
      }
      /**
       *
       * @param waitObject Component
       * @param title String
       * @param message String
       * @param icon Icon
       * @param run Runnable
       */
      public void waitInvoke(Component waitObject,String title, String message, Icon icon,
                           Runnable run,Component waitComp) {
//        waitCount++;
        if ( waitObject != null ) {
          waitObject.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        }
        try {
          for (Iterator i = delegates.iterator(); i.hasNext(); ) {
            WaitingManager em = (WaitingManager) i.next();
            em.waitInvoke(waitObject, title, message, icon, run,waitComp);
          }
        } finally {
//          waitCount--;
          if (waitObject != null) {
            waitObject.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          }
        }
      }
    }
}
