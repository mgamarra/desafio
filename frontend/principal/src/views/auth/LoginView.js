import React, {Component, Fragment} from "react";
import "./Auth.css";
import {connect} from "react-redux";
import AuthActions from "./AuthActions";
import Utils from "../../common/Utils";
import Constants from "../../common/Constants";
import {Link, Redirect} from "react-router-dom";

const mapReduxStateToProps = reduxState => ({
	loggedIn: reduxState.auth.loggedIn
});

const mapReduxDispatchToProps = {
	login: AuthActions.login,
};

export default connect(mapReduxStateToProps, mapReduxDispatchToProps)(
	class LoginView extends Component {
		constructor(props) {
			super(props);

			this.state = {
				name: "admin",
				password: "123456",
			};
		}

		/*
		 * Custom actions
		 */

		isFormValid() {
			return !Utils.isEmpty(this.state.name) && !Utils.isEmpty(this.state.password);
		}

		handleChange = event => {
			this.setState({
				[event.target.id]: event.target.value
			});
		};

		handleSubmit = async event => {
			event.preventDefault();
			this.props.login({
				name: this.state.name,
				password: this.state.password,
			});
		};


		/*
		 * React life cycles
		 */

		render() {
			return (
				<Fragment>
					<div className="container">
						<div className="login-wrap">
							<div className="login-content">
								<div className="login-logo">
									<Link to={"/"}>
										<img src={require("./../../assets/images/logo.png")} alt="appname"/>
									</Link>
								</div>
								<div className="login-form">
									<form action="" method="post" onSubmit={this.handleSubmit}>
										<div className="form-group">
											<label>Nome</label>
											<input className="au-input au-input--full"
											       required={true}
											       id="name"
											       name="name"
											       value={this.state.name}
											       onChange={this.handleChange}
											       placeholder="Nome"/>
										</div>
										<div className="form-group">
											<label>Senha</label>
											<input className="au-input au-input--full"
											       type="password"
											       required={true}
											       id="password"
											       name="password"
											       value={this.state.password}
											       onChange={this.handleChange}
											       placeholder="Senha"/>
										</div>

										<button className="au-btn au-btn--block au-btn--green m-b-20"
										        disabled={!this.isFormValid()}
										        type="submit">Entrar
										</button>
									</form>

								</div>
							</div>
						</div>
					</div>


					{/*REDIRECT TO HOME AFTER LOGIN PROCESS*/}
					{this.props.loggedIn &&
					<Redirect to={Constants.ROUTES.HOME}/>
					}
				</Fragment>
			);
		}
	}
);
