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
    public Account findById(Long accountId) {
        var account = accountRepository.findById(accountId)
            .orElseThrow(IllegalArgumentException::new);
        return new Account(account.getEmail(), account.getUserId());
    }
}
