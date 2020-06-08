import React, {Component} from "react";
import AuthActions from "../../../views/auth/AuthActions";
import {connect} from "react-redux";
import Constants from "../../Constants";

const mapReduxStateToProps = reduxState => ({
	loggedIn: reduxState.auth.loggedIn,
	user: reduxState.auth.user,
});

const mapReduxDispatchToProps = {
	logout: AuthActions.logout,
};

export default connect(mapReduxStateToProps, mapReduxDispatchToProps)(
	class HeaderDesktop extends Component {
		constructor(props) {
			super(props);
			this.state = {
				showNotificationDropdown: false,
				showAccountDropdown: false,
			};
		}

		logout = (e) => {
			e.preventDefault();
			this.props.logout();

			setTimeout((value) => {
				console.log(value);
				window.location.replace(Constants.ROUTES.LOGIN);
			}, 100);
		};

		toggleAccountDropdown = (e) => {
			e.preventDefault();
			this.setState({showAccountDropdown: !this.state.showAccountDropdown});
		};

		toggleNotificationDropdown = (e) => {
			e.preventDefault();
			this.setState({showNotificationDropdown: !this.state.showNotificationDropdown});
		};

		hideDropdowns = () => {
			this.setState({showAccountDropdown: false, showNotificationDropdown: false});
		};

		render() {
			return (
				<header className="header-desktop">
					<div className="section__content section__content--p30">
						<div className="container-fluid">
							<div className="header-wrap">
								<form className="form-header" action="" method="POST">
									<input className="au-input au-input--xl" type="text" name="search"
									       placeholder="O que deseja?"/>
									<button className="au-btn--submit" type="submit">
										<i className="fa fa-search"/>
									</button>
								</form>
								<div className="header-button">
									<div className="noti-wrap">
										<div
											className={`noti__item js-item-menu ${this.state.showNotificationDropdown && "show-dropdown"}`}
											onClick={this.toggleNotificationDropdown}>
											<i className="fa fa-bell"/>
											<span className="quantity">3</span>
											<div className="notifi-dropdown js-dropdown">
												<div className="notifi__title">
													<p>You have 3 Notifications</p>
												</div>
												<div className="notifi__item">
													<div className="bg-c1 img-cir img-40">
														<i className="fa fa-mail-bulk"/>
													</div>
													<div className="content">
														<p>You got a email notification</p>
														<span className="date">April 12, 2018 06:50</span>
													</div>
												</div>
												<div className="notifi__item">
													<div className="bg-c2 img-cir img-40">
														<i className="fa fa-user-alt"/>
													</div>
													<div className="content">
														<p>Your account has been blocked</p>
														<span className="date">April 12, 2018 06:50</span>
													</div>
												</div>
												<div className="notifi__item">
													<div className="bg-c3 img-cir img-40">
														<i className="fa fa-file-alt"/>
													</div>
													<div className="content">
														<p>You got a new file</p>
														<span className="date">April 12, 2018 06:50</span>
													</div>
												</div>
												<div className="notifi__footer">
													<a href="#">All notifications</a>
												</div>
											</div>
										</div>
									</div>
									<div className="account-wrap">
										<div
											className={`account-item clearfix js-item-menu ${this.state.showAccountDropdown && "show-dropdown"}`}
											onClick={this.toggleAccountDropdown}>
											<div className="image">
												
											</div>
											<div className="content">
												<a className="js-acc-btn"
												   href="#">
													{this.props.user.name} 
													<i className="fa fa-angle-down" style={{marginLeft: "5px"}}/>
												</a>
											</div>
											<div className="account-dropdown js-dropdown">
												<div className="info clearfix">
													<div className="image">
														<img
															src="https://scontent.fbsb3-1.fna.fbcdn.net/v/t1.0-9/49712758_2135094853225990_4546655368842838016_n.jpg?_nc_cat=104&_nc_ht=scontent.fbsb3-1.fna&oh=97eca3a131651f69a25b95964179282b&oe=5CFEB618"
															alt={this.props.user.name }/>
													</div>
													<div className="content">
														<h5 className="name">
															<a href="#">{this.props.user.name} </a>
														</h5>
													</div>
												</div>
												<div className="account-dropdown__body">
													<div className="account-dropdown__item">
														<a href="#">
															<i className="fa fa-user"/>Meus dados</a>
													</div>
												</div>
												<div className="account-dropdown__footer">
													<a href="#" onClick={this.logout}>
														<i className="fa fa-power-off"/>Logout
													</a>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</header>
			);
		}

		componentDidMount() {
			window.jQuery("body,html").on("click", () => {
				this.hideDropdowns();
			});
		}
	}
);