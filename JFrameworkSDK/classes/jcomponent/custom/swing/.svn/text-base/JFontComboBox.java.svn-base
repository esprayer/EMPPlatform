package jcomponent.custom.swing;

import javax.swing.JComboBox;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JFontComboBox extends JComboBox {
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JFontComboBox() {
    super();
    ListFont();
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  void ListFont() {
    String fontList[] =
    java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    for(int i=0;i<fontList.length;i++) {
      this.insertItemAt(fontList[i],i);
    }
  }
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public String FindFont(String FontName) {
    int Count = this.getModel().getSize();int i;String FN;
    for(i=0;i<Count;i++) {
      FN = (String)this.getItemAt(i);
      if ( FontName.equals(FN) ) {
        this.setSelectedIndex(i);
        return FN;
      }
    }
    this.setSelectedIndex(-1);
    return null;
  }
}