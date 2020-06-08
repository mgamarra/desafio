import SuperagentBackend from "./superagent/SuperagentBackend";

const auth = {
	login: (loginData) => SuperagentBackend.post('/auth/login', loginData).timeout({response: 50000}),
};

const user = {
	fetchAllUsers: () => SuperagentBackend.get('/users'),
	save: (data) => SuperagentBackend.post('/users/save', data),
};

const addresses = {
	fetchAddressByZipcode: (zipcode) => SuperagentBackend.get('/addresses?zipcode=' + zipcode),
};

export default {
	auth,
	user,
	addresses,
};


