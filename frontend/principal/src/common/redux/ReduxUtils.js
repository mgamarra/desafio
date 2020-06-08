/**
 * Fires a CLEAR action to a specific reducer and reducer value.
 * @param reducer - the reducer name
 * @param dataTarget - field name on reducer, can be a array of field names
 * @param cleanValue - OPTIONAL - value to use on 'cleaning' process. Ex. undefined, 0, "", etc. Defaults is undefined
 */
import {CLEAR_REDUCER_INFO} from "./RATypes";
import ReduxStore from "./ReduxStore";

let clearReducerData = (reducer, dataTarget, cleanValue = undefined) => {
	ReduxStore.dispatch({
		type: CLEAR_REDUCER_INFO,
		reducer,
		dataTarget,
		cleanValue
	});
};

export {clearReducerData};