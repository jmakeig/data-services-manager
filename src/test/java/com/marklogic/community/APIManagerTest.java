package com.marklogic.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.community.dataservices.API;
import com.marklogic.community.dataservices.Param;
import com.marklogic.community.dataservices.Typed;

public class APIManagerTest {
	static final DatabaseClient db = DatabaseClientFactory.newClient("localhost",
			8010, new DatabaseClientFactory.DigestAuthContext("admin", "********"));
	static final APIManager apis = new APIManagerImpl(db)
			.forService("/helloWorld");

	@Test
	void testForService() {
		assertEquals("/helloWorld", apis.getService());
		assertThrows(RuntimeException.class, () -> {
			new APIManagerImpl(db).all();
		});
	}

	@Test
	void test() {

		// Initial state
		assertEquals(1, apis.all().count());
		assertEquals("whatsUp", apis.get("whatsUp").getFunctionName());

		final String functionName = "random-" + UUID.randomUUID();

		// Create
		final API api = new API(functionName, List.of(new Param("p1", "string")),
				new Typed("string"));

		final API saved = apis.upsert(api);
		assertEquals(functionName, saved.getFunctionName());

		// Verify newly created
		assertEquals(2, apis.all().count());
		assertTrue(apis.all()
				.anyMatch(item -> functionName.equals(item.getFunctionName())));
		assertEquals(api, apis.get(functionName));

		// Remove
		apis.delete(api);

		// Verify removal
		assertEquals(1, apis.all().count());
		assertFalse(apis.all()
				.anyMatch(item -> functionName.equals(item.getFunctionName())));
		assertNull(apis.get(functionName));
	}

}
