package modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.flab.movieon.MovieOnApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,
    classes = MovieOnApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class IntegrateTestExtension {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}
