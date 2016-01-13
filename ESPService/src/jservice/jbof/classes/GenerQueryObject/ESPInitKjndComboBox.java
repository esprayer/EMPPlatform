package jservice.jbof.classes.GenerQueryObject;

import javax.swing.JComboBox;
import com.efounder.builder.base.data.EFDataSet;
import java.util.List;

import com.efounder.eai.data.JParamObject;
import com.efounder.eai.service.MDMDataUtil;
import java.util.ArrayList;
import com.efounder.builder.base.data.EFRowSet;
import com.efounder.builder.base.data.DataSetUtils;

public class ESPInitKjndComboBox extends JComboBox {
  public ESPInitKjndComboBox() {
  }

  public void initComboBox(JParamObject PO,String pDCTID){
    List list = new ArrayList();
    EFDataSet eds = (EFDataSet) MDMDataUtil.getTableData(PO,
        pDCTID,
        " F_SYZT='1'", true);
    if (eds != null && eds.getRowCount() > 0) {
      DataSetUtils.sortEFDataSet(eds, new String[]{"NDZD_BH"}, null);
      for (int i = 0; i < eds.getRowCount(); i++) {
        EFRowSet rowset = eds.getRowSet(i);
        String kjnd = rowset.getString("NDZD_BH", "").toString();
        list.add(kjnd);
      }
    }
    String kjnd[] = (String[]) list.toArray(new String[0]);
    for (int i = 0; i < kjnd.length; i++) {
      super.addItem(kjnd[i]);
    }
  }
  /**
  * 设置从数据字典名称并初始化下拉框
  * @param pDCTID String
  */
 public void setPoZdmc(JParamObject PO,String pDCTID) {
   if (pDCTID != null) {
     super.getSelectedItem();
     initComboBox(PO,pDCTID);
   }
 }
}
