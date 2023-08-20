package ru.goncharenko.deck.controller

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import ru.goncharenko.deck.service.DeckService
import java.time.Instant

// TODO: Better use AuthenticationPrincipal over @PreAuthorize
@RestController
@RequestMapping("api/v1")
@SecurityRequirement(name = "keycloak")
class CardController(
    private val deckService: DeckService,
) {
    @PostMapping("decks")
    fun createDeck(
        @RequestBody dto: CreateDeckDTO,
        @AuthenticationPrincipal jwt: Jwt,
    ) = deckService.createDeck(dto, jwt.subject)

    @GetMapping("decks/{deckId}")
    @PreAuthorize("@deckService.securityCheck(#deckId, authentication.name)")
    fun getDeckInfo(
        @PathVariable deckId: String,
    ) = deckService.getDeckInfo(deckId)

    @PostMapping("decks/{deckId}/cards")
    @PreAuthorize("@deckService.securityCheck(#deckId, authentication.name)")
    fun addCardToDeck(
        @PathVariable deckId: String,
        @RequestBody dto: AddCardDTO,
    ) = deckService.addCard(deckId, dto)

    @GetMapping("decks/{deckId}/cards/{cardId}")
    @PreAuthorize("@deckService.securityCheck(#deckId, authentication.name)")
    fun getCardByDeckId(
        @PathVariable deckId: String,
        @PathVariable cardId: String,
    ) = deckService.getCardFromDeck(deckId, cardId)

    @GetMapping("decks/{deckId}/cards")
    @PreAuthorize("@deckService.securityCheck(#deckId, authentication.name)")
    fun getCardsByDeckId(
        @PathVariable deckId: String,
    ) = deckService.getCardsFromDeck(deckId)
}

data class AddCardDTO(
    @JsonProperty("question")
    val question: String,
    @JsonProperty("answer")
    val answer: String,
)

data class CardDTO(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("question")
    val question: String,
    @JsonProperty("answer")
    val answer: String,
    @JsonProperty("bucket")
    val bucket: Int,
    @JsonProperty("lastViewDate")
    val lastViewDate: Instant,
    @JsonProperty("deckId")
    val deckId: String,
)

data class CreateDeckDTO(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("theme")
    val theme: String,
)

data class DeckDTO(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("theme")
    val theme: String,
    @JsonProperty("userId")
    val userId: String,
)