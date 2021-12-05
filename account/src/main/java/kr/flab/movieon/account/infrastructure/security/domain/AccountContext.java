package kr.flab.movieon.account.infrastructure.security.domain;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import kr.flab.movieon.account.domain.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class AccountContext extends User {

    private final Account account;

    public AccountContext(Account account) {
        super(account.getUsername(), account.getPassword(), authorities(account));
        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Account account) {
        return account.getRoles().stream()
            .map(roleType -> new SimpleGrantedAuthority(roleType.getName().name()))
            .collect(Collectors.toList());
    }

    public String getEmail() {
        return account.getEmail();
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
