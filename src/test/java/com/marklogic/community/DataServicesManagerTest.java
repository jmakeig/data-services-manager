package com.marklogic.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		final String service = "/helloWorld";

		// Initial state
		assertEquals(1, manager.getAPI(service).count());
		assertEquals("whatsUp", manager.getAPI(service).collect(Collectors.toList())
				.get(0).getFunctionName());

		// Create
		final String functionName = "random-" + UUID.randomUUID();
		final API api = new API(functionName, List.of(new Param("p1", "string")),
				new Typed("string"));

		final API saved = manager.createAPI(service, api);
		assertEquals(functionName, saved.getFunctionName());

		// Verify newly created
		assertEquals(2, manager.getAPI(service).count());
		assertTrue(manager.getAPI(service)
				.anyMatch(item -> functionName.equals(item.getFunctionName())));

		// Remove
		manager.deleteAPI(service, api);
		assertEquals(1, manager.getAPI(service).count());
		assertFalse(manager.getAPI(service)
				.anyMatch(item -> functionName.equals(item.getFunctionName())));
	}

}
