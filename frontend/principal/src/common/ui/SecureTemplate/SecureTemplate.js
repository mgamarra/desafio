import React, {Component, Fragment} from "react";

import HeaderMobile from "./HeaderMobile";
import LeftMenu from "./LeftMenu";
import HeaderDesktop from "./HeaderDesktop";

export default class SecureTemplate extends Component {
	render() {
		return (
			<Fragment>
				<HeaderMobile/>
				<div className="page-wrapper">
					<LeftMenu/>

					<div className="page-container">
						<HeaderDesktop/>

						{this.props.children}
					</div>
				</div>
			</Fragment>
		);
	}
}
