import {ASYNC_END, ASYNC_START,} from './RATypes';

const defaultState = {
	messages: [],
	isFetching: false,
	fetchingActions: [],
	fetchingHttpRequests: [],
};

export default (state = defaultState, action) => {
	switch (action.type) {
		case ASYNC_START:
			return {
				...state,
				isFetching: true,
				fetchingActions: [...state.fetchingActions, action.subtype],
				fetchingHttpRequests: [...state.fetchingHttpRequests, action.httpRequest],
			};

		case ASYNC_END:
			let index = state.fetchingActions.indexOf(action.subtype);
			let fetchingActions = [...state.fetchingActions];
			let fetchingHttpRequests = [...state.fetchingHttpRequests];

			fetchingActions.splice(index, 1);
			fetchingHttpRequests.splice(index, 1);

			return {
				...state,
				isFetching: fetchingActions.length !== 0,
				fetchingActions,
				fetchingHttpRequests,
			};
		default:
			return state
	}
}