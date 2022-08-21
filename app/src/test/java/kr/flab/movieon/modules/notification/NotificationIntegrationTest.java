package kr.flab.movieon.modules.notification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.flab.movieon.modules.IntegrateTestExtension;
import kr.flab.movieon.notification.presentation.request.UpdateTemplateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public final class NotificationIntegrationTest extends IntegrateTestExtension {

    @Test
    @DisplayName("관리자가 알림 템플릿의 내용을 수정한다.")
    void name() throws Exception {
        // Arrange
        UpdateTemplateRequest request = request();

        // Act
        var result = mockMvc.perform(put("/api/v1/notifications/templates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .accept(MediaType.APPLICATION_JSON)
            .header(AUTHORIZATION, BEARER + tokens.accessToken())
        );

        // Assert
        result.andDo(print())
            .andExpect(status().isOk());
    }

    private UpdateTemplateRequest request() {
        return new UpdateTemplateRequest("EMAIL", "계정 확인 메일", "MovieOn 회원가입 인증 메일입니다.", """
            <!DOCTYPE html>
            <html lang="en" xmlns:th="http://www.thymeleaf.org">
            <head>
              <meta charset="UTF-8">
              <title>MovieOn 회원가입 인증 메일입니다.</title>
            </head>
            <body>
            <div>
              <p>안녕하세요. <span th:text="${username}"></span>님</p>

              <h2 th:text="${message}">메시지</h2>

              <div>
                <a th:href="${host + link}" th:text="${linkName}">Link</a>
                <p>링크가 동작하지 않는 경우에는 아래 URL을 웹브라우저에 복사해서 붙여 넣으세요.</p>
                <small th:text="${host + link}"></small>
              </div>
            </div>
            <footer>
              <small>MovieOn&copy; 2021</small>
            </footer>
            </body>
            </html>""");
    }
}
