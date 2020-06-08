import _ from "underscore";

/**
 * Checa as roles de um usuario logado
 * @param userRoles
 * @param requiredRoles
 * @param matchMode
 * @returns {boolean}
 */

export default {
	isAuthorized: (userRoles, requiredRoles, matchMode) => {
		if (!requiredRoles || !userRoles || !matchMode)
			return true;
			throw new Error("AppUtils: requiredRoles, matchMode={'all', 'any'} and userRoles properties are required.");

		requiredRoles = Array.isArray(requiredRoles) ? requiredRoles : [requiredRoles];
		const intersectionRoles = requiredRoles && userRoles && userRoles.length > 0 ? _.intersection(userRoles, requiredRoles) : [];

		if (requiredRoles.indexOf("*") !== -1
			|| (matchMode === "any"
				? intersectionRoles.length > 0
				: intersectionRoles.length === requiredRoles.length)) {
			return true;
		} else {
			return false;
		}
	},
}
