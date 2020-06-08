import SuperagentBase, {enhanceSuperAgent} from "./SuperagentBase";
import Constants from "../Constants";
import Notifications from "../ui/connected/Notifications/Notifications";

//If had a existing stored token, use it
let base64Auth = sessionStorage.getItem("jwtToken");

const getBackendHeaders = () => {
	let obj = {
		'Content-Type': 'application/json',
	};

	if (base64Auth)
		obj["Authorization"] = "Bearer " + base64Auth;

	return obj;
};

const headersPlugin = (req) => {
	let headers = getBackendHeaders();

	Object.keys(headers).forEach(e => {
		req.set(e, headers[e]);
	});
};

const interceptorPlugin = (err, res) => {
	if (err && !res) {
		Notifications.addNotification([
			{
				title: "Causa 2: ",
				message: "O nosso servidor está enfrentando problemas temporários. ",
				severity: "DANGER",
			},
			{
				title: "Causa 1: ",
				message: "Seu computador está sem acesso a internet. ",
				severity: "DANGER",
			},
			{
				message: "Não foi possível realizar a ação solicitada. \n As causas mais comuns são:",
				severity: "DANGER",
			},
		])
	}
};

// const multipart = (url, headers, method, data) => {
// 	return new Promise((resolve, reject) => {
// 		window.$.ajax({
// 			url: `${Constants.API_BASE_PATH}${url}`,
// 			type: method,
// 			headers,
// 			data: data,
// 			cache: false,
// 			contentType: false,
// 			processData: false,
// 			success: (res) => resolve({body: res}),
// 			error: (res) => reject({response: {body: JSON.parse(res.responseText)}})
// 		});
// 	});
// };


export default {
	...enhanceSuperAgent(SuperagentBase, Constants.API_BASE_PATH, headersPlugin, interceptorPlugin),
	setToken: (token) => {
		base64Auth = token;
	},
	getBackendHeaders
}