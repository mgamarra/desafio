import {FETCH_ALL_USERS , FETCH_ADDRESS_BY_ZIPCODE} from "../../common/redux/RATypes";

const defaultState = {
	users: [],
	address: {},
};

export default (state = defaultState, action) => {
	switch (action.type) {
		
		case FETCH_ALL_USERS:
			return {
				...state,
				users: action.data
			};

		case FETCH_ADDRESS_BY_ZIPCODE:
			return {
				...state,
				address: action.data
			};

		default:
			return state
	}
}