package com.efounder.pfc.window;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

import com.core.xml.StubObject;

public class JCloseIconTabbedPanel extends JTabbedPane implements ActionListener {
	
	// 创建窗体的Map类型集合对象
	private JWindowListener ifs = new JWindowListener();
	
	public JCloseIconTabbedPanel() {
		super();
		this.setUI(new TabbedPaneUI("#086EF1", "#E0EEE0"));
	}
	/**
     * Adds a <code>component</code> represented by a <code>title</code>
     * and/or <code>icon</code>, either of which can be <code>null</code>. 
     * Cover method for <code>insertTab</code>. 
     *
     * @param title the title to be displayed in this tab
     * @param icon the icon to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     *
     * @see #insertTab
     * @see #removeTabAt  
     */
    public void addTab(String title, Icon icon, Component component, String isClose) {
        super.addTab(title, icon, component); 
        ButtonTabComponent buttonTabComp = null;
        for(int i = 0; i < this.getTabCount(); i++) {
        	buttonTabComp = new ButtonTabComponent(this, icon, isClose);
        	if(isClose.equals("1")) {
    			buttonTabComp.addActionListener(this);
    		}
        	this.setTabComponentAt((this.getComponentCount()-1)/this.getTabCount(),buttonTabComp);
        }
    }

    /**
     * Adds a <code>component</code> represented by a <code>title</code>
     * and no icon. 
     * Cover method for <code>insertTab</code>.
     *
     * @param title the title to be displayed in this tab
     * @param component the component to be displayed when this tab is clicked
     * 
     * @see #insertTab
     * @see #removeTabAt  
     */
    public void addTab(String title, Component component, String isClose) {
    	super.addTab(title, component); 
    	ButtonTabComponent buttonTabComp = null;
    	for(int i = 0; i < this.getTabCount(); i++) {
    		buttonTabComp = new ButtonTabComponent(this, null, isClose);
    		if(isClose.equals("1")) {
    			buttonTabComp.addActionListener(this);
    		}
        	this.setTabComponentAt((this.getComponentCount()-1)/this.getTabCount(),buttonTabComp);
        }
    }

    public void addTab(StubObject nodeStub, String title, Icon icon, String tip,Component comp) {
    	int        selectTabIndex = ifs.getJWindow(nodeStub, comp);
		if(selectTabIndex > -1) {
			this.setSelectedIndex(selectTabIndex);
		} else {
			if(nodeStub.getString("tabbedClose", "1").equals("0")) {
				this.addTab(title, icon, comp, nodeStub.getString("tabbedClose", "0"));
				this.setSelectedIndex(this.getTabCount() - 1);
			} else {
				this.addTab(title, icon, comp, nodeStub.getString("tabbedClose", "1"));
				this.setSelectedIndex(this.getTabCount() - 1);
			}
		}
    }
	public void actionPerformed(ActionEvent e) {
		ButtonTabComponent tabComp = (ButtonTabComponent) ((JButton)e.getSource()).getParent();
		tabComp.removeTab(ifs);
	}
}
