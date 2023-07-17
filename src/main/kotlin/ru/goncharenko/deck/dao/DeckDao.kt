package ru.goncharenko.deck.dao

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import ru.goncharenko.deck.collection.Card
import ru.goncharenko.deck.collection.Deck


@Service
class DeckDao(
    private val mongoOperations: MongoOperations,
) {

    fun isDeckExist(deckId: String) = mongoOperations.exists(
        Query(Deck::deckId isEqualTo deckId),
        Deck::class.java
    )

    fun findCardBy(deckId: String, cardId: String) = mongoOperations.findOne(
        Query(
            Criteria().andOperator(
                Card::deckId isEqualTo deckId,
                Card::cardId isEqualTo cardId,
            )
        ),
        Card::class.java
    )

    fun findCardsBy(deckId: String): List<Card> = mongoOperations.find(
        Query(Card::deckId isEqualTo deckId),
        Card::class.java
    )

    fun saveDeck(deck: Deck) = mongoOperations.save(deck)

    fun saveCard(card: Card) = mongoOperations.save(card)
}