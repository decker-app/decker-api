package ru.goncharenko.deck.controller

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import ru.goncharenko.deck.service.DeckService
import java.time.Instant
import java.time.temporal.ChronoUnit

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

    @PutMapping("decks/{deckId}")
    @PreAuthorize("@deckService.securityCheck(#deckId, authentication.name)")
    fun updateDeck(
        @PathVariable deckId: String,
        @RequestBody dto: UpdateDeckDTO,
    ) = deckService.updateDeck(deckId, dto)

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

    @PutMapping("decks/{deckId}/card/{cardId}")
    @PreAuthorize("@deckService.securityCheck(#deckId, authentication.name)")
    fun updateCard(
        @PathVariable deckId: String,
        @PathVariable cardId: String,
        @RequestBody dto: UpdateCardDTO,
    ) = deckService.updateCard(cardId, dto)

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

    @GetMapping("decks/{deckId}/session")
    @PreAuthorize("@deckService.securityCheck(#deckId, authentication.name)")
    fun getCurrentSessionCards(
        @PathVariable deckId: String,
    ): List<CardDTO> {
        return deckService.getCardsFromDeck(deckId)
            .filter { card ->
                ChronoUnit.DAYS.between(card.lastViewDate, Instant.now()) >= card.bucket
            }
    }

    @PostMapping("decks/{deckId}/session/submit")
    @PreAuthorize("@deckService.securityCheck(#deckId, authentication.name)")
    fun submitCard(
        @RequestParam isSuccessful: Boolean,
        @RequestParam cardId: String,
        @PathVariable deckId: String,
    ): CardDTO {
        val card = deckService.getCardFromDeck(deckId, cardId)
        val newBucket = if (isSuccessful) {
            if (card.bucket > 6) card.bucket else card.bucket + 1
        } else {
            if (card.bucket < 2) card.bucket else card.bucket - 1
        }
        return deckService.updateCard(
            card.copy(
                bucket = newBucket,
                lastViewDate = Instant.now(),
            )
        )
    }
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
)

data class UpdateDeckDTO(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("theme")
    val theme: String,
)

data class UpdateCardDTO(
    @JsonProperty("question")
    val question: String,
    @JsonProperty("answer")
    val answer: String,
)