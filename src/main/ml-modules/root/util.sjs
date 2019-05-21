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

module.exports = { trailingSlash };
