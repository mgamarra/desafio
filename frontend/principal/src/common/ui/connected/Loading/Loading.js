import React, {Component, Fragment} from "react";
import "./Loading.css";
import {connect} from "react-redux";

const mapStateToProps = store => ({
	isFetching: store.common.isFetching
});

const mapDispatchToProps = {};

export default connect(mapStateToProps, mapDispatchToProps)(
	class Loading extends Component {

		state = {};

		render() {
			return (
				<Fragment>
					<div id="loading" className={this.props.isFetching ? "show-loading" : ""}>
						<div id="loading-center">
							<div id="loading-center-absolute">
								<div className="object" id="first_object"/>
								<div className="object" id="second_object"/>
								<div className="object" id="third_object"/>
								<div className="object" id="forth_object"/>
							</div>
						</div>
					</div>
				</Fragment>
			);
		}
	}
);


