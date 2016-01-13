package jservice.jbof.classes.monitor.chart;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;

import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TChartMonitor extends JPanel{
  private final static String mDftTitle = "BISKernel";
  private XYSeriesCollection collection = null;
  private final static int    mMAX      = 1 * 60 * 2;

  BorderLayout borderLayout1 = new BorderLayout();
  private JFreeChart        mChart      = null;
  private ChartPanel        mChartPanel = null;

  public TChartMonitor() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * 准备
   * @return
   */
  public boolean Prepare(Vector pValues,int[] pChannIDs,String[] pChanNames) {
    removeAll();
    /**
     * 图表的标题.
     */
    String mTitle = mDftTitle;

    /**
     * 准备数据
     */
    // create subplot 1...
    XYDataset data1 = createDataset(pValues, pChannIDs, pChanNames);

    XYItemRenderer renderer1 = new StandardXYItemRenderer();
    NumberAxis rangeAxis1 = new NumberAxis("数值");

    XYPlot subplot1 = new XYPlot(data1, null, rangeAxis1, renderer1);

    subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

    subplot1.setBackgroundPaint(Color.white);

    NumberAxis rangeAll = new NumberAxis("时间");

    // parent plot...
    CombinedDomainXYPlot plot = new CombinedDomainXYPlot(rangeAll);
    plot.setGap(10.0);

    // add the subplots...
    plot.add(subplot1, 1);
    plot.setBackgroundPaint(Color.white);

    //生成曲线图表
    mChart = new JFreeChart(mTitle, JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    mChart.setTitle(mTitle);
    mChart.setBackgroundPaint(Color.white);

    //加和显示面板中
    mChartPanel = new ChartPanel(mChart);
    this.add(mChartPanel, BorderLayout.CENTER);
    return true;

  }

  /**
   * 初始化
   * @return
   */
  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
  }


  /**
   * 建立一个曲线
   * @param IDS
   * @param strFields
   * @param strIDField
   * @param strFieldNames
   * @return
   */
  private XYDataset createDataset( Vector pValues,int[] pChannIDs,String[] pChanNames){
    collection = new XYSeriesCollection();
    for( int i = 0; i < pChannIDs.length; i++ ){
      String FName = pChanNames[i];
      int     iCurChannal = pChannIDs[i];
      XYSeries series1   = new TMyXYSeries( FName,iCurChannal );
      int      iDataRows = pValues.size();
      int      iLineIndex = 0;
      for( int iRow = 0; iRow < iDataRows; iRow++ ){
        long[]  pMVal = (long[])(pValues.get(iRow));
        if ( pMVal[0] != iCurChannal){
          continue;
        }
        else{
          //TLineItem II = new TLineItem(iLineIndex,pMVal[1],pChanNames[i]);
          TLineItem II = new TLineItem(iLineIndex, pMVal[1], "");
          series1.add(II);
          iLineIndex++;
        }
      }
      collection.addSeries( series1 );
    }
    return collection;
  }


  /**
   * 建立一个曲线
   * @param IDS
   * @param strFields
   * @param strIDField
   * @param strFieldNames
   * @return
   */
  public void appendValues( Vector pValues){
    if ( collection == null ){
      return;
    }

    int    iSeriesCount = collection.getSeriesCount();

    for( int i = 0; i < iSeriesCount; i++ ){
      TMyXYSeries pSeries = (TMyXYSeries)collection.getSeries(i);

      int     iCurChannal = pSeries.getChannel();
      int      iDataRows = pValues.size();
      int      iLineIndex = pSeries.getItemCount();
      for( int iRow = 0; iRow < iDataRows; iRow++ ){
        long[]  pMVal = (long[])(pValues.get(iRow));
        if ( pMVal[0] != iCurChannal){
          continue;
        }
        else{

          /**
           * 如果数据过多，则清除之。
           */
          if ( pSeries.getItemCount() > mMAX ){
            //java.util.List pList = pSeries.getItems();
            //pSeries.clear();
          }

          /**
           * 加入新的元素。
           */
          TLineItem II = new TLineItem(iLineIndex, pMVal[1], "");
          pSeries.add(II);
          iLineIndex++;
        }
      }

    }

    mChartPanel.updateUI();
  }

}
