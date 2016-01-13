package com.efounder.action;

import javax.swing.Timer;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import com.jidesoft.action.CommandBar;
import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class UpdateTimer
    extends Timer {
  protected java.util.List actionButtonList = null;
  protected static ActionListener tempActionListener = new TempActionListener();
  public UpdateTimer(int i) {
    super(i, tempActionListener);
    actionButtonList = new ArrayList();
    addActionListener(new Updater(this));
  }

  public static java.util.List getActionButtonList(UpdateTimer updatetimer) {
    return updatetimer.actionButtonList;
  }

  public int getButtonCount() {
    return actionButtonList.size();
  }

  public void removeUpdateComponent(JComponent commandBar) {
      int count = commandBar.getComponentCount();
      for(int i=0;i<count;i++) {
          Component comp = commandBar.getComponent(i);
          actionButtonList.remove(comp);
          if (actionButtonList.size() == 0) {
            stop();
        }
      }
  }

  public void clearButtons() {
    actionButtonList.clear();
    stop();
  }

  public void removeSlider(ActionSlider actioncombo) {
    actionButtonList.remove(actioncombo);
    if (actionButtonList.size() == 0) {
      stop();
    }
  }

  public void removeCombo(ActionCombo actioncombo) {
    actionButtonList.remove(actioncombo);
    if (actionButtonList.size() == 0) {
      stop();
    }
  }

  public void removeButton(ActionButton actionbutton) {
    actionButtonList.remove(actionbutton);
    if (actionButtonList.size() == 0) {
      stop();
    }
  }

  public void removeActionWidget(ActionWidget actionWidget) {
    if (actionWidget.getActionComponent() == null) {
      return;
    }
    actionButtonList.remove(actionWidget.getActionComponent());
    if (actionButtonList.size() == 0) {
      stop();
    }
  }

  public void addSlider(ActionSlider actionSlider) {
    if (actionButtonList.indexOf(actionSlider) != -1) {
      return;
    }
    actionButtonList.add(actionSlider);
    if (!isRunning()) {
      start();
    }
  }

  public void addCombo(ActionCombo actioncombo) {
    if (actionButtonList.indexOf(actioncombo) != -1) {
      return;
    }
    actionButtonList.add(actioncombo);
    if (!isRunning()) {
      start();
    }
  }

  public void addButton(ActionButton actionbutton) {
    if (actionButtonList.indexOf(actionbutton) != -1) {
      return;
    }
    actionButtonList.add(actionbutton);
    if (!isRunning()) {
      start();
    }
  }

  public void addActionWidget(ActionWidget actionWidget) {
    if (actionWidget.getActionComponent() == null) {
      return;
    }
    if (actionButtonList.indexOf(actionWidget.getActionComponent()) != -1) {
      return;
    }
    actionButtonList.add(actionWidget.getActionComponent());
    if (!isRunning()) {
      start();
    }
  }
}
//    EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
//    if (eventqueue.peekEvent() != null) {
//      return;
//    }
class Updater implements ActionListener {
  UpdateTimer updatetimer = null;
  /**
   *
   * @param actionevent ActionEvent
   */
  public void actionPerformed(ActionEvent actionevent) {
    EventQueue eventqueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
    if (eventqueue.peekEvent() != null) {
      return;
    }
    updatetimer.removeActionListener(this);
    java.util.List actionButtonList = UpdateTimer.getActionButtonList(updatetimer);
    synchronized ( actionButtonList ) {
      try {
        for (int i = 0; i < actionButtonList.size(); i++) {
          try {
            if (! (actionButtonList.get(i) instanceof ActionWidget))
              continue;
            ActionWidget aw = (ActionWidget) actionButtonList.get(i);
            javax.swing.Action action = aw.getAction();
            if (!aw.getActionComponent().isShowing() ||
                ! (action instanceof UpdateableAction))
              continue;
            ( (UpdateableAction) action).update(aw.getSource());
            aw.getActionComponent().repaint();
          }
          catch (Throwable ex) {
            ex.printStackTrace();
          }
        }
      } catch ( Throwable ex ) {
        ex.printStackTrace();
      } finally {
          updatetimer.removeActionListener(this);
          updatetimer.addActionListener(this);
      }
    }
//        try {
//          if (UpdateTimer.getActionButtonList(updatetimer).get(i) instanceof
//              ActionButton) {
//            ActionButton actionbutton = (ActionButton) UpdateTimer.
//                getActionButtonList(updatetimer).get(i);
//            javax.swing.Action action = actionbutton.getAction();
//            if (!actionbutton.isShowing() ||
//                ! (action instanceof UpdateableAction)) {
//              continue;
//            }
//            ( (UpdateableAction) action).update(actionbutton.getSource());
//            actionbutton.repaint();
////          if (action instanceof StateAction) {
////            actionbutton.repaint();
////          }
//            continue;
//          }
//          if (UpdateTimer.getActionButtonList(updatetimer).get(i) instanceof
//              ActionCombo) {
//            ActionCombo actioncombo = (ActionCombo) UpdateTimer.
//                getActionButtonList(updatetimer).get(i);
//            javax.swing.Action action1 = actioncombo.getAction();
//            if (!actioncombo.isPopupVisible() && actioncombo.isShowing() &&
//                (action1 instanceof UpdateableAction)) {
//              ( (UpdateableAction) action1).update(actioncombo.getSource());
//            }
//            continue;
//          }
//          if (UpdateTimer.getActionButtonList(updatetimer).get(i) instanceof
//              ActionSlider) {
//            ActionSlider actionslider = (ActionSlider) UpdateTimer.
//                getActionButtonList(updatetimer).get(i);
//            javax.swing.Action action1 = actionslider.getAction();
//            if (actionslider.isShowing() &&
//                (action1 instanceof UpdateableAction)) {
//              ( (UpdateableAction) action1).update(actionslider.getSource());
//              actionslider.repaint();
//            }
//            continue;
//          }
//          if (UpdateTimer.getActionButtonList(updatetimer).get(i) instanceof
//              ActionWidget) {
//            ActionWidget actionWidget = (ActionWidget) UpdateTimer.
//                getActionButtonList(updatetimer).get(i);
//            javax.swing.Action action1 = actionWidget.getAction();
//            Component comp = actionWidget.getActionComponent();
//            if (comp == null) {
//              continue;
//            }
//            if (comp.isShowing() &&
//                (action1 instanceof UpdateableAction)) {
//              ( (UpdateableAction) action1).update(actionWidget.getSource());
//              comp.repaint();
//            }
//            continue;
//          }
//        }
//        catch ( Exception ex ) {
//          ex.printStackTrace();
//        }
//      }
//    }
  }


  protected Updater(UpdateTimer updatetimer) {
    this.updatetimer = updatetimer;
  }
}

class TempActionListener
    implements ActionListener {
  public TempActionListener() {

  }

  public void actionPerformed(ActionEvent actionevent) {
  }
}
