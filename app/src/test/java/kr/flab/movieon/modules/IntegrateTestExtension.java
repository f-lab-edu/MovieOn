package kr.flab.movieon.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.flab.movieon.MovieOnApplication;
import kr.flab.movieon.account.domain.AccountRepository;
import kr.flab.movieon.account.domain.TokenGenerator;
import kr.flab.movieon.account.domain.Tokens;
import kr.flab.movieon.payment.infrastructure.toss.TossPaymentsPaymentApprovalCommandVerifier;
import kr.flab.movieon.payment.infrastructure.toss.TossPaymentsPaymentApprovalProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = MovieOnApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrateTestExtension {

    protected static final String AUTHORIZATION = "Authorization";
    protected static final String BEARER = "Bearer ";

    @Autowired protected MockMvc mockMvc;
    @Autowired protected ObjectMapper objectMapper;
    @Autowired private TokenGenerator tokenGenerator;
    @Autowired private AccountRepository accountRepository;

    @MockBean protected TossPaymentsPaymentApprovalCommandVerifier verifier;
    @MockBean protected TossPaymentsPaymentApprovalProcessor approvalProcessor;

    protected Tokens tokens;

    @BeforeEach
    void setUp() {
        var account = accountRepository.findByEmail("jiwon@gmail.com");
        tokens = tokenGenerator.generate(account.getEmail(), account.getRoles());
    }
}
