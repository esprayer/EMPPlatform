package com.efounder.dbc.swing.treetable;

import com.efounder.datamanager.pub.meta.MetaDataConstants;
import com.efounder.dbc.swing.context.TreeInfoContext;
import com.efounder.dbc.swing.tree.DataSetTreeNode;
import com.efounder.dbc.swing.tree.TreeUtils;
import com.core.xml.StubObject;
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
public class EFDataSetTreeTableModel extends BaseTreeTableModel {
    public static EFDataSetTreeTableModel getInstance(TreeInfoContext context,String[] col,String[] cn){
          EFDataSetTreeTableModel md=new EFDataSetTreeTableModel(context,col,cn);
          return md;
      }
      protected EFDataSetTreeTableModel(TreeInfoContext ct, String[] ccol, String[] cn) {
          super(ct, ccol, cn);
      }
      public void setValueAt(Object aValue, Object node, int column) {
      }
      public Object getValueAt(Object node, int col) {
          DataSetTreeNode dtn=(DataSetTreeNode)node;
          if(col==0)return dtn.toString();
          EFRowSet so=(EFRowSet)dtn.getUserObject();
          return so.getObject(colIndex[col],"");
      }
      public void addDataSetData(EFDataSet list ){
          addDataSetData(list,null,null);
      }
      public void addDataSetData(EFDataSet list,TreeInfoContext ct,DataSetTreeNode parent){
          if (ct == null)
              ct = context;
          int count=list.getRowCount();
          for (int i = 0; i < count; i++) {
              EFRowSet so =list.getRowSet(i);
              TreeTableNode node = (TreeTableNode)TreeUtils.createTreeNode(so, ct,nodeClass);
              node.setUserObject(so);
              putTreeNode(ct,node.getDctBH(), node);
              addChildNode(ct,node,parent);
          }
      }
      protected void CreateData() {
          putTreeNode(context,MetaDataConstants.DCT_ROOT, (DataSetTreeNode) root);
          addDataSetData((EFDataSet)context.getDataObject(),context,null);
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
