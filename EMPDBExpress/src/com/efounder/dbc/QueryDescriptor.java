package com.efounder.dbc;

import java.io.Serializable;

public class QueryDescriptor implements Serializable {
	private String queryString;
	
	public QueryDescriptor(String strQuerySql) {
		this.queryString = strQuerySql;
	}
	
	public String getQueryString() {
		return this.queryString;
	}
}
