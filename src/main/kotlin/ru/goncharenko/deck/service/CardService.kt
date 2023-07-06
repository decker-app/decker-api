package ru.goncharenko.deck.service

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Service
import ru.goncharenko.deck.collection.Card
import ru.goncharenko.deck.controller.CreateCardDTO


@Service
class CardService(
    private val mongoOperations: MongoOperations,
) {
    fun createCard(dto: CreateCardDTO) =
        mongoOperations.save(
            Card(
                question = dto.question,
                answer = dto.answer,
                bucket = dto.bucket,
                lastViewDate = dto.lastViewDate,
                deckId = dto.deckId
            )
        )
}