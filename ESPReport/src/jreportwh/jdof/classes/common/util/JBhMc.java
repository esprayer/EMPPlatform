package jreportwh.jdof.classes.common.util;

import java.util.ResourceBundle;

/**
 * <p>Title: ComboBox框中的对象，此对象包含两个属性，编号与名称，显示为名称</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: pansoft</p>
 * @author hufeng
 * @version 1.0
 */
public class JBhMc {
  static ResourceBundle res = ResourceBundle.getBundle("jreportwh.jdof.classes.common.util.Language");
  private String m_bh = "";
  private String m_mc = "";

  public JBhMc(String bh, String mc) {
    m_bh = bh;
    m_mc = mc;
  }

  public String toString() {
    return m_mc;
  }

  /**
   * 取编号
   * @return
   */
  public String getBh() {
    return m_bh;
  }

  /**
   * 取名称
   * @return
   */
  public String getMc() {
    return m_mc;
  }
}
