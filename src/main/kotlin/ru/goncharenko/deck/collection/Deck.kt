package ru.goncharenko.deck.collection

import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId

@Document("deck")
@CompoundIndex(
    name = "unique_question_in_deck",
    def = "{'name' : 1, 'userId' : 1}",
    unique = true,
)
data class Deck(
    @MongoId
    val deckId: ObjectId = ObjectId(),

    @Field("theme")
    val theme: String,

    @Field("name")
    val name: String,

    @Field("userId")
    val userId: String,
)