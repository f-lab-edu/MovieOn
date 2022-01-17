package kr.flab.movieon.common;

import java.util.Set;

public interface AuthenticatedUser {

    Set<Role> getRoles();

    Long getId();
}

