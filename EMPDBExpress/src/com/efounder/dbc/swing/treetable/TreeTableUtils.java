package com.efounder.dbc.swing.treetable;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import com.efounder.comp.treetable.JTreeTable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TreeTableUtils {
    public TreeTableUtils() {
    }
    public static TreePath findValue(JTreeTable tree,Object value) {
        return findValue(tree,value,false);
    }
    public static TreePath findValue(JTreeTable tree,Object value,boolean beque) {
        int row = tree.getSelectedRow();
            DataSetTreeNodeCompare dc=new DataSetTreeNodeCompare(beque);
        DefaultMutableTreeNode node, fnode;
        TreePath path = tree.getTree().getPathForRow(row);
        if (path == null) {
            node = (DefaultMutableTreeNode) (tree.getTree().getModel()).getRoot();
        } else
            node = (DefaultMutableTreeNode) path.getLastPathComponent();
        fnode = findChild(node, value,dc);
        if (fnode == null)
            fnode = findSibling(node, value,dc);
        // 往下没找到，往上找
        if (fnode == null)
            fnode = findPrevious(node, value, dc);
        if (fnode != null) {
            TreePath treePath = new TreePath(fnode.getPath());
            path = treePath.getParentPath();
            while (path != null) {
                if (!tree.getTree().isExpanded(path))
                    tree.getTree().collapsePath(path);
                path = path.getParentPath();
            }
            tree.getTree().setSelectionPath(treePath);
//            int selRow = tree.getSelectedRow();
            int selRow=tree.getTree().getRowForPath(treePath);
            if(selRow!=-1){
                tree.getTree().setSelectionRow(selRow);
                Rectangle rect = tree.getCellRect(selRow, 0, true);
                tree.scrollRectToVisible(rect);
            }
            return treePath;
        }
        return null;
}
    public static TreePath findValue(JTree tree,Object value) {
        return findValue(tree,value,false);
    }
    public static TreePath findValue(JTree tree,Object value,boolean beque) {
        DataSetTreeNodeCompare dc=new DataSetTreeNodeCompare(beque);
        return findValue(tree,value,dc);
    }
    public static TreePath findValue(JTree tree,Object value,Comparator dc) {
        DefaultMutableTreeNode node, fnode;
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            node = (DefaultMutableTreeNode) (tree.getModel()).getRoot();
        } else
            node = (DefaultMutableTreeNode) path.getLastPathComponent();
        fnode = findChild(node, value,dc);
        if (fnode == null)
            fnode = findSibling(node, value,dc);
        if (fnode != null) {
            TreePath treePath = new TreePath(fnode.getPath());
            path = treePath.getParentPath();
            while (path != null) {
                if (!tree.isExpanded(path))
                    tree.collapsePath(path);
                path = path.getParentPath();
            }
            tree.setSelectionPath(treePath);
//            int selRow = tree.getSelectedRow();
            int selRow=tree.getRowForPath(treePath);
            if(selRow!=-1){
                tree.setSelectionRow(selRow);
                Rectangle rect = tree.getRowBounds(selRow);
                tree.scrollRectToVisible(rect);
            }
            return treePath;
        }
        return null;
}

protected static DefaultMutableTreeNode findChild(DefaultMutableTreeNode parent,Object value,Comparator cr){
    int count=parent.getChildCount();
    DefaultMutableTreeNode node;
    for(int i=0;i<count;i++){
        node=(DefaultMutableTreeNode)parent.getChildAt(i);
        if(cr.compare(node,value)==0)return node;
        node =findChild(node,value,cr);
        if(node!=null)return node;
    }
    return null;
}

protected static DefaultMutableTreeNode findSibling(DefaultMutableTreeNode node,Object value,Comparator cr){
    if(node==null)return null;
    DefaultMutableTreeNode parent=(DefaultMutableTreeNode)node.getParent();
    if(parent==null)return null;
    int index=parent.getIndex(node);
    for (int i = index + 1; i < parent.getChildCount(); i++) {
        DefaultMutableTreeNode fnode = (DefaultMutableTreeNode) parent.getChildAt(i);
        if (cr.compare(fnode, value) == 0)
            return fnode;
        fnode = findChild(fnode, value, cr);
        if (fnode != null)
            return fnode;
    }
    return findSibling((DefaultMutableTreeNode)node.getParent(),value,cr);
}


protected static DefaultMutableTreeNode findPrevious(DefaultMutableTreeNode node,Object value,Comparator cr){
    if(node==null)return null;
    DefaultMutableTreeNode parent=(DefaultMutableTreeNode)node.getParent();
    if(parent==null)return null;
    int index=parent.getIndex(node);
    for (int i = 0; i < index; i++) {
        DefaultMutableTreeNode fnode = (DefaultMutableTreeNode) parent.getChildAt(i);
        if (cr.compare(fnode, value) == 0)
            return fnode;
        fnode = findChild(fnode, value, cr);
        if (fnode != null)
            return fnode;
    }
    return findPrevious((DefaultMutableTreeNode)node.getParent(),value,cr);
}

}
