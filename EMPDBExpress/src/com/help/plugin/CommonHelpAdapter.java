package com.help.plugin;

import javax.swing.*;
import javax.swing.tree.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.dbc.swing.tree.DataSetTreeNode;
import com.efounder.dbc.swing.treetable.TreeTableUtils;
import com.efounder.mdm.DataSetFilterUtils;
import com.efounder.mdm.EFMDMDataModel;
import com.help.HelpInfoContext;
import com.help.dialog.HelpExplorePanel;
import com.help.dialog.HelpTabPanel;
import com.help.util.ESPHelpUtil;

import java.util.Map;

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
public class CommonHelpAdapter extends IHelpAdapter {
    public CommonHelpAdapter() {
    }
     protected void initExplorer(DCTMetaData dmd, HelpExplorePanel hep) {
         hep.top.removeAll();
     }

     public JPanel createHelpTabPanel(DCTMetaData dmd,HelpInfoContext hic) {
         super.createHelpTabPanel(dmd,hic);
         HelpTabPanel htp = new HelpTabPanel(this);
         if(dmd.getDCT_JSCOLID().trim().length()==0||dmd.getString(SYS_DICTS._DCT_BMSTRU_,"").trim().length()==0)
             htp.setNoExplorer();
         initExplorer(dmd,htp.getExplorePanel());
         return htp;
     }
     
     public boolean listChild(HelpTabPanel htp,EFRowSet value){
         if (dmd.getDCT_JSCOLID().trim().length() == 0 ||
              dmd.getString(SYS_DICTS._DCT_BMSTRU_, "").trim().length() == 0)return false;
         String mxcol=dmd.getDCT_MXCOLID();
         String bhcol=dmd.getDCT_BMCOLID();
         String mx=value.getString(mxcol,"1");
         if("1".equals(mx))return false;
//         TreePath tp=TreeTableUtils.findValue(htp.getExplorePanel().getExploreTree(),value.getString(bhcol,""),true);
         return true;
     }
     public void loadExploreData(HelpTabPanel htp){
          EFDataSet eds=null;
          if(hic.getDataObject()!=null)
              eds=(EFDataSet)hic.getDataObject();
          else{
              EFMDMDataModel edm = ESPHelpUtil.loadHelpData(PO,dmd, htp.isRefrensh());
              eds = edm.getDataSet(dmd.getObjID());
          }
          if (dmd.getDCT_JSCOLID().trim().length() == 0 ||
              dmd.getString(SYS_DICTS._DCT_BMSTRU_, "").trim().length() == 0){
              //add by lchong at 2011-8-9
              eds = filterData(eds);
              //add by lchong
              htp.getContentPanel().setContentData(hic, dmd, eds);
              htp.getFilterPanel().setContentTable(htp.getContentPanel().getContentTable());
          }
          else{
              eds = filterData(eds);
//            try{
//              if (hic.getFilterExp() != null && !"".equals(hic.getFilterExp()))
//              {
//            	  eds = DataSetFilterUtils.filterDataSet(eds, hic.getFilterExp(),hic.getFilterNullCols());
//              }
//            }
//            catch (Exception e) {
//              e.printStackTrace();
//            }
              eds=filterExplore(0,null,eds);
              //modified by lchong at 2011-12-18:把上下文变量传递过去
//              htp.getExplorePanel().setExploreData(dmd, eds,htp.isRefrensh());
              htp.getExplorePanel().setExploreData(hic,dmd, eds,htp.isRefrensh());
              //modified end

              //add by lchong at 2011-4-21：如果左边的树都是明细数据，则隐藏树
              boolean isShowTree = htp.getExplorePanel().isIsShowTree();
              if(!isShowTree){
                  htp.setNoExplorer();
                  htp.setSplitLocation(0);
              }
              //add end
              if (htp.isFirstRun()||htp.isRefrensh()) {
                  locateExplore(htp);
                  if (htp.getExplorePanel().getExploreTree().getSelectionCount() == 0)
                      htp.getExplorePanel().getExploreTree().setSelectionInterval(0,0);
              }
          }
    }
     
