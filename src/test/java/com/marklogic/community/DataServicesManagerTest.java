package com.marklogic.community;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.community.dataservices.API;
import com.marklogic.community.dataservices.Param;
import com.marklogic.community.dataservices.Typed;

public class DataServicesManagerTest {
	static final DataServicesManager manager = new DataServicesManager(
			DatabaseClientFactory.newClient("localhost", 8010,
					new DatabaseClientFactory.DigestAuthContext("admin", "********")));

	@Test
	void test() {
		assertEquals(1,
				manager.getAPIs("/helloWorld").collect(Collectors.toList()).size());
		assertEquals("whatsUp", manager.getAPIs("/helloWorld")
				.collect(Collectors.toList()).get(0).getFunctionName());
	}

	@Test
	void testCreateAPI() {
		final String functionName = "random-" + UUID.randomUUID();
		final API api = new API(functionName, List.of(new Param("p1", "string")),
				new Typed("string"));

		final API saved = manager.createAPI("/helloWorld", api);
		assertEquals(functionName, saved.getFunctionName());
	}
}
