package com.persistence;

import com.dal.object.AbstractDO;

public class FLOW_NODE_LIST extends AbstractDO{
	private String               FLOW_ID;
	private String               NODE_ID;
	private String             NODE_NAME;
	private String         NODE_RETRIEVE;	
	private String             NODE_TYPE;
	private String             NODE_NEXT;

	public FLOW_NODE_LIST(){
	}

	public String getFLOW_ID() {
		return FLOW_ID;
	}

	public void setFLOW_ID(String fLOW_ID) {
		FLOW_ID = fLOW_ID;
	}

	public String getNODE_ID() {
		return NODE_ID;
	}

	public void setNODE_ID(String nODE_ID) {
		NODE_ID = nODE_ID;
	}

	public String getNODE_NAME() {
		return NODE_NAME;
	}

	public void setNODE_NAME(String nODE_NAME) {
		NODE_NAME = nODE_NAME;
	}

	public String getNODE_RETRIEVE() {
		return NODE_RETRIEVE;
	}

	public void setNODE_RETRIEVE(String nODE_RETRIEVE) {
		NODE_RETRIEVE = nODE_RETRIEVE;
	}

	public String getNODE_TYPE() {
		return NODE_TYPE;
	}

	public void setNODE_TYPE(String nODE_TYPE) {
		NODE_TYPE = nODE_TYPE;
	}

	public String getNODE_NEXT() {
		return NODE_NEXT;
	}

	public void setNODE_NEXT(String nODE_NEXT) {
		NODE_NEXT = nODE_NEXT;
	}

}

