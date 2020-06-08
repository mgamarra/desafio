import React from "react";
import PropTypes from "prop-types";
import {Route} from "react-router-dom";

const PublicRoute = ({component: Component, ...rest}) => {
	let RouteToRender;
	console.debug("> Creating PublicRoute", [rest, Component], typeof Component);

	if (!Component)
		throw Error("Missing 'component' property");

	// if (ReduxStore.getState().auth.loggedIn)
	// 	RouteToRender = () => <Redirect to={Constants.ROUTES.HOME}/>;

	else
		RouteToRender = () => <Route {...rest} component={(props) => <Component {...props}/>}/>;

	return <RouteToRender/>;
};

PublicRoute.propTypes = {
	component: PropTypes.oneOfType([PropTypes.func]).isRequired,
};

PublicRoute.defaultProps = {
	exact: true
};

export default PublicRoute;