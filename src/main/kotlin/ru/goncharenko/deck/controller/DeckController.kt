package ru.goncharenko.deck.controller

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.goncharenko.deck.service.DeckService

@RestController
@RequestMapping("api/decks")
class DeckController(
    private val deckService: DeckService,
) {
    @PostMapping
    fun createDeck(@RequestBody dto: CreateDeckDTO) = deckService.createDeck(dto)
}


data class CreateDeckDTO(
    @JsonProperty("name")
    val name: String,

    @JsonProperty("theme")
    val theme: String,
)