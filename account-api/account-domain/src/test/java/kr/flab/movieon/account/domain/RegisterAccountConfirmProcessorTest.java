package kr.flab.movieon.account.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import kr.flab.movieon.account.domain.exception.AccountNotFoundException;
import kr.flab.movieon.account.domain.exception.InvalidEmailTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

@DisplayName("회원가입 이메일 검증 도메인 서비스")
final class RegisterAccountConfirmProcessorTest {

    @TestFactory
    @DisplayName("회원가입 이메일 검증 시나리오")
    Collection<DynamicTest> confirm_register_scenarios() {
        var accountRepository = new FakeAccountRepository();
        var processor = new RegisterAccountConfirmProcessor(accountRepository);

        var account = Account.of("jiwon", "jiwon@gmail.com", "password!");
        accountRepository.save(account);

        return Arrays.asList(
            dynamicTest("회원 이메일 검증에 성공한다.", () -> {
                // Act
                processor.confirm(account.getEmailValidationToken(), account.getEmail());
                var verifiedAccount = accountRepository.findByUserId(account.getUserId())
                    .orElseThrow(() -> fail("Verified account can not found"));

                // Assert
                assertThat(verifiedAccount.isEmailVerified()).isTrue();

            }), dynamicTest("이메일 값이 null 인 경우 예외를 반환한다.", () -> {
                // Act & Assert
                assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> processor.confirm(account.getEmailValidationToken(),
                        null));

            }), dynamicTest("해당 이메일을 가진 Account 가 존재하지 않는 경우 예외를 반환한다.", () -> {
                // Act & Assert
                assertThatExceptionOfType(AccountNotFoundException.class)
                    .isThrownBy(() -> processor.confirm(account.getEmailValidationToken(),
                        "non-existent-email@naver.com"));

            }), dynamicTest("토큰 값이 null 인 경우 검증 실패 예외를 반환한다.", () -> {
                // Act & Assert
                assertThatExceptionOfType(InvalidEmailTokenException.class)
                    .isThrownBy(() -> processor.confirm(null, account.getEmail()));

            }), dynamicTest("유효하지 않은 토큰인 경우 검증 실패 예외를 반환한다.", () -> {
                // Act & Assert
                assertThatExceptionOfType(InvalidEmailTokenException.class)
                    .isThrownBy(() -> processor.confirm("Hi I'm Token", account.getEmail()));

            })

        );

    }
}
