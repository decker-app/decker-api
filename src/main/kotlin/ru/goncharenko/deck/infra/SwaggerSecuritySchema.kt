package ru.goncharenko.deck.infra

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme

@SecurityScheme(
    name = "keycloak",
    type = SecuritySchemeType.OPENIDCONNECT,
    openIdConnectUrl = "\${spring.security.oauth2.resourceserver.jwt.issuer-uri}/.well-known/openid-configuration"
)
class SwaggerSecuritySchema
