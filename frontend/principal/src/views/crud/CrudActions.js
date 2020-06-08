import { FETCH_ALL_USERS, FETCH_ADDRESS_BY_ZIPCODE, CREATE_USER } from "../../common/redux/RATypes";
import RestAPI from "../../common/RestAPI";
import Notifications from "../../common/ui/connected/Notifications/Notifications";
import CommonActions from "../../common/redux/CommonActions";

let saveUser = (data) => CommonActions.baseAsyncThunk(
	RestAPI.user.save(data),
	CREATE_USER,
	(res, hasError) => {
		if (hasError && res.response) {
			Notifications.addNotification(res.response.body.messages);

			return res.response.body;
		} else if (res && res.body && res.body.messages) {
			Notifications.addNotification(res.body.messages);

			return {registeredSuccessfully: true};
		}
	});

let fetchAllUsers = () => CommonActions.baseAsyncThunk(
	RestAPI.user.fetchAllUsers(),
	FETCH_ALL_USERS,
	(res, hasError) => {


		if (hasError && res.response) {
			Notifications.addNotification(res.response.body.messages);

			return res.response.body;
		} else if (res && res.body && res.body.messages) {
			Notifications.addNotification(res.body.messages);
			return res.body.data;
		}
	});

let fetchAddressByZipcode = (zipcode) => CommonActions.baseAsyncThunk(
		RestAPI.addresses.fetchAddressByZipcode(zipcode),
		FETCH_ADDRESS_BY_ZIPCODE,
		(res, hasError) => {
	
			if (hasError && res.response) {
				Notifications.addNotification(res.response.body.messages);
	
				return res.response.body;
			} else if (res && res.body && res.body.messages) {
				Notifications.addNotification(res.body.messages);
				return res.body.data;
			}
		});


export default {
	fetchAllUsers,
	fetchAddressByZipcode,
	saveUser,
};