package com.marklogic.community;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParser;
import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;

class DataServicesProxyTest {

	@Test
	void test() {
		DatabaseClient client = DatabaseClientFactory.newClient("localhost", 8010,
				new DatabaseClientFactory.DigestAuthContext("admin", "********"));

		Stream<JsonParser> apis = DataServicesProxy.on(client)
				.listAPIs("/helloWorld", null);
		List<JsonParser> list = apis.collect(Collectors.toList());

		assertEquals(1, list.size());

		client.release();
	}

}
