import CommonActions from "../../common/redux/CommonActions";
import {LOGIN, LOGOUT, REGISTER_NEW_USER} from "../../common/redux/RATypes";
import RestAPI from "../../common/RestAPI";
import SuperagentBackend from "../../common/superagent/SuperagentBackend";
import Notifications from "../../common/ui/connected/Notifications/Notifications";

let login = (loginData) => CommonActions.baseAsyncThunk(
	RestAPI.auth.login(loginData),
	LOGIN,
	(res, hasError) => {
		if (hasError && res.response) {
			Notifications.addNotification(res.response.body.messages);

			return res.response.body;
		} else if (res && res.body && res.body.data && res.body.data.token) {
			SuperagentBackend.setToken(res.body.data.token);

			sessionStorage.setItem("jwtToken", res.body.data.token);
			sessionStorage.setItem("user", JSON.stringify(res.body.data.user));

			return res.body.data;
		}
	});


let logout = () => dispatch => {
	SuperagentBackend.setToken(undefined);
	sessionStorage.clear();
	dispatch({type: LOGOUT})
};



export default {
	login,
	logout,
}