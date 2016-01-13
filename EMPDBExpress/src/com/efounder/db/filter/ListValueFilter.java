package com.efounder.db.filter;

import com.borland.dx.dataset.DataSet;
import java.util.List;
import com.borland.dx.dataset.Variant;
import com.borland.dx.dataset.ReadRow;
/**
 *
 * <p>Title: </p>
 *
 * <p>Description:DataSet的过滤，初始Map包括多个关键字的值，
 * 可以过滤一组关键字　 SimpleDataSetFilter只可以过滤一个关键字</p>
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ListValueFilter
     extends BaseDataSetFilter {
   public ListValueFilter(DataSet dataSet, java.util.Map fValueList) {
     super(dataSet, fValueList);
   }

   protected boolean checkFieldValue(ReadRow row) {
     Object[] Fields = this.fieldValueList.keySet().toArray();
     String fieldName;
     List fieldValue;
     Object fv = null;
     for (int i = 0; i < Fields.length; i++) {
       fieldName = (String) Fields[i];
       fv = fieldValueList.get(fieldName);
       Value = new Variant();
       row.getVariant(fieldName, Value);
       valueFormatter = dataSet.getColumn(fieldName).getFormatter();
       String s = valueFormatter.format(Value);
       if(fv instanceof String){
        if (!s.equals(fv))
          return false;
       }else{
           fieldValue=(List)fv;
           if (!fieldValue.contains(s))
               return false;
       }
     }
     return true;
   }

  }
