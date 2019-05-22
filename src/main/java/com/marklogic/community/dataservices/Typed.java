package com.marklogic.community.dataservices;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Typed {

	private String datatype;

	@JsonInclude(Include.NON_NULL)
	private Boolean multiple = false;

	@JsonInclude(Include.NON_NULL)
	private Boolean nullable = false;

	public Typed() {
		super();
	}

	public Typed(final String datatype) {
		this();
		this.datatype = Objects.requireNonNull(datatype);
	}

	public Typed(final String datatype, final Boolean multiple,
			final Boolean nullable) {
		this();
		this.datatype = Objects.requireNonNull(datatype);
		this.multiple = Objects.requireNonNull(multiple);
		this.nullable = Objects.requireNonNull(nullable);
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(datatype, multiple, nullable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Typed other = (Typed) obj;
		return Objects.equals(datatype, other.datatype)
				&& Objects.equals(multiple, other.multiple)
				&& Objects.equals(nullable, other.nullable);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Typed [datatype=").append(datatype).append(", multiple=")
				.append(multiple).append(", nullable=").append(nullable).append("]");
		return builder.toString();
	}

}