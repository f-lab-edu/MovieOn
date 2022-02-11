package kr.flab.movieon.order.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import kr.flab.movieon.order.application.request.CreateOrderProductRequest;
import kr.flab.movieon.order.application.request.CreateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@DisplayName("주문 생성 HTTP API")
@ExtendWith(MockitoExtension.class)
final class CreateOrderApiTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @InjectMocks
    private CreateOrderApi orderApi;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(orderApi).build();
    }

    @Test
    @DisplayName("주문 생성 요청 시 요청 파라미터가 null인 경우 400 에러를 리턴한다.")
    void param_is_null_return_http_status_400() throws Exception {
        // Arrange
        var request = new CreateOrderRequest();
        request.setProducts(null);
        request.setPayMethod(null);
        request.setUseOfPoint(null);

        // Act
        final var actions = mockMvc.perform(
            post("/api/order").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Assert
        actions.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("주문 생성 요청 시 요청 파라미터가 최소 범위를 만족하지 못한 경우 400 에러를 리턴한다.")
    void param_is_not_satisfied_minimum_range_return_http_status_400() throws Exception {
        // Arrange
        var request = new CreateOrderRequest();
        request.setProducts(Collections.emptyList());
        request.setPayMethod("CARD");
        request.setUseOfPoint(-1L);

        // Act
        final var actions = mockMvc.perform(
            post("/api/order").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Assert
        actions.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("주문 생성 요청 시 주문 상품 항목의 최소 금액을 만족하지 못한 경우 400 에러를 리턴한다.")
    void if_minimum_amount_orderProduct_is_not_satisfied_return_http_status_400() throws Exception {
        // Arrange
        var productRequest = new CreateOrderProductRequest();
        productRequest.setProductId(1L);
        productRequest.setProductName("보이스");
        productRequest.setPrice(-1L);
        var request = new CreateOrderRequest();
        request.setPayMethod("CARD");
        request.setUseOfPoint(12L);
        request.setProducts(List.of(productRequest));

        // Act
        final var actions = mockMvc.perform(
            post("/api/order").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Assert
        actions.andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("주문 생성 요청 시 주문 상품 항목이 null인 경우 400 에러를 리턴한다.")
    void if_the_order_product_item_is_null_return_http_status_400() throws Exception {
        // Arrange
        var productRequest = new CreateOrderProductRequest();
        productRequest.setProductId(null);
        productRequest.setProductName(null);
        productRequest.setPrice(null);
        var request = new CreateOrderRequest();
        request.setPayMethod("CARD");
        request.setUseOfPoint(12L);
        request.setProducts(List.of(productRequest));

        // Act
        final var actions = mockMvc.perform(
            post("/api/order").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Assert
        actions.andDo(print()).andExpect(status().isBadRequest());
    }
}
