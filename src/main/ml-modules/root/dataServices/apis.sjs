'use strict';

var service = '/helloWorld'; // EXTERNAL
var api;

/**
 * Ensures that a path-like string ends with a slash.
 *
 * @param {string} path
 * @param {string} [separator='/']
 * @returns {string}
 */
function trailingSlash(path, separator = '/') {
  if (!path || path.length < 1) return separator;
  if (separator !== path[path.length - 1]) return path + separator;
  return path;
}

/**
 * Given the service identifier, return an Array of its APIs
 *
 * @param {string} serviceName
 * @returns {object[]}
 */
function listAPIs(serviceName) {
  //const serviceName = '/helloWorld';
  const servicePath = trailingSlash(serviceName) + 'service.json';
  const service = cts.doc(servicePath);
  if (!fn.exists(service)) return [];
  // TODO: Implement api filter, rather than wildcard
  return Sequence.from(
    fn.doc(cts.uriMatch(String(service.root.endpointDirectory) + '*.api')),
    api => api.toObject()
  );
}

listAPIs(service);
