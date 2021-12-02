package kr.flab.movieon.account.infrastructure.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;

import java.util.Arrays;
import java.util.List;
import kr.flab.movieon.account.infrastructure.security.JwtTokenAuthFilter;
import kr.flab.movieon.account.infrastructure.security.SkipPathRequestMatcher;
import kr.flab.movieon.account.infrastructure.security.TokenAccessDeniedHandler;
import kr.flab.movieon.account.infrastructure.security.TokenAuthEntryPoint;
import kr.flab.movieon.account.infrastructure.security.TokenUtils;
import kr.flab.movieon.account.infrastructure.security.domain.AccountDetailsService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REGISTER_URL = "/api/auth/signup";
    private static final String LOGIN_URL = "/api/auth/login";
    private static final String API_ROOT_URL = "/api/**";
    private final AccountDetailsService accountDetailsService;
    private final TokenUtils tokenUtils;
    private List<String> skipUrls = Arrays.asList(LOGIN_URL, REGISTER_URL, "api/test/all");

    public SecurityConfig(
        AccountDetailsService accountDetailsService,
        TokenUtils tokenUtils) {
        this.accountDetailsService = accountDetailsService;
        this.tokenUtils = tokenUtils;
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
        throws Exception {
        authenticationManagerBuilder.userDetailsService(accountDetailsService)
            .passwordEncoder(passwordEncoder());
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
            .antMatchers(POST, "/api/auth/**").permitAll()
            .antMatchers(GET, "/api/test/**").permitAll()
            .anyRequest().authenticated()

            .and()
            .exceptionHandling()
            .accessDeniedHandler(jwtAccessDeniedHandler())
            .authenticationEntryPoint(jwtAuthEntryPoint())

            .and()
            .addFilterBefore(
                jwtTokenAuthFilter(accountDetailsService),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public JwtTokenAuthFilter jwtTokenAuthFilter(AccountDetailsService accountDetailsService) {
        return new JwtTokenAuthFilter(
            tokenUtils,
            accountDetailsService,
            new SkipPathRequestMatcher(skipUrls, API_ROOT_URL));
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
