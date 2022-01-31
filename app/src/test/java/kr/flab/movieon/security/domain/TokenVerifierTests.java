package kr.flab.movieon.security.domain;

import static org.assertj.core.api.Assertions.assertThat;

import kr.flab.movieon.integrate.security.jwt.JwtTokenVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

final class TokenVerifierTests {

    private static final String INVALID_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
        + ".eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ"
        + ".-VehURC5AsJrkeIUxxUzr5_kSHnyuEqn63XjTGqVCIk";

    @Test
    @DisplayName("토큰의 정보가 유효하지 않은 경우 False를 리턴한다.")
    void name() {
        // Arrange
        var sut = new JwtTokenVerifier(new FakeRefreshTokenInfoRepository());

        // Act
        var result = sut.verify(INVALID_TOKEN);

        // Assert
        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("검증에 필요한 토큰 값이 null이거나 Empty 경우 False를 리턴한다.")
    void name1(String jti) {
        // Arrange
        var sut = new JwtTokenVerifier(new FakeRefreshTokenInfoRepository());

        // Act
        var result = sut.verify(jti);

        // Assert
        assertThat(result).isFalse();
    }
}
