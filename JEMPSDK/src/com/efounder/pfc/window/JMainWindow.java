package com.efounder.pfc.window;

import java.util.*;

import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.*;
import javax.swing.*;

import com.efounder.pfc.application.*;
import com.core.xml.*;
import jbof.gui.window.classes.JBOFChildWindow;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

//public class JMainWindow extends jfoundation.gui.window.classes.JMainWindow implements java.awt.event.WindowListener {
public class JMainWindow extends jbof.gui.window.classes.JBOFMainWindow implements java.awt.event.WindowListener {
	protected Vector     WindowCustomEventList = null;
	protected JMenuBar   MainMenu  = null;
	protected JPopupMenu PopupMenu = null;
	
//  protected Vector     ToolBarList = null;
	protected JApplication Application = null;
	public JMainWindow() {
		this.addWindowListener(this);
	}
	public void addWindowCustomEvent(IWindowCustom wc) {
		if ( WindowCustomEventList == null )
			WindowCustomEventList = new Vector();
		WindowCustomEventList.add(wc);
	}
	public void removeWindowCustomEvent(IWindowCustom wc) {
		if ( WindowCustomEventList != null ) {
			WindowCustomEventList.remove(wc);
		}
	}
	protected void CallWindowCustomEvent(String EventName,Object o) {
		if ( WindowCustomEventList == null ) return;
		IWindowCustom iwc = null;
		for(int i=0;i<WindowCustomEventList.size();i++) {
			iwc = (IWindowCustom)WindowCustomEventList.get(i);
			JBOFClass.CallObjectMethod(iwc,EventName,this,o);
		}
	}
	public void setApplication(JApplication App) {
		Application = App;
	}
	public JApplication getApplication() {
		return Application;
	}
	public JChildWindow OpenChildWindow(JChildWindow ChildWindow) {
		return ChildWindow;
	}
	public boolean CloseChildWindow(JChildWindow ChildWindow) {
		return true;
	}
	public void InitMainWindow(Object enterprise,Object args,Object o3,Object o4) {

	}
	public void LoadResource() {
	  
	}
	public void ReleaseResource() {

	}
	public void LoadChildResource(JChildWindow ChildWindow) {

	}
	public void ReleaseChildResource(JChildWindow ChildWindow) {

	}
	public void SwitchChildWindow(JChildWindow OldWindow,JChildWindow NewWindow) {

	}
	public JBOFChildWindow GetChildWindow() {
		return null;
	}
	public JBOFChildWindow GetChildWindow(int Index) {
		return null;
	}
	public int GetChildWindowCount() {
		return 0;
	}
	public JComponent getStatus() {
		return null;
	}
	public JComponent getToolbar(int index) {
		return null;
	}
	public JComponent getMainPanel() {
		return null;
	}
	
	/**
  	 * Invoked the first time a window is made visible.
  	 */
	public void windowOpened(WindowEvent e) {

	}

	/**
	 * Invoked when the user attempts to close the window
	 * from the window's system menu.  If the program does not
	 * explicitly hide or dispose the window while processing
	 * this event, the window close operation will be cancelled.
	 */
	public void windowClosing(WindowEvent e) {

	}

	/**
	 * Invoked when a window has been closed as the result
	 * of calling dispose on the window.
	 */
	public void windowClosed(WindowEvent e) {

	}

	/**
	 * Invoked when a window is changed from a normal to a
	 * minimized state. For many platforms, a minimized window
	 * is displayed as the icon specified in the window's
	 * iconImage property.
	 * @see java.awt.Frame#setIconImage
	 */
	public void windowIconified(WindowEvent e) {

	}

	/**
	 * Invoked when a window is changed from a minimized
	 * to a normal state.
	 */
	public void windowDeiconified(WindowEvent e) {

	}

	/**
	 * Invoked when the Window is set to be the active Window. Only a Frame or
	 * a Dialog can be the active Window. The native windowing system may
	 * denote the active Window or its children with special decorations, such
	 * as a highlighted title bar. The active Window is always either the
	 * focused Window, or the first Frame or Dialog that is an owner of the
	 * focused Window.
	 */
	public void windowActivated(WindowEvent e) {

	}

	/**
	 * Invoked when a Window is no longer the active Window. Only a Frame or a
	 * Dialog can be the active Window. The native windowing system may denote
	 * the active Window or its children with special decorations, such as a
	 * highlighted title bar. The active Window is always either the focused
	 * Window, or the first Frame or Dialog that is an owner of the focused
	 * Window.
	 */
	public void windowDeactivated(WindowEvent e) {

	}
}
