package com.efounder.eai;

import com.efounder.eai.ide.EnterpriseExplorer;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.eai.ui.JWindowPanel;
import com.efounder.pfc.window.IView;
import com.jidesoft.action.DockableBarDockableHolderPanel;
import com.jidesoft.action.DockableBarHolder;
import com.jidesoft.docking.DockableHolder;
import com.jidesoft.document.IDocumentPane;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.office2003.BasicOffice2003Theme;
import com.jidesoft.plaf.office2003.Office2003Painter;
import com.jidesoft.status.StatusBar;
import com.jidesoft.utils.PortingUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class JideDemos extends DockableBarDockableHolderPanel {
	private static JideDemos _holder;
	private static StatusBar _statusBar;
	private static final String PROFILE_NAME = "EnterpriseExplorer";
  
	public static DockableBarHolder getDockableBarHolder() {
		return _holder;
	}
  
	public static DockableHolder getDockableHolder() {
   	 	return _holder;
  	}
  
	public static IDocumentPane getDocumentPane() {
		return null;
	}
  
	public static StatusBar getStatusBar() {
		return _statusBar;
	}
  
	private static boolean _autohideAll = false;
	private static byte[] _fullScreenLayout;
	public static final String TOP_DOCKABLE_FRAME_KEY = "TopView";
	public static final String EXPLORER_DOCKABLE_FRAME_KEY = "ExplorerView";
	public static final String STRUCT_DOCKABLE_FRAME_KEY = "StructView";
	public static final String MESSAGE_DOCKABLE_FRAME_KEY = "MessageView";
	public static final String PROPERTY_DOCKABLE_FRAME_KEY = "PropertyView";
	private static JFrame _frame;
	private static JWindowPanel windowPanel = null;
    public static IView getContentView() {
    	return windowPanel;
	}
  
    public JideDemos(RootPaneContainer rootPaneContainer) {
    	super(rootPaneContainer);
    	initOfficeLandF();
    
    	getLayoutPersistence().setProfileKey("EnterpriseExplorer");
    
    	getDockingManager().setAllowedDockSides(15);

    	windowPanel = new JWindowPanel();
    
    	windowPanel.setDockableBarHolder(this);
    
    	windowPanel.setDockableHolder(this);
    	getDockingManager().getWorkspace().setLayout(new BorderLayout());
   	 	getDockingManager().getWorkspace().add(windowPanel, "Center");

   	 	getDockableBarManager().setProfileKey("EnterpriseExplorer");

   	 	getDockingManager().getWorkspace().setAdjustOpacityOnFly(true);
    
   	 	getDockingManager().setUndoLimit(10);
   	 	getDockingManager().setAutohideShowingContentHidden(false);
    	getDockingManager().beginLoadLayoutData();
    
    	getDockingManager().setInitSplitPriority(0);
    
    	getDockingManager().setShowGripper(true);
    }
  
    protected static void initOfficeLandF() {
    	BasicOffice2003Theme theme = new BasicOffice2003Theme("Custom");
    	theme.setBaseColor(new Color(50, 190, 150), true, "default");
    	((Office2003Painter)Office2003Painter.getInstance()).addTheme(theme);
    
    	LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
    	try {
    		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    		LookAndFeelFactory.installJideExtension(7);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
  
    public static void main(EnterpriseExplorer frame) {
    	PortingUtils.prerequisiteChecking();
    	
    	_frame = frame;
	    
	    frame.getRootPaneContainer().getRootPane().getInputMap(2).put(KeyStroke.getKeyStroke(71, 3), "printMem");

	    frame.getRootPaneContainer().getRootPane().getActionMap().put("printMem", new AbstractAction() {
	    	public void actionPerformed(ActionEvent e) {}
	    });
	    _statusBar = createStatusBar();
	    frame.getRootPaneContainer().getContentPane().add(_statusBar, "Last");
    
	    _holder = new JideDemos(frame.getRootPaneContainer());
	    SwingUtilities.updateComponentTreeUI(frame);
	    SwingUtilities.updateComponentTreeUI(_holder);  
	    frame.getRootPaneContainer().getContentPane().add(_holder, "Center"); 
	    _holder.getLayoutPersistence().loadLayoutData();
	    _holder.getDockingManager().hideFrame("PropertyView");
	    _holder.getDockingManager().activateFrame("ExplorerView"); 
    }
  
    private static void clearUp() {
    	if ((_statusBar != null) && (_statusBar.getParent() != null)) {
    		_statusBar.getParent().remove(_statusBar);
    	}
    	_statusBar = null;
    
    	_frame.dispose();
    }
  
    private static StatusBar createStatusBar() {
    	StatusBar statusBar = new StatusBar();
    
    	return statusBar;
    }
  
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	if (LookAndFeelFactory.isCurrentLnfDecorated()) {
    		ImageIcon imageIcon = (ImageIcon)ExplorerIcons.getExplorerIcon("appflag.png");
    		if (imageIcon != null) {
    			imageIcon.paintIcon(this, g, getWidth() - imageIcon.getIconWidth() - 2, 2);
    		}
    	}
    }
}