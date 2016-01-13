package esyt.framework.tag;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import esyt.framework.com.builder.base.data.EFDataSet;
import esyt.framework.com.builder.base.data.EFRowSet;

public class EMPDataSetForEach extends BodyTagSupport{
	
	private String           var;  
	private String    datasetKey;  
    private Object         items;  
    private String         begin;  
    private String           end;  
    private String          step;  
    private String     varStatus;  
    private Iterator    iterator;  
    private int          counter;  
    private int           iStart;  
    private int            iEnd;  
    private int            iStep;
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getDatasetKey() {
		return datasetKey;
	}
	public void setDatasetKey(String datasetKey) {
		this.datasetKey = datasetKey;
	}
	public Object getItems() {
		return items;
	}
	public void setItems(Object items) {
		this.items = items;
	}
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getVarStatus() {
		return varStatus;
	}
	public void setVarStatus(String varStatus) {
		this.varStatus = varStatus;
	}
	public Iterator getIterator() {
		return iterator;
	}
	public void setIterator(Iterator iterator) {
		this.iterator = iterator;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public int getiStart() {
		return iStart;
	}
	public void setiStart(int iStart) {
		this.iStart = iStart;
	}
	public int getiEnd() {
		return iEnd;
	}
	public void setiEnd(int iSend) {
		this.iEnd = iSend;
	}
	public int getiStep() {
		return iStep;
	}
	public void setiStep(int iStep) {
		this.iStep = iStep;
	}  
	
	@Override
	public int doStartTag() throws JspException {
		// items不为空时,var是必须,迭代方式,因为items是Object不用取作用域
		if (items != null) {
			//如果是EFDataSet
			if(items instanceof EFDataSet) {
				EFDataSet dataset = (EFDataSet) items;
				if(dataset.getRowSetList() == null) return SKIP_BODY;
				this.iterator = dataset.getRowSetList().iterator();
			}
			//如果是EFRowSet
			else if(items instanceof EFRowSet) {
				EFDataSet                     dataset = null;
				EFRowSet                       rowset = (EFRowSet) items;
				if(datasetKey != null && !datasetKey.equals("")) {
					dataset = rowset.getDataSet(datasetKey);
					if(dataset == null || dataset.getRowSetList() == null) return SKIP_BODY;
					this.iterator = dataset.getRowSetList().iterator();
				} else {
					java.util.List<String> dataSetKeyList = rowset.getDataSetKeyList();
					if(dataSetKeyList.size() == 0)  return SKIP_BODY;
					dataset = rowset.getDataSet(dataSetKeyList.get(0));
					if(dataset.getRowSetList() == null) return SKIP_BODY;
					this.iterator = dataset.getRowSetList().iterator();
				}				
			}
			// 如果是集合
			else if (items instanceof Collection) {
				Collection coll = (Collection) this.items;
				this.iterator = coll.iterator();

			}
			// 如果是数组
			else if (items.getClass().isArray()) {
				Object[] o = (Object[]) this.items;
				List list = Arrays.asList(o);
				this.iterator = list.iterator();
			}
			// 检验迭代器是否有下一个值
			if (this.iterator.hasNext()) {
				counter = 0;// 先置0再加否则会一直加
				this.pageContext.setAttribute(var, this.iterator.next());
				if (varStatus != null && varStatus.length() > 0) {
					this.pageContext.setAttribute(varStatus, new Integer(counter));
					counter++;
				}
				return EVAL_BODY_AGAIN;
			} else {
				return SKIP_BODY;
			}
			// items为空时
		} else {
			if(this.begin == null) {
				this.iStart = 0;
			} else {
				this.iStart = Integer.parseInt(this.begin);
			}
			
			if(this.end == null) {
				this.iEnd = 0;
			} else {
				this.iEnd = Integer.parseInt(this.end);
			}
			// 判断step是否为空,为空默认为1
			this.iStep = this.step == null ? 1 : Integer.parseInt(this.step);
			if (iStart < iEnd) {
				iEnd -= iStep;
				return EVAL_BODY_AGAIN;
			} else {
				return SKIP_BODY;
			}
		}

	}

	@Override
	public int doAfterBody() throws JspException {
		try {
			this.getBodyContent().getEnclosingWriter().print(this.getBodyContent().getString());
			this.getBodyContent().clearBody();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (items != null) {
			// 检验迭代器是否有下一个值
			if (this.iterator.hasNext()) {

				this.pageContext.setAttribute(var, this.iterator.next());
				if (varStatus != null && varStatus.length() > 0) {
					this.pageContext.setAttribute(varStatus, new Integer(counter));
					counter++;
				}
				return EVAL_BODY_AGAIN;
			} else {
				return SKIP_BODY;
			}

		} else {

			if (iStart < iEnd) {
				iEnd -= iStep;
				return EVAL_BODY_AGAIN;
			} else {
				return SKIP_BODY;
			}
		}

	}
}
