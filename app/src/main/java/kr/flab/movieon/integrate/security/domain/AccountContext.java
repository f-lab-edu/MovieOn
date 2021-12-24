package kr.flab.movieon.integrate.security.domain;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.common.AccountAuthentication;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public final class AccountContext extends User implements AccountAuthentication {

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

    public String getEmail() {
        return account.getEmail();
    }

    public String getUserId() {
        return account.getUserId();
    }

    @Override
    public Set<String> getRoles() {
        return account.getRoles().stream()
            .map(Enum::name)
            .collect(Collectors.toSet());
    }

    @Override
    public Long getAccountId() {
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
