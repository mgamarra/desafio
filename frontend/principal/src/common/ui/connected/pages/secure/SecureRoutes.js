import React, {Component, Fragment} from "react";
import SecureRoute from "../../../../../common/router/SecureRoute";
import CrudHomeView from "../../../../../views/crud/CrudHomeView";


export default class SecureRoutes extends Component {

    render(){

        let isLoggedIn = ReduxStore.getState().auth.loggedIn;
        let user = ReduxStore.getState().auth.user;
    
        let pathname = this.props.location.pathname;

        <SecureRoute path={Constants.ROUTES.CRUD.HOME}
                    exact
                    isLoggedIn={isLoggedIn}
                    pathname={pathname}
                    component={CrudHomeView}
                    userGroup="ADMIN"/>
    }

    

}