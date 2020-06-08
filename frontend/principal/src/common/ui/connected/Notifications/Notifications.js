import React, {Component, Fragment} from "react";
import ReactNotification from "react-notifications-component";
import "react-notifications-component/dist/theme.css";

export default class Notifications extends Component {
	static notificationDOMRef = React.createRef();

	static addNotification(messages) {
		console.log("DEBUG BOLADO >>>>>> ", messages);

		let titles = {
			DANGER: "Erro",
			INFO: "Informação",
			WARNING: "Atenção",
			SUCCESS: "Sucesso",
		};

		if (messages && Array.isArray(messages) && messages.length > 0)

			messages.forEach((e, i) => {
				if (e.severity === "ERROR")
					e.severity = "DANGER";

				Notifications.notificationDOMRef.current.addNotification({
					title: e.title || titles[e.severity],
					message: e.message,
					type: e.severity.toLowerCase(),
					insert: "top",
					container: "top-right",
					animationIn: ["animated", "fadeIn"],
					animationOut: ["animated", "fadeOut"],
					dismiss: {duration: 10000},
					dismissable: {click: true}
				});
			});
	}

	render() {
		return (
			<Fragment>
				<ReactNotification ref={Notifications.notificationDOMRef}/>
			</Fragment>
		);
	}
}

