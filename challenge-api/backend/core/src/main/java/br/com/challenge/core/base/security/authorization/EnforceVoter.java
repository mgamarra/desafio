package br.com.challenge.core.base.security.authorization;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import br.com.challenge.core.base.security.authentication.UserPrincipal;


public class EnforceVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return attribute instanceof EnforceConfigAttribute;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		EnforceConfigAttribute attribute = findEnforceConfigAttribute(attributes);

		if (attribute == null) {
			return ACCESS_ABSTAIN;
		}

		return isAccessGranted(authentication, attribute) ? ACCESS_GRANTED : ACCESS_DENIED;
	}

	private boolean isAccessGranted(Authentication authentication, EnforceConfigAttribute enforce) {
		UserPrincipal userPrincipal = null;

		if (authentication.getPrincipal() != null && authentication.getPrincipal() instanceof UserPrincipal) {
			userPrincipal = (UserPrincipal) authentication.getPrincipal();
		}

//		if (enforce.team && (userPrincipal == null || userPrincipal.getGroup() == null)) {
//			return false;
//		}
//
//		if (enforce.project && (userPrincipal == null || userPrincipal.getGroup() == null || userPrincipal.getProject() == null)) {
//			return false;
//		}

		return true;
	}

	private EnforceConfigAttribute findEnforceConfigAttribute(Collection<ConfigAttribute> config) {
		for (ConfigAttribute attribute : config) {
			if (attribute instanceof EnforceConfigAttribute) {
				return (EnforceConfigAttribute) attribute;
			}
		}
		return null;
	}

}
