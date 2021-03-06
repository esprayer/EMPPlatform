/*
 * David Bismut, davidou@mageos.com
 * Intern, SETLabs, Infosys Technologies Ltd. May 2004 - Jul 2004
 * Ecole des Mines de Nantes, France
 */

package com.swing.tablepane;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;

import com.pub.toolkit.TResource;

/**
 * A JTabbedPane with some added UI functionalities. A close and max/detach
 * icons are added to every tab, typically to let the user close or detach the
 * tab by clicking on these icons.
 *
 * @version 1.1 06/07/04
 * @author David Bismut, davidou@mageos.com
 */

public class CloseAndMaxTabbedPane extends JTabbedPane {

	private int overTabIndex = -1;

	private CloseTabPaneUI paneUI;

        private void setupSystemicon(){
          String pIconPath = "/com/swing/tablepane/";
          Icon    pMaxIcon = TResource.LoadImageIcon(this,pIconPath+"FM.gif");
          Icon  pCloseIcon = TResource.LoadImageIcon(this,pIconPath+"FC.gif");

          System.getProperties().put("MAX_ICON",pMaxIcon);
          System.getProperties().put("RES_ICON",pCloseIcon);

        }

	/**
	 * Creates the <code>CloseAndMaxTabbedPane</code> with an enhanced UI if
	 * <code>enhancedUI</code> parameter is set to <code>true</code>.
	 *
	 * @param enhancedUI
	 *            whether the tabbedPane should use an enhanced UI
	 */
        public CloseAndMaxTabbedPane(boolean enhancedUI) {
            setupSystemicon();
            super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

            if (enhancedUI)
                paneUI = new CloseTabPaneEnhancedUI();
            else
                paneUI = new CloseTabPaneUI();

            super.setUI(paneUI);
        }

	/**
	 * Returns the index of the last tab on which the mouse did an action.
	 */
	public int getOverTabIndex() {
		return overTabIndex;
	}

	/**
	 * Returns <code>true</code> if the close icon is enabled.
	 */
	public boolean isCloseEnabled() {
		return paneUI.isCloseEnabled();
	}

	/**
	 * Returns <code>true</code> if the max/detach icon is enabled.
	 */
	public boolean isMaxEnabled() {
		return paneUI.isMaxEnabled();
	}

