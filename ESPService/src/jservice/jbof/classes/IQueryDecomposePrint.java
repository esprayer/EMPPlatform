package jservice.jbof.classes;

import jservice.jbof.classes.GenerQueryObject.JGenerQueryWindow;

import com.efounder.eai.data.JParamObject;
import com.print.service.AccountPrintFormatParam;
import jservice.jbof.classes.GenerQueryObject.JQueryStubObject;

public interface IQueryDecomposePrint {

  public void printBill(JGenerQueryObject generQueryObject,
                        JParamObject PO,
                        JQueryStubObject QO,
                        JGenerQueryWindow queryWindow,
                        AccountPrintFormatParam AccountFormat) throws Exception;
}
