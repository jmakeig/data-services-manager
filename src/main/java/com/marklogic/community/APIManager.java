package com.marklogic.community;

import java.util.stream.Stream;

import com.marklogic.community.dataservices.API;

public interface APIManager {
	String getService();

	Stream<API> all();

	API get(String api);

	API upsert(API api);

	void delete(API api);

}