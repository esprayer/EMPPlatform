package com.efounder.eai.application.classes;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JDesktopPane;
import javax.swing.JTabbedPane;

import com.efounder.pfc.window.JChildWindow;

public class JBOFApplication extends JDesktopPane {
	 JChildWindow childWindow = new JChildWindow();
	 
	 public JBOFApplication() {
		 super();		 
		 this.add(childWindow);
		 childWindow.setMaximizable(true);		
		 childWindow.setIconifiable(true);
		 childWindow.setClosable(true);
		 //隐藏标题栏
		 ((javax.swing.plaf.basic.BasicInternalFrameUI)childWindow.getUI()).setNorthPane(null);
		 //隐藏JInternalFrame的边框
		 childWindow.setBorder(BorderFactory.createEmptyBorder());
		 childWindow.pack();
		 childWindow.setVisible(true);
		 try {
			 childWindow.setMaximum(true);
			 childWindow.setSelected(true);
		 } catch (Exception e1) {
			 e1.printStackTrace();
		 }
	 }
	
	 public void openWindow(String title,Icon icon, String tip,Component comp) {
		 childWindow.openWindow(title, icon, tip, comp);
	 }
	 
	 public JChildWindow getJChildWindow() {
		 return this.childWindow;
	 }
}