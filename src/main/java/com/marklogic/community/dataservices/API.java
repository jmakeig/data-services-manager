package com.marklogic.community.dataservices;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class API {
	public API() {
		super();
	}

	public API(final String functionName) {
		this.functionName = functionName;
	}

	public API(String functionName, List<Param> params, Typed returns) {
		super();
		this.functionName = functionName;
		this.params = params;
		this.returns = returns;
	}

	private String functionName;

	private List<Param> params;

	@JsonProperty("return")
	private Typed returns;

	public String getFunctionName() {
		return functionName;
	}

	public List<Param> getParams() {
		return params;
	}

	public Typed getReturn() {
		return returns;
	}

	public void setFunctionName(String name) {
		this.functionName = name;
	}

	public void setParams(List<Param> params) {
		this.params = params;
	}

	public void setReturn(Typed returns) {
		this.returns = returns;
	}

}
