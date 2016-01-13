package jreport.model.classes.calculate;

import jtoolkit.registry.classes.*;
import org.jdom.*;
import jframework.foundation.classes.*;
import java.util.*;
import jreport.value.JReportMask;
import jreport.foundation.classes.calculate.ICaObject;
import jreport.foundation.classes.calculate.IPerformance;
import jreport.foundation.classes.calculate.ISaveObject;
import jreport.jdal.classes.DALReportObject.JReportService;
import com.efounder.builder.base.util.ESPServerContext;
import com.efounder.eai.data.JParamObject;

import org.openide.util.RequestProcessor;
import com.efounder.util.GUID;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//����:
//���: Skyline(2001.04.22)
//ʵ��: Skyline
//�޸�:
//--------------------------------------------------------------------------------------------------
public class JCalculateManager {
static ResourceBundle res = ResourceBundle.getBundle("jreport.model.classes.calculate.Language");

  public Class ICaObjectClass = null;
  public Class IF1ObjectClass = null;
  public Class IPerformanceClass = null;
  public Class ISaveObjectClass = null;
  public Class IJYSaveObjectClass = null;
  public Class IFuncResultClass = null;

//  public ICaObject CaObject=null;
//  public IF1Object F1Object=null;
//  public IPerformance Performance=null;
//  public ISaveObject  SaveObject=null;

  public JFunctionManager FunctionManager = null;

