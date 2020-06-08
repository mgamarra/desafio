package br.com.challenge.core.data.enumeration;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@Getter
public enum UserLocale {
	
	PT_BR ("pt", "BR"),
	EN 	  ("en", ""),
	//SPANISH		("es", "")
	;
	
	private String language;
	private String country;
	
	private UserLocale(String language, String country){
		this.language = language;
		this.country = country;
	}
	
	public Locale getLocale(){
		return new Locale(language, country);
	}
	
	public static UserLocale valueOfEnum(String language){
		if(StringUtils.isBlank(language)) return null;
		language = language.trim().toLowerCase();
		for(UserLocale element : UserLocale.values()){
			if(element.name().toLowerCase().equals(language)){
				return element;
			}
		}
		return null;
	}
}
