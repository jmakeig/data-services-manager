package com.marklogic.community;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marklogic.client.DatabaseClient;
import com.marklogic.community.dataservices.API;

public class APIManagerImpl implements APIManager {
	private DataServicesProxy proxy;
	private ObjectMapper mapper;

	private String service;

	public APIManagerImpl forService(final String service) {
		this.service = Objects.requireNonNull(service);
		this.mapper = new ObjectMapper();
		return this;
	}

	@Override
	public String getService() {
		return this.service;
	}

	public APIManagerImpl(final DatabaseClient db) {
		super();
		this.proxy = DataServicesProxy.on(db);
	}

	private API fromJson(final JsonParser json) {
		if (null == json) {
			return null;
		}
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

	@Override
	public Stream<API> all() {
		return this.proxy.listAPIs(this.service).map(this::fromJson);
	}

	@Override
	public API get(final String api) {
		return this.fromJson(this.proxy.getAPI(this.service, api));
	}

	@Override
	public API upsert(final API api) {
		final JsonNode json = this.toJson(api);
		return this.fromJson(this.proxy.upsertAPI(this.service, json));
	}

	@Override
	public void delete(final API api) {
		this.proxy.deleteAPI(this.service, api.getFunctionName());
	}
}
