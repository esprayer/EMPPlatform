package jfoundation.gui.window.classes;

import java.awt.*;

import javax.swing.*;

import com.efounder.eai.data.JParamObject;

import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.util.Locale;
import jframework.foundation.classes.JActiveDComDM;

/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Skyline
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//     (�ڴ��ļ��п���������Դ�ļ�)
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JFrameDialog extends JDialog implements WindowListener {
	public static         int       RESULT_OK = 1;
	public static         int   RESULT_CANCEL = 0;
	public                int          Result = 0;
	public       JParamObject              PO = null;
	protected          Object    CustomObject;
	private         Dimension        mMinSize = new Dimension(0, 0);
	/**
	 * ���ڴ����ں�ʱ�䣬������־��¼
	 * add by hufeng 2006.7.3
	 */
	protected String mStartDate = "";
	protected String mStartTime = "";
	//----------------------------------------------------------------------------------------------
	//����: ���캯��
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:JActiveDComDM.MainApplication.getMainWindow()
	//----------------------------------------------------------------------------------------------
	public JFrameDialog(Frame frame, String title, boolean modal) {
		/**
		 * JActiveDComDM.MainApplication ����Ϊnull
		 */
//      super(JActiveDComDM.MainApplication.getMainWindow(),title,modal);
		super(frame,title,modal);
		this.addWindowListener(this);
		/**
		 * ��¼��־
		 */
//      if(mStartDate.equals("")){
//        getLogDate();
//      }
      //LoadResource();
	}
//  public JFrameDialog(JBOFMainWindow frame, String title, boolean modal) {
//      super((Frame)null,title,modal);
//      //LoadResource();
//  }
	/**
	 *
	 * @param parent
	 * @param title
	 * @param isModal
	 */
	public JFrameDialog(Dialog parent, String title, boolean isModal) {
		super(parent, title, isModal);
		this.addWindowListener(this);
      /**
       * ��¼��־
       */
//      if(mStartDate.equals("")){
//        getLogDate();
//      }
	}

	//----------------------------------------------------------------------------------------------
	//����: ���캯��
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public JFrameDialog() {
		super();
		this.addWindowListener(this);
		try {
			jbPInit();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	void jbPInit() throws Exception {
	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void CenterWindow() {
		Dimension dlgSize = this.getSize();
		Dimension ScnSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((ScnSize.width - dlgSize.width)/2, (ScnSize.height - dlgSize.height)/2);
	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public int OnOk() {
		//��¼��־
		this.writeLog();
		Result = RESULT_OK;

		this.hide();
//       removeNotify();
		this.dispose();
		return Result;
	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public int OnCancel() {
		//��¼��־
		this.writeLog();
		Result = RESULT_CANCEL;
		this.hide();
		dispose();
		return Result;
	}

	/**
	 * ����ע��ʱӦ�ȼ�¼��־
	 * modified by hufeng 2006.8.15
	 */
	public void dispose() {
		this.writeLog();
		super.dispose();
	}

	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void setCustomObject(Object O) {
		CustomObject = O;
	}
	public Object getCustomObject() {
		return CustomObject;
	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void InitDialog() {

	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void DestroyDialog() {

	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void Show() {
		if ( this.isVisible() ) return;
		InitDialog();
		CenterWindow();
		super.show();
		CenterWindow();
	}
	//----------------------------------------------------------------------------------------------
	//����:
	//���: Skyline(2001.12.29)
	//ʵ��: Skyline
	//�޸�:
	//----------------------------------------------------------------------------------------------
	public void Hide() {
		if ( !this.isVisible() ) return;
		DestroyDialog();
		super.hide();
	}

	/**
	 *
	 * @param width
	 * @param height
	 */
	public void setMinimumSize(int width, int height) {
		this.setMinimumSize(new Dimension(width, height));
	}

	/**
	 *
	 * @param minSize
	 */
	public void setMinimumSize(Dimension minSize) {
		if (minSize != null) {
			this.mMinSize = minSize;
		}
	}

	/**
	 * ȡ����־ʱ��
	 */
	private void getLogDate(){
//    if(JLogManager.isWriteLog()){
//      String date[] = JLogManager.getCurrentDateTime();
//      if(date != null){
//        mStartDate = date[0];
//        mStartTime = date[1];
//      }
//    }
	}

	/**
	 * ��¼��־
	 * �ڴ��ڹر�ʱ��Ҫ��¼������־
	 */
	private void writeLog(){
//    if(JLogManager.isWriteLog()){
//      String date,time,title;
//      title = this.getTitle();
//      if(title == null || title.trim().equals("")){
//        return;
//      }
//      String dateTime[] = JLogManager.getCurrentDateTime();
//      //ʱ��Ϊ��ʱ��������ϵͳ��¼����
//      if(dateTime == null){
//        return;
//      }
//      date = dateTime[0];
//      time = dateTime[1];
//      JLogManager.writeLog(title,mStartDate,mStartTime,date,time);
//    }
	}

	/**
	 *
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * <p>Copyright: Copyright (c) 2004</p>
	 * <p>Company: </p>
	 * @author not attributable
	 * @version 1.0
	 */
	class MyRootPane extends JRootPane {
		JDialog mParentDialog = null;
		public MyRootPane(JDialog parentDialog) {
			this.mParentDialog = parentDialog;
		}

		public void reshape(int i, int j, int k, int l) {
			Dimension dimension = mMinSize;
			if (dimension != null && (k < dimension.width || l < dimension.height)) {
				Dimension dimension1 = mParentDialog.getSize();
				if (k < dimension.width) {
					dimension1.width = (dimension1.width - k) + dimension.width;
					k = dimension.width;
				}
				if (l < dimension.height) {
					dimension1.height = (dimension1.height - l) + dimension.height;
					l = dimension.height;
				}
				mParentDialog.setSize(dimension1);
			}
			super.reshape(i, j, k, l);
		}
	}

	public void windowActivated(WindowEvent e) {
//		System.out.println(0);
	}

	public void windowClosed(WindowEvent e) {
//		System.out.println(1);
	}

	public void windowClosing(WindowEvent e) {
		OnCancel();
//		System.out.println(2);
	}

	public void windowDeactivated(WindowEvent e) {
//		System.out.println(3);
	}

	public void windowDeiconified(WindowEvent e) {
//		System.out.println(4);
	}

	public void windowIconified(WindowEvent e) {
//		System.out.println(5);
	}

	public void windowOpened(WindowEvent e) {
//		System.out.println(6);
	}
}
