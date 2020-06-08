import React from "react";
import "./LoaderButton.css";

export default ({isLoading, text, loadingText, className = "", disabled = false, ...props}) => (
	<button type="submit" className={`LoaderButton au-btn au-btn--block au-btn--green m-b-20 ${className}`}>
		{!isLoading ? text : loadingText}
	</button>
);