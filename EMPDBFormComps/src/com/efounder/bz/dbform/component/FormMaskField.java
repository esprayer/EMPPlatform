package com.efounder.bz.dbform.component;

import javax.swing.*;
import javax.swing.text.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FormMaskField extends JFormattedTextField implements FormComponent{

    //编辑掩码
    private String editMask;
    //是否启用编辑掩码方式，如果不启用和普通的JTextField一样
    private boolean useMask = true;
    //合法字符，可以限制合法的字符数
    private String validCharacters;
    //非法字符
    private String invalidCharacters;
    //格式参考类
    private String maskClass;
    //占位符
    private String placeholderString;


    /**
     *
     */
    public FormMaskField() {
    }

    /**
     * 如果使用格式参考类
     */
    private void initMaskClassFormatter(){

    }

    /**
     *
     */
    private void initMaskFormatter(){
        if (!isUseMask()) return;
        try {
            MaskFormatter formatter = new MaskFormatter();
            formatter.setPlaceholder("");
            //设置编辑掩码
            formatter.setMask(getEditMask());
            //设置合法字符
            String valids = this.getValidCharacters();
            if (valids != null && valids.length() > 0) formatter.setValidCharacters(valids);
            //设置非法字符
            String invalids = this.getInvalidCharacters();
            if (invalids != null && invalids.length() > 0) formatter.setInvalidCharacters(invalids);
            //设置占位符
            formatter.setPlaceholder(getPlaceholder());
            setFormatterFactory(new DefaultFormatterFactory(formatter));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param editMask String
     */
    public void setEditMask(String editMask) {
        this.editMask = editMask;
        this.initMaskFormatter();
    }

    /**
     *
     * @param useMask boolean
     */
    public void setUseMask(boolean useMask) {
        this.useMask = useMask;
    }
    /**
     *
     * @param validCharacters String
     */
    public void setValidCharacters(String validCharacters) {
        this.validCharacters = validCharacters;
    }
    /**
     *
     * @param maskClass String
     */
    public void setMaskClass(String maskClass) {
        this.maskClass = maskClass;
    }
    /**
     *
     * @param invalidCharacters String
     */
    public void setInvalidCharacters(String invalidCharacters) {
      this.invalidCharacters = invalidCharacters;
    }

    /**
     *
     * @return String
     */
    public String getEditMask() {
        return editMask;
    }

    /**
     *
     * @return boolean
     */
    public boolean isUseMask() {
        return useMask;
    }
    /**
     *
     * @return String
     */
    public String getValidCharacters() {
        return validCharacters;
    }
    /**
     *
     * @return String
     */
    public String getMaskClass() {
        return maskClass;
    }
    /**
     *
     * @return String
     */
    public String getInvalidCharacters() {
      return invalidCharacters;
    }

    /**
     *
     * @param placeholder String
     */
    public void setPlaceholder(String placeholder) {
        this.placeholderString = placeholder;
    }

    /**
     *
     */
    public String getPlaceholder() {
        return placeholderString;
    }

    /**
     *
     * @return FormContainer
     */
    public FormContainer getFormContainer() {
        return null;
    }

    /**
     *
     * @return JComponent
     */
    public JComponent getJComponent() {
        return this;
    }

}
