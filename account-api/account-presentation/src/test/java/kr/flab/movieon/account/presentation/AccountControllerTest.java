package kr.flab.movieon.account.presentation;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import kr.flab.movieon.account.application.AccountFacade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @MockBean
    private AccountFacade accountFacade;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("인증된 사용자가 정보 요청에 성공한다.")
    void findInfo() throws Exception {
        var action = mockMvc.perform(
            get("/api/me")
                .with(user("user").roles("USER"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        action.andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("인증되지 않은 사용자가 정보를 요청하면 401 코드를 응답한다.")
    void unAuthenticate_findInfo() throws Exception {
        var action = mockMvc.perform(
            get("/api/me")
                .with(anonymous())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
        );

        action.andDo(print())
            .andExpect(status().isUnauthorized());
    }
}
