package ru.goncharenko.deck.infra

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.goncharenko.deck.exception.CardNotFoundException
import ru.goncharenko.deck.exception.DeckNotFoundException
import ru.goncharenko.deck.exception.ResourceNotFoundException
import java.net.URI

// TODO Apply correct type to problem details
@RestControllerAdvice
class ProblemDetailsAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(
        request: HttpServletRequest,
        ex: ResourceNotFoundException,
    ) = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message!!).apply {
        title = "Resource not found"
        type = URI.create("problems/resource-not-found")
    }

    @ExceptionHandler(CardNotFoundException::class)
    fun handleCardNotFoundException(
        request: HttpServletRequest,
        ex: CardNotFoundException,
    ) = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message!!).apply {
        title = "Card not found"
        type = URI.create("problems/resource-not-found/card")
    }

    @ExceptionHandler(DeckNotFoundException::class)
    fun handleDeckNotFoundException(
        request: HttpServletRequest,
        ex: DeckNotFoundException,
    ) = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message!!).apply {
        title = "Deck not found"
        type = URI.create("problems/resource-not-found/deck")
    }
}