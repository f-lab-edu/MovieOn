package kr.flab.movieon.notification.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.flab.movieon.notification.presentation.request.CreateTemplateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
final class NotificationTemplateApiTests {

    @InjectMocks
    private NotificationTemplateApi templateApi;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(templateApi)
            .build();
    }

    @Nested
    @DisplayName("템플릿 추가 API")
    final class CreateTemplateApiTest {

        private static final String CREATE_URI = "/api/v1/notifications/templates";

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("템플릿을 추가하는 요청에서 필수 값이 비어있거나 NULL인 경우 400 에러가 발생합니다.")
        void name(String arg) throws Exception {
            // Arrange
            var request = new CreateTemplateRequest(arg, arg, arg, arg);

            // Act
            final var actions = mockMvc.perform(post(CREATE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            );

            // Assert
            actions.andExpect(status().isBadRequest());
        }
    }
}
