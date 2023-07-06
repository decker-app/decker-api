package ru.goncharenko.deck.collection

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.*

@Document("card")
data class Card(
    @MongoId
    val cardId: ObjectId = ObjectId(),
    @Field("question")
    val question: String,
    @Field("answer")
    val answer: String,
    @Field("bucket")
    val bucket: Int,
    @Field("lastViewDate")
    val lastViewDate: String,
    @Field("deckId")
    val deckId: ObjectId
)