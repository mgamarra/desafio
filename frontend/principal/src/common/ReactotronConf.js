import Reactotron, {trackGlobalErrors} from 'reactotron-react-js';
import {reactotronRedux} from 'reactotron-redux';

if (process.env.NODE_ENV === 'development') {
	Reactotron
		.configure({
			name: 'appname - Investor'
		})
		.use(reactotronRedux())
		.use(trackGlobalErrors())
		.connect();

	Reactotron.clear();

	console.tron = Reactotron
}