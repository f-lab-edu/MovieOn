package kr.flab.movieon.account.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.exception.RegisterAccountConflictException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class RegisterAccountProcessorImplTest {

    @TestFactory
    @DisplayName("회원가입 시나리오")
    Collection<DynamicTest> register_scenarios() {
        var accountRepository = new FakeAccountRepository();
        var processor = new RegisterAccountProcessorImpl(accountRepository,
            new BCryptPasswordEncoder());

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
                accountRepository.save(Account.of("jiwon3388", existEmail, "!password"));

                // Act & Assert
                assertThatExceptionOfType(RegisterAccountConflictException.class)
                    .isThrownBy(
                        () -> processor.register("won3399", existEmail, "!password"));

            }), dynamicTest("중복된 유저 아이디인 경우 예외를 반환한다.", () -> {
                var existUsername = "duplicated_user";
                accountRepository.save(Account.of(existUsername, "jiwon@naver.com", "!password"));

                // Act & Assert
                assertThatExceptionOfType(RegisterAccountConflictException.class)
                    .isThrownBy(
                        () -> processor.register(existUsername, "adbo@gmail.com", "!password"));
            })

        );

    }

}