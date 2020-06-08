package br.com.challenge.rest.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.challenge.core.base.security.authentication.UserPrincipal;


public class JwtTokenAuthentication extends AbstractAuthenticationToken {
    private final UserPrincipal user;

    public JwtTokenAuthentication(UserPrincipal user) {
        super(user.getAuthorities());
        this.user = user;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return user.getToken();
    }

    @Override
    public UserDetails getPrincipal() {
        return user;
    }
}
