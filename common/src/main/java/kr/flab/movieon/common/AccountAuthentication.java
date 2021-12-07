package kr.flab.movieon.common;

import java.util.Set;

public interface AccountAuthentication {

    Set<String> getRoles();

    Long getAccountId();
}

