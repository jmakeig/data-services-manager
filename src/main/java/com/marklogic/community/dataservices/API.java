package com.marklogic.community.dataservices;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class API {
	private String functionName;

	private List<Param> params;

	@JsonProperty("return")
	private Typed returns;

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

	@Override
	public int hashCode() {
		return Objects.hash(functionName, params, returns);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		API other = (API) obj;
		return Objects.equals(functionName, other.functionName)
				&& Objects.equals(params, other.params)
				&& Objects.equals(returns, other.returns);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("API [functionName=").append(functionName)
				.append(", params=").append(params).append(", returns=").append(returns)
				.append("]");
		return builder.toString();
	}

}