  public static int RequestCount = 0;

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public JCalculateManager() {
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  synchronized public int GetRequestCount() {
    return RequestCount++;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public void InitObject(Element PublicElement, Element PrivateElement) {
    List nodelist;
    Element RFElement = null, CAElement = null;
    Element node;
    int i = 0;
    String Name = "CalculateManager";
    String CLName;
    RFElement = JActiveDComDM.XMLRegistry.GetElementByName(PublicElement, Name);
    //
    Name = "ICaObject";
    CAElement = JActiveDComDM.XMLRegistry.GetElementByName(RFElement, Name);
    if (CAElement != null) {
      CLName = CAElement.getAttribute("class").getValue();
      try {
        ICaObjectClass = Class.forName(CLName);
//        CaObject = (ICaObject)ICaObjectClass.newInstance();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    //
    Name = "IF1Object";
    CAElement = JActiveDComDM.XMLRegistry.GetElementByName(RFElement, Name);
    if (CAElement != null) {
      CLName = CAElement.getAttribute("class").getValue();
      try {
        IF1ObjectClass = Class.forName(CLName);
//        F1Object = (IF1Object)IF1ObjectClass.newInstance();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    //
    Name = "IPerformance";
    CAElement = JActiveDComDM.XMLRegistry.GetElementByName(RFElement, Name);
    if (CAElement != null) {
      CLName = CAElement.getAttribute("class").getValue();
      try {
        IPerformanceClass = Class.forName(CLName);
//        Performance = (IPerformance)IPerformanceClass.newInstance();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    //
    Name = "ISaveObject";
    CAElement = JActiveDComDM.XMLRegistry.GetElementByName(RFElement, Name);
    if (CAElement != null) {
      CLName = CAElement.getAttribute("class").getValue();
      try {
        ISaveObjectClass = Class.forName(CLName);
//        SaveObject = (ISaveObject)ISaveObjectClass.newInstance();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    Name = "IJYSaveObject";
    CAElement = JActiveDComDM.XMLRegistry.GetElementByName(RFElement, Name);
    if (CAElement != null) {
      CLName = CAElement.getAttribute("class").getValue();
      try {
        IJYSaveObjectClass = Class.forName(CLName);
//        SaveObject = (ISaveObject)ISaveObjectClass.newInstance();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    //
    Name = "IFuncResult";
    CAElement = JActiveDComDM.XMLRegistry.GetElementByName(RFElement, Name);
    if (CAElement != null) {
      CLName = CAElement.getAttribute("class").getValue();
      try {
        IFuncResultClass = Class.forName(CLName);
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public ICaObject getCaObject() {
    ICaObject CaObject = null;
    try {
      if (ICaObjectClass != null) {
        CaObject = (ICaObject) ICaObjectClass.newInstance();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return CaObject;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public ISaveObject getSaveObject() {
    ISaveObject SaveObject = null;
    try {
      if (ICaObjectClass != null) {
        SaveObject = (ISaveObject) ISaveObjectClass.newInstance();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return SaveObject;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public ISaveObject getJYSaveObject() {
    ISaveObject SaveObject = null;
    try {
      if (ICaObjectClass != null) {
        SaveObject = (ISaveObject) IJYSaveObjectClass.newInstance();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return SaveObject;
  }

  //------------------------------------------------------------------------------------------------
  //����:
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public IPerformance getPerformance() {
    IPerformance Performance = null;
    try {
      if (IPerformanceClass != null) {
        Performance = (IPerformance) IPerformanceClass.newInstance();
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return Performance;
  }

  //------------------------------------------------------------------------------------------------
  //����: ���㱨��,���ɼ����������
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CalcReport(JParamObject PO, Object ConnObject,Object ConnList) {
    JCalculateRequestObject CRO;

    // �ж��Ƿ�Զ�̵���
    boolean bRemote = isRemoteCall(PO);

    // ����һ������
    CRO = new JCalculateRequestObject(bRemote);
    CRO.CalculateManager = this;

    // ע���������PO.GetValueByEnvName("UserName")
    JActiveDComDM.ManagerRequestObject.SetRequestObject(CRO,
        PO.GetValueByEnvName("UserName"),
        "CalcRequest" + String.valueOf(GetRequestCount()));
    CRO.RequestCaption = "�������";
    //��������������б��ȴ�����
    JThreadManager.timeListThread(PO);
    JThreadManager.addList(CRO,PO,ConnObject,ConnList);

    return null;
  }
  //------------------------------------------------------------------------------------------------
  //����: ���㱨��,���ɼ����������
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CalcSelectedZb(JParamObject PO, Object ConnObject,Object zbList) {
	  JCalculateZbRequestObject CRO;
	  // ����һ������
	  CRO = new JCalculateZbRequestObject();
	  CRO.CalculateManager = this;
	  // ע���������PO.GetValueByEnvName("UserName")
//    JActiveDComDM.ManagerRequestObject.SetRequestObject(CRO,PO.GetValueByEnvName("UserName"),"CalcRequest"+String.valueOf(RequestCount++));
	  // ��ʼ�������
	  CRO.StartNoThread(PO, ConnObject, null, null);

/*

	// ����һ������
	try {
		JConnection conn = (JConnection) ConnObject;
		Vector zbLbList  = (Vector) zbList;
		String sZblb = "";
		for ( int i=0;i<zbLbList.size();i++ ) {
			sZblb = (String) zbLbList.get(i);
		}



//		PO.SetValueByEnvName("RptCalcTask","Report_"+PO.GetValueByEnvName("UserName")+"_"
//							 +PO.GetValueByEnvName("ADD_BH","")+"_"
//							 +StringFunction.getTempStr(6));



   ��ʱ���Զ���̷�ʽ������Ҫ�ǰ��ղ��ô���ʱ��
		ESPServerContext stx = ESPServerContext.getInstance(PO.getEAIParamObject(),
			conn.getESPConnection());
		stx.setValue("TASK_CAN_WAIT", "1");
		stx.putObject("ZBLBLIST", (Vector)zbList);
		stx.getParamObject().setAutoConnection(false);
		TaskManager RptZbCalcTaskM = ReportZbCalcTaskManager.getDefault("ReportZbCalc");
		RequestProcessor.Task task = RptZbCalcTaskM.createTask(stx);
		task.schedule(0);

	} catch (Exception e) {
		e.printStackTrace();
	}
*/
	return CRO.SqlText;
  }

  //------------------------------------------------------------------------------------------------
  //����: ���㱨��,���ɼ����������
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CalcReport_NEW(JParamObject PO, Object ConnObject,Object ConnList) {
	JCalculateRequestObject CRO;

	// �ж��Ƿ�Զ�̵���
	boolean bRemote = isRemoteCall(PO);

	// ����һ������
	try {
		CRO = new JCalculateRequestObject(bRemote);
		CRO.CalculateManager = this;
		// ע���������PO.GetValueByEnvName("UserName")
		JActiveDComDM.ManagerRequestObject.SetRequestObject(CRO,
			PO.GetValueByEnvName("UserName"),
			"CalcRequest" + String.valueOf(GetRequestCount()));
		CRO.RequestCaption = "�������";

		//��������������б��ȴ�����
		JConnection conn = (JConnection) ConnObject;
		String sGuid = GUID.randomGUID().toString();
		PO.SetValueByEnvName("RptCalcTask","Report_"+PO.GetValueByEnvName("UserName")+"_"
							 +PO.GetValueByEnvName("ADD_BH","")+"_"
							 +sGuid);
		ESPServerContext stx = ESPServerContext.getInstance(PO.getEAIParamObject(),
			conn.getESPConnection());
		stx.setValue("TASK_CAN_WAIT", "1");
		stx.putObject("CalcRequest", CRO);
		stx.putObject("connList", (HashMap) ConnList);
		stx.getParamObject().setAutoConnection(false);
		TaskManager RptCalcTaskM = ReportCalcTaskManager.getDefault("ReportCalc");
		RequestProcessor.Task task = RptCalcTaskM.createTask(stx);
		if ( task != null ) {
			task.schedule(0);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

	return null;
  }
  
//------------------------------------------------------------------------------------------------
  //����: У�鹫ʽ,����У���������
  //���: ES(2013.03.27)
  //ʵ��: ES
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CheckReport_NEW(JParamObject PO, Object ConnObject,Object ConnList) {
	  JCheckRequestObject CRO;

	// �ж��Ƿ�Զ�̵���
	boolean bRemote = isRemoteCall(PO);

	// ����һ������
	try {
		CRO = new JCheckRequestObject(bRemote);
		CRO.CalculateManager = this;
		// ע���������PO.GetValueByEnvName("UserName")
		JActiveDComDM.ManagerRequestObject.SetRequestObject(CRO,
			PO.GetValueByEnvName("UserName"),
			"CheckRequest" + String.valueOf(GetRequestCount()));
		CRO.RequestCaption = "У�鹫ʽ";

		//��������������б��ȴ�����
		JConnection conn = (JConnection) ConnObject;
		String sGuid = GUID.randomGUID().toString();
		PO.SetValueByEnvName("RptCheckTask","Report_"+PO.GetValueByEnvName("UserName")+"_"
							 +PO.GetValueByEnvName("ADD_BH","")+"_"
							 +sGuid);
		ESPServerContext stx = ESPServerContext.getInstance(PO.getEAIParamObject(),
			conn.getESPConnection());
		stx.setValue("TASK_CAN_WAIT", "1");
		stx.putObject("CheckRequest", CRO);
		stx.putObject("connList", (HashMap) ConnList);
		stx.getParamObject().setAutoConnection(false);
		TaskManager RptCalcTaskM = ReportCalcTaskManager.getDefault("ReportCheck");
		RequestProcessor.Task task = RptCalcTaskM.createTask(stx);
		if ( task != null ) {
			task.schedule(0);
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

	return null;
  }
  

  //------------------------------------------------------------------------------------------------
  //����: ���㱨��,���ɼ����������
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CalcReportNoThread(JParamObject PO, Object ConnObject,Object ConnList) {
    JCalculateRequestObject CRO;
    // ����һ������
    CRO = new JCalculateRequestObject();
    CRO.CalculateManager = this;
    // ע���������PO.GetValueByEnvName("UserName")
//    JActiveDComDM.ManagerRequestObject.SetRequestObject(CRO,PO.GetValueByEnvName("UserName"),"CalcRequest"+String.valueOf(RequestCount++));
    // ��ʼ�������
    CRO.StartNoThread(PO, ConnObject, ConnList, null);
     return CRO.SqlText;
  }

  //------------------------------------------------------------------------------------------------
//����: ���㱨��,���ɼ����������
//���: Skyline(2001.12.29)
//ʵ��: Skyline
//�޸�:
//------------------------------------------------------------------------------------------------
public Object CalcF1ReportNoThread(JParamObject PO, Object ConnObject) {
//  JCalculateF1RequestObject CRO;
//  // ����һ������
//  CRO = new JCalculateRequestObject();
//  CRO.CalculateManager = this;
//  // ע���������PO.GetValueByEnvName("UserName")
////    JActiveDComDM.ManagerRequestObject.SetRequestObject(CRO,PO.GetValueByEnvName("UserName"),"CalcRequest"+String.valueOf(RequestCount++));
//  // ��ʼ�������
//  CRO.StartNoThread(PO, ConnObject, null, null);
//   return CRO.XMLObject;
	  return null;

}


  //------------------------------------------------------------------------------------------------
  //����: ���㱨��,���ɼ����������
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CheckReport(JParamObject PO, Object ConnObject) {
    JCheckRequestObject CRO;
    try {
        // �ж��Ƿ�Զ�̵���
        boolean bRemote = isRemoteCall(PO);

        // ����һ������
        CRO = new JCheckRequestObject(bRemote);
        CRO.CalculateManager = this;

        // ע���������
        JActiveDComDM.ManagerRequestObject.SetRequestObject(CRO,
            PO.GetValueByEnvName("UserName"),
            "CheckRequest" + String.valueOf(GetRequestCount()));
        CRO.RequestCaption = "����У��";
        CRO.Start(PO, ConnObject, null, null);

        //��������������б��ȴ�����
//        JThreadManager.timeListThread(PO);
//        JThreadManager.addList(CRO, PO, ConnObject, null);
    }
    catch (Exception e) {
        System.err.println("Check Report..............");
        e.printStackTrace();
    }
    return null;
  }

  //------------------------------------------------------------------------------------------------
  //����: ���㱨��,���ɼ����������
  //���: Skyline(2001.12.29)
  //ʵ��: Skyline
  //�޸�:
  //------------------------------------------------------------------------------------------------
  public Object CheckReportNoThread(JParamObject PO, Object ConnObject) {
    JCheckRequestObject CRO;
    // ����һ������
    CRO = new JCheckRequestObject();
    CRO.CalculateManager = this;
    // ע���������
//    JActiveDComDM.ManagerRequestObject.SetRequestObject(CRO,"CheckReport","CheckRequest"+String.valueOf(RequestCount++));
    // ��ʼ�������
    CRO.StartNoThread(PO, ConnObject, null, null);
    Vector v=new Vector();
    v.add(CRO.ErrorObject);
    v.add(new Integer(CRO.ErrorCount));
    return v;
  }


  /**
   * �ж��Ƿ���Զ�̵���
   * @param PO JParamObject
   * @return boolean
   */
  private boolean isRemoteCall(JParamObject PO){
      String flag = PO.GetValueByParamName(JReportMask.RPT_REMOTE_CALL,"0");
      if(flag.equals("1")){
          return true;
      }
      return false;
  }
}
