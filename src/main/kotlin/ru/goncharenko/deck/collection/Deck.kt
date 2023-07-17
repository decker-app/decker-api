package ru.goncharenko.deck.collection

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.MongoId
import java.util.*

@Document("deck")
data class Deck(
    @MongoId
    val deckId: String = UUID.randomUUID().toString(),
    @Field("theme")
    val theme: String,
    @Field("name")
    @Indexed(unique = true)
    val name: String,
)