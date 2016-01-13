package com.efounder.sql;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbcp.AbandonedConfig;

/** @deprecated */
public class AbandonedTrace
{
  private static SimpleDateFormat format = new SimpleDateFormat("'DBCP object created' yyyy-MM-dd HH:mm:ss 'by the following code was never closed:'");

  private AbandonedConfig config = null;
  private AbandonedTrace parent;
  private Exception createdBy;
  private long createdTime;
  private List trace = new ArrayList();

  private long lastUsed = 0L;

  public AbandonedTrace()
  {
    init(this.parent);
  }

  public AbandonedTrace(AbandonedConfig config)
  {
    this.config = config;
    init(this.parent);
  }

  public AbandonedTrace(AbandonedTrace parent)
  {
    this.config = parent.getConfig();
    init(parent);
  }

  private void init(AbandonedTrace parent)
  {
    if (parent != null) {
      parent.addTrace(this);
    }

    if (this.config == null) {
      return;
    }
    if (this.config.getLogAbandoned()) {
      this.createdBy = new Exception();
      this.createdTime = System.currentTimeMillis();
    }
  }

  protected AbandonedConfig getConfig()
  {
    return this.config;
  }

  protected long getLastUsed()
  {
    if (this.parent != null) {
      return this.parent.getLastUsed();
    }
    return this.lastUsed;
  }

  protected void setLastUsed()
  {
    if (this.parent != null)
      this.parent.setLastUsed();
    else
      this.lastUsed = System.currentTimeMillis();
  }

  protected void setLastUsed(long time)
  {
    if (this.parent != null)
      this.parent.setLastUsed(time);
    else
      this.lastUsed = time;
  }

  protected void setStackTrace()
  {
    if (this.config == null) {
      return;
    }
    if (this.config.getLogAbandoned()) {
      this.createdBy = new Exception();
      this.createdTime = System.currentTimeMillis();
    }
    if (this.parent != null)
      this.parent.addTrace(this);
  }

  protected void addTrace(AbandonedTrace trace)
  {
    synchronized (this) {
      this.trace.add(trace);
    }
    setLastUsed();
  }

  protected synchronized void clearTrace()
  {
    if (this.trace != null)
      this.trace.clear();
  }

  protected List getTrace()
  {
    return this.trace;
  }

  public void printStackTrace()
  {
    if (this.createdBy != null) {
      System.err.println(format.format(new Date(this.createdTime)));
      this.createdBy.printStackTrace();
    }
    synchronized (this) {
      Iterator it = this.trace.iterator();
      while (it.hasNext()) {
        AbandonedTrace at = (AbandonedTrace)it.next();
        at.printStackTrace();
      }
    }
  }

  protected synchronized void removeTrace(AbandonedTrace trace)
  {
    if (this.trace != null)
      this.trace.remove(trace);
  }
}