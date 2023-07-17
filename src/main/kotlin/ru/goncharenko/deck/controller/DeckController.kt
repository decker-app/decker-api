package ru.goncharenko.deck.controller

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.*
import ru.goncharenko.deck.service.DeckService
import java.time.Instant

@RestController
@RequestMapping("api/v1")
class CardController(
    private val deckService: DeckService,
) {
    @PostMapping("decks")
    fun createDeck(
        @RequestBody dto: CreateDeckDTO,
    ) = deckService.createDeck(dto)

    @PostMapping("decks/{deckId}/cards")
    fun addCardToDeck(
        @PathVariable deckId: String,
        @RequestBody dto: AddCardDTO,
    ) = deckService.addCard(deckId, dto)

    @GetMapping("decks/{deckId}/cards/{cardId}")
    fun getCardByDeckId(
        @PathVariable deckId: String,
        @PathVariable cardId: String,
    ) = deckService.getCardFromDeck(deckId, cardId)

    @GetMapping("decks/{deckId}/cards")
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
    @JsonProperty("cardId")
    val cardId: String,
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
    @JsonProperty("name")
    val name: String,
    @JsonProperty("theme")
    val theme: String,
)