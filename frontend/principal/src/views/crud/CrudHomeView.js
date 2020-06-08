import React, {Component} from "react";
import SecureTemplate from "../../common/ui/SecureTemplate/SecureTemplate";
import {connect} from "react-redux";
import CrudActions from "./CrudActions";

const mapReduxStateToProps = reduxState => ({
	users: reduxState.crud.users,
});



const mapReduxDispatchToProps = {
	fetchAll: CrudActions.fetchAllUsers,
};

export default connect(mapReduxStateToProps, mapReduxDispatchToProps)( class CrudHomeView extends Component{
	constructor(props) {
		super(props);

		this.state = {};
		this.props.fetchAll({});
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
										<h2 className="title-1">Usuarios</h2>
									</div>
								</div>
							</div>
							
								<div className="row p-4">
								<table className="table table-striped">
									<thead>
										<tr>
											<th scope="col" className="text-center">Nome</th>
											<th scope="col" className="text-center">CPF</th>
											<th scope="col" className="text-center">E-mail</th>
											<th scope="col" className="text-center">Endere&ccedil;o</th>
										</tr>
									</thead>
									<tbody>
									{this.props.users && this.props.users.content && this.props.users.content.map((user, i) => (
										<tr key={i}>
											<td className="text-center">{user.name}</td>
											<td className="text-center">{user.cpf.maskCPF()}</td>
											<td className="text-center">{user.emails.emails}</td>
											<td className="text-center">{user.address && `${user.address.description}, ${user.address.city}, ${user.address.state} `}</td>
											<td className="text-center"></td>
										</tr>
										))}
		
									</tbody>
								</table>
		
							</div>
							
						</div>
					</div>
				</div>
			</SecureTemplate>
		);
	}
}
);