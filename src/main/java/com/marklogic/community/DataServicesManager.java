package com.marklogic.community;

import java.io.IOException;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParser;
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

	private API fromJson(final JsonParser json) {
		try {
			return mapper.readValue(json, API.class);
		} catch (IOException e) {
			// This is ugly, but there’s no way to re-throw a
			// checked exception from a lambda
			throw new RuntimeException(e);
		}
	}

	private JsonNode toJson(final API api) {
		return this.mapper.valueToTree(api);
	}

	public Stream<API> getAPI(final String service) {
		return getAPI(service, null);
	}

	public Stream<API> getAPI(final String service, final String api) {
		return this.proxy.getAPI(service, null).map(node -> this.fromJson(node));
	}

	public API createAPI(final String service, final API api) {
		final JsonNode json = this.toJson(api);
		return this.fromJson(this.proxy.createAPI(service, json));
	}

	public void deleteAPI(final String service, final API api) {
		this.proxy.deleteAPI(service, api.getFunctionName());
	}
}
