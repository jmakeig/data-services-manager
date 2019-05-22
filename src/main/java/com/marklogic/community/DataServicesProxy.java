package com.marklogic.community;

// IMPORTANT: Do not edit. This file is generated.

import java.util.stream.Stream;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.impl.BaseProxy;
import com.marklogic.client.io.Format;

/**
 * Provides a set of operations on the database server
 */
public interface DataServicesProxy {
	/**
	 * Creates a DataServicesProxy object for executing operations on the database
	 * server.
	 *
	 * The DatabaseClientFactory class can create the DatabaseClient parameter. A
	 * single client object can be used for any number of requests and in multiple
	 * threads.
	 *
	 * @param db provides a client for communicating with the database server
	 * @return an object for session state
	 */
	static DataServicesProxy on(DatabaseClient db) {
		final class DataServicesProxyImpl implements DataServicesProxy {
			private BaseProxy baseProxy;

			private DataServicesProxyImpl(DatabaseClient dbClient) {
				baseProxy = new BaseProxy(dbClient, "/dataServices/");
			}

			@Override
			public Stream<String> deleteAPI(String service, String api) {
				return BaseProxy.StringType
						.toString(baseProxy
								.request("deleteAPI.sjs",
										BaseProxy.ParameterValuesKind.MULTIPLE_ATOMICS)
								.withSession()
								.withParams(
										BaseProxy.atomicParam("service", false,
												BaseProxy.StringType.fromString(service)),
										BaseProxy.atomicParam("api", false,
												BaseProxy.StringType.fromString(api)))
								.withMethod("POST").responseMultiple(false, null));
			}

			@Override
			public com.fasterxml.jackson.core.JsonParser upsertAPI(String service,
					com.fasterxml.jackson.databind.JsonNode api) {
				return BaseProxy.JsonDocumentType
						.toJsonParser(
								baseProxy
										.request("upsertAPI.sjs",
												BaseProxy.ParameterValuesKind.MULTIPLE_MIXED)
										.withSession()
										.withParams(
												BaseProxy.atomicParam("service", false,
														BaseProxy.StringType.fromString(service)),
												BaseProxy.documentParam("api", false,
														BaseProxy.JsonDocumentType.fromJsonNode(api)))
										.withMethod("POST").responseSingle(false, Format.JSON));
			}

			@Override
			public com.fasterxml.jackson.core.JsonParser getAPI(String service,
					String api) {
				return BaseProxy.JsonDocumentType
						.toJsonParser(baseProxy
								.request("getAPI.sjs",
										BaseProxy.ParameterValuesKind.MULTIPLE_ATOMICS)
								.withSession()
								.withParams(
										BaseProxy.atomicParam("service", false,
												BaseProxy.StringType.fromString(service)),
										BaseProxy.atomicParam("api", false,
												BaseProxy.StringType.fromString(api)))
								.withMethod("POST").responseSingle(true, Format.JSON));
			}

			@Override
			public Stream<com.fasterxml.jackson.core.JsonParser> listAPIs(
					String service) {
				return BaseProxy.JsonDocumentType
						.toJsonParser(
								baseProxy
										.request("listAPIs.sjs",
												BaseProxy.ParameterValuesKind.SINGLE_ATOMIC)
										.withSession()
										.withParams(BaseProxy.atomicParam("service", false,
												BaseProxy.StringType.fromString(service)))
										.withMethod("POST").responseMultiple(true, Format.JSON));
			}

		}

		return new DataServicesProxyImpl(db);
	}

	/**
	 * Invokes the deleteAPI operation on the database server
	 *
	 * @param service provides input
	 * @param api     provides input
	 * @return as output
	 */
	Stream<String> deleteAPI(String service, String api);

	/**
	 * Invokes the upsertAPI operation on the database server
	 *
	 * @param service provides input
	 * @param api     provides input
	 * @return as output
	 */
	com.fasterxml.jackson.core.JsonParser upsertAPI(String service,
			com.fasterxml.jackson.databind.JsonNode api);

	/**
	 * Invokes the getAPI operation on the database server
	 *
	 * @param service provides input
	 * @param api     provides input
	 * @return as output
	 */
	com.fasterxml.jackson.core.JsonParser getAPI(String service, String api);

	/**
	 * Invokes the listAPIs operation on the database server
	 *
	 * @param service provides input
	 * @return as output
	 */
	Stream<com.fasterxml.jackson.core.JsonParser> listAPIs(String service);

}
