package com.khaled.logins.model;

import java.util.List;
import java.util.stream.Stream;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static com.khaled.logins.model.Role.*;

/**
 *
 * @author khaled
 */
public enum Group {
    USER(EDIT_PROFILE, VIEW_PROFILE),
    ADMIN(Role.values());

    private final List<Role> roles;

    private Group(Role... roles) {
        this.roles = unmodifiableList(asList(roles));
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public List<Group> getByRole(Role role) {
        return Stream.of(values())
                .filter(group -> group.getRoles().contains(role))
                .collect(toList());
    }
}
