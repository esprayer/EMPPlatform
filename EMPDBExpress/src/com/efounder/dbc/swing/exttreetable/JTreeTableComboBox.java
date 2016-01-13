package com.efounder.dbc.swing.exttreetable;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.*;
import com.sun.java.swing.plaf.motif.*;
import com.sun.java.swing.plaf.windows.*;
import com.incors.plaf.alloy.AlloyComboBoxUI;
import com.sunking.swing.JFileTree;
import com.efounder.comp.treetable.JTreeTable;
import java.util.List;
import java.util.ArrayList;

/**
 * <p>Title: OpenSwing</p>
 * <p>Description: 树形下拉列表框</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author  <a href="mailto:rockis@msn.com">zhanglei</a>
 *  && <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
 * @version 1.0
 */
public class JTreeTableComboBox extends JComboBox{
    protected int popWidth;
    protected int popHeight;
    public void setPopHeight(int popHeight)
    {
        this.popHeight = popHeight;
    }

    public void setPopWidth(int popWidth)
    {
        this.popWidth = popWidth;
    }
    public int getPopHeight()
    {
        if(popHeight == 0)
            return 200;
        else
            return popHeight;
    }

    public int getPopWidth()
    {
        if(popWidth == 0)
            return getWidth();
//          return 200;
        else
            return popWidth;
    }
    /**
     * 显示用的树
     */
//    private JTree tree;
    private JTreeTable treeTable;

    public JTreeTableComboBox(){
        this(null);
    }

    public JTreeTableComboBox(JTreeTable tree){
        this.setTree(tree);
    }

    /**
     * 设置树
     * @param tree JTree
     */
    public void setTree(JTreeTable treeTable){
        this.treeTable = treeTable;
        if(treeTable != null){
            this.setSelectedItem(treeTable.getTree().getSelectionPath());
            this.setRenderer(new JTreeComboBoxRenderer());
        }
        this.updateUI();
    }

    /**
     * 取得树
     * @return JTree
     */
    public JTreeTable getTree(){
        if(treeTable == null) return new JTreeTable();
        return  treeTable;
    }

    /**
     * 设置当前选择的树路径
     * @param o Object
     */
    public void setSelectedItem(Object o){
      Object obj;
      if(o instanceof String){
        String bh = (String)o;
        List findNodeList = TreeTableUtil.findNode(bh,treeTable.getTree());
        if(findNodeList == null || findNodeList.size() == 0) return;
        obj = findNodeList.get(0);
      }
      else{
        obj = o;
      }
        treeTable.getTree().setSelectionPath((TreePath)obj);
        setItemValue(obj);
        getModel().setSelectedItem(obj);

    }
    protected void setItemValue(Object o) {

    }
    public void updateUI(){
        ComboBoxUI cui = (ComboBoxUI)UIManager.getUI(this);
        if(cui instanceof MetalComboBoxUI)
            cui = new MetalJTreeComboBoxUI();
        else
        if(cui instanceof MotifComboBoxUI)
            cui = new MotifJTreeComboBoxUI();
        else
        if(cui instanceof AlloyComboBoxUI)
            cui = new AlloyJTreeComboBoxUI();
        else
            cui = new WindowsJTreeComboBoxUI();
        setUI(cui);
    }

    // UI Inner classes -- one for each supported Look and Feel
    class MetalJTreeComboBoxUI extends MetalComboBoxUI{
        protected ComboPopup createPopup(){
            return new TreeTablePopup(comboBox);
        }
    }

    class WindowsJTreeComboBoxUI extends WindowsComboBoxUI{
        protected ComboPopup createPopup(){
            return new TreeTablePopup(comboBox);
        }
    }
    class AlloyJTreeComboBoxUI extends AlloyComboBoxUI
    {
        protected ComboPopup createPopup()        {
            return new TreeTablePopup(comboBox);
        }
    }
    class MotifJTreeComboBoxUI extends MotifComboBoxUI{
        protected ComboPopup createPopup(){
            return new TreeTablePopup(comboBox);
        }
    }
    /**
     * <p>Title: OpenSwing</p>
     * <p>Description: 树形结构而来的DefaultListCellRenderer </p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: </p>
     * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
     * @version 1.0
     */
    class JTreeComboBoxRenderer extends DefaultListCellRenderer{
        public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus){
            if(value != null){
                TreePath path = (TreePath)value;
                TreeNode node = (TreeNode)path.getLastPathComponent();
                value = node;
                TreeCellRenderer r = treeTable.getTree().getCellRenderer();
                JLabel lb = (JLabel)r.getTreeCellRendererComponent(
                    treeTable.getTree(), value, isSelected, false, node.isLeaf(), index,
                    cellHasFocus);
                return lb;
            }
            return super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
        }
    }
}

