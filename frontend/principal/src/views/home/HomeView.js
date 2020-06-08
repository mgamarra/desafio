import React, {Component} from "react";
import SecureTemplate from "../../common/ui/SecureTemplate/SecureTemplate";

export default class HomeView extends Component {
	constructor(props) {
		super(props);

		this.state = {};
	}

	async componentDidMount() {
	}

	render() {
		return (
			<SecureTemplate>
				<div className="main-content">
					<div className="section__content section__content--p30">
						<div className="container-fluid">
							<div className="row">
								<div className="col-md-12">
									<div className="overview-wrap">
										<h2 className="title-1">Bem-vindo ao Challenge</h2>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</SecureTemplate>
		);
	}
}
