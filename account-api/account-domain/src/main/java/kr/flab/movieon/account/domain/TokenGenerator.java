package kr.flab.movieon.account.domain;

import java.util.Set;
import kr.flab.movieon.common.Role;

public interface TokenGenerator {

    Tokens generate(String email, Set<Role> roles);

}
