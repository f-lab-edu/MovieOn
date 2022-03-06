package kr.flab.movieon.account.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kr.flab.movieon.common.error.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회원가입 처리기")
class RegisterAccountProcessorTest {

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
