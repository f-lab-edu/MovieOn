package kr.flab.movieon.security.integrate;

import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;

import java.util.List;
import kr.flab.movieon.security.integrate.auth.JwtAuthenticationFilter;
import kr.flab.movieon.security.integrate.auth.JwtAuthenticationProvider;
import kr.flab.movieon.security.integrate.auth.TokenAccessDeniedHandler;
import kr.flab.movieon.security.integrate.auth.TokenAuthenticationEntryPoint;
import kr.flab.movieon.security.integrate.domain.AccountDetailsService;
import kr.flab.movieon.security.integrate.domain.TokenExtractor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REGISTER_URL = "/api/auth/signup";
    private static final String REGISTER_CONFIRM_URL = "api/auth/confirm";
    private static final String LOGIN_URL = "/api/auth/login";
    private static final String REFRESH_TOKEN_URL = "/api/auth/refresh";
    private static final String API_ROOT_URL = "/api/**";

    private final TokenExtractor tokenExtractor;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AccountDetailsService accountDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    public SecurityConfig(TokenExtractor tokenExtractor,
        JwtAuthenticationProvider jwtAuthenticationProvider,
        AccountDetailsService accountDetailsService,
        HandlerExceptionResolver handlerExceptionResolver) {
        this.tokenExtractor = tokenExtractor;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.accountDetailsService = accountDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        web.ignoring()
            .antMatchers(LOGIN_URL, REGISTER_URL, REFRESH_TOKEN_URL, REGISTER_CONFIRM_URL);
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
            .authorizeRequests().antMatchers(OPTIONS).permitAll()
            .antMatchers(POST, REGISTER_URL).permitAll()
            .antMatchers(POST, LOGIN_URL).permitAll()
            .antMatchers(POST, REFRESH_TOKEN_URL).permitAll()
            .antMatchers(API_ROOT_URL).authenticated()

            .and()
            .exceptionHandling()
            .accessDeniedHandler(tokenAccessDeniedHandler(handlerExceptionResolver))
            .authenticationEntryPoint(tokenAuthenticationEntryPoint(handlerExceptionResolver))

            .and()
            .addFilterBefore(jwtAuthProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE"));
        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private JwtAuthenticationFilter jwtAuthProcessingFilter() throws Exception {
        var filter = new JwtAuthenticationFilter(tokenExtractor,
            new AntPathRequestMatcher(API_ROOT_URL));
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenAccessDeniedHandler tokenAccessDeniedHandler(
        HandlerExceptionResolver handlerExceptionResolver) {
        return new TokenAccessDeniedHandler(handlerExceptionResolver);
    }

    @Bean
    public TokenAuthenticationEntryPoint tokenAuthenticationEntryPoint(
        HandlerExceptionResolver handlerExceptionResolver) {
        return new TokenAuthenticationEntryPoint(handlerExceptionResolver);
    }

}
