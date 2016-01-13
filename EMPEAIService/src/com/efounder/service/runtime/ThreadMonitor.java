package com.efounder.service.runtime;
/*
 * @(#)ThreadMonitor.java	1.6 05/12/22
 *
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

/*
 * @(#)ThreadMonitor.java	1.6 05/12/22
 */

import java.lang.management.ManagementFactory;
import java.lang.management.ManagementFactory.*;
import java.lang.management.ThreadMXBean;
import java.lang.management.ThreadInfo;
//import java.lang.management.LockInfo;
//import java.lang.management.MonitorInfo;
import javax.management.*;
import java.io.*;
import java.util.*;

/**
 * Example of using the java.lang.management API to dump stack trace
 * and to perform deadlock detection.
 *
 * @author  Mandy Chung
 * @version %% 12/22/05
 */
public class ThreadMonitor {
    private MBeanServerConnection server;
    private ThreadMXBean tmbean;
    private ObjectName objname;

    // default - JDK 6+ VM
    private String findDeadlocksMethodName = "findDeadlockedThreads";
    private boolean canDumpLocks = true;

    /**
     * Constructs a ThreadMonitor object to get thread information
     * in a remote JVM.
     */
    public ThreadMonitor(MBeanServerConnection server) throws IOException {
       this.server = server;
       this.tmbean = (ThreadMXBean)ManagementFactory.newPlatformMXBeanProxy(server,
                                            ManagementFactory.THREAD_MXBEAN_NAME,
                                            ThreadMXBean.class);
       try {
           objname = new ObjectName(ManagementFactory.THREAD_MXBEAN_NAME);
        } catch (MalformedObjectNameException e) {
            // should not reach here
            InternalError ie = new InternalError(e.getMessage());
            ie.initCause(e);
            throw ie;
       }
       parseMBeanInfo();
    }

    /**
     * Constructs a ThreadMonitor object to get thread information
     * in the local JVM.
     */
    public ThreadMonitor() {
        this.tmbean = ManagementFactory.getThreadMXBean();
    }

    /**
     * Prints the thread dump information to System.out.
     */
    public void threadDump() {
//        if (canDumpLocks) {
//            if (tmbean.isObjectMonitorUsageSupported() &&
//                tmbean.isSynchronizerUsageSupported()) {
//                // Print lock info if both object monitor usage
//                // and synchronizer usage are supported.
//                // This sample code can be modified to handle if
//                // either monitor usage or synchronizer usage is supported.
//                dumpThreadInfoWithLocks();
//            }
//        } else {
//            dumpThreadInfo();
//        }
    }

    private void dumpThreadInfo() {
       System.out.println("Full Java thread dump");
       long[] tids = tmbean.getAllThreadIds();
       ThreadInfo[] tinfos = tmbean.getThreadInfo(tids, Integer.MAX_VALUE);
       for (int i=0;i<tinfos.length;i++ ) {
           printThreadInfo(tinfos[i]);
       }
    }

    /**
     * Prints the thread dump information with locks info to System.out.
     */
    private void dumpThreadInfoWithLocks() {
       System.out.println("Full Java thread dump with locks info");

//       ThreadInfo[] tinfos = tmbean.dumpAllThreads(true, true);
//       for (int i=0;i<tinfos.length;i++ ) {
//           printThreadInfo(tinfos[i]);
//           LockInfo[] syncs = tinfos[i].getLockedSynchronizers();
//           printLockInfo(syncs);
//       }
//       System.out.println();
    }

    private static String INDENT = "    ";

    private void printThreadInfo(ThreadInfo ti) {
       // print thread information
       printThread(ti);

//       // print stack trace with locks
//       StackTraceElement[] stacktrace = ti.getStackTrace();
//       MonitorInfo[] monitors = ti.getLockedMonitors();
//       for (int i = 0; i < stacktrace.length; i++) {
//           StackTraceElement ste = stacktrace[i];
//           System.out.println(INDENT + "at " + ste.toString());
//           for (int k=0;i<monitors.length;k++) {
//               if (monitors[k].getLockedStackDepth() == i) {
//                   System.out.println(INDENT + "  - locked " + monitors[k]);
//               }
//           }
//       }
       System.out.println();
    }

