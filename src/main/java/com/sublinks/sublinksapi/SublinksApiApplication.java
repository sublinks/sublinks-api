package com.sublinks.sublinksapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Application Boot
 */
@SpringBootApplication
public class SublinksApiApplication {

  /**
   * Main boot method
   *
   * @param args Boot arguments
   */
  public static void main(String[] args) {

    SpringApplication.run(SublinksApiApplication.class, args);
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components()
            .addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
  }

  @Bean
  public GroupedOpenApi v3OpenApi(@Value("${springdoc.version}") String appVersion,
      @Value("${springdoc.pathsToMatch}") String pathsToMatch,
      @Value("#{${springdoc.servers}}") List<String> servers) {
    return GroupedOpenApi.builder().group("v3")
        .addOperationCustomizer((operation, handlerMethod) -> {
          operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
          return operation;
        })
        .addOpenApiCustomizer(openApi -> openApi.info(new Info()
                .title("Lemmy OpenAPI Documentation")
                .version(appVersion))
            .servers(servers.stream().map(s -> new Server().url(s)).toList()))
        .pathsToMatch(pathsToMatch)
        .build();
  }
}