/**
 * <p>Title: JTreeComboBox</p>
 * <p>Description: TreePopup</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author  <a href="mailto:rockis@msn.com">zhanglei</a>
 *  && <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
 * @version 1.0
 */
class TreeTablePopup extends JPopupMenu implements ComboPopup{
    protected JTreeTableComboBox comboBox;
    protected JScrollPane scrollPane;

    protected MouseMotionListener mouseMotionListener;
    protected MouseListener mouseListener;
    private MouseListener treeSelectListener = new MouseAdapter(){
        public void mouseReleased(MouseEvent e){
            JTreeTable treeTable = (JTreeTable)e.getSource();
            int row = treeTable.getSelectedRow();
            if (row == -1) {
              return ;
            }
            JTree tree = treeTable.getTree();
            TreePath path = tree.getPathForRow(row);
//            TreeTableNodeExt selObj = (TreeTableNodeExt)path.getLastPathComponent();
            comboBox.setSelectedItem(path);
//            setVisible(false);
//            togglePopup();
            hide();
            MenuSelectionManager.defaultManager().clearSelectedPath();

//            JTree tree = treeTable.getTree();
//            TreePath tp = tree.getPathForLocation(e.getPoint().x,
//                                                  e.getPoint().y);
//            if(tp == null){
//                return;
//            }
//            comboBox.setSelectedItem(tp);
//            togglePopup();
//            MenuSelectionManager.defaultManager().clearSelectedPath();
        }
    };

    public TreeTablePopup(JComboBox comboBox){
        this.comboBox = (JTreeTableComboBox)comboBox;
        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new BorderLayout());
        setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
        JTreeTable treeTable = this.comboBox.getTree();
        if(treeTable != null){
            scrollPane = new JScrollPane(treeTable);
            scrollPane.setBorder(null);
            add(scrollPane, BorderLayout.CENTER);
            treeTable.addMouseListener(treeSelectListener);
        }
    }

    public void show(){
        updatePopup();
        show(comboBox, 0, comboBox.getHeight());
        if ( comboBox.getTree() != null )
            comboBox.getTree().getTree().requestFocus();
    }

    public void hide(){
        setVisible(false);
        comboBox.firePropertyChange("popupVisible", true, false);
    }

    protected JList list = new JList();
    public JList getList(){
        return list;
    }

    public MouseMotionListener getMouseMotionListener(){
        if(mouseMotionListener == null){
            mouseMotionListener = new MouseMotionAdapter(){};
        }
        return mouseMotionListener;
    }

    public KeyListener getKeyListener(){
        return null;
    }

    public void uninstallingUI(){}

    /**
     * Implementation of ComboPopup.getMouseListener().
     *
     * @return a <code>MouseListener</code> or null
     * @see ComboPopup#getMouseListener
     */
    public MouseListener getMouseListener(){
        if(mouseListener == null){
            mouseListener = new InvocationMouseHandler();
        }
        return mouseListener;
    }

    protected void togglePopup(){
        if(isVisible()){
            hide();
        } else{
            show();
        }
    }

    protected void updatePopup(){
        setPreferredSize(new Dimension(comboBox.getPopWidth(), comboBox.getPopHeight()));
        Object selectedObj = comboBox.getSelectedItem();
        if(selectedObj != null){
            TreePath tp = (TreePath)selectedObj;
            if ( comboBox.getTree() != null )
                ((JTreeTableComboBox)comboBox).getTree().getTree().setSelectionPath(tp);
        }
    }

    protected class InvocationMouseHandler extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            if(!SwingUtilities.isLeftMouseButton(e) || !comboBox.isEnabled()){
                return;
            }
            if(comboBox.isEditable()){
                Component comp = comboBox.getEditor().getEditorComponent();
                if((!(comp instanceof JComponent)) ||
                   ((JComponent)comp).isRequestFocusEnabled()){
                    comp.requestFocus();
                }
            } else if(comboBox.isRequestFocusEnabled()){
                comboBox.requestFocus();
            }
            togglePopup();
        }
    }
}



