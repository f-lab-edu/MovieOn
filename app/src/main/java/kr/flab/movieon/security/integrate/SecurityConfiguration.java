package kr.flab.movieon.security.integrate;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Import(value = {
    DefaultAuthenticationEntryPoint.class,
    DefaultAccessDeniedHandler.class,
    JwtAuthenticationFilter.class,
    FilterChainExceptionHelper.class,
    BCryptPasswordEncoder.class
})
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String REGISTER_URI = "/api/v1/auth/register";
    private static final String REGISTER_CONFIRM_URI = "/api/v1/auth/confirm";
    private static final String LOGIN_URI = "/api/v1/auth/login";
    private static final String RE_ISSUANCE_URI = "/api/v1/auth/reIssuance";
    private static final String[] SWAGGER_URI = {"/v2/api-docs", "/swagger-resources",
        "/swagger-resources/**", "/configuration/ui", "/configuration/security",
        "/swagger-ui.html", "/webjars/**", "/v3/api-docs", "/swagger-ui/**"};

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final FilterChainExceptionHelper filterChainExceptionHelper;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
        FilterChainExceptionHelper filterChainExceptionHelper,
        AuthenticationEntryPoint authenticationEntryPoint,
        AccessDeniedHandler accessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.filterChainExceptionHelper = filterChainExceptionHelper;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler)
            .and()

            .authorizeRequests()
            .antMatchers(SWAGGER_URI).permitAll()
            .antMatchers(OPTIONS).permitAll()
            .antMatchers(POST, REGISTER_URI).permitAll()
            .antMatchers(GET, REGISTER_CONFIRM_URI).permitAll()
            .antMatchers(POST, LOGIN_URI).permitAll()
            .antMatchers(POST, RE_ISSUANCE_URI).permitAll()
            .anyRequest().authenticated()

            .and()
            .addFilterBefore(filterChainExceptionHelper, LogoutFilter.class)
            .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
