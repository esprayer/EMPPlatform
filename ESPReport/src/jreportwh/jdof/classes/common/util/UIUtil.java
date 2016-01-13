package jreportwh.jdof.classes.common.util;

import java.beans.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.tree.*;

import jframework.resource.classes.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public final class UIUtil {

  //弹出式菜单
  static ResourceBundle res = ResourceBundle.getBundle("jreportwh.jdof.classes.common.util.Language");
  private static JPopupMenu popupMenu = null;

  //标准对话框的大小
  private final static int STD_DIALOG_WIDTH   = 680;
  private final static int STD_DIALOG_HEIGHT  = 510;
  //信息对话框的大小
  private final static int INFO_DIALOG_WIDTH  = 545;
  private final static int INFO_DIALOG_HEIGHT = 400;
  //属性对话框的大小
  private final static int ATTR_DIALOG_WIDTH  = 372;
  private final static int ATTR_DIALOG_HEIGHT = 265;
  //标准按钮的大小
  private final static int STD_BUTTON_WIDTH   = 100;
  private final static int STD_BUTTON_HEIGHT  = 30;
  //标准边框的大小(top left bottom right)
  private final static Border
      stdBorder = BorderFactory.createEmptyBorder(5, 1, 5, 1);

  /**
   * 创建菜单型按钮
   */
  public static JButton creatMenuButton(Object object, String lable,
                                         String icon) {
    JButton btn = new JButton(lable);
    btn.setIcon(JXMLResource.LoadImageIcon(object, icon));
    btn.setMargin(new Insets(1, 1, 1, 1));

    return btn;

  }

  /**
   * 创建工具条项
   * 每个工具条项其实就是一个按钮
   */
  private static JButton createToolBarItem(Object object, String[] item) {

    JButton btn = new JButton();
    if (item[2] != null) {
      btn.setIcon(JXMLResource.LoadImageIcon(object, item[2]));
    }
    btn.addActionListener( (ActionListener) EventHandler.create(
        ActionListener.class,
        object,
        item[1]));
    btn.setMnemonic(item[3].charAt(0));
    btn.setText(item[0]);
    btn.setMargin(new Insets(1, 1, 1, 1));

    return btn;

  }

  /**
   * 创建按钮
   */
  public static JButton createButton(Object object, String label,
                                     String iconName, String actionName,
                                     char mnemonic) {

    JButton btn = new JButton();
    btn.addActionListener( (ActionListener) EventHandler.create(
        ActionListener.class,
        object,
        actionName));
    if (iconName != null) {
      btn.setIcon(JXMLResource.LoadImageIcon(object, iconName));
    }
    btn.setText(label);
    btn.setMnemonic(mnemonic);
    btn.setMargin(new Insets(1, 1, 1, 1));

    return btn;

  }

  /**
   * 创建按钮
   */
  public static JButton createBtn(Object object, String label,
                                  String iconName, String actionMethodName) {

    JButton btn = new JButton();
    btn.addActionListener( (ActionListener) EventHandler.create(
        ActionListener.class,
        object,
        actionMethodName));
    if (iconName != null) {
      btn.setIcon(JXMLResource.LoadImageIcon(object, iconName));
    }
    btn.setText(label);
   btn.setMargin(new Insets(1, 1, 1, 1));

    return btn;

  }

  /**
   * 创建菜单项
   */
  public static JMenuItem createMenuItem(Object object, String label,
                                         String iconName,
                                         String actionMethodName,
                                         char mnemonic) {
    JMenuItem mi = new JMenuItem();
    mi.addActionListener( (ActionListener) EventHandler.create(
        ActionListener.class,
        object,
        actionMethodName));
    if (iconName != null) {
      mi.setIcon(JXMLResource.LoadImageIcon(object, iconName));
    }
    mi.setText(label);
    mi.setMnemonic(mnemonic);

    return mi;
  }

  /**
   * 创建菜单项
   */
  public static JMenuItem createMenuItem(Object object, String label,
                                         String iconName,
                                         String actionMethodName) {
    JMenuItem mi = new JMenuItem();
    mi.addActionListener( (ActionListener) EventHandler.create(
        ActionListener.class,
        object,
        actionMethodName));
    if (iconName != null) {
      mi.setIcon(JXMLResource.LoadImageIcon(object, iconName));
    }
    mi.setText(label);

    return mi;
  }

  /**
   * 创建弹出式菜单
   * --所有的维护字典的弹出式菜单是相同的，在这里统一创建
   * --调用的类有：
   */
  public static JPopupMenu createPopupMenu(Object object) {

    if (popupMenu != null)
      return popupMenu;

    popupMenu = new JPopupMenu();
    JMenuItem
        addNodeMI = UIUtil.createMenuItem(object,
                                          res.getString("String_18"), null, "addNode", 'A'),
        addSubNodeMI = UIUtil.createMenuItem(object,
                                             res.getString("String_20"), null, "addSubNode", 'X'),
        removeNodeMI = UIUtil.createMenuItem(object,
                                             res.getString("String_22"), null, "removeNode", 'D'),
        saveMI = UIUtil.createMenuItem(object,
                                       res.getString("String_24"), null, "save", 'S'),
        containMI = UIUtil.createMenuItem(object,
                                          res.getString("String_26"), null, "contain", 'C');
    popupMenu.add(addNodeMI);
    popupMenu.add(addSubNodeMI);
    popupMenu.add(removeNodeMI);
    popupMenu.add(saveMI);
    popupMenu.addSeparator();
    popupMenu.add(containMI);

    return popupMenu;
  }

  /**
   * 创建弹出式菜单
   * --所有的维护字典的弹出式菜单是相同的，在这里统一创建
   * --调用的类有：
   */
  public static JPopupMenu createPopupMenu1(Object object) {

      if (popupMenu != null)
          return popupMenu;

      popupMenu = new JPopupMenu();
      JMenuItem
          addNodeMI = UIUtil.createMenuItem(object,
                                            res.getString("String_18"), null, "addNode", 'A'),
          removeNodeMI = UIUtil.createMenuItem(object,
                                               res.getString("String_22"), null, "removeNode", 'D'),
          saveMI = UIUtil.createMenuItem(object,
                                         res.getString("String_24"), null, "save", 'S'),
          containMI = UIUtil.createMenuItem(object,
                                            res.getString("String_26"), null, "contain", 'C');
      popupMenu.add(addNodeMI);
      popupMenu.add(removeNodeMI);
      popupMenu.add(saveMI);
      popupMenu.addSeparator();
      popupMenu.add(containMI);

      return popupMenu;
  }


  /**
   * 获得标准对话框大小
   */
  public static Dimension getStdDialogSize() {
    return new Dimension(STD_DIALOG_WIDTH, STD_DIALOG_HEIGHT);
  }

  /**
   * 信息对话框的大小
   */
  public static Dimension getInfoDialogSize() {
    return new Dimension(INFO_DIALOG_WIDTH, INFO_DIALOG_HEIGHT);
  }

  /**
   * 属性对话框的大小
   */
  public static Dimension getAttrDialogSize() {
    return new Dimension(ATTR_DIALOG_WIDTH, ATTR_DIALOG_HEIGHT);
  }

  /**
   * 本地屏幕大小
   */
  public static Dimension getScreentSize() {
    return Toolkit.getDefaultToolkit().getScreenSize();
  }

  /**
   * 获得标准边框的大小
   */
  public static Border getStdBorder() {
    return stdBorder;
  }

  /**
   * 获得标准按钮的大小
   */
  public static Dimension getStdBtnDimension() {
    return new Dimension(STD_BUTTON_WIDTH, STD_BUTTON_HEIGHT);
  }

  /**
   * 参照整个屏幕，窗口居中
   */
  public static void setCenter(Window window) {

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension localWindowSize = window.getSize();
    if (localWindowSize.height > screenSize.height) {
      localWindowSize.height = screenSize.height;
    }
    if (localWindowSize.width > screenSize.width) {
      localWindowSize.width = screenSize.width;
    }
    int xCoord = (screenSize.width / 2 - localWindowSize.width / 2);
    int yCoord = (screenSize.height / 2 - localWindowSize.height / 2);
    window.setLocation(xCoord, yCoord);

  }

  /**
   * 参照父窗口，窗口居中
   */
  public static void setCenterOnParent(Window window) {

    window.pack();

    Dimension parentWindowSize = window.getParent().getSize();
    Dimension localWindowSize = window.getSize();
    int xCoord = window.getParent().getLocationOnScreen().x +
        (parentWindowSize.width / 2 -
         localWindowSize.width / 2);
    int yCoord = window.getParent().getLocationOnScreen().y +
        (parentWindowSize.height / 2 -
         localWindowSize.height / 2);

    //Ensure that no part of aWindow will be off-screen
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int xOffScreenExcess = xCoord + localWindowSize.width - screen.width;
    if (xOffScreenExcess > 0) {
      xCoord = xCoord - xOffScreenExcess;
    }
    if (xCoord < 0) {
      xCoord = 0;
    }
    int yOffScreenExcess = yCoord + localWindowSize.height - screen.height;
    if (yOffScreenExcess > 0) {
      yCoord = yCoord - yOffScreenExcess;
    }
    if (yCoord < 0) {
      yCoord = 0;
    }
    window.setLocation(xCoord, yCoord);

  }

  /**
   * 展开或者折叠树的某节点下的所有节点
   * 用法：
   *   TreeNode root = (TreeNode) tree.getModel().getRoot();
   *   Util.expandAll(tree, new TreePath(root), expand);
   * @param tree     树
   * @param parent   父节点
   * @param expand   展开/关闭
   */
  public static void setExpandAll(JTree tree, TreePath parent, boolean expand) {
    // Traverse children
    TreeNode node = (TreeNode) parent.getLastPathComponent();
    if (node.getChildCount() >= 0) {
      for (Enumeration e = node.children(); e.hasMoreElements(); ) {
        TreeNode n = (TreeNode) e.nextElement();
        TreePath path = parent.pathByAddingChild(n);
      }
    }
    if (expand) {
      tree.expandPath(parent);
    }
    else {
      tree.collapsePath(parent);
    }
  }

  /**
   * 展开树中某个节点下的所有节点
   * @param tree     树
   * @param parent   父节点
   * @param expand   展开/关闭
   */
  public static void setExpand(JTree tree, TreePath parent, boolean expand) {
    // Traverse children
    TreeNode node = (TreeNode) parent.getLastPathComponent();
    if (node.getChildCount() >= 0) {
      for (Enumeration e = node.children(); e.hasMoreElements(); ) {
        TreeNode n = (TreeNode) e.nextElement();
        TreePath path = parent.pathByAddingChild(n);
        setExpandAll(tree, path, expand);
      }
    }

  }

  /**
   * 设置鼠标指针状态为等待态
   * @param comp
   */
  public static void setWaitCursor(Component comp) {
    comp.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  }

  /**
   * 设置鼠标指针状态为默认态
   * @param comp
   */
  public static void setDefaultCurosr(Component comp) {
    comp.setCursor(Cursor.getDefaultCursor());
  }

  /**
   * 树节点是否被选择
   * @param tree
   * @return
   */
  public static boolean hasTreeNodeSelection(JTree tree) {
    if (tree.getLastSelectedPathComponent() != null) {
      return true;
    }
    return false;
  }

  /**
   * 树节点是否被选择，除根节点
   * @param tree
   * @return
   */
  public static boolean hasTreeSelectionExceptRoot(JTree tree) {
    TreeNode node = (TreeNode) tree.getLastSelectedPathComponent();
    if (node != null && node != tree.getModel().getRoot()) {
      return true;
    }
    return false;
  }

  /**
   * JTreeTable,除第一行外，有无选择行
   * @param table
   * @return
   */
  public static boolean hasTreeTableRowSelection(JTable table) {
    if (hasTableRowSelection(table)) {
      if (table.getSelectedRowCount() > 1) {
        return true;
      }
      else {
        return table.getSelectedRow() != 0;
      }
    }
    return false;
  }

  /**
   * 表格是否有行选择
   * @param table
   * @return
   */
  public static boolean hasTableRowSelection(JTable table) {
    if (table.getSelectedRow() != -1) {
      return true;
    }
    return false;
  }

  /**
   * 表格是否有列选择
   * @param table
   * @return
   */
  public static boolean hasTableColSelection(JTable table) {
    if (table.getSelectedColumn() != -1) {
      return true;
    }
    return false;
  }

  /**
   * 创建信息提示窗口
   *
   * @param object Object
   *     --父窗口
   * @param content String
   *     --显示内容
   * @param title String
   *     --显示标题
   */
  public static void createOptionDialog(Object object, String content,
                                        String title) {

  }

  /**
   * 创建信息提示窗口
   *
   * @param object Object
   *     --父窗口
   * @param content String
   *     --显示内容
   * @param title String
   *     --显示标题
   */
  public static void createMessageDialog(JDialog object,
                                         String content, String title) {
    JOptionPane.showMessageDialog(object, content, title,
                                  JOptionPane.INFORMATION_MESSAGE);
  }
}
