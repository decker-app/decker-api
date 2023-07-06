package ru.goncharenko.deck.collection

import org.springframework.data.mongodb.core.mapping.*
import java.time.Instant
import java.util.*

@Document("card")
data class Card(
    @MongoId
    val cardId: String = UUID.randomUUID().toString(),
    @Field("question")
    val question: String,
    @Field("answer")
    val answer: String,
    @Field("bucket")
    val bucket: Int,
    @Field("lastViewDate")
    val lastViewDate: Instant,
    @Field("deckId")
    val deckId: String,
)