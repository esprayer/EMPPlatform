package com.efounder.bz.dbform.component.dc.table.render;

import org.jdesktop.swingx.renderer.StringValue;
import com.efounder.bz.dbform.datamodel.RowSetValue;
import org.jdesktop.swingx.renderer.IconValue;
import javax.swing.Icon;
import com.efounder.eai.ide.ExplorerIcons;
import com.efounder.pub.comp.CompoundIcon;
import com.efounder.bz.dbform.datamodel.column.Column;
import com.efounder.builder.base.data.ESPRowSet;
import com.efounder.builder.base.data.EFRowSet;
import org.openide.ErrorManager;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.*;
//import com.efounder.dctbuilder.data.ColumnMetaData;

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
public class RowSetValueConverter implements StringValue,IconValue {
  /**
   *
   */
  public RowSetValueConverter(TableCellRendererManager tableCellRendererManager) {
    this.tableCellRendererManager = tableCellRendererManager;
    titleIcon = tableCellRendererManager;
  }
  protected TitleIcon titleIcon = null;
  /**
   *
   */
  protected TableCellRendererManager tableCellRendererManager = null;

  /**
   * Returns a string representation of the given value.
   *
   * @param value the object to present as a string
   * @return a string representation of the given value, guaranteed to be not null
   * @todo Implement this org.jdesktop.swingx.renderer.StringValue method
   */
  public String getString(Object value) {
    if ( value == null ) return null;
    if ( !(value instanceof RowSetValue) ) return value.toString();
    RowSetValue rowSetValue = (RowSetValue)value;
    if ( titleIcon != null ) {
      String title = titleIcon.getTitle(rowSetValue,0);
      if ( title != null && title.trim().length() > 0 ) return title;
    }
      Column column = rowSetValue.getColumn();
      if(column.isIconColumn())return "";
    EFRowSet colRowSet = (EFRowSet)rowSetValue.getColMetaData();
    Object oo=getValueByColumn(colRowSet,rowSetValue);
    if(oo!=null)return oo.toString();
    String colType = getColType(rowSetValue);
    //add by fsz 有时候用vecid带出来的是数据值的
    if(column.getDimSetRowSet()!=null&&column.getDimSetRowSet().getString("COL_TYPE", "").trim().length()>0)
  	  colType=column.getDimSetRowSet().getString("COL_TYPE", "");
    if(column.getValSetRowSet()!=null&&column.getValSetRowSet().getString("COL_TYPE", "").trim().length()>0)
    	  colType=column.getValSetRowSet().getString("COL_TYPE", "");
    String edittype=getColEditType(rowSetValue);
    if("4".equals(edittype)){
        return "";
    }
    if("6".equals(edittype)){
      String txt=getEnumString((String)rowSetValue.getCellValue(),colRowSet);
      if(txt!=null)return txt;
    }
    if ( "N".equals(colType) ) {
      return getNumberRowSetValue(rowSetValue);
    }
    if ( "D".equals(colType) || "T".equals(colType) || "5".equals(edittype)) {
      return getDateRowSetValue(rowSetValue);
    }
    if ( isFKEYColumn(rowSetValue) ) {
        return getFKEValue(rowSetValue);
      }
    return value.toString();
  }
  public String getEnumString(String value,EFRowSet colDefSet){
    String view = colDefSet.getString("COL_EDITVIEW", null);
    if (view == null)
      view = colDefSet.getString("COL_VIEW", null);
    String[] item = view.split(";");
    Map hash = new HashMap();
    for (int i = 0; i < item.length; i++) {
      if (item[i] == null || item[i].indexOf(":") < 0)
        continue;
      String s1 = item[i].substring(0, item[i].indexOf(":"));
      String s2 = item[i].substring(item[i].indexOf(":") + 1);
      hash.put(s1, s2);
    }
    return (String)hash.get(value);
  }
  /**
   *
   * @param rowSetValue RowSetValue
   * @return String
   */
  protected String getDateRowSetValue(RowSetValue rowSetValue) {
  java.util.Date date =null;
    Object oo=rowSetValue.getCellValue();
    if(oo instanceof java.util.Date  )
      date= (java.util.Date)oo;
    if(oo instanceof Number){
      date=new java.util.Date(((Number)oo).longValue());
    }
    if(oo instanceof String){
      DateFormat df = new SimpleDateFormat("yyyyMMdd");
      try {
    	  if(oo != null && oo.toString().trim().length() > 0){
    		  date = df.parse( (String) oo);
    	  }
      }
      catch (ParseException ex) {
        ex.printStackTrace();
      }
    }
    if ( date == null ) return "";
    String pattern = rowSetValue.getColumn().getDateFormat();
    if ( pattern == null || pattern.trim().length() == 0 ) pattern = "yyyy年MM月dd日";
    DateFormat df = new SimpleDateFormat(pattern);
    return df.format(date);
  }
  /**
   *
   * @param rowSetValue RowSetValue
   * @return String
   */
  protected String getNumberRowSetValue(RowSetValue rowSetValue) {
    Number number = null;
    if ( rowSetValue.getCellValue() instanceof Number ) {
      number = (Number)rowSetValue.getCellValue();
    } else if ( rowSetValue.getCellValue() instanceof String ) {
        String aa= (String) rowSetValue.getCellValue() ;
        if(aa.equals(""))aa="0";
      number = new BigDecimal((String)aa);
    }
    if ( number == null ) return "";
    // 如果为空，则为空串
    if ( BigDecimal.ZERO.equals(number)||number.doubleValue()==0.0 ) return "";
    String pattern = rowSetValue.getColumn().getNumberFormat();
    if ( pattern == null || pattern.trim().length() == 0 ) pattern = "#,##0.00;#,##0.00";
    NumberFormat nf = new DecimalFormat(pattern);
    if(number.doubleValue()>0)
      return nf.format(number);
    else
      return nf.format(number.doubleValue()*-1);
  }
  /**
   *
   * @param rowSetValue RowSetValue
   * @return String
   */
  public static String getColType(RowSetValue rowSetValue) {
    EFRowSet colRowSet = (EFRowSet)rowSetValue.getColMetaData();
    if ( colRowSet == null ) return "C";
    return colRowSet.getString("COL_TYPE",null);
  }
  public static String getColEditType(RowSetValue rowSetValue) {
  EFRowSet colRowSet = (EFRowSet)rowSetValue.getColMetaData();
  if ( colRowSet == null ) return "1";
  return colRowSet.getString("COL_EDIT",null);
}

