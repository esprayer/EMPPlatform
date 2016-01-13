package com.efounder.action;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.JTextComponent;

// Referenced classes of package com.borland.primetime.actions:
//            UpdateAction, DelegateAction, ComboDelegateAction, UpdateableAction,
//            x, ActionWidget

public class ActionCombo extends JComboBox
    implements ActionWidget
{
    public class ActionPropertyChangeListener
        implements PropertyChangeListener
    {
      ActionCombo a;
        public void propertyChange(PropertyChangeEvent propertychangeevent)
        {
            String s = propertychangeevent.getPropertyName();
            if("ShortDescription".equals(s))
            {
                String s1 = (String)propertychangeevent.getNewValue();
                a.setShortText(s1 == null || "".equals(s1) ? a.action.toString() : s1);
            } else
            if("LongDescription".equals(s))
            {
                String s2 = (String)propertychangeevent.getNewValue();
                a.setLongText(s2 == null || "".equals(s2) ? a.action.toString() : s2);
            } else
            if("enabled".equals(s))
                a.setEnabled(a.action.isEnabled());
            else
            if("List".equals(s))
                a.repaint();
            else
            if("DefaultAction".equals(s))
                a.repaint();
        }

        public ActionPropertyChangeListener(ActionCombo actioncombo)
        {
          a = actioncombo;
        }

    }


    protected String longText;
    protected String shortText;
    protected PropertyChangeListener pcl;
    protected ComboDelegateAction action;
    protected int fixedHeight;
    protected int fixedWidth;
    protected Object source;

    protected void initCombo()
    {
        setEditable(action.isEditable());
        Object obj = action.getValue("ShortDescription");
        setEnabled(true);
        javax.swing.ComboBoxModel comboboxmodel = action.getComboItems();
        setModel(comboboxmodel);
        if(comboboxmodel.getSize() > 0)
            setSelectedIndex(0);
        addPopupMenuListener(new x((ActionCombo)this));
    }

    protected void setShortText(String s)
    {
        shortText = s;
        setToolTipText(s);
    }

    protected void setLongText(String s)
    {
        longText = s;
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        super.actionPerformed(actionevent);
        if(actionevent.getID() == 0 && actionevent.getActionCommand().equals(""))
            return;
        if(((String)getSelectedItem()).trim().length() > 0) {
          action.actionPerformed(actionevent);
        }
    }

    public void updateUI()
    {
        super.updateUI();
        if(isVisible())
            fixedHeight = (int)super.getPreferredSize().getHeight();
    }

    public void removeNotify()
    {
        if(action instanceof UpdateableAction)
        {
//            Browser browser = Browser.findBrowser(getParent());
//            if(browser != null)
//                browser.removeCombo(this);
        }
        if(pcl != null && action != null)
            action.removePropertyChangeListener(pcl);
        super.removeNotify();
    }

    public void addNotify()
    {
        super.addNotify();
        if(action instanceof UpdateableAction)
        {
//            Browser browser = Browser.findBrowser(getParent());
//            if(browser != null)
//                browser.addCombo(this);
            if(pcl == null)
                pcl = new ActionPropertyChangeListener(this);
            action.addPropertyChangeListener(pcl);
        }
        JTextComponent jtextcomponent = (JTextComponent)getEditor().getEditorComponent();
        jtextcomponent.setFocusTraversalKeysEnabled(false);
        jtextcomponent.setToolTipText(action.getShortText());
        setToolTipText(action.getShortText());
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(fixedWidth, fixedHeight);
    }

    public void setFixedWidth(int i)
    {
        if(i != fixedWidth)
        {
            if(i > 0)
                fixedWidth = i;
            else
                fixedWidth = 0;
            invalidate();
            repaint();
        }
    }

    public void setFixedHeight(int i)
    {
        if(i != fixedHeight)
        {
            if(i > 0)
                fixedHeight = i;
            else
                fixedHeight = 0;
            invalidate();
            repaint();
        }
    }

    public void setFixedSize(Dimension dimension)
    {
        if(dimension != null)
        {
            setFixedWidth(dimension.width);
            setFixedHeight(dimension.height);
        }
    }

    public Action getAction()
    {
        return action;
    }

    public Object getSource()
    {
        return source;
    }

    public ActionCombo(Object obj, ComboDelegateAction combodelegateaction, Dimension dimension)
    {
        fixedWidth = combodelegateaction.getWidth();
        fixedHeight = 25;
        source = obj;
        action = combodelegateaction;
        fixedHeight = (int)super.getPreferredSize().getHeight();
        setFixedSize(dimension);
        initCombo();
        if ( action.getListCellRenderer() != null )
          this.setRenderer(action.getListCellRenderer());
        ActionToolBar.addAutoUpdateComponent(this);
        this.setEditable(action.isEditable());
        this.addActionListener(combodelegateaction);
    }

    public ActionCombo(Object obj, ComboDelegateAction combodelegateaction)
    {
        this(obj, combodelegateaction, null);
    }

// Unreferenced inner classes:

/* anonymous class */
    class x
        implements PopupMenuListener
    {

        private final ActionCombo a; /* synthetic field */

        public void popupMenuWillBecomeVisible(PopupMenuEvent popupmenuevent)
        {
            javax.swing.ComboBoxModel comboboxmodel = a.action.getComboItems();
            a.setModel(comboboxmodel);
        }

        public void popupMenuWillBecomeInvisible(PopupMenuEvent popupmenuevent)
        {
        }

        public void popupMenuCanceled(PopupMenuEvent popupmenuevent)
        {
        }

        x(ActionCombo actioncombo)
        {
            a = actioncombo;
        }
    }
    public Component getActionComponent() {
      return this;
    }

}
