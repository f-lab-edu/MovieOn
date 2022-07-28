package kr.flab.movieon.account.infrastructure.jwt;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.EnumSet;
import kr.flab.movieon.account.domain.Tokens;
import kr.flab.movieon.account.infrastructure.jwt.fixtures.DummyAccountRepository;
import kr.flab.movieon.account.infrastructure.jwt.fixtures.FakeRefreshTokenInfoRepository;
import kr.flab.movieon.account.infrastructure.jwt.fixtures.Fixtures;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenExtractor;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenGenerator;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenParser;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenReIssuer;
import kr.flab.movieon.common.Role;
import kr.flab.movieon.common.error.InvalidArgumentException;
import kr.flab.movieon.common.error.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("토큰 재발급기")
final class TokenReIssuerTest {

    private static final String PREFIX = "Bearer ";

    @Test
    @DisplayName("토큰 재발급시, payload가 null 혹은 empty인 경우 예외가 발생한다.")
    void sut_token_refreshing_but_payload_is_null_throw_exception() {
        // Arrange
        var tokenInfoRepository = new FakeRefreshTokenInfoRepository();
        var tokenProperties = Fixtures.tokenProperties();
        var tokenExtractor = new JwtTokenExtractor();
        var sut = new JwtTokenReIssuer(new JwtTokenGenerator(tokenInfoRepository, tokenProperties),
            tokenExtractor, new JwtTokenParser(tokenProperties), tokenInfoRepository,
            new DummyAccountRepository());

        // Act & Assert
        assertThatExceptionOfType(InvalidArgumentException.class)
            .isThrownBy(() -> sut.reIssuance(""));
    }

    @Test
    @DisplayName("토큰 재발급시, 토큰이 Refresh 타입이 아닌 경우 예외가 발생한다.")
    void sut_token_refreshing_token_is_not_refresh_type_throw_exception() {
        // Arrange
        var tokenInfoRepository = new FakeRefreshTokenInfoRepository();
        var tokenProperties = Fixtures.tokenProperties();
        var tokenExtractor = new JwtTokenExtractor();
        var tokenGenerator = new JwtTokenGenerator(tokenInfoRepository, tokenProperties);
        var tokens = setUpTokens(tokenGenerator);
        var sut = new JwtTokenReIssuer(tokenGenerator, tokenExtractor,
            new JwtTokenParser(tokenProperties), tokenInfoRepository,
            new DummyAccountRepository());

        // Act & Assert
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> sut.reIssuance(PREFIX + tokens.accessToken()));
    }

    @Test
    @DisplayName("토큰 재발급시, Refresh Token의 Jti 값이 다른 경우 예외가 발생한다.")
    void sut_token_refreshing_but_refresh_token_is_not_equals_jti_throw_exception() {
        // Arrange
        var tokenProperties = Fixtures.tokenProperties();
        var tokenExtractor = new JwtTokenExtractor();
        var tokenInfoRepository = new DummyRefreshTokenInfoRepository();
        var tokenGenerator = new JwtTokenGenerator(tokenInfoRepository, tokenProperties);
        var tokens = setUpTokens(tokenGenerator);
        var sut = new JwtTokenReIssuer(tokenGenerator, tokenExtractor,
            new JwtTokenParser(tokenProperties), tokenInfoRepository,
            new DummyAccountRepository());

        // Act & Assert
        assertThatExceptionOfType(RefreshTokenNotFoundException.class)
            .isThrownBy(() -> sut.reIssuance(PREFIX + tokens.refreshToken()));
    }

    @Test
    @DisplayName("토큰 재발급시, Refresh Token이 이미 만료된 경우 예외가 발생한다.")
    void sut_token_refreshing_but_refresh_token_is_expired_throw_exception() {
        // Arrange
        var tokenInfoRepository = new FakeRefreshTokenInfoRepository();
        var tokenProperties = Fixtures.tokenProperties();
        var tokenExtractor = new JwtTokenExtractor();
        var tokenGenerator = new JwtTokenGenerator(tokenInfoRepository, tokenProperties);
        var tokens = setUpTokens(tokenGenerator);
        var tokenParser = new JwtTokenParser(tokenProperties);
        setUpExpiredRefreshTokenInfo(tokenInfoRepository, tokens, tokenParser);

        var sut = new JwtTokenReIssuer(tokenGenerator,
            tokenExtractor, tokenParser, tokenInfoRepository,
            new DummyAccountRepository());

        // Act& Assert
        assertThatExceptionOfType(AlreadyTokenExpiredException.class)
            .isThrownBy(() -> sut.reIssuance(PREFIX + tokens.refreshToken()));
    }

    private Tokens setUpTokens(JwtTokenGenerator tokenGenerator) {
        return tokenGenerator.generate("msolo021015@gmail.com", EnumSet.of(Role.USER));
    }

    private void setUpExpiredRefreshTokenInfo(RefreshTokenInfoRepository tokenInfoRepository,
        Tokens tokens, JwtTokenParser tokenParser) {
        var rawToken = tokenParser.parse(tokens.refreshToken());
        var refreshTokenInfo = tokenInfoRepository.findByRefreshTokenJti(
            rawToken.getJti());
        refreshTokenInfo.expire();
    }

    private static final class DummyRefreshTokenInfoRepository
        implements RefreshTokenInfoRepository {

        @Override
        public RefreshTokenInfo save(RefreshTokenInfo entity) {
            return null;
        }

        @Override
        public boolean existsByRefreshTokenJti(String jti) {
            return false;
        }

        @Override
        public RefreshTokenInfo findByRefreshTokenJti(String jti) {
            return null;
        }
    }
}
