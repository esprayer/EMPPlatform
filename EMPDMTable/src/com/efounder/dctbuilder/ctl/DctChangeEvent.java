package com.efounder.dctbuilder.ctl;

import com.core.xml.*;
import com.efounder.bz.dbform.datamodel.DictModel;
import com.efounder.dctbuilder.mdl.*;
import java.util.EventObject;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class DctChangeEvent extends EventObject {

	protected int    changeType     = 0;
	/**
	 * 改变源
	 */
	protected Object changeSource   = null;
	/**
	 * 源所在的tableModel
	 */
	protected DictModel dctModel = null;
	/**
	 *
	 * @return TableModel
	 */
	public DictModel getDictModel() {
		return dctModel;
	}
	/**
	 *
	 * @return Object
	 */
	public Object getChangeSource() {
		return changeSource;
	}
	/**
	 *
	 * @return int
	 */
	public int getChangeType() {
		return changeType;
	}
	/**
	 * 构造函数
	 * @param tableModel TableModel
	 * @param changeType int
	 */
	public DctChangeEvent(DictModel dctModel,Object changeSource,int changeType) {

		/**
		 * 改变类型
		 */
		super(changeSource);
		this.changeType   = changeType;
		/**
		 * 改变源
		 */
		this.changeSource = changeSource;
		/**
		 * 源tableModel
		 */
		this.dctModel   = dctModel;
	}

}
