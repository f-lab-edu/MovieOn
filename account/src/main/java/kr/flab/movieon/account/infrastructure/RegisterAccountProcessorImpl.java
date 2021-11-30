package kr.flab.movieon.account.infrastructure;

import static java.lang.Boolean.TRUE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.RegisterAccountProcessor;
import kr.flab.movieon.account.domain.Role;
import kr.flab.movieon.account.domain.RoleRepository;
import kr.flab.movieon.account.domain.RoleType;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class RegisterAccountProcessorImpl implements RegisterAccountProcessor {

    private final AccountRepository accountRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public RegisterAccountProcessorImpl(
        AccountRepository accountRepository,
        RoleRepository roleRepository,
        PasswordEncoder encoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public void register(String username, String password, String email,
        List<String> roles) {
        if (TRUE.equals(accountRepository.existsByUsername(username))) {
            throw new IllegalArgumentException("Error: Username is already in use");
        }

        if (TRUE.equals(accountRepository.existsByEmail(email))) {
            throw new IllegalArgumentException("Error: Email is already in use");
        }

        Set<Role> newRoles = new HashSet<>();

        roles.forEach(role -> {
            if (role.equalsIgnoreCase(RoleType.ADMIN.name())) {
                newRoles.add(findByRoleType(RoleType.ADMIN));
            } else {
                newRoles.add(findByRoleType(RoleType.USER));
            }
        });

        var account = Account.of(username, email, encoder.encode(password));
        account.changeRoles(newRoles);
        accountRepository.save(account);
    }

    private Role findByRoleType(RoleType name) {
        return roleRepository.findByName(name)
            .orElseThrow(() -> new IllegalArgumentException("Error: Role is not found. : " + name));
    }
}
