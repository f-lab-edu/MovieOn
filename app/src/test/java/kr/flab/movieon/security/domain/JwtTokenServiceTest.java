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
import kr.flab.movieon.integrate.security.SecurityAppProperties;
import kr.flab.movieon.integrate.security.domain.RefreshTokenInfo;
import kr.flab.movieon.integrate.security.domain.RefreshTokenInfoRepository;
import kr.flab.movieon.integrate.security.exception.InvalidTokenException;
import kr.flab.movieon.integrate.security.jwt.JwtTokenConverter;
import kr.flab.movieon.integrate.security.jwt.JwtTokenExtractor;
import kr.flab.movieon.integrate.security.jwt.JwtTokenGenerator;
import kr.flab.movieon.integrate.security.jwt.JwtTokenVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.security.authentication.AuthenticationServiceException;

final class JwtTokenServiceTest {

    private final String invalidToken = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
        + ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ"
        + ".-VehURC5AsJrkeIUxxUzr5_kSHnyuEqn63XjTGqVCIk";
    private final SecurityAppProperties securityAppProperties = new SecurityAppProperties(
        1800,
        "movieon-test",
        "c2VjcmV0S2V5LXRlc3QtYXV0aG9yaXphdGlvbi1qd3QtbWFuYWdlLXRva2Vu",
        604800);

    @TestFactory
    @DisplayName("JWT 토큰 발행 및 변환 테스트")
    Collection<DynamicTest> token_converter_test() {
        var authRepository = new FakeRefreshTokenInfoRepository();

        var tokenConverter = new JwtTokenConverter(
            new StubAccountRepository(),
            authRepository,
            securityAppProperties,
            new JwtTokenGenerator(authRepository, securityAppProperties),
            new JwtTokenVerifier(authRepository),
            new JwtTokenExtractor());

        return Arrays.asList(
            dynamicTest("토큰 값이 null 이라면 변환에 인증 예외가 발생한다.", () ->
                assertThatExceptionOfType(AuthenticationServiceException.class).isThrownBy(
                    () -> tokenConverter.convert(null))

            ), dynamicTest("유효하지 않은 토큰을 변환하면 예외를 발생한다.", () ->
                assertThatExceptionOfType(InvalidTokenException.class).isThrownBy(
                    () -> tokenConverter.convert(invalidToken))
            ));
    }

    @TestFactory
    @DisplayName("JWT Refresh 토큰 검증 테스트")
    Collection<DynamicTest> token_verifier_test() {
        var tokenVerifier = new JwtTokenVerifier(new FakeRefreshTokenInfoRepository());

        return Arrays.asList(
            dynamicTest("유효하지 않은 토큰은 검증에 실패한다.", () -> {
                var isVerified = tokenVerifier.verify(invalidToken);

                assertThat(isVerified).isFalse();

            }), dynamicTest("JTI 값이 null 이라면 검증에 실패한다.", () -> {
                var isVerified = tokenVerifier.verify(null);

                assertThat(isVerified).isFalse();

            }), dynamicTest("JTI 값이 공백 이라면 검증에 실패한다.", () -> {
                var isVerified = tokenVerifier.verify("");

                assertThat(isVerified).isFalse();
            }));
    }

    @TestFactory
    @DisplayName("JWT 토큰 추출 테스트")
    Collection<DynamicTest> token_extractor_test() {
        var tokenExtractor = new JwtTokenExtractor();

        return Arrays.asList(
            dynamicTest("공백인 payload 에서 토큰을 추출하려고 하면 인증 예외가 발생한다.", () ->
                assertThatExceptionOfType(AuthenticationServiceException.class).isThrownBy(
                    () -> tokenExtractor.extract(""))

            ), dynamicTest("null 에서 토큰을 추출하려고 하면 인증 예외가 발생한다.", () ->
                assertThatExceptionOfType(AuthenticationServiceException.class).isThrownBy(
                    () -> tokenExtractor.extract(null))

            ), dynamicTest("유효하지 않은 문자열에서 토큰을 추출하려고 하면 인증 예외가 발생한다.", () ->
                assertThatExceptionOfType(AuthenticationServiceException.class).isThrownBy(
                    () -> tokenExtractor.extract("BeBeBe DERFTGYHUJIKOL"))

            ));
    }

    private final class FakeRefreshTokenInfoRepository implements RefreshTokenInfoRepository {

        private final Map<Long, RefreshTokenInfo> data = new ConcurrentHashMap<>();
        private final AtomicLong idGenerator = new AtomicLong();

        @Override
        public boolean existsByRefreshTokenJti(String refreshTokenJti) {
            return data.values().stream()
                .anyMatch(entity -> entity.getRefreshTokenJti().equals(refreshTokenJti));
        }

        @Override
        public Optional<RefreshTokenInfo> findByRefreshTokenJti(String refreshTokenJti) {
            return data.values().stream()
                .filter(entity -> entity.getRefreshTokenJti().equals(refreshTokenJti)).findAny();
        }

        @Override
        public RefreshTokenInfo save(RefreshTokenInfo entity) {
            return data.put(idGenerator.incrementAndGet(), entity);
        }

        @Override
        public void delete(RefreshTokenInfo entity) {
            data.remove(entity.getId());
        }
    }

    private final class StubAccountRepository implements AccountRepository {

        private final Account account = Account.create("userid", "email@naver.com", "password!");

        @Override
        public Account save(Account account) {
            return account;
        }

        @Override
        public Optional<Account> findById(Long accountId) {
            return Optional.of(account);
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
