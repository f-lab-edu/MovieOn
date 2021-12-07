package kr.flab.movieon.account.domain;

import java.util.Arrays;

public enum Role {
    USER,
    PRIME_USER,
    ADMIN;

    public static Role findByRole(String role) {
        return Arrays.stream(Role.values())
            .filter(r -> r.name().equals(role))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
