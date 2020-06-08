package br.com.challenge.core.base.security.authorization;

import br.com.challenge.core.data.enumeration.Role;
import org.springframework.security.access.ConfigAttribute;

public class SecureConfigAttribute implements ConfigAttribute {

    public final boolean anonymous;

    public final boolean authenticated;

    public final boolean authenticatedFully;

    public final Role[] hasRole;

    public final Role[] hasAnyRole;

    public SecureConfigAttribute(Secure secure) {
        this.anonymous = secure.anonymous();
        this.authenticated = secure.authenticated();
        this.authenticatedFully = secure.authenticatedFully();
        this.hasRole = secure.hasRole();
        this.hasAnyRole = secure.hasAnyRole();
    }

    @Override
    public String getAttribute() {
        return null;
    }
}
