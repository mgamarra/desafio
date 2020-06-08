package br.com.challenge.core.base.security.authorization;

import org.springframework.security.access.ConfigAttribute;


public class EnforceConfigAttribute implements ConfigAttribute {

//	public final boolean project;
//	public final boolean team;

	public EnforceConfigAttribute(Enforce enforce){
//		this.team = enforce.team();
//		this.project = enforce.project();
	}

	@Override
	public String getAttribute() {
		return null;
	}
}
