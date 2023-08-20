package ru.goncharenko.deck.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.goncharenko.deck.collection.Card
import ru.goncharenko.deck.collection.Deck
import ru.goncharenko.deck.controller.AddCardDTO
import ru.goncharenko.deck.controller.CardDTO
import ru.goncharenko.deck.controller.CreateDeckDTO
import ru.goncharenko.deck.controller.DeckDTO
import ru.goncharenko.deck.dao.DeckDao
import ru.goncharenko.deck.exception.CardNotFoundException
import ru.goncharenko.deck.exception.DeckNotFoundException


@Service
class DeckService(
    private val deckDao: DeckDao,
) {
    fun createDeck(dto: CreateDeckDTO, userId: String): DeckDTO {
        logger.trace("Start saving Deck = {}", dto)

        val savedDeck = deckDao.saveDeck(
            Deck(
                name = dto.name,
                theme = dto.theme,
                userId = userId,
            )
        )

        logger.trace("Finish saving Deck = {}", dto)
        return savedDeck.toDTO()
    }

    fun addCard(deckId: String, dto: AddCardDTO): CardDTO {
        logger.trace("Start saving Card = {} to Deck with id={}", dto, deckId)
        if (!deckDao.isDeckExist(deckId)) throw DeckNotFoundException("Deck with id=$deckId not found")

        val savedCard = deckDao.saveCard(
            Card(
                question = dto.question,
                answer = dto.answer,
                deckId = deckId
            )
        )

        logger.trace("Finished saving Card = {} to Deck with id={}", dto, deckId)
        return savedCard.toDTO()
    }

    fun getCardsFromDeck(deckId: String): List<CardDTO> {
        logger.trace("Start collecting Cards from Deck with id=$deckId")
        if (!deckDao.isDeckExist(deckId)) throw DeckNotFoundException("Deck with id=$deckId not found")

        val cardList = deckDao.findCardsBy(deckId = deckId)

        logger.trace("Finished collecting Cards from Deck with id=$deckId")
        return cardList.map { card -> card.toDTO() }
    }


    fun getCardFromDeck(deckId: String, cardId: String): CardDTO {
        logger.trace("Start finding Card with id=$cardId from Deck with id=$deckId")
        if (!deckDao.isDeckExist(deckId)) throw DeckNotFoundException("Deck with id=$deckId not found")

        val card = deckDao.findCardBy(
            deckId = deckId,
            cardId = cardId
        ) ?: throw CardNotFoundException("Card with cardId=$cardId, deckId=$deckId not found")

        logger.trace("Finished finding Card with id=$cardId from Deck with id=$deckId")
        return card.toDTO()
    }

    fun getDeckInfo(deckId: String): DeckDTO {
        logger.trace("Start finding Deck with id=$deckId")
        val deck = deckDao.findDeckBy(deckId) ?: throw DeckNotFoundException("Deck with id=$deckId not found")
        logger.trace("Finish finding Deck with id=$deckId")
        return deck.toDTO()
    }

    fun securityCheck(
        deckId: String,
        userId: String,
    ) = deckDao.isDeckExist(deckId, userId)

    companion object {
        val logger = LoggerFactory.getLogger(DeckService::class.java)
    }

    private fun Card.toDTO() = CardDTO(
        id = cardId.toHexString(),
        question = question,
        answer = answer,
        bucket = bucket,
        lastViewDate = lastViewDate,
        deckId = deckId
    )

    private fun Deck.toDTO() = DeckDTO(
        id = deckId.toHexString(),
        name = name,
        theme = theme,
        userId = userId,
    )
}