package com.efounder.dbc.swing.treetable;

import com.core.xml.*;
import com.efounder.datamanager.pub.meta.MetaDataConstants;
import com.efounder.dbc.swing.context.*;
import com.efounder.dbc.swing.tree.*;

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
public class ListTreeTableModel extends BaseTreeTableModel {
    public static ListTreeTableModel getInstance(TreeInfoContext context,String[] col,String[] cn){
          ListTreeTableModel md=new ListTreeTableModel(context,col,cn);
          return md;
      }
      protected ListTreeTableModel(TreeInfoContext ct, String[] ccol, String[] cn) {
          super(ct, ccol, cn);
      }
      public void setValueAt(Object aValue, Object node, int column) {
      }
      public Object getValueAt(Object node, int col) {
          DataSetTreeNode dtn=(DataSetTreeNode)node;
          if(col==0)return dtn.toString();
          StubObject so=(StubObject)dtn.getUserObject();
          return so.getObject(colIndex[col],"");
      }
      public void addListData(java.util.List list ){
          addListData(list,null,null);
      }
      public void addListData(java.util.List list,TreeInfoContext ct,DataSetTreeNode parent){
          if (ct == null)
              ct = context;
          for (int i = 0; i < list.size(); i++) {
              StubObject so = (StubObject) list.get(i);
              TreeTableNode node = (TreeTableNode)TreeUtils.createTreeNode(so, ct,nodeClass);
              node.setUserObject(so);
              putTreeNode(ct,node.getDctBH(), node);
              addChildNode(ct,node,parent);
          }
      }
      protected void CreateData() {
          putTreeNode(context,MetaDataConstants.DCT_ROOT, (DataSetTreeNode) root);
          addListData((java.util.List)context.getDataObject(),context,null);
      }
      public DataSetTreeNode getTreeNode(TreeInfoContext tc,String bh) {
          return (DataSetTreeNode) hash.get(tc.getDCTID()+"."+bh);
      }
      public void putTreeNode(TreeInfoContext tc,String bh, DataSetTreeNode node) {
          if (node == null)
              hash.remove(tc.getDCTID()+"."+bh);
          else
              hash.put(tc.getDCTID()+"."+bh, node);
      }
      protected void addChildNode(TreeInfoContext tc,DataSetTreeNode DOS,DataSetTreeNode parent) {
          if(parent==null)
              parent = getTreeNode(tc,DOS.getParentBH());
          if (parent == null)
              parent = (DataSetTreeNode) root;
          parent.add(DOS);
      }



}
