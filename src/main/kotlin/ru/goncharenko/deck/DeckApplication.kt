package ru.goncharenko.deck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeckApplication

fun main(args: Array<String>) {
    runApplication<DeckApplication>(*args)
}
