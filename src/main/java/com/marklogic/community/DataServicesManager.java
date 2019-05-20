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
public interface DataServicesManager {
    /**
     * Creates a DataServicesManager object for executing operations on the database server.
     *
     * The DatabaseClientFactory class can create the DatabaseClient parameter. A single
     * client object can be used for any number of requests and in multiple threads.
     *
     * @param db	provides a client for communicating with the database server
     * @return	an object for session state
     */
    static DataServicesManager on(DatabaseClient db) {
        final class DataServicesManagerImpl implements DataServicesManager {
            private BaseProxy baseProxy;

            private DataServicesManagerImpl(DatabaseClient dbClient) {
                baseProxy = new BaseProxy(dbClient, "/dataServices/");
            }

            @Override
            public com.fasterxml.jackson.databind.JsonNode delete(String name) {
              return BaseProxy.JsonDocumentType.toJsonNode(
                baseProxy
                .request("delete.sjs", BaseProxy.ParameterValuesKind.SINGLE_ATOMIC)
                .withSession()
                .withParams(
                    BaseProxy.atomicParam("name", false, BaseProxy.StringType.fromString(name)))
                .withMethod("POST")
                .responseSingle(false, Format.JSON)
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


            @Override
            public Stream<Reader> apis(String service, String api) {
              return BaseProxy.JsonDocumentType.toReader(
                baseProxy
                .request("apis.sjs", BaseProxy.ParameterValuesKind.MULTIPLE_ATOMICS)
                .withSession()
                .withParams(
                    BaseProxy.atomicParam("service", false, BaseProxy.StringType.fromString(service)),
                    BaseProxy.atomicParam("api", true, BaseProxy.StringType.fromString(api)))
                .withMethod("POST")
                .responseMultiple(false, Format.JSON)
                );
            }

        }

        return new DataServicesManagerImpl(db);
    }

  /**
   * Invokes the delete operation on the database server
   *
   * @param name	provides input
   * @return	as output
   */
    com.fasterxml.jackson.databind.JsonNode delete(String name);

  /**
   * Invokes the services operation on the database server
   *
   * 
   * @return	as output
   */
    Stream<com.fasterxml.jackson.databind.JsonNode> services();

  /**
   * Invokes the update operation on the database server
   *
   * @param name	provides input
   * @param declaration	provides input
   * @return	as output
   */
    com.fasterxml.jackson.databind.JsonNode update(String name, Reader declaration);

  /**
   * Invokes the apis operation on the database server
   *
   * @param service	provides input
   * @param api	provides input
   * @return	as output
   */
    Stream<Reader> apis(String service, String api);

}
