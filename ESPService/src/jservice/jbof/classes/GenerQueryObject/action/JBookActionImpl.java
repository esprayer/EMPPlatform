package jservice.jbof.classes.GenerQueryObject.action;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Pansoft</p>
 * @author Stephen Zhao
 * @version 1.0
 */

public interface JBookActionImpl {
    //常规
    public void onFind(JBookAction action);

    public void onReplace(JBookAction action);

    public void onDelete(JBookAction action);

    public void onGoto(JBookAction action);

    public void onImport(JBookAction action);

    public void onExport(JBookAction action);

    //编辑
    public void onCut(JBookAction action);

    public void onCopy(JBookAction action);

    public void onPaste(JBookAction action);

    public void onInsertRow(JBookAction action);

    public void onInsertCol(JBookAction action);

    public void onInsertWorksheet(JBookAction action);

    public void onInsertPageBreak(JBookAction action);

    public void onInsertHyperLink(JBookAction action);

    public void onInsertGraphicsNormal(JBookAction action);

    public void onInsertGraphicsChart(JBookAction action);

    public void onInsertGraphicsPicture(JBookAction action);

    public void onInsertGraphicsCurve(JBookAction action);

    public void onInsertGraphicsLine(JBookAction action);

    public void onInsertGraphicsRound(JBookAction action);

    public void onInsertGraphicsPolygon(JBookAction action);

    public void onInsertGraphicsRectangle(JBookAction action);

    public void onInsertFormButton(JBookAction action);

    public void onInsertFormCheckbox(JBookAction action);

    public void onInsertFormCombobox(JBookAction action);

    public void onInsertFormList(JBookAction action);

    public void onInsertFormRationButton(JBookAction action);

    public void onInsertFormTextArea(JBookAction action);

    public void onDeleteRow(JBookAction action);

    public void onDeleteCol(JBookAction action);

    public void onDeleteWorksheet(JBookAction action);

    public void onDeletePageBreak(JBookAction action);

    public void onDeleteHyperLink(JBookAction action);

//格式
    public void onCell(JBookAction action);

    public void onBold(JBookAction action);

    public void onItalic(JBookAction action);

    public void onUnderline(JBookAction action);

    public void onLeft(JBookAction action);

    public void onCenter(JBookAction action);

    public void onRight(JBookAction action);

    public void onLeft2Right(JBookAction action);

    public void onTop2Bottom(JBookAction action);

    public void onMoney(JBookAction action);

    public void onPercent(JBookAction action);

    public void onDot(JBookAction action);

    public void onAddZero(JBookAction action);

    public void onDeleteZero(JBookAction action);

    public void onSubSpace(JBookAction action);

    public void onAddSpace(JBookAction action);

    public void onBorder(JBookAction action);

    public void onFontColor(JBookAction action);

    public void onBackColor(JBookAction action);

    public void onTop(JBookAction action);

    public void onVerticalCenter(JBookAction action);

    public void onBottom(JBookAction action);

    public void onMerge(JBookAction action);

    public void onGraphics(JBookAction action);

    public void onRowHeight(JBookAction action);

    public void onRowPreferHeight(JBookAction action);

    public void onRowHide(JBookAction action);

    public void onRowShow(JBookAction action);

    public void onColWidth(JBookAction action);

    public void onColPreferWidth(JBookAction action);

    public void onColHide(JBookAction action);

    public void onColShow(JBookAction action);

    public void onWorksheetRename(JBookAction action);

    public void onWorksheetHide(JBookAction action);

    public void onWorksheetShow(JBookAction action);

    public void onWorksheetAttribute(JBookAction action);

    public void onObjectOption(JBookAction action);

    public void onObjectBring2Front(JBookAction action);

    public void onObjectSend2Back(JBookAction action);

    public void onObjectUngroup(JBookAction action);

    public void onObjectGroup(JBookAction action);

    public void onFreezePanes(JBookAction action);

    public void onDefreezePanes(JBookAction action);

    //打印
    public void onPrintPageSetup(JBookAction action);

    public void onPrintArea(JBookAction action);

    public void onCancelPrintArea(JBookAction action);

    public void onPrintTitle(JBookAction action);

    public void onPrintPreview(JBookAction action);

    public void onPrint(JBookAction action);

}