package jframework.CntTier.GUI.Table;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import org.jdom.*;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Pansoft Ltd.</p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JXMLTable extends JTable {
    Vector TableActionListener = null;//new Vector();
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public JXMLTable() {
        super();
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public JXMLTable(TableModel dm) {
        super(dm);
        //TableActionListener = new Vector();
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void AddActionListener(IXMLTableEvent Event) {
        if ( TableActionListener == null )
            TableActionListener = new Vector();
        if ( Event != null ) {
            if ( TableActionListener.indexOf(Event) == -1 )
                TableActionListener.add(Event);
        }
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void DelActionListener(IXMLTableEvent Event) {
        if ( TableActionListener == null ) return;
        if ( Event != null )
            TableActionListener.remove(Event);
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void columnMarginChanged(ChangeEvent e) {
      IXMLTableEvent IEvent;int i,Count;
        if ( TableActionListener != null ) {
        Count = TableActionListener.size();
        for(i=0;i<Count;i++) {
            IEvent = (IXMLTableEvent)TableActionListener.get(i);
            if ( IEvent != null )
                IEvent.columnMarginChanged(e);
        }
        }
        super.columnMarginChanged(e);
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void columnMoved(TableColumnModelEvent e) {
      IXMLTableEvent IEvent;int i,Count;
        if ( TableActionListener != null ) {
        Count = TableActionListener.size();
        for(i=0;i<Count;i++) {
            IEvent = (IXMLTableEvent)TableActionListener.get(i);
            if ( IEvent != null )
                IEvent.columnMoved(e);
        }
        }
        super.columnMoved(e);
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void columnSelectionChanged(ListSelectionEvent e) {
      IXMLTableEvent IEvent;int i,Count;
        if ( TableActionListener != null ) {
        Count = TableActionListener.size();
        for(i=0;i<Count;i++) {
            IEvent = (IXMLTableEvent)TableActionListener.get(i);
            if ( IEvent != null )
                IEvent.columnSelectionChanged(e);
        }
        }
        super.columnSelectionChanged(e);
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void valueChanged(ListSelectionEvent e) {
      IXMLTableEvent IEvent;int i,Count;
        if ( TableActionListener != null ) {
        Count = TableActionListener.size();
        for(i=0;i<Count;i++) {
            IEvent = (IXMLTableEvent)TableActionListener.get(i);
            if ( IEvent != null )
                IEvent.valueChanged(e);
        }
        }
        super.valueChanged(e);
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void EditedCell(JXMLTableModel Model,Object[] OArray,Element element,int row,int col) {
      IXMLTableEvent IEvent;int i,Count;
        if ( TableActionListener != null ) {
        Count = TableActionListener.size();
        for(i=0;i<Count;i++) {
            IEvent = (IXMLTableEvent)TableActionListener.get(i);
            if ( IEvent != null )
                IEvent.EditedCell(Model,OArray,element,row,col);
        }
        }
    }
}
