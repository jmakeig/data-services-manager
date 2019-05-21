'use strict';

var service; // string
var api; // string

const { trailingSlash } = require('../util.sjs');
const { remove } = require('../insert.sjs').withMetadata();

declareUpdate();

const apis = cts.search(
	cts.andQuery([
		cts.directoryQuery(trailingSlash(service)),
		cts.jsonPropertyValueQuery('functionName', api)
	])
);
Sequence.from(apis, doc => Sequence.from(remove(xdmp.nodeUri(doc))));
