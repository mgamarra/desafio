import {ASYNC_END, ASYNC_START} from './RATypes'

let asyncStart = (subtype = "EMPTY", httpRequest = null) => ({
	type: ASYNC_START,
	subtype,
	httpRequest
});

let asyncEnd = (subtype = "EMPTY") => ({
	type: ASYNC_END,
	subtype,
});

/**
 * Base Async Thunk for common use of async redux actions! ;D
 * @param restApiRequest - The promise returned by the Rest API Path Call
 * @param actionType - Then Redux Action Type to hit Reducer
 * @param customResponseTransform - Transform Response body results
 * @returns Function Thunk
 * rodrix
 */
let baseAsyncThunk = (restApiRequest, actionType, customResponseTransform = res => res.body) => dispatch => {
	dispatch(asyncStart(actionType, restApiRequest));

	let hasError = false;
	restApiRequest
		.then(res => {
			hasError = res && !res.body && !res.status.toString().startsWith("20");

			if (!hasError)
				dispatch({type: actionType, data: customResponseTransform(res, hasError)});

			return res;
		})
		.catch((err) => {
			hasError = true;

			dispatch({
				type: actionType,
				errorData: {
					...customResponseTransform(err, hasError),
					status: err.response ? err.response.statusCode : 0,
					hasError
				}
			});

			if (err) {
				if (err.response && err.response.req) {
					console.debug(`baseAsyncThunk XHR: ERROR OCCURRED CALLING >>>> ${err.response.req.url}`, {
						request: {
							method: err.response.req.method,
							url: err.response.req.url,
							data: err.response.req.data,
							// headers: JSON.parse(JSON.stringify(err.response.req)).headers,
						},
						response: {
							body: err.response.body,
							// headers: err.response.headers,
							code: err.response.statusCode,
						},
					});
				} else {
					console.debug(err, JSON.stringify(err));
				}
			}

			return err;
		})
		.finally(() => {
			dispatch(asyncEnd(actionType));
		});
};


export default {
	asyncStart,
	asyncEnd,
	baseAsyncThunk,
}