package kr.flab.movieon.security.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Optional;
import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.integrate.security.SecurityAppProperties;
import kr.flab.movieon.integrate.security.exception.InvalidTokenException;
import kr.flab.movieon.integrate.security.jwt.JwtTokenConverter;
import kr.flab.movieon.integrate.security.jwt.JwtTokenExtractor;
import kr.flab.movieon.integrate.security.jwt.JwtTokenGenerator;
import kr.flab.movieon.integrate.security.jwt.JwtTokenVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.security.authentication.AuthenticationServiceException;

final class TokenConverterTests {

    private static final String INVALID_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
        + ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ"
        + ".-VehURC5AsJrkeIUxxUzr5_kSHnyuEqn63XjTGqVCIk";

    private static final SecurityAppProperties APP_PROPERTIES = new SecurityAppProperties(
        1800,
        "movieon-test",
        "c2VjcmV0S2V5LXRlc3QtYXV0aG9yaXphdGlvbi1qd3QtbWFuYWdlLXRva2Vu",
        604800);

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("토큰 변환을 위한 Payload 값이 null이거나 empty인 경우 예외가 발생합니다.")
    void name1(String payload) {
        // Arrange
        var refreshTokenRepository = new FakeRefreshTokenInfoRepository();
        var sut = new JwtTokenConverter(
            new StubAccountRepository(), refreshTokenRepository, APP_PROPERTIES,
            new JwtTokenGenerator(refreshTokenRepository, APP_PROPERTIES),
            new JwtTokenVerifier(refreshTokenRepository),
            new JwtTokenExtractor());

        // Act & Assert
        assertThatExceptionOfType(AuthenticationServiceException.class)
            .isThrownBy(() -> sut.convert(payload));
    }

    @Test
    @DisplayName("토큰 변환을 위한 Payload 값이 잘못되었을 경우 예외가 발생합니다.")
    void name() {
        // Arrange
        var refreshTokenRepository = new FakeRefreshTokenInfoRepository();
        var sut = new JwtTokenConverter(
            new StubAccountRepository(), refreshTokenRepository, APP_PROPERTIES,
            new JwtTokenGenerator(refreshTokenRepository, APP_PROPERTIES),
            new JwtTokenVerifier(refreshTokenRepository),
            new JwtTokenExtractor());

        // Act & Assert
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> sut.convert(INVALID_TOKEN));
    }

    private static final class StubAccountRepository implements AccountRepository {

        private static final Account account =
            Account.create("userid", "email@naver.com", "password!");

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
    }
}
