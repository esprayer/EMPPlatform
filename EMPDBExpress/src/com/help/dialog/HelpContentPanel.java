package com.help.dialog;

import java.awt.*;
import javax.swing.*;
import com.borland.dbswing.*;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.builder.base.data.*;
import com.efounder.builder.meta.domodel.SYS_OBJCOLS;
import com.efounder.bz.dbform.component.dc.table.corner.TableCorner;
import com.efounder.bz.dbform.component.dc.table.header.TableGroupColumnHeader;

import java.awt.event.*;
import com.efounder.eai.*;
import com.efounder.mdm.DataSetFilterUtils;

import java.util.*;
import java.math.*;
import org.openide.*;
import javax.swing.table.TableColumn;

import com.efounder.dbc.swing.render.EnumCellRenderer;
import com.efounder.dbc.swing.render.NameCellRenderer;
import com.efounder.dctbuilder.data.ColumnMetaData;
import com.help.HelpInfoContext;
import com.help.data.HelpTableModel;
import com.pub.util.StringParamUtil;

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
public class HelpContentPanel extends JPanel implements KeyListener,MouseListener{
    BorderLayout borderLayout1 = new BorderLayout();
    JScrollPane tsp=new JScrollPane();
    TableRowHeader rowHeader = null;
    JTable hlpTable=new JTable();
    public HelpContentPanel() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public JTable getContentTable(){
        return hlpTable;
    }
    public Object getResult(){
        int sel=hlpTable.getSelectedRow();
        if(sel==-1)return null;
        int sels[]= hlpTable.getSelectedRows();
       EFRowSet erss[]=new EFRowSet[sels.length];
       for(int i=0;sels!=null&&i<sels.length;i++){
           EFRowSet ers= (EFRowSet) ((HelpTableModel)hlpTable.getModel()).getRowData(sels[i]);
           erss[i]=ers;
       }
        return erss;
    }
    public void locateCode(String txt) {
        HelpTableModel  htm=(HelpTableModel)hlpTable.getModel();
        EFDataSet eds=htm.getDispDataSet();
        int count=eds.getRowCount();
        int row=hlpTable.getSelectedRow();
        row=row+1;
        if(row>=hlpTable.getRowCount())row=0;
        for(int i=row;i<count;i++){
            EFRowSet ers=eds.getRowSet(i);
            String bh=(String)htm.getValueAt(i,0);
            String mc=(String)htm.getValueAt(i,1);
            if(bh.startsWith(txt)||mc.indexOf(txt)!=-1){
                hlpTable.setRowSelectionInterval(i,i);
                Rectangle rc=hlpTable.getCellRect(i,0,true);
                hlpTable.scrollRectToVisible(rc);
//                if(!tsp.getViewport().getVisibleRect().contains(rc))
//                    tsp.getViewport().setViewPosition(rc.getLocation());
                return;
            }
        }
        for(int i=0;i<row;i++){
                    EFRowSet ers=eds.getRowSet(i);
                    String bh=(String)htm.getValueAt(i,0);
                    String mc=(String)htm.getValueAt(i,1);
                    if(bh.startsWith(txt)||mc.indexOf(txt)!=-1){
                        hlpTable.setRowSelectionInterval(i,i);
                        Rectangle rc=hlpTable.getCellRect(i,0,true);
                        hlpTable.scrollRectToVisible(rc);
//                if(!tsp.getViewport().getVisibleRect().contains(rc))
//                    tsp.getViewport().setViewPosition(rc.getLocation());
                        return;
                    }
        }
    }
    public void setFirstLocate(HelpInfoContext hic,DCTMetaData dmd,EFDataSet dataList){
        String selValue=hic.getSelValue();
        if(selValue==null)return;
        if(dataList==null)return;
        EFRowSet ers=(EFRowSet) dataList.getRowSet(selValue);
        if(ers==null)return;
        int index=dataList.getRowSetList().indexOf(ers);
        if(index==-1)return;
         hlpTable.setRowSelectionInterval(index,index);
         Rectangle rc=hlpTable.getCellRect(index,0,true);
         tsp.getViewport().setViewPosition(rc.getLocation());
    }
    public void setContentData(HelpInfoContext hic,DCTMetaData dmd,EFDataSet dataList){
        HelpTableModel htm;
        try{
            if (hic.getFilterExp() != null && !"".equals(hic.getFilterExp()))
                dataList = DataSetFilterUtils.filterDataSet(dataList, hic.getFilterExp(),hic.getFilterNullCols());
        }catch(Exception e){
            e.printStackTrace();
        }
        Object o=hic.getFilterObject();
        if(o !=null&& o instanceof com.efounder.mdm.IMainDataFilter)
            dataList=((com.efounder.mdm.IMainDataFilter)o).filterMainData(dataList);
        boolean bfirst=false;
        if(hlpTable.getModel() instanceof HelpTableModel)
            htm=(HelpTableModel)hlpTable.getModel();
        else{
            hlpTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            htm = HelpTableModel.getInstance(dmd, hic.getHelpColumn());
            hlpTable.setModel(htm);
            java.util.List list=htm.getColumnList();
            if(list.size()<=2){
                hlpTable.getColumnModel().getColumn(0).setPreferredWidth(155);
                hlpTable.getColumnModel().getColumn(1).setPreferredWidth(490);
            }else{
                hlpTable.getColumnModel().getColumn(0).setPreferredWidth(115);
                hlpTable.getColumnModel().getColumn(1).setPreferredWidth(170);//300
                for (int i = 2; i < list.size(); i++) {
                    EFRowSet ers = (EFRowSet) list.get(i);
                    int wid = Integer.parseInt(ers.getString("COL_PREC", "50"));
//                    if (wid < 150)  wid = 135;
                    if (wid < 150)  wid = 100;

                    hlpTable.getColumnModel().getColumn(i).setPreferredWidth(wid);
                    createColRender(hlpTable.getColumnModel().getColumn(i),ers);

                }
            }
            bfirst=true;
//            java.util.List colList=htm.getColumnList();
//            for(int i=0;i<colList.size();i++){
//                EFRowSet ers=(EFRowSet)colList.get(i);
//                int len =Integer.parseInt(ers.getObject("COL_PREC","0").toString());
//                hlpTable.getColumnModel().getColumn(i).setPreferredWidth(len);
//            }
        }
        try{
        	//是否进行使用状态的过滤
            String noFilter = (String) hic.getObject("noFilter", "0");
            Object  staucol = dmd.getDoMetaData().getColumnDefineRow("F_SYZT");
            if (staucol != null && !"1".equals(noFilter)) {
                EFDataSet tmpds = DataSetFilterUtils.filterDataSet(dataList, "F_SYZT.equals(\"1\")");
                if(tmpds!=null)dataList=tmpds;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        htm.setAllDataSet(dataList);
        htm.setDispDataSet(dataList);
        if(bfirst)
            setFirstLocate(hic,dmd,dataList);
        rowHeader.revalidate();
        rowHeader.repaint();
        hlpTable.revalidate();
        hlpTable.repaint();
        if(dataList.getRowCount()>0&&hlpTable.getSelectedRow()==-1)
            hlpTable.setRowSelectionInterval(0,0);

    }

    //add by lchong at 2011-12-16
    protected void createColRender(TableColumn column,EFRowSet colDefRowSet){
        String isFKey = colDefRowSet.getString("COL_ISFKEY","0");
        if("1".equals(isFKey)){
            String fKeyObj = colDefRowSet.getString("COL_FOBJ","");
            if(fKeyObj.trim().length() == 0) return;
            NameCellRenderer fKeyCellRenderer = new NameCellRenderer(fKeyObj);
            column.setCellRenderer(fKeyCellRenderer);
            return;
        }

        String editType = colDefRowSet.getString("COL_EDIT","0");
        int iEditType = Integer.parseInt(editType);
        if(ColumnMetaData.EL_POPUP==iEditType){//枚举
            String colView = colDefRowSet.getString("COL_VIEW","");
            java.util.Map map =   StringParamUtil.getMapFromStr(colView,";",":");
            EnumCellRenderer enumCellRenderer = new EnumCellRenderer(map);
            column.setCellRenderer(enumCellRenderer);
            return;
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.add(tsp,BorderLayout.CENTER);
        rowHeader = new TableRowHeader(hlpTable);
        tsp.setRowHeaderView(rowHeader);
        tsp.getViewport().add(hlpTable);
        hlpTable.addKeyListener(this);
        hlpTable.setRowHeight(20);
        TableGroupColumnHeader tgc = new TableGroupColumnHeader(hlpTable.
                getColumnModel());
        hlpTable.setTableHeader(tgc);
        hlpTable.getTableHeader().addMouseListener(this);
        hlpTable.addMouseListener(this);
       TableCorner tc=new TableCorner(null);
       tc.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        tsp.setCorner(JScrollPane.UPPER_LEFT_CORNER,tc);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
      if(e.getKeyCode()==KeyEvent.VK_ENTER)sendAction("DBCLICK");
      if(e.getKeyCode()==KeyEvent.VK_TAB)sendAction("TAB");
    }
    protected void sendAction(String key){
      Component comp = getParent();
      while (comp != null) {
        if (comp instanceof ActionListener)
          ( (ActionListener) comp).actionPerformed(new ActionEvent(this, 0,
              key));
        comp = comp.getParent();
      }

    }
    public void keyReleased(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        int tableColumn;
        if (e.getSource() == hlpTable.getTableHeader() &&
            (tableColumn = hlpTable.getTableHeader().columnAtPoint(e.getPoint())) != -1) {
            WaitingManager.getDefault().beginWait(this);
            try {
                int mc = hlpTable.convertColumnIndexToModel(tableColumn);
                HelpTableModel htm = (HelpTableModel) hlpTable.getModel();
                EFDataSet list = htm.getDispDataSet();
                 java.util.Collections.sort(list.getRowSetList(),new sortData(htm,mc));
                 hlpTable.revalidate();
                 hlpTable.repaint();
            } finally {
                WaitingManager.getDefault().endWait(this);
            }
        }
        if(e.getSource()==hlpTable&&e.getClickCount()==2&&e.getButton()==e.BUTTON1){
            if(hlpTable.getSelectedRow()!=-1)sendAction("DBCLICK");
        }

    }

    protected void FilterCodeOrName(String txt) {
        HelpTableModel htm = (HelpTableModel) hlpTable.getModel();
        java.util.List colList = htm.getColumnList();
        EFDataSet dataList = htm.getAllDataSet();
        EFDataSet fd=dataList;
        if(!txt.equals("")){
             fd = EFDataSet.getInstance(dataList.getTableName());
            fd.setPrimeKey(dataList.getPrimeKey());
            String bmcol = htm.getCodeColumn();
            String mccol = htm.getNameColumn();
            for (int i = 0; i < dataList.getRowCount(); i++) {
                EFRowSet dataers = dataList.getRowSet(i);
                if (dataers.getString(bmcol, "").indexOf(txt) != -1 ||
                    dataers.getString(mccol, "").indexOf(txt) != -1)
                    fd.insertRowSet(dataers);
            }
        }
        htm.setDispDataSet(fd);
        hlpTable.setVisible(false);
        hlpTable.setVisible(true);
    }

    class sortData implements Comparator{
        String colid;
        boolean bAesc=false;
        public sortData(HelpTableModel htm,int col){
            EFRowSet ers=(EFRowSet) htm.getColumnList().get(col);
            colid=ers.getString(SYS_OBJCOLS._COL_ID_,"");
            bAesc=ers.getBoolean("SORT_TYPE",Boolean.FALSE).booleanValue();
            ers.putBoolean("SORT_TYPE",Boolean.valueOf(!bAesc));
        }
        public int compare(Object o1, Object o2) {
            int aa=compare1(o1,o2);
            aa=aa*(bAesc?1:-1);
            return aa;
        }
        protected int compare1(Object o1, Object o2) {
            EFRowSet r1=(EFRowSet)o1;
            EFRowSet r2=(EFRowSet)o2;
            Object oo1=r1.getObject(colid,null);
            Object oo2=r2.getObject(colid,null);
            if(oo1 instanceof BigDecimal){
                return ((BigDecimal)oo1).compareTo((BigDecimal)oo2);
            }
            return oo1.toString().compareTo(oo2.toString());
        }

        public boolean equals(Object obj) {
            return false;
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
