package br.com.challenge.core.base.security.authorization;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.challenge.core.data.enumeration.Role;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


public class SecureVoter implements AccessDecisionVoter<Object> {

    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return attribute instanceof SecureConfigAttribute;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        SecureConfigAttribute attribute = findSecureConfigAttribute(attributes);

        if (attribute == null) {
            return ACCESS_ABSTAIN;
        }

        return isAccessGranted(authentication, attribute) ? ACCESS_GRANTED : ACCESS_DENIED;
    }

    private boolean isAccessGranted(Authentication authentication, SecureConfigAttribute secure) {
        if (secure.anonymous) {
            return authenticationTrustResolver.isAnonymous(authentication);
        }

        if (secure.authenticated) {
            if (authenticationTrustResolver.isAnonymous(authentication)) {
                return false;
            }
        }

        if (secure.authenticatedFully) {
            if (authenticationTrustResolver.isAnonymous(authentication) || authenticationTrustResolver.isRememberMe(authentication)) {
                return false;
            }
        }

        if (secure.hasRole.length > 0) {
            if (!hasRole(authentication, secure.hasRole)) {
                return false;
            }

        } else if (secure.hasAnyRole.length > 0) {
            if (!hasAnyRole(authentication, secure.hasAnyRole)) {
                return false;
            }
        }

        return true;
    }

    private boolean hasRole(Authentication authentication, Role[] roles) {
        List<String> authorities = getAuthorities(authentication);

        for (Role role : roles) {
            if (!authorities.contains(role.name())) {
                return false;
            }
        }
        return true;
    }

    private boolean hasAnyRole(Authentication authentication, Role[] roles) {
        List<String> authorities = getAuthorities(authentication);

        for (Role role : roles) {
            if (authorities.contains(role.name())) {
                return true;
            }
        }
        return false;
    }

    private List<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private SecureConfigAttribute findSecureConfigAttribute(Collection<ConfigAttribute> config) {
        return (SecureConfigAttribute) config.stream().filter(attr -> attr instanceof SecureConfigAttribute)
                .findFirst().orElse(null);
    }
}