  /**
   *
   * @param rowSetValue RowSetValue
   * @return boolean
   */
  protected boolean isFKEYColumn(RowSetValue rowSetValue) {
    Column column = rowSetValue.getColumn();
    String DCT_ID = column.getViewDataSetID();
    return ( DCT_ID != null && DCT_ID.trim().length() > 0 );
  }
  /**
   *
   * @param rowSetValue RowSetValue
   * @return String
   */
  protected String getFKEValue(RowSetValue rowSetValue) {
    //modified by lchong at 2011-9-13：有些单元格删除了编号，显示的名称应该也显示为空串
    if(rowSetValue.getCellValue() == null || "".equals(rowSetValue.getCellValue())){
        return "";
    }
    //modified end
    Column column = rowSetValue.getColumn();
    // 如果是系统列，返回空
    if ( column.isCheckBoxColumn() ) return null;
    String COL_ID = column.getDataSetColID();
    String DCT_ID = column.getViewDataSetID();
    String VIEW_COL_ID = column.getViewDataSetColID();
    String VALUE_COL_ID = column.getValueDataSetColID();

    // 外键显示方式，元数据或者关键指标设置中定义fkeyShowMode=3:编号+名称
    String fkeyShowMode="";
    if(rowSetValue.getColMetaData()!=null)
    	fkeyShowMode= (String) rowSetValue.getColMetaData().getExtProperty("fkeyShowMode", null);

    // modified by lchong
    if("1".equals( column.getProperty("SHOWCODE",""))){//静态列上设置了该属性
        return rowSetValue.toString();
    }
    if(rowSetValue.getColumn().getDimSetRowSet() != null){
        Map propertyMap = (Map) rowSetValue.getColumn().getDimSetRowSet().getObject("F_SRCCONT_MAP", null);
        if (propertyMap != null) {
          // 外键显示方式 modified by gengeng
          if (propertyMap.get("fkeyShowMode") != null)
            fkeyShowMode = (String) propertyMap.get("fkeyShowMode");

            String showName = (String) propertyMap.get("SHOWCODE");
            if ("1".equals(showName)) { //要求显示编号
                return rowSetValue.toString();
            }
        }
    }
    //modified end

    if(VIEW_COL_ID==null)VIEW_COL_ID="";
    // 如果设置了值ID，则不处理，是什么值就显示什么值
    if ( (VALUE_COL_ID != null && VALUE_COL_ID.trim().length() != 0) ||
         (DCT_ID == null || DCT_ID.trim().length() == 0)||VIEW_COL_ID.equals(COL_ID) ) return rowSetValue.toString();
    if ( (VIEW_COL_ID == null || VIEW_COL_ID.trim().length() == 0) && rowSetValue.getViewDCTMetaData() != null )
      VIEW_COL_ID = rowSetValue.getViewDCTMetaData().getDCT_MCCOLID();
    ESPRowSet viewRowSet = rowSetValue.getMainRowSet().getID2RowSet(DCT_ID,COL_ID);
//    if ( viewRowSet != null ){
//        return viewRowSet.getString(VIEW_COL_ID, rowSetValue.toString());
//    }
//    return rowSetValue.getMainRowSet().getID2Name(DCT_ID,COL_ID,rowSetValue.toString());
    String bhval = rowSetValue.toString(), mcval = "";
    if (viewRowSet != null)
      mcval = viewRowSet.getString(VIEW_COL_ID, "");
    else
      mcval = rowSetValue.getMainRowSet().getID2Name(DCT_ID,COL_ID,"");
    if (mcval == null || mcval.trim().length() == 0)
      mcval = bhval;
    // 编号+名称
    if ("1".equals(fkeyShowMode))
      return bhval;
    else if ("2".equals(fkeyShowMode))
      return mcval;
    else if ("3".equals(fkeyShowMode))
      return bhval + " " + mcval;
    return mcval;
  }
  /**
   *
   * @param value Object
   * @return Icon
   */
  public Icon getIcon(Object value) {
    if ( value == null ) return null;
    if ( !(value instanceof RowSetValue) ) return null;
    if ( titleIcon != null ) {
      Icon icon = titleIcon.getLeafIcon(value,0);
      if ( icon != null ) return icon;
    }
    RowSetValue rowSetValue = (RowSetValue)value;
    String edittype=getColEditType(rowSetValue);
    if ("4".equals(edittype)) {
//      if (String.valueOf(ColumnMetaData.EL_BOOL).equals(edittype)) {
      if("1".equals(rowSetValue.getCellValue()))
         return ExplorerIcons.getExplorerIcon("icons/select1.png");
       else
         return ExplorerIcons.getExplorerIcon("icons/select0.png");
    }
    Column column = rowSetValue.getColumn();
    if ( column.isCheckBoxColumn() ) {
      return getSelectRowIcon(rowSetValue, column);
    }
    Icon icon = null;
    if ( column.isIconColumn() ) {
      Object sIcon = getIconByColumn(rowSetValue);
      if(sIcon instanceof String){
    	  String  iconstr=(String)sIcon;
    	  if(iconstr.indexOf(";")==-1)
    		  icon = ExplorerIcons.getExplorerIcon((String)sIcon);
    	  else{//CompoundIcon
    		  String[]strs=iconstr.split(";");
    		  icon=ExplorerIcons.getExplorerIcon(strs[0]);
    		  for(int i=1;i<strs.length;i++){
    			  Icon two=ExplorerIcons.getExplorerIcon(strs[i]);
    			  icon=new CompoundIcon(icon,two);
    		  }
    	  }
      }
      if(sIcon instanceof Icon)
        return (Icon)sIcon;
    }
    return icon;
  }
  /**
   *
   * @param rowSetValue RowSetValue
   * @return String
   */
  protected Object getValueByColumn(EFRowSet column,RowSetValue rowSetValue) {
  try {
     Object sIcon = ScriptUtil.runComponentFunction(tableCellRendererManager, "getValueByColumn",
         new String[] {"colers","rowSetValue"},
         new Object[] {column,rowSetValue});
     return sIcon==null?null:sIcon.toString();
   } catch ( Exception ex ) {
     ex.printStackTrace();
   }
   return null;
}
  protected Object getIconByColumn(RowSetValue rowSetValue) {
    try {
       Object sIcon = ScriptUtil.runComponentFunction(tableCellRendererManager, "getIconByColumn",
           new String[] {"rowSetValue"},
           new Object[] {rowSetValue});
       return sIcon==null?null:sIcon;
     } catch ( Exception ex ) {
       ex.printStackTrace();
     }
     return null;
  }
  /**
   *
   * @param rowSetValue RowSetValue
   * @return Icon
   */
  private Icon getSelectRowIcon(RowSetValue rowSetValue,Column column) {
    if ( column != null && column.isCheckBoxColumn() ) {
      EFRowSet rowSet = (EFRowSet)rowSetValue.getMainRowSet();
      if ( rowSet == null ) return null;
      if ( rowSet.isRowSelected() ) {
        return ExplorerIcons.getExplorerIcon("icons/select1.png");
      } else {
        return ExplorerIcons.getExplorerIcon("icons/select0.png");
      }
    }
    return null;
  }
  //    // 如果是系统列，返回空
//    if ( column.isCheckBoxColumn() ) return null;
//    String COL_ID = column.getDataSetColID();
//    String DCT_ID = column.getViewDataSetID();
//    String VIEW_COL_ID = column.getViewDataSetColID();
//    String VALUE_COL_ID = column.getValueDataSetColID();
//    // 如果设置了值ID，则不处理，是什么值就显示什么值
//    if ( (VALUE_COL_ID != null && VALUE_COL_ID.trim().length() != 0) ||
//         (DCT_ID == null || DCT_ID.trim().length() == 0) ) return rowSetValue.toString();
//    if ( (VIEW_COL_ID == null || VIEW_COL_ID.trim().length() == 0) && rowSetValue.getViewDCTMetaData() != null )
//      VIEW_COL_ID = rowSetValue.getViewDCTMetaData().getDCT_MCCOLID();
//    ESPRowSet viewRowSet = rowSetValue.getMainRowSet().getID2RowSet(DCT_ID,COL_ID);
//    if ( viewRowSet != null )
//      return viewRowSet.getString(VIEW_COL_ID,rowSetValue.toString());
//    return rowSetValue.getMainRowSet().getID2Name(DCT_ID,COL_ID,rowSetValue.toString());
//    return rowSetValue.toString();
}
