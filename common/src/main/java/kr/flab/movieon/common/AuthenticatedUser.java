package kr.flab.movieon.common;

import java.util.Set;

public record AuthenticatedUser(String id, Set<Role> roles) {}

