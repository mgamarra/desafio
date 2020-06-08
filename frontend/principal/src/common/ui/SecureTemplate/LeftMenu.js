import React, {Component} from "react";
import {Link} from "react-router-dom";
import Constants from "../../Constants";
import {connect} from "react-redux";

const mapReduxStateToProps = reduxState => ({
	user: reduxState.auth.user,
	
});

const mapReduxDispatchToProps = {
    
};

export default connect(mapReduxStateToProps, mapReduxDispatchToProps) (class LeftMenu extends Component {

	constructor(props) {
		super(props);

		this.state = {
			showSubmenuResumo: false,
			showSubmenuCrud: false
		}
	}

	toggleSubmenu = (e, submenu) => {
		e.preventDefault();

		this.setState({
			active: submenu,
			["showSubmenu" + submenu]: !this.state["showSubmenu" + submenu]
		});
	};

	isActive = (current) => this.state.active === current ? "active open" : "";

	render() {
		return (
			<aside className="menu-sidebar d-none d-lg-block">
				<div className="logo">
					<a href="#">
						<img src={require("./../../../assets/images/logo.png")} alt="Appname"/>
					</a>
				</div>
				<div className="menu-sidebar__content js-scrollbar1">
					<nav className="navbar-sidebar">
						<ul className="list-unstyled navbar__list">
							<li className={"has-sub"}>
								<a className="js-arrow" href="#">
									<i className="fas fa-baseball-ball fa-spin"/>Usu&aacute;rios
								</a>
								<ul className="list-unstyled navbar__sub-list js-sub-list">
									<li>
										<Link to={Constants.ROUTES.CRUD.HOME}>
											<i className="fas fa-list"/>Listagem
										</Link>
									</li>
									{this.props.user && this.props.user.userGroup === "ADMIN" &&
										<li>
											<Link to={Constants.ROUTES.CRUD.NEW}>
												<i className="fas fa-star-and-crescent fa-spin"/>Cadastrar
											</Link>
										</li>
									}
								</ul>
							</li>
						</ul>
					</nav>
				</div>
			</aside>
		);
	}

	componentDidMount() {
		try {
			let arrow = window.$('.js-arrow');

			arrow.each(function (e, i) {
				let that = window.$(this);
				that.on('click', e => {
					e.preventDefault();
					window.$(".js-arrow").parent().removeClass("active");
					that.parent().toggleClass("active");
					that.toggleClass("open");
					that.parent().find('.js-sub-list').slideToggle("250");
				});
			});

		} catch (error) {
			console.log(error);
		}
	}
})
