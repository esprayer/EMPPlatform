package jenterprise.bof.classes.AppExplorerObject.messlist;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.text.Position.*;

import com.borland.jbcl.layout.*;
import jframework.foundation.classes.JActiveDComDM;
import jenterprise.bof.classes.AppExplorerObject.JMessageShowWindow;

/**
 * 图标消息的JList。
 *
 * <p>为了能显示滚动条信息，必须把JList放到一个JScrollPane中去。
 *    该类代理提供了JList和JScrollPane的所有方法。使用的时候不需要再重新创建
 *    一个JScrollPane用来放置JList的对象,只需要通过方法<code>getScrollPane()</code>
 *    获得JScrollPane的对象然后添加到目标Panel中即可。
 *
 * @version 1.0 初始版本${gengeng}
 */
public class JIconList
    implements java.io.Serializable {

    public String     titleName;
    private String      moreType;
    private JList       messList;
    public JPanel      contents;
    private JMoreLabel moreLabel;

    public JIconList() {
        super();
        selfDefine();
    }

    public JIconList(String titleName) {
        this(titleName, null);
    }

    public JIconList(String titleName, String moreType) {
        this.titleName = titleName;
        this.moreType  = moreType;
        messList = new JList();
        selfDefine();
    }

    public JIconList(String titleName, Object[] listData, String moreType) {
        this.titleName = titleName;
        this.moreType = moreType;
        messList = new JList(listData);
        selfDefine();
    }

    public JIconList(String titleName, Vector listData, String moreType) {
        this.titleName = titleName;
        this.moreType = moreType;
        messList = new JList(listData);
        selfDefine();
    }

    public JIconList(String titleName, ListModel dataModel, String moreType) {
        this.titleName = titleName;
        this.moreType = moreType;
        messList = new JList(dataModel);
        selfDefine();
    }

    /**
     * 自定义一些属性。
     */
    private void selfDefine() {
        messList.setOpaque(true);
        messList.setVisibleRowCount(messList.getModel().getSize());
        messList.addMouseMotionListener(new JIconListMouseListener(this));
        messList.addMouseListener(new JIconListMouseListener(this));
        messList.setCellRenderer(new IconListRenderer());

        moreLabel = new JMoreLabel("更多...", JLabel.RIGHT);
        moreLabel.setType(moreType);
        moreLabel.addMouseListener(new MoreMouseListener());

        JScrollPane scPane = new JScrollPane(messList);
        scPane.setBorder(null);
        scPane.setBackground(Color.white);

        contents = new JPanel();
        contents.setBackground(Color.white);
        contents.setLayout(new VerticalFlowLayout(VerticalFlowLayout.TOP));
        contents.setBorder(BorderFactory.createTitledBorder(titleName));
        contents.add(messList);
        contents.add(moreLabel);
    }

    public JPanel getParentPanel() {
        return contents;
    }

    public JList getList() {
        return messList;
    }

    /**
     * Delegate method void addListSelectionListener(ListSelectionListener
     * listener) to messList : JList
     *
     * @param listener ListSelectionListener
     */
    public void addListSelectionListener(ListSelectionListener listener) {
        messList.addListSelectionListener(listener);
    }

    /**
     * Delegate method void addSelectionInterval(int anchor, int lead) to
     * messList : JList
     *
     * @param anchor int
     * @param lead int
     */
    public void addSelectionInterval(int anchor, int lead) {
        messList.addSelectionInterval(anchor, lead);
    }

    /**
     * Delegate method void clearSelection() to messList : JList
     */
    public void clearSelection() {
        messList.clearSelection();
    }

    /**
     * Delegate method void ensureIndexIsVisible(int index) to messList : JList
     *
     * @param index int
     */
    public void ensureIndexIsVisible(int index) {
        messList.ensureIndexIsVisible(index);
    }

    /**
     * Delegate method int getAnchorSelectionIndex() to messList : JList
     *
     * @return int
     */
    public int getAnchorSelectionIndex() {
        return messList.getAnchorSelectionIndex();
    }

    /**
     * Delegate method java.awt.Rectangle getCellBounds(int index0, int index1)
     * to messList : JList
     *
     * @param index0 int
     * @param index1 int
     * @return Rectangle
     */
    public Rectangle getCellBounds(int index0, int index1) {
        return messList.getCellBounds(index0, index1);
    }

    /**
     * Delegate method javax.swing.ListCellRenderer getCellRenderer() to messList
     * : JList
     *
     * @return ListCellRenderer
     */
    public ListCellRenderer getCellRenderer() {
        return messList.getCellRenderer();
    }

    /**
     * Delegate method boolean getDragEnabled() to messList : JList
     *
     * @return boolean
     */
    public boolean getDragEnabled() {
        return messList.getDragEnabled();
    }

    /**
     * Delegate method int getFirstVisibleIndex() to messList : JList
     *
     * @return int
     */
    public int getFirstVisibleIndex() {
        return messList.getFirstVisibleIndex();
    }

    /**
     * Delegate method int getFixedCellHeight() to messList : JList
     *
     * @return int
     */
    public int getFixedCellHeight() {
        return messList.getFixedCellHeight();
    }

    /**
     * Delegate method int getFixedCellWidth() to messList : JList
     *
     * @return int
     */
    public int getFixedCellWidth() {
        return messList.getFixedCellWidth();
    }

    /**
     * Delegate method int getLastVisibleIndex() to messList : JList
     *
     * @return int
     */
    public int getLastVisibleIndex() {
        return messList.getLastVisibleIndex();
    }

    /**
     * Delegate method int getLayoutOrientation() to messList : JList
     *
     * @return int
     */
    public int getLayoutOrientation() {
        return messList.getLayoutOrientation();
    }

    /**
     * Delegate method int getLeadSelectionIndex() to messList : JList
     *
     * @return int
     */
    public int getLeadSelectionIndex() {
        return messList.getLeadSelectionIndex();
    }

    /**
     * Delegate method javax.swing.event.ListSelectionListener[]
     * getListSelectionListeners() to messList : JList
     *
     * @return ListSelectionListener[]
     */
    public ListSelectionListener[] getListSelectionListeners() {
        return messList.getListSelectionListeners();
    }

    /**
     * Delegate method int getMaxSelectionIndex() to messList : JList
     *
     * @return int
     */
    public int getMaxSelectionIndex() {
        return messList.getMaxSelectionIndex();
    }

    /**
     * Delegate method int getMinSelectionIndex() to messList : JList
     *
     * @return int
     */
    public int getMinSelectionIndex() {
        return messList.getMinSelectionIndex();
    }

    /**
     * Delegate method javax.swing.ListModel getModel() to messList : JList
     *
     * @return ListModel
     */
    public ListModel getModel() {
        return messList.getModel();
    }

    /**
     * Delegate method int getNextMatch(String prefix, int startIndex, Bias bias)
     * to messList : JList
     *
     * @param prefix String
     * @param startIndex int
     * @param bias Bias
     * @return int
     */
    public int getNextMatch(String prefix, int startIndex, Bias bias) {
        return messList.getNextMatch(prefix, startIndex, bias);
    }

    /**
     * Delegate method java.awt.Dimension getPreferredScrollableViewportSize() to
     * messList : JList
     *
     * @return Dimension
     */
    public Dimension getPreferredScrollableViewportSize() {
        return messList.getPreferredScrollableViewportSize();
    }

    /**
     * Delegate method java.lang.Object getPrototypeCellValue() to messList :
     * JList
     *
     * @return Object
     */
    public Object getPrototypeCellValue() {
        return messList.getPrototypeCellValue();
    }

    /**
     * Delegate method int getScrollableBlockIncrement(Rectangle visibleRect, int
     * orientation, int direction) to messList : JList
     *
     * @param visibleRect Rectangle
     * @param orientation int
     * @param direction int
     * @return int
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return messList.getScrollableBlockIncrement(visibleRect, orientation, direction);
    }

    /**
     * Delegate method boolean getScrollableTracksViewportHeight() to messList :
     * JList
     *
     * @return boolean
     */
    public boolean getScrollableTracksViewportHeight() {
        return messList.getScrollableTracksViewportHeight();
    }

    /**
     * Delegate method boolean getScrollableTracksViewportWidth() to messList :
     * JList
     *
     * @return boolean
     */
    public boolean getScrollableTracksViewportWidth() {
        return messList.getScrollableTracksViewportWidth();
    }

    /**
     * Delegate method int getScrollableUnitIncrement(Rectangle visibleRect, int
     * orientation, int direction) to messList : JList
     *
     * @param visibleRect Rectangle
     * @param orientation int
     * @param direction int
     * @return int
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return messList.getScrollableUnitIncrement(visibleRect, orientation, direction);
    }

    /**
     * Delegate method int getSelectedIndex() to messList : JList
     *
     * @return int
     */
    public int getSelectedIndex() {
        return messList.getSelectedIndex();
    }

    /**
     * Delegate method int[] getSelectedIndices() to messList : JList
     *
     * @return int[]
     */
    public int[] getSelectedIndices() {
        return messList.getSelectedIndices();
    }

    /**
     * Delegate method java.lang.Object getSelectedValue() to messList : JList
     *
     * @return Object
     */
    public Object getSelectedValue() {
        return messList.getSelectedValue();
    }

    /**
     * Delegate method java.lang.Object[] getSelectedValues() to messList : JList
     *
     * @return Object[]
     */
    public Object[] getSelectedValues() {
        return messList.getSelectedValues();
    }

    /**
     * Delegate method java.awt.Color getSelectionBackground() to messList : JList
     *
     * @return Color
     */
    public Color getSelectionBackground() {
        return messList.getSelectionBackground();
    }

    /**
     * Delegate method java.awt.Color getSelectionForeground() to messList : JList
     *
     * @return Color
     */
    public Color getSelectionForeground() {
        return messList.getSelectionForeground();
    }

    /**
     * Delegate method int getSelectionMode() to messList : JList
     *
     * @return int
     */
    public int getSelectionMode() {
        return messList.getSelectionMode();
    }

    /**
     * Delegate method javax.swing.ListSelectionModel getSelectionModel() to
     * messList : JList
     *
     * @return ListSelectionModel
     */
    public ListSelectionModel getSelectionModel() {
        return messList.getSelectionModel();
    }

    /**
     * Delegate method javax.swing.plaf.ListUI getUI() to messList : JList
     *
     * @return ListUI
     */
    public ListUI getUI() {
        return messList.getUI();
    }

    /**
     * Delegate method boolean getValueIsAdjusting() to messList : JList
     *
     * @return boolean
     */
    public boolean getValueIsAdjusting() {
        return messList.getValueIsAdjusting();
    }

    /**
     * Delegate method int getVisibleRowCount() to messList : JList
     *
     * @return int
     */
    public int getVisibleRowCount() {
        return messList.getVisibleRowCount();
    }

    /**
     * Delegate method java.awt.Point indexToLocation(int index) to messList :
     * JList
     *
     * @param index int
     * @return Point
     */
    public Point indexToLocation(int index) {
        return messList.indexToLocation(index);
    }

    /**
     * Delegate method boolean isSelectedIndex(int index) to messList : JList
     *
     * @param index int
     * @return boolean
     */
    public boolean isSelectedIndex(int index) {
        return messList.isSelectedIndex(index);
    }

    /**
     * Delegate method boolean isSelectionEmpty() to messList : JList
     *
     * @return boolean
     */
    public boolean isSelectionEmpty() {
        return messList.isSelectionEmpty();
    }

    /**
     * Delegate method int locationToIndex(Point location) to messList : JList
     *
     * @param location Point
     * @return int
     */
    public int locationToIndex(Point location) {
        return messList.locationToIndex(location);
    }

    /**
     * Delegate method void removeListSelectionListener(ListSelectionListener
     * listener) to messList : JList
     *
     * @param listener ListSelectionListener
     */
    public void removeListSelectionListener(ListSelectionListener listener) {
        messList.removeListSelectionListener(listener);
    }

    /**
     * Delegate method void removeSelectionInterval(int index0, int index1) to
     * messList : JList
     *
     * @param index0 int
     * @param index1 int
     */
    public void removeSelectionInterval(int index0, int index1) {
        messList.removeSelectionInterval(index0, index1);
    }

    /**
     * Delegate method void setCellRenderer(ListCellRenderer cellRenderer) to
     * messList : JList
     *
     * @param cellRenderer ListCellRenderer
     */
    public void setCellRenderer(ListCellRenderer cellRenderer) {
        messList.setCellRenderer(cellRenderer);
    }

    /**
     * Delegate method void setDragEnabled(boolean b) to messList : JList
     *
     * @param b boolean
     */
    public void setDragEnabled(boolean b) {
        messList.setDragEnabled(b);
    }

    /**
     * Delegate method void setFixedCellHeight(int height) to messList : JList
     *
     * @param height int
     */
    public void setFixedCellHeight(int height) {
        messList.setFixedCellHeight(height);
    }

    /**
     * Delegate method void setFixedCellWidth(int width) to messList : JList
     *
     * @param width int
     */
    public void setFixedCellWidth(int width) {
        messList.setFixedCellWidth(width);
    }

    /**
     * Delegate method void setLayoutOrientation(int layoutOrientation) to
     * messList : JList
     *
     * @param layoutOrientation int
     */
    public void setLayoutOrientation(int layoutOrientation) {
        messList.setLayoutOrientation(layoutOrientation);
    }

    /**
     * Delegate method void setListData(Object[] listData) to messList : JList
     *
     * @param listData Object[]
     */
    public void setListData(Object[] listData) {
        messList.setListData(listData);
        ListModel models = messList.getModel();
        if (models != null)
            messList.setVisibleRowCount(models.getSize());    }

    /**
     * Delegate method void setListData(Vector listData) to messList : JList
     *
     * @param listData Vector
     */
    public void setListData(Vector listData) {
        messList.setListData(listData);
        ListModel models = messList.getModel();
        if (models != null)
            messList.setVisibleRowCount(models.getSize());
    }

    /**
     * Delegate method void setModel(ListModel model) to messList : JList
     *
     * @param model ListModel
     */
    public void setModel(ListModel model) {
        messList.setModel(model);
        ListModel models = messList.getModel();
        if (models != null)
            messList.setVisibleRowCount(models.getSize());    }

    /**
     * Delegate method void setPrototypeCellValue(Object prototypeCellValue) to
     * messList : JList
     *
     * @param prototypeCellValue Object
     */
    public void setPrototypeCellValue(Object prototypeCellValue) {
        messList.setPrototypeCellValue(prototypeCellValue);
    }

    /**
     * Delegate method void setSelectedIndex(int index) to messList : JList
     *
     * @param index int
     */
    public void setSelectedIndex(int index) {
        messList.setSelectedIndex(index);
    }

    /**
     * Delegate method void setSelectedIndices(int[] indices) to messList : JList
     *
     * @param indices int[]
     */
    public void setSelectedIndices(int[] indices) {
        messList.setSelectedIndices(indices);
    }

    /**
     * Delegate method void setSelectedValue(Object anObject, boolean
     * shouldScroll) to messList : JList
     *
     * @param anObject Object
     * @param shouldScroll boolean
     */
    public void setSelectedValue(Object anObject, boolean shouldScroll) {
        messList.setSelectedValue(anObject, shouldScroll);
    }

    /**
     * Delegate method void setSelectionBackground(Color selectionBackground) to
     * messList : JList
     *
     * @param selectionBackground Color
     */
    public void setSelectionBackground(Color selectionBackground) {
        messList.setSelectionBackground(selectionBackground);
    }

    /**
     * Delegate method void setSelectionForeground(Color selectionForeground) to
     * messList : JList
     *
     * @param selectionForeground Color
     */
    public void setSelectionForeground(Color selectionForeground) {
        messList.setSelectionForeground(selectionForeground);
    }

    /**
     * Delegate method void setSelectionInterval(int anchor, int lead) to
     * messList : JList
     *
     * @param anchor int
     * @param lead int
     */
    public void setSelectionInterval(int anchor, int lead) {
        messList.setSelectionInterval(anchor, lead);
    }

    /**
     * Delegate method void setSelectionMode(int selectionMode) to messList :
     * JList
     *
     * @param selectionMode int
     */
    public void setSelectionMode(int selectionMode) {
        messList.setSelectionMode(selectionMode);
    }

    /**
     * Delegate method void setSelectionModel(ListSelectionModel selectionModel)
     * to messList : JList
     *
     * @param selectionModel ListSelectionModel
     */
    public void setSelectionModel(ListSelectionModel selectionModel) {
        messList.setSelectionModel(selectionModel);
    }

    /**
     * Delegate method void setUI(ListUI ui) to messList : JList
     *
     * @param ui ListUI
     */
    public void setUI(ListUI ui) {
        messList.setUI(ui);
    }

    /**
     * Delegate method void setValueIsAdjusting(boolean b) to messList : JList
     *
     * @param b boolean
     */
    public void setValueIsAdjusting(boolean b) {
        messList.setValueIsAdjusting(b);
    }

    /**
     * Delegate method void setVisibleRowCount(int visibleRowCount) to messList :
     * JList
     *
     * @param visibleRowCount int
     */
    public void setVisibleRowCount(int visibleRowCount) {
        messList.setVisibleRowCount(visibleRowCount);
    }

    /**
     * Delegate method void setIncreObject(Object object) to moreLabel :
     * JMoreLabel
     *
     * @param object Object
     */
    public void setIncreObject(Object object) {
        moreLabel.setIncreObject(object);
    }

    /**
     * Delegate method void setType(String type) to moreLabel : JMoreLabel
     *
     * @param type String
     */
    public void setType(String type) {
        this.moreType = type;
        moreLabel.setType(type);
    }

    /**
     *
     * @return String
     */
    public String getType() {
        return this.moreType;
    }

    class MoreMouseListener
        extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            JMoreLabel label = (JMoreLabel) e.getSource();
            if (label.isHelpable()) {
                try {
                    JActiveDComDM.MainApplication.BeginWaitCursor();
                    JMessageShowWindow win = new JMessageShowWindow(label.getType());
                    JActiveDComDM.MainApplication.OpenObjectWindow("消息查看",null,"消息查看",win);
                } finally {
                    JActiveDComDM.MainApplication.EndWaitCursor();
                }
            }
        }

        public void mouseEntered(MouseEvent e) {
            JMoreLabel label = (JMoreLabel) e.getSource();
            if (label.isHelpable())
                label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

}
