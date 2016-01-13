package com.error;

import org.openide.*;
import java.util.*;
import com.core.xml.*;
import com.efounder.eai.*;
import com.error.ui.HelpErrorDialog;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;
import java.net.*;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class HelpErrorManager extends ErrorManager {
  protected static HelpErrorManager helpErrorManager = null;
  protected static URL notFound = null;
  public HelpErrorManager() {
    helpErrorManager = this;
  }
  public static HelpErrorManager getHelpErrorManager() {
    return helpErrorManager;
  }
  public java.util.List getExceptionHelp(Throwable e) {
    String resname = null;java.util.List helpList = new java.util.ArrayList();
    String ExceptionClassName = null;
    try {
      ExceptionClassName = e.getClass().getName();
      if ( e instanceof com.efounder.eai.data.EAServerException )
        ExceptionClassName = ((com.efounder.eai.data.EAServerException)e).getExceptionClassName();
      resname = this.getClass().getName();
      resname = resname.replace('.','/')+"/"+EAI.getLanguage()+"/"+ExceptionClassName+".html";
      java.util.Enumeration enumer = this.getClass().getClassLoader().getResources(resname);
      if ( !enumer.hasMoreElements() ) {
          helpList.add(getNotFound());
      } else {
        enumAddList(enumer, helpList);
      }
    } catch ( Exception ex ) {}
    if ( helpList.size() == 0 ) helpList.add(getNotFound());
    return helpList;
  }
  public URL getNotFound() {
    if ( notFound == null ) {
      try {
        String resname = this.getClass().getName();
        resname = resname.replace('.', '/') + "/" + EAI.getLanguage() + "/HelpNotFound.html";
        notFound = this.getClass().getClassLoader().getResource(resname);
      } catch ( Exception ex ) {}
    }
    return notFound;
  }
  public java.util.List getExceptionObjectHelp(Throwable e,String ObjName,String MtdName) {
    String resname = null;java.util.Enumeration enumer = null;StubObject SO = null;
    java.util.List helpList = PackageStub.getContentVector("HelpErrorManager");String help;
    java.util.List helpFileList = new ArrayList();
    //<ErrorHelp
    //exception="java.net.ConnectException"
    //clazz="com.pansoft.servermanager.security.ui.LoginDialog"
    //method="Login"
    //help="loginerror.html"/>
    for(int i=0;helpList!=null&&i<helpList.size();i++) {
      SO = (StubObject)helpList.get(i);
      if (  e.getClass().getName().toLowerCase().equals(SO.getString("exception","").toLowerCase()) &&
           (SO.getString("clazz" ,"").toLowerCase().equals(ObjName.toLowerCase()) &&
            SO.getString("method","").toLowerCase().equals(MtdName.toLowerCase()) ) ) {
       help = SO.getString("help",null);
       if ( help != null ) {
         resname = ObjName.replace('.', '/') + "/" + EAI.getLanguage() + "/" + help;
         try {
           enumer = this.getClass().getClassLoader().getResources(resname);
           if ( !enumer.hasMoreElements() ) {
             helpFileList.add(getNotFound());
           } else {
             enumAddList(enumer,helpFileList);
           }
         } catch ( Exception ex ){}
       }
      }
    }
    if ( helpFileList.size() == 0 ) helpFileList.add(getNotFound());
    return helpFileList;
  }
  private void enumAddList(java.util.Enumeration enumer,java.util.List list) {
    while ( enumer.hasMoreElements() ) {
      list.add(enumer.nextElement());
    }
  }
  public String getExceptionName(Throwable e) {
    String ExceptionClassName = e.getClass().getName();
    if ( e instanceof com.efounder.eai.data.EAServerException )
      ExceptionClassName = ((com.efounder.eai.data.EAServerException)e).getExceptionClassName();
    return EAResource.getString(this.getClass(),ExceptionClassName,ExceptionClassName);
  }
  public void log(int severity, String s) {
    /**@todo Implement this org.openide.ErrorManager abstract method*/
  }
  public ErrorManager.Annotation[] findAnnotations(Throwable t) {
    /**@todo Implement this org.openide.ErrorManager abstract method*/
//    throw new java.lang.UnsupportedOperationException("Method findAnnotations() not yet implemented.");
    return null;
  }
  public ErrorManager getInstance(String name) {
    return this;
  }
  public Throwable annotate(Throwable t, int severity, String message, String localizedMessage, Throwable stackTrace, Date date) {
    /**@todo Implement this org.openide.ErrorManager abstract method*/
//    throw new java.lang.UnsupportedOperationException("Method annotate() not yet implemented.");
    return t;
  }
  Throwable getThrowable(Throwable e) {
	    String m = e.getMessage();
	    if ( m != null )
	      return e;
	    else {
	      if ( e.getCause() == null )
	        return e;
	      else
	        return getThrowable(e.getCause());
	    }
	  }
  public void notify(int severity, Throwable t,Object fireObject,String message) {
	  Throwable e=getThrowable(t);
	   String err =e.getLocalizedMessage();
	 if(err!=null){
		 JTextArea jta=new JTextArea();
			jta.setPreferredSize(new Dimension(250,80));
			jta.setLineWrap(true);
			jta.setEditable(false);
			jta.setText( "程序执行发生错误，要看详细的错误信息吗？\r\n"+err);
		 int op=JOptionPane.showOptionDialog(null,jta,"执行发生错误", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,null,null,null);
		 if(op==JOptionPane.NO_OPTION)return;
	 }
    HelpErrorDialog errorDialog = new HelpErrorDialog(severity,t,fireObject,message);
    errorDialog.CenterWindow();
    errorDialog.show();
    /**@todo Implement this org.openide.ErrorManager abstract method*/
  }
  public Throwable attachAnnotations(Throwable parm1, ErrorManager.Annotation[] parm2) {
    /**@todo Implement this org.openide.ErrorManager abstract method*/
//    throw new java.lang.UnsupportedOperationException("Method attachAnnotations() not yet implemented.");
    return parm1;
  }

}
