package kr.flab.movieon.security.integrate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.http.HttpMethod.*;

@Import(value = {
        DefaultAuthenticationEntryPoint.class,
        DefaultAccessDeniedHandler.class,
        JwtAuthenticationFilter.class,
        FilterChainExceptionHelper.class,
        BCryptPasswordEncoder.class
})
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String REGISTER_URI = "/api/v1/auth/register";
    private static final String REGISTER_CONFIRM_URI = "/api/v1/auth/confirm";
    private static final String LOGIN_URI = "/api/v1/auth/login";
    private static final String RE_ISSUANCE_URI = "/api/v1/auth/reIssuance";
    private static final String SWAGGER_URI = "/swagger-ui/**";
    private static final String SWAGGER_API_URI = "/v3/api-docs/**";

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

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHandle -> exceptionHandle
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(
                        (auth) -> auth
                                .requestMatchers(mvc.pattern(SWAGGER_URI)).permitAll()
                                .requestMatchers(mvc.pattern(SWAGGER_API_URI)).permitAll()
                                .requestMatchers(mvc.pattern(POST, REGISTER_URI)).permitAll()
                                .requestMatchers(mvc.pattern(GET, REGISTER_CONFIRM_URI)).permitAll()
                                .requestMatchers(mvc.pattern(POST, LOGIN_URI)).permitAll()
                                .requestMatchers(mvc.pattern(POST, RE_ISSUANCE_URI)).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(filterChainExceptionHelper, LogoutFilter.class)
                .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    // Fix. https://spring.io/security/cve-2023-34035
    @Scope("prototype")
    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
}
