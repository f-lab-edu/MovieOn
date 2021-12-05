package kr.flab.movieon.account.infrastructure.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;

import java.util.List;
import kr.flab.movieon.account.infrastructure.security.auth.JwtAuthProcessingFilter;
import kr.flab.movieon.account.infrastructure.security.auth.JwtAuthProvider;
import kr.flab.movieon.account.infrastructure.security.auth.SkipPathRequestMatcher;
import kr.flab.movieon.account.infrastructure.security.auth.TokenAccessDeniedHandler;
import kr.flab.movieon.account.infrastructure.security.auth.TokenAuthEntryPoint;
import kr.flab.movieon.account.infrastructure.security.domain.AccountDetailsService;
import kr.flab.movieon.account.infrastructure.security.domain.TokenExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REGISTER_URL = "/api/auth/signup";
    private static final String LOGIN_URL = "/api/auth/login";
    private static final String REFRESH_TOKEN_URL = "/api/auth/token";
    private static final String API_ROOT_URL = "/api/**";

    private final AuthenticationFailureHandler failureHandler;
    private final TokenExtractor tokenExtractor;
    private final JwtAuthProvider jwtAuthProvider;
    private final AccountDetailsService accountDetailsService;
    private final List<String> skipUrls;

    public SecurityConfig(AuthenticationFailureHandler failureHandler,
        TokenExtractor tokenExtractor, JwtAuthProvider jwtAuthProvider,
        AccountDetailsService accountDetailsService) {
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
        this.jwtAuthProvider = jwtAuthProvider;
        this.accountDetailsService = accountDetailsService;

        this.skipUrls = List.of(LOGIN_URL, REGISTER_URL, REFRESH_TOKEN_URL, "api/test/all");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(jwtAuthProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors()

            .and()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers(OPTIONS).permitAll()
            .antMatchers(POST, REGISTER_URL).permitAll()
            .antMatchers(POST, LOGIN_URL).permitAll()
            .antMatchers(POST, REFRESH_TOKEN_URL).permitAll()
            .antMatchers(GET, "/api/test/**").permitAll()
            .anyRequest().authenticated()

            .and().exceptionHandling()
            .accessDeniedHandler(jwtAccessDeniedHandler())
            .authenticationEntryPoint(jwtAuthEntryPoint())

            .and().addFilterBefore(jwtAuthProcessingFilter(skipUrls),
                UsernamePasswordAuthenticationFilter.class);
    }

    private JwtAuthProcessingFilter jwtAuthProcessingFilter(List<String> skipUrls)
        throws Exception {
        var matcher = new SkipPathRequestMatcher(skipUrls, SecurityConfig.API_ROOT_URL);
        var filter = new JwtAuthProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public TokenAccessDeniedHandler jwtAccessDeniedHandler() {
        return new TokenAccessDeniedHandler();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //AuthenticationManager 를 외부에서 사용하기 위하여 @Bean 등록.
        return super.authenticationManagerBean();
    }

    @Bean
    public TokenAuthEntryPoint jwtAuthEntryPoint() {
        return new TokenAuthEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
