package com.help.dialog;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import com.efounder.builder.meta.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.dbc.swing.tree.DataSetTreeNode;
import com.efounder.eai.ide.*;
import com.efounder.pfc.dialog.*;
import com.efounder.pfc.window.IWindowCustom;
import com.efounder.builder.base.data.*;
import com.core.xml.*;
import java.util.List;
import com.efounder.builder.meta.domodel.DOMetaData;
import com.efounder.builder.meta.domodel.SYS_OBJCOLS;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.tree.*;
import com.efounder.builder.base.util.ESPClientContext;
import com.efounder.eai.data.JParamObject;
import java.util.Vector;
import com.efounder.eai.EAI;
import com.help.HelpInfoContext;
import com.help.plugin.CommonHelpAdapter;
import com.help.util.ESPHelpUtil;

import java.util.ArrayList;

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
public class HelpMainDialog extends JDialog implements
        ActionListener,ChangeListener,KeyListener,DocumentListener{
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout fl1=new FlowLayout();
    JPanel oppnel=new JPanel();
    JButton btReLoad=new JButton("刷新");
    JButton btFilter=new JButton("过滤");
    JButton btOk=new JButton("选择");
    JButton btCancel=new JButton("退出");
    TitledBorder titledBorder1 = new TitledBorder("");
    Object selData;
    protected JTabbedPane hlptabs = new JTabbedPane();
    JTextField jtfFind = new JTextField();
    JPanel jPanel1 = new JPanel();
    BorderLayout borderLayout2 = new BorderLayout();
    JPanel jPanel2 = new JPanel();
    BorderLayout borderLayout3 = new BorderLayout();
    JPanel jPanel3 = new JPanel();
    public HelpMainDialog(Frame frame) {
        super(frame, "帮助窗口", true);
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public HelpMainDialog(Dialog frame) {
       super(frame, "帮助窗口", true);
       try {
           jbInit();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }

    private void jbInit() throws Exception {
        getContentPane().setLayout(borderLayout1);
        btOk.setPreferredSize(new Dimension(51, 25));
        btOk.setMargin(new Insets(0, 0, 0, 0));
        btCancel.setPreferredSize(new Dimension(51, 25));
        btCancel.setMargin(new Insets(0, 0, 0, 0));
        fl1.setAlignment(FlowLayout.RIGHT);
        fl1.setHgap(10);
        fl1.setVgap(2);
        oppnel.setLayout(fl1);
        jPanel1.setLayout(borderLayout2);
        jtfFind.setPreferredSize(new Dimension(6, 25));
        btReLoad.setPreferredSize(new Dimension(51, 25));
        btReLoad.setMargin(new Insets(0, 0, 0, 0));
        btFilter.setPreferredSize(new Dimension(61, 25));
        btFilter.setMargin(new Insets(0, 0, 0, 0));

        jPanel2.setLayout(borderLayout3);
        borderLayout3.setVgap(2);
        jPanel3.setMinimumSize(new Dimension(10, 2));
        jPanel3.setPreferredSize(new Dimension(10, 2));
        oppnel.add(btReLoad);
        oppnel.add(btFilter);
        oppnel.add(btOk);
        oppnel.add(btCancel);
        this.getContentPane().add(hlptabs, java.awt.BorderLayout.CENTER);
        jPanel1.add(oppnel, java.awt.BorderLayout.EAST);
        this.getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);
        jPanel2.add(jtfFind, java.awt.BorderLayout.CENTER);
        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);
        btOk.setActionCommand("OK");
        btCancel.setActionCommand("CANCEL");
        btReLoad.setIcon(ExplorerIcons.ICON_REFRESH);
        btFilter.setIcon(ExplorerIcons.getExplorerIcon("idea/debugger/class_filter.png"));
        btOk.setIcon(ExplorerIcons.getExplorerIcon("beeload/ok.gif"));
        btCancel.setIcon(ExplorerIcons.getExplorerIcon("beeload/exit.gif"));
        btOk.addActionListener(this);
        btCancel.addActionListener(this);
        btReLoad.addActionListener(this);
        btFilter.addActionListener(this);
        jtfFind.getDocument().addDocumentListener(this);
        jtfFind.addKeyListener(this);
    }
    public void initHelpObject(HelpInfoContext hic) {
        try{
            String dctid=hic.getDCTID();
            String selvalue=hic.getSelValue();
            DCTMetaData dmd=null;

            // 根据元数据定义确定是否分级查询
            try {
                JParamObject PO = JParamObject.Create();
                PO.setEAIServer(hic.getServerName());
                ESPClientContext ctx = ESPClientContext.getInstance(PO);
                dmd = (DCTMetaData) MetaDataManager.getDCTDataManager().getMetaData(ctx, dctid);
                hic.setFJCX("1".equals(dmd.getExtPropertyValue("FJCX", "")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            if(hic.getDataObject()==null){
//                //利用上HIC中的ServerName
//                JParamObject PO = JParamObject.Create();
//                PO.setEAIServer(hic.getServerName());
//                ESPClientContext ctx = ESPClientContext.getInstance(PO);
//                dmd = (DCTMetaData) MetaDataManager.getDCTDataManager().getMetaData(ctx, dctid);
            }else{
            	dmd=DCTMetaData.getInstance(dctid);
            	dmd.putString(SYS_DICTS._DCT_BMCOLID_,hic.getBHColumn());
            	dmd.putString(SYS_DICTS._DCT_MCCOLID_, hic.getMCColumn());
            	dmd.putString(SYS_DICTS._DCT_JSCOLID_, hic.getJSColumn());
            	dmd.putString(SYS_DICTS._DCT_MXCOLID_, hic.getMXColumn());
            	dmd.putString(SYS_DICTS._DCT_BMSTRU_,hic.getBMStru());
            	DOMetaData dodmd=DOMetaData.getInstance(dctid);
            	dmd.putObject("mainDoMetaData",dodmd);
            	EFDataSet eds=EFDataSet.getInstance("");
            	eds.setPrimeKey(new String[]{SYS_OBJCOLS._COL_ID_});
            	String[]bhs=hic.getHelpColumn();
            	String[]mcs=hic.getHelpCapiton();
            	for(int i=0;i<bhs.length;i++){
            		EFRowSet ers = EFRowSet.getInstance();
            		ers.putString(SYS_OBJCOLS._COL_ID_, bhs[i]);
            		ers.putString(SYS_OBJCOLS._COL_MC_, mcs[i]);
            		eds.addRowSet(ers);
            	}
            	dodmd.setDataSet(SYS_OBJCOLS._SYS_OBJCOLS_,eds);
            }
            hlptabs.addChangeListener(this);
            ESPHelpUtil.loadHelpTabPlugin(hlptabs, dmd,hic);
            int count=hlptabs.getTabCount();
            for(int i=0;i<count;i++){
            	HelpTabPanel htp=(HelpTabPanel)hlptabs.getComponentAt(i);
            	htp.getContentPanel().getContentTable().addKeyListener(this);
            }
            hlptabs.addKeyListener(this);

            //add by lchong
            String type = (String)hic.getObject("TYPE",null);
            //add end
         
            //zhtbin 个性化先显示分组、分类、普通等
            StubObject featureSo = ESPHelpUtil.getFeatureHelp(dmd.getObjID());
            List list=ESPHelpUtil.load(dmd.getObjID(),type);
            //zhangES 如果收藏夹有内容，先展示收藏夹
            if(list.size()>0) {//收藏夹先展示收藏夹
            	hlptabs.setSelectedIndex(count - 1);
        	} else {
        		if(featureSo != null){
        			String indexStr = featureSo.getString("showIndex", "");
	           	 	hlptabs.setSelectedIndex(Integer.valueOf(indexStr));
	           	 	if("0".equals(indexStr)){//如果是分组,设置选中分组
	           	 		hic.setString("SEL_TYPE", featureSo.getString("rlgl", ""));
	           	 	}
        		}else{
//        			List list=ESPHelpUtil.load(dmd.getObjID());        	
        			if(list.size()>0)//收藏夹先展示收藏夹
        				hlptabs.setSelectedIndex(count - 1);
        			else {
        				if (count >= 2)
        					hlptabs.setSelectedIndex(count - 2); //展示普通
        			}
        		}
        	}
         
            HelpTabPanel htp = (HelpTabPanel) hlptabs.getSelectedComponent();
            String where = hic.getWhereClause();
            //记录主数据上次的where条件，如果不一致自动刷新
            String oldWhere = (String) EAI.getEnv("HELP_WHERE_" + dctid, null);
            if (where == null) where = ""; 
            if (oldWhere == null) oldWhere = "";
            if (!where.equals(oldWhere) && htp != null)
            	htp.refrenshData();
            EAI.putEnv("HELP_WHERE_" + dctid, where);
      }catch(Exception e){
            e.printStackTrace();
        }
    }
//    public void CenterWindow() {
//        super.CenterWindow();
//    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("CANCEL"))
            OnCancel();
        if(e.getActionCommand().equals("OK")||e.getActionCommand().equals("DBCLICK")){
            HelpTabPanel htp=(HelpTabPanel)hlptabs.getSelectedComponent();
            if(htp != null && htp.getContentPanel() != null){
                selData = htp.getContentPanel().getResult();
//                // 考虑树形结构
//                if (selData == null && (htp.adapter instanceof CommonHelpAdapter || htp.adapter instanceof FJCXHelpAdapter)) {
//                    List<EFRowSet> list = new ArrayList<EFRowSet>();
//                    int[] rows = htp.getExplorePanel().tree.getSelectionRows();
//                    for (int i = 0; i < rows.length; i++) {
//                        TreePath path = htp.getExplorePanel().tree.getPathForRow(rows[i]);
//                        if (path != null) {
//                            DataSetTreeNode dstn = (DataSetTreeNode) path.getLastPathComponent();
//                            if (dstn != null)
//                                list.add((EFRowSet) dstn.getUserObject());
//                        }
//                    }
//                    EFRowSet[] rowSets = new EFRowSet[list.size()];
//                    System.arraycopy(list.toArray(), 0, rowSets, 0, rowSets.length);
//                    selData = rowSets;
                /**} else */if(((Object[])selData).length==1&&htp.adapter instanceof CommonHelpAdapter){
                    if(((EFRowSet[])selData)[0]==null){
                        TreePath tp = htp.getExplorePanel().tree.getSelectionPath();
                        if (tp != null) {
                            DataSetTreeNode node = (DataSetTreeNode) tp.getLastPathComponent();
                            selData = node.getUserObject();
                        }
                    } else if(e.getActionCommand().equals("DBCLICK")){
                       boolean b=((CommonHelpAdapter)htp.adapter).listChild(htp,((EFRowSet[])selData)[0]);
                       if(b)return;
                   }
                }
            }
            OnOk();
        }
         if(e.getActionCommand().equals("TAB")){
             int row=hlptabs.getSelectedIndex();
             row+=1;
             if(row==hlptabs.getTabCount())row=0;
             hlptabs.setSelectedIndex(row);
         }
         if(e.getSource()==btReLoad){
             HelpTabPanel htp=(HelpTabPanel)hlptabs.getSelectedComponent();
             htp.refrenshData();
         }
         if(e.getSource()==btFilter){
             JPopupMenu menu=new JPopupMenu();
             JMenuItem mi = new JMenuItem("过滤");
             mi.setActionCommand("FILTER");
             mi.addActionListener(this);
             menu.add(mi);
             mi = new JMenuItem("显示全部");
             mi.setActionCommand("ALL");
             mi.addActionListener(this);
             menu.add(mi);
             menu.show(btFilter, 0, btFilter.getHeight());

         }
         if ("FILTER".equals(e.getActionCommand())) {
             HelpTabPanel htp = (HelpTabPanel) hlptabs.getSelectedComponent();
             htp.getContentPanel().FilterCodeOrName(jtfFind.getText());
         }
         if ("ALL".equals(e.getActionCommand())) {
             HelpTabPanel htp = (HelpTabPanel) hlptabs.getSelectedComponent();
             htp.getContentPanel().FilterCodeOrName("");
         }
    }

    public Object getResult() {
        return selData;
    }

    public StubObject getResultStub() {
        Object oo=getResult();
        EFRowSet ers=null;
        if(oo instanceof EFRowSet[])
            ers=((EFRowSet[])oo)[0];
        else
            ers=(EFRowSet)oo;
        if(ers==null)return null;
        StubObject so=new StubObject();
        so.setString("SB","");
        so.getStubTable().putAll(ers.getDataMap());
        return so;
    }

    public void stateChanged(ChangeEvent e) {
        HelpTabPanel htp=(HelpTabPanel)hlptabs.getSelectedComponent();
        if (htp == null) return;
        htp.actionPerformed(null);
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        HelpTabPanel htp = (HelpTabPanel) hlptabs.getSelectedComponent();
        if (e.getSource() == jtfFind) {
            if(e.getKeyChar()=='\n'){
                 find();
                 return;
            }
        } else {
            char ch = e.getKeyChar();
            String aa=String.valueOf(ch);
            String txt = jtfFind.getText().trim();
            if (ch == '\b') {
                if (txt.trim().length() > 0)
                    txt = txt.substring(0, txt.length() - 1);
            } else if(Character.isLetterOrDigit(ch)){
                txt = txt + ch;
            }
            else if(aa.matches("[\\u4E00-\\u9FA5]+")){
                 txt = txt + ch;
            }
            else
                return;
            btabinput=true;
            jtfFind.setText(txt);
        }
    }
    boolean btabinput=false;
    protected void find(){
        HelpTabPanel htp = (HelpTabPanel) hlptabs.getSelectedComponent();
        htp.getContentPanel().locateCode(jtfFind.getText().trim());
        if(btabinput){
            htp.getContentPanel().getContentTable().requestFocus();
            btabinput=false;
        }
    }
    public void insertUpdate(DocumentEvent e) {
    find();
    }

    public void removeUpdate(DocumentEvent e) {
          find();
    }

    public void changedUpdate(DocumentEvent e) {
        find();
    }











  protected Vector     WindowCustomEventList = null;
  public static int RESULT_OK=1;
  public static int RESULT_CANCEL=0;
  public int Result=0;
  public Object PO = null;
  protected Object CustomObject;

  public JComponent getCustom() {
    return null;
  }
  public void addWindowCustomEvent(IWindowCustom wc) {
    if ( WindowCustomEventList == null )
      WindowCustomEventList = new Vector();
    WindowCustomEventList.add(wc);
  }
  public void removeWindowCustomEvent(IWindowCustom wc) {
    if ( WindowCustomEventList != null ) {
      WindowCustomEventList.remove(wc);
    }
  }
  protected void CallWindowCustomEvent(String EventName,Object o) {
    if ( WindowCustomEventList == null ) return;
    IWindowCustom iwc = null;
    for(int i=0;i<WindowCustomEventList.size();i++) {
      iwc = (IWindowCustom)WindowCustomEventList.get(i);
      JBOFClass.CallObjectMethod(iwc,EventName,this,o);
    }
  }
  public Object getResultObject() {
    return null;
  }

  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public void CenterWindow() {
      Dimension dlgSize = this.getSize();
      Dimension ScnSize = Toolkit.getDefaultToolkit().getScreenSize();
      this.setLocation((ScnSize.width - dlgSize.width)/2, (ScnSize.height - dlgSize.height)/2);
  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public int OnOk() {
      Result = RESULT_OK;
//      this.removeNotify();
      this.dispose();
      this.hide();
      return Result;
  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public int OnCancel() {
      Result = RESULT_CANCEL;
//       this.removeNotify();
      this.dispose();
      this.hide();
      return Result;
  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public void setCustomObject(Object O) {
    CustomObject = O;
  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public void InitDialog() {

  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public void DestroyDialog() {

  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public void Show() {
    if ( this.isVisible() ) return;
    InitDialog();
    super.show();
  }
  //----------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //----------------------------------------------------------------------------------------------
  public void Hide() {
    if ( !this.isVisible() ) return;
    DestroyDialog();
    super.hide();
  }

}
