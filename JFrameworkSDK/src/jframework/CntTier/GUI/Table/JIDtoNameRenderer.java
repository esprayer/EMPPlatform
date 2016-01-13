package jframework.CntTier.GUI.Table;

import javax.swing.table.DefaultTableCellRenderer;

import com.efounder.eai.data.JParamObject;

import jframework.foundation.classes.*;

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
public class JIDtoNameRenderer extends DefaultTableCellRenderer {
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public JIDtoNameRenderer() {
    }
    //----------------------------------------------------------------------------------------------
    //描述:
    //设计: Skyline(2001.12.29)
    //实现: Skyline
    //修改:
    //----------------------------------------------------------------------------------------------
    public void setValue(Object value) {
        if ( value instanceof JIDtoName ) {
            JIDtoName c = (JIDtoName)value;
            if ( c != null ){
              if(c.ID_Type.equals("F_DWBH")){
                if(showDwAllName()){
                  setText(c.toDisplayName1());
                }else{
                  setText(c.toDisplayName2());
                }
              }else{
                setText(c.toDisplayName1());
              }
              this.setToolTipText(c.toDisplayAllName());
            }
        } else {
            super.setValue(value);
        }
    }
    //
    private final boolean showDwAllName(){
      JParamObject PO;
      String temp,sDatabase,sUserBh;
      PO = JParamObject.Create();
      sDatabase = PO.GetValueByEnvName("DataBaseName","");
      sUserBh   = PO.GetValueByEnvName("UserName","");
      temp = JActiveDComDM.LocalRegistry.Get(sDatabase+sUserBh+"SHOWDWMC", "zh");
      if(temp != null && temp.equals("1")){
        return true;
      }else{
        return false;
      }
    }
}
