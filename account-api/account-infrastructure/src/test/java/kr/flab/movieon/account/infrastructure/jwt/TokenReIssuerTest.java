package kr.flab.movieon.account.infrastructure.jwt;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.Tokens;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenGenerator;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenParser;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenReIssuer;
import kr.flab.movieon.common.error.InvalidArgumentException;
import kr.flab.movieon.common.error.InvalidTokenException;
import org.javaunit.autoparams.AutoSource;
import org.javaunit.autoparams.customization.Customization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

@DisplayName("토큰 재발급기")
final class TokenReIssuerTest {

    private static final String PREFIX = "Bearer ";
    private static final String ACCESS_TOKEN = PREFIX
        + "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0VSIl0sImVtYWlsIjoibXNvbG8wMjEwMTVAZ21h"
        + "aWwuY29tIiwiaXNzIjoibW92aWVPbiIsImlhdCI6MTY0NjU2MzU4OSwiZXhwIjoxNjQ2OTIzNTg5fQ._xfSkg"
        + "xVs2Qvamjq-VF5t5T7B5ALgdj-zVPxx692GY0";
    private static final String REFRESH_TOKEN = PREFIX
        + "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9SRUZSRVNIX1RPS0VOIl0sImVtYWlsIjoibXNvbG8w"
        + "MjEwMTVAZ21haWwuY29tIiwiaXNzIjoibW92aWVPbiIsImp0aSI6IjYxOTE0YzA5LTIxN2ItNDFiNC1hMDYwL"
        + "TZmMDA1NDVjNjBlNyIsImlhdCI6MTY0NjU2MzU4OSwiZXhwIjoxNjQ3MjgzNTg5fQ.gKGMEGiaSBmUxWjos62"
        + "pIUKelh68OrchqozCFntIHyc";

    @ParameterizedTest
    @AutoSource
    @Customization(JwtCustomization.class)
    @DisplayName("토큰 재발급시, payload가 null 혹은 empty인 경우 예외가 발생한다.")
    void sut_token_refreshing_but_payload_is_null_throw_exception(
        TokenProperties tokenProperties, RefreshTokenInfoRepository tokenInfoRepository,
        TokenExtractor tokenExtractor) {
        // Arrange
        var sut = new JwtTokenReIssuer(new JwtTokenGenerator(tokenInfoRepository, tokenProperties),
            tokenExtractor, new JwtTokenParser(tokenProperties), tokenInfoRepository,
            new DummyAccountRepository());

        // Act & Assert
        assertThatExceptionOfType(InvalidArgumentException.class)
            .isThrownBy(() -> sut.reIssuance(""));
    }

    @ParameterizedTest
    @AutoSource
    @Customization(JwtCustomization.class)
    @DisplayName("토큰 재발급시, 토큰이 Refresh 타입이 아닌 경우 예외가 발생한다.")
    void sut_token_refreshing_token_is_not_refresh_type_throw_exception(
        TokenProperties tokenProperties, RefreshTokenInfoRepository tokenInfoRepository,
        TokenExtractor tokenExtractor) {
        // Arrange
        var sut = new JwtTokenReIssuer(new JwtTokenGenerator(tokenInfoRepository, tokenProperties),
            tokenExtractor, new JwtTokenParser(tokenProperties), tokenInfoRepository,
            new DummyAccountRepository());

        // Act & Assert
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> sut.reIssuance(ACCESS_TOKEN));
    }

    @ParameterizedTest
    @AutoSource
    @Customization(JwtCustomization.class)
    @DisplayName("토큰 재발급시, Refresh Token의 Jti 값이 다른 경우 예외가 발생한다.")
    void sut_token_refreshing_but_refresh_token_is_not_equals_jti_throw_exception(
        TokenProperties tokenProperties, RefreshTokenInfoRepository tokenInfoRepository,
        TokenExtractor tokenExtractor, String jti) {
        // Arrange
        tokenInfoRepository.save(new RefreshTokenInfo(jti));
        var sut = new JwtTokenReIssuer(new JwtTokenGenerator(tokenInfoRepository, tokenProperties),
            tokenExtractor, new JwtTokenParser(tokenProperties), tokenInfoRepository,
            new DummyAccountRepository());

        // Act & Assert
        assertThatExceptionOfType(RefreshTokenNotFoundException.class)
            .isThrownBy(() -> sut.reIssuance(REFRESH_TOKEN));
    }

    @ParameterizedTest
    @AutoSource
    @Customization(JwtCustomization.class)
    @DisplayName("토큰 재발급시, Refresh Token이 이미 만료된 경우 예외가 발생한다.")
    void sut_token_refreshing_but_refresh_token_is_expired_throw_exception(
        TokenProperties tokenProperties, RefreshTokenInfoRepository tokenInfoRepository,
        TokenExtractor tokenExtractor) {
        // Arrange
        var tokenGenerator = new JwtTokenGenerator(tokenInfoRepository, tokenProperties);
        var tokens = setUpTokens(tokenGenerator);
        var tokenParser = new JwtTokenParser(tokenProperties);
        setUpExpiredRefreshTokenInfo(tokenInfoRepository, tokens, tokenParser);

        var sut = new JwtTokenReIssuer(tokenGenerator,
            tokenExtractor, tokenParser, tokenInfoRepository,
            new DummyAccountRepository());

        // Act& Assert
        assertThatExceptionOfType(TokenExpiredException.class)
            .isThrownBy(() -> sut.reIssuance(PREFIX + tokens.getRefreshToken()));
    }

    private Tokens setUpTokens(JwtTokenGenerator tokenGenerator) {
        return tokenGenerator.generate(
            Account.register("msolo021015@gmail.com", "pass", "rebwon"));
    }

    private void setUpExpiredRefreshTokenInfo(RefreshTokenInfoRepository tokenInfoRepository,
        Tokens tokens, JwtTokenParser tokenParser) {
        var rawToken = tokenParser.parse(tokens.getRefreshToken());
        var refreshTokenInfo = tokenInfoRepository.findByRefreshTokenJti(
            rawToken.getJti());
        refreshTokenInfo.expire();
    }
}