     protected void locateExplore(HelpTabPanel htp) {
         String value = hic.getSelValue();
         if (value == null)
             return;
         EFDataSet eds = null;
         if (hic.getDataObject() != null)
             eds = (EFDataSet) hic.getDataObject();
         else {
             EFMDMDataModel edm = ESPHelpUtil.loadHelpData(PO,dmd, htp.isRefrensh());
             eds = edm.getDataSet(dmd.getObjID());
         }
         EFRowSet ers=(EFRowSet) eds.getRowSet(value);
         if(ers==null)return;
         while(value.trim().length()>0){
             value=value.substring(0,value.length()-1);
              ers=(EFRowSet) eds.getRowSet(value);
              if(ers!=null){
                  TreePath tp=TreeTableUtils.findValue(htp.getExplorePanel().getExploreTree(),value,true);
                  break;
              }
         }
     }
    /**
     * add by lchong
     * @param srcDataSet EFDataSet
     * @return EFDataSet
     */
    protected EFDataSet filterData(EFDataSet srcDataSet){
        try{
            if (hic.getFilterExp() != null && !"".equals(hic.getFilterExp())) {
                  return DataSetFilterUtils.filterDataSet(srcDataSet, hic.getFilterExp(),hic.getFilterNullCols());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return srcDataSet;
    }


    public EFDataSet getFilterData(HelpTabPanel htp) {
        TreePath tp = htp.getExplorePanel().getExploreTree().getSelectionPath();
        if(tp==null)return null;
        DataSetTreeNode node=(DataSetTreeNode) tp.getLastPathComponent();
        EFDataSet dataeds = null;
        if (hic.getDataObject() != null)
            dataeds = (EFDataSet) hic.getDataObject();
        else {
            EFMDMDataModel edm = ESPHelpUtil.loadHelpData(PO,dmd, htp.isRefrensh());
            dataeds = edm.getDataSet(dmd.getObjID());
        }
        if(dataeds==null)return null;
        EFDataSet eds = EFDataSet.getInstance(dmd.getObjID());
//        eds.setPrimeKey(new String[] {dmd.getDCT_BMCOLID()});
//        int count = node.getChildCount();
//        for (int i = 0; i < count; i++) {
//            DataSetTreeNode dst = (DataSetTreeNode) node.getChildAt(i);
//            EFRowSet ers = (EFRowSet) dataeds.getRowSet(new String[] {dst.getDctBH()});
//            if (ers != null) eds.insertRowSet(ers);
//        }
        String bhcol = dmd.getDCT_BMCOLID();
        String[] key = dmd.getDoMetaData().getKEYColumnIDs();
        eds.setPrimeKey(key);

        for (int i = 0; i < node.getChildCount(); i++) {
            DataSetTreeNode dst = (DataSetTreeNode) node.getChildAt(i);
            setFilterData(dst.getDctBH(), eds, dataeds, (EFRowSet)dst.getUserObject());
//            EFRowSet ers = (EFRowSet) dataeds.getRowSet(new String[] {dst.getDctBH()});
//            if (ers != null) eds.insertRowSet(ers);
        }

        return eds;
     }

     private void setFilterData(String bhval, EFDataSet eds, EFDataSet dataeds, EFRowSet userObject){
         String[] key = dmd.getDoMetaData().getKEYColumnIDs();
         for (int i = 0; i < dataeds.getRowCount(); i++) {
             EFRowSet rowSet = dataeds.getRowSet(i);
             // 如果key是多个字段，则判断和userObject是否一致
             if (key != null && key.length > 1 && userObject != null) {
                 if (isEqual(rowSet, userObject, key))
                     eds.insertRowSet(rowSet);
                 else
                     continue;
             } else if (rowSet != null && rowSet.getValue(dmd.getDCT_BMCOLID(), "").equals(bhval)){
                 eds.insertRowSet(rowSet);
                 break;
             }
         }
     }

    /**
     *
     * @param rowSet EFRowSet
     * @param userObject EFRowSet
     * @param key String[]
     * @return boolean
     */
    private boolean isEqual(EFRowSet rowSet, EFRowSet userObject, String[] key) {
        for (int i = 0; key != null && i < key.length; i++) {
            if (!rowSet.getString(key[i],"").equals(userObject.getString(key[i], "")))
                return false;
        }
        return true;
    }

}
