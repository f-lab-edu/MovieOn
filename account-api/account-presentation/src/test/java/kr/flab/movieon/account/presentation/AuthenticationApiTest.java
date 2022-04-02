package kr.flab.movieon.account.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.flab.movieon.account.presentation.request.LoginAccountRequest;
import kr.flab.movieon.account.presentation.request.RegisterAccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@DisplayName("Authentication API")
@ExtendWith(MockitoExtension.class)
class AuthenticationApiTest {

    @InjectMocks
    private AuthenticationApi commandApi;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(commandApi).build();
    }

    @Nested
    @DisplayName("토큰 재발급 API")
    class TokenReIssuerApiTest {

        private static final String RE_ISSUANCE_URI = "/api/v1/auth/reIssuance";

        @Test
        @DisplayName("토큰 재발급 요청 시 Authentication Header 가 비었다면 400 에러를 응답한다.")
        void refresh_http_header_is_null_and_empty() throws Exception {
            final var actions = mockMvc.perform(post(RE_ISSUANCE_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
            );

            actions
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("로그인 API")
    class LoginApiTest {

        private static final String LOGIN_URI = "/api/v1/auth/login";

        @ParameterizedTest
        @ValueSource(strings = {"@@@@@@@@@", "@naver.com", "jiwon"})
        @DisplayName("요청 Body의 이메일 형식이 맞지 않는 경우 400 에러를 반환한다.")
        void http_parameter_is_invalid_email(String arg) throws Exception {
            var request = new LoginAccountRequest();
            request.setEmail(arg);
            request.setPassword("password1!");

            final var actions = mockMvc.perform(post(LOGIN_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            );

            actions
                .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @ValueSource(strings = {"1", "a", "1a234567890123456"})
        @DisplayName("요청 Body의 비밀번호 형식이 맞지 않은 경우 400 에러를 반환한다.")
        void http_parameter_password_is_invalid(String args) throws Exception {
            var request = new LoginAccountRequest();
            request.setEmail("jiwon@naver.com");
            request.setPassword(args);

            final var actions = mockMvc.perform(post(LOGIN_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            );

            actions
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("회원가입 이메일 검증 API")
    class RegisterConfirmApiTest {

        private static final String CONFIRM_URI = "/api/v1/auth/confirm";

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("HTTP 입력이 비었거나 null 이라면 400 에러를 응답한다.")
        void http_parameter_is_null_and_empty(String arg) throws Exception {
            final var actions = mockMvc.perform(get(CONFIRM_URI)
                .accept(MediaType.ALL_VALUE)
                .contentType(MediaType.ALL_VALUE)
                .param("token", arg)
                .param("email", arg)
            );

            actions
                .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @ValueSource(strings = {"@@@@@@@@@", "@naver.com", "jiwon"})
        @DisplayName("요청 Body의 이메일 형식이 맞지 않는 경우 400 에러를 반환한다.")
        void http_parameter_is_invalid_email(String arg) throws Exception {
            final var actions = mockMvc.perform(get(CONFIRM_URI)
                .accept(MediaType.ALL_VALUE)
                .contentType(MediaType.ALL_VALUE)
                .param("token", arg)
                .param("email", arg)
            );

            actions
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("회원가입 HTTP API")
    class RegisterApiTest {

        private static final String REGISTER_URI = "/api/v1/auth/register";

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("HTTP 입력이 비었거나 null 이라면 400 에러를 응답한다.")
        void http_parameter_is_null_and_empty(String arg) throws Exception {
            var request = new RegisterAccountRequest();
            request.setUsername(arg);
            request.setEmail(arg);
            request.setPassword(arg);

            final var actions = mockMvc.perform(post(REGISTER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            );

            actions
                .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @ValueSource(strings = {"@@@@@@@@@", "@naver.com", "jiwon"})
        @DisplayName("요청 Body의 이메일 형식이 맞지 않는 경우 400 에러를 반환한다.")
        void http_parameter_is_invalid_email(String arg) throws Exception {
            var request = new RegisterAccountRequest();
            request.setUsername("username");
            request.setEmail(arg);
            request.setPassword("password1!");

            final var actions = mockMvc.perform(post(REGISTER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            );

            actions
                .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @ValueSource(strings = {"1", "a", "1a234567890123456"})
        @DisplayName("요청 Body의 비밀번호 형식이 맞지 않은 경우 400 에러를 반환한다.")
        void http_parameter_password_is_invalid(String args) throws Exception {
            var request = new RegisterAccountRequest();
            request.setUsername("username");
            request.setEmail("jiwon@naver.com");
            request.setPassword(args);

            final var actions = mockMvc.perform(post(REGISTER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
            );

            actions
                .andExpect(status().isBadRequest());
        }
    }
}
