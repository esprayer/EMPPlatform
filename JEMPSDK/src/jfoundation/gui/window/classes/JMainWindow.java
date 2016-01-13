package jfoundation.gui.window.classes;

import javax.swing.*;

import java.awt.*;
import java.util.Vector;

import jbof.gui.window.classes.JBOFChildWindow;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//     (在此文件中可以设置资源文件)
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JMainWindow extends JFrame {
	public Vector                   SystemMenuList = new Vector();
	protected SystemTray                   sysTray;// 当前操作系统的托盘对象
	protected TrayIcon                    trayIcon;// 当前对象的托盘
	
	public JMainWindow() {
	}
	/**
	 *
	 * @param Index int
	 * @param Text String
	 */
	public void setStatusText(int Index,String Text) {

	}
	/**
	 *
	 * @return int
	 */
	public int GetChildWindowCount() {
		return 0;
	}
	/**
 	 *
 	 * @param value boolean
 	 */
	public void setOffline(boolean value) {

	}
	//----------------------------------------------------------------------------------------------
	//����: ��һ����󴰿�
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public Component OpenObjectWindow(String title,Icon icon, String tip,Component comp) {
		return null;
	}
	//----------------------------------------------------------------------------------------------
	//����: �ر�һ����󴰿�
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public boolean CloseObjectWindow(Component comp) {
		return true;
	}
	public void CloseOtherWindow() {

	}
	public void CloseCurrentWindow() {

	}
	public boolean CloseAllWindow() {
		return false;
	}

	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void BeginWaitCursor() {
	  
	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void EndWaitCursor() {

	}

	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public String getTitleAt(JBOFChildWindow wnd) {
		return null;
	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void setTitleAt(Component comp,String Title) {

	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void setToolTipTextAt(Component comp,String Title) {

	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void setIconAt(Component comp,Icon icon) {

	}
	public void setActiveWindow(JChildWindow cw) {

	}
	public Icon getIcon(JChildWindow wnd) {
		return null;
	}
	
	public SystemTray getSysTray() {
		return sysTray;
	}

	public void setSysTray(SystemTray sysTray) {
		this.sysTray = sysTray;
	}

	public TrayIcon getTrayIcon() {
		return trayIcon;
	}

	public void setTrayIcon(TrayIcon trayIcon) {
		this.trayIcon = trayIcon;
	}
}
