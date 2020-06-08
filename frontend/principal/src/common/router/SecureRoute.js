import React from "react";
import {Redirect, Route} from "react-router-dom";
import AppUtils from "../AppUtils";
import PropTypes from "prop-types";
import Constants from "../Constants";
import ReduxStore from "../redux/ReduxStore";

/**
 * Wrapper que garante a segurança de uma rota segura
 *
 * @param component: Componente de destino que será renderizado
 * @param requiredRoles: As roles que a rota precisa para ser acessada. Pode ser apenas uma role ou um array de roles, ou * para qualquer role.
 * @param matchMode: [any: Se o usuário tiver qualquer uma ou mais roles requeridas, autoriza. all: O usuário precisa ter todas as roles requeridas para authorizar]
 * @param rest: Demais parametros a serem repassados aos filhos
 * @returns O famigerado componente.
 */
const SecureRoute = ({component: Component, requiredRoles = ["*"], matchMode = "any", ...rest}) => {
	let RouteToRender;
	console.debug("> Creating SecureRoute", [rest, Component, typeof Component]);

	if (!Component)
		throw Error("Missing 'component' property");

	else if (!ReduxStore.getState().auth.loggedIn)
		RouteToRender = () => <Redirect to={Constants.ROUTES.LOGIN}/>;

	else if (!AppUtils.isAuthorized(ReduxStore.getState().auth.roles, requiredRoles, matchMode) && !rest.location.pathname.safeContains("/401"))
		RouteToRender = () => <Redirect to={Constants.ROUTES.UNAUTHORIZED}/>;

	else {
		RouteToRender = () => <Route {...rest} component={(props) => <Component {...props}/>}/>
	}

	return <RouteToRender/>;
};

SecureRoute.propTypes = {
	component: PropTypes.oneOfType([PropTypes.func]).isRequired,
};

SecureRoute.defaultProps = {
	exact: true
};

SecureRoute.propTypes = {
	component: PropTypes.oneOfType([PropTypes.any]).isRequired,
	componentParams: PropTypes.oneOfType([PropTypes.object]),
	isLoggedIn: PropTypes.oneOfType([PropTypes.bool]).isRequired,
	matchMode: PropTypes.oneOfType([PropTypes.string]),
	pathname: PropTypes.oneOfType([PropTypes.string]),
	userGroup: PropTypes.oneOfType([PropTypes.string]),
};

SecureRoute.defaultProps = {
	exact: true
};

export default SecureRoute;