package com.efounder.eai.application.classes;

import org.jdom.*;

import com.efounder.resource.JResource;

import javax.swing.ImageIcon;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.04.22)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
public class JBOFApplicationStub {
  public String name;
  public String modulexml;
  public String description;
  public String img1;
  public String img2;
  public String img3;
  public String img4;
  public String img5;

  public String XML_OPERATE;        // 操作定义的XML描述
  public String XML_STYLE;          // 当前应用的窗口模式
  public String XML_STYLE_MDI;      // MDI
  public String XML_STYLE_WIZARD;   // WIZARD
  public String XML_STYLE_EXPLORER; // EXPLORER

  public Object AddObject;          // 附加的对象
  public Object AddObject1;         // 附加的对象1

  private ImageIcon mIcon = null;

  public Element        ApplicationAttrib         = null; // 应用在注册表中公用属性元素
  //------------------------------------------------------------------------------------------------
  //描述:
  //设计: Skyline(2001.12.29)
  //实现: Skyline
  //修改:
  //------------------------------------------------------------------------------------------------
  public JBOFApplicationStub() {
  }
  public void setIconName(String pname){
    img1 = pname;
  }

  public String getIconName(){
    return img1;
  }

  public  ImageIcon getIcon(){
    if (mIcon == null ){
      if ( img1 != null && !img1.equals("")){
        mIcon = JResource.LoadImageIcon(this,img1);
      }
    }
    return mIcon;
  }
}
