'use strict';

/**
 * @class
 */
class DocumentExistsError extends Error {
  constructor(uri) {
    super();
    this.uri = uri;
    this.message = `URI ${String(
      uri
    )} exists already. Inserts can only write to new URIs.`;
    Error.captureStackTrace(this);
  }
  get name() {
    return 'DocumentExistsError';
  }
}

/**
 * @class
 */
class DocumentNotExistsError extends Error {
  constructor(uri) {
    super();
    this.uri = uri;
    this.message = `URI ${String(
      uri
    )} does not exist already. Updates must happen to an existing URI.`;
    Error.captureStackTrace(this);
  }
  get name() {
    return 'DocumentNotExistsError';
  }
}

/**
 * Work with XML properties (from property fragments) in JavaScript.
 * Converts JavaScript objects to XML. Pass-through for XML properties.
 *
 * @param {Object|Sequence<Element>|Element} props
 * @param {string} [namespace] - optional namespace applied to all properties when converting from JavaScript. Ignored for existing XML properties.
 * @return {Iterable<Element>} - zero or more XML properties
 */
function properties(props, namespace) {
  if (props instanceof Sequence) {
    props = fn.head(props);
    if (props) {
      return props.xpath('/prop:properties/*', {
        prop: 'http://marklogic.com/xdmp/property'
      });
    } else {
      return undefined;
    }
  }
  if (props instanceof Element) return props;
  return Object.keys(props).map(key => {
    const value = props[key];
    const builder = new NodeBuilder();
    builder.startElement(key, namespace);
    if (value instanceof Element) {
      builder.addNode(value);
    } else {
      builder.addText(String(value));
    }
    builder.endElement();
    return builder.toNode();
  });
}

const INSERT_DATE = 'com.marklogic.insertDate';
const INSERTED_BY = 'com.marklogic.insertedBy';
const UPDATE_DATE = 'com.marklogic.updateDate';
const UPDATED_BY = 'com.marklogic.updatedBy';

/**
 * Get the wall clock timestamp of the current request. Subsequent calls
 * in the same request will get the same value.
 * Note, that this isn’t precisely correct. You’d really want the timestamp
 * at the time the transaction is applied, not the first time this function is
 * called, but that’s not possible in userland code. Also, there are probably
 * issues calling this from separate transactions. Ideally you’d want each
 * transaction to have its own timestamp, not the wrapping request.
 *
 * @function
 * @returns {Date} - the timestamp the first time this is invoked
 */
const getRequestTimestamp = (function() {
  const timestamp = new Date();
  return function _getRequestTimestamp() {
    return timestamp;
  };
})();

/**
 * Get the system-managed metadata for tracking inserts and updates.
 *
 * @param {string} [uri] - system metadata for an existing document or the default, if not provided
 * @returns {Object}
 */
function getUpdateMetadata(uri) {
  // FIXME: This is the slowest part of touching the database. Updates are almost twice as fast with metadata tracking off.
  if (undefined === uri) {
    return {
      // FIXME: This won’t ever work because request timestamp is null for update transactions
      // [UPDATE_DATE]: xdmp.timestampToWallclock(xdmp.requestTimestamp()),
      [UPDATE_DATE]: getRequestTimestamp(), // FIXME: One transaction should only have one date. (This needs to happen in the built-in)
      [UPDATED_BY]: xdmp.getRequestUsername()
    };
  }
  return {
    [INSERT_DATE]: xdmp.documentGetMetadataValue(uri, INSERT_DATE),
    [INSERTED_BY]: xdmp.documentGetMetadataValue(uri, INSERTED_BY),
    [UPDATE_DATE]: xdmp.documentGetMetadataValue(uri, UPDATE_DATE),
    [UPDATED_BY]: xdmp.documentGetMetadataValue(uri, UPDATED_BY)
  };
}

/**
 * Wrapper for `xdmp.documentInsert()` that allows you to specify properties in the options.
 * @private
 * @see {@link xdmp.documentInsert}
 */
function documentInsert(uri, node, options = {}) {
  const pruned = Object.assign({}, options);
  delete pruned.properties;
  const result = xdmp.documentInsert(uri, node, pruned);
  if (options.properties) {
    xdmp.documentSetProperties(uri, properties(options.properties));
  }
  return result;
}

/**
 * Inserts a document at a URI where none exists.
 *
 * @param {string} uri - the URI of the document to insert
 * @param {Node|Object} node - the new document
 * @param {Object} [options={}] - the metadata options
 * @return {undefined}
 * @throws {DocumentExistsError} - if a document at the URI already exists
 */
function insert(uri, node, options = {}, updateMetadata = false) {
  xdmp.lockForUpdate(uri);
  if (fn.exists(cts.doc(uri))) {
    throw new DocumentExistsError(uri);
  }
  if (updateMetadata) {
    options.metadata = Object.assign(
      {},
      options.metadata,
      {
        [INSERT_DATE]: getRequestTimestamp(),
        [INSERTED_BY]: xdmp.getRequestUsername()
      },
      getUpdateMetadata()
    );
  }
  return documentInsert(uri, node, options);
}

/**
 * Updates a document that exists at a URI. Throws an error if a document at the URI
 * doesn’t exist already.
 *
 * @param {string} uri - the URI of the document to update
 * @param {Node|Object} node - the new document
 * @param {Object|function|undefined} [options] - the metadata options
 * @return {undefined}
 * @throws {DocumentNotExistsError} - if a document at the URI doesn’t already exist
 */
