import React, {Component} from "react";
import ReduxStore from "./redux/ReduxStore";

export default function asyncComponent(lazyModule) {
	class AsyncComponent extends Component {
		constructor(props) {
			super(props);

			this.state = {
				component: null
			};
		}

		async componentDidMount() {
			// ReduxStore.dispatch(CommonActions.asyncStart(VIEW_MODULE_LOADING));
			const module = await lazyModule();

			// setTimeout((value) => {
			// 	ReduxStore.dispatch(CommonActions.asyncEnd(VIEW_MODULE_LOADING));
			// }, 500);

			let lazyComponent;

			if (module.reducer && module.reducerName)
				ReduxStore.registerLazyReducer(module.reducerName, module.reducer);

			if (module.lazyComponent)
				lazyComponent = module.lazyComponent;

			else if (module.default)
				lazyComponent = module.default;

			this.setState({
				component: lazyComponent
			});
		}

		render() {
			const C = this.state.component;

			return C ? <C {...this.props} /> : null;
		}
	}

	return AsyncComponent;
}
