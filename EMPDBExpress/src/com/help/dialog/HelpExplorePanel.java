package com.help.dialog;

import java.awt.*;
import java.math.*;

import javax.swing.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.dbc.swing.context.TreeInfoContext;
import com.efounder.dbc.swing.tree.DataSetTreeNode;
import com.efounder.dbc.swing.tree.DictTree;
import com.efounder.dbc.swing.tree.EFDataSetTreeModel;
import com.help.HelpInfoContext;
import com.help.util.ESPHelpUtil;

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
public class HelpExplorePanel extends JPanel {
    BorderLayout borderLayout1 = new BorderLayout();
    DictTree tree=new DictTree();
    JScrollPane tsp=new JScrollPane();
    public JPanel top=new JPanel(new BorderLayout());
    JLabel lb=new JLabel();
    JComboBox typeBox=new JComboBox();

    //是否显示树
    boolean isShowTree = false;
    public HelpExplorePanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void setTypeText(String s){
        lb.setText(s);
    }
    public JComboBox getTypeBox(){
        return typeBox;
    }

    public boolean isIsShowTree() {
        return isShowTree;
    }

    public JTree getExploreTree(){
        return tree;
    }
    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.add(top,BorderLayout.NORTH);
        this.add(tsp,BorderLayout.CENTER);
        top.add(lb,BorderLayout.WEST);
        top.add(typeBox,BorderLayout.CENTER);
        tsp.getViewport().add(tree);
        tree.setVisible(false);
    }
    DCTMetaData dmd;
    //add by lchong :添加一个上下文变量
    public void setExploreData(DCTMetaData dmd,EFDataSet eds,boolean bReSet){
        setExploreData(null,dmd,eds,bReSet);
    }
    public void setExploreData(HelpInfoContext hic,DCTMetaData dmd,EFDataSet eds,boolean bReSet){
        this.dmd=dmd;
        try{
          tree.setVisible(false);
          String mxColId = dmd.getDCT_MXCOLID();

          //modified by lchong at 2011-12-18:左边的树也要根据过滤器的内容进行过滤
          EFDataSet filterDataSet = null;
          if(hic != null){
              Object o=hic.getFilterObject();
              if(o !=null&& o instanceof com.efounder.mdm.IMainDataFilter){
                  filterDataSet = ((com.efounder.mdm.IMainDataFilter) o).filterMainData(eds);
              }else{
                  filterDataSet = eds;
              }
          }else{
              filterDataSet = eds;
          }

//          int cnt = eds.getRowCount();
          int cnt = filterDataSet.getRowCount();
          //modified end

          EFDataSet nomxDataSet = EFDataSet.getInstance(eds.getTableName());
          for(int i = 0; i < cnt; i++){
              //modified by lchong at 2011-12-18
              EFRowSet rowSet = filterDataSet.getRowSet(i);
//             EFRowSet rowSet = eds.getRowSet(i);
              //modified end

             //modified by lchong at 2011-4-21
//             String isMX = rowSet.getString(mxColId,"");
             Object objMX = rowSet.getObject(mxColId,"");
             if(objMX instanceof BigDecimal){
                 int mx = ((BigDecimal)objMX).intValue();
                 if(mx == 0){
                    nomxDataSet.addRowSet(rowSet);
                    isShowTree = true;
                 }
             }else{
                 String mx = objMX.toString();
                 if(!mx.equals("1")){
                     nomxDataSet.addRowSet(rowSet);
                     isShowTree = true;
                 }
             }
          }

//          if(!isMX.equals("1")){
//                nomxDataSet.addRowSet(rowSet);
//           }
          //modified end


          TreeInfoContext exphic = ESPHelpUtil.getTreeInfoFormDO(dmd);
           //modified by lchong at 2011-12-18
//          exphic.setDataObject(eds);
          exphic.setDataObject(filterDataSet);
          //modified end
//          exphic.setDataObject(nomxDataSet);
          EFDataSetTreeModel est = (EFDataSetTreeModel) dmd.getObject(dmd.
              getObjID() + "_HLPTREE", null);
          if (est == null||bReSet) {
            est = EFDataSetTreeModel.getInstance(exphic);
            dmd.putObject(dmd.getObjID() + "_HLPTREE", est);
          }
          if (tree.getModel() != est ) {

            tree.setModel(est);
//              tree.setModel(nomxDataSet);

          }
        }finally{
            tree.setVisible(true);
        }
    }

    /**
     *
     * @param hic HelpInfoContext
     * @param dmd DCTMetaData
     * @param eds EFDataSet
     * @param isFjcx boolean
     * @param bReSet boolean
     */
    public void setExploreData(HelpInfoContext hic,DCTMetaData dmd,EFDataSet eds,EFDataSet addDs,DataSetTreeNode selNode, boolean bReSet){
        this.dmd=dmd;
        try{
          tree.setVisible(false);

          EFDataSet filterDataSet = null;
          if(hic != null){
              Object o=hic.getFilterObject();
              if(o !=null&& o instanceof com.efounder.mdm.IMainDataFilter){
                  filterDataSet = ((com.efounder.mdm.IMainDataFilter) o).filterMainData(eds);
              }else{
                  filterDataSet = eds;
              }
          }else{
              filterDataSet = eds;
          }

          TreeInfoContext exphic = ESPHelpUtil.getTreeInfoFormDO(dmd);
          exphic.setDataObject(filterDataSet);
          EFDataSetTreeModel est = (EFDataSetTreeModel) dmd.getObject(dmd.
              getObjID() + "_HLPTREE", null);
          if (est == null || bReSet) {
              est = EFDataSetTreeModel.getInstance(exphic);
          }else{
              est.setDataSet(filterDataSet);
              selNode.removeAllChildren();
              for(int i=0; i<addDs.getRowCount(); i++){
                  EFRowSet rs = addDs.getRowSet(i);
                  est.setCurrentTreeNode(rs);
              }
              est.reload(selNode);
          }

          dmd.putObject(dmd.getObjID() + "_HLPTREE", est);
          if (tree.getModel() != est ) {

            tree.setModel(est);

          }

        }finally{
            tree.setVisible(true);
        }
    }


}
