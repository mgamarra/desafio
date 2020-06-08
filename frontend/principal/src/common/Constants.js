
export default {
	API_BASE_PATH: process.env.NODE_ENV === 'development' ? "http://localhost:8080/rest/v1" : "/rest/v1",

	ROUTES: {
		ROOT: "/",
		LOGIN: "/login",
		REGISTER: "/register",
		FORGOT: "/forgot",
		HOME: "/home",
		CRUD: {
			HOME: "/crud",
			NEW: "/crud/new",
			EDIT: "/crud/:id"
		},
		UNAUTHORIZED: "/401",
	}
};