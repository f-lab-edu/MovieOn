package kr.flab.movieon.security.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.security.SecurityAppProperties;
import kr.flab.movieon.security.exception.InvalidTokenException;
import kr.flab.movieon.security.jwt.JwtTokenConverter;
import kr.flab.movieon.security.jwt.JwtTokenExtractor;
import kr.flab.movieon.security.jwt.JwtTokenGenerator;
import kr.flab.movieon.security.jwt.JwtTokenVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.security.authentication.AuthenticationServiceException;

final class TokenConvertTest {

    private final SecurityAppProperties securityAppProperties = new SecurityAppProperties(1800,
        "movieon-test", "c2VjcmV0S2V5LXRlc3QtYXV0aG9yaXphdGlvbi1qd3QtbWFuYWdlLXRva2Vu", 604800);

    @TestFactory
    @DisplayName("JWT 토큰 발행 및 변환 테스트")
    Collection<DynamicTest> token_publish_and_convert_scenarios() {
        var authRepository = new FakeAuthEntityRepository();
        var tokenGenerator = new JwtTokenGenerator(authRepository, securityAppProperties);
        var tokenVerifier = new JwtTokenVerifier(authRepository);
        var tokenExtractor = new JwtTokenExtractor();
        var accountRepository = new StubAccountRepository();

        var tokenConverter = new JwtTokenConverter(accountRepository, authRepository,
            securityAppProperties, tokenGenerator, tokenVerifier, tokenExtractor);

        var account = accountRepository.getAccountStub();
        var invalidToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
            + ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ"
            + ".-VehURC5AsJrkeIUxxUzr5_kSHnyuEqn63XjTGqVCIk";

        return Arrays.asList(
            dynamicTest("공백인 payload 에서 토큰을 추출하려고 하면 인증 예외가 발생한다.", () -> {
                assertThatExceptionOfType(AuthenticationServiceException.class).isThrownBy(
                    () -> tokenExtractor.extract(""));

            }), dynamicTest("null 에서 토큰을 추출하려고 하면 인증 예외가 발생한다.", () -> {
                assertThatExceptionOfType(AuthenticationServiceException.class).isThrownBy(
                    () -> tokenExtractor.extract(null));

            }), dynamicTest("유효하지 않은 문자열에서 토큰을 추출하려고 하면 예외가 발생한다.", () -> {
                assertThatExceptionOfType(AuthenticationServiceException.class).isThrownBy(
                    () -> tokenExtractor.extract("BeBeBe DERFTGYHUJIKOL"));

            }), dynamicTest("JTI 값이 null 이라면 검증에 실패한다.", () -> {
                var isVerified = tokenVerifier.verify(null);

                assertThat(isVerified).isFalse();

            }), dynamicTest("JTI 값이 공백 이라면 검증에 실패한다.", () -> {
                var isVerified = tokenVerifier.verify("");

                assertThat(isVerified).isFalse();

            }), dynamicTest("유효하지 않은 토큰을 변환하면 예외를 반환한다.", () -> {
                var isVerified = tokenVerifier.verify(invalidToken);

                assertThat(isVerified).isFalse();

            }), dynamicTest("토큰 값이 null 이라면 변환에 인증 예외가 발생한다.", () -> {
                assertThatExceptionOfType(AuthenticationServiceException.class).isThrownBy(
                    () -> tokenConverter.convert(null));

            }), dynamicTest("유효하지 않은 토큰을 변환하면 예외를 발생한다.", () -> {
                assertThatExceptionOfType(InvalidTokenException.class).isThrownBy(
                    () -> tokenConverter.convert(invalidToken));

            }));
    }

    private final class FakeAuthEntityRepository implements AuthEntityRepository {

        private final Map<Long, AuthEntity> data = new ConcurrentHashMap<>();
        private final AtomicLong idGenerator = new AtomicLong();

        @Override
        public boolean existsByRefreshTokenJti(String refreshTokenJti) {
            return data.values().stream()
                .anyMatch(entity -> entity.getRefreshTokenJti().equals(refreshTokenJti));
        }

        @Override
        public Optional<AuthEntity> findByRefreshTokenJti(String refreshTokenJti) {
            return data.values().stream()
                .filter(entity -> entity.getRefreshTokenJti().equals(refreshTokenJti)).findAny();
        }

        @Override
        public AuthEntity save(AuthEntity entity) {
            return data.put(idGenerator.incrementAndGet(), entity);
        }

        @Override
        public void delete(AuthEntity entity) {
            data.remove(entity.getId());
        }
    }

    private final class StubAccountRepository implements AccountRepository {

        private final Account account = Account.of("userid", "email@naver.com", "password!");

        @Override
        public Account save(Account account) {
            return account;
        }

        @Override
        public Optional<Account> findByUserId(String userId) {
            return Optional.of(account);
        }

        @Override
        public Optional<Account> findByEmail(String email) {
            return Optional.of(account);
        }

        @Override
        public boolean existsByUserId(String userId) {
            return account.getUserId().equals(userId);
        }

        @Override
        public boolean existsByEmail(String email) {
            return account.getEmail().equals(email);
        }

        public Account getAccountStub() {
            return account;
        }
    }
}
