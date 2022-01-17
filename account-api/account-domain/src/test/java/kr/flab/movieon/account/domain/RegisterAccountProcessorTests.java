package kr.flab.movieon.account.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import kr.flab.movieon.account.domain.exception.RegisterAccountConflictException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

final class RegisterAccountProcessorTests {

    @TestFactory
    @DisplayName("회원가입 시나리오")
    Collection<DynamicTest> register_scenarios() {
        var accountRepository = new FakeAccountRepository();
        var processor = new RegisterAccountProcessor(accountRepository,
            new FakePasswordEncrypter());

        return Arrays.asList(
            dynamicTest("회원가입에 성공한다.", () -> {
                var username = "jiwon2237";

                // Act & Assert
                processor.register(username, "jiwon@naver.com", "!password");
                var account = accountRepository.findByUserId(username)
                    .orElseThrow(() -> fail("등록된 Account 가 없습니다. 회원가입에 실패하였습니다."));
                assertThat(account.getUserId()).isEqualTo(username);

            }), dynamicTest("중복된 이메일인 경우 예외를 반환한다.", () -> {
                var existEmail = "duplicated@naver.com";
                accountRepository.save(Account.create("jiwon3388", existEmail, "!password"));

                // Act & Assert
                assertThatExceptionOfType(RegisterAccountConflictException.class)
                    .isThrownBy(
                        () -> processor.register("won3399", existEmail, "!password"));

            }), dynamicTest("중복된 유저 아이디인 경우 예외를 반환한다.", () -> {
                var existUsername = "duplicated_user";
                accountRepository.save(
                    Account.create(existUsername, "jiwon@naver.com", "!password"));

                // Act & Assert
                assertThatExceptionOfType(RegisterAccountConflictException.class)
                    .isThrownBy(
                        () -> processor.register(existUsername, "adbo@gmail.com", "!password"));
            })

        );
    }

    private static final class FakePasswordEncrypter implements PasswordEncrypter {

        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return rawPassword.toString().equals(encodedPassword);
        }
    }
}
