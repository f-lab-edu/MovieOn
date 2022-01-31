package kr.flab.movieon.security.domain;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import kr.flab.movieon.integrate.security.jwt.JwtTokenExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationServiceException;

final class TokenExtractorTests {

    @Test
    @DisplayName("공백인 payload 에서 토큰을 추출하려고 하면 인증 예외가 발생한다.")
    void name() {
        // Arrange
        var sut = new JwtTokenExtractor();

        // Act & Assert
        assertThatExceptionOfType(AuthenticationServiceException.class)
            .isThrownBy(() -> sut.extract(""));
    }

    @Test
    @DisplayName("null 에서 토큰을 추출하려고 하면 인증 예외가 발생한다.")
    void name1() {
        // Arrange
        var sut = new JwtTokenExtractor();

        // Act & Assert
        assertThatExceptionOfType(AuthenticationServiceException.class)
            .isThrownBy(() -> sut.extract(null));
    }

    @Test
    @DisplayName("유효하지 않은 문자열에서 토큰을 추출하려고 하면 인증 예외가 발생한다.")
    void name2() {
        // Arrange
        var sut = new JwtTokenExtractor();

        // Act & Assert
        assertThatExceptionOfType(AuthenticationServiceException.class)
            .isThrownBy(() -> sut.extract("BeBeBe DERFTGYHUJIKOL"));
    }
}
