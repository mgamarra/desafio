import {LOGIN} from "../../common/redux/RATypes";

const defaultState = {
	users: [],
};

export default (state = defaultState, action) => {
	switch (action.type) {
		case LOGIN:
			let nextState = {...state};

			if (action.data) {
				delete action.errorData;
				nextState.token = action.data.token;
				nextState.user = action.data.user;
				nextState.loggedIn = true;

			} else if (action.errorData) {
				nextState.errorData = action.errorData;
			}

			return nextState;

		default:
			return state
	}
}