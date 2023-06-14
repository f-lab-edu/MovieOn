package kr.flab.movieon.supports;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = {"local", "prod"})
public class OpenApiSpecConfiguration {

    @Bean
    public OpenAPI openApi(@Value("${spring.profiles.active}") String profile,
        @Value("${app.url}") String url) {
        var info = info(profile);
        var servers = List.of(new Server()
            .url(url)
            .description("profile (" + profile + ")"));
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("bearer-authentication", securityScheme()))
            .info(info)
            .security(List.of(new SecurityRequirement().addList("bearer-authentication")))
            .servers(servers);
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
            .type(Type.HTTP).scheme("Bearer")
            .bearerFormat("JWT")
            .in(In.HEADER).name("Authorization");
    }

    private Info info(String profile) {
        return new Info()
            .title("MovieOn Api Specification - " + profile)
            .description("MovieOn api provides endpoint for authentication, notification,"
                + " order, product, payment.")
            .version("1.0");
    }
}
