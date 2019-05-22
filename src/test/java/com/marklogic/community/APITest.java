package com.marklogic.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marklogic.community.dataservices.API;
import com.marklogic.community.dataservices.Param;
import com.marklogic.community.dataservices.Typed;

public class APITest {

	@Test
	void testResource() {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("apis.api").getFile());
		assertTrue(file.exists());
	}

	@Test
	void testObjectMapper()
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("apis.api").getFile());

		final API api = mapper.readValue(file, API.class);
		assertEquals("apis", api.getFunctionName());
		assertEquals(2, api.getParams().size());
		assertEquals("jsonDocument", api.getReturn().getDatatype());
		assertEquals(true, api.getReturn().getMultiple());
	}

	@Test
	void testEquals() {
		final API api1 = new API("test", List.of(new Param("p1", "string")),
				new Typed("string"));
		final API api2 = new API("test", List.of(new Param("p1", "string")),
				new Typed("string"));
		assertEquals(api1, api2);
	}
}
