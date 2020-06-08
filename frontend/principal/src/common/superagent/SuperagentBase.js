import superagentIntercept from "superagent-intercept";
import superagentPromise from "superagent-promise";
import _superagent from "superagent";

/**
 * Base Superagent config.
 *
 * *** MUST HAVE ONLY COMMON BEHAVIOUR TO ALL SUPERAGENT BASIC CHILDREN ***
 * *** AVOID SET CUSTOM BEHAVIOURS HERE ***
 */

const superagent = superagentPromise(_superagent, global.Promise);

export default superagent;

export const enhanceSuperAgent = (superagentInstance,
                                  baseApiPath,
                                  headersPlugin = () => {},
                                  interceptorPlugin = () => {}) => {

	const applyPlugins = (req) => req.use(headersPlugin).use(superagentIntercept(interceptorPlugin));

	return {
		del: url => applyPlugins(superagentInstance.del(`${baseApiPath}${url}`)),
		get: url => applyPlugins(superagentInstance.get(`${baseApiPath}${url}`)),
		put: (url, body) => applyPlugins(superagentInstance.put(`${baseApiPath}${url}`, body)),
		patch: (url, body) => applyPlugins(superagentInstance.patch(`${baseApiPath}${url}`, body)),
		post: (url, body) => applyPlugins(superagentInstance.post(`${baseApiPath}${url}`, body)),

		custom: (method, url, body, extraHeaders = {}) => {
			const req = superagentInstance(method, `${baseApiPath}${url}`);

			if (extraHeaders)
				Object.keys(extraHeaders).forEach((e) => {
					req.set(e, extraHeaders[e]);
				});

			if (body)
				req.send(body);

			return applyPlugins(req);
		}
	};
};
