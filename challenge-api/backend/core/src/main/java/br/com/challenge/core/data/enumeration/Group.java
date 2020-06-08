package br.com.challenge.core.data.enumeration;

import static br.com.challenge.core.data.enumeration.Role.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Getter;

@Getter
public enum Group {
    ROOT(null),
    ADMIN(ROOT, FULL_ACCESS_ROLE),

    EDITOR(ROOT, CREATE_CRUDSAMPLE_ROLE, UPDATE_CRUDSAMPLE_ROLE),
    READER(ROOT, VIEW_CRUDSAMPLE_ROLE, FULL_ACCESS_ROLE),
    MY_CUSTOM_PROFILE(ROOT, MY_CUSTOM_FUCKING_POWER_ROLE);

    @Getter
    private Group majorGroup;

    @Getter
    protected final Set<Role> roles;

    Group(Group majorGroup, Role... roles) {
        this.majorGroup = majorGroup;
        this.roles = new HashSet<>(Arrays.asList(roles));
        this.roles.addAll(Arrays.stream(roles).flatMap(role -> role.getRoleDependencies().stream()).collect(Collectors.toSet()));
    }

    @JsonCreator
    public static Group create(String value) {
        return Arrays.stream(values())
                .filter(tg -> tg.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean is(Group group) {
        return this.equals(group);
    }
}
