'use strict';

var service; // EXTERNAL

const { trailingSlash } = require('../util.sjs');

/**
 * Given the service identifier, return an Array of its APIs
 *
 * @param {string} serviceName
 * @returns {object[]}
 */
function listAPIs(serviceName) {
  const servicePath = trailingSlash(serviceName) + 'service.json';
  const service = cts.doc(servicePath);
  if (!fn.exists(service)) return Sequence.from([]);
  return Sequence.from(
    fn.doc(cts.uriMatch(String(service.root.endpointDirectory) + '*.api')),
    // https://github.com/marklogic/java-client-api/issues/1104#issuecomment-494146548
    api => api.toObject()
  );
}

listAPIs(service);
