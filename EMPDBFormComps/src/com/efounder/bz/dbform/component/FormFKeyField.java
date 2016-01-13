package com.efounder.bz.dbform.component;

import javax.swing.*;

import com.efounder.action.*;
import com.efounder.builder.meta.dctmodel.*;
import com.efounder.bz.dbform.component.popup.*;
import com.efounder.comp.*;
import com.efounder.eai.ide.*;
import com.help.HelpInfoContext;

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
public class FormFKeyField extends JComponentComboBox implements FormComponent {

  //外键字典
    private String fkeyObject;
    //关系对象
    private String rlglObject;
    //
    private DCTMetaData fkeymeta;

    //外键帮助显示
    private FormPopupFKey popupfkey = new FormPopupFKey();

    /**
     *
     */
    public FormFKeyField() {
        this.initPopupComps();
        this.setEditable(true);
    }

    /**
     * initPopupComp
     */
    private void initPopupComps() {
        this.setPopupComponent(this.popupfkey);
    }

    /**
     *
     */
    public void beforeShowPopupMenu() {
    }

    /**
     * initHelpButton
     */
    private void initFKeyHelpButton() {
        if (fkeyObject == null || fkeyObject.trim().length() == 0) return;
        Action acdict = new ActiveObjectAction(this, this, "helpfkey", "", '0', "",
                                               ExplorerIcons.ICON_TILE);
        ActionButton bndict = new ActionButton(this, acdict);
        bndict.setBorder(null);
        bndict.setIcon(ExplorerIcons.ICON_RUN);
        addButton(bndict);
    }

    /**
     *
     */
    private void initFKeyMetaData() {
        try {
            fkeymeta = (DCTMetaData) DCTMetaDataManager.getDCTMetaDataManager().getMetaData(this.getFkeyObject());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     */
    private void initRlgl() {
        fkeymeta.getRLGLDefine(getRlglObject());
    }

    /**
     * initHelpButton
     */
    private void initRlglHelpButton() {
        if (rlglObject == null || rlglObject.trim().length() == 0) return;
        Action acrlgl = new ActiveObjectAction(this, this, "helprlgl", "", '0', "",
                                               ExplorerIcons.ICON_CONSOLE);
        ActionButton bnrlgl = new ActionButton(this, acrlgl);
        bnrlgl.setBorder(null);
        bnrlgl.setIcon(ExplorerIcons.ICON_RUN);
        addButton(bnrlgl);
    }

    /**
     * 利用外键帮助
     *
     * @param actionObject Object
     * @param   dataObject Object
     * @param           p1 Object
     * @param           p2 Object
     */
    public void helpfkey(Object actionObject, Object dataObject,
                         Object p1, Object p2) {
        HelpInfoContext hic = new HelpInfoContext();
        hic.setDCTID(getFkeyObject());
        popupfkey.prepareData(hic);
        showPopup();
    }

    /**
     * 利用关系帮助
     *
     * @param actionObject Object
     * @param   dataObject Object
     * @param           p1 Object
     * @param           p2 Object
     */
    public void helprlgl(Object actionObject, Object dataObject,
                         Object p1, Object p2) {

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

    /**
     *
     * @param fkeyObject String
     */
    public void setFkeyObject(String fkeyObject) {
        this.fkeyObject = fkeyObject;
        this.initFKeyHelpButton();
        this.initFKeyMetaData();
    }

    /**
     *
     * @param rlglObject String
     */
    public void setRlglObject(String rlglObject) {
        this.rlglObject = rlglObject;
        this.initRlglHelpButton();
    }

    /**
     *
     * @return String
     */
    public String getFkeyObject() {
        return fkeyObject;
    }

    /**
     *
     * @return String
     */
    public String getRlglObject() {
        return rlglObject;
    }

    /**
     *
     * @return DCTMetaData
     */
    public DCTMetaData getFKeyMetaData(){
        return this.fkeymeta;
    }

}
