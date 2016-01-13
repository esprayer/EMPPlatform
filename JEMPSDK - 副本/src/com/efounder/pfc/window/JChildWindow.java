package com.efounder.pfc.window;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

public class JChildWindow extends JInternalFrame implements MouseListener {
	
	JTabbedPane tabbedPane = new JTabbedPane();  
	
	public JChildWindow() {
		super();
		tabbedPane.addMouseListener(this);
		this.add(tabbedPane);
	}
	
	public void openWindow(String title,Icon icon, String tip,Component comp) {
		ImageIcon icon1 = new ImageIcon("images/middle.jpg");
		tabbedPane.addTab(title, new CloseTabIcon(icon), comp);  
//		tabbedPane.addTab("Three", icon, comp, "第三个卡片提示信息！");  
	}
	
	/**
	* The class which generates the 'X' icon for the tabs. The constructor
	* accepts an icon which is extra to the 'X' icon, so you can have tabs
	* like in JBuilder. This value is null if no extra icon is required.
	*/
	class CloseTabIcon implements Icon {
	    private int x_pos;
	    private int y_pos;
	    private int width;
	    private int height;
	    private Icon fileIcon;
	    public CloseTabIcon(Icon fileIcon) {
	        this.fileIcon = fileIcon;
	        width = 16;
	        height = 16;
	    }
	    public void paintIcon(Component c, Graphics g, int x, int y) {
	        this.x_pos = x;
	        this.y_pos = y;
	        Color col = g.getColor();
	        g.setColor(Color.black);
	        int y_p = y + 2;
	        g.drawLine(x + 1, y_p, x + 12, y_p);
	        g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
	        g.drawLine(x, y_p + 1, x, y_p + 12);
	        g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
	        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
	        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
	        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
	        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
	        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
	        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
	        g.setColor(col);
	        if (fileIcon != null) {
	            fileIcon.paintIcon(c, g, x + width, y_p);
	        }
	    }
	    public int getIconWidth() {
	        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
	    }
	    public int getIconHeight() {
	        return height;
	    }
	    public Rectangle getBounds() {
	        return new Rectangle(x_pos, y_pos, width, height);
	    }
	    
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int tabNumber = tabbedPane.getUI().tabForCoordinate(tabbedPane, e.getX(), e.getY());
        if (tabNumber < 0) {
            return;
        }
        Rectangle rect = ((CloseTabIcon) tabbedPane.getIconAt(tabNumber)).getBounds();
        if (rect.contains(e.getX(), e.getY())) {
            //the tab is being closed
        	tabbedPane.removeTabAt(tabNumber);
        }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
