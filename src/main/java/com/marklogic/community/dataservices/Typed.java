package com.marklogic.community.dataservices;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Typed {

	public Typed() {
		super();
	}

	public Typed(final String datatype) {
		this(datatype, null, null);
	}

	public Typed(final String datatype, final Boolean multiple,
			final Boolean nullable) {
		this();
		this.datatype = datatype;
		this.multiple = multiple;
		this.nullable = nullable;
	}

	private String datatype;
	@JsonInclude(Include.NON_NULL)
	private Boolean multiple = false;
	@JsonInclude(Include.NON_NULL)
	private Boolean nullable = false;

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