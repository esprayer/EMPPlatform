package com.efounder.dbc.blob;

import com.borland.dx.sql.dataset.QueryDescriptor;

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
public class testBlob {
    public testBlob() {
//        BlobContext ct = new BlobContext();
//        ct.setKeyColumn(new String[] {"DIR_BH", "DOC_BH", "DWZD_BH", "RPT_DATE"});
//        ct.setBlobColumn(new String[] {"RPT_IMAGE"});
//        ct.setLoadOpen(false);
//        BlobDataSet bds = new BlobDataSet(ct);
//        bds.setTableName("DB002_OWNER.FUCK");
//        QueryDescriptor qds = new QueryDescriptor(null,
//                "select DIR_BH,DOC_BH,DWZD_BH,RPT_DATE ,RPT_CDATE from DB002_OWNER.FUCK", null, true);
//        bds.setQuerySet(qds);
//        try {
//            AgentDatabase.getDefault().loadDataSet(bds);
//            bds.first();
//            int count = 0;
//            Object o = null;
//            do {
//                count++;
//                if (count == 3) {
//                    o = bds.getBlob("RPT_IMAGE");
//                    bds.deleteRow();
//                    break;
//                }
//            } while (bds.next());
//            bds.insertRow(false);
//            bds.setString("DIR_BH", "SB");
//            bds.setString("DOC_BH", "SB");
//            bds.setString("DWZD_BH", "SB");
//            bds.setString("RPT_DATE", "SB");
//            bds.setString("RPT_CDATE", "sb");
//            bds.setBlob("RPT_IMAGE", o);
//            bds.saveChanges();
//        } catch (Exception ex) {
//        }
    }

}
