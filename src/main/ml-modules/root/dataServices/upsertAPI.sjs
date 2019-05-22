var service; // string
var api; // jsonDocument

declareUpdate();

const { trailingSlash } = require('../util.sjs');
const { upsert } = require('../insert.sjs').withMetadata();


// TODO: Query by value, not URI
// TODO: Verify that service is a valid service
// TODO: Validate api (there’s a JSON schema)

const functionName = api.root.functionName;
const uri = `${trailingSlash(service)}${functionName}.api`;
upsert(uri, api);
api;