    private void printThread(ThreadInfo ti) {
       StringBuilder sb = new StringBuilder("\"" + ti.getThreadName() + "\"" +
                                            " Id=" + ti.getThreadId() +
                                            " in " + ti.getThreadState());
       if (ti.getLockName() != null) {
           sb.append(" on lock=" + ti.getLockName());
       }
       if (ti.isSuspended()) {
           sb.append(" (suspended)");
       }
       if (ti.isInNative()) {
           sb.append(" (running in native)");
       }
       System.out.println(sb.toString());
       if (ti.getLockOwnerName() != null) {
            System.out.println(INDENT + " owned by " + ti.getLockOwnerName() +
                               " Id=" + ti.getLockOwnerId());
       }
    }

//    private void printMonitorInfo(ThreadInfo ti, MonitorInfo[] monitors) {
//       System.out.println(INDENT + "Locked monitors: count = " + monitors.length);
//       for (int i=0;i<monitors.length;i++) {
//           System.out.println(INDENT + "  - " + monitors[i] + " locked at ");
//           System.out.println(INDENT + "      " + monitors[i].getLockedStackDepth() +
//                              " " + monitors[i].getLockedStackFrame());
//       }
//    }

//    private void printLockInfo(LockInfo[] locks) {
//       System.out.println(INDENT + "Locked synchronizers: count = " + locks.length);
//       for (int i=0;i<locks.length;i++) {
//           System.out.println(INDENT + "  - " + locks[i]);
//       }
//       System.out.println();
//    }

    /**
     * Checks if any threads are deadlocked. If any, print
     * the thread dump information.
     */
    public boolean findDeadlock() {
//       long[] tids;
//       if (findDeadlocksMethodName.equals("findDeadlockedThreads") &&
//               tmbean.isSynchronizerUsageSupported()) {
//           tids = tmbean.findDeadlockedThreads();
//           if (tids == null) {
//               return false;
//           }
//
//           System.out.println("Deadlock found :-");
//           ThreadInfo[] infos = tmbean.getThreadInfo(tids, true, true);
//           for (int i=0;i<infos.length;i++) {
//               printThreadInfo(infos[i]);
//               printLockInfo(infos[i].getLockedSynchronizers());
//               System.out.println();
//           }
//       } else {
//           tids = tmbean.findMonitorDeadlockedThreads();
//           if (tids == null) {
//               return false;
//           }
//           ThreadInfo[] infos = tmbean.getThreadInfo(tids, Integer.MAX_VALUE);
//           for (int i=0;i<infos.length;i++) {
//               // print thread information
//               printThreadInfo(infos[i]);
//           }
//       }

       return true;
    }


    private void parseMBeanInfo() throws IOException {
        try {
            MBeanOperationInfo[] mopis = server.getMBeanInfo(objname).getOperations();

            // look for findDeadlockedThreads operations;
            boolean found = false;
            for (int i=0;i<mopis.length;i++) {
                if (mopis[i].getName().equals(findDeadlocksMethodName)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                // if findDeadlockedThreads operation doesn't exist,
                // the target VM is running on JDK 5 and details about
                // synchronizers and locks cannot be dumped.
                findDeadlocksMethodName = "findMonitorDeadlockedThreads";
                canDumpLocks = false;
            }
        } catch (IntrospectionException e) {
            InternalError ie = new InternalError(e.getMessage());
            ie.initCause(e);
            throw ie;
        } catch (InstanceNotFoundException e) {
            InternalError ie = new InternalError(e.getMessage());
            ie.initCause(e);
            throw ie;
        } catch (ReflectionException e) {
            InternalError ie = new InternalError(e.getMessage());
            ie.initCause(e);
            throw ie;
        }
    }
}
