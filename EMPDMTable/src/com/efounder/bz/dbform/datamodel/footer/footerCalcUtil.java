package com.efounder.bz.dbform.datamodel.footer;

import com.efounder.builder.base.data.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.pub.util.*;

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
public class footerCalcUtil {
  public footerCalcUtil() {
  }
  protected static double getDouble(Object o){
    double dd=0.0;
    if(o==null)return 0.0;
    if(o instanceof Number){
      dd=((Number)o).doubleValue();
    }
    else if(o instanceof String){
      try{
        dd = Double.parseDouble( (String) o);
      }catch(Exception e){}
    }
    return dd;
  }
 public static double sumColumn(EFDataSet eds,String colid,String decn){
   if(eds==null)return 0;
   int count=eds.getRowCount();
   double sum=0;
   for(int i=0;i<count;i++){
     Object num=eds.getRowSet(i).getNumber(colid,null);
     if(num==null)num=eds.getRowSet(i).getString(colid,null);
     double dd=getDouble(num);
         dd = JFNumber.round(dd,Integer.parseInt(decn));
     sum+=dd;
   }
   return sum;
 }
  public static double avgColumn(EFDataSet eds,String colid,String decn){
    double sum=sumColumn(eds,colid,decn);
    if(eds.getRowCount()>0)return sum/eds.getRowCount();
    return 0.0;
 }
 public static double maxColumn(EFDataSet eds,String colid,String decn){
   int count = eds.getRowCount();
   if(count==0)return 0;
   double max = 0,v;
   for (int i = 0; i < count; i++) {
     Object num = eds.getRowSet(i).getNumber(colid, null);
     if (num == null)
       num = eds.getRowSet(i).getString(colid, null);
     v=getDouble(num);
     if(i==0)max=v;
     else
       if(max<v)max=v;
   }
   return max;
 }
 public static double minColumn(EFDataSet eds,String colid,String decn){
   int count = eds.getRowCount();
   if (count == 0)
     return 0;
   double min = 0, v;
   for (int i = 0; i < count; i++) {
     Object num = eds.getRowSet(i).getNumber(colid, null);
     if (num == null)
       num = eds.getRowSet(i).getString(colid, null);
     v = getDouble(num);
     if (i == 0)
       min = v;
     else
     if (min > v)
       min = v;
   }
   return min;
 }

}
