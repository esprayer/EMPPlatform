package com.help.plugin;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.data.*;
import com.help.HelpInfoContext;
import com.help.dialog.HelpTabPanel;
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
public abstract class IHelpAdapter implements MouseListener,ActionListener{
    protected DCTMetaData dmd;
    protected HelpInfoContext hic;
    protected JParamObject PO=null;
    public IHelpAdapter(){

    }

    public JPanel createHelpTabPanel(DCTMetaData dmd, HelpInfoContext hic) {
        this.dmd = dmd;
        this.hic = hic;
        PO = JParamObject.Create();
        PO.SetValueByParamName("MDL_ID", hic.getModelID());
        // 利用上ServerName
        PO.setEAIServer(hic.getServerName());
        // 字典的SQL条件
        PO.SetValueByParamName(hic.getDCTID() + "_MDMSelfWhere", hic.getWhereClause());
        //增加表后缀 for daqing add by wujf at 20120717
        PO.SetValueByParamName("DCT_SUFFIX", hic.getString("DCT_SUFFIX", "")); 
        // UNIT_ID
        String UNIT_ID = hic.getUNIT_ID();
        if (UNIT_ID != null && UNIT_ID.trim().length() > 0) PO.SetValueByEnvName("UNIT_ID", hic.getUNIT_ID());
        // 组织机构编码
        String MDL_ID = hic.getModelID();
        String ZZJG_DCT = null;
//        if (MDL_ID != null && MDL_ID.trim().length() > 0)
//        	ZZJG_DCT = ESPHelpUtil.getZZJG_DCT(PO, MDL_ID);
//        if (ZZJG_DCT != null){
//            PO.SetValueByParamName(ZZJG_DCT, hic.getZZJG_CODE());
//            //--modify by zhangft 20130106 使用自己的核算主体过滤数据--
//            if (hic.isUseSelfZZJG_CODE()){
//	            PO.SetValueByEnvName(ZZJG_DCT, hic.getZZJG_CODE());
//            }
//            //------------------------------------------------------
//        }
        return null;
    }

    public void loadExploreData(HelpTabPanel htp){
    }
    public abstract EFDataSet getFilterData(HelpTabPanel htp);
    public void loadContentData(HelpTabPanel htp){
        TreePath tp= htp.getExplorePanel().getExploreTree().getSelectionPath();
        if (tp == null)
            return ;
        boolean isOpen = htp.getExplorePanel().getExploreTree().isExpanded(tp);
        if (!isOpen) {
            htp.getExplorePanel().getExploreTree().expandPath(tp);
        }
        EFDataSet list=getFilterData(htp);
        if(list==null)list=EFDataSet.getInstance(dmd.getObjID());
        htp.getContentPanel().setContentData(hic,dmd, list);
        htp.getFilterPanel().setContentTable(htp.getContentPanel().getContentTable());
        if(list.getRowCount()==0)htp.getExplorePanel().getExploreTree().requestFocus();
    }
    public void mouseClicked(MouseEvent e) {
        if ((e.getModifiers() & e.BUTTON3_MASK) != 0) {
            JTable table = (JTable) e.getSource();
            JPopupMenu menu = new JPopupMenu();
            JMenuItem item = new JMenuItem("发送至收藏夹");
            item.putClientProperty("SOURCE", table);
            item.setIcon(ExplorerIcons.ICON_ADD_CUSTOM_VIEW);
            menu.add(item);
            item.addActionListener(this);
            menu.show((java.awt.Component) e.getSource(), e.getPoint().x,
                      e.getPoint().y);
        }
    }

    public void actionPerformed(ActionEvent e){
        //add by lchong
        String type = (String)hic.getObject("TYPE",null);
        //add end
        JMenuItem item=(JMenuItem) e.getSource();
        JTable table=(JTable)item.getClientProperty("SOURCE");
        item.putClientProperty("SOURCE",null);
//        List list=ESPHelpUtil.load(dmd.getObjID());
        //add by lchong
       List list=ESPHelpUtil.load(dmd.getObjID(),type);
       //add end
       int rows[]=table.getSelectedRows();
       for(int i=0;i<rows.length;i++){
           String bh=(String)table.getValueAt(rows[i],0);
           if(!list.contains(bh))list.add(bh);
       }
       //add by lchong
//       ESPHelpUtil.save(dmd.getObjID(),list);
       ESPHelpUtil.save(dmd.getObjID(),type,list);
       //add end
    }
    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }
    public EFDataSet filterExplore(int type,EFRowSet typeers,EFDataSet ds){
//        Object o=hic.getFilterObject();
//        if(o !=null&& o instanceof IMainDataFilter)
//            ds=((IMainDataFilter)o).filterExploreData(type,typeers,ds);
        return ds;
    }
    public void mouseExited(MouseEvent e) {

    }

}
