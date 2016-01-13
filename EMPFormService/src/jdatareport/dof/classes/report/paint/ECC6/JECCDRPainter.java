package jdatareport.dof.classes.report.paint.ECC6;

import jdatareport.dof.classes.report.paint.JDRPainter;
import jdatareport.dof.classes.report.JDRView;
import jdatareport.dof.classes.report.figure.JDRRptFigure;
import jdatareport.dof.classes.report.paint.JDRQueryPainter;
import jdatareport.dof.classes.report.util.JDRConstants;
import jdatareport.dof.classes.report.figure.JDRCellFigure;
import java.util.Enumeration;

/** 针对ECC6的查询结果绘制器
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class JECCDRPainter extends JDRPainter {
  /**
   *
   */
  public JECCDRPainter() {
    super(null,null);
  }
  /**
   *
   */
  public void draw() {
    try{
      JDRRptFigure rptFigure = mModel.getRptFigure();
      for (Enumeration en = rptFigure.getCellFigures().elements(); en.hasMoreElements(); ) {
          JDRCellFigure cellFigure = (JDRCellFigure) en.nextElement();
          if (cellFigure.getType().equals(JDRConstants.TYPE_QUERY)) {
            Object[] o = (Object[])mModel.getQuery("SingleQuery");
            queryPanter = new JDRQueryPainter(mBook, mModel, cellFigure);
            queryPanter.drawQuery(JDRView.FILTER_ALL,false);
          }else{
            super.draw();
          }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
