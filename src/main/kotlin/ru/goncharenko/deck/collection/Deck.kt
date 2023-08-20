package ru.goncharenko.deck.collection

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId

@Document("deck")
data class Deck(
    @MongoId
    val deckId: ObjectId = ObjectId(),

    @Field("theme")
    val theme: String,

    @Field("name")
    @Indexed(unique = true)
    val name: String,

    @Field("userId")
    val userId: String,
)