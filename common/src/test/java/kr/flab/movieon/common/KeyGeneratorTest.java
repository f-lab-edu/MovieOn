package kr.flab.movieon.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

class KeyGeneratorTest {

    @RepeatedTest(value = 100)
    @DisplayName("IdGenerator로 생성되는 문자열 ID의 길이는 28자릿수까지로 제한한다.")
    void sut_id_generator_length_is_limited_28() {
        // Act
        var orderId = KeyGenerator.generate("ord_");

        // Assert
        assertEquals(orderId.length(), 28);
    }
}
