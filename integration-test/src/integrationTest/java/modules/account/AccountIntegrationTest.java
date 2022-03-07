package modules.account;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.flab.movieon.account.application.command.RegisterAccountCommand;
import modules.IntegrateTestExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

final class AccountIntegrationTest extends IntegrateTestExtension {

    @Nested
    @DisplayName("회원가입 시나리오 테스트")
    class RegisterApiTest {

        private static final String REGISTER_URI = "/api/v1/auth/register";

        @Test
        @DisplayName("회원가입에 적절한 파라미터를 입력하여, 회원가입이 성공한다.")
        void account_register_scenario_test() throws Exception {
            var command = new RegisterAccountCommand();
            command.setUsername("rebwon");
            command.setEmail("msolo021015@gmail.com");
            command.setPassword("12345678!");

            final var actions = mockMvc.perform(post(REGISTER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command))
            );

            actions
                .andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("회원 정보 조회 API")
    class FindInfoApiTest {

        private static final String FIND_INFO_API = "/api/v1/accounts/me";

        @Test
        @DisplayName("인증되지 않은 사용자가 정보를 요청하면 401 코드를 응답한다.")
        void unAuthenticate_findInfo() throws Exception {
            var action = mockMvc.perform(
                get(FIND_INFO_API)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            action.andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.body").exists());
        }
    }
}
