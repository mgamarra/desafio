import React, {Component, Fragment} from "react";
import "./common/ReactotronConf";
import {BrowserRouter as Router, Switch} from "react-router-dom";
import Constants from "./common/Constants";
import E404 from "./views/error/E404";
import Route from "react-router/es/Route";
import SecureRoute from "./common/router/SecureRoute";
import Notifications from "./common/ui/connected/Notifications/Notifications";
import Loading from "./common/ui/connected/Loading/Loading";
import E401 from "./views/error/E401";
import LoginView from "./views/auth/LoginView";
import HomeView from "./views/home/HomeView";
import PublicRoute from "./common/router/PublicRoute";
import CrudHomeView from "./views/crud/CrudHomeView";
import CrudMaintainView from "./views/crud/CrudMaintainView";
import AuthReducer from "./views/auth/AuthReducer";
import ReduxStore from "../src/common/redux/ReduxStore";
import CrudReducer from "./views/crud/CrudReducer";

class App extends Component {
	constructor(props) {
		super(props);
		
		ReduxStore.registerLazyReducer("auth", AuthReducer);
		ReduxStore.registerLazyReducer("crud", CrudReducer);
	}

	render() {
		return (
			<Fragment>
				<Loading/>
				<Notifications/>

				<Router>
					<Switch>
						<PublicRoute path={Constants.ROUTES.ROOT}
						             component={LoginView}/>

						<PublicRoute path={Constants.ROUTES.LOGIN}
						             component={LoginView}/>

						<SecureRoute path={Constants.ROUTES.HOME}
						             component={HomeView}/>

						<SecureRoute path={Constants.ROUTES.CRUD.HOME}
						             component={CrudHomeView}/>

						<SecureRoute path={Constants.ROUTES.CRUD.EDIT}
						             component={CrudMaintainView}/>

						<SecureRoute path={Constants.ROUTES.UNAUTHORIZED}
						             component={E401}/>
						<Route component={E404}/>
					</Switch>
				</Router>
			</Fragment>
		);
	}
}

export default App;
