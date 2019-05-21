var service; // string
var api; // jsonDocument

declareUpdate();

const { trailingSlash } = require('../util.sjs');
const { insert } = require('../insert.sjs').withMetadata();

// TODO: Verify that service is a valid service
// TODO: Validate api (there’s a JSON schema)

const functionName = api.root.functionName;
const uri = `${trailingSlash(service)}${functionName}.api`;
insert(uri, api);
api;
