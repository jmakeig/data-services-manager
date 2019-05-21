package com.marklogic.community.dataservices;

public class Param extends Typed {
	private String name;

	public Param() {
		super();
	}

	public Param(final String name, final String datatype) {
		super(datatype);
		this.name = name;
	}

	public Param(final String name, String datatype, Boolean multiple,
			Boolean nullable) {
		super(datatype, multiple, nullable);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}