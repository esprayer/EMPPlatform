package com.efounder.eai;

import com.jidesoft.action.CommandBar;
import com.jidesoft.action.CommandBarFactory;
import com.jidesoft.action.CommandMenuBar;
import com.jidesoft.action.DockableBarContext;
import com.jidesoft.docking.DockableFrame;
import com.jidesoft.docking.DockableHolder;
import com.jidesoft.swing.JideMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 */
class DemosCommandBarFactory extends CommandBarFactory {

	public static final String DEMOS_DOCKABLE_FRAME_KEY = "Demos";
	public static final String OPTIONS_DOCKABLE_FRAME_KEY = "Options";
    public static CommandBar createMenuCommandBar(Container container) {
        CommandBar commandBar = new CommandMenuBar("Menu Bar");
        commandBar.setInitSide(DockableBarContext.DOCK_SIDE_NORTH);
        commandBar.setInitIndex(0);
        commandBar.setPaintBackground(false);
        commandBar.setStretch(true);
        commandBar.setFloatable(true);
        commandBar.setHidable(false);

        commandBar.add(createFileMenu(container));
        commandBar.add(createViewMenu(container));
        commandBar.add(createLookAndFeelMenu(container));

        return commandBar;
    }


    private static JMenu createFileMenu(final Container container) {
        JMenuItem item;

        JMenu fileMenu = new JideMenu("File");
        fileMenu.setMnemonic('F');

        item = new JMenuItem("Open", 'O');
        fileMenu.add(item);

        fileMenu.addSeparator();

        item = new JMenuItem("Exit", 'x');
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(item);
        return fileMenu;
    }

    private static JMenu createViewMenu(final Container container) {
        JMenuItem item;

        JMenu menu = new JideMenu("View");
        menu.setMnemonic('V');

        item = new JMenuItem("All Demos", DemoIconsFactory.getImageIcon(DemoIconsFactory.Frame.DEMO));
        item.setMnemonic('A');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (container instanceof DockableHolder) {
                    ((DockableHolder) container).getDockingManager().showFrame(DEMOS_DOCKABLE_FRAME_KEY);
                }
            }
        });
        menu.add(item);

        item = new JMenuItem("Options", DemoIconsFactory.getImageIcon(DemoIconsFactory.Frame.OPTIONS));
        item.setMnemonic('O');
        item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));
        item.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (container instanceof DockableHolder) {
                    ((DockableHolder) container).getDockingManager().showFrame(OPTIONS_DOCKABLE_FRAME_KEY);
                }
            }
        });
        menu.add(item);

        return menu;
    }
}
