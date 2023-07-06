package ru.goncharenko.deck.controller

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.goncharenko.deck.service.CardService
import java.time.Instant

@RestController
@RequestMapping("api/cards")
class CardController(
    private val cardService: CardService,
) {
    @PostMapping
    fun createCard(@RequestBody dto: CreateCardDTO) = cardService.createCard(dto)
}

data class CreateCardDTO(
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