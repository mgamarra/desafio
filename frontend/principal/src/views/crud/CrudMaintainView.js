import React, {Component} from "react";
import SecureTemplate from "../../common/ui/SecureTemplate/SecureTemplate";
import {connect} from "react-redux";
import CrudActions from "./CrudActions";
import Select from 'react-select'
import Utils from "../../common/Utils";

const mapReduxStateToProps = reduxState => ({
	address: reduxState.crud.address,
});

const mapReduxDispatchToProps = {
	fetchAddressByZipcode: CrudActions.fetchAddressByZipcode,
	save : CrudActions.saveUser,
};

export default connect(mapReduxStateToProps, mapReduxDispatchToProps)( class CrudMaintainView extends Component {
	constructor(props) {
		super(props);
		
		this.state = {
			name: "",
			cpf: "",
			password: "",
			passwordConfirm: "",
			zipCode: "",
			neighborhood: "",
			city: "",
			uf: "",
			addressDescription: "",
			showModalEmail: true,
			email: "",
			emails: [],
			phones: [],
			phone: "",
			phoneType: "",
			phoneTypeDesc: "",
		};
	}

	async componentDidMount() {
	}

	handleChange = event => {
		this.setState({
			[event.target.id]: event.target.value,
		});
	};

	handleChangeSelect = phoneType => {
		this.setState({ phoneType : phoneType , phoneTypeDesc: phoneType.value});
	  };

	handleSubmit = event => {
		event.preventDefault();
		let createUserData = {
			...this.state,
			cpf: this.state.cpf.unmask(),
			zipCode: this.state.zipCode.unmask(),
		}
		this.props.save(createUserData);

	}

	handleFetchAddress = event => {

		this.props.fetchAddressByZipcode(this.state.zipCode.unmask());
		
	};
	
	isPhoneInValid() {
		return   Utils.isEmpty(this.state.phone) && this.state.phone.unmask().length < 11 && Utils.isEmpty(this.state.phoneType) ;
	}

	isEmailValid() {
		return !Utils.isEmpty(this.state.email) && this.state.email.isEmail();
	}

	static getDerivedStateFromProps(nextProps, prevState) {
		let nextState = {};

		if (nextProps.address ) {
			nextState = {
				city: nextProps.address.localidade ,
				neighborhood: nextProps.address.bairro,
				uf: nextProps.address.uf,
				addressDescription: nextProps.address.logradouro,
			};
		}

		return nextState;
	}

	
	addEmail = event => {
		event.preventDefault();
		this.setState({emails : this.state.emails.concat(this.state.email)})
	};

	addPhone = event => {
		event.preventDefault();
		this.setState({phones : this.state.phones.concat({"tipo" : this.state.phoneTypeDesc, "numero" : this.state.phone})})
	};

	isFormValid() {
		return !Utils.isEmpty(this.state.name)
			&& !Utils.isEmpty(this.state.cpf)
			&& !Utils.isEmpty(this.state.city)
			&& !Utils.isEmpty(this.state.zipCode)
			&& !Utils.isEmpty(this.state.neighborhood)
			&& !Utils.isEmpty(this.state.uf)
			&& this.state.phones.length > 0
			&& this.state.emails.length > 0
		
	}

	render() {

		const options = [
			{ value: 'Residencial', label: 'Residencial' },
			{ value: 'Comercial', label: 'Comercial' },
			{ value: 'Celular', label: 'Celular' },
		  ];

		return (
			<SecureTemplate>
				<div className="main-content">
					<div className="section__content section__content--p30">
						<div className="container-fluid">
							<div className="row ">
								<div className="col-xs-12 col-sm-12 col-md-6 col-lg-4">
									<div className="overview-wrap">
										<h2 className="title-1">CRUD - Cadastrar</h2>
									</div>
								</div>
								<div className="col-xs-12 col-sm-12 col-md-6 col-lg-8 text-right">
									<button className="btn btn-primary"
											onClick={this.handleSubmit}
											disabled={!this.isFormValid()}
										    type="submit">Cadastrar
									</button>
								</div>
							</div>
							
							<div className="card m-t-20">
								<div className="card-header">
									<h5>Dados Pessoais</h5>
								</div>

								<div className="card-body card-block">
									<div className="row">
										<div className="col-xs-12 col-sm-12 col-md-6 col-lg-4">
											<label>Nome:</label>
											<input className="au-input au-input--full"
												required={true}
												id="name"
												name="name"
												value={this.state.name}
												onChange={this.handleChange}
												placeholder="Nome"/>
										</div>
										<div className="col-xs-12 col-sm-12 col-md-6 col-lg-3">
											<label>CPF:</label>
											<input className="au-input au-input--full"
												required={true}
												id="cpf"
												name="cpf"
												maxLength={14}
												value={this.state.cpf.onlyNumbers().maskCPF()}
												onChange={this.handleChange}
												placeholder="CPF"/>
										</div>

										<div className="col-xs-12 col-sm-12 col-md-6 col-lg-3">
											<label>Senha:</label>
											<input className="au-input au-input--full"
											       type="password"
											       required={true}
											       id="password"
											       name="password"
											       value={this.state.password}
											       onChange={this.handleChange}
											       placeholder="Senha"/>
										</div>

									</div>
								
								</div>
								
							</div>

							<div className="card m-t-15">
								<div className="card-header">
									<h5>Endere&ccedil;o</h5>
								</div>
								<div className="card-body card-block">
									<div className="row">
										<div className="col-xs-12 col-sm-12 col-md-6 col-lg-2">
											<label>CEP:</label>
											<input className="au-input au-input--full"
												   required={true}
												   id="zipCode"
												   name="zipCode"
												   maxLength={10}
												   value={this.state.zipCode ? this.state.zipCode.onlyNumbers().maskZipCode() : ''}
												   onChange={(e) => {
									                    this.handleChange(e);
									                    if (e.target.value.length === 10)
										                    setTimeout(() => this.handleFetchAddress())
								                    	}}
													   onClickSearch={this.handleFetchAddress}
													   placeholder="CEP"/>
										</div>
										<div className="col-xs-12 col-sm-12 col-md-6 col-lg-6">
											<label>Logradouro:</label>
											<input className="au-input au-input--full"
													required={true}
													id="addressDescription"
													name="addressDescription"
													value={this.state.addressDescription}
													onChange={this.handleChange}
													placeholder="Logradouro"/>
										</div>

										<div className="col-xs-12 col-sm-12 col-md-6 col-lg-3">
											<label>Bairro:</label>
											<input className="au-input au-input--full"
													required={true}
													id="neighborhood"
													name="neighborhood"
													value={this.state.neighborhood}
													onChange={this.handleChange}
													placeholder="Bairro"/>
										</div>
									</div>
									<div className="row m-t-15">
										
										<div className="col-xs-12 col-sm-12 col-md-6 col-lg-3">
												<label>Cidade:</label>
												<input className="au-input au-input--full"
													   required={true}
													   id="city"
													   name="city"
													   value={this.state.city}
													   onChange={this.handleChange}
													   placeholder="Cidade"/>
										</div>
										<div className="col-xs-12 col-sm-12 col-md-2 col-lg-2">
												<label>Estado:</label>
												<input className="au-input au-input--full"
													   required={true}
													   id="uf"
													   name="uf"
													   value={this.state.uf}
													   onChange={this.handleChange}
													   placeholder="Estado"/>
										</div>
									</div>
								</div>
							</div>
							
							<div className="card m-t-20">
								<div className="card-header">
									<h5>Email</h5>
								</div>
								<div className="card-body card-block">
									<div className="row">
										<div className="col-xs-12 col-sm-12 col-md-12 col-lg-4">
											<input className="au-input au-input--full"
												required={true}
												id="email"
												name="email"
												value={this.state.email}
												onChange={this.handleChange}
												placeholder="E-mail"/>
										</div>
										<div className="col-xs-12 col-sm-12 col-md-12 col-lg-4">
											<button className="btn btn-success btn-cons"
													onClick={this.addEmail}
													disabled={!this.isEmailValid()}
													type="submit">Incluir
											</button>
										</div>
									</div>
									<div className="row m-t-15">
										<div className="col-lg-6">
											<table className="table table-striped">
												<thead>
													<tr>
													</tr>
												</thead>
												<tbody>
													{this.state.emails && this.state.emails.map((email, i) => (
														<tr key={i}>
															<td className="text-center">{email}</td>
														</tr>
													))}
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div className="card m-t-15">
								<div className="card-header">
									<h5>Telefones</h5>
								</div>
								<div className="card-body card-block">
									<div className="row">
										<div className="col-xs-12 col-sm-12 col-md-12 col-lg-4">
											<Select id="phoneType"
													value={this.state.phoneType}
													onChange={this.handleChangeSelect}
													options={options}
												/>
										</div>

										<div className="col-xs-12 col-sm-12 col-md-12 col-lg-4">
											<input className="au-input au-input--full"
												required={true}
												id="phone"
												name="phone"
												maxLength={10}
												value={this.state.phone.onlyNumbers().maskTelefone()}
												onChange={this.handleChange}
												placeholder="Telefone"/>
										</div>
										<div className="col-xs-12 col-sm-12 col-md-12 col-lg-4">
											<button className="btn btn-success btn-cons"
													disabled={this.isPhoneInValid()}
													onClick={this.addPhone}
													type="submit">Incluir
											</button>
										</div>
									</div>
									<div className="row m-t-15">
										<div className="col-lg-6">
											<ul style={{'listStyleType' : 'none'}}>
												{this.state.phones &&  this.state.phones.map((phone, i) => (
													
													<li className="m-l-5" id={i}>
														<span>{phone.tipo} - {phone.numero}</span>
													</li>
												))}
											</ul>
										</div>
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
)