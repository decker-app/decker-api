package ru.goncharenko.deck.service

import org.bson.types.ObjectId
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.goncharenko.deck.collection.Card
import ru.goncharenko.deck.collection.Deck
import ru.goncharenko.deck.controller.*
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

    fun updateDeck(deckId: String, dto: UpdateDeckDTO): DeckDTO? {
        logger.trace("Start updating Deck = {}", dto)

        val updatedDeckDTO = deckDao.findAndUpdateDeck(
            deckId, dto
        )

        logger.trace("Finish updating Deck = {}", dto)
        return updatedDeckDTO?.toDTO()
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

    fun updateCard(cardId: String, dto: UpdateCardDTO): CardDTO? {
        logger.trace("Start updating Card = {}", dto)

        val updatedDeckDTO = deckDao.findAndUpdateCard(
            cardId, dto
        )

        logger.trace("Finish updating Card = {}", dto)
        return updatedDeckDTO?.toDTO()
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

    fun updateCard(cardDTO: CardDTO): CardDTO {
        logger.trace("Start updating Card with id=${cardDTO.id} from Deck with id=${cardDTO.deckId}")

        val card = deckDao.saveCard(cardDTO.toDocument())

        logger.trace("Finished updating Card with id=${cardDTO.id} from Deck with id=${cardDTO.deckId}")
        return card.toDTO()
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

    private fun CardDTO.toDocument() = Card(
        cardId = ObjectId(id),
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
    )
}