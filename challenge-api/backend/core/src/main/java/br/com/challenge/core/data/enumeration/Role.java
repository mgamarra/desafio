package br.com.challenge.core.data.enumeration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

public enum Role implements GrantedAuthority {
    VIEW_CRUDSAMPLE_ROLE,
    LIST_CRUDSAMPLE_ROLE(VIEW_CRUDSAMPLE_ROLE),
    CREATE_CRUDSAMPLE_ROLE(VIEW_CRUDSAMPLE_ROLE),
    UPDATE_CRUDSAMPLE_ROLE(VIEW_CRUDSAMPLE_ROLE),
    DELETE_CRUDSAMPLE_ROLE(VIEW_CRUDSAMPLE_ROLE),
    FULL_ACCESS_ROLE(VIEW_CRUDSAMPLE_ROLE, LIST_CRUDSAMPLE_ROLE, CREATE_CRUDSAMPLE_ROLE, UPDATE_CRUDSAMPLE_ROLE, DELETE_CRUDSAMPLE_ROLE),

    //ULTRA CUSTOM ROLE
    MY_CUSTOM_FUCKING_POWER_ROLE;

    @Getter
    private Set<Role> roleDependencies = new HashSet<>();

    Role(Role... roles) {
        if (roles != null && roles.length > 0)
            for (Role role : roles) {
                roleDependencies.addAll(recursiveRolesResolver(role, null));
            }
    }

    private Set<Role> recursiveRolesResolver(Role baseRole, Set<Role> uniqueRoles) {
        if (uniqueRoles == null)
            uniqueRoles = new HashSet<>();

        uniqueRoles.add(baseRole);

        if (!baseRole.roleDependencies.isEmpty())
            for (Role currentRole : baseRole.roleDependencies) {
                if (!currentRole.roleDependencies.isEmpty())
                    recursiveRolesResolver(currentRole, uniqueRoles);
                else
                    uniqueRoles.add(currentRole);

            }

        return uniqueRoles;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}
