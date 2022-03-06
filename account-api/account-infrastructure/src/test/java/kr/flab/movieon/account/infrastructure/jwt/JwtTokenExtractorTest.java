package kr.flab.movieon.account.infrastructure.jwt;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import kr.flab.movieon.account.infrastructure.jwt.impl.JwtTokenExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("토큰 추출기")
class JwtTokenExtractorTest {

    @Test
    @DisplayName("토큰 추출 시 빈 값을 넘긴 경우, 빈 Optional을 반환한다.")
    void name() {
        // Arrange
        var sut = new JwtTokenExtractor();

        // Act
        var result = sut.extract("");

        // Assert
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("토큰 추출 시 헤더 Prefix가 잘못된 경우, 빈 Optional을 반환한다.")
    void name12() {
        // Arrange
        var sut = new JwtTokenExtractor();

        // Act
        var result = sut.extract("ASDAS");

        // Assert
        assertThat(result.isEmpty()).isTrue();
    }
}
