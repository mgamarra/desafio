package br.com.challenge.core.base.security.authentication;

import java.util.Collection;
import java.util.Collections;

import br.com.challenge.core.data.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

public class UserPrincipal implements UserDetails {

    @Getter
    private User user;

    @Getter
    private Collection<? extends GrantedAuthority> authorities;

    @Getter
    private String token;

    /*
     * Constructor
     */
    public UserPrincipal() {
    }

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities != null
                ? Collections.unmodifiableCollection(authorities)
                : Collections.emptyList();
    }

    public UserPrincipal(String token, User user, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.user = user;
        this.authorities = authorities;
    }

    /*
     * Public Methods
     */

    @Override
    public String getUsername() {
        return user.getName();
    }

    public String getName() {
        return user.getName();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
