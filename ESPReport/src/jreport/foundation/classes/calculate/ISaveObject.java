package jreport.foundation.classes.calculate;

import com.efounder.eai.data.JParamObject;

import jreport.model.classes.calculate.*;
import jtoolkit.xml.classes.JXMLObject;

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
public interface ISaveObject {
  Object SaveFormulaResult(ICaObject CaObject,JParamObject PO,Object ConnObject,JFormulaStub F1S,Object CustomObject);
  Object getFormulaResult(ICaObject CaObject,JParamObject PO,Object ConnObject,JFormulaStub F1S,Object CustomObject,JXMLObject XMLObject);
}
