package com.marklogic.community.dataservices;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Api {

	private String datatype;
	private String functionName;

	private List<Param> params;

	@JsonProperty("return")
	private Typed returns;

	public String getDatatype() {
		return datatype;
	}

	public String getFunctionName() {
		return functionName;
	}

	public List<Param> getParams() {
		return params;
	}

	public Typed getReturns() {
		return returns;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public void setFunctionName(String name) {
		this.functionName = name;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}

	public void setReturns(Typed returns) {
		this.returns = returns;
	}

}
