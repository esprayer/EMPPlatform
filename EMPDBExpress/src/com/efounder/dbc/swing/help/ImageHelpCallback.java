package com.efounder.dbc.swing.help;

import com.efounder.dctbuilder.data.*;
import com.efounder.dbc.blob.BlobDataSet;
import com.efounder.dbc.swing.editor.dlg.ImageDialog;
import com.efounder.eai.*;

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
public class ImageHelpCallback  implements IHelpCallBack {
    public ImageHelpCallback() {
    }

    public String getHelpFKey() {
        return "";
    }
    public Object help(Object o) {
        Object[]oo=(Object[])o;
        ColumnMetaData cmd=(ColumnMetaData)oo[0];
        BlobDataSet bds=(BlobDataSet)oo[1];
        ImageDialog id=new ImageDialog(EAI.EA.getMainWindow(), "图片", true);
        id.setSize(450, 250);
        id.CenterWindow();
        Object im = bds.getBlob(cmd.getColid());
        if (im != null)
            id.showImage(im);
        id.setVisible(true);
        try {
            return id.getImageBytes();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public void setColumnMetaData(ColumnMetaData cmd) {
    }
}
