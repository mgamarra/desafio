import {applyMiddleware, combineReducers, createStore} from 'redux';
import thunk from 'redux-thunk';
import CommonReducer from "./CommonReducer";
import {CLEAR_REDUCER_INFO} from "./RATypes";
import AuthReducer from "../../views/auth/AuthReducer";
import CrudReducer from '../../views/crud/CrudReducer';

const defaultReducers = {
	common: CommonReducer,
	auth: AuthReducer,
	crud: CrudReducer,
};

const createRootReducerWithSugar = (asyncReducers = {}) => {
	return (state, action) => {
		if (action.type === CLEAR_REDUCER_INFO) {
			state = {...state};

			if (!Array.isArray(action.dataTarget))
				action.dataTarget = [action.dataTarget];

			action.dataTarget.forEach(field => {
				state[action.reducer][field] = action.cleanValue;
			});
		}

		return combineReducers({...defaultReducers, ...asyncReducers})(state, action);
	};
};

const createAppropriateStore = process.env.NODE_ENV === 'development' ? console.tron.createStore : createStore;
const reduxStore = createAppropriateStore(createRootReducerWithSugar(), applyMiddleware(thunk));
reduxStore.asyncReducers = {};

const registerLazyReducer = (name, reducer) => {
	if (!reduxStore.asyncReducers[name]) {
		console.debug(`Registering Lazy Reducer ${name} ...`);
		reduxStore.asyncReducers[name] = reducer;
		reduxStore.replaceReducer(createRootReducerWithSugar(reduxStore.asyncReducers));
		console.debug(`Lazy Reducer ${name} registered!`);
	}
};


export default {
	store: reduxStore,
	getState: reduxStore.getState,
	dispatch: reduxStore.dispatch,
	registerLazyReducer
};