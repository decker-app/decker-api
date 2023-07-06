package ru.goncharenko.deck.service

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Service
import ru.goncharenko.deck.collection.Deck
import ru.goncharenko.deck.controller.CreateDeckDTO


@Service
class DeckService(
    private val mongoOperations: MongoOperations,
) {
    fun createDeck(dto: CreateDeckDTO) =
        mongoOperations.save(
            Deck(
                theme = dto.theme,
                name = dto.name,
            )
        )
}