function update(uri, node, options, updateMetadata = false) {
  xdmp.lockForUpdate(uri);
  if (!fn.exists(cts.doc(uri))) {
    throw new DocumentNotExistsError(uri);
  }

  // Inherit all options of existing document.
  // Except update metadata, if specified
  if (undefined === options) {
    if (updateMetadata) {
      xdmp.documentPutMetadata(uri, getUpdateMetadata());
    }
    return xdmp.nodeReplace(cts.doc(uri), node);
  }

  const opts =
    'function' === typeof options
      ? options(uri, node)
      : Object.assign({}, getDocumentOptions(uri), options);

  if (updateMetadata) {
    opts.metadata = Object.assign(
      {},
      getUpdateMetadata(uri),
      opts.metadata,
      getUpdateMetadata()
    );
  }
  return documentInsert(uri, node, opts);
}

/**
 * Insert *or* update a document at a URI. This is the default behavior of `xdmp.documentInsert()`.
 *
 * @param {string} uri - the URI of the document to upsert
 * @param {Node|Object} node - the document to insert
 * @param {Object} [options={}] - the metadata options
 * @return {undefined}
 */
function upsert(uri, node, options = {}, updateMetadata = false) {
  xdmp.lockForUpdate(uri);
  if (fn.exists(cts.doc(uri))) {
    return update(uri, node, options, updateMetadata);
  }
  return documentInsert(uri, node, options);
}

/**
 * Whether something is a valid database URI.
 *
 * @param {string} uri
 * @return {boolean}
 */
function isURI(uri) {
  if ('string' !== typeof uri) return false;
  if (uri.length < 1) return false;
  return true;
}

/**
 * Transactionally move a document and all of its metadata (collections, permissions, metadata, quality, and properties)
 * from one URI to another.
 *
 * @param {string} sourceURI - The URI of the source document
 * @param {string} targetURI - The URI of the target document
 * @param {Object} [newOptions={}] - The `xdmp.documentInsert()` options to be *merged* with the existing options from the target
 * @param {Function} [updater=upsert] - The function used to insert the target. Defaults to `upsert`. Provide your own to customize the behavior.
 * @return {undefined}
 * @throws {TypeError} - invalid URI or non-callable updater function
 * @throws {DocumentNotExistsError} - source document does not exist
 */
function move(
  sourceURI,
  targetURI,
  newOptions = {},
  updater = upsert,
  updateMetadata = false
) {
  if (targetURI === sourceURI) return;
  if (!isURI(sourceURI) || !isURI(targetURI)) {
    throw new TypeError(
      `${String(sourceURI)} and ${String(targetURI)} must valid URIs.`
    );
  }
  if ('function' !== typeof updater) {
    throw new TypeError(`${String(updater)} must be a function.`);
  }
  [sourceURI, targetURI].map(uri => xdmp.lockForUpdate(uri));
  const doc = cts.doc(sourceURI);
  if (!doc) {
    throw new DocumentNotExistsError(sourceURI);
  }
  const options = getDocumentOptions(sourceURI);
  xdmp.documentDelete(sourceURI);
  return updater(
    targetURI,
    doc,
    Object.assign(options, newOptions),
    updateMetadata
  );
}

function getDocumentOptions(uri) {
  return {
    collections: xdmp.documentGetCollections(uri),
    permissions: xdmp.documentGetPermissions(uri),
    metadata: xdmp.documentGetMetadata(uri),
    quality: xdmp.documentGetQuality(uri),
    // This is an extension to to the standard options for xdmp.documentInsert()
    // It’s pruned out and handled separately in documentInsert() above
    properties: xdmp.documentProperties(uri)
  };
}

/**
 * Delete one or more documents by URI.
 *
 * @param {Iterable<string>|string} uris - one or more uris
 * @return {undefined}
 */
function remove(uris) {
  if ('string' === typeof uris) uris = [uris];
  Array.from(uris).map(uri => xdmp.lockForUpdate(uri));
  for (const uri of uris) {
    // TODO: Tombstone?
    xdmp.documentDelete(uri);
  }
  return uris;
}

module.exports = {
  /**
   * Wraps the update functions with versions that automatically implement a globally update metadata strategy.
   *
   * @example
   * const { insert, update } = require('/mutations').withMetadata();
   * insert('/mydoc.json', {}, { collections: ['active'] }); // effectively `true`
   *
   * @example
   * const { insert, update } = require('/mutations');
   * insert('/mydoc.json', {}, { collections: ['active'] }, true);
   *
   * @param {boolean} [updateMetadata=true] - whether to track changes with document metadata
   * @returns {Object} - the wrapped functions
   * @function
   */
  withMetadata(updateMetadata = true) {
    return {
      insert: (uri, node, options) =>
        insert(uri, node, options, updateMetadata),
      update: (uri, node, options) =>
        update(uri, node, options, updateMetadata),
      upsert: (uri, node, options) =>
        upsert(uri, node, options, updateMetadata),
      move: (sourceURI, targetURI, newOptions, updater) =>
        move(sourceURI, targetURI, newOptions, updater, updateMetadata),
      remove: uris => remove(uris, updateMetadata)
    };
  },
  insert,
  update,
  upsert,
  move,
  remove,
  DocumentExistsError,
  DocumentNotExistsError,
  properties
};
