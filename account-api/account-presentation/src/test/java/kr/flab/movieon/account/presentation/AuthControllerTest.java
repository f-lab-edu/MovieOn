package kr.flab.movieon.account.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.flab.movieon.account.application.AccountFacade;
import kr.flab.movieon.account.application.payload.RegisterAccountCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(value = AuthController.class)
final class AuthControllerTest {

    @MockBean
    private AccountFacade accountFacade;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class DefaultConfigWithoutCsrf extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            super.configure(http);
            http.csrf().disable();
        }

        public void configure(WebSecurity web) throws Exception {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
            web.ignoring().antMatchers("/api/auth/**");
        }
    }

    @Nested
    @DisplayName("Refresh 토큰 변환 API ")
    class RefreshTokenApiTest {

        private static final String REFRESH_TOKEN_URL = "/api/auth/refresh";

        @Test
        @DisplayName("refresh 요청 시 Authentication Header 가 비었다면 400 에러를 응답한다.")
        void refresh_http_header_is_null_and_empty() throws Exception {
            final var actions = mockMvc.perform(get(REFRESH_TOKEN_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
            );

            actions
                .andExpect(status().isBadRequest());
        }

    }

    @Nested
    @DisplayName("회원가입 이메일 검증 API")
    class RegisterConfirmApiTest {

        private static final String CONFIRM_URL = "/api/auth/confirm";

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("HTTP 입력이 비었거나 null 이라면 400 에러를 응답한다.")
        void http_parameter_is_null_and_empty(String arg) throws Exception {
            final var actions = mockMvc.perform(get(CONFIRM_URL)
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
            final var actions = mockMvc.perform(get(CONFIRM_URL)
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

        private static final String SIGNUP_URL = "/api/auth/signup";

        @ParameterizedTest
        @NullAndEmptySource
        @DisplayName("HTTP 입력이 비었거나 null 이라면 400 에러를 응답한다.")
        void http_parameter_is_null_and_empty(String arg) throws Exception {
            var command = new RegisterAccountCommand();
            command.setUserId(arg);
            command.setEmail(arg);
            command.setPassword(arg);

            final var actions = mockMvc.perform(post(SIGNUP_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command))
            );

            actions
                .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @ValueSource(strings = {"@@@@@@@@@", "@naver.com", "jiwon"})
        @DisplayName("요청 Body의 이메일 형식이 맞지 않는 경우 400 에러를 반환한다.")
        void http_parameter_is_invalid_email(String arg) throws Exception {
            var command = new RegisterAccountCommand();
            command.setUserId("username");
            command.setEmail(arg);
            command.setPassword("password1!");

            final var actions = mockMvc.perform(post(SIGNUP_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command))
            );

            actions
                .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @ValueSource(strings = {"1", "a", "1a234567890123456"})
        @DisplayName("요청 Body의 비밀번호 형식이 맞지 않은 경우 400 에러를 반환한다.")
        void http_paramete_password_is_invalid(String args) throws Exception {
            var command = new RegisterAccountCommand();
            command.setUserId("username");
            command.setEmail("jiwon@naver.com");
            command.setPassword(args);

            final var actions = mockMvc.perform(post(SIGNUP_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command))
            );

            actions
                .andExpect(status().isBadRequest());
        }

    }

}

