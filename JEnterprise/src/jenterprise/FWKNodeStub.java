package jenterprise;

import javax.swing.SwingUtilities;

import org.openide.WaitingManager;

import jbof.application.classes.operate.JOperateItemStub;
import jframework.foundation.classes.JActiveDComDM;

import com.core.xml.StubObject;
import com.efounder.eai.EAI;
import com.efounder.node.Context;
import com.efounder.node.ExecuteNode;
import com.efounder.node.JNodeStub;
import com.efounder.service.security.OperateLoginfo;
import com.efounder.service.security.ServiceSecurityManager;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class FWKNodeStub extends JNodeStub implements ExecuteNode, Runnable {
	/**
	 *
	 */
	public FWKNodeStub() {
	}

	/**
	 *
	 * @param nodeKey Object
	 * @param key String
	 * @param context Context
	 * @return Object
	 */
	public Object executeNode(Object nodeKey, String key, Context context) {
		JNodeStub        nodeStub = context.getMainNodeStub();
		StubObject     stubObject = nodeStub.getNodeStubObject();
		WaitingManager.getDefault().waitInvoke(EAI.EA.getMainWindow(), "系统正在打开节点" + stubObject.getCaption(), "系统正在打开节点" + stubObject.getCaption(), null, this );
		return null;
//		Object pRES=null;
//		Thread t = new Thread(new Runnable() {//SwingUtilities.invokeLater(
//			public void run() {
//				Object pRES=null;
//				String acObj = getNodeStubObject().getString("object",null);
//				String acMth = getNodeStubObject().getString("method",null);
//				if ( acObj == null || acMth == null ) return;//null
//
//				String param = getNodeStubObject().getString("param",null);
//				String data  = getNodeStubObject().getString("data",null);
//				JOperateItemStub ois = new JOperateItemStub();
//				ois.OperateObject = acObj;ois.OperateMethod = acMth;
//				ois.ParamString = param;ois.ParamData = data;
//				String gnbh = null;
//				StubObject stub = getNodeStubObject();
//				if ( stub != null )
//					gnbh = stub.getString("GNBH",null);
//				if ( gnbh == null && getID() != null ) gnbh = getID().toString();
//				if ( gnbh == null ) gnbh = this.getClass().getSimpleName();
//				String gnmc = this.toString();
//				// 开始记录日志
//				OperateLoginfo ql = ServiceSecurityManager.getDefault().createLoginfo(gnbh,gnmc);
//
//				try {
//					pRES = EAI.BOF.IOM(acObj, acMth, param,data, stub, JActiveDComDM.MainApplication);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				// 将日志写入数据库
//				ServiceSecurityManager.getDefault().writeOperateLog(ql);
//			}
//		});
//		t.start();
//    
//		return pRES;
	}
	
	public void run() {
		openNode();
	}
	
	public Object openNode() {
		Object pRES=null;
		String acObj = getNodeStubObject().getString("object",null);
		String acMth = getNodeStubObject().getString("method",null);
		if ( acObj == null || acMth == null ) return null;//null

		String param = getNodeStubObject().getString("param",null);
		String data  = getNodeStubObject().getString("data",null);
		JOperateItemStub ois = new JOperateItemStub();
		ois.OperateObject = acObj;ois.OperateMethod = acMth;
		ois.ParamString = param;ois.ParamData = data;
		String gnbh = null;
		StubObject stub = getNodeStubObject();
		if ( stub != null )
			gnbh = stub.getString("GNBH",null);
		if ( gnbh == null && getID() != null ) gnbh = getID().toString();
		if ( gnbh == null ) gnbh = this.getClass().getSimpleName();
		String gnmc = this.toString();
		// 开始记录日志
		OperateLoginfo ql = ServiceSecurityManager.getDefault().createLoginfo(gnbh,gnmc);
		try {
			pRES = EAI.BOF.IOM(acObj, acMth, param,data, stub, JActiveDComDM.MainApplication);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 将日志写入数据库
		ServiceSecurityManager.getDefault().writeOperateLog(ql);
		return pRES;
	}
}
