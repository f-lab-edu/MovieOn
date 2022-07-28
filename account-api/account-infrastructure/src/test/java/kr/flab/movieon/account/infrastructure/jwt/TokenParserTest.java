package kr.flab.movieon.account.infrastructure.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

import java.util.EnumSet;
import kr.flab.movieon.account.domain.Tokens;
import kr.flab.movieon.account.infrastructure.jwt.fixtures.FakeRefreshTokenInfoRepository;
import kr.flab.movieon.account.infrastructure.jwt.fixtures.Fixtures;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenGenerator;
import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenParser;
import kr.flab.movieon.common.Role;
import kr.flab.movieon.common.error.InvalidTokenException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("토큰 파서")
final class TokenParserTest {

    @Test
    @DisplayName("정상적인 Access Token을 파싱한 경우, Jti가 null이고 "
        + "token을 verify할 경우 예외가 발생한다.")
    void sut_parse_access_token_jti_is_null() {
        // Arrange
        var tokenProperties = Fixtures.tokenProperties();
        var accessToken = getTokens(new FakeRefreshTokenInfoRepository(), tokenProperties)
            .accessToken();
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
        return generator.generate("msolo021015@gmail.com", EnumSet.of(Role.USER));
    }

    @Test
    @DisplayName("정상적인 Refresh Token을 파싱한 경우, Jti가 not-null이고"
        + "token을 verify했을 때 예외가 발생하지 않는다.")
    void sut_parse_refresh_token_jti_is_not_null() {
        // Arrange
        var tokenProperties = Fixtures.tokenProperties();
        var refreshToken = getTokens(new FakeRefreshTokenInfoRepository(), tokenProperties)
            .refreshToken();
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

    @Test
    @DisplayName("올바르지 않은 Token을 파싱한 경우, 예외가 발생한다.")
    void sut_parse_invalid_token_throw_exception() {
        // Arrange
        var sut = new JwtTokenParser(Fixtures.tokenProperties());

        // Act & Assert
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> sut.parse("Invalid token"));
    }
}
