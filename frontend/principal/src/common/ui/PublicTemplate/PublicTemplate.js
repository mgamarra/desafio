import React, {Component} from "react";

export default class PublicTemplate extends Component {
	render() {
		return (
			<div className="page-wrapper">
				<div className="page-content--bge5" style={{...this.props.style}}>
					{this.props.children}
				</div>
			</div>
		);
	}
}
