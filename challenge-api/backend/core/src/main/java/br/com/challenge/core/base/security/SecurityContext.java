package br.com.challenge.core.base.security;

import br.com.challenge.core.data.entity.User;
import br.com.challenge.core.data.enumeration.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.challenge.core.base.security.authentication.UserPrincipal;


public abstract class SecurityContext {
    public static User getUser() {
        UserPrincipal userPrincipal = getUserPrincipal();
        return userPrincipal != null ? userPrincipal.getUser() : null;
    }

    public static Long getUserId() {
        User user = getUser();
        return user != null ? user.getId() : null;
    }

    public static UserPrincipal getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof UserPrincipal) {
            return (UserPrincipal) authentication.getPrincipal();
        } else {
            return null;
        }
    }

    public static boolean hasRole(Role role) {
        UserPrincipal userPrincipal = getUserPrincipal();
        if (userPrincipal != null) {
            return userPrincipal.getAuthorities().contains(role);
        }
        return false;
    }

    public static <T> T getUserPrincipalAs() {
        return (T) getUserPrincipal();
    }
}
