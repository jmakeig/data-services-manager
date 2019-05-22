package com.marklogic.community.dataservices;

import java.util.Objects;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Param other = (Param) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Param [name=").append(name).append("]");
		return builder.toString();
	}

}