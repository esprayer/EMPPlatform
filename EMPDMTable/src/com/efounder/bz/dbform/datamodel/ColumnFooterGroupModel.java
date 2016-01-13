package com.efounder.bz.dbform.datamodel;

import java.beans.*;

import com.efounder.bz.dbform.component.dc.table.footer.*;
import com.efounder.bz.dbform.datamodel.footer.*;

public class ColumnFooterGroupModel
    extends DataComponentAdapter implements PropertyChangeListener, DataModelComponent {

    /**
     *
     */
    protected GroupTableModel   footerModel;
    /**
     *
     */
    protected TableGroupColumnFooter footer;

    /**
     *
     */
    public ColumnFooterGroupModel(){

    }

    /**
     *
     */
    protected ColumnModel columnModel = null;
    /**
     *
     * @return ColumnModel
     */
    public ColumnModel getColumnModel() {
        return columnModel;
    }

    /**
     *
     * @param columnModel ColumnModel
     */
    public void setColumnModel(ColumnModel columnModel) {
        this.columnModel = columnModel;
        if(footerModel!=null)
          footerModel.setColumnModel(columnModel);
    }


    /**
     *
     * @param footer TableGroupColumnFooter
     */
    public ColumnFooterGroupModel(TableGroupColumnFooter footer) {
        this.footer = footer;
    }

    /**
     *
     * @return TableModel
     */
    public GroupTableModel getFooterModel(){
        return this.footerModel;
    }
    /**
     *
     * @param model TableModel
     */
    public void setFooterModel(GroupTableModel model) {
        this.footerModel = model;
        if (footer != null) footer.setModel(model);
        if(model!=null)model.setColumnModel(columnModel);
    }

    /**
     *
     * @param footer TableGroupColumnFooter
     */
    public void setFooter(TableGroupColumnFooter footer){
        this.footer = footer;
    }

    /**
     *
     * @return TableGroupColumnFooter
     */
    public TableGroupColumnFooter getFooter(){
        return this.footer;
    }

    /**
     *
     * @param dataComponent DataComponent
     */
    public int insertDataComponent(DataComponent dataComponent) {
        if (! (dataComponent instanceof ColumnFooterGroup)) return -1;
        int index = super.insertDataComponent(dataComponent);
        fireGroupChanged();
        return index;
    }

    /**
     * fireGroupChanged
     */
    private void fireGroupChanged() {
    }

    /**
     *
     * @param evt PropertyChangeEvent
     */
    public void propertyChange(PropertyChangeEvent evt) {
    }

}
