package com.marklogic.community.dataservices;

public class Typed {
	private String datatype;
	private Boolean multiple;
	private Boolean nullable;

	public String getDatatype() {
		return datatype;
	}

	public Boolean getMultiple() {
		return multiple;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

}