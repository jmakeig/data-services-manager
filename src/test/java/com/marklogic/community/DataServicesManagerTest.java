package com.marklogic.community;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;

class DataServicesManagerTest {

	@Test
	void test() {
		DatabaseClient client = DatabaseClientFactory.newClient("localhost", 8010,
				new DatabaseClientFactory.DigestAuthContext("admin", "********"));

		assertEquals("Hey! Hey!", DataServicesManager.on(client).list());

		client.release();
	}

}
