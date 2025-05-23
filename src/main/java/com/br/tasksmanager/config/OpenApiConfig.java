package com.br.tasksmanager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Email",
                        email = "allisonsouza10261@gmail.com"
                ),
                title = "Task Manager",
                description = "Api to manage Tasks",
                version = "1.0",
                license = @License(
                    url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
//                termsOfService = ""
        ),
        servers = {
                @Server(
                        url = "/",
                        description = "any description of Server URL"
                )
        },
        security = {
               @SecurityRequirement(
                       name = "bearerAuth"
               )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "log in to get JWT",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
