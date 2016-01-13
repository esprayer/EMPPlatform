package com.efounder.bz.dbform.property.editor;

import com.l2fprod.common.beans.editor.*;
import java.awt.event.ActionEvent;
import com.l2fprod.common.swing.ComponentFactory;
import com.core.xml.StubObject;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import com.efounder.bz.dbform.datamodel.DataSetComponent;
import com.efounder.bz.dbform.datamodel.DMComponent;
import javax.swing.JTable;
import com.l2fprod.common.swing.PercentLayout;
import com.efounder.pfc.swing.JIConListCellRenderer;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.bz.dbform.bizmodel.MDMComponent;
import com.efounder.builder.meta.bizmodel.BIZMetaData;
import com.efounder.builder.meta.dctmodel.DCTMetaData;
import com.efounder.dctbuilder.data.DctConstants;
import com.efounder.builder.base.data.EFDataSet;
import com.efounder.builder.base.data.EFRowSet;
import com.help.HelpInfoContext;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class BizFKEYIDPropertyEditor extends AbstractPropertyEditor implements ActionListener{

  private JComboBox cbLayoutList;
  private JButton button;
  private JButton bthelp;
  /**
   *
   * @param contextObject Object
   */
  public void initPropertyEditor(Object contextObject) {
    initLayoutList(contextObject);
  }
  /**
   *
   */
  protected void initLayoutList(Object contextObject) {
    JTable editTable = (JTable)contextObject;
    if ( editTable == null ) return;
    Object comp = editTable.getClientProperty("ownerComponent");
    if ( !(comp instanceof MDMComponent) ) return;
    MDMComponent mdmComponent = (MDMComponent)comp;
    String DCT_ID = mdmComponent.getDCT_ID();
    if ( DCT_ID == null || DCT_ID.trim().length() == 0 ) return;
    BIZMetaData bizMetaData = mdmComponent.getBIZMetaData();
    if ( bizMetaData == null ) return;
    DCTMetaData dctMetaData = bizMetaData.getDCTMetaData(DCT_ID);
    if ( dctMetaData == null ) return;
//    if ( !(comp instanceof DataSetComponent) ) return;
//    DataSetComponent dataSetComponent = (DataSetComponent)comp;
//    if ( dataSetComponent == null ) return;
    String[] dataSetKeys = dctMetaData.getFKeyDCTColIDs();//dataSetComponent.getDataSetKey();
    if ( dataSetKeys == null || dataSetKeys.length == 0 ) return;
    Icon icon = ExplorerIcons.getExplorerIcon("dataset.gif");
    JIConListCellRenderer cellRenderer = new JIConListCellRenderer(icon);
    cbLayoutList.setRenderer(cellRenderer);
    cbLayoutList.setVisible(false);
    String dataSetKey = null;int index = 0;
    dctEds=EFDataSet.getInstance("");
    for(int i=0;i<dataSetKeys.length;i++) {
      StubObject stub = null;
      dataSetKey = dataSetKeys[i];
      stub = new StubObject();
      stub.setID(dataSetKey);stub.setCaption(dataSetKey);
      cbLayoutList.insertItemAt(stub,index++);
      DCTMetaData mdm=dctMetaData.getFKeyDCTMetaData(dataSetKey);
      EFRowSet ers=EFRowSet.getInstance();
      ers.putString("DCT_ID",dataSetKey);
      ers.putString("DCT_MC",dataSetKey);
      if(mdm!=null)  ers.putString("DCT_MC",mdm.getDCT_MC());
      dctEds.insertRowSet(ers);
    }
  }
  EFDataSet dctEds;

  public BizFKEYIDPropertyEditor() {
    JPanel panel = new JPanel(new PercentLayout(PercentLayout.HORIZONTAL, 0));
    editor = panel;
    cbLayoutList = new JComboBox();
    panel.add("*", cbLayoutList);
    ( (JPanel) editor).add(bthelp = ComponentFactory.Helper.getFactory()
                          .createMiniButton());
   bthelp.addActionListener(this);

    ((JPanel)editor).add(button = ComponentFactory.Helper.getFactory().createMiniButton());
    button.setText("X");
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setNull();
      }
    });
    ((JPanel)editor).setOpaque(false);
  }
  /**
   *
   * @return Object
   */
  public Object getValue() {
    cbLayoutList.removeActionListener(this);
    return dataObject;
  }
  protected Object dataObject = null;
  /**
   *
   * @param value Object
   */
  public void setValue(Object value) {
    this.cbLayoutList.setVisible(true);
    dataObject = value;
    this.cbLayoutList.addActionListener(this);
    if ( value == null ) return;
    setCurrentLayout(value);
  }
  /**
   *
   * @param value Object
   */
  protected void setCurrentLayout(Object value) {
    StubObject stub = null;
    for(int i=0;i<cbLayoutList.getItemCount();i++) {
      stub = (StubObject)cbLayoutList.getItemAt(i);
      if ( value.equals(stub.getID()) ) {
        cbLayoutList.setSelectedIndex(i);
        break;
      }
    }
  }
  /**
   *
   */
  protected void selectLayout() {
    StubObject stub = (StubObject)cbLayoutList.getSelectedItem();
    if ( stub == null ) return;
    Object oldFont = dataObject;
    dataObject = stub.getID();
    firePropertyChange(oldFont, dataObject);
  }
  /**
   *
   */
  protected void setNull() {
    Object oldFont = dataObject;
    dataObject = null;
    firePropertyChange(oldFont, null);
  }
  /**
   *
   * @param e ActionEvent
   */
  public void actionPerformed(ActionEvent e) {
    if(e.getSource()==bthelp){
      if(dctEds==null)return;
     HelpInfoContext hic = new HelpInfoContext();
       hic.setBufdata(false);
       hic.setHelpColumn(new String[] {"DCT_ID", "DCT_MC"});
       hic.setBHColumn("DCT_ID");
       hic.setMCColumn("DCT_MC");
       hic.setSelValue((String)dataObject);
       hic.setObject(DctConstants.ALLDATA, dctEds);
//       StubObject so = (StubObject) DictHelpObject.showHelpList(hic);
//       String[] mess = new String[2];
//       if (so != null) {
//           mess[0] = so.getString("DCT_ID", "");
//           mess[1] = so.getString("DCT_MC", "");
//           Object oldFont = dataObject;
//           dataObject =  mess[0];
//           firePropertyChange(oldFont, dataObject);
//       }

   }else

    selectLayout();
  }
}
