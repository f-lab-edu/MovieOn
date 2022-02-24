package kr.flab.movieon.product.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import kr.flab.movieon.product.application.RegisterItemCommand;
import kr.flab.movieon.product.application.RegisterProductCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
final class RegisterProductApiTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @InjectMocks
    private RegisterProductApi registerProductApi;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registerProductApi)
            .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("상품 등록 시 입력 값이 Null인 경우 예외가 발생한다.")
    void name() throws Exception {
        // Arrange
        var command = toNullCommand();

        // Act
        var actions = mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(command))
        );

        // Assert
        actions.andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("상품 등록 시 입력 값이 유효하지 경우 예외가 발생한다.")
    void name1() throws Exception {
        // Arrange
        var command = toInvalidCommand();

        // Act
        var actions = mockMvc.perform(post("/api/v1/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(command))
        );

        // Assert
        actions
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    private RegisterProductCommand toInvalidCommand() {
        var itemCommand = new RegisterItemCommand();
        itemCommand.setBasePrice(0);
        itemCommand.setAvailableDays(0);
        itemCommand.setType("RENTAL");
        var command = new RegisterProductCommand();
        command.setName("보이스");
        command.setDescription("보이스 설명");
        command.setCategoryId(1L);
        command.setYear(1969);
        command.setMonth(12);
        command.setDays(2);
        command.setRunningTime(14);
        command.setActors("장혁");
        command.setDirectors("김땡땡");
        command.setRateDescription("GENERAL");
        command.setImages(Collections.emptyList());
        command.setThumbnails("섬네일.png");
        command.setItem(itemCommand);
        return command;
    }

    private RegisterProductCommand toNullCommand() {
        var command = new RegisterProductCommand();
        command.setName(null);
        command.setDescription(null);
        command.setCategoryId(null);
        command.setYear(null);
        command.setMonth(null);
        command.setDays(null);
        command.setRunningTime(null);
        command.setActors(null);
        command.setDirectors(null);
        command.setRateDescription(null);
        command.setImages(null);
        command.setThumbnails(null);
        command.setItem(null);
        return command;
    }
}
