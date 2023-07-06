package ru.goncharenko.deck.service

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import ru.goncharenko.deck.collection.Card
import ru.goncharenko.deck.collection.Deck
import ru.goncharenko.deck.controller.CreateCardDTO


@Service
class CardService(
    private val mongoOperations: MongoOperations,
) {
    fun createCard(dto: CreateCardDTO): Card {
        val isDeckExist = mongoOperations.exists(
            Query(Deck::deckId isEqualTo dto.deckId),
            Deck::class.java
        )

        require(isDeckExist) { "Deck with deckId=${dto.deckId} not exist" }

        return mongoOperations.save(
            Card(
                question = dto.question,
                answer = dto.answer,
                bucket = dto.bucket,
                lastViewDate = dto.lastViewDate,
                deckId = dto.deckId
            )
        )
    }
}