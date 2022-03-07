package kr.flab.movieon.security.integrate;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    private static final String REGISTER_URL = "/api/v1/auth/register";
    private static final String REGISTER_CONFIRM_URL = "/api/v1/auth/confirm";
    private static final String LOGIN_URL = "/api/v1/auth/login";
    private static final String REFRESH_TOKEN_URL = "/api/v1/auth/refresh";

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
            .antMatchers(OPTIONS).permitAll()
            .antMatchers(POST, REGISTER_URL).permitAll()
            .antMatchers(GET, REGISTER_CONFIRM_URL).permitAll()
            .antMatchers(POST, LOGIN_URL).permitAll()
            .antMatchers(POST, REFRESH_TOKEN_URL).permitAll()
            .anyRequest().authenticated()

            .and()
            .addFilterBefore(filterChainExceptionHelper, LogoutFilter.class)
            .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
