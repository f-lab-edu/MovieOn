package kr.flab.movieon.order.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import kr.flab.movieon.order.application.OrderCommandService;
import kr.flab.movieon.order.presentation.CreateOrderApi.CreateOrderProductRequest;
import kr.flab.movieon.order.presentation.CreateOrderApi.CreateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@DisplayName("주문 생성 HTTP API")
@ExtendWith(MockitoExtension.class)
final class CreateOrderApiTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private OrderCommandService commandService;
    @InjectMocks
    CreateOrderApi orderApi;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(orderApi)
            .build();
    }

    @Test
    @DisplayName("주문 생성 요청 시 요청 파라미터가 null인 경우 400 에러를 리턴한다.")
    void name() throws Exception {
        // Arrange
        var request = new CreateOrderRequest(null, null, null);

        // Act
        final var actions = mockMvc.perform(post("/api/order")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("주문 생성 요청 시 요청 파라미터가 최소 범위를 만족하지 못한 경우 400 에러를 리턴한다.")
    void ss() throws Exception {
        // Arrange
        var request = new CreateOrderRequest("CARD", -1L, Collections.emptyList());

        // Act
        final var actions = mockMvc.perform(post("/api/order")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("주문 생성 요청 시 주문 상품 항목의 최소 금액을 만족하지 못한 경우 400 에러를 리턴한다.")
    void aaaa() throws Exception {
        // Arrange
        var request = new CreateOrderRequest("CARD", 12L,
            List.of(new CreateOrderProductRequest(1L, "보이스", -1L)));

        // Act
        final var actions = mockMvc.perform(post("/api/order")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("주문 생성 요청 시 주문 상품 항목이 null인 경우 400 에러를 리턴한다.")
    void aaaa11() throws Exception {
        // Arrange
        var request = new CreateOrderRequest("CARD", 12L,
            List.of(new CreateOrderProductRequest(null, null, null)));

        // Act
        final var actions = mockMvc.perform(post("/api/order")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions
            .andDo(print())
            .andExpect(status().isBadRequest());
    }
}