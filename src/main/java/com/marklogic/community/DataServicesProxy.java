package com.marklogic.community;

// IMPORTANT: Do not edit. This file is generated.

import com.marklogic.client.io.Format;
import com.marklogic.client.io.marker.AbstractWriteHandle;
import java.util.stream.Stream;
import java.io.Reader;


import com.marklogic.client.DatabaseClient;

import com.marklogic.client.impl.BaseProxy;

/**
 * Provides a set of operations on the database server
 */
public interface DataServicesProxy {
    /**
     * Creates a DataServicesProxy object for executing operations on the database server.
     *
     * The DatabaseClientFactory class can create the DatabaseClient parameter. A single
     * client object can be used for any number of requests and in multiple threads.
     *
     * @param db	provides a client for communicating with the database server
     * @return	an object for session state
     */
    static DataServicesProxy on(DatabaseClient db) {
        final class DataServicesProxyImpl implements DataServicesProxy {
            private BaseProxy baseProxy;

            private DataServicesProxyImpl(DatabaseClient dbClient) {
                baseProxy = new BaseProxy(dbClient, "/dataServices/");
            }

            @Override
            public com.fasterxml.jackson.core.JsonParser createAPI(String service, com.fasterxml.jackson.databind.JsonNode api) {
              return BaseProxy.JsonDocumentType.toJsonParser(
                baseProxy
                .request("createAPI.sjs", BaseProxy.ParameterValuesKind.MULTIPLE_MIXED)
                .withSession()
                .withParams(
                    BaseProxy.atomicParam("service", false, BaseProxy.StringType.fromString(service)),
                    BaseProxy.documentParam("api", false, BaseProxy.JsonDocumentType.fromJsonNode(api)))
                .withMethod("POST")
                .responseSingle(false, Format.JSON)
                );
            }


            @Override
            public Stream<String> deleteAPI(String service, String api) {
              return BaseProxy.StringType.toString(
                baseProxy
                .request("deleteAPI.sjs", BaseProxy.ParameterValuesKind.MULTIPLE_ATOMICS)
                .withSession()
                .withParams(
                    BaseProxy.atomicParam("service", false, BaseProxy.StringType.fromString(service)),
                    BaseProxy.atomicParam("api", false, BaseProxy.StringType.fromString(api)))
                .withMethod("POST")
                .responseMultiple(false, null)
                );
            }


            @Override
            public Stream<com.fasterxml.jackson.databind.JsonNode> services() {
              return BaseProxy.JsonDocumentType.toJsonNode(
                baseProxy
                .request("services.sjs", BaseProxy.ParameterValuesKind.NONE)
                .withSession()
                .withParams(
                    )
                .withMethod("POST")
                .responseMultiple(false, Format.JSON)
                );
            }


            @Override
            public Stream<com.fasterxml.jackson.core.JsonParser> getAPI(String service, String api) {
              return BaseProxy.JsonDocumentType.toJsonParser(
                baseProxy
                .request("getAPI.sjs", BaseProxy.ParameterValuesKind.MULTIPLE_ATOMICS)
                .withSession()
                .withParams(
                    BaseProxy.atomicParam("service", false, BaseProxy.StringType.fromString(service)),
                    BaseProxy.atomicParam("api", true, BaseProxy.StringType.fromString(api)))
                .withMethod("POST")
                .responseMultiple(false, Format.JSON)
                );
            }


            @Override
            public com.fasterxml.jackson.databind.JsonNode update(String name, Reader declaration) {
              return BaseProxy.JsonDocumentType.toJsonNode(
                baseProxy
                .request("update.sjs", BaseProxy.ParameterValuesKind.MULTIPLE_MIXED)
                .withSession()
                .withParams(
                    BaseProxy.atomicParam("name", false, BaseProxy.StringType.fromString(name)),
                    BaseProxy.documentParam("declaration", false, BaseProxy.JsonDocumentType.fromReader(declaration)))
                .withMethod("POST")
                .responseSingle(false, Format.JSON)
                );
            }

        }

        return new DataServicesProxyImpl(db);
    }

  /**
   * Invokes the createAPI operation on the database server
   *
   * @param service	provides input
   * @param api	provides input
   * @return	as output
   */
    com.fasterxml.jackson.core.JsonParser createAPI(String service, com.fasterxml.jackson.databind.JsonNode api);

  /**
   * Invokes the deleteAPI operation on the database server
   *
   * @param service	provides input
   * @param api	provides input
   * @return	as output
   */
    Stream<String> deleteAPI(String service, String api);

  /**
   * Invokes the services operation on the database server
   *
   * 
   * @return	as output
   */
    Stream<com.fasterxml.jackson.databind.JsonNode> services();

  /**
   * Invokes the getAPI operation on the database server
   *
   * @param service	provides input
   * @param api	provides input
   * @return	as output
   */
    Stream<com.fasterxml.jackson.core.JsonParser> getAPI(String service, String api);

  /**
   * Invokes the update operation on the database server
   *
   * @param name	provides input
   * @param declaration	provides input
   * @return	as output
   */
    com.fasterxml.jackson.databind.JsonNode update(String name, Reader declaration);

}
