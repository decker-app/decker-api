package ru.goncharenko.deck.service

import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import ru.goncharenko.deck.collection.Card
import ru.goncharenko.deck.collection.Deck
import ru.goncharenko.deck.controller.AddCardDTO
import ru.goncharenko.deck.controller.CreateDeckDTO
import java.time.Instant


@Service
class DeckService(
    private val mongoOperations: MongoOperations,
) {
    fun createDeck(dto: CreateDeckDTO): Deck {
        logger.trace("Start saving Deck with name = ${dto.name} theme = ${dto.theme}")
        val savedDeck = mongoOperations.save(
            Deck(
                theme = dto.theme,
                name = dto.name,
            )
        )
        logger.info("Finish saving Deck with name = ${dto.name} theme = ${dto.theme}")
        return savedDeck
    }

    fun addCard(deckId: String, dto: AddCardDTO): Card {
        require(isDeckExist(deckId)) { "Deck with deckId=${deckId} not exist" }

        return mongoOperations.save(
            Card(
                question = dto.question,
                answer = dto.answer,
                bucket = DEFAULT_BUCKET,
                lastViewDate = DEFAULT_LAST_VIEW_DATE,
                deckId = deckId
            )
        )
    }

    fun getCardsFromDeck(deckId: String): List<Card> {
        require(isDeckExist(deckId)) { "Deck with deckId=${deckId} not exist" }

        return mongoOperations.find(
            Query(Card::deckId isEqualTo deckId),
            Card::class.java
        )
    }


    fun getCardFromDeck(deckId: String, cardId: String): Card? {
        require(isDeckExist(deckId)) { "Deck with deckId=${deckId} not exist" }

        return mongoOperations.findOne(
            Query(
                Criteria().andOperator(
                    Card::deckId isEqualTo deckId,
                    Card::cardId isEqualTo cardId,
                )
            ),
            Card::class.java
        )
    }

    private fun isDeckExist(deckId: String) = mongoOperations.exists(
        Query(Deck::deckId isEqualTo deckId),
        Deck::class.java
    )

    companion object {
        const val DEFAULT_BUCKET = 1
        val DEFAULT_LAST_VIEW_DATE = Instant.MIN!!

        var logger = LoggerFactory.getLogger(DeckService::class.java)
    }
}