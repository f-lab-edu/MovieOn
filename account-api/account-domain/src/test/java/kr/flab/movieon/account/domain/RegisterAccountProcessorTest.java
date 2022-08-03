package kr.flab.movieon.account.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kr.flab.movieon.account.domain.exception.DuplicatedEmailException;
import kr.flab.movieon.account.domain.exception.DuplicatedUsernameException;
import kr.flab.movieon.common.error.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원가입 처리기")
class RegisterAccountProcessorTest {

    @Test
    @DisplayName("회원 등록을 완료한 경우 이벤트가 등록된다.")
    void name122() {
        // Arrange
        var accountRepository = new FakeAccountRepository();
        var sut = new RegisterAccountProcessor(accountRepository,
            new FakePasswordEncrypter());

        // Act
        var account = sut.register("msolo021015@gmail.com", "12345678!", "rebwon");

        // Assert
        assertThat(account.isEmailVerified()).isFalse();
        assertThat(account.pollAllEvents().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("회원 등록 확인 절차를 완료한 경우 이벤트가 등록된다.")
    void ddd11() {
        // Arrange
        var accountRepository = new FakeAccountRepository();
        var account = setUpAccount(accountRepository);
        var sut = new RegisterAccountProcessor(accountRepository,
            new FakePasswordEncrypter());

        // Act
        var confirmAccount = sut.registerConfirm(account.getEmailCheckToken(), account.getEmail());

        // Assert
        assertThat(confirmAccount.isEmailVerified()).isTrue();
        assertThat(confirmAccount.pollAllEvents().size()).isEqualTo(1);
    }

    private Account setUpAccount(FakeAccountRepository accountRepository) {
        var account = Account.register("msolo021015@gmail.com", "12345678!", "rebwon");
        accountRepository.save(account);
        account.pollAllEvents();
        return account;
    }

    @Test
    @DisplayName("회원가입 시 이메일이 중복되었을 경우 예외가 발생한다.")
    void name() {
        // Arrange
        var accountRepository = new FakeAccountRepository();
        accountRepository.save(Account.register("msolo021015@gmail.com", "12345678!", "rebwon"));
        var sut = new RegisterAccountProcessor(accountRepository,
            new FakePasswordEncrypter());

        // Act & Assert
        assertThatExceptionOfType(DuplicatedEmailException.class)
            .isThrownBy(() -> sut.register("msolo021015@gmail.com", "12345678!", "rebwon"));
    }

    @Test
    @DisplayName("회원가입 시 유저명이 중복되었을 경우 예외가 발생한다.")
    void name22() {
        // Arrange
        var accountRepository = new FakeAccountRepository();
        accountRepository.save(Account.register("msolo021015@gmail.com", "12345678!", "rebwon"));
        var sut = new RegisterAccountProcessor(accountRepository,
            new FakePasswordEncrypter());

        // Act & Assert
        assertThatExceptionOfType(DuplicatedUsernameException.class)
            .isThrownBy(() -> sut.register("msolo021015@naver.com", "12345678!", "rebwon"));
    }

    @Test
    @DisplayName("회원가입 확인 시 이메일 토큰이 변조되었을 경우 에외가 발생한다.")
    void name1() {
        // Arrange
        var accountRepository = new FakeAccountRepository();
        accountRepository.save(Account.register("msolo021015@gmail.com", "12345678!", "rebwon"));
        var sut = new RegisterAccountProcessor(accountRepository,
            new FakePasswordEncrypter());

        // Act & Assert
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> sut.registerConfirm("invalid", "msolo021015@gmail.com"));
    }
}
