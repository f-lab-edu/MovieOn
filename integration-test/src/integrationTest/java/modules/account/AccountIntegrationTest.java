package modules.account;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.flab.movieon.account.application.command.RegisterAccountCommand;
import kr.flab.movieon.notification.domain.NotificationTemplate;
import kr.flab.movieon.notification.domain.NotificationTemplateRepository;
import modules.IntegrateTestExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

final class AccountIntegrationTest extends IntegrateTestExtension {

    @Autowired
    private NotificationTemplateRepository templateRepository;

    @Nested
    @DisplayName("회원가입 시나리오 테스트")
    class RegisterApiTest {

        private static final String REGISTER_URI = "/api/v1/auth/register";

        @BeforeEach
        void setUp() {
            templateRepository.save(NotificationTemplate.create("EMAIL",
                "계정 확인 메일", "MovieOn 회원가입 인증 메일입니다.", REGISTER_EMAIL_TEMPLATE));
        }

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

    private static final String REGISTER_EMAIL_TEMPLATE = "<!DOCTYPE html>\n"
        + "<html lang=\"en\" xmlns:th=\"http://www.thymeleaf.org\">\n"
        + "<head>\n"
        + "  <meta charset=\"UTF-8\">\n"
        + "  <title>MovieOn 회원가입 인증 메일</title>\n"
        + "</head>\n"
        + "<body>\n"
        + "<div>\n"
        + "  <p>안녕하세요. <span th:text=\"${username}\"></span>님</p>\n"
        + "\n"
        + "  <h2 th:text=\"${message}\">메시지</h2>\n"
        + "\n"
        + "  <div>\n"
        + "    <a th:href=\"${host + link}\" th:text=\"${linkName}\">Link</a>\n"
        + "    <p>링크가 동작하지 않는 경우에는 아래 URL을 웹브라우저에 복사해서 붙여 넣으세요.</p>\n"
        + "    <small th:text=\"${host + link}\"></small>\n"
        + "  </div>\n"
        + "</div>\n"
        + "<footer>\n"
        + "  <small>MovieOn&copy; 2021</small>\n"
        + "</footer>\n"
        + "</body>\n"
        + "</html>";
}
