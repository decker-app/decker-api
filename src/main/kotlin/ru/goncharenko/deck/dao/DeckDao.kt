package ru.goncharenko.deck.dao

import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.query.isEqualTo
import org.springframework.stereotype.Service
import ru.goncharenko.deck.collection.Card
import ru.goncharenko.deck.collection.Deck
import ru.goncharenko.deck.controller.UpdateCardDTO
import ru.goncharenko.deck.controller.UpdateDeckDTO


@Service
class DeckDao(
    private val mongoOperations: MongoOperations,
) {

    fun isDeckExist(deckId: String) = mongoOperations.exists(
        Query(Deck::deckId isEqualTo deckId),
        Deck::class.java
    )

    fun isDeckExist(
        deckId: String,
        userId: String,
    ) = mongoOperations.exists(
        Query(
            Criteria().andOperator(
                Deck::deckId isEqualTo deckId,
                Deck::userId isEqualTo userId,
            )
        ),
        Deck::class.java
    )

    fun findDeckBy(deckId: String) = mongoOperations.findOne(
        Query(
            Criteria().andOperator(
                Deck::deckId isEqualTo deckId,
            )
        ),
        Deck::class.java
    )

    fun findDeckBy(deckId: String, userId: String) = mongoOperations.findOne(
        Query(
            Criteria().andOperator(
                Deck::deckId isEqualTo deckId,
                Deck::userId isEqualTo userId,
            )
        ),
        Deck::class.java
    )

    fun findAndUpdateDeck(deckId: String, deckDto: UpdateDeckDTO) = mongoOperations.findAndModify(
        Query(
            Deck::deckId isEqualTo deckId,
        ),
        Update()
            .set(Deck::name.name, deckDto.name)
            .set(Deck::theme.name, deckDto.theme),
        FindAndModifyOptions.options().returnNew(true),
        Deck::class.java
    )


    fun findAndUpdateCard(cardId: String, cardDTO: UpdateCardDTO) = mongoOperations.findAndModify(
        Query(
            Card::cardId isEqualTo cardId,
        ),
        Update()
            .set(Card::question.name, cardDTO.question)
            .set(Card::answer.name, cardDTO.answer),
        FindAndModifyOptions.options().returnNew(true),
        Card::class.java
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