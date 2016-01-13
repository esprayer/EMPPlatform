package com.efounder.dbc.blob;

import java.util.*;

import com.borland.dx.dataset.*;
import com.borland.jb.util.*;
import com.efounder.dbc.*;
import com.efounder.eai.*;
import com.efounder.eai.data.*;

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
public class BlobDataSet extends ClientDataSet implements  EditListener{
    String[] keyColumn;
    String[] blobColumn;
    String where;
    BlobContext blobContext;

    public BlobDataSet(BlobContext bc) {
        super();
       blobContext=bc;
//       this.addEditListener(this);
    }
    public void setBlogContext(BlobContext bc){
          blobContext=bc;
    }
    public Object getBlob(String column) {
//         String key=getInternalRow()+" "+column;
//        Object o= blobData.get(key);
//        if(o==null&&this.getStatus()!=RowStatus.INSERTED&&
//        !blobContext.isLoadOpen()){
//          key=blobContext.getKeys(this);
//          JParamObject PO=JParamObject.Create();
//          key=key.substring(0,key.length()-1);
//          String where=key.replaceAll(";"," AND ");
//        try {
//            JResponseObject RO=(JResponseObject) EAI.DAL.IOM("DBService", "getBlobData", PO, this.getTableName(),
//                        column, where);
//            o=RO.getResponseObject();
//            if(o!=null){
//                 key=getInternalRow()+" "+column;
//                blobData.put(key, o);
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//        }
//        return o;
    	return null;
    }
    public void setBlob(String column, Object o) throws Exception{
//      if(this.getStatus()==RowStatus.INSERTED){
//        boolean b = post();
//        if (!b) {
//            throw new Exception("设置BLOB列失败，请先设置所有的主键值");
//        }
//      }
//        String key=getInternalRow()+" "+column;
//        blobData.put(key, o);
//        updateBlob.put(key,o);
//        this.setMidified(true);
    }
    public void EndDataLoad(ClientDataSetData cdss) {
//        super.EndDataLoad(cdss);
//        Object[]oo=(Object[])cdss.getUserObject();
//        this.setUserObject(oo[0]);
//        BlobContext context=(BlobContext)oo[1];
//        blobData=context.getBlobMap();
//        if(blobData==null)blobData=new HashMap();
//        updateBlob.clear();
    }
    public void BeforeDataLoad(ClientDataSetData cdss) {
        Object uo=cdss.getUserObject();
        cdss.setHasBlobData(true);
        cdss.setUserObject(new Object[]{uo,blobContext});
    }
    transient Map blobData;
    transient Map updateBlob=new HashMap();
    private BlobContext blogContext;
    public void BeforDataSave(ClientDataSetData cdss) {
//        cdss.setHasBlobData(true);
//        Map map=new HashMap();
//        String key;
//        Iterator it=updateBlob.keySet().iterator();
//        while(it.hasNext()){
//            key=(String)it.next();
//            Object o=updateBlob.get(key);
//            String[]strs=key.split(" ");
//            long lr=Long.parseLong(strs[0]);
//            if(!this.goToInternalRow(lr))continue;
//            key=blobContext.getKeys(this);
//            map.put(key+strs[1],o);
//        }
//        blobContext.setBlobMap(map);
//        Object uo=cdss.getUserObject();
//        cdss.setUserObject(new Object[]{uo,blobContext});
    }
    public void EndDataSave(Object o) {
//        super.EndDataSave(o);
//        updateBlob.clear();
    }

    public void canceling(DataSet dataSet) throws Exception {
    }

    public void updating(DataSet dataSet, ReadWriteRow newRow, ReadRow oldRow) throws
            Exception {
    }

    public void updated(DataSet dataSet) {
    }

    public void adding(DataSet dataSet, ReadWriteRow newRow) throws Exception {
    }
    public void added(DataSet dataSet) {
    }
    public void deleting(DataSet dataSet) throws Exception {
//       String column[]=blobContext.getBlobColumn();
//       long lr=this.getInternalRow();
//       for(int i=0;i<column.length;i++){
//           updateBlob.remove(lr+" "+column[i]);
//           blobData.remove(lr+" "+column[i]);
//       }
    }
    public void deleted(DataSet dataSet) {
    }
    public void modifying(DataSet dataSet) throws Exception {
    }
    public void inserting(DataSet dataSet) throws Exception {
    }
    public void inserted(DataSet dataSet) {
    }

    public void editError(DataSet dataSet, Column column, Variant value,
                          DataSetException ex, ErrorResponse response) {
    }

    public void addError(DataSet dataSet, ReadWriteRow row, DataSetException ex,
                         ErrorResponse response) {
    }

    public void updateError(DataSet dataSet, ReadWriteRow row,
                            DataSetException ex, ErrorResponse response) {
    }

    public void deleteError(DataSet dataSet, DataSetException ex,
                            ErrorResponse response) {
    }

    public BlobContext getBlogContext() {
        return blogContext;
    }
}
