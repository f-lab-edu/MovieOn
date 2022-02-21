package kr.flab.movieon.security.integrate.domain;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.common.AuthenticatedUser;
import kr.flab.movieon.common.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public final class AccountContext extends User implements AuthenticatedUser {

    private final Account account;

    public AccountContext(Account account) {
        super(account.getUserId(), account.getPassword(), authorities(account));
        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Account account) {
        return account.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .collect(Collectors.toList());
    }

    @Override
    public Set<Role> getRoles() {
        return account.getRoles();
    }

    @Override
    public Long getId() {
        return account.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountContext)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        AccountContext that = (AccountContext) o;
        return Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), account);
    }
}
