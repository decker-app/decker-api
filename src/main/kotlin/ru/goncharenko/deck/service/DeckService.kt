package ru.goncharenko.deck.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.goncharenko.deck.collection.Card
import ru.goncharenko.deck.collection.Deck
import ru.goncharenko.deck.controller.AddCardDTO
import ru.goncharenko.deck.controller.CreateDeckDTO
import ru.goncharenko.deck.dao.DeckDao


@Service
class DeckService(
    private val deckDao: DeckDao,
) {
    fun createDeck(dto: CreateDeckDTO): Deck {
        logger.trace("Start saving Deck = {}", dto)
        val savedDeck = deckDao.saveDeck(
            Deck(
                name = dto.name,
                theme = dto.theme,
            )
        )
        logger.info("Finish saving Deck = $dto")
        return savedDeck
    }

    fun addCard(deckId: String, dto: AddCardDTO): Card {
        logger.trace("Start saving Card = {} to Deck with id={}", dto, deckId)
        require(deckDao.isDeckExist(deckId)) { "Deck with deckId=${deckId} not exist" }

        val savedCard = deckDao.saveCard(
            Card(
                question = dto.question,
                answer = dto.answer,
                deckId = deckId
            )
        )
        logger.info("Finished saving Card = $dto to Deck with id=$deckId")
        return savedCard
    }

    fun getCardsFromDeck(deckId: String): List<Card> {
        logger.trace("Start collecting Cards from Deck with id=$deckId")
        require(deckDao.isDeckExist(deckId)) { "Deck with deckId=${deckId} not exist" }

        val cardList = deckDao.findCardsBy(deckId = deckId)
        logger.info("Finished collecting Cards from Deck with id=$deckId")
        return cardList
    }


    fun getCardFromDeck(deckId: String, cardId: String): Card? {
        logger.trace("Start finding Card with id=$cardId from Deck with id=$deckId")
        require(deckDao.isDeckExist(deckId)) { "Deck with deckId=${deckId} not exist" }

        val card = deckDao.findCardBy(
            deckId = deckId,
            cardId = cardId
        )
        logger.info("Finished finding Card with id=$cardId from Deck with id=$deckId")
        return card
    }

    companion object {
        val logger = LoggerFactory.getLogger(DeckService::class.java)
    }
}