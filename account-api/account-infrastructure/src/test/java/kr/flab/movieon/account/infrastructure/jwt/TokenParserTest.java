package kr.flab.movieon.account.infrastructure.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenParser;
import kr.flab.movieon.common.error.InvalidTokenException;
import org.javaunit.autoparams.AutoSource;
import org.javaunit.autoparams.customization.Customization;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

@DisplayName("토큰 파서")
final class TokenParserTest {

    private static final String ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9VU0V"
        + "SIl0sImVtYWlsIjoibXNvbG8wMjEwMTVAZ21haWwuY29tIiwiaXNzIjoibW92aWVPbiIsImlhdCI6MTY0NjU2"
        + "MzU4OSwiZXhwIjoxNjQ2OTIzNTg5fQ._xfSkgxVs2Qvamjq-VF5t5T7B5ALgdj-zVPxx692GY0";
    private static final String REFRESH_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzY29wZXMiOlsiUk9MRV9SRU"
        + "ZSRVNIX1RPS0VOIl0sImVtYWlsIjoibXNvbG8wMjEwMTVAZ21haWwuY29tIiwiaXNzIjoibW92aWVPbiIsImp"
        + "0aSI6IjYxOTE0YzA5LTIxN2ItNDFiNC1hMDYwLTZmMDA1NDVjNjBlNyIsImlhdCI6MTY0NjU2MzU4OSwiZXhw"
        + "IjoxNjQ3MjgzNTg5fQ.gKGMEGiaSBmUxWjos62pIUKelh68OrchqozCFntIHyc";

    @ParameterizedTest
    @AutoSource
    @Customization(TokenPropertiesCustomizer.class)
    @DisplayName("정상적인 Access Token을 파싱한 경우, Jti가 null이고 "
        + "isRefreshable이 False여야 한다.")
    void sut_parse_access_token_jti_is_null(TokenProperties tokenProperties) {
        // Arrange
        var sut = new JwtTokenParser(tokenProperties);

        // Act
        RawToken token = sut.parse(ACCESS_TOKEN);

        // Assert
        assertThat(token.getSubject()).isNotNull();
        assertThat(token.getAuthorities()).isNotNull();
        assertThat(token.getJti()).isNull();
        assertThat(token.isRefreshable(Scopes.REFRESH_TOKEN.authority())).isFalse();
    }

    @ParameterizedTest
    @AutoSource
    @Customization(TokenPropertiesCustomizer.class)
    @DisplayName("정상적인 Refresh Token을 파싱한 경우, Jti가 not-null이고"
        + "isRefreshable이 True여야 한다.")
    void sut_parse_refresh_token_jti_is_not_null(TokenProperties tokenProperties) {
        // Arrange
        var sut = new JwtTokenParser(tokenProperties);

        // Act
        RawToken token = sut.parse(REFRESH_TOKEN);

        // Assert
        assertThat(token.getSubject()).isNotNull();
        assertThat(token.getAuthorities()).isNotNull();
        assertThat(token.getJti()).isNotNull();
        assertThat(token.isRefreshable(Scopes.REFRESH_TOKEN.authority())).isTrue();
    }

    @ParameterizedTest
    @AutoSource
    @Customization(TokenPropertiesCustomizer.class)
    @DisplayName("올바르지 않은 Token을 파싱한 경우, 예외가 발생한다.")
    void sut_parse_invalid_token_throw_exception(TokenProperties tokenProperties) {
        // Arrange
        var sut = new JwtTokenParser(tokenProperties);

        // Act & Assert
        assertThatExceptionOfType(InvalidTokenException.class)
            .isThrownBy(() -> sut.parse("Invalid token"));
    }
}
