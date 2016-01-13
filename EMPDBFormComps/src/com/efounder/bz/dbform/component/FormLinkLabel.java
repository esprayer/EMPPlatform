package com.efounder.bz.dbform.component;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormLinkLabel extends JLabel {
	private String text;
	private boolean isSupported;
	public FormLinkLabel(String text) {
		this.text = text;
		try {
			this.isSupported = Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
		} catch (Exception e) {
			this.isSupported = false;
		}
		setText(false);
		addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				setText(isSupported);
				if (isSupported)
					setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			public void mouseExited(MouseEvent e) {
				setText(false);
			}
		});
	}
	private void setText(boolean b) {
	     if (!b)
	    	 setText("<html><font color=blue><u>" + text);
	     else
	    	 setText("<html><font color=red><u>" + text);
	}
	public static void main(String[] args) {
	     JFrame jf = new JFrame("一个超链接实现的例子-志文工作室");
	     JPanel jp = new JPanel();
	     jp.add(new FormLinkLabel("志文工作室"));
	     jf.setContentPane(jp);
	     jf.pack();
	     jf.setVisible(true);
	}
}