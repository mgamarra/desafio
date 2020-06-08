/**
 * Generate a UUID
 * @param withSeparator bolean
 * @returns {string}
 */
function uuid(withSeparator = true) {
	let sep = withSeparator ? '-' : '';
	let uuid = '';

	for (let i = 0; i < 8; i++)
		uuid += Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1) + (i < 7 ? sep : "");

	return uuid;
}


/**
 * Generate a half size UUID
 * @param withSeparator bolean
 * @returns {string}
 */
function halfUuid(withSeparator) {
	let uuid = this.uuid(withSeparator);
	return uuid.substring(0, uuid.length / 2);
}


/**
 * Encode object json to url parameters
 *
 * @param      {Object} json The object needs to encode as url parameters;
 * @param      {Object} complete If string must be returned with "?", complete should be true;
 */
function jsonToQueryString(json = {}, complete = false) {
	let str = '';
	for (const key in json) {
		if (!isEmpty(json[key]) && !isEmpty(str))
			str += '&';

		if (!isEmpty(json[key]))
			str += `${key}=${encodeURIComponent(json[key])}`;
	}

	if (complete && str.length > 0)
		str = "?" + str;

	return str;
}


/**
 * Test if a given value is empty or empty like.
 *
 * @param value: Value to bem asserted
 * @param zeroIsEmpty: In case of numbers, check if zeros are considered null/empty.
 * @returns boolean: true if is empty, false if don´t;
 */
function isEmpty(value, zeroIsEmpty = false) {
	if (typeof zeroIsEmpty !== "boolean")
		throw new Error("zeroIsEmpty must be boolean");

	switch (Object.prototype.toString.call(value)) {
		case '[object Undefined]':
		case '[object Null]':
			return true;

		case '[object String]':
			if (value.trim().length === 0
				|| value.trim().toLowerCase() === 'undefined'
				|| value.trim().toLowerCase() === 'null'
				|| value.trim().toLowerCase() === 'empty'
				|| (value.safeContains("R$") && value.brazilianRealToFloat() === 0 && zeroIsEmpty))
				return true;
			break;

		case '[object Number]':
			if (zeroIsEmpty && value === 0)
				return true;
			break;

		case '[object Object]':
			if (Object.keys(value).length === 0)
				return true;
			break;

		case '[object Array]':
			if (value.length === 0)
				return true;
			break;

		default:
			return false;
	}

	return false;
}

/**
 * Check is desired param is a true JSON object
 * @param param
 * @returns {boolean}
 */
function isJson(param) {
	return Object.prototype.toString.call(param) === '[object Object]';
}


/**
 * Generate a random color
 */

const generateRandomColor = (() => {
	const randomInt = (min, max) => {
		return Math.floor(Math.random() * (max - min + 1)) + min;
	};

	return () => {
		let h = randomInt(0, 360);
		let s = randomInt(42, 98);
		let l = randomInt(40, 90);
		return `hsl(${h},${s}%,${l}%)`;
	};
})();


/**
 * Generate a paged array based on a base array informed
 */
function generatePagedArray(array = [], currentPage = 0, pageSize = 5) {
	let first = currentPage === 0
		? 0
		: currentPage * pageSize;

	let last;

	if (currentPage === 0)
		if (pageSize > array.length)
			last = array.length;
		else
			last = pageSize;

	else if (((currentPage + 1) * pageSize) > array.length)
		last = array.length;

	else
		last = ((currentPage + 1) * pageSize);

	return {
		first: currentPage === 0,
		last: currentPage === Math.ceil(array.length / pageSize) - 1,
		totalPages: Math.ceil(array.length / pageSize),
		totalElements: array.length,
		size: pageSize,
		content: array.slice(first, last)
	}
}


/**
 * Iterate over JSON structure and join keys and values in a single string, like:
 * E.G:
 *      JSON Input: {KEY:VALUE, KEY2:VALUE2}
 *
 *      -
 *
 *      String Output:
 *
 *      KEY:\n
 *      VALUE\n\n
 *
 *      KEY2:\n
 *      VALUE2\n\n
 *      ...
 *
 * and so on.
 *
 * If keys are translatable, needs to pass a second function parameter of REACT translator instance, cause utils is outside of react domain.
 */
function jsonToSpecificPlainText(json, translator) {
	if (json) {
		let translate = (k) => translator ? translator(k) : k;
		let string = "";

		for (let k in json)
			if (json.hasOwnProperty(k) && !isEmpty(json[k]))
				string += translate(k) + ":\n" + json[k] + "\n\n";

		return string;
	}

	return "";
}

/**
 *  Deep Merge JSON objects.
 *
 *  E.g:
 *
 *  let a = {testA: {test1: '1', test2: '1'}};
 *  let b = {testA: {test2: '2', test3: '3'}};
 *
 *  utils.deepJsonMerge(a, b); //{test1: '1', test2: '2', test3: '3'}};
 *
 * @param target - let a = {testA: {test1: '1', test2: '1'}};
 * @param source - let b = {testA: {test2: '2', test3: '3'}};
 * @returns Deep Merged Json - {test1: '1', test2: '2', test3: '3'}};
 */
function deepJsonMerge(target = {}, source = {}) {
	let isObject = item => (item && typeof item === 'object' && !Array.isArray(item));
	let output = Object.assign({}, target);

	if (isObject(target) && isObject(source)) {
		Object.keys(source).forEach(key => {
			if (isObject(source[key])) {
				if (!(key in target))
					Object.assign(output, {[key]: source[key]});
				else
					output[key] = deepJsonMerge(target[key], source[key]);
			} else {
				Object.assign(output, {[key]: source[key]});
			}
		});
	}

	return output;
}


/**
 * Check if given Json has entire path of keys.
 *
 * E.g:
 * Consider:
 * let json = {level1: {level2: {level3: {level4: "hi nigga"}}}};
 *
 * Common undefined check can be:
 *
 * json.level1 && json.level1.level2 && json.level1.level2.level3 && json.level1.level2.level3.level4; //true
 * json.level1 && json.level1.level2 && json.level1.level2.level3 && json.level1.level2.level3.levelXYZ; //false
 *
 * With Json Has Path:
 *
 * utils.jsonHasPath(json, "level1.level2.level3.level4"); //true
 * utils.jsonHasPath(json, "level1.level2.level3.levelXYZ"); //false
 *
 * @param json - Desired json to check. Sample: let json = {level1: {level2: {level3: {level4: "hi nigga"}}}};
 * @param path - Desired path to check. Must follow this sample: "level1.level2.level3.level4"
 * @returns {boolean} - True if path is valid, false otherwise
 */
function jsonHasPath(json = {}, path = "") {
	let args = path.split(".");

	for (let i = 0; i < args.length; i++) {
		if (!json || !json.hasOwnProperty(args[i]))
			return false;

		json = json[args[i]];
	}

	return true;
}


/**
 * Escape html content
 * @param string
 * @returns Escaped html
 */
let stripHTML = (string) => string.replace(/<(?:.|\n)*?>/gm, '');

export default {
	uuid,
	halfUuid,
	jsonToQueryString,
	isEmpty,
	isJson,
	generateRandomColor,
	generatePagedArray,
	jsonToSpecificPlainText,
	deepJsonMerge,
	jsonHasPath,
	stripHTML,
}
