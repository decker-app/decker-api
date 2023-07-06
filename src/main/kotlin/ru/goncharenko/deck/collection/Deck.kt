package ru.goncharenko.deck.collection

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("deck")
data class Deck(
    @Id
    val deckId: ObjectId = ObjectId(),
    @Field("theme")
    val theme: String,
    @Field("name")
    val name: String,
)

