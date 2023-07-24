package ru.goncharenko.deck.exception

open class ResourceNotFoundException(message: String) : RuntimeException(message)

class CardNotFoundException(message: String) : ResourceNotFoundException(message)

class DeckNotFoundException(message: String) : ResourceNotFoundException(message)