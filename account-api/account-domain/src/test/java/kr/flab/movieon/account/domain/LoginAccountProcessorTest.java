package kr.flab.movieon.account.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kr.flab.movieon.account.domain.exception.InvalidAccountException;
import kr.flab.movieon.account.domain.exception.PasswordNotMatchedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("로그인 처리기")
class LoginAccountProcessorTest {

    @Test
    @DisplayName("로그인 시 패스워드가 일치하지 않는 경우 예외가 발생한다.")
    void name() {
        // Arrange
        var accountRepository = new FakeAccountRepository();
        accountRepository.save(Account.register("msolo021015@gmail.com", "12345678!", "rebwon"));
        var sut = new LoginAccountProcessor(accountRepository,
            new FakePasswordEncrypter());

        // Act & Assert
        assertThatExceptionOfType(PasswordNotMatchedException.class)
            .isThrownBy(() -> sut.login("msolo021015@gmail.com", "wrongpassword"));
    }

    @Test
    @DisplayName("로그인 시 이메일 인증이 되지 않았을 경우 예외가 발생한다.")
    void name22() {
        // Arrange
        var accountRepository = new FakeAccountRepository();
        accountRepository.save(Account.register("msolo021015@gmail.com", "12345678!", "rebwon"));
        var sut = new LoginAccountProcessor(accountRepository,
            new FakePasswordEncrypter());

        // Act & Assert
        assertThatExceptionOfType(InvalidAccountException.class)
            .isThrownBy(() -> sut.login("msolo021015@gmail.com", "12345678!"));
    }
}
