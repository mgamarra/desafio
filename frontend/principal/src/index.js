import * as serviceWorker from "./serviceWorker";
import "./common/Extensions";
import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import {Provider} from "react-redux";
import ReduxStore from "./common/redux/ReduxStore";

import "normalize.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "popper.js";
import "bootstrap";
import jQuery from "jquery";

import "@fortawesome/fontawesome-free/css/all.min.css";
import "./assets/css/theme.css";

window.jQuery = window.$ = jQuery;

ReactDOM.render(
	<Provider store={ReduxStore.store}>
		<App/>
	</Provider>,
	document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();

if (process.env.NODE_ENV === 'development') {
	console.debug = function () {
		console.log.apply(this, arguments);
	};
} else {
	console.debug = () => {
	};
}