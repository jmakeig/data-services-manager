'use strict';

var service; // EXTERNAL string
var api; // EXTERNAL string

const { trailingSlash } = require('../util.sjs');

const apiDoc = fn.head(
  cts.search(
    cts.andQuery([
      cts.directoryQuery(trailingSlash(service)),
      cts.jsonPropertyValueQuery('functionName', api)
    ])
  )
);

// https://github.com/marklogic/java-client-api/issues/1104#issuecomment-494146548
if (fn.exists(apiDoc)) apiDoc.toObject();
else null;
