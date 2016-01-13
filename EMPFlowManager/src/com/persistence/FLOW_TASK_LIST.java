package com.persistence;

import com.dal.object.AbstractDO;

public class FLOW_TASK_LIST extends AbstractDO{
	
	public static String                 _FLOW_ID = "FLOW_ID";
	public static String                   _OP_ID = "OP_ID";
	public static String                _BIZ_DATE = "BIZ_DATE";
	public static String                _BIZ_DJBH = "BIZ_DJBH";
	public static String                _BIZ_GUID = "BIZ_GUID";
	public static String                 _OP_NODE = "OP_NODE";
	public static String                 _OP_NOTE = "OP_NOTE";
	public static String             _OP_NODE_PRE = "OP_NODE_PRE";
	public static String            _OP_NODE_NEXT = "OP_NODE_NEXT";
	public static String            _OP_NODE_TYPE = "OP_NODE_TYPE";
	public static String                 _OP_USER = "OP_USER";
	public static String            _OP_USER_NAME = "OP_USER_ANEM";
	public static String                 _OP_BMBH = "OP_BMBH";
	public static String                _NODE_SRC = "NODE_SRC";
	public static String           _NODE_SRC_NAME = "NODE_SRC_NAME";
	public static String                _NODE_TAG = "NODE_TAG";
	public static String           _NODE_TAG_NAME = "NODE_TAG_NAME";
	public static String             _RESR_STATUS = "RESR_STATUS";
	public static String           _RESR_IN_CAUSE = "RESR_IN_CAUSE";
	public static String          _RESR_OUT_CAUSE = "RESR_OUT_CAUSE";
	public static String                  _F_CHAR = "F_CHAR";
	public static String                  _F_DATE = "F_DATE";
	public static String                   _F_NUM = "F_NUM";
	public static String            _TASK_TO_UNIT = "TASK_TO_UNIT";
	public static String       _TASK_TO_UNIT_NAME = "TASK_TO_UNIT_NAME";
	
	
	private String                        FLOW_ID;
	private String                        OP_NODE;
	private String                       NODE_SRC;
	private String                  NODE_SRC_NAME;
	private String                       NODE_TAG;
	private String                  NODE_TAG_NAME;
	private String                        OP_USER;	
	private String                        OP_BMBH;	

	public FLOW_TASK_LIST(){
	}

	public String getFLOW_ID() {
		return FLOW_ID;
	}

	public void setFLOW_ID(String fLOW_ID) {
		FLOW_ID = fLOW_ID;
	}

	public String getOP_NODE() {
		return OP_NODE;
	}

	public void setOP_NODE(String oP_NODE) {
		OP_NODE = oP_NODE;
	}

	public String getNODE_SRC() {
		return NODE_SRC;
	}

	public void setNODE_SRC(String nODE_SRC) {
		NODE_SRC = nODE_SRC;
	}

	public String getNODE_SRC_NAME() {
		return NODE_SRC_NAME;
	}

	public void setNODE_SRC_NAME(String nODE_SRC_NAME) {
		NODE_SRC_NAME = nODE_SRC_NAME;
	}

	public String getNODE_TAG() {
		return NODE_TAG;
	}

	public void setNODE_TAG(String nODE_TAG) {
		NODE_TAG = nODE_TAG;
	}

	public String getNODE_TAG_NAME() {
		return NODE_TAG_NAME;
	}

	public void setNODE_TAG_NAME(String nODE_TAG_NAME) {
		NODE_TAG_NAME = nODE_TAG_NAME;
	}

	public String getOP_USER() {
		return OP_USER;
	}

	public void setOP_USER(String oP_USER) {
		OP_USER = oP_USER;
	}

	public String getOP_BMBH() {
		return OP_BMBH;
	}

	public void setOP_BMBH(String oP_BMBH) {
		OP_BMBH = oP_BMBH;
	}

}

