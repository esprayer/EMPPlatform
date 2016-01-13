package com.efounder.eai.ui.action.fileaction;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public interface FileActionInterface {
  public boolean canImport();
  public void importData();
  public boolean canExport();
  public void exportData();
  public boolean canPagesetup();
  public void    pageSetup();
  // 是否可以打印
  public boolean canPrint();
  // 是否可以预览
  public boolean canPreview();
  // 是否可以保存
  public boolean canSave();
  public boolean canSaveAs();
  // 是否已修改
  public boolean isModified();
  public void   setModified(boolean v);
  //新建
  public void newNode();
  // 打印
  public void printNode();
  // 预览
  public void previewNode();
  // 保存
  public void saveNode() throws Exception;
  public void saveAsNode();
}
