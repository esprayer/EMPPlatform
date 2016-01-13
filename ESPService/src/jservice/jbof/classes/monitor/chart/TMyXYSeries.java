package jservice.jbof.classes.monitor.chart;

import org.jfree.data.xy.XYSeries;

/**
 * <p>Title: BISExplorer</p>
 * <p>Description: BIS探索器</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: BIS</p>
 * @author Fndlvr
 * @version 1.0
 */

public class TMyXYSeries extends XYSeries{
  int   mChannelID = 0;
  public TMyXYSeries(String pName,int iChannel) {
    super(pName);

    mChannelID = iChannel;
  }

  public int getChannel(){
    return mChannelID;
  }

}
