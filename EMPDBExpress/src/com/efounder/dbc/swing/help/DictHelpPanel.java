package com.efounder.dbc.swing.help;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import com.core.xml.*;
import com.efounder.dbc.swing.context.*;
import com.efounder.dbc.swing.tree.*;
import com.efounder.dctbuilder.data.*;
import com.efounder.pfc.dialog.*;
import com.efounder.service.meta.db.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import com.efounder.builder.base.data.*;

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
public class DictHelpPanel extends JPanel   implements IDictColHelp, MouseListener {
      JScrollPane jsp=new JScrollPane();
       DictTree tree=null;
      public DictHelpPanel() {

    }

    private void jbInit()  {
        this.setBackground(SystemColor.control);
        this.setLayout(borderLayout1);
        this.add(jsp,BorderLayout.CENTER);
        jsp.getViewport().add(tree);
        jsp.setPreferredSize(new Dimension(350,400));
    }

    BorderLayout borderLayout1 = new BorderLayout();

    public void findValue(String value) {
        int count=tree.getRowCount();
      int[]rows=tree.getSelectionRows();
      int row=0;
      if(rows!=null)row=rows[0];
      for(row++;row<count;row++){
          DataSetTreeNode node=(DataSetTreeNode)tree.getPathForRow(row).getLastPathComponent();
          if(node.getDctBH().indexOf(value)!=-1||
          node.getDctMc().indexOf(value)!=-1){
              tree.setSelectionRow(row);
              tree.scrollRowToVisible(row);
              break;
          }
      }

    }
    public void initFormContext(TreeInfoContext info){
        tree = new DictTree();
        jbInit();
        if(info.getDataSet()!=null)
            tree.setModel(DatasetTreeModel.getInstance(info));
        else
            tree.setModel(ListDataTreeModel.getInstance(info));
    }
    protected void initFormMetaData(Container parent,ColumnMetaData o){
        tree = new DictTree();
        jbInit();
        ColumnMetaData cmd = o;
        DictMetaDataEx cmde = null;
        DictMetadata dmd = cmd.getFKMetaData();
        if (dmd != null) {
            cmde = new DictMetaDataEx(dmd);
        }
        EFDataSet map = cmd.getFKDataMap();
        if (map == null)
            return;
        TreeInfoContext tif = new TreeInfoContext();
        if (cmde != null) {
            tif.setBHColumn(cmde.getDctBmCol());
            tif.setMCColumn(cmde.getDctMcCol());
            tif.setJSColumn(cmde.getDctJsCol());
            tif.setMXColumn(cmde.getDctMxCol());
            tif.setParentColumn(cmde.getDctParentCol());
            tif.setBMStru(cmde.getDctBMStru());
        } else {
            tif.setBHColumn("ZD_BH");
            tif.setMCColumn("ZD_MC");
        }
        tif.setBMStru("");
        tif.setCaption(cmd.getColMc());
        tif.setDataObject(map);
        initFormContext(tif);
    }
    public void initObj(Container parent,Object o) {
        if(o instanceof ColumnMetaData)
            initFormMetaData(parent,(ColumnMetaData)o);
        else
            initFormContext((TreeInfoContext)o);

        TreeUtils.expandAll(tree, true);
    }
    public Object getResult() {
        String selBh="",selMC="";
        if(tree.getSelectionPaths()!=null){
            TreePath[]tps=tree.getSelectionPaths();
            Object[]strs=new Object[tps.length];
            for(int i=0;i<tps.length;i++){
                DataSetTreeNode dstn = (DataSetTreeNode) tps[i].getLastPathComponent();
                strs[i]=dstn.getUserObject();
            }
            if(tps.length==1)return strs[0];
            return strs;
        }else
            selBh="";
        return  new StubObject();
    }

    public DictTree getTree() {
        return tree;
    }

    /**
     * Invoked when the mouse button has been clicked (pressed and released) on a component.
     *
     * @param e MouseEvent
     * @todo Implement this java.awt.event.MouseListener method
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getButton() == e.BUTTON1) {

        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }


}
