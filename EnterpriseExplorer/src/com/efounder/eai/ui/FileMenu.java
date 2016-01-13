package com.efounder.eai.ui;

import com.efounder.action.*;
import com.efounder.eai.ide.*;
import com.efounder.eai.ui.action.fileaction.*;
import java.util.ResourceBundle;

/**
 * <p>Title: Enterprise Explorer</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: EFounder</p>
 * @author Skyline
 * @version 1.0
 */

public class FileMenu {
	static ResourceBundle res = ResourceBundle.getBundle("com.efounder.eai.ui.Res");
	public static final ActionGroup GROUP_FileBar;
	public static final ActionGroup GROUP_File;
	public static final ActionGroup GROUP_FileNew   = new ExplorerActionGroup();
//  public static final ActionGroup GROUP_FileNewBar = new NewAllItemActionGroup(res.getString("KEY"),'0',res.getString("KEY1"),ExplorerIcons.getExplorerIcon("oicons/new.png"));//new ExplorerActionGroup(res.getString("KEY"),'0',res.getString("KEY1"),ExplorerIcons.getExplorerIcon("oicons/new.png"),false);
	public static final ActionGroup GROUP_FileOpen  = new ExplorerActionGroup();
//  public static final ActionGroup GROUP_FileReOpen  = new ReOpenExplorerActionGroup("重新打开",'0',"重新打开",ExplorerIcons.ICON_REOPEN,true);
	public static final ActionGroup GROUP_FileClose = new ExplorerActionGroup();
	public static final ActionGroup GROUP_FileSave  = new ExplorerActionGroup();
	public static final ActionGroup GROUP_FileSaveAll  = new SaveAllActionGroup(res.getString("KEY2"),'0',res.getString("KEY3"),ExplorerIcons.getExplorerIcon("oicons/saveall.png"),true);
	public static final ActionGroup GROUP_IM           = new ExplorerActionGroup(res.getString("KEY4"),'0',res.getString("KEY5"),ExplorerIcons.ICON_REOPEN,true);
	public static final ActionGroup GROUP_FilePrint = new ExplorerActionGroup();
	public static final ActionGroup GROUP_FileExit  = new ExplorerActionGroup();
	static {// 初始化系统工具栏
		GROUP_FileBar = new ExplorerActionGroup("系统",'0',"系统工具栏");
//    GROUP_FileNewBar.add(GROUP_FileNew);
		// 新建组
		GROUP_FileBar.add(NewAllItemActionGroup.newAllItemAction);//GROUP_FileNewBar
		// 打开
		GROUP_FileBar.add(OpenExplorerActioin.openFileAction);
		// 打开组
//		GROUP_FileBar.add(GROUP_FileReOpen);
		// 关闭
		GROUP_FileBar.add(CloseExplorerAction.closeFileAction);
		// 保存
		GROUP_FileBar.add(SaveExplorerAction.saveFileAction);
		// 全部保存组
		GROUP_FileBar.add(GROUP_FileSaveAll);
		// 页面设置
   	 	GROUP_FileBar.add(PagesetExplorerAction.pagesetFileAction);
   	 	// 打印预览
   	 	GROUP_FileBar.add(PreviewExplorerAction.previewFileAction);
   	 	// 打印
   	 	GROUP_FileBar.add(PrintExplorerAction.printFileAction);
	}
	static {// 初始化系统菜单
		GROUP_File    = new ExplorerActionGroup(res.getString("KEY45")+"[F]", 'D', true);
//		// 新建
//		GROUP_File.add(NewAllItemActionGroup.newAllItemAction);//GROUP_FileNewBar
//		// 打开
//		GROUP_File.add(GROUP_FileOpen);
////    GROUP_FileOpen.add(GROUP_FileReOpen);
//		// 关闭
//		GROUP_FileClose.add(CloseExplorerAction.closeFileAction);
//		GROUP_File.add(GROUP_FileClose);
//		// 保存
//		GROUP_FileSave.add(SaveExplorerAction.saveFileAction);
//		// 另存为
//		GROUP_FileSave.add(SaveAsExplorerAction.saveAsFileAction);
//		//
//		GROUP_FileSave.add(SaveAllItemExplorerAction.saveAllItemAction);
//		GROUP_File.add(GROUP_FileSave);
//		// 转入转出
//		ActionGroup ag = new ActionGroup();
//		ag.add(GROUP_IM);
//		GROUP_File.add(ag);
//		//
//		GROUP_IM.add(ImportDataExplorerAction.importDataAction);
//		GROUP_IM.add(ExportDataExplorerAction.exportDataAction);
//		// 打印
//		GROUP_FilePrint.add(PagesetExplorerAction.pagesetFileAction);
//		GROUP_FilePrint.add(PreviewExplorerAction.previewFileAction);
//		GROUP_FilePrint.add(PrintExplorerAction.printFileAction);
//		GROUP_File.add(GROUP_FilePrint);
//		GROUP_FileExit.add(ChangeCodeAction.chagePasswordAction);
		GROUP_FileExit.add(ChangepasswordAction.chagePasswordAction);
		// 退出
		GROUP_FileExit.add(ExitExplorerAction.exitFileAction);
		GROUP_File.add(GROUP_FileExit);
	}
	protected FileMenu() {
	}
}
