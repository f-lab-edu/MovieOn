package testcase;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.flab.movieon.MovieOnApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(classes = MovieOnApplication.class)
@AutoConfigureMockMvc
final class AccountIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("회원 정보 조회 API")
    class FindInfoApiTest {

        private static final String FIND_INFO_API = "/api/accounts/me";

        @Test
        @DisplayName("인증되지 않은 사용자가 정보를 요청하면 401 코드를 응답한다.")
        void unAuthenticate_findInfo() throws Exception {
            var action = mockMvc.perform(
                get(FIND_INFO_API)
                    .with(anonymous())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            );

            action.andDo(print())
                .andExpect(status().isUnauthorized());
        }

    }
}
