package ru.goncharenko.deck.collection

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Document("card")
@CompoundIndex(
    name = "unique_question_in_deck",
    def = "{'question' : 1, 'answer' : 1, deckId: 1}",
    unique = true,
)
data class Card(
    @MongoId
    val cardId: ObjectId = ObjectId(),

    @Field("question")
    val question: String,

    @Field("answer")
    val answer: String,

    @Field("bucket")
    val bucket: Int = 1,

    // solve issue with Instant.MIN
    @Field("lastViewDate")
    val lastViewDate: Instant = Instant.EPOCH,

    @Field("deckId")
    val deckId: String,
)