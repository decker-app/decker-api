package ru.goncharenko.deck.collection

import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.*
import java.time.Instant
import java.util.*

@Document("card")
@CompoundIndex(
    name = "question_answer_unique",
    def = "{'question' : 1, 'answer' : 1}",
    unique = true,
)
data class Card(
    @MongoId
    val cardId: String = UUID.randomUUID().toString(),
    @Field("question")
    val question: String,
    @Field("answer")
    val answer: String,
    @Field("bucket")
    val bucket: Int = 1,
    @Field("lastViewDate")
    val lastViewDate: Instant = Instant.now(),
    @Field("deckId")
    val deckId: String,
)