package com.adapter.web.listener;

import java.io.PrintStream;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.logging.impl.LogFactoryImpl;

public class StdoutListener implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent event) {
    }
    private void log(Object info) {
        LogFactoryImpl.getLog(getClass()).error(info);
        LogFactoryImpl.getLog(getClass()).warn(info);
        LogFactoryImpl.getLog(getClass()).debug(info);
    }
    
    private void logErr(Object info) {
    	LogFactoryImpl.getLog(getClass()).error(info);
        LogFactoryImpl.getLog(getClass()).warn(info);
        LogFactoryImpl.getLog(getClass()).debug(info);
    }
    
    public void contextInitialized(ServletContextEvent event) {
        PrintStream printStream = new PrintStream(System.out) {
            public void println(boolean x) {
                log(Boolean.valueOf(x));	
            }
            public void println(char x) {
                log(Character.valueOf(x));
            }
            public void println(char[] x) {
                log(x == null ? null : new String(x));
            }
            public void println(double x) {
                log(Double.valueOf(x));
            }
            public void println(float x) {
                log(Float.valueOf(x));
            }
            public void println(int x) {
                log(Integer.valueOf(x));
            }
            public void println(long x) {
                log(x);
            }
            public void println(Object x) {
                log(x);
            }
            public void println(String x) {
                log(x);
            }
        };
        
        PrintStream printStackTrace = new PrintStream(System.err) {
        	public void println(boolean x) {
                log(Boolean.valueOf(x));	
            }
            public void println(char x) {
                log(Character.valueOf(x));
            }
            public void println(char[] x) {
                log(x == null ? null : new String(x));
            }
            public void println(double x) {
                log(Double.valueOf(x));
            }
            public void println(float x) {
                log(Float.valueOf(x));
            }
            public void println(int x) {
                log(Integer.valueOf(x));
            }
            public void println(long x) {
                log(x);
            }
            public void println(Object x) {
                log(x);
            }
            public void println(String x) {
                log(x);
            }
        };
        
        System.setOut(printStream);
        System.setErr(printStackTrace);
    }
}