package jservice.jbof.classes.monitor.grid;


import javax.swing.table.DefaultTableModel;
import java.util.Hashtable;
import java.util.Vector;
/**
 * <p>Title: BISExplorer</p>
 * <p>Description: BIS探索器</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: BIS</p>
 * @author Fndlvr
 * @version 1.0
 */

public class TGridMonitorModel extends DefaultTableModel{
  private             String   mClassInfo    = "";
  private          Hashtable   mChannel2Row  = new Hashtable();
  private static    String[]   mTitle        = new String[]{"频道","数值"};
  private             long[]   mValues       = new long[1000];
  private           String[]   mChannelID         = new String[0];
  private           String[]   mChannelname       = new String[0];
  private              int[]   mChannelIdx        = new int[0];
  private                int   mRows              = 0;

  public TGridMonitorModel() {
  }

  public         void setupChannels(String[] pChannel){
    mChannel2Row.clear();
    mChannelID   = new String[pChannel.length];
    mChannelname = new String[pChannel.length];
    mChannelIdx  = new int[pChannel.length];
    for ( int i = 0; i < mChannelname.length; i ++){
      String pChanInfo = pChannel[i];
      String[] pInfo   = pChanInfo.split(":");

      mChannelID[i]    = pInfo[0];
      mChannelname[i]  = pInfo[1];


      //放入映射表.
      mChannel2Row.put(new Integer(i),new Integer(mChannelID[i]));
    }

    mRows      = pChannel.length;

  }

  /**
   * 根据行来得到映射的频道。
   * @param pVal int[]
   * @return int[]
   */
  public        int[] getMonitorChannels(int[] pVal){
    int[] pChannels = new int[pVal.length];

    for ( int i = 0; i < pChannels.length; i++){
      int iRow = pVal[i];
      Integer pKey = new Integer(iRow);
      Integer pCh  = (Integer)(mChannel2Row.get(pKey));

      pChannels[i] = pCh.intValue();
    }

    return pChannels;
  }

  public         void setupValues(Vector pvals){
    int iSize = pvals.size();

    for ( int i = 0; i < iSize; i ++){
      long[] pVal = (long[])(pvals.get(i));
      int    pIndex = (int)pVal[0];
      mValues[pIndex] = pVal[1];
    }

  }
  /**
   * 获得显示值.
   * @param rowIndex int
   * @param columnIndex int
   * @return Object
   */
  public Object getValueAt(int iRow, int iCol) {
    if ( iCol == 1 ){
      Object pOBJ = mChannel2Row.get(new Integer(iRow));
      if (pOBJ == null) {
        return "";
      }
      else{
        Integer pChannelID = (Integer)pOBJ;
        long    pVal       = mValues[pChannelID.intValue()];
        return  Long.toString(pVal);
      }
    }
    /*
    if ( iCol == 0 ){
      return mChannelID[iRow];
    }*/

    if ( iCol == 0 ){
      return mChannelname[iRow]+"("+mChannelID[iRow]+")";
    }

    return "";
  }

  public Class getColumnClass(int columnIndex) {
    return mClassInfo.getClass();
  }

  public int getColumnCount() {
    return mTitle.length;
  }

  public String getColumnName(int columnIndex) {
    return mTitle[columnIndex];
  }

  public int getRowCount() {
    return mRows;
  }


  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

}
