package com.efounder.bz.dbform.datamodel.footer;

import java.awt.event.*;
import javax.swing.*;

import com.efounder.action.*;
import com.efounder.bz.dbform.datamodel.*;
import com.efounder.eai.ide.*;

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
public class ColumnFooterGroupTookit extends DataComponentToolkitAdapter {

    public ColumnFooterGroupTookit() {
    }

    /**
     *
     * @param dataComp DataComponent
     * @param dataContainer DataContainer
     * @return Action[]
     */
    public Action[] getComponentContextAction(DataComponent dataComp,
                                              DataContainer dataContainer) {
        Action action = new ActiveObjectAction(this, null, "test",
                                               "待实现功能", '0',
                                               "待实现功能",
                                               ExplorerIcons.getExplorerIcon(
            "oicons/column.png"));
        return new Action[] {action};
    }

    /**
     *
     * @param actionObject Object
     * @param nodes Object[]
     * @param action Action
     * @param actionevent ActionEvent
     */
    public void test(Object actionObject, Object[] nodes,
                     javax.swing.Action action, ActionEvent actionevent) {

    }

}
