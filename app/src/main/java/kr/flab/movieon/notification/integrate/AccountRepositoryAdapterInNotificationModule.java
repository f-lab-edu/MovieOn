package kr.flab.movieon.notification.integrate;

import org.springframework.stereotype.Component;

@Component
public final class AccountRepositoryAdapterInNotificationModule implements AccountRepository {

    private final kr.flab.movieon.account.domain.AccountRepository accountRepository;

    public AccountRepositoryAdapterInNotificationModule(
        kr.flab.movieon.account.domain.AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findById(String accountId) {
        var account = accountRepository.findById(accountId);
        return new Account(account.getEmail(), account.getUsername());
    }
}
