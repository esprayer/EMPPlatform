package com.core.core;

import java.io.PrintStream;

public final class TopThreadGroup extends ThreadGroup
{
  static ThreadGroup topThreadGroup = null;
  static ClassLoader classLoader = null;
  private String[] _$3588;
  private boolean _$4644;

  public TopThreadGroup(String name, ClassLoader classLoader)
  {
    super(name);
    classLoader = classLoader; }

  public static ThreadGroup getDefault() {
    ThreadGroup tg = topThreadGroup;
    return tg;
  }

  public TopThreadGroup(ThreadGroup parent, String name) {
    super(parent, name);
  }

  public void uncaughtException(Thread t, Throwable e) {
    if ((!(e instanceof ThreadDeath)) && (((!(e instanceof IllegalMonitorStateException)) || (!("AWT-Selection".equals(t.getName()))))))
    {
      if (e instanceof VirtualMachineError)
      {
        e.printStackTrace();
      }
      System.err.flush();
    }
    else
    {
      super.uncaughtException(t, e);
    }
  }

  public static Thread startThread(Runnable target, String name)
  {
    return startThread(target, name, false);
  }

  public static synchronized Thread startThread(Runnable target, String name, boolean isdaemon)
  {
    Thread t = new Thread(topThreadGroup, target, name);
    t.setContextClassLoader(classLoader);
    t.setDaemon(isdaemon);
    t.start();
    return t; }

  public static synchronized void setContextClassLoader(ClassLoader cl) {
    classLoader = cl; }

  static {
    topThreadGroup = new TopThreadGroup("TopThreadGroup", null);
  }
}