package ru.goncharenko.deck.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.goncharenko.deck.service.DeckService
import java.util.*

@RestController
@RequestMapping("/local")
@SecurityRequirement(name = "keycloak")
@ConditionalOnProperty(
    prefix = "local-controller",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = false,
)
class LocalController(
    private val deckService: DeckService,
) {
    @GetMapping("me")
    fun me(): Authentication? {
        return SecurityContextHolder.getContext().authentication
    }

    @GetMapping("/random/deck")
    fun createRandomDeck(
        @RequestParam count: Int,
        @AuthenticationPrincipal jwt: Jwt,
    ): List<CardDTO> {
        val (id) = deckService.createDeck(
            CreateDeckDTO(
                theme = UUID.randomUUID().toString(),
                name = UUID.randomUUID().toString(),
            ), jwt.subject
        )
        val result = mutableListOf<CardDTO>()
        repeat(count) {
            deckService.addCard(
                id, AddCardDTO(
                    question = UUID.randomUUID().toString(),
                    answer = UUID.randomUUID().toString(),
                )
            ).also { result.add(it) }
        }

        return result
    }
}