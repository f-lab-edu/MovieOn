package kr.flab.movieon.account.infrastructure.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import kr.flab.movieon.account.domain.Account;
import kr.flab.movieon.account.domain.Tokens;
import kr.flab.movieon.account.infrastructure.jwt.fixtures.JwtCustomization;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenGenerator;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenParser;
import kr.flab.movieon.common.error.InvalidTokenException;
import org.javaunit.autoparams.AutoSource;
import org.javaunit.autoparams.customization.Customization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

@DisplayName("토큰 파서")
final class TokenParserTest {

    @ParameterizedTest
    @AutoSource
    @Customization(JwtCustomization.class)
    @DisplayName("정상적인 Access Token을 파싱한 경우, Jti가 null이고 "
        + "token을 verify할 경우 예외가 발생한다.")
    void sut_parse_access_token_jti_is_null(RefreshTokenInfoRepository refreshTokenInfoRepository,
        TokenProperties tokenProperties) {
        // Arrange
        var accessToken = getTokens(refreshTokenInfoRepository, tokenProperties)
            .getAccessToken();
        var sut = new JwtTokenParser(tokenProperties);

        // Act
        RawToken token = sut.parse(accessToken);

        // Assert
        assertThat(token.getSubject()).isNotNull();
        assertThat(token.getAuthorities()).isNotNull();
        assertThat(token.getJti()).isNull();
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> token.verify(Scopes.REFRESH_TOKEN.authority()));
    }

    private Tokens getTokens(RefreshTokenInfoRepository refreshTokenInfoRepository,
        TokenProperties tokenProperties) {
        var generator = new JwtTokenGenerator(refreshTokenInfoRepository, tokenProperties);
        return generator.generate(Account
            .register("msolo021015@gmail.com", "pass", "rebwon"));
    }

    @ParameterizedTest
    @AutoSource
    @Customization(JwtCustomization.class)
    @DisplayName("정상적인 Refresh Token을 파싱한 경우, Jti가 not-null이고"
        + "token을 verify했을 때 예외가 발생하지 않는다.")
    void sut_parse_refresh_token_jti_is_not_null(
        RefreshTokenInfoRepository refreshTokenInfoRepository, TokenProperties tokenProperties) {
        // Arrange
        var refreshToken = getTokens(refreshTokenInfoRepository, tokenProperties)
            .getRefreshToken();
        var sut = new JwtTokenParser(tokenProperties);

        // Act
        RawToken token = sut.parse(refreshToken);

        // Assert
        assertThat(token.getSubject()).isNotNull();
        assertThat(token.getAuthorities()).isNotNull();
        assertThat(token.getJti()).isNotNull();
        assertThatNoException()
            .isThrownBy(() -> token.verify(Scopes.REFRESH_TOKEN.authority()));
    }

    @ParameterizedTest
    @AutoSource
    @Customization(JwtCustomization.class)
    @DisplayName("올바르지 않은 Token을 파싱한 경우, 예외가 발생한다.")
    void sut_parse_invalid_token_throw_exception(TokenProperties tokenProperties) {
        // Arrange
        var sut = new JwtTokenParser(tokenProperties);

        // Act & Assert
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> sut.parse("Invalid token"));
    }
}
