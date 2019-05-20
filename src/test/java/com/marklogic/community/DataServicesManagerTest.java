package com.marklogic.community;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;

class DataServicesManagerTest {

	@Test
	void test() {
		DatabaseClient client = DatabaseClientFactory.newClient("localhost", 8010,
				new DatabaseClientFactory.DigestAuthContext("admin", "********"));

		Stream<Reader> apis = DataServicesManager.on(client).apis("/helloWorld", null);
		List<Reader> list = apis.collect(Collectors.toList());

		assertEquals(1, list.size());

		client.release();
	}

}
