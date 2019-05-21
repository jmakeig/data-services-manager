package com.marklogic.community;

import java.io.IOException;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.community.dataservices.API;

public class DataServicesManager {
	private DataServicesProxy proxy;
	private ObjectMapper mapper;

	public DataServicesManager(final DatabaseClient db) {
		super();
		this.proxy = DataServicesProxy.on(db);
		this.mapper = new ObjectMapper();
	}

	public Stream<API> getAPIs(final String service) {
		return getAPIs(service, null);
	}

	public Stream<API> getAPIs(final String service, final String api) {
		return this.proxy.listAPIs(service, null).map(node -> {
			try {
				return mapper.readValue(node, API.class);
			} catch (IOException e) {
				// This is ugly, but there’s no way to re-throw a
				// checked exception from a lambda
				throw new RuntimeException(e);
			}
		});
	}

	public API createAPI(final String service, final API api) {
		final JsonNode json = this.mapper.valueToTree(api);
		try {
			return mapper.readValue(this.proxy.createAPI(service, json), API.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