	/**
	 * Override JTabbedPane method. Does nothing.
	 */
	public void setTabLayoutPolicy(int tabLayoutPolicy) {
          super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	/**
	 * Override JTabbedPane method. Does nothing.
	 */
	public void setTabPlacement(int tabPlacement) {
          super.setTabPlacement(tabPlacement);
	}

	/**
	 * Override JTabbedPane method. Does nothing.
	 */
	public void setUI(TabbedPaneUI ui) {
	}

	/**
	 * Sets whether the tabbedPane should have a close icon or not.
	 *
	 * @param b
	 *            whether the tabbedPane should have a close icon or not
	 */
	public void setCloseIcon(boolean b) {
		paneUI.setCloseIcon(b);
	}

	/**
	 * Sets whether the tabbedPane should have a max/detach icon or not.
	 *
	 * @param b
	 *            whether the tabbedPane should have a max/detach icon or not
	 */
	public void setMaxIcon(boolean b) {
		paneUI.setMaxIcon(b);
	}

	/**
	 * Detaches the <code>index</code> tab in a seperate frame. When the frame
	 * is closed, the tab is automatically reinserted into the tabbedPane.
	 *
	 * @param index
	 *            index of the tabbedPane to be detached
	 */
	public void detachTab(int index) {

		if (index < 0 || index >= getTabCount())
			return;

		final JFrame frame = new JFrame();

		Window parentWindow = SwingUtilities.windowForComponent(this);

		final int tabIndex = index;
		final JComponent c = (JComponent) getComponentAt(tabIndex);

		final Icon icon = getIconAt(tabIndex);
		final String title = getTitleAt(tabIndex);
		final String toolTip = getToolTipTextAt(tabIndex);
		final Border border = c.getBorder();

		removeTabAt(index);

		c.setPreferredSize(c.getSize());

		frame.setTitle(title);
		frame.getContentPane().add(c);
		frame.setLocation(parentWindow.getLocation());
		frame.pack();

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				frame.dispose();

				insertTab(title, icon, c, toolTip, Math.min(tabIndex,
						getTabCount()));

				c.setBorder(border);
				setSelectedComponent(c);
			}

		});

		WindowFocusListener windowFocusListener = new WindowFocusListener() {
			long start;

			long end;

			public void windowGainedFocus(WindowEvent e) {
				start = System.currentTimeMillis();
			}

			public void windowLostFocus(WindowEvent e) {
				end = System.currentTimeMillis();
				long elapsed = end - start;
				//System.out.println(elapsed);
				if (elapsed < 100)
					frame.toFront();

				frame.removeWindowFocusListener(this);
			}
		};

		/*
		 * This is a small hack to avoid Windows GUI bug, that prevent a new
		 * window from stealing focus (without this windowFocusListener, most of
		 * the time the new frame would just blink from foreground to
		 * background). A windowFocusListener is added to the frame, and if the
		 * time between the frame beeing in foreground and the frame beeing in
		 * background is less that 100ms, it just brings the windows to the
		 * front once again. Then it removes the windowFocusListener. Note that
		 * this hack would not be required on Linux or UNIX based systems.
		 */

		frame.addWindowFocusListener(windowFocusListener);

		frame.show();
		frame.toFront();

	}

	/**
	 * Adds a <code>CloseListener</code> to the tabbedPane.
	 *
	 * @param l
	 *            the <code>CloseListener</code> to add
	 * @see #fireCloseTabEvent
	 * @see #removeCloseListener
	 */
	public synchronized void addCloseListener(CloseListener l) {
		listenerList.add(CloseListener.class, l);
	}

	/**
	 * Adds a <code>MaxListener</code> to the tabbedPane.
	 *
	 * @param l
	 *            the <code>MaxListener</code> to add
	 * @see #fireMaxTabEvent
	 * @see #removeMaxListener
	 */
	public synchronized void addMaxListener(MaxListener l) {
		listenerList.add(MaxListener.class, l);
	}

	/**
	 * Adds a <code>DoubleClickListener</code> to the tabbedPane.
	 *
	 * @param l
	 *            the <code>DoubleClickListener</code> to add
	 * @see #fireDoubleClickTabEvent
	 * @see #removeDoubleClickListener
	 */
	public synchronized void addDoubleClickListener(DoubleClickListener l) {
		listenerList.add(DoubleClickListener.class, l);
	}
        /**
         *
         * @param l PopupMenuListener
         */
        public synchronized void addPopupMenuListener(PopupMenuListener l) {
          listenerList.add(PopupMenuListener.class, l);
        }
	/**
	 * Adds a <code>PopupOutsideListener</code> to the tabbedPane.
	 *
	 * @param l
	 *            the <code>PopupOutsideListener</code> to add
	 * @see #firePopupOutsideTabEvent
	 * @see #removePopupOutsideListener
	 */
	public synchronized void addPopupOutsideListener(PopupOutsideListener l) {
		listenerList.add(PopupOutsideListener.class, l);
	}

	/**
	 * Removes a <code>CloseListener</code> from this tabbedPane.
	 *
	 * @param l
	 *            the <code>CloseListener</code> to remove
	 * @see #fireCloseTabEvent
	 * @see #addCloseListener
	 */
	public synchronized void removeCloseListener(CloseListener l) {
		listenerList.remove(CloseListener.class, l);
	}

	/**
	 * Removes a <code>MaxListener</code> from this tabbedPane.
	 *
	 * @param l
	 *            the <code>MaxListener</code> to remove
	 * @see #fireMaxTabEvent
	 * @see #addMaxListener
	 */
	public synchronized void removeMaxListener(MaxListener l) {
		listenerList.remove(MaxListener.class, l);
	}

	/**
	 * Removes a <code>DoubleClickListener</code> from this tabbedPane.
	 *
	 * @param l
	 *            the <code>DoubleClickListener</code> to remove
	 * @see #fireDoubleClickTabEvent
	 * @see #addDoubleClickListener
	 */
	public synchronized void removeDoubleClickListener(DoubleClickListener l) {
		listenerList.remove(DoubleClickListener.class, l);
	}

	/**
	 * Removes a <code>PopupOutsideListener</code> from this tabbedPane.
	 *
	 * @param l
	 *            the <code>PopupOutsideListener</code> to remove
	 * @see #firePopupOutsideTabEvent
	 * @see #addPopupOutsideListener
	 */
	public synchronized void removePopupOutsideListener(PopupOutsideListener l) {
		listenerList.remove(PopupOutsideListener.class, l);
	}

	/**
	 * Sends a <code>MouseEvent</code>, whose source is this tabbedpane, to
	 * every <code>CloseListener</code>. The method also updates the
	 * <code>overTabIndex</code> of the tabbedPane with a value coming from
	 * the UI. This method method is called each time a <code>MouseEvent</code>
	 * is received from the UI when the user clicks on the close icon of the tab
	 * which index is <code>overTabIndex</code>.
	 *
	 * @param e
	 *            the <code>MouseEvent</code> to be sent
	 * @param overTabIndex
	 *            the index of a tab, usually the tab over which the mouse is
	 *
	 * @see #addCloseListener
	 * @see EventListenerList
	 */
	public void fireCloseTabEvent(MouseEvent e, int overTabIndex) {
		this.overTabIndex = overTabIndex;

		EventListener closeListeners[] = getListeners(CloseListener.class);
		for (int i = 0; i < closeListeners.length; i++) {
			((CloseListener) closeListeners[i]).closeOperation(e);
		}
	}

	/**
	 * Sends a <code>MouseEvent</code>, whose source is this tabbedpane, to
	 * every <code>MaxListener</code>. The method also updates the
	 * <code>overTabIndex</code> of the tabbedPane with a value coming from
	 * the UI. This method method is called each time a <code>MouseEvent</code>
	 * is received from the UI when the user clicks on the max icon of the tab
	 * which index is <code>overTabIndex</code>.
	 *
	 * @param e
	 *            the <code>MouseEvent</code> to be sent
	 * @param overTabIndex
	 *            the index of a tab, usually the tab over which the mouse is
	 *
	 * @see #addMaxListener
	 * @see EventListenerList
	 */
	public void fireMaxTabEvent(MouseEvent e, int overTabIndex) {
		this.overTabIndex = overTabIndex;

		EventListener maxListeners[] = getListeners(MaxListener.class);
		for (int i = 0; i < maxListeners.length; i++) {
			((MaxListener) maxListeners[i]).maxOperation(e);
		}
	}

	/**
	 * Sends a <code>MouseEvent</code>, whose source is this tabbedpane, to
	 * every <code>DoubleClickListener</code>. The method also updates the
	 * <code>overTabIndex</code> of the tabbedPane with a value coming from
	 * the UI. This method method is called each time a <code>MouseEvent</code>
	 * is received from the UI when the user double-clicks on the tab which
	 * index is <code>overTabIndex</code>.
	 *
	 * @param e
	 *            the <code>MouseEvent</code> to be sent
	 * @param overTabIndex
	 *            the index of a tab, usually the tab over which the mouse is
	 *
	 * @see #addDoubleClickListener
	 * @see EventListenerList
	 */
	public void fireDoubleClickTabEvent(MouseEvent e, int overTabIndex) {
		this.overTabIndex = overTabIndex;

		EventListener dClickListeners[] = getListeners(DoubleClickListener.class);
		for (int i = 0; i < dClickListeners.length; i++) {
			((DoubleClickListener) dClickListeners[i]).doubleClickOperation(e);
		}
	}
        /**
         *
         * @param e MouseEvent
         * @param overTabIndex int
         */
        public void firePopupMenuTabEvent(MouseEvent e, int overTabIndex) {
                this.overTabIndex = overTabIndex;

                EventListener dClickListeners[] = getListeners(PopupMenuListener.class);
                for (int i = 0; i < dClickListeners.length; i++) {
                        ((PopupMenuListener) dClickListeners[i]).popupOutsideOperation(e);
                }
        }

	/**
	 * Sends a <code>MouseEvent</code>, whose source is this tabbedpane, to
	 * every <code>PopupOutsideListener</code>. The method also sets the
	 * <code>overTabIndex</code> to -1. This method method is called each time
	 * a <code>MouseEvent</code> is received from the UI when the user
	 * right-clicks on the inactive part of a tabbedPane.
	 *
	 * @param e
	 *            the <code>MouseEvent</code> to be sent
	 *
	 * @see #addPopupOutsideListener
	 * @see EventListenerList
	 */
	public void firePopupOutsideTabEvent(MouseEvent e) {
		this.overTabIndex = -1;

		EventListener popupListeners[] = getListeners(PopupOutsideListener.class);
		for (int i = 0; i < popupListeners.length; i++) {
			((PopupOutsideListener) popupListeners[i]).popupOutsideOperation(e);
		}
	}

}

