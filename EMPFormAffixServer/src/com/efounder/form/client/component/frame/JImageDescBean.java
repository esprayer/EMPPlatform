package com.efounder.form.client.component.frame;

import com.efounder.form.client.util.*;



/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2010</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JImageDescBean {
    public JImageDescBean(String name,String type,int wid,int height,int iSize,String order) {
        this.imgName    =   name;
        this.imgType    =   type;
        this.imgWidth   =   wid;
        this.imgHeight  =   height;
        this.imgSize    =   iSize;
        this.order      =   order;
    }

    public JImageDescBean(String name,String type) {
        this(name,type,0,0,0,"0");
    }


    String imgName = "";
    String imgType = "";
    int imgWidth  = 0;
    int imgHeight = 0;
    int imgSize   = 0;
    String order = "";

    public int getImgHeight() {
        return imgHeight;
    }

    public String getImgName() {
        return imgName;
    }

    public int getImgSize() {
        return imgSize;
    }

    public String getImgType() {
        return imgType;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public String getOrder() {
        return order;
    }

    public String getImgTypeName() {
        String imgTypeName = "";
        if(JFormAffixVar.TYPE_PIC.equals(imgType)){
            imgTypeName = "影像";
        }else if(JFormAffixVar.TYPE_DOC.equals(imgType)){
            imgTypeName = "电子文档";
        }else if(JFormAffixVar.TYPE_XLS.equals(imgType)){
            imgTypeName = "电子表格";
        }else if(JFormAffixVar.TYPE_PDF.equals(imgType)){
            imgTypeName = "PDF文档";
        }else if(JFormAffixVar.TYPE_TXT.equals(imgType)){
            imgTypeName = "纯文本文档";
        }else if(JFormAffixVar.TYPE_CUSBILL.equals(imgType)){
            imgTypeName = "自制表单";
        }else if(JFormAffixVar.TYPE_DOCX.equals(imgType)){
            imgTypeName = "电子文档2007";
        }else if(JFormAffixVar.TYPE_XLSX.equals(imgType)){
            imgTypeName = "电子表格2007";
        }
        else{
            imgTypeName = "未知";
        }
        return imgTypeName;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public void setImgSize(int imgSize) {
        this.imgSize = imgSize;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
